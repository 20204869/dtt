package com.example.dtt.service.extract.impl;

import com.example.dtt.constant.datasource.Constants;
import com.example.dtt.datasource.common.DataSourceClientProvider;
import com.example.dtt.datasource.common.datasource.BaseConnectionParam;
import com.example.dtt.enums.DbType;
import com.example.dtt.mapper.extract.ExtractTemplateMapper;
import com.example.dtt.service.extract.ExtractTemplateService;
import com.example.dtt.utils.JSONUtils;
import com.example.dtt.utils.sql.DataSourceUtils;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author reid
 * @date 2022/3/27 16:33
 * @describe
 */
@Service
public class ExtractTemplateServiceImpl implements ExtractTemplateService {
    protected final Logger logger = LoggerFactory.getLogger(ExtractTemplateServiceImpl.class);

    @Autowired
    private ExtractTemplateMapper templateMapper;

    @Override
    public List<LinkedHashMap<String, Object>> runHiveResult(String executeSql) {
        return templateMapper.runHiveResult(executeSql);
    }

    @Override
    public List<LinkedHashMap<String, Object>> templateResult(String executeSql) {
        BaseConnectionParam baseConnectionParam = (BaseConnectionParam) DataSourceUtils.buildConnectionParams(
                DbType.valueOf("HIVE"), Constants.PRO_HIVE);
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        List<LinkedHashMap<String, Object>> result = new ArrayList<>();
        try {
            // create connection
            connection = DataSourceClientProvider.getInstance().getConnection(DbType.valueOf("HIVE"), baseConnectionParam);
            // pre sql
            String preSql = Constants.SQL_ADMIN;
            stmt = connection.prepareStatement(preSql);
            stmt.executeUpdate();
            //TODO SQL优化处理
            stmt = connection.prepareStatement(executeSql);
            resultSet = stmt.executeQuery();
            result = resultProcess(resultSet);
        } catch (Exception e) {
            logger.error("execute sql error: {}", e.getMessage());
            e.printStackTrace();
        } finally {
            close(resultSet, stmt, connection);
        }
        return result;
    }

    private List<LinkedHashMap<String, Object>> resultProcess(ResultSet resultSet) throws Exception {
        List<LinkedHashMap<String, Object>> result = new ArrayList<>();
        if (resultSet != null) {
            ResultSetMetaData md = resultSet.getMetaData();
            int num = md.getColumnCount();
            while (resultSet.next()) {
                LinkedHashMap<String,Object> tmpMap = new LinkedHashMap<>();
                for (int i = 1; i <= num; i++) {
                    tmpMap.put(md.getColumnLabel(i), resultSet.getObject(i));
                }
                result.add(tmpMap);
            }
        }
       // logger.debug("execute sql result : {}", result);
        return result;
    }

    /**
     * 查询结果转string
     *
     * @param resultSet
     * @return
     * @throws Exception
    private String resultProcess(ResultSet resultSet) throws Exception {
        ArrayNode resultJSONArray = JSONUtils.createArrayNode();
        if (resultSet != null) {
            ResultSetMetaData md = resultSet.getMetaData();
            int num = md.getColumnCount();
            while (resultSet.next()) {
                ObjectNode mapOfColValues = JSONUtils.createObjectNode();
                for (int i = 1; i <= num; i++) {
                    mapOfColValues.set(md.getColumnLabel(i), JSONUtils.toJsonNode(resultSet.getObject(i)));
                }
                resultJSONArray.add(mapOfColValues);
            }
        }
        String result = JSONUtils.toJsonString(resultJSONArray);
        logger.debug("execute sql result : {}", result);
        return result;
    }*/

    /**
     * 关闭数据库连接
     *
     * @param resultSet
     * @param pstmt
     * @param connection
     */
    private void close(ResultSet resultSet, PreparedStatement pstmt, Connection connection) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                logger.error("close result set error : {}", e.getMessage(), e);
            }
        }
        if (pstmt != null) {
            try {
                pstmt.close();
            } catch (SQLException e) {
                logger.error("close prepared statement error : {}", e.getMessage(), e);
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.error("close connection error : {}", e.getMessage(), e);
            }
        }
    }
}

package com.example.dtt.service.query.impl;

import com.example.dtt.constant.datasource.Constants;
import com.example.dtt.datasource.common.DataSourceClientProvider;
import com.example.dtt.datasource.common.datasource.BaseConnectionParam;
import com.example.dtt.domain.entity.query.HistoryQuery;
import com.example.dtt.domain.entity.query.HiveUser;
import com.example.dtt.domain.entity.query.QueryLog;
import com.example.dtt.enums.DbType;
import com.example.dtt.exception.GlobalException;
import com.example.dtt.mapper.query.HistoryQueryMapper;
import com.example.dtt.mapper.query.HiveUserMapper;
import com.example.dtt.service.query.HistoryQueryService;
import com.example.dtt.utils.sql.DataSourceUtils;
import org.apache.hive.jdbc.HiveStatement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author reid
 * @date 2021/11/23 13:46
 * @describe 数据查询 历史查询记录 业务层
 */
@Service
public class HistoryQueryServiceImpl implements HistoryQueryService {
    protected final Logger logger = LoggerFactory.getLogger(HistoryQueryServiceImpl.class);

    @Autowired
    private HistoryQueryMapper queryMapper;

    @Autowired
    private HiveUserMapper hiveUserMapper;

    @Override
    public List<HistoryQuery> historyQueryByUserName(String userName) {
        return queryMapper.historyQueryByUserName(userName);
    }

    @Override
    public int saveQuery(HistoryQuery historyQuery) {
        return queryMapper.saveQuery(historyQuery);
    }

    @Override
    public QueryLog logByUserName(String userName) {
        return queryMapper.logByUserName(userName);
    }

    @Override
    public List<LinkedHashMap<String, Object>> runHive(String userName, String executeSql) {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
       // String path = "D:\\";
        //查询连接的hive用户
        HiveUser hiveUser = hiveUserMapper.queryHiveUserByName(userName);
        if (hiveUser == null) {
            new GlobalException("当前用户无法执行查询操作，请联系管理员！");
        }
        //创建hive连接的参数信息
        BaseConnectionParam baseConnectionParam = (BaseConnectionParam) DataSourceUtils.buildConnectionParams(
                DbType.valueOf("HIVE"), hiveUser.getConnectionParams());

        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
       // HiveStatement hiveStmt = null;
        List<LinkedHashMap<String, Object>> result = new ArrayList<>();
        try {
            // create connection
            connection = DataSourceClientProvider.getInstance().getConnection(DbType.valueOf("HIVE"), baseConnectionParam);
            // pre sql
          String preSql = Constants.SQL_ADMIN;
            stmt = connection.prepareStatement(preSql);
            stmt.executeUpdate();

            stmt = connection.prepareStatement(executeSql);
            resultSet = stmt.executeQuery();
            result = resultProcess(resultSet);

          /*  Class.forName("org.apache.hive.jdbc.HiveDriver");
            connection = DriverManager.getConnection("jdbc:hive2://10.42.0.122:10000", "root", "root");
            hiveStmt = (HiveStatement) connection.createStatement();

            String fileName = path + userName + ".txt";
            Thread logThread = new Thread(new LogTask(hiveStmt, fileName));
            logThread.setDaemon(true);
            logThread.start();
            boolean hasResult = hiveStmt.execute(executeSql);
            logThread.interrupt();
            if (hasResult) {
                resultSet = hiveStmt.getResultSet();
                result = resultProcess(resultSet);
            } else {
                hiveStmt.getUpdateCount();
            }*/
        } catch (Exception e) {
            logger.error("execute sql error: {}", e.getMessage());
            e.printStackTrace();
        } finally {
            close(resultSet, stmt, connection);
        }
        return result;
    }

    public static class LogTask implements Runnable {
        private HiveStatement stmt;
        private String fileName;

        public LogTask(HiveStatement stmt, String fileName) {
            this.stmt = stmt;
            this.fileName = fileName;
        }

        @Override
        public void run() {
            try {
                while (stmt.hasMoreLogs()) {
                    try {
                        for (String line : stmt.getQueryLog(true, 1000)) {
                            //System.out.println(line);
                            File file = new File(fileName);
                            if (!file.exists()) {
                                file.createNewFile();
                            }
                            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file,true)));
                            out.write(line+"\r\n");
                            out.close();
                        }
                       // Thread.sleep(200);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private List<LinkedHashMap<String, Object>> resultProcess(ResultSet resultSet) throws Exception {
        List<LinkedHashMap<String, Object>> result = new ArrayList<>();
        if (resultSet != null) {
            ResultSetMetaData md = resultSet.getMetaData();
            int num = md.getColumnCount();
            while (resultSet.next()) {
                LinkedHashMap<String, Object> tmpMap = new LinkedHashMap<>();
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

package com.example.dtt.service.datasource;

import com.example.dtt.datasource.common.BaseDataSourceParamDTO;
import com.example.dtt.datasource.common.datasource.ConnectionParam;
import com.example.dtt.domain.AjaxResult;
import com.example.dtt.domain.entity.datasource.DataSource;
import com.example.dtt.enums.DbType;

import java.util.List;
import java.util.Map;

/**
 * 数据源 服务层接口
 */
public interface DataSourceService {

    /**
     * 创建数据源
     * @param datasourceParam
     * @return
     */
    AjaxResult createDataSource(String userName,BaseDataSourceParamDTO datasourceParam);

    /**
     * 测试连接信息
     * @param type
     * @param parameter
     * @return
     */
    AjaxResult checkConnection(DbType type, ConnectionParam parameter);

    /**
     * 更新数据源
     * @param dataSourceParam
     * @return
     */
    int updateDataSource(int id,BaseDataSourceParamDTO dataSourceParam,String userName);

    /**
     * 获取数据源详情
     * @param id
     * @return
     */
    AjaxResult queryDataSource(int id);

    /**
     * 数据源 列表
     */
    List<DataSource> queryDataSourceListPaging(DataSource source);

    /**
     * delete datasource
     *
     * @param datasourceId data source id
     * @return delete result code
     */
    int deleteDataSourceById(Long datasourceId);

    /**
     * 批量删除数据源
     * @param ids
     * @return
     */
    int deleteDataSourceByIds(Long[] ids);

}

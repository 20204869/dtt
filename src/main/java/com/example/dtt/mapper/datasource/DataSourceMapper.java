package com.example.dtt.mapper.datasource;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.dtt.domain.entity.datasource.DataSource;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 数据源 映射器接口
 */
public interface DataSourceMapper extends BaseMapper<DataSource>{

    /**
     * 数据源列表
     */
    List<DataSource> queryDataSourceListPaging(DataSource source);

    /**
     * 根据数据源名查询数据源
     * @param name
     * @return
     */
    List<DataSource> queryDataSourceByName(@Param("name") String name);

    /**
     * 根据id查询数据源
     * @param id
     * @return
     */
    DataSource queryDataSourceById(@Param("id") int id);

    /**
     * 根据id删除数据源
     * @param id
     * @return
     */
    int deleteDataSourceById(@Param("id") Long id);

    /**
     * 更新数据源信息
     * @return
     */
    int updateDataSource(DataSource source);

    /**
     * 批量删除数据源
     * @param ids
     * @return
     */
    int deleteDataSourceByIds(Long[] ids);

    /**
     * 创建数据源
     * @param source
     * @return
     */
    int createDataSource(DataSource source);
}

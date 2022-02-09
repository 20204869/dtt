package com.example.dtt.service.load;

import com.example.dtt.domain.entity.load.LoadFileConf;

import java.util.List;

/**
 * @author reid
 * @date 2021/12/24 15:26
 * @describe 导入文件配置管理 业务层
 */
public interface LoadFileConfService {

    /**
     * 查询配置列表
     * @param conf
     * @return
     */
    List<LoadFileConf> LoadFileConfList(LoadFileConf conf);

    /**
     * 获取配置信息
     * @return
     */
    List<LoadFileConf> getConf();

    /**
     * 新增配置
     * @param conf
     * @return
     */
    int saveLoadFileConf(LoadFileConf conf);

    /**
     * 根据文件id删除文件
     * @param confIds
     */
    int deleteConfByIds(Long[] confIds);

    /**
     * 验证是否已存在HDFS 路径
     * @param hdfsPath
     * @return
     */
    int checkConfByHdfsPath(String hdfsPath);

    /**
     * 验证是否已存在hive table
     * @param hiveTable
     * @return
     */
    int checkConfByHiveTable(String hiveTable);

    /**
     * 修改配置
     * @param conf
     * @return
     */
    int updateConf(LoadFileConf conf);

    /**
     * 通过编号查询配置详情
     * @param confId
     * @return
     */
    LoadFileConf selectConfById(Long confId);

}

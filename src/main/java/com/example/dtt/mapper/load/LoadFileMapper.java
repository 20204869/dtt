package com.example.dtt.mapper.load;

import com.example.dtt.domain.entity.load.LoadFile;

import java.util.List;

/**
 * 导入文件 数据层
 */
public interface LoadFileMapper {

    /**
     * 查询文件列表
     * @param file
     * @return
     */
    List<LoadFile> LoadFileList(LoadFile file);

    /**
     * 导入文件
     * @param file
     * @return
     */
    int saveFile(LoadFile file);

    /**
     * 根据文件id删除文件
     * @param fileIds
     */
    int deleteFileByIds(Long[] fileIds);

    /**
     * 根据id查询文件列表
     * @param fileIds
     * @return
     */
    List<LoadFile> loadFilesByIds(Long[] fileIds);
}

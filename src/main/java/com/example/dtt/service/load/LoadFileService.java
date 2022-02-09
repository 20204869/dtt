package com.example.dtt.service.load;

import com.example.dtt.domain.entity.load.LoadFile;

import java.util.List;

/**
 * @author reid
 * @date 2021/12/24 15:26
 * @describe 导入文件 业务层
 */
public interface LoadFileService {

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

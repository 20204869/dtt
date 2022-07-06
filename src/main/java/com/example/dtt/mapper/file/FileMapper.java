package com.example.dtt.mapper.file;

import com.example.dtt.domain.entity.file.Resource;
import com.example.dtt.domain.entity.load.LoadFile;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 文件管理 数据层
 */
public interface FileMapper {

    /**
     * 创建文件夹
     * @return
     */
    int createDirectory(Resource resource);

    /**
     * check resource exist
     * @param fullName full name
     * @return true if exist else return null
     */
    Boolean existFile(@Param("fullName") String fullName);


    /**
     * 按id查询文件详情
     * @param pid
     * @return
     */
    Resource selectByPId(int pid);

    /**
     * 按id查询文件详情
     * @param id
     * @return
     */
    Resource selectById(int id);

    /**
     * 根据条件筛选文件
     * @param resource
     * @return
     */
    List<Resource> fileList(Resource resource);

    List<Resource> queryResource(String fullName);

    int updateById(Resource resource);

    List<Integer> listChildren(@Param("direcotyId") int direcotyId);

    List<Resource> listFileByIds(@Param("resIds")Integer[] resIds);

    int batchUpdateFile(@Param("resourceList") List<Resource> resourceList);

    int deleteIds(@Param("resIds")Integer[] resIds);
}

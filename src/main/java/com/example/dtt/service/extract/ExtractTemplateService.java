package com.example.dtt.service.extract;

import com.example.dtt.domain.entity.extract.ExtractConf;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author reid
 * @date 2022/3/27 16:32
 * @describe 取数 服务层
 */
public interface ExtractTemplateService {
    /**
     * 根据传入的执行SQL获取查询结果
     * @param executeSql
     * @return
     */
    List<LinkedHashMap<String,Object>> runHiveResult(String executeSql);

    /**
     * 取数模板查询结果
     * @param executeSql
     * @return
     */
    List<LinkedHashMap<String, Object>> templateResult(String executeSql);
}

package com.example.dtt.mapper.extract;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据源 映射器接口
 */
public interface ExtractTemplateMapper {

    /**
     * 根据传入的执行SQL获取查询结果
     * @param executeSql
     * @return
     */
    List<LinkedHashMap<String,Object>> runHiveResult(String executeSql);

}

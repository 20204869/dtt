package com.example.dtt.utils;

import java.util.*;

/**
 * @author reid
 * @date 2022/3/3 10:40
 * @describe
 */
public class ListUtils {

    // 删除ArrayList中重复元素，保持顺序
    public static List removeDuplicateWithOrder(List list) {
        Set set = new HashSet();
        List newList = new ArrayList();
        for (Iterator iter = list.iterator(); iter.hasNext();) {
            Object element = iter.next();
            if (set.add(element))
                newList.add(element);
        }
        list.clear();
        list.addAll(newList);
        return list;
    }
}

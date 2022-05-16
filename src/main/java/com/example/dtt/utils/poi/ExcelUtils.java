package com.example.dtt.utils.poi;

import com.alibaba.excel.EasyExcel;
import com.example.dtt.utils.StringUtils;
import com.google.common.collect.ImmutableList;
import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author reid
 * @date 2022/4/10 21:55
 * @describe
 */
public class ExcelUtils {
    private static final Logger LOG = LoggerFactory.getLogger(ExcelUtils.class);
    static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");

    /**
     * @param response
     * @throws Exception
     */
    public static void exportExcel2007(HttpServletResponse response, List<LinkedHashMap<String, Object>> list, String fileName) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        ServletOutputStream outputStream = null;
        try {
            XSSFSheet sheet = workbook.createSheet(fileName);
            if (CollectionUtils.isEmpty(list)) {
                return;
            }
            // 设置表头
            XSSFRow headRow = sheet.createRow(0);
            Map<String, Object> objectMap = list.get(0);
            int i = 0;
            for (String title : objectMap.keySet()) {
                headRow.createCell(i++).setCellValue(title);
            }
            for (Map<String, Object> currRowDataMap : list) {
                XSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1); // 获取当前行
                i = 0;
                for (String title : currRowDataMap.keySet()) {
                    Object obj = currRowDataMap.get(title);
                    dataRow.createCell(i++).setCellValue(StringUtils.isNull(obj) ? null : obj.toString());
                }
            }
            // 设置头信息
            String downloadFileName = fileName + dateTimeFormatter.format(LocalDateTime.now()) + ".xlsx";
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment;downloadFileName=" + downloadFileName);
            outputStream = response.getOutputStream();
            workbook.write(outputStream);
            outputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }
        }
    }

    /**
     * List<map>导出Excel
     * @param response
     * @param list
     * @param fileName
     */
    public static void exportExcel(HttpServletResponse response, List<LinkedHashMap<String, Object>> list, String fileName) {
        try {
            // 表头
            String[] title = sqlTitle(list);
            // 字段
            List<String> strField = strField(list);
            // 一个List<Object> 代表一行 （核心：Map<String, Object> 转成 List<String>）
            List<List<Object>> data = list.stream().map(o1 -> trans(o1, strField)).collect(Collectors.toList());

            String filename = fileName + ".xlsx";
            try (OutputStream outputStream = response.getOutputStream()) {
                // 设置名称
                response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "utf-8"));
                // 设置成流，只下载，不会直接打开
                response.setContentType("application/octet-stream");

                List<List<String>> collect = Arrays.stream(title).map(ImmutableList::of).collect(Collectors.toList());
                EasyExcel.write(outputStream).head(collect).sheet(fileName).doWrite(data);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("导出Excel数据问题，请查询异常！");
        }
    }

    // Map<String, Object> 按顺序转成 List<String>
    private static List<Object> trans(Map<String, Object> map, List<String> head) {
        return head.stream().map(map::get).collect(Collectors.toList());
    }
    //表头
    private static String[] sqlTitle(List<LinkedHashMap<String, Object>> list){
        LinkedHashMap<String,Object> first = list.get(0);
        String[] title = new String[first.size()];
        Iterator<Map.Entry<String,Object>> iterator = first.entrySet().iterator();
        /*while (iterator.hasNext()){
            Map.Entry<String,Object> entry = iterator.next();

        }*/
        for (int index = 0; iterator.hasNext();index ++){
            Map.Entry<String,Object> entry = iterator.next();
            title[index] = entry.getKey();
        }
        return title;
    }
    private static List<String> strField(List<LinkedHashMap<String, Object>> list){
        LinkedHashMap<String,Object> first = list.get(0);
        List<String> strField = new ArrayList<>();
        Iterator<Map.Entry<String,Object>> iterator = first.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<String,Object> entry = iterator.next();
            strField.add(entry.getKey());
        }
        return strField;
    }
}

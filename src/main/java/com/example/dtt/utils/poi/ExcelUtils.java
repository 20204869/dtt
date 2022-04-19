package com.example.dtt.utils.poi;

import com.example.dtt.utils.StringUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
}

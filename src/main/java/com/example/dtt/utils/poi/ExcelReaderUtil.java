package com.example.dtt.utils.poi;

import com.example.dtt.utils.files.CreateFileUtil;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @desc excel 数据处理工具类
 **/
public class ExcelReaderUtil {
    //excel2003扩展名
    public static final String EXCEL03_EXTENSION = ".xls";
    //excel2007扩展名
    public static final String EXCEL07_EXTENSION = ".xlsx";

    /**
     * 每获取一条记录，即打印
     * 在flume里每获取一条记录即发送，而不必缓存起来，可以大大减少内存的消耗，这里主要是针对flume读取大数据量excel来说的
     * @param sheetName
     * @param sheetIndex
     * @param curRow
     * @param cellList
     */
    public static void sendRows(String filePath, String sheetName, int sheetIndex, int curRow, List<String> cellList,String resultFilePath,String resultFileName){
         List<String[]> resultList = new ArrayList<>();
        StringBuffer oneLineSb = new StringBuffer();
			/*oneLineSb.append(filePath);
			oneLineSb.append("--");
			oneLineSb.append("sheet" + sheetIndex);
			oneLineSb.append("::" + sheetName);//加上sheet名
			oneLineSb.append("--");
			oneLineSb.append("row" + curRow);
			oneLineSb.append("::");*/
        String[] cells = new String[cellList.size()];
        //for (String cell : cellList) {
        for (int index = 0; index < cellList.size(); index++) {
            //oneLineSb.append(cell.trim());
            oneLineSb.append(cellList.get(index));
            oneLineSb.append(",");
            cells[index] = cellList.get(index);
        }
        String oneLine = oneLineSb.toString();
        if(oneLine.endsWith(","))
        {
            oneLine = oneLine.substring(0, oneLine.lastIndexOf(","));
        }// 去除最后一个分隔符
        // System.out.println(oneLine);
        resultList.add(cells);
        CreateFileUtil.createCsvFile(resultList,resultFilePath,resultFileName);
}

    public static void readExcel(MultipartFile file/*String fileName*/, String resultFilePath, String resultFileName) throws Exception {
        int totalRows = 0;
        String fileName = file.getOriginalFilename();
        if (fileName.endsWith(EXCEL03_EXTENSION)) { //处理excel2003文件
            ExcelXlsReader excelXls = new  ExcelXlsReader(resultFilePath,resultFileName);
            totalRows = excelXls.process(file);
        } else if (fileName.endsWith(EXCEL07_EXTENSION)) {//处理excel2007文件
             ExcelXlsxReaderWithDefaultHandler excelXlsxReader = new  ExcelXlsxReaderWithDefaultHandler(resultFilePath,resultFileName);
            totalRows = excelXlsxReader.process(file);
        } else {
            throw new Exception("文件格式错误，fileName的扩展名只能是xls或xlsx。");
        }
        System.out.println("发送的总行数：" + totalRows);
    }

    public static void readExcelLocal(MultipartFile file/*String fileName*/, String resultFilePath, String resultFileName) throws Exception {
        int totalRows = 0;
        String fileName = file.getOriginalFilename();
        if (fileName.endsWith(EXCEL03_EXTENSION)) { //处理excel2003文件
            ExcelXlsReader excelXls = new ExcelXlsReader(resultFilePath,resultFileName);
            totalRows = excelXls.process(file);
        } else if (fileName.endsWith(EXCEL07_EXTENSION)) {//处理excel2007文件
            ExcelXlsxReaderWithDefaultHandler excelXlsxReader = new ExcelXlsxReaderWithDefaultHandler(resultFilePath,resultFileName);
            totalRows = excelXlsxReader.process(file);
        } else {
            throw new Exception("文件格式错误，fileName的扩展名只能是xls或xlsx。");
        }
        System.out.println("发送的总行数：" + totalRows);
    }

    public static void copyToTemp(File file, String tmpDir) throws Exception {
        FileInputStream fis = new FileInputStream(file);
        File file1 = new File(tmpDir);
        if (file1.exists()) {
            file1.delete();
        }
        FileOutputStream fos = new FileOutputStream(tmpDir);
        byte[] b = new byte[1024];
        int n = 0;
        while ((n = fis.read(b)) != -1) {
            fos.write(b, 0, n);
        }
        fis.close();
        fos.close();
    }

   /* public static void main(String[] args) throws Exception {
        //String path="D:\\Github\\test.xls";
        //String path="D:\\H3CIDEA\\POIExcel\\test.xlsx";
        String path = "D:\\demo\\user.xlsx";
        path = new String(path.getBytes("GBK"), "UTF-8");
        *//*ExcelReaderUtil.readExcel(file2.getAbsolutePath(),"/home/test/tmp.xlsx");*//*
        String resultFilePath = "D:\\demo";
        String resultFileName = "user";
       // ExcelReaderUtil.readExcel(path,resultFilePath,resultFileName);
        *//*readXlsx(file2.getAbsolutePath());*//*
    }*/
}

package com.yidu;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;

public class ExcelUtil {

    public void writer(String path) throws IOException {
        //创建Excel-2010处理对象
        Workbook xlsx=new XSSFWorkbook();
        //创建sheet（或者表格中的一页）
        Sheet t285=xlsx.createSheet("t285");
        //创建行 row
        Row headrow = t285.createRow(0);
        //创建列col,设置列的值
        headrow.createCell(0).setCellValue("序号");
        headrow.createCell(1).setCellValue("名字");
        headrow.createCell(2).setCellValue("性别");
        headrow.createCell(3).setCellValue("年龄");

        for (int i = 1; i <10 ; i++) {
            Row row = t285.createRow(i);
            row.createCell(0).setCellValue(i);
            row.createCell(1).setCellValue("成龙"+i);
            row.createCell(2).setCellValue("男");
            row.createCell(3).setCellValue(5*i);
        }

        //创建Excel文件
        File file=new File(path);
        //创建写入流
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        //将Excel对象写入指定文件
        xlsx.write(fileOutputStream);
        //关闭资源
        fileOutputStream.close();
        System.out.println("成功"+file.getAbsolutePath());

    }

    public void read() throws IOException {
        String path="holle.xlsx";
        File file=new File(path);
        //创建读取流
        FileInputStream fis=new FileInputStream(file);
        //创建Excel处理对象
        Workbook workbook=null;
        if(path.endsWith("xls")){
            workbook=new HSSFWorkbook(fis);
        }else {
            workbook=new XSSFWorkbook(fis);
        }
        Sheet t285=workbook.getSheet("t285");

        //取出表头，根据下标得到第一行
        Row row=t285.getRow(0);
        //取出表格内容
        String numt=row.getCell(0).getStringCellValue();
        String namet=row.getCell(1).getStringCellValue();
        String sext=row.getCell(2).getStringCellValue();
        String aget=row.getCell(3).getStringCellValue();
        System.out.println("表头---"+numt+"\t"+namet+"\t"+sext+"\t"+aget);
        //得到当前表格的总行数
        int rows=t285.getLastRowNum();
        for (int i = 1; i < rows; i++) {
            Row cells=t285.getRow(i);
            double num=cells.getCell(0).getNumericCellValue();
            String name=cells.getCell(1).getStringCellValue();
            String sex=cells.getCell(2).getStringCellValue();
            double age=cells.getCell(3).getNumericCellValue();
            System.out.println("数据---"+num+"\t"+name+"\t"+sex+"\t"+age);
        }
        fis.close();
    }


    public static void main(String[] args) {
        ExcelUtil excelUtil=new ExcelUtil();
        /*try {
            excelUtil.writer("holle.xlsx");
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        try {
            excelUtil.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

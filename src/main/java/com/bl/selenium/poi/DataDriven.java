package com.bl.selenium.poi;

import com.bl.selenium.base.SeleniumBasicBase;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;

public class DataDriven extends SeleniumBasicBase {

    XSSFWorkbook workbook;
    XSSFSheet sheet;

    public void initializeSheet(String excelPath) {
        try {
            File file = new File(excelPath);//set file path
            FileInputStream fis = new FileInputStream(file);//read file path
            workbook = new XSSFWorkbook(fis);//set as XSSFWorkbook
        } catch(Exception e) {
            System.out.println(e);
        }
    }

    //get Data from sheet
    public String getData(int sheetNumber, int row, int column) {
        sheet = workbook.getSheetAt(sheetNumber);
        String data = sheet.getRow(row).getCell(column).getStringCellValue();
        return data;
    }

    //get total rows in sheet
    public int getTotalRows(int sheetIndex) {
        int row = workbook.getSheetAt(sheetIndex).getLastRowNum();
        return row;
    }
}

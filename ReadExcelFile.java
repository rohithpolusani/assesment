package Assesment;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;

public class ReadExcelFile {
    static XSSFWorkbook wb;
    static XSSFSheet sheet;

    public ReadExcelFile(String excelPath){
        try {
            File src=new File(excelPath);
            FileInputStream fis=new FileInputStream(src);
            wb=new XSSFWorkbook(fis);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public String getData(int sheetNumber, int row, int column){
        sheet=wb.getSheetAt(sheetNumber);
        DataFormatter formatter = new DataFormatter();
        String data1 = formatter.formatCellValue(sheet.getRow(row).getCell(column));

        return data1;
    }
    public int getRowCount(int sheetIndex){
        int row=wb.getSheetAt(sheetIndex).getLastRowNum();
        row=row -1;
        System.out.println(row);
        return row;

    }
}
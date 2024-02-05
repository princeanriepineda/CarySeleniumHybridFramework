package Utilities;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileInputStream;
import java.io.IOException;

public class ExcelUtils {
    public static Object[][] readExcel(String filePath, String sheetName) throws IOException {
        FileInputStream inputStream = new FileInputStream("C://Users//Prince Pineda//IdeaProjects//RequestorFramework//src//main//java//Resources//testdata.xlsx");
        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet sheet = workbook.getSheet(sheetName);

        int rowCount = sheet.getLastRowNum();
        int columnCount = sheet.getRow(0).getLastCellNum();

        Object[][] data = new Object[rowCount][columnCount];

        for (int i = 0; i < rowCount; i++) {
            Row row = sheet.getRow(i + 1);
            for (int j = 0; j < columnCount; j++) {
                Cell cell = row.getCell(j);
                data[i][j] = cell.toString();
            }
        }

        workbook.close();
        inputStream.close();
        return data;
    }
    public static Object[][] readExcel1(String filePath, String sheetName) throws IOException {
        FileInputStream inputStream = new FileInputStream("C://Users//Prince Pineda//IdeaProjects//RequestorFramework//src//main//java//Resources//testdataRM.xlsx");
        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet sheet = workbook.getSheet(sheetName);

        int rowCount = sheet.getLastRowNum();
        int columnCount = sheet.getRow(0).getLastCellNum();

        Object[][] data = new Object[rowCount][columnCount];

        for (int i = 0; i < rowCount; i++) {
            Row row = sheet.getRow(i + 1);
            for (int j = 0; j < columnCount; j++) {
                Cell cell = row.getCell(j);
                data[i][j] = cell.toString();
            }
        }

        workbook.close();
        inputStream.close();
        return data;
    }
}

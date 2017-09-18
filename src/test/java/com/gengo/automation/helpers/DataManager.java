package com.gengo.automation.helpers;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @class A class that will serve as the caller of data from the DataSource.xls
 */
public class DataManager {

    private FileInputStream fileInputStream;
    private FileOutputStream fileOutputStream;
    private POIFSFileSystem fs;
    private HSSFWorkbook workbook;
    private HSSFSheet sheet;
    private HSSFRow row;
    private HSSFCell cell;
    private final String WORKBOOK_NAME = System.getProperty("user.dir") + "/src/test/resources/testData/DataSource.xls";

    public DataManager() throws IOException {
        fileInputStream = new FileInputStream(WORKBOOK_NAME);
        fs = new POIFSFileSystem(fileInputStream);
        workbook = new HSSFWorkbook(fs);
    }

    public String getCellValue(String sheetName, int rowIndex, int columnIndex) {
        sheet = workbook.getSheet(sheetName);
        row = sheet.getRow(rowIndex);
        cell = row.getCell(columnIndex);

        return this.cell.getStringCellValue();
    }

    public String getLastRecordedUser(String sheetName, int columnIndex) {
        String lastRecordedValue;
        sheet = workbook.getSheet(sheetName);
        row = sheet.getRow(this.getLastRowIndex(sheetName));
        cell = row.getCell(columnIndex);
        lastRecordedValue = cell.getStringCellValue();

        return lastRecordedValue;
    }

    public int getLastRowIndex(String sheetName) {
        sheet = workbook.getSheet(sheetName);

        try {
            return sheet.getLastRowNum();
        } catch (NullPointerException e) { // Catch NullPointerException. Return 1 (index) as it will assume that the first row index has value.
            return 1;
        }
    }

    /**
     * @param sheetName
     *  Gets the sheet name argument to be processed.
     * @param columnIndex
     *  Gets the column index argument to be processed
     * @return
     *  Newly generate user.
     */
    public String generateNewUser(String sheetName, int columnIndex) {
        String newUser, lastUser, processedUser;
        int foundInteger;

        // Extract the last user account in the row.
        lastUser = this.getLastRecordedUser(sheetName, columnIndex);

        // Get the numeric value in the string extracted.
        processedUser = lastUser.replaceAll("[^0-9]", "");

        // Convert the String type value into an Integer and add 1 for new generated account.
        foundInteger = Integer.parseInt(processedUser) + 1;

        // Extract the email body from the 'lastUser' string.
        newUser = lastUser.replaceAll("[^a-zA-Z+.@]", "");

        // Input the 'foundInteger' with incremented value to create new user.
        // Ex : lastUser = "gengo.automationtest+c3@gmail.com"
        // 'newUser' will be "gengo.automationtest+c4@gmail.com"
        newUser = newUser.replaceAll("\\s", Integer.toString(foundInteger));
        newUser = new StringBuilder(newUser).insert(newUser.length()-10, foundInteger).toString();
        return newUser.trim();
    }

    /**
     * @param newValue
     *  Result from the generateNewUser() method.
     * @param sheetName
     *  Sheet name argument the user wants to write the new value.
     * @param columnIndex
     *  Column index argument from the sheet.
     * @throws IOException
     */
    public void writeValue(String newValue, String sheetName, int columnIndex) throws IOException {
        sheet = workbook.getSheet(sheetName);
        row = sheet.getRow(this.getLastRowIndex(sheetName));
        if (row != null) row = sheet.createRow(this.getLastRowIndex(sheetName) + 1);
        Cell newCell = row.getCell(columnIndex);
        if (newCell == null) newCell = row.createCell(columnIndex, Cell.CELL_TYPE_STRING);
        newCell.setCellValue(newValue);
        fileOutputStream = new FileOutputStream(WORKBOOK_NAME);
        workbook.write(fileOutputStream);
        fileOutputStream.close();
    }

    public void writeValue(String[] newValues, String sheetName) throws IOException {
        sheet = workbook.getSheet(sheetName);
        row = sheet.getRow(this.getLastRowIndex(sheetName));
        if (row != null) row = sheet.createRow(this.getLastRowIndex(sheetName) + 1);

        for (int ctr = 0; ctr < newValues.length; ctr++) {
            Cell newCell = row.getCell(ctr);
            if (newCell == null) newCell = row.createCell(ctr, Cell.CELL_TYPE_STRING);
            newCell.setCellValue(newValues[ctr]);
        }
        fileOutputStream = new FileOutputStream(WORKBOOK_NAME);
        workbook.write(fileOutputStream);
        fileOutputStream.close();
    }

    public void replaceValue(String newValue, String sheetName, int rowIndex, int columnIndex) throws IOException {
        sheet = workbook.getSheet(sheetName);
        row = sheet.getRow(rowIndex);
        Cell newCell = row.getCell(columnIndex);
        newCell.setCellValue(newValue);
        fileOutputStream = new FileOutputStream(WORKBOOK_NAME);
        workbook.write(fileOutputStream);
        fileOutputStream.close();
    }
}

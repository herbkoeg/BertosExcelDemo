/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hk.exceldemo;

import de.hk.exceldemo.business.AuftragHeader;
import de.hk.exceldemo.exception.FileFormatException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author palmherby
 */
public class ExcelAdapter {

    /**
     * Returns the relevant Rows of the Excel-File, e.g. all rows needed to
     * create an Aufrag
     *
     * @param sheet
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void getRelevantRowsOld(XSSFSheet sheet) throws FileNotFoundException, IOException {

        //Iterate through each rows one by one
        Iterator<Row> rowIterator = sheet.iterator();
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            //For each row, iterate through all the columns
            Iterator<Cell> cellIterator = row.cellIterator();

            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                //Check the cell type and format accordingly
                switch (cell.getCellType()) {
                    case Cell.CELL_TYPE_NUMERIC:
                        System.out.print(cell.getNumericCellValue() + "t");
                        break;
                    case Cell.CELL_TYPE_STRING:
                        System.out.print(cell.getStringCellValue() + "t");
                        break;
                }
            }
            System.out.println("");
        }

    }

    public List<Row> getRelevantRows(XSSFSheet sheet) {

        List<Row> relevantRows = new ArrayList<Row>();
        int counter = 0;
        //Iterate through each rows one by one
        Iterator<Row> rowIterator = sheet.iterator();
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            counter++;
            if (counter > 2) // ignore first Lines
            {
                relevantRows.add(row);
            }
        }
        return relevantRows;
    }

    public AuftragHeader getHeader(XSSFSheet sheet) throws FileFormatException {
        int counter = 0;
        
        Iterator<Row> rowIterator = sheet.iterator();
        if (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            AuftragHeader auftragHeader = new AuftragHeader(row.getCell(0).getStringCellValue());
            return auftragHeader;
        }
        else
        {
            throw new FileFormatException("Ungueltiger Header");
        }
    }

    public XSSFSheet loadXSSFSheet(FileInputStream file, int sheetNr) throws IOException, InvalidFormatException {

        XSSFWorkbook workbook = new XSSFWorkbook(file);
        XSSFSheet sheet = workbook.getSheetAt(sheetNr);
        return sheet;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sax.parser;

import genericECHO.ConceptPair;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import negex.paragonTest;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author aka324
 */
public class PTExlsParser {
     
    mainParagon mP = new mainParagon();

    Map<Integer, Patient> patMap = mP.getMap();
    public static void main(String[] argsv) throws IOException{
//        PTEntriesXLS();
    }
    public  Map<Integer, Patient> PTEntriesXLS() throws IOException {
        String fileName = "Dataset/TE1.xls";
        HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(fileName));
        HSSFSheet sheet = wb.getSheetAt(0);
        int rows = sheet.getPhysicalNumberOfRows();

        for (int r = 0; r < rows; r++) {
            try {
                HSSFRow row = sheet.getRow(r);
                if (row == null || r == 0) {
                    continue;
                }
                // if(r!=95) continue;

                int cells = row.getPhysicalNumberOfCells();
                if (cells < 2) {
                    continue;
                }
                

//			report = report.toLowerCase();
                paragonTest pT = new paragonTest();
                try {
                    HSSFCell cell = row.getCell(0);
                    int currPat = (int) cell.getNumericCellValue();
                    cell = row.getCell(11);
                    String dxName = cell.getStringCellValue(); 

//            System.out.println("Diagnosis Name: "
//                    + dxName +" pi "+ pat_ID);
                    if (patMap.containsKey(currPat)) {
                        Patient px = patMap.get(currPat);
                        px.lvef = pT.getLVEF(dxName, px.lvef);

                    }

                } catch (NullPointerException e) {
                System.out.println("NPE");
            }

            } catch (NullPointerException e) {
                System.out.println("NPE");
            }
            // System.out.println("\n\n\n\n");
        }
        
        //TE2 -- Fix it when possible
        
        fileName = "Dataset/TE2.xls";
        wb = new HSSFWorkbook(new FileInputStream(fileName));
        sheet = wb.getSheetAt(0);
        rows = sheet.getPhysicalNumberOfRows();

        for (int r = 0; r < rows; r++) {
            try {
                HSSFRow row = sheet.getRow(r);
                if (row == null || r == 0) {
                    continue;
                }
                // if(r!=95) continue;

                int cells = row.getPhysicalNumberOfCells();
                if (cells < 2) {
                    continue;
                }

//			report = report.toLowerCase();
                paragonTest pT = new paragonTest();
                try {
                    HSSFCell cell = row.getCell(0);
                    int currPat = (int) cell.getNumericCellValue();
                    cell = row.getCell(11);
                    String dxName = cell.getStringCellValue();

//            System.out.println("Diagnosis Name: "
//                    + dxName +" pi "+ pat_ID);
                    if (patMap.containsKey(currPat)) {
                        Patient px = patMap.get(currPat);
                        px.lvef = pT.getLVEF(dxName, px.lvef);

                    }

                } catch (NullPointerException e) {
                    System.out.println("NPE");
                }

            } catch (NullPointerException e) {
                System.out.println("NPE");
            }
            // System.out.println("\n\n\n\n");
        }
        return patMap;
    }
    
    public Map<Integer, Patient> getMap() {
        return patMap;
    }
    
}

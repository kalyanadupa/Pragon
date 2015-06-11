/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sax.aprser;

import genericECHO.ConceptPair;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import negex.paragonTest;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 *
 * @author aka324
 */
public class pxlsParser {
     
    
    public List<logPat> PLogXLS() throws IOException{
        String fileName = "temp1.xls";
        HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(fileName));
        HSSFSheet sheet = wb.getSheetAt(0);
        int rows = sheet.getPhysicalNumberOfRows();
        
        List<logPat> lp = new ArrayList<logPat>();
        
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
                HSSFCell cell = row.getCell(0);
                String report = cell.getStringCellValue();
                logPat px = new logPat();
                px.answer = report;
                cell = row.getCell(1);
                px.reason = cell.getStringCellValue();
                
                px.mrn = (int) row.getCell(2).getNumericCellValue();
                lp.add(px);
               

            } catch (NullPointerException e) {
                System.out.println("NPE");
            }
            // System.out.println("\n\n\n\n");
        }
        return lp;
    }
    
//    public List<logPat> getlogPat() {
//        return lp;
//    }
    
    public static void PEncountersXLS() throws IOException {
        String fileName = "Dataset/Paragon Encounters.xls";
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
                HSSFCell cell = row.getCell(16);
                String report = cell.getStringCellValue();

//			report = report.toLowerCase();
                paragonTest pT = new paragonTest();
                if (pT.hasHeartFailure(report)) {
                    System.out.println(report + " --> Yay");
                } else {
                    System.out.println(report + " --> Nay");
                }

            } catch (NullPointerException e) {
                System.out.println("NPE");
            }
            // System.out.println("\n\n\n\n");
        }
    }
    
}

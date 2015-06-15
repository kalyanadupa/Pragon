/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negex;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import opennlp.tools.chunker.ChunkerME;
import opennlp.tools.chunker.ChunkerModel;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.Span;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;


/**
 *
 * @author aka324
 */
public class paragonTest {
    public boolean hasHeartFailure(String doc1) {
        String doc = doc1.replaceAll("\\p{Punct}|\\d", " ").toLowerCase();
        doc = " "+doc+" ";
        
        Pattern pHF = Pattern.compile("\\s+HF\\s+");
        Matcher mHF = pHF.matcher(doc);
        if (mHF.find()) {
            return true;
        }
        Pattern pHFC = Pattern.compile("^HF\\s+");
        Matcher mHFC = pHFC.matcher(doc);
        if (mHFC.find()) {
            return true;
        }

        Pattern pHeFa = Pattern.compile("\\s+heart\\s+failure\\s+");
        Matcher mHeFa = pHeFa.matcher(doc.toLowerCase());
        if (mHeFa.find()) {
            return true;
        }
        Pattern pHeFaC = Pattern.compile("^heart\\s+failure\\s+");
        Matcher mHeFaC = pHeFaC.matcher(doc.toLowerCase());
        if (mHeFaC.find()) {
            return true;
        }
        

        Pattern pDiDy = Pattern.compile("\\s+diastolic\\s+dysfunction\\s+");
        Matcher mDiDy = pDiDy.matcher(doc.toLowerCase());
        if (mDiDy.find()) {
            return true;
        }
        
        
        Pattern pDiDyC = Pattern.compile("^diastolic\\s+dysfunction\\s+");
        Matcher mDiDyC = pDiDyC.matcher(doc.toLowerCase());
        if (mDiDyC.find()) {
            return true;
        }

        Pattern pCa = Pattern.compile("\\s+cardiomyopathy\\s+");
        Pattern pCaC = Pattern.compile("^cardiomyopathy\\s*");
        Matcher mCa = pCa.matcher(doc.toLowerCase());
        Matcher mCaC = pCaC.matcher(doc.toLowerCase());
        if ((mCa.find()) || (mCaC.find())) {
            return true;
        }

        return false;
    }
    
    public boolean searchwithoutNegation(String doc,String query){
        doc = " "+doc+" ";
        
       String[] words =  query.toLowerCase().split(" ");
       if(words.length == 1){
           Pattern p1 = Pattern.compile("\\s+"+words[0] +"\\s+");
           Matcher m1 = p1.matcher(doc.toLowerCase());
           if (m1.find()) {
               return true;
           }
           return false;
       }
       
        if (words.length == 2) {
            Pattern p1 = Pattern.compile("\\s+"+words[0]+"\\s+"+words[1]+"\\s+");
            Matcher m1 = p1.matcher(doc.toLowerCase());
            if (m1.find()) {
                return true;
            }
            return false;
        }
        
        if (words.length == 3) {
            Pattern p1 = Pattern.compile("\\s+" + words[0] + "\\s+" + words[1] + "\\s+"+ words[2] + "\\s+");
            Matcher m1 = p1.matcher(doc.toLowerCase());
            if (m1.find()) {
                return true;
            }
            return false;
        }
        
        if (words.length == 4) {
            Pattern p1 = Pattern.compile("\\s+" + words[0] + "\\s+" + words[1] + "\\s+"+ words[2] + "\\s+" + "\\s+"+ words[3] + "\\s+");
            Matcher m1 = p1.matcher(doc.toLowerCase());
            if (m1.find()) {
                return true;
            }
            return false;
        }
        return false;       
    }
    
    public boolean searchWithNegation(String doc,String query) throws FileNotFoundException, Exception{
        doc = " "+ doc.toLowerCase()+ " ";
        query = query.toLowerCase();
        if(!searchwithoutNegation(doc,query)){
            return false;
        }
        GenNegEx g  = new GenNegEx();
        //
        File ruleFile = new File("negex_triggers.txt");
        Scanner sc = new Scanner(ruleFile);
        
        boolean negatePossible = true;
        ArrayList rules = new ArrayList();
        String afterNegCheck = "";

        while (sc.hasNextLine()) {
            rules.add(sc.nextLine());
        }

        afterNegCheck = g.negCheck(doc, query, rules, negatePossible);
        
        if (afterNegCheck.contains("[NEGATED]")) {
            return false;
        } else if (afterNegCheck.contains("[POSSIBLE]")) {
            return false;
        } else{ 
            return true;
        }
    }
    
    //LVEF Stuff
    
    
    public List<Float> getLVEF(String check1,List<Float> currLVEF){
        check1 = check1.replaceAll("\n", "");
        Pattern p36 = Pattern.compile("(lvef|(ef)|(lv\\s*ejection\\s*fraction)|(left\\s*(ventricular|ventricle)\\s*ejection\\s*fraction)|(ejection\\s*fraction)).{1,85}((\\d+\\s*(\\-|to)\\s*\\d+)|(\\d*\\.\\d*\\s*(\\-|to)\\s*\\d*\\.\\d*)|(\\d*\\.\\d+)|(\\d+))\\s*(\\%)");
        Pattern p37 = Pattern.compile("((\\d+\\s*(\\-|to)\\s*\\d+)|(\\d*\\.\\d*\\s*(\\-|to)\\s*\\d*\\.\\d*)|(\\d*\\.\\d+)|(\\d+))(?=(\\s*(\\%)))");
        String check2 = " ";
        
        if (check1.matches("(.*)((lvef)|(ejection\\s*fraction)|(ef))(.*)")) {
            Matcher m36 = p36.matcher(check1);
            while (m36.find()) {
                check2 = check2 + "^^ " + m36.group();
                //excelData[i][startIndexCol] = excelData[i][startIndexCol] + "\n" + "<OUTPUT>" + m36.group();
            }
            Matcher m37 = p37.matcher(check2);
            List<String> matchstring37 = new ArrayList<String>();
            while (m37.find()) {
                calculate(matchstring37, m37);
            }
            
            if (matchstring37 != null && !matchstring37.isEmpty()) {
                for(String str: matchstring37){                    
                    try {
                        currLVEF.add(Float.parseFloat(str));
                    } catch (NumberFormatException numberFormatException) {
                        System.out.println("** BIG ERROR ** " + str);
                    }
                }
//                excelData[i][startIndexCol + 1] = matchstring37.toString() + "@" + Integer.toString(matchstring37.size());
//                excelData[i][startIndexCol + 2] = Collections.max(matchstring37) + "," + Collections.min(matchstring37);
//                excelData[i][startIndexCol + 3] = matchstring37.get(matchstring37.size() - 1);
            }
            
        }
        return currLVEF;
        
    }
    
    public static String getLVF(String check1) {
        check1 = check1.replaceAll("\n", "");
        Pattern p36 = Pattern.compile("(lvef|(ef)|(lv\\s*ejection\\s*fraction)|(left\\s*(ventricular|ventricle)\\s*ejection\\s*fraction)|(ejection\\s*fraction)).{1,85}((\\d+\\s*(\\-|to)\\s*\\d+)|(\\d*\\.\\d*\\s*(\\-|to)\\s*\\d*\\.\\d*)|(\\d*\\.\\d+)|(\\d+))\\s*(\\%)");
        Pattern p37 = Pattern.compile("((\\d+\\s*(\\-|to)\\s*\\d+)|(\\d*\\.\\d*\\s*(\\-|to)\\s*\\d*\\.\\d*)|(\\d*\\.\\d+)|(\\d+))(?=(\\s*(\\%)))");
        String check2 = " ";
        List<Integer> currLVEF = new ArrayList<Integer>();
        if (check1.matches("(.*)((lvef)|(ejection\\s*fraction)|(ef))(.*)")) {
            Matcher m36 = p36.matcher(check1);
            while (m36.find()) {
                check2 = check2 + "^^ " + m36.group();
                //excelData[i][startIndexCol] = excelData[i][startIndexCol] + "\n" + "<OUTPUT>" + m36.group();
            }
            Matcher m37 = p37.matcher(check2);
            List<String> matchstring37 = new ArrayList<String>();
            while (m37.find()) {
                calculate(matchstring37, m37);
            }

            if (matchstring37 != null && !matchstring37.isEmpty()) {
                for (String str : matchstring37) {
                    try {
                        currLVEF.add(Integer.parseInt(str));
                    } catch (NumberFormatException numberFormatException) {
                        System.out.println("** BIG ERROR ** " + str);
                    }
                }
//                excelData[i][startIndexCol + 1] = matchstring37.toString() + "@" + Integer.toString(matchstring37.size());
//                excelData[i][startIndexCol + 2] = Collections.max(matchstring37) + "," + Collections.min(matchstring37);
//                excelData[i][startIndexCol + 3] = matchstring37.get(matchstring37.size() - 1);
            }

        }
        return currLVEF.toString();

    }
    private static boolean calculate(List<String> matchstring, Matcher match) {
        if ((match.group().contains("-") || match.group().contains("to"))
                && !match.group().contains("[") && !match.group().contains("_")) {
            String[] items = match.group().split("to|-");
            Float f1 = new Float(items[0]);
            Float f2 = new Float(items[1]);
            String ca = Float.toString((f1 + f2) / 2);
            return matchstring.add(ca);
        } else {
            return matchstring.add(match.group());
        }
    }
    
            
    public static void main(String[] args) throws Exception{
        String doc = "                                   Actual    Normal      Indexed        Normal\n"
                + "                                   Pt Value  Value       Pt Value       Indexed Value\n"
                + " LV Size-end systole              3.3   cm   2.3-3.4 cm\n"
                + " LV size-end diastole             4.8   cm   3.5-5.1 cm   2.7   cm/mÂ²   2.1-2.7 cm/mÂ²\n"
                + " LV ejection fraction             35    %    &gt;= 55%\n"
                + " LV post wall thick-diastole      1.0   cm   0.65-1.1 cm\n"
                + " IV septal wall thick-diastole    0.9   cm   0.5-1.0 cm\n"
                + " Aortic Root Dia.                 2.8   cm   2.0-3.7 cm   1.6   cm/mÂ²   &lt;2.0cm/mÂ²\n"
                + " Aortic Cusp Separation           2.2   cm   1.5-2.6 cm\n"
                + " LVOT Diameter                    2.0   cm\n"
                + " LA Diameter                      2.9   cm   1.9-4.0 cm   1.7   cm/mÂ²   &lt;= 2.1cm/mÂ²\n"
                + " LA Volume                        37.0  mL                21.1  mL/mÂ²   22 +- 6mL/mÂ²";
        
        
            
        
        System.out.println(getLVF(doc.toLowerCase()));
//        System.out.println("||"+doc+"||");
//        if (searchWithNegation(doc,"cancer")) {
//            System.out.println("Working");
//        }
//        else
//            System.out.println("Nay");
           
    }
           
    
}

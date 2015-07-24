package sax.parser;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import negex.paragonTest;
import opennlp.tools.sentdetect.SentenceDetectorME;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author aka324
 */
public class myXMLParser {
    
    
    
    public Map<Integer, Patient> parseTE(Map<Integer,Patient> patMap) throws FileNotFoundException, IOException{
        String fileName = "Dataset/Paragon Text Entries.xml";
        FileReader fileReader = new FileReader(fileName);
        paragonTest pT = new paragonTest();

        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line;
        Patient px  = new Patient();
        String notesText;
        while ((line = bufferedReader.readLine()) != null) {
            if(line.contains("<pat_id>")){
                line = bufferedReader.readLine();
                Integer currPat = Integer.valueOf(line);
                if (patMap.containsKey(currPat))
                    px = patMap.get(currPat);
                else{
                    px = new Patient(currPat);
                    patMap.put(currPat, px);
                }
            }
            if(line.contains("<echo_text>")){
//                System.out.println(px.Pat_ID);
                String nT = "";
                notesText = bufferedReader.readLine();
                while (!notesText.contains("</echo_text>")) {
                    if(notesText != null)
                        if(!notesText.matches("\\s+"))
                            nT = nT + notesText + " ";
                    notesText = bufferedReader.readLine();
                }
                px.lvef = pT.getLVEF(nT, px.lvef);
                if (px.amt == -1) {
                    if ((pT.searchwithoutNegation(nT, "severe aortic stenosis")) || (pT.searchwithoutNegation(nT, "severe mitral stenosis")) || (pT.searchwithoutNegation(notesText, "severe tricuspid stenosis")) || (pT.searchwithoutNegation(notesText, "severe aortic regurgitation")) || (pT.searchwithoutNegation(notesText, "severe mitral regurgitation")) || (pT.searchwithoutNegation(notesText, "severe tricuspid regurgitation"))) {
                        px.amt = 1;
                    } else {
                        px.amt = 0;
                    }
                }

                if ((pT.searchwithoutNegation(nT, "dilated cardiomyopathy")) || (pT.searchwithoutNegation(nT, "peripartum cardiomyopathy")) || (pT.searchwithoutNegation(nT, "chemotherapy induced cardiomyopathy")) || (pT.searchwithoutNegation(notesText, "viral myocarditis"))) {
                    px.cm = false;

                }
            }
        }


        
//        out = new PrintStream(new FileOutputStream("output.txt"));
//        System.setOut(out);
        return patMap;
    }
    
    
    public Map<Integer, Patient> parsePN(Map<Integer, Patient> patMap) throws FileNotFoundException, IOException, ParseException, Exception {
        
        String fileName = "Dataset/Paragon Notes.xml";
        FileReader fileReader = new FileReader(fileName);
        paragonTest pT = new paragonTest();
        // Always wrap FileReader in BufferedReader.
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line;
        Patient px = new Patient();
        String notesText;
        String vDate = null;
        while ((line = bufferedReader.readLine()) != null) {
            if (line.contains("<pat_id>")) {
                line = bufferedReader.readLine();
                Integer currPat = Integer.valueOf(line);
                if (patMap.containsKey(currPat)) {
                    px = patMap.get(currPat);
                } else {
                    px = new Patient(currPat);
                    patMap.put(currPat, px);
                }
                vDate = null;
            }
            if(line.contains("<contact_date>"))
                vDate = bufferedReader.readLine();
            if (line.contains("<note_text>")) {
//                System.out.println(px.Pat_ID);
                notesText = bufferedReader.readLine();
                while(!notesText.contains("</note_text>")){
                    
                    if(notesText != null)
                    if(!notesText.matches("\\s+")){
                        px.lvef = pT.getLVEF(notesText, px.lvef);
                        if (pT.searchwithoutNegation(notesText, "Angioedema") || pT.searchwithoutNegation(notesText, "Pancreatitis") || pT.searchwithoutNegation(notesText, "bilateral Renal artery stenosis")) {
                            px.apr = false;
                        }

                        if ((pT.searchwithoutNegation(notesText, "stroke")) || (pT.searchwithoutNegation(notesText, "transitent ischemic attack")) || (pT.searchwithoutNegation(notesText, "carotid surgery")) || (pT.searchwithoutNegation(notesText, "carotid angioplasty"))) {
                            testDate td = new testDate();
                            if (vDate != null) {
                                if (td.nMonth(vDate) <= 3) {
                                    px.stc = false;
                                }
                            }
                        }

                        if (pT.searchwithoutNegation(notesText, "basal cell")) {
                            px.bc = true;
                        }
                        if (pT.searchwithoutNegation(notesText, "cancer")) {
                            px.cancer2 = false;
                        }

                        if ((pT.searchwithoutNegation(notesText, "dilated cardiomyopathy")) || (pT.searchwithoutNegation(notesText, "peripartum cardiomyopathy")) || (pT.searchwithoutNegation(notesText, "chemotherapy induced cardiomyopathy")) || (pT.searchwithoutNegation(notesText, "viral myocarditis"))) {
                            px.cm = false;

                        }

                        try {
                            List<String> sentences = getScentences(notesText);
                            for (String eachS : sentences) {
                                try {
                                    if (pT.searchWithNegation(eachS, "transplant")) {
                                        px.T_ICD = false;
                                    }

                                    // Checking for Cancer Step 8
                                    if ((!pT.searchwithoutNegation(eachS, "screening")) && (pT.searchWithNegation(eachS, "malignant")) && (!pT.searchWithNegation(eachS, "prostate")) && (!pT.searchWithNegation(eachS, "basal cell"))) {
                                        testDate td = new testDate();
                                        if (td.nMonth(vDate) <= 60) { // The month limit is taken as 60 because, it should be within last 5 years(60 months)
                                            px.cancer = false;
                                        }
                                    }

                                } catch (Exception ex) {
                                    //System.out.println("Error: " + eachS);
                                }
                                
                            }

                        } catch (Exception ex) {
                            Logger.getLogger(PEncountersHandler.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    
                    notesText = bufferedReader.readLine();
                }

                
            }
        }


                
            
        
        return patMap;
    }
    
    public static List<String> getScentences(String str){
        List<String> allL = new ArrayList<String>();
        Pattern re = Pattern.compile("[^.!?\\s][^.!?]*(?:[.!?](?!['\"]?\\s|$)[^.!?]*)*[.!?]?['\"]?(?=\\s|$)", Pattern.MULTILINE | Pattern.COMMENTS);
        Matcher reMatcher = re.matcher(str);
        while (reMatcher.find()) {
            allL.add(reMatcher.group());
        }
        return allL;
    }

    
}

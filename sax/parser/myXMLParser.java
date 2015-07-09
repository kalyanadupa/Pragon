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
                else
                    px = new Patient();
            }
            if(line.contains("<echo_text>")){
                notesText = bufferedReader.readLine();
                px.lvef = pT.getLVEF(notesText, px.lvef);
                
                if (px.amt == -1) {
                    if ((pT.searchwithoutNegation(notesText, "severe aortic stenosis")) || (pT.searchwithoutNegation(notesText, "severe mitral stenosis")) || (pT.searchwithoutNegation(notesText, "severe tricuspid stenosis")) || (pT.searchwithoutNegation(notesText, "severe aortic regurgitation")) || (pT.searchwithoutNegation(notesText, "severe mitral regurgitation")) || (pT.searchwithoutNegation(notesText, "severe tricuspid regurgitation"))) {
                        px.amt = 1;
                    } else {
                        px.amt = 0;
                    }
                }
                
                if ((pT.searchwithoutNegation(notesText, "dilated cardiomyopathy")) || (pT.searchwithoutNegation(notesText, "peripartum cardiomyopathy")) || (pT.searchwithoutNegation(notesText, "chemotherapy induced cardiomyopathy")) || (pT.searchwithoutNegation(notesText, "viral myocarditis"))) {
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
                    px = new Patient();
                }
                notesText = "";
                vDate = null;
            }
            if(line.contains("<contact_date>"))
                vDate = bufferedReader.readLine();
            if (line.contains("<note_text>")) {
                notesText = bufferedReader.readLine();
                px.lvef = pT.getLVEF(notesText, px.lvef);
                
                if (pT.searchwithoutNegation(notesText, "Angioedema")  || pT.searchwithoutNegation(notesText, "Pancreatitis") || pT.searchwithoutNegation(notesText, "bilateral Renal artery stenosis")) {
                        px.apr = false;
                }

                if ((pT.searchwithoutNegation(notesText, "stroke")) || (pT.searchwithoutNegation(notesText, "transitent ischemic attack")) || (pT.searchwithoutNegation(notesText, "carotid surgery")) || (pT.searchwithoutNegation(notesText, "carotid angioplasty"))) {
                    testDate td = new testDate();
                    if(vDate != null){
                        if (td.nMonth(vDate) <= 3) {
                            px.stc = false;
                        }
                    }
                }
                
                if ((pT.searchwithoutNegation(notesText, "prostate")) || (pT.searchwithoutNegation(notesText, "basal cell"))) {
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

    private List<String> getTagValues(final String str) {
        List<String> tagValues = new ArrayList<String>();
//        Matcher matcher = TAG_REGEX.matcher(str);
//        while (matcher.find()) {
//            tagValues.add(matcher.group(1));
//            tagValues.add(matcher.group(2));
//        }
        
        Matcher matcher = Pattern.compile("<(pat_id)>(.+?)</pat_id>|<(echo_text)>(.+?)</echo_text>").matcher(str);
        while (matcher.find()) {
            
            if (matcher.group(1) != null) {
                tagValues.add(matcher.group(1));
                tagValues.add(matcher.group(2));
            }
            else if(matcher.group(3)!= null){
                tagValues.add(matcher.group(3));
                tagValues.add(matcher.group(4));
            }
        }
        
        
        
        return tagValues;
    }
    
    private static List<String> getTagValuesPN(final String str) {
        List<String> tagValues = new ArrayList<String>();
        Matcher matcher1 = Pattern.compile("<(Detail)>(.+?)</Detail>").matcher(str);
        while(matcher1.find()){
            Matcher matcher = Pattern.compile("<(pat_id)>(.+?)</pat_id>|<(contact_date)>(.+?)</contact_date>|<(note_text)>(.+?)</note_text>").matcher(matcher1.group(2));
            while (matcher.find()) {

                if (matcher.group(1) != null) {
                    tagValues.add(matcher.group(1));
                    tagValues.add(matcher.group(2));
                } else if (matcher.group(3) != null) {
                    tagValues.add(matcher.group(3));
                    tagValues.add(matcher.group(4));
                } else if (matcher.group(5) != null) {
                    tagValues.add(matcher.group(5));
                    tagValues.add(matcher.group(6));
                }
            }
        }
        return tagValues;
    }
    
}

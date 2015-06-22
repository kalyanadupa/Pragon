package sax.aprser;


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
        // Always wrap FileReader in BufferedReader.
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line;
        
        StringBuilder stB = new StringBuilder();
        while ((line = bufferedReader.readLine()) != null) {
            stB.append(line + " " );
        }


//        while ((str = bufferedReader.readLine()) != null) {
//            System.out.println(Arrays.toString(getTagValues(str).toArray()));
//        }
        String str = stB.toString();
        List<String> tagValues = getTagValues(str);
        for (int i = 0; i < tagValues.size(); i = i + 2) {
            if (tagValues.get(i).equalsIgnoreCase("echo_text")) {
                Integer id = Integer.valueOf(tagValues.get(i-1));
                List<Float> temp = new ArrayList<Float>();
                temp = pT.getLVEF(tagValues.get(i+1),temp);        
                if(!temp.isEmpty()){
                    for(float t : temp){
                        if(t < 45)
                            System.out.println("-----\n"+id+"\n--- "+t+" --\n");
                    }
                    if(patMap.containsKey(id)){
                        Patient px = patMap.get(id);
                        temp.addAll(px.lvef);
                        px.lvef = temp;
                    }
                }
                Integer currPat = Integer.valueOf(tagValues.get(i - 1));
                String notesText = tagValues.get(i + 1);
                if (patMap.containsKey(currPat)){
                    Patient px = patMap.get(currPat);
                    if(px.amt == -1){
                        if ((pT.searchwithoutNegation(notesText, "severe aortic stenosis")) || (pT.searchwithoutNegation(notesText, "severe mitral stenosis")) || (pT.searchwithoutNegation(notesText, "severe tricuspid stenosis")) || (pT.searchwithoutNegation(notesText, "severe aortic regurgitation")) || (pT.searchwithoutNegation(notesText, "severe mitral regurgitation")) || (pT.searchwithoutNegation(notesText, "severe tricuspid regurgitation"))) {
                            px.amt = 1;
                        }
                        else{
                            px.amt = 0;
                        }
                    } 
                }
                //dilated cardiomyopathy, including peripartum cardiomyopathy, chemotherapy induced cardiomyopathy, or viral myocarditis
                if ((pT.searchwithoutNegation(notesText, "dilated cardiomyopathy")) || (pT.searchwithoutNegation(notesText, "peripartum cardiomyopathy")) || (pT.searchwithoutNegation(notesText, "chemotherapy induced cardiomyopathy")) || (pT.searchwithoutNegation(notesText, "viral myocarditis"))) {
                    if(patMap.containsKey(currPat)){
                        Patient px = patMap.get(currPat);
                        px.cm = false;
                    }
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
        
        StringBuilder stB = new StringBuilder();
        while ((line = bufferedReader.readLine()) != null) {
            stB.append(line + " " );
        }


//        while ((str = bufferedReader.readLine()) != null) {
//            System.out.println(Arrays.toString(getTagValues(str).toArray()));
//        }
        String str = stB.toString();
        List<String> tagValues = getTagValuesPN(str);
        
        for (int i = 0; i < tagValues.size(); i = i + 2) {
            if (tagValues.get(i).equalsIgnoreCase("note_text")) {
                Integer currPat = Integer.valueOf(tagValues.get(i-3));
                String notesText = tagValues.get(i+1);
                String vDate = tagValues.get(i-1);
                if (pT.searchwithoutNegation(notesText, "Angioedema")) {
                    if (patMap.containsKey(currPat)) {
                        Patient px = patMap.get(currPat);
                        px.apr = false;
                    }
                } else if (pT.searchwithoutNegation(notesText, "Pancreatitis")) {
                    if (patMap.containsKey(currPat)) {
                        Patient px = patMap.get(currPat);
                        px.apr = false;
                    }
                } else if (pT.searchwithoutNegation(notesText, "bilateral Renal artery stenosis")) {
                    if (patMap.containsKey(currPat)) {
                        Patient px = patMap.get(currPat);
                        px.apr = false;
                    }
                }
                if ((pT.searchwithoutNegation(notesText, "stroke")) || (pT.searchwithoutNegation(notesText, "transitent ischemic attack")) || (pT.searchwithoutNegation(notesText, "carotid surgery")) || (pT.searchwithoutNegation(notesText, "carotid angioplasty")) ) {
                    testDate td = new testDate();
                    if (td.nMonth(vDate) <= 3){
                        if (patMap.containsKey(currPat)) {
                            Patient px = patMap.get(currPat);
                            px.stc = false;
                        }
                    }
                }
                if ((pT.searchwithoutNegation(notesText, "prostate")) || (pT.searchwithoutNegation(notesText, "basal cell"))) {
                    if (patMap.containsKey(currPat)) {
                        Patient px = patMap.get(currPat);
                        px.bc = true;
                    }
                }
                //dilated cardiomyopathy, including peripartum cardiomyopathy, chemotherapy induced cardiomyopathy, or viral myocarditis
                if ((pT.searchwithoutNegation(notesText, "dilated cardiomyopathy")) || (pT.searchwithoutNegation(notesText, "peripartum cardiomyopathy")) || (pT.searchwithoutNegation(notesText, "chemotherapy induced cardiomyopathy")) || (pT.searchwithoutNegation(notesText, "viral myocarditis")) || (pT.searchwithoutNegation(notesText, "dilated cm")) || (pT.searchwithoutNegation(notesText, "peripartum cm")) || (pT.searchwithoutNegation(notesText, "chemotherapy induced cm")) ) {
                    if (patMap.containsKey(currPat)) {
                        Patient px = patMap.get(currPat);
                        px.cm = false;
                    }
                }
 
                List<Float> temp = new ArrayList<Float>();
                temp = pT.getLVEF(tagValues.get(i + 1), temp);
                if (!temp.isEmpty()) {
                    for (float t : temp) {
                        if (t < 45) {
                            System.out.println("-----\n" + currPat + "\n--- " + t + " --\n" );
                        }
                    }
                    if (patMap.containsKey(currPat)) {
                        Patient px = patMap.get(currPat);
                        temp.addAll(px.lvef);
                        px.lvef = temp;
                    }
                }
                
                
                
                try{
                    List<String> sentences = getScentences(notesText);
                    for(String eachS : sentences){
                       try{
                            if (pT.searchWithNegation(eachS, "transplant")) {
                                if (patMap.containsKey(currPat)) {
                                    Patient px = patMap.get(currPat);
                                    px.T_ICD = false;
                                }
                            }    

                            // Checking for Cancer Step 8
                            if ((!pT.searchwithoutNegation(eachS, "screening"))&&(pT.searchWithNegation(eachS, "malignant")) && (!pT.searchWithNegation(eachS, "prostate")) && (!pT.searchWithNegation(eachS, "basal cell"))) {
                                testDate td = new testDate();
                                if (td.nMonth(vDate) <= 60) { // The month limit is taken as 60 because, it should be within last 5 years(60 months)
                                    if (patMap.containsKey(currPat)) {
                                        Patient px = patMap.get(currPat);
                                        px.cancer = false;
                                    }
                                }
                            }
                            
                        }
                        catch(Exception ex){
                            //System.out.println("Error: " + eachS);
                        }
                    }
                    
                }
                catch (Exception ex) {
                    Logger.getLogger(PEncountersHandler.class.getName() ).log(Level.SEVERE, null, ex);
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
        
        Matcher matcher = Pattern.compile("<(pat_id)>(.+?)</pat_id>|<(contact_date)>(.+?)</contact_date>|<(note_text)>(.+?)</note_text>").matcher(str);
        while (matcher.find()) {
            
            if (matcher.group(1) != null) {
                tagValues.add(matcher.group(1));
                tagValues.add(matcher.group(2));
            }
            else if(matcher.group(3)!= null){
                tagValues.add(matcher.group(3));
                tagValues.add(matcher.group(4));
            }
            else if(matcher.group(5)!= null){
                tagValues.add(matcher.group(5));
                tagValues.add(matcher.group(6));
            }
        }
        
        
        
        return tagValues;
    }
    
}

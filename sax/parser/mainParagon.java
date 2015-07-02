/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sax.parser;

import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.time;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.stream.XMLStreamException;
import static org.apache.poi.hssf.usermodel.HeaderFooter.time;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class mainParagon {
    
    
    // Hash Map Patient ID -> Patient Class
    static Map<Integer, Patient> patMap = new HashMap<Integer, Patient>();
    // Hash Map CRNT -> Lab Patient Class
    static Map<String, labPatient> labMap = new HashMap<String, labPatient>();
    //List of Meds (ACE/ARB)
    static List<String> medList = new ArrayList<String>();
    //List of Meds for Diuretics
    static List<String> diuList = new ArrayList<String>();
    //List of BP Meds (Anti Hypersensitive drugs etc)
    static List<String> BPMedList = new ArrayList<String>();
    static List<String> crnt; 
    public static void main(String[] args) throws IOException, XMLStreamException, FileNotFoundException, ParseException, Exception {
        
        medList = readMeds();
        diuList = readDiuretics();
        BPMedList = readBPMeds();
        PrintStream out = new PrintStream(new FileOutputStream("output.txt"));
        System.setOut(out);
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        System.out.println(sdf.format(cal.getTime()));
        System.out.println();
        myXMLParser mxp = new myXMLParser();
        
        try {
            File inputFile = new File("Dataset/Paragon BMI and Other Vitals.xml");
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            PBMIHandler pBMIHandler = new PBMIHandler();
            saxParser.parse(inputFile, pBMIHandler);
            patMap = pBMIHandler.getMap();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Age Done");
//        //Paragon Problem List Deidentified
        
//        try {
//            File inputFile = new File("Dataset/Paragon Problem List.xml");
//            SAXParserFactory factory = SAXParserFactory.newInstance();
//            SAXParser saxParser = factory.newSAXParser();
//            PListHandler pListHandler = new PListHandler();
//            saxParser.parse(inputFile, pListHandler);
//            patMap = pListHandler.getMap();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        
        
        // PPL Alternative - Stax Parser
        staxPL ste = new staxPL();
        patMap = ste.parsePL(patMap);
        
        System.out.println("PPL  Done");
                //Paragon Encounters
        //Remove Comment 
        
//        try {
//            File inputFile = new File("Dataset/Paragon Encounters.xml");
//            SAXParserFactory factory = SAXParserFactory.newInstance();
//            SAXParser saxParser = factory.newSAXParser();
//            PEncountersHandler pEncountersHandler = new PEncountersHandler();
//            saxParser.parse(inputFile, pEncountersHandler);
//            patMap = pEncountersHandler.getMap();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        System.out.println("PE  Done");

        
//        //Paragon Notes 
        
//        try {
//            File inputFile = new File("Dataset/Paragon Notes.xml");
//            SAXParserFactory factory = SAXParserFactory.newInstance();
//            SAXParser saxParser = factory.newSAXParser();
//            PNotesHandler pNotesHandler = new PNotesHandler();
//            saxParser.parse(inputFile, pNotesHandler);
//            patMap = pNotesHandler.getMap();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        
        
        // Alternative PN Approach 
        //Remove comment below 
//        patMap = mxp.parsePN(patMap);
        
        System.out.println("PN  Done");
        

        
//        Text Entries - SAX Parser
//        try {
//            File inputFile = new File("Dataset/Paragon Text Entries.xml");
//            SAXParserFactory factory = SAXParserFactory.newInstance();
//            SAXParser saxParser = factory.newSAXParser();
//            PTEntriesHandler pTEntriesHandler = new PTEntriesHandler();
//            saxParser.parse(inputFile, pTEntriesHandler);
//            patMap = pTEntriesHandler.getMap();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        
        
        

        
        // Alternate version of TE - Stax Parser:
        
        
//        staxTE ste = new staxTE();
//        patMap = ste.parseTE(patMap);
//        
//        System.out.println("TE  Done");
        
        // Another Alternate version of TE - Regex Parser
        
        //Remove comment to parse Text Entry
//        patMap = mxp.parseTE(patMap);
        
        System.out.println("TE Done");
        
//        for (Map.Entry<Integer, Patient> entry : patMap.entrySet()) {
//            Patient px = entry.getValue();
//            
//            System.out.println(entry.getKey()+ "/" + px.crnt  + "/" + px.age + "/" + px.HF + "/" + px.apr + "/" + px.T_ICD + "/" +  "/" + px.lvef.toString());
//        }
        

        //Med
        try {
            File inputFile = new File("Dataset/Paragon Meds.xml");
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            PMedID pMedID = new PMedID();
            saxParser.parse(inputFile, pMedID);
            crnt= pMedID.getCrnt();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        
        
        try {
            File inputFile = new File("Dataset/Paragon Meds.xml");
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            PMedHandler pMedHandler = new PMedHandler();
            saxParser.parse(inputFile, pMedHandler);
            labMap = pMedHandler.getLabMap();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
//        
//        // Lab
        
        try {
            File inputFile = new File("Dataset/Paragon CBC and Other Labs.xml");
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            PCBCHandler pCBCHandler = new PCBCHandler();
            saxParser.parse(inputFile, pCBCHandler);
            labMap = pCBCHandler.getLabMap();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Lab Done");
        
//        for (Map.Entry<String, labPatient> entry : labMap.entrySet()) {
//            labPatient px = entry.getValue();
//            px.labPrint();
//            
//        }
        
        
        // Merging and priniting
        System.out.println("Pat ID" + "\t" + "crnt_mrn" + "\t" + "RESULT" + "\t" + "REASON");
        for (Map.Entry<Integer, Patient> entry : patMap.entrySet()) {
            Patient px = entry.getValue();
            System.out.println("Checking "+ px.Pat_ID + "/"+px.crnt);
            px.lp = labMap.get(px.crnt);
            System.out.println(px.lp.BPmedNo);
            px.patPrint();
        }
        
        //Delete the following stuff 
        
//        String fileName = "tempMRN.txt";
//        FileReader fileReader = new FileReader(fileName);
//        BufferedReader bufferedReader = new BufferedReader(fileReader);
//        String line;
//        
//        while ((line = bufferedReader.readLine()) != null) {
//            for (Map.Entry<Integer, Patient> entry : patMap.entrySet()) {
//                Patient px = entry.getValue();
//                if(px.crnt.contains(line)){
//                    px.patPrint();
//                }
//            }
//        }
        
    }
    

    public Map<Integer, Patient> getMap() {
        return patMap;
    }
    
    public Map<String, labPatient> getLabMap() {
        return labMap;
    }
    public List<String> getCrnt() {

        return crnt;
    }
    
    public List<String> getMedList(){
        
        return medList;
    }
    public List<String> getDiuList(){
        
        return diuList;
    }
    
    public List<String> getBPList() {

        return BPMedList;
    }
    public static List<String> readDiuretics() throws FileNotFoundException, IOException{
        String line;
        
        FileReader fileReader   = new FileReader("Dataset/diuretcs.txt");


        BufferedReader bufferedReader   = new BufferedReader(fileReader);
        
        
        
        while ((line = bufferedReader.readLine()) != null) {
            String broken[] = line.split(",");
            for(String str: broken){
                if(!diuList.contains(str)){
                    diuList.add(str);
                    
                }
            }
        }
        bufferedReader.close();
        return diuList;     
        
    }
    
    public static List<String> readMeds() throws FileNotFoundException, IOException{
        String line;
        
        FileReader fileReader   = new FileReader("Dataset/meds2.txt");


        BufferedReader bufferedReader   = new BufferedReader(fileReader);
        
        
        
        while ((line = bufferedReader.readLine()) != null) {
            String broken[] = line.split(",");
            for(String str: broken){
                if(!medList.contains(str)){
                    medList.add(str);
                    
                }
            }
        }
        bufferedReader.close();
        return medList;     
        
    }
    //BPmeds
    public static List<String> readBPMeds() throws FileNotFoundException, IOException {
        String line;

        FileReader fileReader = new FileReader("Dataset/BPmeds.txt");

        BufferedReader bufferedReader = new BufferedReader(fileReader);

        while ((line = bufferedReader.readLine()) != null) {
            String broken[] = line.split(",");
            for (String str : broken) {
                if (!BPMedList.contains(str)) {
                    BPMedList.add(str);
                }
            }
        }
        bufferedReader.close();
        return BPMedList;

    }
}


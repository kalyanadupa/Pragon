/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sax.aprser;

import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.time;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
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
    
    static Map<Integer, Patient> patMap = new HashMap<Integer, Patient>();
    static Map<String, labPatient> labMap = new HashMap<String, labPatient>();
    static List<String> medList = new ArrayList<String>();
    static List<String> crnt; 
    public static void main(String[] args) throws IOException, XMLStreamException {
        
        medList = readMeds();
        PrintStream out = new PrintStream(new FileOutputStream("outputN.txt"));
        System.setOut(out);
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        System.out.println(sdf.format(cal.getTime()));
        System.out.println();
        
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
        
        try {
            File inputFile = new File("Dataset/Paragon Problem List.xml");
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            PListHandler pListHandler = new PListHandler();
            saxParser.parse(inputFile, pListHandler);
            patMap = pListHandler.getMap();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        System.out.println("PPL  Done");
//                //Paragon Encounters
        try {
            File inputFile = new File("Dataset/Paragon Encounters.xml");
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            PEncountersHandler pEncountersHandler = new PEncountersHandler();
            saxParser.parse(inputFile, pEncountersHandler);
            patMap = pEncountersHandler.getMap();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("PE  Done");

        
//        //Paragon Notes 
        
        try {
            File inputFile = new File("Dataset/Paragon Notes.xml");
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            PNotesHandler pNotesHandler = new PNotesHandler();
            saxParser.parse(inputFile, pNotesHandler);
            patMap = pNotesHandler.getMap();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        
        myXMLParser mxp = new myXMLParser();
        patMap = mxp.parseTE(patMap);
        
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
            px.lp = labMap.get(px.crnt);
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
    
    
}


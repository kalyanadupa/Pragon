/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sax.parser;

import java.util.List;
import java.util.Map;
import negex.paragonTest;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class PBMIHandler extends DefaultHandler {

    boolean bAge = false;
    boolean bpat_id = false;
    boolean bBMI = false;
    boolean bcrnt = false;
    boolean bBP = false;
    String currCRNT = "null";
//    boolean bMarks = false;
    mainParagon mP = new mainParagon();
    
    Map<Integer, Patient> patMap = mP.getMap();
    String pat_ID = "NULL";
    
    int currPat = 0;
    paragonTest pT = new paragonTest();
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.equalsIgnoreCase("Detail")) {
            
        } else if (qName.equalsIgnoreCase("pat_id")) {
            bpat_id = true;
        } 
        else if (qName.equalsIgnoreCase("age")) {
            bAge = true;
        } 
        else if (qName.equalsIgnoreCase("bmi")) {
            bBMI = true;
        } 
        else if (qName.equalsIgnoreCase("crnt_mrn")) {
            bcrnt = true;
        }
        else if (qName.equalsIgnoreCase("bp_systolic")) {
            bBP = true;
        }
    }

    @Override
    public void endElement(String uri,  String localName, String qName) throws SAXException {
        if (qName.equalsIgnoreCase("Detail")) {
//            System.out.println("End Element :" + qName);
        }
    }

    @Override
    public void characters(char ch[],
            int start, int length) throws SAXException {
        
        if(bcrnt){
            currCRNT = new String(ch, start, length);
            bcrnt = false;
        }
        
        
        if (bpat_id) {
            pat_ID = new String(ch, start, length);
//            System.out.println("in Pat " + pat_ID);
            Integer id = Integer.valueOf(pat_ID);
            if(patMap.containsKey(id))
                currPat = id;
            else{
                Patient pNew = new Patient(id,currCRNT);
                patMap.put(id, pNew);
                currPat = id;
            }

            bpat_id = false;
        } 
        else if (bAge) {
            
            String strAge = new String(ch, start, length);
            
//            System.out.println("Diagnosis Name: "
//                    + dxName +" pi "+ pat_ID);     
            Integer age = Integer.valueOf(strAge);
            if (age >= 55) {
                if(patMap.containsKey(currPat)){
                    Patient px = patMap.get(currPat);
                    px.age = true;
                }                    
            }
            bAge = false;            
        } 
        else if (bBMI) {
            String strBMI = new String(ch, start, length);
            

//            System.out.println("Diagnosis Name: "s
//                    + dxName +" pi "+ pat_ID);     
            
            float fbmi = Float.parseFloat(strBMI);
            if (patMap.containsKey(currPat)) {
                Patient px = patMap.get(currPat);
                
                if(px.bmi == -1)
                    px.bmi = fbmi;
            }
                
            
            bBMI = false;
        }
        else if (bBP) {
            String strBMI = new String(ch, start, length);

//            System.out.println("Diagnosis Name: "s
//                    + dxName +" pi "+ pat_ID);     
            int fbmi = Integer.valueOf(strBMI);
            if (patMap.containsKey(currPat)) {
                Patient px = patMap.get(currPat);

                if (px.sbp == -1) {
                    px.sbp = fbmi;
                }
            }

            bBP = false;
        }
    }
    
    public Map<Integer, Patient> getMap() {
        return patMap;
    }
    
    
}

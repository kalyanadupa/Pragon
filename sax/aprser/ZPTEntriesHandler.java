/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sax.aprser;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import negex.paragonTest;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ZPTEntriesHandler extends DefaultHandler {

    
    boolean bpat_id = false;
    boolean bEText = false;
//    boolean bNickName = false;
//    boolean bMarks = false;
    mainParagon mP = new mainParagon();
    
    Map<Integer, Patient> patMap = mP.getMap();
    String pat_ID = "NULL";
    
    int currPat = 0;
    paragonTest pT = new paragonTest();
    @Override
    public void startElement(String uri,
            String localName, String qName, Attributes attributes)
            throws SAXException {
        if (qName.equalsIgnoreCase("Detail")) {
            
        } else if (qName.equalsIgnoreCase("pat_id")) {
            bpat_id = true;
        } 
        else if (qName.equalsIgnoreCase("echo_text")) {
            bEText = true;
        } 
//            else if (qName.equalsIgnoreCase("nickname")) {
//            bNickName = true;
//        } else if (qName.equalsIgnoreCase("marks")) {
//            bMarks = true;
//        }
    }

    @Override
    public void endElement(String uri,  String localName, String qName) throws SAXException {
        if (qName.equalsIgnoreCase("Detail")) {
//            System.out.println("End Element :" + qName);
        }
    }

    @Override
    public void characters(char ch[], int start, int length) throws SAXException {
        
        if (bpat_id) {
            pat_ID = new String(ch, start, length);
//            System.out.println("in Pat " + pat_ID);
            Integer id = Integer.valueOf(pat_ID);
            if(patMap.containsKey(id))
                currPat = id;
            else{
                Patient pNew = new Patient(id);
                patMap.put(id, pNew);
                currPat = id;
            }

            bpat_id = false;
        } 
        else if (bEText) {
            try {
                String dxName = new String(ch, start, length);
                
//            System.out.println("Diagnosis Name: "
//                    + dxName +" pi "+ pat_ID);
               
                if (patMap.containsKey(currPat)) {
                    Patient px = patMap.get(currPat);
                    px.lvef = pT.getLVEF(dxName, px.lvef);
                    
                }
                
                
            } catch (Exception ex) {
                Logger.getLogger(ZPTEntriesHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
            bEText = false;
        } 

    }
    
    public Map<Integer, Patient> getMap() {
        return patMap;
    }
    
    
}

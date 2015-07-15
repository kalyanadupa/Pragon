/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sax.parser;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import negex.paragonTest;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class PListHandler extends DefaultHandler {

    boolean bdx_name = false;
    boolean bpat_id = false;
    boolean bnd = false;
//    boolean bNickName = false;
//    boolean bMarks = false;
    mainParagon mP = new mainParagon();
    
    Map<Integer, Patient> patMap = mP.getMap();
    String pat_ID = "NULL";
    String vDate = "NULL";
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
        else if (qName.equalsIgnoreCase("dx_name")) {
            bdx_name = true;
        } 
            else if (qName.equalsIgnoreCase("noted_date")) {
            bnd = true;
        } 
            
//            else if (qName.equalsIgnoreCase("marks")) {
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
    public void characters(char ch[],
            int start, int length) throws SAXException {
        
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
        else if (bnd) {
            vDate = new String(ch, start, length);

            bpat_id = false;
        }
        else if (bdx_name) {
            try {
                String dxName = new String(ch, start, length);
//            System.out.println("Diagnosis Name: "
//                    + dxName +" pi "+ pat_ID);
                if (pT.hasHeartFailure(dxName)) {
                    if(patMap.containsKey(currPat)){
                        Patient px = patMap.get(currPat);
                        px.HF = true;
                    }
                }
                if (pT.searchWithNegation(dxName, "transplant")) {
                    if (currPat == 772126) {
                        System.out.println("hgft" + dxName);
                    }
                    if (patMap.containsKey(currPat)) {
                        Patient px = patMap.get(currPat);
                        px.T_ICD = false;
                    }
                }
                
                else if (pT.searchWithNegation(dxName, "ICD")) {
                    if (currPat == 772126) {
                        System.out.println("hgft" + dxName);
                    }
                    if (patMap.containsKey(currPat)) {
                        Patient px = patMap.get(currPat);
                        px.T_ICD = false;
                    }
                }
                if (pT.searchWithNegation(dxName, "implantable cardioverter defibrillator") ) {
                    if (currPat == 772126) {
                        System.out.println("hgft" + dxName);
                    }
                    if (patMap.containsKey(currPat)) {
                        Patient px = patMap.get(currPat);
                        px.T_ICD = false;
                    }
                }
                
                // Checking for Cnacer Step 8
                if ((pT.searchWithNegation(dxName, "malignant")) && (!pT.searchWithNegation(dxName, "prostate")) && (!pT.searchWithNegation(dxName, "basal cell"))) {
                    if (patMap.containsKey(currPat)) {
                        Patient px = patMap.get(currPat);
                        px.cancer = false;
                    }
                }
                if ((pT.searchWithNegation(dxName, "prostate")) || (pT.searchWithNegation(dxName, "basal cell"))) {
                    if (patMap.containsKey(currPat)) {
                        Patient px = patMap.get(currPat);
                        px.bc = true;
                    }
                }
                
            } catch (Exception ex) {
                Logger.getLogger(PListHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
            bdx_name = false;
        } 

    }
    
    public Map<Integer, Patient> getMap() {
        return patMap;
    }
    
    
}

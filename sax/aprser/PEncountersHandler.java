/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sax.aprser;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import negex.paragonTest;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class PEncountersHandler extends DefaultHandler {

    boolean bdx_name = false;
    boolean bpat_id = false;
    boolean bvType = false;
    boolean bvDate = false;
    
//    boolean bNickName = false;
//    boolean bMarks = false;
    
    mainParagon mP = new mainParagon();

    Map<Integer, Patient> patMap = mP.getMap();
    String pat_ID = "NULL";
    String vDate,vType ;

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
        else if (qName.equalsIgnoreCase("DX_NAME")) {
            bdx_name = true;
        } 
        else if (qName.equalsIgnoreCase("enc_type_dsc")) {
            bvType = true;
        } 
        else if (qName.equalsIgnoreCase("contact_date")) {
            bvDate = true;
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
        if (bpat_id) {
            pat_ID = new String(ch, start, length);
//            System.out.println("in Pat " + pat_ID);
            Integer id = Integer.valueOf(pat_ID);
            if (patMap.containsKey(id)) {
                currPat = id;
            } else {
                Patient pNew = new Patient(id);
                patMap.put(id, pNew);
                currPat = id;
            }
            bpat_id = false;
        } 
        else if (bdx_name) {
            try {
                String dxName = new String(ch, start, length);
                // Checks or heart failure step 3
                if (pT.hasHeartFailure(dxName)) {
                    if (patMap.containsKey(currPat)) {
                        Patient px = patMap.get(currPat);
                        px.HF = true;
                    }
                }
               // Checks for transplant or ICD - step 7 
                if (pT.searchWithNegation(dxName, "transplant")) {
                    if((currPat == 4123629) || (currPat == 1945618) || (currPat == 1464556) )
                        System.out.println(currPat + "\n"+dxName);
                    if (patMap.containsKey(currPat)) {
                        Patient px = patMap.get(currPat);
                        px.T_ICD = false;
                    }
                } else if (pT.searchWithNegation(dxName, "ICD")) {
                    if((currPat == 4123629) || (currPat == 1945618) || (currPat == 1464556) )
                        System.out.println(currPat + "\n"+dxName);
                    if (patMap.containsKey(currPat)) {
                        Patient px = patMap.get(currPat);
                        px.T_ICD = false;
                    }
                }
                if (pT.searchWithNegation(dxName, "implantable cardioverter defibrillator")) {
                    if ((currPat == 4123629) || (currPat == 1945618) || (currPat == 1464556)) {
                        System.out.println(currPat);
                        System.out.println(dxName);
                    }
                    if (patMap.containsKey(currPat)) {
                        Patient px = patMap.get(currPat);
                        px.T_ICD = false;
                    }
                }
                
                // Checking for Cnacer Step 8
                if ((pT.searchWithNegation(dxName, "malignant")) && (!pT.searchWithNegation(dxName, "prostate"))  && (!pT.searchWithNegation(dxName, "basal cell")) ) {
                    try {
                        testDate td = new testDate();
                        if (td.nMonth(vDate) <= 60) {

                            if (patMap.containsKey(currPat)) {
                                Patient px = patMap.get(currPat);
                                px.cancer = false;
                            }
                        }
                    } catch (ParseException ex) {
                        Logger.getLogger(PEncountersHandler.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    
                    
                    
                }
                
                if ((pT.searchWithNegation(dxName, "prostate")) && (pT.searchWithNegation(dxName, "basal cell"))) {
                    if (patMap.containsKey(currPat)) {
                        Patient px = patMap.get(currPat);
                        px.bc = true;
                    }
                }
                if (vType.toLowerCase().contains("inpatient")) {
                    try {
                        testDate td = new testDate();
                        if (td.nMonth(vDate) <= 9) {
                            
                            if (pT.hasHeartFailure(dxName)) {
                                if (patMap.containsKey(currPat)) {
                                    Patient px = patMap.get(currPat);
                                    px.inpatient = true;
                                }
                            }
                        }
                    } catch (ParseException ex) {
                        Logger.getLogger(PEncountersHandler.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
                
                // Checks for Cancer step 8
                
                

            } catch (Exception ex) {
                Logger.getLogger(PEncountersHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
            bdx_name = false;
        } 
        else if (bvType){
            vType = new String(ch, start, length);
           
            bvType = false;
        }
        else if (bvDate){
            vDate = new String(ch, start, length);
            bvDate = false;
        }

    }
    
    public Map<Integer, Patient> getMap() {
        return patMap;
    }
}

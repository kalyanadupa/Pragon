/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sax.aprser;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import negex.paragonTest;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class PNotesHandler extends DefaultHandler {

    boolean bnote_text = false;
    boolean bpat_id = false;

    mainParagon mP = new mainParagon();

    Map<Integer, Patient> patMap = mP.getMap();
    String pat_ID = "NULL";
    String vDate,vType ;
    int currPat = 0;
    boolean bvType = false;
    boolean bvDate = false;
    paragonTest pT = new paragonTest();
    
    
    @Override
    public void startElement(String uri,
            String localName, String qName, Attributes attributes)
            throws SAXException {
        if (qName.equalsIgnoreCase("Detail")) {
            
        } else if (qName.equalsIgnoreCase("pat_id")) {
            bpat_id = true;
        } 
        else if (qName.equalsIgnoreCase("note_text")) {
            bnote_text = true;
        } 
        else if (qName.equalsIgnoreCase("enc_type_dsc")) {
            bvType = true;
        } else if (qName.equalsIgnoreCase("contact_date")) {
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
        /**
         * Angioedema (excl 7) Pancreatitis (excl 22) Renal artery stenosis
         * (excl 25)

         */
        else if (bnote_text) {
            
            String notesText = new String(ch, start, length);
            if(currPat == 3900782){
                
                if(notesText.contains("yolodeha"))
                    System.out.println("Working");
                
            }
            if(pT.searchwithoutNegation(notesText, "Angioedema")){
                if(patMap.containsKey(currPat)){
                    Patient px = patMap.get(currPat);
                    px.apr = false;
                }
            }
            
            else if(pT.searchwithoutNegation(notesText, "Pancreatitis")){
                if (patMap.containsKey(currPat)) {
                    Patient px = patMap.get(currPat);
                    px.apr = false;
                }
            }
            else if(pT.searchwithoutNegation(notesText, "Renal artery stenosis")){
                if (patMap.containsKey(currPat)) {
                    Patient px = patMap.get(currPat);
                    px.apr = false;
                }
            }
            if (vType.toLowerCase().contains("inpatient")) {
                    try {
                        testDate td = new testDate();
                        
                        if(td.nMonth(vDate) <= 9)
                            if (pT.hasHeartFailure(notesText)) {
                                if (patMap.containsKey(currPat)) {
                                    Patient px = patMap.get(currPat);
                                    px.inpatient = true;
                                }
                            }
                        
                    } catch (Exception ex) {
                        Logger.getLogger(PEncountersHandler.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
            }
            bnote_text = false; 
        }
        else if (bvType) {
            vType = new String(ch, start, length);
            
            bvType = false;

        } else if (bvDate) {
            vDate = new String(ch, start, length);
            bvDate = false;
        }

    }
    
    public Map<Integer, Patient> getMap() {
        return patMap;
    }
}

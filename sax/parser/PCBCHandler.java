/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sax.parser;

import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import negex.paragonTest;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
//import static sax.aprser.mainParagon.labMap;

public class PCBCHandler extends DefaultHandler {

    boolean bName = false;
    boolean blab_id = false;
    boolean bValue = false;
    boolean bDate = false;
//    boolean bNickName = false;
//    boolean bMarks = false;
    mainParagon mP = new mainParagon();
    
    Map<String, labPatient> labMap = mP.getLabMap();
    Map<Integer, Patient> patMap = mP.getMap();
    String lab_ID = "NULL";
    String labName = "NULL";
    String strBMI = "NULL";
    String currPat = null;
    paragonTest pT = new paragonTest();
    @Override
    public void startElement(String uri,
            String localName, String qName, Attributes attributes)
            throws SAXException {
        if (qName.equalsIgnoreCase("Detail")) {
            
        } else if (qName.equalsIgnoreCase("crnt_mrn")) {
            blab_id = true;
        } 
        else if (qName.equalsIgnoreCase("lab_nm")) {
            bName = true;
        } 
        else if (qName.equalsIgnoreCase("lab_val")) {
            bValue = true;
            
        } 
        else if (qName.equalsIgnoreCase("lab_filed_dts")) {
            bDate = true;
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
        
        if (blab_id) {
            lab_ID = new String(ch, start, length);
//            System.out.println("in Pat " + lab_ID);
            
            if(labMap.containsKey(lab_ID))
                currPat = lab_ID;
            else{
                labPatient pNew = new labPatient();
                pNew.id = lab_ID;
                labMap.put(lab_ID, pNew);
                currPat = lab_ID;
            }

            blab_id = false;
        } 
        else if (bName) {
            labName = new String(ch, start, length);
//            System.out.println("Diagnosis Name: "
//                    + dxName +" pi "+ lab_ID);     
            bName = false;            
        } 
        else if (bValue) {
            strBMI = new String(ch, start, length);
            strBMI = strBMI.replace(",", "");
//            System.out.println("Diagnosis Name: "
//                    + dxName +" pi "+ lab_ID);     
            
            
            
                
            
            bValue = false;
        }
        else if (bDate) {
            String dt = new String(ch, start, length);
            String[]  tok = dt.split(" ");
            dt = tok[0];
            
            float labValue = 0;
            try {

                if (labMap.containsKey(currPat)) {
                    labPatient px = labMap.get(currPat);
                    if (labName.toLowerCase().contains("hemoglobin")) {
                        testDate td = new testDate();
                        
                        if ((px.Hemoglobin == -1) && (td.nMonth(dt) <= 2)) {
                            labValue = Float.parseFloat(strBMI);
                            px.Hemoglobin = labValue;
                        }
                    }
                    //AFRICAN AMERICAN
                    
                    if ((labName.contains("GFR")) && (labName.toUpperCase().contains("AFRICAN AMERICAN")) ) {
                        if (px.gfr == -1) {
                            labValue = Float.parseFloat(strBMI);
                            if(labValue < 99999)
                                px.gfr = labValue;
                        }
                    }
                    if ((labName.contains("BNP")) && (labName.toLowerCase().contains("natriuretic"))) {
                        if (px.bnp == -1) {
                            labValue = Float.parseFloat(strBMI);
                            px.bnp = labValue;
                        }
                    }
                }

            } catch (NumberFormatException numberFormatException) {
                System.out.println("***" + lab_ID + "/" + labName + "/" + strBMI);
            } catch (ParseException ex) {
                Logger.getLogger(PCBCHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
            catch(Exception e){
                System.out.println("Error : " + dt);
            }
            
            bDate = false;
        }

    }
    
    public Map<String, labPatient> getLabMap() {
        return labMap;
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sax.aprser;

import java.util.List;
import java.util.Map;
import negex.paragonTest;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
//import static sax.aprser.mainParagon.labMap;

public class PMedHandler extends DefaultHandler {

    
    boolean blab_id = false;
    boolean bGenName = false;
    boolean bTradeName = false;
//    boolean bNickName = false;
//    boolean bMarks = false;
    mainParagon mP = new mainParagon();
    
    Map<String, labPatient> labMap = mP.getLabMap();
    List<String> medList = mP.getMedList();
    List<String> crnt = mP.getCrnt();
    String lab_ID = "NULL";
    String labGenName = "NULL";
    int index = 0;
    String currPat = crnt.get(index);
    
    paragonTest pT = new paragonTest();
    @Override
    public void startElement(String uri,
            String localName, String qName, Attributes attributes)
            throws SAXException {
        if (qName.equalsIgnoreCase("Detail")) {
            
        } else if (qName.equalsIgnoreCase("crnt_mrn")) {
            blab_id = true;
        } 
        else if (qName.equalsIgnoreCase("generic_nm")) {
            bGenName = true;
        } 
        else if (qName.equalsIgnoreCase("trade_nm")) {
            bTradeName = true;
            
        } 
//        else if (qName.equalsIgnoreCase("marks")) {
//            bMarks = true;
//        }
    }

    @Override
    public void endElement(String uri,  String localName, String qName) throws SAXException {
        if (qName.equalsIgnoreCase("Detail")) {
            index++;
//            System.out.println("End Element :" + qName);
        }
    }

    @Override
    public void characters(char ch[],
            int start, int length) throws SAXException {
        
        if (blab_id) {
            lab_ID = new String(ch, start, length);
//            System.out.println("in Pat " + lab_ID);
            
//            if(labMap.containsKey(lab_ID))
//                currPat = lab_ID;
//            else{
//                labPatient pNew = new labPatient();
//                pNew.id = lab_ID;
//                labMap.put(lab_ID, pNew);
//                currPat = lab_ID;
//            }

            blab_id = false;
        } 
        else if (bGenName) {
            lab_ID = crnt.get(index);
            if (labMap.containsKey(lab_ID)) {
                currPat = lab_ID;
            } else {
                labPatient pNew = new labPatient();
                pNew.id = lab_ID;
                labMap.put(lab_ID, pNew);
                currPat = lab_ID;
            }
            
            labGenName = new String(ch, start, length);
            labGenName = labGenName.toLowerCase();
            if(labMap.containsKey(currPat)){
                labPatient px = labMap.get(currPat);
                //if(!px.med){
                    for(String str : medList){
                        if(labGenName.contains(str.toLowerCase())){
                            px.med = true;
                            break;
                        }                            
                    }
                //}
            }
            bGenName = false;            
        } 
        else if (bTradeName) {            
            String tradeName = new String(ch, start, length);
            if (labMap.containsKey(currPat)) {
                labPatient px = labMap.get(currPat);
                if (!px.med) {
                    for (String str : medList) {
                        if (tradeName.toLowerCase().contains(str.toLowerCase())) {
                            px.med = true;
                            break;
                        }
                    }
                }
            }
            bTradeName = false;
        }

    }
    
    public Map<String, labPatient> getLabMap() {
        return labMap;
    }
    
}
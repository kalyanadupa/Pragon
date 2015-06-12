package sax.aprser;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author aka324
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import negex.paragonTest;

public class staxPL {

    public static void main(String[] args) {
        
    }
    public Map<Integer, Patient> parsePL(Map<Integer, Patient> patMap)  throws XMLStreamException, FileNotFoundException {
//        List<Employee> empList = null;
        Patient currPat = null;
        String tagContent = null;
        paragonTest pT = new paragonTest();
        XMLInputFactory factory = XMLInputFactory.newInstance();
//        File f = new File("employee.xml");
        XMLStreamReader reader = factory.createXMLStreamReader(new FileInputStream("Dataset/Paragon Problem List.xml"));
        String vDate = null;
        testDate td = new testDate();
        while (reader.hasNext()) {
            int event = reader.next();

            switch (event) {
                case XMLStreamConstants.START_ELEMENT:
                    if ("Detail".equals(reader.getLocalName())) {
                        currPat = new Patient();
                        vDate = null;
                    }
                    if ("Detail_Collection".equals(reader.getLocalName())) {
//                        empList = new ArrayList<>();
                    }
                    break;

                case XMLStreamConstants.CHARACTERS:
                    tagContent = reader.getText().trim();
                    break;

                case XMLStreamConstants.END_ELEMENT:
                    switch (reader.getLocalName()) {
                        case "Detail":
                            
                            break;
                        case "pat_id":
                            Integer id = Integer.valueOf(tagContent);
                            currPat.Pat_ID = id;
                            break;
                        case "noted_date":
                            vDate = tagContent;
                            break;
                        case "dx_name":
                            try {
                                String dxName = tagContent;
                                
                                if (pT.hasHeartFailure(dxName)) {
                                    if (patMap.containsKey(currPat.Pat_ID)) {
                                        Patient px = patMap.get(currPat.Pat_ID);
                                        px.HF = true;
                                    }
                                }
                                if (pT.searchWithNegation(dxName, "transplant")) {
                                    if ((currPat.Pat_ID == 4123629) || (currPat.Pat_ID == 1945618) || (currPat.Pat_ID == 1464556)) {
                                        System.out.println(currPat.Pat_ID);
                                        System.out.println(dxName);
                                    }
                                    if (patMap.containsKey(currPat.Pat_ID)) {
                                        Patient px = patMap.get(currPat.Pat_ID);
                                        px.T_ICD = false;
                                    }
                                } else if (pT.searchWithNegation(dxName, "ICD")) {
                                    if ((currPat.Pat_ID == 4123629) || (currPat.Pat_ID == 1945618) || (currPat.Pat_ID == 1464556)) {
                                        System.out.println(currPat.Pat_ID);
                                        System.out.println(dxName);
                                    }
                                    if (patMap.containsKey(currPat.Pat_ID)) {
                                        
                                        Patient px = patMap.get(currPat.Pat_ID);
                                        px.T_ICD = false;
                                    }
                                }
                                if (pT.searchWithNegation(dxName, "implantable cardioverter defibrillator")) {
                                    if ((currPat.Pat_ID == 4123629) || (currPat.Pat_ID == 1945618) || (currPat.Pat_ID == 1464556)) {
                                        System.out.println(currPat.Pat_ID);
                                        System.out.println(dxName);
                                    }
                                    if (patMap.containsKey(currPat.Pat_ID)) {
                                        Patient px = patMap.get(currPat.Pat_ID);
                                        px.T_ICD = false;
                                    }
                                }

                                // Checking for Cnacer Step 8
                                if(!vDate.isEmpty()){
                                    if(td.nMonth(vDate) < 120){
                                        if ((pT.searchWithNegation(dxName, "malignant")) && (!pT.searchWithNegation(dxName, "prostate")) && (!pT.searchWithNegation(dxName, "basal cell"))) {
                                            if (patMap.containsKey(currPat.Pat_ID)) {
                                                Patient px = patMap.get(currPat.Pat_ID);
                                                px.cancer = false;
                                            }
                                        }
                                    }
                                }

                            } catch (Exception ex) {
                                Logger.getLogger(PListHandler.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            break;
                    }
                    break;

                case XMLStreamConstants.START_DOCUMENT:
//                    empList = new ArrayList<>();
                    break;
            }

        }


        return patMap;
    }
}

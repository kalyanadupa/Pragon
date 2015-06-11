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
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import negex.paragonTest;

public class staxTE {

    public static void main(String[] args) {
        
    }
    public Map<Integer, Patient> parseTE(Map<Integer, Patient> patMap)  throws XMLStreamException, FileNotFoundException {
//        List<Employee> empList = null;
        Patient currPat = null;
        String tagContent = null;
        paragonTest pT = new paragonTest();
        XMLInputFactory factory = XMLInputFactory.newInstance();
//        File f = new File("employee.xml");
        XMLStreamReader reader = factory.createXMLStreamReader(new FileInputStream("Dataset/Paragon Text Entries New.xml"));

        while (reader.hasNext()) {
            int event = reader.next();

            switch (event) {
                case XMLStreamConstants.START_ELEMENT:
                    if ("Detail".equals(reader.getLocalName())) {
                        currPat = new Patient();
                        
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
                            if(patMap.containsKey(currPat.Pat_ID)){
                                Patient px = patMap.get(currPat.Pat_ID);
                                if(!currPat.lvef.isEmpty()){
                                    List<Float> tempList = new ArrayList<Float>();
                                    tempList.addAll(px.lvef);
                                    tempList.addAll(currPat.lvef);
                                    px.lvef = tempList;
                                    System.out.println(tempList.toString() +" | " + currPat.lvef.toString());
                                }
                                
                            }
                            break;
                        case "pat_id":
                            Integer id = Integer.valueOf(tagContent);
                            currPat.Pat_ID = id;
                            break;
                        case "crnt_mrn":
                            currPat.crnt = tagContent;
                            break;
                        case "echo_text":
                            String dxName = tagContent;
                            System.out.println("***");
                            System.out.println(currPat.Pat_ID);
                            System.out.println("");
                            System.out.println(tagContent);
                            System.out.println("\n***\n");
                            currPat.lvef = pT.getLVEF(dxName, currPat.lvef);
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

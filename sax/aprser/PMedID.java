/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sax.aprser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import negex.paragonTest;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
//import static sax.aprser.mainParagon.labMap;

public class PMedID extends DefaultHandler {

    
    boolean blab_id = false;
    List<String> crnt =new  ArrayList<String>();
    
    
    @Override
    public void startElement(String uri,
            String localName, String qName, Attributes attributes)
            throws SAXException {
        if (qName.equalsIgnoreCase("Detail")) {
            
        } else if (qName.equalsIgnoreCase("crnt_mrn")) {
            blab_id = true;
        }
//        else if (qName.equalsIgnoreCase("marks")) {
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
        
        if (blab_id) {
            String lab_ID = new String(ch, start, length);
//            System.out.println("in Pat " + lab_ID);
            crnt.add(lab_ID);
            
            blab_id = false;
        } 
    }
    
    public List<String> getCrnt() {
        return crnt;
    }
    
}

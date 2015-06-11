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
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

public class StaxParserDemo {

    public static void main(String[] args) throws XMLStreamException, FileNotFoundException {
        List<Employee> empList = null;
        Employee currEmp = null;
        String tagContent = null;
        XMLInputFactory factory = XMLInputFactory.newInstance();
//        File f = new File("employee.xml");
        XMLStreamReader reader = factory.createXMLStreamReader(new FileInputStream("Dataset/Paragon Text Entries.xml"));

        while (reader.hasNext()) {
            int event = reader.next();

            switch (event) {
                case XMLStreamConstants.START_ELEMENT:
                    if ("Detail".equals(reader.getLocalName())) {
                        currEmp = new Employee();
                        currEmp.id = reader.getAttributeValue(0);
                    }
                    if ("Detail_Collection".equals(reader.getLocalName())) {
                        empList = new ArrayList<>();
                    }
                    break;

                case XMLStreamConstants.CHARACTERS:
                    tagContent = reader.getText().trim();
                    break;

                case XMLStreamConstants.END_ELEMENT:
                    switch (reader.getLocalName()) {
                        case "Detail":
                            empList.add(currEmp);
                            break;
                        case "pat_id":
                            currEmp.firstName = tagContent;
                            break;
                        case "crnt_mrn":
                            currEmp.lastName = tagContent;
                            break;
                        case "echo_text":
                            currEmp.location = tagContent;
                            break;
                    }
                    break;

                case XMLStreamConstants.START_DOCUMENT:
                    empList = new ArrayList<>();
                    break;
            }

        }

        //Print the employee list populated from XML
        for (Employee emp : empList) {
            System.out.println(emp);
        }

    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sax.parser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author aka324
 */
public class PrettyPrintXML {
    public static void format(String filePath) throws IOException{
//        String fileName = "delete.xml";
        String fileName = filePath.replaceAll("\\+", " ");
        FileWriter fileWriter = new FileWriter(fileName);
        String line = null;
        try {
            FileReader fileReader = new FileReader(filePath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            BufferedWriter bufferedWriter =  new BufferedWriter(fileWriter);
            int value =0;

//            while ((line = bufferedReader.readLine()) != null) {
//                System.out.println(line);
//            }
//            System.out.println(value);
            
            
            while ((value = bufferedReader.read()) != -1) {
                char c = (char) value;
                if(c == '<')
                    bufferedWriter.write("\n<");
                else if(c == '>')
                    bufferedWriter.write(">\n");
                else
                    bufferedWriter.write(c);
            }
            
            

            
            
            bufferedWriter.close();
            
            
            bufferedReader.close();
            
            
            
        } catch (FileNotFoundException ex) {
            System.out.println("Unable to open file '"+ fileName + "'");
        } catch (IOException ex) {
            System.out.println( "Error reading file '"+ fileName + "'");
        }
    }
     
   public static String prettyPrint(String xml) {
        if (xml == null || xml.trim().length() == 0) {
            return "";
        }

        int stack = 0;
        StringBuilder pretty = new StringBuilder();
        String[] rows = xml.trim().replaceAll(">", ">\n").replaceAll("<", "\n<").split("\n");

        for (int i = 0; i < rows.length; i++) {
            if (rows[i] == null || rows[i].trim().length() == 0) {
                continue;
            }

            String row = rows[i].trim();
            if (row.startsWith("<?")) {
                // xml version tag
                pretty.append(row + "\n");
            } else if (row.startsWith("</")) {
                // closing tag
                String indent = repeatString("    ", --stack);
                pretty.append(indent + row + "\n");
            } else if (row.startsWith("<")) {
                // starting tag
                String indent = repeatString("    ", stack++);
                pretty.append(indent + row + "\n");
            } else {
                // tag data
                String indent = repeatString("    ", stack);
                pretty.append(indent + row + "\n");
            }
        }

        return pretty.toString().trim();
    }
    
    private static String repeatString(String space,int stack) {
        StringBuilder indent = new StringBuilder();
        for (int i = 0; i < stack; i++) {
            indent.append(space);
        }
        return indent.toString();
    }
    
    
    public static void main(String[] argsv) throws IOException{
        format("Dataset/Paragon+Text+Entries.xml");
    }
}

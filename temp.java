
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import negex.paragonTest;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author aka324
 */
public class temp {
    public static void main(String[] argsv) throws FileNotFoundException, IOException{
        String report = " ,hey, ";
//        report = " "+ report+ " ";
        if(report.matches("[-!$%^&*()_+|~=`{}\\[\\]:\";'<>?,.\\/\\s]+hey[-!$%^&*()_+|~=`{}\\[\\]:\";'<>?,.\\/\\s]+"))
            System.out.println("ifgye");
        
        
    }
    public static void prettyPrint(String xml) {
        

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
                System.out.println(row);
            } else if (row.startsWith("</")) {
                // closing tag
                String indent = repeatString(--stack);
               System.out.println(indent + row);
            } else if (row.startsWith("<")) {
                // starting tag
                String indent = repeatString(stack++);
                System.out.println(indent + row);
            } else {
                // tag data
                String indent = repeatString(stack);
               System.out.println(indent + row);
            }
        }

        
    }
    private static String repeatString(int stack) {
        StringBuilder indent = new StringBuilder();
        for (int i = 0; i < stack; i++) {
            indent.append(" ");
        }
        return indent.toString();
    }
}

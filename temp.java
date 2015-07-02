
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
        String fileName = "Dataset/Paragon Notes.xml";
        FileReader fileReader = new FileReader(fileName);
        paragonTest pT = new paragonTest();
        // Always wrap FileReader in BufferedReader.
        BufferedReader bufferedReader
                = new BufferedReader(fileReader);
        String line;
        PrintStream out = new PrintStream(new FileOutputStream("checkXMLParser.txt"));
        System.setOut(out);
        String bigString = "";
        while ((line = bufferedReader.readLine()) != null) {
            System.out.print(line);
            System.out.print(" ");
        }
        
        fileName = "checkXMLParser.txt";
        fileReader = new FileReader(fileName);

        // Always wrap FileReader in BufferedReader.
        bufferedReader = new BufferedReader(fileReader);

        out = new PrintStream(new FileOutputStream("checkXMLParserOutput.txt"));
        System.setOut(out);
        String[] str = bufferedReader.readLine().split("<\note>");
        //prettyPrint(str);
//        while ((str = bufferedReader.readLine()) != null) {
//            System.out.println(Arrays.toString(getTagValues(str).toArray()));
//        }
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

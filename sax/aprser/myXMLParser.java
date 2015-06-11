package sax.aprser;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
public class myXMLParser {

    
    public Map<Integer, Patient> parseTE(Map<Integer,Patient> patMap) throws FileNotFoundException, IOException{
        String fileName = "Dataset/Paragon Text Entries.xml";
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
        String str = "";

//        while ((str = bufferedReader.readLine()) != null) {
//            System.out.println(Arrays.toString(getTagValues(str).toArray()));
//        }
        str = bufferedReader.readLine();
        List<String> tagValues = getTagValues(str);
        for (int i = 0; i < tagValues.size(); i = i + 2) {
            if (tagValues.get(i).equalsIgnoreCase("echo_text")) {
                Integer id = Integer.valueOf(tagValues.get(i-1));
                List<Float> temp = new ArrayList<Float>();
                temp = pT.getLVEF(tagValues.get(i+1),temp);        
                if(!temp.isEmpty()){
                    if(patMap.containsKey(id)){
                        Patient px = patMap.get(id);
                        temp.addAll(px.lvef);
                        px.lvef = temp;
                    }
                }
            }
        }
        return patMap;
    }
    private static final Pattern TAG_REGEX = Pattern.compile("<pat_id>(.+?)</pat_id>.*<echo_text>(.+?)</echo_text>");

    private static List<String> getTagValues(final String str) {
        List<String> tagValues = new ArrayList<String>();
//        Matcher matcher = TAG_REGEX.matcher(str);
//        while (matcher.find()) {
//            tagValues.add(matcher.group(1));
//            tagValues.add(matcher.group(2));
//        }
        
        Matcher matcher = Pattern.compile("<(pat_id)>(.+?)</pat_id>|<(echo_text)>(.+?)</echo_text>").matcher(str);
        while (matcher.find()) {
            
            if (matcher.group(1) != null) {
                tagValues.add(matcher.group(1));
                tagValues.add(matcher.group(2));
            }
            else if(matcher.group(3)!= null){
                tagValues.add(matcher.group(3));
                tagValues.add(matcher.group(4));
            }
        }
        
        
        
        return tagValues;
    }
    
}

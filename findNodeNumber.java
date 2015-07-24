
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import static java.lang.System.in;
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
public class findNodeNumber {
    public static void main(String[] argsv) throws FileNotFoundException, IOException{
        final File folder = new File("getCount");

        listFilesForFolder(folder);
    }
    public static void listFilesForFolder(final File folder) throws FileNotFoundException, IOException {
        String line = "";
        int j  = 0;
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                listFilesForFolder(fileEntry);
            } else {
                int i = 0;
                String fname = fileEntry.getName();
                String fileName = "getCount/"+fname;
                FileReader fileReader = new FileReader(fileName);
                paragonTest pT = new paragonTest();

                BufferedReader bufferedReader = new BufferedReader(fileReader);
                if((fname.contains(".xml"))&&(!fname.contains("+"))){
                    Pattern p = Pattern.compile("<Detail>");
                    
                    while ((line = bufferedReader.readLine()) != null){
                        Matcher m = p.matcher(line);
                        while (m.find()) {
                            i++;
                            j++;
                        }
                    }
                    System.out.println(fname +" - "+ i + " - " + j );
                }
                bufferedReader.close();
                    
            }
        }
    }

    
    
}

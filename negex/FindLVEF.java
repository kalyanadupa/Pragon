/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negex;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static jdk.nashorn.internal.objects.NativeArray.join;

/**
 *
 * @author aka324
 */
public class FindLVEF {
    public static void main(String[] argsv){
        String report = " EF is 34%";
        ArrayList<String> extractedValues = new ArrayList<>();
        
        //Patterns
        Pattern p1 = Pattern.compile("(left ventricular ejection fraction|lvef|lv ejection fraction|left ventricle ejection fraction|ejection fraction| ef |ejection frcation)[^_%\\.]*?([\\d-\\.]+)\\s*'?%");
        String anatomy = "(left ventricular systolic function|left ventricular function|systolic function of the left ventricle|lv systolic function|left ventricular ejection fraction|ejection fraction|left ventricle)";
        String expression = "(normal|normal global|low normal|well preserved|severely reduced|moderately reduced|moderately decreased|moderately depressed|severely decreased|severe|markedly decreased|markedly reduced|severely globally reduced|mildly reduced|mildly decreased|mildly depressed|severely depressed)";
        Pattern p2 = Pattern.compile(anatomy + " [^_%\\.]*?" + expression);
        Pattern p3 = Pattern.compile(expression + "[^A-Za-z]+" + anatomy); //example-The left ventricle is mildly dilated with normal (50-55%) ejection fraction
		/*Pattern p3= Pattern.compile("lv ejection fraction .*? ([\\d-]+)(%?)");*/

		//Pattern p4=Pattern.compile("(\\d+) ml");
        
        // Code
        String[] splits = report.split("\n\n");
        for (String string : splits) {
            //string = join(string.split("\\s*\n\\s*"), ' ');

				//if(!string.contains("In summary, this is an abnormal echocardiogram with left ventricular enlargement, severe left ventricular systolic dysfunction")) continue;
				/*if(string.contains("LV ejection fraction 50.0 % >= 55")){
             System.out.println(string);
             }*/
            boolean found = false;
            Matcher m = p1.matcher(string.toLowerCase());
            while (m.find()) {
                System.out.println(string + "\t" + m.group() + "\t" + m.group(1) + "\t" + m.group(2));
                found = true;
                extractedValues.add(m.group(2));
            }

            if (found) {
                continue;
            }
            m = p2.matcher(string.toLowerCase());
            while (m.find()) {
                System.out.println(string + "\t" + m.group() + "\t" + m.group(1) + "\t" + m.group(2));
                found = true;
                extractedValues.add(m.group(2));
            }

            if (found) {
                continue;
            }
            m = p3.matcher(string.toLowerCase());
            while (m.find()) {
                System.out.println(string + "\t" + m.group() + "\t" + m.group(2) + "\t" + m.group(1));
                found = true;
                extractedValues.add(m.group(1));
            }

        }
        
        if (!extractedValues.isEmpty()) {
            System.out.println("This1 " + extractedValues.toString());
            System.out.println("This2 " + inferLVEF(extractedValues));
        }
        
        else {
//            report = join(report.split("\\s*\n\\s*"), ' ');

            if (report.toLowerCase().matches(".*moderate (lv systolic dysfunction|left ventricular dysfunction|left ventricular systolic dysfunction).*")) {
                extractedValues.add("moderately decreased");
                System.out.println("Extracted : " +extractedValues.toString());
                System.out.println("Infered : " +inferLVEF(extractedValues));
            } else if (report.toLowerCase().matches(".*(marked|severe) (lv systolic dysfunction|left ventricular dysfunction|left ventricular systolic dysfunction).*")) {
                extractedValues.add("markedly reduced");
                System.out.println("Extracted : " +extractedValues.toString());
                System.out.println("Infered : " +inferLVEF(extractedValues));
            }  

        }
        
    }
    
    private static String inferLVEF(ArrayList<String> extractedValues) {
        if (extractedValues.size() > 0) {
            //examples : 10, 10.5
            for (String val : extractedValues) {
                if (val.matches("\\d+(\\.\\d+)?")) {
                    return val;
                }
            }

            //examples : 10-15
            for (String val : extractedValues) {
                if (val.matches("\\d+(\\.\\d+)?-\\d+(\\.\\d+)?")) {
                    String[] splits = val.split("-");
                    return "" + (Float.parseFloat(splits[0]) + Float.parseFloat(splits[1])) / 2;
                }
            }

            //examples : 10-
            for (String val : extractedValues) {
                if (val.matches("\\d+(\\.\\d+)?-")) {
                    return val.substring(0, val.length() - 1);
                }
            }

            //examples : -10
            for (String val : extractedValues) {
                if (val.matches("-\\d+(\\.\\d+)?")) {
                    return val.substring(1);
                }
            }

            for (String val : extractedValues) {
                if (val.matches("markedly reduced|severely reduced|severely decreased|severely globally reduced|severe|severely depressed")) {
                    return "" + 20;
                }
            }

            for (String val : extractedValues) {
                if (val.matches("moderately reduced|moderately decreased|moderately depressed|mildly depressed")) {
                    return "" + 32.5;
                }
            }

            for (String val : extractedValues) {
                if (val.matches("normal|normal global|low normal|well preserved")) {
                    return "" + 55;
                }
            }

            for (String val : extractedValues) {
                if (val.matches("mildly reduced|mildly decreased")) {
                    return "" + 42.5;
                }
            }

            return extractedValues.get(0);
        }

        return "";

    }
    
    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negex;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import opennlp.tools.chunker.ChunkerME;
import opennlp.tools.chunker.ChunkerModel;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.Span;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;


/**
 *
 * @author aka324
 */
public class paragonTest {
    public boolean hasHeartFailure(String doc1) {
        String doc = doc1.replaceAll("\\p{Punct}|\\d", " ").toLowerCase();
        doc = " "+doc+" ";

        Pattern pHF = Pattern.compile("\\s+HF\\s+");
        Matcher mHF = pHF.matcher(doc);
        if (mHF.find()) {
            return true;
        }
        Pattern pHFC = Pattern.compile("^HF\\s+");
        Matcher mHFC = pHFC.matcher(doc);
        if (mHFC.find()) {
            return true;
        }

        Pattern pHeFa = Pattern.compile("\\s+heart\\s+failure\\s+");
        Matcher mHeFa = pHeFa.matcher(doc.toLowerCase());
        if (mHeFa.find()) {
            return true;
        }
        Pattern pHeFaC = Pattern.compile("^heart\\s+failure\\s+");
        Matcher mHeFaC = pHeFaC.matcher(doc.toLowerCase());
        if (mHeFaC.find()) {
            return true;
        }


        Pattern pDiDy = Pattern.compile("\\s+diastolic\\s+dysfunction\\s+");
        Matcher mDiDy = pDiDy.matcher(doc.toLowerCase());
        if (mDiDy.find()) {
            return true;
        }


        Pattern pDiDyC = Pattern.compile("^diastolic\\s+dysfunction\\s+");
        Matcher mDiDyC = pDiDyC.matcher(doc.toLowerCase());
        if (mDiDyC.find()) {
            return true;
        }

        Pattern pCa = Pattern.compile("\\s+cardiomyopathy\\s+");
        Pattern pCaC = Pattern.compile("^cardiomyopathy\\s*");
        Matcher mCa = pCa.matcher(doc.toLowerCase());
        Matcher mCaC = pCaC.matcher(doc.toLowerCase());
        if ((mCa.find()) || (mCaC.find())) {
            return true;
        }

        return false;
    }

    public boolean searchwithoutNegation(String doc,String query){
        doc = " "+doc+" ";

       String[] words =  query.toLowerCase().split(" ");
       if(words.length == 1){
           Pattern p1 = Pattern.compile("\\s+"+words[0] +"\\s+");
           Matcher m1 = p1.matcher(doc.toLowerCase());
           if (m1.find()) {
               return true;
           }
           return false;
       }

        if (words.length == 2) {
            Pattern p1 = Pattern.compile("\\s+"+words[0]+"\\s+"+words[1]+"\\s+");
            Matcher m1 = p1.matcher(doc.toLowerCase());
            if (m1.find()) {
                return true;
            }
            return false;
        }

        if (words.length == 3) {
            Pattern p1 = Pattern.compile("\\s+" + words[0] + "\\s+" + words[1] + "\\s+"+ words[2] + "\\s+");
            Matcher m1 = p1.matcher(doc.toLowerCase());
            if (m1.find()) {
                return true;
            }
            return false;
        }

        if (words.length == 4) {
            Pattern p1 = Pattern.compile("\\s+" + words[0] + "\\s+" + words[1] + "\\s+"+ words[2] + "\\s+" + "\\s+"+ words[3] + "\\s+");
            Matcher m1 = p1.matcher(doc.toLowerCase());
            if (m1.find()) {
                return true;
            }
            return false;
        }
        return false;
    }

    public boolean searchWithNegation(String doc,String query) throws FileNotFoundException, Exception{
        doc = " "+ doc.toLowerCase()+ " ";
        query = query.toLowerCase();
        if(!searchwithoutNegation(doc,query)){
            return false;
        }
        GenNegEx g  = new GenNegEx();
        //
        File ruleFile = new File("negex_triggers.txt");
        Scanner sc = new Scanner(ruleFile);

        boolean negatePossible = true;
        ArrayList rules = new ArrayList();
        String afterNegCheck = "";

        while (sc.hasNextLine()) {
            rules.add(sc.nextLine());
        }

        afterNegCheck = g.negCheck(doc, query, rules, negatePossible);

        if (afterNegCheck.contains("[NEGATED]")) {
            return false;
        } else if (afterNegCheck.contains("[POSSIBLE]")) {
            return false;
        } else{
            return true;
        }
    }
    //Malignant stuff

    List<String> getScentencesWithWord(String input,String[] words){
        List<String> op = new ArrayList<String>();
        try {


            String word_re = words[0];
            String str = "";

            for (int i = 1; i < words.length; i++) {
                word_re += "|" + words[i];
            }
            word_re = "[^.]*\\b(" + word_re + ")\\b[^.]*[.]";
            str = input;
            Pattern re = Pattern.compile(word_re,
                    Pattern.MULTILINE | Pattern.COMMENTS
                    | Pattern.CASE_INSENSITIVE);
            Matcher match = re.matcher(str);
            String sentenceString = "";
            while (match.find()) {
                op.add(match.group(0));
            }
        } catch (Exception e) {
        }
        return op;
    }




    //LVEF Stuff
    public static List<Float> getLVEF(String report,List<Float> currLVEF){
        try{
            ArrayList<String> extractedValues = new ArrayList<>();
            report = " " + report.toLowerCase() + " ";
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
//                System.out.println(string + "\t" + m.group() + "\t" + m.group(1) + "\t" + m.group(2));
                    found = true;
                    extractedValues.add(m.group(2));
                }

                if (found) {
                    continue;
                }
                m = p2.matcher(string.toLowerCase());
                while (m.find()) {
//                System.out.println(string + "\t" + m.group() + "\t" + m.group(1) + "\t" + m.group(2));
                    found = true;
                    extractedValues.add(m.group(2));
                }

                if (found) {
                    continue;
                }
                m = p3.matcher(string.toLowerCase());
                while (m.find()) {
//                System.out.println(string + "\t" + m.group() + "\t" + m.group(2) + "\t" + m.group(1));
                    found = true;
                    extractedValues.add(m.group(1));
                }

            }

            if (!extractedValues.isEmpty()) {
//            System.out.println("This1 " + extractedValues.toString());
                extractedValues.add(inferLVEF(extractedValues));
//            System.out.println("This2 " + inferLVEF(extractedValues));
            } else {
//            report = join(report.split("\\s*\n\\s*"), ' ');

                if (report.toLowerCase().matches(".*moderate (lv systolic dysfunction|left ventricular dysfunction|left ventricular systolic dysfunction).*")) {
                    //extractedValues.add("moderately decreased");
//                System.out.println("Extracted : " + extractedValues.toString());
                    extractedValues.add(inferLVEF(extractedValues));
                } else if (report.toLowerCase().matches(".*(marked|severe) (lv systolic dysfunction|left ventricular dysfunction|left ventricular systolic dysfunction).*")) {
                    extractedValues.add("markedly reduced");
//                System.out.println("Extracted : " + extractedValues.toString());
                    extractedValues.add(inferLVEF(extractedValues));
                }

            }
            Pattern p37 = Pattern.compile("((\\d+\\s*(\\-|to)\\s*\\d+)|(\\d*\\.\\d*\\s*(\\-|to)\\s*\\d*\\.\\d*)|(\\d*\\.\\d+)|(\\d+))(?=(\\s*(\\%)))");
            String check2 = " ";
            Matcher m37 = p37.matcher(check2);

            for (String str : extractedValues) {
                try {
                    Float f = Float.parseFloat(calculate(str));
                    if (f > 5) {
                        currLVEF.add(f);
                    }
                } catch (NumberFormatException numberFormatException) {
//                System.out.println("** BIG ERROR ** " + str);
                }
            }
            if (!extractedValues.isEmpty()) {
//            System.out.println("Final 1 "+extractedValues.toString());
//            System.out.println("Final 2 "+currLVEF.toString());
            }
        }
        catch(Exception ex){
            System.out.println("Error in getLEVf \n"+report);
        }
        return currLVEF;
    }

    public static String inferLVEF(ArrayList<String> extractedValues) {
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

        public static String calculate(String matchstring) {
        String[] items = matchstring.split("to|-");
        if ((matchstring.contains("-") || matchstring.contains("to")) && !matchstring.contains("[") && !matchstring.contains("_") && (items.length >=2)) {
            Integer f1 = new Integer(items[0]);
            Integer f2 = new Integer(items[1]);
            String ca = "";
            if(((f1 < 45) && (f2 > 45)) || (f1 == 45) || (f2 == 45) )
                ca = Integer.toString(9999);
            else
                ca = Float.toString((f1 + f2) / 2);

            return ca;
            //return matchstring.add(ca);
        } else {
            return matchstring;
        }
    }





    public static void main(String[] args) throws Exception{
        String doc = "REPORT:\n"
                + " Accession/Order Number:EC110010822\n"
                + " Exam           05/25/2011 15:24\n"
                + " Date:\n"
                + " Ht           191    Wt           96     BSA   2.26\n"
                + " (cm):               (kg):\n"
                + " Ordering Physician:        GORDON, AMY  \n"
                + " Attending Physician:       HOLLY, THOMAS A. MD\n"
                + " Fellow Physician:          Schimmel, Daniel, MD\n"
                + " Technologist               VASSALLO MD, PATRICIA\n"
                + "\n"
                + " Procedure CPT:     93312 93325 93320 93312 \n"
                + " Indications          Atrial fibrillation\n"
                + "\n"
                + " ICD                  427.31\n"
                + " Codes:\n"
                + "       141     /   95            105\n"
                + "\n"
                + " Contrast:                           Total Dose \n"
                + "                                     (mL):\n"
                + " Technical ?Quality:                                               Rhythm:\n"
                + " BASIC MEASUREMENTS\n"
                + "                                  Actual     Normal    Indexed        Normal\n"
                + "                                  Pt Value   Value     Pt Value       Indexed Value\n"
                + " LV Size-end systole                     cm 2.3-3.4 cm\n"
                + " LV size-end diastole                    cm 3.5-5.1 cm          cm/m?  2.1-2.7 cm/m?\n"
                + " LV ejection fraction             35-    %  &gt;= 55%\n"
                + "\n"
                + " LV post wall thick-diastole             cm 0.65-1.1 cm\n"
                + " IV septal wall thick-diastole           cm 0.5-1.0 cm\n"
                + " Aortic Root Dia.                        cm 2.0-3.7 cm          cm/m?  &lt; 2.0 cm/m?\n"
                + " Aortic Cusp Separation                  cm 1.5-2.6 cm\n"
                + " LVOT Diameter\n"
                + "\n"
                + " LA Diameter                             cm 1.9-4.0 cm          cm/m?  &lt;= 2.1 cm/m?\n"
                + " LA Volume                                                      mL/m?  22 +- mL/m?\n"
                + "\n"
                + "\n"
                + " DOPPLER\n"
                + " TV Peak Velocity                  269 cm/s              \n"
                + "\n"
                + "\n"
                + " Proc. Components\n"
                + " A transesophageal echocardiogram, Doppler study (pulsed, continuous \n"
                + " wave, spectral and color flow) and contrast study were performed.  \n"
                + " After obtaining informed written consent, the patient's posterior \n"
                + " oropharynx was anesthetized utilizing viscous lidocaine gargle as well \n"
                + " as Exactacain spray. The patient was given a total of 4 mg of Versed \n"
                + " and 100 mcg of fentanyl in incremental doses throughout the study for \n"
                + " conscious sedation.  Conscious sedation and the transesophageal \n"
                + " echocardiogram procedure were performed by the cardiology fellow, Dr. \n"
                + " Dan Schimmel under direct attending supervision. A multiplane \n"
                + " transesophageal echocardiogram probe was inserted and advanced to 45 cm \n"
                + " from the incisors.  Transgastric as well as transesophageal images were \n"
                + " obtained.  The probe was then rotated to 180 degrees and interrogation \n"
                + " of the descending aorta was performed during a pullback maneuver. \n"
                + " Several transthoracic images were also taken. The patient tolerated the \n"
                + " procedure well.  Periprocedural monitoring was performed by the nurse, \n"
                + " Maria Davila, RN.  There were no procedure or post procedure \n"
                + " complications noted.. The patient was observed in the echocardiography \n"
                + " laboratory for 75 minutes after the procedure and was discharged in \n"
                + " stable condition. \n"
                + "\n"
                + " FINDINGS\n"
                + "\n"
                + " Left Ventricle (LV)\n"
                + " Left ventricular size is normal. Left ventricular systolic function is \n"
                + " moderately reduced. Visually estimated LV ejection fraction is 35-40%. \n"
                + " There is significant beat to beat variation due to underlying atrial \n"
                + " fibrillation. In addition, the patient was tachycardic throughout the \n"
                + " study. \n"
                + "\n"
                + " Right Ventricle (RV)\n"
                + " The right ventricle is mildly dilated. Right ventricular systolic \n"
                + " function is reduced. \n"
                + "\n"
                + " Left Atrium (LA)\n"
                + " The left atrium is dilated. \n"
                + "\n"
                + " Right Atrium (RA)\n"
                + " The right atrium is dilated. \n"
                + "\n"
                + " LA \n"
                + " Appendage\n"
                + " The left atrial appendage is free of thrombus. The left atrial \n"
                + " appendage emptying velocity is decreased at 24 cm/s, which indicates \n"
                + " high thrombotic potential. \n"
                + "\n"
                + " IA \n"
                + " Septum\n"
                + " There is no evidence of interatrial communication by color Doppler or \n"
                + " bubble contrast study. \n"
                + "\n"
                + " Hemodynamic\n"
                + "\n"
                + " The pulmonary artery systolic pressure is estimated to be 30 mmHg plus \n"
                + " right atrial pressure.\n"
                + "\n"
                + " Aortic \n"
                + " Valve\n"
                + " The aortic valve is tri-leaflet. The aortic valve opens well. There is \n"
                + " no aortic regurgitation. There is no evidence of aortic stenosis. \n"
                + "\n"
                + " Mitral \n"
                + " Valve\n"
                + " Mitral valve leaflets are thickened but open well. Mild mitral \n"
                + " regurgitation is present. There is no evidence of mitral stenosis. \n"
                + "\n"
                + " Tricuspid Valve\n"
                + " The tricuspid valve is normal in structure and function. There is \n"
                + " moderate tricuspid regurgitation. \n"
                + "\n"
                + " Pulmonic Valve\n"
                + " The pulmonic valve is not well visualized, but grossly normal. There is \n"
                + " trace pulmonic regurgitation. \n"
                + "\n"
                + " Great \n"
                + " Vessels\n"
                + " The aortic root diameter at the sinus of Valsalva is upper normal and \n"
                + " measures 3.8 cm. The proximal ascending aorta diameter is mildly \n"
                + " increased and measures 4.0 cm. The descending thoracic aorta diameter \n"
                + " is normal. There is no significant atherosclerosis.\n"
                + "\n"
                + " Pericardiu\n"
                + "\n"
                + " There is no pericardial effusion. \n"
                + " CONCLUSIONS\n"
                + " Left ventricular size is normal. Left ventricular systolic function is \n"
                + " moderately reduced. Visually estimated LV ejection fraction is 35-40%. \n"
                + " There is significant beat to beat variation due to underlying atrial \n"
                + " fibrillation. In addition, the patient was tachycardic throughout the \n"
                + " study. \n"
                + " The right ventricle is mildly dilated. Right ventricular systolic \n"
                + " function is reduced. \n"
                + " There is biatrial dilation.The left atrial appendage is free of \n"
                + " thrombus.\n"
                + " Mitral valve leaflets are thickened but open well. Mild mitral \n"
                + " regurgitation is present.\n"
                + " There is moderate tricuspid regurgitation.\n"
                + " The aortic root diameter at the sinus of Valsalva is upper normal and \n"
                + " measures 3.8 cm. The proximal ascending aorta diameter is mildly \n"
                + " increased and measures 4.0 cm.\n"
                + " No other study is available for comparison. \n"
                + " Marc2173\n"
                + "\n"
                + "\n"
                + " Patricia Vassallo MD\n"
                + "\n"
                + " (Electronically Signed)\n"
                + "\n"
                + " Final            25 May 2011 17:11\n"
                + " Date:\n"
                + " Northwestern Memorial Hospital Cardiac Graphics Lab is Accredited by The ICAEL.";

        List<Float> tempL = new ArrayList<Float>();
        
        System.out.println(getLVEF(doc,tempL));

//        System.out.println(getLVF(doc.toLowerCase()));
//        System.out.println("||"+doc+"||");
//        if (searchWithNegation(doc,"cancer")) {
//            System.out.println("Working");
//        }
//        else
//            System.out.println("Nay");

    }


}

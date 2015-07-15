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
    public List<Float> getLVEF(String report,List<Float> currLVEF){
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
        
        for(String str : extractedValues){
            try {
                Float f = Float.parseFloat(calculate(str));
                if(f > 5)
                    currLVEF.add(f);
            } catch (NumberFormatException numberFormatException) {
//                System.out.println("** BIG ERROR ** " + str);
            }
        }
        if(!extractedValues.isEmpty()){
//            System.out.println("Final 1 "+extractedValues.toString());
//            System.out.println("Final 2 "+currLVEF.toString());
        }
        return currLVEF;
    }
    
    public String inferLVEF(ArrayList<String> extractedValues) {
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
    
        public String calculate(String matchstring) {
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
    
    
    
//    public List<Float> getLVEF(String check1,List<Float> currLVEF){
//        check1 = check1.toLowerCase().replaceAll("\n", "");
//        Pattern p36 = Pattern.compile("(lvef|(ef)|(lv\\s*ejection\\s*fraction)|(left\\s*(ventricular|ventricle)\\s*ejection\\s*fraction)|(ejection\\s*fraction)).{1,85}((\\d+\\s*(\\-|to)\\s*\\d+)|(\\d*\\.\\d*\\s*(\\-|to)\\s*\\d*\\.\\d*)|(\\d*\\.\\d+)|(\\d+))\\s*(\\%)");
//        Pattern p37 = Pattern.compile("((\\d+\\s*(\\-|to)\\s*\\d+)|(\\d*\\.\\d*\\s*(\\-|to)\\s*\\d*\\.\\d*)|(\\d*\\.\\d+)|(\\d+))(?=(\\s*(\\%)))");
//        String check2 = " ";
//
//        if (check1.matches("(.*)((lvef)|(LVEF)|(ejection\\s*fraction)|(//sef//s))(.*)")) {
//            Matcher m36 = p36.matcher(check1);
//            while (m36.find()) {
//                check2 = check2 + "^^ " + m36.group();
//                //excelData[i][startIndexCol] = excelData[i][startIndexCol] + "\n" + "<OUTPUT>" + m36.group();
//            }
//            Matcher m37 = p37.matcher(check2);
//            List<String> matchstring37 = new ArrayList<String>();
//            while (m37.find()) {
//                calculate(matchstring37, m37);
//            }
//
//            if (matchstring37 != null && !matchstring37.isEmpty()) {
//                for (String str : matchstring37) {
//                    try {
//                        currLVEF.add(Float.parseFloat(str));
//                    } catch (NumberFormatException numberFormatException) {
//                        System.out.println("** BIG ERROR ** " + str);
//                    }
//                }
////                excelData[i][startIndexCol + 1] = matchstring37.toString() + "@" + Integer.toString(matchstring37.size());
////                excelData[i][startIndexCol + 2] = Collections.max(matchstring37) + "," + Collections.min(matchstring37);
////                excelData[i][startIndexCol + 3] = matchstring37.get(matchstring37.size() - 1);
//            }
//
//        }
//        return currLVEF;
//        
//    }
    
//    public static String getLVF(String check1) {
//        
//        List<Float> currLVEF = new ArrayList<Float>();
//        check1 = check1.toLowerCase().replaceAll("\n", "");
//        Pattern p36 = Pattern.compile("(lvef|(ef)|(lv\\s*ejection\\s*fraction)|(left\\s*(ventricular|ventricle)\\s*ejection\\s*fraction)|(ejection\\s*fraction)).{1,85}((\\d+\\s*(\\-|to)\\s*\\d+)|(\\d*\\.\\d*\\s*(\\-|to)\\s*\\d*\\.\\d*)|(\\d*\\.\\d+)|(\\d+))\\s*(\\%)");
//        Pattern p37 = Pattern.compile("((\\d+\\s*(\\-|to)\\s*\\d+)|(\\d*\\.\\d*\\s*(\\-|to)\\s*\\d*\\.\\d*)|(\\d*\\.\\d+)|(\\d+))(?=(\\s*(\\%)))");
//        String check2 = " ";
//        
//        if (check1.matches("(.*)((lvef)|(LVEF)|(ejection\\s*fraction)|(\\sef\\s))(.*)")) {
//            Matcher m36 = p36.matcher(check1);
//            while (m36.find()) {
//                check2 = check2 + "^^ " + m36.group();
//                //excelData[i][startIndexCol] = excelData[i][startIndexCol] + "\n" + "<OUTPUT>" + m36.group();
//            }
//            Matcher m37 = p37.matcher(check2);
//            List<String> matchstring37 = new ArrayList<String>();
//            while (m37.find()) {
//                calculate(matchstring37, m37);
//            }
//            
//            if (matchstring37 != null && !matchstring37.isEmpty()) {
//                for(String str: matchstring37){                    
//                    try {
//                        currLVEF.add(Float.parseFloat(str));
//                    } catch (NumberFormatException numberFormatException) {
//                        System.out.println("** BIG ERROR ** " + str);
//                    }
//                }
////                excelData[i][startIndexCol + 1] = matchstring37.toString() + "@" + Integer.toString(matchstring37.size());
////                excelData[i][startIndexCol + 2] = Collections.max(matchstring37) + "," + Collections.min(matchstring37);
////                excelData[i][startIndexCol + 3] = matchstring37.get(matchstring37.size() - 1);
//            }
//            
//        }
//        return currLVEF.toString();
//
//    }

            
    public static void main(String[] args) throws Exception{
        String doc = "Barbara Deroo is a 66y/o female with Turner's syndrome, osteoporosis and hypothyroidism here today for f/up.    hpi  First seen by Dr Hahr during admission in Aug 2013 for A fib.   She has hypothyroidism dx'd age 11.  She has Turner Syndrome diagnosed at age 11, was on HT until about 2007. Has left leg lymphedema.   Follows with cardiology, Dr S. Shah, for coarctation of the aorta, systemic hypertension, hyperlipidemia, severe CAD, and HFpEF with Grade III diastolic dysfunction.  She also has schizophrenia, hearing loss, low vision from detached retina.   First seen in consultation 8/23/2013 at which Atelvia was started for osteoporosis.     She has been going to cardiac rehab, found to be in a fib again.    She has had a couple episodes of dizziness/falls, related to too much salt intake?    Hypothyroidism:  Taking LT4 100 mcg 6.5 tabs per week (dose raised from 6 tabs/wk end of April)    Following gluten-free diet.      hpi bone:  Has GERD  Fractures: hand fracture - listed in the chart but pt doesn't recall.     DXA: 8/2013    Treatment:   Boniva started 2007 - but she couldn't take it fasting (she is terrible at taking anything fasting), but she is now retired so it might be easier. However, it is very hard for her to wait 1 hour to eat in the AM so doesn't think she can do it. No problems remembering to take it.   Reclast June 2011. Learned about data regarding a fib and Reclast - came to discuss  Atelvia started 8/2013 - takes after breakfast, reports no missed doses    Dental visits: regular visits, no implants/extractions planned     Calcium intake:   Diet:   Milk - daily in AM with cereal, in evening with pills  Yogurt - sometimes, 2 today, sometimes none  Cheese - sometimes  Supplements:   Ca - none  Vitamin D 1000 IU daily    Physical activity: cardiac rehab 2-3 x/wk, since 2009, but has not returned since incident where she had a fib    BONE HISTORY  Smoking history: never   Alcohol: none Glucocorticoid Hx: none   Maximum adult height 4'7&quot;  Kidney stone history: none  Family hx of bone disease or fracture: mom - treated for bone disease, no fractures. PGM - hip fracture       Patient Active Problem List:  PMH/PSH:  Patient Active Problem List:     Hyperlipidemia[272.4]    Hypertension[401.9]    Psoriasis[696.1]    Allergic Rhinitis[477.9]    Retinal Disease[362.9]    Other Specified Forms of Hearing Loss[389.8]    Turner Syndrome[758.6]    Hypothyroidism[244.9]    Lymphedema[457.1]    Cataract[366.9]    GERD (Gastroesophageal Reflux Disease)[530.81]    Coarctation of the Aorta[747.10]    Aortic Insufficiency[424.1]    Left Atrial Enlargement[429.3]    Pulmonary hypertension[416.8]    Visual impairment in both eyes[369.3]    Hearing impairment[389.9]    Schizophrenia[295.90]    Coronary artery disease[414.00]    Osteoporosis[733.00]    Sinus disease[473.9]    Meningioma[225.2]    Subconjunctival hemorrhage[372.72]    Basal cell carcinoma of nose[173.31]    Atrial fibrillation[427.31]    Cerumen impaction[380.4]    Lipoma[214.9]    Celiac disease[579.0]    (HFpEF) heart failure with preserved ejection fraction[428.9]    PAST MEDICAL HISTORY   DIAGNOSIS DATE   • ESSENTIAL HYPERTENSION, BENIGN      DIAGNOSED IN 1979, ON INDERAL FOR YEARS, THEN CHANGED TO METOPROLOL;    • OTHER AND UNSPECIFIED HYPERLIPIDEMIA      ON LIPITOR   • OTHER SPECIFIED FORMS OF HEARING LOSS    • TURNER SYNDROME    • HYPOTHYROIDISM      DIAGNOSED AGE 11   • LYMPHEDEMA      SINCE CHILDHOOD   • RETINAL DISEASE      RETINAL DETACHMENT LEFT, HOLE IN RIGHT?   • GERD (GASTROESOPHAGEAL REFLUX DISEASE)      OCCASIONAL SYMPTOMS   • PSYCHIATRIC DISORDER      FOLLOWED BY PSYCHIATRIST, PER PT IS IN REMISSION; ON CHLORPROMAZINE   • PSORIASIS    • BASAL CELL CARCINOMA OF SKIN      IN 2004 - RIGHT TEMPLE AND CHEEK;  LEFT TEMPLE IN JAN. 2004   • AVM      PULMONARY - BY CT ANGIO/MRI; FIRST NOTED 3/08. HAS REMAINED STABLE.    • CATARACT • COARCTATION OF AORTA    • CAD (CORONARY ARTERY DISEASE)      NOTED ON CATH IN 2010: LAD:(PROXIMAL), TUBULAR 30% LESION,  DIFFUSE 80% LESION,,DIAG1 (OSTIAL), DISCRETE 50% LESION ,   • DIASTOLIC DYSFUNCTION    • HEARING LOSS      HEARING AIDS   • SINUS DISEASE      INCIDENTIAL RIGHT MAXILLARY SINUS OPACIFICATION, FOLLOWED BY DR. CONLEY. ASYMPTOMATIC, OBSERVING FOR NOW.    • OSTEOPOROSIS WITH PATHOLOGICAL FRACTURE OF HAND    • PULMONARY HYPERTENSION 2/2/2010   • CORONARY ARTERY DISEASE 7/21/2010   • MENINGIOMA 9/19/2011   • BASAL CELL CARCINOMA OF NASAL TIP    • ATRIAL FIBRILLATION      PAST SURGICAL HISTORY   PROCEDURE DATE   • NECK/CHEST SURGERY PROCEDURE      FOR TURNER SURGERY, WEBBED NECK   • REMOVAL OF NODULE      IN VAGINAL AREA   • EYE SURGERY PROCEDURE      DETATCHED RETNIA   • REMOVAL OF SKIN CANCER    • CATARACT SURG W/IOL, 1 STAGE 11/28/07     OS   • TREATMENT OF RETINA 1993     OS       CURRENT PRESCRIPTIONS:    Levothyroxine Sodium 100 MCG TA     1 tab orally six days a week  1/2 tab on       thursday. - oral  simvastatin 20 MG TA     take 1 tablet by mouth every day  Metoprolol Succinate 50 MG TB     take 1 tablet by mouth daily  Calcipotriene-Betameth Diprop (TACLONEX) 0.005-0.064 % OI     apply once daily to areas on the body and left       scalp  Clobetasol Propionate 0.05 % SO     apply daily to scalp on affected spot  furosemide 20 MG TA     take 1 tablet by mouth twice daily  Cholecalciferol (VITAMIN D) 1000 UNITS TA     1 tablet by mouth daily  lisinopril 5 MG TA     take 1 tablet by mouth every morning  omeprazole 20 MG CP     1 tablet po daily  ATELVIA 35 MG TB     please take 1 tablet once weekly with 4 ounces       of water; take after breakfast  aspirin 325 MG TA     1 tablet every day  Ascorbic Acid (VITAMIN C CR) 500 MG TB     1 tablet twice daily  CHLORPROMAZINE HCL 25 MG OR TA     1 tablet in the am &amp; 3 tablets in the pm        Family History: mom - treated for bone disease, no fractures. PGM - hip fracture     Social History:   Tobacco Use: Never   Alcohol Use: No   Drug Use: No   Sexually Active: No   NARRATIVE:   Single. One brother who lives in Wisconsin. Mother with whom she was very close died in 2006. Worked for State Children and Family Services for 27 years, retired 2009.   Allergies    Allergen  Reactions    •  Dye (Contrast Dye)       Pt. Doesn't think it is an allergy-just doesn't feel well after    •  No Known Drug Allergy     •  No Latex Allergy       PHYSICAL EXAM  BP 112/68 | Pulse 66 | Resp 12 | Ht 4' 5&quot; (1.346 m) | Wt 86 lb (39.009 kg) | BMI 21.53 kg/m2.  Gen: NAD, short stature  Eyes: trouble with seeing to perform eye movements, wears glasses, low vision   ENT: MMM, OP clear, high arched palate  Ears low   Neck webbed, well-healed scar  Thyroid: small-normal size  CV: RRR   Lungs: CTA B  Ext: lymphedema present on the left  Neuro: Alert, interactive   MSK:  +kyphosis   using visual assistance cane    DATA:     VITAMIN D 25 HYDROXY (ng/mL)   Date Value   10/19/2013 26.6*        VITAMIN D25-HYDROXY (ng/mL)   Date Value   11/9/2009 40.7      T3 (ng/dL)   Date Value   8/12/2013 60*        T4 (ug/dL)   Date Value   8/11/2013 7.7         TSH, 3RD GENERATION (uIU/ML)   Date Value   6/18/2014 8.87*   4/30/2014 15.14*        FREE T4 (ng/dL)   Date Value   6/18/2014 0.9    8/12/2013 1.0      Region Exam Date BMD  (g/cm2) T-Score Z-Score Classification   AP Spine (L1-L4) 08/29/2013 0.822 -2.0 -0.2 Osteopenia   Femoral Neck (Left) 08/29/2013 0.559 -2.6 -1.1 Osteoporosis   Total Hip (Left) 08/29/2013 0.631 -2.5 -1.3 Osteoporosis   Total Hip Bilateral Average 08/29/2013 0.646 -2.4 -1.2 Osteopenic   Femoral Neck (Right) 08/29/2013 0.563 -2.6 -1.0 Osteoporosis   Total Hip (Right) 08/29/2013 0.659 -2.3 -1.1 Osteopenia   World Health Organization criteria for BMD interpretation classify patients as Normal (T-score at or above –1.0), Osteopenic (T-score between –1.0 and –2.5), or Osteoporotic (T-score at or below –2.5).      Previous Exams:     Region Exam  Date Age BMD (g/cm2) T-Score BMD Change vs. Baseline BMD Change vs. Previous   AP Spine(L1-L4) 08/29/2013 65 0.822 -2.0 3.8%* 3.8%*    04/18/2011 63 0.793 -2.3     Total Hip(Left) 08/29/2013 65 0.631 -2.5 -2.4% -2.4%    04/18/2011 63 0.647 -2.4         A/P:  Osteoporosis, started on Atelvia 8/2013  Turner syndrome w/CV complications, hearing loss  hypothyroidism   A fib   Celiac disease    1) Hypothyroidism, afib  Last TSH too high and dose raised.  - check TSH today  - adjust levothyroxine as indicated    2) Osteoporosis. DXA 8/29/2013  - doing well with Atelvia, compliant  - check renal chem, vit D 25 OH, PTH  - cont Ca, vit D intake  - regular dental visits, fall precautions  - encouraged to go back to cardiac rehab    3) Turner syndrome  * off HT now  * bone: above  * cardiology significant ds: follows with cardiology  * metabolic syndrome: annual glucose fasting check, lipids at target on statin per cardiology  * celiac: follow gluten-free diet  * liver: yearly LFTs elev alk phos noted in 9/2013, mildly low GFR    RTC 4-6 mos with Erica Klaw, PA-C to monitor thyroid  Allison J. Hahr, MD";
        
        
            
        
//        System.out.println(getLVF(doc.toLowerCase()));
//        System.out.println("||"+doc+"||");
//        if (searchWithNegation(doc,"cancer")) {
//            System.out.println("Working");
//        }
//        else
//            System.out.println("Nay");
           
    }
           
    
}

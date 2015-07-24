/*
 * To change this license header/choose License Headers in Project Properties.
 * To change this template file/choose Tools | Templates
 * and open the template in the editor.
 */
package sax.parser;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author aka324
 */
public class Patient {
    int Pat_ID;
    String crnt;
    String race;
    boolean HF; // true if heartt failure is present 
    boolean age; // true if age is >=55
    boolean apr;  // false if they are present
    boolean T_ICD; // Checks with Negation for transplant and ICD false if its present
    boolean cancer; // malignant & !prostate & !basal cell | returns false if condition is present
    boolean cancer2; // check if any cancer term is present to warn | returns false if present
    boolean bc;     // Checks if it has basal cell or prostate cancer. // true if present
    float bmi;
    boolean inpatient;
    int amt; // amt value changes from -1 to 0 or 1 for the first entry in TE. 0 = amt absent 1 = amt present // Step 16 aortic mitral tricuspid
    boolean stc;
    int sbp;
    boolean cm; // Cardiomyopathy stuff (excl 11) // False if present
    labPatient lp;
    
    List<Float> lvef = new ArrayList<Float>();
    
    public Patient() {
        this.Pat_ID = -1;
        this.race = "";
        this.HF = false;
        this.age = false;
        this.apr = true;
        this.T_ICD = true;
        this.cancer = true;
        this.cancer2 = true;
        this.bc = false;
        this.bmi = -1;
        this.amt = -1;
        this.stc = true;
        this.sbp = -1;
        this.inpatient = true; //Change this to false if you need to use Step 12
        this.cm = true;
    }
    
    public Patient(int id){
        this.Pat_ID = id;
        this.race = "";
        this.HF = false;
        this.age = false;
        this.apr = true;
        this.T_ICD = true;
        this.cancer = true;
        this.cancer2 = true;
        this.bc = false;
        this.bmi = -1;
        this.amt = -1;
        this.stc = true;
        this.sbp = -1;
        this.inpatient = true; //Change this to false if you need to use Step 12
        this.cm = true;
    }
    
    public Patient(int id,String crnt_ip) {
        this.Pat_ID = id;
        this.crnt = crnt_ip;
        this.race = "";
        this.HF = false;
        this.age = false;
        this.apr = true;
        this.T_ICD = true;
        this.cancer = true;
        this.cancer2 = true;
        this.bc = false;
        this.bmi = -1;
        this.amt = -1;
        this.stc = true;
        this.sbp = -1;
        this.inpatient = true; //Change this to false if you need to use Step 12
        this.cm = true;
    }
    
    public void patPrint(){
        //System.out.println(entry.getKey()+ "/" + px.crnt  + "/" + px.age + "/" + px.HF + "/" + px.apr + "/" + px.T_ICD + "/" + bBmi + "/" + px.lvef.toString());
        List<String> reason = new ArrayList<String>();
        List<String> warning = new ArrayList<String>();
        boolean warnLVEF = false; // Checks if LVEF value is 9999 at any time // 9999 is used as a flag to tell that the border value of 45 falls im the lvef 
        if(!this.age)
            reason.add("Age < 55 (step 2)");
        if(!this.HF)
            reason.add("HF not present (step 4)");
        boolean lvef = true;
        for(float f : this.lvef){
            if(f < 45)
                lvef = false;
            if(f > 9998)
                warnLVEF = true;
        }
        if(warnLVEF)
            warning.add("[WARNING] Check the LVEF Range");
        if(!lvef)
            reason.add("lvef < 45 (step 5)");
            
        if(!this.apr)
            reason.add("Angioedema/Pancreatitis/Renal Artery stenosis  were present(step 6)");
        if(!this.T_ICD)
            reason.add("transplant/ICD present (step 7)");
        
        if (!this.cancer) 
            reason.add("History of Malignancy other than prostate or basal cell (step 7)");
        if(this.bmi > 40)
            reason.add("Most recent BMI > 40 (step 9)");
        boolean missing = false;
        
        if (this.lp.Hemoglobin == -1) {
            missing = true;
//            reason.add("Hemoglobin value missing");
        } else {
            if (this.lp.Hemoglobin < 10) {
                reason.add("Hemoglobin < 10 (Step 10)");
            }
        }

        if (this.lp.gfr == -1) {
            missing = true;
//            reason.add("GFR value missing");
        } else {
            if (this.lp.gfr < 30) {
                reason.add("GFR < 30 (Step 10)");
            }
        }
        if (this.lp.bnp == -1) {
            missing = true;
//            reason.add("BNP value missing");
        } else {
            if (this.lp.bnp < 50) {
                warning.add("[WARNING] BNP < 50 (Step 11)");
            }
        }
        
  
        // The inpatient criteria was not considered -- Dr. Shah
//        if(!this.inpatient)
//            reason.add("Not inpatient with HF in last 9Months (Step 12)");
        
        if(this.lp.medCount >= 2)
            reason.add("More than 2  ACEI/Renin/ARB Meds (Step 13)");        
        if(!this.lp.diu)
            reason.add("No Diuretics ");
        if((this.lp.BPmedNo <3) && (this.sbp > 150))
            reason.add("BP value is high without medication (Step 15)");
        if((this.lp.BPmedNo <3) && (this.sbp <111))
            reason.add("BP value is low without medication (Step 15)");
        
        if(this.amt == 1)
            reason.add("aortic/mitral/tricuspid stenosis or regurgitation present (Step 16)");
        if(!this.stc)
            reason.add("Stroke/transient ischemic attack/carotid surgery/carotid angioplasty present (Step 17)");
        if(this.bc)
            warning.add("[WARNING] May contain basal cell or prostate"); // To improve the performance of system remove basal cell condition from malignant condition
        if (!this.cancer2) 
            warning.add("[WARNING] May have cancer "); 
        if(!this.cm)
            reason.add("Cardiomyopathy - DCM/PCM/CCM/VMC");
        
//        if(missing){
//            System.out.println(this.Pat_ID + "\t" + this.crnt + "\t" + "VALUES MISSING" + "\t" + reason.toString());
//            return;
//        }
//        
//        if(reason.isEmpty())
//            System.out.println(this.Pat_ID + "\t" + this.crnt + "\t" + "ACCEPTED");
//        else
//            System.out.println(this.Pat_ID + "\t" + this.crnt + "\t" + "REJECTED" + "\t" + reason.toString());
        
        
        
            
        if(reason.isEmpty())
            if(!warning.isEmpty())
                System.out.println(this.Pat_ID + "\t" + this.crnt + "\t" + "YES" + "\t" + warning.toString());
            else    
                System.out.println(this.Pat_ID + "\t" +this.crnt + "\t" + "YES" );
        else{
            if((!warning.isEmpty()) && (reason.size() == 1) && (reason.get(0).contains("History of Malignancy"))){
                reason.addAll(warning);
                System.out.println(this.Pat_ID + "\t" +this.crnt + "\t" + "YES" + "\t" + reason.toString());
            }
            else{
                reason.addAll(warning);
                System.out.println(this.Pat_ID + "\t" +this.crnt + "\t" + "NO" + "\t" + reason.toString());
            }
        }
        
                
        
    }
    
    public void tempPatPrint(List<logPat> lp) {
        //System.out.println(entry.getKey()+ "/" + px.crnt  + "/" + px.age + "/" + px.HF + "/" + px.apr + "/" + px.T_ICD + "/" + bBmi + "/" + px.lvef.toString());
        List<String> reason = new ArrayList<String>();
        

        if (!this.age) {
            reason.add("Age < 55 (step 2)");
        }
        if (!this.HF) {
            reason.add("HF not present (step 4)");
        }
        boolean lvef = true;
        for (float f : this.lvef) {
            if (f < 45) {
                lvef = false;
            }
        }
        if (!lvef) {
            reason.add("lvef < 45 (step 5)");
        }
        if (!this.apr) {
            reason.add("Angioedema/Pancreatitis/Renal Artery stenosis  were present(step 6)");
        }
        if (!this.T_ICD) {
            reason.add("transplant/ICD present (step 7)");
        }

        if (!this.cancer) {
            reason.add("History of Malignancy other than prostate or basal cell (step 7)");
        }
        if (this.bmi > 40) {
            reason.add("Most recent BMI > 40 (step 9)");
        }
        boolean missing = false;

        if (this.lp.Hemoglobin == -1) {
            missing = true;
//            reason.add("Hemoglobin value missing");
        } else {
            if (this.lp.Hemoglobin < 10) {
                reason.add("Hemoglobin < 10 (Step 10)");
            }
        }

        if (this.lp.gfr == -1) {
            missing = true;
//            reason.add("GFR value missing");
        } else {
            if (this.lp.gfr < 30) {
                reason.add("GFR < 30 (Step 10)");
            }
        }
        if (this.lp.bnp == -1) {
            missing = true;
//            reason.add("BNP value missing");
        } else {
            if (this.lp.bnp < 100) {
                reason.add("BNP < 100 (Step 11)");
            }
        }

        if (!this.inpatient) {
            reason.add("Not inpatient with HF in last 9Months (Step 12)");
        }
        if (!this.lp.med) {
            reason.add("No treatment with ACEI/Renin/ARB (Step 13)");
        }

//        if(missing){
//            System.out.println(this.Pat_ID + "\t" + this.crnt + "\t" + "VALUES MISSING" + "\t" + reason.toString());
//            return;
//        }
//        
//        if(reason.isEmpty())
//            System.out.println(this.Pat_ID + "\t" + this.crnt + "\t" + "ACCEPTED");
//        else
//            System.out.println(this.Pat_ID + "\t" + this.crnt + "\t" + "REJECTED" + "\t" + reason.toString());
        if (reason.isEmpty()) {
            for(logPat lop : lp){
                if(lop.mrn == Integer.parseInt(this.crnt))
                    System.out.println(lop.mrn + "\t" + lop.reason + "\t" + lop.answer+ "\t" + "YES" );
            }
            
        } else {
            for(logPat lop : lp){
                if(lop.mrn == Integer.parseInt(this.crnt))
                    System.out.println(lop.mrn + "\t" + lop.reason + "\t" + lop.answer+ "\t" + "NO" + "\t" + reason.toString()  );
            }
        }

    }
    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sax.parser;

/**
 *
 * @author aka324
 */
public class labPatient {
    String id;  // Current medical record number
    float Hemoglobin;   // Hg Value
    float gfr;  // GFR Value
    float bnp;  // BNP Value
    boolean med;    // Step 13, True if patient should be excluded
    boolean diu; //Diuretics condition incl 4  
    int BPmedNo;    // Number of BP medications patient is receiving 
    
    public labPatient(){
        this.id  = null;
        this.bnp = -1;
        this.gfr = -1;
        this.Hemoglobin = -1;   
        this.med = false;
        this.diu = false;
        this.BPmedNo = 0;
    }
    
    public void labPrint(){
        System.out.println(this.id+ "/" + this.Hemoglobin + "/" + this.gfr + "/" + this.bnp + "/" + this.med);
    }
    
}

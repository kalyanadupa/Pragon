/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sax.aprser;

/**
 *
 * @author aka324
 */
public class labPatient {
    String id;
    float Hemoglobin;
    float gfr;
    float bnp;
    boolean med;
    
    public labPatient(){
        this.id  = null;
        this.bnp = -1;
        this.gfr = -1;
        this.Hemoglobin = -1;   
        this.med = false;
    }
    
    public void labPrint(){
        System.out.println(this.id+ "/" + this.Hemoglobin + "/" + this.gfr + "/" + this.bnp + "/" + this.med);
    }
    
}

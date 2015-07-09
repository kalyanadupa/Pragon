/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sax.parser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import org.joda.time.DateTime;
import org.joda.time.Interval;

/**
 *
 * @author aka324
 */
public class testDate {
    
    public long nMonth(String strDate) throws ParseException{
        Date dateStr = new Date();
        Date dNow = new Date();
        if(strDate.contains("T")){
            String[] tempTok = strDate.split("T");
            strDate = tempTok[0];
        }
            
        org.joda.time.Duration period = null ;
        try {
            
            
            SimpleDateFormat formatter;
            if(checkFormat("yyyy-M-d",strDate)){
                formatter = new SimpleDateFormat("yyyy-M-d");
                dateStr = formatter.parse(strDate);
            }    
            else if(checkFormat("M/d/yyyy",strDate)){
                formatter = new SimpleDateFormat("M/d/yyyy");
                dateStr = formatter.parse(strDate);
            }
            else if(checkFormat("yyyy-MM-dd",strDate)){
                formatter = new SimpleDateFormat("yyyy-MM-dd");
                dateStr = formatter.parse(strDate);
            }    
            else if(checkFormat("MM/dd/yyyy",strDate)){
                formatter = new SimpleDateFormat("MM/dd/yyyy");
                dateStr = formatter.parse(strDate);
            }
            else{
                System.out.println("** Error in Date Parse " + strDate);
                formatter = new SimpleDateFormat("M/d/yyyy");
                dateStr = formatter.parse("01/01/1980"); // Excluding the patients cases in which dates are not available
            }
            DateTime dt = new DateTime(dateStr);
            DateTime dtNow = new DateTime(dNow);
            Interval interval = new Interval(dt, dtNow);
            period = interval.toDuration();
            
        } catch (ParseException parseException) {
            System.out.println("** Error in Date Parse " + strDate);
        }
        catch(IllegalArgumentException e){
            System.out.println("start date / End date"+ dateStr+ "/" + dNow);
        }
        if(period == null)
            return 432;
        else
            return period.getStandardDays() / 30;
    }
    
    
    
    
    public boolean checkFormat(String format, String value) {
        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            date = sdf.parse(value);
            if (!value.equals(sdf.format(date))) {
                date = null;
            }
        } catch (ParseException ex) {
//            ex.printStackTrace();
        }
        return date != null;
    }

    
    
    public static void main(String argsv[]) throws ParseException{
        testDate td = new testDate();
//        System.out.println(nMonth("546578"));
//        System.out.println("2012-08-23T00:00:00" + checkFormat("M/d/yyyy","2012-08-23"));
        
    }
    
}

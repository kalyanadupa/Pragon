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
        org.joda.time.Duration period = null ;
        try {
//            SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date dateStr = new Date();
            Date dNow = new Date();
            dateStr = formatter.parse(strDate);
            System.out.println(dateStr);
            DateTime dt = new DateTime(dateStr);
            DateTime dtNow = new DateTime(dNow);
            Interval interval = new Interval(dt, dtNow);
            period = interval.toDuration();

//        long months = dateStr.getTime() - dNow.getTime();
//        Calendar c = Calendar.getInstance();
//
//        c.setTimeInMillis(months);
//        int mYear = c.get(Calendar.YEAR);
//        int mMonth = c.get(Calendar.MONTH);
//        int mDay = c.get(Calendar.DAY_OF_MONTH);
            return period.getStandardDays() / 30;
        } catch (ParseException parseException) {
            System.out.println("** Error in Date Parse " + strDate);
            SimpleDateFormat formatter = new SimpleDateFormat("mm/dd/YYYY");
            Date dateStr = new Date();
            Date dNow = new Date();
            dateStr = formatter.parse("1/1/1980");
            
            DateTime dt = new DateTime(dateStr);
            DateTime dtNow = new DateTime(dNow);
            Interval interval = new Interval(dt, dtNow);
            period = interval.toDuration();
            return period.getStandardDays() / 30;
        }
    }
    
    public static void main(String argsv[]) throws ParseException{
        testDate td = new testDate();
        System.out.println(td.nMonth("2012-08-23T00:00:00"));
    }
    
}

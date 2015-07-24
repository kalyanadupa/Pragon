
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author aka324
 */
public class regextest {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Pattern p = Pattern.compile("[^\\w]+heart\\s+failure[^\\w]+");
        Matcher m = p.matcher(" heart failUre ");
        if (m.find()) {
            System.out.println("yes");
        }
    }
}


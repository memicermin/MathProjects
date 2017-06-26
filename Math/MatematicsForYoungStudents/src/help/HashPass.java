/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package help;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.swing.JOptionPane;

/**
 *
 * @author Enver
 */
public class HashPass {
    
    /**
     * Turns inputed string into a new MD5 version using MessageDigest java class.
     *
     * @param password <code>String</code> type value of user inputed password
     * @return <code>String</code> type value of password hashed with MD5 that will go to database
     */
    public static String getEncriptedPasswordMD5(String password) {

        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(password.getBytes(), 0, password.length());
            String result = new BigInteger(1, md5.digest()).toString(16);
            md5.reset();
            return result;
        } catch (NoSuchAlgorithmException e) {
            //Logger.error("Could not encript password", e.getMessage());
            JOptionPane.showInternalMessageDialog(null, e.toString());
        }
        return "INVALID PASSWORD";
    }
}

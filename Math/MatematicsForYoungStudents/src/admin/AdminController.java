/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin;

import help.HashPass;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JOptionPane;
import jdk.nashorn.internal.ir.BreakNode;
import mathematicsforyoungstudents.WelcomeFrame;

/**
 *
 * @author Enver
 */
public class AdminController {

    static String userHome = System.getProperty("user.home");
    static String extensionFolderPath = "\\Documents\\mathforyoungstudents";
    static String extensionFilePath = "\\Documents\\mathforyoungstudents\\key.txt";

    static String fileLocation = userHome + extensionFilePath;
    static String folderLocation = userHome + extensionFolderPath;

    public static boolean folderExsist() {
        boolean exist = false;
        File folder = new File(folderLocation);
        if (!folder.exists()) {
            if (folder.mkdirs()) {
                exist = true;
            } else {
                exist = false;
            }
            folder.mkdir();
        } else {
            exist = true;
        }
        return exist;
    }

    public static boolean fileExsist() throws IOException {
        boolean exist = false;
        File file = new File(fileLocation);
        if (!file.exists()) {
            if (file.createNewFile()) {
                exist = true;
            }
        } else {
            exist = true;
        }
        return exist;
    }

    public static String getAdminsUsernameAndPass(){
        String line = null;
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileLocation));
            line = br.readLine();
            br.close();
        } catch (IOException ex) {
            
        }
        return line;
    }
    
    /**
     * This method compares username and password which are entered in the log
     * in window; If in the archive exist registered admin and if the password
     * and username are authentic the method returns true; If in the archive do
     * not exist registered admin, the method writes new admin in the archive
     * (username and password) and returns true;
     *
     * In all other cases the method returns false;
     *
     * @param username
     * @param password
     * @return true or false;
     */
    static boolean isOkPass(String username, String password, AdminLogin adminLogin) {
        //Check username
        if (password.equals(username)) {
            JOptionPane.showMessageDialog(null, "Password can not be the same as the username");
            return false;
        }
        //Check password lenght
        if (password.length() < 6) {
            JOptionPane.showMessageDialog(null, "Password must be longer than 5");
            return false;
        }
        //Taking admin data from archive
        String line = null;
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileLocation));
            line = br.readLine();
            br.close();
        } catch (IOException ex) {
            return false;
        }
        //Check entered log in data and archive admin data
        if (line != null) {
            String[] data = line.split(" ");
            String arhivUsername = data[0];
            String arhivPass = data[1];
            if (!arhivUsername.equals(username)) {
                int dialogResult = JOptionPane.showConfirmDialog(null, "Username ERROR\n Do you want to change username");
                if (dialogResult == JOptionPane.YES_OPTION) {
                    adminLogin.setVisible(false);
                    new WelcomeFrame();
                    return false;
                } else {
                    return false;
                }
            }
            if (username.equals(arhivUsername) && HashPass.getEncriptedPasswordMD5(password).equals(arhivPass)) {
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Password is not correct");
                return false;
            }
        } else {
            //Writing new admin in archive
            String newText = username + " " + HashPass.getEncriptedPasswordMD5(password);
            BufferedWriter bw;
            FileWriter fw;
            try {
                fw = new FileWriter(fileLocation);
                bw = new BufferedWriter(fw);
                bw.write(newText);
                bw.close();
                return true;
            } catch (IOException e) {
                return false;
            }
        }
    }
}

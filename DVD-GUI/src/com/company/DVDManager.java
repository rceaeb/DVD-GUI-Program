package com.company;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * 	Program to display and modify a simple DVD collection
 */

public class DVDManager {

    public static void main(String[] args) {

        DVDUserInterface dlInterface;
        DVDCollection dl = new DVDCollection();
        boolean foundFile;
        do {
            String filename = JOptionPane.showInputDialog("Enter Name Of Data File To Load");
            foundFile = dl.loadData(filename);

            if(!foundFile){
                JOptionPane.showMessageDialog(null, "This File Does Not Exist!", "File Not Found", JOptionPane.INFORMATION_MESSAGE);
            }
        }while(!foundFile);
        dlInterface = new DVDGUI(dl);
        dlInterface.processCommands();
        System.exit(0);

    }

}

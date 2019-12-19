package com.company;

import com.company.DVDCollection;
import com.company.DVDUserInterface;
import com.company.DVD;

import javax.swing.*;
import java.awt.*;

/**
 *  This class is an implementation of DVDUserInterface
 *  that uses JOptionPane to display the menu of command choices.
 */

public class DVDGUI implements DVDUserInterface {

    private DVDCollection dvdlist;

    public DVDGUI(DVDCollection dl)
    {
        dvdlist = dl;
    }

    public void processCommands()
    {
        String[] commands = {"Add/Modify DVD",
                "Remove DVD",
                "Get DVDs By Rating",
                "Get Total Running Time",
                "Show List",
                "Edit List",
                "Display DVD Information",
                "Exit and Save"};

        int choice;

        do {
            choice = JOptionPane.showOptionDialog(null,
                    "Select a command",
                    "DVD Collection",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    commands,
                    commands[commands.length - 1]);

            switch (choice) {
                case 0: doAddOrModifyDVD(); break;
                case 1: doRemoveDVD(); break;
                case 2: doGetDVDsByRating(); break;
                case 3: doGetTotalRunningTime(); break;
                case 4: doShowList(); break;
                case 5: doEdit(); break;
                case 6: doDisplay(); break;
                case 7: doSave(); break;
                default:  // do nothing
            }

        } while (choice != commands.length-1);
        System.exit(0);
    }

    private void doAddOrModifyDVD() {

        // Request the title
        String title = JOptionPane.showInputDialog("Enter title");
        if (title == null) {
            return;		// dialog was cancelled
        }
        title = title.toUpperCase();

        // Request the rating
        String rating = JOptionPane.showInputDialog("Enter rating for " + title);
        if (rating == null) {
            return;		// dialog was cancelled
        }
        rating = rating.toUpperCase();

        // Request the running time
        String time = JOptionPane.showInputDialog("Enter running time for " + title);
        if (time == null) {
            return;
        }
        // Add or modify the DVD (assuming the rating and time are valid
        dvdlist.addOrModifyDVD(title, rating, time);
    }

    private void doRemoveDVD() {

        // Request the title
        String title = JOptionPane.showInputDialog("Enter title");
        if (title == null) {
            return;		// dialog was cancelled
        }
        title = title.toUpperCase();

        // Remove the matching DVD if found
        dvdlist.removeDVD(title);
    }

    private void doGetDVDsByRating() {

        // Request the rating
        String rating = JOptionPane.showInputDialog("Enter rating");
        if (rating == null) {
            return;		// dialog was cancelled
        }
        rating = rating.toUpperCase();

        String results = "DVDs With Rating: " + rating + "\n" + dvdlist.getDVDsByRating(rating);
        JOptionPane.showMessageDialog(null,results,"Get DVDs By Rating",JOptionPane.INFORMATION_MESSAGE);
    }

    private void doGetTotalRunningTime() {

        int total = dvdlist.getTotalRunningTime();
        String displayRunningTime = "Total Running Time: " + total + " minutes";

        JOptionPane.showMessageDialog(null,displayRunningTime,"Total Running Time", JOptionPane.INFORMATION_MESSAGE);
    }

    private void doShowList(){
        int numDVD = dvdlist.getNumDvds();
        if(numDVD > 0) {
            DVD[] copyArr = dvdlist.getDvdArray();
            String [] columnNames = {"DVD No.", "Title", "Rating", "Running Time"};
            String[][] data = new String[numDVD][4];

            for(int i = 0; i < numDVD; i++){
                data[i][0] = Integer.toString(i + 1);
                data[i][1] = copyArr[i].getTitle();
                data[i][2] = copyArr[i].getRating();
                data[i][3] = Integer.toString(copyArr[i].getRunningTime());
            }
            JFrame frame = new JFrame("List of DVDS");
            JTable dvdTable = new JTable(data, columnNames);
            frame.add(new JScrollPane(dvdTable));
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);
        }else{
            JOptionPane.showMessageDialog(null, "The List Is Currently Empty!", "Empty List", JOptionPane.INFORMATION_MESSAGE);
        }
   }

    private void doEdit(){
        String dvdNo = JOptionPane.showInputDialog("Enter DVD No. (SEE DVD LIST)");
        if(dvdNo == null)
            return;
        int numDVD = dvdlist.getNumDvds();
        int dvdNumber = 0;
        try {
            dvdNumber = Integer.parseInt(dvdNo);
        }catch (Exception e){
            System.out.println("Invalid Selection");
        }
        if (dvdNumber > 0 && dvdNumber <= numDVD){
            // Edit Title: If left blank, original title will be left alone
            String title = JOptionPane.showInputDialog("Enter Title: If Empty, Title Will Be Unaffected");
            // If User Cancels, return to main menu
            if(title == null)
                return;
            title = title.toUpperCase();
            String rating = JOptionPane.showInputDialog("Enter Rating (If Empty, Rating Will Be Unaffected");
            // If User Cancels, return to main menu
            if(rating == null)
                return;
            rating = rating.toUpperCase();
            String runningTime = JOptionPane.showInputDialog("Enter Running Time (If Empty, Running Time Will Be Unaffected");
            int runningINT = 0;
            if(runningTime.equals("")){
                runningINT = 0;
            }else{
                try {
                    runningINT = Integer.parseInt(runningTime);
                }catch (Exception e){
                    JOptionPane.showMessageDialog(null, "This is not a valid selection!", "ERROR", JOptionPane.INFORMATION_MESSAGE);
                }
            }
            dvdNumber -= 1;
            dvdlist.editDVD(dvdNumber, title, rating, runningINT);
        }else{
            JOptionPane.showMessageDialog(null, "This is not a valid selection!", "ERROR", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void doDisplay(){
        String dvdNo = JOptionPane.showInputDialog("Enter DVD No. To Display Information (SEE DVD LIST)");
        if(dvdNo == null)
            return;
        int dvdNumber = 0;
        try {
            dvdNumber = Integer.parseInt(dvdNo);
        }catch (Exception e){
            System.out.println("Invalid Selection");
        }
        if(dvdNumber > 0 && dvdNumber <= dvdlist.getNumDvds()){
            String DVDinformation = "Information for DVD No. " + dvdNo + "" + "\n";
            DVDinformation += "Title: " + dvdlist.getDvdArray()[dvdNumber -1].getTitle() + "\n";
            DVDinformation += "Rating: " + dvdlist.getDvdArray()[dvdNumber -1].getRating() + "\n";
            DVDinformation += "Running Time: " + dvdlist.getDvdArray()[dvdNumber -1].getRunningTime() + " minutes\n";
            JFrame frame = new JFrame("Display Information");
            JTextArea text = new JTextArea(5,20);
            text.setText(DVDinformation);
            JScrollPane scrollPane = new JScrollPane(text);
            frame.getContentPane().add(scrollPane);
            // Add Image
            ImageIcon icon;
            if(dvdlist.getDvdArray()[dvdNumber - 1].getRating().equals("PG")){
               icon = new ImageIcon("src/com/company/PG.jpg");
            }else if(dvdlist.getDvdArray()[dvdNumber - 1].getRating().equals("PG-13")){
                icon = new ImageIcon("src/com/company/PG13.jpg");
            }else{
                icon = new ImageIcon("src/com/company/R.jpg");
            }
            JLabel label = new JLabel();
            label.setIcon(icon);
            frame.add(label);
            frame.setLayout(new FlowLayout());
            frame.setSize(500,500);
            frame.setVisible(true);
        }else{
            JOptionPane.showMessageDialog(null, "This is not a valid selection!", "ERROR", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void doSave() {

        dvdlist.save();
    }
}

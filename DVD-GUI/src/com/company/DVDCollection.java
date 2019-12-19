package com.company;

import javax.swing.*;
import java.io.*;

public class DVDCollection {

    // Data fields

    /** The current number of DVDs in the array */
    private int numdvds;

    /** The array to contain the DVDs */
    private DVD[] dvdArray;

    /** The name of the data file that contains dvd data */
    private String sourceName = "dvddata.txt";

    /** Boolean flag to indicate whether the DVD collection was
     modified since it was last saved. */
    private boolean modified;

    /**
     *  Constructs an empty directory as an array
     *  with an initial capacity of 7. When we try to
     *  insert into a full array, we will double the size of
     *  the array first.
     */
    public DVDCollection() {
        numdvds = 0;
        dvdArray = new DVD[7];
    }

    public String toString() {
        String collectionInfo = "numdvds = " + numdvds + "\n" + "dvdArray.length = " + dvdArray.length + "\n";

        for (int i = 0; i < numdvds; i++)
            collectionInfo += "dvdArray[" + i + "] = " + dvdArray[i].toString() + "\n";

        return collectionInfo;
    }

    public void addOrModifyDVD(String title, String rating, String runningTime) {
        boolean matchFound = false;
        boolean ratingCheck = false;
        try {
            int stringNum = Integer.parseInt(runningTime);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid Running Time Input!", "Running Time Error", JOptionPane.INFORMATION_MESSAGE);

            return;
        }

        if (rating.equals("PG-13") || rating.equals("R") || rating.equals("PG")) {
            ratingCheck = true;
        }else {
            JOptionPane.showMessageDialog(null, "Invalid Rating! DVD Will Not Be Added!", "Rating Error", JOptionPane.INFORMATION_MESSAGE);

        }

        if (ratingCheck == true) {
            for (int i = 0; i < numdvds; i++) {
                if (dvdArray[i].getTitle().equals(title)) {
                    dvdArray[i].setRunningTime(Integer.parseInt(runningTime));
                    dvdArray[i].setRating(rating);
                    matchFound = true;
                }
            }
            if (matchFound == false) {
                if (numdvds == dvdArray.length) {
                    DVD[] newArray = new DVD[dvdArray.length * 2];
                    for (int i = 0; i < dvdArray.length; i++) {
                        newArray[i] = dvdArray[i];
                    }
                    this.dvdArray = newArray;
                }
                // Create new DVD
                DVD tempDVD = new DVD(title, rating, Integer.parseInt(runningTime));
                dvdArray[numdvds] = tempDVD;
                this.numdvds++;
            }
        }
    }
    public void removeDVD(String title) {
        boolean foundDVD = false;
        int count = 0;
        for(int i = 0; i < numdvds; i++){
            if(this.dvdArray[i].getTitle().equals(title)){
                foundDVD = true;
                this.numdvds--;
                for(int j = count; j < numdvds; j++){
                    dvdArray[j] = dvdArray[j+1];
                }
            }
            count++;
        }
        if(foundDVD == false)
            JOptionPane.showMessageDialog(null, "No Such DVD Exists!", "No Match!", JOptionPane.INFORMATION_MESSAGE);
        else
            JOptionPane.showMessageDialog(null, "The Movie " + title + " Was Removed From The DVD List!", "Remove Successful", JOptionPane.INFORMATION_MESSAGE);

    }

    public String getDVDsByRating(String rating) {

        String foundDVDS = "";

        for(int i = 0; i < numdvds; i++){
            if(dvdArray[i].getRating().equals(rating)){
                foundDVDS += dvdArray[i].toString() + "\n";
            }
        }

        return foundDVDS;
    }

    public int getTotalRunningTime() {

        int totalRuningTime = 0;

        for(int i = 0; i < numdvds; i++){
            totalRuningTime += dvdArray[i].getRunningTime();
        }

        return totalRuningTime;
    }

    public boolean loadData(String filename)  {
        // Get Path of File
        String dvdFilePath = System.getProperty("user.dir");
        boolean foundFile = true;

        try {
            BufferedReader fileContents = new BufferedReader(new FileReader(dvdFilePath + "\\src\\com\\company\\" + filename));
            try{
                String fileDVD = fileContents.readLine();
                String[] dvdComponents = fileDVD.split(",");
                addOrModifyDVD(dvdComponents[0], dvdComponents[1], dvdComponents[2]);

                while(fileDVD != null) {
                    fileDVD = fileContents.readLine();
                    dvdComponents = fileDVD.split(",");
                    addOrModifyDVD(dvdComponents[0], dvdComponents[1], dvdComponents[2]);
                }
            }catch(IOException e){
                System.out.println(e);
            }catch(NullPointerException e){ }
            fileContents.close();
        }catch (FileNotFoundException e){
            System.out.println(e);
            foundFile = false;
        }catch(IOException e){}
        catch(ArrayIndexOutOfBoundsException e){}

        return foundFile;
    }

    public void save() {
        String dvdFilePath = System.getProperty("user.dir") + "\\src\\com\\company\\dvddata.txt";

        try {
            byte [] dvdBytes;
            String allDVDs = "";
            String individualDVD ="";
            OutputStream output = new FileOutputStream(dvdFilePath);
            for(int i = 0; i < numdvds; i++) {
                individualDVD = dvdArray[i].toString();
                individualDVD = individualDVD.replace("min", "\n");
                individualDVD = individualDVD.replace("/",",");
                allDVDs += individualDVD;
            }
            dvdBytes = allDVDs.getBytes();
            output.write(dvdBytes);
            output.close();

        }catch(FileNotFoundException e){
            System.out.println(e);
        }catch(IOException e){}
    }

    public DVD[] getDvdArray(){
        return this.dvdArray;
    }

    public int getNumDvds(){return this.numdvds;}

    public void editDVD(int element, String title, String rating, int runningTime){
        if(element >= 0 && element < numdvds) {
            // Set title
            if (!title.equals("")) {
                dvdArray[element].setTitle(title, 0);
            }

            // Set rating
            if (rating.equals("PG-13") || rating.equals("R") || rating.equals("PG")) {
                dvdArray[element].setRating(rating);
            }else if(rating.equals("")){
                System.out.println("Rating Will Not Change!");
            }else{
                JOptionPane.showMessageDialog(null, "This Is Not A Valid Rating! The Rating Was Unchanged.", "Invalid Rating", JOptionPane.INFORMATION_MESSAGE);
            }

            // Set running time
            if(runningTime != 0)
                dvdArray[element].setRunningTime(runningTime);
        }
    }
}
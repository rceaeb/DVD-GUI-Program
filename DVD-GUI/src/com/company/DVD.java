package com.company;

public class DVD {

    // Fields:

    private String title;		// Title of this DVD
    private String rating;		// Rating of this DVD
    private int runningTime;	// Running time of this DVD in minutes


    // Constructors
    public DVD(){
        this.title = "";
        this.rating = "";
        this.runningTime = 0;
    }
    public DVD(String newTitle) {
        this.title = newTitle;
    }

    public DVD(int newRunningTime) {
        this.runningTime = newRunningTime;
    }

    public DVD(String newTitle, String newRating) {
        this.title = newTitle;
        this.rating = newRating;
    }

    public DVD(String newTitle, int newRunningTime) {
        this.title = newTitle;
        this.runningTime = newRunningTime;
    }

    public DVD(int newRunningTime, String newRating){
        this.runningTime = newRunningTime;
        this.rating = newRating;
    }
    public DVD(String newTitle, String newRating, int newRunningTime) {
        this.title = newTitle;
        this.rating = newRating;
        this.runningTime = newRunningTime;
    }

    public String getTitle()
    {
        return this.title.toUpperCase();
    }

    public String getRating()
    {
        return this.rating;
    }

    public int getRunningTime()
    {
        return this.runningTime;
    }

    public void setTitle(String newTitle, int nullPar) {
        this.title = newTitle;
    }

    public void setRating(String newRating) {
        this.rating = newRating;
    }

    public void setRunningTime(int newRunningTime) {
        this.runningTime = newRunningTime;
    }

    public String toString() {
        return this.title + "/" + this.rating + "/" + this.runningTime + "min";
    }
}

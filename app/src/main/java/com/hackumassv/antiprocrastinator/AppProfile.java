package com.hackumassv.antiprocrastinator;

import java.util.ArrayList;

/**
 * Created by Travis on 11/4/2017.
 */

public class AppProfile implements Comparable<AppProfile>{

    private String name;
    private ArrayList<TimeEvent> timeList;
    private long timeBeforeApp;
    private long totalTime;
    //Will be -1 if no time is currently proposed.
    private long proposedStartTime;


    public AppProfile(String name){
        this.name=name;
        timeList = new ArrayList<TimeEvent>();
        timeBeforeApp = 0;
        totalTime = 0;
        proposedStartTime = -1;
    }

    public void addTimeEvent(long startTime, long endTime){
        TimeEvent newEvent = new TimeEvent(startTime,endTime);
        timeList.add(newEvent);
    }

    public int timeEntrySize(){
        return timeList.size();
    }

    public void setTimeBeforeApp(long timeBeforeApp){
        this.timeBeforeApp = timeBeforeApp;
    }

    public String getName(){
        return name;
    }

    public int compareTo(AppProfile comparingProfile){
        return getName().compareTo(comparingProfile.getName());
    }

    public void proposeStartTime(long proposedStartTime){
        this.proposedStartTime = proposedStartTime;
    }

    public void proposeEndTime(long proposedEndTime){
        addTimeEvent(proposedStartTime, proposedEndTime);
        proposedStartTime = -1;
    }

    public String toString(){
        String returnString = "Name: " + name;
        for(TimeEvent event : timeList){
            returnString+= "\n  " + event.toString();
        }
        return returnString;
    }



}

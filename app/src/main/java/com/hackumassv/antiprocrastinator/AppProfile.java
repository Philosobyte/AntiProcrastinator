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

    public AppProfile(String name){
        this.name=name;
        timeList = new ArrayList<TimeEvent>();
        timeBeforeApp = 0;
        totalTime = 0;
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



}

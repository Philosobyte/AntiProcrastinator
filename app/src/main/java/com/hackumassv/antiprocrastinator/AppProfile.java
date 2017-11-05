package com.hackumassv.antiprocrastinator;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Travis on 11/4/2017.
 */

public class AppProfile implements Comparable<AppProfile>, Iterable<TimeEvent>{

    private String name;
    private ArrayList<TimeEvent> timeList;
    private long timeBeforeApp;
    private long totalTime;
    //Will be -1 if no time is currently proposed.
    private long proposedStartTime;
    private Context context;

    public AppProfile(Context context, String name){
        this.name=name;
        this.context=context;
        timeList = new ArrayList<TimeEvent>();
        timeBeforeApp = 0;
        totalTime = 0;
        proposedStartTime = -1;
    }

    public void addTimeEvent(long startTime, long endTime){
        TimeEvent newEvent = new TimeEvent(startTime,endTime);
        timeList.add(newEvent);
    }

    public int timeEntrySize() {
        return timeList.size();
    }

    public void setTimeBeforeApp(long timeBeforeApp) {
        this.timeBeforeApp = timeBeforeApp;
    }

    public String getName() {
        return name;
    }

    public int compareTo(AppProfile comparingProfile) {
        return getName().compareTo(comparingProfile.getName());
    }
    public String returnAppName() {

        PackageManager pm = context.getPackageManager();
        ApplicationInfo ai;
        try{
            ai = pm.getApplicationInfo(name, 0); }
        catch(PackageManager.NameNotFoundException e)
            {
                return "Horse with no name";
            }

        final String applicationName = (String) (pm.getApplicationLabel(ai));
        return applicationName;
    }


    public void proposeStartTime(long proposedStartTime) {
        this.proposedStartTime = proposedStartTime;
    }

    public void proposeEndTime(long proposedEndTime) {
        if (proposedStartTime > 0) {
            addTimeEvent(proposedStartTime, proposedEndTime);
            proposedStartTime = -1;
        }
    }

    public String toString() {
        String returnString = "Name: " + name;
        for(TimeEvent event : timeList){
            returnString+= "\n  " + event.toString();
        }
        return returnString;
    }


    public Drawable returnAppIcon() {
        PackageManager pm=context.getPackageManager();
        Drawable icon;
        try{
            icon= pm.getApplicationIcon(name); }
        catch(PackageManager.NameNotFoundException e)
        {
            icon = null;
        }

        return icon;
    }

    public Iterator iterator() {
        return timeList.iterator();
    }

}

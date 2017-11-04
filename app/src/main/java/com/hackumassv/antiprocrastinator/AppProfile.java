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
    private Context context;

    public AppProfile(Context context, String name){
        this.name=name;
        this.context=context;
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
    public String returnAppName()
    {

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

    public Drawable returnAppIcon()
    {
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

    public Iterator iterator()
    {
        return timeList.iterator();
    }

}

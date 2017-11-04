package com.hackumassv.antiprocrastinator;

import android.app.usage.UsageEvents;
import android.app.usage.UsageStatsManager;
import android.content.Context;

import java.util.ArrayList;

/**
 * Created by Travis on 11/4/2017.
 */

public class ProfileList {

    public static final long DELTA_DAY = 7*24*60*1000;
    public static final long DELTA_WEEK = 4*7*24*60*1000;
    public static final long DELTA_MONTH = 6*4*7*24*60*1000;
    public static final long DELTA_YEAR = 2*365*24*60*1000;


    private ArrayList<AppProfile> profileList;
    private long firstProcessedTime;
    private long lastProcessedTime;


    public ProfileList(){
        profileList = new ArrayList<AppProfile>();
        firstProcessedTime = System.currentTimeMillis();

    }

    //Returns false if already exists
    public boolean addProfile(String name){
        AppProfile newProfile = new AppProfile(name);
        if (profileList.contains(profileList)){
            return false;
        }
        profileList.add(newProfile);
        return true;
    }

    public void processApps(Context context){
        long time = System.currentTimeMillis();
        UsageStatsManager statsManager = context.getSystemService(UsageStatsManager.class);
        UsageEvents usageEvents = statsManager.queryEvents(lastProcessedTime, time);
        UsageEvents.Event event = new UsageEvents.Event();
        while(usageEvents.hasNextEvent()){
            usageEvents.getNextEvent(event);
            AppProfile eventApp = getProfile(event.getPackageName());
            int eventType = event.getEventType();
            long timeStamp = event.getTimeStamp();
            if (eventType == UsageEvents.Event.MOVE_TO_FOREGROUND) { //Pulled to Foreground
                eventApp.proposeStartTime(timeStamp);
            }
            else if (eventType == UsageEvents.Event.MOVE_TO_BACKGROUND) {
                eventApp.proposeEndTime(timeStamp);
            }
        }
    }

    public AppProfile getProfile(String name){
        AppProfile toFind = new AppProfile(name);
        int index = profileList.indexOf(toFind);
        return profileList.get(index);
    }




}

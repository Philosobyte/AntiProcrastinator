package com.hackumassv.antiprocrastinator;


import android.app.usage.UsageEvents;
import android.app.usage.UsageStatsManager;

import android.content.Context;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Travis on 11/4/2017.
 */

public class ProfileList implements Iterable<ProfileList>{

    public static final long DELTA_DAY = 7*24*60*1000;
    public static final long DELTA_WEEK = 4*7*24*60*1000;
    public static final long DELTA_MONTH = 6*4*7*24*60*1000;
    public static final long DELTA_YEAR = 2*365*24*60*1000;
    private ArrayList<AppProfile> profileList;
    private long firstProcessedTime;
    private long lastProcessedTime;
    private Context context;


    public ProfileList(Context context){
        profileList = new ArrayList<AppProfile>();
        firstProcessedTime = System.currentTimeMillis();
        lastProcessedTime = 0;
        this.context=context;
    }

    //Returns false if already exists
    public boolean addProfile(String name) {
        AppProfile newProfile = new AppProfile(context, name);
        if (profileList.contains(profileList)) {
            return false;
        }
        profileList.add(newProfile);
        return true;
    }

    public void processApps(){
        long time = System.currentTimeMillis();
        UsageStatsManager statsManager = context.getSystemService(UsageStatsManager.class);
        //"time-DEALTA_MONTH" should be lastprocessedTime
        UsageEvents usageEvents = statsManager.queryEvents(time-DELTA_MONTH, time);
        UsageEvents.Event event = new UsageEvents.Event();
        while(usageEvents.hasNextEvent()){
            usageEvents.getNextEvent(event);
            AppProfile eventProfile = null;
            for(AppProfile iterProfile : profileList){
                if (iterProfile.getName().compareTo(event.getPackageName()) == 0){
                    eventProfile = iterProfile;
                    break;
                }
            }
            if (eventProfile == null){
                eventProfile = new AppProfile(context, event.getPackageName());
                profileList.add(eventProfile);
            }


            int eventType = event.getEventType();
            long timeStamp = event.getTimeStamp();
            if (eventType == UsageEvents.Event.MOVE_TO_FOREGROUND) { //Pulled to Foreground
                eventProfile.proposeStartTime(timeStamp);
            }
            else if (eventType == UsageEvents.Event.MOVE_TO_BACKGROUND) {
                eventProfile.proposeEndTime(timeStamp);
            }
        }


    }


    public AppProfile getProfile(String name){
        AppProfile toFind = new AppProfile(context,name);
        int index = profileList.indexOf(toFind);
        return profileList.get(index);
    }

    public Iterator iterator() {
        return profileList.iterator();
    }

    public String toString() {
        String returnString = "";
        for(AppProfile profile : profileList){

            returnString+="\n" + profile.toString();
        }
        return returnString;
    }







}

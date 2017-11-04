package com.hackumassv.antiprocrastinator;

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






}

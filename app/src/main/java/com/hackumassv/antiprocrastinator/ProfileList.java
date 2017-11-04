package com.hackumassv.antiprocrastinator;

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


    public ProfileList(){
        profileList = new ArrayList<AppProfile>();
        firstProcessedTime = System.currentTimeMillis();
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


    public Iterator iterator()
    {
        return profileList.iterator();
    }






}

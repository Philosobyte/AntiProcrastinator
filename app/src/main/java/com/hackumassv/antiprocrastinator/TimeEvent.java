package com.hackumassv.antiprocrastinator;

/**
 * Created by Travis on 11/4/2017.
 * An object that holds the time an app was opened (pulled to foreground),
 * and when it was closed (pushed to background).
 */

public class TimeEvent {

    private long startTime;
    private long totalTime;
    private long endTime;

    public TimeEvent(long startTime, long endTime){
        this.startTime = startTime;
        this.endTime = endTime;
        totalTime = endTime - startTime;
    }

    public long getStartTime(){
        return startTime;
    }

    public long getEndTime(){
        return endTime;
    }
    public long getTotalTime(){
        return totalTime;
    }

    public String toString(){
        return startTime + " " + endTime;
    }

}

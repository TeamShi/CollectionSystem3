package com.teamshi.collectionsystem3.datastructure;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Created by Alfred on 16/7/14.
 */
public class Rig implements UIMethods, Serializable{
    protected String classPeopleCount;

    protected Calendar date;
    protected Calendar startTime;
    protected Calendar endTime;

    public Rig(String classPeopleCount, Calendar date, Calendar startTime, Calendar endTime) {
        this.classPeopleCount = classPeopleCount;

        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public Rig deepCopy() {
        return null;
    }

    public String getClassPeopleCount() {
        return classPeopleCount;
    }

    public void setClassPeopleCount(String classPeopleCount) {
        this.classPeopleCount = classPeopleCount;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public Calendar getStartTime() {
        return startTime;
    }

    public void setStartTime(Calendar startTime) {
        this.startTime = startTime;
    }

    public Calendar getEndTime() {
        return endTime;
    }

    public void setEndTime(Calendar endTime) {
        this.endTime = endTime;
    }
}

interface UIMethods {
    public Rig deepCopy();
}

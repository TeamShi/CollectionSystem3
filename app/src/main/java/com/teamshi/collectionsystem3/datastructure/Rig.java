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

    protected int lastPipeNumber;
    protected Calendar lastRigEndTime;
    protected double lastRockCorePipeLength;
    protected double lastAccumulatedMeterageLength;
    protected int lastMaxRigRockCoreIndex;

    protected String lastRockName;
    protected String lastRockColor;
    protected String lastRockSaturation;

    public Rig(String classPeopleCount, Calendar date, Calendar startTime, Calendar endTime) {
        this.classPeopleCount = classPeopleCount;

        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;

        this.lastPipeNumber = 0;
        this.lastRigEndTime = Calendar.getInstance();
        this.lastRockCorePipeLength = 0;
        this.lastAccumulatedMeterageLength = 0;
        this.lastMaxRigRockCoreIndex = 0;
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

    public int getLastPipeNumber() {
        return lastPipeNumber;
    }

    public void setLastPipeNumber(int lastPipeNumber) {
        this.lastPipeNumber = lastPipeNumber;
    }

    public Calendar getLastRigEndTime() {
        return lastRigEndTime;
    }

    public void setLastRigEndTime(Calendar lastRigEndTime) {
        this.lastRigEndTime = lastRigEndTime;
    }

    public double getLastRockCorePipeLength() {
        return lastRockCorePipeLength;
    }

    public void setLastRockCorePipeLength(double lastRockCorePipeLength) {
        this.lastRockCorePipeLength = lastRockCorePipeLength;
    }

    public double getLastAccumulatedMeterageLength() {
        return lastAccumulatedMeterageLength;
    }

    public void setLastAccumulatedMeterageLength(double lastAccumulatedMeterageLength) {
        this.lastAccumulatedMeterageLength = lastAccumulatedMeterageLength;
    }

    public int getLastMaxRigRockCoreIndex() {
        return lastMaxRigRockCoreIndex;
    }

    public void setLastMaxRigRockCoreIndex(int lastMaxRigRockCoreIndex) {
        this.lastMaxRigRockCoreIndex = lastMaxRigRockCoreIndex;
    }

    public String getLastRockName() {
        return lastRockName;
    }

    public void setLastRockName(String lastRockName) {
        this.lastRockName = lastRockName;
    }

    public String getLastRockColor() {
        return lastRockColor;
    }

    public void setLastRockColor(String lastRockColor) {
        this.lastRockColor = lastRockColor;
    }

    public String getLastRockSaturation() {
        return lastRockSaturation;
    }

    public void setLastRockSaturation(String lastRockSaturation) {
        this.lastRockSaturation = lastRockSaturation;
    }
}

interface UIMethods {
    public Rig deepCopy();
}

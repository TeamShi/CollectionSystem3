package com.teamshi.collectionsystem3.datastructure;

import java.util.Calendar;

/**
 * Created by Alfred on 16/9/7.
 */
public class OtherSamplingRig extends Rig {
    private String samplingRigType;

    public OtherSamplingRig(String classPeopleCount, Calendar date, Calendar startTime, Calendar endTime) {
        super(classPeopleCount, date, startTime, endTime);
        this.samplingRigType = samplingRigType;
    }

    public String getSamplingRigType() {
        return samplingRigType;
    }

    public void setSamplingRigType(String samplingRigType) {
        this.samplingRigType = samplingRigType;
    }

    @Override
    public Rig deepCopy() {
        OtherSamplingRig temp = new OtherSamplingRig(classPeopleCount, (Calendar) date.clone(), (Calendar) startTime.clone(), (Calendar) endTime.clone());

        temp.setLastPipeNumber(lastPipeNumber);
        temp.setLastRigEndTime((Calendar) lastRigEndTime.clone());
        temp.setLastRockCorePipeLength(lastRockCorePipeLength);
        temp.setLastMaxRigRockCoreIndex(lastMaxRigRockCoreIndex);

        temp.setLastRockName(lastRockName);
        temp.setLastRockColor(lastRockColor);
        temp.setLastRockSaturation(lastRockSaturation);

        return temp;
    }
}

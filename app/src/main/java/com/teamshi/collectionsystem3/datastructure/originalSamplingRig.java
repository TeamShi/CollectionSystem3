package com.teamshi.collectionsystem3.datastructure;

import java.util.Calendar;

/**
 * Created by Alfred on 16/9/7.
 */
public class OriginalSamplingRig extends SamplingRig {
    private String samplerType;

    public String getSamplerType() {
        return samplerType;
    }

    public void setSamplerType(String samplerType) {
        this.samplerType = samplerType;
    }

    public OriginalSamplingRig(String classPeopleCount, Calendar date,
                               Calendar startTime, Calendar endTime,
                               double drillToolTotalLength, double drillPipeRemainLength, double roundTripMeterageLength, double accumulatedMeterageLength,
                               int samplerDiameter, long samplerLength, String index, String samplerType, double startDepth, double endDepth, int count) {
        super(classPeopleCount, date, startTime, endTime,
                drillToolTotalLength, drillPipeRemainLength, roundTripMeterageLength, accumulatedMeterageLength,
                samplerDiameter, samplerLength, index, startDepth, endDepth, count);

        this.samplerType = samplerType;

    }

    @Override
    public Rig deepCopy() {
        OriginalSamplingRig temp = new OriginalSamplingRig(classPeopleCount, (Calendar) date.clone(), (Calendar) startTime.clone(), (Calendar) endTime.clone(),
                drillToolTotalLength, drillPipeRemainLength, roundTripMeterageLength, accumulatedMeterageLength,
                samplerDiameter, samplerLength, samplerType, index, startDepth, endDepth, count);

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

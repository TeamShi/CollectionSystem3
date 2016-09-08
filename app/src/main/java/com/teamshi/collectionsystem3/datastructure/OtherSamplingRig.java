package com.teamshi.collectionsystem3.datastructure;

import java.util.Calendar;

/**
 * Created by Alfred on 16/9/7.
 */
public class OtherSamplingRig extends SamplingRig {
    private String samplingRigType;

    public OtherSamplingRig(String classPeopleCount, Calendar date, Calendar startTime, Calendar endTime,
                            double drillToolTotalLength, double drillPipeRemainLength, double roundTripMeterageLength, double accumulatedMeterageLength,
                            int samplerDiameter, long samplerLength,
                            String samplerDrillType, int samplerDrillDiameter, double samplerDrillLength,
                            String index, double startDepth, double endDepth, int count, String samplingRigType) {
        super(classPeopleCount, date, startTime, endTime, drillToolTotalLength, drillPipeRemainLength, roundTripMeterageLength, accumulatedMeterageLength, samplerDiameter, samplerLength, samplerDrillType, samplerDrillDiameter, samplerDrillLength, index, startDepth, endDepth, count);
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
        OtherSamplingRig temp = new OtherSamplingRig(classPeopleCount, (Calendar) date.clone(), (Calendar) startTime.clone(), (Calendar) endTime.clone(),
                drillToolTotalLength, drillPipeRemainLength, roundTripMeterageLength, accumulatedMeterageLength,
                samplerPipeDiameter, samplerPipeLength, samplerDrillType, samplerDrillDiameter, samplerDrillLength, index, startDepth, endDepth, count, samplingRigType);

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

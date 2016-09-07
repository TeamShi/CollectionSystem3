package com.teamshi.collectionsystem3.datastructure;

import java.util.Calendar;

/**
 * Created by Alfred on 16/8/25.
 */
public class SamplingRig extends CalculatingRig {
    protected int samplerDiameter;
    protected long samplerLength;
    protected String index;
    protected double startDepth;
    protected double endDepth;
    protected int count;

    public SamplingRig(String classPeopleCount, Calendar date, Calendar startTime, Calendar endTime,
                       double drillToolTotalLength, double drillPipeRemainLength,
                       double roundTripMeterageLength, double accumulatedMeterageLength,
                       int samplerDiameter, long samplerLength,
                       String index,
                       double startDepth, double endDepth, int count) {
        super(classPeopleCount, date, startTime, endTime, drillToolTotalLength, drillPipeRemainLength, roundTripMeterageLength, accumulatedMeterageLength);
        this.samplerDiameter = samplerDiameter;
        this.samplerLength = samplerLength;
        this.index = index;
        this.startDepth = startDepth;
        this.endDepth = endDepth;
        this.count = count;
    }

    public int getSamplerDiameter() {
        return samplerDiameter;
    }

    public void setSamplerDiameter(int samplerDiameter) {
        this.samplerDiameter = samplerDiameter;
    }

    public long getSamplerLength() {
        return samplerLength;
    }

    public void setSamplerLength(long samplerLength) {
        this.samplerLength = samplerLength;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public double getStartDepth() {
        return startDepth;
    }

    public void setStartDepth(double startDepth) {
        this.startDepth = startDepth;
    }

    public double getEndDepth() {
        return endDepth;
    }

    public void setEndDepth(double endDepth) {
        this.endDepth = endDepth;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public Rig deepCopy() {
        SamplingRig temp = new SamplingRig(classPeopleCount, (Calendar) date.clone(), (Calendar) startTime.clone(), (Calendar) endTime.clone(),
                drillToolTotalLength, drillPipeRemainLength, roundTripMeterageLength, accumulatedMeterageLength,
                samplerDiameter, samplerLength, index, startDepth, endDepth, count);

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

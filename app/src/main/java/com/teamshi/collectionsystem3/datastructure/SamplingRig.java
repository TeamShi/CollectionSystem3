package com.teamshi.collectionsystem3.datastructure;

import java.util.Calendar;

/**
 * Created by Alfred on 16/8/25.
 */
public class SamplingRig extends CalculatingRig {
    protected int samplerPipeDiameter;
    protected double samplerPipeLength;
    protected String samplerDrillType;
    protected int samplerDrillDiameter;
    protected double samplerDrillLength;
    protected String index;
    protected double startDepth;
    protected double endDepth;
    protected int count;

    public SamplingRig(String classPeopleCount, Calendar date, Calendar startTime, Calendar endTime,
                       double drillToolTotalLength, double drillPipeRemainLength,
                       double roundTripMeterageLength, double accumulatedMeterageLength,
                       int samplerPipeDiameter, double samplerPipeLength,
                       String samplerDrillType, int samplerDrillDiameter, double samplerDrillLength,
                       String index,
                       double startDepth, double endDepth, int count) {
        super(classPeopleCount, date, startTime, endTime, drillToolTotalLength, drillPipeRemainLength, roundTripMeterageLength, accumulatedMeterageLength);
        this.samplerPipeDiameter = samplerPipeDiameter;
        this.samplerPipeLength = samplerPipeLength;
        this.samplerDrillType = samplerDrillType;
        this.samplerDrillLength = samplerDrillLength;
        this.samplerDrillDiameter = samplerDrillDiameter;
        this.index = index;
        this.startDepth = startDepth;
        this.endDepth = endDepth;
        this.count = count;
    }

    public int getSamplerPipeDiameter() {
        return samplerPipeDiameter;
    }

    public void setSamplerPipeDiameter(int samplerPipeDiameter) {
        this.samplerPipeDiameter = samplerPipeDiameter;
    }

    public double getSamplerPipeLength() {
        return samplerPipeLength;
    }

    public void setSamplerPipeLength(double samplerPipeLength) {
        this.samplerPipeLength = samplerPipeLength;
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

    public String getSamplerDrillType() {
        return samplerDrillType;
    }

    public void setSamplerDrillType(String samplerDrillType) {
        this.samplerDrillType = samplerDrillType;
    }

    public int getSamplerDrillDiameter() {
        return samplerDrillDiameter;
    }

    public void setSamplerDrillDiameter(int samplerDrillDiameter) {
        this.samplerDrillDiameter = samplerDrillDiameter;
    }

    public double getSamplerDrillLength() {
        return samplerDrillLength;
    }

    public void setSamplerDrillLength(double samplerDrillLength) {
        this.samplerDrillLength = samplerDrillLength;
    }

    @Override
    public Rig deepCopy() {
        SamplingRig temp = new SamplingRig(classPeopleCount, (Calendar) date.clone(), (Calendar) startTime.clone(), (Calendar) endTime.clone(),
                drillToolTotalLength, drillPipeRemainLength, roundTripMeterageLength, accumulatedMeterageLength,
                samplerPipeDiameter, samplerPipeLength, samplerDrillType, samplerDrillDiameter, samplerDrillLength,
                index, startDepth, endDepth, count);

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

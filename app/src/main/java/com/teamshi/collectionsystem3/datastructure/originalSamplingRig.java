package com.teamshi.collectionsystem3.datastructure;

import java.util.Calendar;

/**
 * Created by Alfred on 16/8/25.
 */
public class OriginalSamplingRig extends CalculatingRig {
    protected int samplerPipeDiameter;
    protected double samplerPipeLength;
    protected String index;
    protected double startDepth;
    protected double endDepth;
    protected int count;
    private String samplerType;

    private String note;                            // 备注

    public OriginalSamplingRig(String classPeopleCount, Calendar date, Calendar startTime, Calendar endTime,
                               double drillToolTotalLength, double drillPipeRemainLength,
                               double roundTripMeterageLength, double accumulatedMeterageLength,
                               int samplerPipeDiameter, double samplerPipeLength,
                               String index,
                               double startDepth, double endDepth, int count, String samplerType,
                               int rockCoreIndex, double rockCoreLength, double rockCorePickPerentage,
                               String rigStartEndDepth, String rockType, String rockColor,
                               String rockDensity, String rockSaturation, String rockWeathering,
                               String rockDescription) {
        super(classPeopleCount, date, startTime, endTime, drillToolTotalLength, drillPipeRemainLength, roundTripMeterageLength, accumulatedMeterageLength);
        this.samplerPipeDiameter = samplerPipeDiameter;
        this.samplerPipeLength = samplerPipeLength;
        this.index = index;
        this.startDepth = startDepth;
        this.endDepth = endDepth;
        this.count = count;
        this.samplerType = samplerType;

        this.rockCoreIndex = rockCoreIndex;
        this.rockCoreLength = rockCoreLength;
        this.rockCorePickPercentage = rockCorePickPerentage;

        this.rigStartEndDepth = rigStartEndDepth;
        this.rockType = rockType;
        this.rockColor = rockColor;
        this.rockDensity = rockDensity;
        this.rockSaturation = rockSaturation;
        this.rockWeathering = rockWeathering;
        this.rockDescription = rockDescription;
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

    public String getSamplerType() {
        return samplerType;
    }

    public void setSamplerType(String samplerType) {
        this.samplerType = samplerType;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public Rig deepCopy() {
        OriginalSamplingRig temp = new OriginalSamplingRig(classPeopleCount, (Calendar) date.clone(), (Calendar) startTime.clone(), (Calendar) endTime.clone(),
                drillToolTotalLength, drillPipeRemainLength, roundTripMeterageLength, accumulatedMeterageLength,
                samplerPipeDiameter, samplerPipeLength,
                index, startDepth, endDepth, count, samplerType,
                rockCoreIndex, rockCoreLength, rockCorePickPercentage,
                rigStartEndDepth, rockType, rockColor,
                rockDensity, rockSaturation, rockWeathering,
                rockDescription);

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

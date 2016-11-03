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

    private int rockCoreIndex;                      // 岩芯采取编号
    private double rockCoreLength;                  // 岩芯长度
    private double rockCorePickPercentage;          // 岩芯采取率

    private String rigStartEndDepth;                // 本钻起止深度
    private String rockType;                        // 岩土名称
    private String rockColor;                       // 颜色
    private String rockDensity;                     // 稠度/密实度
    private String rockSaturation;                  // 饱和度
    private String rockWeathering;                  // 风化程度
    private String rockDescription;                 // 名称及岩性

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

    public int getRockCoreIndex() {
        return rockCoreIndex;
    }

    public void setRockCoreIndex(int rockCoreIndex) {
        this.rockCoreIndex = rockCoreIndex;
    }

    public double getRockCoreLength() {
        return rockCoreLength;
    }

    public void setRockCoreLength(double rockCoreLength) {
        this.rockCoreLength = rockCoreLength;
    }

    public double getRockCorePickPercentage() {
        return rockCorePickPercentage;
    }

    public void setRockCorePickPercentage(double rockCorePickPercentage) {
        this.rockCorePickPercentage = rockCorePickPercentage;
    }

    public String getRigStartEndDepth() {
        return rigStartEndDepth;
    }

    public void setRigStartEndDepth(String rigStartEndDepth) {
        this.rigStartEndDepth = rigStartEndDepth;
    }

    public String getRockType() {
        return rockType;
    }

    public void setRockType(String rockType) {
        this.rockType = rockType;
    }

    public String getRockColor() {
        return rockColor;
    }

    public void setRockColor(String rockColor) {
        this.rockColor = rockColor;
    }

    public String getRockDensity() {
        return rockDensity;
    }

    public void setRockDensity(String rockDensity) {
        this.rockDensity = rockDensity;
    }

    public String getRockSaturation() {
        return rockSaturation;
    }

    public void setRockSaturation(String rockSaturation) {
        this.rockSaturation = rockSaturation;
    }

    public String getRockWeathering() {
        return rockWeathering;
    }

    public void setRockWeathering(String rockWeathering) {
        this.rockWeathering = rockWeathering;
    }

    public String getRockDescription() {
        return rockDescription;
    }

    public void setRockDescription(String rockDescription) {
        this.rockDescription = rockDescription;
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

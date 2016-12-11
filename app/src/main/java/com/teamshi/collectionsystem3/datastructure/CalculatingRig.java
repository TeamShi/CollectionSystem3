package com.teamshi.collectionsystem3.datastructure;

import java.util.Calendar;

/**
 * Created by Alfred on 16/8/8.
 */
public class CalculatingRig extends Rig {
    protected double drillToolTotalLength;            // 钻具总长
    protected double drillPipeRemainLength;           // 钻杆余长
    protected double roundTripMeterageLength;         // 回次进尺
    protected double accumulatedMeterageLength;       // 累计进尺

    protected int rockCoreIndex;                      // 岩芯采取编号
    protected double rockCoreLength;                  // 岩芯长度
    protected double rockCorePickPercentage;          // 岩芯采取率

    protected String rigStartEndDepth;                // 本钻起止深度
    protected String rockType;                        // 岩土名称
    protected String rockColor;                       // 颜色
    protected String rockDensity;                     // 稠度/密实度
    protected String rockSaturation;                  // 饱和度
    protected String rockWeathering;                  // 风化程度
    protected String rockDescription;                 // 名称及岩性

    public CalculatingRig(String classPeopleCount, Calendar date, Calendar startTime, Calendar endTime,
                          double drillToolTotalLength, double drillPipeRemainLength,
                          double roundTripMeterageLength, double accumulatedMeterageLength,
                          int rockCoreIndex, double rockCoreLength, double rockCorePickPercentage,
                          String rigStartEndDepth, String rockType, String rockColor, String rockDensity,
                          String rockSaturation, String rockWeathering, String rockDescription) {
        super(classPeopleCount, date, startTime, endTime);

        this.drillToolTotalLength = drillToolTotalLength;
        this.drillPipeRemainLength = drillPipeRemainLength;
        this.roundTripMeterageLength = roundTripMeterageLength;
        this.accumulatedMeterageLength = accumulatedMeterageLength;

        this.rockCoreIndex = rockCoreIndex;
        this.rockCoreLength = rockCoreLength;
        this.rockCorePickPercentage = rockCorePickPercentage;
        this.rigStartEndDepth = rigStartEndDepth;
        this.rockType = rockType;
        this.rockColor = rockColor;
        this.rockDensity = rockDensity;
        this.rockSaturation = rockSaturation;
        this.rockWeathering = rockWeathering;
        this.rockDescription = rockDescription;
    }

    public double getDrillToolTotalLength() {
        return drillToolTotalLength;
    }

    public void setDrillToolTotalLength(double drillToolTotalLength) {
        this.drillToolTotalLength = drillToolTotalLength;
    }

    public double getDrillPipeRemainLength() {
        return drillPipeRemainLength;
    }

    public void setDrillPipeRemainLength(double drillPipeRemainLength) {
        this.drillPipeRemainLength = drillPipeRemainLength;
    }

    public double getRoundTripMeterageLength() {
        return roundTripMeterageLength;
    }

    public void setRoundTripMeterageLength(double roundTripMeterageLength) {
        this.roundTripMeterageLength = roundTripMeterageLength;
    }

    public double getAccumulatedMeterageLength() {
        return accumulatedMeterageLength;
    }

    public void setAccumulatedMeterageLength(double accumulatedMeterageLength) {
        this.accumulatedMeterageLength = accumulatedMeterageLength;
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
}

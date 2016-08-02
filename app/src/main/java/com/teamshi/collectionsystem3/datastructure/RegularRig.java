package com.teamshi.collectionsystem3.datastructure;

import java.util.Calendar;

/**
 * Created by Alfred on 16/7/26.
 */
public class RegularRig extends Rig {
    private String rigType;                         // 作业项目
    private int pipeNumber;                         // 钻杆编号
    private double pipeLength;                      // 钻杆长度
    private double pipeTotalLength;                 // 累积长度

    private int rockCorePipeDiameter;                   // 岩芯管直径
    private double rockCorePipeLength;                  // 岩芯管长度

    private String drillBitType;                    // 钻头类型
    private double drillBitDiameter;                // 钻头直径
    private double drillBitLength;                  // 钻头长度

    private double drillToolTotalLength;            // 钻具总长
    private double drillPipeRemainLength;           // 钻杆余长
    private double roundTripMeterageLength;         // 回次进尺
    private double accumulatedMeterageLength;       // 累积进尺

    private int rockCoreIndex;                      // 岩芯采取编号
    private double rockCoreLength;                  // 岩芯长度
    private double rockCorePickPercentage;          // 岩芯采取率

    private String rigStartEndDepth;                // 本钻起止深度
    private String rockType;                        // 岩土名称
    private String rockColor;                       // 颜色
    private String rockDensity;                     // 稠度/密实度
    private String rockSatuation;                   // 饱和度
    private String rockWeathering;                  // 风化程度
    private String rockDescription;                 // 名称及岩性

    private String note;                            // 备注

    public RegularRig(String classPeopleCount, Calendar date, Calendar startTime, Calendar endTime,
                      int pipeNumber, double pipeLength, double pipeTotalLength,
                      int rockCorePipeDiameter, double rockCorePipeLength,
                      String drillBitType, double drillBitDiameter, double drillBitLength,
                      double drillToolTotalLength, double drillPipeRemainLength, double roundTripMeterageLength, double accumulatedMeterageLength,
                      int rockCoreIndex, double rockCoreLength, double rockCorePickPercentage) {
        super(classPeopleCount, date, startTime, endTime);

        this.rigType = "干钻";
        this.pipeNumber = pipeNumber;
        this.pipeLength = pipeLength;
        this.pipeTotalLength = pipeTotalLength;

        this.rockCorePipeDiameter = rockCorePipeDiameter;
        this.rockCorePipeLength = rockCorePipeLength;

        this.drillBitType = drillBitType;
        this.drillBitDiameter = drillBitDiameter;
        this.drillBitLength = drillBitLength;

        this.drillToolTotalLength = drillToolTotalLength;
        this.drillPipeRemainLength = drillPipeRemainLength;
        this.roundTripMeterageLength = roundTripMeterageLength;
        this.accumulatedMeterageLength = accumulatedMeterageLength;

        this.rockCoreIndex = rockCoreIndex;
        this.rockCoreLength = rockCoreLength;
        this.rockCorePickPercentage = rockCorePickPercentage;
    }
    
    @Override
    public Rig deepCopy() {
        RegularRig temp = new RegularRig(classPeopleCount, date, startTime, endTime,
                pipeNumber, pipeLength, pipeTotalLength,
                rockCorePipeDiameter, rockCorePipeLength, drillBitType, drillBitDiameter, drillBitLength,
                drillToolTotalLength, drillPipeRemainLength, roundTripMeterageLength, accumulatedMeterageLength,
                rockCoreIndex, rockCoreLength, rockCorePickPercentage);
        temp.setRigType(rigType);

        temp.setRockCorePipeDiameter(rockCorePipeDiameter);
        temp.setRockCorePipeLength(rockCorePipeLength);

        temp.setDrillBitType(drillBitType);
        temp.setDrillBitDiameter(drillBitDiameter);
        temp.setDrillBitLength(drillBitLength);

        return temp;
    }

    public String getRigType() {
        return rigType;
    }

    public void setRigType(String rigType) {
        this.rigType = rigType;
    }

    public int getPipeNumber() {
        return pipeNumber;
    }

    public void setPipeNumber(int pipeNumber) {
        this.pipeNumber = pipeNumber;
    }

    public double getPipeLength() {
        return pipeLength;
    }

    public void setPipeLength(double pipeLength) {
        this.pipeLength = pipeLength;
    }

    public double getPipeTotalLength() {
        return pipeTotalLength;
    }

    public void setPipeTotalLength(double pipeTotalLength) {
        this.pipeTotalLength = pipeTotalLength;
    }

    public int getRockCorePipeDiameter() {
        return rockCorePipeDiameter;
    }

    public void setRockCorePipeDiameter(int rockCorePipeDiameter) {
        this.rockCorePipeDiameter = rockCorePipeDiameter;
    }

    public double getRockCorePipeLength() {
        return rockCorePipeLength;
    }

    public void setRockCorePipeLength(double rockCorePipeLength) {
        this.rockCorePipeLength = rockCorePipeLength;
    }

    public String getDrillBitType() {
        return drillBitType;
    }

    public void setDrillBitType(String drillBitType) {
        this.drillBitType = drillBitType;
    }

    public double getDrillBitDiameter() {
        return drillBitDiameter;
    }

    public void setDrillBitDiameter(double drillBitDiameter) {
        this.drillBitDiameter = drillBitDiameter;
    }

    public double getDrillBitLength() {
        return drillBitLength;
    }

    public void setDrillBitLength(double drillBitLength) {
        this.drillBitLength = drillBitLength;
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
}

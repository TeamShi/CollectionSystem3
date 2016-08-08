package com.teamshi.collectionsystem3.datastructure;

import java.util.Calendar;

/**
 * Created by Alfred on 16/8/8.
 */
public class CalculatingRig extends Rig {
    protected double drillToolTotalLength;            // 钻具总长
    protected double drillPipeRemainLength;           // 钻杆余长
    protected double roundTripMeterageLength;         // 回次进尺
    protected double accumulatedMeterageLength;       // 累积进尺

    public CalculatingRig(String classPeopleCount, Calendar date, Calendar startTime, Calendar endTime,
                          double drillToolTotalLength, double drillPipeRemainLength,
                          double roundTripMeterageLength, double accumulatedMeterageLength) {
        super(classPeopleCount, date, startTime, endTime);

        this.drillToolTotalLength = drillToolTotalLength;
        this.drillPipeRemainLength = drillPipeRemainLength;
        this.roundTripMeterageLength = roundTripMeterageLength;
        this.accumulatedMeterageLength = accumulatedMeterageLength;
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
}

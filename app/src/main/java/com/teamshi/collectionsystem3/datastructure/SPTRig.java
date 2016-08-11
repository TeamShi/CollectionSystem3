package com.teamshi.collectionsystem3.datastructure;

import java.util.Calendar;

/**
 * Created by Alfred on 16/8/9.
 */
public class SPTRig extends CalculatingRig {
    private int injectionToolDiameter;              // 贯入器直径
    private double injectionToolLength;             // 贯入器长度
    private String probeType;                       // 探头类型
    private int probeDiameter;                   // 探头直径
    private double probeLength;                     // 探头长度

    public SPTRig(String classPeopleCount, Calendar date, Calendar startTime, Calendar endTime,
                  double drillToolTotalLength, double drillPipeRemainLength, double roundTripMeterageLength, double accumulatedMeterageLength,
                  int injectionToolDiameter, double injectionToolLength,
                  String probeType, int probeDiameter, double probeLength) {
        super(classPeopleCount, date, startTime, endTime,
                drillToolTotalLength, drillPipeRemainLength, roundTripMeterageLength, accumulatedMeterageLength);

        this.injectionToolDiameter = injectionToolDiameter;
        this.injectionToolLength = injectionToolLength;
        this.probeType = probeType;
        this.probeDiameter = probeDiameter;
        this.probeLength = probeLength;
    }

    public int getInjectionToolDiameter() {
        return injectionToolDiameter;
    }

    public void setInjectionToolDiameter(int injectionToolDiameter) {
        this.injectionToolDiameter = injectionToolDiameter;
    }

    public double getInjectionToolLength() {
        return injectionToolLength;
    }

    public void setInjectionToolLength(double injectionToolLength) {
        this.injectionToolLength = injectionToolLength;
    }

    public String getProbeType() {
        return probeType;
    }

    public void setProbeType(String probeType) {
        this.probeType = probeType;
    }

    public int getProbeDiameter() {
        return probeDiameter;
    }

    public void setProbeDiameter(int probeDiameter) {
        this.probeDiameter = probeDiameter;
    }

    public double getProbeLength() {
        return probeLength;
    }

    public void setProbeLength(double probeLength) {
        this.probeLength = probeLength;
    }

    @Override
    public Rig deepCopy() {
        SPTRig temp = new SPTRig(classPeopleCount, date, startTime, endTime,
                drillToolTotalLength, drillPipeRemainLength, roundTripMeterageLength, accumulatedMeterageLength,
                injectionToolDiameter, injectionToolLength,
                probeType, probeDiameter, probeLength);

        return temp;
    }
}

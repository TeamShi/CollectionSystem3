package com.teamshi.collectionsystem3.datastructure;

import java.util.Calendar;

/**
 * Created by Alfred on 16/8/9.
 */
public class SPTRig extends CalculatingRig {
    private int injectionToolDiameter;
    private double injectionToolLength;
    private String drillToolType;
    private double drillToolDiameter;
    private double drillToolLength;

    public SPTRig(String classPeopleCount, Calendar date, Calendar startTime, Calendar endTime,
                  double drillToolTotalLength, double drillPipeRemainLength, double roundTripMeterageLength, double accumulatedMeterageLength,
                  int injectionToolDiameter, int injectionToolLength,
                  String drillToolType, double drillToolDiameter, double drillToolLength) {
        super(classPeopleCount, date, startTime, endTime,
                drillToolTotalLength, drillPipeRemainLength, roundTripMeterageLength, accumulatedMeterageLength);

        this.injectionToolDiameter = injectionToolDiameter;
        this.injectionToolLength = injectionToolLength;
        this.drillToolType = drillToolType;
        this.drillToolDiameter = drillToolDiameter;
        this.drillToolLength = drillToolLength;
    }
}

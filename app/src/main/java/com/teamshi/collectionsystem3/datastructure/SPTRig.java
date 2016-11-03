package com.teamshi.collectionsystem3.datastructure;

import java.util.Calendar;

/**
 * Created by Alfred on 16/8/9.
 */
public class SPTRig extends CalculatingRig {
    private int injectionToolDiameter;              // 贯入器直径
    private double injectionToolLength;             // 贯入器长度
    private String probeType;                       // 探头类型
    private int probeDiameter;                      // 探头直径
    private double probeLength;                     // 探头长度

    private double penetrationStartDepth;           // 贯入深度自
    private double penetrationEndDepth;             // 贯入深度至

    private int hitCount1;                          // 贯入击数1
    private int hitCount2;                          // 贯入击数2
    private int hitCount3;                          // 贯入击数3
    private int accumulatehHitCount;                // 累计击数

    private double countStartDepth1;                // 计数深度自1
    private double countEndDepth1;                  // 计数深度至1
    private double countStartDepth2;                // 计数深度自2
    private double countEndDepth2;                  // 计数深度至2
    private double countStartDepth3;                // 计数深度自3
    private double countEndDepth3;                  // 计数深度至3

    private double drillStartDepth1;                // 钻进深度自1
    private double drillEndDepth1;                  // 钻进深度至1
    private double drillStartDepth2;                // 钻进深度自2
    private double drillEndDepth2;                  // 钻进深度至2
    private double drillStartDepth3;                // 钻进深度自3
    private double drillEndDepth3;                  // 钻进深度至3

    private String rockName;                        // 岩土名称
    private String oldRockColor;                       // 岩土颜色
    private String oldRockDensity;                     // 岩土稠度/密实度
    private String oldRockSaturation;                  // 岩土饱和度
    private String otherDescription;                // 其它描述

    private int oldRockCoreIndex;                   // 隐含参数, 岩芯采取编号

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

    public SPTRig(String classPeopleCount, Calendar date, Calendar startTime, Calendar endTime,
                  double drillToolTotalLength, double drillPipeRemainLength, double roundTripMeterageLength, double accumulatedMeterageLength,
                  int injectionToolDiameter, double injectionToolLength,
                  String probeType, int probeDiameter, double probeLength,
                  double penetrationStartDepth, double penetrationEndDepth,
                  int hitCount1, int hitCount2, int hitCount3,
                  double countStartDepth1, double countEndDepth1,
                  double countStartDepth2, double countEndDepth2,
                  double countStartDepth3, double countEndDepth3,
                  double drillStartDepth1, double drillEndDepth1,
                  double drillStartDepth2, double drillEndDepth2,
                  double drillStartDepth3, double drillEndDepth3,
                  String rockName,
                  String oldRockColor,
                  String oldRockDensity,
                  String oldRockSaturation,
                  String otherDescription,
                  int oldRockCoreIndex,
                  int rockCoreIndex, double rockCoreLength, double rockCorePickPerentage,
                  String rigStartEndDepth, String rockType, String rockColor,
                  String rockDensity, String rockSaturation, String rockWeathering,
                  String rockDescription) {
        super(classPeopleCount, date, startTime, endTime,
                drillToolTotalLength, drillPipeRemainLength, roundTripMeterageLength, accumulatedMeterageLength);

        this.injectionToolDiameter = injectionToolDiameter;
        this.injectionToolLength = injectionToolLength;
        this.probeType = probeType;
        this.probeDiameter = probeDiameter;
        this.probeLength = probeLength;

        this.penetrationStartDepth = penetrationStartDepth;
        this.penetrationEndDepth = penetrationEndDepth;

        this.hitCount1 = hitCount1;
        this.hitCount2 = hitCount2;
        this.hitCount3 = hitCount3;

        this.accumulatehHitCount = hitCount1 + hitCount2 + hitCount3;

        this.countStartDepth1 = countStartDepth1;
        this.countStartDepth2 = countStartDepth2;
        this.countStartDepth3 = countStartDepth3;

        this.countEndDepth1 = countEndDepth1;
        this.countEndDepth2 = countEndDepth2;
        this.countEndDepth3 = countEndDepth3;

        this.drillStartDepth1 = drillStartDepth1;
        this.drillStartDepth2 = drillStartDepth2;
        this.drillStartDepth3 = drillStartDepth3;

        this.drillEndDepth1 = drillEndDepth1;
        this.drillEndDepth2 = drillEndDepth2;
        this.drillEndDepth3 = drillEndDepth3;

        this.rockName = rockName;
        this.oldRockColor = oldRockColor;
        this.oldRockDensity = oldRockDensity;
        this.oldRockSaturation = oldRockSaturation;
        this.otherDescription = otherDescription;

        this.oldRockCoreIndex = oldRockCoreIndex;

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

    public double getPenetrationStartDepth() {
        return penetrationStartDepth;
    }

    public void setPenetrationStartDepth(double penetrationStartDepth) {
        this.penetrationStartDepth = penetrationStartDepth;
    }

    public double getPenetrationEndDepth() {
        return penetrationEndDepth;
    }

    public void setPenetrationEndDepth(double penetrationEndDepth) {
        this.penetrationEndDepth = penetrationEndDepth;
    }

    public int getHitCount1() {
        return hitCount1;
    }

    public void setHitCount1(int hitCount1) {
        this.hitCount1 = hitCount1;
    }

    public int getHitCount2() {
        return hitCount2;
    }

    public void setHitCount2(int hitCount2) {
        this.hitCount2 = hitCount2;
    }

    public int getHitCount3() {
        return hitCount3;
    }

    public void setHitCount3(int hitCount3) {
        this.hitCount3 = hitCount3;
    }

    public int getAccumulatehHitCount() {
        return accumulatehHitCount;
    }

    public void setAccumulatehHitCount(int accumulatehHitCount) {
        this.accumulatehHitCount = accumulatehHitCount;
    }

    public double getCountStartDepth1() {
        return countStartDepth1;
    }

    public void setCountStartDepth1(double countStartDepth1) {
        this.countStartDepth1 = countStartDepth1;
    }

    public double getCountEndDepth1() {
        return countEndDepth1;
    }

    public void setCountEndDepth1(double countEndDepth1) {
        this.countEndDepth1 = countEndDepth1;
    }

    public double getCountStartDepth2() {
        return countStartDepth2;
    }

    public void setCountStartDepth2(double countStartDepth2) {
        this.countStartDepth2 = countStartDepth2;
    }

    public double getCountEndDepth2() {
        return countEndDepth2;
    }

    public void setCountEndDepth2(double countEndDepth2) {
        this.countEndDepth2 = countEndDepth2;
    }

    public double getCountStartDepth3() {
        return countStartDepth3;
    }

    public void setCountStartDepth3(double countStartDepth3) {
        this.countStartDepth3 = countStartDepth3;
    }

    public double getCountEndDepth3() {
        return countEndDepth3;
    }

    public void setCountEndDepth3(double countEndDepth3) {
        this.countEndDepth3 = countEndDepth3;
    }

    public double getDrillStartDepth1() {
        return drillStartDepth1;
    }

    public void setDrillStartDepth1(double drillStartDepth1) {
        this.drillStartDepth1 = drillStartDepth1;
    }

    public double getDrillEndDepth1() {
        return drillEndDepth1;
    }

    public void setDrillEndDepth1(double drillEndDepth1) {
        this.drillEndDepth1 = drillEndDepth1;
    }

    public double getDrillStartDepth2() {
        return drillStartDepth2;
    }

    public void setDrillStartDepth2(double drillStartDepth2) {
        this.drillStartDepth2 = drillStartDepth2;
    }

    public double getDrillEndDepth2() {
        return drillEndDepth2;
    }

    public void setDrillEndDepth2(double drillEndDepth2) {
        this.drillEndDepth2 = drillEndDepth2;
    }

    public double getDrillStartDepth3() {
        return drillStartDepth3;
    }

    public void setDrillStartDepth3(double drillStartDepth3) {
        this.drillStartDepth3 = drillStartDepth3;
    }

    public double getDrillEndDepth3() {
        return drillEndDepth3;
    }

    public void setDrillEndDepth3(double drillEndDepth3) {
        this.drillEndDepth3 = drillEndDepth3;
    }

    public String getRockName() {
        return rockName;
    }

    public void setRockName(String rockName) {
        this.rockName = rockName;
    }

    public String getOldRockColor() {
        return oldRockColor;
    }

    public void setOldRockColor(String oldRockColor) {
        this.oldRockColor = oldRockColor;
    }

    public String getOldRockDensity() {
        return oldRockDensity;
    }

    public void setOldRockDensity(String oldRockDensity) {
        this.oldRockDensity = oldRockDensity;
    }

    public String getOldRockSaturation() {
        return oldRockSaturation;
    }

    public void setOldRockSaturation(String oldRockSaturation) {
        this.oldRockSaturation = oldRockSaturation;
    }

    public String getOtherDescription() {
        return otherDescription;
    }

    public void setOtherDescription(String otherDescription) {
        this.otherDescription = otherDescription;
    }

    public int getOldRockCoreIndex() {
        return oldRockCoreIndex;
    }

    public void setOldRockCoreIndex(int oldRockCoreIndex) {
        this.oldRockCoreIndex = oldRockCoreIndex;
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
    public SPTRig deepCopy() {
        SPTRig temp = new SPTRig(classPeopleCount, (Calendar) date.clone(), (Calendar) startTime.clone(), (Calendar) endTime.clone(),
                drillToolTotalLength, drillPipeRemainLength, roundTripMeterageLength, accumulatedMeterageLength,
                injectionToolDiameter, injectionToolLength,
                probeType, probeDiameter, probeLength,
                penetrationStartDepth, penetrationEndDepth,
                hitCount1, hitCount2, hitCount3,
                countStartDepth1, countEndDepth1,
                countStartDepth2, countEndDepth2,
                countStartDepth3, countEndDepth3,
                drillStartDepth1, drillEndDepth1,
                drillStartDepth2, drillEndDepth2,
                drillStartDepth3, drillEndDepth3,
                rockName, oldRockColor,
                oldRockDensity, oldRockSaturation,
                otherDescription, oldRockCoreIndex,
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

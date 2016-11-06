package com.teamshi.collectionsystem3.datastructure;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Alfred on 16/8/14.
 */
public class DSTRig extends CalculatingRig  {
    private String probeType;                           // 探头类型
    private int probeDiameter;                          // 探头直径
    private double probeLength;                         // 探头长度

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

    private ArrayList<DSTDetailInfo> dstDetailInfos;    // 动力触探具体信息

    private String rockName;

    public DSTRig(String classPeopleCount, Calendar date, Calendar startTime, Calendar endTime,
                  double drillToolTotalLength, double drillPipeRemainLength, double roundTripMeterageLength, double accumulatedMeterageLength,
                  String probeType, int probeDiameter, double probeLength,
                  int rockCoreIndex, double rockCoreLength, double rockCorePickPerentage,
                  String rigStartEndDepth, String rockType, String rockColor,
                  String rockDensity, String rockSaturation, String rockWeathering,
                  String rockDescription) {
        super(classPeopleCount, date, startTime, endTime,
                drillToolTotalLength, drillPipeRemainLength, roundTripMeterageLength, accumulatedMeterageLength);

        this.probeType = probeType;
        this.probeDiameter = probeDiameter;
        this.probeLength = probeLength;

        dstDetailInfos = new ArrayList<>();

        dstDetailInfos.add(new DSTDetailInfo(drillToolTotalLength, drillToolTotalLength - 1.9, 0.1, 10, ""));

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

    public static class DSTDetailInfo implements Serializable{
        private double pipeLength;
        private double depth;
        private double length;
        private int hitCount;
        private String saturationDescription;

        public DSTDetailInfo(double pipeLength, double depth, double length, int hitCount, String saturationDescription) {
            this.pipeLength = pipeLength;
            this.depth = depth;
            this.length = length;
            this.hitCount = hitCount;
            this.saturationDescription = saturationDescription;
        }

        public double getPipeLength() {
            return pipeLength;
        }

        public void setPipeLength(double pipeLength) {
            this.pipeLength = pipeLength;
        }

        public double getDepth() {
            return depth;
        }

        public void setDepth(double depth) {
            this.depth = depth;
        }

        public double getLength() {
            return length;
        }

        public void setLength(double length) {
            this.length = length;
        }

        public int getHitCount() {
            return hitCount;
        }

        public void setHitCount(int hitCount) {
            this.hitCount = hitCount;
        }

        public String getSaturationDescription() {
            return saturationDescription;
        }

        public void setSaturationDescription(String saturationDescription) {
            this.saturationDescription = saturationDescription;
        }

        public DSTDetailInfo deepCopy() {
            DSTDetailInfo temp = new DSTDetailInfo(pipeLength, depth, length, hitCount, saturationDescription);

            return temp;
        }
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

    public ArrayList<DSTDetailInfo> getDstDetailInfos() {
        return dstDetailInfos;
    }

    public void setDstDetailInfos(ArrayList<DSTDetailInfo> dstDetailInfos) {
        this.dstDetailInfos = dstDetailInfos;
    }

    public String getRockName() {
        return rockName;
    }

    public void setRockName(String rockName) {
        this.rockName = rockName;
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
    public DSTRig deepCopy() {
        DSTRig temp = new DSTRig(classPeopleCount, (Calendar) date.clone(), (Calendar) startTime.clone(), (Calendar) endTime.clone(),
                drillToolTotalLength, drillPipeRemainLength, roundTripMeterageLength, accumulatedMeterageLength,
                probeType, probeDiameter, probeLength,
                rockCoreIndex, rockCoreLength, rockCorePickPercentage,
                rigStartEndDepth, rockType, rockColor,
                rockDensity, rockSaturation, rockWeathering,
                rockDescription);

        temp.dstDetailInfos.clear();

        for (DSTDetailInfo info : dstDetailInfos) {
            temp.getDstDetailInfos().add(info.deepCopy());
        }

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

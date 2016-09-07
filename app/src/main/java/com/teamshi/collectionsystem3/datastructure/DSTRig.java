package com.teamshi.collectionsystem3.datastructure;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Alfred on 16/8/14.
 */
public class DSTRig extends CalculatingRig  {
    private String probeType;                           // 探头类型
    private int probeDiameter;                          // 探头直径
    private double probeLength;                         // 探头长度

    private ArrayList<DSTDetailInfo> dstDetailInfos;    // 动力触探具体信息

    private String rockName;

    public DSTRig(String classPeopleCount, Calendar date, Calendar startTime, Calendar endTime,
                  double drillToolTotalLength, double drillPipeRemainLength, double roundTripMeterageLength, double accumulatedMeterageLength,
                  String probeType, int probeDiameter, double probeLength) {
        super(classPeopleCount, date, startTime, endTime,
                drillToolTotalLength, drillPipeRemainLength, roundTripMeterageLength, accumulatedMeterageLength);

        this.probeType = probeType;
        this.probeDiameter = probeDiameter;
        this.probeLength = probeLength;

        dstDetailInfos = new ArrayList<>();

        dstDetailInfos.add(new DSTDetailInfo(drillToolTotalLength, drillToolTotalLength - 1.9, 0.1, 10, ""));
    }

    public static class DSTDetailInfo {
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

    @Override
    public DSTRig deepCopy() {
        DSTRig temp = new DSTRig(classPeopleCount, (Calendar) date.clone(), (Calendar) startTime.clone(), (Calendar) endTime.clone(),
                drillToolTotalLength, drillPipeRemainLength, roundTripMeterageLength, accumulatedMeterageLength,
                probeType, probeDiameter, probeLength);

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

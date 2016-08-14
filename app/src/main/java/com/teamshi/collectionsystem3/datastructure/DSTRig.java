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

    public DSTRig(String classPeopleCount, Calendar date, Calendar startTime, Calendar endTime,
                  double drillToolTotalLength, double drillPipeRemainLength, double roundTripMeterageLength, double accumulatedMeterageLength,
                  String probeType, int probeDiameter, double probeLength) {
        super(classPeopleCount, date, startTime, endTime,
                drillToolTotalLength, drillPipeRemainLength, roundTripMeterageLength, accumulatedMeterageLength);

        this.probeType = probeType;
        this.probeDiameter = probeDiameter;
        this.probeLength = probeLength;

        dstDetailInfos = new ArrayList<>();

        dstDetailInfos.add(new DSTDetailInfo(0, 0, 0, 0, ""));
    }

    public class DSTDetailInfo {
        private int pipeLength;
        private int depth;
        private int length;
        private int hitCount;
        private String saturationDescription;

        public DSTDetailInfo(int pipeLength, int depth, int length, int hitCount, String saturationDescription) {
            this.pipeLength = pipeLength;
            this.depth = depth;
            this.length = length;
            this.hitCount = hitCount;
            this.saturationDescription = saturationDescription;
        }

        public int getPipeLength() {
            return pipeLength;
        }

        public void setPipeLength(int pipeLength) {
            this.pipeLength = pipeLength;
        }

        public int getDepth() {
            return depth;
        }

        public void setDepth(int depth) {
            this.depth = depth;
        }

        public int getLength() {
            return length;
        }

        public void setLength(int length) {
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

    @Override
    public DSTRig deepCopy() {
        DSTRig temp = new DSTRig(classPeopleCount, date, startTime, endTime,
                drillToolTotalLength, drillPipeRemainLength, roundTripMeterageLength, accumulatedMeterageLength,
                probeType, probeDiameter, probeLength);

        for (DSTDetailInfo info : dstDetailInfos) {
            temp.getDstDetailInfos().add(info.deepCopy());
        }

        return temp;
    }
}

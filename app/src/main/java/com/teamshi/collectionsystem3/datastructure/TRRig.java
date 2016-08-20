package com.teamshi.collectionsystem3.datastructure;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Alfred on 16/8/20.
 */
public class TRRig extends Rig {
    private ArrayList<TRInfo> trInfos;

    public TRRig(String classPeopleCount, Calendar date, Calendar startTime, Calendar endTime) {
        super(classPeopleCount, date, startTime, endTime);
        trInfos = new ArrayList<>();

        trInfos.add(new TRInfo("钢管", 1, 127, 0, 0));
    }

    public static class TRInfo {
        public TRInfo(String wallType, int index, int diameter,
                      double length, double totalLength) {
            this.wallType = wallType;
            this.index = index;
            this.diameter = diameter;
            this.length = length;
            this.totalLength = totalLength;
        }
        private String wallType;
        private int index;
        private int diameter;
        private double length;
        private double totalLength;

        public String getWallType() {
            return wallType;
        }

        public void setWallType(String wallType) {
            this.wallType = wallType;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public int getDiameter() {
            return diameter;
        }

        public void setDiameter(int diameter) {
            this.diameter = diameter;
        }

        public double getLength() {
            return length;
        }

        public void setLength(double length) {
            this.length = length;
        }

        public double getTotalLength() {
            return totalLength;
        }

        public void setTotalLength(double totalLength) {
            this.totalLength = totalLength;
        }

        public TRInfo deepCopy() {
            TRInfo info = new TRInfo(wallType, index, diameter, length, totalLength);
            return info;
        }
    };

    public ArrayList<TRInfo> getTrInfos() {
        return trInfos;
    }

    public void setTrInfos(ArrayList<TRInfo> trInfos) {
        this.trInfos = trInfos;
    }

    @Override
    public Rig deepCopy() {
        TRRig temp = new TRRig(classPeopleCount, date, startTime, endTime);
        temp.getTrInfos().clear();

        for (TRInfo info : trInfos) {
            temp.getTrInfos().add(info.deepCopy());
        }

        return temp;
    }
}

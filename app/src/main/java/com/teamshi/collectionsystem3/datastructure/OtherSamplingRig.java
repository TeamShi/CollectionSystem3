package com.teamshi.collectionsystem3.datastructure;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Alfred on 16/9/7.
 */
public class OtherSamplingRig extends Rig {
    private static final long serialVersionUID = -8924416849156404466L;
    private ArrayList<OtherSamplingDetail> details;
    private String samplingType;

    public static class OtherSamplingDetail extends Rig {
        private static final long serialVersionUID = -632178456444355967L;
        private String samplingType;
        private String index;
        private double startDepth;
        private double endDepth;
        private String count;
        private int diameter;

        public OtherSamplingDetail(String samplingType, String index, double startDepth, double endDepth, String count, int diameter) {
            super("", Calendar.getInstance(), Calendar.getInstance(), Calendar.getInstance());
            this.samplingType = samplingType;
            this.index = index;
            this.startDepth = startDepth;
            this.endDepth = endDepth;
            this.count = count;
            this.diameter = diameter;
        }

        public String getSamplingType() {
            return samplingType;
        }

        public void setSamplingType(String samplingType) {
            this.samplingType = samplingType;
        }

        public String getIndex() {
            return index;
        }

        public void setIndex(String index) {
            this.index = index;
        }

        public double getStartDepth() {
            return startDepth;
        }

        public void setStartDepth(double startDepth) {
            this.startDepth = startDepth;
        }

        public double getEndDepth() {
            return endDepth;
        }

        public void setEndDepth(double endDepth) {
            this.endDepth = endDepth;
        }

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public int getDiameter() {
            return diameter;
        }

        public void setDiameter(int diameter) {
            this.diameter = diameter;
        }

        public OtherSamplingDetail deepCopy() {
            return new OtherSamplingDetail(samplingType, index, startDepth, endDepth, count, diameter);
        }
    }

    public OtherSamplingRig(String classPeopleCount, Calendar date, Calendar startTime, Calendar endTime, String samplingType) {
        super(classPeopleCount, date, startTime, endTime);
        this.details = new ArrayList<>();
        this.samplingType = samplingType;
    }

    public OtherSamplingRig(String classPeopleCount, Calendar date, Calendar startTime, Calendar endTime, ArrayList<OtherSamplingDetail> details, String samplingType) {
        super(classPeopleCount, date, startTime, endTime);
        this.details = details;
        this.samplingType = samplingType;
    }

    public ArrayList<OtherSamplingDetail> getDetails() {
        return details;
    }

    public void setDetails(ArrayList<OtherSamplingDetail> details) {
        this.details = details;
    }

    public String getSamplingType() {
        return samplingType;
    }

    public void setSamplingType(String samplingType) {
        this.samplingType = samplingType;
    }

    public OtherSamplingRig deepCopy() {
        OtherSamplingRig temp = new OtherSamplingRig(classPeopleCount, (Calendar) date.clone(), (Calendar) startTime.clone(), (Calendar) endTime.clone(), samplingType);

        for (OtherSamplingDetail detail : details) {
            temp.getDetails().add(detail.deepCopy());
        }

        return temp;
    }
}

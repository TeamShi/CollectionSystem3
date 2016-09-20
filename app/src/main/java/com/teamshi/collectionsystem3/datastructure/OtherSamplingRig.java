package com.teamshi.collectionsystem3.datastructure;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Alfred on 16/9/7.
 */
public class OtherSamplingRig extends Rig {
    public static class OtherSamplingDetail {
        private String index;
        private double startDepth;
        private double endDepth;
        private String count;

        public OtherSamplingDetail(String index, double startDepth, double endDepth, String count) {
            this.index = index;
            this.startDepth = startDepth;
            this.endDepth = endDepth;
            this.count = count;
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

        public OtherSamplingDetail deepCopy() {
            return new OtherSamplingDetail(index, startDepth, endDepth, count);
        }
    }

    private ArrayList<OtherSamplingDetail> details;

    public OtherSamplingRig(String classPeopleCount, Calendar date, Calendar startTime, Calendar endTime) {
        super(classPeopleCount, date, startTime, endTime);
        this.details = new ArrayList<>();
    }

    public OtherSamplingRig(String classPeopleCount, Calendar date, Calendar startTime, Calendar endTime, ArrayList<OtherSamplingDetail> details) {
        super(classPeopleCount, date, startTime, endTime);
        this.details = details;
    }

    public ArrayList<OtherSamplingDetail> getDetails() {
        return details;
    }

    public void setDetails(ArrayList<OtherSamplingDetail> details) {
        this.details = details;
    }

    public OtherSamplingRig deepCopy() {
        OtherSamplingRig temp = new OtherSamplingRig(classPeopleCount, (Calendar) date.clone(), (Calendar) startTime.clone(), (Calendar) endTime.clone());

        for (OtherSamplingDetail detail : details) {
            temp.getDetails().add(detail.deepCopy());
        }

        return temp;
    }
}

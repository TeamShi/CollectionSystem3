package com.teamshi.collectionsystem3.datastructure;

import java.util.Calendar;

/**
 * Created by Alfred on 16/7/24.
 */
public class NARig extends Rig {
    private String naType;

    public NARig(String classPeopleCount, Calendar date, Calendar startTime, Calendar endTime) {
        super(classPeopleCount, date, startTime, endTime);
    }

    public String getNaType() {
        return naType;
    }

    public void setNaType(String naType) {
        this.naType = naType;
    }

    @Override
    public Rig deepCopy() {
        NARig temp = new NARig(classPeopleCount, date, startTime, endTime);
        temp.setNaType(naType);

        return temp;
    }
}

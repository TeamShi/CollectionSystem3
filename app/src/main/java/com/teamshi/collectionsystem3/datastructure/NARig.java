package com.teamshi.collectionsystem3.datastructure;

import java.util.Calendar;

/**
 * Created by Alfred on 16/7/24.
 */
public class NARig extends Rig {
    private static final long serialVersionUID = 8351230744891420455L;
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
        NARig temp = new NARig(classPeopleCount, (Calendar) date.clone(), (Calendar) startTime.clone(), (Calendar) endTime.clone());
        temp.setNaType(naType);

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

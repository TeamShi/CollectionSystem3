package com.teamshi.collectionsystem3.datastructure;

import java.util.Calendar;

/**
 * Created by Alfred on 16/7/26.
 */
public class RegularRig extends Rig {
    private String rigType;                     // 作业项目
    private int pipeNumber;                     // 钻杆编号
    private double pipeLength;                  // 钻杆长度
    private double pipeTotalLength;             // 累积长度

    public RegularRig(String classPeopleCount, Calendar date, Calendar startTime, Calendar endTime) {
        super(classPeopleCount, date, startTime, endTime);

        this.rigType = "干钻";
    }
    
    @Override
    public Rig deepCopy() {
        RegularRig temp = new RegularRig(classPeopleCount, date, startTime, endTime);
        temp.setRigType(rigType);

        return temp;
    }

    public String getRigType() {
        return rigType;
    }

    public void setRigType(String rigType) {
        this.rigType = rigType;
    }
}

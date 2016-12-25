package com.teamshi.collectionsystem3.datastructure;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Alfred on 16/7/14.
 */
public class Project implements Serializable {
    private static final long serialVersionUID = 639852686742355896L;
    private String projectName;
    private ArrayList<Hole> holeList;

    public Project(String projectName) {
        this.projectName = projectName;
        this.holeList = new ArrayList<Hole>();
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public ArrayList<Hole> getHoleList() {
        return holeList;
    }

    public void setHoleList(ArrayList<Hole> holeList) {
        this.holeList = holeList;
    }
}

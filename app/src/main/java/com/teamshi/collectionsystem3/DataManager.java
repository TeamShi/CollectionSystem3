package com.teamshi.collectionsystem3;

import com.teamshi.collectionsystem3.datastructure.Hole;
import com.teamshi.collectionsystem3.datastructure.Project;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Alfred on 16/7/14.
 */
public class DataManager {
    public static Project project;

    public static void loadProject(Project project) {
        DataManager.project = project;
    }

    public static Project getProject() {
        return project;
    }

    public static boolean isHoleExistInProject(String holeId) {
        for (Hole hole : project.getHoleList()) {
            if (hole.getHoleId().equals(holeId)) {
                return true;
            }
        }

        return false;
    }

    public static Hole getHole(String holeId) {
        for (Hole hole : project.getHoleList()) {
            if (hole.getHoleId().equals(holeId)) {
                return hole;
            }
        }

        return null;
    }

    public static void deleteHole(String holeId) {
        for (int i = 0; i < project.getHoleList().size(); i++) {
            if (project.getHoleList().get(i).getHoleId().equals(holeId)) {
                project.getHoleList().remove(i);
                break;
            }
        }
    }

    public static void updateHole(String holeId, Hole hole) {
        for (int i = 0; i < project.getHoleList().size(); i++) {
            if (project.getHoleList().get(i).getHoleId().equals(holeId)) {
                project.getHoleList().set(i, hole);
                break;
            }
        }
    }

    public static String[] getHoleIdOptionList() {
        ArrayList<String> resultArray = new ArrayList<>();
        String[] resultStringArray = new String[project.getHoleList().size()];

        for (Hole hole : project.getHoleList()) {
            resultArray.add(hole.getHoleId());
        }

        return resultArray.toArray(resultStringArray);
    }
}

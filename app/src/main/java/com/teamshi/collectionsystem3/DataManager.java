package com.teamshi.collectionsystem3;

import com.teamshi.collectionsystem3.datastructure.CalculatingRig;
import com.teamshi.collectionsystem3.datastructure.Hole;
import com.teamshi.collectionsystem3.datastructure.Project;
import com.teamshi.collectionsystem3.datastructure.RegularRig;
import com.teamshi.collectionsystem3.datastructure.Rig;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;

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

    public static Rig getRig(String holeId, int selectedRigIndex) {
        for (Hole hole : project.getHoleList()) {
            if (hole.getHoleId().equals(holeId)) {
                return hole.getRigList().get(selectedRigIndex);
            }
        }

        return null;
    }

    public static void addRig(String holeId, Rig rig) {
        for (Hole hole : project.getHoleList()) {
            if (hole.getHoleId().equals(holeId)) {

                hole.getRigList().add(rig);

                break;
            }
        }


    }

    public static void removeLastRig(String holeId) {
        for (Hole hole : project.getHoleList()) {
            if (hole.getHoleId().equals(holeId)) {
                hole.getRigList().remove(hole.getRigList().size() - 1);
                break;
            }
        }
    }

    public static Rig getLastRig(String holeId) {
        for (Hole hole : project.getHoleList()) {
            if (hole.getHoleId().equals(holeId)) {
                return hole.getRigList().get(hole.getRigList().size() - 1);
            }
        }

        return null;
    }

    public static CalculatingRig getLastCaculatingRig(String holeId) {
        for (Hole hole : project.getHoleList()) {
            if (hole.getHoleId().equals(holeId)) {
                for (int i = hole.getRigList().size() - 2; i >= 0; i--) {
                    if (hole.getRigList().get(i) instanceof CalculatingRig) {
                        return (CalculatingRig) hole.getRigList().get(i);
                    }
                }
            }
        }

        return null;
    }

    public static void updateRig(String holeId, int index, Rig newRig) {
        for (Hole hole : project.getHoleList()) {
            if (hole.getHoleId().equals(holeId)) {
                hole.getRigList().set(index, newRig);
                break;
            }
        }
    }

    public static void setLastClassPeopleCount(String holeId, String classPeopleCount) {
        for (Hole hole : project.getHoleList()) {
            if (hole.getHoleId().equals(holeId)) {
                hole.setLastClassPeopleCount(classPeopleCount);
                break;
            }
        }
    }
}

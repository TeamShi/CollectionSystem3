package com.teamshi.collectionsystem3;

import com.teamshi.collectionsystem3.datastructure.Hole;
import com.teamshi.collectionsystem3.datastructure.Project;

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
}

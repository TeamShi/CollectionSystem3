package com.teamshi.collectionsystem3;

import android.os.Environment;
import android.util.Log;

import com.teamshi.collectionsystem3.datastructure.Project;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jishshi on 2016/7/24.
 */
public class IOManager {
    private static final String TAG = "CollectionSystem3";
    public static String APP_NAME = "ZuanTan";
    public static String APP_ROOT = Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator+APP_NAME;
    public static String APP_ROOT_DATA = APP_ROOT +"Data/";


    public static void initFileSystem(){
    /*
      app file system overview

                     |- Project name - ...
      ZuanTan - Data |                 |- Hole id
                     |- Project name - |
                                       |- Hole id
                                       |
                                       |- projectName.ser

     */
        File root = new File(APP_ROOT);
        if(!root.exists()){
            root.mkdirs();
        }
        File dateDir = new File(APP_ROOT_DATA);
        if(!dateDir.exists()){
            dateDir.mkdirs();
        }
    }

    public static Object parseFileToObject(File file) {
        if(null == file || file.getName().equals("")){
            Log.e(TAG, "Invalid File Path.");
            return null;
        }

        Object object;
        FileInputStream fis;
        ObjectInputStream ois = null;
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            fis = new FileInputStream(file);
            ois = new ObjectInputStream(fis);
            object = ois.readObject();
            return object;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            assert ois != null;
            try {
                ois.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }


    public static File parseObjectToFile(Object object, String fileName) {
        if(null == fileName || fileName.equals("")){
            Log.e(TAG, "Invalid File Path.");
            return null;
        }

        FileOutputStream fos;
        ObjectOutputStream oos = null;
        File file;
        try {
            file = new File(fileName);
            // override ser file
            if(!file.exists()){
                file.getParentFile().mkdirs();
                file.createNewFile();
            }

            fos = new FileOutputStream(file);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(object);
            return file;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            assert oos != null;
            try {
                oos.flush();
                oos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }


    private static Map<String,Project> projects = null;

    public static Map<String,Project> getProjects() {
        if(null != projects) {
            return projects;
        }
        projects = new HashMap<String, Project>();
        // Scan app dir to list all exists valid projects
        File [] projectDirs = new File(APP_ROOT_DATA).listFiles();
        if(projectDirs != null && projectDirs.length  > 0){
            for(File dir: projectDirs){
                // load *.ser file
                String projectName = dir.getName();
                File serFile = new File(dir,projectName+".ser");
                Project project = (Project) parseFileToObject(serFile);
                projects.put(projectName,project);
            }
        }

        return projects;
    }

    /**
     * add or save the project instance to 'projects' and persist on disk
     * @param project
     * @return
     */
    public static boolean updateProject(Project project) {
        File projectDir = new File(APP_ROOT_DATA,project.getProjectName());
        projectDir.mkdirs();
        String fileName = projectDir.getAbsolutePath()+File.separator+project.getProjectName()+".ser";
        File projectFile = parseObjectToFile(project,fileName);
        if(null == projectFile){
            return false;
        } else {
            projects.put(project.getProjectName(),project);
            return true;
        }
    }

    public static boolean deleteProject(String projectName) {
        Map<String,Project> projects = getProjects();
        Project removedProject = projects.remove(projectName);
        if(null == removedProject){
            return false;
        }

        File projectDir = new File(APP_ROOT_DATA+File.separator+projectName);
        if(!projectDir.exists()){
            return false;
        }else{
            return Utility.deleteDir(projectDir);
        }

    }
}

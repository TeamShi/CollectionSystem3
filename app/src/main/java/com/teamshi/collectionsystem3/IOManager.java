package com.teamshi.collectionsystem3;

import android.os.Environment;
import android.util.Log;

import com.teamshi.collectionsystem3.datastructure.Project;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jishshi on 2016/7/24.
 */
public class IOManager {
    private static final String TAG = "CollectionSystem3";
    public static String APP_ROOT = Environment.getExternalStorageDirectory().getPath()+"/ZuanTan/";
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
        if(!root.exists()){
            dateDir.mkdirs();
        }
    }

    public static Object parseFileToObject(File file) {
        if(null == file || file.getName().equals("")){
            Log.e(TAG, "Invalid File Path.");
            return null;
        }

        if (!file.exists()) {
            file.mkdirs();
        }

        Object object;
        FileInputStream fis;
        ObjectInputStream ois = null;
        try {
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


    public static boolean parseObjectToFile(Object object, File file) throws IOException {
        if(null == file || file.getName().equals("")){
            Log.e(TAG, "Invalid File Path.");
            return false;
        }

        if (!file.exists()) {
            file.mkdirs();
        }

        FileOutputStream fos;
        ObjectOutputStream oos = null;
        try {
            fos = new FileOutputStream(file);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(object);
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            assert oos != null;
            oos.flush();
            oos.close();
        }

        return false;
    }


    private static Map<String,Project> projects = null;

    public static Map<String,Project> getProjecs() {
        if(null != projects) {
            return projects;
        }
        projects = new HashMap<String, Project>();
        // Scan app dir to list all exists valid projects
        File [] projectDirs = new File(APP_ROOT_DATA).listFiles();
        if(projectDirs != null && projectDirs.length  > 0){
            for(File file: projectDirs){
                Project project = (Project) parseFileToObject(file);
                projects.put(file.getName(),project);
            }
        }

        return projects;
    }


    public static File createProject(Project project) {
        File projectDir = new File(APP_ROOT_DATA,project.getProjectName());
        projectDir.mkdirs();
        File projectSer = new File(projectDir,project.getProjectName()+".ser");
        try {
            parseObjectToFile(project,projectSer);
            return projectSer;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return projectSer;
    }
}

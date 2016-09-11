package com.teamshi.collectionsystem3;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.teamshi.collectionsystem3.datastructure.DSTRig;
import com.teamshi.collectionsystem3.datastructure.Hole;
import com.teamshi.collectionsystem3.datastructure.Project;
import com.teamshi.collectionsystem3.datastructure.SPTRig;
import com.teamshi.collectionsystem3.parser.HtmlParser;
import com.teamshi.collectionsystem3.parser.XlsParser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jishshi on 2016/7/24.
 */
public class IOManager {
    private static final String TAG = "CollectionSystem3";
    public static String APP_NAME = "ZuanTan";
    public static String APP_ROOT = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + APP_NAME;
    public static String APP_DATA = APP_ROOT + File.separator + "Data/";
    private static final String APP_CONFIG = APP_ROOT + File.separator + "Config/";
    public static final String APP_TEMP = APP_ROOT + File.separator + "Temp/";
    private static Context appContext = null;


    public static void initFileSystem(Context applicationContext) {
    /*
      app file system overview

              |- Temp - html preview files (钻探表 岩样表 土样表 标贯表 动力触探表 临时图片)
              |
              |
              |- Config - config.ser (copy from assets if not specified by user)
              |
              |
              |      |- Project name - ...
              |      |
      ZuanTan - Data |                 |- projectName.ser
                     |- Project name - |
                                       |- Hole id - ...
                                       |
                                       |- Hole id - ...
                                       |
                                       |- Hole id - |- holeId.jpg
                                                    |
                                                    |- holeId_sign_xxx.jpg
                                                    |
                                                    |- holeId.xls

     */
        File root = new File(APP_ROOT);
        if (!root.exists()) {
            root.mkdirs();
        }
        File dateDir = new File(APP_DATA);
        if (!dateDir.exists()) {
            dateDir.mkdirs();
        }

        appContext = applicationContext;
    }

    public static Object parseFileToObject(File file) {
        if (null == file || file.getName().equals("")) {
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
        } catch (IOException | ClassNotFoundException e) {
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

    public static void loadConfiguration() {
        File configFile = null;
        try {
            configFile = new File(APP_CONFIG + "/config.ser");
            if (!configFile.exists()) {
                Utility.createFile(configFile.getPath(), false);
                InputStream configFileStream = appContext.getAssets().open("config.ser");
                Utility.copyFile(configFileStream, configFile);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        ConfigurationManager.loadConfig(configFile);

    }


    public static File parseObjectToFile(Object object, String fileName) {
        if (null == fileName || fileName.trim().equals("")) {
            Log.e(TAG, "Invalid File Path.");
            return null;
        }

        FileOutputStream fos;
        ObjectOutputStream oos = null;
        File file;
        try {
            file = new File(fileName);
            // override ser file
            if (!file.exists()) {
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


    private static Map<String, Project> projects = null;

    public static Map<String, Project> getProjects() {
        if (null != projects) {
            return projects;
        }
        projects = new HashMap<>();
        // Scan app dir to listß all exists valid projects
        File[] projectDirs = new File(APP_DATA).listFiles();
        if (projectDirs != null && projectDirs.length > 0) {
            for (File dir : projectDirs) {
                // load *.ser file
                String projectName = dir.getName();
                File serFile = new File(dir, projectName + ".ser");
                Project project = (Project) parseFileToObject(serFile);
                // remove invalid files with invalid ser file
                if (null == project) {
                    Utility.deleteDir(dir);
                }
                projects.put(projectName, project);
            }
        }

        return projects;
    }

    /**
     * add or save the project instance to 'projects' and persist on disk
     *
     * @param project
     * @return boolean
     */
    public static boolean updateProject(Project project) {
        File projectDir = new File(APP_DATA, project.getProjectName());
        projectDir.mkdirs();
        String fileName = projectDir.getAbsolutePath() + File.separator + project.getProjectName() + ".ser";
        File projectFile = parseObjectToFile(project, fileName);
        if (null == projectFile) {
            return false;
        } else {
            ArrayList<Hole> holes = project.getHoleList();
            if (holes != null && holes.size() > 0) {
                String projectDirPath = APP_DATA + File.separator + project.getProjectName();
                for (int i = 0; i < holes.size(); i++) {
                    Hole hole = holes.get(i);
                    String holeDirPath = projectDirPath + File.separator + hole.getHoleId() + File.separator;
                    XlsParser.parse(holeDirPath, hole);
                }
            }
            projects.put(project.getProjectName(), project);
            return true;
        }
    }

    public static boolean deleteProject(String projectName) {
        Map<String, Project> projects = getProjects();
        Project removedProject = projects.remove(projectName);
        if (null != removedProject) {
            File projectDir = new File(APP_DATA + File.separator + projectName);
            return projectDir.exists() && Utility.deleteDir(projectDir);
        } else {
            return false;
        }

    }

    public static List<String> previewProject() {
        Project project = DataManager.getProject();
        if (project == null || project.getHoleList() == null || project.getHoleList().size() == 0) {
            return null;
        }

        List<String> urls = new ArrayList<>();
        AssetManager assetManager = appContext.getAssets();

        String path = HtmlParser.parse(APP_TEMP, project, assetManager);
        if (null == path) {
            Log.d(TAG, "IOManager.previewProject: path isnull");
            return null;
        } else {
            Uri uri = Uri.fromFile(new File(path));
            urls.add(uri.toString());
        }

        return urls;
    }

    public static List<String> previewSPTRig(SPTRig sptRig) {
        if (null == sptRig) {
            return null;
        }

        List<String> urls = new ArrayList<>();
        AssetManager assetManager = appContext.getAssets();

        String path = HtmlParser.parseSptRig(APP_TEMP, sptRig, assetManager);
        if (null == path) {
            Log.d(TAG, "IOManager.previewSPTRig: path isnull");
            return null;
        } else {
            Uri uri = Uri.fromFile(new File(path));
            urls.add(uri.toString());
        }

        return urls;
    }

    public static List<String> previewDSTRig(DSTRig dstRig) {
        if (null != dstRig) {

            List<String> urls = new ArrayList<>();
            AssetManager assetManager = appContext.getAssets();

            String path = HtmlParser.parseDstRig(APP_TEMP, dstRig, assetManager);
            if (null == path) {
                Log.d(TAG, "IOManager.previewSPTRig: path isnull");
                return null;
            } else {
                Uri uri = Uri.fromFile(new File(path));
                urls.add(uri.toString());
            }

            return urls;
        } else {
            return null;
        }
    }

    public static File getTempJpgFile() {
        String holeImagePath = APP_TEMP + new Date().getTime() + ".jpg";
        return new File(holeImagePath);
    }

//    public static void copyHoleFiles(Hole hole) {
//        String holeDirPath = APP_DATA + hole.getProjectName() + File.separator + hole.getHoleId() + File.separator + hole.getHoleId() + "_" + roleName + ".jpg";
//
//        InputStream inputstream;
//        try {
//            inputstream = new FileInputStream(temp);
//            if(dest.exists()) {
//                Utility.copyFile(inputstream, dest);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }

    public static boolean saveBitmapToJpg(Bitmap bitmap, String jpgPath) {
        try {
            File photo = new File(jpgPath);
            Bitmap newBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(newBitmap);
            canvas.drawColor(Color.WHITE);
            canvas.drawBitmap(bitmap, 0, 0, null);
            OutputStream stream = new FileOutputStream(photo);
            newBitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream);
            stream.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void scanMediaFile(Context context, File photo) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri contentUri = Uri.fromFile(photo);
        mediaScanIntent.setData(contentUri);
        context.sendBroadcast(mediaScanIntent);
    }

    public static void copyImagesFromTemp(HashMap<String, File> tempImagsMap, Hole hole) {
        String holeDirPath = APP_DATA + hole.getProjectName() + File.separator + hole.getHoleId();
        for (Map.Entry<String, File> tempEntry : tempImagsMap.entrySet())
            try {
                File tempFile = tempEntry.getValue();
                if (null == tempFile) {
                    continue;
                }

                File holeDir = new File(holeDirPath);
                if (!holeDir.exists()) {
                    holeDir.mkdir();
                }

                File dest = new File(holeDirPath, tempEntry.getKey() + ".jpg");
                Utility.copyFile(new FileInputStream(tempFile), dest);
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    public static HashMap<String, File> getHoleImages(Hole hole) {
        HashMap<String, File> imagesMap = new HashMap<>();
        String holeDirPath = APP_DATA + hole.getProjectName() + File.separator + hole.getHoleId();
        File holeDir = new File(holeDirPath);
        File[] files = holeDir.listFiles();
        if (files == null) {
            return null;
        }
        for (File file : files) {
            String fileName = file.getName();
            int indexOfDot = fileName.lastIndexOf(".");
            String fileType = fileName.substring( indexOfDot+ 1, fileName.length());
            if (("jpg").equals(fileType)) {
                imagesMap.put(fileName.substring(0,indexOfDot), file);
            }
        }

        return imagesMap;
    }

    public static void emptyTempDir() {
        String tempDirPath = APP_TEMP;
        File tempDir = new File(tempDirPath);
        if (tempDir.exists()) {
            File[] files = tempDir.listFiles();
            for (File file : files) {
                file.delete();
            }
        } else {
            tempDir.mkdir();
        }
    }

}

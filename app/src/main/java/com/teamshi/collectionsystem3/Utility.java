package com.teamshi.collectionsystem3;

import java.io.IOException;
import java.util.Calendar;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by Alfred on 16/7/14.
 */
public class Utility {
    public static String formatCalendarDateString(Calendar c) {
        return c.get(Calendar.YEAR) + "/" + (c.get(Calendar.MONTH) + 1) + "/" + c.get(Calendar.DAY_OF_MONTH);
    }

    public static String formatTimeString(Calendar c) {
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        if (minute < 10) {
            return hour + ":0" + minute;
        } else {
            return hour + ":" + minute;
        }
    }

    public static String formatCalendarDateStringWithoutYear(Calendar c) {
        return (c.get(Calendar.MONTH) + 1) + "/" + c.get(Calendar.DAY_OF_MONTH);
    }

    public static String formatTimeStringChinese(Calendar c) {
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        if (minute < 10) {
            return hour + "时0" + minute + "分";
        } else {
            return hour + "时" + minute + "分";
        }
    }

    public static String calculateTimeSpan(Calendar c1, Calendar c2) {
        int hour1 = c1.get(Calendar.HOUR_OF_DAY);
        int hour2 = c2.get(Calendar.HOUR_OF_DAY);
        int minute1 = c1.get(Calendar.MINUTE);
        int minute2 = c2.get(Calendar.MINUTE);

        int answerHour = 0;
        int answerMinute = 0;

        if (minute2 < minute1) {
            answerMinute = minute2 + 60 - minute1;
            answerHour = hour2 - hour1 - 1;
        } else {
            answerHour = hour2 - hour1;
            answerMinute = minute2 - minute1;
        }

        if (answerMinute < 10) {
            return answerHour + ":0" + answerMinute;
        } else {
            return answerHour + ":" + answerMinute;
        }
    }

    public static String calculateTimeSpanChinese(Calendar c1, Calendar c2) {
        int hour1 = c1.get(Calendar.HOUR_OF_DAY);
        int hour2 = c2.get(Calendar.HOUR_OF_DAY);
        int minute1 = c1.get(Calendar.MINUTE);
        int minute2 = c2.get(Calendar.MINUTE);

        int answerHour = 0;
        int answerMinute = 0;

        if (minute2 < minute1) {
            answerMinute = minute2 + 60 - minute1;
            answerHour = hour2 - hour1 - 1;
        } else {
            answerHour = hour2 - hour1;
            answerMinute = minute2 - minute1;
        }

        if (answerMinute < 10) {
            return answerHour + "时0" + answerMinute + "分";
        } else {
            return answerHour + "时" + answerMinute + "分";
        }
    }

    public static boolean validateStartEndTime(Calendar startTime, Calendar endTime) {
        int hour1 = startTime.get(Calendar.HOUR_OF_DAY);
        int hour2 = endTime.get(Calendar.HOUR_OF_DAY);
        int minute1 = startTime.get(Calendar.MINUTE);
        int minute2 = endTime.get(Calendar.MINUTE);

        if (hour1 > hour2) {
            return false;
        } else if (hour1 == hour2) {
            return minute1 < minute2;
        } else {
            return true;
        }
    }

    /**
     * 递归压缩文件夹
     *
     * @param srcRootDir 压缩文件夹根目录的子路径
     * @param file       当前递归压缩的文件或目录对象
     * @param zos        压缩文件存储对象
     * @throws Exception
     */
    private static void zip(String srcRootDir, File file, ZipOutputStream zos) throws Exception {
        if (file == null) {
            return;
        }

        //如果是文件，则直接压缩该文件
        if (file.isFile()) {
            int count, bufferLen = 1024;
            byte data[] = new byte[bufferLen];

            //获取文件相对于压缩文件夹根目录的子路径
            String subPath = file.getAbsolutePath();
            int index = subPath.indexOf(srcRootDir);
            if (index != -1) {
                subPath = subPath.substring(srcRootDir.length() + File.separator.length());
            }
            ZipEntry entry = new ZipEntry(subPath);
            zos.putNextEntry(entry);
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
            while ((count = bis.read(data, 0, bufferLen)) != -1) {
                zos.write(data, 0, count);
            }
            bis.close();
            zos.closeEntry();
        } else {
            //如果是目录，则压缩整个目录
            //压缩目录中的文件或子目录
            File[] childFileList = file.listFiles();
            for (File aChildFileList : childFileList) {

                aChildFileList.getAbsolutePath().indexOf(file.getAbsolutePath());
                zip(srcRootDir, aChildFileList, zos);
            }
        }
    }

    /**
     * 对文件或文件目录进行压缩
     *
     * @param srcPath     要压缩的源文件路径。如果压缩一个文件，则为该文件的全路径；如果压缩一个目录，则为该目录的顶层目录路径
     * @param zipPath     压缩文件保存的路径。注意：zipPath不能是srcPath路径下的子文件夹
     * @param zipFileName 压缩文件名
     * @throws Exception
     */
    public static void zip(String srcPath, String zipPath, String zipFileName) throws Exception {
        CheckedOutputStream cos;
        ZipOutputStream zos = null;
        try {
            File srcFile = new File(srcPath);

            //判断压缩文件保存的路径是否存在，如果不存在，则创建目录
            File zipDir = new File(zipPath);
            if (!zipDir.exists() || !zipDir.isDirectory()) {
                zipDir.mkdirs();
            }

            //创建压缩文件保存的文件对象
            String zipFilePath = zipPath + File.separator + zipFileName;
            File zipFile = new File(zipFilePath);
            if (zipFile.exists()) {
                //检测文件是否允许删除，如果不允许删除，将会抛出SecurityException
                SecurityManager securityManager = new SecurityManager();
                securityManager.checkDelete(zipFilePath);
                //删除已存在的目标文件
                zipFile.delete();
            }

            cos = new CheckedOutputStream(new FileOutputStream(zipFile), new CRC32());
            zos = new ZipOutputStream(cos);

            //如果只是压缩一个文件，则需要截取该文件的父目录
            String srcRootDir = srcPath;
            if (srcFile.isFile()) {
                int index = srcPath.lastIndexOf(File.separator);
                if (index != -1) {
                    srcRootDir = srcPath.substring(0, index);
                }
            }
            //调用递归压缩方法进行目录或文件压缩
            zip(srcRootDir, srcFile, zos);
            zos.flush();
        } finally {
            try {
                if (zos != null) {
                    zos.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 递归删除目录下的所有文件及子目录下所有文件
     * @param dir 将要删除的文件目录
     * @return boolean Returns "true" if all deletions were successful.
     *                 If a deletion fails, the method stops attempting to
     *                 delete and returns "false".
     */
    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            //递归删除目录中的子目录下
            for (int i=0; i<children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }

    /**
     * create file if not exists
     * @param path
     * @return
     */
    public static File createFile(String path) throws IOException {

        File file = new File(path);
        if(!file.exists()){
            File parent = file.getParentFile();
            if(!file.isDirectory()) {
                parent.mkdirs();
                file.createNewFile();
            }else{
                file.mkdirs();
            }
        }

        return file;
    }

    public static boolean verifySuffix(String file,String targetSuffix) {
        String fileType = file.substring(file.lastIndexOf(".") + 1, file.length());
        if (!fileType.equals(targetSuffix)) {
            System.out.println("您的文档格式不正确(非"+targetSuffix+")！");
            return false;
        }
        return true;
    }

    public static String computeTimeInterval(Calendar c1,Calendar c2) {

        if(c1.getTimeInMillis() > c2.getTimeInMillis()) {
            Calendar temp = c1;
            c1 = c2;
            c2 = temp;
        }

        int hour1 = c1.get(Calendar.HOUR_OF_DAY);
        int hour2 = c2.get(Calendar.HOUR_OF_DAY);
        int minute1 = c1.get(Calendar.MINUTE);
        int minute2 = c2.get(Calendar.MINUTE);

        int answerHour = 0;
        int answerMinute = 0;

        if (minute2 < minute1) {
            answerMinute = minute2 + 60 - minute1;
            answerHour = hour2 - hour1 - 1;
        } else {
            answerHour = hour2 - hour1;
            answerMinute = minute2 - minute1;
        }

        if (answerMinute < 10) {
            return answerHour + ":0" + answerMinute;
        } else {
            return answerHour + ":" + answerMinute;
        }
    }

    public static String formatNumber(double number) {
        int thousands = (int) (number / 1000);
        String output = thousands > 0 ? thousands+"+"+ (number - 1000 * thousands) : String.valueOf(number);
        return output ;
    }
}

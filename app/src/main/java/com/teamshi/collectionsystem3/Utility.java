package com.teamshi.collectionsystem3;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.ActivityCompat;
import android.util.Base64;

import org.apache.commons.io.output.ByteArrayOutputStream;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
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
    private static String[] PERMISSIONS_STORAGE = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
    static final int REQUEST_EXTERNAL_STORAGE = 1;

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
     * @param isDir
     * @return
     */
    public static File createFile(String path, boolean isDir) throws IOException {

        File file = new File(path);
        if(!file.exists()){
            if(!isDir) {
                File parent = file.getParentFile();
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

    public static String formatNumber(double number) {
        int thousands = (int) (number / 1000);
        String output = thousands > 0 ? thousands+"+"+ (number - 1000 * thousands) : String.valueOf(number);
        return output ;
    }

    public static void copyFile(InputStream is, File dest) throws IOException {
        FileOutputStream fos = new FileOutputStream(dest);
        byte[] buffer = new byte[1024];
        while (true) {
            int len = is.read(buffer);
            if (len == -1) {
                break;
            }
            fos.write(buffer, 0, len);
        }
        is.close();
        fos.close();
    }

    public static String formatCalendarDateString(Calendar calendar ,String simpleDateFormat) {
        SimpleDateFormat fmt = new SimpleDateFormat(simpleDateFormat);// example "yyyy年MM月dd日"
        String dateStr = fmt.format(calendar.getTime());
        return dateStr;
    }

    public static String[][] concat(String[][] a, String[][] b) {
        int aLen = a.length;
        int bLen = b.length;
        String[][] c= new String[aLen+bLen][];
        System.arraycopy(a, 0, c, 0, aLen);
        System.arraycopy(b, 0, c, aLen, bLen);
        return c;
    }

    public static String formatDouble(double d) {
        if (d % 1 == 0) {
            return String.format("%.0f", d);
        } else {
            return String.format("%.2f", d);
        }
    }

    /**
     * Checks if the app has permission to write to device storage
     * <p>
     * If the app does not has permission then the user will be prompted to grant permissions
     *
     * @param activity the activity from which permissions are checked
     */
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }
    public static boolean validateDate(String dateString) {
        long expiredDate = getExpiredDate(dateString.toUpperCase());

        if (Calendar.getInstance().getTimeInMillis() / 1000 < expiredDate) {
            return true;
        } else {
            return false;
        }
    }

    public static long getExpiredDate(String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            sb.append(decodeChar(s.charAt(i)));
        }

        String temp = sb.toString();

        StringBuilder sb2 = new StringBuilder();

        try {
            sb2.append(temp.charAt(5));
            sb2.append(temp.charAt(8));
            sb2.append(temp.charAt(0));
            sb2.append(temp.charAt(6));
            sb2.append(temp.charAt(3));
            sb2.append(temp.charAt(1));
            sb2.append(temp.charAt(4));
            sb2.append(temp.charAt(7));
            sb2.append(temp.charAt(2));
            sb2.append(temp.charAt(9));
        } catch (Exception e) {
            return Long.MIN_VALUE;
        }

        long result;
        try {
            result = Long.parseLong(sb2.toString());
        } catch (Exception e){
            return Long.MIN_VALUE;
        }
        return result;
    }

    public static String decodeChar(char c) {
        switch (c) {
            case 'X':
                return "0";
            case 'A':
                return "1";
            case 'Z':
                return "2";
            case 'F':
                return "3";
            case 'D':
                return "4";
            case 'G':
                return "5";
            case 'H':
                return "6";
            case 'T':
                return "7";
            case 'R':
                return "8";
            case 'L':
                return "9";
            default:
                return "";
        }
    }

    public static String getExpiredString(long date) {
        int[] dateArray = new int[10];
        for (int i = 9; i >= 0; i--) {
            dateArray[i] = (int)(date % 10);
            date /= 10;
        }

        StringBuilder sb = new StringBuilder();
        sb.append(encodeNum(dateArray[2]));
        sb.append(encodeNum(dateArray[5]));
        sb.append(encodeNum(dateArray[8]));
        sb.append(encodeNum(dateArray[4]));
        sb.append(encodeNum(dateArray[6]));
        sb.append(encodeNum(dateArray[0]));
        sb.append(encodeNum(dateArray[3]));
        sb.append(encodeNum(dateArray[7]));
        sb.append(encodeNum(dateArray[1]));
        sb.append(encodeNum(dateArray[9]));

        return sb.toString();
    }

    public static String encodeNum(int i) {
        switch (i) {
            case 0:
                return "X";
            case 1:
                return "A";
            case 2:
                return "Z";
            case 3:
                return "F";
            case 4:
                return "D";
            case 5:
                return "G";
            case 6:
                return "H";
            case 7:
                return "T";
            case 8:
                return "R";
            case 9:
                return "L";
            default:
                return "";
        }
    }

    public static String imgToBase64(String path) throws IOException {
        Bitmap bm = BitmapFactory.decodeFile(path);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
        byte[] b = baos.toByteArray();

        return Base64.encodeToString(b, Base64.DEFAULT);
    }
}

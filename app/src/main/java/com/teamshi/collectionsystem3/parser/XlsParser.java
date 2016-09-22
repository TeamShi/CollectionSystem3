package com.teamshi.collectionsystem3.parser;
/**
 * Created by jishshi on 2015/5/9.
 */

import com.teamshi.collectionsystem3.Utility;
import com.teamshi.collectionsystem3.datastructure.DSTRig;
import com.teamshi.collectionsystem3.datastructure.Hole;
import com.teamshi.collectionsystem3.datastructure.OtherSamplingRig;
import com.teamshi.collectionsystem3.datastructure.Rig;
import com.teamshi.collectionsystem3.datastructure.SPTRig;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;


/**
 * xls工具类
 *
 * @author johnson
 */
public class XlsParser extends Parser {
    //todo xls headers updated
    public static String[] HOLE_HEADER = new String[]{"勘察点名称", "工程名称", "阶 段", "冠 词", "里  程", "偏移量", "高  程", "经距X", "纬距Y", "位置描述", "记录者", "记录日期", "复核者", "复核日期", "附  注", "孔  深"};
    public static String[] Rig_HEADER = new String[]{"班次/人数", "日期", "开始时间", "结束时间", "作业项目", "钻杆编号", "钻杆长度", "累计长度", "岩心管直接", "岩心管长度", "钻头类型", "钻头直径", "钻头长度",
            "贯入器直径", "贯入器长度", "动探类型", "探头直径", "探头长度", "钻具总长", "钻杆余长", "回次进尺", "累计进尺", "岩芯编号", "岩芯长度", "岩芯采取旅", "岩土名称", "本钻起深度", "本钻止深度", "岩土颜色", "岩土稠密度", "岩土饱和度", "岩石风化程度", "岩土岩性"};
    public static String[] SPTEVENT_HEADER = new String[]{"勘探点名称", "作业ID", "贯入深度起", "贯入深度止", "第一击起深度", "第一击止深度", "第一击贯入击数", "钻进1深度起", "钻进1深度止",
            "第一击起深度", "第二击止深度", "第二击贯入击数", "钻进2深度起", "钻进2深度止", "第三击起深度", "第三击止深度", "第三击贯入击数", "钻进3深度起", "钻进3深度止", "岩土名称", "岩土颜色", "岩土饱和度", "累计击数", "其他特征"};
    public static String[] DSTEVENT_HEADER = new String[]{"勘探点名称", "作业ID", "岩土名称", "探杆总长", "入土深度", "贯入度", "锤击数", "密实度"};
    public static String[] SMPL_WATER_EVENT_HEADER = new String[]{"勘探点名称", "作业ID", "岩土名称", "探杆总长", "入土深度", "贯入度", "锤击数", "密实度"};
    public static String[] SMPL_ROCK_EVENT_HEADER = new String[]{"勘探点名称", "作业ID", "岩土名称", "探杆总长", "入土深度", "贯入度", "锤击数", "密实度"};
    private static final String[] SMPL_EARTH_EVENT_HEADER = new String[]{};

    public static String RegularRig_NAME = "钻孔原始记录";
    public static String SptRig_NAME = "标准贯入";
    public static String DstRig_NAME = "动力触探";
    public static String SampleWater_NAME = "水样";
    public static String SampleRock_NAME = "岩样";
    private static String SampleEarth_NAME = "土样";


    public static boolean write(String outPath, String[][] array, String sheetName, String[] headerNames) throws Exception {
        String fileType = outPath.substring(outPath.lastIndexOf(".") + 1, outPath.length());
        File file = new File(outPath);
        Workbook wb;
        if (file.exists()) {
            FileInputStream fileInputStream = new FileInputStream(file);
            switch (fileType) {
                case "xls":
                    wb = new HSSFWorkbook(fileInputStream);
                    break;
                case "xlsx":
                    wb = new XSSFWorkbook(fileInputStream);
                    break;
                default:
                    System.out.println("您的文档格式不正确！");
                    return false;
            }
            fileInputStream.close();
        } else {
            switch (fileType) {
                case "xls":
                    wb = new HSSFWorkbook();
                    break;
                case "xlsx":
                    wb = new XSSFWorkbook();
                    break;
                default:
                    System.out.println("您的文档格式不正确！");
                    return false;
            }
        }

        //写表头
        Sheet sheet1 = wb.createSheet(sheetName);
        if (headerNames != null && headerNames.length > 0) {
            Row header = sheet1.createRow(0);
            for (int m = 0; m < headerNames.length; m++) {
                Cell cell = header.createCell(m);
                String text = headerNames[m].equals("null") ? "" : headerNames[m];
                cell.setCellValue(text);
            }
        }

        // 循环写入行数据
        for (int i = 0, rows = array.length; i < rows; i++) {
            Row row = sheet1.createRow(i + 1);
            // 循环写入列数据
            for (int j = 0, cols = array[i].length; j < cols; j++) {
                Cell cell = row.createCell(j);
                String text = array[i][j].equals("null") ? "" : array[i][j];
                cell.setCellValue(text);
            }
        }

        // 创建文件流
        OutputStream stream = new FileOutputStream(file, false);
        // 写入数据
        wb.write(stream);
        // 关闭文件流
        stream.close();
        return true;
    }


    public static String parse(String dirPath, Hole hole) {
        ArrayList<SPTRig> sptRigs = new ArrayList<>();
        ArrayList<DSTRig> dstRigs = new ArrayList<>();
        ArrayList<OtherSamplingRig.OtherSamplingDetail> waterSampleDetails = new ArrayList<>();
        ArrayList<OtherSamplingRig.OtherSamplingDetail> rockSampleDetails = new ArrayList<>();
        ArrayList<OtherSamplingRig.OtherSamplingDetail> earthSampleDetails = new ArrayList<>();

        for (Rig rig : hole.getRigIndexViewList()) {
            if (rig instanceof SPTRig) {
                sptRigs.add((SPTRig) rig);
            }

            if (rig instanceof DSTRig) {
                dstRigs.add((DSTRig) rig);
            }

            if (rig instanceof OtherSamplingRig.OtherSamplingDetail) {
                OtherSamplingRig.OtherSamplingDetail detail = (OtherSamplingRig.OtherSamplingDetail) rig;
                switch (detail.getSamplingType()) {
                    case "岩样":
                        rockSampleDetails.add(detail);
                        break;
                    case "水样":
                        waterSampleDetails.add(detail);
                        break;
                    case "扰动样":
                        earthSampleDetails.add(detail);
                        break;
                    default:
                        break;
                }
            }
        }


        String[][] rigArray = convertHole(hole, "\n");
        String[][] sptRigArray = convertSpts(sptRigs, "\n");
        String[][] dstRigArray = convertDsts(dstRigs);
        String[][] waterSampleArray = convertWaterSmpls(hole, waterSampleDetails);
        String[][] rockSampleArray = convertRockSmpls(hole, rockSampleDetails);
        String[][] earthSampleArray = convertEarthSmpls(hole, earthSampleDetails);


        String filePath = dirPath + "hole_" + hole.getHoleId() + ".xls";
        File dirFile = new File(dirPath);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }

        try {
            XlsParser.write(filePath, rigArray, RegularRig_NAME, Rig_HEADER);
            XlsParser.write(filePath, sptRigArray, SptRig_NAME, SPTEVENT_HEADER);
            XlsParser.write(filePath, dstRigArray, DstRig_NAME, DSTEVENT_HEADER);
            XlsParser.write(filePath, waterSampleArray, SampleWater_NAME, SMPL_WATER_EVENT_HEADER);
            XlsParser.write(filePath, rockSampleArray, SampleRock_NAME, SMPL_ROCK_EVENT_HEADER);
            XlsParser.write(filePath, earthSampleArray, SampleEarth_NAME, SMPL_EARTH_EVENT_HEADER);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return filePath;
    }

    private static String[][] convertDsts(ArrayList<DSTRig> dstRigs) {
        String[][] resultData = new String[1][];
        resultData[0] = DSTEVENT_HEADER;
        for (int i = 0, len = dstRigs.size(); i < len; i++) {
            String[][] lines = convertDst(dstRigs.get(i));
            resultData = Utility.concat(lines, resultData);
        }

        return resultData;
    }

    private static String[][] convertSpts(ArrayList<SPTRig> sptRigs, String BR) {
        String[][] resultData = new String[1][];
        resultData[0] = DSTEVENT_HEADER;
        for (int i = 0, len = sptRigs.size(); i < len; i++) {
            String[][] lines = convertSpt(sptRigs.get(i), (i + 1), BR);
            resultData = Utility.concat(lines, resultData);
        }

        return resultData;
    }

    private static String[][] convertEarthSmpls(Hole hole, ArrayList<OtherSamplingRig.OtherSamplingDetail> earthSampleDetails) {
        String[][] resultData = new String[1][];
        resultData[0] = SMPL_WATER_EVENT_HEADER;
        for (int i = 0, len = earthSampleDetails.size(); i < len; i++) {
            String[][] lines = convertEarthSmplDetail(hole, earthSampleDetails.get(i), "\n");
            resultData = Utility.concat(lines, resultData);
        }

        return resultData;
    }

    private static String[][] convertWaterSmpls(Hole hole, ArrayList<OtherSamplingRig.OtherSamplingDetail> waterSampleRigs) {
        String[][] resultData = new String[1][];
        resultData[0] = SMPL_WATER_EVENT_HEADER;
        for (int i = 0, len = waterSampleRigs.size(); i < len; i++) {
            String[][] lines = convertWaterSmplDetail(hole, waterSampleRigs.get(i), "\n");
            resultData = Utility.concat(lines, resultData);
        }

        return resultData;
    }

    private static String[][] convertRockSmpls(Hole hole, ArrayList<OtherSamplingRig.OtherSamplingDetail> details) {
        String[][] resultData = new String[1][];
        resultData[0] = SMPL_ROCK_EVENT_HEADER;
        for (int i = 0, len = details.size(); i < len; i++) {
            String[][] lines = convertRockSmplDetail(hole, details.get(i), "\n");
            resultData = Utility.concat(lines, resultData);
        }

        return resultData;
    }


}
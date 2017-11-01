package com.teamshi.collectionsystem3.parser;
/**
 * Created by jishshi on 2015/5/9.
 */

import com.teamshi.collectionsystem3.Utility;
import com.teamshi.collectionsystem3.datastructure.DSTRig;
import com.teamshi.collectionsystem3.datastructure.Hole;
import com.teamshi.collectionsystem3.datastructure.OriginalSamplingRig;
import com.teamshi.collectionsystem3.datastructure.OtherSamplingRig;
import com.teamshi.collectionsystem3.datastructure.Rig;
import com.teamshi.collectionsystem3.datastructure.RigGraphData;
import com.teamshi.collectionsystem3.datastructure.SPTRig;

import org.apache.poi.hpsf.Util;
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
import java.util.List;


/**
 * xls工具类
 *
 * @author johnson
 */
public class XlsParser extends Parser {
    public static String[] HOLE_HEADER = new String[]{"勘察点名称", "工程名称", "阶 段", "冠 词", "里  程", "偏移量", "高  程", "经距X", "纬距Y", "位置描述", "记录者", "记录日期", "复核者", "复核日期", "附  注", "孔  深"};
    public static String[] Rig_HEADER = new String[]{"班次/人数", "日期", "开始时间", "结束时间", "计",
            "作业项目", "钻杆编号", "钻杆长度", "钻杆总长", "岩心管直径", "岩心管长度", "钻头类型",
            "钻头直径", "钻头长度", "钻具总长", "钻杆余长", "回次进尺", "累计孔深", "护壁类型", "套管编号",
            "套管直径", "套管长度", "套管总长", "钻探过程孔内情况", "岩芯编号", "岩芯长度", "岩芯采取率",
            "原状土样编号", "原状土样直径", "原状土样深度", "原状土样数量", "水样编号", "水样深度", "水样数量",
            "地层编号", "层底深度", "层厚", "名称及岩性", "岩层等级", "地下水观测时间", "初见水位", "稳定水位",
            "水位变化", "特殊情况记录"};
    public static String[] SPTEVENT_HEADER = new String[]{"顺序号", "月", "日", "班次", "自", "至",
            "分层", "贯入深度自", "贯入深度至", "计数深度自", "计数深度至", "钻进深度自",
            "钻进深度至", "分类", "颜色", "密度", "湿度", "黏土等级", "黏土状态", "光泽", "夹杂物", "气味"
            , "其他特征及包含", "贯入击数", "初见水位", "静止水位", "备注"};
    public static String[] DSTEVENT_HEADER = new String[]{"探杆总长", "入土深度", "贯入度", "锤击数",
            "密实度", "矫正后击数", "附注"};
    public static String[] SMPL_WATER_EVENT_HEADER = new String[]{"外业", "试验室", "勘探点编号",
            "取样日期", "取样地点", "取样深度", "水样类别", "取样时气候", "取样时温度", "化验目的", "备注"};
    public static String[] SMPL_ROCK_EVENT_HEADER = new String[]{"外业", "试验室", "勘探点编号",
            "取样地点", "取样深度", "岩石名称", "风化程度", "工程名称", "密度", "颗粒密度", "吸水率",
            "饱和吸水率", "天然-单轴抗压强度", "干燥-单轴抗压强度", "饱和-单轴抗压强度", "薄片鉴定", "特殊项目", "附注"};
    private static String[] SMPL_EARTH_EVENT_HEADER = new String[]{"试验室", "外业", "勘探点编号",
            "地点", "试件深度", "野外鉴定名称", "工程名称", "原状土", "扰动土", "备注"};
    private static String[] SMPL_ORIGIN_EVENT_HEADER = new String[]{"试验室", "外业", "勘探点编号",
            "地点", "试件深度", "野外鉴定名称", "工程名称", "原状土", "扰动土", "备注"};

    private static String[] RIG_GRAPH_HEADER =  new String[] {"主层", "亚层", "次亚层",
            "层深", "成因", "时代", "稠度", "填充岩性", "岩性描述", "承载力"};

    public static String RegularRig_NAME = "钻孔原始记录";
    public static String SptRig_NAME = "标准贯入";
    public static String DstRig_NAME = "动力触探";
    public static String SampleWater_NAME = "水样";
    public static String SampleRock_NAME = "岩样";
    private static String SampleEarth_NAME = "土样";
    private static String SampleOrigin_NAME = "原状样";
    private static String RigGraph_NAME = "钻探记录表";


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
            int numberOfSheets = wb.getNumberOfSheets();
            for(int ii = 0; ii < numberOfSheets; ii ++) {
                if(sheetName.equals(wb.getSheetName(ii))) {
                    wb.removeSheetAt(ii);
                    break;
                }
            }
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
        assert headerNames != null;
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
                String text = null == array[i][j] || array[i][j].equals("null") ? "" : array[i][j];
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
        ArrayList<OriginalSamplingRig> originalSamplingRigs = new ArrayList<>();

        for (Rig rig : hole.getRigIndexViewList()) {
            if (rig instanceof SPTRig) {
                sptRigs.add((SPTRig) rig);
            }

            if (rig instanceof DSTRig) {
                dstRigs.add((DSTRig) rig);
            }

            if( rig instanceof OriginalSamplingRig) {
                originalSamplingRigs.add((OriginalSamplingRig) rig);
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


        String[][] rigArray = convertHole(hole, "\\");
        String[][] sptRigArray = convertSpts(sptRigs, "\\");
        String[][] dstRigArray = convertDsts(dstRigs);
        String[][] waterSampleArray = convertWaterSmpls(hole, waterSampleDetails);
        String[][] rockSampleArray = convertRockSmpls(hole, rockSampleDetails);
        String[][] earthSampleArray = convertEarthSmpls(hole, earthSampleDetails);
        String[][] originalSampleArray = convertOriginSmpls(hole, originalSamplingRigs);
        String[][] rigGraphArray = convertRigGrapph(hole, hole.getRigGraphData());


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
            XlsParser.write(filePath, originalSampleArray, SampleOrigin_NAME, SMPL_ORIGIN_EVENT_HEADER);
            XlsParser.write(filePath, rigGraphArray, RigGraph_NAME, RIG_GRAPH_HEADER);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return filePath;
    }

    private static String[][] convertRigGrapph(Hole hole, RigGraphData rigGraphData) {
        if(rigGraphData == null) {
            return  new String[0][];
        }

        List<RigGraphData.RigNode> nodes =  rigGraphData.getRigNodeList();
        int rows = nodes.size();
        String[] endDepth = new String[rows];
        String[] drillTypes = new String[rows];
        String[] rockDensities = new String[rows];
        String[] desc = new String[rows];
        for(int i = 0; i < rows; i++)  {
            RigGraphData.RigNode node = nodes.get(i);
            node.getRockDensity();
            endDepth[i] = Utility.formatDouble(node.getEndDepth());
            rockDensities[i] = node.getRockDensity();
            drillTypes[i] = node.getDrillType();
            desc[i] = node.getDescription();
        }

        String[][] result = new String[rows][10];
        for(int r= 0; r < rows; r++) {
           result[r] = new String[]{
                   "", //主层
                   "", //次层
                   "", //次亚层
                   endDepth[r], //回次进尺， 层深
                   "",//成因
                   "",//时代
                   rockDensities[r],//稠度
                   drillTypes[r], //岩性
                   desc[r],
                   "",//承载力
           };
        }

        return result;
    }

    private static String[][] convertDsts(ArrayList<DSTRig> dstRigs) {
        String[][] resultData = new String[0][];
        for (int i = 0, len = dstRigs.size(); i < len; i++) {
            String[][] lines = convertDst(dstRigs.get(i));
            resultData = Utility.concat(resultData, lines);
        }

        return resultData;
    }

    private static String[][] convertSpts(ArrayList<SPTRig> sptRigs, String BR) {
        String[][] resultData = new String[0][];
        for (int i = 0, len = sptRigs.size(); i < len; i++) {
            String[][] lines = convertSpt(sptRigs.get(i), (i + 1), BR);
            resultData = Utility.concat(resultData, lines);
        }

        return resultData;
    }

    private static String[][] convertEarthSmpls(Hole hole, ArrayList<OtherSamplingRig.OtherSamplingDetail> earthSampleDetails) {
        String[][] resultData = new String[0][];
        for (int i = 0, len = earthSampleDetails.size(); i < len; i++) {
            String[][] lines = convertEarthSmplDetail(hole, earthSampleDetails.get(i), "\\");
            resultData = Utility.concat(resultData, lines);
        }

        return resultData;
    }

    private static String[][] convertOriginSmpls(Hole hole, ArrayList<OriginalSamplingRig> originalSamplingRigs) {
        String[][] resultData = new String[0][];
        for (int i = 0, len = originalSamplingRigs.size(); i < len; i++) {
            String[][] lines = convertOriSmpl(hole, originalSamplingRigs.get(i), "\\");
            resultData = Utility.concat(resultData, lines);
        }

        return resultData;
    }


    private static String[][] convertWaterSmpls(Hole hole, ArrayList<OtherSamplingRig.OtherSamplingDetail> waterSampleRigs) {
        String[][] resultData = new String[0][];
        for (int i = 0, len = waterSampleRigs.size(); i < len; i++) {
            String[][] lines = convertWaterSmplDetail(hole, waterSampleRigs.get(i), "\\");
            resultData = Utility.concat(resultData, lines);
        }

        return resultData;
    }

    private static String[][] convertRockSmpls(Hole hole, ArrayList<OtherSamplingRig.OtherSamplingDetail> details) {
        String[][] resultData = new String[0][];
        for (int i = 0, len = details.size(); i < len; i++) {
            String[][] lines = convertRockSmplDetail(hole, details.get(i), "\\");
            resultData = Utility.concat(resultData, lines);
        }

        return resultData;
    }


}
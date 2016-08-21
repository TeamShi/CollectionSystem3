package com.teamshi.collectionsystem3.parser;

import android.content.res.AssetManager;
import android.net.NetworkInfo;

import com.teamshi.collectionsystem3.Utility;
import com.teamshi.collectionsystem3.datastructure.DSTRig;
import com.teamshi.collectionsystem3.datastructure.Hole;
import com.teamshi.collectionsystem3.datastructure.NARig;
import com.teamshi.collectionsystem3.datastructure.Project;
import com.teamshi.collectionsystem3.datastructure.RegularRig;
import com.teamshi.collectionsystem3.datastructure.Rig;
import com.teamshi.collectionsystem3.datastructure.SPTRig;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static com.teamshi.collectionsystem3.Utility.deleteDir;
import static com.teamshi.collectionsystem3.Utility.formatCalendarDateString;


public class HtmlParser {

    private static final String NA = "NA";
    public static String PROJECT_OVERVIEW_TEMPLATE = "Project.html";
    public static String BASIC_RIG_EVENT_TEMPLATE = "RigEventTable.html";
    public static String SPT_RIG_EVENT_TEMPLATE = "SPTRigEventTable.html";
    public static String SMPL_RIG_EVENT_TEMPLATE = "SMPLRigEventTable.html";
    public static String DST_RIG_EVENT_TEMPLATE = "DSTRigEventTable.html"; // 动力触探


    public static String TBODY_ID = "tableBody";
    public static String PROJECTNAME_ID = "projectName";
    public static String PROJECTNAME_LIST = "projectList";
    public static String POSITION_ID = "position";
    public static String MILEAGE_ID = "mileage";

    public static String HOLEELEVATION_ID = "holeElevation";
    public static String HOLE_OFFSET = "offsetRight";
    public static String HOLE_ID = "holeId";
    public static String EXPLORATIONUNIT_ID = "company";
    public static String MACHINENUMBER_ID = "machineNumber";
    public static String RIGTYPE_ID = "rigType";
    public static String STARTDATE_ID = "startDate";

    public static String RECORDER_ID = "recorderName";
    public static String SQUAD_ID = "squadName";
    public static String CAPTAIN_ID = "captainName";

    public static String[] convert2Array(String string) {

        String[] row = string.replaceAll("##", "# #").split("#");

        for (int j = 0, colLen = row.length; j < colLen; j++) {
            if (row[j].equals(NA)) {
                row[j] = "";
            }
        }
        return row;
    }


    public static boolean write(String outPath, String[][] data, InputStream inputStream) throws IOException {
        String fileType = outPath.substring(outPath.lastIndexOf(".") + 1, outPath.length());
        if (!fileType.equals("html")) {
            System.out.println("您的文档格式不正确(非html)！");
            return false;
        }

        //读模版文件
        Document doc = Jsoup.parse(inputStream, "UTF-8", "./");
        Element tbody = doc.getElementById(TBODY_ID);
        if (data != null) {
            // 循环写入行数据
            for (int i = 0, rows = data.length; i < rows; i++) {
                StringBuffer row = new StringBuffer();
                row.append("<tr>");
                // 循环写入列数据
                for (int j = 0, cols = data[i].length; j < cols; j++) {
                    row.append("<td>");
                    String text = data[i][j].equals("null") ? "" : data[i][j];
                    row.append(text);
                    row.append("</td>");
                }
                row.append("</tr>");
                tbody.append(row.toString());
            }
        }

        FileWriter fileWriter = new FileWriter(outPath,false);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write(doc.outerHtml());
        bufferedWriter.close();
        fileWriter.close();

        return true;

    }

    public static String parse(String dirPath, Project project, AssetManager assetManager) {
        //TODO  Johnson update convertion methods
//        String[][] sptRigEventArray = convertSpt(hole);
//        String[][] dstRigEventArray = convertDst(hole);
        List<Hole> holes = project.getHoleList();
        String projectPath = dirPath + "project_" + project.getProjectName() + ".html";
        InputStream inputStream;
        try {
            File projectPreviewFile = Utility.createFile(projectPath, false);
            inputStream = assetManager.open(PROJECT_OVERVIEW_TEMPLATE);
            //读模版文件
            Document doc = Jsoup.parse(inputStream, "UTF-8", "./");
            Element header = doc.getElementById(PROJECTNAME_ID);
            header.appendText("项目名称: " + project.getProjectName());

            Element list = doc.getElementById(PROJECTNAME_LIST);
            for (Hole hole : holes) {
                Element anchor = doc.createElement("a");
                anchor.attr("href", "" + "hole_" + hole.getHoleId() + ".html");
                anchor.appendText(hole.getHoleId());
                list.append("<li>" + anchor.toString() + "</li>");
            }

            FileWriter fileWriter = new FileWriter(projectPreviewFile);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(doc.outerHtml());
            bufferedWriter.close();
            fileWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        for (Hole hole : holes) {
            try {
                parseHole(dirPath + "hole_" + hole.getHoleId() + ".html", hole, assetManager.open(BASIC_RIG_EVENT_TEMPLATE));
////            write(dirPath + "/sptRig_" + hole.getHoleId() + ".html", sptRigEventArray, assetManager.open(SPT_RIG_EVENT_TEMPLATE));
////            write(dirPath + "/dstRig_" + hole.getHoleId() + ".html", dstRigEventArray, assetManager.open(DST_RIG_EVENT_TEMPLATE));
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }


        return projectPath;
    }


    public static String parseSptRig(String dirPath, SPTRig sptRig, AssetManager assetManager) {
        if (sptRig == null) {
            return null;
        }

        String[][] sptEventArray = convertSpt(sptRig);
        String path = dirPath + "sptRigEvent.html";

        try {
            write(path, sptEventArray, assetManager.open(SPT_RIG_EVENT_TEMPLATE));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return path;
    }

    public static boolean parseSmplRig(String dirPath, Hole hole, AssetManager assetManager) {
        if (hole == null) {
            return false;
        }

//        String[][] smplEventArray = convertSmpl(hole);

//        try {
//            write(dirPath + "smplRigEvent.html", smplEventArray, assetManager.open(SMPL_RIG_EVENT_TEMPLATE));
//        } catch (IOException e) {
//        } catch (IOException e) {
//            e.printStackTrace();
//            return false;
//        }

        return true;
    }

    public static String parseDstRig(String dirPath, DSTRig dstRig, AssetManager assetManager) {
        if (dstRig == null) {
            return null;
        }

        String[][] dstEventArray = convertDst(dstRig);
        String path = dirPath + "dstRigEvent.html";

        try {
            write(path, dstEventArray, assetManager.open(DST_RIG_EVENT_TEMPLATE));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return path;
    }

    private static String[][] convertSpt(SPTRig sptRig) {
        String[][] resultData = new String[1][];
        StringBuffer sb = new StringBuffer();

        sb.append("1").append("#");

        sb.append(formatCalendarDateString(sptRig.getDate(), "MM月")).append("#");
        sb.append(formatCalendarDateString(sptRig.getDate(), "dd日")).append("#");
        sb.append(sptRig.getClassPeopleCount()).append("#");
        sb.append(formatCalendarDateString(sptRig.getStartTime(), "hh时mm分")).append("#");
        sb.append(formatCalendarDateString(sptRig.getEndTime(), "hh时mm分")).append("#");

        //分层
        sb.append(NA).append("#");

        //进尺
        sb.append(sptRig.getPenetrationStartDepth()).append("#");
        sb.append(sptRig.getPenetrationEndDepth()).append("#");

        sb.append(sptRig.getCountStartDepth1() + "," + sptRig.getCountStartDepth2() + "," + sptRig.getCountStartDepth3()).append("#");
        sb.append(sptRig.getCountEndDepth1() + "," + sptRig.getCountEndDepth2() + "," + sptRig.getCountEndDepth3()).append("#");

        //todo
        sb.append(sptRig.getDrillStartDepth1() + "," + sptRig.getDrillStartDepth2() + "," + sptRig.getDrillStartDepth3()).append("#");
        sb.append(sptRig.getDrillEndDepth1() + "," + sptRig.getDrillEndDepth2() + "," + sptRig.getDrillEndDepth3()).append("#");

        //分类
        sb.append(NA).append("#");

        //野外描述
        sb.append(sptRig.getRockColor()).append("#");
        sb.append(sptRig.getRockDensity()).append("#");
        sb.append(NA).append("#");
        sb.append(sptRig.getRockName()).append("#");
        sb.append(sptRig.getRockSaturation()).append("#");
        //光泽
        sb.append(NA).append("#");
        sb.append(NA).append("#");
        sb.append(NA).append("#");

        sb.append(sptRig.getOtherDescription()).append("#");

        sb.append(sptRig.getHitCount1() + "," + sptRig.getHitCount2() + "," + sptRig.getHitCount3()).append("#");

        sb.append(NA).append("#");
        sb.append(NA).append("#");
        sb.append(NA).append("#");

        resultData[0] = convert2Array(sb.toString());

        return resultData;
    }

//    private static String[][] convertSmpl(Hole hole) {
//        ArrayList<SamplingRig> smplRigEvents = new ArrayList<>();
//        for (RigEvent rigEvent : hole.getRigList()) {
//            if (rigEvent instanceof SamplingRig) {
//                smplRigEvents.add((SamplingRig) rigEvent);
//            }
//        }
//        int rows = smplRigEvents.size();
//        String[][] resultData = new String[rows][];
//        for (int i = 0; i < rows; i++) {
//            SamplingRig samplingRig = smplRigEvents.get(i);
//            StringBuffer sb = new StringBuffer();
//            sb.append(samplingRig.getClassPeopleCount()).append("#");
//            sb.append(formatCalendarDateString(samplingRig.getDate(), "yyyy年MM月dd日")).append("#");
//            sb.append(formatCalendarDateString(samplingRig.getStartTime(), "hh时mm分")).append("#");
//            sb.append(formatCalendarDateString(samplingRig.getEndTime(), "hh时mm分")).append("#");
//
//            sb.append(samplingRig.getSampleStatus()).append("#");
//            sb.append(samplingRig.getSamplerType()).append("#");
//            sb.append(samplingRig.getSampleId()).append("#");
//            sb.append(samplingRig.getSampleDiameter()).append("#");
//            sb.append(samplingRig.getSampleStartDepth()).append("#");
//            sb.append(samplingRig.getSampleEndDepth()).append("#");
//            sb.append(samplingRig.getSampleCount()).append("#");
//
//            resultData[i] = convert2Array(sb.toString());
//        }
//        return resultData;
//    }

    private static String[][] convertDst(DSTRig dstRig) {
        ArrayList<DSTRig.DSTDetailInfo> details = dstRig.getDstDetailInfos();

        StringBuffer sb ;
        String[][] resultData = new String[details.size()][];

        for (int index = 0; index < details.size(); index++) {
            sb = new StringBuffer();
            DSTRig.DSTDetailInfo detailInfo = details.get(index);
            sb.append(detailInfo.getPipeLength()).append("#");
            sb.append(detailInfo.getDepth()).append("#");
            sb.append(detailInfo.getLength()).append("#");
            sb.append(detailInfo.getHitCount()).append("#");
            sb.append(NA).append("#");
            sb.append(NA).append("#");
            sb.append(detailInfo.getSaturationDescription().equals("") ? NA : detailInfo.getSaturationDescription()).append("#");
            resultData[index] = convert2Array(sb.toString());
        }

        return resultData;
    }
     
    private static String[][] convertHole(Hole hole) {
        ArrayList<Rig> rigs = hole.getRigList();
        int rows = rigs.size();
        String[][] resultData = new String[rows][];
        for (int i = 0; i < rows; i++) {
            Rig rig = rigs.get(i);
            boolean isNAType = rig instanceof NARig;
            boolean isRegular = rig instanceof RegularRig;

            StringBuffer sb = new StringBuffer();

            sb.append(rig.getClassPeopleCount()).append("#");
            sb.append(formatCalendarDateString(rig.getDate(), "MM月dd日")).append("#");
            sb.append(formatCalendarDateString(rig.getStartTime(), "hh时mm分")).append("#");
            sb.append(formatCalendarDateString(rig.getEndTime(), "hh时mm分")).append("#");
            sb.append(Utility.calculateTimeSpanChinese(rig.getStartTime(), rig.getEndTime())).append("#");

            sb.append(hole.getProjectName()).append("#");

            if (isRegular) {
                RegularRig regularRig = (RegularRig) rigs.get(i);
                //钻杆
                sb.append(regularRig.getPipeNumber()).append("#");
                sb.append(regularRig.getPipeLength()).append("#");
                sb.append(regularRig.getPipeTotalLength()).append("#");

                //岩芯管
                sb.append(regularRig.getRockCorePipeDiameter()).append("#");
                sb.append(regularRig.getRockCorePipeLength()).append("#");

                //钻头
                sb.append(regularRig.getDrillBitType()).append("#");
                sb.append(regularRig.getDrillBitDiameter()).append("#");
                sb.append(regularRig.getDrillBitLength()).append("#");

                //进尺
                sb.append(regularRig.getDrillToolTotalLength()).append("#");
                sb.append(regularRig.getDrillPipeRemainLength()).append("#");
                sb.append(regularRig.getRoundTripMeterageLength()).append("#");
                sb.append(regularRig.getAccumulatedMeterageLength()).append("#");
                //护壁措施
                sb.append("").append("#");
                sb.append("").append("#");
                sb.append("").append("#");
                sb.append("").append("#");
                sb.append("").append("#");

                //孔内情况
                sb.append("").append("#");

                //岩心采取
                sb.append("").append("#");
                sb.append("").append("#");
                sb.append("").append("#");

                //土样
                sb.append("").append("#");
                sb.append("").append("#");
                sb.append("").append("#");
                sb.append("").append("#");

                //水样
                sb.append("").append("#");
                sb.append("").append("#");
                sb.append("").append("#");

                //地层
                sb.append("").append("#");//编号, 四类普通钻,编号加1
                sb.append("").append("#"); //底层深度 本次累计进尺
                sb.append("").append("#");//层厚 本次累计进尺 -上次累计进尺
                sb.append("").append("#"); // 名称及岩性
                sb.append("").append("#"); //岩层等级

                //地下水 只填第一行
                sb.append("").append("#");
                sb.append("").append("#");
                sb.append("").append("#");
                sb.append("").append("#");

                //特殊情况记录 最后一个string 特殊处理
                sb.append(regularRig.getNote().trim().equals("") ? NA : regularRig.getNote()).append("#");

            } else if (isNAType) {
                NARig naRig = (NARig) rigs.get(i);

                //钻杆
                sb.append("").append("#");
                sb.append("").append("#");
                sb.append("").append("#");

                //岩芯管
                sb.append("").append("#");
                sb.append("").append("#");

                //钻头
                sb.append("").append("#");
                sb.append("").append("#");
                sb.append("").append("#");

                //进尺
                sb.append("").append("#");
                sb.append("").append("#");
                sb.append("").append("#");
                sb.append("").append("#");

                //护壁措施
                sb.append("").append("#");
                sb.append("").append("#");
                sb.append("").append("#");
                sb.append("").append("#");
                sb.append("").append("#");

                //孔内情况
                sb.append("").append("#");

                //岩心采取
                sb.append("").append("#");
                sb.append("").append("#");
                sb.append("").append("#");

                //土样
                sb.append("").append("#");
                sb.append("").append("#");
                sb.append("").append("#");
                sb.append("").append("#");

                //水样
                sb.append("").append("#");
                sb.append("").append("#");
                sb.append("").append("#");

                //地层
                sb.append("").append("#");//编号, 四类普通钻,编号加1
                sb.append("").append("#"); //底层深度 本次累计进尺
                sb.append("").append("#");//层厚 本次累计进尺 -上次累计进尺
                sb.append("").append("#"); // 名称及岩性
                sb.append("").append("#"); //岩层等级

                //地下水 只填第一行
                sb.append("").append("#");
                sb.append("").append("#");
                sb.append("").append("#");
                sb.append("").append("#");

                //特殊情况记录 最后一个string 特殊处理
                sb.append(naRig.getNaType().trim().equals("") ? NA : naRig.getNaType()).append("#");

            } else {

            }

            resultData[i] = convert2Array(sb.toString());
        }

        return resultData;
    }

    public static boolean parseToLocal(String outPath, ArrayList<Hole> holes, String srcTemplate) throws IOException {
//        ArrayList<RigEvent> rigEvents = new ArrayList<RigEvent>();
//        ArrayList<SPTRig> sptRigEvents = new ArrayList<SPTRig>();
//        ArrayList<DSTRig> dstRigEvents = new ArrayList<DSTRig>();
//
//        for (int i = 0, len = holes.size(); i < len; i++) {
//            ArrayList<RigEvent> currRigEvents = holes.get(i).getRigList();
//            rigEvents.addAll(currRigEvents);
//            for (int j = 0, size = currRigEvents.size(); j < size; j++) {
//                RigEvent currRigEvent = currRigEvents.get(j);
//                if (currRigEvent instanceof SPTRig) {
//                    sptRigEvents.add((SPTRig) currRigEvent);
//                } else if (currRigEvent instanceof DSTRig) {
//                    dstRigEvents.add((DSTRig) currRigEvent);
//                } else {
//                    // do nothing
//                }
//            }
//        }


        //html output
        for (Hole hole : holes) {
//            String[][] sptRigEventArray = convertSpt(hole);
//            String[][] dstRigEventArray = convertDst(hole);
            File holeDir = new File(outPath + "_" + hole.getHoleId());
            if (!holeDir.exists()) {
                holeDir.mkdir();
            }
            try {
                parseHole(outPath + "/hole_" + hole.getHoleId() + ".html", hole, new FileInputStream(srcTemplate + "/" + BASIC_RIG_EVENT_TEMPLATE));
//                write(outPath + "/spt_" + hole.getHoleId() + ".html", sptRigEventArray, new FileInputStream(srcTemplate + "/" + SPT_RIG_EVENT_TEMPLATE));
//                write(outPath + "/dst_" + hole.getHoleId() + ".html", dstRigEventArray, new FileInputStream(srcTemplate + "/" + DST_RIG_EVENT_TEMPLATE));
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }

        return true;

    }

    public static boolean parseHole(String outPath, Hole hole, InputStream inputStream) throws IOException {
        String[][] data = convertHole(hole);

        boolean isHtml = Utility.verifySuffix(outPath, "html");
        if (!isHtml) {
            return false;
        }

        //读模版文件
        Document doc = Jsoup.parse(inputStream, "UTF-8", "./");
        Element tableBody = doc.getElementById(TBODY_ID);
        if (data != null) {
            // 循环写入行数据
            for (int i = 0, rows = data.length; i < rows; i++) {
                StringBuffer row = new StringBuffer();
                row.append("<tr>");
                // 循环写入列数据
                for (int j = 0, cols = data[i].length; j < cols; j++) {
                    row.append("<td>");
                    String text = data[i][j].equals("null") ? "" : data[i][j];
                    row.append(text);
                    row.append("</td>");
                }
                row.append("</tr>");
                tableBody.append(row.toString());
            }
        }

        Element projectName = doc.getElementById(PROJECTNAME_ID);
        projectName.text(hole.getProjectName());

        Element positionId = doc.getElementById(POSITION_ID);
        String projectStage = hole.getHoleIdPart2();
        positionId.text(projectStage);

        Element mileageId = doc.getElementById(MILEAGE_ID);
        mileageId.text(Utility.formatNumber(hole.getMileage()));

        Element offset = doc.getElementById(HOLE_OFFSET);
        offset.text(String.valueOf(hole.getOffset()));

        Element holeElevation = doc.getElementById(HOLEELEVATION_ID);
        holeElevation.text(String.valueOf(hole.getHoleHeight()));

        Element holeId = doc.getElementById(HOLE_ID);
        holeId.text(hole.getHoleId());

        Element explorationUnit = doc.getElementById(EXPLORATIONUNIT_ID);
        explorationUnit.text(hole.getCompany() == null ? "铁四院工勘院" : hole.getCompany());

        Element machineNumber = doc.getElementById(MACHINENUMBER_ID);
        machineNumber.text(hole.getMachineId() == null ? "4101" : hole.getMachineId());

        Element rigType = doc.getElementById(RIGTYPE_ID);
        rigType.text(hole == null ? "XY-100" : hole.getRigMachineType());

        Element startDate = doc.getElementById(STARTDATE_ID);
        startDate.text(formatCalendarDateString(hole.getStartDate()));

        Element recorderName = doc.getElementById(RECORDER_ID);
        recorderName.text(hole.getRecorder() == null ? "xxx" : hole.getRecorder());

        Element squName = doc.getElementById(SQUAD_ID);
        squName.text(hole.getClassMonitor() == null ? "xxx" : hole.getClassMonitor());

        Element captainName = doc.getElementById(CAPTAIN_ID);
        captainName.text(hole.getMachineMonitor() == null ? "xxx" : hole.getMachineMonitor());
        FileWriter fileWriter = new FileWriter(outPath);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write(doc.outerHtml());
        bufferedWriter.close();
        fileWriter.close();

        return true;

    }

}

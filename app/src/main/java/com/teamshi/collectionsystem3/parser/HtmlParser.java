package com.teamshi.collectionsystem3.parser;

import android.content.res.AssetManager;

import com.teamshi.collectionsystem3.Utility;
import com.teamshi.collectionsystem3.datastructure.Hole;
import com.teamshi.collectionsystem3.datastructure.NARig;
import com.teamshi.collectionsystem3.datastructure.Project;
import com.teamshi.collectionsystem3.datastructure.RegularRig;
import com.teamshi.collectionsystem3.datastructure.Rig;

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


public class HtmlParser {

    public static String PROJECT_OVERVIEW_TEMPLATE = "Project.html";
    public static String BASIC_RIG_EVENT_TEMPLATE = "RigEventTable.html";
    public static String SPT_RIG_EVENT_TEMPLATE = "SPTRigEventTable.html";
    public static String SMPL_RIG_EVENT_TEMPLATE = "SMPLRigEventTable.html";
    public static String DST_RIG_EVENT_TEMPLATE = "DSTRigEventTable.html";


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
            if (row[j].equals("null")) {
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

        FileWriter fileWriter = new FileWriter(outPath);
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


    public static boolean parseSptRig(String dirPath, Hole hole, AssetManager assetManager) {
        if (hole == null) {
            return false;
        }


//        String[][] sptEventArray = convertSpt(hole);
//
//        try {
//            write(dirPath + "sptRigEvent.html", sptEventArray, assetManager.open(SPT_RIG_EVENT_TEMPLATE));
//        } catch (IOException e) {
//            e.printStackTrace();
//            return false;
//        }

        return true;
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

    public static boolean parseDstRig(String dirPath, Hole hole, AssetManager assetManager) {
        if (hole == null) {
            return false;
        }

//        String[][] dstEventArray = convertDst(hole);
//
//        try {
//            write(dirPath + "dstRigEvent.html", dstEventArray, assetManager.open(DST_RIG_EVENT_TEMPLATE));
//        } catch (IOException e) {
//            e.printStackTrace();
//            return false;
//        }

        return true;
    }

    /**
     * private static String[][] convertSpt(Hole hole) {
     * ArrayList<SPTRig> sptRigEvents = new ArrayList<>();
     * for(RigEvent rigEvent : hole.getRigList()){
     * if(rigEvent instanceof  SPTRig) {
     * sptRigEvents.add((SPTRig) rigEvent);
     * }
     * }
     * int rows = sptRigEvents.size();
     * String[][] resultData = new String[rows][];
     * for (int i = 0; i < rows; i++) {
     * SPTRig sptRigEvent = sptRigEvents.get(i);
     * StringBuffer sb = new StringBuffer();
     * sb.append(sptRigEvent.getClassPeopleCount()).append("#");
     * sb.append(formatCalendarDateString(sptRigEvent.getDate(), "yyyy年MM月dd日")).append("#");
     * sb.append(formatCalendarDateString(sptRigEvent.getStartTime(), "hh时mm分")).append("#");
     * sb.append(formatCalendarDateString(sptRigEvent.getEndTime(), "hh时mm分")).append("#");
     * sb.append(sptRigEvent.getPenetration1DepthFrom()).append("#");
     * sb.append(sptRigEvent.getPenetration1DepthTo()).append("#");
     * <p/>
     * sb.append(sptRigEvent.getPenetration1DepthFrom()).append("#");
     * sb.append(sptRigEvent.getPenetration1DepthTo()).append("#");
     * sb.append(sptRigEvent.getPenetration1Count()).append("#");
     * sb.append(sptRigEvent.getRig1DepthFrom()).append("#");
     * sb.append(sptRigEvent.getRig1DepthTo()).append("#");
     * <p/>
     * sb.append(sptRigEvent.getPenetration2DepthFrom()).append("#");
     * sb.append(sptRigEvent.getPenetration2DepthTo()).append("#");
     * sb.append(sptRigEvent.getPenetration2Count()).append("#");
     * sb.append(sptRigEvent.getRig2DepthFrom()).append("#");
     * sb.append(sptRigEvent.getRig2DepthTo()).append("#");
     * <p/>
     * sb.append(sptRigEvent.getPenetration3DepthFrom()).append("#");
     * sb.append(sptRigEvent.getPenetration3DepthTo()).append("#");
     * sb.append(sptRigEvent.getPenetration3Count()).append("#");
     * sb.append(sptRigEvent.getRig3DepthFrom()).append("#");
     * sb.append(sptRigEvent.getRig3DepthTo()).append("#");
     * <p/>
     * sb.append(sptRigEvent.getGroundName()).append("#");
     * sb.append(sptRigEvent.getGroundColor()).append("#");
     * sb.append(sptRigEvent.getGroundSaturation()).append("#");
     * sb.append(sptRigEvent.getCumulativeCount()).append("#");
     * sb.append(sptRigEvent.getSpecialNote()).append("#");
     * <p/>
     * resultData[i] = convert2Array(sb.toString());
     * }
     * return resultData;
     * }
     * <p/>
     * private static String[][] convertSmpl(Hole hole) {
     * ArrayList<SamplingRig> smplRigEvents = new ArrayList<>();
     * for(RigEvent rigEvent : hole.getRigList()){
     * if(rigEvent instanceof SamplingRig) {
     * smplRigEvents.add((SamplingRig) rigEvent);
     * }
     * }
     * int rows = smplRigEvents.size();
     * String[][] resultData = new String[rows][];
     * for (int i = 0; i < rows; i++) {
     * SamplingRig samplingRig = smplRigEvents.get(i);
     * StringBuffer sb = new StringBuffer();
     * sb.append(samplingRig.getClassPeopleCount()).append("#");
     * sb.append(formatCalendarDateString(samplingRig.getDate(), "yyyy年MM月dd日")).append("#");
     * sb.append(formatCalendarDateString(samplingRig.getStartTime(), "hh时mm分")).append("#");
     * sb.append(formatCalendarDateString(samplingRig.getEndTime(), "hh时mm分")).append("#");
     * <p/>
     * sb.append(samplingRig.getSampleStatus()).append("#");
     * sb.append(samplingRig.getSamplerType()).append("#");
     * sb.append(samplingRig.getSampleId()).append("#");
     * sb.append(samplingRig.getSampleDiameter()).append("#");
     * sb.append(samplingRig.getSampleStartDepth()).append("#");
     * sb.append(samplingRig.getSampleEndDepth()).append("#");
     * sb.append(samplingRig.getSampleCount()).append("#");
     * <p/>
     * resultData[i] = convert2Array(sb.toString());
     * }
     * return resultData;
     * }
     * <p/>
     * private static String[][] convertDst(Hole hole) {
     * ArrayList<DSTRig> dstRigEvents = new ArrayList<>();
     * for(RigEvent rigEvent : hole.getRigList()){
     * if(rigEvent instanceof  DSTRig) {
     * dstRigEvents.add((DSTRig) rigEvent);
     * }
     * }
     * ArrayList<String> records = new ArrayList<>();
     * for (int i = 0, len = dstRigEvents.size(); i < len; i++) {
     * DSTRig dstRig = dstRigEvents.get(i);
     * ArrayList<DSTRig.DynamicSoundingEvent> events = dstRig.getDynamicSoundingEvents();
     * for (int j = 0, size = events.size(); j < size; j++) {
     * DSTRig.DynamicSoundingEvent event = events.get(j);
     * StringBuffer sb = new StringBuffer();
     * sb.append(dstRig.getClassPeopleCount()).append("#");
     * sb.append(formatCalendarDateString(dstRig.getDate(), "yyyy年MM月dd日")).append("#");
     * sb.append(formatCalendarDateString(dstRig.getStartTime(), "hh时mm分")).append("#");
     * sb.append(formatCalendarDateString(dstRig.getEndTime(), "hh时mm分")).append("#");
     * sb.append(event.getTotalLength()).append("#");
     * sb.append(event.getDigDepth()).append("#");
     * sb.append(event.getPenetration()).append("#");
     * sb.append(event.getHammeringCount()).append("#");
     * sb.append(dstRig.getGroundName()).append("#");
     * <p/>
     * records.add(sb.toString());
     * }
     * }
     * int rows = records.size();
     * String[][] resultData = new String[rows][];
     * for (int i = 0; i < rows; i++) {
     * resultData[i] = convert2Array(records.get(i));
     * }
     * <p/>
     * return resultData;
     * }
     **/
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
            sb.append(Utility.formatCalendarDateString(rig.getDate(), "MM月dd日")).append("#");
            sb.append(Utility.formatCalendarDateString(rig.getStartTime(), "hh时mm分")).append("#");
            sb.append(Utility.formatCalendarDateString(rig.getEndTime(), "hh时mm分")).append("#");
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
                if (rig.getRigType().equals("Normal")) {
                    sb.append(groundNo++).append("#");//编号, 四类普通钻,编号加1
                } else {
                    sb.append("").append("#");
                }
                sb.append(rig.getGroundDepth()).append("#"); //底层深度 本次累计进尺
                sb.append(rig.getGroundDepthDiff()).append("#");//层厚 本次累计进尺 -上次累计进尺
                sb.append(rig.getGroundNote()).append("#"); // 名称及岩性
                sb.append(rig.getGroundClass()).append("#"); //岩层等级

                //地下水 只填第一行
                sb.append("").append("#");
                sb.append("").append("#");
                sb.append("").append("#");
                sb.append("").append("#");
                sb.append(regularRig.getNote()).append("#");

            }else if(isNAType){

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
        startDate.text(Utility.formatCalendarDateString(hole.getStartDate()));

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

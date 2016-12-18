package com.teamshi.collectionsystem3.parser;

import android.content.res.AssetManager;

import com.teamshi.collectionsystem3.Utility;
import com.teamshi.collectionsystem3.datastructure.DSTRig;
import com.teamshi.collectionsystem3.datastructure.Hole;
import com.teamshi.collectionsystem3.datastructure.OriginalSamplingRig;
import com.teamshi.collectionsystem3.datastructure.OtherSamplingRig;
import com.teamshi.collectionsystem3.datastructure.Project;
import com.teamshi.collectionsystem3.datastructure.Rig;
import com.teamshi.collectionsystem3.datastructure.RigGraphData;
import com.teamshi.collectionsystem3.datastructure.SPTRig;

import org.apache.poi.hpsf.Util;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.teamshi.collectionsystem3.Utility.formatCalendarDateString;


public class HtmlParser extends Parser {

    public static String PROJECT_OVERVIEW_TEMPLATE = "Project.html";
    public static String BASIC_RIG_EVENT_TEMPLATE = "RigEventTable.html";
    public static String SPT_RIG_EVENT_TEMPLATE = "SPTRigEventTable.html";
    public static String DST_RIG_EVENT_TEMPLATE = "DSTRigEventTable.html"; // 动力触探
    public static String SMPL_ORIGIN_RIG_EVENT_TEMPLATE = "SMPLRigEventTable.html";
    public static String SMPL_EARTH_RIG_EVENT_TEMPLATE = "SampleEarth.html";
    public static String SMPL_WATER_RIG_EVENT_TEMPLATE = "SampleWater.html";
    public static String SMPL_ROCK_RIG_EVENT_TEMPLATE = "SampleRock.html";

    public static String RIG_GRAPH_TEMPLATE = "RigGraphTable.html";
    public static String RIG_GRAPH_COVER_TEMPLATE = "RigGraphCover.html";
    public static String RIG_GRAPH_BACK_COVER_TEMPLATE = "RigGraphBackCover.html";

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
    public static String ENDDATE_ID = "endDate";
    public static String HOLE_DESC = "description";

    public static String RECORDER_ID = "recorderName";
    public static String SQUAD_ID = "squadName";
    public static String CAPTAIN_ID = "captainName";


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

        FileWriter fileWriter = new FileWriter(outPath, false);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write(doc.outerHtml());
        bufferedWriter.close();
        fileWriter.close();

        return true;

    }

    public static String parse(String dirPath, Project project, AssetManager assetManager) {
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
                String path = dirPath + hole.getHoleId() + File.separator + "hole_" + hole.getHoleId() + ".html";
                Utility.createFile(path, false);
                parseHole(path, hole, assetManager.open(BASIC_RIG_EVENT_TEMPLATE));
                String tempPath = dirPath + "hole_" + hole.getHoleId() + ".html";
                Utility.copyFile(new FileInputStream(path), new File(tempPath));
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

        String[][] sptEventArray = convertSpt(sptRig, 1, "<br/>");
        String path = dirPath + "sptRigEvent.html";

        try {
            write(path, sptEventArray, assetManager.open(SPT_RIG_EVENT_TEMPLATE));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return path;
    }


    public static String parseOriSmlRig(String dirPath, Hole hole, OriginalSamplingRig originalSamplingRig, AssetManager assetManager) {
        if (originalSamplingRig == null) {
            return null;
        }

        String[][] oriRigEventArray = convertOriSmpl(hole, originalSamplingRig, "<br/>");
        String path = dirPath + "originalSampling.html";

        try {
            write(path, oriRigEventArray, assetManager.open(SMPL_EARTH_RIG_EVENT_TEMPLATE));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return path;
    }

    public static String parseOtherSmlRig(String dirPath, Hole hole, OtherSamplingRig otherSamplingRig, AssetManager assetManager) {
        if (otherSamplingRig == null) {
            return null;
        }

        String path = dirPath + "otherSampling.html";
        try {
            String[][] oriRigEventArray = null;
            String htmlTemp = null;
            switch (otherSamplingRig.getSamplingType()) {
                case "扰动样":
                    oriRigEventArray = convertDistributionSmpl(hole, otherSamplingRig, "<br/>");
                    htmlTemp = SMPL_EARTH_RIG_EVENT_TEMPLATE;
                    break;
                case "岩样":
                    oriRigEventArray = convertRockSmpl(hole, otherSamplingRig, "<br/>");
                    htmlTemp = SMPL_ROCK_RIG_EVENT_TEMPLATE;
                    break;
                case "水样":
                    oriRigEventArray = convertWaterSmpl(hole, otherSamplingRig, "<br/>");
                    htmlTemp = SMPL_WATER_RIG_EVENT_TEMPLATE;
                    break;
                default:
                    break;
            }

            write(path, oriRigEventArray, assetManager.open(htmlTemp));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return path;
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


    public static boolean parseHole(String outPath, Hole hole, InputStream inputStream) throws IOException {
        String[][] data = convertHole(hole, "<br/>");

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
        offset.text(Utility.formatDouble((hole.getOffset())));

        Element holeElevation = doc.getElementById(HOLEELEVATION_ID);
        holeElevation.text(Utility.formatDouble(hole.getHoleHeight()));

        Element holeId = doc.getElementById(HOLE_ID);
        holeId.text(hole.getHoleId());

        Element explorationUnit = doc.getElementById(EXPLORATIONUNIT_ID);
        explorationUnit.text(hole.getCompany() == null ? "铁四院工勘院" : hole.getCompany());

        Element machineNumber = doc.getElementById(MACHINENUMBER_ID);
        machineNumber.text(hole.getMachineId() == null ? "4101" : hole.getMachineId());

        Element rigType = doc.getElementById(RIGTYPE_ID);
        rigType.text(hole.getRigMachineType() == null ? "XY-100" : hole.getRigMachineType());

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

    public static boolean parseRigGraphCover(String outPath, Hole hole, AssetManager assetManager) throws IOException {

        boolean isHtml = Utility.verifySuffix(outPath, "html");
        if (!isHtml) {
            return false;
        }

        File rigGraphCover = Utility.createFile(outPath, false);
        InputStream inputStream = assetManager.open(RIG_GRAPH_COVER_TEMPLATE);

        //读模版文件
        Document doc = Jsoup.parse(inputStream, "UTF-8", "./");

        Element projectName = doc.getElementById(PROJECTNAME_ID);
        projectName.text(hole.getProjectName());


        Element mileageId = doc.getElementById(MILEAGE_ID);
        mileageId.text(Utility.formatNumber(hole.getMileage()));

        Element holeElevation = doc.getElementById(HOLEELEVATION_ID);
        holeElevation.text(Utility.formatDouble(hole.getHoleHeight()));

        Element holeId = doc.getElementById(HOLE_ID);
        holeId.text(hole.getHoleId());

        Element machineNumber = doc.getElementById(MACHINENUMBER_ID);
        machineNumber.text(hole.getMachineId() == null ? "4101" : hole.getMachineId());

        Element rigType = doc.getElementById(RIGTYPE_ID);
        rigType.text(hole.getRigMachineType() == null ? "XY-100" : hole.getRigMachineType());

        Element startDate = doc.getElementById(STARTDATE_ID);
        startDate.text(formatCalendarDateString(hole.getStartDate()));

        Element endDate = doc.getElementById(ENDDATE_ID);
        endDate.text(formatCalendarDateString(hole.getEndDate()));

        Element recorderName = doc.getElementById(RECORDER_ID);
        recorderName.text(hole.getRecorder() == null ? "xxx" : hole.getRecorder());

        Element squName = doc.getElementById(SQUAD_ID);
        squName.text(hole.getClassMonitor() == null ? "xxx" : hole.getClassMonitor());

        Element captainName = doc.getElementById(CAPTAIN_ID);
        captainName.text(hole.getMachineMonitor() == null ? "xxx" : hole.getMachineMonitor());

        FileWriter fileWriter = new FileWriter(rigGraphCover);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write(doc.outerHtml());
        bufferedWriter.close();
        fileWriter.close();

        return true;
    }

    public static boolean parseRigGraphBackCover(String outPath, Hole hole, AssetManager assetManager) throws IOException {

        boolean isHtml = Utility.verifySuffix(outPath, "html");
        if (!isHtml) {
            return false;
        }

        File rigGraphBackCover = Utility.createFile(outPath, false);
        InputStream inputStream = assetManager.open(RIG_GRAPH_BACK_COVER_TEMPLATE);

        //读模版文件
        Document doc = Jsoup.parse(inputStream, "UTF-8", "./");

        Element projectName = doc.getElementById(HOLE_DESC);
        projectName.text(hole.getNote());

        FileWriter fileWriter = new FileWriter(rigGraphBackCover);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write(doc.outerHtml());
        bufferedWriter.close();
        fileWriter.close();

        return true;

    }

    public static String parseRigGraphTable(String outPath, Hole hole, AssetManager assetManager) throws IOException {

        boolean isHtml = Utility.verifySuffix(outPath, "html");
        if (!isHtml) {
            return null;
        }

        File rigGraph = Utility.createFile(outPath, false);
        InputStream inputStream = assetManager.open(RIG_GRAPH_TEMPLATE);

        //读模版文件
        Document doc = Jsoup.parse(inputStream, "UTF-8", "./");

        RigGraphData rigGraphData = hole.getRigGraphData();

        //日期
        for (RigGraphData.GraphNode node : rigGraphData.getDateNodeList()) {
            Element el = doc.getElementById("date").appendElement("div");
            el.text(node.getContent());
            el.attr("style", "height:" + node.getHeight() + "rem;");
        }


        //岩芯
        for (RigGraphData.GraphNode node : rigGraphData.getRockCoreNodeList()) {
            Element el = doc.getElementById("rockCore").appendElement("div");
            el.text(node.getContent());
            el.attr("style", "height:" + node.getHeight() + "rem;");
        }

        //水样
        for (RigGraphData.GraphNode node : rigGraphData.getWaterSamplingNodeList()) {
            Element el = doc.getElementById("waterSampling").appendElement("div");
            el.text(node.getContent());
            el.attr("style", "height:" + node.getHeight() + "rem;");
        }

        //原样
        for (RigGraphData.GraphNode node : rigGraphData.getOriginalSamplingNodeList()) {
            Element el = doc.getElementById("originalSampling").appendElement("div");
            el.text(node.getContent());
            el.attr("style", "height:" + node.getHeight() + "rem;");
        }

        //扰动样
        for (RigGraphData.GraphNode node : rigGraphData.getDisturbanceSamplingNodeList()) {
            Element el = doc.getElementById("distSampling").appendElement("div");
            el.text(node.getContent());
            el.attr("style", "height:" + node.getHeight() + "rem;");
        }

        //下套管
        for (RigGraphData.GraphNode node : rigGraphData.getTrNodeList()) {
            Element el = doc.getElementById("trNode").appendElement("div");
            el.text(node.getContent());
            el.attr("style", "height:" + node.getHeight() + "rem;");
        }

        //初始水位
        RigGraphData.GraphNode initialWaterDepthNode = rigGraphData.getInitialWaterDepthNode();
        Element el = doc.getElementById("initWater").appendElement("div");
        el.text(initialWaterDepthNode.getContent());
        el.attr("style", "height:" + initialWaterDepthNode.getHeight() + "rem;");

        //初始水位
        RigGraphData.GraphNode finalWaterDepthNode = rigGraphData.getFinalWaterDepthNode();
        el = doc.getElementById("finalWater").appendElement("div");
        el.text(finalWaterDepthNode.getContent());
        el.attr("style", "height:" + finalWaterDepthNode.getHeight() + "rem;");

        //水位稳定时间
        RigGraphData.GraphNode waterDepthDateNode = rigGraphData.getWaterDepthDateNode();
        el = doc.getElementById("waterDate").appendElement("div");
        el.text(waterDepthDateNode.getContent());
        el.attr("style", "height:" + waterDepthDateNode.getHeight() + "rem;");

        List<RigGraphData.RigNode> rigNodes = rigGraphData.getRigNodeList();
        doc.getElementsByClass("flex-row").attr("style", "height:" + 30 * rigNodes.size() + "px");
        for (RigGraphData.RigNode rigNode : rigNodes) {
            el = doc.getElementById("rockCorePer").appendElement("div");
            el.text(String.valueOf(rigNode.getRockPickPercentage() * 100));
            el.attr("style", "height:" + rigNode.getHeight() + "rem;");

            //钻头直径
            el = doc.getElementById("drillDiameter").appendElement("div");
            el.text(String.valueOf(Utility.formatDouble(rigNode.getDrillDiameter())));
            el.attr("style", "height:" + rigNode.getHeight() + "rem;");

            //岩芯编号
            el = doc.getElementById("rockCoreIndex").appendElement("div");
            el.text(String.valueOf(rigNode.getRockLayoutIndex()));
            el.attr("style", "height:" + rigNode.getHeight() + "rem;");

            //岩芯长度
            el = doc.getElementById("rockCoreLength").appendElement("div");
            el.text(String.valueOf(Utility.formatDouble(rigNode.getRockPickLength())));
            el.attr("style", "height:" + rigNode.getHeight() + "rem;");

            //岩层描述
            el = doc.getElementById("desc").appendElement("div");
            el.text(String.valueOf(rigNode.getDescription()));
            el.attr("style", "height:" + rigNode.getHeight() + "rem;");

            //钻进深度至
            el = doc.getElementById("endDepth").appendElement("div");
            el.text(String.valueOf(Utility.formatDouble(rigNode.getEndDepth())));
            el.attr("style", "height:" + rigNode.getHeight() + "rem;");

            //钻进深度至
            el = doc.getElementById("depthAll").appendElement("div");
            el.text(String.valueOf(Utility.formatDouble(rigNode.getEndDepth())));
            el.attr("style", "height:" + rigNode.getHeight() + "rem;");

            //钻进深度由
            el = doc.getElementById("startDepth").appendElement("div");
            el.text(String.valueOf(Utility.formatDouble(rigNode.getStartDepth())));
            el.attr("style", "height:" + rigNode.getHeight() + "rem;");

            //层底深度
            el = doc.getElementById("layerEndDepth").appendElement("div");
            el.text(String.valueOf(Utility.formatDouble(rigNode.getLayoutEndDepth())));
            el.attr("style", "height:" + rigNode.getHeight() + "rem;");

            //回次进尺
            el = doc.getElementById("roundTrip").appendElement("div");
            el.text(String.valueOf(Utility.formatDouble(rigNode.getRoundTripDepth())));
            el.attr("style", "height:" + rigNode.getHeight() + "rem;");

            //类型 TODO
            el = doc.getElementById("legend").appendElement("div");
            el.text(String.valueOf("ff"));
            el.attr("class", getEarthType(rigNode.getDrillType()));
            el.attr("style", "height:" + rigNode.getHeight() + "rem;" + ";color:rgba(255,255,255,0)");
        }

        FileWriter fileWriter = new FileWriter(rigGraph);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write(doc.outerHtml());
        bufferedWriter.close();
        fileWriter.close();

        return outPath;
    }

    private static String getEarthType(String drillType) {
        String type = "zatiantu";
        switch (drillType.trim()) {
            case "黏土":
                type = "niantu";
                break;
            case "杂填土":
                type = "zatiantu";
                break;
            case "素填土":
                type = "sutiantu";
                break;
            case "吹填土":
                type = "chuitiantu";
                break;
            case "粉质黏土":
                type = "fenzhiniantu";
                break;
            case "粉土":
                type = "fentu";
                break;
            case "粉砂":
                type = "fensha";
                break;
            case "细砂":
                type = "xisha";
                break;
            case "中砂":
                type = "zhongsha";
                break;
            case "粗砂":
                type = "cusha";
                break;
            case "砾砂":
                type = "lisha";
                break;
            case "漂石":
                type = "piaoshi";
                break;
            case "块石":
                type = "kuaishi";
                break;
            case "卵石":
                type = "ruanshi";
                break;
            case "碎石":
                type = "suishi";
                break;
            case "粗圆砾":
                type = "cuyuanli";
                break;
            case "粗角砾":
                type = "cujiaoli";
                break;
            case "细角砾":
                type = "xijiaoli";
                break;
            case "泥岩":
                type = "niyan";
                break;
            case "砂岩":
                type = "shayan";
                break;
            case "灰岩":
                type = "huiyan";
                break;
            case "花岗岩":
                type = "huagangyan";
                break;
            default:
                break;
        }
        return type;
    }

    public static String parseSptRigs(String dirPath, Project project, List<Rig> rigs, AssetManager assetManager) {
        if (project == null) {
            return null;
        }

        ArrayList<SPTRig> sptRigs = new ArrayList<>();
        for (Rig rig : rigs) {
            if (rig instanceof SPTRig) {
                sptRigs.add((SPTRig) rig);
            }
        }

        if (sptRigs.isEmpty()) {
            return null;
        }

        String[][] sptResults = null;
        for (int i = 0, len = sptRigs.size(); i < len; i++) {
            SPTRig sptRig = sptRigs.get(i);
            String[][] result = convertSpt(sptRig, (i + 1), "<br/>");
            sptResults = sptResults == null ? result : Utility.concat(sptResults, result);
        }

        String path = dirPath + "sptRigs.html";

        try {
            write(path, sptResults, assetManager.open(SPT_RIG_EVENT_TEMPLATE));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return path;
    }

    public static String parseDstRigs(String dirPath, Project project, List<Rig> rigs, AssetManager assetManager) {
        if (project == null) {
            return null;
        }

        ArrayList<DSTRig> dstRigs = new ArrayList<>();
        for (Rig rig : rigs) {
            if (rig instanceof DSTRig) {
                DSTRig dst = (DSTRig) rig;
                dstRigs.add(dst);
            }
        }

        if (dstRigs.isEmpty()) {
            return null;
        }

        String[][] dstResults = null;
        for (DSTRig dstRig : dstRigs) {
            String[][] result = convertDst(dstRig);
            dstResults = null == dstResults ? result : Utility.concat(dstResults, result);
        }

        String path = dirPath + "dstRigs.html";

        try {
            write(path, dstResults, assetManager.open(DST_RIG_EVENT_TEMPLATE));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return path;
    }

    public static String parseEarthSmlRigs(String dirPath, Project project, AssetManager assetManager) {
        if (project == null) {
            return null;
        }

        HashMap<Hole, OtherSamplingRig.OtherSamplingDetail> distributionDetails = new HashMap<>();
        HashMap<Hole, OriginalSamplingRig> originalSampling = new HashMap<>();
        for (Hole hole : project.getHoleList()) {
            for (Rig rig : hole.getRigIndexViewList()) {
                if (rig instanceof OtherSamplingRig.OtherSamplingDetail) {
                    OtherSamplingRig.OtherSamplingDetail detail = (OtherSamplingRig.OtherSamplingDetail) rig;
                    if (detail.getSamplingType().equals("扰动样")) {
                        distributionDetails.put(hole, detail);
                    }
                } else if (rig instanceof OriginalSamplingRig) {
                    OriginalSamplingRig originalSamplingRig = (OriginalSamplingRig) rig;
                    originalSampling.put(hole, originalSamplingRig);
                }
            }
        }

        if (distributionDetails.isEmpty()) {
            return null;
        }

        String[][] earthResults;
        String[][] distributionResults = null;
        for (Map.Entry<Hole, OtherSamplingRig.OtherSamplingDetail> entry : distributionDetails.entrySet()) {
            String[][] result = convertEarthSmplDetail(entry.getKey(), entry.getValue(), "<BR/>");
            distributionResults = null == distributionResults ? result : Utility.concat(distributionResults, result);
        }

        String[][] originalSmplResults = null;
        for (Map.Entry<Hole, OriginalSamplingRig> entry : originalSampling.entrySet()) {
            String[][] result = convertOriSmpl(entry.getKey(), entry.getValue(), "<BR/>");
            originalSmplResults = null == originalSmplResults ? result : Utility.concat(originalSmplResults, result);
        }

        distributionResults = distributionResults == null ? new String[0][] : distributionResults;
        originalSmplResults = originalSmplResults == null ? new String[0][] : originalSmplResults;

        earthResults = Utility.concat(distributionResults, originalSmplResults);

        String path = dirPath + "smplEarthRigs.html";

        try {
            write(path, earthResults, assetManager.open(SMPL_EARTH_RIG_EVENT_TEMPLATE));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return path;
    }

    public static String parseWaterSmlRigs(String dirPath, Project project, AssetManager assetManager) {
        if (project == null) {
            return null;
        }

        HashMap<Hole, OtherSamplingRig.OtherSamplingDetail> details = new HashMap<>();
        for (Hole hole : project.getHoleList()) {
            for (Rig rig : hole.getRigIndexViewList()) {
                if (rig instanceof OtherSamplingRig.OtherSamplingDetail) {
                    OtherSamplingRig.OtherSamplingDetail detail = (OtherSamplingRig.OtherSamplingDetail) rig;
                    if (detail.getSamplingType().equals("水样")) {
                        details.put(hole, detail);
                    }
                }
            }
        }

        if (details.isEmpty()) {
            return null;
        }

        String[][] smplWaterResults = null;
        for (Map.Entry<Hole, OtherSamplingRig.OtherSamplingDetail> entry : details.entrySet()) {
            String[][] result = convertWaterSmplDetail(entry.getKey(), entry.getValue(), "<BR/>");
            smplWaterResults = null == smplWaterResults ? result : Utility.concat(smplWaterResults, result);
        }

        String path = dirPath + "smplWaterRigs.html";

        try {
            write(path, smplWaterResults, assetManager.open(SMPL_WATER_RIG_EVENT_TEMPLATE));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return path;
    }

    public static String parseRockSmlRigs(String dirPath, Project project, AssetManager assetManager) {
        if (project == null) {
            return null;
        }

        HashMap<Hole, OtherSamplingRig.OtherSamplingDetail> details = new HashMap<>();
        for (Hole hole : project.getHoleList()) {
            for (Rig rig : hole.getRigIndexViewList()) {
                if (rig instanceof OtherSamplingRig.OtherSamplingDetail) {
                    OtherSamplingRig.OtherSamplingDetail detail = (OtherSamplingRig.OtherSamplingDetail) rig;
                    if (detail.getSamplingType().equals("岩样")) {
                        details.put(hole, detail);
                    }
                }
            }
        }

        if (details.isEmpty()) {
            return null;
        }

        String[][] smplRockResults = null;
        for (Map.Entry<Hole, OtherSamplingRig.OtherSamplingDetail> entry : details.entrySet()) {
            String[][] result = convertRockSmplDetail(entry.getKey(), entry.getValue(), "<BR/>");
            smplRockResults = null == smplRockResults ? result : Utility.concat(smplRockResults, result);
        }

        String path = dirPath + "smplRockRigs.html";

        try {
            write(path, smplRockResults, assetManager.open(SMPL_ROCK_RIG_EVENT_TEMPLATE));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return path;
    }

    public static void main(String[] args) throws IOException {
        RigGraphData rigGraphData = RigGraphData.generateTestInstance();
        String outPath = "/Users/yueyue/Desktop/ff.html";

        File rigGraph = Utility.createFile(outPath, false);
        InputStream inputStream = new FileInputStream("/Users/yueyue/Desktop/" + RIG_GRAPH_TEMPLATE);

        //读模版文件
        Document doc = Jsoup.parse(inputStream, "UTF-8", "./");

        //日期
        for (RigGraphData.GraphNode node : rigGraphData.getDateNodeList()) {
            Element el = doc.getElementById("date").appendElement("div");
            el.text(node.getContent());
            el.attr("style", "height:" + node.getHeight() + "rem;");
        }

        //岩芯
        for (RigGraphData.GraphNode node : rigGraphData.getRockCoreNodeList()) {
            Element el = doc.getElementById("rockCore").appendElement("div");
            el.text(node.getContent());
            el.attr("style", "height:" + node.getHeight() + "rem;");
        }

        //水样
        for (RigGraphData.GraphNode node : rigGraphData.getWaterSamplingNodeList()) {
            Element el = doc.getElementById("waterSampling").appendElement("div");
            el.text(node.getContent());
            el.attr("style", "height:" + node.getHeight() + "rem;");
        }

        //原样
        for (RigGraphData.GraphNode node : rigGraphData.getOriginalSamplingNodeList()) {
            Element el = doc.getElementById("originalSampling").appendElement("div");
            el.text(node.getContent());
            el.attr("style", "height:" + node.getHeight() + "rem;");
        }

        //扰动样
        for (RigGraphData.GraphNode node : rigGraphData.getDisturbanceSamplingNodeList()) {
            Element el = doc.getElementById("distSampling").appendElement("div");
            el.text(node.getContent());
            el.attr("style", "height:" + node.getHeight() + "rem;");
        }

        //下套管
        for (RigGraphData.GraphNode node : rigGraphData.getTrNodeList()) {
            Element el = doc.getElementById("trNode").appendElement("div");
            el.text(node.getContent());
            el.attr("style", "height:" + node.getHeight() + "rem;");
        }

        //初始水位
        RigGraphData.GraphNode initialWaterDepthNode = rigGraphData.getInitialWaterDepthNode();
        Element el = doc.getElementById("initWater").appendElement("div");
        el.text(initialWaterDepthNode.getContent());
        el.attr("style", "height:" + initialWaterDepthNode.getHeight() + "rem;");

        //初始水位
        RigGraphData.GraphNode finalWaterDepthNode = rigGraphData.getFinalWaterDepthNode();
        el = doc.getElementById("finalWater").appendElement("div");
        el.text(finalWaterDepthNode.getContent());
        el.attr("style", "height:" + finalWaterDepthNode.getHeight() + "rem;");

        //水位稳定时间
        RigGraphData.GraphNode waterDepthDateNode = rigGraphData.getWaterDepthDateNode();
        el = doc.getElementById("waterDate").appendElement("div");
        el.text(waterDepthDateNode.getContent());
        el.attr("style", "height:" + waterDepthDateNode.getHeight() + "rem;");

        //TODO rigNodeList
        List<RigGraphData.RigNode> rigNodes = rigGraphData.getRigNodeList();
        doc.getElementsByClass("flex-row").attr("style", "height:" + 30 * rigNodes.size() + "px");
        for (RigGraphData.RigNode rigNode : rigNodes) {
            el = doc.getElementById("rockCorePer").appendElement("div");
            el.text(String.valueOf(rigNode.getRockPickPercentage() * 100));
            el.attr("style", "height:" + rigNode.getHeight() + "rem;");

            //钻头直径
            el = doc.getElementById("drillDiameter").appendElement("div");
            el.text(String.valueOf(rigNode.getDrillDiameter()));
            el.attr("style", "height:" + rigNode.getHeight() + "rem;");

            //岩芯编号
            el = doc.getElementById("rockCoreIndex").appendElement("div");
            el.text(String.valueOf(rigNode.getRockLayoutIndex()));
            el.attr("style", "height:" + rigNode.getHeight() + "rem;");

            //岩芯长度
            el = doc.getElementById("rockCoreLength").appendElement("div");
            el.text(String.valueOf(rigNode.getRockPickLength()));
            el.attr("style", "height:" + rigNode.getHeight() + "rem;");

            //岩层描述
            el = doc.getElementById("desc").appendElement("div");
            el.text(String.valueOf(rigNode.getDescription()));
            el.attr("style", "height:" + rigNode.getHeight() + "rem;");

            //钻进深度至
            el = doc.getElementById("endDepth").appendElement("div");
            el.text(String.valueOf(rigNode.getEndDepth()));
            el.attr("style", "height:" + rigNode.getHeight() + "rem;");

            //钻进深度至
            el = doc.getElementById("depthAll").appendElement("div");
            el.text(String.valueOf(rigNode.getEndDepth()));
            el.attr("style", "height:" + rigNode.getHeight() + "rem;");

            //钻进深度由
            el = doc.getElementById("startDepth").appendElement("div");
            el.text(String.valueOf(rigNode.getStartDepth()));
            el.attr("style", "height:" + rigNode.getHeight() + "rem;");

            //层底深度
            el = doc.getElementById("layerEndDepth").appendElement("div");
            el.text(String.valueOf(rigNode.getLayoutEndDepth()));
            el.attr("style", "height:" + rigNode.getHeight() + "rem;");

            //回次进尺
            el = doc.getElementById("roundTrip").appendElement("div");
            el.text(String.valueOf(rigNode.getRoundTripDepth()));
            el.attr("style", "height:" + rigNode.getHeight() + "rem;");

            //类型 TODO
            el = doc.getElementById("legend").appendElement("div");
            el.text(String.valueOf("ff"));
            el.attr("class", "earth_fill");
            el.attr("style", "height:" + rigNode.getHeight() + "rem;" + ";color:rgba(255,255,255,0)");
        }


        FileWriter fileWriter = new FileWriter(rigGraph);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write(doc.outerHtml());
        bufferedWriter.close();
        fileWriter.close();
    }

}

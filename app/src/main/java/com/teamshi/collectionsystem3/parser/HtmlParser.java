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

import static com.teamshi.collectionsystem3.IOManager.APP_TEMP;
import static com.teamshi.collectionsystem3.IOManager.getHolePath;
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
    public static String ENGIN_ID = "enginType";
    public static String PUMP_ID = "pumpType";
    public static String DESIGN_DEPTH_ID = "holeDepth";
    public static String ACTURAL_DEPTH_ID = "actualDepth";

    public static String RIGTYPE_ID = "rigType";
    public static String STARTDATE_ID = "startDate";
    public static String ENDDATE_ID = "endDate";
    public static String DATE_RANGE_ID = "dateRange";

    public static String HOLE_DESC = "description";

    public static String RECORDER_ID = "recorderName";
    public static String SQUAD_ID = "squadName";
    public static String CAPTAIN_ID = "captainName";


//    public static boolean write(String outPath, String[][] data, InputStream inputStream) throws IOException {
//        String fileType = outPath.substring(outPath.lastIndexOf(".") + 1, outPath.length());
//        if (!fileType.equals("html")) {
//            System.out.println("您的文档格式不正确(非html)！");
//            return false;
//        }
//
//        //读模版文件
//        Document doc = Jsoup.parse(inputStream, "UTF-8", "./");
//        Element tbody = doc.getElementById(TBODY_ID);
//        if (data != null) {
//            // 循环写入行数据
//            for (int i = 0, rows = data.length; i < rows; i++) {
//                StringBuffer row = new StringBuffer();
//                row.append("<tr>");
//                // 循环写入列数据
//                for (int j = 0, cols = data[i].length; j < cols; j++) {
//                    row.append("<td>");
//                    String text = data[i][j].equals("null") ? "" : data[i][j];
//                    row.append(text);
//                    row.append("</td>");
//                }
//                row.append("</tr>");
//                tbody.append(row.toString());
//            }
//        }
//
//        FileWriter fileWriter = new FileWriter(outPath, false);
//        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
//        bufferedWriter.write(doc.outerHtml());
//        bufferedWriter.close();
//        fileWriter.close();
//
//        return true;
//
//    }

    public static boolean writeWithHole(String outPath, String[][] data, Hole hole, InputStream inputStream) throws IOException {
        String fileType = outPath.substring(outPath.lastIndexOf(".") + 1, outPath.length());
        if (!fileType.equals("html")) {
            System.out.println("您的文档格式不正确(非html)！");
            return false;
        }

        //读模版文件
        Document doc = Jsoup.parse(inputStream, "UTF-8", "./");
        if (hole != null) {
            Element projectName = doc.getElementById(PROJECTNAME_ID);
            if (projectName != null) {
                projectName.text(hole.getProjectName());
            }

            Element positionId = doc.getElementById(POSITION_ID);
            if (positionId != null) {
                String projectStage = hole.getHoleIdPart2();
                positionId.text(projectStage);
            }

            Element holeElevation = doc.getElementById(HOLEELEVATION_ID);
            if (holeElevation != null) {
                holeElevation.text(Utility.formatDouble(hole.getHoleHeight()));
            }

            Element holeId = doc.getElementById(HOLE_ID);
            if (holeId != null) {
                holeId.text(hole.getHoleId());
            }

            Element startDate = doc.getElementById(STARTDATE_ID);
            if (startDate != null) {
                startDate.text(formatCalendarDateString(hole.getStartDate()));
            }

            Element endDate = doc.getElementById(ENDDATE_ID);
            if (endDate != null) {
                endDate.text(formatCalendarDateString(hole.getEndDate()));
            }

            Element mileageId = doc.getElementById(MILEAGE_ID);
            if (mileageId != null) {
                mileageId.text(Utility.formatNumber(hole.getMileage()));
            }

            Element offset = doc.getElementById(HOLE_OFFSET);
            if (offset != null) {
                offset.text(Utility.formatDouble((hole.getOffset())));
            }

            Element explorationUnit = doc.getElementById(EXPLORATIONUNIT_ID);
            if (explorationUnit != null) {
                explorationUnit.text(hole.getCompany() == null ? "铁四院工勘院" : hole.getCompany());
            }

            Element machineNumber = doc.getElementById(MACHINENUMBER_ID);
            if (machineNumber != null) {
                machineNumber.text(hole.getMachineId() == null ? "4101" : hole.getMachineId());
            }

            Element rigType = doc.getElementById(RIGTYPE_ID);
            if (rigType != null) {
                rigType.text(hole.getRigMachineType() == null ? "XY-100" : hole.getRigMachineType());
            }

            Element recorderName = doc.getElementById(RECORDER_ID);
            if (recorderName != null) {
                recorderName.text(hole.getRecorder() == null ? "" : hole.getRecorder());
            }

            Element squName = doc.getElementById(SQUAD_ID);
            if (squName != null) {
                squName.text(hole.getClassMonitor() == null ? "" : hole.getClassMonitor());
            }

            Element captainName = doc.getElementById(CAPTAIN_ID);
            if (captainName != null) {
                captainName.text(hole.getMachineMonitor() == null ? "" : hole.getMachineMonitor());
            }

        }

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


    public static String parse(Project project, AssetManager assetManager) {
        List<Hole> holes = project.getHoleList();
        String projectPath = APP_TEMP + "project_" + project.getProjectName() + ".html";
        String relativeDataPaths = "../Data/";
        InputStream inputStream;
        try {
            File projectPreviewFile = Utility.createFile(projectPath, false);
            inputStream = assetManager.open(PROJECT_OVERVIEW_TEMPLATE);
            //读模版文件
            Document doc = Jsoup.parse(inputStream, "UTF-8", "./");
            Element header = doc.getElementById(PROJECTNAME_ID);
            header.appendText("项目名称: " + project.getProjectName());

            Element list = doc.getElementById(PROJECTNAME_LIST).append("<ul></ul>");
            for (Hole hole : holes) {
                // generate htmls
                String absoluteHolepath = getHolePath(hole);
                Utility.createFile(absoluteHolepath, false);

                parseHole(absoluteHolepath + "hole_" + hole.getHoleId() + ".html", hole, assetManager.open(BASIC_RIG_EVENT_TEMPLATE));
                HtmlParser.parseEarthSmlRigs(absoluteHolepath + "smplEarthRigs.html", hole, assetManager);
                HtmlParser.parseWaterSmlRigs(absoluteHolepath + "smplWaterRigs.html", hole, assetManager);
                HtmlParser.parseRockSmlRigs(absoluteHolepath + "smplRockRigs.html", hole, assetManager);
                HtmlParser.parseSptRigs(absoluteHolepath + "sptRigs.html", hole, assetManager);
                HtmlParser.parseDstRigs(absoluteHolepath + "dstRigs.html", hole, hole.getRigIndexViewList(), assetManager);
                HtmlParser.parseRigGraphTable(absoluteHolepath + "rigGraph.html", hole, assetManager);
                HtmlParser.parseRigGraphCover(absoluteHolepath + "rigGraphCover.html", hole, assetManager);
                HtmlParser.parseRigGraphBackCover(absoluteHolepath + "rigGraphBackCover.html", hole, assetManager);

                // 原状样
                ArrayList<Rig> rigs = hole.getRigIndexViewList();
                if (rigs != null && (rigs.size() > 0)) {
                    for (Rig rig : rigs) {
                        if (rig instanceof OriginalSamplingRig)
                            HtmlParser.parseOriSmlRig(absoluteHolepath + "originalSampling.html", hole, (OriginalSamplingRig) rig, assetManager);
                        if (rig instanceof OtherSamplingRig)
                            HtmlParser.parseOtherSmlRig(absoluteHolepath + "otherSampling.html", hole, (OtherSamplingRig) rig, assetManager);
                    }
                }

                String holePath = relativeDataPaths + hole.getProjectName() + File.separator + hole.getHoleId() + File.separator;
                String allRigsPath = holePath + "hole_" + hole.getHoleId() + ".html";
                String sptPath = holePath + "sptRigs.html";
                String dstPath = holePath + "dstRigs.html";
                String smpleEarthRigPath = holePath + "smplEarthRigs.html";
                String smpleWaterRigPath = holePath + "smplWaterRigs.html";
                String smpleRockRigPath = holePath + "smplRockRigs.html";

                Element holeNode = doc.createElement("li");
                holeNode.text(hole.getHoleId());
                Element holeNodeList = doc.createElement("ul");
                Element allRigsNode = doc.createElement("li");
                allRigsNode.appendElement("a").attr("href", allRigsPath).text("原始记录表");
                Element sptNode = doc.createElement("li");
                sptNode.appendElement("a").attr("href", sptPath).text("标准贯入表");
                Element dstNode = doc.createElement("li");
                dstNode.appendElement("a").attr("href", dstPath).text("动力触探表");
                Element smpleEarthRigNode = doc.createElement("li");
                smpleEarthRigNode.appendElement("a").attr("href", smpleEarthRigPath).text("土样表");
                Element smpleWaterRigNode = doc.createElement("li");
                smpleWaterRigNode.appendElement("a").attr("href", smpleWaterRigPath).text("水样表");
                Element smpleRockRigNode = doc.createElement("li");
                smpleRockRigNode.appendElement("a").attr("href", smpleRockRigPath).text("岩样表");

                holeNodeList.appendChild(allRigsNode);
                holeNodeList.appendChild(sptNode);
                holeNodeList.appendChild(dstNode);
                holeNodeList.appendChild(smpleEarthRigNode);
                holeNodeList.appendChild(smpleWaterRigNode);
                holeNodeList.appendChild(smpleRockRigNode);

                holeNode.appendChild(holeNodeList);
                list.appendChild(holeNode);
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


        return projectPath;
    }


    public static String parseSptRig(String dirPath, Hole hole, SPTRig sptRig, AssetManager assetManager) {
        if (hole == null || sptRig == null) {
            return null;
        }

        String[][] sptEventArray = convertSpt(sptRig, 1, "<br/>");
        String path = dirPath + "sptRigEvent.html";

        try {
            writeWithHole(path, sptEventArray, hole, assetManager.open(SPT_RIG_EVENT_TEMPLATE));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return path;
    }


    public static String parseOriSmlRig(String path, Hole hole, OriginalSamplingRig originalSamplingRig, AssetManager assetManager) {
        if (originalSamplingRig == null) {
            return null;
        }

        String[][] oriRigEventArray = convertOriSmpl(hole, originalSamplingRig, "<br/>");

        try {
            writeWithHole(path, oriRigEventArray, hole, assetManager.open(SMPL_EARTH_RIG_EVENT_TEMPLATE));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return path;
    }

    public static String parseOtherSmlRig(String path, Hole hole, OtherSamplingRig otherSamplingRig, AssetManager assetManager) {
        if (otherSamplingRig == null) {
            return null;
        }

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

            writeWithHole(path, oriRigEventArray, hole, assetManager.open(htmlTemp));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return path;
    }


    public static String parseDstRig(String dirPath, Hole hole, DSTRig dstRig, AssetManager assetManager) {
        if (dstRig == null) {
            return null;
        }

        String[][] dstEventArray = convertDst(dstRig);
        String path = dirPath + "dstRigEvent.html";

        try {
            writeWithHole(path, dstEventArray, hole, assetManager.open(DST_RIG_EVENT_TEMPLATE));
            Document doc = Jsoup.parse(new FileInputStream(path), "UTF-8", "./");
            Element dateRange = doc.getElementById(DATE_RANGE_ID);
            if (dateRange != null) {
                dateRange.text(Utility.formatCalendarDateString(dstRig.getStartTime()) + "-" + Utility.formatCalendarDateString(dstRig.getEndTime()));
            }
            FileWriter fileWriter = new FileWriter(path, false);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(doc.outerHtml());
            bufferedWriter.close();
            fileWriter.close();
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
        if(positionId != null) {
            String projectStage = hole.getHoleIdPart2();
            positionId.text(projectStage);
        }

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
        recorderName.text(hole.getRecorder() == null ? "" : hole.getRecorder());

        Element squName = doc.getElementById(SQUAD_ID);
        squName.text(hole.getClassMonitor() == null ? "" : hole.getClassMonitor());

        Element captainName = doc.getElementById(CAPTAIN_ID);
        captainName.text(hole.getMachineMonitor() == null ? "" : hole.getMachineMonitor());
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

        Element pumpType = doc.getElementById(PUMP_ID);
        pumpType.text(hole.getPumpType() == null ? "" : hole.getPumpType());

        Element rigType = doc.getElementById(RIGTYPE_ID);
        rigType.text(hole.getRigMachineType() == null ? "XY-100" : hole.getRigMachineType());

        Element enginType = doc.getElementById(ENGIN_ID);
        enginType.text(hole.getEngineType() == null ? "" : hole.getEngineType());

        Element startDate = doc.getElementById(STARTDATE_ID);
        startDate.text(formatCalendarDateString(hole.getStartDate()));

        Element designDepth = doc.getElementById(DESIGN_DEPTH_ID);
        designDepth.text(Utility.formatDouble(hole.getHoleDepth()));

        Element acturalDepth = doc.getElementById(ACTURAL_DEPTH_ID);
        acturalDepth.text(Utility.formatDouble(hole.getActualDepth()));

        Element endDate = doc.getElementById(ENDDATE_ID);
        endDate.text(formatCalendarDateString(hole.getEndDate()));

        Element recorderName = doc.getElementById(RECORDER_ID);
        recorderName.text(hole.getRecorder() == null ? "" : hole.getRecorder());

        Element squName = doc.getElementById(SQUAD_ID);
        squName.text(hole.getClassMonitor() == null ? "" : hole.getClassMonitor());

        Element captainName = doc.getElementById(CAPTAIN_ID);
        captainName.text(hole.getMachineMonitor() == null ? "" : hole.getMachineMonitor());

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
            el.text(String.valueOf(node.getContent()));
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
            Element el = doc.getElementById("trNodeDiameter").appendElement("div");
            el.text(Utility.formatDouble(node.getHeight()));
            el.attr("style", "height:" + node.getHeight() + "rem;");

            el = doc.getElementById("trNodeStart").appendElement("div");
            el.text(Utility.formatDouble(0));
            el.attr("style", "height:" + node.getHeight() + "rem;");

            el = doc.getElementById("trNodeEnd").appendElement("div");
            el.text(node.getContent());
            el.attr("style", "height:" + node.getHeight() + "rem;");

            el = doc.getElementById("trNodeTotal").appendElement("div");
            el.text(node.getContent());
            el.attr("style", "height:" + node.getHeight() + "rem;");


        }

        //初始水位
        RigGraphData.GraphNode initialWaterDepthNode = rigGraphData.getInitialWaterDepthNode();
        Element el = doc.getElementById("initWater").appendElement("div");
        el.text(initialWaterDepthNode.getContent());
        el.text(initialWaterDepthNode.getContent().equals("") || Double.valueOf(initialWaterDepthNode.getContent()).equals("") ? "" :initialWaterDepthNode.getContent() );
        el.attr("style", "height:" + initialWaterDepthNode.getHeight() + "rem;");

        //稳定水位
        RigGraphData.GraphNode finalWaterDepthNode = rigGraphData.getFinalWaterDepthNode();
        el = doc.getElementById("finalWater").appendElement("div");
        el.text(finalWaterDepthNode.getContent().equals("") || Double.valueOf(finalWaterDepthNode.getContent()).equals("") ? "" :finalWaterDepthNode.getContent() );
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
            el.text(Utility.formatDouble(rigNode.getRockPickPercentage() * 100));
            el.attr("style", "height:" + rigNode.getHeight() + "rem;");

            //钻头直径
            el = doc.getElementById("drillDiameter").appendElement("div");
            el.text(String.valueOf(rigNode.getDrillDiameter() < 0 ? "" : Utility.formatDouble(rigNode.getDrillDiameter())));
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

            //类型
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

    public static String parseSptRigs(String path, Hole hole, AssetManager assetManager) {
        if (hole == null) {
            return null;
        }

        ArrayList<SPTRig> sptRigs = new ArrayList<>();
        for (Rig rig : hole.getRigIndexViewList()) {
            if (rig instanceof SPTRig) {
                sptRigs.add((SPTRig) rig);
            }
        }

        String[][] sptResults = null;
        for (int i = 0, len = sptRigs.size(); i < len; i++) {
            SPTRig sptRig = sptRigs.get(i);
            String[][] result = convertSpt(sptRig, (i + 1), "<br/>");
            sptResults = sptResults == null ? result : Utility.concat(sptResults, result);
        }

        try {
            sptResults = sptResults == null ? new String[0][] : sptResults;
            writeWithHole(path, sptResults, hole, assetManager.open(SPT_RIG_EVENT_TEMPLATE));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return path;
    }

    public static String parseDstRigs(String path, Hole hole, List<Rig> rigs, AssetManager assetManager) {
        if (hole == null) {
            return null;
        }

        ArrayList<DSTRig> dstRigs = new ArrayList<>();
        for (Rig rig : rigs) {
            if (rig instanceof DSTRig) {
                DSTRig dst = (DSTRig) rig;
                dstRigs.add(dst);
            }
        }


        String[][] dstResults = null;
        for (DSTRig dstRig : dstRigs) {
            String[][] result = convertDst(dstRig);
            dstResults = null == dstResults ? result : Utility.concat(dstResults, result);
        }

        try {
            dstResults = null == dstResults ? new String[0][] : dstResults;
            writeWithHole(path, dstResults, hole, assetManager.open(DST_RIG_EVENT_TEMPLATE));
            Document doc = Jsoup.parse(new FileInputStream(path), "UTF-8", "./");
            Element dateRange = doc.getElementById(DATE_RANGE_ID);
            if (dateRange != null && dstRigs.size() > 0) {
                DSTRig firstRig = dstRigs.get(0);
                DSTRig lastRig = dstRigs.get(dstRigs.size() - 1);
                dateRange.text(Utility.formatCalendarDateString(firstRig.getStartTime()) + " - " + Utility.formatCalendarDateString(lastRig.getEndTime()));
            }
            FileWriter fileWriter = new FileWriter(path, false);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(doc.outerHtml());
            bufferedWriter.close();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return path;
    }

    public static String parseEarthSmlRigs(String path, Hole hole, AssetManager assetManager) {
        if (hole == null) {
            return null;
        }

        List<Rig> rigs = hole.getRigIndexViewList() == null ? new ArrayList<Rig>() : hole.getRigIndexViewList();

        List<OtherSamplingRig.OtherSamplingDetail> distributionDetails = new ArrayList<>();
        List<OriginalSamplingRig> originalSampling = new ArrayList<>();

        for (Rig rig : rigs) {
            if (rig instanceof OtherSamplingRig.OtherSamplingDetail) {
                OtherSamplingRig.OtherSamplingDetail detail = (OtherSamplingRig.OtherSamplingDetail) rig;
                if (detail.getSamplingType().equals("扰动样")) {
                    distributionDetails.add(detail);
                }
            } else if (rig instanceof OriginalSamplingRig) {
                OriginalSamplingRig originalSamplingRig = (OriginalSamplingRig) rig;
                originalSampling.add(originalSamplingRig);
            }
        }

        String[][] earthResults;
        String[][] distributionResults = new String[0][];
        for ( OtherSamplingRig.OtherSamplingDetail detail : distributionDetails) {
            String[][] result = convertEarthSmplDetail(hole, detail, "<BR/>");
            distributionResults = Utility.concat(distributionResults, result);
        }

        distributionResults = null == distributionResults ? new String[0][] : distributionResults;

        String[][] originalSmplResults = new String[0][];
        for (OriginalSamplingRig originalSamplingRig : originalSampling) {
            String[][] result = convertOriSmpl(hole, originalSamplingRig, "<BR/>");
            originalSmplResults = null == originalSmplResults ? result : Utility.concat(originalSmplResults, result);
        }
        originalSmplResults = originalSmplResults == null ? new String[0][] : originalSmplResults;

        earthResults = Utility.concat(originalSmplResults, distributionResults);

        try {
            writeWithHole(path, earthResults, hole, assetManager.open(SMPL_EARTH_RIG_EVENT_TEMPLATE));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return path;
    }

    public static String parseWaterSmlRigs(String path, Hole hole, AssetManager assetManager) {
        if (hole == null) {
            return null;
        }

        List<Rig> rigs = hole.getRigIndexViewList() == null ? new ArrayList<Rig>() : hole.getRigIndexViewList();

        HashMap<Hole, OtherSamplingRig.OtherSamplingDetail> details = new HashMap<>();
        for (Rig rig : rigs) {
            if (rig instanceof OtherSamplingRig.OtherSamplingDetail) {
                OtherSamplingRig.OtherSamplingDetail detail = (OtherSamplingRig.OtherSamplingDetail) rig;
                if (detail.getSamplingType().equals("水样")) {
                    details.put(hole, detail);
                }
            }
        }

        String[][] smplWaterResults = null;
        for (Map.Entry<Hole, OtherSamplingRig.OtherSamplingDetail> entry : details.entrySet()) {
            String[][] result = convertWaterSmplDetail(entry.getKey(), entry.getValue(), "<BR/>");
            smplWaterResults = null == smplWaterResults ? result : Utility.concat(smplWaterResults, result);
        }

        try {
            smplWaterResults = null == smplWaterResults ? new String[0][] : smplWaterResults;
            writeWithHole(path, smplWaterResults, hole, assetManager.open(SMPL_WATER_RIG_EVENT_TEMPLATE));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return path;
    }

    public static String parseRockSmlRigs(String path, Hole hole, AssetManager assetManager) {
        if (hole == null) return null;

        List<Rig> rigs = hole.getRigIndexViewList() == null ? new ArrayList<Rig>() : hole.getRigIndexViewList();

        HashMap<Hole, OtherSamplingRig.OtherSamplingDetail> details = new HashMap<>();
        for (Rig rig : rigs) {
            if (rig instanceof OtherSamplingRig.OtherSamplingDetail) {
                OtherSamplingRig.OtherSamplingDetail detail = (OtherSamplingRig.OtherSamplingDetail) rig;
                if (detail.getSamplingType().equals("岩样")) {
                    details.put(hole, detail);
                }
            }
        }

        String[][] smplRockResults = null;
        for (Map.Entry<Hole, OtherSamplingRig.OtherSamplingDetail> entry : details.entrySet()) {
            String[][] result = convertRockSmplDetail(entry.getKey(), entry.getValue(), "<BR/>");
            smplRockResults = null == smplRockResults ? result : Utility.concat(smplRockResults, result);
        }

        try {
            smplRockResults = null == smplRockResults ? new String[0][] : smplRockResults;
            writeWithHole(path, smplRockResults, hole, assetManager.open(SMPL_ROCK_RIG_EVENT_TEMPLATE));
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

            //回次进尺 岩底厚度
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

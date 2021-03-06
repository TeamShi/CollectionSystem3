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

    private static String PROJECT_OVERVIEW_TEMPLATE = "Project.html";
    public static String BASIC_RIG_EVENT_TEMPLATE = "RigEventTable.html";
    private static String SPT_RIG_EVENT_TEMPLATE = "SPTRigEventTable.html";
    private static String DST_RIG_EVENT_TEMPLATE = "DSTRigEventTable.html"; // 动力触探
    private static String SMPL_EARTH_RIG_EVENT_TEMPLATE = "SampleEarth.html";
    private static String SMPL_WATER_RIG_EVENT_TEMPLATE = "SampleWater.html";
    private static String SMPL_ROCK_RIG_EVENT_TEMPLATE = "SampleRock.html";

    private static String RIG_GRAPH_TEMPLATE = "RigGraphTable.html";
    private static String RIG_GRAPH_BACK_COVER_TEMPLATE = "RigGraphBackCover.html";
    public static String MODIFY_NOTE_TEMPLATE = "ModificationDesc.html";

    private static String TBODY_ID = "tableBody";
    private static String PROJECTNAME_ID = "projectName";
    private static String PROJECTNAME_LIST = "projectList";
    private static String POSITION_ID = "position";
    private static String MILEAGE_ID = "mileage";

    public static String HOLEELEVATION_ID = "holeElevation";
    public static String HOLE_OFFSET = "offsetRight";
    public static String HOLE_ID = "holeId";
    private static String EXPLORATIONUNIT_ID = "company";
    private static String MACHINENUMBER_ID = "machineNumber";
    private static String ENGIN_ID = "enginType";
    private static String PUMP_ID = "pumpType";
    private static String DESIGN_DEPTH_ID = "holeDepth";
    private static String ACTURAL_DEPTH_ID = "actualDepth";

    private static String RIGTYPE_ID = "rigType";
    private static String STARTDATE_ID = "startDate";
    private static String ENDDATE_ID = "endDate";
    private static String DATE_RANGE_ID = "dateRange";

    private static String HOLE_DESC = "description";

    private static String RECORDER_ID = "recorderName";
    private static String SQUAD_ID = "squadName";
    private static String CAPTAIN_ID = "captainName";

    private static String MODIFY_STAFF_ID = "checkStaff";
    private static String MODIFY_DATE_ID = "checkDate";

    public static int PAGE_SIZE = 20;

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

            Element modifyDate = doc.getElementById(MODIFY_DATE_ID);
            if (modifyDate != null) {
                modifyDate.text(Utility.formatCalendarDateString(hole.getFixDate()));
            }

            Element modifyStaff = doc.getElementById(MODIFY_STAFF_ID);
            if (modifyStaff != null) {
                modifyStaff.text(hole.getFixSignature());
            }
        }

        Element tbody = doc.getElementById(TBODY_ID);
        if (data != null) {
            // 循环写入行数据
            for (String[] aData : data) {
                StringBuilder row = new StringBuilder();
                row.append("<tr>");
                // 循环写入列数据
                for (String anAData : aData) {
                    row.append("<td>");
                    String text = anAData.equals("null") ? "" : anAData;
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


    private static void appendSubLink(List<String> fileNames, Hole hole, String textPrefix, Document doc, Element parent) {
        String relativeDataPaths = "../Data/";
        String holePath = relativeDataPaths + hole.getProjectName() + File.separator + hole.getHoleId() + File.separator;
        for (int i = 0; i < fileNames.size(); i++) {
            String hrefPath = holePath + fileNames.get(i);
            Element node = doc.createElement("li");
            node.appendElement("a").attr("href", hrefPath).text(textPrefix + "-" + (i + 1));
            parent.appendChild(node);
        }
    }

    public static String parse(Project project, AssetManager assetManager) {
        List<Hole> holes = project.getHoleList();
        String projectPath = APP_TEMP + "project_" + project.getProjectName() + ".html";
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

                List allRigsPaths = HtmlParser.parseHole(absoluteHolepath, "hole_" + hole.getHoleId(), assetManager, hole);
                List<String> smpleWaterRigPaths = HtmlParser.parseWaterSmlRigs(absoluteHolepath, "smplWaterRigs", hole, assetManager);
                List<String> smpleRockRigPaths = HtmlParser.parseRockSmlRigs(absoluteHolepath, "smplRockRigs", hole, assetManager);
                List<String> smpleEarthRigPaths = HtmlParser.parseEarthSmlRigs(absoluteHolepath, "smplEarthRigs", hole, assetManager);
                List<String> sptPaths = HtmlParser.parseSptRigs(absoluteHolepath, "sptRigs", hole, assetManager);
                List<String> dstPaths = HtmlParser.parseDstRigs(absoluteHolepath, "dstRigs", hole, hole.getRigIndexViewList(), assetManager);
                List<String> modifyHoldePaths = HtmlParser.parseModifyHoleDesc(absoluteHolepath, "modifyHole", hole, assetManager);
                HtmlParser.parseRigGraphTable(absoluteHolepath, "rigGraph", hole, assetManager);
                HtmlParser.parseRigGraphCover(absoluteHolepath + "rigGraphCover.html", hole, assetManager);
                HtmlParser.parseRigGraphBackCover(absoluteHolepath + "rigGraphBackCover.html", hole, assetManager);


                Element holeNode = doc.createElement("li");
                holeNode.text(hole.getHoleId());
                Element holeNodeList = doc.createElement("ul");

                appendSubLink(allRigsPaths, hole, "原始记录表", doc, holeNodeList);
                appendSubLink(sptPaths, hole, "标准贯入表", doc, holeNodeList);
                appendSubLink(dstPaths, hole, "动力触探表", doc, holeNodeList);
                appendSubLink(smpleEarthRigPaths, hole, "土样表", doc, holeNodeList);
                appendSubLink(smpleWaterRigPaths, hole, "水样表", doc, holeNodeList);
                appendSubLink(smpleRockRigPaths, hole, "岩样表", doc, holeNodeList);
                appendSubLink(modifyHoldePaths, hole, "修正表", doc, holeNodeList);

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

    private static List<String> parseModifyHoleDesc(String dir, String fileNamePrefix, Hole hole, AssetManager assetManager) {
        if (hole == null) {
            return null;
        }

        Hole.FixItem[] items = hole.getHoleFixItems();
        String[][] results = new String[0][];

        if( items != null) {
            results = new String[items.length][3];
            for (int i = 0, len = items.length; i < len; i ++ ) {
                results[i] = new String[3];
                results[i][0] = String.valueOf(i + 1);
                results[i][1] = items[i].getOriginItem();
                results[i][2] = items[i].getFixedItem();
            }
        }

        List<String> fileNames = new ArrayList<>();

        //列数
        List<String[][]> records = Utility.fillArray(results, 3, PAGE_SIZE, " ");
        for (int index = 0; index < records.size(); index++) {
            try {
                String fileName = fileNamePrefix + "_" + (index + 1) + ".html";
                fileNames.add(fileName);
                String path = dir + fileName;

                writeWithHole(path, records.get(index), hole, assetManager.open(MODIFY_NOTE_TEMPLATE));

                //NOTICE
                Document doc = Jsoup.parse(new FileInputStream(dir), "UTF-8", "./");
                FileWriter fileWriter = new FileWriter(dir, false);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                bufferedWriter.write(doc.outerHtml());
                bufferedWriter.close();
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return fileNames;
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


    private static List parseHole(String dir, String fileNamePrefix, AssetManager assetManager, Hole hole) throws IOException {
        String[][] _data = convertHole(hole, "<br/>");
        List<String> fileNames = new ArrayList<>();
        //列数
        List<String[][]> records = Utility.fillArray(_data, 44, PAGE_SIZE, " ");
        for (int index = 0; index < records.size(); index++) {
            String fileName = fileNamePrefix + "_" + (index + 1) + ".html";
            fileNames.add(fileName);
            String path = dir + fileName;

            InputStream inputStream = assetManager.open(BASIC_RIG_EVENT_TEMPLATE);
            //读模版文件
            Document doc = Jsoup.parse(inputStream, "UTF-8", "./");
            Element tableBody = doc.getElementById(TBODY_ID);
            String[][] data = records.get(index);
            if (data != null) {
                // 循环写入行数据
                for (String[] aData : data) {
                    StringBuilder row = new StringBuilder();
                    row.append("<tr>");
                    // 循环写入列数据
                    for (String anAData : aData) {
                        row.append("<td>");
                        String text = anAData.equals("null") ? "" : anAData;
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
            if (positionId != null) {
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
            FileWriter fileWriter = new FileWriter(path);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(doc.outerHtml());
            bufferedWriter.close();
            fileWriter.close();

        }

        return fileNames;

    }

    private static boolean parseRigGraphCover(String outPath, Hole hole, AssetManager assetManager) throws IOException {

        File rigGraphCover = Utility.createFile(outPath, false);
        String RIG_GRAPH_COVER_TEMPLATE = "RigGraphCover.html";
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

    private static boolean parseRigGraphBackCover(String outPath, Hole hole, AssetManager assetManager) throws IOException {

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

    private static void appendRigNode(List<RigGraphData.RigNode> rigNodes, double offset, Document doc, String path) {
        RigGraphData.RigNode prevRigNode = null;
        for (int f = 0, len = rigNodes.size(); f < len; f++) {
            RigGraphData.RigNode rigNode = rigNodes.get(f);
            Element el = doc.getElementById("rockCorePer").appendElement("div");
            el.text(Utility.formatDouble(rigNode.getRockPickPercentage() * 100));
            el.attr("style", "height:" + getRelativeHeight(prevRigNode, rigNode, offset) + "rem;");

            //钻头直径
            el = doc.getElementById("drillDiameter").appendElement("div");
            el.text(String.valueOf(rigNode.getDrillDiameter() < 0 ? "" : Utility.formatDouble(rigNode.getDrillDiameter())));
            el.attr("style", "height:" + getRelativeHeight(prevRigNode, rigNode, offset) + "rem;");

            //岩芯编号
            el = doc.getElementById("rockCoreIndex").appendElement("div");
            el.text(String.valueOf(rigNode.getRockLayoutIndex()));
            el.attr("style", "height:" + getRelativeHeight(prevRigNode, rigNode, offset) + "rem;");

            //岩芯长度
            el = doc.getElementById("rockCoreLength").appendElement("div");
            el.text(String.valueOf(Utility.formatDouble(rigNode.getRockPickLength())));
            el.attr("style", "height:" + getRelativeHeight(prevRigNode, rigNode, offset) + "rem;");

            //岩层描述
            el = doc.getElementById("desc").appendElement("div");
            el.text(String.valueOf(rigNode.getDescription()));
            el.attr("style", "height:" + getRelativeHeight(prevRigNode, rigNode, offset) + "rem;");

            //钻进深度至
            el = doc.getElementById("endDepth").appendElement("div");
            el.text(String.valueOf(Utility.formatDouble(rigNode.getEndDepth())));
            el.attr("style", "height:" + getRelativeHeight(prevRigNode, rigNode, offset) + "rem;");

            //钻进深度至
            el = doc.getElementById("depthAll").appendElement("div");
            el.text(String.valueOf(Utility.formatDouble(rigNode.getEndDepth())));
            el.attr("style", "height:" + getRelativeHeight(prevRigNode, rigNode, offset) + "rem;");

            //钻进深度由
            el = doc.getElementById("startDepth").appendElement("div");
            el.text(String.valueOf(Utility.formatDouble(rigNode.getStartDepth())));
            el.attr("style", "height:" + getRelativeHeight(prevRigNode, rigNode, offset) + "rem;");

            //层底深度
            el = doc.getElementById("layerEndDepth").appendElement("div");
            el.text(String.valueOf(Utility.formatDouble(rigNode.getLayoutEndDepth())));
            el.attr("style", "height:" + getRelativeHeight(prevRigNode, rigNode, offset) + "rem;");

            //回次进尺
            el = doc.getElementById("roundTrip").appendElement("div");
            el.text(String.valueOf(Utility.formatDouble(rigNode.getRoundTripDepth())));
            el.attr("style", "height:" + getRelativeHeight(prevRigNode, rigNode, offset) + "rem;");

            //类型
            el = doc.getElementById("legend").appendElement("div");
            el.text(String.valueOf("ff"));
            el.attr("class", getEarthType(rigNode.getDrillType()));
            el.attr("style", "height:" + getRelativeHeight(prevRigNode, rigNode, offset) + "rem;" + ";color:rgba(255,255,255,0)");

            prevRigNode = rigNode;
        }

        FileWriter fileWriter;
        try {
            fileWriter = new FileWriter(path, false);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(doc.outerHtml());
            bufferedWriter.close();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static double getRelativeHeight(RigGraphData.GraphNode prevNode, RigGraphData.GraphNode node) {
        if (prevNode == null) {
            return node.getHeight();
        }
        return node.getHeight() - prevNode.getHeight();
    }

    private static double getRelativeHeight(RigGraphData.RigNode prevNode, RigGraphData.RigNode node, double offset) {
        if (prevNode == null) {
            return node.getHeight() - offset;
        }
        return node.getHeight() - prevNode.getHeight();
    }

    private static List<String> parseRigGraphTable(String dir, String fileNamePrefix, Hole hole, AssetManager assetManager) throws IOException {
        Utility.createFile(dir, false);
        InputStream inputStream = assetManager.open(RIG_GRAPH_TEMPLATE);

        //读模版文件
        Document doc = Jsoup.parse(inputStream, "UTF-8", "./");

        RigGraphData rigGraphData = hole.getRigGraphData();

        //日期
        RigGraphData.GraphNode prevNode = null;
        for (int f = 0; f < rigGraphData.getDateNodeList().size(); f++) {
            RigGraphData.GraphNode node = rigGraphData.getDateNodeList().get(f);
            Element el = doc.getElementById("date").appendElement("div");
            el.text(node.getContent());
            el.attr("style", "height:" + getRelativeHeight(prevNode, node) + "rem;");
            prevNode = node;
        }

        prevNode = null;
        //岩芯
        for (int f = 0, len = rigGraphData.getRockCoreNodeList().size(); f < len; f++) {
            RigGraphData.GraphNode node = rigGraphData.getRockCoreNodeList().get(f);
            Element el = doc.getElementById("rockCore").appendElement("div");
            el.text(node.getContent());
            el.attr("style", "height:" + getRelativeHeight(prevNode, node) + "rem;");
            prevNode = node;
        }

        prevNode = null;
        //水样
        for (int f = 0, len = rigGraphData.getWaterSamplingNodeList().size(); f < len; f++) {
            RigGraphData.GraphNode node = rigGraphData.getWaterSamplingNodeList().get(f);
            Element el = doc.getElementById("waterSampling").appendElement("div");
            el.text(node.getContent());
            el.attr("style", "height:" + getRelativeHeight(prevNode, node) + "rem;");
            prevNode = node;
        }

        prevNode = null;
        //原样
        for (int f = 0, len = rigGraphData.getOriginalSamplingNodeList().size(); f < len; f++) {
            RigGraphData.GraphNode node = rigGraphData.getOriginalSamplingNodeList().get(f);
            Element el = doc.getElementById("originalSampling").appendElement("div");
            el.text(String.valueOf(node.getContent()));
            el.attr("style", "height:" + getRelativeHeight(prevNode, node) + "rem;");
            prevNode = node;
        }

        prevNode = null;
        //扰动样
        for (int f = 0, len = rigGraphData.getDisturbanceSamplingNodeList().size(); f < len; f++) {
            RigGraphData.GraphNode node = rigGraphData.getDisturbanceSamplingNodeList().get(f);
            Element el = doc.getElementById("distSampling").appendElement("div");
            el.text(node.getContent());
            el.attr("style", "height:" + getRelativeHeight(prevNode, node) + "rem;");
            prevNode = node;
        }

        prevNode = null;
        //下套管
        for (int f = 0, len = rigGraphData.getTrNodeList().size(); f < len; f++) {
            RigGraphData.GraphNode node = rigGraphData.getTrNodeList().get(f);
            Element el = doc.getElementById("trNodeDiameter").appendElement("div");
            el.text(node.getContent());
            el.attr("style", "height:" + getRelativeHeight(prevNode, node) + "rem;");

            el = doc.getElementById("trNodeStart").appendElement("div");
            el.text(Utility.formatDouble(0));
            el.attr("style", "height:" + getRelativeHeight(prevNode, node) + "rem;");

            el = doc.getElementById("trNodeEnd").appendElement("div");
            el.text(Utility.formatDouble(node.getHeight()));
            el.attr("style", "height:" + getRelativeHeight(prevNode, node) + "rem;");

            el = doc.getElementById("trNodeTotal").appendElement("div");
            el.text(Utility.formatDouble(node.getHeight()));
            el.attr("style", "height:" + getRelativeHeight(prevNode, node) + "rem;");
            prevNode = node;
        }

        //初始水位
        RigGraphData.GraphNode initialWaterDepthNode = rigGraphData.getInitialWaterDepthNode();
        Element el = doc.getElementById("initWater").appendElement("div");
        el.text(initialWaterDepthNode.getContent());
        el.text(initialWaterDepthNode.getContent().equals("") || Double.valueOf(initialWaterDepthNode.getContent()).equals("") ? "" : initialWaterDepthNode.getContent());
        el.attr("style", "height:" + initialWaterDepthNode.getHeight() + "rem;");

        //稳定水位
        RigGraphData.GraphNode finalWaterDepthNode = rigGraphData.getFinalWaterDepthNode();
        el = doc.getElementById("finalWater").appendElement("div");
        el.text(finalWaterDepthNode.getContent().equals("") || Double.valueOf(finalWaterDepthNode.getContent()).equals("") ? "" : finalWaterDepthNode.getContent());
        el.attr("style", "height:" + finalWaterDepthNode.getHeight() + "rem;");

        //水位稳定时间
        RigGraphData.GraphNode waterDepthDateNode = rigGraphData.getWaterDepthDateNode();
        el = doc.getElementById("waterDate").appendElement("div");
        el.text(waterDepthDateNode.getContent());
        el.attr("style", "height:" + waterDepthDateNode.getHeight() + "rem;");

        List<RigGraphData.RigNode> rigNodes = rigGraphData.getRigNodeList();
        doc.getElementsByClass("flex-row").attr("style", "height:" + 30 * rigNodes.size() + "px");

        List<String> fileNames = new ArrayList<>();

        Map<Double, List<RigGraphData.RigNode>> rigNodePages = new HashMap<>();
        List<RigGraphData.RigNode> nodes = new ArrayList<>();
        double offset = 0;
        double currentOffset;
        for (int i = 0; i < rigNodes.size(); i++) {
            RigGraphData.RigNode node = rigNodes.get(i);
            currentOffset = node.getHeight() - offset;
            nodes.add(node);

            if (currentOffset > PAGE_SIZE) {
                offset = node.getHeight();
                rigNodePages.put(offset, nodes);
                nodes = new ArrayList<>();
                continue;
            }

            if (i == (rigNodes.size() - 1)) {
                rigNodePages.put(offset, nodes);
            }
        }

        int index = 0;
        for (Map.Entry<Double, List<RigGraphData.RigNode>> entry : rigNodePages.entrySet()) {
            String fileName = fileNamePrefix + "_" + (index + 1) + ".html";
            fileNames.add(fileName);
            if (index == 0) {
                appendRigNode(entry.getValue(), entry.getKey(), doc, dir + fileName);
                index++;
                continue;
            }
            inputStream = assetManager.open(RIG_GRAPH_TEMPLATE);
            doc = Jsoup.parse(inputStream, "UTF-8", "./");
            appendRigNode(entry.getValue(), entry.getKey(), doc, dir + fileName);
            index++;
            //TODO display offset
        }

        return fileNames;
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

    private static List<String> parseSptRigs(String dir, String fileNamePrefix, Hole hole, AssetManager assetManager) {
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

        sptResults = sptResults == null ? new String[0][] : sptResults;

        List<String> fileNames = new ArrayList<>();
        //列数
        List<String[][]> records = Utility.fillArray(sptResults, 27, PAGE_SIZE, " ");
        for (int index = 0; index < records.size(); index++) {
            try {
                String fileName = fileNamePrefix + "_" + (index + 1) + ".html";
                fileNames.add(fileName);
                String path = dir + fileName;
                writeWithHole(path, records.get(index), hole, assetManager.open(SPT_RIG_EVENT_TEMPLATE));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return fileNames;
    }

    private static List<String> parseDstRigs(String dir, String fileNamePrefix, Hole hole, List<Rig> rigs, AssetManager assetManager) {
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

        dstResults = null == dstResults ? new String[0][] : dstResults;
        List<String> fileNames = new ArrayList<>();

        //列数
        List<String[][]> records = Utility.fillArray(dstResults, 7, PAGE_SIZE, " ");
        for (int index = 0; index < records.size(); index++) {
            try {
                String fileName = fileNamePrefix + "_" + (index + 1) + ".html";
                fileNames.add(fileName);
                String path = dir + fileName;

                writeWithHole(path, records.get(index), hole, assetManager.open(DST_RIG_EVENT_TEMPLATE));

                //NOTICE
                Document doc = Jsoup.parse(new FileInputStream(dir), "UTF-8", "./");
                Element dateRange = doc.getElementById(DATE_RANGE_ID);
                if (dateRange != null && dstRigs.size() > 0) {
                    DSTRig firstRig = dstRigs.get(0);
                    DSTRig lastRig = dstRigs.get(dstRigs.size() - 1);
                    dateRange.text(Utility.formatCalendarDateString(firstRig.getStartTime()) + " - " + Utility.formatCalendarDateString(lastRig.getEndTime()));
                }
                FileWriter fileWriter = new FileWriter(dir, false);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                bufferedWriter.write(doc.outerHtml());
                bufferedWriter.close();
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return fileNames;
    }

    private static List<String> parseEarthSmlRigs(String dir, String fileNamePrefix, Hole hole, AssetManager assetManager) {
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
        for (OtherSamplingRig.OtherSamplingDetail detail : distributionDetails) {
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

        List<String> fileNames = new ArrayList<>();
        //列数
        List<String[][]> records = Utility.fillArray(earthResults, 39, PAGE_SIZE, " ");
        for (int index = 0; index < records.size(); index++) {
            try {
                String fileName = fileNamePrefix + "_" + (index + 1) + ".html";
                fileNames.add(fileName);
                String path = dir + fileName;
                writeWithHole(path, records.get(index), hole, assetManager.open(SMPL_EARTH_RIG_EVENT_TEMPLATE));
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        return fileNames;
    }

    private static List<String> parseWaterSmlRigs(String dir, String fileNamePrefix, Hole hole, AssetManager assetManager) {
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

        smplWaterResults = null == smplWaterResults ? new String[0][] : smplWaterResults;
        List<String> fileNames = new ArrayList<>();
        //列数
        List<String[][]> records = Utility.fillArray(smplWaterResults, 11, PAGE_SIZE, " ");
        for (int index = 0; index < records.size(); index++) {
            try {
                String fileName = fileNamePrefix + "_" + (index + 1) + ".html";
                fileNames.add(fileName);
                String path = dir + fileName;
                writeWithHole(path, records.get(index), hole, assetManager.open(SMPL_WATER_RIG_EVENT_TEMPLATE));
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        return fileNames;
    }

    private static List<String> parseRockSmlRigs(String dir, String fileNamePrefix, Hole hole, AssetManager assetManager) {
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

        smplRockResults = null == smplRockResults ? new String[0][] : smplRockResults;

        List<String> fileNames = new ArrayList<>();
        //列数
        List<String[][]> records = Utility.fillArray(smplRockResults, 18, PAGE_SIZE, " ");
        for (int index = 0; index < records.size(); index++) {
            try {
                String fileName = fileNamePrefix + "_" + (index + 1) + ".html";
                fileNames.add(fileName);
                String path = dir + fileName;
                writeWithHole(path, records.get(index), hole, assetManager.open(SMPL_ROCK_RIG_EVENT_TEMPLATE));
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        return fileNames;
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

package com.teamshi.collectionsystem3.parser;

import android.content.res.AssetManager;

import com.teamshi.collectionsystem3.Utility;
import com.teamshi.collectionsystem3.datastructure.DSTRig;
import com.teamshi.collectionsystem3.datastructure.Hole;
import com.teamshi.collectionsystem3.datastructure.OriginalSamplingRig;
import com.teamshi.collectionsystem3.datastructure.OtherSamplingRig;
import com.teamshi.collectionsystem3.datastructure.Project;
import com.teamshi.collectionsystem3.datastructure.SPTRig;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static com.teamshi.collectionsystem3.Utility.formatCalendarDateString;


public class HtmlParser extends Parser{

    public static String PROJECT_OVERVIEW_TEMPLATE = "Project.html";
    public static String BASIC_RIG_EVENT_TEMPLATE = "RigEventTable.html";
    public static String SPT_RIG_EVENT_TEMPLATE = "SPTRigEventTable.html";
    public static String DST_RIG_EVENT_TEMPLATE = "DSTRigEventTable.html"; // 动力触探
    public static String SMPL_ORIGIN_RIG_EVENT_TEMPLATE = "SMPLRigEventTable.html";
    public static String SMPL_EARTH_RIG_EVENT_TEMPLATE = "SampleEarth.html";
    public static String SMPL_WATER_RIG_EVENT_TEMPLATE = "SampleWater.html";
    public static String SMPL_ROCK_RIG_EVENT_TEMPLATE = "SampleRock.html";


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

        String[][] sptEventArray = convertSpt(sptRig,1, "<br/>");
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

package com.teamshi.collectionsystem3.datastructure;

import com.teamshi.collectionsystem3.DataManager;
import com.teamshi.collectionsystem3.Utility;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Alfred on 2016/12/4.
 */

public class RigGraphData implements Serializable {
    private static final long serialVersionUID = -4023880943791610892L;

    public static class GraphNode implements Serializable {
        private static final long serialVersionUID = -2012433594588755186L;
        private String content;
        private double height;

        public GraphNode(String content, double height) {
            this.content = content;
            this.height = height;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public double getHeight() {
            return height;
        }

        public void setHeight(double height) {
            this.height = height;
        }

        public GraphNode deepCopy() {
            return new GraphNode(content, height);
        }
    }


    public static class RigNode implements Serializable {
        private static final long serialVersionUID = -1679676837360313236L;
        private double height;
        private String drillType;
        private double drillDiameter;

        private double startDepth;
        private double endDepth;
        private double roundTripDepth;

        private double layoutEndDepth;

        private double rockDepth;
        private int rockLayoutIndex;

        private double rockPickLength;
        private double rockPickPercentage;

        private String rockDensity;

        private String description;

        public RigNode(double height, String drillType, double drillDiameter, double startDepth, double endDepth, double roundTripDepth, double layoutEndDepth, double rockDepth, double rockPickLength, double rockPickPercentage, int rockLayoutIndex, String description) {
            this.height = height;
            this.drillType = drillType;
            this.drillDiameter = drillDiameter;
            this.startDepth = startDepth;
            this.endDepth = endDepth;
            this.roundTripDepth = roundTripDepth;
            this.layoutEndDepth = layoutEndDepth;
            this.rockDepth = rockDepth;
            this.rockPickLength = rockPickLength;
            this.rockPickPercentage = rockPickPercentage;
            this.rockLayoutIndex = rockLayoutIndex;
            this.description = description;
        }

        public String getRockDensity() {
            return rockDensity;
        }

        public void setRockDensity(String rockDensity) {
            this.rockDensity = rockDensity;
        }

        public double getHeight() {
            return height;
        }

        public void setHeight(double height) {
            this.height = height;
        }

        public String getDrillType() {
            return drillType;
        }

        public void setDrillType(String drillType) {
            this.drillType = drillType;
        }

        public double getDrillDiameter() {
            return drillDiameter;
        }

        public void setDrillDiameter(double drillDiameter) {
            this.drillDiameter = drillDiameter;
        }

        public double getStartDepth() {
            return startDepth;
        }

        public void setStartDepth(double startDepth) {
            this.startDepth = startDepth;
        }

        public double getEndDepth() {
            return endDepth;
        }

        public void setEndDepth(double endDepth) {
            this.endDepth = endDepth;
        }

        public double getRoundTripDepth() {
            return roundTripDepth;
        }

        public void setRoundTripDepth(double roundTripDepth) {
            this.roundTripDepth = roundTripDepth;
        }

        public double getLayoutEndDepth() {
            return layoutEndDepth;
        }

        public void setLayoutEndDepth(double layoutEndDepth) {
            this.layoutEndDepth = layoutEndDepth;
        }

        public double getRockDepth() {
            return rockDepth;
        }

        public void setRockDepth(double rockDepth) {
            this.rockDepth = rockDepth;
        }

        public int getRockLayoutIndex() {
            return rockLayoutIndex;
        }

        public void setRockLayoutIndex(int rockLayoutIndex) {
            this.rockLayoutIndex = rockLayoutIndex;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public double getRockPickLength() {
            return rockPickLength;
        }

        public void setRockPickLength(double rockPickLength) {
            this.rockPickLength = rockPickLength;
        }

        public double getRockPickPercentage() {
            return rockPickPercentage;
        }

        public void setRockPickPercentage(double rockPickPercentage) {
            this.rockPickPercentage = rockPickPercentage;
        }

        public RigNode deepCopy() {
            return new RigNode(height, drillType, drillDiameter, startDepth, endDepth, roundTripDepth, layoutEndDepth, rockDepth, rockPickLength, rockPickPercentage, rockLayoutIndex, description);
        }
    }

    private ArrayList<GraphNode> dateNodeList;
    private ArrayList<GraphNode> drillDiameterList;
    private ArrayList<RigNode> rigNodeList;
    private ArrayList<GraphNode> trNodeList;
    private ArrayList<GraphNode> rockCoreNodeList;
    private GraphNode initialWaterDepthNode;
    private GraphNode finalWaterDepthNode;
    private GraphNode waterDepthDateNode;
    private ArrayList<GraphNode> waterSamplingNodeList;
    private ArrayList<GraphNode> originalSamplingNodeList;
    private ArrayList<GraphNode> disturbanceSamplingNodeList;

    public RigGraphData() {
        this.dateNodeList = new ArrayList<>();
        this.drillDiameterList = new ArrayList<>();
        this.rigNodeList = new ArrayList<>();
        this.trNodeList = new ArrayList<>();
        this.rockCoreNodeList = new ArrayList<>();
        this.initialWaterDepthNode = new GraphNode("", 0);
        this.finalWaterDepthNode = new GraphNode("", 0);
        this.waterDepthDateNode = new GraphNode("", 0);
        this.waterSamplingNodeList = new ArrayList<>();
        this.originalSamplingNodeList = new ArrayList<>();
        this.disturbanceSamplingNodeList = new ArrayList<>();
    }

    public ArrayList<GraphNode> getDateNodeList() {
        return dateNodeList;
    }

    public ArrayList<GraphNode> getDrillDiameterList() {
        return drillDiameterList;
    }

    public void setDrillDiameterList(ArrayList<GraphNode> drillDiameterList) {
        this.drillDiameterList = drillDiameterList;
    }

    public void setDateNodeList(ArrayList<GraphNode> dateNodeList) {
        this.dateNodeList = dateNodeList;
    }

    public ArrayList<RigNode> getRigNodeList() {
        return rigNodeList;
    }

    public void setRigNodeList(ArrayList<RigNode> rigNodeList) {
        this.rigNodeList = rigNodeList;
    }

    public ArrayList<GraphNode> getTrNodeList() {
        return trNodeList;
    }

    public void setTrNodeList(ArrayList<GraphNode> trNodeList) {
        this.trNodeList = trNodeList;
    }

    public ArrayList<GraphNode> getRockCoreNodeList() {
        return rockCoreNodeList;
    }

    public void setRockCoreNodeList(ArrayList<GraphNode> rockCoreNodeList) {
        this.rockCoreNodeList = rockCoreNodeList;
    }

    public GraphNode getInitialWaterDepthNode() {
        return initialWaterDepthNode;
    }

    public void setInitialWaterDepthNode(GraphNode initialWaterDepthNode) {
        this.initialWaterDepthNode = initialWaterDepthNode;
    }

    public GraphNode getFinalWaterDepthNode() {
        return finalWaterDepthNode;
    }

    public void setFinalWaterDepthNode(GraphNode finalWaterDepthNode) {
        this.finalWaterDepthNode = finalWaterDepthNode;
    }

    public GraphNode getWaterDepthDateNode() {
        return waterDepthDateNode;
    }

    public void setWaterDepthDateNode(GraphNode waterDepthDateNode) {
        this.waterDepthDateNode = waterDepthDateNode;
    }

    public ArrayList<GraphNode> getWaterSamplingNodeList() {
        return waterSamplingNodeList;
    }

    public void setWaterSamplingNodeList(ArrayList<GraphNode> waterSamplingNodeList) {
        this.waterSamplingNodeList = waterSamplingNodeList;
    }

    public ArrayList<GraphNode> getOriginalSamplingNodeList() {
        return originalSamplingNodeList;
    }

    public void setOriginalSamplingNodeList(ArrayList<GraphNode> originalSamplingNodeList) {
        this.originalSamplingNodeList = originalSamplingNodeList;
    }

    public ArrayList<GraphNode> getDisturbanceSamplingNodeList() {
        return disturbanceSamplingNodeList;
    }

    public void setDisturbanceSamplingNodeList(ArrayList<GraphNode> disturbanceSamplingNodeList) {
        this.disturbanceSamplingNodeList = disturbanceSamplingNodeList;
    }

    public RigGraphData deepCopy() {
        RigGraphData newRigGraphData = new RigGraphData();

        for (GraphNode node : dateNodeList) {
            newRigGraphData.dateNodeList.add(node.deepCopy());
        }

        for (GraphNode node : drillDiameterList) {
            newRigGraphData.drillDiameterList.add(node.deepCopy());
        }

        for (RigNode node : rigNodeList) {
            newRigGraphData.rigNodeList.add(node.deepCopy());
        }

        for (GraphNode node : trNodeList) {
            newRigGraphData.trNodeList.add(node.deepCopy());
        }

        for (GraphNode node : rockCoreNodeList) {
            newRigGraphData.rockCoreNodeList.add(node.deepCopy());
        }

        newRigGraphData.initialWaterDepthNode = initialWaterDepthNode.deepCopy();

        newRigGraphData.finalWaterDepthNode = finalWaterDepthNode.deepCopy();

        newRigGraphData.waterDepthDateNode = waterDepthDateNode.deepCopy();

        for (GraphNode node : waterSamplingNodeList) {
            newRigGraphData.waterSamplingNodeList.add(node.deepCopy());
        }

        for (GraphNode node : originalSamplingNodeList) {
            newRigGraphData.originalSamplingNodeList.add(node.deepCopy());
        }

        for (GraphNode node : disturbanceSamplingNodeList) {
            newRigGraphData.disturbanceSamplingNodeList.add(node.deepCopy());
        }

        return newRigGraphData;
    }

    public static RigGraphData generateTestInstance() {
        RigGraphData d = new RigGraphData();
        d.getDateNodeList().add(new RigGraphData.GraphNode("9/1", 1));
        d.getDateNodeList().add(new RigGraphData.GraphNode("9/2", 10));
        d.getDateNodeList().add(new RigGraphData.GraphNode("9/3", 21));


        // Drill Type
        d.getDrillDiameterList().add(new RigGraphData.GraphNode("110", 8));
        d.getDrillDiameterList().add(new RigGraphData.GraphNode("120", 19));



        // TR info
        d.getTrNodeList().add(new RigGraphData.GraphNode("5", 8));
        d.getTrNodeList().add(new RigGraphData.GraphNode("13", 21));


        // Water level
        d.setInitialWaterDepthNode(new RigGraphData.GraphNode("0.9", 0.9));
        d.setFinalWaterDepthNode(new RigGraphData.GraphNode("1.5", 1.5));
        d.setWaterDepthDateNode(new RigGraphData.GraphNode("9/1", 1.5));

        // Sampling

        d.getOriginalSamplingNodeList().add(new RigGraphData.GraphNode("3 ~ 5", 5));
        d.getOriginalSamplingNodeList().add(new RigGraphData.GraphNode("13 ~ 15", 25));
        d.getOriginalSamplingNodeList().add(new RigGraphData.GraphNode("23 ~ 25", 25));

        d.getWaterSamplingNodeList().add(new RigGraphData.GraphNode("6 ~ 8", 8));
        d.getWaterSamplingNodeList().add(new RigGraphData.GraphNode("22 ~ 23.5", 23.5));
        d.getWaterSamplingNodeList().add(new RigGraphData.GraphNode("34 ~ 35", 35));

        d.getDisturbanceSamplingNodeList().add(new RigGraphData.GraphNode("1.5 ~ 2.5", 2.5));
        d.getDisturbanceSamplingNodeList().add(new RigGraphData.GraphNode("11.5 ~ 12.5", 12.5));
        d.getDisturbanceSamplingNodeList().add(new RigGraphData.GraphNode("21.5 ~ 22.5", 22.5));

        d.getRigNodeList().add(new RigGraphData.RigNode(
                1,
                "黏土",
                -1,
                0,
                1,
                1,
                1,
                1,
                0.9,
                0.9,
                1,
                "土非常好"));

        d.getRigNodeList().add(new RigGraphData.RigNode(
                11,
                "腊鸡土",
                -1,
                1,
                11,
                10,
                11,
                10,
                7,
                0.7,
                2,
                "黄鹤你不是个东西"));

        d.getRigNodeList().add(new RigGraphData.RigNode(
                21,
                "渣土",
                -1,
                11,
                21,
                10,
                21,
                10,
                8.5,
                0.85,
                3,
                "PPAP"));

        return d;
    }
}



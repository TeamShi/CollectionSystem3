package com.teamshi.collectionsystem3.datastructure;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Alfred on 2016/12/4.
 */

public class RigGraphData implements Serializable {
    public static class GraphNode implements Serializable {
        private String content;
        private int height;

        public GraphNode(String content, int height) {
            this.content = content;
            this.height = height;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public GraphNode deepCopy() {
            return new GraphNode(content, height);
        }
    }

    public static class RigNode implements Serializable{
        private int height;

        private String drillType;
        private int drillDiameter;

        private double startDepth;
        private double endDepth;
        private double roundTripDepth;

        private double layoutEndDepth;

        private double rockDepth;
        private int rockLayoutIndex;

        private String description;

        public RigNode(int height, String drillType, int drillDiameter, double startDepth, double endDepth, double roundTripDepth, double layoutEndDepth, double rockDepth, int rockLayoutIndex, String description) {
            this.height = height;
            this.drillType = drillType;
            this.drillDiameter = drillDiameter;
            this.startDepth = startDepth;
            this.endDepth = endDepth;
            this.roundTripDepth = roundTripDepth;
            this.layoutEndDepth = layoutEndDepth;
            this.rockDepth = rockDepth;
            this.rockLayoutIndex = rockLayoutIndex;
            this.description = description;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public String getDrillType() {
            return drillType;
        }

        public void setDrillType(String drillType) {
            this.drillType = drillType;
        }

        public int getDrillDiameter() {
            return drillDiameter;
        }

        public void setDrillDiameter(int drillDiameter) {
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

        public RigNode deepCopy() {
            return new RigNode(height, drillType, drillDiameter, startDepth, endDepth, roundTripDepth, layoutEndDepth, rockDepth, rockLayoutIndex, description);
        }
    }

    private ArrayList<GraphNode> dateNodeList;
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

}



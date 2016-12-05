package com.teamshi.collectionsystem3.datastructure;

import java.util.ArrayList;

/**
 * Created by Alfred on 2016/12/4.
 */

public class RigGraphData {
    public static class GraphNode {
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
    }

    // TODO: todo Alfred: add more information
    public static class RigNode {
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
}



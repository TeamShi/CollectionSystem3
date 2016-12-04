package com.teamshi.collectionsystem3.datastructure;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Alfred on 2016/12/4.
 */

public class RigGraphData {
    public static class GraphEntry {
        private String content;
        private int height;

        public GraphEntry(String content, int height) {
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
    public static class RigEntry {
    }

    private ArrayList<GraphEntry> dateEntryList;
    private ArrayList<RigEntry> rigEntryList;
    private ArrayList<GraphEntry> trEntryList;
    private ArrayList<GraphEntry> rockCoreEntryList;
    private GraphEntry initialWaterDepthEntry;
    private GraphEntry finalWaterDepthEntry;
    private GraphEntry waterDepthDateEntry;
    private ArrayList<GraphEntry> waterSamplingEntryList;
    private ArrayList<GraphEntry> originalSamplingEntryList;
    private ArrayList<GraphEntry> disturbanceSamplingEntryList;

    public ArrayList<GraphEntry> getDateEntryList() {
        return dateEntryList;
    }

    public void setDateEntryList(ArrayList<GraphEntry> dateEntryList) {
        this.dateEntryList = dateEntryList;
    }

    public ArrayList<RigEntry> getRigEntryList() {
        return rigEntryList;
    }

    public void setRigEntryList(ArrayList<RigEntry> rigEntryList) {
        this.rigEntryList = rigEntryList;
    }

    public ArrayList<GraphEntry> getTrEntryList() {
        return trEntryList;
    }

    public void setTrEntryList(ArrayList<GraphEntry> trEntryList) {
        this.trEntryList = trEntryList;
    }

    public ArrayList<GraphEntry> getRockCoreEntryList() {
        return rockCoreEntryList;
    }

    public void setRockCoreEntryList(ArrayList<GraphEntry> rockCoreEntryList) {
        this.rockCoreEntryList = rockCoreEntryList;
    }

    public GraphEntry getInitialWaterDepthEntry() {
        return initialWaterDepthEntry;
    }

    public void setInitialWaterDepthEntry(GraphEntry initialWaterDepthEntry) {
        this.initialWaterDepthEntry = initialWaterDepthEntry;
    }

    public GraphEntry getFinalWaterDepthEntry() {
        return finalWaterDepthEntry;
    }

    public void setFinalWaterDepthEntry(GraphEntry finalWaterDepthEntry) {
        this.finalWaterDepthEntry = finalWaterDepthEntry;
    }

    public GraphEntry getWaterDepthDateEntry() {
        return waterDepthDateEntry;
    }

    public void setWaterDepthDateEntry(GraphEntry waterDepthDateEntry) {
        this.waterDepthDateEntry = waterDepthDateEntry;
    }

    public ArrayList<GraphEntry> getWaterSamplingEntryList() {
        return waterSamplingEntryList;
    }

    public void setWaterSamplingEntryList(ArrayList<GraphEntry> waterSamplingEntryList) {
        this.waterSamplingEntryList = waterSamplingEntryList;
    }

    public ArrayList<GraphEntry> getOriginalSamplingEntryList() {
        return originalSamplingEntryList;
    }

    public void setOriginalSamplingEntryList(ArrayList<GraphEntry> originalSamplingEntryList) {
        this.originalSamplingEntryList = originalSamplingEntryList;
    }

    public ArrayList<GraphEntry> getDisturbanceSamplingEntryList() {
        return disturbanceSamplingEntryList;
    }

    public void setDisturbanceSamplingEntryList(ArrayList<GraphEntry> disturbanceSamplingEntryList) {
        this.disturbanceSamplingEntryList = disturbanceSamplingEntryList;
    }
}



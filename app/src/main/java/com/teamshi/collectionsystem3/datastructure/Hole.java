package com.teamshi.collectionsystem3.datastructure;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * Created by Alfred on 16/7/14.
 */
public class Hole implements Serializable {
    public static class FixItem implements  Serializable{
        private static final long serialVersionUID = -6820511806124628509L;

        private String originItem;
        private String fixedItem;

        public String getOriginItem() {
            return originItem;
        }

        public void setOriginItem(String originItem) {
            this.originItem = originItem;
        }

        public String getFixedItem() {
            return fixedItem;
        }

        public void setFixedItem(String fixedItem) {
            this.fixedItem = fixedItem;
        }

        public FixItem(String originItem, String fixedItem) {
            this.originItem = originItem;
            this.fixedItem = fixedItem;
        }

        public FixItem deepCopy() {
            FixItem newItem = new FixItem(originItem, fixedItem);

            return newItem;
        }
    }
    private static final long serialVersionUID = -7800855430416140956L;
    private String projectName;                     // 工程名称

    /* 钻探编号或者为五段部分, 或者为其他, 由用户编辑纯文本.*/
    private boolean isSpecialHoleId;                // 标示该孔是否为特殊钻探编号

    private String holeIdPart1;                         // 钻探编号第一部分. (JC, JZ) 默认JC
    private String holeIdPart2;                         // 钻探编号第二部分. (I, II, III, IV)
    private String holeIdPart3;                         // 钻探编号第三部分, 默认为该年年份, 可编辑, 可修改
    private String holeIdPart4;                         // 钻探编号第四部分, (1, 2, 3, 4)
    private String holeIdPart5;                         // 钻探编号第五部分, 纯文本.默认值为1.

    private String specialHoleId;                   // 钻探特殊编号.当且仅当特殊钻探编号为true时有效.

    private Calendar startDate;                     // 开始日期
    private Calendar endDate;                       // 完工日期

    private boolean isSpecialArticle;               // 标示该孔是否为特殊冠词
    private String article;                         // 冠词, 默认值为DK. 取值范围为(K, DK, AK, ACK, CDK或其他)

    private double mileage;                         // 里程数
    private double offset;                          // `偏移量
    private double holeHeight;                      // 孔口标高
    private double holeDepth;                       // 设计孔深

    private String rigMachineType;                  // 钻机类型
    private String engineType;                      // 发动机类型
    private String pumpType;                        // 水泵类型

    private double initialWaterDepth;               // 初见水位
    private double finalWaterDepth;                 // 稳定水位
    private Calendar initialWaterDepthLoggedDate;   // 初见水位记录日期
    private Calendar finalWaterDepthLoggedDate;     // 稳定水位记录日期

    private double longitude;                       // 经距
    private double latitude;                        // 纬距

    private String company;                         // 勘探单位
    private String machineId;                       // 机组编号

    private double actualDepth;                     // 实际孔深

    private String note;                            // 备注
    private String positionInformation;             // 位置信息

    private String recorder;                        // 记录者
    private String reviewer;                        // 复核者

    private Calendar recordDate;                    // 记录日期
    private Calendar reviewDate;                    // 复核日期

    private String classMonitor;                    // 班长
    private String machineMonitor;                  // 机长

    private int maxRigRockCoreIndex;

    private ArrayList<Rig> rigList;

    private String lastClassPeopleCount;

    private ArrayList<Double> pipeArray;

    private Calendar lastRigEndTime;
    private Calendar lastDate;

    private double lastAccumulatedMeterageLength;

    private String lastRockName;
    private String lastRockColor;
    private String lastRockDentisy;
    private String lastRockSaturation;
    private String lastRockWeathering;

    private boolean isApproved;

    private int rockCoreIndex;

    private int originalSampleIndex;

    private OtherSamplingRig disturbanceSample;
    private OtherSamplingRig rockSample;
    private OtherSamplingRig waterSample;

    private double lastRockCorePipeLength;
    private int lastTRIndex;
    private int lastTR108Index;

    private int lastTRLength;
    private int lastTR108Length;

    private RigGraphData rigGraphData;

    private FixItem[] holeFixItems;

    private Calendar fixDate;

    private String fixSignature;

    public Hole() {

    }

    // Default hole while creating.
    public Hole(String projectName) {
        this.projectName = projectName;

        this.isSpecialHoleId = false;

        this.holeIdPart1 = "JZ";
        this.holeIdPart2 = "I";
        String currentYearString = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
        this.holeIdPart3 = currentYearString.substring(currentYearString.length() - 2);
        this.holeIdPart4 = "1";
        this.holeIdPart5 = "1";

        this.startDate = Calendar.getInstance();
        this.endDate = Calendar.getInstance();

        this.article = "DK";

        this.mileage = 0;
        this.offset = 0;
        this.holeHeight = 0;
        this.holeDepth = 0;

        this.rigMachineType = "XJ-1";
        this.engineType = "S1105";
        this.pumpType = "BW50";

        this.initialWaterDepth = 0;
        this.finalWaterDepth = 0;
        this.initialWaterDepthLoggedDate = Calendar.getInstance();
        this.finalWaterDepthLoggedDate = Calendar.getInstance();

        this.latitude = 0;
        this.longitude = 0;

        this.company = "铁四院工勘院地质_队";
        this.machineId = "4000";

        this.actualDepth = 0;

        this.note = "";
        this.positionInformation = "";

        this.recorder = "";
        this.reviewer = "";

        this.recordDate = Calendar.getInstance();
        this.reviewDate = Calendar.getInstance();

        this.classMonitor = "";
        this.machineMonitor = "";

        this.rigList = new ArrayList<>();

        this.lastClassPeopleCount = "";

        this.pipeArray = new ArrayList<>();

        this.lastRigEndTime = Calendar.getInstance();
        this.lastDate = Calendar.getInstance();

        this.lastAccumulatedMeterageLength = 0;
        this.lastRockCorePipeLength = 0;

        this.maxRigRockCoreIndex = 0;

        this.isApproved = false;

        this.rockCoreIndex = 0;

        this.originalSampleIndex = 1;

        this.lastRockName = "黏土";
        this.lastRockColor = "灰色";
        this.lastRockDentisy = "坚硬";
        this.lastRockSaturation = "";
        this.lastRockWeathering = "";

        disturbanceSample = new OtherSamplingRig("", Calendar.getInstance(), Calendar.getInstance(), Calendar.getInstance(), "扰动样");
        rockSample = new OtherSamplingRig("", Calendar.getInstance(), Calendar.getInstance(), Calendar.getInstance(), "岩样");
        waterSample = new OtherSamplingRig("", Calendar.getInstance(), Calendar.getInstance(), Calendar.getInstance(), "水样");

        this.lastTRIndex = 1;
        this.lastTR108Index = 1;

        this.lastTRLength = 0;
        this.lastTRLength = 0;

        this.rigGraphData = new RigGraphData();

        this.holeFixItems = new FixItem[8];

        for (int i = 0; i < 8; i++) {
            holeFixItems[i] = new FixItem("", "");
        }

        this.fixSignature = "";

        this.fixDate = Calendar.getInstance();
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public boolean isSpecialHoleId() {
        return isSpecialHoleId;
    }

    public void setSpecialHoleId(boolean specialHoleId) {
        isSpecialHoleId = specialHoleId;
    }

    public String getHoleIdPart1() {
        return holeIdPart1;
    }

    public void setHoleIdPart1(String holeIdPart1) {
        this.holeIdPart1 = holeIdPart1;
    }

    public String getHoleIdPart2() {
        return holeIdPart2;
    }

    public void setHoleIdPart2(String holeIdPart2) {
        this.holeIdPart2 = holeIdPart2;
    }

    public String getHoleIdPart3() {
        return holeIdPart3;
    }

    public void setHoleIdPart3(String holeIdPart3) {
        this.holeIdPart3 = holeIdPart3;
    }

    public String getHoleIdPart4() {
        return holeIdPart4;
    }

    public void setHoleIdPart4(String holeIdPart4) {
        this.holeIdPart4 = holeIdPart4;
    }

    public String getHoleIdPart5() {
        return holeIdPart5;
    }

    public void setHoleIdPart5(String holeIdPart5) {
        this.holeIdPart5 = holeIdPart5;
    }

    public String getSpecialHoleId() {
        return specialHoleId;
    }

    public void setSpecialHoleId(String specialHoleId) {
        this.specialHoleId = specialHoleId;
    }

    public Calendar getStartDate() {
        return startDate;
    }

    public void setStartDate(Calendar startDate) {
        this.startDate = startDate;
    }

    public Calendar getEndDate() {
        return endDate;
    }

    public void setEndDate(Calendar endDate) {
        this.endDate = endDate;
    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public double getMileage() {
        return mileage;
    }

    public void setMileage(double mileage) {
        this.mileage = mileage;
    }

    public double getOffset() {
        return offset;
    }

    public void setOffset(double offset) {
        this.offset = offset;
    }

    public double getHoleHeight() {
        return holeHeight;
    }

    public void setHoleHeight(double holeHeight) {
        this.holeHeight = holeHeight;
    }

    public double getHoleDepth() {
        return holeDepth;
    }

    public void setHoleDepth(double holeDepth) {
        this.holeDepth = holeDepth;
    }

    public String getRigMachineType() {
        return rigMachineType;
    }

    public void setRigMachineType(String rigMachineType) {
        this.rigMachineType = rigMachineType;
    }

    public String getEngineType() {
        return engineType;
    }

    public void setEngineType(String engineType) {
        this.engineType = engineType;
    }

    public String getPumpType() {
        return pumpType;
    }

    public void setPumpType(String pumpType) {
        this.pumpType = pumpType;
    }

    public double getInitialWaterDepth() {
        return initialWaterDepth;
    }

    public void setInitialWaterDepth(double initialWaterDepth) {
        this.initialWaterDepth = initialWaterDepth;
    }

    public double getFinalWaterDepth() {
        return finalWaterDepth;
    }

    public void setFinalWaterDepth(double finalWaterDepth) {
        this.finalWaterDepth = finalWaterDepth;
    }

    public Calendar getInitialWaterDepthLoggedDate() {
        return initialWaterDepthLoggedDate;
    }

    public void setInitialWaterDepthLoggedDate(Calendar initialWaterDepthLoggedDate) {
        this.initialWaterDepthLoggedDate = initialWaterDepthLoggedDate;
    }

    public Calendar getFinalWaterDepthLoggedDate() {
        return finalWaterDepthLoggedDate;
    }

    public void setFinalWaterDepthLoggedDate(Calendar finalWaterDepthLoggedDate) {
        this.finalWaterDepthLoggedDate = finalWaterDepthLoggedDate;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getMachineId() {
        return machineId;
    }

    public void setMachineId(String machineId) {
        this.machineId = machineId;
    }

    public double getActualDepth() {
        return actualDepth;
    }

    public void setActualDepth(double actualDepth) {
        this.actualDepth = actualDepth;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getPositionInformation() {
        return positionInformation;
    }

    public void setPositionInformation(String positionInformation) {
        this.positionInformation = positionInformation;
    }

    public String getRecorder() {
        return recorder;
    }

    public void setRecorder(String recorder) {
        this.recorder = recorder;
    }

    public String getReviewer() {
        return reviewer;
    }

    public void setReviewer(String reviewer) {
        this.reviewer = reviewer;
    }

    public Calendar getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(Calendar recordDate) {
        this.recordDate = recordDate;
    }

    public Calendar getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(Calendar reviewDate) {
        this.reviewDate = reviewDate;
    }

    public String getClassMonitor() {
        return classMonitor;
    }

    public void setClassMonitor(String classMonitor) {
        this.classMonitor = classMonitor;
    }

    public String getMachineMonitor() {
        return machineMonitor;
    }

    public void setMachineMonitor(String machineMonitor) {
        this.machineMonitor = machineMonitor;
    }

    public boolean isSpecialArticle() {
        return isSpecialArticle;
    }

    public void setSpecialArticle(boolean specialArticle) {
        isSpecialArticle = specialArticle;
    }

    public String getLastRockDentisy() {
        return lastRockDentisy;
    }

    public void setLastRockDentisy(String lastRockDentisy) {
        this.lastRockDentisy = lastRockDentisy;
    }

    public String getLastRockWeathering() {
        return lastRockWeathering;
    }

    public void setLastRockWeathering(String lastRockWeathering) {
        this.lastRockWeathering = lastRockWeathering;
    }

    public int getLastTR108Index() {
        return lastTR108Index;
    }

    public void setLastTR108Index(int lastTR108Index) {
        this.lastTR108Index = lastTR108Index;
    }

    public FixItem[] getHoleFixItems() {
        return holeFixItems;
    }

    public void setHoleFixItems(FixItem[] holeFixItems) {
        this.holeFixItems = holeFixItems;
    }

    public Calendar getFixDate() {
        return fixDate;
    }

    public void setFixDate(Calendar fixDate) {
        this.fixDate = fixDate;
    }

    public String getFixSignature() {
        return fixSignature;
    }

    public void setFixSignature(String fixSignature) {
        this.fixSignature = fixSignature;
    }

    public String getHoleId() {
        if (isSpecialHoleId()) {
            return specialHoleId;
        } else {
            StringBuilder sb = new StringBuilder();

            sb.append(holeIdPart1);
            sb.append("-");
            sb.append(holeIdPart2);
            sb.append(holeIdPart3);
            sb.append("-");
            sb.append(holeIdPart4);
            sb.append("-");
            sb.append(holeIdPart5);

            return sb.toString();
        }
    }

    public ArrayList<Rig> getRigList() {
        return rigList;
    }

    public ArrayList<Rig> getRigIndexViewList() {
        ArrayList<Rig> viewList = new ArrayList<>();

        for (Rig rig : rigList) {
            viewList.add(rig);
        }


        for (OtherSamplingRig.OtherSamplingDetail detail : disturbanceSample.getDetails()) {
            boolean hitFlag = false;

            for (int i = 0; i < viewList.size(); i++) {
                if (viewList.get(i) instanceof OtherSamplingRig.OtherSamplingDetail) {
                    if (detail.getEndDepth() < ((OtherSamplingRig.OtherSamplingDetail) viewList.get(i)).getEndDepth()) {
                        viewList.add(i + 1, detail);
                        hitFlag = true;
                        break;
                    }
                } else if (viewList.get(i) instanceof CalculatingRig) {
                    if (detail.getEndDepth() < ((CalculatingRig) viewList.get(i)).getAccumulatedMeterageLength()) {
                        viewList.add(i + 1, detail);
                        hitFlag = true;
                        break;
                    }
                } else {
                    continue;
                }
            }

            if (!hitFlag) {
                viewList.add(detail);
            }
        }


        for (OtherSamplingRig.OtherSamplingDetail detail : rockSample.getDetails()) {
            boolean hitFlag = false;

            for (int i = 0; i < viewList.size(); i++) {
                if (viewList.get(i) instanceof OtherSamplingRig.OtherSamplingDetail) {
                    if (detail.getEndDepth() < ((OtherSamplingRig.OtherSamplingDetail) viewList.get(i)).getEndDepth()) {
                        viewList.add(i + 1, detail);
                        hitFlag = true;
                        break;
                    }
                } else if (viewList.get(i) instanceof CalculatingRig) {
                    if (detail.getEndDepth() < ((CalculatingRig) viewList.get(i)).getAccumulatedMeterageLength()) {
                        viewList.add(i + 1, detail);
                        hitFlag = true;
                        break;
                    }
                } else {
                    continue;
                }
            }

            if (!hitFlag) {
                viewList.add(detail);
            }
        }

        for (OtherSamplingRig.OtherSamplingDetail detail : waterSample.getDetails()) {
            boolean hitFlag = false;

            for (int i = 0; i < viewList.size(); i++) {
                if (viewList.get(i) instanceof OtherSamplingRig.OtherSamplingDetail) {
                    if (detail.getEndDepth() < ((OtherSamplingRig.OtherSamplingDetail) viewList.get(i)).getEndDepth()) {
                        viewList.add(i + 1, detail);
                        hitFlag = true;
                        break;
                    }
                } else if (viewList.get(i) instanceof CalculatingRig) {
                    if (detail.getEndDepth() < ((CalculatingRig) viewList.get(i)).getAccumulatedMeterageLength()) {
                        viewList.add(i + 1, detail);
                        hitFlag = true;
                        break;
                    }
                } else {
                    continue;
                }
            }

            if (!hitFlag) {
                viewList.add(detail);
            }
        }

        return viewList;
    }

    public void setRigList(ArrayList<Rig> rigList) {
        this.rigList = rigList;
    }

    public String getLastClassPeopleCount() {
        return lastClassPeopleCount;
    }

    public void setLastClassPeopleCount(String lastClassPeopleCount) {
        this.lastClassPeopleCount = lastClassPeopleCount;
    }

    public int getPipeCount() {
        return pipeArray.size();
    }

    public double getPipeLength() {
        return pipeArray.get(pipeArray.size() - 1);
    }

    public double getTotalPipeLength() {
        double sum = 0;

        for (Double length : pipeArray) {
            sum += length;
        }

        return sum;
    }

    public ArrayList<Double> getPipeArray() {
        return pipeArray;
    }

    public void setPipeArray(ArrayList<Double> pipeArray) {
        this.pipeArray = pipeArray;
    }

    public Calendar getLastRigEndTime() {
        return lastRigEndTime;
    }

    public void setLastRigEndTime(Calendar lastRigEndTime) {
        this.lastRigEndTime = lastRigEndTime;
    }

    public Calendar getLastDate() {
        return lastDate;
    }

    public void setLastDate(Calendar lastDate) {
        this.lastDate = lastDate;
    }

    public double getLastAccumulatedMeterageLength() {
        return lastAccumulatedMeterageLength;
    }

    public void setLastAccumulatedMeterageLength(double lastAccumulatedMeterageLength) {
        this.lastAccumulatedMeterageLength = lastAccumulatedMeterageLength;
    }

    public void addPipe(double length) {
        pipeArray.add(length);
    }

    public void removeLastPipe() {
        pipeArray.remove(pipeArray.size() - 1);
    }

    public double getLastRockCorePipeLength() {
        return lastRockCorePipeLength;
    }

    public void setLastRockCorePipeLength(double lastRockCorePipeLength) {
        this.lastRockCorePipeLength = lastRockCorePipeLength;
    }

    public int getMaxRigRockCoreIndex() {
        return maxRigRockCoreIndex;
    }

    public void setMaxRigRockCoreIndex(int maxRigRockCoreIndex) {
        this.maxRigRockCoreIndex = maxRigRockCoreIndex;
    }

    public String getLastRockName() {
        return lastRockName;
    }

    public void setLastRockName(String lastRockName) {
        this.lastRockName = lastRockName;
    }

    public String getLastRockColor() {
        return lastRockColor;
    }

    public void setLastRockColor(String lastRockColor) {
        this.lastRockColor = lastRockColor;
    }

    public String getLastRockSaturation() {
        return lastRockSaturation;
    }

    public void setLastRockSaturation(String lastRockSaturation) {
        this.lastRockSaturation = lastRockSaturation;
    }

    public boolean isApproved() {
        return isApproved;
    }

    public void setApproved(boolean approved) {
        isApproved = approved;
    }

    public int getRockCoreIndex() {
        return rockCoreIndex;
    }

    public void setRockCoreIndex(int rockCoreIndex) {
        this.rockCoreIndex = rockCoreIndex;
    }

    public int getOriginalSampleIndex() {
        return originalSampleIndex;
    }

    public void setOriginalSampleIndex(int originalSampleIndex) {
        this.originalSampleIndex = originalSampleIndex;
    }

    public OtherSamplingRig getDisturbanceSample() {
        return disturbanceSample;
    }

    public void setDisturbanceSample(OtherSamplingRig disturbanceSample) {
        this.disturbanceSample = disturbanceSample;
    }

    public OtherSamplingRig getRockSample() {
        return rockSample;
    }

    public void setRockSample(OtherSamplingRig rockSample) {
        this.rockSample = rockSample;
    }

    public OtherSamplingRig getWaterSample() {
        return waterSample;
    }

    public void setWaterSample(OtherSamplingRig waterSample) {
        this.waterSample = waterSample;
    }

    public int getLastTRIndex() {
        return lastTRIndex;
    }

    public void setLastTRIndex(int lastTRIndex) {
        this.lastTRIndex = lastTRIndex;
    }

    public RigGraphData getRigGraphData() {
        return rigGraphData;
    }

    public void setRigGraphData(RigGraphData rigGraphData) {
        this.rigGraphData = rigGraphData;
    }

    public int getLastTRLength() {
        return lastTRLength;
    }

    public void setLastTRLength(int lastTRLength) {
        this.lastTRLength = lastTRLength;
    }

    public int getLastTR108Length() {
        return lastTR108Length;
    }

    public void setLastTR108Length(int lastTR108Length) {
        this.lastTR108Length = lastTR108Length;
    }

    public Hole deepCopy() {
        Hole newHole = new Hole(projectName);

        newHole.setSpecialHoleId(isSpecialHoleId);
        newHole.setHoleIdPart1(holeIdPart1);
        newHole.setHoleIdPart2(holeIdPart2);
        newHole.setHoleIdPart3(holeIdPart3);
        newHole.setHoleIdPart4(holeIdPart4);
        newHole.setHoleIdPart5(holeIdPart5);

        newHole.setSpecialHoleId(specialHoleId);

        newHole.setStartDate((Calendar) startDate.clone());
        newHole.setEndDate((Calendar) endDate.clone());

        newHole.setSpecialArticle(isSpecialArticle);
        newHole.setArticle(article);

        newHole.setMileage(mileage);
        newHole.setOffset(offset);
        newHole.setHoleHeight(holeHeight);
        newHole.setHoleDepth(holeDepth);

        newHole.setRigMachineType(rigMachineType);
        newHole.setEngineType(engineType);
        newHole.setPumpType(pumpType);

        newHole.setInitialWaterDepth(initialWaterDepth);
        newHole.setFinalWaterDepth(finalWaterDepth);
        newHole.setInitialWaterDepthLoggedDate(initialWaterDepthLoggedDate);
        newHole.setFinalWaterDepthLoggedDate(finalWaterDepthLoggedDate);

        newHole.setLongitude(longitude);
        newHole.setLatitude(latitude);

        newHole.setCompany(company);
        newHole.setMachineId(machineId);

        newHole.setActualDepth(actualDepth);

        newHole.setNote(note);
        newHole.setPositionInformation(positionInformation);

        newHole.setRecorder(recorder);
        newHole.setReviewer(reviewer);

        newHole.setReviewDate((Calendar) reviewDate.clone());
        newHole.setRecordDate((Calendar) recordDate.clone());

        newHole.setClassMonitor(classMonitor);
        newHole.setMachineMonitor(machineMonitor);

        ArrayList<Rig> newRigList = new ArrayList<>();
        for (Rig rig : this.getRigList()) {
            newRigList.add(rig.deepCopy());
        }
        newHole.setRigList(newRigList);

        newHole.setPipeArray(new ArrayList<Double>());

        for (double pipeLength : pipeArray) {
            newHole.getPipeArray().add(pipeLength);
        }

        newHole.setLastRigEndTime((Calendar) lastRigEndTime.clone());

        newHole.setLastDate((Calendar) lastDate.clone());

        newHole.setLastAccumulatedMeterageLength(lastAccumulatedMeterageLength);

        newHole.setLastRockCorePipeLength(lastRockCorePipeLength);

        newHole.setMaxRigRockCoreIndex(maxRigRockCoreIndex);

        newHole.setLastRockName(lastRockName);

        newHole.setLastRockColor(lastRockColor);

        newHole.setLastRockSaturation(lastRockSaturation);

        newHole.setLastRockDentisy(lastRockDentisy);

        newHole.setLastRockWeathering(lastRockWeathering);

        newHole.setApproved(isApproved);

        newHole.setRockCoreIndex(rockCoreIndex);

        newHole.setOriginalSampleIndex(originalSampleIndex);

        newHole.setDisturbanceSample(disturbanceSample.deepCopy());

        newHole.setRockSample(rockSample.deepCopy());

        newHole.setWaterSample(waterSample.deepCopy());

        newHole.setLastTRIndex(lastTRIndex);

        newHole.setLastTR108Index(lastTR108Index);

        newHole.setRigGraphData(rigGraphData.deepCopy());

        newHole.setLastTRLength(lastTRLength);

        newHole.setLastTR108Length(lastTR108Length);

        for (int i = 0; i < 8; i++) {
            newHole.getHoleFixItems()[i] = holeFixItems[i];
        }

        newHole.setFixSignature(fixSignature);

        newHole.setFixDate((Calendar) fixDate.clone());

        return newHole;
    }
}

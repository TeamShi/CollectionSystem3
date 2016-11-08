package com.teamshi.collectionsystem3.parser;

import com.teamshi.collectionsystem3.Utility;
import com.teamshi.collectionsystem3.datastructure.DSTRig;
import com.teamshi.collectionsystem3.datastructure.Hole;
import com.teamshi.collectionsystem3.datastructure.NARig;
import com.teamshi.collectionsystem3.datastructure.OriginalSamplingRig;
import com.teamshi.collectionsystem3.datastructure.OtherSamplingRig;
import com.teamshi.collectionsystem3.datastructure.RegularRig;
import com.teamshi.collectionsystem3.datastructure.Rig;
import com.teamshi.collectionsystem3.datastructure.SPTRig;
import com.teamshi.collectionsystem3.datastructure.TRRig;

import java.util.ArrayList;
import java.util.List;

import static com.teamshi.collectionsystem3.Utility.formatCalendarDateString;

/**
 * Created by yueyue on 16/8/21.
 */
public class Parser {
    protected static final String NA = "NA";

    public static String[] convert2Array(String string) {

        String[] row = string.replaceAll("##", "# #").split("#");

        for (int j = 0, colLen = row.length; j < colLen; j++) {
            if (row[j].equals(NA)) {
                row[j] = "";
            }
        }
        return row;
    }

    protected static String[][] convertSpt(SPTRig sptRig, int index, String BR) {
        String[][] resultData = new String[1][];
        StringBuffer sb = new StringBuffer();
        if (null == BR || "".equals(BR)) {
            BR = ",";
        }

        sb.append(index).append("#");

        sb.append(formatCalendarDateString(sptRig.getDate(), "MM月")).append("#");
        sb.append(formatCalendarDateString(sptRig.getDate(), "dd日")).append("#");
        sb.append(sptRig.getClassPeopleCount()).append("#");
        sb.append(formatCalendarDateString(sptRig.getStartTime(), "hh时mm分")).append("#");
        sb.append(formatCalendarDateString(sptRig.getEndTime(), "hh时mm分")).append("#");

        //分层
        sb.append(NA).append("#");

        //进尺
        sb.append(Utility.formatDouble(sptRig.getPenetrationStartDepth())).append("#");
        sb.append(Utility.formatDouble(sptRig.getPenetrationEndDepth())).append("#");

        sb.append(Utility.formatDouble(sptRig.getCountStartDepth1()) + BR + Utility.formatDouble(sptRig.getCountStartDepth2()) + BR + Utility.formatDouble(sptRig.getCountStartDepth3())).append("#");
        sb.append(Utility.formatDouble(sptRig.getCountEndDepth1()) + BR + Utility.formatDouble(sptRig.getCountEndDepth2()) + BR + Utility.formatDouble(sptRig.getCountEndDepth3())).append("#");

        sb.append(Utility.formatDouble(sptRig.getDrillStartDepth1()) + BR + Utility.formatDouble(sptRig.getDrillStartDepth2()) + BR + Utility.formatDouble(sptRig.getDrillStartDepth3())).append("#");
        sb.append(Utility.formatDouble(sptRig.getDrillEndDepth1()) + BR + Utility.formatDouble(sptRig.getDrillEndDepth2()) + BR + Utility.formatDouble(sptRig.getDrillEndDepth3())).append("#");

        //分类
        sb.append(NA).append("#");

        //野外描述
        sb.append(sptRig.getOldRockColor()).append("#");
        sb.append(sptRig.getOldRockDensity()).append("#");
        sb.append(NA).append("#");
        sb.append(sptRig.getRockName()).append("#");
        sb.append(sptRig.getOldRockSaturation()).append("#");
        //光泽
        sb.append(NA).append("#");
        sb.append(NA).append("#");
        sb.append(NA).append("#");

        sb.append(sptRig.getOtherDescription()).append("#");

        sb.append(sptRig.getHitCount1() + BR + sptRig.getHitCount2() + BR + sptRig.getHitCount3()).append("#");

        sb.append(NA).append("#");
        sb.append(NA).append("#");
        sb.append(NA).append("#");

        resultData[0] = convert2Array(sb.toString());

        return resultData;
    }

    protected static String[][] convertDst(DSTRig dstRig) {
        ArrayList<DSTRig.DSTDetailInfo> details = dstRig.getDstDetailInfos();

        StringBuffer sb;
        String[][] resultData = new String[details.size()][];

        for (int index = 0; index < details.size(); index++) {
            sb = new StringBuffer();
            DSTRig.DSTDetailInfo detailInfo = details.get(index);
            sb.append(Utility.formatDouble(detailInfo.getPipeLength())).append("#");
            sb.append(Utility.formatDouble(detailInfo.getDepth())).append("#");
            sb.append(Utility.formatDouble(detailInfo.getLength())).append("#");
            sb.append(detailInfo.getHitCount()).append("#");
            sb.append(NA).append("#");
            sb.append(NA).append("#");
            sb.append(detailInfo.getSaturationDescription().equals("") ? NA : detailInfo.getSaturationDescription()).append("#");
            resultData[index] = convert2Array(sb.toString());
        }

        return resultData;
    }

    protected static String[][] convertDistributionSmpl(Hole hole, OtherSamplingRig otherSamplingRig, String BR) {
        StringBuffer sb;
        if (null == BR || "".equals(BR)) {
            BR = ",";
        }

        ArrayList<OtherSamplingRig.OtherSamplingDetail> details = otherSamplingRig.getDetails();
        String[][] resultData = new String[details.size()][];
        for (int i = 0, len = details.size(); i < len; i++) {
            OtherSamplingRig.OtherSamplingDetail detail = details.get(i);
            sb = new StringBuffer();

            //实验室
            sb.append("").append("#");

            //外业
            sb.append(detail.getIndex()).append("#");

            //勘探点编号
            sb.append(hole.getHoleId()).append("#");

            //取样地点
            sb.append("").append("#");

            //试件深度
            sb.append(detail.getStartDepth() + BR + detail.getEndDepth()).append("#");

            //野外鉴定名称
            sb.append("").append("#");

            //工程名称
            sb.append("").append("#");

            //原状土(直径)
            sb.append("").append("#");

            //扰动土(千克)
            sb.append("").append("#");

            //备注
            sb.append(NA).append("#");

            resultData[i] = convert2Array(sb.toString());
        }

        return resultData;

    }


    protected static String[][] convertOriSmpl(Hole hole, OriginalSamplingRig originalSamplingRig, String BR) {
        String[][] resultData = new String[1][];
        StringBuffer sb = new StringBuffer();
        if (null == BR || "".equals(BR)) {
            BR = ",";
        }

        //实验室
        sb.append("").append("#");

        //外业
        sb.append(originalSamplingRig.getIndex()).append("#");

        //勘探点编号
        sb.append(hole.getHoleId()).append("#");

        //取样地点
        sb.append("").append("#");

        //试件深度
        sb.append(originalSamplingRig.getStartDepth() + BR + originalSamplingRig.getEndDepth()).append("#");

        //野外鉴定名称
        sb.append("").append("#");

        //工程名称
        sb.append("").append("#");

        //原状土(直径)
        sb.append("").append("#");

        //扰动土(千克)
        sb.append("").append("#");

        //备注
        sb.append(originalSamplingRig.getRockDescription()).append("#");

        resultData[0] = convert2Array(sb.toString());

        return resultData;
    }


    protected static String[][] convertWaterSmpl(Hole hole, OtherSamplingRig otherSamplingRig, String BR) {
        StringBuffer sb;
        if (null == BR || "".equals(BR)) {
            BR = ",";
        }
        ArrayList<OtherSamplingRig.OtherSamplingDetail> details = otherSamplingRig.getDetails();
        String[][] resultData = new String[details.size()][];
        for (int i = 0, len = details.size(); i < len; i++) {
            OtherSamplingRig.OtherSamplingDetail detail = details.get(i);
            sb = new StringBuffer();
            //外业
            sb.append(detail.getIndex()).append("#");
            //实验室
            sb.append("").append("#");
            //钻孔编号
            sb.append(hole.getHoleId()).append("#");
            //取样日期
            sb.append(formatCalendarDateString(otherSamplingRig.getDate(), "MM月dd日")).append("#");

            //取样地点
            sb.append("").append("#");

            //深度
            sb.append(detail.getStartDepth() + BR + detail.getEndDepth()).append("#");

            sb.append("").append("#");
            sb.append("").append("#");
            sb.append("").append("#");
            sb.append("").append("#");
            sb.append("").append("#");

            resultData[i] = convert2Array(sb.toString());
        }

        return resultData;
    }

    protected static String[][] convertWaterSmplDetail(Hole hole, OtherSamplingRig.OtherSamplingDetail detail, String BR) {
        if (null == BR || "".equals(BR)) {
            BR = ",";
        }

        StringBuffer sb = new StringBuffer();
        String[][] resultData = new String[1][];

        //外业
        sb.append(detail.getIndex()).append("#");
        //实验室
        sb.append("").append("#");
        //钻孔编号
        sb.append(hole.getHoleId()).append("#");
        //取样日期
        sb.append(formatCalendarDateString(detail.getDate(), "MM月dd日")).append("#");

        //取样地点
        sb.append("").append("#");

        //深度
        sb.append(detail.getStartDepth() + BR + detail.getEndDepth()).append("#");

        sb.append("").append("#");
        sb.append("").append("#");
        sb.append("").append("#");
        sb.append("").append("#");
        sb.append("").append("#");

        resultData[0] = convert2Array(sb.toString());

        return resultData;
    }

    protected static String[][] convertRockSmpl(Hole hole, OtherSamplingRig otherSamplingRig, String BR) {
        if (null == BR || "".equals(BR)) {
            BR = ",";
        }

        ArrayList<OtherSamplingRig.OtherSamplingDetail> details = otherSamplingRig.getDetails();
        String[][] resultData = new String[details.size()][];
        StringBuffer sb;
        for (int i = 0, len = details.size(); i < len; i++) {
            OtherSamplingRig.OtherSamplingDetail detail = details.get(i);
            sb = new StringBuffer();

            //外业
            sb.append(detail.getIndex()).append("#");
            //实验室
            sb.append("").append("#");
            //钻孔编号
            sb.append(hole.getHoleId()).append("#");
            //取样地点
            sb.append("").append("#");
            //深度
            sb.append(detail.getStartDepth() + BR + detail.getEndDepth()).append("#");

            //岩石名称
            sb.append(otherSamplingRig.getLastRockName()).append("#");
            sb.append(otherSamplingRig.getLastRockSaturation()).append("#");

            //工程名称
            sb.append("").append("#");

            sb.append("").append("#");
            sb.append("").append("#");
            sb.append("").append("#");
            sb.append("").append("#");
            sb.append("").append("#");
            sb.append("").append("#");
            sb.append("").append("#");
            sb.append("").append("#");
            sb.append("").append("#");
            sb.append("").append("#");

            resultData[i] = convert2Array(sb.toString());
        }

        return resultData;
    }

    protected static String[][] convertEarthSmplDetail(Hole hole, OtherSamplingRig.OtherSamplingDetail detail, String BR) {
        StringBuffer sb;
        if (null == BR || "".equals(BR)) {
            BR = ",";
        }

        sb = new StringBuffer();
        String[][] resultData = new String[1][];

        //实验室
        sb.append("").append("#");

        //外业
        sb.append(detail.getIndex()).append("#");

        //勘探点编号
        sb.append(hole.getHoleId()).append("#");

        //取样地点
        sb.append("").append("#");

        //试件深度
        sb.append(detail.getStartDepth() + BR + detail.getEndDepth()).append("#");

        //野外鉴定名称
        sb.append("").append("#");

        //工程名称
        sb.append("").append("#");

        //原状土(直径)
        sb.append("").append("#");

        //扰动土(千克)
        sb.append("").append("#");

        //备注
        sb.append(NA).append("#");

        resultData[0] = convert2Array(sb.toString());

        return resultData;
    }

    protected static String[][] convertRockSmplDetail(Hole hole, OtherSamplingRig.OtherSamplingDetail detail, String BR) {
        if (null == BR || "".equals(BR)) {
            BR = ",";
        }

        StringBuffer sb = new StringBuffer();
        String[][] resultData = new String[1][];

        //外业
        sb.append(detail.getIndex()).append("#");
        //实验室
        sb.append("").append("#");
        //钻孔编号
        sb.append(hole.getHoleId()).append("#");
        //取样地点
        sb.append("").append("#");
        //深度
        sb.append(detail.getStartDepth() + BR + detail.getEndDepth()).append("#");

        //岩石名称
        sb.append(detail.getLastRockName()).append("#");
        sb.append(detail.getLastRockSaturation()).append("#");

        //工程名称
        sb.append("").append("#");

        sb.append("").append("#");
        sb.append("").append("#");
        sb.append("").append("#");
        sb.append("").append("#");
        sb.append("").append("#");
        sb.append("").append("#");
        sb.append("").append("#");
        sb.append("").append("#");
        sb.append("").append("#");
        sb.append("").append("#");

        resultData[0] = convert2Array(sb.toString());

        return resultData;
    }

    protected static void generateEarthSampleIndexInfo(Rig nextRig, StringBuffer sb) {
        if (nextRig != null
                && nextRig instanceof OtherSamplingRig.OtherSamplingDetail
                && (
                ((OtherSamplingRig.OtherSamplingDetail) nextRig).getSamplingType().equals("扰动样")
                        || ((OtherSamplingRig.OtherSamplingDetail) nextRig).getSamplingType().equals("岩样")
        )) {
            sb.append(((OtherSamplingRig.OtherSamplingDetail) nextRig).getIndex()).append("#");
            sb.append((String.valueOf(((OtherSamplingRig.OtherSamplingDetail) nextRig).getDiameter()))).append("#");
            sb.append((Utility.formatDouble(((OtherSamplingRig.OtherSamplingDetail) nextRig).getStartDepth()) + " ~ " + Utility.formatDouble(((OtherSamplingRig.OtherSamplingDetail) nextRig).getEndDepth()))).append("#");
            sb.append((String.valueOf(((OtherSamplingRig.OtherSamplingDetail) nextRig).getCount()))).append("#");
        } else {
            sb.append("").append("#");
            sb.append("").append("#");
            sb.append("").append("#");
            sb.append("").append("#");
        }
    }

    protected static void generateWaterSampleInfo(Rig nextRig, StringBuffer sb) {
        if (nextRig != null
                && nextRig instanceof OtherSamplingRig.OtherSamplingDetail
                && (
                ((OtherSamplingRig.OtherSamplingDetail) nextRig).getSamplingType().equals("水样")
        )) {
            sb.append(((OtherSamplingRig.OtherSamplingDetail) nextRig).getIndex()).append("#");
            sb.append(Utility.formatDouble(((OtherSamplingRig.OtherSamplingDetail) nextRig).getStartDepth()) +
                    " ~ " + Utility.formatDouble(((OtherSamplingRig.OtherSamplingDetail) nextRig).getEndDepth())).append("#");
            sb.append(String.valueOf(((OtherSamplingRig.OtherSamplingDetail) nextRig).getCount())).append("#");
            sb.append(((OtherSamplingRig.OtherSamplingDetail) nextRig).getIndex()).append("#");

        } else {
            sb.append("").append("#");
            sb.append("").append("#");
            sb.append("").append("#");
        }
    }

    protected static String[][] convertHole(Hole hole, String BR) {
        if (BR == null || BR.equals("")) {
            BR = ",";
        }
        ArrayList<Rig> rigs = hole.getRigIndexViewList();
        int rows = rigs.size();
        List<String> resultList = new ArrayList<>();
        String initialWaterDepth;
        String finalWaterDepth;
        boolean hasStart = false;
        for (int i = 0; i < rows; i++) {
            Rig rig = rigs.get(i);
            boolean isOtherSmplDetail = rig instanceof OtherSamplingRig.OtherSamplingDetail;
            if (isOtherSmplDetail) {
                continue;
            }

            Rig nextRig = i < rows - 1 ? rigs.get(i + 1) : null;
            boolean isNAType = rig instanceof NARig;
            boolean isRegular = rig instanceof RegularRig;
            boolean isSpt = rig instanceof SPTRig;
            boolean isDst = rig instanceof DSTRig;
            boolean isTrr = rig instanceof TRRig;
            boolean isOriSmpl = rig instanceof OriginalSamplingRig;

            if (hasStart) {
                initialWaterDepth = Utility.formatDouble(hole.getInitialWaterDepth());
                finalWaterDepth = Utility.formatDouble(hole.getFinalWaterDepth());
                hasStart = true;
            } else {
                initialWaterDepth = "";
                finalWaterDepth = "";
            }

            StringBuffer sb = new StringBuffer();

            sb.append(rig.getClassPeopleCount()).append("#");
            sb.append(formatCalendarDateString(rig.getDate(), "MM月dd日")).append("#");
            sb.append(formatCalendarDateString(rig.getStartTime(), "hh时mm分")).append("#");
            sb.append(formatCalendarDateString(rig.getEndTime(), "hh时mm分")).append("#");
            sb.append(Utility.calculateTimeSpanChinese(rig.getStartTime(), rig.getEndTime())).append("#");


            if (isRegular) {
                RegularRig regularRig = (RegularRig) rigs.get(i);

                sb.append(regularRig.getRigType()).append("#");
                //钻杆
                sb.append(regularRig.getPipeNumber()).append("#");
                sb.append(Utility.formatDouble(regularRig.getPipeLength())).append("#");
                sb.append(Utility.formatDouble(regularRig.getPipeTotalLength())).append("#");

                //岩芯管
                sb.append(regularRig.getRockCorePipeDiameter()).append("#");
                sb.append(Utility.formatDouble(regularRig.getRockCorePipeLength())).append("#");

                //钻头
                sb.append(regularRig.getDrillBitType()).append("#");
                sb.append(Utility.formatDouble(regularRig.getDrillBitDiameter())).append("#");
                sb.append(Utility.formatDouble(regularRig.getDrillBitLength())).append("#");

                //进尺
                sb.append(Utility.formatDouble(regularRig.getDrillToolTotalLength())).append("#");
                sb.append(Utility.formatDouble(regularRig.getDrillPipeRemainLength())).append("#");
                sb.append(Utility.formatDouble(regularRig.getRoundTripMeterageLength())).append("#");
                sb.append(Utility.formatDouble(regularRig.getAccumulatedMeterageLength())).append("#");
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
                generateEarthSampleIndexInfo(nextRig, sb);

                //水样
                generateWaterSampleInfo(nextRig, sb);

                //地层
                sb.append("").append("#");//编号, 四类普通钻,编号加1
                sb.append("").append("#"); //底层深度 本次累计进尺
                sb.append("").append("#");//层厚 本次累计进尺 -上次累计进尺
                sb.append(regularRig.getRockDescription()).append("#"); // 名称及岩性
                sb.append("").append("#"); //岩层等级

                //地下水 只填第一行
                sb.append("").append("#");
                sb.append(initialWaterDepth).append("#");
                sb.append(finalWaterDepth).append("#");
                sb.append("").append("#");

                //特殊情况记录 最后一个string 特殊处理
                sb.append(regularRig.getNote().trim().equals("") ? NA : regularRig.getNote()).append("#");

            } else if (isNAType) {
                NARig naRig = (NARig) rigs.get(i);

                sb.append(naRig.getNaType()).append("#");

                //钻杆
                sb.append("").append("#");
                sb.append("").append("#");
                sb.append("").append("#");

                //岩芯管
                sb.append("").append("#");
                sb.append("").append("#");

                //钻头
                sb.append("").append("#");
                sb.append("").append("#");
                sb.append("").append("#");

                //进尺
                sb.append("").append("#");
                sb.append("").append("#");
                sb.append("").append("#");
                sb.append("").append("#");

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
                generateEarthSampleIndexInfo(nextRig, sb);

                //水样
                generateWaterSampleInfo(nextRig, sb);

                //地层
                sb.append("").append("#");//编号, 四类普通钻,编号加1
                sb.append("").append("#"); //底层深度 本次累计进尺
                sb.append("").append("#");//层厚 本次累计进尺 -上次累计进尺
                sb.append("").append("#"); // 名称及岩性
                sb.append("").append("#"); //岩层等级

                //地下水 只填第一行
                sb.append("").append("#");
                sb.append(initialWaterDepth).append("#");
                sb.append(finalWaterDepth).append("#");
                sb.append("").append("#");

                //特殊情况记录 最后一个string 特殊处理
                sb.append(naRig.getNaType().trim().equals("") ? NA : naRig.getNaType()).append("#");

            } else if (isSpt) {
                SPTRig sptRig = (SPTRig) rigs.get(i);

                sb.append("标 贯").append("#");

                //钻杆
                sb.append(sptRig.getProbeType()).append("#");
                sb.append(Utility.formatDouble(sptRig.getProbeLength())).append("#");
                sb.append(NA).append("#");

                //岩芯管
                sb.append(sptRig.getInjectionToolDiameter()).append("#");
                sb.append(Utility.formatDouble(sptRig.getInjectionToolLength())).append("#");

                //钻头
                sb.append("").append("#");
                sb.append("").append("#");
                sb.append("").append("#");

                //进尺
                sb.append(Utility.formatDouble(sptRig.getDrillToolTotalLength())).append("#");
                sb.append(Utility.formatDouble(sptRig.getDrillPipeRemainLength())).append("#");
                sb.append(Utility.formatDouble(sptRig.getRoundTripMeterageLength())).append("#");
                sb.append(Utility.formatDouble(sptRig.getAccumulatedMeterageLength())).append("#");
                //护壁措施
                sb.append("").append("#");
                sb.append("").append("#");
                sb.append("").append("#");
                sb.append("").append("#");
                sb.append("").append("#");

                //孔内情况
                sb.append("").append("#");

                //岩心采取 标贯编号顺便 长度0.45 采取率100%
                sb.append(sptRig.getRockCoreIndex()).append("#");
                sb.append("0.45").append("#");
                sb.append("100%").append("#");

                //土样
                generateEarthSampleIndexInfo(nextRig, sb);

                //水样
                generateWaterSampleInfo(nextRig, sb);

                //地层
                sb.append("").append("#");//编号, 四类普通钻,编号加1
                sb.append("").append("#"); //底层深度 本次累计进尺
                sb.append("").append("#");//层厚 本次累计进尺 -上次累计进尺
                sb.append(sptRig.getOtherDescription()).append("#"); // 名称及岩性
                sb.append("").append("#"); //岩层等级

                //地下水 只填第一行
                sb.append("").append("#");
                sb.append(initialWaterDepth).append("#");
                sb.append(finalWaterDepth).append("#");
                sb.append("").append("#");

                //特殊情况记录 最后一个string 特殊处理
                sb.append(sptRig.getOtherDescription().trim().equals("") ? NA : sptRig.getOtherDescription()).append("#");

            } else if (isDst) {
                DSTRig dstRig = (DSTRig) rigs.get(i);

                sb.append("动 探").append("#");
                //钻杆
                sb.append(dstRig.getProbeType()).append("#");
                sb.append(Utility.formatDouble(dstRig.getProbeLength())).append("#");
                sb.append("").append("#");

                //岩芯管
                sb.append("动").append("#");
                sb.append("").append("#");

                //钻头
                sb.append("").append("#");
                sb.append("").append("#");
                sb.append("").append("#");

                //进尺
                sb.append(Utility.formatDouble(dstRig.getDrillToolTotalLength())).append("#");
                sb.append(Utility.formatDouble(dstRig.getDrillPipeRemainLength())).append("#");
                sb.append(Utility.formatDouble(dstRig.getRoundTripMeterageLength())).append("#");
                sb.append(Utility.formatDouble(dstRig.getAccumulatedMeterageLength())).append("#");
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
                generateEarthSampleIndexInfo(nextRig, sb);

                //水样
                generateWaterSampleInfo(nextRig, sb);

                //地层
                sb.append("").append("#");//编号, 四类普通钻,编号加1
                sb.append("").append("#"); //底层深度 本次累计进尺
                sb.append("").append("#");//层厚 本次累计进尺 -上次累计进尺
                sb.append(dstRig.getRockDescription()).append("#");
                sb.append("").append("#"); //岩层等级

                //地下水 只填第一行
                sb.append("").append("#");
                sb.append(initialWaterDepth).append("#");
                sb.append(finalWaterDepth).append("#");
                sb.append("").append("#");

                //特殊情况记录 最后一个string 特殊处理
                sb.append(NA).append("#");

            } else if (isTrr) {
                TRRig trRig = (TRRig) rigs.get(i);

                sb.append("下套管").append("#");

                //钻杆
                sb.append(NA).append("#");
                sb.append(NA).append("#");
                sb.append("").append("#");

                //岩芯管
                sb.append("").append("#");
                sb.append("").append("#");

                //钻头
                sb.append("").append("#");
                sb.append("").append("#");
                sb.append("").append("#");

                //进尺
                sb.append(NA).append("#");
                sb.append(NA).append("#");
                sb.append(NA).append("#");
                sb.append(NA).append("#");

                //护壁措施
                List<TRRig.TRInfo> infos = trRig.getTrInfos();
                if (null == infos || infos.size() == 0) {
                    sb.append("").append("#");
                    sb.append("").append("#");
                    sb.append("").append("#");
                    sb.append("").append("#");
                    sb.append("").append("#");
                } else {
                    String wallType = "";
                    String index = "";
                    String diameter = "";
                    String length = "";
                    String totalLen = "";
                    for (int j = 0; j < infos.size(); j++) {
                        TRRig.TRInfo info = infos.get(j);
                        if (j == 0) {
                            wallType += info.getWallType();
                        } else {
                            wallType += " " + BR;
                        }

                        index += info.getIndex() + BR;
                        diameter += info.getDiameter() + BR;
                        length += info.getLength() + BR;

                        if (j == (infos.size() - 1)) {
                            totalLen += info.getTotalLength();
                        } else {
                            totalLen += " " + BR;
                        }
                    }
                    sb.append(wallType).append("#");
                    sb.append(index).append("#");
                    sb.append(diameter).append("#");
                    sb.append(length).append("#");
                    sb.append(totalLen).append("#");
                }

                //孔内情况
                sb.append("").append("#");

                //岩心采取
                sb.append("").append("#");
                sb.append("").append("#");
                sb.append("").append("#");

                //土样
                generateEarthSampleIndexInfo(nextRig, sb);

                //水样
                generateWaterSampleInfo(nextRig, sb);

                //地层
                sb.append("").append("#");//编号, 四类普通钻,编号加1
                sb.append("").append("#"); //底层深度 本次累计进尺
                sb.append("").append("#");//层厚 本次累计进尺 -上次累计进尺
                sb.append("").append("#"); // 名称及岩性
                sb.append("").append("#"); //岩层等级

                //地下水 只填第一行
                sb.append("").append("#");
                sb.append(initialWaterDepth).append("#");
                sb.append(finalWaterDepth).append("#");
                sb.append("").append("#");

                //特殊情况记录 最后一个string 特殊处理
                sb.append(trRig.getSpecialDescription().trim().equals("") ? NA : trRig.getSpecialDescription()).append("#");
            } else if (isOriSmpl) {
                OriginalSamplingRig originalSamplingRig = (OriginalSamplingRig) rigs.get(i);

                sb.append("取原状样").append("#");
                //钻杆
                sb.append("").append("#");
                sb.append("").append("#");
                sb.append("").append("#");

                //岩芯管
                sb.append("").append("#");
                sb.append("").append("#");

                //钻头
                sb.append("").append("#");
                sb.append("").append("#");
                sb.append("").append("#");

                //进尺
                sb.append(Utility.formatDouble(originalSamplingRig.getDrillToolTotalLength())).append("#");
                sb.append(Utility.formatDouble(originalSamplingRig.getDrillPipeRemainLength())).append("#");
                sb.append(Utility.formatDouble(originalSamplingRig.getRoundTripMeterageLength())).append("#");
                sb.append(Utility.formatDouble(originalSamplingRig.getAccumulatedMeterageLength())).append("#");

                //护壁措施
                sb.append("").append("#");
                sb.append("").append("#");
                sb.append("").append("#");
                sb.append("").append("#");
                sb.append("").append("#");

                //孔内情况
                sb.append("").append("#");

                //岩心采取
                sb.append(String.valueOf(originalSamplingRig.getRockCoreIndex())).append("#");
                sb.append(Utility.formatDouble(originalSamplingRig.getLastRockCorePipeLength())).append("#");
                sb.append(Utility.formatDouble(originalSamplingRig.getRockCorePickPercentage() * 100) + "%").append("#");

                //土样
                sb.append(originalSamplingRig.getIndex()).append("#");
                sb.append(Utility.formatDouble(originalSamplingRig.getSamplerPipeDiameter())).append("#");
                sb.append((originalSamplingRig.getEndDepth() + BR + originalSamplingRig.getStartDepth())).append("#");
                sb.append(originalSamplingRig.getCount()).append("#");

                //水样
                sb.append("").append("#");
                sb.append("").append("#");
                sb.append("").append("#");

                //地层
                sb.append("").append("#");//编号, 四类普通钻,编号加1
                sb.append("").append("#"); //底层深度 本次累计进尺
                sb.append("").append("#");//层厚 本次累计进尺 -上次累计进尺
                sb.append(originalSamplingRig.getRockDescription()).append("#"); // 名称及岩性
                sb.append("").append("#"); //岩层等级

                //地下水 只填第一行
                sb.append("").append("#");
                sb.append(initialWaterDepth).append("#");
                sb.append(finalWaterDepth).append("#");
                sb.append("").append("#");

                //特殊情况记录 最后一个string 特殊处理
                sb.append(NA).append("#");
            }

            resultList.add(sb.toString());

        }

        String[] resultArray = resultList.toArray(new String[resultList.size()]);
        String[][] resultData = new String[resultArray.length][];
        for (int i = 0, len = resultData.length; i < len; i++) {
            resultData[i] = convert2Array(resultArray[i]);
        }

        return resultData;
    }
}

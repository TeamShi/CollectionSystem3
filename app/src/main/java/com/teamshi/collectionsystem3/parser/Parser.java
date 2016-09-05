package com.teamshi.collectionsystem3.parser;

import com.teamshi.collectionsystem3.Utility;
import com.teamshi.collectionsystem3.datastructure.DSTRig;
import com.teamshi.collectionsystem3.datastructure.Hole;
import com.teamshi.collectionsystem3.datastructure.NARig;
import com.teamshi.collectionsystem3.datastructure.RegularRig;
import com.teamshi.collectionsystem3.datastructure.Rig;
import com.teamshi.collectionsystem3.datastructure.SPTRig;
import com.teamshi.collectionsystem3.datastructure.TRRig;

import java.util.ArrayList;

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

    protected static String[][] convertSpt(SPTRig sptRig) {
        String[][] resultData = new String[1][];
        StringBuffer sb = new StringBuffer();

        sb.append("1").append("#");

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

        sb.append(Utility.formatDouble(sptRig.getCountStartDepth1()) + "," + Utility.formatDouble(sptRig.getCountStartDepth2()) + "," + Utility.formatDouble(sptRig.getCountStartDepth3())).append("#");
        sb.append(Utility.formatDouble(sptRig.getCountEndDepth1()) + "," + Utility.formatDouble(sptRig.getCountEndDepth2()) + "," + Utility.formatDouble(sptRig.getCountEndDepth3())).append("#");

        //todo need confirmation , currentlly data separated by comma
        sb.append(Utility.formatDouble(sptRig.getDrillStartDepth1()) + "," + Utility.formatDouble(sptRig.getDrillStartDepth2()) + "," + Utility.formatDouble(sptRig.getDrillStartDepth3())).append("#");
        sb.append(Utility.formatDouble(sptRig.getDrillEndDepth1()) + "," + Utility.formatDouble(sptRig.getDrillEndDepth2()) + "," + Utility.formatDouble(sptRig.getDrillEndDepth3())).append("#");

        //分类
        sb.append(NA).append("#");

        //野外描述
        sb.append(sptRig.getRockColor()).append("#");
        sb.append(sptRig.getRockDensity()).append("#");
        sb.append(NA).append("#");
        sb.append(sptRig.getRockName()).append("#");
        sb.append(sptRig.getRockSaturation()).append("#");
        //光泽
        sb.append(NA).append("#");
        sb.append(NA).append("#");
        sb.append(NA).append("#");

        sb.append(sptRig.getOtherDescription()).append("#");

        sb.append(sptRig.getHitCount1() + "," + sptRig.getHitCount2() + "," + sptRig.getHitCount3()).append("#");

        sb.append(NA).append("#");
        sb.append(NA).append("#");
        sb.append(NA).append("#");

        resultData[0] = convert2Array(sb.toString());

        return resultData;
    }

    protected static String[][] convertDst(DSTRig dstRig) {
        ArrayList<DSTRig.DSTDetailInfo> details = dstRig.getDstDetailInfos();

        StringBuffer sb ;
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

    protected static String[][] convertHole(Hole hole) {
        ArrayList<Rig> rigs = hole.getRigList();
        int rows = rigs.size();
        String[][] resultData = new String[rows][];
        for (int i = 0; i < rows; i++) {
            Rig rig = rigs.get(i);
            boolean isNAType = rig instanceof NARig;
            boolean isRegular = rig instanceof RegularRig;
            boolean isSpt = rig instanceof SPTRig;
            boolean isDst = rig instanceof DSTRig;
            boolean isTrr = rig instanceof TRRig;

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
                sb.append("").append("#");
                sb.append("").append("#");
                sb.append("").append("#");
                sb.append("").append("#");

                //水样
                sb.append("").append("#");
                sb.append("").append("#");
                sb.append("").append("#");

                //地层
                sb.append("").append("#");//编号, 四类普通钻,编号加1
                sb.append("").append("#"); //底层深度 本次累计进尺
                sb.append("").append("#");//层厚 本次累计进尺 -上次累计进尺
                sb.append("").append("#"); // 名称及岩性
                sb.append("").append("#"); //岩层等级

                //地下水 只填第一行
                sb.append("").append("#");
                sb.append("").append("#");
                sb.append("").append("#");
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
                sb.append("").append("#");
                sb.append("").append("#");
                sb.append("").append("#");
                sb.append("").append("#");

                //水样
                sb.append("").append("#");
                sb.append("").append("#");
                sb.append("").append("#");

                //地层
                sb.append("").append("#");//编号, 四类普通钻,编号加1
                sb.append("").append("#"); //底层深度 本次累计进尺
                sb.append("").append("#");//层厚 本次累计进尺 -上次累计进尺
                sb.append("").append("#"); // 名称及岩性
                sb.append("").append("#"); //岩层等级

                //地下水 只填第一行
                sb.append("").append("#");
                sb.append("").append("#");
                sb.append("").append("#");
                sb.append("").append("#");

                //特殊情况记录 最后一个string 特殊处理
                sb.append(naRig.getNaType().trim().equals("") ? NA : naRig.getNaType()).append("#");

            } else if(isSpt){
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

                //岩心采取
                sb.append("").append("#");
                sb.append("").append("#");
                sb.append("").append("#");

                //土样
                sb.append("").append("#");
                sb.append("").append("#");
                sb.append("").append("#");
                sb.append("").append("#");

                //水样
                sb.append("").append("#");
                sb.append("").append("#");
                sb.append("").append("#");

                //地层
                sb.append("").append("#");//编号, 四类普通钻,编号加1
                sb.append("").append("#"); //底层深度 本次累计进尺
                sb.append("").append("#");//层厚 本次累计进尺 -上次累计进尺
                sb.append("").append("#"); // 名称及岩性
                sb.append("").append("#"); //岩层等级

                //地下水 只填第一行
                sb.append("").append("#");
                sb.append("").append("#");
                sb.append("").append("#");
                sb.append("").append("#");

                //特殊情况记录 最后一个string 特殊处理
                sb.append(sptRig.getOtherDescription().trim().equals("") ? NA : sptRig.getOtherDescription()).append("#");

            }else if(isDst){
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
                sb.append("").append("#");
                sb.append("").append("#");
                sb.append("").append("#");
                sb.append("").append("#");

                //水样
                sb.append("").append("#");
                sb.append("").append("#");
                sb.append("").append("#");

                //地层
                sb.append("").append("#");//编号, 四类普通钻,编号加1
                sb.append("").append("#"); //底层深度 本次累计进尺
                sb.append("").append("#");//层厚 本次累计进尺 -上次累计进尺
                sb.append("").append("#"); // 名称及岩性
                sb.append("").append("#"); //岩层等级

                //地下水 只填第一行
                sb.append("").append("#");
                sb.append("").append("#");
                sb.append("").append("#");
                sb.append("").append("#");

                //特殊情况记录 最后一个string 特殊处理
                sb.append( NA).append("#");

            }else if(isTrr){
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
                sb.append("").append("#");
                sb.append("").append("#");
                sb.append("").append("#");
                sb.append("").append("#");

                //水样
                sb.append("").append("#");
                sb.append("").append("#");
                sb.append("").append("#");

                //地层
                sb.append("").append("#");//编号, 四类普通钻,编号加1
                sb.append("").append("#"); //底层深度 本次累计进尺
                sb.append("").append("#");//层厚 本次累计进尺 -上次累计进尺
                sb.append("").append("#"); // 名称及岩性
                sb.append("").append("#"); //岩层等级

                //地下水 只填第一行
                sb.append("").append("#");
                sb.append("").append("#");
                sb.append("").append("#");
                sb.append("").append("#");

                //特殊情况记录 最后一个string 特殊处理
                sb.append( trRig.getSpecialDescription().trim().equals("") ? NA : trRig.getSpecialDescription()).append("#");
            }

            resultData[i] = convert2Array(sb.toString());
        }

        return resultData;
    }
}

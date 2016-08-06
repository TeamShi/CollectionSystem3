package com.teamshi.collectionsystem3.datastructure;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by
 jishshi on 2015/6/16.
 */


public class Configuration  implements Serializable {
    private  double sptTable1Argument1 = 2;
    private  double sptTable1Argument2 = 4;
    private  double sptTable1Argument3 = 7;
    private  double sptTable1Argument4 = 18;
    private  double sptTable1Argument5 = 35;

    private  double sptTable2Argument1 = 10;
    private  double sptTable2Argument2 = 15;
    private  double sptTable2Argument3 = 30;

    private  double sptTable3Argument1 = 10;
    private  double sptTable3Argument2 = 15;
    private  double sptTable3Argument3 = 30;

    private  double dstTable1_63_5_Argument1 = 0;
    private  double dstTable1_63_5_Argument2 = 5;
    private  double dstTable1_63_5_Argument3 = 10;
    private  double dstTable1_63_5_Argument4 = 20;

    private  double dstTable1_120_Argument1 = 0;
    private  double dstTable1_120_Argument2 = 3;
    private  double dstTable1_120_Argument3 = 6;
    private  double dstTable1_120_Argument4 = 11;
    private  double dstTable1_120_Argument5 = 14;

    private  double dstTable2_63_6_Argument1 = 5;
    private  double dstTable2_63_6_Argument2 = 8;
    private  double dstTable2_63_6_Argument3 = 10;

    private  double dstTable2_63_7_Argument1 = 5;
    private  double dstTable2_63_7_Argument2 = 6.5;
    private  double dstTable2_63_7_Argument3 = 9.5;

    private  double dstTable2_63_8_Argument1 = 5;
    private  double dstTable2_63_8_Argument2 = 6;
    private  double dstTable2_63_8_Argument3 = 9;
    private Map<String, String> templateDictionary = new HashMap<>();

    public  double getSptTable1Argument1() {
        return sptTable1Argument1;
    }

    public  void setSptTable1Argument1(double sptTable1Argument1) {
        this.sptTable1Argument1 = sptTable1Argument1;
    }

    public  double getSptTable1Argument2() {
        return sptTable1Argument2;
    }

    public  void setSptTable1Argument2(double sptTable1Argument2) {
        this.sptTable1Argument2 = sptTable1Argument2;
    }

    public  double getSptTable1Argument3() {
        return sptTable1Argument3;
    }

    public  void setSptTable1Argument3(double sptTable1Argument3) {
        this.sptTable1Argument3 = sptTable1Argument3;
    }

    public  double getSptTable1Argument4() {
        return sptTable1Argument4;
    }

    public  void setSptTable1Argument4(double sptTable1Argument4) {
        this.sptTable1Argument4 = sptTable1Argument4;
    }

    public  double getSptTable1Argument5() {
        return sptTable1Argument5;
    }

    public  void setSptTable1Argument5(double sptTable1Argument5) {
        this.sptTable1Argument5 = sptTable1Argument5;
    }

    public  double getSptTable2Argument1() {
        return sptTable2Argument1;
    }

    public  void setSptTable2Argument1(double sptTable2Argument1) {
        this.sptTable2Argument1 = sptTable2Argument1;
    }

    public  double getSptTable2Argument2() {
        return sptTable2Argument2;
    }

    public  void setSptTable2Argument2(double sptTable2Argument2) {
        this.sptTable2Argument2 = sptTable2Argument2;
    }

    public  double getSptTable2Argument3() {
        return sptTable2Argument3;
    }

    public  void setSptTable2Argument3(double sptTable2Argument3) {
        this.sptTable2Argument3 = sptTable2Argument3;
    }

    public  double getSptTable3Argument1() {
        return sptTable3Argument1;
    }

    public  void setSptTable3Argument1(double sptTable3Argument1) {
        this.sptTable3Argument1 = sptTable3Argument1;
    }

    public  double getSptTable3Argument2() {
        return sptTable3Argument2;
    }

    public  void setSptTable3Argument2(double sptTable3Argument2) {
        this.sptTable3Argument2 = sptTable3Argument2;
    }

    public  double getSptTable3Argument3() {
        return sptTable3Argument3;
    }

    public  void setSptTable3Argument3(double sptTable3Argument3) {
        this.sptTable3Argument3 = sptTable3Argument3;
    }

    public  double getDstTable1_63_5_Argument1() {
        return dstTable1_63_5_Argument1;
    }

    public  void setDstTable1_63_5_Argument1(double dstTable1_63_5_Argument1) {
        this.dstTable1_63_5_Argument1 = dstTable1_63_5_Argument1;
    }

    public  double getDstTable1_63_5_Argument2() {
        return dstTable1_63_5_Argument2;
    }

    public  void setDstTable1_63_5_Argument2(double dstTable1_63_5_Argument2) {
        this.dstTable1_63_5_Argument2 = dstTable1_63_5_Argument2;
    }

    public  double getDstTable1_63_5_Argument3() {
        return dstTable1_63_5_Argument3;
    }

    public  void setDstTable1_63_5_Argument3(double dstTable1_63_5_Argument3) {
        this.dstTable1_63_5_Argument3 = dstTable1_63_5_Argument3;
    }

    public  double getDstTable1_63_5_Argument4() {
        return dstTable1_63_5_Argument4;
    }

    public  void setDstTable1_63_5_Argument4(double dstTable1_63_5_Argument4) {
        this.dstTable1_63_5_Argument4 = dstTable1_63_5_Argument4;
    }

    public  double getDstTable1_120_Argument1() {
        return dstTable1_120_Argument1;
    }

    public  void setDstTable1_120_Argument1(double dstTable1_120_Argument1) {
        this.dstTable1_120_Argument1 = dstTable1_120_Argument1;
    }

    public  double getDstTable1_120_Argument2() {
        return dstTable1_120_Argument2;
    }

    public  void setDstTable1_120_Argument2(double dstTable1_120_Argument2) {
        this.dstTable1_120_Argument2 = dstTable1_120_Argument2;
    }

    public  double getDstTable1_120_Argument3() {
        return dstTable1_120_Argument3;
    }

    public  void setDstTable1_120_Argument3(double dstTable1_120_Argument3) {
        this.dstTable1_120_Argument3 = dstTable1_120_Argument3;
    }

    public  double getDstTable1_120_Argument4() {
        return dstTable1_120_Argument4;
    }

    public  void setDstTable1_120_Argument4(double dstTable1_120_Argument4) {
        this.dstTable1_120_Argument4 = dstTable1_120_Argument4;
    }

    public  double getDstTable1_120_Argument5() {
        return dstTable1_120_Argument5;
    }

    public  void setDstTable1_120_Argument5(double dstTable1_120_Argument5) {
        this.dstTable1_120_Argument5 = dstTable1_120_Argument5;
    }

    public  double getDstTable2_63_6_Argument1() {
        return dstTable2_63_6_Argument1;
    }

    public  void setDstTable2_63_6_Argument1(double dstTable2_63_6_Argument1) {
        this.dstTable2_63_6_Argument1 = dstTable2_63_6_Argument1;
    }

    public  double getDstTable2_63_6_Argument2() {
        return dstTable2_63_6_Argument2;
    }

    public  void setDstTable2_63_6_Argument2(double dstTable2_63_6_Argument2) {
        this.dstTable2_63_6_Argument2 = dstTable2_63_6_Argument2;
    }

    public  double getDstTable2_63_6_Argument3() {
        return dstTable2_63_6_Argument3;
    }

    public  void setDstTable2_63_6_Argument3(double dstTable2_63_6_Argument3) {
        this.dstTable2_63_6_Argument3 = dstTable2_63_6_Argument3;
    }

    public  double getDstTable2_63_7_Argument1() {
        return dstTable2_63_7_Argument1;
    }

    public  void setDstTable2_63_7_Argument1(double dstTable2_63_7_Argument1) {
        this.dstTable2_63_7_Argument1 = dstTable2_63_7_Argument1;
    }

    public  double getDstTable2_63_7_Argument2() {
        return dstTable2_63_7_Argument2;
    }

    public  void setDstTable2_63_7_Argument2(double dstTable2_63_7_Argument2) {
        this.dstTable2_63_7_Argument2 = dstTable2_63_7_Argument2;
    }

    public  double getDstTable2_63_7_Argument3() {
        return dstTable2_63_7_Argument3;
    }

    public  void setDstTable2_63_7_Argument3(double dstTable2_63_7_Argument3) {
        this.dstTable2_63_7_Argument3 = dstTable2_63_7_Argument3;
    }

    public  double getDstTable2_63_8_Argument1() {
        return dstTable2_63_8_Argument1;
    }

    public  void setDstTable2_63_8_Argument1(double dstTable2_63_8_Argument1) {
        this.dstTable2_63_8_Argument1 = dstTable2_63_8_Argument1;
    }

    public  double getDstTable2_63_8_Argument2() {
        return dstTable2_63_8_Argument2;
    }

    public  void setDstTable2_63_8_Argument2(double dstTable2_63_8_Argument2) {
        this.dstTable2_63_8_Argument2 = dstTable2_63_8_Argument2;
    }

    public  double getDstTable2_63_8_Argument3() {
        return dstTable2_63_8_Argument3;
    }

    public  void setDstTable2_63_8_Argument3(double dstTable2_63_8_Argument3) {
        this.dstTable2_63_8_Argument3 = dstTable2_63_8_Argument3;
    }

    public void setTemplateDictionary(Map<String,String> templateDictionary) {
        this.templateDictionary = templateDictionary;
    }

    public Map<String, String> getTemplateDictionary() {
        return templateDictionary;
    }
}

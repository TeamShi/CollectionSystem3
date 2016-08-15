package com.teamshi.collectionsystem3;

import com.teamshi.collectionsystem3.datastructure.Configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by
 * liashi on 2015/6/8.
 */
public class ConfigurationManager {
    private static double sptTable1Argument1 = 2;
    private static double sptTable1Argument2 = 4;
    private static double sptTable1Argument3 = 7;
    private static double sptTable1Argument4 = 18;
    private static double sptTable1Argument5 = 35;

    private static double sptTable2Argument1 = 10;
    private static double sptTable2Argument2 = 15;
    private static double sptTable2Argument3 = 30;

    private static double sptTable3Argument1 = 10;
    private static double sptTable3Argument2 = 15;
    private static double sptTable3Argument3 = 30;

    private static double dstTable1_63_5_Argument1 = 0;
    private static double dstTable1_63_5_Argument2 = 5;
    private static double dstTable1_63_5_Argument3 = 10;
    private static double dstTable1_63_5_Argument4 = 20;

    private static double dstTable1_120_Argument1 = 0;
    private static double dstTable1_120_Argument2 = 3;
    private static double dstTable1_120_Argument3 = 6;
    private static double dstTable1_120_Argument4 = 11;
    private static double dstTable1_120_Argument5 = 14;

    private static double dstTable2_63_6_Argument1 = 5;
    private static double dstTable2_63_6_Argument2 = 8;
    private static double dstTable2_63_6_Argument3 = 10;

    private static double dstTable2_63_7_Argument1 = 5;
    private static double dstTable2_63_7_Argument2 = 6.5;
    private static double dstTable2_63_7_Argument3 = 9.5;

    private static double dstTable2_63_8_Argument1 = 5;
    private static double dstTable2_63_8_Argument2 = 6;
    private static double dstTable2_63_8_Argument3 = 9;
    private static Map<String, String> templateDictionary = new HashMap<>();


    public static double getSptTable1Argument1() {
        return sptTable1Argument1;
    }

    public static void setSptTable1Argument1(double sptTable1Argument1) {
        ConfigurationManager.sptTable1Argument1 = sptTable1Argument1;
    }

    public static double getSptTable1Argument2() {
        return sptTable1Argument2;
    }

    public static void setSptTable1Argument2(double sptTable1Argument2) {
        ConfigurationManager.sptTable1Argument2 = sptTable1Argument2;
    }

    public static double getSptTable1Argument3() {
        return sptTable1Argument3;
    }

    public static void setSptTable1Argument3(double sptTable1Argument3) {
        ConfigurationManager.sptTable1Argument3 = sptTable1Argument3;
    }

    public static double getSptTable1Argument4() {
        return sptTable1Argument4;
    }

    public static void setSptTable1Argument4(double sptTable1Argument4) {
        ConfigurationManager.sptTable1Argument4 = sptTable1Argument4;
    }

    public static double getSptTable1Argument5() {
        return sptTable1Argument5;
    }

    public static void setSptTable1Argument5(double sptTable1Argument5) {
        ConfigurationManager.sptTable1Argument5 = sptTable1Argument5;
    }

    public static double getSptTable2Argument1() {
        return sptTable2Argument1;
    }

    public static void setSptTable2Argument1(double sptTable2Argument1) {
        ConfigurationManager.sptTable2Argument1 = sptTable2Argument1;
    }

    public static double getSptTable2Argument2() {
        return sptTable2Argument2;
    }

    public static void setSptTable2Argument2(double sptTable2Argument2) {
        ConfigurationManager.sptTable2Argument2 = sptTable2Argument2;
    }

    public static double getSptTable2Argument3() {
        return sptTable2Argument3;
    }

    public static void setSptTable2Argument3(double sptTable2Argument3) {
        ConfigurationManager.sptTable2Argument3 = sptTable2Argument3;
    }

    public static double getSptTable3Argument1() {
        return sptTable3Argument1;
    }

    public static void setSptTable3Argument1(double sptTable3Argument1) {
        ConfigurationManager.sptTable3Argument1 = sptTable3Argument1;
    }

    public static double getSptTable3Argument2() {
        return sptTable3Argument2;
    }

    public static void setSptTable3Argument2(double sptTable3Argument2) {
        ConfigurationManager.sptTable3Argument2 = sptTable3Argument2;
    }

    public static double getSptTable3Argument3() {
        return sptTable3Argument3;
    }

    public static void setSptTable3Argument3(double sptTable3Argument3) {
        ConfigurationManager.sptTable3Argument3 = sptTable3Argument3;
    }

    public static double getDstTable1_63_5_Argument1() {
        return dstTable1_63_5_Argument1;
    }

    public static void setDstTable1_63_5_Argument1(double dstTable1_63_5_Argument1) {
        ConfigurationManager.dstTable1_63_5_Argument1 = dstTable1_63_5_Argument1;
    }

    public static double getDstTable1_63_5_Argument2() {
        return dstTable1_63_5_Argument2;
    }

    public static void setDstTable1_63_5_Argument2(double dstTable1_63_5_Argument2) {
        ConfigurationManager.dstTable1_63_5_Argument2 = dstTable1_63_5_Argument2;
    }

    public static double getDstTable1_63_5_Argument3() {
        return dstTable1_63_5_Argument3;
    }

    public static void setDstTable1_63_5_Argument3(double dstTable1_63_5_Argument3) {
        ConfigurationManager.dstTable1_63_5_Argument3 = dstTable1_63_5_Argument3;
    }

    public static double getDstTable1_63_5_Argument4() {
        return dstTable1_63_5_Argument4;
    }

    public static void setDstTable1_63_5_Argument4(double dstTable1_63_5_Argument4) {
        ConfigurationManager.dstTable1_63_5_Argument4 = dstTable1_63_5_Argument4;
    }

    public static double getDstTable1_120_Argument1() {
        return dstTable1_120_Argument1;
    }

    public static void setDstTable1_120_Argument1(double dstTable1_120_Argument1) {
        ConfigurationManager.dstTable1_120_Argument1 = dstTable1_120_Argument1;
    }

    public static double getDstTable1_120_Argument2() {
        return dstTable1_120_Argument2;
    }

    public static void setDstTable1_120_Argument2(double dstTable1_120_Argument2) {
        ConfigurationManager.dstTable1_120_Argument2 = dstTable1_120_Argument2;
    }

    public static double getDstTable1_120_Argument3() {
        return dstTable1_120_Argument3;
    }

    public static void setDstTable1_120_Argument3(double dstTable1_120_Argument3) {
        ConfigurationManager.dstTable1_120_Argument3 = dstTable1_120_Argument3;
    }

    public static double getDstTable1_120_Argument4() {
        return dstTable1_120_Argument4;
    }

    public static void setDstTable1_120_Argument4(double dstTable1_120_Argument4) {
        ConfigurationManager.dstTable1_120_Argument4 = dstTable1_120_Argument4;
    }

    public static double getDstTable1_120_Argument5() {
        return dstTable1_120_Argument5;
    }

    public static void setDstTable1_120_Argument5(double dstTable1_120_Argument5) {
        ConfigurationManager.dstTable1_120_Argument5 = dstTable1_120_Argument5;
    }

    public static double getDstTable2_63_6_Argument1() {
        return dstTable2_63_6_Argument1;
    }

    public static void setDstTable2_63_6_Argument1(double dstTable2_63_6_Argument1) {
        ConfigurationManager.dstTable2_63_6_Argument1 = dstTable2_63_6_Argument1;
    }

    public static double getDstTable2_63_6_Argument2() {
        return dstTable2_63_6_Argument2;
    }

    public static void setDstTable2_63_6_Argument2(double dstTable2_63_6_Argument2) {
        ConfigurationManager.dstTable2_63_6_Argument2 = dstTable2_63_6_Argument2;
    }

    public static double getDstTable2_63_6_Argument3() {
        return dstTable2_63_6_Argument3;
    }

    public static void setDstTable2_63_6_Argument3(double dstTable2_63_6_Argument3) {
        ConfigurationManager.dstTable2_63_6_Argument3 = dstTable2_63_6_Argument3;
    }

    public static double getDstTable2_63_7_Argument1() {
        return dstTable2_63_7_Argument1;
    }

    public static void setDstTable2_63_7_Argument1(double dstTable2_63_7_Argument1) {
        ConfigurationManager.dstTable2_63_7_Argument1 = dstTable2_63_7_Argument1;
    }

    public static double getDstTable2_63_7_Argument2() {
        return dstTable2_63_7_Argument2;
    }

    public static void setDstTable2_63_7_Argument2(double dstTable2_63_7_Argument2) {
        ConfigurationManager.dstTable2_63_7_Argument2 = dstTable2_63_7_Argument2;
    }

    public static double getDstTable2_63_7_Argument3() {
        return dstTable2_63_7_Argument3;
    }

    public static void setDstTable2_63_7_Argument3(double dstTable2_63_7_Argument3) {
        ConfigurationManager.dstTable2_63_7_Argument3 = dstTable2_63_7_Argument3;
    }

    public static double getDstTable2_63_8_Argument1() {
        return dstTable2_63_8_Argument1;
    }

    public static void setDstTable2_63_8_Argument1(double dstTable2_63_8_Argument1) {
        ConfigurationManager.dstTable2_63_8_Argument1 = dstTable2_63_8_Argument1;
    }

    public static double getDstTable2_63_8_Argument2() {
        return dstTable2_63_8_Argument2;
    }

    public static void setDstTable2_63_8_Argument2(double dstTable2_63_8_Argument2) {
        ConfigurationManager.dstTable2_63_8_Argument2 = dstTable2_63_8_Argument2;
    }

    public static double getDstTable2_63_8_Argument3() {
        return dstTable2_63_8_Argument3;
    }

    public static void setDstTable2_63_8_Argument3(double dstTable2_63_8_Argument3) {
        ConfigurationManager.dstTable2_63_8_Argument3 = dstTable2_63_8_Argument3;
    }

    public static Map<String, String> getTemplateDictionary() {
        return templateDictionary;
    }

    public static void setTemplateDictionary(Map<String, String> templateDictionary) {
        ConfigurationManager.templateDictionary = templateDictionary;
    }

    public static String getSPTDestiny(int selectionIndex, int hit) {
        switch (selectionIndex) {
            case 0:
                if (hit < sptTable1Argument1) {
                    return "流动";
                } else if (hit < sptTable1Argument2) {
                    return "软塑";
                } else if (hit < sptTable1Argument3) {
                    return "软可塑";
                } else if (hit < sptTable1Argument4) {
                    return "硬可塑";
                } else if (hit < sptTable1Argument5) {
                    return "硬塑";
                } else if (hit >= sptTable1Argument5) {
                    return "坚硬";
                }
                break;
            case 1:
                if (hit <= sptTable2Argument1) {
                    return "松散";
                } else if (hit <= sptTable2Argument2) {
                    return "稍密";
                } else if (hit <= sptTable2Argument3) {
                    return "中密";
                } else if (hit > sptTable2Argument3) {
                    return "密实";
                }
                break;
            case 2:
                if (hit <= sptTable3Argument1) {
                    return "松散";
                } else if (hit <= sptTable3Argument2) {
                    return "稍密";
                } else if (hit <= sptTable3Argument3) {
                    return "中密";
                } else if (hit > sptTable3Argument3) {
                    return "密实";
                }
                break;
        }
        return "";
    }

    public static String getDSTDestiny(int selectionIndex, int hit) {
        switch (selectionIndex) {
            case 0:
                if (hit <= dstTable1_63_5_Argument2) {
                    return "松散";
                } else if (hit <= dstTable1_63_5_Argument3) {
                    return "稍密";
                } else if (hit <= dstTable1_63_5_Argument4) {
                    return "中密";
                } else if (hit > dstTable1_63_5_Argument4) {
                    return "密实";
                }
                break;
            case 1:
                if (hit <= dstTable1_120_Argument2) {
                    return "松散";
                } else if (hit <= dstTable1_120_Argument3) {
                    return "稍密";
                } else if (hit <= dstTable1_120_Argument4) {
                    return "中密";
                } else if (hit <= dstTable1_120_Argument5) {
                    return "密实";
                } else if (hit > dstTable1_120_Argument5) {
                    return "很密";
                }
                break;
            case 2:
                if (hit <= dstTable2_63_6_Argument1) {
                    return "松散";
                } else if (hit <= dstTable2_63_6_Argument2) {
                    return "稍密";
                } else if (hit <= dstTable2_63_6_Argument3) {
                    return "中密";
                } else if (hit > dstTable2_63_6_Argument3) {
                    return "密实";
                }
                break;
            case 3:
                if (hit <= dstTable2_63_7_Argument1) {
                    return "松散";
                } else if (hit <= dstTable2_63_7_Argument2) {
                    return "稍密";
                } else if (hit <= dstTable2_63_7_Argument3) {
                    return "中密";
                } else if (hit > dstTable2_63_7_Argument3) {
                    return "密实";
                }
                break;
            case 4:
                if (hit <= dstTable2_63_8_Argument1) {
                    return "松散";
                } else if (hit <= dstTable2_63_8_Argument2) {
                    return "稍密";
                } else if (hit <= dstTable2_63_8_Argument3) {
                    return "中密";
                } else if (hit > dstTable2_63_8_Argument3) {
                    return "密实";
                }
                break;
        }
        return "";
    }


    public static boolean exportConfig(Configuration configuration,String path) {
        try {
            File file = new File(path);
            ObjectOutputStream oo = new ObjectOutputStream(new FileOutputStream(file));
            oo.writeObject(configuration);
            oo.flush();
            oo.close();
            System.out.println("对象序列化成功！");
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean loadConfig(File file) {
        try {

            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
            Configuration configuration = (Configuration) ois.readObject();
            System.out.println("对象反序列化成功！");

            ConfigurationManager.setSptTable1Argument1(configuration.getSptTable1Argument1());
            ConfigurationManager.setSptTable1Argument2((configuration.getSptTable1Argument2()));
            ConfigurationManager.setSptTable1Argument3((configuration.getSptTable1Argument3()));
            ConfigurationManager.setSptTable1Argument4((configuration.getSptTable1Argument4()));
            ConfigurationManager.setSptTable1Argument5((configuration.getSptTable1Argument5()));

            ConfigurationManager.setSptTable2Argument1((configuration.getSptTable2Argument1()));
            ConfigurationManager.setSptTable2Argument2((configuration.getSptTable2Argument2()));
            ConfigurationManager.setSptTable2Argument3((configuration.getSptTable2Argument3()));

            ConfigurationManager.setSptTable3Argument1((configuration.getSptTable3Argument1()));
            ConfigurationManager.setSptTable3Argument2((configuration.getSptTable3Argument2()));
            ConfigurationManager.setSptTable3Argument3((configuration.getSptTable3Argument3()));

            ConfigurationManager.setDstTable1_63_5_Argument1((configuration.getDstTable1_63_5_Argument1()));
            ConfigurationManager.setDstTable1_63_5_Argument2((configuration.getDstTable1_63_5_Argument2()));
            ConfigurationManager.setDstTable1_63_5_Argument3((configuration.getDstTable1_63_5_Argument3()));
            ConfigurationManager.setDstTable1_63_5_Argument4((configuration.getDstTable1_63_5_Argument4()));

            ConfigurationManager.setDstTable1_120_Argument1((configuration.getDstTable1_120_Argument1()));
            ConfigurationManager.setDstTable1_120_Argument2((configuration.getDstTable1_120_Argument2()));
            ConfigurationManager.setDstTable1_120_Argument3((configuration.getDstTable1_120_Argument3()));
            ConfigurationManager.setDstTable1_120_Argument4((configuration.getDstTable1_120_Argument4()));
            ConfigurationManager.setDstTable1_120_Argument5((configuration.getDstTable1_120_Argument5()));

            ConfigurationManager.setDstTable2_63_6_Argument1((configuration.getDstTable2_63_6_Argument1()));
            ConfigurationManager.setDstTable2_63_6_Argument2((configuration.getDstTable2_63_6_Argument2()));
            ConfigurationManager.setDstTable2_63_6_Argument3((configuration.getDstTable2_63_6_Argument3()));

            ConfigurationManager.setDstTable2_63_7_Argument1((configuration.getDstTable2_63_7_Argument1()));
            ConfigurationManager.setDstTable2_63_7_Argument2((configuration.getDstTable2_63_7_Argument2()));
            ConfigurationManager.setDstTable2_63_7_Argument3((configuration.getDstTable2_63_7_Argument3()));

            ConfigurationManager.setDstTable2_63_8_Argument1((configuration.getDstTable2_63_8_Argument1()));
            ConfigurationManager.setDstTable2_63_8_Argument2((configuration.getDstTable2_63_8_Argument2()));
            ConfigurationManager.setDstTable2_63_8_Argument3((configuration.getDstTable2_63_8_Argument3()));
            ConfigurationManager.setTemplateDictionary(configuration.getTemplateDictionary());


        } catch (IOException e) {
            e.printStackTrace();
            return  false;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return  false;
        }

        return true;
    }


}

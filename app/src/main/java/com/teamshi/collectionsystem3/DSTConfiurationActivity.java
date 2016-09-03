package com.teamshi.collectionsystem3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.teamshi.collectionsystem3.datastructure.Configuration;

public class DSTConfiurationActivity extends AppCompatActivity {

    private TextView dstConfigurationTable1635Cell1TextView;
    private TextView dstConfigurationTable1635Cell2TextView;
    private TextView dstConfigurationTable1635Cell3TextView;
    private TextView dstConfigurationTable1635Cell4TextView;

    private TextView dstConfigurationTable1120Cell1TextView;
    private TextView dstConfigurationTable1120Cell2TextView;
    private TextView dstConfigurationTable1120Cell3TextView;
    private TextView dstConfigurationTable1120Cell4TextView;
    private TextView dstConfigurationTable1120Cell5TextView;

    private TextView dstConfigurationTable2636Cell1TextView;
    private TextView dstConfigurationTable2636Cell2TextView;
    private TextView dstConfigurationTable2636Cell3TextView;
    private TextView dstConfigurationTable2636Cell4TextView;

    private TextView dstConfigurationTable2637Cell1TextView;
    private TextView dstConfigurationTable2637Cell2TextView;
    private TextView dstConfigurationTable2637Cell3TextView;
    private TextView dstConfigurationTable2637Cell4TextView;

    private TextView dstConfigurationTable2638Cell1TextView;
    private TextView dstConfigurationTable2638Cell2TextView;
    private TextView dstConfigurationTable2638Cell3TextView;
    private TextView dstConfigurationTable2638Cell4TextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dstconfiuration);

        dstConfigurationTable1635Cell1TextView = (TextView) findViewById(R.id.textview_dst_rig_configuration_table_1_63_5_cell_1);
        dstConfigurationTable1635Cell2TextView = (TextView) findViewById(R.id.textview_dst_rig_configuration_table_1_63_5_cell_2);
        dstConfigurationTable1635Cell3TextView = (TextView) findViewById(R.id.textview_dst_rig_configuration_table_1_63_5_cell_3);
        dstConfigurationTable1635Cell4TextView = (TextView) findViewById(R.id.textview_dst_rig_configuration_table_1_63_5_cell_4);

        dstConfigurationTable1120Cell1TextView = (TextView) findViewById(R.id.textview_dst_rig_configuration_table_1_120_cell_1);
        dstConfigurationTable1120Cell2TextView = (TextView) findViewById(R.id.textview_dst_rig_configuration_table_1_120_cell_2);
        dstConfigurationTable1120Cell3TextView = (TextView) findViewById(R.id.textview_dst_rig_configuration_table_1_120_cell_3);
        dstConfigurationTable1120Cell4TextView = (TextView) findViewById(R.id.textview_dst_rig_configuration_table_1_120_cell_4);
        dstConfigurationTable1120Cell5TextView = (TextView) findViewById(R.id.textview_dst_rig_configuration_table_1_120_cell_5);

        dstConfigurationTable2636Cell1TextView = (TextView) findViewById(R.id.textview_dst_rig_configuration_table_2_63_6_cell_1);
        dstConfigurationTable2636Cell2TextView = (TextView) findViewById(R.id.textview_dst_rig_configuration_table_2_63_6_cell_2);
        dstConfigurationTable2636Cell3TextView = (TextView) findViewById(R.id.textview_dst_rig_configuration_table_2_63_6_cell_3);
        dstConfigurationTable2636Cell4TextView = (TextView) findViewById(R.id.textview_dst_rig_configuration_table_2_63_6_cell_4);

        dstConfigurationTable2637Cell1TextView = (TextView) findViewById(R.id.textview_dst_rig_configuration_table_2_63_7_cell_1);
        dstConfigurationTable2637Cell2TextView = (TextView) findViewById(R.id.textview_dst_rig_configuration_table_2_63_7_cell_2);
        dstConfigurationTable2637Cell3TextView = (TextView) findViewById(R.id.textview_dst_rig_configuration_table_2_63_7_cell_3);
        dstConfigurationTable2637Cell4TextView = (TextView) findViewById(R.id.textview_dst_rig_configuration_table_2_63_7_cell_4);

        dstConfigurationTable2638Cell1TextView = (TextView) findViewById(R.id.textview_dst_rig_configuration_table_2_63_8_cell_1);
        dstConfigurationTable2638Cell2TextView = (TextView) findViewById(R.id.textview_dst_rig_configuration_table_2_63_8_cell_2);
        dstConfigurationTable2638Cell3TextView = (TextView) findViewById(R.id.textview_dst_rig_configuration_table_2_63_8_cell_3);
        dstConfigurationTable2638Cell4TextView = (TextView) findViewById(R.id.textview_dst_rig_configuration_table_2_63_8_cell_4);

        dstConfigurationTable1635Cell1TextView.setText(String.valueOf((int) ConfigurationManager.getDstTable1_63_5_Argument1()) + " < N63.5 <= " + String.valueOf((int) ConfigurationManager.getDstTable1_63_5_Argument2()));
        dstConfigurationTable1635Cell2TextView.setText(String.valueOf((int) ConfigurationManager.getDstTable1_63_5_Argument2()) + " < N63.5 <= " + String.valueOf((int) ConfigurationManager.getDstTable1_63_5_Argument3()));
        dstConfigurationTable1635Cell3TextView.setText(String.valueOf((int) ConfigurationManager.getDstTable1_63_5_Argument3()) + " < N63.5 <= " + String.valueOf((int) ConfigurationManager.getDstTable1_63_5_Argument4()));
        dstConfigurationTable1635Cell4TextView.setText("N63.5 > " + String.valueOf((int) ConfigurationManager.getDstTable1_63_5_Argument4()));

        dstConfigurationTable1120Cell1TextView.setText(String.valueOf((int) ConfigurationManager.getDstTable1_120_Argument1()) + " < N120 <= " + String.valueOf((int) ConfigurationManager.getDstTable1_120_Argument2()));
        dstConfigurationTable1120Cell2TextView.setText(String.valueOf((int) ConfigurationManager.getDstTable1_120_Argument2()) + " < N120 <= " + String.valueOf((int) ConfigurationManager.getDstTable1_120_Argument3()));
        dstConfigurationTable1120Cell3TextView.setText(String.valueOf((int) ConfigurationManager.getDstTable1_120_Argument3()) + " < N120 <= " + String.valueOf((int) ConfigurationManager.getDstTable1_120_Argument4()));
        dstConfigurationTable1120Cell4TextView.setText(String.valueOf((int) ConfigurationManager.getDstTable1_120_Argument4()) + " < N120 <= " + String.valueOf((int) ConfigurationManager.getDstTable1_120_Argument5()));
        dstConfigurationTable1120Cell5TextView.setText("N120 > " + String.valueOf((int) ConfigurationManager.getDstTable1_120_Argument5()));

        dstConfigurationTable2636Cell1TextView.setText("N63.5 <= " + String.valueOf((int) ConfigurationManager.getDstTable2_63_6_Argument1()));
        dstConfigurationTable2636Cell2TextView.setText(String.valueOf((int) ConfigurationManager.getDstTable2_63_6_Argument1()) + " < N63.5 <= " + String.valueOf((int) ConfigurationManager.getDstTable2_63_6_Argument2()));
        dstConfigurationTable2636Cell3TextView.setText(String.valueOf((int) ConfigurationManager.getDstTable2_63_6_Argument2()) + " < N63.5 <= " + String.valueOf((int) ConfigurationManager.getDstTable2_63_6_Argument3()));
        dstConfigurationTable2636Cell4TextView.setText("N63.5 > " + String.valueOf((int) ConfigurationManager.getDstTable2_63_6_Argument3()));

        dstConfigurationTable2637Cell1TextView.setText("N63.5 <= " + String.valueOf((int) ConfigurationManager.getDstTable2_63_7_Argument1()));
        dstConfigurationTable2637Cell2TextView.setText(String.valueOf((int) ConfigurationManager.getDstTable2_63_7_Argument1()) + " < N63.5 <= " + String.valueOf((int) ConfigurationManager.getDstTable2_63_7_Argument2()));
        dstConfigurationTable2637Cell3TextView.setText(String.valueOf((int) ConfigurationManager.getDstTable2_63_7_Argument2()) + " < N63.5 <= " + String.valueOf((int) ConfigurationManager.getDstTable2_63_7_Argument3()));
        dstConfigurationTable2637Cell4TextView.setText("N63.5 > " + String.valueOf((int) ConfigurationManager.getDstTable2_63_7_Argument3()));

        dstConfigurationTable2638Cell1TextView.setText("N63.5 <= " + String.valueOf((int) ConfigurationManager.getDstTable2_63_8_Argument1()));
        dstConfigurationTable2638Cell2TextView.setText(String.valueOf((int) ConfigurationManager.getDstTable2_63_8_Argument1()) + " < N63.5 <= " + String.valueOf((int) ConfigurationManager.getDstTable2_63_8_Argument2()));
        dstConfigurationTable2638Cell3TextView.setText(String.valueOf((int) ConfigurationManager.getDstTable2_63_8_Argument2()) + " < N63.5 <= " + String.valueOf((int) ConfigurationManager.getDstTable2_63_8_Argument3()));
        dstConfigurationTable2638Cell4TextView.setText("N63.5 > " + String.valueOf((int) ConfigurationManager.getDstTable2_63_8_Argument3()));
    }
}

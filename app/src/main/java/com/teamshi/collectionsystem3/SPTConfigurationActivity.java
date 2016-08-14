package com.teamshi.collectionsystem3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class SPTConfigurationActivity extends AppCompatActivity {
    private TextView sptConfigurationTable1Value1TextView;
    private TextView sptConfigurationTable1Value2TextView;
    private TextView sptConfigurationTable1Value3TextView;
    private TextView sptConfigurationTable1Value4TextView;
    private TextView sptConfigurationTable1Value5TextView;
    private TextView sptConfigurationTable1Value6TextView;

    private TextView sptConfigurationTable2Value1TextView;
    private TextView sptConfigurationTable2Value2TextView;
    private TextView sptConfigurationTable2Value3TextView;
    private TextView sptConfigurationTable2Value4TextView;

    private TextView sptConfigurationTable3Value1TextView;
    private TextView sptConfigurationTable3Value2TextView;
    private TextView sptConfigurationTable3Value3TextView;
    private TextView sptConfigurationTable3Value4TextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sptconfiguration);

        setTitle("标贯试验判断参数表");

        sptConfigurationTable1Value1TextView = (TextView) findViewById(R.id.textview_spt_rig_configuration_table1_configuration1);
        sptConfigurationTable1Value2TextView = (TextView) findViewById(R.id.textview_spt_rig_configuration_table1_configuration2);
        sptConfigurationTable1Value3TextView = (TextView) findViewById(R.id.textview_spt_rig_configuration_table1_configuration3);
        sptConfigurationTable1Value4TextView = (TextView) findViewById(R.id.textview_spt_rig_configuration_table1_configuration4);
        sptConfigurationTable1Value5TextView = (TextView) findViewById(R.id.textview_spt_rig_configuration_table1_configuration5);
        sptConfigurationTable1Value6TextView = (TextView) findViewById(R.id.textview_spt_rig_configuration_table1_configuration6);

        sptConfigurationTable2Value1TextView = (TextView) findViewById(R.id.textview_spt_rig_configuration_table2_configuration1);
        sptConfigurationTable2Value2TextView = (TextView) findViewById(R.id.textview_spt_rig_configuration_table2_configuration2);
        sptConfigurationTable2Value3TextView = (TextView) findViewById(R.id.textview_spt_rig_configuration_table2_configuration3);
        sptConfigurationTable2Value4TextView = (TextView) findViewById(R.id.textview_spt_rig_configuration_table2_configuration4);

        sptConfigurationTable3Value1TextView = (TextView) findViewById(R.id.textview_spt_rig_configuration_table3_configuration1);
        sptConfigurationTable3Value2TextView = (TextView) findViewById(R.id.textview_spt_rig_configuration_table3_configuration2);
        sptConfigurationTable3Value3TextView = (TextView) findViewById(R.id.textview_spt_rig_configuration_table3_configuration3);
        sptConfigurationTable3Value4TextView = (TextView) findViewById(R.id.textview_spt_rig_configuration_table3_configuration4);

        sptConfigurationTable1Value1TextView.setText("< " + String.valueOf((int) ConfigurationManager.getSptTable1Argument1()));
        sptConfigurationTable1Value2TextView.setText(String.valueOf((int) ConfigurationManager.getSptTable1Argument1()) + " ~ " + String.valueOf((int) ConfigurationManager.getSptTable1Argument2()));
        sptConfigurationTable1Value3TextView.setText(String.valueOf((int) ConfigurationManager.getSptTable1Argument2()) + " ~ " + String.valueOf((int) ConfigurationManager.getSptTable1Argument3()));
        sptConfigurationTable1Value4TextView.setText(String.valueOf((int) ConfigurationManager.getSptTable1Argument3()) + " ~ " + String.valueOf((int) ConfigurationManager.getSptTable1Argument4()));
        sptConfigurationTable1Value5TextView.setText(String.valueOf((int) ConfigurationManager.getSptTable1Argument4()) + " ~ " + String.valueOf((int) ConfigurationManager.getSptTable1Argument5()));
        sptConfigurationTable1Value6TextView.setText("> " + String.valueOf((int) ConfigurationManager.getSptTable1Argument5()));

        sptConfigurationTable2Value1TextView.setText("N <= " + String.valueOf((int) ConfigurationManager.getSptTable2Argument1()));
        sptConfigurationTable2Value2TextView.setText(String.valueOf((int) ConfigurationManager.getSptTable2Argument1() + " < N <= " + String.valueOf((int) ConfigurationManager.getSptTable2Argument2())));
        sptConfigurationTable2Value3TextView.setText(String.valueOf((int) ConfigurationManager.getSptTable2Argument2() + " < N <= " + String.valueOf((int) ConfigurationManager.getSptTable2Argument3())));
        sptConfigurationTable2Value4TextView.setText(String.valueOf("N > " + String.valueOf((int) ConfigurationManager.getSptTable2Argument3())));

        sptConfigurationTable3Value1TextView.setText("N <= " + String.valueOf((int) ConfigurationManager.getSptTable3Argument1()));
        sptConfigurationTable3Value2TextView.setText(String.valueOf((int) ConfigurationManager.getSptTable3Argument1() + " < N <= " + String.valueOf((int) ConfigurationManager.getSptTable3Argument2())));
        sptConfigurationTable3Value3TextView.setText(String.valueOf((int) ConfigurationManager.getSptTable3Argument2() + " < N <= " + String.valueOf((int) ConfigurationManager.getSptTable3Argument3())));
        sptConfigurationTable3Value4TextView.setText(String.valueOf("N > " + String.valueOf((int) ConfigurationManager.getSptTable3Argument3())));



    }
}

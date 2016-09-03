package com.teamshi.collectionsystem3;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.teamshi.collectionsystem3.datastructure.DSTRig;
import com.teamshi.collectionsystem3.datastructure.Project;

import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class DSTRigActivity extends AppCompatActivity {
    private static final String TAG = "CollectionSystem3";

    private static final String[] ROCK_NAME_OPTIONS = {"", "黏土", "粉质黏土", "粉土", "粉砂", "细砂", "中砂" , "粗砂", "砾砂", "漂石",
            "块石", "卵石", "碎石", "粗圆砾土", "细角砾土", "细圆砾土", "粗角砾土", "泥岩", "砂岩", "灰岩", "花岗岩"};

    private static final String[] ROCK120 = {"细圆砾土", "粗圆砾土", "细角砾土", "粗角砾土", "碎石", "卵石", "块石", "漂石"};

    private boolean refreshLock = false;

    private DSTRig rigViewModel;

    private Button confirmAddRigButton;
    private Button cancelAddRigButton;

    private String holeId;

    private EditText classPeopleCountEditText;
    private TextView dateButton;
    private TextView startTimeButton;
    private TextView endTimeButton;
    private TextView timeDurationTextView;

    private TextView probeTypeTextView;
    private TextView probeDiameterTextView;
    private TextView probeLengthTextView;

    private TextView drillToolTotalLengthTextView;
    private TextView drillPipeRemainLengthEditText;
    private TextView roundTripMeterageLengthTextView;
    private TextView accumulatedMeterageLengthTextView;

    private TableRow[] detailedInfoTableRows = new TableRow[20];
    private TextView[] detailedInfoPipeLengthTextViews = new TextView[20];
    private TextView[] detailedInfoDepthTextViews = new TextView[20];
    private EditText[] detailedInfoLengthEditTexts = new EditText[20];
    private EditText[] detailedInfoHitCountEditTexts = new EditText[20];
    private TextView[] detailedInfoSaturationDescriptions = new TextView[20];

    private Button addDstDetailButton;
    private Button deleteDstDetailButton;
    private Button dstTablePreviewButton;
    private Button dstConfigurationPreviewButton;

    private Spinner rockNameSpinner;
    private SpinnerAdapter rockNameSpinnerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "Start DSTRigActivity.");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dstrig);

        this.setTitle("动力触探试验原始数据录入");

        confirmAddRigButton = (Button) findViewById(R.id.button_confirm_add_dst_rig);
        cancelAddRigButton = (Button) findViewById(R.id.button_cancel_add_dst_rig);

        classPeopleCountEditText = (EditText) findViewById(R.id.edittext_dst_rig_class_people_count);
        dateButton = (Button) findViewById(R.id.button_dst_rig_date);
        startTimeButton = (Button) findViewById(R.id.button_dst_rig_start_time);
        endTimeButton = (Button) findViewById(R.id.button_dst_rig_end_time);
        timeDurationTextView = (TextView) findViewById(R.id.textview_dst_rig_duration);

        probeTypeTextView = (TextView) findViewById(R.id.textview_dst_rig_probe_type);
        probeDiameterTextView = (TextView) findViewById(R.id.textview_dst_rig_probe_diameter);
        probeLengthTextView = (TextView) findViewById(R.id.textview_dst_rig_probe_length);

        drillToolTotalLengthTextView = (TextView) findViewById(R.id.textview_dst_rig_drill_tool_total_length);
        drillPipeRemainLengthEditText = (TextView) findViewById(R.id.textview_dst_rig_drill_pipe_remain_length);
        roundTripMeterageLengthTextView = (TextView) findViewById(R.id.textview_dst_round_trip_meterage_length);
        accumulatedMeterageLengthTextView = (TextView) findViewById(R.id.textview_dst_accumulated_meterage_length);

        addDstDetailButton = (Button) findViewById(R.id.button_add_dst_detail);
        deleteDstDetailButton = (Button) findViewById(R.id.button_delete_dst_detail);
        dstTablePreviewButton = (Button) findViewById(R.id.button_dst_rig_preview);
        dstConfigurationPreviewButton = (Button) findViewById(R.id.button_dst_rig_configuration);

        rockNameSpinner = (Spinner) findViewById(R.id.spinner_dst_rig_rock_name);
        rockNameSpinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, ROCK_NAME_OPTIONS);
        rockNameSpinner.setAdapter(rockNameSpinnerAdapter);

        for (int i = 1; i <= 20; i++) {
            final TableRow detailedInfoTableRow = (TableRow) findViewById(getResources().getIdentifier("tablerow_dst_detail_" + i, "id", getPackageName()));
            detailedInfoTableRows[i - 1] = detailedInfoTableRow;

            final TextView detailedInfoPipeLengthTextView = (TextView) findViewById(getResources().getIdentifier("textview_dst_detail_" + i + "_pipe_length", "id", getPackageName()));
            detailedInfoPipeLengthTextViews[i - 1] = detailedInfoPipeLengthTextView;

            final TextView detailedInfoDepthTextView = (TextView) findViewById(getResources().getIdentifier("textview_dst_detail_" + i + "_depth", "id", getPackageName()));
            detailedInfoDepthTextViews[i - 1] = detailedInfoDepthTextView;

            final EditText detailedInfoLengthEditText = (EditText) findViewById(getResources().getIdentifier("edittext_dst_detail_" + i + "_length", "id", getPackageName()));
            detailedInfoLengthEditText.setTag(i);
            detailedInfoLengthEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (!refreshLock) {
                        try {
                            rigViewModel.getDstDetailInfos().get((Integer) detailedInfoLengthEditText.getTag() - 1).setLength(Double.parseDouble(s.toString()));

                            double afterDepth = rigViewModel.getDrillToolTotalLength() - 2 + 0.1 * ((Integer) detailedInfoLengthEditText.getTag() - 1) + Double.parseDouble(s.toString());
                            rigViewModel.getDstDetailInfos().get((int) detailedInfoLengthEditText.getTag() - 1).setDepth(afterDepth);
                            rigViewModel.setAccumulatedMeterageLength(afterDepth);
                            rigViewModel.setRoundTripMeterageLength(0.1 * ((int) detailedInfoLengthEditText.getTag() - 1) + Double.parseDouble(s.toString()));
                            rigViewModel.setDrillPipeRemainLength(2 - rigViewModel.getRoundTripMeterageLength());
                            rigViewModel.setDrillPipeRemainLength(rigViewModel.getDrillToolTotalLength() - rigViewModel.getAccumulatedMeterageLength());

                            if (Double.parseDouble(s.toString()) <= 0.1 && Double.parseDouble(s.toString()) > 0) {
                                detailedInfoLengthEditText.setTextColor(getResources().getColor(android.R.color.black));
                            } else {
                                detailedInfoLengthEditText.setTextColor(getResources().getColor(android.R.color.holo_red_light));
                            }

                            refreshInfo();
                        } catch (Exception e) {
                            detailedInfoLengthEditText.setTextColor(getResources().getColor(android.R.color.holo_red_light));
                        }
                    }
                }
            });
            detailedInfoLengthEditTexts[i - 1] = detailedInfoLengthEditText;

            final EditText detailedInfoHitCountEditText = (EditText) findViewById(getResources().getIdentifier("edittext_dst_detail_" + i + "_hit_count", "id", getPackageName()));
            detailedInfoHitCountEditText.setTag(i);
            detailedInfoHitCountEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (!refreshLock) {
                        try {
                            rigViewModel.getDstDetailInfos().get((int) detailedInfoLengthEditText.getTag() - 1).setHitCount(Integer.parseInt(s.toString()));
                            rigViewModel.getDstDetailInfos().get((int) detailedInfoLengthEditText.getTag() - 1).setSaturationDescription(ConfigurationManager.parseDSTSaturationDescription(rigViewModel.getRockName(),Integer.parseInt(s.toString())));

                            detailedInfoHitCountEditText.setTextColor(getResources().getColor(android.R.color.black));
                            if (Integer.parseInt(s.toString()) <= 51 && Integer.parseInt(s.toString()) > 0) {
                                detailedInfoHitCountEditText.setTextColor(getResources().getColor(android.R.color.black));
                            } else {
                                detailedInfoHitCountEditText.setTextColor(getResources().getColor(android.R.color.holo_red_light));
                            }

                            refreshInfo();
                        } catch (Exception e) {
                            detailedInfoHitCountEditText.setTextColor(getResources().getColor(android.R.color.holo_red_light));
                        }
                    }
                }
            });
            detailedInfoHitCountEditTexts[i - 1] = detailedInfoHitCountEditText;

            TextView detailedInfoSaturationDescription = (TextView) findViewById(getResources().getIdentifier("textview_dst_detail_" + i + "_saturation_description", "id", getPackageName()));
            detailedInfoSaturationDescriptions[i - 1] = detailedInfoSaturationDescription;
        }

        addDstDetailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DSTRig.DSTDetailInfo lastInfo = rigViewModel.getDstDetailInfos().get(rigViewModel.getDstDetailInfos().size() - 1);

                rigViewModel.getDstDetailInfos().add(new DSTRig.DSTDetailInfo(lastInfo.getPipeLength(), lastInfo.getDepth() + 0.1, 0.1, 10, ""));

                rigViewModel.setAccumulatedMeterageLength(rigViewModel.getAccumulatedMeterageLength() + 0.1);
                rigViewModel.setRoundTripMeterageLength(rigViewModel.getRoundTripMeterageLength() + 0.1);
                rigViewModel.setDrillPipeRemainLength(rigViewModel.getDrillPipeRemainLength() - 0.1);

                refreshInfo();
            }
        });

        deleteDstDetailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DSTRig.DSTDetailInfo info = rigViewModel.getDstDetailInfos().get(rigViewModel.getDstDetailInfos().size() - 1);

                rigViewModel.setAccumulatedMeterageLength(rigViewModel.getAccumulatedMeterageLength() - info.getLength());
                rigViewModel.setRoundTripMeterageLength(rigViewModel.getRoundTripMeterageLength() - info.getLength());
                rigViewModel.setDrillPipeRemainLength(rigViewModel.getDrillPipeRemainLength() + info.getLength());

                rigViewModel.getDstDetailInfos().remove(rigViewModel.getDstDetailInfos().size() - 1);

                refreshInfo();
            }
        });

        dstTablePreviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Project project = DataManager.getProject();
                PreviewActivity.setUrls(IOManager.previewDSTRig(rigViewModel));
                Intent intent = new Intent(DSTRigActivity.this, PreviewActivity.class);
                intent.putExtra("projectName", project.getProjectName());
                startActivity(intent);
            }
        });

        dstConfigurationPreviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DSTRigActivity.this, DSTConfiurationActivity.class);
                startActivity(intent);
            }
        });


        classPeopleCountEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!refreshLock) {
                    rigViewModel.setClassPeopleCount(s.toString());
                    DataManager.setLastClassPeopleCount(holeId, s.toString());
                }
            }
        });

        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar rigDate = rigViewModel.getDate();
                DatePickerDialog dialog = new DatePickerDialog(DSTRigActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        GregorianCalendar temp = new GregorianCalendar();
                        temp.set(year, monthOfYear, dayOfMonth);
                        rigViewModel.setDate(temp);
                        refreshInfo();
                    }
                }, rigDate.get(Calendar.YEAR), rigDate.get(Calendar.MONTH), rigDate.get(Calendar.DAY_OF_MONTH));

                dialog.show();
            }
        });

        startTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar rigStartTime = rigViewModel.getStartTime();

                TimePickerDialog dialog = new TimePickerDialog(DSTRigActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        GregorianCalendar temp = new GregorianCalendar();
                        temp.set(1, 1, 1, hourOfDay, minute);
                        rigViewModel.setStartTime(temp);

                        refreshInfo();
                    }
                }, rigStartTime.get(Calendar.HOUR_OF_DAY), rigStartTime.get(Calendar.MINUTE), true);
                dialog.show();
            }
        });

        endTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar rigEndTime = rigViewModel.getEndTime();

                TimePickerDialog dialog = new TimePickerDialog(DSTRigActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        GregorianCalendar temp = new GregorianCalendar();
                        temp.set(1, 1, 1, hourOfDay, minute);
                        rigViewModel.setEndTime(temp);

                        refreshInfo();
                    }
                }, rigEndTime.get(Calendar.HOUR_OF_DAY), rigEndTime.get(Calendar.MINUTE), true);
                dialog.show();
            }
        });

        confirmAddRigButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String requestCode = getIntent().getStringExtra("requestCode");

                switch (requestCode) {
                    case "ACTION_ADD_RIG":
                        if (validate()) {
                            String holeId = getIntent().getStringExtra("holeId");

                            rigViewModel.setLastPipeNumber(DataManager.getHole(holeId).getPipeCount());
                            rigViewModel.setLastRigEndTime((Calendar) DataManager.getHole(holeId).getLastRigEndTime().clone());
                            rigViewModel.setLastRockCorePipeLength(DataManager.getHole(holeId).getLastRockCorePipeLength());
                            rigViewModel.setLastAccumulatedMeterageLength(DataManager.getHole(holeId).getLastAccumulatedMeterageLength());
                            rigViewModel.setLastMaxRigRockCoreIndex(DataManager.getHole(holeId).getMaxRigRockCoreIndex());

                            rigViewModel.setLastRockName(DataManager.getHole(holeId).getLastRockName());
                            rigViewModel.setLastRockColor(DataManager.getHole(holeId).getLastRockColor());
                            rigViewModel.setLastRockSaturation(DataManager.getHole(holeId).getLastRockSaturation());

                            DataManager.addRig(holeId, rigViewModel);

                            DataManager.getHole(holeId).setLastRigEndTime(rigViewModel.getEndTime());
                            DataManager.getHole(holeId).setLastAccumulatedMeterageLength(rigViewModel.getAccumulatedMeterageLength());
                            DataManager.getHole(holeId).setLastRockName(rigViewModel.getRockName());

                            Calendar now = Calendar.getInstance();
                            DataManager.getHole(holeId).setEndDate(now);

                            now.add(Calendar.DATE, 2);

                            DataManager.getHole(holeId).setReviewDate(now);

                            DataManager.getHole(holeId).setLastRigEndTime(rigViewModel.getEndTime());
                            DataManager.getHole(holeId).setActualDepth(rigViewModel.getAccumulatedMeterageLength());

                            IOManager.updateProject(DataManager.getProject());
                            DSTRigActivity.this.setResult(RESULT_OK);
                            DSTRigActivity.this.finish();
                        }
                        break;
                    case "ACTION_EDIT_RIG":
                        if (validate()) {
                            String holeId = getIntent().getStringExtra("holeId");
                            int rigIndex = getIntent().getIntExtra("rigIndex", 0);

                            DataManager.updateRig(holeId, rigIndex, rigViewModel);

                            IOManager.updateProject(DataManager.getProject());
                            DSTRigActivity.this.setResult(RESULT_OK);
                            DSTRigActivity.this.finish();
                        }
                        break;
                }
            }
        });

        cancelAddRigButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DSTRigActivity.this.setResult(RESULT_CANCELED);
                DSTRigActivity.this.finish();
            }
        });

        rockNameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // set probe type
                rigViewModel.setRockName(ROCK_NAME_OPTIONS[position]);

                if (Arrays.asList(ROCK120).contains(rigViewModel.getRockName())) {
                    rigViewModel.setProbeType("超重型");
                } else {
                    rigViewModel.setProbeType("重型");
                }

                for (DSTRig.DSTDetailInfo info : rigViewModel.getDstDetailInfos()) {
                    info.setSaturationDescription(ConfigurationManager.parseDSTSaturationDescription(rigViewModel.getRockName(), info.getHitCount()));
                }

                refreshInfo();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        String requestCode = getIntent().getStringExtra("requestCode");

        holeId = getIntent().getStringExtra("holeId");

        switch (requestCode) {
            case "ACTION_ADD_RIG":
                Calendar startTime = (Calendar) DataManager.getHole(holeId).getLastRigEndTime().clone();
                Calendar endTime = (Calendar) DataManager.getHole(holeId).getLastRigEndTime().clone();
                endTime.add(Calendar.MINUTE, 1);

                rigViewModel = new DSTRig(DataManager.getHole(holeId).getLastClassPeopleCount(), startTime, startTime, endTime,
                        DataManager.getHole(holeId).getLastAccumulatedMeterageLength() + 2, 1.9,
                        0.1, DataManager.getHole(holeId).getLastAccumulatedMeterageLength() + 0.1,
                        "重型", 74, 0.25
                );

                refreshInfo();
                break;
            case "ACTION_EDIT_RIG":
                String holeId = getIntent().getStringExtra("holeId");
                int rigIndex = getIntent().getIntExtra("rigIndex", 0);

                rigViewModel = (DSTRig) DataManager.getRig(holeId, rigIndex).deepCopy();

                refreshInfo();

                for (EditText et : detailedInfoLengthEditTexts) {
                    et.setEnabled(false);
                }

                for (EditText et : detailedInfoHitCountEditTexts) {
                    et.setEnabled(false);
                }

                classPeopleCountEditText.setEnabled(false);
                dateButton.setEnabled(false);
                startTimeButton.setEnabled(false);
                endTimeButton.setEnabled(false);
                probeTypeTextView.setEnabled(false);
                probeDiameterTextView.setEnabled(false);
                probeLengthTextView.setEnabled(false);
                addDstDetailButton.setEnabled(false);
                deleteDstDetailButton.setEnabled(false);

                break;
        }
    }

    private void refreshInfo() {
        refreshLock = true;

        if (getCurrentFocus() != classPeopleCountEditText) {
            classPeopleCountEditText.setText(rigViewModel.getClassPeopleCount());
        }

        dateButton.setText(Utility.formatCalendarDateString(rigViewModel.getDate()));
        startTimeButton.setText(Utility.formatTimeString(rigViewModel.getStartTime()));
        endTimeButton.setText(Utility.formatTimeString(rigViewModel.getEndTime()));
        timeDurationTextView.setText(Utility.calculateTimeSpan(rigViewModel.getStartTime(), rigViewModel.getEndTime()));

        probeTypeTextView.setText(rigViewModel.getProbeType());
        probeDiameterTextView.setText(String.valueOf(rigViewModel.getProbeDiameter()));
        probeLengthTextView.setText(Utility.formatDouble(rigViewModel.getProbeLength()));

        drillToolTotalLengthTextView.setText(Utility.formatDouble(rigViewModel.getDrillToolTotalLength()));

        if (getCurrentFocus() != drillPipeRemainLengthEditText) {
            drillPipeRemainLengthEditText.setText(Utility.formatDouble(rigViewModel.getDrillPipeRemainLength()));
        }

        roundTripMeterageLengthTextView.setText(Utility.formatDouble(rigViewModel.getRoundTripMeterageLength()));
        accumulatedMeterageLengthTextView.setText(Utility.formatDouble(rigViewModel.getAccumulatedMeterageLength()));

        if (rigViewModel.getDstDetailInfos().size() == 1) {
            deleteDstDetailButton.setEnabled(false);
        } else {
            deleteDstDetailButton.setEnabled(true);
        }

        if (rigViewModel.getDstDetailInfos().size() == 20) {
            addDstDetailButton.setEnabled(false);
        } else {
            addDstDetailButton.setEnabled(true);
        }

        String requestCode = getIntent().getStringExtra("requestCode");
        if (requestCode.equals("ACTION_EDIT_RIG")) {
            addDstDetailButton.setEnabled(false);
            deleteDstDetailButton.setEnabled(false);
        }

        for (int i = 0; i < 20; i++) {
            if (i < rigViewModel.getDstDetailInfos().size()) {
                detailedInfoTableRows[i].setVisibility(View.VISIBLE);
                detailedInfoPipeLengthTextViews[i].setText(Utility.formatDouble(rigViewModel.getDstDetailInfos().get(i).getPipeLength()));
                detailedInfoDepthTextViews[i].setText(Utility.formatDouble(rigViewModel.getDstDetailInfos().get(i).getDepth()));

                if (getCurrentFocus() != detailedInfoLengthEditTexts[i]) {
                    detailedInfoLengthEditTexts[i].setText(Utility.formatDouble(rigViewModel.getDstDetailInfos().get(i).getLength()));
                }

                if (getCurrentFocus() != detailedInfoHitCountEditTexts[i]) {
                    detailedInfoHitCountEditTexts[i].setText(String.valueOf(rigViewModel.getDstDetailInfos().get(i).getHitCount()));
                }

                if (i == rigViewModel.getDstDetailInfos().size() - 1) {
                    detailedInfoLengthEditTexts[i].setEnabled(true);
                    detailedInfoHitCountEditTexts[i].setEnabled(true);

                    if (rigViewModel.getDstDetailInfos().get(i).getLength() == 0.1 && rigViewModel.getDstDetailInfos().get(i).getHitCount() <= 51) {
                        addDstDetailButton.setEnabled(true);
                    } else {
                        addDstDetailButton.setEnabled(false);
                    }
                } else {
                    detailedInfoLengthEditTexts[i].setEnabled(false);
                    detailedInfoHitCountEditTexts[i].setEnabled(false);
                }

                detailedInfoSaturationDescriptions[i].setText(rigViewModel.getDstDetailInfos().get(i).getSaturationDescription());

            } else {
                detailedInfoTableRows[i].setVisibility(View.GONE);
            }
        }

        for (int i = 0;i < 20; i++) {
            if (requestCode.equals("ACTION_EDIT_RIG")) {
                detailedInfoLengthEditTexts[i].setEnabled(false);
                detailedInfoHitCountEditTexts[i].setEnabled(false);
            }

        }

        for (int i = 0; i < ROCK_NAME_OPTIONS.length; i++) {
            if (ROCK_NAME_OPTIONS[i].equals(rigViewModel.getRockName())) {
                rockNameSpinner.setSelection(i);
                break;
            }
        }

        refreshLock = false;
    }

    private boolean validate() {
        DSTRig.DSTDetailInfo info = rigViewModel.getDstDetailInfos().get(rigViewModel.getDstDetailInfos().size() - 1);

        if (info.getLength() > 0.1) {
            Toast.makeText(DSTRigActivity.this, "贯入深度必须小于等于0.1m.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (info.getHitCount() > 52) {
            Toast.makeText(DSTRigActivity.this, "锤击数不得大于51.", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}

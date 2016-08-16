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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.teamshi.collectionsystem3.datastructure.Project;
import com.teamshi.collectionsystem3.datastructure.RegularRig;
import com.teamshi.collectionsystem3.datastructure.SPTRig;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class SPTRigActivity extends AppCompatActivity {
    private static final String TAG = "CollectionSystem3";

    private boolean refreshLock = false;

    private SPTRig rigViewModel;

    private Button confirmAddRigButton;
    private Button cancelAddRigButton;

    private String holeId;

    private EditText classPeopleCountEditText;
    private TextView dateButton;
    private TextView startTimeButton;
    private TextView endTimeButton;
    private TextView timeDurationTextView;

    private TextView injectionToolDialameterTextView;
    private TextView injectionToolLengthTextView;

    private EditText probeTypeEditText;
    private EditText probeDiameterEditText;
    private EditText probeLengthEditText;

    private TextView drillToolTotalLengthTextView;
    private EditText drillPipeRemainLengthEditText;
    private TextView roundTripMeterageLengthTextView;
    private TextView accumulatedMeterageLengthTextView;

    private EditText penetrationStartDepthEditText;
    private EditText penetrationEndDepthEditText;

    private EditText countStartDepth1EditText;
    private EditText countEndDepth1EditText;
    private EditText countStartDepth2EditText;
    private EditText countEndDepth2EditText;
    private EditText countStartDepth3EditText;
    private EditText countEndDepth3EditText;

    private EditText hitCount1EditText;
    private EditText hitCount2EditText;
    private EditText hitCount3EditText;

    private EditText drillStartDepth1EditText;
    private EditText drillEndDepth1EditText;
    private EditText drillStartDepth2EditText;
    private EditText drillEndDepth2EditText;
    private EditText drillStartDepth3EditText;
    private EditText drillEndDepth3EditText;

    private Button rockParameterButton;

    private EditText rockNameEditText;
    private EditText rockColorEditText;
    private EditText rockDensityEditText;
    private EditText rockSaturationEditText;
    private TextView accumulatedHitCountTextView;

    private EditText otherDescriptionEditText;

    private Button rigViewTableButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "Start SPTRigActivity.");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sptrig);

        this.setTitle("标准贯入试验原始数据录入");

        confirmAddRigButton = (Button) findViewById(R.id.button_confirm_add_spt_rig);
        cancelAddRigButton = (Button) findViewById(R.id.button_cancel_add_spt_rig);

        classPeopleCountEditText = (EditText) findViewById(R.id.edittext_spt_rig_class_people_count);
        dateButton = (Button) findViewById(R.id.button_spt_rig_date);
        startTimeButton = (Button) findViewById(R.id.button_spt_rig_start_time);
        endTimeButton = (Button) findViewById(R.id.button_spt_rig_end_time);
        timeDurationTextView = (TextView) findViewById(R.id.textview_spt_rig_duration);

        injectionToolDialameterTextView = (TextView) findViewById(R.id.textview_spt_rig_injection_tool_diameter);
        injectionToolLengthTextView = (TextView) findViewById(R.id.textview_spt_rig_injection_tool_length);

        probeTypeEditText = (EditText) findViewById(R.id.edittext_spt_rig_probe_type);
        probeDiameterEditText = (EditText) findViewById(R.id.edittext_spt_rig_probe_diameter);
        probeLengthEditText = (EditText) findViewById(R.id.edittext_spt_rig_probe_length);

        drillToolTotalLengthTextView = (TextView) findViewById(R.id.textview_spt_rig_drill_tool_total_length);
        drillPipeRemainLengthEditText = (EditText) findViewById(R.id.edittext_spt_rig_drill_pipe_remain_length);
        roundTripMeterageLengthTextView = (TextView) findViewById(R.id.textview_spt_round_trip_meterage_length);
        accumulatedMeterageLengthTextView = (TextView) findViewById(R.id.textview_spt_accumulated_meterage_length);

        penetrationStartDepthEditText = (EditText) findViewById(R.id.edittext_spt_rig_penetration_start_depth);
        penetrationEndDepthEditText = (EditText) findViewById(R.id.edittext_spt_rig_penetration_end_depth);

        countStartDepth1EditText = (EditText) findViewById(R.id.edittext_spt_rig_count_start_depth_1);
        countEndDepth1EditText = (EditText) findViewById(R.id.edittext_spt_rig_count_end_depth_1);
        countStartDepth2EditText = (EditText) findViewById(R.id.edittext_spt_rig_count_start_depth_2);
        countEndDepth2EditText = (EditText) findViewById(R.id.edittext_spt_rig_count_end_depth_2);
        countStartDepth3EditText = (EditText) findViewById(R.id.edittext_spt_rig_count_start_depth_3);
        countEndDepth3EditText = (EditText) findViewById(R.id.edittext_spt_rig_count_end_depth_3);

        hitCount1EditText = (EditText) findViewById(R.id.edittext_spt_rig_hit_count_1);
        hitCount2EditText = (EditText) findViewById(R.id.edittext_spt_rig_hit_count_2);
        hitCount3EditText = (EditText) findViewById(R.id.edittext_spt_rig_hit_count_3);

        drillStartDepth1EditText = (EditText) findViewById(R.id.edittext_spt_rig_drill_start_depth_1);
        drillEndDepth1EditText = (EditText) findViewById(R.id.edittext_spt_rig_drill_end_depth_1);
        drillStartDepth2EditText = (EditText) findViewById(R.id.edittext_spt_rig_drill_start_depth_2);
        drillEndDepth2EditText = (EditText) findViewById(R.id.edittext_spt_rig_drill_end_depth_2);
        drillStartDepth3EditText = (EditText) findViewById(R.id.edittext_spt_rig_drill_start_depth_3);
        drillEndDepth3EditText = (EditText) findViewById(R.id.edittext_spt_rig_drill_end_depth_3);

        rockParameterButton = (Button) findViewById(R.id.button_spt_rig_rock_parameter_table);

        rockNameEditText = (EditText) findViewById(R.id.edittext_spt_rig_rock_name);
        rockColorEditText = (EditText) findViewById(R.id.edittext_spt_rig_rock_color);
        rockDensityEditText = (EditText) findViewById(R.id.edittext_spt_rig_rock_density);
        rockSaturationEditText = (EditText) findViewById(R.id.edittext_spt_rig_rock_saturation);
        accumulatedHitCountTextView = (TextView) findViewById(R.id.textview_spt_rig_accumulated_hit_count);

        otherDescriptionEditText = (EditText) findViewById(R.id.edittext_spt_rig_other_description);
        rigViewTableButton =  (Button) findViewById(R.id.button_preview_spt);

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
                DatePickerDialog dialog = new DatePickerDialog(SPTRigActivity.this, new DatePickerDialog.OnDateSetListener() {
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

                TimePickerDialog dialog = new TimePickerDialog(SPTRigActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        GregorianCalendar temp = new GregorianCalendar();
                        temp.set(1, 1, 1, hourOfDay, minute);
                        rigViewModel.setStartTime(temp);

                        refreshInfo();
                    }
                }, rigStartTime.get(Calendar.HOUR), rigStartTime.get(Calendar.MINUTE), true);
                dialog.show();
            }
        });

        endTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar rigEndTime = rigViewModel.getEndTime();

                TimePickerDialog dialog = new TimePickerDialog(SPTRigActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        GregorianCalendar temp = new GregorianCalendar();
                        temp.set(1, 1, 1, hourOfDay, minute);
                        rigViewModel.setEndTime(temp);

                        refreshInfo();
                    }
                }, rigEndTime.get(Calendar.HOUR), rigEndTime.get(Calendar.MINUTE), true);
                dialog.show();
            }
        });

        confirmAddRigButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String requestCode = getIntent().getStringExtra("requestCode");

                switch (requestCode) {
                    case "ACTION_ADD_RIG":
                    case "ACTION_COPY_RIG":
                        if (validate()) {
                            String holeId = getIntent().getStringExtra("holeId");

                            DataManager.addRig(holeId, rigViewModel);

//                            if (rigViewModel.getPipeNumber() == DataManager.getHole(holeId).getPipeCount() + 1) {
//                                DataManager.getHole(holeId).addPipe(rigViewModel.getPipeLength());
//                            }
//
//                            DataManager.getHole(holeId).setLastRigEndTime(rigViewModel.getEndTime());
//                            DataManager.getHole(holeId).setLastRockCorePipeLength(rigViewModel.getRockCorePipeLength());
//                            DataManager.getHole(holeId).setLastAccumulatedMeterageLength(rigViewModel.getAccumulatedMeterageLength());
//
//                            if (rigViewModel.getRockCoreIndex() > DataManager.getHole(holeId).getMaxRigRockCoreIndex()) {
//                                DataManager.getHole(holeId).setMaxRigRockCoreIndex(rigViewModel.getRockCoreIndex());
//                            }

                            IOManager.updateProject(DataManager.getProject());
                            SPTRigActivity.this.setResult(RESULT_OK);
                            SPTRigActivity.this.finish();
                        }
                        break;
                    case "ACTION_EDIT_RIG":
                        if (validate()) {
                            String holeId = getIntent().getStringExtra("holeId");
                            int rigIndex = getIntent().getIntExtra("rigIndex", 0);

                            DataManager.updateRig(holeId, rigIndex, rigViewModel);

//                            if (rigViewModel.getPipeNumber() == DataManager.getHole(holeId).getPipeCount() + 1) {
//                                DataManager.getHole(holeId).addPipe(rigViewModel.getPipeLength());
//                            }

                            DataManager.getHole(holeId).setLastRigEndTime(rigViewModel.getEndTime());

                            IOManager.updateProject(DataManager.getProject());
                            SPTRigActivity.this.setResult(RESULT_OK);
                            SPTRigActivity.this.finish();
                        }
                        break;
                }
            }
        });

        cancelAddRigButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SPTRigActivity.this.setResult(RESULT_CANCELED);
                SPTRigActivity.this.finish();
            }
        });

        probeTypeEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                rigViewModel.setProbeType(s.toString());
            }
        });

        probeLengthEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    rigViewModel.setProbeLength(Double.parseDouble(s.toString()));
                    probeLengthEditText.setTextColor(getResources().getColor(android.R.color.black));
                } catch (Exception e) {
                    probeLengthEditText.setTextColor(getResources().getColor(android.R.color.holo_red_light));
                }
            }
        });

        probeDiameterEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    rigViewModel.setProbeDiameter(Integer.parseInt(s.toString()));
                    probeDiameterEditText.setTextColor(getResources().getColor(android.R.color.black));
                } catch (Exception e) {
                    probeDiameterEditText.setTextColor(getResources().getColor(android.R.color.holo_red_light));
                }

            }
        });

        drillPipeRemainLengthEditText.addTextChangedListener(new TextWatcher() {
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
                        rigViewModel.setDrillPipeRemainLength(Double.parseDouble(s.toString()));
                        drillPipeRemainLengthEditText.setTextColor(getResources().getColor(android.R.color.black));

                        //rigViewModel.setDrillToolTotalLength(rigViewModel.getPipeTotalLength() + rigViewModel.getRockCorePipeLength() + rigViewModel.getDrillBitLength());
                        rigViewModel.setRoundTripMeterageLength(rigViewModel.getDrillToolTotalLength() - DataManager.getHole(holeId).getLastAccumulatedMeterageLength());
                        //rigViewModel.setAccumulatedMeterageLength(rigViewModel.getPipeTotalLength() - rigViewModel.getDrillPipeRemainLength());

                        refreshInfo();
                    } catch (Exception e) {
                        drillPipeRemainLengthEditText.setTextColor(getResources().getColor(android.R.color.holo_red_light));
                    }
                }
            }
        });

        penetrationEndDepthEditText.addTextChangedListener(new TextWatcher() {
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
                        double penetrationEndDepth = Double.parseDouble(s.toString());

                        if (penetrationEndDepth < rigViewModel.getPenetrationStartDepth() + 0.15) {
                            rigViewModel.setPenetrationEndDepth(penetrationEndDepth);
                            rigViewModel.setOtherDescription("贯入深度自 " + rigViewModel.getPenetrationStartDepth() + "m 至 " + String.format("%.2f", rigViewModel.getPenetrationEndDepth()) + "m 反弹51击");

                            rigViewModel.setCountStartDepth1(rigViewModel.getPenetrationStartDepth());
                            rigViewModel.setCountStartDepth2(-1);
                            rigViewModel.setCountStartDepth3(-1);

                            rigViewModel.setCountEndDepth1(penetrationEndDepth);
                            rigViewModel.setCountEndDepth2(-1);
                            rigViewModel.setCountEndDepth3(-1);

                            rigViewModel.setDrillStartDepth1(rigViewModel.getPenetrationStartDepth());
                            rigViewModel.setDrillStartDepth2(-1);
                            rigViewModel.setDrillStartDepth3(-1);

                            rigViewModel.setDrillEndDepth1(penetrationEndDepth);
                            rigViewModel.setDrillEndDepth2(-1);
                            rigViewModel.setDrillEndDepth3(-1);

                            rigViewModel.setHitCount1(51);
                            rigViewModel.setHitCount2(-1);
                            rigViewModel.setHitCount3(-1);

                            rigViewModel.setAccumulatehHitCount(51);
                        } else {
                            rigViewModel.setPenetrationEndDepth(penetrationEndDepth);
                            rigViewModel.setOtherDescription("");

                            rigViewModel.setCountStartDepth1(rigViewModel.getPenetrationStartDepth() + 0.15);
                            rigViewModel.setCountStartDepth2(rigViewModel.getPenetrationStartDepth() + 0.25);
                            rigViewModel.setCountStartDepth3(rigViewModel.getPenetrationStartDepth() + 0.35);

                            rigViewModel.setCountEndDepth1(rigViewModel.getPenetrationStartDepth() + 0.25);
                            rigViewModel.setCountEndDepth2(rigViewModel.getPenetrationStartDepth() + 0.35);
                            rigViewModel.setCountEndDepth3(penetrationEndDepth);

                            rigViewModel.setDrillStartDepth1(rigViewModel.getPenetrationStartDepth());
                            rigViewModel.setDrillStartDepth2(rigViewModel.getPenetrationStartDepth() + 0.25);
                            rigViewModel.setDrillStartDepth3(rigViewModel.getPenetrationStartDepth() + 0.35);

                            rigViewModel.setDrillEndDepth1(rigViewModel.getPenetrationStartDepth() + 0.25);
                            rigViewModel.setDrillEndDepth2(rigViewModel.getPenetrationStartDepth() + 0.35);
                            rigViewModel.setDrillEndDepth3(penetrationEndDepth);

                            rigViewModel.setHitCount1(0);
                            rigViewModel.setHitCount2(0);
                            rigViewModel.setHitCount3(0);

                            rigViewModel.setAccumulatehHitCount(0);
                        }

                        penetrationEndDepthEditText.setTextColor(getResources().getColor(android.R.color.black));

                        refreshInfo();
                    } catch (Exception e) {
                        penetrationEndDepthEditText.setTextColor(getResources().getColor(android.R.color.holo_red_light));
                    }
                }

            }
        });

        rockParameterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SPTRigActivity.this, SPTConfigurationActivity.class);
                startActivity(intent);
            }
        });

        rigViewTableButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Johnson preview
                Project project = DataManager.getProject();

                PreviewActivity.setUrls(IOManager.previewSPTRig(rigViewModel));
                Intent intent = new Intent(SPTRigActivity.this, PreviewActivity.class);
                intent.putExtra("projectName", project.getProjectName());
                startActivity(intent);
            }
        });

        String requestCode = getIntent().getStringExtra("requestCode");

        holeId = getIntent().getStringExtra("holeId");

        switch (requestCode) {
            case "ACTION_ADD_RIG":
                Calendar startTime = (Calendar) DataManager.getHole(holeId).getLastRigEndTime().clone();
                Calendar endTime = (Calendar) DataManager.getHole(holeId).getLastRigEndTime().clone();
                endTime.add(Calendar.MINUTE, 1);

                rigViewModel = new SPTRig(DataManager.getHole(holeId).getLastClassPeopleCount(), startTime, startTime, endTime,
                        0, 0, 0, 0,
                        51, 0.5,
                        "管靴",0, 0,
                        DataManager.getHole(holeId).getLastAccumulatedMeterageLength(), DataManager.getHole(holeId).getLastAccumulatedMeterageLength() + 0.45,
                        0, 0, 0,
                        DataManager.getHole(holeId).getLastAccumulatedMeterageLength() + 0.15, DataManager.getHole(holeId).getLastAccumulatedMeterageLength() + 0.25,
                        DataManager.getHole(holeId).getLastAccumulatedMeterageLength() + 0.25, DataManager.getHole(holeId).getLastAccumulatedMeterageLength() + 0.35,
                        DataManager.getHole(holeId).getLastAccumulatedMeterageLength() + 0.35, DataManager.getHole(holeId).getLastAccumulatedMeterageLength() + 0.45,
                        DataManager.getHole(holeId).getLastAccumulatedMeterageLength(), DataManager.getHole(holeId).getLastAccumulatedMeterageLength() + 0.25,
                        DataManager.getHole(holeId).getLastAccumulatedMeterageLength() + 0.25, DataManager.getHole(holeId).getLastAccumulatedMeterageLength() + 0.35,
                        DataManager.getHole(holeId).getLastAccumulatedMeterageLength() + 0.35, DataManager.getHole(holeId).getLastAccumulatedMeterageLength() + 0.45,
                        "",
                        "",
                        "",
                        "",
                        ""
                );
                
                refreshInfo();
                break;
            case "ACTION_COPY_RIG":
                break;
            case "ACTION_EDIT_RIG":
                String holeId = getIntent().getStringExtra("holeId");
                int rigIndex = getIntent().getIntExtra("rigIndex", 0);

                rigViewModel = (SPTRig) DataManager.getRig(holeId, rigIndex).deepCopy();

                refreshInfo();

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

        injectionToolDialameterTextView.setText(String.valueOf(rigViewModel.getInjectionToolDiameter()));
        injectionToolLengthTextView.setText(String.format("%.2f", rigViewModel.getInjectionToolLength()));

        probeTypeEditText.setText(rigViewModel.getProbeType());
        probeDiameterEditText.setText(String.valueOf(rigViewModel.getProbeDiameter()));
        probeLengthEditText.setText(String.format("%.2f", rigViewModel.getProbeLength()));

        drillToolTotalLengthTextView.setText(String.format("%.2f", rigViewModel.getDrillToolTotalLength()));

        if (getCurrentFocus() != drillPipeRemainLengthEditText) {
            drillPipeRemainLengthEditText.setText(String.format("%.2f", rigViewModel.getDrillPipeRemainLength()));
        }

        roundTripMeterageLengthTextView.setText(String.format("%.2f", rigViewModel.getRoundTripMeterageLength()));
        accumulatedMeterageLengthTextView.setText(String.format("%.2f", rigViewModel.getAccumulatedMeterageLength()));

        if (getCurrentFocus() != penetrationStartDepthEditText) {
            penetrationStartDepthEditText.setText(String.format("%.2f", rigViewModel.getPenetrationStartDepth()));
        }

        if (getCurrentFocus() != penetrationEndDepthEditText) {
            penetrationEndDepthEditText.setText(String.format("%.2f", rigViewModel.getPenetrationEndDepth()));
        }

        if (getCurrentFocus() != hitCount1EditText) {
            hitCount1EditText.setText(rigViewModel.getHitCount1() < 0? "": String.valueOf(rigViewModel.getHitCount1()));
            hitCount1EditText.setEnabled(rigViewModel.getHitCount1() >= 0 && rigViewModel.getHitCount1() != 51);
        }

        if (getCurrentFocus() != hitCount2EditText) {
            hitCount2EditText.setText(rigViewModel.getHitCount2() < 0? "": String.valueOf(rigViewModel.getHitCount2()));
            hitCount2EditText.setEnabled(rigViewModel.getHitCount2() >= 0);
        }

        if (getCurrentFocus() != hitCount3EditText) {
            hitCount3EditText.setText(rigViewModel.getHitCount3() < 0? "": String.valueOf(rigViewModel.getHitCount3()));
            hitCount3EditText.setEnabled(rigViewModel.getHitCount3() >= 0);
        }

        if (getCurrentFocus() != countStartDepth1EditText) {
            countStartDepth1EditText.setText(rigViewModel.getCountStartDepth1() < 0? "": String.format("%.2f", rigViewModel.getCountStartDepth1()));
        }

        if (getCurrentFocus() != countEndDepth1EditText) {
            countEndDepth1EditText.setText(rigViewModel.getCountEndDepth1() < 0? "": String.format("%.2f", rigViewModel.getCountEndDepth1()));
            countEndDepth1EditText.setEnabled(rigViewModel.getCountEndDepth1() >= 0 && rigViewModel.getHitCount1() != 51);
        }

        if (getCurrentFocus() != countStartDepth2EditText) {
            countStartDepth2EditText.setText(rigViewModel.getCountStartDepth2() < 0? "": String.format("%.2f", rigViewModel.getCountStartDepth2()));
        }

        if (getCurrentFocus() != countEndDepth2EditText) {
            countEndDepth2EditText.setText(rigViewModel.getCountEndDepth2() < 0? "": String.format("%.2f", rigViewModel.getCountEndDepth2()));
            countEndDepth2EditText.setEnabled(rigViewModel.getCountEndDepth2() >= 0);
        }

        if (getCurrentFocus() != countStartDepth3EditText) {
            countStartDepth3EditText.setText(rigViewModel.getCountStartDepth3() < 0? "": String.format("%.2f", rigViewModel.getCountStartDepth3()));
        }

        if (getCurrentFocus() != countEndDepth3EditText) {
            countEndDepth3EditText.setText(rigViewModel.getCountEndDepth3() < 0? "": String.format("%.2f", rigViewModel.getCountEndDepth3()));
            countEndDepth3EditText.setEnabled(rigViewModel.getCountEndDepth3() >= 0);
        }

        if (getCurrentFocus() != drillStartDepth1EditText) {
            drillStartDepth1EditText.setText(rigViewModel.getDrillStartDepth1() < 0? "": String.format("%.2f", rigViewModel.getDrillStartDepth1()));
        }

        if (getCurrentFocus() != drillEndDepth1EditText) {
            drillEndDepth1EditText.setText(rigViewModel.getDrillEndDepth1() < 0? "": String.format("%.2f", rigViewModel.getDrillEndDepth1()));
        }

        if (getCurrentFocus() != drillStartDepth2EditText) {
            drillStartDepth2EditText.setText(rigViewModel.getDrillStartDepth2() < 0? "": String.format("%.2f", rigViewModel.getDrillStartDepth2()));
        }

        if (getCurrentFocus() != drillEndDepth2EditText) {
            drillEndDepth2EditText.setText(rigViewModel.getDrillEndDepth2() < 0? "": String.format("%.2f", rigViewModel.getDrillEndDepth2()));
        }

        if (getCurrentFocus() != drillStartDepth3EditText) {
            drillStartDepth3EditText.setText(rigViewModel.getDrillStartDepth3() < 0? "": String.format("%.2f", rigViewModel.getDrillStartDepth3()));
        }

        if (getCurrentFocus() != drillEndDepth3EditText) {
            drillEndDepth3EditText.setText(rigViewModel.getDrillEndDepth3() < 0? "": String.format("%.2f", rigViewModel.getDrillEndDepth3()));
        }

        if (getCurrentFocus() != otherDescriptionEditText) {
            otherDescriptionEditText.setText(rigViewModel.getOtherDescription());
        }

        accumulatedHitCountTextView.setText(rigViewModel.getAccumulatehHitCount() < 0? "": String.valueOf(rigViewModel.getAccumulatehHitCount()));

        refreshLock = false;
    }

    private boolean validate() {

        if (!Utility.validateStartEndTime(rigViewModel.getStartTime(), rigViewModel.getEndTime())) {
            Toast.makeText(SPTRigActivity.this, "开始时间不得大于等于结束时间", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }
}

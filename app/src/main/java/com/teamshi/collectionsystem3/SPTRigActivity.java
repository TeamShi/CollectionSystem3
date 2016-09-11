package com.teamshi.collectionsystem3;

import android.app.Application;
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
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.teamshi.collectionsystem3.datastructure.Project;
import com.teamshi.collectionsystem3.datastructure.SPTRig;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;

public class SPTRigActivity extends AppCompatActivity {
    private static final String TAG = "CollectionSystem3";
    private static final String[] ROCK_NAME_OPTIONS = {"黏性土", "砂类土", "粉土", "其它"};
    private static final HashMap<String, String> rockNameMap;

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

    private Spinner rockNameSpinner;
    private ArrayAdapter<String> rockNameSpinnerAdapter;
    private EditText rockColorEditText;
    private EditText rockDensityEditText;
    private EditText rockSaturationEditText;
    private TextView accumulatedHitCountTextView;

    private EditText otherDescriptionEditText;

    private Button rigViewTableButton;

    static {
        rockNameMap = new HashMap<>();
        rockNameMap.put("黏土", "黏性土");
        rockNameMap.put("粉质黏土", "黏性土");
        rockNameMap.put("粗砂", "砂类土");
        rockNameMap.put("中砂", "砂类土");
        rockNameMap.put("细砂", "砂类土");
        rockNameMap.put("粉土", "砂类土");
        rockNameMap.put("粉土", "粉土");
    }

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

        rockNameSpinner = (Spinner) findViewById(R.id.spinner_spt_rig_rock_name);
        rockNameSpinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, ROCK_NAME_OPTIONS);
        rockNameSpinner.setAdapter(rockNameSpinnerAdapter);

        rockColorEditText = (EditText) findViewById(R.id.edittext_spt_rig_rock_color);
        rockDensityEditText = (EditText) findViewById(R.id.edittext_spt_rig_rock_density);
        rockSaturationEditText = (EditText) findViewById(R.id.edittext_spt_rig_rock_density);

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
                }, rigStartTime.get(Calendar.HOUR_OF_DAY), rigStartTime.get(Calendar.MINUTE), true);
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
                    case "ACTION_COPY_RIG":
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

                            DataManager.getHole(holeId).setLastRigEndTime((Calendar) rigViewModel.getEndTime().clone());
                            DataManager.getHole(holeId).setLastAccumulatedMeterageLength(DataManager.getHole(holeId).getLastAccumulatedMeterageLength());
                            DataManager.getHole(holeId).setLastRockName(rigViewModel.getRockName());
                            DataManager.getHole(holeId).setLastRockColor(rigViewModel.getRockColor());
                            DataManager.getHole(holeId).setLastRockSaturation(rigViewModel.getRockSaturation());

                            Calendar now = Calendar.getInstance();
                            DataManager.getHole(holeId).setEndDate(now);

                            now.add(Calendar.DATE, 2);

                            DataManager.getHole(holeId).setReviewDate(now);

                            DataManager.getHole(holeId).setLastRigEndTime(rigViewModel.getEndTime());
                            DataManager.getHole(holeId).setActualDepth(rigViewModel.getAccumulatedMeterageLength());

                            DataManager.getHole(holeId).setRockCoreIndex(DataManager.getHole(holeId).getRockCoreIndex() + 1);

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

                        if (penetrationEndDepth < rigViewModel.getPenetrationStartDepth()) {
                            rigViewModel.setPenetrationEndDepth(penetrationEndDepth);
                            rigViewModel.setOtherDescription("");

                            rigViewModel.setCountStartDepth1(-1);
                            rigViewModel.setCountStartDepth2(-1);
                            rigViewModel.setCountStartDepth3(-1);

                            rigViewModel.setCountEndDepth1(-1);
                            rigViewModel.setCountEndDepth2(-1);
                            rigViewModel.setCountEndDepth3(-1);

                            rigViewModel.setDrillStartDepth1(-1);
                            rigViewModel.setDrillStartDepth2(-1);
                            rigViewModel.setDrillStartDepth3(-1);

                            rigViewModel.setDrillEndDepth1(-1);
                            rigViewModel.setDrillEndDepth2(-1);
                            rigViewModel.setDrillEndDepth3(-1);

                            rigViewModel.setHitCount1(-1);
                            rigViewModel.setHitCount2(-1);
                            rigViewModel.setHitCount3(-1);

                            rigViewModel.setAccumulatehHitCount(0);

                            rigViewModel.setRoundTripMeterageLength(penetrationEndDepth - rigViewModel.getPenetrationStartDepth());
                            rigViewModel.setAccumulatedMeterageLength(penetrationEndDepth);
                            rigViewModel.setDrillPipeRemainLength(2 - rigViewModel.getRoundTripMeterageLength());

                            penetrationEndDepthEditText.setTextColor(getResources().getColor(android.R.color.holo_red_light));
                            Toast.makeText(SPTRigActivity.this, "钻进深度至不得小于钻进深度自.", Toast.LENGTH_LONG).show();

                            refreshInfo();
                        } else if (penetrationEndDepth < rigViewModel.getPenetrationStartDepth() + 0.15) {
                            rigViewModel.setPenetrationEndDepth(penetrationEndDepth);
                            rigViewModel.setOtherDescription("贯入深度自 " + rigViewModel.getPenetrationStartDepth() + "m 至 " + Utility.formatDouble(rigViewModel.getPenetrationEndDepth()) + "m 反弹51击");

                            rigViewModel.setCountStartDepth1(-1);
                            rigViewModel.setCountStartDepth2(-1);
                            rigViewModel.setCountStartDepth3(-1);

                            rigViewModel.setCountEndDepth1(-1);
                            rigViewModel.setCountEndDepth2(-1);
                            rigViewModel.setCountEndDepth3(-1);

                            rigViewModel.setDrillStartDepth1(-1);
                            rigViewModel.setDrillStartDepth2(-1);
                            rigViewModel.setDrillStartDepth3(-1);

                            rigViewModel.setDrillEndDepth1(-1);
                            rigViewModel.setDrillEndDepth2(-1);
                            rigViewModel.setDrillEndDepth3(-1);

                            rigViewModel.setHitCount1(-1);
                            rigViewModel.setHitCount2(-1);
                            rigViewModel.setHitCount3(-1);

                            rigViewModel.setAccumulatehHitCount(51);

                            rigViewModel.setRoundTripMeterageLength(penetrationEndDepth - rigViewModel.getPenetrationStartDepth());
                            rigViewModel.setAccumulatedMeterageLength(penetrationEndDepth);
                            rigViewModel.setDrillPipeRemainLength(2 - rigViewModel.getRoundTripMeterageLength());

                            penetrationEndDepthEditText.setTextColor(getResources().getColor(android.R.color.black));

                            refreshInfo();
                        } else if (penetrationEndDepth <= rigViewModel.getPenetrationStartDepth() + 0.25) {
                            rigViewModel.setPenetrationEndDepth(penetrationEndDepth);
                            rigViewModel.setOtherDescription("");

                            rigViewModel.setCountStartDepth1(rigViewModel.getPenetrationStartDepth() + 0.15);
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

                            rigViewModel.setHitCount1(0);
                            rigViewModel.setHitCount2(-1);
                            rigViewModel.setHitCount3(-1);

                            rigViewModel.setRoundTripMeterageLength(penetrationEndDepth - rigViewModel.getPenetrationStartDepth());
                            rigViewModel.setAccumulatedMeterageLength(penetrationEndDepth);
                            rigViewModel.setDrillPipeRemainLength(2 - rigViewModel.getRoundTripMeterageLength());

                            rigViewModel.setAccumulatehHitCount(0);
                            penetrationEndDepthEditText.setTextColor(getResources().getColor(android.R.color.black));

                            refreshInfo();

                        } else if (penetrationEndDepth <= rigViewModel.getPenetrationStartDepth() + 0.35) {
                            rigViewModel.setPenetrationEndDepth(penetrationEndDepth);
                            rigViewModel.setOtherDescription("");

                            rigViewModel.setCountStartDepth1(rigViewModel.getPenetrationStartDepth() + 0.15);
                            rigViewModel.setCountStartDepth2(rigViewModel.getPenetrationStartDepth() + 0.25);
                            rigViewModel.setCountStartDepth3(-1);

                            rigViewModel.setCountEndDepth1(rigViewModel.getPenetrationStartDepth() + 0.25);
                            rigViewModel.setCountEndDepth2(penetrationEndDepth);
                            rigViewModel.setCountEndDepth3(-1);

                            rigViewModel.setDrillStartDepth1(rigViewModel.getPenetrationStartDepth());
                            rigViewModel.setDrillStartDepth2(rigViewModel.getPenetrationStartDepth() + 0.25);
                            rigViewModel.setDrillStartDepth3(-1);

                            rigViewModel.setDrillEndDepth1(rigViewModel.getPenetrationStartDepth() + 0.25);
                            rigViewModel.setDrillEndDepth2(penetrationEndDepth);
                            rigViewModel.setDrillEndDepth3(-1);

                            rigViewModel.setHitCount1(0);
                            rigViewModel.setHitCount2(0);
                            rigViewModel.setHitCount3(-1);

                            rigViewModel.setRoundTripMeterageLength(penetrationEndDepth - rigViewModel.getPenetrationStartDepth());
                            rigViewModel.setAccumulatedMeterageLength(penetrationEndDepth);
                            rigViewModel.setDrillPipeRemainLength(2 - rigViewModel.getRoundTripMeterageLength());

                            rigViewModel.setAccumulatehHitCount(0);
                            penetrationEndDepthEditText.setTextColor(getResources().getColor(android.R.color.black));

                            refreshInfo();

                        } else if (penetrationEndDepth <= rigViewModel.getPenetrationStartDepth() + 0.45) {
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

                            rigViewModel.setRoundTripMeterageLength(penetrationEndDepth - rigViewModel.getPenetrationStartDepth());
                            rigViewModel.setAccumulatedMeterageLength(penetrationEndDepth);
                            rigViewModel.setDrillPipeRemainLength(2 - rigViewModel.getRoundTripMeterageLength());

                            rigViewModel.setAccumulatehHitCount(0);
                            penetrationEndDepthEditText.setTextColor(getResources().getColor(android.R.color.black));

                            refreshInfo();
                        } else {
                            rigViewModel.setPenetrationEndDepth(penetrationEndDepth);
                            rigViewModel.setOtherDescription("");

                            rigViewModel.setCountStartDepth1(-1);
                            rigViewModel.setCountStartDepth2(-1);
                            rigViewModel.setCountStartDepth3(-1);

                            rigViewModel.setCountEndDepth1(-1);
                            rigViewModel.setCountEndDepth2(-1);
                            rigViewModel.setCountEndDepth3(-1);

                            rigViewModel.setDrillStartDepth1(-1);
                            rigViewModel.setDrillStartDepth2(-1);
                            rigViewModel.setDrillStartDepth3(-1);

                            rigViewModel.setDrillEndDepth1(-1);
                            rigViewModel.setDrillEndDepth2(-1);
                            rigViewModel.setDrillEndDepth3(-1);

                            rigViewModel.setHitCount1(-1);
                            rigViewModel.setHitCount2(-1);
                            rigViewModel.setHitCount3(-1);

                            rigViewModel.setRoundTripMeterageLength(penetrationEndDepth - rigViewModel.getPenetrationStartDepth());
                            rigViewModel.setAccumulatedMeterageLength(penetrationEndDepth);
                            rigViewModel.setDrillPipeRemainLength(2 - rigViewModel.getRoundTripMeterageLength());

                            rigViewModel.setAccumulatehHitCount(0);

                            penetrationEndDepthEditText.setTextColor(getResources().getColor(android.R.color.holo_red_light));
                            Toast.makeText(SPTRigActivity.this, "钻进深度至不得大于钻进深度自+0.45m.", Toast.LENGTH_LONG).show();

                            refreshInfo();
                        }
                    } catch (Exception e) {
                        penetrationEndDepthEditText.setTextColor(getResources().getColor(android.R.color.holo_red_light));
                    }
                }

            }
        });

        countEndDepth1EditText.addTextChangedListener(new TextWatcher() {
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
                        double countEndDepth1 = Double.parseDouble(s.toString());

                        if (countEndDepth1 <= rigViewModel.getPenetrationStartDepth() + 0.25 && countEndDepth1 > rigViewModel.getPenetrationStartDepth() + 0.15) {
                            rigViewModel.setPenetrationEndDepth(countEndDepth1);

                            rigViewModel.setCountStartDepth1(rigViewModel.getPenetrationStartDepth() + 0.15);
                            rigViewModel.setCountStartDepth2(-1);
                            rigViewModel.setCountStartDepth3(-1);

                            rigViewModel.setCountEndDepth1(countEndDepth1);
                            rigViewModel.setCountEndDepth2(-1);
                            rigViewModel.setCountEndDepth3(-1);

                            rigViewModel.setDrillStartDepth1(rigViewModel.getPenetrationStartDepth());
                            rigViewModel.setDrillStartDepth2(-1);
                            rigViewModel.setDrillStartDepth3(-1);

                            rigViewModel.setDrillEndDepth1(countEndDepth1);
                            rigViewModel.setDrillEndDepth2(-1);
                            rigViewModel.setDrillEndDepth3(-1);

                            rigViewModel.setHitCount1(0);
                            rigViewModel.setHitCount2(-1);
                            rigViewModel.setHitCount3(-1);

                            rigViewModel.setAccumulatehHitCount(0);

                            rigViewModel.setRoundTripMeterageLength(countEndDepth1 - rigViewModel.getPenetrationStartDepth());
                            rigViewModel.setAccumulatedMeterageLength(countEndDepth1);
                            rigViewModel.setDrillPipeRemainLength(2 - rigViewModel.getRoundTripMeterageLength());

                            countEndDepth1EditText.setTextColor(getResources().getColor(android.R.color.black));

                            refreshInfo();
                        } else {
                            Toast.makeText(SPTRigActivity.this, "计数深度至1的取值范围为0.15 < 贯入深度自 <= 0.25.", Toast.LENGTH_SHORT).show();
                            countEndDepth1EditText.setTextColor(getResources().getColor(android.R.color.holo_red_light));


                            rigViewModel.setCountStartDepth1(rigViewModel.getPenetrationStartDepth() + 0.15);
                            rigViewModel.setCountStartDepth2(-1);
                            rigViewModel.setCountStartDepth3(-1);

                            rigViewModel.setCountEndDepth2(-1);
                            rigViewModel.setCountEndDepth3(-1);

                            rigViewModel.setDrillStartDepth1(rigViewModel.getPenetrationStartDepth());
                            rigViewModel.setDrillStartDepth2(-1);
                            rigViewModel.setDrillStartDepth3(-1);

                            rigViewModel.setDrillEndDepth2(-1);
                            rigViewModel.setDrillEndDepth3(-1);

                            rigViewModel.setHitCount1(0);
                            rigViewModel.setHitCount2(-1);
                            rigViewModel.setHitCount3(-1);

                            rigViewModel.setAccumulatehHitCount(0);

                            refreshInfo();
                        }
                    } catch (Exception e) {
                        countEndDepth1EditText.setTextColor(getResources().getColor(android.R.color.holo_red_light));
                    }
                }
            }
        });

        countEndDepth2EditText.addTextChangedListener(new TextWatcher() {
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
                        double countEndDepth2 = Double.parseDouble(s.toString());

                        if (countEndDepth2 <= rigViewModel.getPenetrationStartDepth() + 0.35 && countEndDepth2 > rigViewModel.getPenetrationStartDepth() + 0.25) {
                            rigViewModel.setPenetrationEndDepth(countEndDepth2);

                            rigViewModel.setCountStartDepth1(rigViewModel.getPenetrationStartDepth() + 0.15);
                            rigViewModel.setCountStartDepth2(rigViewModel.getPenetrationStartDepth() + 0.25);
                            rigViewModel.setCountStartDepth3(-1);

                            rigViewModel.setCountEndDepth1(rigViewModel.getPenetrationStartDepth() + 0.25);
                            rigViewModel.setCountEndDepth2(countEndDepth2);
                            rigViewModel.setCountEndDepth3(-1);

                            rigViewModel.setDrillStartDepth1(rigViewModel.getPenetrationStartDepth());
                            rigViewModel.setDrillStartDepth2(rigViewModel.getPenetrationStartDepth() + 0.25);
                            rigViewModel.setDrillStartDepth3(-1);

                            rigViewModel.setDrillEndDepth1(rigViewModel.getPenetrationStartDepth() + 0.25);
                            rigViewModel.setDrillEndDepth2(countEndDepth2);
                            rigViewModel.setDrillEndDepth3(-1);

                            rigViewModel.setHitCount1(0);
                            rigViewModel.setHitCount2(0);
                            rigViewModel.setHitCount3(-1);

                            rigViewModel.setAccumulatehHitCount(0);

                            rigViewModel.setRoundTripMeterageLength(countEndDepth2 - rigViewModel.getPenetrationStartDepth());
                            rigViewModel.setAccumulatedMeterageLength(countEndDepth2);
                            rigViewModel.setDrillPipeRemainLength(2 - rigViewModel.getRoundTripMeterageLength());

                            countEndDepth2EditText.setTextColor(getResources().getColor(android.R.color.black));

                            refreshInfo();
                        } else {
                            Toast.makeText(SPTRigActivity.this, "计数深度至2的取值范围为0.25 < 贯入深度自 <= 0.35.", Toast.LENGTH_SHORT).show();
                            countEndDepth2EditText.setTextColor(getResources().getColor(android.R.color.holo_red_light));

                            rigViewModel.setCountStartDepth1(rigViewModel.getPenetrationStartDepth() + 0.15);
                            rigViewModel.setCountStartDepth2(rigViewModel.getPenetrationStartDepth() + 0.25);
                            rigViewModel.setCountStartDepth3(-1);

                            rigViewModel.setCountEndDepth1(rigViewModel.getPenetrationStartDepth() + 0.25);
                            rigViewModel.setCountEndDepth3(-1);

                            rigViewModel.setDrillStartDepth1(rigViewModel.getPenetrationStartDepth());
                            rigViewModel.setDrillStartDepth2(rigViewModel.getPenetrationStartDepth() + 0.25);
                            rigViewModel.setDrillStartDepth3(-1);

                            rigViewModel.setDrillEndDepth1(rigViewModel.getPenetrationStartDepth() + 0.25);
                            rigViewModel.setDrillEndDepth3(-1);

                            rigViewModel.setHitCount1(0);
                            rigViewModel.setHitCount2(0);
                            rigViewModel.setHitCount3(-1);

                            rigViewModel.setAccumulatehHitCount(0);

                        }
                    } catch (Exception e) {
                        countEndDepth2EditText.setTextColor(getResources().getColor(android.R.color.holo_red_light));
                    }
                }
            }
        });

        countEndDepth3EditText.addTextChangedListener(new TextWatcher() {
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
                        double countEndDepth3 = Double.parseDouble(s.toString());

                        if (countEndDepth3 <= rigViewModel.getPenetrationStartDepth() + 0.45 && countEndDepth3 > rigViewModel.getPenetrationStartDepth() + 0.35) {
                            rigViewModel.setPenetrationEndDepth(countEndDepth3);

                            rigViewModel.setCountStartDepth1(rigViewModel.getPenetrationStartDepth() + 0.15);
                            rigViewModel.setCountStartDepth2(rigViewModel.getPenetrationStartDepth() + 0.25);
                            rigViewModel.setCountStartDepth3(rigViewModel.getPenetrationStartDepth() + 0.35);

                            rigViewModel.setCountEndDepth1(rigViewModel.getPenetrationStartDepth() + 0.25);
                            rigViewModel.setCountEndDepth2(rigViewModel.getPenetrationStartDepth() + 0.35);
                            rigViewModel.setCountEndDepth3(countEndDepth3);

                            rigViewModel.setDrillStartDepth1(rigViewModel.getPenetrationStartDepth());
                            rigViewModel.setDrillStartDepth2(rigViewModel.getPenetrationStartDepth() + 0.25);
                            rigViewModel.setDrillStartDepth3(rigViewModel.getPenetrationStartDepth() + 0.35);

                            rigViewModel.setDrillEndDepth1(rigViewModel.getPenetrationStartDepth() + 0.25);
                            rigViewModel.setDrillEndDepth2(rigViewModel.getPenetrationStartDepth() + 0.35);
                            rigViewModel.setDrillEndDepth3(countEndDepth3);

                            rigViewModel.setHitCount1(0);
                            rigViewModel.setHitCount2(0);
                            rigViewModel.setHitCount3(0);

                            rigViewModel.setAccumulatehHitCount(0);

                            rigViewModel.setRoundTripMeterageLength(countEndDepth3 - rigViewModel.getPenetrationStartDepth());
                            rigViewModel.setAccumulatedMeterageLength(countEndDepth3);
                            rigViewModel.setDrillPipeRemainLength(2 - rigViewModel.getRoundTripMeterageLength());

                            countEndDepth3EditText.setTextColor(getResources().getColor(android.R.color.black));

                            refreshInfo();
                        } else {
                            Toast.makeText(SPTRigActivity.this, "计数深度至3的取值范围为0.35 < 贯入深度自 <= 0.45.", Toast.LENGTH_SHORT).show();
                            countEndDepth3EditText.setTextColor(getResources().getColor(android.R.color.holo_red_light));


                            rigViewModel.setCountStartDepth1(rigViewModel.getPenetrationStartDepth() + 0.15);
                            rigViewModel.setCountStartDepth2(rigViewModel.getPenetrationStartDepth() + 0.25);
                            rigViewModel.setCountStartDepth3(rigViewModel.getPenetrationStartDepth() + 0.35);

                            rigViewModel.setCountEndDepth1(rigViewModel.getPenetrationStartDepth() + 0.25);
                            rigViewModel.setCountEndDepth2(rigViewModel.getPenetrationStartDepth() + 0.35);

                            rigViewModel.setDrillStartDepth1(rigViewModel.getPenetrationStartDepth());
                            rigViewModel.setDrillStartDepth2(rigViewModel.getPenetrationStartDepth() + 0.25);

                            rigViewModel.setDrillEndDepth1(rigViewModel.getPenetrationStartDepth() + 0.25);
                            rigViewModel.setDrillEndDepth2(rigViewModel.getPenetrationStartDepth() + 0.35);

                            rigViewModel.setHitCount1(0);
                            rigViewModel.setHitCount2(0);
                            rigViewModel.setHitCount3(0);

                            rigViewModel.setAccumulatehHitCount(0);
                        }
                    } catch (Exception e) {
                        countEndDepth3EditText.setTextColor(getResources().getColor(android.R.color.holo_red_light));
                    }
                }

            }
        });

        hitCount1EditText.addTextChangedListener(new TextWatcher() {
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
                        int hitCount1 = Integer.parseInt(s.toString());

                        rigViewModel.setHitCount1(hitCount1);
                        int totalHitCount = hitCount1;
                        if (rigViewModel.getHitCount2() != -1) {
                            totalHitCount += rigViewModel.getHitCount2();
                        }

                        if (rigViewModel.getHitCount3() != -1) {
                            totalHitCount += rigViewModel.getHitCount3();
                        }

                        rigViewModel.setAccumulatehHitCount(totalHitCount);

                        if (totalHitCount > 50) {
                            hitCount1EditText.setTextColor(getResources().getColor(android.R.color.holo_red_light));
                            hitCount2EditText.setTextColor(getResources().getColor(android.R.color.holo_red_light));
                            hitCount3EditText.setTextColor(getResources().getColor(android.R.color.holo_red_light));
                            accumulatedHitCountTextView.setTextColor(getResources().getColor(android.R.color.holo_red_light));
                            Toast.makeText(SPTRigActivity.this, "累计击数超过50.", Toast.LENGTH_SHORT).show();
                        } else {
                            hitCount1EditText.setTextColor(getResources().getColor(android.R.color.black));
                            hitCount2EditText.setTextColor(getResources().getColor(android.R.color.black));
                            hitCount3EditText.setTextColor(getResources().getColor(android.R.color.black));
                            accumulatedHitCountTextView.setTextColor(getResources().getColor(android.R.color.black));
                        }

                        rigViewModel.setRockDensity(ConfigurationManager.parseSPTSaturationDescription(rigViewModel.getRockName(), rigViewModel.getAccumulatehHitCount()));

                        refreshInfo();
                    } catch (Exception e) {
                        hitCount1EditText.setTextColor(getResources().getColor(android.R.color.holo_red_light));
                    }

                }
            }
        });

        hitCount2EditText.addTextChangedListener(new TextWatcher() {
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
                        int hitCount2 = Integer.parseInt(s.toString());

                        rigViewModel.setHitCount2(hitCount2);
                        int totalHitCount = hitCount2;
                        if (rigViewModel.getHitCount1() != -1) {
                            totalHitCount += rigViewModel.getHitCount1();
                        }

                        if (rigViewModel.getHitCount3() != -1) {
                            totalHitCount += rigViewModel.getHitCount3();
                        }

                        rigViewModel.setAccumulatehHitCount(totalHitCount);

                        if (totalHitCount > 50) {
                            hitCount1EditText.setTextColor(getResources().getColor(android.R.color.holo_red_light));
                            hitCount2EditText.setTextColor(getResources().getColor(android.R.color.holo_red_light));
                            hitCount3EditText.setTextColor(getResources().getColor(android.R.color.holo_red_light));
                            accumulatedHitCountTextView.setTextColor(getResources().getColor(android.R.color.holo_red_light));
                            Toast.makeText(SPTRigActivity.this, "累计击数超过50.", Toast.LENGTH_SHORT).show();
                        } else {
                            hitCount1EditText.setTextColor(getResources().getColor(android.R.color.black));
                            hitCount2EditText.setTextColor(getResources().getColor(android.R.color.black));
                            hitCount3EditText.setTextColor(getResources().getColor(android.R.color.black));
                            accumulatedHitCountTextView.setTextColor(getResources().getColor(android.R.color.black));
                        }

                        rigViewModel.setRockDensity(ConfigurationManager.parseSPTSaturationDescription(rigViewModel.getRockName(), rigViewModel.getAccumulatehHitCount()));

                        refreshInfo();
                    } catch (Exception e) {
                        hitCount2EditText.setTextColor(getResources().getColor(android.R.color.holo_red_light));
                    }

                }
            }
        });

        hitCount3EditText.addTextChangedListener(new TextWatcher() {
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
                        int hitCount3 = Integer.parseInt(s.toString());

                        rigViewModel.setHitCount3(hitCount3);
                        int totalHitCount = hitCount3;
                        if (rigViewModel.getHitCount2() != -1) {
                            totalHitCount += rigViewModel.getHitCount2();
                        }

                        if (rigViewModel.getHitCount1() != -1) {
                            totalHitCount += rigViewModel.getHitCount1();
                        }

                        rigViewModel.setAccumulatehHitCount(totalHitCount);

                        if (totalHitCount > 50) {
                            hitCount1EditText.setTextColor(getResources().getColor(android.R.color.holo_red_light));
                            hitCount2EditText.setTextColor(getResources().getColor(android.R.color.holo_red_light));
                            hitCount3EditText.setTextColor(getResources().getColor(android.R.color.holo_red_light));
                            accumulatedHitCountTextView.setTextColor(getResources().getColor(android.R.color.holo_red_light));
                            Toast.makeText(SPTRigActivity.this, "累计击数超过50.", Toast.LENGTH_SHORT).show();
                        } else {
                            hitCount1EditText.setTextColor(getResources().getColor(android.R.color.black));
                            hitCount2EditText.setTextColor(getResources().getColor(android.R.color.black));
                            hitCount3EditText.setTextColor(getResources().getColor(android.R.color.black));
                            accumulatedHitCountTextView.setTextColor(getResources().getColor(android.R.color.black));
                        }

                        rigViewModel.setRockDensity(ConfigurationManager.parseSPTSaturationDescription(rigViewModel.getRockName(), rigViewModel.getAccumulatehHitCount()));

                        refreshInfo();
                    } catch (Exception e) {
                        hitCount3EditText.setTextColor(getResources().getColor(android.R.color.holo_red_light));
                    }

                }
            }
        });

        rockNameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                rigViewModel.setRockName(ROCK_NAME_OPTIONS[position]);

                rigViewModel.setRockDensity(ConfigurationManager.parseSPTSaturationDescription(rigViewModel.getRockName(), rigViewModel.getAccumulatehHitCount()));

                refreshInfo();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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
                Project project = DataManager.getProject();
                PreviewActivity.setUrls(IOManager.previewSPTRig(rigViewModel));
                Intent intent = new Intent(SPTRigActivity.this, PreviewActivity.class);
                intent.putExtra("projectName", project.getProjectName());
                startActivity(intent);
            }
        });

        otherDescriptionEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!refreshLock) {
                    rigViewModel.setOtherDescription(s.toString());
                }
            }
        });

        String requestCode = getIntent().getStringExtra("requestCode");

        holeId = getIntent().getStringExtra("holeId");

        switch (requestCode) {
            case "ACTION_ADD_RIG":
                Calendar startTime = (Calendar) DataManager.getHole(holeId).getLastRigEndTime().clone();
                Calendar endTime = (Calendar) DataManager.getHole(holeId).getLastRigEndTime().clone();
                endTime.add(Calendar.MINUTE, 1);

                String rockName = rockNameMap.containsKey(DataManager.getHole(holeId).getLastRockName())?rockNameMap.get(DataManager.getHole(holeId).getLastRockName()): "其它";

                rigViewModel = new SPTRig(DataManager.getHole(holeId).getLastClassPeopleCount(), startTime, startTime, endTime,
                        DataManager.getHole(holeId).getLastAccumulatedMeterageLength() + 2, 1.55, 0.45, DataManager.getHole(holeId).getLastAccumulatedMeterageLength() + 0.45,
                        51, 0.5,
                        "管靴",51, 0.05,
                        DataManager.getHole(holeId).getLastAccumulatedMeterageLength(), DataManager.getHole(holeId).getLastAccumulatedMeterageLength() + 0.45,
                        0, 0, 0,
                        DataManager.getHole(holeId).getLastAccumulatedMeterageLength() + 0.15, DataManager.getHole(holeId).getLastAccumulatedMeterageLength() + 0.25,
                        DataManager.getHole(holeId).getLastAccumulatedMeterageLength() + 0.25, DataManager.getHole(holeId).getLastAccumulatedMeterageLength() + 0.35,
                        DataManager.getHole(holeId).getLastAccumulatedMeterageLength() + 0.35, DataManager.getHole(holeId).getLastAccumulatedMeterageLength() + 0.45,
                        DataManager.getHole(holeId).getLastAccumulatedMeterageLength(), DataManager.getHole(holeId).getLastAccumulatedMeterageLength() + 0.25,
                        DataManager.getHole(holeId).getLastAccumulatedMeterageLength() + 0.25, DataManager.getHole(holeId).getLastAccumulatedMeterageLength() + 0.35,
                        DataManager.getHole(holeId).getLastAccumulatedMeterageLength() + 0.35, DataManager.getHole(holeId).getLastAccumulatedMeterageLength() + 0.45,
                        rockName,
                        DataManager.getHole(holeId).getLastRockColor(),
                        DataManager.getHole(holeId).getLastRockSaturation(),
                        ConfigurationManager.parseSPTSaturationDescription(rockName, 0),
                        ""
                );

                refreshInfo();
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
        if (refreshLock) {
            return;
        }

        refreshLock = true;

        String requestCode = getIntent().getStringExtra("requestCode");

        if (getCurrentFocus() != classPeopleCountEditText) {
            classPeopleCountEditText.setText(rigViewModel.getClassPeopleCount());
        }

        dateButton.setText(Utility.formatCalendarDateString(rigViewModel.getDate()));
        startTimeButton.setText(Utility.formatTimeString(rigViewModel.getStartTime()));
        endTimeButton.setText(Utility.formatTimeString(rigViewModel.getEndTime()));
        timeDurationTextView.setText(Utility.calculateTimeSpan(rigViewModel.getStartTime(), rigViewModel.getEndTime()));

        injectionToolDialameterTextView.setText(String.valueOf(rigViewModel.getInjectionToolDiameter()));
        injectionToolLengthTextView.setText(Utility.formatDouble(rigViewModel.getInjectionToolLength()));

        probeTypeEditText.setText(rigViewModel.getProbeType());
        probeDiameterEditText.setText(String.valueOf(rigViewModel.getProbeDiameter()));
        probeLengthEditText.setText(Utility.formatDouble(rigViewModel.getProbeLength()));

        drillToolTotalLengthTextView.setText(Utility.formatDouble(rigViewModel.getDrillToolTotalLength()));

        if (getCurrentFocus() != drillPipeRemainLengthEditText) {
            drillPipeRemainLengthEditText.setText(Utility.formatDouble(rigViewModel.getDrillPipeRemainLength()));
        }

        roundTripMeterageLengthTextView.setText(Utility.formatDouble(rigViewModel.getRoundTripMeterageLength()));
        accumulatedMeterageLengthTextView.setText(Utility.formatDouble(rigViewModel.getAccumulatedMeterageLength()));

        if (getCurrentFocus() != penetrationStartDepthEditText) {
            penetrationStartDepthEditText.setText(Utility.formatDouble(rigViewModel.getPenetrationStartDepth()));
        }

        if (getCurrentFocus() != penetrationEndDepthEditText) {
            penetrationEndDepthEditText.setText(Utility.formatDouble(rigViewModel.getPenetrationEndDepth()));
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
            countStartDepth1EditText.setText(rigViewModel.getCountStartDepth1() < 0? "": Utility.formatDouble(rigViewModel.getCountStartDepth1()));
        }

        if (getCurrentFocus() != countEndDepth1EditText) {
            countEndDepth1EditText.setText(rigViewModel.getCountEndDepth1() < 0? "": Utility.formatDouble(rigViewModel.getCountEndDepth1()));
            countEndDepth1EditText.setEnabled(rigViewModel.getCountEndDepth1() >= 0 && rigViewModel.getHitCount1() != 51);
        }

        if (getCurrentFocus() != countStartDepth2EditText) {
            countStartDepth2EditText.setText(rigViewModel.getCountStartDepth2() < 0? "": Utility.formatDouble(rigViewModel.getCountStartDepth2()));
        }

        if (getCurrentFocus() != countEndDepth2EditText) {
            countEndDepth2EditText.setText(rigViewModel.getCountEndDepth2() < 0? "": Utility.formatDouble(rigViewModel.getCountEndDepth2()));
            countEndDepth2EditText.setEnabled(rigViewModel.getCountEndDepth2() >= 0);
        }

        if (getCurrentFocus() != countStartDepth3EditText) {
            countStartDepth3EditText.setText(rigViewModel.getCountStartDepth3() < 0? "": Utility.formatDouble(rigViewModel.getCountStartDepth3()));
        }

        if (getCurrentFocus() != countEndDepth3EditText) {
            countEndDepth3EditText.setText(rigViewModel.getCountEndDepth3() < 0? "": Utility.formatDouble(rigViewModel.getCountEndDepth3()));
            countEndDepth3EditText.setEnabled(rigViewModel.getCountEndDepth3() >= 0);
        }

        if (getCurrentFocus() != drillStartDepth1EditText) {
            drillStartDepth1EditText.setText(rigViewModel.getDrillStartDepth1() < 0? "": Utility.formatDouble(rigViewModel.getDrillStartDepth1()));
        }

        if (getCurrentFocus() != drillEndDepth1EditText) {
            drillEndDepth1EditText.setText(rigViewModel.getDrillEndDepth1() < 0? "": Utility.formatDouble(rigViewModel.getDrillEndDepth1()));
        }

        if (getCurrentFocus() != drillStartDepth2EditText) {
            drillStartDepth2EditText.setText(rigViewModel.getDrillStartDepth2() < 0? "": Utility.formatDouble(rigViewModel.getDrillStartDepth2()));
        }

        if (getCurrentFocus() != drillEndDepth2EditText) {
            drillEndDepth2EditText.setText(rigViewModel.getDrillEndDepth2() < 0? "": Utility.formatDouble(rigViewModel.getDrillEndDepth2()));
        }

        if (getCurrentFocus() != drillStartDepth3EditText) {
            drillStartDepth3EditText.setText(rigViewModel.getDrillStartDepth3() < 0? "": Utility.formatDouble(rigViewModel.getDrillStartDepth3()));
        }

        if (getCurrentFocus() != drillEndDepth3EditText) {
            drillEndDepth3EditText.setText(rigViewModel.getDrillEndDepth3() < 0? "": Utility.formatDouble(rigViewModel.getDrillEndDepth3()));
        }

        if (getCurrentFocus() != otherDescriptionEditText) {
            otherDescriptionEditText.setText(rigViewModel.getOtherDescription());
        }

        accumulatedHitCountTextView.setText(rigViewModel.getAccumulatehHitCount() < 0? "": String.valueOf(rigViewModel.getAccumulatehHitCount()));

        for (int i = 0; i < ROCK_NAME_OPTIONS.length; i++) {
            if (rigViewModel.getRockName().equals(ROCK_NAME_OPTIONS[i])) {
                rockNameSpinner.setSelection(i);
                break;
            }
        }

        if (getCurrentFocus() != rockColorEditText) {
            rockColorEditText.setText(rigViewModel.getRockColor());
        }

        if (getCurrentFocus() != rockDensityEditText) {
            rockDensityEditText.setText(rigViewModel.getRockDensity());
        }

        if (getCurrentFocus() != rockSaturationEditText) {
            rockSaturationEditText.setText(rigViewModel.getRockSaturation());
        }

        if (requestCode.equals("ACTION_EDIT_RIG")) {
            classPeopleCountEditText.setEnabled(false);
            dateButton.setEnabled(false);
            startTimeButton.setEnabled(false);
            endTimeButton.setEnabled(false);
            probeTypeEditText.setEnabled(false);
            probeLengthEditText.setEnabled(false);
            probeDiameterEditText.setEnabled(false);
            penetrationEndDepthEditText.setEnabled(false);
            countEndDepth1EditText.setEnabled(false);
            countEndDepth2EditText.setEnabled(false);
            countEndDepth3EditText.setEnabled(false);
        }

        if (DataManager.getHole(holeId).isApproved()) {
            classPeopleCountEditText.setEnabled(false);
            dateButton.setEnabled(false);
            startTimeButton.setEnabled(false);
            endTimeButton.setEnabled(false);
            probeTypeEditText.setEnabled(false);
            drillPipeRemainLengthEditText.setEnabled(false);
            penetrationEndDepthEditText.setEnabled(false);
            countEndDepth1EditText.setEnabled(false);
            countEndDepth2EditText.setEnabled(false);
            countEndDepth3EditText.setEnabled(false);
            hitCount1EditText.setEnabled(false);
            hitCount2EditText.setEnabled(false);
            hitCount3EditText.setEnabled(false);
            rockNameSpinner.setEnabled(false);
            rockColorEditText.setEnabled(false);
            rockDensityEditText.setEnabled(false);
            rockSaturationEditText.setEnabled(false);
            otherDescriptionEditText.setEnabled(false);
        }

        refreshLock = false;
    }

    private boolean validate() {
        String requestCode = getIntent().getStringExtra("requestCode");

        if (requestCode.equals("ACTION_ADD_RIG")) {
            if (!Utility.validateStartEndTime(rigViewModel.getStartTime(), rigViewModel.getEndTime())) {
                Toast.makeText(SPTRigActivity.this, "开始时间不得大于等于结束时间", Toast.LENGTH_LONG).show();
                return false;
            }

            if (rigViewModel.getProbeDiameter() == 0) {
                Toast.makeText(SPTRigActivity.this, "探头直径不能为0.", Toast.LENGTH_LONG).show();
                return false;
            }

            if (rigViewModel.getProbeDiameter() == 0) {
                Toast.makeText(SPTRigActivity.this, "探头长度不能为0.", Toast.LENGTH_LONG).show();
                return false;
            }

            if (penetrationEndDepthEditText.getCurrentTextColor() == getResources().getColor(android.R.color.holo_red_light)) {
                Toast.makeText(SPTRigActivity.this, "贯入深度至数值非法.", Toast.LENGTH_LONG).show();
                return false;
            }

            if (countEndDepth1EditText.getCurrentTextColor() == getResources().getColor(android.R.color.holo_red_light)) {
                Toast.makeText(SPTRigActivity.this, "计数深度1至数值非法.", Toast.LENGTH_LONG).show();
                return false;
            }

            if (countEndDepth2EditText.getCurrentTextColor() == getResources().getColor(android.R.color.holo_red_light)) {
                Toast.makeText(SPTRigActivity.this, "计数深度2至数值非法.", Toast.LENGTH_LONG).show();
                return false;
            }

            if (countEndDepth3EditText.getCurrentTextColor() == getResources().getColor(android.R.color.holo_red_light)) {
                Toast.makeText(SPTRigActivity.this, "计数深度3至数值非法.", Toast.LENGTH_LONG).show();
                return false;
            }

            if (rigViewModel.getAccumulatehHitCount() > 51) {
                Toast.makeText(SPTRigActivity.this, "累计击数大于51.", Toast.LENGTH_LONG).show();
                return false;
            }

            return true;
        } else {
            if (rigViewModel.getAccumulatehHitCount() > 51) {
                Toast.makeText(SPTRigActivity.this, "累计击数大于51.", Toast.LENGTH_LONG).show();
                return false;
            }

            return true;
        }
    }
}

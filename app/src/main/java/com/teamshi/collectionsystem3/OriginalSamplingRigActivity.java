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
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.teamshi.collectionsystem3.datastructure.Hole;
import com.teamshi.collectionsystem3.datastructure.OriginalSamplingRig;
import com.teamshi.collectionsystem3.datastructure.Project;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class OriginalSamplingRigActivity extends AppCompatActivity {
    private static final String TAG = "CollectionSystem3";

    private static final String [] SAMPLER_PIPE_DIAMETER_OPTIONS = {"108", "89"};
    private static final String [] SAMPLER_DRILL_DIAMETER_OPTIONS = {"110", "91"};
    private static final String [] SAMPLER_TYPE_OPTIONS = {"厚壁", "薄壁"};

    private boolean refreshLock = false;

    private OriginalSamplingRig rigViewModel;
    private Button confirmAddRigButton;
    private Button cancelAddRigButton;

    private String holeId;

    private EditText classPeopleCountEditText;
    private TextView dateButton;
    private TextView startTimeButton;
    private TextView endTimeButton;
    private TextView timeDurationTextView;

    private Spinner pipeDiameterSpinner;
    private ArrayAdapter<String> pipeDiameterSpinnerAdapter;

    private EditText pipeLengthEditText;

    private EditText drillTypeEditText;
    private Spinner drillDiameterSpinner;
    private ArrayAdapter<String> drillDiameterSpinnerAdapter;

    private EditText drillLengthEditText;

    private TextView drillToolTotalLengthTextView;
    private TextView drillPipeRemainLengthTextView;
    private TextView roundTripMeterageLengthTextView;
    private TextView accumulatedMeterageLengthTextView;

    private Spinner samplerTypeSpinner;
    private ArrayAdapter<String> samplerTypeSpinnerAdapter;

    private EditText samplerIndexEditText;
    private EditText startLengthEditText;
    private EditText endLengthEditText;
    private EditText countEditText;
    private Button previewButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "Start OriginalSamplingRigActivity.");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_original_sampling_rig);

        this.setTitle("原状信息表");

        confirmAddRigButton = (Button) findViewById(R.id.button_confirm_add_original_sampling_rig);
        cancelAddRigButton = (Button) findViewById(R.id.button_cancel_add_original_sampling_rig);

        classPeopleCountEditText = (EditText) findViewById(R.id.edittext_original_sampling_rig_class_people_count);
        dateButton = (Button) findViewById(R.id.button_original_sampling_rig_date);
        startTimeButton = (Button) findViewById(R.id.button_original_sampling_rig_start_time);
        endTimeButton = (Button) findViewById(R.id.button_original_sampling_rig_end_time);
        timeDurationTextView = (TextView) findViewById(R.id.textview_original_sampling_rig_duration);

        pipeDiameterSpinner = (Spinner) findViewById(R.id.spinner_original_sampling_pipe_diameter);
        pipeDiameterSpinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, SAMPLER_PIPE_DIAMETER_OPTIONS);
        pipeDiameterSpinner.setAdapter(pipeDiameterSpinnerAdapter);

        pipeLengthEditText = (EditText) findViewById(R.id.edittext_original_sampling_rig_pipe_length);

        drillTypeEditText = (EditText) findViewById(R.id.edittext_original_sampling_rig_drill_type);

        drillDiameterSpinner = (Spinner) findViewById(R.id.spinner_original_sampling_rig_drill_diameter);
        drillDiameterSpinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, SAMPLER_DRILL_DIAMETER_OPTIONS);
        drillDiameterSpinner.setAdapter(drillDiameterSpinnerAdapter);

        drillLengthEditText = (EditText) findViewById(R.id.edittext_original_sampling_rig_drill_length);

        drillToolTotalLengthTextView = (TextView) findViewById(R.id.textview_original_sampling_rig_drill_tool_total_length);
        drillPipeRemainLengthTextView = (TextView) findViewById(R.id.textView_original_sampling_rig_drill_pipe_remain_length);
        roundTripMeterageLengthTextView = (TextView) findViewById(R.id.textview_original_sampling_rig_round_trip_meterage_length);
        accumulatedMeterageLengthTextView = (TextView) findViewById(R.id.textview_original_sampling_rig_accumulated_meterage_length);

        samplerTypeSpinner = (Spinner) findViewById(R.id.spinner_original_sampling_sampler_type);
        samplerTypeSpinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, SAMPLER_TYPE_OPTIONS);
        samplerTypeSpinner.setAdapter(samplerTypeSpinnerAdapter);

        samplerIndexEditText = (EditText) findViewById(R.id.edittext_original_sampling_rig_sampler_index);
        startLengthEditText = (EditText) findViewById(R.id.edittext_original_sampling_rig_start_depth);
        endLengthEditText = (EditText) findViewById(R.id.edittext_original_sampling_rig_end_depth);
        countEditText = (EditText) findViewById(R.id.edittext_original_sampling_rig_count);

        previewButton = (Button) findViewById(R.id.button_original_sampling_rig_preview);

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
                DatePickerDialog dialog = new DatePickerDialog(OriginalSamplingRigActivity.this, new DatePickerDialog.OnDateSetListener() {
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

                TimePickerDialog dialog = new TimePickerDialog(OriginalSamplingRigActivity.this, new TimePickerDialog.OnTimeSetListener() {
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

                TimePickerDialog dialog = new TimePickerDialog(OriginalSamplingRigActivity.this, new TimePickerDialog.OnTimeSetListener() {
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

        pipeDiameterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!refreshLock) {
                    rigViewModel.setSamplerPipeDiameter(Integer.valueOf(SAMPLER_PIPE_DIAMETER_OPTIONS[position]));

                    rigViewModel.setSamplerDrillDiameter(Integer.valueOf(SAMPLER_DRILL_DIAMETER_OPTIONS[position]));

                    if (position == 0) {
                        rigViewModel.setSamplerPipeLength(0.8);
                    } else if (position == 1) {
                        rigViewModel.setSamplerPipeLength(0.9);
                    }

                    refreshInfo();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        pipeLengthEditText.addTextChangedListener(new TextWatcher() {
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
                        rigViewModel.setSamplerPipeLength(Double.valueOf(s.toString()));
                        pipeLengthEditText.setTextColor(getResources().getColor(android.R.color.black));
                    } catch (Exception e) {
                        pipeLengthEditText.setTextColor(getResources().getColor(android.R.color.holo_red_light));
                    }

                }
            }
        });

        drillTypeEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!refreshLock) {
                    rigViewModel.setSamplerDrillType(s.toString());
                }
            }
        });

        drillDiameterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                rigViewModel.setSamplerDrillDiameter(Integer.parseInt(SAMPLER_DRILL_DIAMETER_OPTIONS[position]));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        drillLengthEditText.addTextChangedListener(new TextWatcher() {
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
                        rigViewModel.setSamplerDrillLength(Double.valueOf(s.toString()));
                        drillLengthEditText.setTextColor(getResources().getColor(android.R.color.black));
                    } catch (Exception e) {
                        drillLengthEditText.setTextColor(getResources().getColor(android.R.color.holo_red_light));
                    }

                }
            }
        });

        samplerTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!refreshLock) {
                    rigViewModel.setSamplerType(SAMPLER_TYPE_OPTIONS[position]);

                    if (position == 0) {
                        rigViewModel.setEndDepth(DataManager.getHole(holeId).getLastAccumulatedMeterageLength() + 0.3);
                        rigViewModel.setRoundTripMeterageLength(0.4);
                        rigViewModel.setAccumulatedMeterageLength(DataManager.getHole(holeId).getLastAccumulatedMeterageLength() + 0.4);
                        rigViewModel.setDrillPipeRemainLength(1.6);
                    } else if (position == 1) {
                        rigViewModel.setEndDepth(DataManager.getHole(holeId).getLastAccumulatedMeterageLength() + 0.4);
                        rigViewModel.setRoundTripMeterageLength(0.5);
                        rigViewModel.setAccumulatedMeterageLength(DataManager.getHole(holeId).getLastAccumulatedMeterageLength() + 0.5);
                        rigViewModel.setDrillPipeRemainLength(1.5);
                    }

                    refreshInfo();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        samplerIndexEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                rigViewModel.setIndex(s.toString());
            }
        });

        startLengthEditText.addTextChangedListener(new TextWatcher() {
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
                        rigViewModel.setStartDepth(Double.valueOf(s.toString()));
                        startLengthEditText.setTextColor(getResources().getColor(android.R.color.black));
                    } catch (Exception e) {
                        startLengthEditText.setTextColor(getResources().getColor(android.R.color.holo_red_light));
                    }

                }
            }
        });

        endLengthEditText.addTextChangedListener(new TextWatcher() {
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
                        rigViewModel.setEndDepth(Double.valueOf(s.toString()));
                        endLengthEditText.setTextColor(getResources().getColor(android.R.color.black));

                        rigViewModel.setAccumulatedMeterageLength(rigViewModel.getEndDepth() + 0.1);
                        rigViewModel.setDrillPipeRemainLength(rigViewModel.getDrillToolTotalLength() - rigViewModel.getAccumulatedMeterageLength());

                        refreshInfo();
                    } catch (Exception e) {
                        endLengthEditText.setTextColor(getResources().getColor(android.R.color.holo_red_light));
                    }

                }
            }
        });

        countEditText.addTextChangedListener(new TextWatcher() {
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
                        rigViewModel.setCount(Integer.valueOf(s.toString()));
                        countEditText.setTextColor(getResources().getColor(android.R.color.black));
                    } catch (Exception e) {
                        countEditText.setTextColor(getResources().getColor(android.R.color.holo_red_light));
                    }

                }
            }
        });

        previewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Project project = DataManager.getProject();
                Hole hole = DataManager.getHole(holeId);
                PreviewActivity.setUrls(IOManager.previewOriginalSamplingRig(hole, rigViewModel));
                Intent intent = new Intent(OriginalSamplingRigActivity.this, PreviewActivity.class);
                intent.putExtra("projectName", project.getProjectName());
                startActivity(intent);
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

                            Calendar now = Calendar.getInstance();
                            DataManager.getHole(holeId).setEndDate(now);

                            now.add(Calendar.DATE, 2);

                            DataManager.getHole(holeId).setReviewDate(now);

                            DataManager.getHole(holeId).setLastRigEndTime(rigViewModel.getEndTime());
                            DataManager.getHole(holeId).setActualDepth(rigViewModel.getAccumulatedMeterageLength());

                            DataManager.getHole(holeId).setOriginalSampleIndex(DataManager.getHole(holeId).getOriginalSampleIndex() + 1);

                            IOManager.updateProject(DataManager.getProject());
                            OriginalSamplingRigActivity.this.setResult(RESULT_OK);
                            OriginalSamplingRigActivity.this.finish();
                        }
                        break;
                    case "ACTION_EDIT_RIG":
                        if (validate()) {
                            String holeId = getIntent().getStringExtra("holeId");
                            int rigIndex = getIntent().getIntExtra("rigIndex", 0);

                            DataManager.updateRig(holeId, rigIndex, rigViewModel);

                            IOManager.updateProject(DataManager.getProject());
                            OriginalSamplingRigActivity.this.setResult(RESULT_OK);
                            OriginalSamplingRigActivity.this.finish();
                        }
                        break;
                }
            }
        });

        cancelAddRigButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OriginalSamplingRigActivity.this.setResult(RESULT_CANCELED);
                OriginalSamplingRigActivity.this.finish();
            }
        });

        String requestCode = getIntent().getStringExtra("requestCode");

        holeId = getIntent().getStringExtra("holeId");

        switch (requestCode) {
            case "ACTION_ADD_RIG":
                Calendar startTime = (Calendar) DataManager.getHole(holeId).getLastRigEndTime().clone();
                Calendar endTime = (Calendar) DataManager.getHole(holeId).getLastRigEndTime().clone();
                endTime.add(Calendar.MINUTE, 1);

                rigViewModel = new OriginalSamplingRig(DataManager.getHole(holeId).getLastClassPeopleCount(), startTime, startTime, endTime,
                        DataManager.getHole(holeId).getLastAccumulatedMeterageLength() + 2,
                        1.6,
                        0.4,
                        DataManager.getHole(holeId).getLastAccumulatedMeterageLength() + 0.4,
                        89, 0.9,
                        "管靴", 91, 0,
                        "原" + DataManager.getHole(holeId).getOriginalSampleIndex(), "厚壁",
                        DataManager.getHole(holeId).getLastAccumulatedMeterageLength() + 0.1,
                        DataManager.getHole(holeId).getLastAccumulatedMeterageLength() + 0.3,
                        1);

                refreshInfo();
                break;
            case "ACTION_EDIT_RIG":
                String holeId = getIntent().getStringExtra("holeId");
                int rigIndex = getIntent().getIntExtra("rigIndex", 0);

                rigViewModel = (OriginalSamplingRig) DataManager.getRig(holeId, rigIndex).deepCopy();

                refreshInfo();
                break;
        }
    }

    private void refreshInfo() {
        if (refreshLock) {
            return;
        }

        refreshLock = true;

        if (getCurrentFocus() != classPeopleCountEditText) {
            classPeopleCountEditText.setText(rigViewModel.getClassPeopleCount());
        }

        dateButton.setText(Utility.formatCalendarDateString(rigViewModel.getDate()));
        startTimeButton.setText(Utility.formatTimeString(rigViewModel.getStartTime()));
        endTimeButton.setText(Utility.formatTimeString(rigViewModel.getEndTime()));
        timeDurationTextView.setText(Utility.calculateTimeSpan(rigViewModel.getStartTime(), rigViewModel.getEndTime()));

        for (int i = 0; i < SAMPLER_PIPE_DIAMETER_OPTIONS.length; i++) {
            if (SAMPLER_PIPE_DIAMETER_OPTIONS[i].equals(String.valueOf(rigViewModel.getSamplerPipeDiameter()))) {
                pipeDiameterSpinner.setSelection(i);
                break;
            }
        }

        if (getCurrentFocus() != pipeLengthEditText) {
            pipeLengthEditText.setText(Utility.formatDouble(rigViewModel.getSamplerPipeLength()));
        }

        for (int i = 0; i < SAMPLER_DRILL_DIAMETER_OPTIONS.length; i++) {
            if (SAMPLER_DRILL_DIAMETER_OPTIONS[i].equals(String.valueOf(rigViewModel.getSamplerDrillDiameter()))) {
                drillDiameterSpinner.setSelection(i);
                break;
            }
        }

        if (getCurrentFocus() != drillLengthEditText) {
            drillLengthEditText.setText(Utility.formatDouble(rigViewModel.getSamplerDrillLength()));
        }

        if (getCurrentFocus() != drillTypeEditText) {
            drillTypeEditText.setText(rigViewModel.getSamplerDrillType());
        }

        drillToolTotalLengthTextView.setText(Utility.formatDouble(rigViewModel.getDrillToolTotalLength()));
        drillPipeRemainLengthTextView.setText(Utility.formatDouble(rigViewModel.getDrillPipeRemainLength()));
        roundTripMeterageLengthTextView.setText(Utility.formatDouble(rigViewModel.getRoundTripMeterageLength()));
        accumulatedMeterageLengthTextView.setText(Utility.formatDouble(rigViewModel.getAccumulatedMeterageLength()));

        for (int i = 0; i < SAMPLER_TYPE_OPTIONS.length; i++) {
            if (rigViewModel.getSamplerType().equals(SAMPLER_TYPE_OPTIONS[i])) {
                samplerTypeSpinner.setSelection(i);
                break;
            }
        }

        if (getCurrentFocus() != samplerIndexEditText) {
            samplerIndexEditText.setText(rigViewModel.getIndex());
        }

        if (getCurrentFocus() != startLengthEditText) {
            startLengthEditText.setText(Utility.formatDouble(rigViewModel.getStartDepth()));
        }

        if (getCurrentFocus() != endLengthEditText) {
            endLengthEditText.setText(Utility.formatDouble(rigViewModel.getEndDepth()));
        }

        if (getCurrentFocus() != countEditText) {
            countEditText.setText(String.valueOf(rigViewModel.getCount()));
        }

        if (DataManager.getHole(holeId).isApproved()) {
            classPeopleCountEditText.setEnabled(false);
            dateButton.setEnabled(false);
            startTimeButton.setEnabled(false);
            endTimeButton.setEnabled(false);
            timeDurationTextView.setEnabled(false);
            pipeDiameterSpinner.setEnabled(false);
            pipeLengthEditText.setEnabled(false);
            drillDiameterSpinner.setEnabled(false);
            drillLengthEditText.setEnabled(false);
            drillTypeEditText.setEnabled(false);
            drillToolTotalLengthTextView.setEnabled(false);
            drillPipeRemainLengthTextView.setEnabled(false);
            roundTripMeterageLengthTextView.setEnabled(false);
            accumulatedMeterageLengthTextView.setEnabled(false);
            samplerTypeSpinner.setEnabled(false);
            samplerIndexEditText.setEnabled(false);
            startLengthEditText.setEnabled(false);
            endLengthEditText.setEnabled(false);
            countEditText.setEnabled(false);
        }

        refreshLock = false;
    }

    private boolean validate() {
        if (!Utility.validateStartEndTime(rigViewModel.getStartTime(), rigViewModel.getEndTime())) {
            Toast.makeText(OriginalSamplingRigActivity.this, "开始时间不得大于等于结束时间.", Toast.LENGTH_LONG).show();
            return false;
        }

        if (rigViewModel.getStartDepth() >= rigViewModel.getEndDepth()) {
            Toast.makeText(OriginalSamplingRigActivity.this, "取样起深要小于取样止深.", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }
}

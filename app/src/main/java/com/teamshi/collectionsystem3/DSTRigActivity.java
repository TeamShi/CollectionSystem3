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
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TimePicker;

import com.teamshi.collectionsystem3.datastructure.DSTRig;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class DSTRigActivity extends AppCompatActivity {
    private static final String TAG = "CollectionSystem3";

    private static final String[] PROBE_TYPE_SPINNER_OPTIONS = {"重型", "超重型"};

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

    private Spinner probeTypeSpinner;
    private ArrayAdapter<String> probeTypeSpinnerAdapter;
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

        probeTypeSpinner = (Spinner) findViewById(R.id.spinner_dst_rig_probe_type);
        probeTypeSpinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, PROBE_TYPE_SPINNER_OPTIONS);
        probeTypeSpinner.setAdapter(probeTypeSpinnerAdapter);
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

        for (int i = 1; i <= 20; i++) {
            TableRow detailedInfoTableRow = (TableRow) findViewById(getResources().getIdentifier("tablerow_dst_detail_" + i, "id", getPackageName()));
            detailedInfoTableRows[i - 1] = detailedInfoTableRow;

            TextView detailedInfoPipeLengthTextView = (TextView) findViewById(getResources().getIdentifier("textview_dst_detail_" + i + "_pipe_length", "id", getPackageName()));
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
                            rigViewModel.getDstDetailInfos().get((int) detailedInfoLengthEditText.getTag() - 1).setLength(Double.parseDouble(s.toString()));

                            double afterDepth = rigViewModel.getDstDetailInfos().get(0).getDepth() + 0.1 * ((int) detailedInfoLengthEditText.getTag() - 1) + Double.parseDouble(s.toString());
                            rigViewModel.getDstDetailInfos().get((int) detailedInfoLengthEditText.getTag() - 1).setDepth(afterDepth);
                            rigViewModel.setAccumulatedMeterageLength(afterDepth);
                            rigViewModel.setRoundTripMeterageLength(0.1 * ((int) detailedInfoLengthEditText.getTag() - 1) + Double.parseDouble(s.toString()));
                            rigViewModel.setDrillPipeRemainLength(rigViewModel.getDstDetailInfos().get((int) detailedInfoLengthEditText.getTag() - 1).getPipeLength() - rigViewModel.getAccumulatedMeterageLength());
                            rigViewModel.setDrillToolTotalLength(rigViewModel.getDstDetailInfos().get((int) detailedInfoLengthEditText.getTag() - 1).getPipeLength());

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

                refreshInfo();
            }
        });

        deleteDstDetailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rigViewModel.getDstDetailInfos().remove(rigViewModel.getDstDetailInfos().size() - 1);

                refreshInfo();
            }
        });

        dstTablePreviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Johnson
                // Preview dst
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
                }, rigStartTime.get(Calendar.HOUR), rigStartTime.get(Calendar.MINUTE), true);
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
                            DSTRigActivity.this.setResult(RESULT_OK);
                            DSTRigActivity.this.finish();
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

        probeTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                rigViewModel.setProbeType(PROBE_TYPE_SPINNER_OPTIONS[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        probeLengthTextView.addTextChangedListener(new TextWatcher() {
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
                    probeLengthTextView.setTextColor(getResources().getColor(android.R.color.black));
                } catch (Exception e) {
                    probeLengthTextView.setTextColor(getResources().getColor(android.R.color.holo_red_light));
                }
            }
        });

        probeDiameterTextView.addTextChangedListener(new TextWatcher() {
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
                    probeDiameterTextView.setTextColor(getResources().getColor(android.R.color.black));
                } catch (Exception e) {
                    probeDiameterTextView.setTextColor(getResources().getColor(android.R.color.holo_red_light));
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

        String requestCode = getIntent().getStringExtra("requestCode");

        holeId = getIntent().getStringExtra("holeId");

        switch (requestCode) {
            case "ACTION_ADD_RIG":
                Calendar startTime = (Calendar) DataManager.getHole(holeId).getLastRigEndTime().clone();
                Calendar endTime = (Calendar) DataManager.getHole(holeId).getLastRigEndTime().clone();
                endTime.add(Calendar.MINUTE, 1);

                rigViewModel = new DSTRig(DataManager.getHole(holeId).getLastClassPeopleCount(), startTime, startTime, endTime,
                        DataManager.getHole(holeId).getLastAccumulatedMeterageLength() + 2, DataManager.getHole(holeId).getLastAccumulatedMeterageLength() + 0.1, 10, 10,
                        "重型", 74, 0.25
                );

                refreshInfo();
                break;
            case "ACTION_COPY_RIG":
                break;
            case "ACTION_EDIT_RIG":
                String holeId = getIntent().getStringExtra("holeId");
                int rigIndex = getIntent().getIntExtra("rigIndex", 0);

                rigViewModel = (DSTRig) DataManager.getRig(holeId, rigIndex).deepCopy();

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

        for (int i = 0; i < PROBE_TYPE_SPINNER_OPTIONS.length; i++) {
            if (PROBE_TYPE_SPINNER_OPTIONS[i].equals(rigViewModel.getProbeType())) {
                probeTypeSpinner.setSelection(i);
                break;
            }
        }
        probeDiameterTextView.setText(String.valueOf(rigViewModel.getProbeDiameter()));
        probeLengthTextView.setText(String.format("%.2f", rigViewModel.getProbeLength()));

        drillToolTotalLengthTextView.setText(String.format("%.2f", rigViewModel.getDrillToolTotalLength()));

        if (getCurrentFocus() != drillPipeRemainLengthEditText) {
            drillPipeRemainLengthEditText.setText(String.format("%.2f", rigViewModel.getDrillPipeRemainLength()));
        }

        roundTripMeterageLengthTextView.setText(String.format("%.2f", rigViewModel.getRoundTripMeterageLength()));
        accumulatedMeterageLengthTextView.setText(String.format("%.2f", rigViewModel.getAccumulatedMeterageLength()));

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

        for (int i = 0; i < 20; i++) {
            if (i < rigViewModel.getDstDetailInfos().size()) {
                detailedInfoTableRows[i].setVisibility(View.VISIBLE);
                detailedInfoPipeLengthTextViews[i].setText(String.format("%.2f", rigViewModel.getDstDetailInfos().get(i).getPipeLength()));
                detailedInfoDepthTextViews[i].setText(String.format("%.2f", rigViewModel.getDstDetailInfos().get(i).getDepth()));

                if (getCurrentFocus() != detailedInfoLengthEditTexts[i]) {
                    detailedInfoLengthEditTexts[i].setText(String.format("%.2f", rigViewModel.getDstDetailInfos().get(i).getLength()));
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

            } else {
                detailedInfoTableRows[i].setVisibility(View.GONE);
            }
        }

        refreshLock = false;
    }

    private boolean validate() {
        return true;
    }
}

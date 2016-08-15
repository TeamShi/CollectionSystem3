package com.teamshi.collectionsystem3;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TimePicker;

import com.teamshi.collectionsystem3.datastructure.DSTRig;
import com.teamshi.collectionsystem3.datastructure.SPTRig;

import org.w3c.dom.Text;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class DSTRigActivity extends AppCompatActivity {
    private static final String TAG = "CollectionSystem3";

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

    private EditText probeTypeEditText;
    private EditText probeDiameterEditText;
    private EditText probeLengthEditText;

    private TextView drillToolTotalLengthTextView;
    private EditText drillPipeRemainLengthEditText;
    private TextView roundTripMeterageLengthTextView;
    private TextView accumulatedMeterageLengthTextView;

    private TableRow detailedInfo1TableRow;
    private TableRow detailedInfo2TableRow;

    private TextView detailedInfo1PipeLengthTextView;
    private TextView detailedInfo2PipeLengthTextView;

    private TextView detailedInfo1DepthEditText;
    private TextView detailedInfo2DepthEditText;

    private EditText detailedInfo1LengthEditText;
    private EditText detailedInfo2LengthEditText;

    private EditText detailedInfo1HitCountEditText;
    private EditText edtailedInfo2HitCountEditText;

    private TextView detailedInfo1SaturationDescriptionTextView;
    private TextView detailedInfo2SaturationDescriptionTextView;

    private TableRow[] detailedInfoTableRows = new TableRow[20];
    private TextView[] detailedInfoPipeLengthTextViews = new TextView[20];
    private TextView[] detailedInfoDepthTextViews = new TextView[20];
    private EditText[] detailedInfoLengthEditTexts = new EditText[20];
    private EditText[] detaieldInfoHitCountEditTexts = new EditText[20];
    private TextView[] detailedInfoSaturationDescription = new TextView[20];

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

        probeTypeEditText = (EditText) findViewById(R.id.edittext_dst_rig_probe_type);
        probeDiameterEditText = (EditText) findViewById(R.id.edittext_dst_rig_probe_diameter);
        probeLengthEditText = (EditText) findViewById(R.id.edittext_dst_rig_probe_length);

        drillToolTotalLengthTextView = (TextView) findViewById(R.id.textview_dst_rig_drill_tool_total_length);
        drillPipeRemainLengthEditText = (EditText) findViewById(R.id.edittext_dst_rig_drill_pipe_remain_length);
        roundTripMeterageLengthTextView = (TextView) findViewById(R.id.textview_dst_round_trip_meterage_length);
        accumulatedMeterageLengthTextView = (TextView) findViewById(R.id.textview_dst_accumulated_meterage_length);

        for (int i = 1; i <= 20; i++) {
            try {
                Field f = this.getClass().getDeclaredField("detailedInfo" + i + "TableRow");
                f.setAccessible(true);
                TableRow tr = (TableRow) f.get(this);
                tr = (TableRow) findViewById(getResources().getIdentifier("tablerow_dst_detail_" + i, "id", getPackageName()));
                detailedInfoTableRows[i - 1] = tr;
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }


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

        String requestCode = getIntent().getStringExtra("requestCode");

        holeId = getIntent().getStringExtra("holeId");

        switch (requestCode) {
            case "ACTION_ADD_RIG":
                Calendar startTime = (Calendar) DataManager.getHole(holeId).getLastRigEndTime().clone();
                Calendar endTime = (Calendar) DataManager.getHole(holeId).getLastRigEndTime().clone();
                endTime.add(Calendar.MINUTE, 1);

                rigViewModel = new DSTRig(DataManager.getHole(holeId).getLastClassPeopleCount(), startTime, startTime, endTime,
                        0, 0, 0, 0,
                        "重型",74, 0.25
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

        probeTypeEditText.setText(rigViewModel.getProbeType());
        probeDiameterEditText.setText(String.valueOf(rigViewModel.getProbeDiameter()));
        probeLengthEditText.setText(String.format("%.2f", rigViewModel.getProbeLength()));

        drillToolTotalLengthTextView.setText(String.format("%.2f", rigViewModel.getDrillToolTotalLength()));

        if (getCurrentFocus() != drillPipeRemainLengthEditText) {
            drillPipeRemainLengthEditText.setText(String.format("%.2f", rigViewModel.getDrillPipeRemainLength()));
        }

        roundTripMeterageLengthTextView.setText(String.format("%.2f", rigViewModel.getRoundTripMeterageLength()));
        accumulatedMeterageLengthTextView.setText(String.format("%.2f", rigViewModel.getAccumulatedMeterageLength()));

        refreshLock = false;
    }

    private boolean validate() {
        return true;
    }
}

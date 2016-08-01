package com.teamshi.collectionsystem3;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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

import com.teamshi.collectionsystem3.datastructure.RegularRig;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class RegularRigActivity extends AppCompatActivity {
    private static final String TAG = "CollectionSystem3";

    private static final String [] rigTypeSpinnerOptions = {"干钻", "合水钻", "金刚石钻", "钢粒钻"};
    private static final String [] rockCorePipeSpinnerOptions = {"73", "89", "108", "127", "146"};
    private static final String [] drillBitTypeSpinnerOptions = {"合金", "金刚石", "钢粒"};

    private boolean refreshLock = false;

    private static final String [] DRILL_BIT_TYPE_TEMPLATE = {
            "合金", "合金", "金刚石", "纲粒"
    };

    private static final double [] DRILL_BIT_LENGTH_TEMPLATE = {
            0.05, 0.05, 0.05, 0.6
    };

    private static final int[] ROCK_CORE_PIPE_DIAMETER_TEMPLATE = {
            127, 108, 89, 73
    };

    private static final double[] DRILL_BIT_DIAMETER_TEMPLATE = {
            130, 110, 91, 75
    };

    private RegularRig rigViewModel;
    private Button confirmAddRigButton;
    private Button cancelAddRigButton;

    private String holeId;

    private TextView classPeopleCountTextView;
    private TextView dateButton;
    private TextView startTimeButton;
    private TextView endTimeButton;
    private TextView timeDurationTextView;
    private Spinner rigTypeSpinner;

    private ArrayAdapter<String> rigTypeSpinnerAdapter;

    private EditText pipeNumberEditText;
    private EditText pipeLengthEditText;
    private TextView pipeTotalLengthTextView;

    private Spinner rockCorePipeDiameterSpinner;
    private ArrayAdapter<String> rockCorePipeDiameterAdapter;
    private EditText rockCorePipeLengthEditText;

    private Spinner drillBitTypeSpinner;
    private ArrayAdapter<String> drillBitTypeSpinnerAdapter;
    private EditText drillBitDiameterEditText;
    private EditText drillBitLengthEditText;

    private TextView drillToolTotalLengthTextView;
    private EditText drillPipeRemainLengthEditText;
    private TextView roundTripMeterageLengthTextView;
    private TextView accumulatedMeterageLengthTextView;

    private EditText rockCoreIndexEditText;
    private EditText rockCoreLengthEditText;
    private TextView rockCorePickPercentageTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "Start RegularRigActivity.");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regular_rig);

        confirmAddRigButton = (Button) findViewById(R.id.button_confirm_add_regular_rig);
        cancelAddRigButton = (Button) findViewById(R.id.button_cancel_add_regular_rig);

        classPeopleCountTextView = (TextView) findViewById(R.id.edittext_regular_rig_class_people_count);
        dateButton = (Button) findViewById(R.id.button_regular_rig_date);
        startTimeButton = (Button) findViewById(R.id.button_regular_rig_start_time);
        endTimeButton = (Button) findViewById(R.id.button_regular_rig_end_time);
        timeDurationTextView = (TextView) findViewById(R.id.textview_regular_rig_duration);
        rigTypeSpinner = (Spinner) findViewById(R.id.spinner_regular_rig_type);

        rigTypeSpinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, rigTypeSpinnerOptions);
        rigTypeSpinner.setAdapter(rigTypeSpinnerAdapter);

        pipeNumberEditText = (EditText) findViewById(R.id.edittext_regular_rig_pipe_number);
        pipeLengthEditText = (EditText) findViewById(R.id.edittext_regular_rig_pipe_length);
        pipeTotalLengthTextView = (TextView) findViewById(R.id.textview_regular_rig_pipe_total_length);

        rockCorePipeDiameterSpinner = (Spinner) findViewById(R.id.spinner_regular_rig_rock_core_pipe_diameter);

        rockCorePipeDiameterAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, rockCorePipeSpinnerOptions);
        rockCorePipeDiameterSpinner.setAdapter(rockCorePipeDiameterAdapter);

        rockCorePipeLengthEditText = (EditText) findViewById(R.id.edittext_regular_rig_rock_core_pipe_length);

        drillBitTypeSpinner = (Spinner) findViewById(R.id.spinner_regular_rig_drill_bit_type);
        drillBitTypeSpinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, drillBitTypeSpinnerOptions);
        drillBitTypeSpinner.setAdapter(drillBitTypeSpinnerAdapter);

        drillBitDiameterEditText = (EditText) findViewById(R.id.edittext_regular_rig_drill_bit_diameter);
        drillBitLengthEditText = (EditText) findViewById(R.id.edittext_regular_rig_drill_bit_length);

        drillToolTotalLengthTextView = (TextView) findViewById(R.id.textview_regular_rig_drill_tool_total_length);
        drillPipeRemainLengthEditText = (EditText) findViewById(R.id.edittext_regular_rig_drill_pipe_remain_length);
        roundTripMeterageLengthTextView = (TextView) findViewById(R.id.textview_regular_rig_round_trip_meterage_length);
        accumulatedMeterageLengthTextView = (TextView) findViewById(R.id.textview_regular_rig_accumulated_meterage_length);

        rockCoreIndexEditText = (EditText) findViewById(R.id.edittext_regular_rig_rock_core_index);
        rockCoreLengthEditText = (EditText) findViewById(R.id.edittext_regular_rig_rock_core_length);
        rockCorePickPercentageTextView = (TextView) findViewById(R.id.textview_regular_rig_rock_core_pick_percentage);

        classPeopleCountTextView.addTextChangedListener(new TextWatcher() {
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
                DatePickerDialog dialog = new DatePickerDialog(RegularRigActivity.this, new DatePickerDialog.OnDateSetListener() {
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

                TimePickerDialog dialog = new TimePickerDialog(RegularRigActivity.this, new TimePickerDialog.OnTimeSetListener() {
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

                TimePickerDialog dialog = new TimePickerDialog(RegularRigActivity.this, new TimePickerDialog.OnTimeSetListener() {
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

                            if (rigViewModel.getPipeNumber() == DataManager.getHole(holeId).getPipeCount() + 1) {
                                DataManager.getHole(holeId).addPipe(rigViewModel.getPipeLength());
                            }

                            DataManager.getHole(holeId).setLastRigEndTime(rigViewModel.getEndTime());
                            DataManager.getHole(holeId).setLastRockCorePipeLength(rigViewModel.getRockCorePipeLength());
                            DataManager.getHole(holeId).setLastAccumulatedMeterageLength(rigViewModel.getAccumulatedMeterageLength());

                            IOManager.updateProject(DataManager.getProject());
                            RegularRigActivity.this.setResult(RESULT_OK);
                            RegularRigActivity.this.finish();
                        }
                        break;
                    case "ACTION_EDIT_RIG":
                        if (validate()) {
                            String holeId = getIntent().getStringExtra("holeId");
                            int rigIndex = getIntent().getIntExtra("rigIndex", 0);

                            DataManager.updateRig(holeId, rigIndex, rigViewModel);

                            if (rigViewModel.getPipeNumber() == DataManager.getHole(holeId).getPipeCount() + 1) {
                                DataManager.getHole(holeId).addPipe(rigViewModel.getPipeLength());
                            }

                            DataManager.getHole(holeId).setLastRigEndTime(rigViewModel.getEndTime());

                            IOManager.updateProject(DataManager.getProject());
                            RegularRigActivity.this.setResult(RESULT_OK);
                            RegularRigActivity.this.finish();
                        }
                        break;
                }
            }
        });

        cancelAddRigButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegularRigActivity.this.setResult(RESULT_CANCELED);
                RegularRigActivity.this.finish();
            }
        });

        pipeNumberEditText.addTextChangedListener(new TextWatcher() {
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
                        if (Integer.parseInt(s.toString()) != DataManager.getHole(holeId).getPipeCount())  {
                            rigViewModel.setPipeNumber(Integer.parseInt(s.toString()));
                            rigViewModel.setPipeLength(0);

                            pipeNumberEditText.setTextColor(getResources().getColor(android.R.color.black));

                            refreshInfo();
                        }
                    } catch (Exception e) {
                        pipeNumberEditText.setTextColor(getResources().getColor(android.R.color.holo_red_light));
                    }
                }
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
                    if (rigViewModel.getPipeNumber() != DataManager.getHole(holeId).getPipeCount()) {
                        try {
                            rigViewModel.setPipeLength(Double.parseDouble(s.toString()));
                            rigViewModel.setPipeTotalLength(DataManager.getHole(holeId).getTotalPipeLength() + rigViewModel.getPipeLength());
                            pipeLengthEditText.setTextColor(getResources().getColor(android.R.color.black));

                            rigViewModel.setDrillToolTotalLength(rigViewModel.getPipeTotalLength() + rigViewModel.getRockCorePipeLength() + rigViewModel.getDrillBitLength());
                            rigViewModel.setRoundTripMeterageLength(rigViewModel.getDrillToolTotalLength() - DataManager.getHole(holeId).getLastAccumulatedMeterageLength());
                            rigViewModel.setAccumulatedMeterageLength(rigViewModel.getPipeTotalLength() - rigViewModel.getDrillPipeRemainLength());

                            rigViewModel.setRockCorePickPercentage(rigViewModel.getRockCoreLength() / rigViewModel.getRoundTripMeterageLength());

                            refreshInfo();
                        } catch (Exception e) {
                            pipeLengthEditText.setTextColor(getResources().getColor(android.R.color.holo_red_light));
                        }
                    }
                }
            }
        });

        rockCorePipeDiameterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        rigViewModel.setRockCorePipeDiameter(73);
                        break;
                    case 1:
                        rigViewModel.setRockCorePipeDiameter(89);
                        break;
                    case 2:
                        rigViewModel.setRockCorePipeDiameter(108);
                        break;
                    case 3:
                        rigViewModel.setRockCorePipeDiameter(127);
                        break;
                    case 4:
                        rigViewModel.setRockCorePipeDiameter(146);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        rockCorePipeLengthEditText.addTextChangedListener(new TextWatcher() {
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
                        rigViewModel.setRockCorePipeLength(Double.parseDouble(s.toString()));
                        rockCorePipeLengthEditText.setTextColor(getResources().getColor(android.R.color.black));

                        rigViewModel.setDrillToolTotalLength(rigViewModel.getPipeTotalLength() + rigViewModel.getRockCorePipeLength() + rigViewModel.getDrillBitLength());
                        rigViewModel.setRoundTripMeterageLength(rigViewModel.getDrillToolTotalLength() - DataManager.getHole(holeId).getLastAccumulatedMeterageLength());
                        rigViewModel.setAccumulatedMeterageLength(rigViewModel.getPipeTotalLength() - rigViewModel.getDrillPipeRemainLength());

                        rigViewModel.setRockCorePickPercentage(rigViewModel.getRockCoreLength() / rigViewModel.getRoundTripMeterageLength());

                        refreshInfo();
                    } catch (Exception e) {
                        rockCorePipeLengthEditText.setTextColor(getResources().getColor(android.R.color.holo_red_light));
                    }
                }
            }
        });

        drillBitTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        rigViewModel.setDrillBitType("合金");
                        break;
                    case 1:
                        rigViewModel.setDrillBitType("金刚石");
                        break;
                    case 2:
                        rigViewModel.setDrillBitType("钢粒");
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        drillBitDiameterEditText.addTextChangedListener(new TextWatcher() {
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
                        rigViewModel.setDrillBitDiameter(Double.parseDouble(s.toString()));
                        drillBitDiameterEditText.setTextColor(getResources().getColor(android.R.color.black));

                    } catch (Exception e) {
                        drillBitDiameterEditText.setTextColor(getResources().getColor(android.R.color.holo_red_light));
                    }
                }
            }
        });

        drillBitLengthEditText.addTextChangedListener(new TextWatcher() {
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
                        rigViewModel.setDrillBitLength(Double.parseDouble(s.toString()));
                        drillBitLengthEditText.setTextColor(getResources().getColor(android.R.color.black));

                        rigViewModel.setDrillToolTotalLength(rigViewModel.getPipeTotalLength() + rigViewModel.getRockCorePipeLength() + rigViewModel.getDrillBitLength());
                        rigViewModel.setRoundTripMeterageLength(rigViewModel.getDrillToolTotalLength() - DataManager.getHole(holeId).getLastAccumulatedMeterageLength());
                        rigViewModel.setAccumulatedMeterageLength(rigViewModel.getPipeTotalLength() - rigViewModel.getDrillPipeRemainLength());

                        rigViewModel.setRockCorePickPercentage(rigViewModel.getRockCoreLength() / rigViewModel.getRoundTripMeterageLength());

                        refreshInfo();
                    } catch (Exception e) {
                        drillBitLengthEditText.setTextColor(getResources().getColor(android.R.color.holo_red_light));
                    }
                }
            }
        });

        rigTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        rigViewModel.setRigType("干钻");
                        break;
                    case 1:
                        rigViewModel.setRigType("合水钻");
                        break;
                    case 2:
                        rigViewModel.setRigType("金刚石钻");
                        break;
                    case 3:
                        rigViewModel.setRigType("钢粒钻");
                        break;
                }

                rigViewModel.setDrillBitType(DRILL_BIT_TYPE_TEMPLATE[position]);
                rigViewModel.setDrillBitLength(DRILL_BIT_LENGTH_TEMPLATE[position]);
                rigViewModel.setRockCorePipeDiameter(ROCK_CORE_PIPE_DIAMETER_TEMPLATE[position]);
                rigViewModel.setDrillBitDiameter(DRILL_BIT_DIAMETER_TEMPLATE[position]);

                refreshInfo();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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

                        rigViewModel.setDrillToolTotalLength(rigViewModel.getPipeTotalLength() + rigViewModel.getRockCorePipeLength() + rigViewModel.getDrillBitLength());
                        rigViewModel.setRoundTripMeterageLength(rigViewModel.getDrillToolTotalLength() - DataManager.getHole(holeId).getLastAccumulatedMeterageLength());
                        rigViewModel.setAccumulatedMeterageLength(rigViewModel.getPipeTotalLength() - rigViewModel.getDrillPipeRemainLength());

                        rigViewModel.setRockCorePickPercentage(rigViewModel.getRockCoreLength() / rigViewModel.getRoundTripMeterageLength());

                        refreshInfo();
                    } catch (Exception e) {
                        drillPipeRemainLengthEditText.setTextColor(getResources().getColor(android.R.color.holo_red_light));
                    }
                }
            }
        });

        rockCoreIndexEditText.addTextChangedListener(new TextWatcher() {
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
                        rigViewModel.setRockCoreIndex(Integer.parseInt(s.toString()));
                        rockCoreIndexEditText.setTextColor(getResources().getColor(android.R.color.black));
                    } catch (Exception e) {
                        rockCoreIndexEditText.setTextColor(getResources().getColor(android.R.color.holo_red_light));
                    }
                }
            }
        });

        rockCoreLengthEditText.addTextChangedListener(new TextWatcher() {
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
                        rigViewModel.setRockCoreLength(Double.parseDouble(s.toString()));
                        rigViewModel.setRockCorePickPercentage(rigViewModel.getRockCoreLength() / rigViewModel.getRoundTripMeterageLength());

                        rockCoreLengthEditText.setTextColor(getResources().getColor(android.R.color.black));

                        refreshInfo();
                    } catch (Exception e) {
                        rockCoreLengthEditText.setTextColor(getResources().getColor(android.R.color.holo_red_light));
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

                if (DataManager.getHole(holeId).getPipeCount() == 0) {
                    rigViewModel = new RegularRig(DataManager.getHole(holeId).getLastClassPeopleCount(), startTime, startTime, endTime, 1, 0, 0, 0, 0, "合金", 0, 0, 0, 0, 0, 0, 1, 0, 0);
                } else {
                    //TODO: alfred rockcoreIndex handle;
                    rigViewModel = new RegularRig(DataManager.getHole(holeId).getLastClassPeopleCount(), startTime, startTime, endTime,
                            DataManager.getHole(holeId).getPipeCount(), DataManager.getHole(holeId).getPipeLength(), DataManager.getHole(holeId).getTotalPipeLength(),
                            0, DataManager.getHole(holeId).getLastRockCorePipeLength(),
                            "合金", 0, 0.05,
                            DataManager.getHole(holeId).getTotalPipeLength() + 0.05 + DataManager.getHole(holeId).getLastRockCorePipeLength(), 0,
                            DataManager.getHole(holeId).getTotalPipeLength() + 0.05 + DataManager.getHole(holeId).getLastRockCorePipeLength() - DataManager.getHole(holeId).getLastAccumulatedMeterageLength(),
                            DataManager.getHole(holeId).getTotalPipeLength() + 0.05 + DataManager.getHole(holeId).getLastRockCorePipeLength(),
                            1, DataManager.getHole(holeId).getTotalPipeLength() + 0.05 + DataManager.getHole(holeId).getLastRockCorePipeLength() - DataManager.getHole(holeId).getLastAccumulatedMeterageLength(), 1
                            );
                }

                refreshInfo();
                break;
            case "ACTION_COPY_RIG":
                break;
            case "ACTION_EDIT_RIG":
                String holeId = getIntent().getStringExtra("holeId");
                int rigIndex = getIntent().getIntExtra("rigIndex", 0);

                rigViewModel = (RegularRig) DataManager.queryRig(holeId, rigIndex).deepCopy();

                refreshInfo();
                break;
        }
    }

    private void refreshInfo() {
        refreshLock = true;

        if (getCurrentFocus() != classPeopleCountTextView) {
            classPeopleCountTextView.setText(rigViewModel.getClassPeopleCount());
        }

        dateButton.setText(Utility.formatCalendarDateString(rigViewModel.getDate()));
        startTimeButton.setText(Utility.formatTimeString(rigViewModel.getStartTime()));
        endTimeButton.setText(Utility.formatTimeString(rigViewModel.getEndTime()));
        timeDurationTextView.setText(Utility.calculateTimeSpan(rigViewModel.getStartTime(), rigViewModel.getEndTime()));

        switch (rigViewModel.getRigType()) {
            case "干钻":
                rigTypeSpinner.setSelection(0);
                break;
            case "合水钻":
                rigTypeSpinner.setSelection(1);
                break;
            case "金刚石钻":
                rigTypeSpinner.setSelection(2);
                break;
            case "钢粒钻":
                rigTypeSpinner.setSelection(3);
                break;
        }

        if (getCurrentFocus() != pipeNumberEditText) {
            pipeNumberEditText.setText(String.valueOf(rigViewModel.getPipeNumber()));
        }

        if (getCurrentFocus() != pipeLengthEditText) {
            pipeLengthEditText.setText(String.valueOf(rigViewModel.getPipeLength()));
        }

        if (getCurrentFocus() != pipeTotalLengthTextView) {
            pipeTotalLengthTextView.setText(String.valueOf(rigViewModel.getPipeTotalLength()));
        }

        switch (rigViewModel.getRockCorePipeDiameter()) {
            case 73:
                rockCorePipeDiameterSpinner.setSelection(0);
                break;
            case 89:
                rockCorePipeDiameterSpinner.setSelection(1);
                break;
            case 108:
                rockCorePipeDiameterSpinner.setSelection(2);
                break;
            case 127:
                rockCorePipeDiameterSpinner.setSelection(3);
                break;
            case 146:
                rockCorePipeDiameterSpinner.setSelection(4);
                break;
        }

        if (getCurrentFocus() != rockCorePipeLengthEditText) {
            rockCorePipeLengthEditText.setText(String.valueOf(rigViewModel.getRockCorePipeLength()));
        }

        switch (rigViewModel.getDrillBitType()) {
            case "合金":
                drillBitTypeSpinner.setSelection(0);
                break;
            case "金刚石":
                drillBitTypeSpinner.setSelection(1);
                break;
            case "钢粒":
                drillBitTypeSpinner.setSelection(2);
                break;
        }

        if (getCurrentFocus() != drillBitDiameterEditText) {
            drillBitDiameterEditText.setText(String.valueOf(rigViewModel.getDrillBitDiameter()));
        }

        if (getCurrentFocus() != drillBitLengthEditText) {
            drillBitLengthEditText.setText(String.valueOf(rigViewModel.getDrillBitLength()));
        }

        drillToolTotalLengthTextView.setText(String.valueOf(rigViewModel.getDrillToolTotalLength()));

        if (getCurrentFocus() != drillPipeRemainLengthEditText) {
            drillPipeRemainLengthEditText.setText(String.valueOf(rigViewModel.getDrillPipeRemainLength()));
        }

        roundTripMeterageLengthTextView.setText(String.valueOf(rigViewModel.getRoundTripMeterageLength()));
        accumulatedMeterageLengthTextView.setText(String.valueOf(rigViewModel.getAccumulatedMeterageLength()));

        if (getCurrentFocus() != rockCoreIndexEditText) {
            rockCoreIndexEditText.setText(String.valueOf(rigViewModel.getRockCoreIndex()));
        }

        if (getCurrentFocus() != rockCoreLengthEditText) {
            rockCoreLengthEditText.setText(String.valueOf(rigViewModel.getRockCoreLength()));
        }

        rockCorePickPercentageTextView.setText(String.format("%.2f", rigViewModel.getRockCorePickPercentage() * 100) + "%");

        refreshLock = false;
    }

    private boolean validate() {
        if (!Utility.validateStartEndTime(rigViewModel.getStartTime(), rigViewModel.getEndTime())) {
            Toast.makeText(RegularRigActivity.this, "开始时间不得大于等于结束时间", Toast.LENGTH_LONG).show();
            return false;
        }

        if (rigViewModel.getPipeNumber() != DataManager.getHole(holeId).getPipeCount() &&
                rigViewModel.getPipeNumber() != DataManager.getHole(holeId).getPipeCount() + 1) {
            pipeNumberEditText.setTextColor(getResources().getColor(android.R.color.holo_red_light));
            Toast.makeText(RegularRigActivity.this, "钻杆编号只能不变或加1", Toast.LENGTH_LONG).show();
            return false;
        }

        if (rigViewModel.getPipeLength() <= 0) {
            pipeLengthEditText.setTextColor(getResources().getColor(android.R.color.holo_red_light));
            Toast.makeText(RegularRigActivity.this, "钻杆长度必须为正数", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }
}

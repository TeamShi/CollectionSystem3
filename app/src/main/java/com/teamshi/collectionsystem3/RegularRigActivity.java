package com.teamshi.collectionsystem3;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class RegularRigActivity extends AppCompatActivity {
    private static final String TAG = "CollectionSystem3";

    private static final String [] rigTypeSpinnerOptions = {"干钻", "合水钻", "金刚石钻", "钢粒钻"};
    private static final String [] rockCorePipeDiameterSpinnerOptions = {"73", "89", "108", "127", "146"};
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

    private static final CharSequence[] ROCK_TYPE_OPTIONS = {"黏土", "杂填土", "素填土", "吹填土", "~~土", "粉质黏土", "粉土", "粉砂", "细砂", "中砂" , "粗砂", "砾砂", "漂石",
            "块石", "卵石", "碎石", "粗圆砾", "粗角砾", "细圆砾", "细角砾", "泥岩", "砂岩", "灰岩", "花岗岩", "~~岩"};
    private static final CharSequence[] ROCK_COLOR_OPTIONS = {"灰色", "青灰色", "深灰色", "紫色", "棕黄色", "浅黄色", "褐黄色", "红褐色", "棕红色", "棕色", "褐色", "黄褐色",
            "青色","灰绿色","浅紫色", "暗红色", "黑色", "浅蓝色", "蓝色"};
    private static final CharSequence[] ROCK_DENSITY_OPTIONS = {"坚硬", "硬塑", "软塑", "流塑", "稍密", "中密", "密实", "松散"};
    private static final CharSequence[] ROCK_SATURATION_OPTIONS = {"稍湿", "潮湿", "饱和"};
    private static final CharSequence[] ROCK_WEATHERING_OPTIONS = {"全风化", "强风化", "中风化", "弱风化", "微风化", "未风化"};

    private RegularRig rigViewModel;
    private Button confirmAddRigButton;
    private Button cancelAddRigButton;

    private String holeId;

    private EditText classPeopleCountEditText;
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

    private TextView startEndDepthTextView;
    private EditText rockTypeEditText;
    private Button rockTypeButton;

    private EditText rockColorEditText;
    private Button rockColorButton;
    private EditText rockDensityEditText;
    private Button rockDensityButton;

    private EditText rockSaturationEditText;
    private Button rockSaturationButton;
    private EditText rockWeatheringEditText;
    private Button rockWeatheringButton;

    private Button generateRockDescriptionButton;
    private Button loadRockDescriptionTemplateButton;
    private EditText rockDescriptionEditText;
    private EditText rigNoteEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "Start RegularRigActivity.");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regular_rig);

        this.setTitle("钻进原始数据录入");

        confirmAddRigButton = (Button) findViewById(R.id.button_confirm_add_regular_rig);
        cancelAddRigButton = (Button) findViewById(R.id.button_cancel_add_regular_rig);

        classPeopleCountEditText = (EditText) findViewById(R.id.edittext_regular_rig_class_people_count);
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

        rockCorePipeDiameterAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, rockCorePipeDiameterSpinnerOptions);
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

        startEndDepthTextView = (TextView) findViewById(R.id.textview_regular_rig_start_end_depth);

        rockTypeEditText = (EditText) findViewById(R.id.edittext_regular_rig_rock_type);
        rockTypeButton = (Button) findViewById(R.id.button_regular_rig_rock_type);

        rockColorEditText = (EditText) findViewById(R.id.edittext_regular_rig_rock_color);
        rockColorButton = (Button) findViewById(R.id.button_regular_rig_rock_color);
        rockDensityEditText = (EditText) findViewById(R.id.edittext_regular_rig_rock_density);
        rockDensityButton = (Button) findViewById(R.id.button_regular_rig_rock_density);

        rockSaturationEditText = (EditText) findViewById(R.id.edittext_regular_rig_rock_saturation);
        rockSaturationButton = (Button) findViewById(R.id.button_regular_rig_rock_saturation);
        rockWeatheringEditText = (EditText) findViewById(R.id.edittext_regular_rig_rock_weathering);
        rockWeatheringButton = (Button) findViewById(R.id.button_regular_rig_rock_weathering);

        generateRockDescriptionButton = (Button) findViewById(R.id.button_generate_rock_description);
        loadRockDescriptionTemplateButton = (Button) findViewById(R.id.button_load_description_template);
        rockDescriptionEditText = (EditText) findViewById(R.id.edittext_regular_rig_rock_description);
        rigNoteEditText = (EditText) findViewById(R.id.edittext_regular_rig_note);

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
                }, rigStartTime.get(Calendar.HOUR_OF_DAY), rigStartTime.get(Calendar.MINUTE), true);
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
                            rigViewModel.setLastRockDentisy(DataManager.getHole(holeId).getLastRockDentisy());
                            rigViewModel.setLastRockWeathering(DataManager.getHole(holeId).getLastRockWeathering());

                            DataManager.addRig(holeId, rigViewModel);

                            if (rigViewModel.getPipeNumber() == DataManager.getHole(holeId).getPipeCount() + 1) {
                                DataManager.getHole(holeId).addPipe(rigViewModel.getPipeLength());
                            }

                            DataManager.getHole(holeId).setLastRigEndTime(rigViewModel.getEndTime());
                            DataManager.getHole(holeId).setLastDate(rigViewModel.getDate());
                            DataManager.getHole(holeId).setLastRockCorePipeLength(rigViewModel.getRockCorePipeLength());
                            DataManager.getHole(holeId).setLastAccumulatedMeterageLength(rigViewModel.getAccumulatedMeterageLength());

                            if (rigViewModel.getRockCoreIndex() > DataManager.getHole(holeId).getMaxRigRockCoreIndex()) {
                                DataManager.getHole(holeId).setMaxRigRockCoreIndex(rigViewModel.getRockCoreIndex());
                            }

                            DataManager.getHole(holeId).setLastRockName(rigViewModel.getRockType());
                            DataManager.getHole(holeId).setLastRockColor(rigViewModel.getRockColor());
                            DataManager.getHole(holeId).setLastRockSaturation(rigViewModel.getRockSaturation());
                            DataManager.getHole(holeId).setLastRockDentisy(rigViewModel.getRockDensity());
                            DataManager.getHole(holeId).setLastRockWeathering(rigViewModel.getRockWeathering());

                            Calendar now = Calendar.getInstance();
                            DataManager.getHole(holeId).setEndDate(now);

                            now.add(Calendar.DATE, 2);

                            DataManager.getHole(holeId).setReviewDate(now);

                            DataManager.getHole(holeId).setActualDepth(rigViewModel.getAccumulatedMeterageLength());

                            DataManager.getHole(holeId).setRockCoreIndex(DataManager.getHole(holeId).getRockCoreIndex() + 1);

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
                            rigViewModel.setAccumulatedMeterageLength(rigViewModel.getDrillToolTotalLength() - rigViewModel.getDrillPipeRemainLength());
                            rigViewModel.setRoundTripMeterageLength(rigViewModel.getAccumulatedMeterageLength() - DataManager.getHole(holeId).getLastAccumulatedMeterageLength());

                            rigViewModel.setRockCorePickPercentage(rigViewModel.getRockCoreLength() / rigViewModel.getRoundTripMeterageLength());
                            rigViewModel.setRigStartEndDepth(Utility.formatDouble(DataManager.getHole(holeId).getLastAccumulatedMeterageLength()) + " m ~ " + Utility.formatDouble(rigViewModel.getAccumulatedMeterageLength()) + " m");

                            refreshInfo();
                        } catch (Exception e) {
                            pipeLengthEditText.setTextColor(getResources().getColor(android.R.color.holo_red_light));
                        }
                    } else if (rigViewModel.getPipeNumber() == DataManager.getHole(holeId).getPipeCount()) {
                        try {
                            rigViewModel.setPipeLength(Double.parseDouble(s.toString()));
                            DataManager.getHole(holeId).getPipeArray().set(DataManager.getHole(holeId).getPipeArray().size() - 1, Double.parseDouble(s.toString()));
                            rigViewModel.setPipeTotalLength(DataManager.getHole(holeId).getTotalPipeLength());
                            pipeLengthEditText.setTextColor(getResources().getColor(android.R.color.black));

                            rigViewModel.setDrillToolTotalLength(rigViewModel.getPipeTotalLength() + rigViewModel.getRockCorePipeLength() + rigViewModel.getDrillBitLength());
                            rigViewModel.setAccumulatedMeterageLength(rigViewModel.getDrillToolTotalLength() - rigViewModel.getDrillPipeRemainLength());
                            rigViewModel.setRoundTripMeterageLength(rigViewModel.getAccumulatedMeterageLength() - DataManager.getHole(holeId).getLastAccumulatedMeterageLength());

                            rigViewModel.setRockCorePickPercentage(rigViewModel.getRockCoreLength() / rigViewModel.getRoundTripMeterageLength());
                            rigViewModel.setRigStartEndDepth(Utility.formatDouble(DataManager.getHole(holeId).getLastAccumulatedMeterageLength()) + " m ~ " + Utility.formatDouble(rigViewModel.getAccumulatedMeterageLength()) + " m");

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
                if (!refreshLock) {
                    switch (position) {
                        case 0:
                            rigViewModel.setRockCorePipeDiameter(73);
                            rigViewModel.setDrillBitDiameter(75);
                            break;
                        case 1:
                            rigViewModel.setRockCorePipeDiameter(89);
                            rigViewModel.setDrillBitDiameter(91);
                            break;
                        case 2:
                            rigViewModel.setRockCorePipeDiameter(108);
                            rigViewModel.setDrillBitDiameter(110);
                            break;
                        case 3:
                            rigViewModel.setRockCorePipeDiameter(127);
                            rigViewModel.setDrillBitDiameter(130);
                            break;
                        case 4:
                            rigViewModel.setRockCorePipeDiameter(146);
                            break;
                    }

                    refreshInfo();
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
                        rigViewModel.setAccumulatedMeterageLength(rigViewModel.getDrillToolTotalLength() - rigViewModel.getDrillPipeRemainLength());
                        rigViewModel.setRoundTripMeterageLength(rigViewModel.getAccumulatedMeterageLength() - DataManager.getHole(holeId).getLastAccumulatedMeterageLength());

                        rigViewModel.setRockCorePickPercentage(rigViewModel.getRockCoreLength() / rigViewModel.getRoundTripMeterageLength());
                        rigViewModel.setRigStartEndDepth(Utility.formatDouble(DataManager.getHole(holeId).getLastAccumulatedMeterageLength()) + " m ~ " + Utility.formatDouble(rigViewModel.getAccumulatedMeterageLength()) + " m");

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
                if (!refreshLock) {
                    rigViewModel.setDrillBitType(drillBitTypeSpinnerOptions[position]);
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
                        rigViewModel.setAccumulatedMeterageLength(rigViewModel.getDrillToolTotalLength() - rigViewModel.getDrillPipeRemainLength());
                        rigViewModel.setRoundTripMeterageLength(rigViewModel.getAccumulatedMeterageLength() - DataManager.getHole(holeId).getLastAccumulatedMeterageLength());

                        rigViewModel.setRockCorePickPercentage(rigViewModel.getRockCoreLength() / rigViewModel.getRoundTripMeterageLength());
                        rigViewModel.setRigStartEndDepth(Utility.formatDouble(DataManager.getHole(holeId).getLastAccumulatedMeterageLength()) + " m ~ " + Utility.formatDouble(rigViewModel.getAccumulatedMeterageLength()) + " m");

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
                if (!refreshLock) {
                    rigViewModel.setRigType(rigTypeSpinnerOptions[position]);

                    String requestCode = getIntent().getStringExtra("requestCode");

                    if (requestCode.equals("ACTION_ADD_RIG")) {
                        rigViewModel.setDrillBitType(DRILL_BIT_TYPE_TEMPLATE[position]);
                        rigViewModel.setDrillBitLength(DRILL_BIT_LENGTH_TEMPLATE[position]);
                        rigViewModel.setRockCorePipeDiameter(ROCK_CORE_PIPE_DIAMETER_TEMPLATE[position]);
                        rigViewModel.setDrillBitDiameter(DRILL_BIT_DIAMETER_TEMPLATE[position]);
                    }

                    refreshInfo();
                }
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
                        rigViewModel.setAccumulatedMeterageLength(rigViewModel.getDrillToolTotalLength() - rigViewModel.getDrillPipeRemainLength());
                        rigViewModel.setRoundTripMeterageLength(rigViewModel.getAccumulatedMeterageLength() - DataManager.getHole(holeId).getLastAccumulatedMeterageLength());

                        rigViewModel.setRockCorePickPercentage(rigViewModel.getRockCoreLength() / rigViewModel.getRoundTripMeterageLength());
                        rigViewModel.setRigStartEndDepth(Utility.formatDouble(DataManager.getHole(holeId).getLastAccumulatedMeterageLength()) + " m ~ " + Utility.formatDouble(rigViewModel.getAccumulatedMeterageLength()) + " m");

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

        rockTypeEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!refreshLock) {
                    rockColorEditText.setEnabled(true);
                    rockColorButton.setEnabled(true);
                    rigViewModel.setRockColor("灰色");
                    rockDensityEditText.setEnabled(true);
                    rockDensityButton.setEnabled(true);
                    rigViewModel.setRockDensity("坚硬");
                    rockSaturationEditText.setEnabled(true);
                    rockSaturationButton.setEnabled(true);
                    rigViewModel.setRockSaturation("稍湿");
                    rockWeatheringEditText.setEnabled(true);
                    rockWeatheringButton.setEnabled(true);
                    rigViewModel.setRockWeathering("全风化");
                    rigViewModel.setRockDescription("");

                    rigViewModel.setRockType(s.toString());
                }
            }
        });

        rockTypeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog rockTypeDialog;
                AlertDialog.Builder builder = new AlertDialog.Builder(RegularRigActivity.this);

                builder.setTitle("岩土名称");

                builder.setSingleChoiceItems(ROCK_TYPE_OPTIONS, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        rigViewModel.setRockType(ROCK_TYPE_OPTIONS[which].toString());

                        dialog.dismiss();

                        if (rigViewModel.getRockType().equals(ROCK_TYPE_OPTIONS[1])
                                || rigViewModel.getRockType().equals(ROCK_TYPE_OPTIONS[2])
                                || rigViewModel.getRockType().equals(ROCK_TYPE_OPTIONS[3])
                                || rigViewModel.getRockType().equals(ROCK_TYPE_OPTIONS[6])) {
                            rockColorEditText.setEnabled(true);
                            rockColorButton.setEnabled(true);
                            rigViewModel.setRockColor("灰色");
                            rockDensityEditText.setEnabled(true);
                            rockDensityButton.setEnabled(true);
                            rigViewModel.setRockDensity("坚硬");
                            rockSaturationEditText.setEnabled(true);
                            rockSaturationButton.setEnabled(true);
                            rigViewModel.setRockSaturation("稍湿");
                            rockWeatheringEditText.setEnabled(false);
                            rockWeatheringButton.setEnabled(false);
                            rigViewModel.setRockWeathering("");
                            rigViewModel.setRockDescription("");
                        } else if (rigViewModel.getRockType().equals(ROCK_TYPE_OPTIONS[0])
                                || rigViewModel.getRockType().equals(ROCK_TYPE_OPTIONS[4])
                                || rigViewModel.getRockType().equals(ROCK_TYPE_OPTIONS[5])) {
                            rockColorEditText.setEnabled(true);
                            rockColorButton.setEnabled(true);
                            rigViewModel.setRockColor("灰色");
                            rockDensityEditText.setEnabled(true);
                            rockDensityButton.setEnabled(true);
                            rigViewModel.setRockDensity("坚硬");
                            rockSaturationEditText.setEnabled(false);
                            rockSaturationButton.setEnabled(false);
                            rigViewModel.setRockSaturation("");
                            rockWeatheringEditText.setEnabled(false);
                            rockWeatheringButton.setEnabled(false);
                            rigViewModel.setRockWeathering("");
                            rigViewModel.setRockDescription("");
                        } else if (rigViewModel.getRockType().endsWith("砂") || rigViewModel.getRockType().endsWith("石") || rigViewModel.getRockType().endsWith("砾")) {
                            rockColorEditText.setEnabled(true);
                            rockColorButton.setEnabled(true);
                            rigViewModel.setRockColor("灰色");
                            rockDensityEditText.setEnabled(true);
                            rockDensityButton.setEnabled(true);
                            rigViewModel.setRockDensity("坚硬");
                            rockSaturationEditText.setEnabled(true);
                            rockSaturationButton.setEnabled(true);
                            rigViewModel.setRockSaturation("稍湿");
                            rockWeatheringEditText.setEnabled(false);
                            rockWeatheringButton.setEnabled(false);
                            rigViewModel.setRockWeathering("");
                            rigViewModel.setRockDescription("");
                        } else if (rigViewModel.getRockType().endsWith("岩")) {
                            rockColorEditText.setEnabled(true);
                            rockColorButton.setEnabled(true);
                            rigViewModel.setRockColor("灰色");
                            rockDensityEditText.setEnabled(false);
                            rockDensityButton.setEnabled(false);
                            rigViewModel.setRockDensity("");
                            rockSaturationEditText.setEnabled(false);
                            rockSaturationButton.setEnabled(false);
                            rigViewModel.setRockSaturation("");
                            rockWeatheringEditText.setEnabled(true);
                            rockWeatheringButton.setEnabled(true);
                            rigViewModel.setRockWeathering("全风化");
                            rigViewModel.setRockDescription("");
                        }

                        refreshInfo();
                    }
                });

                rockTypeDialog = builder.create();
                rockTypeDialog.show();
            }
        });

        rockColorEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!refreshLock) {
                    rigViewModel.setRockColor(s.toString());
                }
            }
        });

        rockColorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog rockColorDialog;
                AlertDialog.Builder builder = new AlertDialog.Builder(RegularRigActivity.this);

                builder.setTitle("颜色");

                builder.setSingleChoiceItems(ROCK_COLOR_OPTIONS, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        rigViewModel.setRockColor(ROCK_COLOR_OPTIONS[which].toString());

                        dialog.dismiss();

                        refreshInfo();
                    }
                });

                rockColorDialog = builder.create();
                rockColorDialog.show();

            }
        });

        rockDensityEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                rigViewModel.setRockDensity(s.toString());
            }
        });

        rockDensityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog rockDensityDialog;
                AlertDialog.Builder builder = new AlertDialog.Builder(RegularRigActivity.this);

                builder.setTitle("稠度/密实度");

                builder.setSingleChoiceItems(ROCK_DENSITY_OPTIONS, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        rigViewModel.setRockDensity(ROCK_DENSITY_OPTIONS[which].toString());

                        dialog.dismiss();

                        refreshInfo();
                    }
                });

                rockDensityDialog = builder.create();
                rockDensityDialog.show();

            }
        });

        rockSaturationEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!refreshLock) {
                    rigViewModel.setRockSaturation(s.toString());
                }
            }
        });

        rockSaturationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog rockSaturationDialog;
                AlertDialog.Builder builder = new AlertDialog.Builder(RegularRigActivity.this);

                builder.setTitle("饱和度");

                builder.setSingleChoiceItems(ROCK_SATURATION_OPTIONS, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        rigViewModel.setRockSaturation(ROCK_SATURATION_OPTIONS[which].toString());

                        dialog.dismiss();

                        refreshInfo();
                    }
                });

                rockSaturationDialog = builder.create();
                rockSaturationDialog.show();

            }
        });

        rockWeatheringEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!refreshLock) {
                    rigViewModel.setRockWeathering(s.toString());
                }
            }
        });

        rockWeatheringButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog rockWeatheringDialog;
                AlertDialog.Builder builder = new AlertDialog.Builder(RegularRigActivity.this);

                builder.setTitle("岩石风化程度");

                builder.setSingleChoiceItems(ROCK_WEATHERING_OPTIONS, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        rigViewModel.setRockWeathering(ROCK_WEATHERING_OPTIONS[which].toString());

                        dialog.dismiss();

                        refreshInfo();
                    }
                });

                rockWeatheringDialog = builder.create();
                rockWeatheringDialog.show();

            }
        });

        generateRockDescriptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> stringList = new ArrayList<String>();
                stringList.add(rigViewModel.getRigStartEndDepth());
                stringList.add(rigViewModel.getRockType());

                if (!rigViewModel.getRockColor().equals("")) {
                    stringList.add(rigViewModel.getRockColor());
                }

                if (!rigViewModel.getRockDensity().equals("")) {
                    stringList.add(rigViewModel.getRockDensity());
                }

                if (!rigViewModel.getRockSaturation().equals("")) {
                    stringList.add(rigViewModel.getRockSaturation());
                }

                if (!rigViewModel.getRockWeathering().equals("")) {
                    stringList.add(rigViewModel.getRockWeathering());
                }

                rigViewModel.setRockDescription(TextUtils.join(", ", stringList));

                refreshInfo();
            }
        });

        loadRockDescriptionTemplateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Map<String, String> configMap = ConfigurationManager.getTemplateDictionary();

                AlertDialog typeDialog;

                final CharSequence[] items = new CharSequence[configMap.size()];

                Set set = configMap.keySet();

                int i = 0;

                for (Iterator iter = set.iterator(); iter.hasNext();)
                {
                    items[i] = (String) iter.next();
                    i++;
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(RegularRigActivity.this);

                builder.setTitle("描述模版");

                builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        rigViewModel.setRockDescription(rigViewModel.getRockDescription() + ", " + configMap.get(items[which]));

                        refreshInfo();

                        dialog.dismiss();
                    }
                });

                typeDialog = builder.create();
                typeDialog.show();

            }
        });

        rockDescriptionEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!refreshLock) {
                    rigViewModel.setRockDescription(s.toString());
                }
            }
        });

        rigNoteEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!refreshLock) {
                    rigViewModel.setNote(s.toString());
                }
            }
        });

        String requestCode = getIntent().getStringExtra("requestCode");

        holeId = getIntent().getStringExtra("holeId");

        switch (requestCode) {
            case "ACTION_ADD_RIG":
                Calendar date = (Calendar) DataManager.getHole(holeId).getLastDate().clone();
                Calendar startTime = (Calendar) DataManager.getHole(holeId).getLastRigEndTime().clone();
                Calendar endTime = (Calendar) DataManager.getHole(holeId).getLastRigEndTime().clone();
                endTime.add(Calendar.MINUTE, 1);

                if (DataManager.getHole(holeId).getPipeCount() == 0) {
                    rigViewModel = new RegularRig(DataManager.getHole(holeId).getLastClassPeopleCount(), date, startTime, endTime, 1, 0, 0, 108, 0, "合金", 110, 0, 0, 0, 0, 0, DataManager.getHole(holeId).getRockCoreIndex() + 1, 0, 0, "0 m ~ 0 m", "黏土", "灰色", "坚硬", "", "", "", "");
                } else {
                    rigViewModel = new RegularRig(DataManager.getHole(holeId).getLastClassPeopleCount(), date, startTime, endTime,
                            DataManager.getHole(holeId).getPipeCount(), DataManager.getHole(holeId).getPipeLength(), DataManager.getHole(holeId).getTotalPipeLength(),
                            108, DataManager.getHole(holeId).getLastRockCorePipeLength(),
                            "合金", 110, 0.05,
                            DataManager.getHole(holeId).getTotalPipeLength() + 0.05 + DataManager.getHole(holeId).getLastRockCorePipeLength(), 0,
                            DataManager.getHole(holeId).getTotalPipeLength() + 0.05 + DataManager.getHole(holeId).getLastRockCorePipeLength() - DataManager.getHole(holeId).getLastAccumulatedMeterageLength(),
                            DataManager.getHole(holeId).getTotalPipeLength() + 0.05 + DataManager.getHole(holeId).getLastRockCorePipeLength(),
                            DataManager.getHole(holeId).getRockCoreIndex() + 1, DataManager.getHole(holeId).getTotalPipeLength() + 0.05 + DataManager.getHole(holeId).getLastRockCorePipeLength() - DataManager.getHole(holeId).getLastAccumulatedMeterageLength(), 1,
                            DataManager.getHole(holeId).getLastAccumulatedMeterageLength() + " m ~ " + (DataManager.getHole(holeId).getTotalPipeLength() + 0.05 + DataManager.getHole(holeId).getLastRockCorePipeLength()) + " m",
                            DataManager.getHole(holeId).getLastRockName(),
                            DataManager.getHole(holeId).getLastRockColor(),
                            DataManager.getHole(holeId).getLastRockDentisy(),
                            DataManager.getHole(holeId).getLastRockSaturation(),
                            DataManager.getHole(holeId).getLastRockWeathering(),
                            "", "");
                }

                refreshInfo();
                break;
            case "ACTION_COPY_RIG":
                int selectedRigIndex = getIntent().getIntExtra("rigIndex", 0);

                RegularRig oldRig = (RegularRig) DataManager.getRig(holeId, selectedRigIndex);

                Calendar copiedDate = (Calendar) DataManager.getHole(holeId).getLastDate().clone();
                Calendar copiedStartTime = (Calendar) DataManager.getHole(holeId).getLastRigEndTime().clone();
                Calendar copiedEndTime = (Calendar) DataManager.getHole(holeId).getLastRigEndTime().clone();
                copiedEndTime.add(Calendar.MINUTE, 1);

                rigViewModel = new RegularRig(DataManager.getHole(holeId).getLastClassPeopleCount(), copiedDate, copiedStartTime, copiedEndTime,
                        oldRig.getPipeNumber(), oldRig.getPipeLength(), oldRig.getPipeTotalLength(),
                        oldRig.getRockCorePipeDiameter(), oldRig.getRockCorePipeLength(),
                        oldRig.getDrillBitType(), oldRig.getDrillBitDiameter(), oldRig.getDrillBitLength(),
                        DataManager.getHole(holeId).getTotalPipeLength() + 0.05 + DataManager.getHole(holeId).getLastRockCorePipeLength(), 0,
                        DataManager.getHole(holeId).getTotalPipeLength() + 0.05 + DataManager.getHole(holeId).getLastRockCorePipeLength() - DataManager.getHole(holeId).getLastAccumulatedMeterageLength(),
                        DataManager.getHole(holeId).getTotalPipeLength() + 0.05 + DataManager.getHole(holeId).getLastRockCorePipeLength(),
                        DataManager.getHole(holeId).getRockCoreIndex() + 1, 0, 1,
                        oldRig.getRigStartEndDepth(),
                        oldRig.getRockType(), oldRig.getRockColor(), oldRig.getRockDensity(), oldRig.getRockSaturation(), oldRig.getRockWeathering(), oldRig.getRockDescription(), oldRig.getNote()
                        );

                rigViewModel.setRigType(oldRig.getRigType());

                refreshInfo();
                break;
            case "ACTION_EDIT_RIG":
                String holeId = getIntent().getStringExtra("holeId");
                int rigIndex = getIntent().getIntExtra("rigIndex", 0);

                classPeopleCountEditText.setEnabled(false);
                dateButton.setEnabled(false);
                startTimeButton.setEnabled(false);
                endTimeButton.setEnabled(false);
                rigTypeSpinner.setEnabled(false);
                pipeNumberEditText.setEnabled(false);
                pipeLengthEditText.setEnabled(false);
                rockCorePipeDiameterSpinner.setEnabled(false);
                rockCorePipeLengthEditText.setEnabled(false);
                drillBitTypeSpinner.setEnabled(false);
                drillBitDiameterEditText.setEnabled(false);
                drillBitLengthEditText.setEnabled(false);
                drillPipeRemainLengthEditText.setEnabled(false);
                rockCoreIndexEditText.setEnabled(false);
                rockCoreLengthEditText.setEnabled(false);

                timeDurationTextView.setEnabled(false);
                drillToolTotalLengthTextView.setEnabled(false);
                roundTripMeterageLengthTextView.setEnabled(false);
                accumulatedMeterageLengthTextView.setEnabled(false);
                rockCorePickPercentageTextView.setEnabled(false);
                startEndDepthTextView.setEnabled(false);

                rigViewModel = (RegularRig) DataManager.getRig(holeId, rigIndex).deepCopy();

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

        for (int i = 0; i < rigTypeSpinnerOptions.length; i++) {
            if (rigTypeSpinnerOptions[i].equals(rigViewModel.getRigType())) {
                rigTypeSpinner.setSelection(i);
                break;
            }
        }

        if (getCurrentFocus() != pipeNumberEditText) {
            pipeNumberEditText.setText(String.valueOf(rigViewModel.getPipeNumber()));
        }

        if (getCurrentFocus() != pipeLengthEditText) {
            pipeLengthEditText.setText(Utility.formatDouble(rigViewModel.getPipeLength()));
        }

        if (getCurrentFocus() != pipeTotalLengthTextView) {
            pipeTotalLengthTextView.setText(Utility.formatDouble(rigViewModel.getPipeTotalLength()));
        }

        for (int i = 0; i < rockCorePipeDiameterSpinnerOptions.length; i++) {
            if (Double.parseDouble(rockCorePipeDiameterSpinnerOptions[i]) == rigViewModel.getRockCorePipeDiameter()) {
                rockCorePipeDiameterSpinner.setSelection(i);
                break;
            }
        }

        if (getCurrentFocus() != rockCorePipeLengthEditText) {
            rockCorePipeLengthEditText.setText(Utility.formatDouble(rigViewModel.getRockCorePipeLength()));
        }

        for (int i = 0; i < drillBitTypeSpinnerOptions.length; i++) {
            if (rigViewModel.getDrillBitType().equals(drillBitTypeSpinnerOptions[i])) {
                drillBitTypeSpinner.setSelection(i);
                break;
            }
        }

        if (getCurrentFocus() != drillBitDiameterEditText) {
            drillBitDiameterEditText.setText(Utility.formatDouble(rigViewModel.getDrillBitDiameter()));
        }

        if (getCurrentFocus() != drillBitLengthEditText) {
            drillBitLengthEditText.setText(Utility.formatDouble(rigViewModel.getDrillBitLength()));
        }

        drillToolTotalLengthTextView.setText(Utility.formatDouble(rigViewModel.getDrillToolTotalLength()));

        if (getCurrentFocus() != drillPipeRemainLengthEditText) {
            drillPipeRemainLengthEditText.setText(Utility.formatDouble(rigViewModel.getDrillPipeRemainLength()));
        }

        roundTripMeterageLengthTextView.setText(Utility.formatDouble(rigViewModel.getRoundTripMeterageLength()));
        accumulatedMeterageLengthTextView.setText(Utility.formatDouble(rigViewModel.getAccumulatedMeterageLength()));

        if (getCurrentFocus() != rockCoreIndexEditText) {
            rockCoreIndexEditText.setText(String.valueOf(rigViewModel.getRockCoreIndex()));
        }

        if (getCurrentFocus() != rockCoreLengthEditText) {
            rockCoreLengthEditText.setText(Utility.formatDouble(rigViewModel.getRockCoreLength()));
        }

        rockCorePickPercentageTextView.setText(Utility.formatDouble(rigViewModel.getRockCorePickPercentage() * 100) + "%");

        startEndDepthTextView.setText(rigViewModel.getRigStartEndDepth());


        if (getCurrentFocus() != rockTypeEditText) {
            rockTypeEditText.setText(rigViewModel.getRockType());
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

        if (getCurrentFocus() != rockWeatheringEditText) {
            rockWeatheringEditText.setText(rigViewModel.getRockWeathering());
        }

        if (getCurrentFocus() != rockDescriptionEditText) {
            rockDescriptionEditText.setText(rigViewModel.getRockDescription());
        }

        if (getCurrentFocus() != rigNoteEditText) {
            rigNoteEditText.setText(rigViewModel.getNote());
        }

        if (rigViewModel.getRockType().equals(ROCK_TYPE_OPTIONS[0]) || rigViewModel.getRockType().equals(ROCK_TYPE_OPTIONS[1])) {
            rockColorEditText.setEnabled(true);
            rockColorButton.setEnabled(true);
            rockDensityEditText.setEnabled(true);
            rockDensityButton.setEnabled(true);
            rockSaturationEditText.setEnabled(false);
            rockSaturationButton.setEnabled(false);
            rockWeatheringEditText.setEnabled(false);
            rockWeatheringButton.setEnabled(false);
        } else if (rigViewModel.getRockType().equals(ROCK_TYPE_OPTIONS[2])) {
            rockColorEditText.setEnabled(true);
            rockColorButton.setEnabled(true);
            rockDensityEditText.setEnabled(true);
            rockDensityButton.setEnabled(true);
            rockSaturationEditText.setEnabled(true);
            rockSaturationButton.setEnabled(true);
            rockWeatheringEditText.setEnabled(false);
            rockWeatheringButton.setEnabled(false);
        } else if (rigViewModel.getRockType().endsWith("砂") || rigViewModel.getRockType().endsWith("石") || rigViewModel.getRockType().endsWith("砾")) {
            rockColorEditText.setEnabled(true);
            rockColorButton.setEnabled(true);
            rockDensityEditText.setEnabled(true);
            rockDensityButton.setEnabled(true);
            rockSaturationEditText.setEnabled(true);
            rockSaturationButton.setEnabled(true);
            rockWeatheringEditText.setEnabled(false);
            rockWeatheringButton.setEnabled(false);
        } else if (rigViewModel.getRockType().endsWith("岩")) {
            rockColorEditText.setEnabled(true);
            rockColorButton.setEnabled(true);
            rockDensityEditText.setEnabled(false);
            rockDensityButton.setEnabled(false);
            rockSaturationEditText.setEnabled(false);
            rockSaturationButton.setEnabled(false);
            rockWeatheringEditText.setEnabled(true);
            rockWeatheringButton.setEnabled(true);
        }


        if (DataManager.getHole(holeId).isApproved()) {
            classPeopleCountEditText.setEnabled(false);
            dateButton.setEnabled(false);
            startTimeButton.setEnabled(false);
            endTimeButton.setEnabled(false);
            rigTypeSpinner.setEnabled(false);
            pipeNumberEditText.setEnabled(false);
            pipeLengthEditText.setEnabled(false);
            pipeTotalLengthTextView.setEnabled(false);
            rockCorePipeDiameterSpinner.setEnabled(false);
            rockCoreLengthEditText.setEnabled(false);
            drillBitTypeSpinner.setEnabled(false);
            drillBitDiameterEditText.setEnabled(false);
            drillBitLengthEditText.setEnabled(false);
            drillPipeRemainLengthEditText.setEnabled(false);
            rockCoreIndexEditText.setEnabled(false);
            rockCoreLengthEditText.setEnabled(false);
            rockTypeEditText.setEnabled(false);
            rockTypeButton.setEnabled(false);
            rockColorEditText.setEnabled(false);
            rockColorButton.setEnabled(false);
            rockDensityEditText.setEnabled(false);
            rockDensityButton.setEnabled(false);
            rockSaturationEditText.setEnabled(false);
            rockSaturationButton.setEnabled(false);
            rockWeatheringEditText.setEnabled(false);
            rockWeatheringButton.setEnabled(false);
            generateRockDescriptionButton.setEnabled(false);
            loadRockDescriptionTemplateButton.setEnabled(false);
            rockDescriptionEditText.setEnabled(false);
            rigNoteEditText.setEnabled(false);
        }

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

        if (rigViewModel.getRockCoreIndex() < DataManager.getHole(holeId).getMaxRigRockCoreIndex()) {
            rockCoreIndexEditText.setTextColor(getResources().getColor(android.R.color.holo_red_light));
            Toast.makeText(RegularRigActivity.this, "岩芯采取编号不得小于前值", Toast.LENGTH_LONG).show();
            return false;
        }

        if (rigViewModel.getRockDescription().equals("")) {
            Toast.makeText(RegularRigActivity.this, "名称及岩性不能为空", Toast.LENGTH_LONG).show();
            return false;
        }


        return true;
    }
}

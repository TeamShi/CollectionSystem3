package com.teamshi.collectionsystem3;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
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

import com.teamshi.collectionsystem3.datastructure.Hole;
import com.teamshi.collectionsystem3.datastructure.OriginalSamplingRig;
import com.teamshi.collectionsystem3.datastructure.Project;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class OriginalSamplingRigActivity extends AppCompatActivity {
    private static final String TAG = "CollectionSystem3";

    private static final String [] SAMPLER_PIPE_DIAMETER_OPTIONS = {"108", "89"};
    private static final String [] SAMPLER_TYPE_OPTIONS = {"厚壁", "薄壁"};

    private static final CharSequence[] ROCK_TYPE_OPTIONS = {"黏土", "杂填土", "素填土", "吹填土", "~~土", "粉质黏土", "粉土", "粉砂", "细砂", "中砂" , "粗砂", "砾砂", "漂石",
            "块石", "卵石", "碎石", "粗圆砾", "粗角砾", "细圆砾", "细角砾", "泥岩", "砂岩", "灰岩", "花岗岩", "~~岩"};
    private static final CharSequence[] ROCK_COLOR_OPTIONS = {"灰色", "青灰色", "深灰色", "紫色", "棕黄色", "浅黄色", "褐黄色", "红褐色", "棕红色", "棕色", "褐色", "黄褐色",
            "青色","灰绿色","浅紫色", "暗红色", "黑色", "浅蓝色", "蓝色"};
    private static final CharSequence[] ROCK_DENSITY_OPTIONS = {"坚硬", "硬塑", "软塑", "流塑", "稍密", "中密", "密实", "松散"};
    private static final CharSequence[] ROCK_SATURATION_OPTIONS = {"稍湿", "潮湿", "饱和"};
    private static final CharSequence[] ROCK_WEATHERING_OPTIONS = {"全风化", "强风化", "中风化", "弱风化", "微风化", "未风化"};


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

        rockCoreIndexEditText = (EditText) findViewById(R.id.edittext_original_sampling_rig_rock_core_index);
        rockCoreLengthEditText = (EditText) findViewById(R.id.edittext_original_sampling_rig_rock_core_length);
        rockCorePickPercentageTextView = (TextView) findViewById(R.id.textview_original_sampling_rig_rock_core_pick_percentage);

        startEndDepthTextView = (TextView) findViewById(R.id.textview_original_sampling_rig_start_end_depth);

        rockTypeEditText = (EditText) findViewById(R.id.edittext_original_sampling_rig_rock_type);
        rockTypeButton = (Button) findViewById(R.id.button_original_sampling_rig_rock_type);

        rockColorEditText = (EditText) findViewById(R.id.edittext_original_sampling_rig_rock_color);
        rockColorButton = (Button) findViewById(R.id.button_original_sampling_rig_rock_color);
        rockDensityEditText = (EditText) findViewById(R.id.edittext_original_sampling_rig_rock_density);
        rockDensityButton = (Button) findViewById(R.id.button_original_sampling_rig_rock_density);

        rockSaturationEditText = (EditText) findViewById(R.id.edittext_original_sampling_rig_rock_saturation);
        rockSaturationButton = (Button) findViewById(R.id.button_original_sampling_rig_rock_saturation);
        rockWeatheringEditText = (EditText) findViewById(R.id.edittext_original_sampling_rig_rock_weathering);
        rockWeatheringButton = (Button) findViewById(R.id.button_original_sampling_rig_rock_weathering);

        generateRockDescriptionButton = (Button) findViewById(R.id.button_generate_rock_description);
        loadRockDescriptionTemplateButton = (Button) findViewById(R.id.button_load_description_template);
        rockDescriptionEditText = (EditText) findViewById(R.id.edittext_original_sampling_rig_rock_description);
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

                        rigViewModel.setAccumulatedMeterageLength(rigViewModel.getEndDepth() + 0.1);
                        rigViewModel.setDrillPipeRemainLength(rigViewModel.getDrillToolTotalLength() - rigViewModel.getAccumulatedMeterageLength());
                        rigViewModel.setRockCorePickPercentage(rigViewModel.getRockCoreLength() / rigViewModel.getRoundTripMeterageLength());

                        rigViewModel.setRigStartEndDepth(rigViewModel.getStartDepth() + " m ~ " + rigViewModel.getEndDepth() + " m");
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
                        rigViewModel.setRockCorePickPercentage(rigViewModel.getRockCoreLength() / rigViewModel.getRoundTripMeterageLength());

                        rigViewModel.setRigStartEndDepth(Utility.formatDouble(rigViewModel.getStartDepth()) + " m ~ " + Utility.formatDouble(rigViewModel.getEndDepth()) + " m");
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
                AlertDialog.Builder builder = new AlertDialog.Builder(OriginalSamplingRigActivity.this);

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
                AlertDialog.Builder builder = new AlertDialog.Builder(OriginalSamplingRigActivity.this);

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
                AlertDialog.Builder builder = new AlertDialog.Builder(OriginalSamplingRigActivity.this);

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
                AlertDialog.Builder builder = new AlertDialog.Builder(OriginalSamplingRigActivity.this);

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
                AlertDialog.Builder builder = new AlertDialog.Builder(OriginalSamplingRigActivity.this);

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

                AlertDialog.Builder builder = new AlertDialog.Builder(OriginalSamplingRigActivity.this);

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
                        "原" + DataManager.getHole(holeId).getOriginalSampleIndex(),
                        DataManager.getHole(holeId).getLastAccumulatedMeterageLength() + 0.1,
                        DataManager.getHole(holeId).getLastAccumulatedMeterageLength() + 0.3,
                        1, "厚壁",
                        DataManager.getHole(holeId).getRockCoreIndex(), DataManager.getHole(holeId).getTotalPipeLength() + 0.05 + DataManager.getHole(holeId).getLastRockCorePipeLength() - DataManager.getHole(holeId).getLastAccumulatedMeterageLength(), 1,
                        (DataManager.getHole(holeId).getLastAccumulatedMeterageLength() + 0.1) + " m ~ " + (DataManager.getHole(holeId).getTotalPipeLength() + 0.3) + " m",
                        "黏土", "灰色", "坚硬", "", "", "");

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

        String holeId = getIntent().getStringExtra("holeId");
        int rigIndex = getIntent().getIntExtra("rigIndex", 0);

        if (DataManager.getHole(holeId).getRigList().size() != 0 && rigIndex != DataManager.getHole(holeId).getRigList().size() - 1) {
            classPeopleCountEditText.setEnabled(false);
            dateButton.setEnabled(false);
            startTimeButton.setEnabled(false);
            endTimeButton.setEnabled(false);
            timeDurationTextView.setEnabled(false);
            pipeDiameterSpinner.setEnabled(false);
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

        if (DataManager.getHole(holeId).isApproved()) {
            classPeopleCountEditText.setEnabled(false);
            dateButton.setEnabled(false);
            startTimeButton.setEnabled(false);
            endTimeButton.setEnabled(false);
            timeDurationTextView.setEnabled(false);
            pipeDiameterSpinner.setEnabled(false);
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

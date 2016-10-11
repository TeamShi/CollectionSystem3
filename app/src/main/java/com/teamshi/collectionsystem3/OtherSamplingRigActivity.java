package com.teamshi.collectionsystem3;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
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
import android.widget.Toast;

import com.teamshi.collectionsystem3.datastructure.Hole;
import com.teamshi.collectionsystem3.datastructure.OriginalSamplingRig;
import com.teamshi.collectionsystem3.datastructure.OtherSamplingRig;
import com.teamshi.collectionsystem3.datastructure.Project;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class OtherSamplingRigActivity extends AppCompatActivity {
    private static final String TAG = "CollectionSystem3";

    private static final String [] OTHER_SAMPLER_TYPE_OPTIONS = {"扰动样", "岩样", "水样"};

    private boolean refreshLock = false;

    private OtherSamplingRig rigViewModel;
    private Button confirmAddRigButton;
    private Button cancelAddRigButton;

    private String holeId;

    private EditText classPeopleCountEditText;
    private TextView dateButton;
    private TextView startTimeButton;
    private TextView endTimeButton;
    private TextView timeDurationTextView;

    private TextView samplingTypeTextView;

    private Button previewButton;

    private Button addDetailedButton;
    private Button removeDetailedButton;

    private TableRow[] detailedInfoTableRows = new TableRow[80];
    private EditText[] detailedInfoIndexEditTexts = new EditText[80];
    private EditText[] detailedInfoDiameterEditTexts = new EditText[80];
    private EditText[] detailedInfoStartDepthEditTexts = new EditText[80];
    private EditText[] detailedInfoEndDepthEditTexts = new EditText[80];
    private EditText[] detailedInfoCountEditTexts = new EditText[80];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "Start OtherSamplingRigActivity.");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_sampling_rig);

        this.setTitle("取样信息表");

        confirmAddRigButton = (Button) findViewById(R.id.button_confirm_add_other_sampling_rig);
        cancelAddRigButton = (Button) findViewById(R.id.button_cancel_add_other_sampling_rig);

        classPeopleCountEditText = (EditText) findViewById(R.id.edittext_other_sampling_rig_class_people_count);
        dateButton = (Button) findViewById(R.id.button_other_sampling_rig_date);
        startTimeButton = (Button) findViewById(R.id.button_other_sampling_rig_start_time);
        endTimeButton = (Button) findViewById(R.id.button_other_sampling_rig_end_time);
        timeDurationTextView = (TextView) findViewById(R.id.textview_other_sampling_rig_duration);

        previewButton = (Button) findViewById(R.id.button_other_sampling_rig_preview);

        addDetailedButton = (Button) findViewById(R.id.button_add_sampling_detail);
        removeDetailedButton = (Button) findViewById(R.id.button_delete_sampling_detail);

        samplingTypeTextView = (TextView) findViewById(R.id.textview_other_sampling_rig_type);

        for (int i = 1; i <= 80; i++) {
            final TableRow detailedInfoTableRow = (TableRow) findViewById(getResources().getIdentifier("table_row_sampling_detail_" + i, "id", getPackageName()));
            detailedInfoTableRows[i - 1] = detailedInfoTableRow;

            final EditText detailedInfoIndexEditText = (EditText) findViewById(getResources().getIdentifier("edittext_other_sampling_detail_index_" + i, "id", getPackageName()));
            detailedInfoIndexEditText.setTag(i);
            detailedInfoIndexEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (!refreshLock) {
                        rigViewModel.getDetails().get((Integer) detailedInfoIndexEditText.getTag() - 1).setIndex(s.toString());
                    }
                }
            });
            detailedInfoIndexEditTexts[i - 1] = detailedInfoIndexEditText;

            final EditText detailedInfoDiameterEditText = (EditText) findViewById(getResources().getIdentifier("edittext_other_sampling_detail_diameter_" + i, "id", getPackageName()));
            detailedInfoDiameterEditText.setTag(i);
            detailedInfoDiameterEditText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(OtherSamplingRigActivity.this);
                    AlertDialog typeDialog;
                    final CharSequence[] items = {"130", "110", "91", "其他"};
                    builder.setTitle("直径");

                    builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case 0:
                                case 1:
                                case 2:
                                    rigViewModel.getDetails().get((Integer) detailedInfoDiameterEditText.getTag() - 1).setDiameter(Integer.parseInt(items[which].toString()));
                                    detailedInfoDiameterEditText.setText(items[which].toString());
                                    break;
                                case 3:
                                    rigViewModel.getDetails().get((Integer) detailedInfoDiameterEditText.getTag() - 1).setDiameter(0);
                                    detailedInfoDiameterEditText.setText("0");
                                    break;
                            }

                            dialog.dismiss();
                        }
                    });

                    typeDialog = builder.create();
                    typeDialog.show();
                }
            });
            detailedInfoDiameterEditText.addTextChangedListener(new TextWatcher() {
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
                            rigViewModel.getDetails().get((Integer) detailedInfoDiameterEditText.getTag() - 1).setDiameter(Integer.parseInt(s.toString()));
                            detailedInfoDiameterEditText.setTextColor(getResources().getColor(android.R.color.black));

                        } catch (Exception e) {
                            detailedInfoDiameterEditText.setTextColor(getResources().getColor(android.R.color.holo_red_light));
                        }
                    }
                }
            });
            detailedInfoDiameterEditTexts[i - 1] = detailedInfoDiameterEditText;

            final EditText detailedInfoStartDepthEditText = (EditText) findViewById(getResources().getIdentifier("edittext_other_sampling_detail_start_depth_" + i, "id", getPackageName()));
            detailedInfoStartDepthEditText.setTag(i);
            detailedInfoStartDepthEditText.addTextChangedListener(new TextWatcher() {
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
                            rigViewModel.getDetails().get((Integer) detailedInfoStartDepthEditText.getTag() - 1).setStartDepth(Double.parseDouble(s.toString()));
                            detailedInfoStartDepthEditText.setTextColor(getResources().getColor(android.R.color.black));

                        } catch (Exception e) {
                            detailedInfoStartDepthEditText.setTextColor(getResources().getColor(android.R.color.holo_red_light));
                        }
                    }
                }
            });
            detailedInfoStartDepthEditTexts[i - 1] = detailedInfoStartDepthEditText;

            final EditText detailedInfoEndDepthEditText = (EditText) findViewById(getResources().getIdentifier("edittext_other_sampling_detail_end_depth_" + i, "id", getPackageName()));
            detailedInfoEndDepthEditText.setTag(i);
            detailedInfoEndDepthEditText.addTextChangedListener(new TextWatcher() {
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
                            rigViewModel.getDetails().get((Integer) detailedInfoEndDepthEditText.getTag() - 1).setEndDepth(Double.parseDouble(s.toString()));
                            detailedInfoEndDepthEditText.setTextColor(getResources().getColor(android.R.color.black));

                        } catch (Exception e) {
                            detailedInfoEndDepthEditText.setTextColor(getResources().getColor(android.R.color.holo_red_light));
                        }
                    }
                }
            });
            detailedInfoEndDepthEditTexts[i - 1] = detailedInfoEndDepthEditText;

            final EditText detailedInfoCountEditText = (EditText) findViewById(getResources().getIdentifier("edittext_other_sampling_detail_count_" + i, "id", getPackageName()));
            detailedInfoCountEditText.setTag(i);
            detailedInfoCountEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (!refreshLock) {
                        rigViewModel.getDetails().get((Integer) detailedInfoCountEditText.getTag() - 1).setCount(s.toString());
                    }
                }
            });
            detailedInfoCountEditTexts[i - 1] = detailedInfoCountEditText;
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
                DatePickerDialog dialog = new DatePickerDialog(OtherSamplingRigActivity.this, new DatePickerDialog.OnDateSetListener() {
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

                TimePickerDialog dialog = new TimePickerDialog(OtherSamplingRigActivity.this, new TimePickerDialog.OnTimeSetListener() {
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

                TimePickerDialog dialog = new TimePickerDialog(OtherSamplingRigActivity.this, new TimePickerDialog.OnTimeSetListener() {
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

        addDetailedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rigViewModel.getSamplingType().equals("扰动样")) {
                    rigViewModel.getDetails().add(new OtherSamplingRig.OtherSamplingDetail("扰动样", "扰" + String.valueOf(rigViewModel.getDetails().size() + 1), 0, 0, "0", 130));
                } else if (rigViewModel.getSamplingType().equals("岩样")) {
                    rigViewModel.getDetails().add(new OtherSamplingRig.OtherSamplingDetail("岩样", "岩" + String.valueOf(rigViewModel.getDetails().size() + 1), 0, 0, "0", 130));
                } else if (rigViewModel.getSamplingType().equals("水样")) {
                    rigViewModel.getDetails().add(new OtherSamplingRig.OtherSamplingDetail("水样", "水" + String.valueOf(rigViewModel.getDetails().size() + 1), 0, 0, "0", 130));

                }

                refreshInfo();
            }
        });

        removeDetailedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rigViewModel.getDetails().remove(rigViewModel.getDetails().size() - 1);

                refreshInfo();
            }
        });

        previewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                otherType = "扰动样" "岩样" "水样";
            Project project = DataManager.getProject();
            Hole hole = DataManager.getHole(holeId);
            PreviewActivity.setUrls(IOManager.previewOtherSamplingRig(hole, rigViewModel));
            Intent intent = new Intent(OtherSamplingRigActivity.this, PreviewActivity.class);
            intent.putExtra("projectName", project.getProjectName());
            startActivity(intent);
            }
        });

        confirmAddRigButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String requestCode = getIntent().getStringExtra("requestCode");

                switch (requestCode) {
                    case "ACTION_DISTURBANCE_SAMPLE":
                        DataManager.getHole(holeId).setDisturbanceSample(rigViewModel);
                        break;
                    case "ACTION_ROCK_SAMPLE":
                        DataManager.getHole(holeId).setRockSample(rigViewModel);
                        break;
                    case "ACTION_WATER_SAMPLE":
                        DataManager.getHole(holeId).setWaterSample(rigViewModel);
                        break;
                }

                IOManager.updateProject(DataManager.getProject());
                OtherSamplingRigActivity.this.setResult(RESULT_OK);
                OtherSamplingRigActivity.this.finish();
            }
        });

        cancelAddRigButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OtherSamplingRigActivity.this.setResult(RESULT_CANCELED);
                OtherSamplingRigActivity.this.finish();
            }
        });

        String requestCode = getIntent().getStringExtra("requestCode");

        holeId = getIntent().getStringExtra("holeId");

        switch (requestCode) {
            case "ACTION_DISTURBANCE_SAMPLE":
                rigViewModel = DataManager.getHole(holeId).getDisturbanceSample().deepCopy();
                rigViewModel.setClassPeopleCount(DataManager.getHole(holeId).getLastClassPeopleCount());
                refreshInfo();
                break;
            case "ACTION_ROCK_SAMPLE":
                rigViewModel = DataManager.getHole(holeId).getRockSample().deepCopy();
                rigViewModel.setClassPeopleCount(DataManager.getHole(holeId).getLastClassPeopleCount());
                refreshInfo();
                break;
            case "ACTION_WATER_SAMPLE":
                rigViewModel = DataManager.getHole(holeId).getWaterSample().deepCopy();
                rigViewModel.setClassPeopleCount(DataManager.getHole(holeId).getLastClassPeopleCount());
                refreshInfo();
                break;
        }

    }

    private boolean validate() {
        if (!Utility.validateStartEndTime(rigViewModel.getStartTime(), rigViewModel.getEndTime())) {
            Toast.makeText(OtherSamplingRigActivity.this, "开始时间不得大于等于结束时间.", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
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

        samplingTypeTextView.setText(rigViewModel.getSamplingType());

        for (int i = 0; i < 80; i++) {
            if (i < rigViewModel.getDetails().size()) {
                detailedInfoTableRows[i].setVisibility(View.VISIBLE);

                detailedInfoIndexEditTexts[i].setText(rigViewModel.getDetails().get(i).getIndex());
                detailedInfoDiameterEditTexts[i].setText(String.valueOf(rigViewModel.getDetails().get(i).getDiameter()));
                detailedInfoStartDepthEditTexts[i].setText(Utility.formatDouble(rigViewModel.getDetails().get(i).getStartDepth()));
                detailedInfoEndDepthEditTexts[i].setText(Utility.formatDouble(rigViewModel.getDetails().get(i).getEndDepth()));
                detailedInfoCountEditTexts[i].setText(rigViewModel.getDetails().get(i).getCount());

                detailedInfoIndexEditTexts[i].setEnabled(true);
                detailedInfoDiameterEditTexts[i].setEnabled(true);
                detailedInfoStartDepthEditTexts[i].setEnabled(true);
                detailedInfoEndDepthEditTexts[i].setEnabled(true);
                detailedInfoCountEditTexts[i].setEnabled(true);
            } else {
                detailedInfoTableRows[i].setVisibility(View.GONE);

                detailedInfoIndexEditTexts[i].setText("");
                detailedInfoDiameterEditTexts[i].setText("");
                detailedInfoStartDepthEditTexts[i].setText("");
                detailedInfoEndDepthEditTexts[i].setText("");
                detailedInfoCountEditTexts[i].setText("");

                detailedInfoIndexEditTexts[i].setEnabled(false);
                detailedInfoDiameterEditTexts[i].setEnabled(false);
                detailedInfoStartDepthEditTexts[i].setEnabled(false);
                detailedInfoEndDepthEditTexts[i].setEnabled(false);
                detailedInfoCountEditTexts[i].setEnabled(false);
            }
        }

        if (rigViewModel.getDetails().size() == 0) {
            addDetailedButton.setEnabled(true);
            removeDetailedButton.setEnabled(false);
        } else if (rigViewModel.getDetails().size() == 80) {
            addDetailedButton.setEnabled(false);
            removeDetailedButton.setEnabled(true);
        } else {
            addDetailedButton.setEnabled(true);
            removeDetailedButton.setEnabled(true);
        }

        if (DataManager.getHole(holeId).isApproved()) {
            classPeopleCountEditText.setEnabled(false);
            dateButton.setEnabled(false);
            startTimeButton.setEnabled(false);
            endTimeButton.setEnabled(false);
            timeDurationTextView.setEnabled(false);
            addDetailedButton.setEnabled(false);
            removeDetailedButton.setEnabled(false);
        }

        refreshLock = false;
    }

}

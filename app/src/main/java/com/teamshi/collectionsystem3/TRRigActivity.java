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
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.teamshi.collectionsystem3.datastructure.TRRig;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class TRRigActivity extends AppCompatActivity {
    private static final String TAG = "CollectionSystem3";

    private String holeId;

    private String[] TR_INFO_DIAMETER_OPTIONS = {"127", "108"};

    private TRRig rigViewModel;
    private Button confirmAddRigButton;
    private Button cancelAddRigButton;

    private TextView classPeopleCountTextView;
    private TextView dateButton;
    private TextView startTimeButton;
    private TextView endTimeButton;
    private TextView timeDurationTextView;

    private TableRow[] detailedInfoTableRows = new TableRow[15];
    private EditText[] detailedInfoWallTypeEditTexts = new EditText[15];
    private EditText[] detailedInfoIndexEditTexts = new EditText[15];
    private Spinner[] detailedInfoDiameterSpinners = new Spinner[15];
    private EditText[] detailedInfoLengthEditTexts = new EditText[15];
    private EditText[] detailedInfoTotalLengthEditTexts = new EditText[15];

    private Button addTRDetailedButton;
    private Button removeTRDetailedButton;

    private EditText holeSituraionEditText;
    private EditText specialDescriptionEditText;

    private boolean refreshLock;
    private boolean addDeleteLock;

    private int lastTRIndex;
    private int lastTR108Index;

    private int lastTRLength;
    private int lastTR108Length;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trrig);

        Log.d(TAG, "Start TRRigActivity.");

        this.setTitle("下套管原始数据录入");

        confirmAddRigButton = (Button) findViewById(R.id.button_confirm_add_trrig);
        cancelAddRigButton = (Button) findViewById(R.id.button_cancel_add_trrig);

        classPeopleCountTextView = (TextView) findViewById(R.id.textview_tr_rig_class_people_count);
        dateButton = (Button) findViewById(R.id.button_tr_rig_date);
        startTimeButton = (Button) findViewById(R.id.button_tr_rig_start_time);
        endTimeButton = (Button) findViewById(R.id.button_tr_rig_end_time);
        timeDurationTextView = (TextView) findViewById(R.id.textview_tr_rig_duration);

        addTRDetailedButton = (Button) findViewById(R.id.button_add_tr_detail);
        removeTRDetailedButton = (Button) findViewById(R.id.button_delete_tr_detail);

        specialDescriptionEditText = (EditText) findViewById(R.id.edittext_tr_rig_special_description);
        holeSituraionEditText = (EditText) findViewById(R.id.edittext_tr_rig_hole_situration);

        for (int i = 1; i <= 15; i++) {
            TableRow detailedInfoTabRow = (TableRow) findViewById(getResources().getIdentifier("tablerow_tr_detail_" + i, "id", getPackageName()));
            detailedInfoTableRows[i - 1] = detailedInfoTabRow;

            final EditText wallTypeEditText = (EditText) findViewById(getResources().getIdentifier("edittext_tr_rig_wall_type_" + i, "id", getPackageName()));
            wallTypeEditText.setTag(i);
            wallTypeEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (!refreshLock) {
                        rigViewModel.getTrInfos().get((Integer) wallTypeEditText.getTag() - 1).setWallType(s.toString());
                        refreshInfo();
                    }
                }
            });
            detailedInfoWallTypeEditTexts[i - 1] = wallTypeEditText;

            final EditText detailedInfoIndexEditText = (EditText) findViewById(getResources().getIdentifier("edittext_tr_rig_tr_detail_index_" + i, "id", getPackageName()));
            detailedInfoIndexEditText.setTag(i);
            detailedInfoIndexEditText.setEnabled(false);
            detailedInfoIndexEditTexts[i - 1] = detailedInfoIndexEditText;

            final Spinner detailedInfoDiameterSpinner = (Spinner) findViewById(getResources().getIdentifier("spinner_tr_rig_tr_detail_diameter_" + i, "id", getPackageName()));
            detailedInfoDiameterSpinner.setTag(i);
            final ArrayAdapter<String> detailedInfoDiameterSpinnerAdapter = new ArrayAdapter<String>(TRRigActivity.this, android.R.layout.simple_spinner_dropdown_item, TR_INFO_DIAMETER_OPTIONS);
            detailedInfoDiameterSpinner.setAdapter(detailedInfoDiameterSpinnerAdapter);
            detailedInfoDiameterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (!refreshLock && !addDeleteLock) {
                        rigViewModel.getTrInfos().get((Integer) detailedInfoDiameterSpinner.getTag() - 1).setDiameter(Integer.parseInt(TR_INFO_DIAMETER_OPTIONS[position]));

                        int count127 = 0;
                        int count108 = 0;

                        for (int k = 0; k < rigViewModel.getTrInfos().size(); k++) {
                            if (rigViewModel.getTrInfos().get(k).getDiameter() == 127) {
                                count127++;
                            } else {
                                count108++;
                            }
                        }

                        if (position == 1) {
                            rigViewModel.getTrInfos().get((Integer) detailedInfoDiameterSpinner.getTag() - 1).setIndex(count108 + lastTR108Index - 1);
                            rigViewModel.getTrInfos().get((Integer) detailedInfoDiameterSpinner.getTag() - 1).setTotalLength(lastTR108Length);
                        } else {
                            rigViewModel.getTrInfos().get((Integer) detailedInfoDiameterSpinner.getTag() - 1).setIndex(count127 + lastTRIndex - 1);
                            rigViewModel.getTrInfos().get((Integer) detailedInfoDiameterSpinner.getTag() - 1).setTotalLength(lastTRLength);
                        }

                        refreshInfo();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            detailedInfoDiameterSpinners[i - 1] = detailedInfoDiameterSpinner;

            final EditText detailedInfoLengthEditText = (EditText) findViewById(getResources().getIdentifier("edittext_tr_rig_tr_detail_length_" + i, "id", getPackageName()));
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
                            rigViewModel.getTrInfos().get((Integer) detailedInfoLengthEditText.getTag() - 1).setLength(Double.parseDouble(s.toString()));

                            double totalLength = 0;
                            for (TRRig.TRInfo info : rigViewModel.getTrInfos()) {
                                totalLength += info.getLength();
                            }

                            if (rigViewModel.getTrInfos().get((Integer) detailedInfoLengthEditText.getTag() - 1).getDiameter() == 127) {
                                rigViewModel.getTrInfos().get((Integer) detailedInfoLengthEditText.getTag() - 1).setTotalLength(lastTRLength + Double.parseDouble(s.toString()));
                                lastTRLength += Double.parseDouble(s.toString());
                            } else {
                                rigViewModel.getTrInfos().get((Integer) detailedInfoLengthEditText.getTag() - 1).setTotalLength(lastTR108Length + Double.parseDouble(s.toString()));
                                lastTR108Length += Double.parseDouble(s.toString());
                            }

                            detailedInfoLengthEditText.setTextColor(getResources().getColor(android.R.color.black));

                            refreshInfo();
                        } catch (Exception e) {
                            detailedInfoLengthEditText.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                        }

                    }
                }
            });
            detailedInfoLengthEditTexts[i - 1] = detailedInfoLengthEditText;

            final EditText detailedInfoTotalLengthEditText = (EditText) findViewById(getResources().getIdentifier("edittext_tr_rig_tr_detail_total_length_" + i, "id", getPackageName()));
            detailedInfoTotalLengthEditText.setTag(i);
            detailedInfoTotalLengthEditText.setEnabled(false);
            detailedInfoTotalLengthEditTexts[i - 1] = detailedInfoTotalLengthEditText;
        }

        addTRDetailedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count127 = 0;

                for (int k = 0; k < rigViewModel.getTrInfos().size(); k++) {
                    if (rigViewModel.getTrInfos().get(k).getDiameter() == 127) {
                        count127++;
                    }
                }

                rigViewModel.getTrInfos().add(new TRRig.TRInfo("钢管", count127 + lastTRIndex , rigViewModel.getTrInfos().get(rigViewModel.getTrInfos().size() - 1).getDiameter(), 0, lastTRLength));
                addDeleteLock = true;
                refreshInfo();
                addDeleteLock = false;
            }
        });

        removeTRDetailedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TRRig.TRInfo trInfoToDelete = rigViewModel.getTrInfos().get(rigViewModel.getTrInfos().size() - 1);

                if (trInfoToDelete.getDiameter() == 127) {
                    lastTRLength -= trInfoToDelete.getLength();
                } else {
                    lastTR108Length -= trInfoToDelete.getLength();
                }

                rigViewModel.getTrInfos().remove(rigViewModel.getTrInfos().size() - 1);

                addDeleteLock = true;
                refreshInfo();
                addDeleteLock = false;
            }
        });

        classPeopleCountTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                rigViewModel.setClassPeopleCount(s.toString());
                DataManager.setLastClassPeopleCount(holeId, s.toString());
            }
        });

        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar rigDate = rigViewModel.getDate();
                DatePickerDialog dialog = new DatePickerDialog(TRRigActivity.this, new DatePickerDialog.OnDateSetListener() {
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

                TimePickerDialog dialog = new TimePickerDialog(TRRigActivity.this, new TimePickerDialog.OnTimeSetListener() {
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

                TimePickerDialog dialog = new TimePickerDialog(TRRigActivity.this, new TimePickerDialog.OnTimeSetListener() {
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
                            rigViewModel.setLastRockDentisy(DataManager.getHole(holeId).getLastRockDentisy());
                            rigViewModel.setLastRockWeathering(DataManager.getHole(holeId).getLastRockWeathering());


                            int count127 = 0;
                            int count108 = 0;

                            for (int k = 0; k < rigViewModel.getTrInfos().size(); k++) {
                                if (rigViewModel.getTrInfos().get(k).getDiameter() == 127) {
                                    count127++;
                                } else {
                                    count108++;
                                }
                            }

                            DataManager.getHole(holeId).setLastTRIndex(lastTRIndex + count127);
                            DataManager.getHole(holeId).setLastTR108Index(lastTR108Index + count108);
                            DataManager.getHole(holeId).setLastDate(rigViewModel.getDate());

                            DataManager.getHole(holeId).setLastTRLength(lastTRLength);
                            DataManager.getHole(holeId).setLastTR108Length(lastTR108Length);

                            DataManager.addRig(holeId, rigViewModel);

                            DataManager.getHole(holeId).setLastRigEndTime(rigViewModel.getEndTime());

                            Calendar now = Calendar.getInstance();
                            DataManager.getHole(holeId).setEndDate(now);

                            now.add(Calendar.DATE, 2);

                            DataManager.getHole(holeId).setReviewDate(now);

                            IOManager.updateProject(DataManager.getProject());
                            TRRigActivity.this.setResult(RESULT_OK);
                            TRRigActivity.this.finish();
                        }
                        break;
                    case "ACTION_EDIT_RIG":
                        if (validate()) {
                            String holeId = getIntent().getStringExtra("holeId");
                            int rigIndex = getIntent().getIntExtra("rigIndex", 0);

                            DataManager.updateRig(holeId, rigIndex, rigViewModel);

                            IOManager.updateProject(DataManager.getProject());
                            TRRigActivity.this.setResult(RESULT_OK);
                            TRRigActivity.this.finish();
                        }
                        break;
                }
            }
        });

        cancelAddRigButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TRRigActivity.this.setResult(RESULT_CANCELED);
                TRRigActivity.this.finish();
            }
        });

        specialDescriptionEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                rigViewModel.setSpecialDescription(s.toString());
            }
        });

        holeSituraionEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                rigViewModel.setHoleSaturation(s.toString());
            }
        });

        String requestCode = getIntent().getStringExtra("requestCode");

        holeId = getIntent().getStringExtra("holeId");


        lastTRIndex = DataManager.getHole(holeId).getLastTRIndex();
        lastTR108Index = DataManager.getHole(holeId).getLastTR108Index();

        lastTRLength = DataManager.getHole(holeId).getLastTRLength();
        lastTR108Length = DataManager.getHole(holeId).getLastTR108Length();


        switch (requestCode) {
            case "ACTION_ADD_RIG":
                Calendar date = (Calendar) DataManager.getHole(holeId).getLastDate().clone();

                Calendar startTime = (Calendar) DataManager.getHole(holeId).getLastRigEndTime().clone();
                Calendar endTime = Calendar.getInstance();

                rigViewModel = new TRRig(DataManager.getHole(holeId).getLastClassPeopleCount(), date, endTime, endTime);
                rigViewModel.getTrInfos().get(0).setIndex(lastTRIndex);
                refreshInfo();
                break;
            case "ACTION_EDIT_RIG":
                String holeId = getIntent().getStringExtra("holeId");
                int rigIndex = getIntent().getIntExtra("rigIndex", 0);

                rigViewModel = (TRRig) DataManager.getRig(holeId, rigIndex).deepCopy();

                refreshInfo();
                break;
        }

    }

    private void refreshInfo() {
        if (refreshLock) {
            return;
        }

        refreshLock = true;

        classPeopleCountTextView.setText(rigViewModel.getClassPeopleCount());
        dateButton.setText(Utility.formatCalendarDateString(rigViewModel.getDate()));
        startTimeButton.setText(Utility.formatTimeString(rigViewModel.getStartTime()));
        endTimeButton.setText(Utility.formatTimeString(rigViewModel.getEndTime()));
        timeDurationTextView.setText(Utility.calculateTimeSpan(rigViewModel.getStartTime(), rigViewModel.getEndTime()));

        for (int i = 0; i < 15; i++) {
            if (i < rigViewModel.getTrInfos().size()) {
                detailedInfoTableRows[i].setVisibility(View.VISIBLE);

                detailedInfoWallTypeEditTexts[i].setEnabled(i == rigViewModel.getTrInfos().size() - 1);

                if (getCurrentFocus() != detailedInfoWallTypeEditTexts[i] || addDeleteLock) {
                    detailedInfoWallTypeEditTexts[i].setText(rigViewModel.getTrInfos().get(i).getWallType());
                }

                detailedInfoIndexEditTexts[i].setText(String.valueOf(rigViewModel.getTrInfos().get(i).getIndex()));

                detailedInfoDiameterSpinners[i].setEnabled(i == rigViewModel.getTrInfos().size() - 1);

                for (int j = 0; j < TR_INFO_DIAMETER_OPTIONS.length; j++) {
                    if (TR_INFO_DIAMETER_OPTIONS[j].equals(String.valueOf(rigViewModel.getTrInfos().get(i).getDiameter()))) {
                        detailedInfoDiameterSpinners[i].setSelection(j);
                        break;
                    }
                }

                detailedInfoLengthEditTexts[i].setEnabled(i == rigViewModel.getTrInfos().size() - 1);

                if (getCurrentFocus() != detailedInfoLengthEditTexts[i] || addDeleteLock) {
                    detailedInfoLengthEditTexts[i].setText(String.valueOf(Utility.formatDouble(rigViewModel.getTrInfos().get(i).getLength())));
                }

                detailedInfoTotalLengthEditTexts[i].setText(Utility.formatDouble(rigViewModel.getTrInfos().get(i).getTotalLength()));
            } else {
                detailedInfoTableRows[i].setVisibility(View.GONE);
            }
        }

        removeTRDetailedButton.setEnabled(! (rigViewModel.getTrInfos().size() == 1));
        addTRDetailedButton.setEnabled(! (rigViewModel.getTrInfos().size() == 15));

        holeSituraionEditText.setText(rigViewModel.getHoleSaturation());
        specialDescriptionEditText.setText(rigViewModel.getSpecialDescription());

        if (DataManager.getHole(holeId).isApproved()) {
            classPeopleCountTextView.setEnabled(false);
            dateButton.setEnabled(false);
            startTimeButton.setEnabled(false);
            endTimeButton.setEnabled(false);
            addTRDetailedButton.setEnabled(false);
            removeTRDetailedButton.setEnabled(false);

            for (EditText et : detailedInfoWallTypeEditTexts) {
                et.setEnabled(false);
            }

            for (EditText et : detailedInfoLengthEditTexts) {
                et.setEnabled(false);
            }

            for (EditText et : detailedInfoTotalLengthEditTexts) {
                et.setEnabled(false);
            }

            holeSituraionEditText.setEnabled(false);
            specialDescriptionEditText.setEnabled(false);

        }

        refreshLock = false;
    }

    private boolean validate() {
        for (int i = 0; i < rigViewModel.getTrInfos().size(); i ++) {
            try {
                Double.parseDouble(detailedInfoLengthEditTexts[i].getText().toString());
            } catch (Exception e) {
                detailedInfoLengthEditTexts[i].setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                Toast.makeText(TRRigActivity.this, "套管" + String.valueOf(i + 1) + "长度非法.", Toast.LENGTH_LONG).show();
                return false;
            }

        }

        return true;
    }
}

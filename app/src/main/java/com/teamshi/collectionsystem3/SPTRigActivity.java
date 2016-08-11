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
import android.widget.TextView;
import android.widget.TimePicker;

import com.teamshi.collectionsystem3.datastructure.RegularRig;
import com.teamshi.collectionsystem3.datastructure.SPTRig;

import java.util.Calendar;
import java.util.GregorianCalendar;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "Start SPTRigActivity.");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sptrig);

        confirmAddRigButton = (Button) findViewById(R.id.button_confirm_add_spt_rig);
        cancelAddRigButton = (Button) findViewById(R.id.button_cancel_add_spt_rig);

        classPeopleCountEditText = (EditText) findViewById(R.id.edittext_spt_rig_class_people_count);
        dateButton = (Button) findViewById(R.id.button_spt_rig_date);
        startTimeButton = (Button) findViewById(R.id.button_spt_rig_start_time);
        endTimeButton = (Button) findViewById(R.id.button_spt_rig_end_time);
        timeDurationTextView = (TextView) findViewById(R.id.textview_spt_rig_duration);

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

        String requestCode = getIntent().getStringExtra("requestCode");

        holeId = getIntent().getStringExtra("holeId");

        switch (requestCode) {
            case "ACTION_ADD_RIG":
                Calendar startTime = (Calendar) DataManager.getHole(holeId).getLastRigEndTime().clone();
                Calendar endTime = (Calendar) DataManager.getHole(holeId).getLastRigEndTime().clone();
                endTime.add(Calendar.MINUTE, 1);

                rigViewModel = new SPTRig(DataManager.getHole(holeId).getLastClassPeopleCount(), startTime, startTime, endTime,
                        0, 0, 0, 0,
                        0, 0,
                        "",0, 0);
                
                refreshInfo();
                break;
            case "ACTION_COPY_RIG":
                break;
            case "ACTION_EDIT_RIG":
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

        refreshLock = false;
    }

    private boolean validate() {
        return true;
    }
}

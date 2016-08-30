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
import android.widget.Toast;

import com.teamshi.collectionsystem3.datastructure.NARig;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class NARigInfoActivity extends AppCompatActivity {
    private static final String TAG = "CollectionSystem3";

    private String holeId;

    private NARig rigViewModel;
    private Button confirmAddRigButton;
    private Button cancelAddRigButton;

    private TextView classPeopleCountTextView;
    private TextView dateButton;
    private TextView startTimeButton;
    private TextView endTimeButton;
    private TextView timeDurationTextView;
    private EditText naTypeEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "Start RegularRigActivity.");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_narig_info);

        this.setTitle("原始数据录入");

        confirmAddRigButton = (Button) findViewById(R.id.button_confirm_add_narig);
        cancelAddRigButton = (Button) findViewById(R.id.button_cancel_add_narig);

        classPeopleCountTextView = (TextView) findViewById(R.id.textview_na_rig_class_people_count);
        dateButton = (Button) findViewById(R.id.button_na_rig_date);
        startTimeButton = (Button) findViewById(R.id.button_na_rig_start_time);
        endTimeButton = (Button) findViewById(R.id.button_na_rig_end_time);
        timeDurationTextView = (TextView) findViewById(R.id.textview_na_rig_duration);
        naTypeEditText = (EditText) findViewById(R.id.edittext_na_rig_type);

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

        naTypeEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                rigViewModel.setNaType(s.toString());
            }
        });

        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar rigDate = rigViewModel.getDate();
                DatePickerDialog dialog = new DatePickerDialog(NARigInfoActivity.this, new DatePickerDialog.OnDateSetListener() {
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

                TimePickerDialog dialog = new TimePickerDialog(NARigInfoActivity.this, new TimePickerDialog.OnTimeSetListener() {
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

                TimePickerDialog dialog = new TimePickerDialog(NARigInfoActivity.this, new TimePickerDialog.OnTimeSetListener() {
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

        naTypeEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                rigViewModel.setNaType(s.toString());
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

                            DataManager.getHole(holeId).setLastRigEndTime(rigViewModel.getEndTime());

                            IOManager.updateProject(DataManager.getProject());

                            NARigInfoActivity.this.setResult(RESULT_OK);
                            NARigInfoActivity.this.finish();
                        }
                        break;
                    case "ACTION_EDIT_RIG":
                        if (validate()) {
                            String holeId = getIntent().getStringExtra("holeId");
                            int rigIndex = getIntent().getIntExtra("rigIndex", 0);

                            DataManager.updateRig(holeId, rigIndex, rigViewModel);
                            DataManager.getHole(holeId).setLastRigEndTime(rigViewModel.getEndTime());

                            IOManager.updateProject(DataManager.getProject());

                            NARigInfoActivity.this.setResult(RESULT_OK);
                            NARigInfoActivity.this.finish();
                        }
                        break;
                }
            }
        });

        cancelAddRigButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NARigInfoActivity.this.setResult(RESULT_CANCELED);
                NARigInfoActivity.this.finish();
            }
        });

        String requestCode = getIntent().getStringExtra("requestCode");

        holeId = getIntent().getStringExtra("holeId");

        switch (requestCode) {
            case "ACTION_ADD_RIG":
                Calendar startTime = (Calendar) DataManager.getHole(holeId).getLastRigEndTime().clone();
                Calendar endTime = (Calendar) DataManager.getHole(holeId).getLastRigEndTime().clone();
                endTime.add(Calendar.MINUTE, 1);
                rigViewModel = new NARig(DataManager.getHole(holeId).getLastClassPeopleCount(), startTime, startTime, endTime);

                refreshInfo();
                break;
            case "ACTION_COPY_RIG":
                break;
            case "ACTION_EDIT_RIG":
                String holeId = getIntent().getStringExtra("holeId");
                int rigIndex = getIntent().getIntExtra("rigIndex", 0);

                rigViewModel = (NARig) DataManager.getRig(holeId, rigIndex).deepCopy();

                refreshInfo();
                break;
        }
    }

    private void refreshInfo() {
        classPeopleCountTextView.setText(rigViewModel.getClassPeopleCount());
        dateButton.setText(Utility.formatCalendarDateString(rigViewModel.getDate()));
        startTimeButton.setText(Utility.formatTimeString(rigViewModel.getStartTime()));
        endTimeButton.setText(Utility.formatTimeString(rigViewModel.getEndTime()));
        timeDurationTextView.setText(Utility.calculateTimeSpan(rigViewModel.getStartTime(), rigViewModel.getEndTime()));
        naTypeEditText.setText(rigViewModel.getNaType());
    }

    private boolean validate() {
        if (!Utility.validateStartEndTime(rigViewModel.getStartTime(), rigViewModel.getEndTime())) {
            Toast.makeText(NARigInfoActivity.this, "开始时间不得大于等于结束时间", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }


}

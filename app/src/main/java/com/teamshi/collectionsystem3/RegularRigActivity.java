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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.teamshi.collectionsystem3.datastructure.RegularRig;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class RegularRigActivity extends AppCompatActivity {
    private static final String TAG = "CollectionSystem3";

    private static final String [] rigTypeSpinnerOptions = {"干钻", "合水钻", "金刚石钻", "钢粒钻"};

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

                            RegularRigActivity.this.setResult(RESULT_OK);
                            RegularRigActivity.this.finish();
                        }
                        break;
                    case "ACTION_EDIT_RIG":
                        if (validate()) {
                            String holeId = getIntent().getStringExtra("holeId");
                            int rigIndex = getIntent().getIntExtra("rigIndex", 0);

                            DataManager.updateRig(holeId, rigIndex, rigViewModel);

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
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        String requestCode = getIntent().getStringExtra("requestCode");

        holeId = getIntent().getStringExtra("holeId");

        switch (requestCode) {
            case "ACTION_ADD_RIG":
                Calendar start_time = Calendar.getInstance();
                Calendar end_time = Calendar.getInstance();
                end_time.add(Calendar.MINUTE, 1);
                rigViewModel = new RegularRig(DataManager.getHole(holeId).getLastClassPeopleCount(), start_time, start_time, end_time);

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
        classPeopleCountTextView.setText(rigViewModel.getClassPeopleCount());
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
    }

    private boolean validate() {
        return true;
    }
}

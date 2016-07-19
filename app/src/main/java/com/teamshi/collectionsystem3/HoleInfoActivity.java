package com.teamshi.collectionsystem3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.teamshi.collectionsystem3.datastructure.Hole;

import org.w3c.dom.Text;

public class HoleInfoActivity extends AppCompatActivity {
    private static final String TAG = "CollectionSystem3";

    private static final int ACTION_ADD_HOLE = 1;

    private Hole holeViewModel;

    private static final String[] HOLE_ID_PART1_SPINNER_OPTIONS = {"JC", "JZ", "其他"};
    private static final String[] HOLE_ID_PART2_SPINNER_OPTIONS = {"I", "II", "III", "IV"};
    private static final String[] HOLE_ID_PART4_SPINNER_OPTIONS = {"1", "2", "3", "4"};

    private TextView projectNameTextView;
    private TextView holeStartDateTextView;
    private TextView holeEndDateTextView;

    private Spinner holeIdPart1Spinner;
    private Spinner holeIdPart2Spinner;
    private EditText holeIdPart3EditText;
    private Spinner holeIdPart4Spinner;
    private EditText holeIdPart5EditText;
    private EditText holeIdSpecialIdEditText;

    private ArrayAdapter<String> holeIdPart1SpinnerAdapter;
    private ArrayAdapter<String> holeIdPart2SpinnerAdapter;
    private ArrayAdapter<String> holeIdPart4SpinnerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "Start HoleInfoActivity.");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hole_info);

        projectNameTextView = (TextView) findViewById(R.id.textview_hole_info_project_name);
        holeStartDateTextView = (TextView) findViewById(R.id.textview_hole_start_date);
        holeEndDateTextView = (TextView) findViewById(R.id.textview_hole_end_date);

        holeIdPart1Spinner = (Spinner) findViewById(R.id.spinner_hole_id_part1);
        holeIdPart2Spinner = (Spinner) findViewById(R.id.spinner_hole_id_part2);
        holeIdPart3EditText = (EditText) findViewById(R.id.edittext_hole_id_part3);
        holeIdPart4Spinner = (Spinner) findViewById(R.id.spinner_hole_id_part4);
        holeIdPart5EditText = (EditText) findViewById(R.id.edittext_hole_id_part5);
        holeIdSpecialIdEditText = (EditText) findViewById(R.id.edittext_hole_special_id);

        holeIdPart1SpinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, HOLE_ID_PART1_SPINNER_OPTIONS);
        holeIdPart1Spinner.setAdapter(holeIdPart1SpinnerAdapter);
        holeIdPart2SpinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, HOLE_ID_PART2_SPINNER_OPTIONS);
        holeIdPart2Spinner.setAdapter(holeIdPart2SpinnerAdapter);
        holeIdPart4SpinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, HOLE_ID_PART4_SPINNER_OPTIONS);
        holeIdPart4Spinner.setAdapter(holeIdPart4SpinnerAdapter);

        holeIdPart1Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 2) {
                    Log.d(TAG, "Special hole id selected.");
                    setHoleIdSpecialControllers();
                } else {
                    Log.d(TAG, "Normal hole id selected.");
                    setHoleIdNormalControllers();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        String requestCode = getIntent().getStringExtra("requestCode");

        switch (requestCode) {
            case "ACTION_ADD_HOLE":
                this.holeViewModel = new Hole(getIntent().getStringExtra("projectName"));

                refreshInfo();
                break;
            default:
                break;
        }
    }

    private void refreshInfo() {
        projectNameTextView.setText(this.holeViewModel.getProjectName());

        if (this.holeViewModel.isSpecialHoleId()) {
            holeIdPart1Spinner.setSelection(2);

            holeIdSpecialIdEditText.setText(holeViewModel.getSpecialHoleId());

            setHoleIdSpecialControllers();
        } else {
            switch (holeViewModel.getHoleId1()) {
                case "JC":
                    holeIdPart1Spinner.setSelection(0);
                    break;
                case "JZ":
                    holeIdPart1Spinner.setSelection(1);
                    break;
            }

            switch (holeViewModel.getHoleId2()) {
                case "I":
                    holeIdPart2Spinner.setSelection(0);
                    break;
                case "II":
                    holeIdPart2Spinner.setSelection(1);
                    break;
                case "III":
                    holeIdPart2Spinner.setSelection(2);
                    break;
                case "IV":
                    holeIdPart2Spinner.setSelection(3);
                    break;
            }

            holeIdPart3EditText.setText(holeViewModel.getHoleId3());

            switch (holeViewModel.getHoleId4()) {
                case "1":
                    holeIdPart4Spinner.setSelection(0);
                    break;
                case "2":
                    holeIdPart4Spinner.setSelection(1);
                    break;
                case "3":
                    holeIdPart4Spinner.setSelection(2);
                    break;
                case "4":
                    holeIdPart4Spinner.setSelection(3);
                    break;
            }

            holeIdPart5EditText.setText(holeViewModel.getHoleId5());

            holeStartDateTextView.setText(Utility.formatCalendarDateString(holeViewModel.getStartDate()));
            holeEndDateTextView.setText(Utility.formatCalendarDateString(holeViewModel.getEndDate()));
        }
    }

    private void setHoleIdSpecialControllers() {
        holeIdPart2Spinner.setVisibility(View.GONE);
        holeIdPart3EditText.setVisibility(View.GONE);
        holeIdPart4Spinner.setVisibility(View.GONE);
        holeIdPart5EditText.setVisibility(View.GONE);
        holeIdSpecialIdEditText.setVisibility(View.VISIBLE);
    }

    private void setHoleIdNormalControllers() {
        holeIdPart2Spinner.setVisibility(View.VISIBLE);
        holeIdPart3EditText.setVisibility(View.VISIBLE);
        holeIdPart4Spinner.setVisibility(View.VISIBLE);
        holeIdPart5EditText.setVisibility(View.VISIBLE);
        holeIdSpecialIdEditText.setVisibility(View.GONE);
    }
}

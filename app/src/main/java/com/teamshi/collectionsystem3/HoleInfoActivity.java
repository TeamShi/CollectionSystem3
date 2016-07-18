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

import org.w3c.dom.Text;

public class HoleInfoActivity extends AppCompatActivity {
    private static final String TAG = "CollectionSystem3";

    private static final String[] HOLE_ID_PART1_SPINNER_OPTIONS = {"JC", "JZ", "其他"};
    private static final String[] HOLE_ID_PART2_SPINNER_OPTIONS = {"I", "II", "III", "IV"};
    private static final String[] HOLE_ID_PART4_SPINNER_OPTIONS = {"1", "2", "3", "4"};

    private TextView projectNameTextView;

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
                    holeIdPart2Spinner.setVisibility(View.GONE);
                    holeIdPart3EditText.setVisibility(View.GONE);
                    holeIdPart4Spinner.setVisibility(View.GONE);
                    holeIdPart5EditText.setVisibility(View.GONE);
                    holeIdSpecialIdEditText.setVisibility(View.VISIBLE);
                } else {
                    Log.d(TAG, "Normal hole id selected.");
                    holeIdPart2Spinner.setVisibility(View.VISIBLE);
                    holeIdPart3EditText.setVisibility(View.VISIBLE);
                    holeIdPart4Spinner.setVisibility(View.VISIBLE);
                    holeIdPart5EditText.setVisibility(View.VISIBLE);
                    holeIdSpecialIdEditText.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }
}

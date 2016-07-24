package com.teamshi.collectionsystem3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TableLayout;

import com.teamshi.collectionsystem3.datastructure.Project;

public class RigIndexActivity extends AppCompatActivity {
    private Project project;

    private TableLayout rigListTableLayout;

    private Spinner holeListSpinner;
    private ArrayAdapter<String> holeListSpinnerAdapter;
    private String[] holeListSpinnerOptions;

    private Button addRigButton;

    private String selectedHoleId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rig_index);

        project = DataManager.getProject();

        rigListTableLayout = (TableLayout) findViewById(R.id.tablelayout_riglist);

        holeListSpinner = (Spinner)findViewById(R.id.spinner_hole_list);
        addRigButton = (Button) findViewById(R.id.button_add_rig);

        holeListSpinnerOptions = DataManager.getHoleIdOptionList();
        holeListSpinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, holeListSpinnerOptions);
        holeListSpinner.setAdapter(holeListSpinnerAdapter);

        addRigButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        holeListSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        selectedHoleId = getIntent().getStringExtra("holeId");

        for (int i = 0; i < holeListSpinnerOptions.length; i++) {
            if (holeListSpinnerOptions[i].equals(selectedHoleId)) {
                holeListSpinner.setSelection(i);
                break;
            }
        }
    }
}

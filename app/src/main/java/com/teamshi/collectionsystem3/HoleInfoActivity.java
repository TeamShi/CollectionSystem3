package com.teamshi.collectionsystem3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.teamshi.collectionsystem3.datastructure.Hole;

import java.util.Calendar;

public class HoleInfoActivity extends AppCompatActivity {
    private static final String TAG = "CollectionSystem3";

    private static final int ACTION_ADD_HOLE = 1;

    private Hole holeViewModel;

    private Button confirmAddHoleButton;
    private Button cancelAddHoleButton;

    private static final String[] HOLE_ID_PART1_SPINNER_OPTIONS = {"JC", "JZ", "其他"};
    private static final String[] HOLE_ID_PART2_SPINNER_OPTIONS = {"I", "II", "III", "IV"};
    private static final String[] HOLE_ID_PART4_SPINNER_OPTIONS = {"1", "2", "3", "4"};
    private static final String[] ARTICLE_SPINNER_OPTIONS = {"K", "DK", "AK", "ACK", "CDK", "其他"};

    private TextView projectNameTextView;
    private TextView holeStartDateTextView;
    private TextView holeEndDateTextView;

    private Spinner holeIdPart1Spinner;
    private Spinner holeIdPart2Spinner;
    private EditText holeIdPart3EditText;
    private Spinner holeIdPart4Spinner;
    private EditText holeIdPart5EditText;
    private EditText holeIdSpecialIdEditText;

    private Spinner articleSpinner;
    private EditText articleEditText;
    private Button articleSpecialCancelButton;
    private EditText rigMachineTypeEditText;

    private ArrayAdapter<String> holeIdPart1SpinnerAdapter;
    private ArrayAdapter<String> holeIdPart2SpinnerAdapter;
    private ArrayAdapter<String> holeIdPart4SpinnerAdapter;
    private ArrayAdapter<String> articleSpinnerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "Start HoleInfoActivity.");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hole_info);

        confirmAddHoleButton = (Button) findViewById(R.id.button_confirm_add_hole);
        cancelAddHoleButton = (Button) findViewById(R.id.button_cancel_add_hole);

        projectNameTextView = (TextView) findViewById(R.id.textview_hole_info_project_name);
        holeStartDateTextView = (TextView) findViewById(R.id.textview_hole_start_date);
        holeEndDateTextView = (TextView) findViewById(R.id.textview_hole_end_date);

        holeIdPart1Spinner = (Spinner) findViewById(R.id.spinner_hole_id_part1);
        holeIdPart2Spinner = (Spinner) findViewById(R.id.spinner_hole_id_part2);
        holeIdPart3EditText = (EditText) findViewById(R.id.edittext_hole_id_part3);
        holeIdPart4Spinner = (Spinner) findViewById(R.id.spinner_hole_id_part4);
        holeIdPart5EditText = (EditText) findViewById(R.id.edittext_hole_id_part5);
        holeIdSpecialIdEditText = (EditText) findViewById(R.id.edittext_hole_special_id);

        articleSpinner = (Spinner) findViewById(R.id.spinner_article);
        articleEditText = (EditText) findViewById(R.id.edittext_article);
        articleSpecialCancelButton = (Button) findViewById(R.id.button_cancel_special_article);
        rigMachineTypeEditText = (EditText) findViewById(R.id.edittext_rig_machine_type);

        holeIdPart1SpinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, HOLE_ID_PART1_SPINNER_OPTIONS);
        holeIdPart1Spinner.setAdapter(holeIdPart1SpinnerAdapter);
        holeIdPart2SpinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, HOLE_ID_PART2_SPINNER_OPTIONS);
        holeIdPart2Spinner.setAdapter(holeIdPart2SpinnerAdapter);
        holeIdPart4SpinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, HOLE_ID_PART4_SPINNER_OPTIONS);
        holeIdPart4Spinner.setAdapter(holeIdPart4SpinnerAdapter);

        articleSpinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, ARTICLE_SPINNER_OPTIONS);
        articleSpinner.setAdapter(articleSpinnerAdapter);

        holeIdPart1Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String currentYearString = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));

                switch (position) {
                    case 0:
                        if (holeViewModel.isSpecialHoleId()) {
                            holeViewModel.setHoleIdPart2("I");
                            holeViewModel.setHoleIdPart3(currentYearString.substring(currentYearString.length() - 2));
                            holeViewModel.setHoleIdPart4("1");
                            holeViewModel.setHoleIdPart5("1");
                        }

                        holeViewModel.setSpecialHoleId(false);
                        holeViewModel.setHoleIdPart1("JC");

                        holeViewModel.setSpecialHoleId("");
                        setHoleIdNormalControllers();

                        refreshInfo();
                        break;
                    case 1:
                        if (holeViewModel.isSpecialHoleId()) {
                            holeViewModel.setHoleIdPart2("I");
                            holeViewModel.setHoleIdPart3(currentYearString.substring(currentYearString.length() - 2));
                            holeViewModel.setHoleIdPart4("1");
                            holeViewModel.setHoleIdPart5("1");
                        }

                        holeViewModel.setSpecialHoleId(false);
                        holeViewModel.setHoleIdPart1("JZ");

                        holeViewModel.setSpecialHoleId("");
                        setHoleIdNormalControllers();

                        refreshInfo();
                        break;
                    case 2:
                        holeViewModel.setSpecialHoleId(true);
                        setHoleIdSpecialControllers();

                        refreshInfo();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        holeIdPart2Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        holeViewModel.setHoleIdPart2("I");
                        break;
                    case 1:
                        holeViewModel.setHoleIdPart2("II");
                        break;
                    case 2:
                        holeViewModel.setHoleIdPart2("III");
                        break;
                    case 3:
                        holeViewModel.setHoleIdPart2("IV");
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        holeIdPart3EditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                holeViewModel.setHoleIdPart3(s.toString());
            }
        });

        holeIdPart4Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        holeViewModel.setHoleIdPart4("1");
                        break;
                    case 1:
                        holeViewModel.setHoleIdPart4("2");
                        break;
                    case 2:
                        holeViewModel.setHoleIdPart4("3");
                        break;
                    case 3:
                        holeViewModel.setHoleIdPart4("4");
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        holeIdPart5EditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                holeViewModel.setHoleIdPart5(s.toString());
            }
        });

        holeIdSpecialIdEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                holeViewModel.setSpecialHoleId(s.toString());
            }
        });

        articleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        holeViewModel.setSpecialArticle(false);
                        holeViewModel.setArticle("K");
                        setArticleNormalControllers();
                        break;
                    case 1:
                        holeViewModel.setSpecialArticle(false);
                        holeViewModel.setArticle("DK");
                        setArticleNormalControllers();
                        break;
                    case 2:
                        holeViewModel.setSpecialArticle(false);
                        holeViewModel.setArticle("AK");
                        setArticleNormalControllers();
                        break;
                    case 3:
                        holeViewModel.setSpecialArticle(false);
                        holeViewModel.setArticle("ACK");
                        setArticleNormalControllers();
                        break;
                    case 4:
                        holeViewModel.setSpecialArticle(false);
                        holeViewModel.setArticle("CDK");
                        setArticleNormalControllers();
                        break;
                    case 5:
                        holeViewModel.setSpecialArticle(true);
                        holeViewModel.setArticle("");
                        setArticleSpecialControllers();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        articleSpecialCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holeViewModel.setSpecialArticle(false);
                holeViewModel.setArticle("DK");

                setArticleNormalControllers();
                refreshInfo();
            }
        });

        articleEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                holeViewModel.setSpecialArticle(true);
                holeViewModel.setArticle(s.toString());
            }
        });

        rigMachineTypeEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                holeViewModel.setRigMachineType(s.toString());
            }
        });

        confirmAddHoleButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String requestCode = getIntent().getStringExtra("requestCode");

                switch (requestCode) {
                    case "ACTION_ADD_HOLE":
                    case "ACTION_COPY_HOLE":
                        if (validateAdding()) {
                            DataManager.getProject().getHoleList().add(holeViewModel);
                            HoleInfoActivity.this.setResult(RESULT_OK);
                            HoleInfoActivity.this.finish();
                        }
                        break;
                    case "ACTION_EDIT_HOLE":
                        if (validateUpdating()) {
                            Hole oldHole = DataManager.getHole(getIntent().getStringExtra("holeId"));

                            DataManager.updateHole(oldHole.getHoleId(), holeViewModel);

                            HoleInfoActivity.this.setResult(RESULT_OK);
                            HoleInfoActivity.this.finish();
                        }
                        break;
                }

            }
        });

        cancelAddHoleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HoleInfoActivity.this.setResult(RESULT_CANCELED);
                HoleInfoActivity.this.finish();
            }
        });

        String requestCode = getIntent().getStringExtra("requestCode");

        switch (requestCode) {
            case "ACTION_ADD_HOLE":
                holeViewModel = new Hole(getIntent().getStringExtra("projectName"));

                refreshInfo();
                break;
            case "ACTION_COPY_HOLE":
                holeViewModel = new Hole();
                Hole oldHole = DataManager.getHole(getIntent().getStringExtra("holeId"));

                holeViewModel.setProjectName(oldHole.getProjectName());
                holeViewModel.setStartDate(oldHole.getStartDate());
                holeViewModel.setEndDate(oldHole.getEndDate());

                holeViewModel.setSpecialHoleId(oldHole.isSpecialHoleId());
                holeViewModel.setHoleIdPart1(oldHole.getHoleIdPart1());
                holeViewModel.setHoleIdPart2(oldHole.getHoleIdPart2());
                holeViewModel.setHoleIdPart3(oldHole.getHoleIdPart3());
                holeViewModel.setHoleIdPart4(oldHole.getHoleIdPart4());
                holeViewModel.setHoleIdPart5(oldHole.getHoleIdPart5());

                holeViewModel.setSpecialHoleId(oldHole.isSpecialHoleId());
                holeViewModel.setSpecialHoleId(oldHole.getSpecialHoleId());

                holeViewModel.setStartDate(oldHole.getStartDate());
                holeViewModel.setEndDate(oldHole.getEndDate());

                holeViewModel.setArticle(oldHole.getArticle());
                holeViewModel.setRigMachineType(oldHole.getRigMachineType());

                refreshInfo();
                break;
            case "ACTION_EDIT_HOLE":
                holeViewModel = DataManager.getHole(getIntent().getStringExtra("holeId")).deepCopy();

                refreshInfo();
                break;
            default:
                break;
        }

        refreshInfo();
    }

    private void refreshInfo() {
        projectNameTextView.setText(this.holeViewModel.getProjectName());

        if (this.holeViewModel.isSpecialHoleId()) {
            holeIdPart1Spinner.setSelection(2);

            holeIdSpecialIdEditText.setText(holeViewModel.getSpecialHoleId());

            setHoleIdSpecialControllers();
        } else {
            switch (holeViewModel.getHoleIdPart1()) {
                case "JC":
                    holeIdPart1Spinner.setSelection(0);
                    break;
                case "JZ":
                    holeIdPart1Spinner.setSelection(1);
                    break;
            }

            switch (holeViewModel.getHoleIdPart2()) {
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

            holeIdPart3EditText.setText(holeViewModel.getHoleIdPart3());

            switch (holeViewModel.getHoleIdPart4()) {
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

            holeIdPart5EditText.setText(holeViewModel.getHoleIdPart5());

            holeStartDateTextView.setText(Utility.formatCalendarDateString(holeViewModel.getStartDate()));
            holeEndDateTextView.setText(Utility.formatCalendarDateString(holeViewModel.getEndDate()));
        }

        if (this.holeViewModel.isSpecialArticle()) {
            setArticleSpecialControllers();

            articleEditText.setText(holeViewModel.getArticle());
            articleSpinner.setSelection(5);
        } else {
            setArticleNormalControllers();

            switch (holeViewModel.getArticle()) {
                case "K":
                    articleSpinner.setSelection(0);
                    break;
                case "DK":
                    articleSpinner.setSelection(1);
                    break;
                case "AK":
                    articleSpinner.setSelection(2);
                    break;
                case "ACK":
                    articleSpinner.setSelection(3);
                    break;
                case "CDK":
                    articleSpinner.setSelection(4);
                    break;
            }
        }

        rigMachineTypeEditText.setText(holeViewModel.getRigMachineType());
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

    private void setArticleSpecialControllers() {
        articleSpinner.setVisibility(View.GONE);
        articleEditText.setVisibility(View.VISIBLE);
        articleSpecialCancelButton.setVisibility(View.VISIBLE);
    }

    private void setArticleNormalControllers() {
        articleSpinner.setVisibility(View.VISIBLE);
        articleEditText.setVisibility(View.GONE);
        articleSpecialCancelButton.setVisibility(View.GONE);
    }

    private boolean validateAdding() {
        if (holeViewModel.getHoleId().equals("")) {
            Toast.makeText(getApplicationContext(), "钻探编号不能为空", Toast.LENGTH_LONG).show();
            return false;
        }

        if (DataManager.isHoleExistInProject(holeViewModel.getHoleId())) {
            Toast.makeText(getApplicationContext(), "钻探编号已存在", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    private boolean validateUpdating() {
        if (holeViewModel.getHoleId().equals("")) {
            Toast.makeText(getApplicationContext(), "钻探编号不能为空", Toast.LENGTH_LONG).show();
            return false;
        }

        Hole oldHole = DataManager.getHole(getIntent().getStringExtra("holeId"));
        if (!holeViewModel.getHoleId().equals(oldHole.getHoleId()) && DataManager.isHoleExistInProject(holeViewModel.getHoleId())) {
            Toast.makeText(getApplicationContext(), "钻探编号已存在", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

}

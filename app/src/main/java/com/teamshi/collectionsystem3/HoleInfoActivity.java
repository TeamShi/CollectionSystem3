package com.teamshi.collectionsystem3;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.teamshi.collectionsystem3.datastructure.Hole;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class HoleInfoActivity extends AppCompatActivity {
    private static final String TAG = "CollectionSystem3";

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

    private EditText mileageEditText;
    private EditText engineTypeEditText;
    private EditText offsetEditText;
    private EditText pumpTypeEditText;
    private EditText holeHeightEditText;
    private EditText holeDepthEditText;

    private CheckBox initialWaterDepthCheckBox;
    private EditText initialWaterDepthEditText;
    private CheckBox finalWaterDepthCheckBox;
    private EditText finalWaterDepthEditText;

    private TextView initialWaterDepthDateTextView;
    private TextView finalWaterDepthDateTextView;

    private EditText longtitudeEditText;
    private EditText latitudeEditText;

    private EditText companyEditText;
    private EditText machineIdEditText;

    private TextView holeDescImageView;
    private Button takeHolePhotoButton;

    private EditText noteEditText;
    private EditText positionInformationEditText;

    private TextView recorderTextView;
    private TextView reviewerTextView;
    private Button signRecorderButton;
    private Button signReviewerButton;
    private TextView recordDateTextView;
    private TextView reviewDateTextView;

    private TextView classMonitorTextView;
    private TextView machineMonitorTextView;
    private Button signClassMonitorButton;
    private Button signMachineMonitorButton;

    private TextView signApproverTextView;
    private Button signApproverButton;

    private Button offsetNegativeButton;
    private Button offsetPositiveButton;

    private boolean refreshLock = false;

    private static final int TAKE_PHOTO = 0;
    private static final int CROP_PHOTO = 1;
    private static final int CAP_SIGN_RECORDER = 2;
    private static final int CAP_SIGN_REVIEWER = 3;
    private static final int CAP_SIGN_APPROVER = 4;
    private static final int CAP_SIGN_MMONITOR = 5;
    private static final int CAP_SIGN_CMONITOR = 6;
    private static HashMap<String, File> tempImagsMap = new HashMap<>();
    public final String JPG_HOLE_DESC = "hole_desc";
    public final String JPG_SIGN_RECO = "sign_recorder";
    public final String JPG_SIGN_REVI = "sign_reviewer";
    public final String JPG_SIGN_APPR = "sign_approver";
    public final String JPG_SIGN_MMON = "sign_mmonitor";
    public final String JPG_SIGN_CMON = "sign_cmonitor";

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case TAKE_PHOTO:
                Intent intent = new Intent("com.android.camera.action.CROP");

                Uri tempHoleImageUri = Uri.fromFile(tempImagsMap.get(JPG_HOLE_DESC));
                intent.setDataAndType(tempHoleImageUri, "image/*");
                intent.putExtra("scale", true);

                intent.putExtra("aspectX", 1);
                intent.putExtra("aspectY", 1);
                intent.putExtra("outputX", 800);
                intent.putExtra("outputY", 600);

                intent.putExtra(MediaStore.EXTRA_OUTPUT, tempHoleImageUri);

                startActivityForResult(intent, CROP_PHOTO);
                break;
            case CROP_PHOTO:
                Bitmap bitmap = null;
                tempHoleImageUri = Uri.fromFile(tempImagsMap.get(JPG_HOLE_DESC));
                try {
                    bitmap = BitmapFactory.decodeStream(
                            getContentResolver().openInputStream(tempHoleImageUri));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                Drawable background = new BitmapDrawable(bitmap);
                if (null != background) {
                    Toast.makeText(this, "照片保存成功", Toast.LENGTH_SHORT).show();
                    holeDescImageView.setText("");
                    holeDescImageView.setBackground(background);
                } else {
                    tempImagsMap.put(JPG_HOLE_DESC, null);
                    Toast.makeText(this, "照片保存失败", Toast.LENGTH_SHORT).show();
                }
                break;
            case CAP_SIGN_RECORDER:
                if (resultCode == Activity.RESULT_OK) {
                    bitmap = null;
                    tempHoleImageUri = Uri.fromFile(tempImagsMap.get(JPG_SIGN_RECO));
                    try {
                        bitmap = BitmapFactory.decodeStream(
                                getContentResolver().openInputStream(tempHoleImageUri));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    background = new BitmapDrawable(bitmap);
                    if (null != background) {
                        Toast.makeText(this, "签名保存成功", Toast.LENGTH_SHORT).show();
                        recorderTextView.setText("");
                        recorderTextView.setBackground(background);
                    } else {
                        tempImagsMap.put(JPG_SIGN_RECO, null);
                        Toast.makeText(this, "签名保存失败", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "签名保存失败", Toast.LENGTH_SHORT).show();
                }
                break;
            case CAP_SIGN_REVIEWER:
                if (resultCode == Activity.RESULT_OK) {
                    bitmap = null;
                    tempHoleImageUri = Uri.fromFile(tempImagsMap.get(JPG_SIGN_REVI));
                    try {
                        bitmap = BitmapFactory.decodeStream(
                                getContentResolver().openInputStream(tempHoleImageUri));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    background = new BitmapDrawable(bitmap);
                    if (null != background) {
                        Toast.makeText(this, "签名保存成功", Toast.LENGTH_SHORT).show();
                        reviewerTextView.setText("");
                        reviewerTextView.setBackground(background);
                    } else {
                        tempImagsMap.put(JPG_SIGN_REVI, null);
                        Toast.makeText(this, "签名保存失败", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "签名保存失败", Toast.LENGTH_SHORT).show();
                }
                break;
            case CAP_SIGN_APPROVER:
                if (resultCode == Activity.RESULT_OK) {
                    holeViewModel.setApproved(true);
                    bitmap = null;
                    tempHoleImageUri = Uri.fromFile(tempImagsMap.get(JPG_SIGN_APPR));
                    try {
                        bitmap = BitmapFactory.decodeStream(
                                getContentResolver().openInputStream(tempHoleImageUri));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    background = new BitmapDrawable(bitmap);
                    if (null != background) {
                        Toast.makeText(this, "签名保存成功", Toast.LENGTH_SHORT).show();
                        signApproverTextView.setText("");
                        signApproverTextView.setBackground(background);
                    } else {
                        tempImagsMap.put(JPG_SIGN_APPR, null);
                        Toast.makeText(this, "签名保存失败", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "签名保存失败", Toast.LENGTH_SHORT).show();
                }
                break;
            case CAP_SIGN_MMONITOR:
                if (resultCode == Activity.RESULT_OK) {
                    bitmap = null;
                    tempHoleImageUri = Uri.fromFile(tempImagsMap.get(JPG_SIGN_MMON));
                    try {
                        bitmap = BitmapFactory.decodeStream(
                                getContentResolver().openInputStream(tempHoleImageUri));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    background = new BitmapDrawable(bitmap);
                    if (null != background) {
                        Toast.makeText(this, "签名保存成功", Toast.LENGTH_SHORT).show();
                        machineMonitorTextView.setText("");
                        machineMonitorTextView.setBackground(background);
                    } else {
                        tempImagsMap.put(JPG_SIGN_MMON, null);
                        Toast.makeText(this, "签名保存失败", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "签名保存失败", Toast.LENGTH_SHORT).show();
                }
                break;
            case CAP_SIGN_CMONITOR:
                if (resultCode == Activity.RESULT_OK) {
                    bitmap = null;
                    tempHoleImageUri = Uri.fromFile(tempImagsMap.get(JPG_SIGN_CMON));
                    try {
                        bitmap = BitmapFactory.decodeStream(
                                getContentResolver().openInputStream(tempHoleImageUri));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    background = new BitmapDrawable(bitmap);
                    if (null != background) {
                        Toast.makeText(this, "签名保存成功", Toast.LENGTH_SHORT).show();
                        classMonitorTextView.setText("");
                        classMonitorTextView.setBackground(background);
                    } else {
                        tempImagsMap.put(JPG_SIGN_CMON, null);
                        Toast.makeText(this, "签名保存失败", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "签名保存失败", Toast.LENGTH_SHORT).show();
                }
                break;

            default:
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "Start HoleInfoActivity.");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hole_info);

        this.setTitle("作业点原始数据录入");

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

        mileageEditText = (EditText) findViewById(R.id.edittext_mileage);
        engineTypeEditText = (EditText) findViewById(R.id.edittext_engine_type);
        offsetEditText = (EditText) findViewById(R.id.edittext_offset);
        pumpTypeEditText = (EditText) findViewById(R.id.edittext_pump_type);
        holeHeightEditText = (EditText) findViewById(R.id.edittext_hole_height);
        holeDepthEditText = (EditText) findViewById(R.id.edittext_hole_depth);

        initialWaterDepthCheckBox = (CheckBox) findViewById(R.id.checkbox_initial_water_depth);
        initialWaterDepthEditText = (EditText) findViewById(R.id.edittext_initial_water_depth);
        initialWaterDepthDateTextView = (TextView) findViewById(R.id.textview_initial_water_depth_date);

        finalWaterDepthCheckBox = (CheckBox) findViewById(R.id.checkbox_final_water_depth);
        finalWaterDepthEditText = (EditText) findViewById(R.id.edittext_final_water_depth);
        finalWaterDepthDateTextView = (TextView) findViewById(R.id.textview_final_water_depth_date);

        longtitudeEditText = (EditText) findViewById(R.id.edittext_longtitude);
        latitudeEditText = (EditText) findViewById(R.id.edittext_latitude);

        companyEditText = (EditText) findViewById(R.id.edittext_company);
        machineIdEditText = (EditText) findViewById(R.id.edittext_machine_id);

        holeDescImageView = (TextView) findViewById(R.id.image_view);
        takeHolePhotoButton = (Button) findViewById(R.id.button_take_hole_photo);

        noteEditText = (EditText) findViewById(R.id.edittext_note);
        positionInformationEditText = (EditText) findViewById(R.id.edittext_position_information);

        recorderTextView = (TextView) findViewById(R.id.textview_recorder);
        reviewerTextView = (TextView) findViewById(R.id.textview_reviewer);
        recordDateTextView = (TextView) findViewById(R.id.textview_record_date);
        reviewDateTextView = (TextView) findViewById(R.id.textview_review_date);

        signRecorderButton = (Button) findViewById(R.id.button_sign_recorder);
        signReviewerButton = (Button) findViewById(R.id.button_sign_reviewer);

        classMonitorTextView = (TextView) findViewById(R.id.textview_class_monitor);
        machineMonitorTextView = (TextView) findViewById(R.id.textview_machine_monitor);

        signClassMonitorButton = (Button) findViewById(R.id.button_sign_class_monitor);
        signMachineMonitorButton = (Button) findViewById(R.id.button_sign_machine_monitor);

        signApproverTextView = (TextView) findViewById(R.id.textview_sign_approver);
        signApproverButton = (Button) findViewById(R.id.button_sign_approver);

        offsetNegativeButton = (Button) findViewById(R.id.button_offset_negative);
        offsetPositiveButton = (Button) findViewById(R.id.button_offset_positve);

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
                        break;
                    case 2:
                        holeViewModel.setSpecialHoleId(true);
                        break;
                }

                refreshInfo();
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

                refreshInfo();
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

                refreshInfo();
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
                        break;
                    case 1:
                        holeViewModel.setSpecialArticle(false);
                        holeViewModel.setArticle("DK");
                        break;
                    case 2:
                        holeViewModel.setSpecialArticle(false);
                        holeViewModel.setArticle("AK");
                        break;
                    case 3:
                        holeViewModel.setSpecialArticle(false);
                        holeViewModel.setArticle("ACK");
                        break;
                    case 4:
                        holeViewModel.setSpecialArticle(false);
                        holeViewModel.setArticle("CDK");
                        break;
                    case 5:
                        holeViewModel.setSpecialArticle(true);
                        holeViewModel.setArticle("");
                        break;
                }

                refreshInfo();
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

        mileageEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    holeViewModel.setMileage(Double.parseDouble(s.toString()));
                    mileageEditText.setTextColor(getResources().getColor(android.R.color.black));
                } catch (Exception e) {
                    mileageEditText.setTextColor(getResources().getColor(android.R.color.holo_red_light));
                }
            }
        });

        engineTypeEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                holeViewModel.setEngineType(s.toString());
            }
        });

        offsetEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    if (!refreshLock) {
                        holeViewModel.setOffset(Double.parseDouble(s.toString()));
                        offsetEditText.setTextColor(getResources().getColor(android.R.color.black));

                    }

                } catch (Exception e) {
                    offsetEditText.setTextColor(getResources().getColor(android.R.color.holo_red_light));
                }
            }
        });

        pumpTypeEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                holeViewModel.setPumpType(s.toString());
            }
        });

        holeHeightEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    holeViewModel.setHoleHeight(Double.parseDouble(s.toString()));
                    holeHeightEditText.setTextColor(getResources().getColor(android.R.color.black));
                } catch (Exception e) {
                    holeHeightEditText.setTextColor(getResources().getColor(android.R.color.holo_red_light));
                }
            }
        });

        holeDepthEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    holeViewModel.setHoleDepth(Double.parseDouble(s.toString()));
                    holeDepthEditText.setTextColor(getResources().getColor(android.R.color.black));
                } catch (Exception e) {
                    holeDepthEditText.setTextColor(getResources().getColor(android.R.color.holo_red_light));
                }
            }
        });

        initialWaterDepthCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    holeViewModel.setInitialWaterDepth(-1);
                } else {
                    holeViewModel.setInitialWaterDepth(0);
                }

                refreshInfo();
            }
        });

        finalWaterDepthCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    holeViewModel.setFinalWaterDepth(-1);
                } else {
                    holeViewModel.setFinalWaterDepth(0);
                }

                refreshInfo();
            }
        });

        initialWaterDepthEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().equals("")) {
                    holeViewModel.setInitialWaterDepth(-1);
                } else {
                    holeViewModel.setInitialWaterDepth(Double.parseDouble(s.toString()));
                }
            }
        });

        finalWaterDepthEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().equals("")) {
                    holeViewModel.setFinalWaterDepth(-1);
                } else {
                    holeViewModel.setFinalWaterDepth(Double.parseDouble(s.toString()));
                }
                holeViewModel.setFinalWaterDepthLoggedDate(Calendar.getInstance());
            }
        });

        longtitudeEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    holeViewModel.setLongitude(Double.parseDouble(s.toString()));
                    longtitudeEditText.setTextColor(getResources().getColor(android.R.color.black));
                } catch (Exception e) {
                    longtitudeEditText.setTextColor(getResources().getColor(android.R.color.holo_red_light));
                }
            }
        });

        latitudeEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    holeViewModel.setLatitude(Double.parseDouble(s.toString()));
                    latitudeEditText.setTextColor(getResources().getColor(android.R.color.black));
                } catch (Exception e) {
                    latitudeEditText.setTextColor(getResources().getColor(android.R.color.holo_red_light));
                }
            }
        });

        companyEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                holeViewModel.setCompany(s.toString());
            }
        });

        machineIdEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                holeViewModel.setMachineId(s.toString());
            }
        });

        takeHolePhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File file = IOManager.getTempJpgFile();
                tempImagsMap.put(JPG_HOLE_DESC, file);
                Uri uri = Uri.fromFile(file);
                //拍照
                Intent photoIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                //指定图片输出地址
                photoIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                startActivityForResult(photoIntent, TAKE_PHOTO);
            }
        });

        noteEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                holeViewModel.setNote(s.toString());
            }
        });

        positionInformationEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                holeViewModel.setPositionInformation(s.toString());
            }
        });

        confirmAddHoleButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String requestCode = getIntent().getStringExtra("requestCode");

                switch (requestCode) {
                    case "ACTION_ADD_HOLE":
                        if (validateAdding()) {
                            DataManager.getProject().getHoleList().add(holeViewModel);
                            IOManager.updateProject(DataManager.getProject());
                            IOManager.copyImagesFromTemp(tempImagsMap, holeViewModel);
                            HoleInfoActivity.this.setResult(RESULT_OK);
                            HoleInfoActivity.this.finish();
                        }
                        break;
                    case "ACTION_COPY_HOLE":
                        if (validateAdding()) {
                            //TODO clone is not avail since holeid is same , hole dir is same
                            DataManager.getProject().getHoleList().add(holeViewModel);
                            IOManager.updateProject(DataManager.getProject());
//                            IOManager.copyHoleFiles(holeViewModel);
                            HoleInfoActivity.this.setResult(RESULT_OK);
                            HoleInfoActivity.this.finish();
                        }
                        break;
                    case "ACTION_EDIT_HOLE":
                        if (validateUpdating()) {
                            Hole oldHole = DataManager.getHole(getIntent().getStringExtra("holeId"));
                            DataManager.updateHole(oldHole.getHoleId(), holeViewModel);
                            IOManager.copyImagesFromTemp(tempImagsMap, holeViewModel);
                            IOManager.updateProject(DataManager.getProject());
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

        offsetPositiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holeViewModel.getOffset() < 0) {
                    holeViewModel.setOffset(0 - holeViewModel.getOffset());

                    refreshInfo();
                }
            }
        });

        offsetNegativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holeViewModel.getOffset() > 0) {
                    holeViewModel.setOffset(0 - holeViewModel.getOffset());

                    refreshInfo();
                }
            }
        });

        signRecorderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HoleInfoActivity.this, CaptureSignature.class);
                File file = IOManager.getTempJpgFile();
                intent.putExtra("path", file.getAbsolutePath());
                tempImagsMap.put(JPG_SIGN_RECO, file);
                startActivityForResult(intent, CAP_SIGN_RECORDER);
            }
        });

        signReviewerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HoleInfoActivity.this, CaptureSignature.class);
                File file = IOManager.getTempJpgFile();
                String signaturePath = file.getAbsolutePath();
                intent.putExtra("path", signaturePath);
                tempImagsMap.put(JPG_SIGN_REVI, file);
                startActivityForResult(intent, CAP_SIGN_REVIEWER);
            }
        });

        signMachineMonitorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HoleInfoActivity.this, CaptureSignature.class);
                File file = IOManager.getTempJpgFile();
                String signaturePath = file.getAbsolutePath();
                intent.putExtra("path", signaturePath);
                tempImagsMap.put(JPG_SIGN_MMON, file);
                startActivityForResult(intent, CAP_SIGN_MMONITOR);

            }
        });

        signClassMonitorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HoleInfoActivity.this, CaptureSignature.class);
                File file = IOManager.getTempJpgFile();
                String signaturePath = file.getAbsolutePath();
                intent.putExtra("path", signaturePath);
                tempImagsMap.put(JPG_SIGN_CMON, file);
                startActivityForResult(intent, CAP_SIGN_CMONITOR);

            }
        });

        signApproverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HoleInfoActivity.this, CaptureSignature.class);
                File file = IOManager.getTempJpgFile();
                String signaturePath = file.getAbsolutePath();
                intent.putExtra("path", signaturePath);
                tempImagsMap.put(JPG_SIGN_APPR, file);
                startActivityForResult(intent, CAP_SIGN_APPROVER);
            }
        });

        // empty temp files
        IOManager.emptyTempDir();
        tempImagsMap.clear();

        String requestCode = getIntent().getStringExtra("requestCode");

        switch (requestCode) {
            case "ACTION_ADD_HOLE":
                holeViewModel = new Hole(getIntent().getStringExtra("projectName"));
                break;
            case "ACTION_COPY_HOLE":

                Hole oldHole = DataManager.getHole(getIntent().getStringExtra("holeId"));

                holeViewModel = new Hole(oldHole.getProjectName());

                holeViewModel.setSpecialHoleId(oldHole.isSpecialHoleId());
                holeViewModel.setHoleIdPart1(oldHole.getHoleIdPart1());
                holeViewModel.setHoleIdPart2(oldHole.getHoleIdPart2());
                holeViewModel.setHoleIdPart3(oldHole.getHoleIdPart3());
                holeViewModel.setHoleIdPart4(oldHole.getHoleIdPart4());
                holeViewModel.setHoleIdPart5(oldHole.getHoleIdPart5());

                holeViewModel.setSpecialHoleId(oldHole.isSpecialHoleId());
                holeViewModel.setSpecialHoleId(oldHole.getSpecialHoleId());

                holeViewModel.setArticle(oldHole.getArticle());
                holeViewModel.setRigMachineType(oldHole.getRigMachineType());

                holeViewModel.setEngineType(oldHole.getEngineType());
                holeViewModel.setPumpType(oldHole.getPumpType());
                holeViewModel.setCompany(oldHole.getCompany());
                holeViewModel.setMachineId(oldHole.getMachineId());
                holeViewModel.setRecorder(oldHole.getRecorder());
                holeViewModel.setRecordDate((Calendar) oldHole.getRecordDate().clone());
                holeViewModel.setClassMonitor(oldHole.getClassMonitor());
                holeViewModel.setMachineMonitor(oldHole.getMachineMonitor());
                holeViewModel.setReviewer(oldHole.getReviewer());
                holeViewModel.setReviewDate((Calendar) oldHole.getReviewDate().clone());
                break;
            case "ACTION_EDIT_HOLE":
                holeViewModel = DataManager.getHole(getIntent().getStringExtra("holeId")).deepCopy();
                break;
            default:
                break;
        }

        refreshInfo();
    }

    private void refreshInfo() {
        if (refreshLock) {
            return;
        }

        refreshLock = true;

        projectNameTextView.setText(this.holeViewModel.getProjectName());

        if (holeViewModel.isSpecialHoleId()) {
            holeIdPart1Spinner.setSelection(2);

            holeIdSpecialIdEditText.setText(holeViewModel.getSpecialHoleId());

            setHoleIdSpecialControllers();
        } else {
            setHoleIdNormalControllers();
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

        mileageEditText.setText(Utility.formatDouble(holeViewModel.getMileage()));
        engineTypeEditText.setText(holeViewModel.getEngineType());

        if (holeViewModel.getOffset() == 0) {
            offsetNegativeButton.setEnabled(false);
            offsetPositiveButton.setEnabled(false);
        } else if (holeViewModel.getOffset() > 0) {
            offsetNegativeButton.setEnabled(true);
            offsetPositiveButton.setEnabled(false);
        } else {
            offsetNegativeButton.setEnabled(false);
            offsetPositiveButton.setEnabled(true);
        }

        offsetEditText.setText(Utility.formatDouble(holeViewModel.getOffset()));

        pumpTypeEditText.setText(holeViewModel.getPumpType());
        holeHeightEditText.setText(Utility.formatDouble(holeViewModel.getHoleHeight()));
        holeDepthEditText.setText(Utility.formatDouble(holeViewModel.getHoleDepth()));

        if (holeViewModel.getInitialWaterDepth() == -1) {
            initialWaterDepthCheckBox.setChecked(true);
            initialWaterDepthEditText.setText("");
            initialWaterDepthEditText.setEnabled(false);
            initialWaterDepthDateTextView.setText("");
        } else {
            initialWaterDepthCheckBox.setChecked(false);
            initialWaterDepthEditText.setText(Utility.formatDouble(holeViewModel.getInitialWaterDepth()));
            initialWaterDepthEditText.setEnabled(true);
            initialWaterDepthDateTextView.setText(Utility.formatCalendarDateString(holeViewModel.getInitialWaterDepthLoggedDate()));
        }

        if (holeViewModel.getFinalWaterDepth() == -1) {
            finalWaterDepthCheckBox.setChecked(true);
            finalWaterDepthEditText.setText("");
            finalWaterDepthEditText.setEnabled(false);
            finalWaterDepthDateTextView.setText("");
        } else {
            finalWaterDepthCheckBox.setChecked(false);
            finalWaterDepthEditText.setText(Utility.formatDouble(holeViewModel.getFinalWaterDepth()));
            finalWaterDepthEditText.setEnabled(true);
            finalWaterDepthDateTextView.setText(Utility.formatCalendarDateString(holeViewModel.getFinalWaterDepthLoggedDate()));
        }

        longtitudeEditText.setText(String.valueOf(holeViewModel.getLongitude()));
        latitudeEditText.setText(String.valueOf(holeViewModel.getLatitude()));

        noteEditText.setText(holeViewModel.getNote());
        positionInformationEditText.setText(holeViewModel.getPositionInformation());

        companyEditText.setText(holeViewModel.getCompany());
        machineIdEditText.setText(holeViewModel.getMachineId());

        HashMap<String, File> holeImagesMap = IOManager.getHoleImages(holeViewModel);
        if (null != holeImagesMap) {
            for (Map.Entry<String, File> entry : holeImagesMap.entrySet()) {
                File image = entry.getValue();
                if (image != null && image.exists()) {
                    Bitmap bitmap = null;
                    Uri uri = Uri.fromFile(image);
                    try {
                        bitmap = BitmapFactory.decodeStream(
                                getContentResolver().openInputStream(uri));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    Drawable background = new BitmapDrawable(bitmap);
                    if (null != background) {
                        switch (entry.getKey()) {
                            case JPG_HOLE_DESC:
                                holeDescImageView.setText("");
                                holeDescImageView.setBackground(background);
                                break;
                            case JPG_SIGN_RECO:
                                recorderTextView.setText("");
                                recorderTextView.setBackground(background);
                                break;
                            case JPG_SIGN_REVI:
                                reviewerTextView.setText("");
                                reviewerTextView.setBackground(background);
                                break;
                            case JPG_SIGN_APPR:
                                signApproverTextView.setText("");
                                signApproverTextView.setBackground(background);
                                break;
                            case JPG_SIGN_CMON:
                                classMonitorTextView.setText("");
                                classMonitorTextView.setBackground(background);
                                break;
                            case JPG_SIGN_MMON:
                                machineMonitorTextView.setText("");
                                machineMonitorTextView.setBackground(background);
                                break;
                            default:
                                break;
                        }
                    }
                }
            }
        }

        recordDateTextView.setText(Utility.formatCalendarDateString(holeViewModel.getRecordDate()));
        reviewDateTextView.setText(Utility.formatCalendarDateString(holeViewModel.getReviewDate()));

        refreshLock = false;
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


        try {
            Double.parseDouble(mileageEditText.getText().toString());
        } catch (Exception e) {
            mileageEditText.setTextColor(getResources().getColor(android.R.color.holo_red_light));
            Toast.makeText(HoleInfoActivity.this, "里程数值非法", Toast.LENGTH_LONG).show();
            return false;
        }

        try {
            Double.parseDouble(offsetEditText.getText().toString());
        } catch (Exception e) {
            offsetEditText.setTextColor(getResources().getColor(android.R.color.holo_red_light));
            Toast.makeText(HoleInfoActivity.this, "偏移量数值非法", Toast.LENGTH_LONG).show();
            return false;
        }

        try {
            Double.parseDouble(holeHeightEditText.getText().toString());
        } catch (Exception e) {
            holeHeightEditText.setTextColor(getResources().getColor(android.R.color.holo_red_light));
            Toast.makeText(HoleInfoActivity.this, "孔口标高数值非法", Toast.LENGTH_LONG).show();
            return false;
        }

        try {
            Double.parseDouble(holeDepthEditText.getText().toString());
        } catch (Exception e) {
            holeDepthEditText.setTextColor(getResources().getColor(android.R.color.holo_red_light));
            Toast.makeText(HoleInfoActivity.this, "设计孔深数值非法", Toast.LENGTH_LONG).show();
            return false;
        }

        try {
            Double.parseDouble(latitudeEditText.getText().toString());
        } catch (Exception e) {
            latitudeEditText.setTextColor(getResources().getColor(android.R.color.holo_red_light));
            Toast.makeText(HoleInfoActivity.this, "经距数值非法", Toast.LENGTH_LONG).show();
            return false;
        }

        try {
            Double.parseDouble(longtitudeEditText.getText().toString());
        } catch (Exception e) {
            longtitudeEditText.setTextColor(getResources().getColor(android.R.color.holo_red_light));
            Toast.makeText(HoleInfoActivity.this, "纬距数值非法", Toast.LENGTH_LONG).show();
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

        try {
            Double.parseDouble(mileageEditText.getText().toString());
        } catch (Exception e) {
            mileageEditText.setTextColor(getResources().getColor(android.R.color.holo_red_light));
            Toast.makeText(HoleInfoActivity.this, "里程数值非法", Toast.LENGTH_LONG).show();
            return false;
        }

        try {
            Double.parseDouble(offsetEditText.getText().toString());
        } catch (Exception e) {
            offsetEditText.setTextColor(getResources().getColor(android.R.color.holo_red_light));
            Toast.makeText(HoleInfoActivity.this, "偏移量数值非法", Toast.LENGTH_LONG).show();
            return false;
        }

        try {
            Double.parseDouble(holeHeightEditText.getText().toString());
        } catch (Exception e) {
            holeHeightEditText.setTextColor(getResources().getColor(android.R.color.holo_red_light));
            Toast.makeText(HoleInfoActivity.this, "孔口标高数值非法", Toast.LENGTH_LONG).show();
            return false;
        }

        try {
            Double.parseDouble(holeDepthEditText.getText().toString());
        } catch (Exception e) {
            holeDepthEditText.setTextColor(getResources().getColor(android.R.color.holo_red_light));
            Toast.makeText(HoleInfoActivity.this, "设计孔深数值非法", Toast.LENGTH_LONG).show();
            return false;
        }

        try {
            Double.parseDouble(latitudeEditText.getText().toString());
        } catch (Exception e) {
            latitudeEditText.setTextColor(getResources().getColor(android.R.color.holo_red_light));
            Toast.makeText(HoleInfoActivity.this, "经距数值非法", Toast.LENGTH_LONG).show();
            return false;
        }

        try {
            Double.parseDouble(longtitudeEditText.getText().toString());
        } catch (Exception e) {
            longtitudeEditText.setTextColor(getResources().getColor(android.R.color.holo_red_light));
            Toast.makeText(HoleInfoActivity.this, "纬距数值非法", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();

    }
}

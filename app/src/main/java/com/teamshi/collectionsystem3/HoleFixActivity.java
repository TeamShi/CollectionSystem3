package com.teamshi.collectionsystem3;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.teamshi.collectionsystem3.datastructure.Hole;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class HoleFixActivity extends AppCompatActivity {
    private static final String TAG = "CollectionSystem3";

    private Hole holeViewModel;
    private String holeId;

    private Button confirmAddHoleFixButton;
    private Button cancelAddHoleFixButton;

    private EditText[] originalTexts;
    private EditText[] fixedTexts;

    private TextView fixDateButton;
    private EditText fixSignatureEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "Start HoleFixActivity.");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hole_fix);

        this.setTitle("修正内容提示表");

        confirmAddHoleFixButton = (Button) findViewById(R.id.button_confirm_edit_hole_fix);
        cancelAddHoleFixButton = (Button) findViewById(R.id.button_cancel_edit_hole_fix);

        originalTexts = new EditText[8];
        fixedTexts = new EditText[8];

        fixDateButton = (TextView) findViewById(R.id.textview_hole_fix_date);
        fixSignatureEditText = (EditText) findViewById(R.id.edittext_fix_signature);

        for (int i = 1; i <= 8; i++) {
            final EditText originEditText = (EditText) findViewById(getResources().getIdentifier("edit_text_fix_info_origin_" + i, "id", getPackageName()));
            originEditText.setTag(i);

            originEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    holeViewModel.getHoleFixItems()[(Integer) originEditText.getTag() - 1].setOriginItem(editable.toString());
                }
            });

            originalTexts[i - 1] = originEditText;

            final EditText fixedEditText = (EditText) findViewById(getResources().getIdentifier("edit_text_fix_info_after_" + i, "id", getPackageName()));

            fixedEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    holeViewModel.getHoleFixItems()[(Integer) originEditText.getTag() - 1].setFixedItem(editable.toString());
                }
            });

            fixedTexts[i - 1] = fixedEditText;
        }

        fixSignatureEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                holeViewModel.setFixSignature(editable.toString());
            }
        });

        confirmAddHoleFixButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataManager.updateHole(holeId, holeViewModel);

                HoleFixActivity.this.setResult(RESULT_OK);
                HoleFixActivity.this.finish();
            }
        });

        cancelAddHoleFixButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HoleFixActivity.this.setResult(RESULT_CANCELED);
                HoleFixActivity.this.finish();
            }
        });

        fixDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar rigDate = holeViewModel.getFixDate();
                DatePickerDialog dialog = new DatePickerDialog(HoleFixActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        GregorianCalendar temp = new GregorianCalendar();
                        temp.set(year, monthOfYear, dayOfMonth);
                        holeViewModel.setFixDate(temp);
                        refreshInfo();
                    }
                }, holeViewModel.getFixDate().get(Calendar.YEAR), holeViewModel.getFixDate().get(Calendar.MONTH), holeViewModel.getFixDate().get(Calendar.DAY_OF_MONTH));

                dialog.show();
            }
        });

        String requestCode = getIntent().getStringExtra("requestCode");

        holeId = getIntent().getStringExtra("holeId");

        holeViewModel = DataManager.getHole(holeId).deepCopy();

        refreshInfo();
    }

    private void refreshInfo() {
        for (int i = 0; i < 8; i++) {
            originalTexts[i].setText(holeViewModel.getHoleFixItems()[i].getOriginItem());
            fixedTexts[i].setText(holeViewModel.getHoleFixItems()[i].getFixedItem());
        }

        fixSignatureEditText.setText(holeViewModel.getFixSignature());
        fixDateButton.setText(Utility.formatCalendarDateString(holeViewModel.getFixDate()));
    }
}

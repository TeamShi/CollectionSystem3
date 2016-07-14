package com.teamshi.collectionsystem3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Visibility;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class StartUpActivity extends AppCompatActivity {
    private LinearLayout projectlistLinearLayout;

    private Button activeButton;
    private Button newProjectButton;

    private TextView validationStatusTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_up);

        projectlistLinearLayout = (LinearLayout) findViewById(R.id.linearlayout_project_list);

        activeButton = (Button) findViewById(R.id.button_active_system);
        newProjectButton = (Button) findViewById(R.id.button_new_project);

        validationStatusTextView = (TextView) findViewById(R.id.textview_validation_info);

        activeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Alfred, Input key.
            }
        });

        newProjectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Alfred, new Project
            }
        });

        if (ValidationManager.validate()) {
            projectlistLinearLayout.setVisibility(View.VISIBLE);

            activeButton.setEnabled(false);
            activeButton.setText("已激活");

            validationStatusTextView.setText(ValidationManager.getExpiredDate());

        } else {
            projectlistLinearLayout.setVisibility(View.GONE);

            activeButton.setEnabled(true);
            activeButton.setText("输入激活码");

            validationStatusTextView.setText("未激活");
        }
    }
}

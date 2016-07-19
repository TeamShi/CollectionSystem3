package com.teamshi.collectionsystem3;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.teamshi.collectionsystem3.datastructure.Project;

import java.io.File;

public class StartUpActivity extends AppCompatActivity {
    private static final String TAG = "CollectionSystem3";
    private static String APP_ROOT = Environment.getExternalStorageDirectory().getPath()+"/ZuanTan/";
    /**
     * app file system overview
     *
     *           |- Project name - ...
     * ZuanTan - |                 |- Hole id
     *           |- Project name - |
     *                             |- Hole id
     *
     */

    private LinearLayout projectlistLinearLayout;
    private Button activeButton;
    private Button newProjectButton;

    private TextView validationStatusTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "Start CollectionSystem3.");

        Log.d(TAG, "Start StartUpActivity.");

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
                Log.d(TAG, "newProjectedButton clicked.");

                AlertDialog.Builder builder = new AlertDialog.Builder(StartUpActivity.this);
                builder.setTitle("工程名称");

                final EditText input = new EditText(StartUpActivity.this);
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);
                Log.d(TAG, "Pop up window to input new project name.");

                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d(TAG, "Confirm Button clicked.");
                        Log.d(TAG, "Project name: \"" + input.getText().toString() + "\".");
                        DataManager.loadProject(new Project(input.getText().toString()));
                        Intent intent = new Intent(StartUpActivity.this, HoleIndexActivity.class);
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d(TAG, "Cancel button is clicked.");
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });

        if (ValidationManager.validate()) {
            Log.d(TAG, "Validation pass. Load normally.");
            projectlistLinearLayout.setVisibility(View.VISIBLE);

            activeButton.setEnabled(false);
            activeButton.setText("已激活");

            validationStatusTextView.setText(ValidationManager.getExpiredDate());

        } else {
            Log.d(TAG, "Validation failed. Load nothing.");
            projectlistLinearLayout.setVisibility(View.GONE);

            activeButton.setEnabled(true);
            activeButton.setText("输入激活码");

            validationStatusTextView.setText("未激活");
        }

        //Scan app dir to list all exists valid projects
        File[] projectFiles = new File(APP_ROOT).listFiles();
        if (null == projectFiles || projectFiles.length == 0) {
            Toast.makeText(getApplicationContext(), "应用目录工程项目为空 请新建工程", Toast.LENGTH_SHORT).show();
        } else {
            //TODO: Johnson add file manager plugin
        }

    }
}

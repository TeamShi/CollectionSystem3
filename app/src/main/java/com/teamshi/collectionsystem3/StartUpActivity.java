package com.teamshi.collectionsystem3;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.teamshi.collectionsystem3.datastructure.Project;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Map;
import java.util.Set;

public class StartUpActivity extends AppCompatActivity {
    private static final String TAG = "CollectionSystem3";
    private Button activeButton;
    private Button newProjectButton;
    private Button openProjectButton;
    private Button deleteProjectButton;
    private LinearLayout mainButtonsLinearLayout;
    private ListView projectListView;
    private LinearLayout projectListViewWrapper;
    private TextView validationStatusTextView;
    private ArrayAdapter<String> arrayAdapter;
    private TextView storagePathTextView;
    private String selectedProjectName = null;
    private String licenseString = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "Start CollectionSystem3.");

        Log.d(TAG, "Start StartUpActivity.");

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_start_up);

        IOManager.initFileSystem(getApplicationContext());

        IOManager.loadConfiguration();

        projectListViewWrapper = (LinearLayout) findViewById(R.id.wrapper_project_list);
        projectListView = (ListView) projectListViewWrapper.findViewById(R.id.lv_project_list);
        mainButtonsLinearLayout = (LinearLayout) findViewById(R.id.linear_layout_main_buttons);

        activeButton = (Button) findViewById(R.id.button_active_system);
        newProjectButton = (Button) findViewById(R.id.button_new_project);
        openProjectButton = (Button) findViewById(R.id.button_open_project);
        deleteProjectButton = (Button) findViewById(R.id.button_delete_project);

        validationStatusTextView = (TextView) findViewById(R.id.textview_validation_info);
        storagePathTextView = (TextView) findViewById(R.id.textview_storage_path);

        activeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(StartUpActivity.this);
                builder.setTitle("请输入验证码");

                final EditText input = new EditText(StartUpActivity.this);
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);
                Log.d(TAG, "Pop up window to input new project name.");

                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d(TAG, "Cancel button is clicked.");
                        dialog.cancel();
                    }
                });

                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (Utility.validateDate(input.getText().toString())) {

                            Calendar c = new GregorianCalendar();
                            c.setTimeInMillis(Utility.getExpiredDate(input.getText().toString().toString()) * 1000);
                            validationStatusTextView.setText("验证成功。过期时间：" + c.get(Calendar.YEAR) + "/" + (c.get(Calendar.MONTH) + 1) + "/" + (c.get(Calendar.DAY_OF_MONTH)));

                            projectListView.setVisibility(View.VISIBLE);
                            mainButtonsLinearLayout.setVisibility(View.VISIBLE);

                            activeButton.setEnabled(false);
                            activeButton.setText("已激活");

                            String licenseFilePath = Environment.getExternalStorageDirectory().getPath() + "/ZuanTan/config/license.dat";
                            try {
                                FileWriter fw = new FileWriter(licenseFilePath);
                                BufferedWriter bw = new BufferedWriter(fw);
                                bw.write(input.getText().toString().toUpperCase());
                                bw.close();
                                fw.close();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {

                            projectListView.setVisibility(View.GONE);

                            activeButton.setEnabled(true);
                            activeButton.setText("输入激活码");

                            validationStatusTextView.setText("有效期过期，请重新输入授权码。");
                        }
                    }
                });

                builder.show();
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

                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d(TAG, "Cancel button is clicked.");
                        dialog.cancel();
                    }
                });

                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String projectName = input.getText().toString();

                        Log.d(TAG, "Confirm Button clicked.");
                        Log.d(TAG, "Project name: \"" + projectName + "\".");

                        if (projectName.equals("")) {
                            Toast.makeText(getApplicationContext(), "项目名称不能为空.", Toast.LENGTH_LONG).show();
                        } else if (IOManager.getProjects().containsKey(projectName)) {
                            Toast.makeText(getApplicationContext(), "项目名称 '"+projectName+"' 已存在.", Toast.LENGTH_LONG).show();
                        } else {
                            Project project = new Project(projectName);
                            boolean isUpdated = IOManager.updateProject(project);
                            if(isUpdated){
                                // refresh project names in ListView
                                arrayAdapter.add(projectName);
                                projectListView.invalidateViews();
                                Toast.makeText(getApplicationContext(), "新建成功.", Toast.LENGTH_LONG).show();
                                // load new Project and switch to HoleIndexActivity
                                DataManager.loadProject(project);
                                Intent intent = new Intent(StartUpActivity.this, HoleIndexActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(getApplicationContext(), "新建失败.", Toast.LENGTH_LONG).show();
                            }

                        }
                    }
                });

                builder.show();
            }
        });

        openProjectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if( null == selectedProjectName){
                    Toast.makeText(getApplicationContext(), "请先选择项目", Toast.LENGTH_LONG).show();
                    return;
                }

                Map<String,Project>  projects = IOManager.getProjects();
                Project project = projects.get(selectedProjectName);
                DataManager.loadProject(project);
                Intent intent = new Intent(StartUpActivity.this, HoleIndexActivity.class);
                startActivity(intent);
            }
        });

        deleteProjectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( null == selectedProjectName){
                    Toast.makeText(getApplicationContext(), "请先选择项目", Toast.LENGTH_LONG).show();
                    return;
                }

                Log.d(TAG, "deleteProjectedButton clicked.");

                AlertDialog.Builder builder = new AlertDialog.Builder(StartUpActivity.this);
                builder.setTitle("删除");

                builder.setMessage("确认删除工程\'"+selectedProjectName+"\'吗?");
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d(TAG, "Cancel button is clicked.");
                        dialog.cancel();
                    }
                });

                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        boolean isDeleted = IOManager.deleteProject(selectedProjectName);
                        if(isDeleted){
                            Toast.makeText(getApplicationContext(), "删除成功", Toast.LENGTH_LONG).show();
                            arrayAdapter.remove(selectedProjectName);
                        }else{
                            Toast.makeText(getApplicationContext(), "删除失败", Toast.LENGTH_LONG).show();
                        }
                    }
                });

                builder.show();
            }
        });

        Set<String> names = IOManager.getProjects().keySet();
        final ArrayList<String>  projectNames = new ArrayList<>(Arrays.asList(names.toArray(new String[names.size()])));
        if (projectNames.size() == 0) {
            Toast.makeText(getApplicationContext(), "应用目录工程项目为空 请新建工程", Toast.LENGTH_LONG).show();
        }

        arrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_single_choice, projectNames);
        projectListView.setAdapter(arrayAdapter);
        projectListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        projectListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedProjectName = (String) projectListView.getItemAtPosition(position);
            }
        });
        projectListView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
//        projectListView.setSelection(-1);

        storagePathTextView.setFocusable(false);
        storagePathTextView.setOnClickListener(null);
        storagePathTextView.setOnTouchListener(null);
        storagePathTextView.setOnKeyListener(null);
        storagePathTextView.setText("文件保存路径: "+IOManager.APP_ROOT);

        String licenseFilePath = Environment.getExternalStorageDirectory().getPath() + "/ZuanTan/config/license.dat";
        File licenseFile = new File(licenseFilePath);
        if (licenseFile.exists()) {
            FileReader fr = null;
            try {
                fr = new FileReader(licenseFilePath);
                BufferedReader br = new BufferedReader(fr);
                licenseString = br.readLine();
                br.close();
                fr.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (Utility.validateDate(licenseString)) {
                Log.d(TAG, "Validation pass. Load normally.");
                projectListView.setVisibility(View.VISIBLE);

                activeButton.setEnabled(false);
                activeButton.setText("已激活");

                Calendar c = new GregorianCalendar();
                c.setTimeInMillis(Utility.getExpiredDate(licenseString) * 1000);
                validationStatusTextView.setText("验证成功。过期时间：" + c.get(Calendar.YEAR) + "/" + (c.get(Calendar.MONTH) + 1) + "/" + (c.get(Calendar.DAY_OF_MONTH)));

            } else {
                Log.d(TAG, "Validation failed. Load nothing.");
                projectListView.setVisibility(View.GONE);
                mainButtonsLinearLayout.setVisibility(View.GONE);

                activeButton.setEnabled(true);
                activeButton.setText("输入激活码");

                validationStatusTextView.setText("有效期过期，请重新输入授权码。");
            }
        } else {
            File ztFolder = new File(Environment.getExternalStorageDirectory().getPath() + "/ZuanTan/");
            ztFolder.mkdir();
            File ztConfigFolder = new File(Environment.getExternalStorageDirectory().getPath() + "/ZuanTan/config/");
            ztConfigFolder.mkdir();

            FileWriter fw = null;
            long expireDate = 0;
            try {
                fw = new FileWriter(licenseFilePath);
                BufferedWriter bw = new BufferedWriter(fw);
                Calendar c = new GregorianCalendar();
                expireDate = c.getTimeInMillis() / 1000;
                expireDate += 8035200;
                bw.write(Utility.getExpiredString(expireDate));
                bw.close();
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            projectListView.setVisibility(View.VISIBLE);

            activeButton.setEnabled(false);
            activeButton.setText("已激活");


            Calendar c = new GregorianCalendar();
            c.setTimeInMillis(expireDate * 1000);
            validationStatusTextView.setText("验证成功。过期时间：" + c.get(Calendar.YEAR) + "/" + (c.get(Calendar.MONTH) + 1) + "/" + (c.get(Calendar.DAY_OF_MONTH)));
        }

    }

    @Override
    public void onStart() {
        super.onStart();

        // reset selected items
        ArrayAdapter<String> adapter = (ArrayAdapter<String>) projectListView.getAdapter();
        int selectedProjectPosition = adapter.getPosition(selectedProjectName);
        projectListView.setSelection(selectedProjectPosition);
        projectListView.invalidateViews();
    }
}

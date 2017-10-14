package com.teamshi.collectionsystem3;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class WelcomeActivity extends AppCompatActivity {
    private Button confirmButton;
    private EditText licenseEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        confirmButton = (Button) findViewById(R.id.button_license);
        licenseEditText = (EditText) findViewById(R.id.edit_text_license);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Utility.validateDate(licenseEditText.getText().toString())) {
                    String licenseFilePath = Environment.getExternalStorageDirectory().getPath() + "/ZuanTan/config/license.dat";
                    try {
                        FileWriter fw = new FileWriter(licenseFilePath);
                        BufferedWriter bw = new BufferedWriter(fw);
                        bw.write(licenseEditText.getText().toString().toUpperCase());
                        bw.close();
                        fw.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    Intent intent = new Intent(WelcomeActivity.this, StartUpActivity.class);
                    startActivity(intent);
                } else {
                    licenseEditText.setHint("有效期过期，请重新输入授权码。");
                    licenseEditText.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
                }
            }
        });

        String licenseFilePath = Environment.getExternalStorageDirectory().getPath() + "/ZuanTan/config/license.dat";
        String licenseString = "";
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
                Intent intent = new Intent(WelcomeActivity.this, StartUpActivity.class);
                startActivity(intent);
            } else {
                licenseEditText.setHint("有效期过期，请重新输入授权码。");
                licenseEditText.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
            }
        }  else {
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
                expireDate += 0;
                licenseEditText.setHint("有效期过期，请重新输入授权码。");
                licenseEditText.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
                bw.write(Utility.getExpiredString(expireDate));
                bw.close();
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

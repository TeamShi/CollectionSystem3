package com.teamshi.collectionsystem3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class HoleIndexActivity extends AppCompatActivity {
    private static final String TAG = "CollectionSystem3";

    private static final int ACTION_ADD_HOLE = 1;
    private static final int ACTION_EDIT_HOLE = 2;

    private Button saveProjectButton;
    private Button newHoleButton;
    private Button previewButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "Start HoleIndexActivity.");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hole_index);

        Log.d(TAG, "Project name: " + DataManager.project.getProjectName());
        this.setTitle(DataManager.project.getProjectName());

        this.saveProjectButton = (Button) findViewById(R.id.button_save_project);
        this.newHoleButton = (Button) findViewById(R.id.button_add_hole);
        this.previewButton = (Button) findViewById(R.id.button_preview_project);

        this.saveProjectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Johnson. Save DataManager.getProject() to storage.
            }
        });

        this.newHoleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "newHoleButton clicked.");
                Intent intent = new Intent(HoleIndexActivity.this, HoleInfoActivity.class);
                intent.putExtra("requestCode", "ACTION_ADD_HOLE");
                intent.putExtra("projectName", DataManager.project.getProjectName());
                startActivityForResult(intent, ACTION_ADD_HOLE);
            }
        });

        this.previewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Johnson. preview Datamanager.getProject(). No need to save to storage.
            }
        });
    }
}

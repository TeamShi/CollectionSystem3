package com.teamshi.collectionsystem3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.teamshi.collectionsystem3.datastructure.Hole;

public class HoleIndexActivity extends AppCompatActivity {
    private static final String TAG = "CollectionSystem3";

    private static final int ACTION_ADD_HOLE = 1;
    private static final int ACTION_EDIT_HOLE = 2;
    private static final int ACTION_COPY_HOLE = 3;

    private static final int CONTEXT_MENU_QUERY = 0;
    private static final int CONTEXT_MENU_INPUT = 1;
    private static final int CONTEXT_MENU_COPY_NEW = 2;
    private static final int CONTEXT_MENU_DELETE = 3;

    private Button saveProjectButton;
    private Button newHoleButton;
    private Button previewButton;

    private TableLayout holesTableLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "Start HoleIndexActivity.");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hole_index);

        Log.d(TAG, "Project name: " + DataManager.project.getProjectName());
        this.setTitle(DataManager.project.getProjectName());

        saveProjectButton = (Button) findViewById(R.id.button_save_project);
        newHoleButton = (Button) findViewById(R.id.button_add_hole);
        previewButton = (Button) findViewById(R.id.button_preview_project);

        holesTableLayout = (TableLayout) findViewById(R.id.tablelayout_holes);

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

        refreshInfo();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        menu.add(0, CONTEXT_MENU_QUERY, 0, "查询");
        menu.add(0, CONTEXT_MENU_INPUT, 0, "输入");
        menu.add(0, CONTEXT_MENU_COPY_NEW, 0, "复制新增");
        menu.add(0, CONTEXT_MENU_DELETE, 0, "删除");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        Intent intent = null;
        String holeId = getIntent().getStringExtra("selectedHoleId");;

        switch (item.getItemId()) {
            case CONTEXT_MENU_QUERY:
                Log.d(TAG, "EditHole " + holeId + " clicked.");
                intent = new Intent(HoleIndexActivity.this, HoleInfoActivity.class);
                intent.putExtra("requestCode", "ACTION_EDIT_HOLE");
                intent.putExtra("holeId", holeId);
                startActivityForResult(intent, ACTION_EDIT_HOLE);
                break;
            case CONTEXT_MENU_INPUT:
                Log.d(TAG, "Input rig for hole " + holeId + ".");
                intent = new Intent(HoleIndexActivity.this, RigIndexActivity.class);
                intent.putExtra("holeId", holeId);
                startActivity(intent);
                break;
            case CONTEXT_MENU_COPY_NEW:
                Log.d(TAG, "Copy hole clicked.");
                intent = new Intent(HoleIndexActivity.this, HoleInfoActivity.class);
                intent.putExtra("requestCode", "ACTION_COPY_HOLE");
                intent.putExtra("holeId", holeId);
                startActivityForResult(intent, ACTION_COPY_HOLE);
                break;
            case CONTEXT_MENU_DELETE:
                Log.d(TAG, "Delete hole " + holeId + ".");
                DataManager.deleteHole(holeId);
                refreshInfo();
                break;
        }

        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_hole_index, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void refreshInfo() {
        Log.d(TAG, "Clear table content.");
        while (holesTableLayout.getChildCount() != 1) {
            holesTableLayout.removeViewAt(1);
        }

        Log.d(TAG, "Draw table.");
        for (Hole hole : DataManager.getProject().getHoleList()) {
            TableRow row = new TableRow(this);

            row.setBackgroundColor(getResources().getColor(android.R.color.white));

            TableLayout.LayoutParams param = new TableLayout.LayoutParams();
            param.height = TableLayout.LayoutParams.WRAP_CONTENT;
            param.width = TableLayout.LayoutParams.WRAP_CONTENT;

            row.setLayoutParams(param);

            row.addView(generateHoleInfoCell(hole.getHoleId()));
            row.addView(generateHoleInfoCell(hole.getProjectName()));

            if (!hole.isSpecialHoleId()) {
                row.addView(generateHoleInfoCell(hole.getHoleIdPart2()));
            } else {
                row.addView(generateHoleInfoCell(""));
            }

            row.addView(generateHoleInfoCell(hole.getArticle()));
            row.addView(generateHoleInfoCell(String.valueOf(hole.getMileage())));
            row.addView(generateHoleInfoCell(String.valueOf(hole.getOffset())));

            row.addView(generateHoleInfoCell(String.valueOf(hole.getHoleHeight())));
            row.addView(generateHoleInfoCell(String.valueOf(hole.getLongitude())));
            row.addView(generateHoleInfoCell(String.valueOf(hole.getLatitude())));
            row.addView(generateHoleInfoCell(hole.getPositionInformation()));

            row.addView(generateHoleInfoCell(hole.getRecorder()));
            row.addView(generateHoleInfoCell(Utility.formatCalendarDateString(hole.getRecordDate())));
            row.addView(generateHoleInfoCell(hole.getReviewer()));
            row.addView(generateHoleInfoCell(Utility.formatCalendarDateString(hole.getReviewDate())));

            row.addView(generateHoleInfoCell(hole.getNote()));
            row.addView(generateHoleInfoCell(String.valueOf(hole.getHoleDepth())));

            row.setTag(hole.getHoleId());

            row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String holeId = v.getTag().toString();
                    Log.d(TAG, "Input rig for hole " + holeId + ".");
                    Intent intent = new Intent(HoleIndexActivity.this, RigIndexActivity.class);
                    intent.putExtra("holeId", holeId);
                    startActivity(intent);
                }
            });

            row.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    setIntent(getIntent().putExtra("selectedHoleId", v.getTag().toString()));

                    return false;
                }
            });

            registerForContextMenu(row);

            holesTableLayout.addView(row);
        }
    }

    private TextView generateHoleInfoCell(String s) {
        TextView tv = new TextView(this);
        tv.setText(s);
        tv.setBackground(getResources().getDrawable(R.drawable.cell_input));

        tv.setTextSize(getResources().getDimension(R.dimen.default_text_size) / getResources().getDisplayMetrics().density);

        int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, getResources().getDimension(R.dimen.default_table_height) / getResources().getDisplayMetrics().density, getResources().getDisplayMetrics());
        TableRow.LayoutParams param = new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, height, 0f);
        int paddingInDp = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, getResources().getDimension(R.dimen.default_table_padding) / getResources().getDisplayMetrics().density, getResources().getDisplayMetrics());

        tv.setPadding(paddingInDp, paddingInDp, paddingInDp, paddingInDp);
        tv.setLayoutParams(param);

        tv.setGravity(Gravity.CENTER);

        return tv;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case ACTION_ADD_HOLE:
            case ACTION_COPY_HOLE:
                if (resultCode == RESULT_OK) {
                    Log.d(TAG, "Hole added.");
                    Toast.makeText(HoleIndexActivity.this, "添加成功.", Toast.LENGTH_SHORT).show();
                    refreshInfo();
                }
                break;
            case ACTION_EDIT_HOLE:
                if (resultCode == RESULT_OK) {
                    Log.d(TAG, "Hole modified.");
                    Toast.makeText(HoleIndexActivity.this, "修改成功.", Toast.LENGTH_LONG).show();
                    refreshInfo();
                }
        }
    }
}

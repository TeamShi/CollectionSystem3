package com.teamshi.collectionsystem3;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.teamshi.collectionsystem3.datastructure.Hole;
import com.teamshi.collectionsystem3.datastructure.NARig;
import com.teamshi.collectionsystem3.datastructure.RegularRig;
import com.teamshi.collectionsystem3.datastructure.Rig;

import java.util.ArrayList;

public class RigIndexActivity extends AppCompatActivity {
    private static final String TAG = "Collectionsystem3";

    private static final int ACTION_ADD_RIG = 0;
    private static final int ACTION_EDIT_RIG = 1;
    private static final int ACTION_COPY_RIG = 2;

    private static final int CONTEXT_MENU_QUERY = 0;
    private static final int CONTEXT_MENU_COPY_NEW = 1;
    private static final int CONTEXT_MENU_DELETE = 2;

    private Hole hole;
    private String holeId;

    private TableLayout rigListTableLayout;

    private Spinner holeListSpinner;
    private ArrayAdapter<String> holeListSpinnerAdapter;
    private String[] holeListSpinnerOptions;

    private Button addRigButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rig_index);

        this.setTitle("钻探信息表");

        rigListTableLayout = (TableLayout) findViewById(R.id.tablelayout_riglist);

        holeListSpinner = (Spinner)findViewById(R.id.spinner_hole_list);
        addRigButton = (Button) findViewById(R.id.button_add_rig);

        holeListSpinnerOptions = DataManager.getHoleIdOptionList();
        holeListSpinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, holeListSpinnerOptions);
        holeListSpinner.setAdapter(holeListSpinnerAdapter);

        addRigButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "newRigButton clicked.");
                AlertDialog typeDialog;
                final CharSequence[] items = {"搬家移孔, 下雨停工, 其它", "干钻, 合水钻, 金刚石钻, 钢粒钻"};

                AlertDialog.Builder builder = new AlertDialog.Builder(RigIndexActivity.this);

                builder.setTitle("作业类型");

                builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = null;
                        switch (which) {
                            case 0:
                                intent = new Intent(RigIndexActivity.this, NARigInfoActivity.class);
                                intent.putExtra("requestCode", "ACTION_ADD_RIG");
                                intent.putExtra("holeId", holeId);
                                startActivityForResult(intent, ACTION_ADD_RIG);
                                break;
                            case 1:
                                intent = new Intent(RigIndexActivity.this, RegularRigActivity.class);
                                intent.putExtra("requestCode", "ACTION_ADD_RIG");
                                intent.putExtra("holeId", holeId);
                                startActivityForResult(intent, ACTION_ADD_RIG);
                                break;
                        }

                        dialog.dismiss();
                    }
                });

                typeDialog = builder.create();
                typeDialog.show();
            }
        });

        holeListSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                holeId = holeListSpinnerOptions[position];
                hole = DataManager.getHole(holeId);
                refreshInfo();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        holeId = getIntent().getStringExtra("holeId");

        for (int i = 0; i < holeListSpinnerOptions.length; i++) {
            if (holeListSpinnerOptions[i].equals(holeId)) {
                holeListSpinner.setSelection(i);
                break;
            }
        }

        hole = DataManager.getHole(holeId);

        refreshInfo();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        menu.add(0, CONTEXT_MENU_QUERY, 0, "查询");
        menu.add(0, CONTEXT_MENU_COPY_NEW, 0, "复制新增");
        menu.add(0, CONTEXT_MENU_DELETE, 0, "删除");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        Intent intent = null;

        int selectedRigIndex = Integer.valueOf(getIntent().getStringExtra("selectedRigIndex"));
        Rig rig = DataManager.getRig(holeId, selectedRigIndex);

        switch (item.getItemId()) {
            case CONTEXT_MENU_QUERY:
                Log.d(TAG, "EditRig of holeId: " + holeId + ", rig index: " + selectedRigIndex);

                if (rig instanceof NARig) {
                    intent = new Intent(RigIndexActivity.this, NARigInfoActivity.class);
                    intent.putExtra("requestCode", "ACTION_EDIT_RIG");
                    intent.putExtra("holeId", holeId);
                    intent.putExtra("rigIndex", selectedRigIndex);
                    startActivityForResult(intent, ACTION_EDIT_RIG);
                } else if (rig instanceof RegularRig) {
                    intent = new Intent(RigIndexActivity.this, RegularRigActivity.class);
                    intent.putExtra("requestCode", "ACTION_EDIT_RIG");
                    intent.putExtra("holeId", holeId);
                    intent.putExtra("rigIndex", selectedRigIndex);
                    startActivityForResult(intent, ACTION_EDIT_RIG);
                }
                break;
            case CONTEXT_MENU_COPY_NEW:
                Log.d(TAG, "Copy rig clicked.");

                if (rig instanceof NARig) {
                    intent = new Intent(RigIndexActivity.this, NARig.class);
                    intent.putExtra("requestCode", "ACTION_COPY_RIG");
                    intent.putExtra("rigIndex", selectedRigIndex);
                    intent.putExtra("holeId", holeId);
                    startActivityForResult(intent, ACTION_COPY_RIG);
                } else if (rig instanceof RegularRig) {
                    Log.d(TAG, "Copy hole clicked.");
                    intent = new Intent(RigIndexActivity.this, RegularRigActivity.class);
                    intent.putExtra("requestCode", "ACTION_COPY_RIG");
                    intent.putExtra("rigIndex", selectedRigIndex);
                    intent.putExtra("holeId", holeId);
                    startActivityForResult(intent, ACTION_COPY_RIG);
                }

                break;
            case CONTEXT_MENU_DELETE:
                Log.d(TAG, "DeleteRig of holeId: " + holeId + ", rig index: " + selectedRigIndex);
                if (selectedRigIndex != DataManager.getHole(holeId).getRigList().size() - 1) {
                    Toast.makeText(RigIndexActivity.this, "只能删除最后一次的作业信息.", Toast.LENGTH_LONG).show();
                } else {
                    Rig deletingRig = DataManager.getLastRig(holeId);

//                    if (deletingRig instanceof CalculatingRig) {
//                        CalculatingRig lastCalculatingRig = DataManager.getLastCaculatingRig(holeId);
//
//                        if (lastCalculatingRig == null) {
//                            DataManager.getHole(holeId).setLastRockCorePipeLength(0);
//                            DataManager.getHole(holeId).setLastAccumulatedMeterageLength(0);
//                        }
//
//                        DataManager.getHole(holeId).setLastRigEndTime(rigViewModel.getEndTime());
//                        DataManager.getHole(holeId).setLastRockCorePipeLength(lastCalculatingRig.getRockCorePipeLength());
//                        DataManager.getHole(holeId).setLastAccumulatedMeterageLength(lastCalculatingRig.getAccumulatedMeterageLength());
//                    }
//
//                    if (deletingRig.getPipeNumber() == DataManager.getHole(holeId).getPipeCount() + 1) {
//                        DataManager.getHole(holeId).addPipe(deletingRig.getPipeLength());
//                    }
                    // TODO: delete logic
                    DataManager.removeLastRig(holeId);

                    Toast.makeText(RigIndexActivity.this, "删除成功.", Toast.LENGTH_LONG).show();

                    refreshInfo();
                }
                break;
        }

        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_rig_index, menu);
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
        while (rigListTableLayout.getChildCount() != 3) {
            rigListTableLayout.removeViewAt(3);
        }

        Log.d(TAG, "Draw table.");
        for (int i = 0; i < hole.getRigList().size(); i++) {
            TableRow row = new TableRow(this);

            row.setBackgroundColor(getResources().getColor(android.R.color.white));

            TableLayout.LayoutParams param = new TableLayout.LayoutParams();
            param.height = TableLayout.LayoutParams.WRAP_CONTENT;
            param.width = TableLayout.LayoutParams.WRAP_CONTENT;

            row.setLayoutParams(param);

            if (hole.getRigList().get(i) instanceof NARig) {
                NARig rig = (NARig) hole.getRigList().get(i);

                for (TextView tv : generateNARigRowContent(rig)) {
                    row.addView(tv);
                }
            } else if (hole.getRigList().get(i) instanceof RegularRig) {
                RegularRig rig = (RegularRig) hole.getRigList().get(i);

                for (TextView tv : generateRegularRigRowContent(rig)) {
                    row.addView(tv);
                }
            }

            row.setTag(i);

            row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int selectedRigIndex = (int) v.getTag();
                    Rig rig = DataManager.getRig(holeId, selectedRigIndex);
                    Intent intent = null;

                    Log.d(TAG, "EditRig of holeId: " + holeId + ", rig index: " + selectedRigIndex);

                    if (rig instanceof NARig) {
                        intent = new Intent(RigIndexActivity.this, NARigInfoActivity.class);
                        intent.putExtra("requestCode", "ACTION_EDIT_RIG");
                        intent.putExtra("holeId", holeId);
                        intent.putExtra("rigIndex", selectedRigIndex);
                        startActivityForResult(intent, ACTION_EDIT_RIG);
                    } else if (rig instanceof RegularRig) {
                        intent = new Intent(RigIndexActivity.this, RegularRigActivity.class);
                        intent.putExtra("requestCode", "ACTION_EDIT_RIG");
                        intent.putExtra("holeId", holeId);
                        intent.putExtra("rigIndex", selectedRigIndex);
                        startActivityForResult(intent, ACTION_EDIT_RIG);
                    }
                }
            });

            row.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    setIntent(getIntent().putExtra("selectedRigIndex", v.getTag().toString()));
                    return false;
                }
            });

            registerForContextMenu(row);

            rigListTableLayout.addView(row);
        }
    }

    private TextView generateRigInfoCell(String s) {
        TextView tv = new TextView(this);

        tv.setText(s);
        tv.setBackground(getResources().getDrawable(R.drawable.cell_input));

        tv.setTextSize(getResources().getDimension(R.dimen.default_text_size) / getResources().getDisplayMetrics().density);

        int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, getResources().getDimension(R.dimen.default_table_height) / getResources().getDisplayMetrics().density, getResources().getDisplayMetrics());
        TableRow.LayoutParams param = new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, height, 0f);
        tv.setLayoutParams(param);

        tv.setGravity(Gravity.CENTER);

        return tv;
    }

    private ArrayList<TextView> generateNARigRowContent(NARig rig) {
        ArrayList<TextView> result = new ArrayList<>();

        result.add(generateRigInfoCell(rig.getClassPeopleCount()));

        result.add(generateRigInfoCell(Utility.formatCalendarDateStringWithoutYear(rig.getDate())));
        result.add(generateRigInfoCell(Utility.formatTimeStringChinese(rig.getStartTime())));
        result.add(generateRigInfoCell(Utility.formatTimeStringChinese(rig.getEndTime())));
        result.add(generateRigInfoCell(Utility.calculateTimeSpanChinese(rig.getStartTime(), rig.getEndTime())));

        result.add(generateRigInfoCell(rig.getNaType()));

        result.add(generateRigInfoCell(""));
        result.add(generateRigInfoCell(""));
        result.add(generateRigInfoCell(""));

        result.add(generateRigInfoCell(""));
        result.add(generateRigInfoCell(""));

        result.add(generateRigInfoCell(""));
        result.add(generateRigInfoCell(""));
        result.add(generateRigInfoCell(""));

        result.add(generateRigInfoCell(""));
        result.add(generateRigInfoCell(""));
        result.add(generateRigInfoCell(""));
        result.add(generateRigInfoCell(""));

        result.add(generateRigInfoCell(""));
        result.add(generateRigInfoCell(""));
        result.add(generateRigInfoCell(""));
        result.add(generateRigInfoCell(""));
        result.add(generateRigInfoCell(""));

        result.add(generateRigInfoCell(""));

        result.add(generateRigInfoCell(""));
        result.add(generateRigInfoCell(""));
        result.add(generateRigInfoCell(""));

        result.add(generateRigInfoCell(""));
        result.add(generateRigInfoCell(""));
        result.add(generateRigInfoCell(""));
        result.add(generateRigInfoCell(""));

        result.add(generateRigInfoCell(""));
        result.add(generateRigInfoCell(""));
        result.add(generateRigInfoCell(""));

        result.add(generateRigInfoCell(""));
        result.add(generateRigInfoCell(""));
        result.add(generateRigInfoCell(""));
        result.add(generateRigInfoCell(""));
        result.add(generateRigInfoCell(""));

        result.add(generateRigInfoCell(""));
        result.add(generateRigInfoCell(""));
        result.add(generateRigInfoCell(""));
        result.add(generateRigInfoCell(""));

        result.add(generateRigInfoCell(""));

        return result;
    }

    private ArrayList<TextView> generateRegularRigRowContent(RegularRig rig) {
        ArrayList<TextView> result = new ArrayList<>();

        result.add(generateRigInfoCell(rig.getClassPeopleCount()));

        result.add(generateRigInfoCell(Utility.formatCalendarDateStringWithoutYear(rig.getDate())));
        result.add(generateRigInfoCell(Utility.formatTimeStringChinese(rig.getStartTime())));
        result.add(generateRigInfoCell(Utility.formatTimeStringChinese(rig.getEndTime())));
        result.add(generateRigInfoCell(Utility.calculateTimeSpanChinese(rig.getStartTime(), rig.getEndTime())));

        result.add(generateRigInfoCell(rig.getRigType()));

        result.add(generateRigInfoCell(String.valueOf(rig.getPipeNumber())));
        result.add(generateRigInfoCell(String.format("%.2f", rig.getPipeLength())));
        result.add(generateRigInfoCell(String.format("%.2f", rig.getPipeTotalLength())));

        result.add(generateRigInfoCell(String.valueOf(rig.getRockCorePipeDiameter())));
        result.add(generateRigInfoCell(String.format("%.2f", rig.getRockCorePipeLength())));

        result.add(generateRigInfoCell(rig.getDrillBitType()));
        result.add(generateRigInfoCell(String.valueOf(rig.getDrillBitDiameter())));
        result.add(generateRigInfoCell(String.format("%.2f", rig.getDrillBitLength())));

        result.add(generateRigInfoCell(String.format("%.2f", rig.getDrillToolTotalLength())));
        result.add(generateRigInfoCell(String.format("%.2f", rig.getDrillPipeRemainLength())));
        result.add(generateRigInfoCell(String.format("%.2f", rig.getRoundTripMeterageLength())));
        result.add(generateRigInfoCell(String.format("%.2f", rig.getAccumulatedMeterageLength())));

        result.add(generateRigInfoCell(""));
        result.add(generateRigInfoCell(""));
        result.add(generateRigInfoCell(""));
        result.add(generateRigInfoCell(""));
        result.add(generateRigInfoCell(""));

        result.add(generateRigInfoCell(""));

        result.add(generateRigInfoCell(String.valueOf(rig.getRockCoreIndex())));
        result.add(generateRigInfoCell(String.format("%.2f", rig.getRockCoreLength())));
        result.add(generateRigInfoCell(String.format("%.2f", rig.getRockCorePickPercentage() * 100) + "%"));

        result.add(generateRigInfoCell(""));
        result.add(generateRigInfoCell(""));
        result.add(generateRigInfoCell(""));
        result.add(generateRigInfoCell(""));

        result.add(generateRigInfoCell(""));
        result.add(generateRigInfoCell(""));
        result.add(generateRigInfoCell(""));

        result.add(generateRigInfoCell(""));
        result.add(generateRigInfoCell(""));
        result.add(generateRigInfoCell(""));
        result.add(generateRigInfoCell(""));
        result.add(generateRigInfoCell(""));

        result.add(generateRigInfoCell(""));
        result.add(generateRigInfoCell(""));
        result.add(generateRigInfoCell(""));
        result.add(generateRigInfoCell(""));

        result.add(generateRigInfoCell(""));

        return result;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case ACTION_ADD_RIG:
            case ACTION_COPY_RIG:
                if (resultCode == RESULT_OK) {
                    Log.d(TAG, "Rig added.");
                    Toast.makeText(RigIndexActivity.this, "添加成功.", Toast.LENGTH_SHORT).show();
                    refreshInfo();
                }
                break;
            case ACTION_EDIT_RIG:
                if (resultCode == RESULT_OK) {
                    Log.d(TAG, "Hole modified.");
                    Toast.makeText(RigIndexActivity.this, "修改成功.", Toast.LENGTH_LONG).show();
                    refreshInfo();
                }
        }
    }
}

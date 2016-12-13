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

import com.teamshi.collectionsystem3.datastructure.DSTRig;
import com.teamshi.collectionsystem3.datastructure.Hole;
import com.teamshi.collectionsystem3.datastructure.NARig;
import com.teamshi.collectionsystem3.datastructure.OriginalSamplingRig;
import com.teamshi.collectionsystem3.datastructure.OtherSamplingRig;
import com.teamshi.collectionsystem3.datastructure.RegularRig;
import com.teamshi.collectionsystem3.datastructure.Rig;
import com.teamshi.collectionsystem3.datastructure.RigGraphData;
import com.teamshi.collectionsystem3.datastructure.SPTRig;
import com.teamshi.collectionsystem3.datastructure.TRRig;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class RigIndexActivity extends AppCompatActivity {
    private static final String TAG = "Collectionsystem3";

    private static final int ACTION_ADD_RIG = 0;
    private static final int ACTION_EDIT_RIG = 1;
    private static final int ACTION_COPY_RIG = 2;
    private static final int ACTION_EDIT_GRAPH_DATA = 3;

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
    private Button splitRigButton;

    private boolean waterDepthFlag = false;
    private boolean refreshLock = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rig_index);

        this.setTitle("钻探信息表");

        rigListTableLayout = (TableLayout) findViewById(R.id.tablelayout_riglist);

        holeListSpinner = (Spinner)findViewById(R.id.spinner_hole_list);
        addRigButton = (Button) findViewById(R.id.button_add_rig);
        splitRigButton = (Button) findViewById(R.id.button_split_layer);

        holeListSpinnerOptions = DataManager.getHoleIdOptionList();
        holeListSpinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, holeListSpinnerOptions);
        holeListSpinner.setAdapter(holeListSpinnerAdapter);

        addRigButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "newRigButton clicked.");
                AlertDialog typeDialog;
                final CharSequence[] items = {"搬家移孔, 下雨停工, 其它", "干钻, 合水钻, 金刚石钻, 钢粒钻", "标准贯入试验", "动力触探试验", "下套管", "原状样", "扰动样", "岩样", "水样"};

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
                            case 2:
                                intent = new Intent(RigIndexActivity.this, SPTRigActivity.class);
                                intent.putExtra("requestCode", "ACTION_ADD_RIG");
                                intent.putExtra("holeId", holeId);
                                startActivityForResult(intent, ACTION_ADD_RIG);
                                break;
                            case 3:
                                intent = new Intent(RigIndexActivity.this, DSTRigActivity.class);
                                intent.putExtra("requestCode", "ACTION_ADD_RIG");
                                intent.putExtra("holeId", holeId);
                                startActivityForResult(intent, ACTION_ADD_RIG);
                                break;
                            case 4:
                                intent = new Intent(RigIndexActivity.this, TRRigActivity.class);
                                intent.putExtra("requestCode", "ACTION_ADD_RIG");
                                intent.putExtra("holeId", holeId);
                                startActivityForResult(intent, ACTION_ADD_RIG);
                                break;
                            case 5:
                                intent = new Intent(RigIndexActivity.this, OriginalSamplingRigActivity.class);
                                intent.putExtra("requestCode", "ACTION_ADD_RIG");
                                intent.putExtra("holeId", holeId);
                                startActivityForResult(intent, ACTION_ADD_RIG);
                                break;
                            case 6:
                                intent = new Intent(RigIndexActivity.this, OtherSamplingRigActivity.class);
                                intent.putExtra("requestCode", "ACTION_DISTURBANCE_SAMPLE");
                                intent.putExtra("holeId", holeId);
                                startActivityForResult(intent, ACTION_ADD_RIG);
                                break;
                            case 7:
                                intent = new Intent(RigIndexActivity.this, OtherSamplingRigActivity.class);
                                intent.putExtra("requestCode", "ACTION_ROCK_SAMPLE");
                                intent.putExtra("holeId", holeId);
                                startActivityForResult(intent, ACTION_ADD_RIG);
                                break;
                            case 8:
                                intent = new Intent(RigIndexActivity.this, OtherSamplingRigActivity.class);
                                intent.putExtra("requestCode", "ACTION_WATER_SAMPLE");
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

        splitRigButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RigIndexActivity.this, RigGraphActivity.class);
                intent.putExtra("holeId", holeId);
                startActivityForResult(intent, ACTION_EDIT_GRAPH_DATA);
            }
        });

        holeListSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!refreshLock) {
                    holeId = holeListSpinnerOptions[position];
                    hole = DataManager.getHole(holeId);
                    refreshInfo();
                }
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
                } else if (rig instanceof SPTRig) {
                    intent = new Intent(RigIndexActivity.this, SPTRigActivity.class);
                    intent.putExtra("requestCode", "ACTION_EDIT_RIG");
                    intent.putExtra("holeId", holeId);
                    intent.putExtra("rigIndex", selectedRigIndex);
                    startActivityForResult(intent, ACTION_EDIT_RIG);
                } else if (rig instanceof DSTRig) {
                    intent = new Intent(RigIndexActivity.this, SPTRigActivity.class);
                    intent.putExtra("requestCode", "ACTION_EDIT_RIG");
                    intent.putExtra("holeId", holeId);
                    intent.putExtra("rigIndex", selectedRigIndex);
                    startActivityForResult(intent, ACTION_EDIT_RIG);
                } else if (rig instanceof TRRig) {
                    intent = new Intent(RigIndexActivity.this, TRRigActivity.class);
                    intent.putExtra("requestCode", "ACTION_EDIT_RIG");
                    intent.putExtra("holeId", holeId);
                    intent.putExtra("rigIndex", selectedRigIndex);
                    startActivityForResult(intent, ACTION_EDIT_RIG);
                } else if (rig instanceof OriginalSamplingRig) {
                    intent = new Intent(RigIndexActivity.this, OriginalSamplingRigActivity.class);
                    intent.putExtra("requestCode", "ACTION_EDIT_RIG");
                    intent.putExtra("holeId", holeId);
                    intent.putExtra("rigIndex", selectedRigIndex);
                    startActivityForResult(intent, ACTION_EDIT_RIG);
                } else if (rig instanceof OtherSamplingRig) {
                    intent = new Intent(RigIndexActivity.this, OtherSamplingRigActivity.class);
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
                    intent = new Intent(RigIndexActivity.this, RegularRigActivity.class);
                    intent.putExtra("requestCode", "ACTION_COPY_RIG");
                    intent.putExtra("rigIndex", selectedRigIndex);
                    intent.putExtra("holeId", holeId);
                    startActivityForResult(intent, ACTION_COPY_RIG);
                } else if (rig instanceof SPTRig) {
                    Toast.makeText(RigIndexActivity.this, "标准贯入试验不能复制新增.", Toast.LENGTH_SHORT).show();
                } else if (rig instanceof DSTRig) {
                    Toast.makeText(RigIndexActivity.this, "动力触探试验不能复制新增.", Toast.LENGTH_SHORT).show();
                } else if (rig instanceof TRRig) {
                    Toast.makeText(RigIndexActivity.this, "下套管试验不能复制新增.", Toast.LENGTH_SHORT).show();
                } else if (rig instanceof OriginalSamplingRig) {
                    Toast.makeText(RigIndexActivity.this, "原状样不能复制新增.", Toast.LENGTH_SHORT).show();
                } else if (rig instanceof OtherSamplingRig) {
                    Toast.makeText(RigIndexActivity.this, "扰动样, 岩样, 水样不能复制新增.", Toast.LENGTH_SHORT).show();
                }

                break;
            case CONTEXT_MENU_DELETE:
                Log.d(TAG, "DeleteRig of holeId: " + holeId + ", rig index: " + selectedRigIndex);
                if (selectedRigIndex != DataManager.getHole(holeId).getRigList().size() - 1) {
                    Toast.makeText(RigIndexActivity.this, "只能删除最后一次的作业信息.", Toast.LENGTH_LONG).show();
                } else {
                    Rig deletingRig = DataManager.getLastRig(holeId);

                    DataManager.getHole(holeId).setLastRigEndTime(deletingRig.getLastRigEndTime());
                    DataManager.getHole(holeId).setLastRockCorePipeLength(deletingRig.getLastRockCorePipeLength());
                    DataManager.getHole(holeId).setLastAccumulatedMeterageLength(deletingRig.getLastAccumulatedMeterageLength());

                    if (deletingRig.getLastPipeNumber() != DataManager.getHole(holeId).getPipeCount()) {
                        DataManager.getHole(holeId).removeLastPipe();
                    }

                    if (deletingRig.getLastMaxRigRockCoreIndex() != DataManager.getHole(holeId).getMaxRigRockCoreIndex()) {
                        DataManager.getHole(holeId).setMaxRigRockCoreIndex(deletingRig.getLastMaxRigRockCoreIndex());
                    }

                    if (deletingRig instanceof RegularRig ||
                            deletingRig instanceof SPTRig ||
                            deletingRig instanceof OriginalSamplingRig) {
                        DataManager.getHole(holeId).setRockCoreIndex(DataManager.getHole(holeId).getRockCoreIndex() - 1);
                    }

                    if (deletingRig instanceof OriginalSamplingRig) {
                        DataManager.getHole(holeId).setOriginalSampleIndex(DataManager.getHole(holeId).getOriginalSampleIndex() - 1);
                    }

                    if (deletingRig instanceof TRRig) {
                        DataManager.getHole(holeId).setLastTRIndex(DataManager.getHole(holeId).getLastTRIndex() - ((TRRig) deletingRig).getTrInfos().size());
                    }

                    DataManager.getHole(holeId).setLastRockName(deletingRig.getLastRockName());
                    DataManager.getHole(holeId).setLastRockColor(deletingRig.getLastRockColor());
                    DataManager.getHole(holeId).setLastRockSaturation(deletingRig.getLastRockSaturation());
                    DataManager.getHole(holeId).setLastRockDentisy(deletingRig.getLastRockDentisy());
                    DataManager.getHole(holeId).setLastRockWeathering(deletingRig.getLastRockWeathering());

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
        if (refreshLock) {
            return;
        }

        refreshLock = true;
        Log.d(TAG, "Clear table content.");
        while (rigListTableLayout.getChildCount() != 3) {
            rigListTableLayout.removeViewAt(3);
        }

        Log.d(TAG, "Draw table.");

        waterDepthFlag = false;

        ArrayList<Rig> viewList = hole.getRigIndexViewList();

        for (int i = 0; i < viewList.size(); i++) {
            TableRow row = new TableRow(this);

            row.setBackgroundColor(getResources().getColor(android.R.color.white));

            TableLayout.LayoutParams param = new TableLayout.LayoutParams();
            param.height = TableLayout.LayoutParams.WRAP_CONTENT;
            param.width = TableLayout.LayoutParams.WRAP_CONTENT;

            row.setLayoutParams(param);

            if (viewList.get(i) instanceof NARig) {
                NARig rig = (NARig) viewList.get(i);

                for (TextView tv : generateNARigRowContent(rig, i + 1 < viewList.size()? viewList.get(i + 1): null)) {
                    row.addView(tv);
                }
            } else if (viewList.get(i) instanceof RegularRig) {
                RegularRig rig = (RegularRig) viewList.get(i);

                for (TextView tv : generateRegularRigRowContent(rig, i + 1 < viewList.size()? viewList.get(i + 1): null)) {
                    row.addView(tv);
                }
            } else if (viewList.get(i) instanceof SPTRig) {
                SPTRig rig = (SPTRig) viewList.get(i);

                for (TextView tv : generateSPTRigRowContent(rig, i + 1 < viewList.size()? viewList.get(i + 1): null)) {
                    row.addView(tv);
                }
            } else if (viewList.get(i) instanceof DSTRig) {
                DSTRig rig = (DSTRig) viewList.get(i);
                for (TextView tv : generateDSTRigRowContent(rig, i + 1 < viewList.size()? viewList.get(i + 1): null)) {
                    row.addView(tv);
                }
            } else if (viewList.get(i) instanceof TRRig) {
                TRRig rig = (TRRig) viewList.get(i);
                for (TextView tv : generateTRRigRowContent(rig, i + 1 < viewList.size()? viewList.get(i + 1): null)) {
                    row.addView(tv);
                }
            } else if (viewList.get(i) instanceof OriginalSamplingRig) {
                OriginalSamplingRig rig = (OriginalSamplingRig) viewList.get(i);
                for (TextView tv : generateOriginalSamplingRigContent(rig)) {
                    row.addView(tv);
                }
            } else if (viewList.get(i) instanceof OtherSamplingRig.OtherSamplingDetail) {
                continue;
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
                    } else if (rig instanceof SPTRig) {
                        intent = new Intent(RigIndexActivity.this, SPTRigActivity.class);
                        intent.putExtra("requestCode", "ACTION_EDIT_RIG");
                        intent.putExtra("holeId", holeId);
                        intent.putExtra("rigIndex", selectedRigIndex);
                        startActivityForResult(intent, ACTION_EDIT_RIG);
                    } else if (rig instanceof DSTRig) {
                        intent = new Intent(RigIndexActivity.this, DSTRigActivity.class);
                        intent.putExtra("requestCode", "ACTION_EDIT_RIG");
                        intent.putExtra("holeId", holeId);
                        intent.putExtra("rigIndex", selectedRigIndex);
                        startActivityForResult(intent, ACTION_EDIT_RIG);
                    } else if (rig instanceof TRRig) {
                        intent = new Intent(RigIndexActivity.this, TRRigActivity.class);
                        intent.putExtra("requestCode", "ACTION_EDIT_RIG");
                        intent.putExtra("holeId", holeId);
                        intent.putExtra("rigIndex", selectedRigIndex);
                        startActivityForResult(intent, ACTION_EDIT_RIG);
                    } else if (rig instanceof OriginalSamplingRig) {
                        intent = new Intent(RigIndexActivity.this, OriginalSamplingRigActivity.class);
                        intent.putExtra("requestCode", "ACTION_EDIT_RIG");
                        intent.putExtra("holeId", holeId);
                        intent.putExtra("rigIndex", selectedRigIndex);
                        startActivityForResult(intent, ACTION_EDIT_RIG);
                    } else if (rig instanceof OtherSamplingRig) {
                        intent = new Intent(RigIndexActivity.this, OtherSamplingRigActivity.class);
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

        refreshLock = false;
    }

    private TextView generateRigInfoCell(String s) {
        TextView tv = new TextView(this);

        tv.setText(s);
        tv.setBackground(getResources().getDrawable(R.drawable.cell_input));

        tv.setTextSize(getResources().getDimension(R.dimen.default_text_size) / getResources().getDisplayMetrics().density);

        int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, getResources().getDimension(R.dimen.default_table_height) / getResources().getDisplayMetrics().density, getResources().getDisplayMetrics());
        TableRow.LayoutParams param = new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT, 0f);

        int paddingInDp = 2 * (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, getResources().getDimension(R.dimen.default_table_padding) / getResources().getDisplayMetrics().density, getResources().getDisplayMetrics());

        tv.setPadding(paddingInDp, paddingInDp, paddingInDp, paddingInDp);

        tv.setLayoutParams(param);

        tv.setGravity(Gravity.CENTER);

        return tv;
    }

    private ArrayList<TextView> generateNARigRowContent(NARig rig, Rig nextRig) {
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

        if (nextRig != null
                && nextRig instanceof OtherSamplingRig.OtherSamplingDetail
                && (((OtherSamplingRig.OtherSamplingDetail) nextRig).getSamplingType().equals("扰动样") || ((OtherSamplingRig.OtherSamplingDetail) nextRig).getSamplingType().equals("岩样"))) {
            result.add(generateRigInfoCell(((OtherSamplingRig.OtherSamplingDetail) nextRig).getIndex()));
            int diameter = ((OtherSamplingRig.OtherSamplingDetail) nextRig).getDiameter();
            if (diameter == -1) {
                result.add(generateRigInfoCell(""));
            } else {
                result.add(generateRigInfoCell(String.valueOf(diameter)));
            }
            result.add(generateRigInfoCell(Utility.formatDouble(((OtherSamplingRig.OtherSamplingDetail) nextRig).getStartDepth()) + " ~ " + Utility.formatDouble(((OtherSamplingRig.OtherSamplingDetail) nextRig).getEndDepth())));
            result.add(generateRigInfoCell(String.valueOf(((OtherSamplingRig.OtherSamplingDetail) nextRig).getCount())));
        } else {
            result.add(generateRigInfoCell(""));
            result.add(generateRigInfoCell(""));
            result.add(generateRigInfoCell(""));
            result.add(generateRigInfoCell(""));
        }

        if (nextRig != null
                && nextRig instanceof OtherSamplingRig.OtherSamplingDetail
                && ((OtherSamplingRig.OtherSamplingDetail) nextRig).getSamplingType().equals("水样")) {
            result.add(generateRigInfoCell(((OtherSamplingRig.OtherSamplingDetail) nextRig).getIndex()));
            result.add(generateRigInfoCell(Utility.formatDouble(((OtherSamplingRig.OtherSamplingDetail) nextRig).getStartDepth()) + " ~ " + Utility.formatDouble(((OtherSamplingRig.OtherSamplingDetail) nextRig).getEndDepth())));
            result.add(generateRigInfoCell(String.valueOf(((OtherSamplingRig.OtherSamplingDetail) nextRig).getCount())));
        } else {
            result.add(generateRigInfoCell(""));
            result.add(generateRigInfoCell(""));
            result.add(generateRigInfoCell(""));
        }

        result.add(generateRigInfoCell(""));
        result.add(generateRigInfoCell(""));
        result.add(generateRigInfoCell(""));
        result.add(generateRigInfoCell(""));
        result.add(generateRigInfoCell(""));

        result.add(generateRigInfoCell(""));
        if (!waterDepthFlag) {
            result.add(generateRigInfoCell(hole.getInitialWaterDepth() < 0? "未见": Utility.formatDouble(hole.getInitialWaterDepth())));
            result.add(generateRigInfoCell(hole.getFinalWaterDepth() < 0? "未见": Utility.formatDouble(hole.getFinalWaterDepth())));

            waterDepthFlag = true;
        } else {
            result.add(generateRigInfoCell(""));
            result.add(generateRigInfoCell(""));
        }
        result.add(generateRigInfoCell(""));

        result.add(generateRigInfoCell(""));

        return result;
    }

    private ArrayList<TextView> generateRegularRigRowContent(RegularRig rig, Rig nextRig) {
        ArrayList<TextView> result = new ArrayList<>();

        result.add(generateRigInfoCell(rig.getClassPeopleCount()));

        result.add(generateRigInfoCell(Utility.formatCalendarDateStringWithoutYear(rig.getDate())));
        result.add(generateRigInfoCell(Utility.formatTimeStringChinese(rig.getStartTime())));
        result.add(generateRigInfoCell(Utility.formatTimeStringChinese(rig.getEndTime())));
        result.add(generateRigInfoCell(Utility.calculateTimeSpanChinese(rig.getStartTime(), rig.getEndTime())));

        result.add(generateRigInfoCell(rig.getRigType()));

        result.add(generateRigInfoCell(String.valueOf(rig.getPipeNumber())));
        result.add(generateRigInfoCell(Utility.formatDouble(rig.getPipeLength())));
        result.add(generateRigInfoCell(Utility.formatDouble(rig.getPipeTotalLength())));

        result.add(generateRigInfoCell(String.valueOf(rig.getRockCorePipeDiameter())));
        result.add(generateRigInfoCell(Utility.formatDouble(rig.getRockCorePipeLength())));

        result.add(generateRigInfoCell(rig.getDrillBitType()));
        result.add(generateRigInfoCell(String.valueOf(rig.getDrillBitDiameter())));
        result.add(generateRigInfoCell(Utility.formatDouble(rig.getDrillBitLength())));

        result.add(generateRigInfoCell(Utility.formatDouble(rig.getDrillToolTotalLength())));
        result.add(generateRigInfoCell(Utility.formatDouble(rig.getDrillPipeRemainLength())));
        result.add(generateRigInfoCell(Utility.formatDouble(rig.getRoundTripMeterageLength())));
        result.add(generateRigInfoCell(Utility.formatDouble(rig.getAccumulatedMeterageLength())));

        result.add(generateRigInfoCell(""));
        result.add(generateRigInfoCell(""));
        result.add(generateRigInfoCell(""));
        result.add(generateRigInfoCell(""));
        result.add(generateRigInfoCell(""));

        result.add(generateRigInfoCell(""));

        result.add(generateRigInfoCell(String.valueOf(rig.getRockCoreIndex())));
        result.add(generateRigInfoCell(Utility.formatDouble(rig.getRockCoreLength())));
        result.add(generateRigInfoCell(Utility.formatDouble(rig.getRockCorePickPercentage() * 100) + "%"));

        if (nextRig != null
                && nextRig instanceof OtherSamplingRig.OtherSamplingDetail
                && (((OtherSamplingRig.OtherSamplingDetail) nextRig).getSamplingType().equals("扰动样") || ((OtherSamplingRig.OtherSamplingDetail) nextRig).getSamplingType().equals("岩样"))) {
            result.add(generateRigInfoCell(((OtherSamplingRig.OtherSamplingDetail) nextRig).getIndex()));
            int diameter = ((OtherSamplingRig.OtherSamplingDetail) nextRig).getDiameter();
            if (diameter == -1) {
                result.add(generateRigInfoCell(""));
            } else {
                result.add(generateRigInfoCell(String.valueOf(diameter)));
            }            result.add(generateRigInfoCell(Utility.formatDouble(((OtherSamplingRig.OtherSamplingDetail) nextRig).getStartDepth()) + " ~ " + Utility.formatDouble(((OtherSamplingRig.OtherSamplingDetail) nextRig).getEndDepth())));
            result.add(generateRigInfoCell(String.valueOf(((OtherSamplingRig.OtherSamplingDetail) nextRig).getCount())));
        } else {
            result.add(generateRigInfoCell(""));
            result.add(generateRigInfoCell(""));
            result.add(generateRigInfoCell(""));
            result.add(generateRigInfoCell(""));
        }

        if (nextRig != null
                && nextRig instanceof OtherSamplingRig.OtherSamplingDetail
                && ((OtherSamplingRig.OtherSamplingDetail) nextRig).getSamplingType().equals("水样")) {
            result.add(generateRigInfoCell(((OtherSamplingRig.OtherSamplingDetail) nextRig).getIndex()));
            result.add(generateRigInfoCell(Utility.formatDouble(((OtherSamplingRig.OtherSamplingDetail) nextRig).getStartDepth()) + " ~ " + Utility.formatDouble(((OtherSamplingRig.OtherSamplingDetail) nextRig).getEndDepth())));
            result.add(generateRigInfoCell(String.valueOf(((OtherSamplingRig.OtherSamplingDetail) nextRig).getCount())));
        } else {
            result.add(generateRigInfoCell(""));
            result.add(generateRigInfoCell(""));
            result.add(generateRigInfoCell(""));
        }

        result.add(generateRigInfoCell(""));
        result.add(generateRigInfoCell(""));
        result.add(generateRigInfoCell(""));
        result.add(generateRigInfoCell(rig.getRockDescription()));
        result.add(generateRigInfoCell(""));

        result.add(generateRigInfoCell(""));
        if (!waterDepthFlag) {
            result.add(generateRigInfoCell(hole.getInitialWaterDepth() < 0? "未见": Utility.formatDouble(hole.getInitialWaterDepth())));
            result.add(generateRigInfoCell(hole.getFinalWaterDepth() < 0? "未见": Utility.formatDouble(hole.getFinalWaterDepth())));

            waterDepthFlag = true;
        } else {
            result.add(generateRigInfoCell(""));
            result.add(generateRigInfoCell(""));
        }
        result.add(generateRigInfoCell(""));

        result.add(generateRigInfoCell(""));

        return result;
    }

    private ArrayList<TextView> generateSPTRigRowContent(SPTRig rig, Rig nextRig) {
        ArrayList<TextView> result = new ArrayList<>();

        result.add(generateRigInfoCell(rig.getClassPeopleCount()));

        result.add(generateRigInfoCell(Utility.formatCalendarDateStringWithoutYear(rig.getDate())));
        result.add(generateRigInfoCell(Utility.formatTimeStringChinese(rig.getStartTime())));
        result.add(generateRigInfoCell(Utility.formatTimeStringChinese(rig.getEndTime())));
        result.add(generateRigInfoCell(Utility.calculateTimeSpanChinese(rig.getStartTime(), rig.getEndTime())));

        result.add(generateRigInfoCell("标准贯入试验"));

        result.add(generateRigInfoCell(""));
        result.add(generateRigInfoCell(""));
        result.add(generateRigInfoCell(Utility.formatDouble(rig.getDrillToolTotalLength() - 0.5)));

        result.add(generateRigInfoCell("51"));
        result.add(generateRigInfoCell("0.5"));

        result.add(generateRigInfoCell(""));
        result.add(generateRigInfoCell(""));
        result.add(generateRigInfoCell(""));

        result.add(generateRigInfoCell(Utility.formatDouble(rig.getDrillToolTotalLength())));
        result.add(generateRigInfoCell(Utility.formatDouble(rig.getDrillPipeRemainLength())));
        result.add(generateRigInfoCell(Utility.formatDouble(rig.getRoundTripMeterageLength())));
        result.add(generateRigInfoCell(Utility.formatDouble(rig.getAccumulatedMeterageLength())));

        result.add(generateRigInfoCell(""));
        result.add(generateRigInfoCell(""));
        result.add(generateRigInfoCell(""));
        result.add(generateRigInfoCell(""));
        result.add(generateRigInfoCell(""));

        result.add(generateRigInfoCell(""));

        result.add(generateRigInfoCell(String.valueOf(rig.getRockCoreIndex())));
        result.add(generateRigInfoCell(Utility.formatDouble(rig.getRoundTripMeterageLength())));
        result.add(generateRigInfoCell("100%"));

        if (nextRig != null
                && nextRig instanceof OtherSamplingRig.OtherSamplingDetail
                && (((OtherSamplingRig.OtherSamplingDetail) nextRig).getSamplingType().equals("扰动样") || ((OtherSamplingRig.OtherSamplingDetail) nextRig).getSamplingType().equals("岩样"))) {
            result.add(generateRigInfoCell(((OtherSamplingRig.OtherSamplingDetail) nextRig).getIndex()));
            int diameter = ((OtherSamplingRig.OtherSamplingDetail) nextRig).getDiameter();
            if (diameter == -1) {
                result.add(generateRigInfoCell(""));
            } else {
                result.add(generateRigInfoCell(String.valueOf(diameter)));
            }            result.add(generateRigInfoCell(Utility.formatDouble(((OtherSamplingRig.OtherSamplingDetail) nextRig).getStartDepth()) + " ~ " + Utility.formatDouble(((OtherSamplingRig.OtherSamplingDetail) nextRig).getEndDepth())));
            result.add(generateRigInfoCell(String.valueOf(((OtherSamplingRig.OtherSamplingDetail) nextRig).getCount())));
        } else {
            result.add(generateRigInfoCell(""));
            result.add(generateRigInfoCell(""));
            result.add(generateRigInfoCell(""));
            result.add(generateRigInfoCell(""));
        }

        if (nextRig != null
                && nextRig instanceof OtherSamplingRig.OtherSamplingDetail
                && ((OtherSamplingRig.OtherSamplingDetail) nextRig).getSamplingType().equals("水样")) {
            result.add(generateRigInfoCell(((OtherSamplingRig.OtherSamplingDetail) nextRig).getIndex()));
            result.add(generateRigInfoCell(Utility.formatDouble(((OtherSamplingRig.OtherSamplingDetail) nextRig).getStartDepth()) + " ~ " + Utility.formatDouble(((OtherSamplingRig.OtherSamplingDetail) nextRig).getEndDepth())));
            result.add(generateRigInfoCell(String.valueOf(((OtherSamplingRig.OtherSamplingDetail) nextRig).getCount())));
        } else {
            result.add(generateRigInfoCell(""));
            result.add(generateRigInfoCell(""));
            result.add(generateRigInfoCell(""));
        }

        result.add(generateRigInfoCell(""));
        result.add(generateRigInfoCell(""));
        result.add(generateRigInfoCell(""));
        result.add(generateRigInfoCell(rig.getRockDescription()));
        result.add(generateRigInfoCell(""));

        result.add(generateRigInfoCell(""));
        if (!waterDepthFlag) {
            result.add(generateRigInfoCell(hole.getInitialWaterDepth() < 0? "未见": Utility.formatDouble(hole.getInitialWaterDepth())));
            result.add(generateRigInfoCell(hole.getFinalWaterDepth() < 0? "未见": Utility.formatDouble(hole.getFinalWaterDepth())));

            waterDepthFlag = true;
        } else {
            result.add(generateRigInfoCell(""));
            result.add(generateRigInfoCell(""));
        }
        result.add(generateRigInfoCell(""));

        result.add(generateRigInfoCell(""));

        return result;
    }

    private ArrayList<TextView> generateDSTRigRowContent(DSTRig rig, Rig nextRig) {
        ArrayList<TextView> result = new ArrayList<>();

        result.add(generateRigInfoCell(rig.getClassPeopleCount()));

        result.add(generateRigInfoCell(Utility.formatCalendarDateStringWithoutYear(rig.getDate())));
        result.add(generateRigInfoCell(Utility.formatTimeStringChinese(rig.getStartTime())));
        result.add(generateRigInfoCell(Utility.formatTimeStringChinese(rig.getEndTime())));
        result.add(generateRigInfoCell(Utility.calculateTimeSpanChinese(rig.getStartTime(), rig.getEndTime())));

        result.add(generateRigInfoCell("动力触探试验"));

        result.add(generateRigInfoCell(""));
        result.add(generateRigInfoCell(""));
        result.add(generateRigInfoCell(Utility.formatDouble(rig.getDrillToolTotalLength() - 0.25)));

        result.add(generateRigInfoCell("74"));
        result.add(generateRigInfoCell("0.25"));

        result.add(generateRigInfoCell(""));
        result.add(generateRigInfoCell(""));
        result.add(generateRigInfoCell(""));

        result.add(generateRigInfoCell(Utility.formatDouble(rig.getDrillToolTotalLength())));
        result.add(generateRigInfoCell(Utility.formatDouble(rig.getDrillPipeRemainLength())));
        result.add(generateRigInfoCell(Utility.formatDouble(rig.getRoundTripMeterageLength())));
        result.add(generateRigInfoCell(Utility.formatDouble(rig.getAccumulatedMeterageLength())));

        result.add(generateRigInfoCell(""));
        result.add(generateRigInfoCell(""));
        result.add(generateRigInfoCell(""));
        result.add(generateRigInfoCell(""));
        result.add(generateRigInfoCell(""));

        result.add(generateRigInfoCell(""));

        result.add(generateRigInfoCell(""));
        result.add(generateRigInfoCell(""));
        result.add(generateRigInfoCell(""));

        if (nextRig != null
                && nextRig instanceof OtherSamplingRig.OtherSamplingDetail
                && (((OtherSamplingRig.OtherSamplingDetail) nextRig).getSamplingType().equals("扰动样") || ((OtherSamplingRig.OtherSamplingDetail) nextRig).getSamplingType().equals("岩样"))) {
            result.add(generateRigInfoCell(((OtherSamplingRig.OtherSamplingDetail) nextRig).getIndex()));
            int diameter = ((OtherSamplingRig.OtherSamplingDetail) nextRig).getDiameter();
            if (diameter == -1) {
                result.add(generateRigInfoCell(""));
            } else {
                result.add(generateRigInfoCell(String.valueOf(diameter)));
            }            result.add(generateRigInfoCell(Utility.formatDouble(((OtherSamplingRig.OtherSamplingDetail) nextRig).getStartDepth()) + " ~ " + Utility.formatDouble(((OtherSamplingRig.OtherSamplingDetail) nextRig).getEndDepth())));
            result.add(generateRigInfoCell(String.valueOf(((OtherSamplingRig.OtherSamplingDetail) nextRig).getCount())));
        } else {
            result.add(generateRigInfoCell(""));
            result.add(generateRigInfoCell(""));
            result.add(generateRigInfoCell(""));
            result.add(generateRigInfoCell(""));
        }

        if (nextRig != null
                && nextRig instanceof OtherSamplingRig.OtherSamplingDetail
                && ((OtherSamplingRig.OtherSamplingDetail) nextRig).getSamplingType().equals("水样")) {
            result.add(generateRigInfoCell(((OtherSamplingRig.OtherSamplingDetail) nextRig).getIndex()));
            result.add(generateRigInfoCell(Utility.formatDouble(((OtherSamplingRig.OtherSamplingDetail) nextRig).getStartDepth()) + " ~ " + Utility.formatDouble(((OtherSamplingRig.OtherSamplingDetail) nextRig).getEndDepth())));
            result.add(generateRigInfoCell(String.valueOf(((OtherSamplingRig.OtherSamplingDetail) nextRig).getCount())));
        } else {
            result.add(generateRigInfoCell(""));
            result.add(generateRigInfoCell(""));
            result.add(generateRigInfoCell(""));
        }

        result.add(generateRigInfoCell(""));
        result.add(generateRigInfoCell(""));
        result.add(generateRigInfoCell(""));
        result.add(generateRigInfoCell(rig.getRockDescription()));
        result.add(generateRigInfoCell(""));

        result.add(generateRigInfoCell(""));
        if (!waterDepthFlag) {
            result.add(generateRigInfoCell(hole.getInitialWaterDepth() < 0? "未见": Utility.formatDouble(hole.getInitialWaterDepth())));
            result.add(generateRigInfoCell(hole.getFinalWaterDepth() < 0? "未见": Utility.formatDouble(hole.getFinalWaterDepth())));

            waterDepthFlag = true;
        } else {
            result.add(generateRigInfoCell(""));
            result.add(generateRigInfoCell(""));
        }
        result.add(generateRigInfoCell(""));

        result.add(generateRigInfoCell(""));

        return result;
    }

    private ArrayList<TextView> generateTRRigRowContent(TRRig rig, Rig nextRig) {
        ArrayList<TextView> result = new ArrayList<>();

        result.add(generateRigInfoCell(rig.getClassPeopleCount()));

        result.add(generateRigInfoCell(Utility.formatCalendarDateStringWithoutYear(rig.getDate())));
        result.add(generateRigInfoCell(Utility.formatTimeStringChinese(rig.getStartTime())));
        result.add(generateRigInfoCell(Utility.formatTimeStringChinese(rig.getEndTime())));
        result.add(generateRigInfoCell(Utility.calculateTimeSpanChinese(rig.getStartTime(), rig.getEndTime())));

        result.add(generateRigInfoCell("下套管"));

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

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < rig.getTrInfos().size(); i++) {
            if (i == 0) {
                sb.append(rig.getTrInfos().get(i).getWallType());
            }

            if (i != rig.getTrInfos().size() - 1) {
                sb.append("\n");
            }

        }
        result.add(generateRigInfoCell(sb.toString()));

        sb = new StringBuilder();
        for (int i = 0; i < rig.getTrInfos().size(); i++) {
            sb.append(rig.getTrInfos().get(i).getIndex());
            if (i != rig.getTrInfos().size() - 1) {
                sb.append("\n");
            }

        }
        result.add(generateRigInfoCell(sb.toString()));

        sb = new StringBuilder();
        for (int i = 0; i < rig.getTrInfos().size(); i++) {
            sb.append(Utility.formatDouble(rig.getTrInfos().get(i).getDiameter()));
            if (i != rig.getTrInfos().size() - 1) {
                sb.append("\n");
            }

        }
        result.add(generateRigInfoCell(sb.toString()));

        sb = new StringBuilder();
        for (int i = 0; i < rig.getTrInfos().size(); i++) {
            sb.append(Utility.formatDouble(rig.getTrInfos().get(i).getLength()));
            if (i != rig.getTrInfos().size() - 1) {
                sb.append("\n");
            }

        }
        result.add(generateRigInfoCell(sb.toString()));

        sb = new StringBuilder();
        for (int i = 0; i < rig.getTrInfos().size(); i++) {
            if (i == rig.getTrInfos().size() - 1) {
                sb.append(Utility.formatDouble(rig.getTrInfos().get(i).getTotalLength()));
            }
            if (i != rig.getTrInfos().size() - 1) {
                sb.append("\n");
            }

        }
        result.add(generateRigInfoCell(sb.toString()));

        result.add(generateRigInfoCell(""));
        result.add(generateRigInfoCell(""));
        result.add(generateRigInfoCell(""));

        if (nextRig != null
                && nextRig instanceof OtherSamplingRig.OtherSamplingDetail
                && (((OtherSamplingRig.OtherSamplingDetail) nextRig).getSamplingType().equals("扰动样") || ((OtherSamplingRig.OtherSamplingDetail) nextRig).getSamplingType().equals("岩样"))) {
            result.add(generateRigInfoCell(((OtherSamplingRig.OtherSamplingDetail) nextRig).getIndex()));
            int diameter = ((OtherSamplingRig.OtherSamplingDetail) nextRig).getDiameter();
            if (diameter == -1) {
                result.add(generateRigInfoCell(""));
            } else {
                result.add(generateRigInfoCell(String.valueOf(diameter)));
            }            result.add(generateRigInfoCell(Utility.formatDouble(((OtherSamplingRig.OtherSamplingDetail) nextRig).getStartDepth()) + " ~ " + Utility.formatDouble(((OtherSamplingRig.OtherSamplingDetail) nextRig).getEndDepth())));
            result.add(generateRigInfoCell(String.valueOf(((OtherSamplingRig.OtherSamplingDetail) nextRig).getCount())));
        } else {
            result.add(generateRigInfoCell(""));
            result.add(generateRigInfoCell(""));
            result.add(generateRigInfoCell(""));
            result.add(generateRigInfoCell(""));
        }

        if (nextRig != null
                && nextRig instanceof OtherSamplingRig.OtherSamplingDetail
                && ((OtherSamplingRig.OtherSamplingDetail) nextRig).getSamplingType().equals("水样")) {
            result.add(generateRigInfoCell(((OtherSamplingRig.OtherSamplingDetail) nextRig).getIndex()));
            result.add(generateRigInfoCell(Utility.formatDouble(((OtherSamplingRig.OtherSamplingDetail) nextRig).getStartDepth()) + " ~ " + Utility.formatDouble(((OtherSamplingRig.OtherSamplingDetail) nextRig).getEndDepth())));
            result.add(generateRigInfoCell(String.valueOf(((OtherSamplingRig.OtherSamplingDetail) nextRig).getCount())));
        } else {
            result.add(generateRigInfoCell(""));
            result.add(generateRigInfoCell(""));
            result.add(generateRigInfoCell(""));
        }

        result.add(generateRigInfoCell(""));
        result.add(generateRigInfoCell(""));
        result.add(generateRigInfoCell(""));
        result.add(generateRigInfoCell(""));
        result.add(generateRigInfoCell(""));

        result.add(generateRigInfoCell(""));
        if (!waterDepthFlag) {
            result.add(generateRigInfoCell(hole.getInitialWaterDepth() < 0? "未见": Utility.formatDouble(hole.getInitialWaterDepth())));
            result.add(generateRigInfoCell(hole.getFinalWaterDepth() < 0? "未见": Utility.formatDouble(hole.getFinalWaterDepth())));

            waterDepthFlag = true;
        } else {
            result.add(generateRigInfoCell(""));
            result.add(generateRigInfoCell(""));
        }
        result.add(generateRigInfoCell(""));

        result.add(generateRigInfoCell(rig.getSpecialDescription()));

        return result;
    }

    private ArrayList<TextView> generateOriginalSamplingRigContent(OriginalSamplingRig rig) {
        ArrayList<TextView> result = new ArrayList<>();

        result.add(generateRigInfoCell(rig.getClassPeopleCount()));

        result.add(generateRigInfoCell(Utility.formatCalendarDateStringWithoutYear(rig.getDate())));

        result.add(generateRigInfoCell(Utility.formatTimeStringChinese(rig.getStartTime())));
        result.add(generateRigInfoCell(Utility.formatTimeStringChinese(rig.getEndTime())));
        result.add(generateRigInfoCell(Utility.calculateTimeSpanChinese(rig.getStartTime(), rig.getEndTime())));

        result.add(generateRigInfoCell("原状样"));

        result.add(generateRigInfoCell(""));
        result.add(generateRigInfoCell(""));
        if (rig.getSamplerPipeDiameter() == 0.9) {
            result.add(generateRigInfoCell(Utility.formatDouble(rig.getDrillToolTotalLength() - 0.9)));
        } else {
            result.add(generateRigInfoCell(Utility.formatDouble(rig.getDrillToolTotalLength() - 0.8)));
        }

        result.add(generateRigInfoCell(Utility.formatDouble(rig.getSamplerPipeDiameter())));
        result.add(generateRigInfoCell(Utility.formatDouble(rig.getSamplerPipeLength())));

        result.add(generateRigInfoCell(""));
        result.add(generateRigInfoCell(""));
        result.add(generateRigInfoCell(""));

        result.add(generateRigInfoCell(Utility.formatDouble(rig.getDrillToolTotalLength())));
        result.add(generateRigInfoCell(Utility.formatDouble(rig.getDrillPipeRemainLength())));
        result.add(generateRigInfoCell(Utility.formatDouble(rig.getRoundTripMeterageLength())));
        result.add(generateRigInfoCell(Utility.formatDouble(rig.getAccumulatedMeterageLength())));

        result.add(generateRigInfoCell(""));
        result.add(generateRigInfoCell(""));
        result.add(generateRigInfoCell(""));
        result.add(generateRigInfoCell(""));
        result.add(generateRigInfoCell(""));

        result.add(generateRigInfoCell(""));

        result.add(generateRigInfoCell(String.valueOf(rig.getRockCoreIndex())));
        result.add(generateRigInfoCell(Utility.formatDouble(rig.getRockCoreLength())));
        result.add(generateRigInfoCell(Utility.formatDouble(rig.getRockCorePickPercentage() * 100) + "%"));

        result.add(generateRigInfoCell(rig.getIndex()));
        result.add(generateRigInfoCell(Utility.formatDouble(rig.getSamplerPipeDiameter())));
        result.add(generateRigInfoCell(Utility.formatDouble(rig.getStartDepth()) + " ~ " + Utility.formatDouble(rig.getEndDepth())));
        result.add(generateRigInfoCell(String.valueOf(rig.getCount())));

        result.add(generateRigInfoCell(""));
        result.add(generateRigInfoCell(""));
        result.add(generateRigInfoCell(""));

        result.add(generateRigInfoCell(""));
        result.add(generateRigInfoCell(""));
        result.add(generateRigInfoCell(""));
        result.add(generateRigInfoCell(rig.getRockDescription()));
        result.add(generateRigInfoCell(""));

        result.add(generateRigInfoCell(""));
        if (!waterDepthFlag) {
            result.add(generateRigInfoCell(hole.getInitialWaterDepth() < 0? "未见": Utility.formatDouble(hole.getInitialWaterDepth())));
            result.add(generateRigInfoCell(hole.getFinalWaterDepth() < 0? "未见": Utility.formatDouble(hole.getFinalWaterDepth())));

            waterDepthFlag = true;
        } else {
            result.add(generateRigInfoCell(""));
            result.add(generateRigInfoCell(""));
        }
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

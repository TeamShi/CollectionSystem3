package com.teamshi.collectionsystem3;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.teamshi.collectionsystem3.datastructure.CalculatingRig;
import com.teamshi.collectionsystem3.datastructure.Hole;
import com.teamshi.collectionsystem3.datastructure.OriginalSamplingRig;
import com.teamshi.collectionsystem3.datastructure.OtherSamplingRig;
import com.teamshi.collectionsystem3.datastructure.RegularRig;
import com.teamshi.collectionsystem3.datastructure.Rig;
import com.teamshi.collectionsystem3.datastructure.RigGraphData;
import com.teamshi.collectionsystem3.datastructure.TRRig;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RigGraphActivity extends Activity {
    public static class GraphRigNodeRockInfo {
        private String rockName;
        private String rockColor;
        private String rockDensity;
        private String rockSaturation;
        private String rockWeathering;
        private String rockDescription;

        private double rockPickLength;
        private double rockPickPercentage;

        public GraphRigNodeRockInfo(String rockName, String rockColor, String rockDensity, String rockSaturation, String rockWeathering, String rockDescription, double rockPickLength, double rockPickPercentage) {
            this.rockName = rockName;
            this.rockColor = rockColor;
            this.rockDensity = rockDensity;
            this.rockSaturation = rockSaturation;
            this.rockWeathering = rockWeathering;
            this.rockDescription = rockDescription;
            this.rockPickLength = rockPickLength;
            this.rockPickPercentage = rockPickPercentage;
        }

        public String getRockName() {
            return rockName;
        }

        public void setRockName(String rockName) {
            this.rockName = rockName;
        }

        public String getRockColor() {
            return rockColor;
        }

        public void setRockColor(String rockColor) {
            this.rockColor = rockColor;
        }

        public String getRockDensity() {
            return rockDensity;
        }

        public void setRockDensity(String rockDensity) {
            this.rockDensity = rockDensity;
        }

        public String getRockSaturation() {
            return rockSaturation;
        }

        public void setRockSaturation(String rockSaturation) {
            this.rockSaturation = rockSaturation;
        }

        public String getRockWeathering() {
            return rockWeathering;
        }

        public void setRockWeathering(String rockWeathering) {
            this.rockWeathering = rockWeathering;
        }

        public String getRockDescription() {
            return rockDescription;
        }

        public void setRockDescription(String rockDescription) {
            this.rockDescription = rockDescription;
        }

        public double getRockPickLength() {
            return rockPickLength;
        }

        public void setRockPickLength(double rockPickLength) {
            this.rockPickLength = rockPickLength;
        }

        public double getRockPickPercentage() {
            return rockPickPercentage;
        }

        public void setRockPickPercentage(double rockPickPercentage) {
            this.rockPickPercentage = rockPickPercentage;
        }
    }
    private static final String TAG = "CollectionSystem3";

    private String holeId;


    private Hole holeViewModel;
    private RigGraphData graphDataViewModel;
    private RigGraphData.RigNode rigNodeViewModel;
    private GraphRigNodeRockInfo graphRigNodeRockInfoViewModel;

    private TableLayout rockDescriptionTableLayout;
    private TableLayout splitRockDescriptionTableLayout;
    private LinearLayout rigGprahDetailLinearLayout;

    private Button confirmGraphButton;
    private Button cancelGraphButton;
    private Button addGraphButton;
    private Button deleteGraphButton;
    private Button clearGraphButton;

    public Button confirmRigDetailGraphButton;

    private EditText detailRockCoreIndexEditText;
    private EditText detailEndLengthEditText;
    private TextView detailRoundTripLengthTextView;

    private EditText detailRockCoreLengthEditText;
    private TextView detailRockCorePickPercentageTextView;

    private EditText rockTypeEditText;
    private Button rockTypeButton;

    private EditText rockColorEditText;
    private Button rockColorButton;
    private EditText rockDensityEditText;
    private Button rockDensityButton;

    private EditText rockSaturationEditText;
    private Button rockSaturationButton;
    private EditText rockWeatheringEditText;
    private Button rockWeatheringButton;

    private static final CharSequence[] ROCK_TYPE_OPTIONS = {"黏土", "杂填土", "素填土", "吹填土", "~~土", "粉质黏土", "粉土", "粉砂", "细砂", "中砂" , "粗砂", "砾砂", "漂石",
            "块石", "卵石", "碎石", "粗圆砾", "粗角砾", "细圆砾", "细角砾", "泥岩", "砂岩", "灰岩", "花岗岩", "~~岩"};
    private static final CharSequence[] ROCK_COLOR_OPTIONS = {"灰色", "青灰色", "深灰色", "紫色", "棕黄色", "浅黄色", "褐黄色", "红褐色", "棕红色", "棕色", "褐色", "黄褐色",
            "青色","灰绿色","浅紫色", "暗红色", "黑色", "浅蓝色", "蓝色"};
    private static final CharSequence[] ROCK_DENSITY_OPTIONS = {"坚硬", "硬塑", "软塑", "流塑", "可塑", "稍密", "中密", "密实", "松散"};
    private static final CharSequence[] ROCK_SATURATION_OPTIONS = {"稍湿", "潮湿", "饱和"};
    private static final CharSequence[] ROCK_WEATHERING_OPTIONS = {"全风化", "强风化", "中风化", "弱风化", "微风化", "未风化"};

    private Button generateRockDescriptionButton;
    private Button loadRockDescriptionTemplateButton;
    private EditText rockDescriptionEditText;

    private boolean refreshLock;

    public RigGraphActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rig_graph);

        refreshLock = false;

        rockDescriptionTableLayout = (TableLayout) findViewById(R.id.table_layout_rock_description_list);
        splitRockDescriptionTableLayout = (TableLayout) findViewById(R.id.table_layout_split_rock_description);
        rigGprahDetailLinearLayout = (LinearLayout) findViewById(R.id.linear_layout_rig_graph_detail);

        confirmGraphButton = (Button) findViewById(R.id.button_confirm_hole_graph);
        deleteGraphButton = (Button) findViewById(R.id.button_cancel_hole_graph);
        addGraphButton = (Button) findViewById(R.id.button_add_hole_graph);
        deleteGraphButton = (Button) findViewById(R.id.button_delete_hole_graph);
        clearGraphButton = (Button) findViewById(R.id.button_clear_hole_graph);
        cancelGraphButton = (Button) findViewById(R.id.button_cancel_hole_graph);

        confirmRigDetailGraphButton = (Button) findViewById(R.id.button_confirm_rig_detail_graph);

        detailRockCoreIndexEditText = (EditText) findViewById(R.id.edittext_graph_detail_rig_rock_core_index);
        detailEndLengthEditText = (EditText) findViewById(R.id.edittext_graph_detail_end_length);
        detailRoundTripLengthTextView = (TextView) findViewById(R.id.textview_graph_detail_round_trip_length);

        detailRockCoreLengthEditText = (EditText) findViewById(R.id.edittext_graph_detail_rock_pick_up_length);
        detailRockCorePickPercentageTextView = (TextView) findViewById(R.id.textview_graph_detail_pick_up_percentage);

        rockTypeEditText = (EditText) findViewById(R.id.edittext_graph_detail_rock_type);
        rockTypeButton = (Button) findViewById(R.id.button_graph_detail_rock_type);

        rockColorEditText = (EditText) findViewById(R.id.edittext_graph_detail_rock_color);
        rockColorButton = (Button) findViewById(R.id.button_graph_detail_rock_color);
        rockDensityEditText = (EditText) findViewById(R.id.edittext_graph_detail_rock_density);
        rockDensityButton = (Button) findViewById(R.id.button_graph_detail_rock_density);

        rockSaturationEditText = (EditText) findViewById(R.id.edittext_graph_detail_rock_saturation);
        rockSaturationButton = (Button) findViewById(R.id.button_graph_detail_rock_saturation);
        rockWeatheringEditText = (EditText) findViewById(R.id.edittext_graph_detail_rock_weathering);
        rockWeatheringButton = (Button) findViewById(R.id.button_graph_detail_rock_weathering);

        generateRockDescriptionButton = (Button) findViewById(R.id.button_graph_detail_generate_rock_description);
        loadRockDescriptionTemplateButton = (Button) findViewById(R.id.button_graph_detail_load_description_template);
        rockDescriptionEditText = (EditText) findViewById(R.id.edittext_graph_detail_rock_description);

        confirmGraphButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List<Rig> rigList = DataManager.getHole(holeId).getRigList();
                List<CalculatingRig> calculatingRigs = DataManager.getCalculatingList(holeId);

                if (!(rigList.size() == 0)) {
                    // Date
                    graphDataViewModel.getDateNodeList().clear();

                    String lastDate = Utility.formatCalendarDateStringWithoutYear(rigList.get(0).getDate());

                    CalculatingRig lastRig = null;

                    for (CalculatingRig rig : calculatingRigs) {
                        if (!Utility.formatCalendarDateStringWithoutYear(rig.getDate()).equals(lastDate)) {
                            graphDataViewModel.getDateNodeList().add(new RigGraphData.GraphNode(lastDate, lastRig.getAccumulatedMeterageLength()));
                        }

                        lastRig = rig;
                        lastDate = Utility.formatCalendarDateStringWithoutYear(rig.getDate());
                    }

                    graphDataViewModel.getDateNodeList().add(new RigGraphData.GraphNode(Utility.formatCalendarDateStringWithoutYear(calculatingRigs.get(calculatingRigs.size() - 1).getDate()), calculatingRigs.get(calculatingRigs.size() - 1).getAccumulatedMeterageLength()));

                    // Drill Type

                    double currentDrillDiameter = 0;

                    for (Rig rig : rigList) {
                        if (!(rig instanceof RegularRig)) {
                            continue;
                        }

                        if (((RegularRig) rig).getDrillBitDiameter() != currentDrillDiameter) {
                            currentDrillDiameter = ((RegularRig) rig).getDrillBitDiameter();
                            graphDataViewModel.getDrillDiameterList().add(new RigGraphData.GraphNode(String.valueOf(currentDrillDiameter), ((RegularRig) rig).getAccumulatedMeterageLength()));
                        }
                    }

                    // TR info
                    graphDataViewModel.getTrNodeList().clear();

                    double max108Length = -1;
                    double max127Length = -1;

                    for (int i = 0; i < rigList.size(); i++) {
                        if (rigList.get(i) instanceof TRRig) {
                            TRRig r = (TRRig) rigList.get(i);
                            for (int j = 0; j < r.getTrInfos().size(); j++) {
                                if (r.getTrInfos().get(j).getDiameter() == 108) {
                                    max108Length = Math.max(max108Length, r.getTrInfos().get(j).getLength());
                                } else {
                                    max127Length = Math.max(max127Length, r.getTrInfos().get(j).getLength());
                                }
                            }
                        }
                    }

                    if (max108Length != -1) {
                        graphDataViewModel.getTrNodeList().add(new RigGraphData.GraphNode("108" ,max108Length));
                    }

                    if (max127Length != -1) {
                        graphDataViewModel.getTrNodeList().add(new RigGraphData.GraphNode("127" ,max127Length));
                    }

                    // Water level
                    graphDataViewModel.setInitialWaterDepthNode(new RigGraphData.GraphNode(String.valueOf(DataManager.getHole(holeId).getInitialWaterDepth()), DataManager.getHole(holeId).getInitialWaterDepth()));
                    graphDataViewModel.setFinalWaterDepthNode(new RigGraphData.GraphNode(String.valueOf(DataManager.getHole(holeId).getFinalWaterDepth()), DataManager.getHole(holeId).getFinalWaterDepth()));
                    graphDataViewModel.setWaterDepthDateNode(new RigGraphData.GraphNode(Utility.formatCalendarDateString(DataManager.getHole(holeId).getFinalWaterDepthLoggedDate()), DataManager.getHole(holeId).getFinalWaterDepth()));

                    // Sampling

                    graphDataViewModel.getOriginalSamplingNodeList().clear();
                    for (Rig rig : rigList) {
                        if (rig instanceof OriginalSamplingRig) {
                            OriginalSamplingRig r = (OriginalSamplingRig) rig;
                            graphDataViewModel.getOriginalSamplingNodeList().add(new RigGraphData.GraphNode(Utility.formatDouble(r.getStartDepth()) + " ~ " + Utility.formatDouble(r.getEndDepth()), r.getEndDepth()));
                        }
                    }


                    rigList = DataManager.getHole(holeId).getRigIndexViewList();
                    graphDataViewModel.getWaterSamplingNodeList().clear();
                    for (Rig rig : rigList) {
                        if (rig instanceof OtherSamplingRig.OtherSamplingDetail && ((OtherSamplingRig.OtherSamplingDetail) rig).getSamplingType().equals("水样")) {
                            OtherSamplingRig.OtherSamplingDetail r = (OtherSamplingRig.OtherSamplingDetail) rig;
                            graphDataViewModel.getWaterSamplingNodeList().add(new RigGraphData.GraphNode(r.getStartDepth() + " ~ " + r.getEndDepth(), r.getEndDepth()));
                        }
                    }

                    graphDataViewModel.getDisturbanceSamplingNodeList().clear();
                    for (Rig rig : rigList) {
                        if (rig instanceof OtherSamplingRig.OtherSamplingDetail && ((OtherSamplingRig.OtherSamplingDetail) rig).getSamplingType().equals("扰动样")) {
                            OtherSamplingRig.OtherSamplingDetail r = (OtherSamplingRig.OtherSamplingDetail) rig;
                            graphDataViewModel.getDisturbanceSamplingNodeList().add(new RigGraphData.GraphNode(r.getStartDepth() + " ~ " + r.getEndDepth(), r.getEndDepth()));
                        }
                    }
                }


                DataManager.getHole(holeId).setRigGraphData(graphDataViewModel);

                IOManager.updateProject(DataManager.getProject());

                RigGraphActivity.this.setResult(RESULT_OK);
                RigGraphActivity.this.finish();
            }
        });

        addGraphButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rigNodeViewModel = getNextRigNode();
                if (rigNodeViewModel == null) {
                    Toast.makeText(RigGraphActivity.this, "已无钻孔可供切分。", Toast.LENGTH_SHORT).show();
                } else {
                    rigGprahDetailLinearLayout.setVisibility(View.VISIBLE);

                    addGraphButton.setEnabled(false);
                    deleteGraphButton.setEnabled(false);
                    clearGraphButton.setEnabled(false);

                    refreshDetailTable();
                }
            }
        });

        deleteGraphButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                graphDataViewModel.getRigNodeList().remove(graphDataViewModel.getRigNodeList().size() - 1);

                refreshInfo();
            }
        });

        clearGraphButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                graphDataViewModel.getRigNodeList().clear();

                refreshInfo();
            }
        });

        confirmRigDetailGraphButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rigGprahDetailLinearLayout.setVisibility(View.GONE);
                rigNodeViewModel.setDescription(graphRigNodeRockInfoViewModel.getRockDescription());
                graphDataViewModel.getRigNodeList().add(rigNodeViewModel);

                // Now drillType is used to save rock name
                rigNodeViewModel.setDrillType(graphRigNodeRockInfoViewModel.getRockName());

                addGraphButton.setEnabled(true);
                deleteGraphButton.setEnabled(true);
                clearGraphButton.setEnabled(true);

                refreshInfo();
            }
        });

        cancelGraphButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RigGraphActivity.this.setResult(RESULT_CANCELED);
                RigGraphActivity.this.finish();
            }
        });

        detailEndLengthEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!refreshLock) {
                    try {
                        rigNodeViewModel.setEndDepth(Double.parseDouble(s.toString()));
                        rigNodeViewModel.setHeight(Double.parseDouble(s.toString()));
                        rigNodeViewModel.setLayoutEndDepth(Double.parseDouble(s.toString()));
                        rigNodeViewModel.setRoundTripDepth(rigNodeViewModel.getEndDepth() - rigNodeViewModel.getStartDepth());
                        rigNodeViewModel.setRockPickLength(Double.parseDouble(s.toString()));
                        rigNodeViewModel.setRockPickPercentage(1);
                        detailEndLengthEditText.setTextColor(getResources().getColor(android.R.color.black));

                        refreshDetailTable();
                    } catch (Exception e) {
                        detailEndLengthEditText.setTextColor(getResources().getColor(android.R.color.holo_red_light));
                    }
                }
            }
        });

        rockTypeEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!refreshLock) {
                    rockColorEditText.setEnabled(true);
                    rockColorButton.setEnabled(true);
                    graphRigNodeRockInfoViewModel.setRockColor("灰色");
                    rockDensityEditText.setEnabled(true);
                    rockDensityButton.setEnabled(true);
                    graphRigNodeRockInfoViewModel.setRockDensity("坚硬");
                    rockSaturationEditText.setEnabled(true);
                    rockSaturationButton.setEnabled(true);
                    graphRigNodeRockInfoViewModel.setRockSaturation("稍湿");
                    rockWeatheringEditText.setEnabled(true);
                    rockWeatheringButton.setEnabled(true);
                    graphRigNodeRockInfoViewModel.setRockWeathering("全风化");
                    graphRigNodeRockInfoViewModel.setRockDescription("");

                    graphRigNodeRockInfoViewModel.setRockName(s.toString());
                }
            }
        });

        rockTypeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog rockTypeDialog;
                AlertDialog.Builder builder = new AlertDialog.Builder(RigGraphActivity.this);

                builder.setTitle("岩土名称");

                builder.setSingleChoiceItems(ROCK_TYPE_OPTIONS, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        graphRigNodeRockInfoViewModel.setRockName(ROCK_TYPE_OPTIONS[which].toString());

                        dialog.dismiss();

                        if (graphRigNodeRockInfoViewModel.getRockName().equals(ROCK_TYPE_OPTIONS[1])
                                || graphRigNodeRockInfoViewModel.getRockName().equals(ROCK_TYPE_OPTIONS[2])) {
                            rockColorEditText.setEnabled(true);
                            rockColorButton.setEnabled(true);
                            graphRigNodeRockInfoViewModel.setRockColor("灰色");
                            rockDensityEditText.setEnabled(true);
                            rockDensityButton.setEnabled(true);
                            graphRigNodeRockInfoViewModel.setRockDensity("坚硬");
                            rockSaturationEditText.setEnabled(true);
                            rockSaturationButton.setEnabled(true);
                            graphRigNodeRockInfoViewModel.setRockSaturation("稍湿");
                            rockWeatheringEditText.setEnabled(false);
                            rockWeatheringButton.setEnabled(false);
                            graphRigNodeRockInfoViewModel.setRockWeathering("");
                            graphRigNodeRockInfoViewModel.setRockDescription("");
                        } else if (graphRigNodeRockInfoViewModel.getRockName().equals(ROCK_TYPE_OPTIONS[3])
                                || graphRigNodeRockInfoViewModel.getRockName().equals(ROCK_TYPE_OPTIONS[6])) {
                            rockColorEditText.setEnabled(true);
                            rockColorButton.setEnabled(true);
                            graphRigNodeRockInfoViewModel.setRockColor("灰色");
                            rockDensityEditText.setEnabled(true);
                            rockDensityButton.setEnabled(true);
                            graphRigNodeRockInfoViewModel.setRockDensity("坚硬");
                            rockSaturationEditText.setEnabled(true);
                            rockSaturationButton.setEnabled(true);
                            graphRigNodeRockInfoViewModel.setRockSaturation("稍湿");
                            rockWeatheringEditText.setEnabled(false);
                            rockWeatheringButton.setEnabled(false);
                            graphRigNodeRockInfoViewModel.setRockWeathering("");
                            graphRigNodeRockInfoViewModel.setRockDescription("");
                        } else if (graphRigNodeRockInfoViewModel.getRockName().equals(ROCK_TYPE_OPTIONS[0])
                                || graphRigNodeRockInfoViewModel.getRockName().equals(ROCK_TYPE_OPTIONS[4])
                                || graphRigNodeRockInfoViewModel.getRockName().equals(ROCK_TYPE_OPTIONS[5])) {
                            rockColorEditText.setEnabled(true);
                            rockColorButton.setEnabled(true);
                            graphRigNodeRockInfoViewModel.setRockColor("灰色");
                            rockDensityEditText.setEnabled(true);
                            rockDensityButton.setEnabled(true);
                            graphRigNodeRockInfoViewModel.setRockDensity("坚硬");
                            rockSaturationEditText.setEnabled(false);
                            rockSaturationButton.setEnabled(false);
                            graphRigNodeRockInfoViewModel.setRockSaturation("");
                            rockWeatheringEditText.setEnabled(false);
                            rockWeatheringButton.setEnabled(false);
                            graphRigNodeRockInfoViewModel.setRockWeathering("");
                            graphRigNodeRockInfoViewModel.setRockDescription("");
                        } else if (graphRigNodeRockInfoViewModel.getRockName().endsWith("砂") || graphRigNodeRockInfoViewModel.getRockName().endsWith("石") || graphRigNodeRockInfoViewModel.getRockName().endsWith("砾")) {
                            rockColorEditText.setEnabled(true);
                            rockColorButton.setEnabled(true);
                            graphRigNodeRockInfoViewModel.setRockColor("灰色");
                            rockDensityEditText.setEnabled(true);
                            rockDensityButton.setEnabled(true);
                            graphRigNodeRockInfoViewModel.setRockDensity("坚硬");
                            rockSaturationEditText.setEnabled(true);
                            rockSaturationButton.setEnabled(true);
                            graphRigNodeRockInfoViewModel.setRockSaturation("稍湿");
                            rockWeatheringEditText.setEnabled(false);
                            rockWeatheringButton.setEnabled(false);
                            graphRigNodeRockInfoViewModel.setRockWeathering("");
                            graphRigNodeRockInfoViewModel.setRockDescription("");
                        } else if (graphRigNodeRockInfoViewModel.getRockName().endsWith("岩")) {
                            rockColorEditText.setEnabled(true);
                            rockColorButton.setEnabled(true);
                            graphRigNodeRockInfoViewModel.setRockColor("灰色");
                            rockDensityEditText.setEnabled(false);
                            rockDensityButton.setEnabled(false);
                            graphRigNodeRockInfoViewModel.setRockDensity("");
                            rockSaturationEditText.setEnabled(false);
                            rockSaturationButton.setEnabled(false);
                            graphRigNodeRockInfoViewModel.setRockSaturation("");
                            rockWeatheringEditText.setEnabled(true);
                            rockWeatheringButton.setEnabled(true);
                            graphRigNodeRockInfoViewModel.setRockWeathering("全风化");
                            graphRigNodeRockInfoViewModel.setRockDescription("");
                        }

                        refreshDetailTable();
                    }
                });

                rockTypeDialog = builder.create();
                rockTypeDialog.show();
            }
        });

        rockColorEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!refreshLock) {
                    graphRigNodeRockInfoViewModel.setRockColor(s.toString());
                }
            }
        });

        rockColorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog rockColorDialog;
                AlertDialog.Builder builder = new AlertDialog.Builder(RigGraphActivity.this);

                builder.setTitle("颜色");

                builder.setSingleChoiceItems(ROCK_COLOR_OPTIONS, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        graphRigNodeRockInfoViewModel.setRockColor(ROCK_COLOR_OPTIONS[which].toString());

                        dialog.dismiss();

                        refreshDetailTable();
                    }
                });

                rockColorDialog = builder.create();
                rockColorDialog.show();

            }
        });

        rockDensityEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                graphRigNodeRockInfoViewModel.setRockDensity(s.toString());
            }
        });

        rockDensityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog rockDensityDialog;
                AlertDialog.Builder builder = new AlertDialog.Builder(RigGraphActivity.this);

                builder.setTitle("稠度/密实度");

                builder.setSingleChoiceItems(ROCK_DENSITY_OPTIONS, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        graphRigNodeRockInfoViewModel.setRockDensity(ROCK_DENSITY_OPTIONS[which].toString());

                        dialog.dismiss();

                        refreshDetailTable();
                    }
                });

                rockDensityDialog = builder.create();
                rockDensityDialog.show();

            }
        });

        rockSaturationEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!refreshLock) {
                    graphRigNodeRockInfoViewModel.setRockSaturation(s.toString());
                }
            }
        });

        rockSaturationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog rockSaturationDialog;
                AlertDialog.Builder builder = new AlertDialog.Builder(RigGraphActivity.this);

                builder.setTitle("饱和度");

                builder.setSingleChoiceItems(ROCK_SATURATION_OPTIONS, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        graphRigNodeRockInfoViewModel.setRockSaturation(ROCK_SATURATION_OPTIONS[which].toString());

                        dialog.dismiss();

                        refreshDetailTable();
                    }
                });

                rockSaturationDialog = builder.create();
                rockSaturationDialog.show();

            }
        });

        rockWeatheringEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!refreshLock) {
                    graphRigNodeRockInfoViewModel.setRockWeathering(s.toString());
                }
            }
        });

        rockWeatheringButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog rockWeatheringDialog;
                AlertDialog.Builder builder = new AlertDialog.Builder(RigGraphActivity.this);

                builder.setTitle("岩石风化程度");

                builder.setSingleChoiceItems(ROCK_WEATHERING_OPTIONS, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        graphRigNodeRockInfoViewModel.setRockWeathering(ROCK_WEATHERING_OPTIONS[which].toString());

                        dialog.dismiss();

                        refreshDetailTable();
                    }
                });

                rockWeatheringDialog = builder.create();
                rockWeatheringDialog.show();

            }
        });

        detailRockCoreLengthEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!refreshLock) {
                    try {

                        rigNodeViewModel.setRockPickLength(Double.parseDouble(s.toString()));
                        rigNodeViewModel.setRockPickPercentage(rigNodeViewModel.getRockPickLength() / rigNodeViewModel.getRoundTripDepth());
                        detailRockCoreLengthEditText.setTextColor(getResources().getColor(android.R.color.black));

                        refreshDetailTable();
                    } catch (Exception e) {
                        detailRockCoreLengthEditText.setTextColor(getResources().getColor(android.R.color.holo_red_light));
                    }
                }
            }
        });

        generateRockDescriptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> stringList = new ArrayList<String>();
                stringList.add(Utility.formatDouble(rigNodeViewModel.getStartDepth()) + " m ~ " + Utility.formatDouble(rigNodeViewModel.getEndDepth()) + " m");
                stringList.add(graphRigNodeRockInfoViewModel.getRockName());

                if (!graphRigNodeRockInfoViewModel.getRockColor().equals("")) {
                    stringList.add(graphRigNodeRockInfoViewModel.getRockColor());
                }

                if (!graphRigNodeRockInfoViewModel.getRockDensity().equals("")) {
                    stringList.add(graphRigNodeRockInfoViewModel.getRockDensity());
                }

                if (!graphRigNodeRockInfoViewModel.getRockSaturation().equals("")) {
                    stringList.add(graphRigNodeRockInfoViewModel.getRockSaturation());
                }

                if (!graphRigNodeRockInfoViewModel.getRockWeathering().equals("")) {
                    stringList.add(graphRigNodeRockInfoViewModel.getRockWeathering());
                }

                graphRigNodeRockInfoViewModel.setRockDescription(TextUtils.join(", ", stringList));

                refreshDetailTable();
            }
        });

        loadRockDescriptionTemplateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Map<String, String> configMap = ConfigurationManager.getTemplateDictionary();

                AlertDialog typeDialog;

                final CharSequence[] items = new CharSequence[configMap.size()];

                Set set = configMap.keySet();

                int i = 0;

                for (Iterator iter = set.iterator(); iter.hasNext();)
                {
                    items[i] = (String) iter.next();
                    i++;
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(RigGraphActivity.this);

                builder.setTitle("描述模版");

                builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        graphRigNodeRockInfoViewModel.setRockDescription(graphRigNodeRockInfoViewModel.getRockDescription() + ", " + configMap.get(items[which]));

                        refreshDetailTable();

                        dialog.dismiss();
                    }
                });

                typeDialog = builder.create();
                typeDialog.show();

            }
        });

        rockDescriptionEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!refreshLock) {
                    graphRigNodeRockInfoViewModel.setRockDescription(s.toString());
                }
            }
        });

        holeId = getIntent().getStringExtra("holeId");
        holeViewModel = DataManager.getHole(holeId).deepCopy();
        graphDataViewModel = holeViewModel.getRigGraphData();

        deleteGraphButton.setEnabled(!graphDataViewModel.getRigNodeList().isEmpty());

        ArrayList<Rig> rigList = holeViewModel.getRigList();
        for (int i = 0; i < rigList.size(); i++) {
            TableRow tr = new TableRow(this);

            tr.setBackgroundColor(getResources().getColor(android.R.color.white));
            TableLayout.LayoutParams param = new TableLayout.LayoutParams();

            param.height = TableLayout.LayoutParams.WRAP_CONTENT;
            param.width = TableLayout.LayoutParams.WRAP_CONTENT;

            if (rigList.get(i) instanceof CalculatingRig) {
                CalculatingRig rig = (CalculatingRig) rigList.get(i);

                tr.addView(generateTableRowContent(Utility.formatDouble(rig.getAccumulatedMeterageLength() - rig.getRoundTripMeterageLength())));
                tr.addView(generateTableRowContent(Utility.formatDouble(rig.getAccumulatedMeterageLength())));
                tr.addView(generateTableRowContent(rig.getRockDescription()));
            }

            rockDescriptionTableLayout.addView(tr);
        }

        refreshInfo();

    }

    protected TextView generateTableRowContent(String s) {
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

    protected void refreshInfo() {
        if (refreshLock) {
            return;
        }

        refreshLock = true;

        deleteGraphButton.setEnabled(graphDataViewModel.getRigNodeList().size() != 0);
        while (splitRockDescriptionTableLayout.getChildCount() != 1) {
            splitRockDescriptionTableLayout.removeViewAt(1);
        }

        for (int i = 0; i < graphDataViewModel.getRigNodeList().size(); i++) {
            TableRow tr = new TableRow(this);

            tr.setBackgroundColor(getResources().getColor(android.R.color.white));
            TableLayout.LayoutParams param = new TableLayout.LayoutParams();

            param.height = TableLayout.LayoutParams.WRAP_CONTENT;
            param.width = TableLayout.LayoutParams.WRAP_CONTENT;

            tr.addView(generateTableRowContent(String.valueOf(graphDataViewModel.getRigNodeList().get(i).getRockLayoutIndex())));
            tr.addView(generateTableRowContent(Utility.formatDouble(graphDataViewModel.getRigNodeList().get(i).getLayoutEndDepth())));
            tr.addView(generateTableRowContent(Utility.formatDouble(graphDataViewModel.getRigNodeList().get(i).getRoundTripDepth())));

            splitRockDescriptionTableLayout.addView(tr);
        }


        if (DataManager.getHole(holeId).isApproved()) {
            addGraphButton.setEnabled(false);
            deleteGraphButton.setEnabled(false);
            clearGraphButton.setEnabled(false);
        }

        refreshLock = false;
    }

    protected void refreshDetailTable() {
        if (refreshLock) {
            return;
        }

        refreshLock = true;

        detailRockCoreIndexEditText.setText(String.valueOf(graphDataViewModel.getRigNodeList().size() + 1));

        if (getCurrentFocus() != detailEndLengthEditText) {
            detailEndLengthEditText.setText(Utility.formatDouble(rigNodeViewModel.getEndDepth()));
        }

        if (getCurrentFocus() != detailRoundTripLengthTextView) {
            detailRoundTripLengthTextView.setText(Utility.formatDouble(rigNodeViewModel.getRoundTripDepth()));
        }

        if (getCurrentFocus() != detailRockCoreLengthEditText) {
            detailRockCoreLengthEditText.setText(Utility.formatDouble(rigNodeViewModel.getRockPickLength()));
        }

        if (getCurrentFocus() != detailRockCorePickPercentageTextView) {
            detailRockCorePickPercentageTextView.setText(Utility.formatDouble(rigNodeViewModel.getRockPickPercentage() * 100) + "%");
        }

        if (getCurrentFocus() != rockTypeEditText) {
            rockTypeEditText.setText(graphRigNodeRockInfoViewModel.getRockName());
        }


        if (getCurrentFocus() != rockTypeEditText) {
            rockTypeEditText.setText(graphRigNodeRockInfoViewModel.getRockName());
        }


        if (getCurrentFocus() != rockTypeEditText) {
            rockTypeEditText.setText(graphRigNodeRockInfoViewModel.getRockName());
        }

        if (getCurrentFocus() != rockColorEditText) {
            rockColorEditText.setText(graphRigNodeRockInfoViewModel.getRockColor());
        }

        if (getCurrentFocus() != rockDensityEditText) {
            rockDensityEditText.setText(graphRigNodeRockInfoViewModel.getRockDensity());
        }

        if (getCurrentFocus() != rockSaturationEditText) {
            rockSaturationEditText.setText(graphRigNodeRockInfoViewModel.getRockSaturation());
        }

        if (getCurrentFocus() != rockWeatheringEditText) {
            rockWeatheringEditText.setText(graphRigNodeRockInfoViewModel.getRockWeathering());
        }

        if (getCurrentFocus() != rockDescriptionEditText) {
            rockDescriptionEditText.setText(graphRigNodeRockInfoViewModel.getRockDescription());
        }

        if (graphRigNodeRockInfoViewModel.getRockName().equals(ROCK_TYPE_OPTIONS[0])) {
            rockColorEditText.setEnabled(true);
            rockColorButton.setEnabled(true);
            rockDensityEditText.setEnabled(true);
            rockDensityButton.setEnabled(true);
            rockSaturationEditText.setEnabled(false);
            rockSaturationButton.setEnabled(false);
            rockWeatheringEditText.setEnabled(false);
            rockWeatheringButton.setEnabled(false);
        } else if (graphRigNodeRockInfoViewModel.getRockName().equals(ROCK_TYPE_OPTIONS[1]) || graphRigNodeRockInfoViewModel.getRockName().equals(ROCK_TYPE_OPTIONS[2]) || graphRigNodeRockInfoViewModel.getRockName().equals(ROCK_TYPE_OPTIONS[3])) {
            rockColorEditText.setEnabled(true);
            rockColorButton.setEnabled(true);
            rockDensityEditText.setEnabled(true);
            rockDensityButton.setEnabled(true);
            rockSaturationEditText.setEnabled(true);
            rockSaturationButton.setEnabled(true);
            rockWeatheringEditText.setEnabled(false);
            rockWeatheringButton.setEnabled(false);
        } else if (graphRigNodeRockInfoViewModel.getRockName().endsWith("砂") || graphRigNodeRockInfoViewModel.getRockName().endsWith("石") || graphRigNodeRockInfoViewModel.getRockName().endsWith("砾")) {
            rockColorEditText.setEnabled(true);
            rockColorButton.setEnabled(true);
            rockDensityEditText.setEnabled(true);
            rockDensityButton.setEnabled(true);
            rockSaturationEditText.setEnabled(true);
            rockSaturationButton.setEnabled(true);
            rockWeatheringEditText.setEnabled(false);
            rockWeatheringButton.setEnabled(false);
        } else if (graphRigNodeRockInfoViewModel.getRockName().endsWith("岩")) {
            rockColorEditText.setEnabled(true);
            rockColorButton.setEnabled(true);
            rockDensityEditText.setEnabled(false);
            rockDensityButton.setEnabled(false);
            rockSaturationEditText.setEnabled(false);
            rockSaturationButton.setEnabled(false);
            rockWeatheringEditText.setEnabled(true);
            rockWeatheringButton.setEnabled(true);
        }

        refreshLock = false;
    }

    protected RigGraphData.RigNode getNextRigNode() {
        Hole hole = DataManager.getHole(holeId);
        double currentLength = 0;

        int rigNodeSize = graphDataViewModel.getRigNodeList().size();

        if (rigNodeSize != 0) {
            currentLength = graphDataViewModel.getRigNodeList().get(rigNodeSize - 1).getEndDepth();
        }

        List<CalculatingRig> calculatingRigs = DataManager.getCalculatingList(holeId);

        CalculatingRig startRig = null;
        int startIndex = -1;

        for (int i = 0; i < calculatingRigs.size(); i++) {
            if (calculatingRigs.get(i).getAccumulatedMeterageLength() - calculatingRigs.get(i).getRoundTripMeterageLength() >= currentLength) {
                startIndex = i;
                break;
            }
        }

        if (startIndex == -1) {
            return null;
        }

        String rockName = calculatingRigs.get(startIndex).getRockType();
        String rockColor = calculatingRigs.get(startIndex).getRockColor();
        String rockDensity = calculatingRigs.get(startIndex).getRockDensity();
        String rockSaturation = calculatingRigs.get(startIndex).getRockSaturation();
        String rockWeathering = calculatingRigs.get(startIndex).getRockWeathering();

        int endIndex = -1;

        for (int i = startIndex + 1; i < calculatingRigs.size(); i++) {
            if (rockName.equals(calculatingRigs.get(i).getRockType())
                    && rockColor.equals(calculatingRigs.get(i).getRockColor())
                    && rockDensity.equals(calculatingRigs.get(i).getRockDensity())
                    && rockSaturation.equals(calculatingRigs.get(i).getRockSaturation())
                    && rockWeathering.equals(calculatingRigs.get(i).getRockWeathering())) {
                continue;
            } else {
                endIndex = i - 1;
                break;
            }
        }

        if (endIndex == -1) {
            endIndex = calculatingRigs.size() - 1;
        }

        double startLength = graphDataViewModel.getRigNodeList().size() == 0?0:graphDataViewModel.getRigNodeList().get(graphDataViewModel.getRigNodeList().size() - 1).getEndDepth();

        graphRigNodeRockInfoViewModel = new GraphRigNodeRockInfo(calculatingRigs.get(startIndex).getRockType(),
                calculatingRigs.get(startIndex).getRockColor(),
                calculatingRigs.get(startIndex).getRockDensity(),
                calculatingRigs.get(startIndex).getRockSaturation(),
                calculatingRigs.get(startIndex).getRockWeathering(),
                Utility.formatDouble(calculatingRigs.get(startIndex).getAccumulatedMeterageLength() - calculatingRigs.get(startIndex).getRoundTripMeterageLength()) + " m ~ " +
                            Utility.formatDouble(calculatingRigs.get(endIndex).getAccumulatedMeterageLength()) + " m",
                calculatingRigs.get(endIndex).getAccumulatedMeterageLength() - startLength,
                1
        );

        RigGraphData.RigNode node = new RigGraphData.RigNode(
                calculatingRigs.get(endIndex).getAccumulatedMeterageLength(),
                calculatingRigs.get(startIndex).getRockType(),
                calculatingRigs.get(startIndex) instanceof RegularRig? ((RegularRig) calculatingRigs.get(startIndex)).getDrillBitDiameter(): -1,
                startLength,
                calculatingRigs.get(endIndex).getAccumulatedMeterageLength(),
                calculatingRigs.get(endIndex).getAccumulatedMeterageLength() - startLength,
                calculatingRigs.get(endIndex).getAccumulatedMeterageLength(),
                calculatingRigs.get(endIndex).getAccumulatedMeterageLength() - startLength,
                calculatingRigs.get(endIndex).getAccumulatedMeterageLength() - startLength,
                1,
                graphDataViewModel.getRigNodeList().size() + 1,
                    "");

        return node;
    }
}

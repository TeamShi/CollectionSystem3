package com.teamshi.collectionsystem3;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.teamshi.collectionsystem3.datastructure.Hole;
import com.teamshi.collectionsystem3.datastructure.RegularRig;
import com.teamshi.collectionsystem3.datastructure.Rig;
import com.teamshi.collectionsystem3.datastructure.RigGraphData;

import java.util.ArrayList;

public class RigGraphActivity extends Activity {
    private static final String TAG = "CollectionSystem3";

    private Hole holeViewModel;
    private RigGraphData graphData;

    private TableLayout rockDescriptionTableLayout;
    private TableLayout splitRockDescriptionTableLayout;
    private LinearLayout rigGprahDetailLinearLayout;
    private LinearLayout informationLinearLayout;

    private Button confirmGraphButton;
    private Button cancelGraphButton;
    private Button addGraphButton;
    private Button deleteGraphButton;
    private Button clearGraphButton;

    public Button confirmRigDetailGraphButton;

    public RigGraphActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rig_graph);

        rockDescriptionTableLayout = (TableLayout) findViewById(R.id.table_layout_rock_description_list);
        splitRockDescriptionTableLayout = (TableLayout) findViewById(R.id.table_layout_split_rock_description);
        rigGprahDetailLinearLayout = (LinearLayout) findViewById(R.id.linear_layout_rig_graph_detail);
        informationLinearLayout = (LinearLayout) findViewById(R.id.linear_layout_information);

        confirmGraphButton = (Button) findViewById(R.id.button_confirm_hole_graph);
        deleteGraphButton = (Button) findViewById(R.id.button_cancel_hole_graph);
        addGraphButton = (Button) findViewById(R.id.button_add_hole_graph);
        deleteGraphButton = (Button) findViewById(R.id.button_delete_hole_graph);
        clearGraphButton = (Button) findViewById(R.id.button_clear_hole_graph);

        confirmRigDetailGraphButton = (Button) findViewById(R.id.button_confirm_rig_detail_graph);

        confirmGraphButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        addGraphButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rigGprahDetailLinearLayout.setVisibility(View.VISIBLE);

                addGraphButton.setEnabled(false);
                deleteGraphButton.setEnabled(false);
                clearGraphButton.setEnabled(false);



                // TODO: auto fullfil

            }
        });

        deleteGraphButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                graphData.getRigNodeList().remove(graphData.getRigNodeList().size() - 1);

                refreshInfo();
            }
        });

        clearGraphButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                graphData.getRigNodeList().clear();

                refreshInfo();
            }
        });

        confirmRigDetailGraphButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rigGprahDetailLinearLayout.setVisibility(View.GONE);
                // TODO: fix when finish UI test.
                graphData.getRigNodeList().add(new RigGraphData.RigNode(0, "", 0, 0, 0, 0, 0, 0, 0, ""));

                addGraphButton.setEnabled(true);
                deleteGraphButton.setEnabled(true);
                clearGraphButton.setEnabled(true);


                refreshInfo();
            }
        });

        String holeId = getIntent().getStringExtra("holeId");
        holeViewModel = DataManager.getHole(holeId).deepCopy();
        graphData = holeViewModel.getRigGraphData();

        ArrayList<Rig> rigList = holeViewModel.getRigList();
        for (int i = 0; i < rigList.size(); i++) {
            TableRow tr = new TableRow(this);

            tr.setBackgroundColor(getResources().getColor(android.R.color.white));
            TableLayout.LayoutParams param = new TableLayout.LayoutParams();

            param.height = TableLayout.LayoutParams.WRAP_CONTENT;
            param.width = TableLayout.LayoutParams.WRAP_CONTENT;

            if (rigList.get(i) instanceof RegularRig) {
                RegularRig rig = (RegularRig) rigList.get(i);

                tr.addView(generateTableRowContent(rig.getDrillBitType()));
                tr.addView(generateTableRowContent(Utility.formatDouble(rig.getDrillBitDiameter())));
                tr.addView(generateTableRowContent(Utility.formatDouble(rig.getAccumulatedMeterageLength() - rig.getRoundTripMeterageLength())));
                tr.addView(generateTableRowContent(Utility.formatDouble(rig.getAccumulatedMeterageLength())));
                tr.addView(generateTableRowContent(rig.getRockDescription()));
            }

            rockDescriptionTableLayout.addView(tr);
        }

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
        deleteGraphButton.setEnabled(graphData.getRigNodeList().size() != 0);
        while (splitRockDescriptionTableLayout.getChildCount() != 1) {
            splitRockDescriptionTableLayout.removeViewAt(1);
        }

        for (int i = 0; i < graphData.getRigNodeList().size(); i++) {
            TableRow tr = new TableRow(this);

            tr.setBackgroundColor(getResources().getColor(android.R.color.white));
            TableLayout.LayoutParams param = new TableLayout.LayoutParams();

            param.height = TableLayout.LayoutParams.WRAP_CONTENT;
            param.width = TableLayout.LayoutParams.WRAP_CONTENT;

            tr.addView(generateTableRowContent(String.valueOf(graphData.getRigNodeList().get(i).getRockLayoutIndex())));
            tr.addView(generateTableRowContent(Utility.formatDouble(graphData.getRigNodeList().get(i).getLayoutEndDepth())));
            tr.addView(generateTableRowContent(Utility.formatDouble(graphData.getRigNodeList().get(i).getHeight())));

            splitRockDescriptionTableLayout.addView(tr);
        }
    }

}

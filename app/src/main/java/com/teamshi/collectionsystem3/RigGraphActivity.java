package com.teamshi.collectionsystem3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.teamshi.collectionsystem3.datastructure.Hole;
import com.teamshi.collectionsystem3.datastructure.RegularRig;
import com.teamshi.collectionsystem3.datastructure.Rig;

import java.util.ArrayList;

public class RigGraphActivity extends AppCompatActivity {
    private static final String TAG = "CollectionSystem3";

    private Hole holeViewModel;

    private TableLayout rockDescriptionTableLayout;
    private TableLayout splitRockDescriptionTableLayout;

    private Button confirmGraphButton;
    private Button cancelGraphButton;
    private Button addGraphButton;
    private Button deleteGraphButton;
    private Button clearGraphButton;

    public RigGraphActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rig_graph);

        rockDescriptionTableLayout = (TableLayout) findViewById(R.id.table_layout_rock_description_list);
        splitRockDescriptionTableLayout = (TableLayout) findViewById(R.id.table_layout_split_rock_description);

        confirmGraphButton = (Button) findViewById(R.id.button_confirm_hole_graph);
        deleteGraphButton = (Button) findViewById(R.id.button_cancel_hole_graph);
        addGraphButton = (Button) findViewById(R.id.button_add_hole_graph);
        deleteGraphButton = (Button) findViewById(R.id.button_delete_hole_graph);
        clearGraphButton = (Button) findViewById(R.id.button_clear_hole_graph);

        confirmGraphButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        deleteGraphButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        addGraphButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        deleteGraphButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        clearGraphButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        ArrayList<Rig> rigList = DataManager.getHole(getIntent().getStringExtra("holeId")).getRigList();
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
        TextView tv =new TextView(this);

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

}

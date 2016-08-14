package com.teamshi.collectionsystem3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.teamshi.collectionsystem3.datastructure.DSTRig;

public class DSTRigActivity extends AppCompatActivity {

    private static final String TAG = "CollectionSystem3";

    private boolean refreshLock = false;

    private DSTRig rigViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "Start DSTRigActivity.");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dstrig);
    }
}

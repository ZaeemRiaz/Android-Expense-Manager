package com.base.software_for_mobile_devices_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

public class TransactionListActivity extends AppCompatActivity {

    private static final String TAG = "TransactionListActivity";

    private TransactionListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: init");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_list);
    }

    private void initRecyclerView() {
        Log.d(TAG, "initRecyclerView: init");
        // TODO: fix this line RecyclerView recyclerView = findViewById(R.id.recycler_view);
        adapter = new TransactionListAdapter();
        // recyclerView.setAdapter(adapter);
        // recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

}

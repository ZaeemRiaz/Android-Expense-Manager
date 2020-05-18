package com.base.software_for_mobile_devices_project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class TransactionListActivity extends AppCompatActivity {

    private static final String TAG = "=== TransactionListActivity ===";
    List<Transaction> transactions = new ArrayList<>();
    private TransactionListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: init");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_list);
        try {
            initTransactions();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        initRecyclerView();
    }

    private void initTransactions() throws ParseException {

        transactions.add(new Transaction("2020-04-01 11:12:13", 100, "first"));
        transactions.add(new Transaction("2021-04-02 11:12:14", -200, "second"));
        transactions.add(new Transaction("2022-04-03 11:12:15", 300, "third"));
        transactions.add(new Transaction("2023-04-04 11:12:16", -400, "fourth"));
        transactions.add(new Transaction("2024-04-05 11:12:17", 500, "fifth"));
        transactions.add(new Transaction("2025-04-06 11:12:18", -600, "sixth"));
        transactions.add(new Transaction("2026-04-07 11:12:19", 700, "seventh"));
        transactions.add(new Transaction("2027-04-08 11:12:20", 800, "eighth"));

    }

    private void initRecyclerView() {
        Log.d(TAG, "initRecyclerView: init");
        RecyclerView recyclerView = findViewById(R.id.recyclerview_transaction_list);
        adapter = new TransactionListAdapter(transactions);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void onAddTransaction(View view) {
        Log.i(TAG, "onAddTransaction: init");
        Intent intent = new Intent(getApplicationContext(), TransactionAddEditActivity.class);
        startActivity(intent);
    }
}

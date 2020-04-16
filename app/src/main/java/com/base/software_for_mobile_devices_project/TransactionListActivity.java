package com.base.software_for_mobile_devices_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class TransactionListActivity extends AppCompatActivity {

    private static final String TAG = "=== TransactionListActivity ===";

    private TransactionListAdapter adapter;

    List<Transaction> transactions = new ArrayList<>();

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

        transactions.add(new Transaction("01-04-2020 11:12:13", 100, "first"));
        transactions.add(new Transaction("02-04-2021 11:12:14", -200, "second"));
        transactions.add(new Transaction("03-04-2022 11:12:15", 300, "third"));
        transactions.add(new Transaction("04-04-2023 11:12:16", -400, "fourth"));
        transactions.add(new Transaction("05-04-2024 11:12:17", 500, "fifth"));
        transactions.add(new Transaction("06-04-2025 11:12:18", -600, "sixth"));
        transactions.add(new Transaction("07-04-2026 11:12:19", 700, "seventh"));
        transactions.add(new Transaction("08-04-2027 11:12:20", 800, "eighth"));

    }

    private void initRecyclerView() {
        Log.d(TAG, "initRecyclerView: init");
        RecyclerView recyclerView = findViewById(R.id.recyclerview_transaction_list);
        adapter = new TransactionListAdapter(transactions);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void test(View view) {
        Log.i(TAG, "test: init");
    }
}

package com.base.software_for_mobile_devices_project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class TransactionListActivity extends AppCompatActivity {

    private static final String TAG = "=== TransactionListActivity ===";
    private List<Transaction> transactions = new ArrayList<>();
    private TransactionListAdapter adapter;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: init");
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_transaction_list);

//        initTransactions();
        initRecyclerView();

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

    }

    private void initTransactions() {

        try {
            transactions.add(new Transaction("2020-04-01 11:12:13", 100, "first"));
            transactions.add(new Transaction("2021-04-02 11:12:14", -200, "second"));
            transactions.add(new Transaction("2022-04-03 11:12:15", 300, "third"));
            transactions.add(new Transaction("2023-04-04 11:12:16", -400, "fourth"));
            transactions.add(new Transaction("2024-04-05 11:12:17", 500, "fifth"));
            transactions.add(new Transaction("2025-04-06 11:12:18", -600, "sixth"));
            transactions.add(new Transaction("2026-04-07 11:12:19", 700, "seventh"));
            transactions.add(new Transaction("2027-04-08 11:12:20", 800, "eighth"));
        } catch (ParseException e) {
            Log.w(TAG, "initTransactions: ", e);
        }

        PersistableCollection<Transaction> collection = new PersistableCollection<Transaction>(transactions, Transaction.class);
        collection.save(getApplicationContext());
    }

    private void readTransactions() {
        transactions.clear();
        PersistableCollection<Transaction> collection = new PersistableCollection<Transaction>(transactions, Transaction.class);
        collection.load(getApplicationContext());
    }

    private void initRecyclerView() {
        Log.d(TAG, "initRecyclerView: init");
        RecyclerView recyclerView = findViewById(R.id.recyclerview_transaction_list);
        adapter = new TransactionListAdapter(transactions);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void onAddTransaction(View view) {
        Log.d(TAG, "onAddTransaction: init");
        Intent intent = new Intent(getApplicationContext(), TransactionAddEditActivity.class);
        startActivity(intent);
    }

//    @Override
//    protected void onPause() {
//        Log.d(TAG, "onPause: init");
//        super.onPause();
//        PersistableCollection<Transaction> collection = new PersistableCollection<Transaction>(transactions, Transaction.class);
//        collection.save(getApplicationContext());
//    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume: init");
        super.onResume();
        readTransactions();
        adapter.notifyDataSetChanged();
    }
}

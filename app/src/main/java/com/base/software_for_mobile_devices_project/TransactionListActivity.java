package com.base.software_for_mobile_devices_project;

import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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
    private Messenger messenger;
    private boolean bound = false;
    private List<Transaction> transactions = new ArrayList<>();
    private TransactionListAdapter adapter;
    private AdView mAdView;
    private ServiceConnection connection = new ServiceConnection() {

        public void onServiceConnected(ComponentName className, IBinder binder) {
            messenger = new Messenger(binder);
            bound = true;
        }

        public void onServiceDisconnected(ComponentName className) {
            bound = false;
        }
    };

    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, DataSyncService.class);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }

    protected void onStop() {
        super.onStop();
        if (bound) {
            unbindService(connection);
        }
    }

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

        PersistableCollection<Transaction> collection = new PersistableCollection<>(transactions, Transaction.class);
        collection.save(getApplicationContext());
    }

    private void readTransactions() {
        transactions.clear();
        PersistableCollection<Transaction> collection = new PersistableCollection<>(transactions, Transaction.class);
        collection.load(getApplicationContext());
    }

    private void initRecyclerView() {
        Log.d(TAG, "initRecyclerView: init");
        RecyclerView recyclerView = findViewById(R.id.recyclerview_transaction_list);
        adapter = new TransactionListAdapter(transactions);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

//    @Override
//    protected void onPause() {
//        Log.d(TAG, "onPause: init");
//        super.onPause();
//        PersistableCollection<Transaction> collection = new PersistableCollection<Transaction>(transactions, Transaction.class);
//        collection.save(getApplicationContext());
//    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.transaction_list_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View view;
        AlertDialog dialog;
        switch (item.getItemId()) {
            case R.id.add_transaction:
                Intent intent = new Intent(getApplicationContext(), TransactionAddEditActivity.class);
                startActivity(intent);
                return true;
            case R.id.import_transactions:
                view = inflater.inflate(R.layout.import_transactions_dialog, null);
                builder.setView(view);
                builder.setPositiveButton("Import", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        EditText editText = view.findViewById(R.id.url_import_dialog);
                        String url = editText.getText().toString();

                        Bundle bundle = new Bundle();
                        bundle.putSerializable("url", url);

                        Message message = Message.obtain(null, 1);
                        message.setData(bundle);

                        try {
                            messenger.send(message);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                });
                dialog = builder.create();
                dialog.show();
                return true;
            case R.id.export_transactions:
                view = inflater.inflate(R.layout.export_transactions_dialog, null);
                builder.setView(view);
                builder.setPositiveButton("Export", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        EditText editText = view.findViewById(R.id.url_export_dialog);
                        String url = editText.getText().toString();

                        Bundle bundle = new Bundle();
                        bundle.putSerializable("url", url);

                        Message message = Message.obtain(null, 2);
                        message.setData(bundle);

                        try {
                            messenger.send(message);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                });
                dialog = builder.create();
                dialog.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume: init");
        super.onResume();
        readTransactions();
        adapter.notifyDataSetChanged();
    }
}

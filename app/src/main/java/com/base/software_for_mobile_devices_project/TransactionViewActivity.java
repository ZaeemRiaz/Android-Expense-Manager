package com.base.software_for_mobile_devices_project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class TransactionViewActivity extends AppCompatActivity {

    private static final String TAG = "=== TransactionViewActivity ===";
    private Transaction transaction;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: init");
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        transaction = (Transaction) intent.getSerializableExtra("transactionViewIntent");

        setContentView(R.layout.activity_transaction_view);

        // Set Details
        TextView typeView = findViewById(R.id.type_text_view_transaction_view);
        TextView dateView = findViewById(R.id.date_text_view_transaction_view);
        TextView timeView = findViewById(R.id.time_text_view_transaction_view);
        TextView amountView = findViewById(R.id.amount_text_view_transaction_view);
        TextView descriptionView = findViewById(R.id.description_text_view_transaction_view);

        double amount = transaction.getAmount();
        String type;
        if (amount < 0)
            type = "Expense";
        else
            type = "Income";

        typeView.setText(type);
        dateView.setText(transaction.getDate("dd-MM-yyyy"));
        timeView.setText(transaction.getDate("hh-mm-ss"));
        amountView.setText(String.valueOf(amount));
        descriptionView.setText(transaction.getDescription());

        // Banner Ad
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mAdView = findViewById(R.id.adView_transaction_view);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    public void editTransactionOnClick(View view) {
        Intent intent = new Intent(this, TransactionAddEditActivity.class);
        intent.putExtra("transactionEditIntent", transaction);
        intent.putExtra("editIntent", true);
        startActivity(intent);
    }

    public void deleteTransactionOnClick(View view) {
        //delete corresponding records in DB
        try {
            getContentResolver().delete(TransactionProvider.CONTENT_URI,
                    TransactionDbHelper.id + " = ?",
                    new String[]{String.valueOf(transaction.getId())});
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        finish();
    }
}

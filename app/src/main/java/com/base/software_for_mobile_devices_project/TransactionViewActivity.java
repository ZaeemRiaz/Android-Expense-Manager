package com.base.software_for_mobile_devices_project;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;

import androidx.annotation.Nullable;
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
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);


        Intent intent = getIntent();
        transaction = (Transaction) intent.getSerializableExtra("transactionIntent");

        setContentView(R.layout.activity_transaction_view);

//        MobileAds.initialize(this, new OnInitializationCompleteListener() {
//            @Override
//            public void onInitializationComplete(InitializationStatus initializationStatus) {
//            }
//        });
//        mAdView = findViewById(R.id.adView_transaction_view);
//        AdRequest adRequest = new AdRequest.Builder().build();
//        mAdView.loadAd(adRequest);
    }

    public void editTransactionOnClick(View view) {
    }

    public void deleteTransactionOnClick(View view) {
    }
}

package com.base.software_for_mobile_devices_project;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class TransactionAddEditActivity extends AppCompatActivity {

    private static final String TAG = "=== TransactionAddEditActivity ===";
    private TabLayout tabLayout;
    private ViewPager2 viewPager2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: init");
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_transaction_add_edit);

        viewPager2 = (ViewPager2) findViewById(R.id.pager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);

        TransactionPagerAdapter transactionPagerAdapter = new TransactionPagerAdapter(
                getSupportFragmentManager(), getLifecycle(), tabLayout.getTabCount());

        viewPager2.setAdapter(transactionPagerAdapter);

        new TabLayoutMediator(tabLayout, viewPager2, )
    }


}

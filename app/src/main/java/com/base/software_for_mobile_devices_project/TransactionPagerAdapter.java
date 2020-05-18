package com.base.software_for_mobile_devices_project;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class TransactionPagerAdapter extends FragmentStateAdapter {
    private int noOfTabs;

    public TransactionPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, int noOfTabs) {
        super(fragmentManager, lifecycle);
        this.noOfTabs = noOfTabs;
    }

    public TransactionPagerAdapter(@NonNull Fragment fragment, int noOfTabs) {
        super(fragment);
        this.noOfTabs = noOfTabs;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new IncomeFragment();
            case 1:
                return new ExpenseFragment();
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return noOfTabs;
    }
}

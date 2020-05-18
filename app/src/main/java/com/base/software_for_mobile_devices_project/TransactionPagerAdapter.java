package com.base.software_for_mobile_devices_project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class TransactionPagerAdapter extends FragmentStatePagerAdapter {
    private int noOfTabs;

    public TransactionPagerAdapter(@NonNull FragmentManager fragmentManager, int noOfTabs) {
        super(fragmentManager);
        this.noOfTabs = noOfTabs;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Income";
            case 1:
                return "Expense";
            default:
                return super.getPageTitle(position);
        }
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
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
    public int getCount() {
        return noOfTabs;
    }


}

package com.base.software_for_mobile_devices_project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class TransactionPagerAdapter extends FragmentStatePagerAdapter {
    private int noOfTabs;
    private Transaction transaction;

    TransactionPagerAdapter(@NonNull FragmentManager fragmentManager, int noOfTabs, Transaction transaction) {
        super(fragmentManager);
        this.noOfTabs = noOfTabs;
        this.transaction = transaction;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Expense";
            case 1:
                return "Income";
            default:
                return super.getPageTitle(position);
        }
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new ExpenseFragment(transaction);
            case 1:
                return new IncomeFragment(transaction);
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return noOfTabs;
    }


}

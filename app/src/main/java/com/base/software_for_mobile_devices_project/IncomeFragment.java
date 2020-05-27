package com.base.software_for_mobile_devices_project;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class IncomeFragment extends Fragment {
    private static final String TAG = "=== IncomeFragment ===";
    private Transaction transaction;
    private View view;

    public IncomeFragment(Transaction transaction) {
        this.transaction = transaction;}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_income, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }
    @Override
    public void onResume() {
        super.onResume();
        TextView expenseDateTextView = view.findViewById(R.id.date_text_view_income);
        TextView expenseTimeTextView = view.findViewById(R.id.time_text_view_income);
        EditText expenseAmountEditText = view.findViewById(R.id.amount_edit_text_income);
        EditText expenseDescriptionEditText = view.findViewById(R.id.description_edit_text_income);

        expenseDateTextView.setText(transaction.getDate("dd-MM-yyyy"));
        expenseTimeTextView.setText(transaction.getDate("hh:mm"));
        expenseAmountEditText.setText(String.valueOf(transaction.getAmount()));
        expenseDescriptionEditText.setText(transaction.getDescription());
    }

    public void updateTransaction(Transaction transaction) {
        this.transaction = transaction;
        Log.d(TAG, "updateTransaction: date: " + transaction.getDate());
    }
}

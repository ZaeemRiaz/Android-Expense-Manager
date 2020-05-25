package com.base.software_for_mobile_devices_project;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.Calendar;
import java.util.Date;

public class TransactionAddEditActivity extends AppCompatActivity {

    private static final String TAG = "=== TransactionAddEditActivity ===";
    private Transaction currentTransaction;
    private final TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            String strTime = hourOfDay + ":" + minute + ":00";
            String date = currentTransaction.getDate("dd-MM-yyyy");
            date = date + " " + strTime;
            currentTransaction.setDate(date, "dd-MM-yyyy hh:mm:ss");
            Log.i(TAG, "onTimeSet: date: " + date);
            updateView();
        }
    };
    private final DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            String strDate = dayOfMonth + "-" + (month + 1) + "-" + year;
            String date = currentTransaction.getDate("hh:mm:ss");
            date = strDate + " " + date;
            currentTransaction.setDate(date, "dd-MM-yyyy hh:mm:ss");
            Log.i(TAG, "onDateSet: date: " + date);
            updateView();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: init");
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_transaction_add_edit);

        ViewPager viewPager = findViewById(R.id.pager);
        TabLayout tabLayout = findViewById(R.id.tabs);

        TransactionPagerAdapter transactionPagerAdapter = new TransactionPagerAdapter(
                getSupportFragmentManager(), tabLayout.getTabCount());

        viewPager.setAdapter(transactionPagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setupWithViewPager(viewPager);

        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        currentTransaction = new Transaction(date, 0, "");

//        EditText amountExpense = findViewById(R.id.amount_edit_text_expense);
//        EditText amountIncome = findViewById(R.id.amount_edit_text_income);
//        EditText descriptionExpense = findViewById(R.id.description_edit_text_expense);
//        EditText descriptionIncome = findViewById(R.id.description_edit_text_income);
//
//        amountExpense.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                //do nothing
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                currentTransaction.setAmount(Double.parseDouble(s.toString()));
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                updateView();
//            }
//        });
//        amountIncome.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                //do nothing
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                currentTransaction.setAmount(Double.parseDouble(s.toString()));
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                updateView();
//            }
//        });
//        descriptionExpense.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                //do nothing
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                currentTransaction.setDescription(s.toString());
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                updateView();
//            }
//        });
//        descriptionIncome.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                //do nothing
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                currentTransaction.setDescription(s.toString());
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                updateView();
//            }
//        });
//
//        updateView();

    }

    public void openDatePickerDialog(View view) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(this, dateSetListener, year, month, day);
        dialog.show();
    }

    public void openTimePickerDialog(View view) {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog dialog = new TimePickerDialog(this, timeSetListener, hour, minute, false);
        dialog.show();
    }

    private void updateView() {
        //TODO: removes text from EditText, priority of view or stored
        TextView expenseDateTextView = findViewById(R.id.date_text_view_expense);
        TextView expenseTimeTextView = findViewById(R.id.time_text_view_expense);
//        EditText expenseAmountEditText = findViewById(R.id.amount_edit_text_expense);
//        EditText expenseDescriptionEditText = findViewById(R.id.description_edit_text_expense);
        TextView incomeDateTextView = findViewById(R.id.date_text_view_income);
        TextView incomeTimeTextView = findViewById(R.id.time_text_view_income);
//        EditText incomeAmountEditText = findViewById(R.id.amount_edit_text_income);
//        EditText incomeDescriptionEditText = findViewById(R.id.description_edit_text_income);

        expenseDateTextView.setText(currentTransaction.getDate("dd-MM-yyyy"));
        expenseTimeTextView.setText(currentTransaction.getDate("hh:mm"));
//        expenseAmountEditText.setText(String.valueOf(currentTransaction.getAmount()));
//        expenseDescriptionEditText.setText(currentTransaction.getDescription());
        incomeDateTextView.setText(currentTransaction.getDate("dd-MM-yyyy"));
        incomeTimeTextView.setText(currentTransaction.getDate("hh:mm"));
//        incomeAmountEditText.setText(String.valueOf(currentTransaction.getAmount()));
//        incomeDescriptionEditText.setText(currentTransaction.getDescription());
    }

    public void saveExpenseTransactionButton(View view) {
        Log.d(TAG, "saveExpenseTransactionButton: init");

        EditText expenseAmountEditText = findViewById(R.id.amount_edit_text_expense);
        EditText expenseDescriptionEditText = findViewById(R.id.description_edit_text_expense);

        String text = expenseAmountEditText.getText().toString();
        if (!text.isEmpty()) {
            try {
                currentTransaction.setAmount(Double.parseDouble(text));
            } catch (Exception e) {
                Log.w(TAG, "saveExpenseTransactionButton: ", e);
            }
        }
        currentTransaction.setDescription(expenseDescriptionEditText.getText().toString());

        getContentResolver().insert(
                TransactionProvider.CONTENT_URI, currentTransaction.getContentValues());
    }

    public void saveIncomeTransactionButton(View view) {
        Log.d(TAG, "saveIncomeTransactionButton: init");

        EditText incomeAmountEditText = findViewById(R.id.amount_edit_text_income);
        EditText incomeDescriptionEditText = findViewById(R.id.description_edit_text_income);

        String text = incomeAmountEditText.getText().toString();
        if (!text.isEmpty()) {
            try {
                currentTransaction.setAmount(Double.parseDouble(text));
            } catch (Exception e) {
                Log.w(TAG, "saveExpenseTransactionButton: ", e);
            }
        }
        currentTransaction.setDescription(incomeDescriptionEditText.getText().toString());

        getContentResolver().insert(
                TransactionProvider.CONTENT_URI, currentTransaction.getContentValues());
    }
}

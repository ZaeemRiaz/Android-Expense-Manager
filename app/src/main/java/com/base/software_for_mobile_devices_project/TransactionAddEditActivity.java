package com.base.software_for_mobile_devices_project;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.tabs.TabLayout;

import java.util.Calendar;
import java.util.Date;

public class TransactionAddEditActivity extends AppCompatActivity {

    private static final String TAG = "=== TransactionAddEditActivity ===";
    private Transaction transaction;
    private final TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            String strTime = hourOfDay + ":" + minute + ":00";
            String date = transaction.getDate("dd-MM-yyyy");
            date = date + " " + strTime;
            transaction.setDate(date, "dd-MM-yyyy hh:mm:ss");
            Log.i(TAG, "onTimeSet: date: " + date);
            updateView();
        }
    };
    private final DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            String strDate = dayOfMonth + "-" + (month + 1) + "-" + year;
            String date = transaction.getDate("hh:mm:ss");
            date = strDate + " " + date;
            transaction.setDate(date, "dd-MM-yyyy hh:mm:ss");
            Log.i(TAG, "onDateSet: date: " + date);
            updateView();
        }
    };
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: init");
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();

        // Add or Edit
        Boolean edit = (Boolean) intent.getSerializableExtra("editIntent");
        Log.i(TAG, "onCreate: edit: " + edit);
        if (edit) {
            transaction = (Transaction) intent.getSerializableExtra("transactionEditIntent");
        } else {
            // Get current Date
            Calendar calendar = Calendar.getInstance();
            Date date = calendar.getTime();

            transaction = new Transaction(date, 0, "");
        }
        Log.i(TAG, "onCreate: transaction: " + transaction.getDate());

        setContentView(R.layout.activity_transaction_add_edit);

        // setup fragments
        final ViewPager viewPager = findViewById(R.id.pager);
        final TabLayout tabLayout = findViewById(R.id.tabs);

        TransactionPagerAdapter transactionPagerAdapter = new TransactionPagerAdapter(
                getSupportFragmentManager(), tabLayout.getTabCount(), transaction);

        viewPager.setAdapter(transactionPagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setupWithViewPager(viewPager);

        // If editing income
        if (edit && transaction.getAmount() > 0)
            tabLayout.getTabAt(1).select();
        else if (edit && transaction.getAmount() < 0)
            transaction.setAmount(transaction.getAmount() * -1);

        // Banner Ad
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mAdView = findViewById(R.id.adView_transaction_add_edit);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        // Tab view listener
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tabLayout.getSelectedTabPosition() == 0 ){
                    Log.d(TAG, "onTabSelected: Tab 0");

                    TransactionPagerAdapter adapter = (TransactionPagerAdapter)viewPager.getAdapter();
                    ExpenseFragment fragment = (ExpenseFragment)adapter.getItem(0);
                    fragment.updateTransaction(transaction);
                }
                if (tabLayout.getSelectedTabPosition() == 1 ){
                    Log.d(TAG, "onTabSelected: Tab 1");
                    TransactionPagerAdapter adapter = (TransactionPagerAdapter)viewPager.getAdapter();
                    IncomeFragment fragment = (IncomeFragment)adapter.getItem(1);
                    fragment.updateTransaction(transaction);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // TODO: 27/05/2020 remove keyboard on unselect
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


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
//                transaction.setAmount(Double.parseDouble(s.toString()));
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
//                transaction.setAmount(Double.parseDouble(s.toString()));
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
//                transaction.setDescription(s.toString());
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
//                transaction.setDescription(s.toString());
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
        // TODO: removes text from EditText, priority of view or stored
        TextView expenseDateTextView = findViewById(R.id.date_text_view_expense);
        TextView expenseTimeTextView = findViewById(R.id.time_text_view_expense);
//        EditText expenseAmountEditText = findViewById(R.id.amount_edit_text_expense);
//        EditText expenseDescriptionEditText = findViewById(R.id.description_edit_text_expense);
        TextView incomeDateTextView = findViewById(R.id.date_text_view_income);
        TextView incomeTimeTextView = findViewById(R.id.time_text_view_income);
//        EditText incomeAmountEditText = findViewById(R.id.amount_edit_text_income);
//        EditText incomeDescriptionEditText = findViewById(R.id.description_edit_text_income);

        expenseDateTextView.setText(transaction.getDate("dd-MM-yyyy"));
        expenseTimeTextView.setText(transaction.getDate("hh:mm"));
//        expenseAmountEditText.setText(String.valueOf(transaction.getAmount()));
//        expenseDescriptionEditText.setText(transaction.getDescription());
        incomeDateTextView.setText(transaction.getDate("dd-MM-yyyy"));
        incomeTimeTextView.setText(transaction.getDate("hh:mm"));
//        incomeAmountEditText.setText(String.valueOf(transaction.getAmount()));
//        incomeDescriptionEditText.setText(transaction.getDescription());
    }

    public void saveExpenseTransactionButton(View view) {
        Log.d(TAG, "saveExpenseTransactionButton: init");

        EditText expenseAmountEditText = findViewById(R.id.amount_edit_text_expense);
        EditText expenseDescriptionEditText = findViewById(R.id.description_edit_text_expense);

        String text = expenseAmountEditText.getText().toString();
        if (!text.isEmpty()) {
            try {
                double val = Double.parseDouble(text);
                val *= -1;
                transaction.setAmount(val);
            } catch (Exception e) {
                Log.w(TAG, "saveExpenseTransactionButton: ", e);
            }
        }
        transaction.setDescription(expenseDescriptionEditText.getText().toString());

        // TODO: 27/05/2020 update option too
        getContentResolver().insert(
                TransactionProvider.CONTENT_URI, transaction.getContentValues());

        Toast.makeText(this, "Saved Successfully", Toast.LENGTH_SHORT).show();

        finish();
    }

    public void saveIncomeTransactionButton(View view) {
        Log.d(TAG, "saveIncomeTransactionButton: init");

        EditText incomeAmountEditText = findViewById(R.id.amount_edit_text_income);
        EditText incomeDescriptionEditText = findViewById(R.id.description_edit_text_income);

        String text = incomeAmountEditText.getText().toString();
        if (!text.isEmpty()) {
            try {
                transaction.setAmount(Double.parseDouble(text));
            } catch (Exception e) {
                Log.w(TAG, "saveExpenseTransactionButton: ", e);
            }
        }
        transaction.setDescription(incomeDescriptionEditText.getText().toString());

        getContentResolver().insert(
                TransactionProvider.CONTENT_URI, transaction.getContentValues());

        Toast.makeText(this, "Saved Successfully", Toast.LENGTH_SHORT).show();
    }
}

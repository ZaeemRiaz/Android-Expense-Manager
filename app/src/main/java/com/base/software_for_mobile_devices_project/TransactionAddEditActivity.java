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
    private TabLayout tabLayout;
    private ViewPager viewPager;
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

        viewPager = findViewById(R.id.pager);
        tabLayout = findViewById(R.id.tabs);

        TransactionPagerAdapter transactionPagerAdapter = new TransactionPagerAdapter(
                getSupportFragmentManager(), tabLayout.getTabCount());

        viewPager.setAdapter(transactionPagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setupWithViewPager(viewPager);

        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        currentTransaction = new Transaction(date, 0, "");

        updateView();
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
        TextView expenseDateTextView = findViewById(R.id.date_text_view_expense);
        TextView expenseTimeTextView = findViewById(R.id.time_text_view_expense);
        EditText expenseAmountEditText = findViewById(R.id.amount_edit_text_expense);
        EditText expenseDescriptionEditText = findViewById(R.id.description_edit_text_expense);
        TextView incomeDateTextView = findViewById(R.id.date_text_view_income);
        TextView incomeTimeTextView = findViewById(R.id.time_text_view_income);
        EditText incomeAmountEditText = findViewById(R.id.amount_edit_text_income);
        EditText incomeDescriptionEditText = findViewById(R.id.description_edit_text_income);

        expenseDateTextView.setText(currentTransaction.getDate("dd-MM-yyyy"));
        incomeDateTextView.setText(currentTransaction.getDate("dd-MM-yyyy"));
        expenseTimeTextView.setText(currentTransaction.getDate("hh:mm"));
        incomeTimeTextView.setText(currentTransaction.getDate("hh:mm"));
        expenseAmountEditText.setText(String.valueOf(currentTransaction.getAmount()));
        incomeAmountEditText.setText(String.valueOf(currentTransaction.getAmount()));
        expenseDescriptionEditText.setText(currentTransaction.getDescription());
        incomeDescriptionEditText.setText(currentTransaction.getDescription());
    }
}

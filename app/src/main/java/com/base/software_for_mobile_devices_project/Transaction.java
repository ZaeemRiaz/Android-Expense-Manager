package com.base.software_for_mobile_devices_project;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Transaction implements Serializable,Persistable {
    private static final String TAG = "=== Transaction ===";

    private Date date;
    private double amount;
    private String description;

    Transaction(String date, double amount, String description) throws ParseException {

        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        this.date = sdf.parse(date);
        this.amount = amount;
        this.description = description;
    }

    String getDate() {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        return sdf.format(date);
    }

    public void setDate(Date date) {
        this.date = date;
    }

    double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public void save(SQLiteDatabase dataStore, String date) {

    }

    @Override
    public void load(Cursor dataStore) {

    }

    @Override
    public String getType() {
        return null;
    }
}
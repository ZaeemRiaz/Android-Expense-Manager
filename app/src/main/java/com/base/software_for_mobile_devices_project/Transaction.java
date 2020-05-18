package com.base.software_for_mobile_devices_project;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Transaction implements Serializable, Persistable {
    private static final String TAG = "=== Transaction ===";
    private int id;
    private Date date;
    private double amount;
    private String description;

    Transaction(Date date, double amount, String description) {
        this.date = date;
        this.amount = amount;
        this.description = description;
    }

    Transaction(String date, double amount, String description) throws ParseException {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        this.date = sdf.parse(date);
        this.amount = amount;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    String getDate(String format) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    public void setDate(String date, String format) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            this.date = sdf.parse(date);
        } catch (ParseException e) {

            e.printStackTrace();
        }
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
    public void save(SQLiteDatabase dataStore) {
        Log.d(TAG, "save: init");
    }

    @Override
    public void load(Cursor dataStore) {
        Log.d(TAG, "load: init");
    }

    @Override
    public String getType() {
        return null;
    }
}
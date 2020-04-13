package com.base.software_for_mobile_devices_project;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.Serializable;
import java.util.Date;

public class Transaction implements Serializable,Persistable {
    private static final String TAG = "=== Transaction ===";

    private Date date;
    private double amount;
    private String description;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
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
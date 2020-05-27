package com.base.software_for_mobile_devices_project;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Transaction implements Serializable, Persistable {
    private static final String TAG = "=== Transaction ===";
    public static int nextId = 0;
    private int id;
    private Date date;
    private double amount;
    private String description;

    public Transaction() {
//        id = nextId++;
    }

    Transaction(Date date, double amount, String description) {
        id = nextId++;
        this.date = date;
        this.amount = amount;

        this.description = description;
    }

    Transaction(String date, double amount, String description) throws ParseException {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        id = nextId++;
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

    void setDate(String date, String format) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            this.date = sdf.parse(date);
        } catch (ParseException e) {
            Log.w(TAG, "setDate: ", e);
        }
    }

    double getAmount() {
        return amount;
    }

    void setAmount(double amount) {
        this.amount = amount;
    }

    String getDescription() {
        return description;
    }

    void setDescription(String description) {
        this.description = description;
    }

    ContentValues getContentValues() {
        ContentValues values = new ContentValues();
        values.put(TransactionDbHelper.id, id);
        values.put(TransactionDbHelper.date, getDate("yyyy-MM-dd hh:mm:ss"));
        values.put(TransactionDbHelper.amount, amount);
        values.put(TransactionDbHelper.description, description);

        return values;
    }

    @Override
    public void save(SQLiteDatabase dataStore) {
        dataStore.insertWithOnConflict(TransactionDbHelper.transaction, null, getContentValues(), SQLiteDatabase.CONFLICT_REPLACE);
    }

    @Override
    public void load(Cursor dataStore) {
        id = dataStore.getInt(dataStore.getColumnIndex(TransactionDbHelper.id));
        setDate(dataStore.getString(dataStore.getColumnIndex(TransactionDbHelper.date)), "yyyy-MM-dd hh:mm:ss");
        amount = dataStore.getDouble(dataStore.getColumnIndex(TransactionDbHelper.amount));
        description = dataStore.getString(dataStore.getColumnIndex(TransactionDbHelper.description));
//
//        //update next id
//        if (nextId <= id) {
//            nextId = id + 1;
//        }
//        Log.i(TAG, "load: nextId: " + nextId);
    }

    public void delete(SQLiteDatabase dataStore) {
        String[] args = new String[1];
        args[0] = String.valueOf(id);
        dataStore.delete(TransactionDbHelper.transaction, TransactionDbHelper.id + " = ?", args);
    }

    @Override
    public String getType() {
        return null;
    }
}
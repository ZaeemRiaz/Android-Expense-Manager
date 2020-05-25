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
    private static int nextId = 0;
    private int id;
    private Date date;
    private double amount;
    private String description;

    public Transaction() {
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
        ContentValues values = new ContentValues();
        values.put(TransactionDbHelper.id, id);
        values.put(TransactionDbHelper.date, getDate("yyyy-MM-dd hh:mm:ss"));
        values.put(TransactionDbHelper.amount, amount);
        values.put(TransactionDbHelper.description, description);

        dataStore.insertWithOnConflict(TransactionDbHelper.transaction, null, values, SQLiteDatabase.CONFLICT_REPLACE);
    }

    @Override
    public void load(Cursor dataStore) {
        Log.d(TAG, "load: init");
        id = dataStore.getInt(dataStore.getColumnIndex(TransactionDbHelper.id));
        setDate(dataStore.getString(dataStore.getColumnIndex(TransactionDbHelper.date)), "yyyy-MM-dd hh:mm:ss");
        // TODO: 19/05/2020 change amount get type
        amount = dataStore.getInt(dataStore.getColumnIndex(TransactionDbHelper.amount));
        description = dataStore.getString(dataStore.getColumnIndex(TransactionDbHelper.description));
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
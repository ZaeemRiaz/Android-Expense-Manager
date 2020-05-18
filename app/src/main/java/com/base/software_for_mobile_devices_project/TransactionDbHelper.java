package com.base.software_for_mobile_devices_project;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class TransactionDbHelper extends SQLiteOpenHelper {
    private static final String TAG = "=== TransactionDbHelper ===";
    static final String transaction = "Transactions";
    static final String id = "Id";
    static final String date = "Date";
    static final String amount = "Amount";
    static final String description = "Description";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Attendance.db";

    public TransactionDbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // TODO: 19/05/2020: table columns types
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + transaction + "(" +
                id + " INTEGER, " +
                date + " TEXT, " +
                amount + " INTEGER, " +
                description + " TEXT," +
                "PRIMARY KEY (" + id + "))";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Attendance");
        onCreate(db);
    }
}

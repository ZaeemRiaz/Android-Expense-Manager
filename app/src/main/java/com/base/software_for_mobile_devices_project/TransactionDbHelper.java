package com.base.software_for_mobile_devices_project;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class TransactionDbHelper extends SQLiteOpenHelper {
    //TODO: rename table columns and variables
    static final String transaction = "Transactions";
    static final String date = "Date";
    static final String rollNo = "RollNo";
    static final String name = "Name";
    static final String present = "Present";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Attendance.db";

    public TransactionDbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + transaction + "(" +
                "Date TEXT , " +
                "RollNo TEXT, " +
                "Name TEXT, " +
                "Present INTEGER," +
                "PRIMARY KEY (Date, RollNo))";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Attendance");
        onCreate(db);
    }
}

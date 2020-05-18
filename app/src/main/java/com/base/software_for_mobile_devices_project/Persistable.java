package com.base.software_for_mobile_devices_project;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public interface Persistable {
    void save(SQLiteDatabase dataStore);

    void load(Cursor dataStore);

    String getType();
}

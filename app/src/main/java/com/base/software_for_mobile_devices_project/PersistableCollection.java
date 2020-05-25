package com.base.software_for_mobile_devices_project;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.NonNull;

import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;

public class PersistableCollection<T extends Persistable> extends AbstractCollection {
    private static final String TAG = "=== PersistableCollection ===";
    private Collection<T> collection;
    private Class<T> tClass;

    public PersistableCollection(Collection<T> collection, Class<T> tClass) {
        this.collection = collection;
        this.tClass = tClass;
    }

    @NonNull
    @Override
    public Iterator iterator() {
        return collection.iterator();
    }

    @Override
    public int size() {
        return collection.size();
    }

    public void save(Context context) {
        Log.d(TAG, "Save: collection size: " + collection.size());
        TransactionDbHelper dbHelper = new TransactionDbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        for (T t : collection) {
            ((Persistable) t).save(db);
        }
    }

    public void load(Context context) {
        Log.d(TAG, "Load");
        TransactionDbHelper dbHelper = new TransactionDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // TODO: 19/05/2020: convert to normal query and check if needed or not
        //check if table empty
        Cursor cur = db.rawQuery("SELECT COUNT(*) FROM " + TransactionDbHelper.transaction, null);
        if (cur != null) {
            cur.moveToFirst();                       // Always one row returned.
            if (cur.getInt(0) == 0) {               // Zero count means empty table.
                Log.d(TAG, "Table Empty");
                return;
            }
        }

        // TODO: 19/05/2020: convert to normal query
        //load table to collection
        collection.clear();
        String query = "SELECT * FROM " + TransactionDbHelper.transaction;
        Cursor cursor = db.rawQuery(query, null);

        while (cursor.moveToNext()) {
            T object = getObject(Transaction.class);
            object.load(cursor);
            collection.add(object);
        }

    }
    private T getObject(Class clazz) {
        try {
//            Class c = Class.forName(type);
            Class c = clazz;
            return (T) c.newInstance();
        } catch (Exception e) {
            Log.w(TAG, "getObject: ", e);
            return null;
        }
    }
}

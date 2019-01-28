package com.noisyz.mvvmbase.db;

import android.database.Cursor;

/**
 * Created by TESSARACT2 on 18.04.2018.
 */

public class CursorData {

    private Cursor cursor;

    public CursorData(Cursor cursor) {
        this.cursor = cursor;
    }

    public double getDouble(String fieldName) {
        return cursor.getDouble(cursor.getColumnIndex(fieldName));
    }

    public long getLong(String fieldName) {
        return cursor.getLong(cursor.getColumnIndex(fieldName));
    }

    public String getString(String fieldName) {
        return cursor.getString(cursor.getColumnIndex(fieldName));
    }

    public int getInt(String fieldName) {
        return cursor.getInt(cursor.getColumnIndex(fieldName));
    }
}

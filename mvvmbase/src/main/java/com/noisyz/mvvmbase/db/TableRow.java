package com.noisyz.mvvmbase.db;

import android.content.ContentValues;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public abstract class TableRow implements Serializable {
    public static final String TEXT = " text", REAL = " real", INTEGER = " integer", INTEGER_PRIMARY_KEY = " integer primary key autoincrement";
    public static final String ID = "_id", TIME = "time";
    private String tableName;
    private long id = -1, time;

    public static <T extends TableRow> T createTableRow(Class<T> clazz, String tableName, CursorData cursor) {
        try {
            T t = clazz.newInstance();
            if (cursor != null) {
                t.setTableName(tableName);
                t.fillDataWithCursor(cursor);
                return t;
            }
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public TableRow() {
    }

    public TableRow(String tableName, long time) {
        this(tableName, null);
        this.time = time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public boolean hasBeenSaved() {
        return id != -1;
    }

    public TableRow(String tableName, CursorData cursor) {
        this.tableName = tableName
                .replaceAll("\\s+", "")
                .replaceAll("/", "")
                .replaceAll("\\.", "");
        if (cursor != null) {
            fillDataWithCursor(cursor);
        }
    }

    public long getId() {
        return id;
    }

    public long getTime() {
        return time;
    }

    public ContentValues createRow() {
        ContentValues cv = new ContentValues();
        cv.put(TIME, String.valueOf(time));
        fillRow(cv);
        return cv;
    }

    public abstract void fillRow(ContentValues contentValues);

    protected void fillDataWithCursor(CursorData cursor) {
        id = cursor.getLong(ID);
        time = Long.parseLong(cursor.getString(TIME));
        fillData(cursor);
    }

    public void setId(long id) {
        this.id = id;
    }

    protected abstract void fillData(CursorData cursor);

    protected abstract void fillColumns(Map<String, String> columns);

    private Map<String, String> getColumns() {
        Map<String, String> columns = new HashMap<>();
        columns.put(ID, INTEGER_PRIMARY_KEY);
        columns.put(TIME, TEXT);
        fillColumns(columns);
        return columns;
    }

    public String getCreateTableRequest() {
        return "CREATE TABLE " + this.tableName + " (" + getColumnsInString() + ");";
    }

    private String getColumnsInString() {
        StringBuilder columnsInString = new StringBuilder();
        Map<String, String> columns = getColumns();
        for (String key : columns.keySet()) {
            columnsInString.append(key).append(" ").append(columns.get(key)).append(", ");
        }
        String columnsInStringValue = columnsInString.toString();
        return columnsInStringValue.substring(0, columnsInStringValue.length() - 2);
    }

    public String getTableName() {
        return this.tableName;
    }
}

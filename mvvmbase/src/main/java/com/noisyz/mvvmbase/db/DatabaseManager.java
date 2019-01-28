package com.noisyz.mvvmbase.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {

    private static SQLiteOpenHelper databaseHelper;

    public static void init(SQLiteOpenHelper sqLiteOpenHelper) {
        databaseHelper = sqLiteOpenHelper;
    }

    public static void clearTable(String tableName) {
        try {
            databaseHelper.getWritableDatabase().execSQL("delete from " + tableName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void dropTable(String tableName) {
        try {
            databaseHelper.getWritableDatabase().execSQL("drop table if exists " + tableName);
            Log.d("myLogs", "Table " + tableName + " dropped");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isTableExists(String tableName) {
        try {
            Cursor cursor = databaseHelper.getReadableDatabase().rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '" + tableName + "'", null);
            if (cursor != null) {
                boolean result;
                if (cursor.getCount() > 0) {
                    result = true;
                } else {
                    result = false;
                }
                cursor.close();
                return result;
            }
        } catch (Exception e) {
        }
        return false;
    }

    public static void removeRows(String tableName, String where, String... args) {
        try {
            databaseHelper.getWritableDatabase().delete(tableName,
                    where, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void removeTableRow(TableRow tableRow) {
        removeRows(tableRow.getTableName(), TableRow.ID + " = ?", String.valueOf(tableRow.getId()));
    }

    public static long saveTableRow(TableRow tableRow) {
        try {
            if (tableRow.hasBeenSaved()) {
                databaseHelper.getWritableDatabase().update(tableRow.getTableName(), tableRow.createRow(),
                        TableRow.ID + " = ?", new String[]{String.valueOf(tableRow.getId())});
                return tableRow.getId();
            } else {
                if (!isTableExists(tableRow.getTableName())) {
                    databaseHelper.getWritableDatabase().execSQL(tableRow.getCreateTableRequest());
                }
                long id = databaseHelper.getWritableDatabase().insert(tableRow.getTableName(), null, tableRow.createRow());
                tableRow.setId(id);
                return id;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static <T extends TableRow> T getTableRow(String tableName, Class<T> clazz, String selection, String... args) {
        List<T> tList = getTableRows(tableName, clazz, selection, args);
        return tList.isEmpty() ? null : tList.get(0);
    }

    public static <T extends TableRow> T getTableRowById(Class<T> clazz, String tableName, long id) {
        return getTableRow(tableName, clazz, TableRow.ID + " = ?", String.valueOf(id));
    }

    public static <T extends TableRow> T getTableRowByTime(Class<T> clazz, String tableName, long time) {
        return getTableRow(tableName, clazz, TableRow.TIME + " = ?", String.valueOf(time));
    }

    public static <T extends TableRow> List<T> getTableRows(String tableName, Class<T> clazz) {
        return getTableRows(tableName, clazz, 0, System.currentTimeMillis() + 1000);
    }

    public static <T extends TableRow> List<T> getTableRows(String tableName, Class<T> clazz, long period) {
        long currentTime = System.currentTimeMillis();
        return getTableRows(tableName, clazz, currentTime - period, currentTime);
    }


    public static <T extends TableRow> List<T> getTableRows(String tableName, Class<T> clazz, long from, long till) {
        return getTableRows(tableName, clazz, TableRow.TIME + " > ? AND " + TableRow.TIME + " < ?",
                String.valueOf(from / 1000), String.valueOf(till / 1000));
    }

    public static <T extends TableRow> List<T> getTableRows(String tableName, Class<T> clazz, String selection, String... args) {
        List<T> rows = new ArrayList<>();
        try {
            if (isTableExists(tableName)) {
                Cursor cursor = databaseHelper.getReadableDatabase().query(tableName,
                        null, selection, args, null, null, TableRow.TIME + " ASC");
                CursorData cursorData = new CursorData(cursor);
                if (cursor.moveToFirst()) {
                    do {
                        rows.add(TableRow.createTableRow(clazz, tableName, cursorData));
                    } while (cursor.moveToNext());
                }
                cursor.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rows;
    }
}


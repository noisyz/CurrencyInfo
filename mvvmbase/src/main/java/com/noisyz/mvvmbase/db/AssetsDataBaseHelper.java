package com.noisyz.mvvmbase.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by imac on 07.11.16.
 */


//Helper to use database from assets
public abstract class AssetsDataBaseHelper extends SQLiteOpenHelper {

    private static final String SHARED_KEY = "DB_PREFS", BASE_COPIED = "BASE_COPIED";
    private static String TAG = "DataBaseHelper"; // Tag just for the LogCat window
    //destination path (location) of our database on device
    private static String DB_PATH = "";
    private final Context mContext;
    private final String name;


    public AssetsDataBaseHelper(String dbName, Context context, int version) {
        super(context, dbName, null, version);
        if (android.os.Build.VERSION.SDK_INT >= 17) {
            DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
        } else {
            DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
        }
        this.mContext = context;
        this.name = dbName;
    }

    private void commitDataBaseCopied() {
        mContext.getSharedPreferences(SHARED_KEY, Context.MODE_PRIVATE)
                .edit()
                .putBoolean(BASE_COPIED, true)
                .apply();
    }

    private boolean hasBeenDataBaseCopied() {
        return mContext.getSharedPreferences(SHARED_KEY, Context.MODE_PRIVATE)
                .getBoolean(BASE_COPIED, false);
    }

    public void createDataBase() throws IOException {
        //If the database does not exist, copy it from the assets.
        boolean mDataBaseExist = hasBeenDataBaseCopied();
        if (!mDataBaseExist) {
            this.getReadableDatabase();
            this.close();
            try {
                //Copy the database from assests
                copyDataBase();
                Log.e(TAG, "createDatabase database created");
            } catch (IOException mIOException) {
                throw new Error("ErrorCopyingDataBase");
            }
        }
    }

    protected String getDbName() {
        return name;
    }

    //Check that the database exists here: /data/data/your package/databases/Da Name
    private boolean checkDataBase() {
        File dbFile = new File(DB_PATH + getDbName());
        //Log.v("dbFile", dbFile + "   "+ dbFile.exists());
        return dbFile.exists();
    }

    //Copy the database from assets
    private void copyDataBase() throws IOException {
        InputStream mInput = mContext.getAssets().open(getDbName());
        String outFileName = DB_PATH + getDbName();
        OutputStream mOutput = new FileOutputStream(outFileName);
        byte[] mBuffer = new byte[1024];
        int mLength;
        while ((mLength = mInput.read(mBuffer)) > 0) {
            mOutput.write(mBuffer, 0, mLength);
        }
        mOutput.flush();
        mOutput.close();
        mInput.close();
        commitDataBaseCopied();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        mContext.getSharedPreferences(SHARED_KEY, Context.MODE_PRIVATE)
                .edit()
                .putBoolean(BASE_COPIED, false)
                .apply();
    }
}

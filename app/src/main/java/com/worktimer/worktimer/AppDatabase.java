package com.worktimer.worktimer;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class AppDatabase  extends SQLiteOpenHelper {
    public static final String TAG = "AppDatabase";

    public static final String DATABASE_NAME = "WorkTimer.db";
    public static final int DATABASE_VERSION = 3;


    private static  AppDatabase instance = null;


    public AppDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d(TAG, "AppDatabase: constructor");
    }

    static AppDatabase getInstance(Context context) {
        Log.d(TAG, "getInstance: ");
        if(instance == null) {
            Log.d(TAG, "getInstance: creating new instance");
            instance = new AppDatabase(context);
        }

        return instance;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        Log.d(TAG, "onCreate: ");

        String sSQL;
        sSQL = "CREATE TABLE " + WorkTimer.TABLE_NAME + " ("
                + WorkTimer.Columns._ID + " INTEGER PRIMARY KEY NOT NULL, "
                + WorkTimer.Columns.TASK_NAME + " TEXT NOT NULL, "
                + WorkTimer.Columns.TASK_DESCRIPTION + " TEXT, "
                + WorkTimer.Columns.TASKS_SORTORDER + " INTEGER);";
        Log.d(TAG, sSQL);
        db.execSQL(sSQL);


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Log.d(TAG, "onUpgrade: ");
    }
}

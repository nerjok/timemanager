package com.worktimer.worktimer;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

public class AppProvider extends ContentProvider {

    private static final String TAG = "AppProvider";

    static final String CONTENT_AUTHORITY = "com.worktimer.worktimer.provider";
    public static final Uri CONTENT_AUTHORITY_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    private AppDatabase appDatabase;

    public static final UriMatcher sUriMatcher = buildUriMatcher();

    private static final int TASKS = 100;
    private static final int TASKS_ID = 101;

    private static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

        matcher.addURI(CONTENT_AUTHORITY, WorkTimer.TABLE_NAME, TASKS);
        matcher.addURI(CONTENT_AUTHORITY, WorkTimer.TABLE_NAME + "/#", TASKS_ID);

        return matcher;
    }

    @Override
    public boolean onCreate() {
        Log.d(TAG, "onCreate: calling state");
        appDatabase = AppDatabase.getInstance(getContext());
        //appDatabase.getWritableDatabase();
        //appDatabase = new AppDatabase(getBaseContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Log.d(TAG, "query: ");
        final int match = sUriMatcher.match(uri);

        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        switch (match) {
            case TASKS:
                queryBuilder.setTables(WorkTimer.TABLE_NAME);
                break;
            case TASKS_ID:
                queryBuilder.setTables(WorkTimer.TABLE_NAME);
                long taskId = WorkTimer.getTaskId(uri);
                queryBuilder.appendWhere(WorkTimer.Columns._ID + "=" + taskId);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        SQLiteDatabase db = appDatabase.getReadableDatabase();

        Cursor cursor = queryBuilder.query(db, projection, selection, selectionArgs, null, null, sortOrder);

        Log.d(TAG, "query: AppProvider rows count returned" + cursor.getCount());
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        Log.d(TAG, "getType: ");
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        Log.d(TAG, "insert: ");

        final int match = sUriMatcher.match(uri);

        final SQLiteDatabase db;
        Uri returnUri;
        long recordId;

        switch (match) {
            case TASKS:
                db = appDatabase.getWritableDatabase();
                recordId = db.insert(WorkTimer.TABLE_NAME, null, values);
                if (recordId >= 0) {
                    returnUri = WorkTimer.buildTaskUri(recordId);
                } else {
                    throw new android.database.SQLException("Failed to insert into " + uri.toString());
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown uri: " + uri);
        }


        if (recordId >= 0) {
            // something was inserted
            Log.d(TAG, "insert: Setting notifyChanged with " + uri);
            //noinspection ConstantConditions
            getContext().getContentResolver().notifyChange(uri, null);
        } else {
            Log.d(TAG, "insert: nothing inserted");
        }

        Log.d(TAG, "Exiting insert, returning " + returnUri);

        return returnUri;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int delete( @NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}

package com.worktimer.worktimer;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.security.InvalidParameterException;

public class Main2Activity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>,
    View.OnClickListener {

    private static final int LOADER_ID = 1;

    private static final String SELECTION_PARAM = "SELECTION";
    private static final String SELECTION_ARGS_PARAM = "SELECTION_ARGS";
    private static final String SORT_ORDER_PARAM = "SORT_ORDER";

    public static final String TAG = "Main2Activity";
    private Bundle mArgs = new Bundle();

    CursorRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main2);

        final Button button = (Button) findViewById(R.id.button1);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //View mainFragment = findViewById(R.id.fragment);


                   LinearLayout fragContainer = (LinearLayout) findViewById(R.id.fragment);
                   LinearLayout ll = new LinearLayout(this);
                   ll.setOrientation(LinearLayout.VERTICAL);
                   int dis = 123;
                   ll.setId(dis);

        fragmentTransaction.add(ll.getId(), TestFragment.newInstance("one", "two"),"gtgt").commit();
        fragContainer.addView(ll);

        button.setOnClickListener(this);

        //if (adapter == null)
            //adapter = new CursorRecyclerViewAdapter([], this);

        
        Log.d(TAG, "onCreate: Mainactivity2");
    }


    @Override
    public void onClick(View view) {

        Log.d(TAG, "onClick: Main2Activity "+ view.getId());
        //getSupportLoaderManager().restartLoader(LOADER_ID, mArgs, this);

        ContentResolver contentResolver = getContentResolver();
        ContentValues values = new ContentValues();

        values.put(WorkTimer.Columns.TASK_NAME, "bubu");
        values.put(WorkTimer.Columns.TASK_DESCRIPTION, "description");
        values.put(WorkTimer.Columns.TASKS_SORTORDER, "orderSort");
        contentResolver.insert(WorkTimer.CONTENT_URI, values);


        android.support.v4.app.LoaderManager.getInstance(this).restartLoader(LOADER_ID, mArgs, this);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        Log.d(TAG, "onCreateLoader: MainActivity2");

        switch (id) {
            case LOADER_ID:
                String[] projection = {BaseColumns._ID,
                        WorkTimer.Columns.TASK_NAME,
                        WorkTimer.Columns.TASK_DESCRIPTION,
                        WorkTimer.Columns.TASKS_SORTORDER};

                String selection = null;
                String[] selectionArgs = null;
                String sortOrder = null;

                if (args != null) {
                    selection = args.getString(SELECTION_PARAM);
                    selectionArgs = args.getStringArray(SELECTION_ARGS_PARAM);
                    sortOrder = args.getString(SORT_ORDER_PARAM);
                }

                return new CursorLoader(this,
                        WorkTimer.CONTENT_URI,
                        projection,
                        selection,
                        selectionArgs,
                        sortOrder);

            default:
                throw new InvalidParameterException(TAG + ".onCreateLoader called with invalid loader id " + id);
        }
        }

        @Override
        public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {
            Log.d(TAG, "onLoadFinished: ");
        }

        @Override
        public void onLoaderReset(@NonNull Loader<Cursor> loader) {
            Log.d(TAG, "onLoaderReset: ");
        }

        @Override
        public void onPointerCaptureChanged(boolean hasCapture) {
            Log.d(TAG, "onPointerCaptureChanged: ");
        }
    }

package com.worktimer.worktimer;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.security.InvalidParameterException;

public class Main2Activity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>,
AddEditActivityFragment.OnFragmentInteractionListener,
    View.OnClickListener {

    private static final int LOADER_ID = 1;

    private static final String SELECTION_PARAM = "SELECTION";
    private static final String SELECTION_ARGS_PARAM = "SELECTION_ARGS";
    private static final String SORT_ORDER_PARAM = "SORT_ORDER";

    public static final String TAG = "Main2Activity";
    private Bundle mArgs = new Bundle();

    CursorRecyclerViewAdapter adapter;
    private boolean mTwoPane = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main2);

        final Button button = (Button) findViewById(R.id.button1);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //fragmentTransaction.commit();
        //View mainFragment = findViewById(R.id.fragment);

                   LinearLayout fragContainer = (LinearLayout) findViewById(R.id.fragment);
                   LinearLayout ll = new LinearLayout(this);
                   ll.setOrientation(LinearLayout.VERTICAL);
                   int dis = 123;
                   ll.setId(dis);

        fragmentTransaction.add(ll.getId(), TestFragment.newInstance("one", "two"),"gtgt").commit();
        fragContainer.addView(ll);





        button.setOnClickListener(this);

        if (adapter == null)
            adapter = new CursorRecyclerViewAdapter(null, null);

        
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
        public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
            adapter.swapCursor(data);
            int count = adapter.getItemCount();
            Log.d(TAG, "onLoadFinished: "+ data);
        }

        @Override
        public void onLoaderReset(@NonNull Loader<Cursor> loader) {
            Log.d(TAG, "onLoaderReset: ");
            adapter.swapCursor(null);
        }

        @Override
        public void onPointerCaptureChanged(boolean hasCapture) {
            Log.d(TAG, "onPointerCaptureChanged: ");
        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {
            case R.id.add_task:
                taskEdit(null);
                Log.d(TAG, "onOptionsItemSelected: " + id);
                break;
             default:
                 Log.d(TAG, "onOptionsItemSelected: default");
        }
        return super.onOptionsItemSelected(item);
    }

    public void taskEdit(Worktime task) {

        AddEditActivityFragment fragment = new AddEditActivityFragment();

        Bundle arguments = new Bundle();
        //arguments.putSerializable(Worktime.class.getSimpleName(), task);
        //fragment.setArguments(arguments);

        Log.d(TAG, "taskEditRequest: twoPaneMode");
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.task_details_container, fragment)
                .commit();


        if(!mTwoPane) {
            Log.d(TAG, "taskEditRequest: in single-pane mode (phone)");
            // Hide the left hand fragment and show the right hand frame
            View mainFragment = findViewById(R.id.fragment);
            View addEditLayout = findViewById(R.id.task_details_container);
            mainFragment.setVisibility(View.GONE);
            addEditLayout.setVisibility(View.VISIBLE);
        }
        Log.d(TAG, "Exiting taskEditRequest");

    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        Log.d(TAG, "onFragmentInteraction: kuku");

        AddEditActivityFragment fragment = new AddEditActivityFragment();

        Bundle arguments = new Bundle();
        //arguments.putSerializable(Worktime.class.getSimpleName(), task);
        //fragment.setArguments(arguments);

        Log.d(TAG, "taskEditRequest: twoPaneMode");
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.task_details_container, fragment)
                .commit();


        if(!mTwoPane) {
            Log.d(TAG, "taskEditRequest: in single-pane mode (phone)");
            // Hide the left hand fragment and show the right hand frame
            View mainFragment = findViewById(R.id.fragment);
            View addEditLayout = findViewById(R.id.task_details_container);
            mainFragment.setVisibility(View.VISIBLE);
            addEditLayout.setVisibility(View.GONE);
            android.support.v4.app.LoaderManager.getInstance(this).restartLoader(LOADER_ID, mArgs, this);

        }
    }
}

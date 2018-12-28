package com.worktimer.worktimer;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.security.InvalidParameterException;

public class TestFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>,
        View.OnClickListener,
        CursorRecyclerViewAdapter.OnTaskClickListener
{

    private CursorRecyclerViewAdapter mAdapter; // add adapter reference
    public static final String TAG = "TestFragment";
    private static final String SELECTION_PARAM = "SELECTION";
    private static final String SELECTION_ARGS_PARAM = "SELECTION_ARGS";
    private static final String SORT_ORDER_PARAM = "SORT_ORDER";
    private static final int LOADER_ID = 1;


    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        Log.d(TAG, "onCreateLoader: ");
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
                Log.d(TAG, "onCreateLoader: switch LOADER_ID");

                return new CursorLoader(getActivity(),
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
    public void onEditClick(Worktime workTime) {
        Log.d(TAG, "onEditClick: ");
        CursorRecyclerViewAdapter.OnTaskClickListener listener = (CursorRecyclerViewAdapter.OnTaskClickListener) getActivity();
        if (listener != null) {
            listener.onEditClick(workTime);
        }

    }

    @Override
    public void onDeleteClick(Worktime task) {
        Log.d(TAG, "onDeleteClick: ");
        CursorRecyclerViewAdapter.OnTaskClickListener listener = (CursorRecyclerViewAdapter.OnTaskClickListener) getActivity();
        if (listener != null) {
            listener.onDeleteClick(task);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated: ");
        android.support.v4.app.LoaderManager.getInstance(this).initLoader(LOADER_ID, null, this);
        //getLoaderManager().initLoader(LOADER_ID, null, this);

    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        Log.d(TAG, "Entering onLoadFinished");
        mAdapter.swapCursor(data);
        int count = mAdapter.getItemCount();

        Log.d(TAG, "onLoadFinished: count is " + count);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        Log.d(TAG, "onLoaderReset: ");
        mAdapter.swapCursor(null);
    }

    @Override
    public void onClick(View view) {
        Log.d(TAG, "onClick: ");

    }

    public static TestFragment newInstance(String text, String text2) {
        Log.d(TAG, "newInstance: ");
        TestFragment f = new TestFragment();

        Bundle b = new Bundle();
        b.putString("text", text);
        f.setArguments(b);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");

        View v =  inflater.inflate(R.layout.fragments, container, false);
        //((TextView) v.findViewById(R.id.tvFragText)).setText(getArguments().getString("text"));

        /* Recycleview */
        RecyclerView recyclerView = v.findViewById(R.id.task_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        if (mAdapter == null) {
            mAdapter = new CursorRecyclerViewAdapter(null, this);
        }
        recyclerView.setAdapter(mAdapter);


        return v;
    }
}
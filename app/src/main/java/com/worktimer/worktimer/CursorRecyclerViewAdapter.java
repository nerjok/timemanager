package com.worktimer.worktimer;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CursorRecyclerViewAdapter extends RecyclerView.Adapter<CursorRecyclerViewAdapter.MyViewHolder>{

    private String[] mDataset;
    private OnTaskClickListener mListener;
    public static final String TAG = "CursorRecyclerViewAdapt";
    private Cursor mCursor;

    interface OnTaskClickListener {
        void onEditClick(WorkTimer workTimer);
    }
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public View mTextView;
        public TextView taskItem;
        TextView text2;
        public MyViewHolder(View v) {
            super(v);
            mTextView = v;
            this.taskItem = v.findViewById(R.id.taskItem);
            this.text2 = v.findViewById(R.id.text2);
        }
    }


    public CursorRecyclerViewAdapter(Cursor cursor, OnTaskClickListener listener) {
        Log.d(TAG, "CursorRecyclerViewAdapter: Constructor called " + cursor);
        mCursor = cursor;
        mListener = listener;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public CursorRecyclerViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        Log.d(TAG, "onCreateViewHolder: ");
        // create a new view
        View v =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_item, parent, false);
/*
        LinearLayout ll = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_item, parent, true); // get the parent layout view
        TextView tv = (TextView) ll.findViewById(R.id.taskItem);
*/
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        if((mCursor == null) || (mCursor.getCount() == 0)) {
            Log.d(TAG, "onBindViewHolder: count is zero");
        } else if ( mCursor != null /*&& mCursor.moveToFirst()/* mCursor.getCount() > 1*/ ){
            if(!mCursor.moveToPosition(position)) {
                throw new IllegalStateException("Couldn't move cursor to position " + position);
            }
            final Worktime workTime = new Worktime(mCursor.getLong(mCursor.getColumnIndex(WorkTimer.Columns._ID)),
                    mCursor.getString(mCursor.getColumnIndex(WorkTimer.Columns.TASK_NAME)),
                    mCursor.getString(mCursor.getColumnIndex(WorkTimer.Columns.TASK_DESCRIPTION)),
                    mCursor.getInt(mCursor.getColumnIndex(WorkTimer.Columns.TASKS_SORTORDER)));

            holder.taskItem.setText(workTime.getName());
            holder.text2.setText(workTime.getDescription());
        }
        Log.d(TAG, "onBindViewHolder: " + mCursor);
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        //holder.mTextView.setText(mDataset[position]);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        if((mCursor == null) || (mCursor.getCount() == 0)) {
            return 1; // fib, because we populate a single ViewHolder with instructions
        } else {
            Log.d(TAG, "getItemCount: "+ mCursor.getCount());
            return mCursor.getCount();
        }
    }

    Cursor swapCursor(Cursor newCursor) {
        Log.d(TAG, "swapCursor: ");
        if (newCursor == mCursor) {
            return null;
        }

        int numItems = getItemCount();

        final Cursor oldCursor = mCursor;
        mCursor = newCursor;
        if(newCursor != null) {
            Log.d(TAG, "swapCursor: changed");
            // notify the observers about the new cursor
            notifyDataSetChanged();
        } else {
            Log.d(TAG, "swapCursor: unchanged");
            // notify the observers about the lack of a data set
            notifyItemRangeRemoved(0, numItems);
        }
        return oldCursor;

    }
}

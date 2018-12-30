package com.worktimer.worktimer;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.JobIntentService;
import android.util.Log;

public class LocationTrack extends JobIntentService {
    /**
     * Unique job ID for this service.
     */
    public static final String TAG = "LocationTrack";
    static final int JOB_ID = 1000;

    /**
     * Convenience method for enqueuing work in to this service.
     */
    /*
    static void enqueueWork(Context context, Intent work) {
        enqueueWork(context, SimpleJobIntentService.class, JOB_ID, work);
    }
*/
    @Override
    protected void onHandleWork(Intent intent) {
        Log.d(TAG, "onHandleWork: ");
        // We have received work to do.  The system or framework is already
        // holding a wake lock for us at this point, so we can just go.
        /*
        Log.i("SimpleJobIntentService", "Executing work: " + intent);
        String label = intent.getStringExtra("label");
        if (label == null) {
            label = intent.toString();
        }
        toast("Executing: " + label);
        for (int i = 0; i < 5; i++) {
            Log.i("SimpleJobIntentService", "Running service " + (i + 1)
                    + "/5 @ " + SystemClock.elapsedRealtime());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
        }
        Log.i("SimpleJobIntentService", "Completed service @ " + SystemClock.elapsedRealtime());
        */
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }


}
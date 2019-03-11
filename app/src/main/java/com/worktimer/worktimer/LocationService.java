package com.worktimer.worktimer;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.os.ResultReceiver;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.JobIntentService;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by 5943 6417 on 14-09-2016.
 */
public class LocationService extends JobIntentService {

    private static final String TAG = LocationService.class.getSimpleName();
    public static final String RECEIVER = "receiver";
    public static final int SHOW_RESULT = 123;
    /**
     * Result receiver object to send results
     */
    private ResultReceiver mResultReceiver;
    /**
     * Unique job ID for this service.
     */
    static final int DOWNLOAD_JOB_ID = 1000;
    /**
     * Actions download
     */
    private static final String ACTION_DOWNLOAD = "action.DOWNLOAD_DATA";

    private LocationManager locationManager = null;
    LocationListener locationListenerGPS = new LocationListener() {
        @Override
        public void onLocationChanged(android.location.Location location) {
            Log.d(TAG, "onLocationChanged: ");
            double latitude=location.getLatitude();
            double longitude=location.getLongitude();
            String msg="New Latitude: "+latitude + "New Longitude: "+longitude;

            Log.d(TAG, "onLocationChanged: " + msg);
            Intent intent = new Intent();
            intent.putExtra(RECEIVER, "workerResultReceiver");
            intent.setAction(ACTION_DOWNLOAD);
            enqueueWork(getApplicationContext(), LocationService.class, DOWNLOAD_JOB_ID, intent);
            //Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
            //Toast.makeText(mContext, "", Toast.LENGTH_SHORT).show();.makeText(mContext,msg,Toast.LENGTH_LONG).show();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    /**
     * Convenience method for enqueuing work in to this service.
     */
    public static void enqueueWork(Context context, Intent intent) {
        //Intent intent = new Intent(context, LocationService.class);
        intent.putExtra(RECEIVER, "workerResultReceiver");
        intent.setAction(ACTION_DOWNLOAD);
        enqueueWork(context, LocationService.class, DOWNLOAD_JOB_ID, intent);
    }

    /**/
    @SuppressLint("DefaultLocale")
    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        Log.d(TAG, "onHandleWork() called with: intent = [" + intent.toString() + "]");
        if (intent.getAction() != null) {

        }
        //Toast.makeText(getApplicationContext(), "msg", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: ");

        locationManager=(LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);

        if ( ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
/*
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        10);
            }
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
                ActivityCompat.requestPermissions(this,
                        new String[] { Manifest.permission.ACCESS_FINE_LOCATION },
                        11);
            }
            */
            Log.d(TAG, "onCreate: Permisions not granted");
        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 10, locationListenerGPS);

        }
    }
}
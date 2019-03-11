package com.worktimer.worktimer;

import android.Manifest;
import android.app.Service;
import android.app.job.JobService;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.v4.app.JobIntentService;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by 5943 6417 on 14-09-2016.
 */
public class LocationService extends JobIntentService
{
    public static final String TAG = "LocationListener";
    public static final String BROADCAST_ACTION = "Hello World";
    private static final int TWO_MINUTES = 1000 * 60 * 1;
    static final int JOB_ID = 1000;

    public LocationManager locationManager;

    Context context;
    Intent intent;
    int counter = 0;

    public LocationService() {
        super();
        Log.d(TAG, "LocationService: ");
    }

    LocationListener locationListenerGPS = new LocationListener() {
        @Override
        public void onLocationChanged(android.location.Location location) {
            Log.d(TAG, "onLocationChanged: ");
            double latitude=location.getLatitude();
            double longitude=location.getLongitude();
            String msg="New Latitude: "+latitude + "New Longitude: "+longitude;
            //Intent work = new Intent(context, LocationService.class);
            //enqueueWork(context, LocationService.class, JOB_ID, work);

            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
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
    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        Log.d(TAG, "onHandleWork: ");
    }
    public static void enqueueWork(Context context, Intent work) {
        enqueueWork(context, LocationService.class, JOB_ID, work);

    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: ");
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        //locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 4000, 0, listener);
        if ( ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 4000, 0, locationListenerGPS);
            Log.d(TAG, "onCreate: listener set");
        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 10, locationListenerGPS);
        }
    }

    @Override
    public void onStart(Intent intent, int startId) {
        Log.d(TAG, "onStart: ");

    }









}
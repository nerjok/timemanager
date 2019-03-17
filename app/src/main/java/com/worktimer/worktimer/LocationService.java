package com.worktimer.worktimer;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.app.job.JobService;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.JobIntentService;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;


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
            String msg="Service: New Latitude: "+latitude + "New Longitude: "+longitude;
            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
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
        Log.i(TAG, "onCreate: dfgdfgdf");
        /*
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if ( ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 4000, 0, locationListenerGPS);
            Log.d(TAG, "onCreate: listener set");
        } else {
            startForeground(121, getNotification());
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 10, locationListenerGPS);

        }
        /**/
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand: ");
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);


            Log.d(TAG, "onStart: " + intent.getAction().toString());
            if ( ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 4000, 0, locationListenerGPS);
                Log.d(TAG, "onCreate: listener set");
            } else {
                startForeground(121, getNotification());
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 10, locationListenerGPS);

            }
        if (intent.getAction().equals("StopService")) {
            Log.d(TAG, "onStart: stop");
            stopForeground(true);
            stopSelf();
            locationManager.removeUpdates(locationListenerGPS);
        }

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy: ");
        locationManager.removeUpdates(locationListenerGPS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            stopForeground(true); //true will remove notification
        }
    }

    private Notification getNotification() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("channel_01", "My Channel", NotificationManager.IMPORTANCE_DEFAULT);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);

            Notification.Builder builder = new Notification.Builder(getApplicationContext(), "channel_01")
                                                                    .setAutoCancel(true)
                                                                    .setStyle(new Notification.BigTextStyle().bigText("Application is listening for gps changes"))
                                                                    .setContentText("setContent Text")
                                                                    .setSmallIcon(R.drawable.ic_launcher_foreground);
            return builder.build();
        }
        return null;
    }







}
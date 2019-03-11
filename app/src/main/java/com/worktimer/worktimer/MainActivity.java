package com.worktimer.worktimer;

import android.Manifest;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.IBinder;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "Main2Activity";
    LocationManager locationManager;
    Context mContext;

    private AlertDialog mDialog = null;
    public AppDatabase mydb;

    /**
     * Test backgound services
     */
    private ServiceConnection serviceConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            String name = className.getClassName();
            if (name.endsWith("BackgroundService")) {
                Log.d(TAG, "onServiceConnected: ");
                //gpsService = ((BackgroundService.LocationServiceBinder) service).getService();
                //btnStartTracking.setEnabled(true);
                //txtStatus.setText("GPS Ready");
            }
        }

        public void onServiceDisconnected(ComponentName className) {
            if (className.getClassName().equals("BackgroundService")) {
                Log.d(TAG, "onServiceDisconnected: ");
                //gpsService = null;
            }
        }
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mydb = new AppDatabase(getBaseContext());

        mContext=this;


        locationManager=(LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);


        //Backgound gps

        final Intent intent = new Intent(this.getApplication(), BackgroundService.class);
        this.getApplication().bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
        /**/

        if ( ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

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
        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 10, locationListenerGPS);

        }

/***/

        final Button button = findViewById(R.id.button_main);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getBaseContext(), Main2Activity.class);
                i.putExtra("PersonID", 12);
                startActivity(i);
            }
        });
        final Button bcgGps = findViewById(R.id.bcgGps);
        bcgGps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if ( ContextCompat.checkSelfPermission( getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED ||
                        ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                    if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
                        ActivityCompat.requestPermissions((Activity) getApplicationContext(),
                                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                                10);
                    }
                    if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
                        ActivityCompat.requestPermissions((Activity) getApplicationContext(),
                                new String[] { Manifest.permission.ACCESS_FINE_LOCATION },
                                11);
                    }
                }

               // LocationService.enqueueWork(getApplicationContext(), getIntent());
            }
        });

        final Button cameraList =findViewById(R.id.camera_list);
        cameraList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getBaseContext(), CameraActivity.class);
                startActivity(i);

            }
        });
        final Button button_cmr = findViewById(R.id.camera22);
        button_cmr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getBaseContext(), AndroidCameraApi.class);
                startActivity(i);

            }
        });

       Button  btnScanBarcode = findViewById(R.id.btnScanBarcode);
       btnScanBarcode.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               startActivity(new Intent(MainActivity.this, ScannedBarcodeActivity.class));
           }
       });
    }



    LocationListener locationListenerGPS = new LocationListener() {
        @Override
        public void onLocationChanged(android.location.Location location) {
            Log.d(TAG, "onLocationChanged: ");
            double latitude=location.getLatitude();
            double longitude=location.getLongitude();
            String msg="New Latitude: "+latitude + "New Longitude: "+longitude;

            Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {
            case R.id.nex_window:
                startActivity(new Intent(this, Main2Activity.class));
                break;
            case R.id.about:
                showAboutDialog();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void showAboutDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View messageView = getLayoutInflater().inflate(R.layout.about, null, false);


        builder.setTitle("Work Timer");
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setView(messageView);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                Log.d(TAG, "onClick: Entering messageView.onClick, showing = " + mDialog.isShowing());
                if(mDialog != null && mDialog.isShowing()) {
                    mDialog.dismiss();
                }
            }
        });


        mDialog = builder.create();
        mDialog.setCanceledOnTouchOutside(true);

        mDialog.show();


    }


    private void isLocationEnabled() {

        if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            AlertDialog.Builder alertDialog=new AlertDialog.Builder(mContext);
            alertDialog.setTitle("Enable Location");
            alertDialog.setMessage("Your locations setting is not enabled. Please enabled it in settings menu.");
            alertDialog.setPositiveButton("Location Settings", new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dialog, int which){
                    Intent intent=new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(intent);
                }
            });
            alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dialog, int which){
                    dialog.cancel();
                }
            });
            AlertDialog alert=alertDialog.create();
            alert.show();
        }
        else{
            AlertDialog.Builder alertDialog=new AlertDialog.Builder(mContext);
            alertDialog.setTitle("Confirm Location");
            alertDialog.setMessage("Your Location is enabled, please enjoy");
            alertDialog.setNegativeButton("Back to interface",new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dialog, int which){
                    dialog.cancel();
                }
            });
            AlertDialog alert=alertDialog.create();
            alert.show();
        }
    }

}

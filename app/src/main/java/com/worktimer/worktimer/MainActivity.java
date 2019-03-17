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
    Context mContext;

    private AlertDialog mDialog = null;
    public AppDatabase mydb;
    private String gpsStatus = "StartService";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mydb = new AppDatabase(getBaseContext());
        mContext=this;


        //Backgound gps
        /*
        Intent startIntent = new Intent(getApplicationContext(), LocationService.class);
        startIntent.setAction("StartService");
        ContextCompat.startForegroundService(this, startIntent);

/*
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
            Intent startIntent = new Intent(getApplicationContext(), LocationService.class);
            startIntent.setAction("StartService");
            ContextCompat.startForegroundService(this, startIntent);
        }


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
                if (gpsStatus == "StartService") {
                    gpsStatus = "StopService";
                } else
                    gpsStatus = "StartService";
                Log.d(TAG, "onClick: " + gpsStatus);
                Intent stopIntent = new Intent(getApplicationContext(), LocationService.class);
                stopIntent.setAction(gpsStatus);
                ContextCompat.startForegroundService(getApplicationContext(), stopIntent);
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

}

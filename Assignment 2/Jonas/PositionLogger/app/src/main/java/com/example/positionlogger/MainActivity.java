package com.example.positionlogger;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements ServiceConnection {

    static private final String TAG = MainActivity.class.getCanonicalName();

    /**Stuff for Activity-Service Connection*/
    //private LocationManager locationManager;
    //private LocationService locationService;
    //private boolean bound = false;

    static private final int MY_PERMISSION_REQUEST_FINE_LOCATION = 1;

    private TextView tvLong, tvLat, tvDist, tvSpeed;

    Button btStart, btExit, btStop, btUpdate;

    private ILocationReader locationServerProxy = null;

    private double lat, lon, distance, speed;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i(TAG, "MainActivity created");

        View layout = findViewById(R.id.main_layout_id);
        final Snackbar snackbarStart = Snackbar.make(layout,"Service started",Snackbar.LENGTH_LONG);
        final Snackbar snackbarStop = Snackbar.make(layout,"Service stopped",Snackbar.LENGTH_LONG);

        tvLong = findViewById(R.id.tvLon);
        tvLat = findViewById(R.id.tvLat);
        tvDist = findViewById(R.id.tvDist);
        tvSpeed = findViewById(R.id.tvSpeed);

        btStart = findViewById(R.id.btStart);
        btExit = findViewById(R.id.btExit);
        btStop = findViewById(R.id.btStop);
        btUpdate = findViewById(R.id.btUpdate);

        btStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Service started");
                Intent i = new Intent(MainActivity.this, LocationService.class);
                bindService(i, MainActivity.this, BIND_AUTO_CREATE);

                snackbarStart.show();
            }
        });

        btExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "MainActivity destroyed");
                if(locationServerProxy != null) {
                    unbindService(MainActivity.this);
                }
                MainActivity.this.finishAndRemoveTask();
            }

        });

        btStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationServerProxy = null;
                unbindService(MainActivity.this);
                Log.i(TAG,"Service disconnected");
                snackbarStop.show();
            }
        });

        btUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(locationServerProxy != null) {
                                    displayLocationData();
                                }
                            }
                        });

                    }


        });
    }

    @Override
    protected void onStart() {
        super.onStart();


       //locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        Log.i(TAG, "Service connected");
        locationServerProxy = ILocationReader.Stub.asInterface(service);

        //display the data every 100 milliseconds
        new Timer().scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(locationServerProxy != null) {
                            displayLocationData();
                        }
                    }
                });

            }
        },0,1000);
    }

    public void displayLocationData()  {
        try {
            lat = locationServerProxy.getLatitude();
            lon = locationServerProxy.getLongitude();
            distance = locationServerProxy.getDistance();
            speed = locationServerProxy.getAverageSpeed();

            tvLat.setText("" + lat);
            tvLong.setText(""+lon);
            tvDist.setText(""+(int) distance+" m");
            tvSpeed.setText(""+(int) speed+" km/h");

        } catch (RemoteException e) {
            Log.e(TAG, "Failed to get sensor data");
            e.printStackTrace();
        }
    }
    @Override
    public void onServiceDisconnected(ComponentName name) {
        Log.i(TAG,"Service disconnected");
        locationServerProxy = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "MainActivity destroyed");
        if(locationServerProxy != null) {
            unbindService(this);
        }
    }

    //Unused
    public void requestLocationPermission() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                MY_PERMISSION_REQUEST_FINE_LOCATION);
    }

    //Unused
    public void enableLocationSettings() {
        new AlertDialog.Builder(this)
                .setTitle("Enable GPS")
                .setMessage("GPS currently disabled. Do you want to enable GPS?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(settingsIntent);
                    }
                })
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
/**Stuff for Activity-Service Communication**/
    //    @Override
//    protected void onStop() {
//        super.onStop();
//        Log.i(TAG, "MainActivity stopped");
//        // Unbind from service
//        if (bound) {
//            locationService.setCallBacks(null); // unregister
//            unbindService(serviceConnection);
//            bound = false;
//        }
//    }
//
//    /** Callbacks for service binding, passed to bindService() */
//    private ServiceConnection serviceConnection = new ServiceConnection() {
//
//        @Override
//        public void onServiceConnected(ComponentName className, IBinder service) {
//            // cast the IBinder and get MyService instance
//            LocationService.LocalBinder binder = (LocationService.LocalBinder) service;
//            locationService = binder.getService();
//            bound = true;
//            locationService.setCallBacks(MainActivity.this); // register
//            Log.i(TAG, "Service connected");
//
//            //display the data every 100 milliseconds
//            new Timer().scheduleAtFixedRate(new TimerTask() {
//
//                @Override
//                public void run() {
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                        displayLocationData();
//                    }
//                });
//
//            }
//        },0,100);
//        }
//
//        @Override
//        public void onServiceDisconnected(ComponentName arg0) {
//            Log.i(TAG,"Service disconnected");
//            bound = false;
//        }
//    };


//        if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
//            enableLocationSettings();
//        } else {
//            //GPS is enabled.
//            if(Build.VERSION.SDK_INT >= 23 &&
//                    ContextCompat.checkSelfPermission(this,
//                            Manifest.permission.ACCESS_FINE_LOCATION) !=
//                            PackageManager.PERMISSION_GRANTED) {
//                requestLocationPermission();
//            } else {
//                //Permission to access fine-granted positions has been granted.
//                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTime,
//                      minDistance, (LocationListener) this);//might not work
//            }
//        }

//    }
}

package com.example.positionlogger;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.provider.Settings;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.Calendar;
import java.util.Date;

/**
 * This implements a Service which reads location data
 * @author Jonas Allali (2965826), Julian Blumenröther (2985877), Jena Satkunarajan (2965839)
 */
public class LocationService extends Service implements LocationListener {
    public static String TAG = LocationService.class.getCanonicalName();

    private Location location;
    private double lastLat;
    private double lastLon;
    private double startTime;


    //Minimum time between location updates [ms]
    static private final long minTime = 1000;
    //Minimum distance between location updates [m]
    static private final float minDistance = 10;

    static private final int MY_PERMISSION_REQUEST_FINE_LOCATION = 1;

    private LocationManager locationManager;
    private double lat, lon;
    private double distance = 0; //in m
    private double speed = 0; //in km/h

    /**Stuff for Activity-Service Communication*/
//    //Binder given to clients
//    private final IBinder binder = new LocalBinder();
//    //Registered callbacks
//    private ServiceCallbacks serviceCallbacks;
//
//    // Class used for the client Binder.
//    public class LocalBinder extends Binder {
//        LocationService getService() {
//            // Return this instance of MyService so clients can call public methods
//            return LocationService.this;
//        }
//    }

    private LocationServiceImpl impl;

    private class LocationServiceImpl extends ILocationReader.Stub {

        @Override
        public double getLatitude() throws RemoteException {
            return lat;
        }

        @Override
        public double getLongitude() throws RemoteException {
            return lon;
        }

        @Override
        public double getDistance() throws RemoteException {
            return distance;
        }

        @Override
        public double getAverageSpeed() throws RemoteException {
            return speed;
        }
    }

    @Override
    public void onCreate() {
        Log.i(TAG,"Creating service");
        super.onCreate();
        impl = new LocationServiceImpl();
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

    }
    @Override
    public void onLocationChanged(Location location) {
        if(this.location != null) {
            distance = (distance + location.distanceTo(this.location));
        }
        lon = location.getLongitude();
        lat = location.getLatitude();
        this.location = location;
        double overallTime = (Calendar.getInstance().getTime().getTime() - startTime) / 1000 / 60 / 60;//in hours
        speed =  (distance / 1000) / overallTime;
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

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "Binding service");

        /**Check if location services are activated on smartphone and check for location permissions**/
        if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            //Call method of Mainactivity
            //serviceCallbacks.enableLocationSettings();
        } else {
            //GPS is enabled.
            if(Build.VERSION.SDK_INT >= 23 &&
                    ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION) !=
                            PackageManager.PERMISSION_GRANTED) {
                //Call method from Mainactivity
                //Mainactivity.requestLocationPermission();
            } else {
                //Permission to access fine-granted positions has been granted.
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTime,
                        minDistance,  this);
            }
        }
        startTime = Calendar.getInstance().getTime().getTime();
        //location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        return impl;
    }

    //unused
//    public void setCallBacks(ServiceCallbacks callbacks) {
//        serviceCallbacks = callbacks;
//    }


    @Override
    public void onDestroy() {
        locationManager.removeUpdates(this);
        Log.i(TAG, "Destroying service");
        super.onDestroy();
    }

}

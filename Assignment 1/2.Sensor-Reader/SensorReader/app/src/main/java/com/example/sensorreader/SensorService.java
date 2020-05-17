package com.example.sensorreader;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class SensorService extends Service implements SensorEventListener {
    public static String TAG = SensorService.class.getCanonicalName();

    private SensorServiceImpl impl;

    final private int period = 100000;

    private SensorManager sensorManager;
    private Sensor accSensor;
    private Sensor gyroSensor;
    private boolean accSensorListenerRegistered = false;
    private boolean gyroSensorListenerRegistered = false;
    private boolean sensorListenerRegistered = false;

    private float accX, accY, accZ, gyroX, gyroY,gyroZ;

    private class SensorServiceImpl extends ISensorReader.Stub {

        @Override
        public float[] readData() throws RemoteException {
            return new float[]{accX,accY,accZ, gyroX, gyroY,gyroZ};
        }
    }

    @Override
    public void onCreate() {
        Log.i(TAG, "Creating service");
        super.onCreate();
        impl = new SensorServiceImpl();

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        gyroSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            accX = event.values[0];
            accY = event.values[1];
            accZ = event.values[2];
        } else if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
            gyroX = event.values[0];
            gyroY = event.values[1];
            gyroZ = event.values[2];
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }



    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "Binding service");
        if (sensorManager.registerListener(this, accSensor, period)) {
            Log.i(TAG, "Registered accSensor listener");
            accSensorListenerRegistered = true;
            sensorListenerRegistered = true;

        } else {
            Log.e(TAG, "Could not register accSensor listener");
        }

        if (sensorManager.registerListener(this, gyroSensor, period)) {
            Log.i(TAG, "Registered gyroSensor listener");
            gyroSensorListenerRegistered = true;
            sensorListenerRegistered = true;
        } else {
            Log.e(TAG, "Could not register gyroSensor listener");
        }
        return impl;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "Starting service");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        if(sensorListenerRegistered) {
            Log.i(TAG, "Unregistered sensor listener");
            sensorManager.unregisterListener(this);
        }
        Log.i(TAG, "Destroying service");
        super.onDestroy();
    }
}

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

/**
 * This implements a Service which reads sensor data
 * @author Jonas Allali (2965826), Julian Blumenröther (2985877), Jena Satkunarajan (2965839)
 */
public class SensorService extends Service implements SensorEventListener {
    public static String TAG = SensorService.class.getCanonicalName();

    private SensorServiceImpl impl;
    private SensorManager sensorManager;
    private Sensor accSensor;
    private Sensor gyroSensor;

    private boolean accSensorListenerRegistered = false;
    private boolean gyroSensorListenerRegistered = false;

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
    /*
    retrieve new sensor data
     */
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
    //unused
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "Binding service");
        int period = 100000;
        if (sensorManager.registerListener(this, accSensor, period)) {
            Log.i(TAG, "Registered accSensor listener");
            accSensorListenerRegistered = true;

        } else {
            Log.e(TAG, "Could not register accSensor listener");
        }

        if (sensorManager.registerListener(this, gyroSensor, period)) {
            Log.i(TAG, "Registered gyroSensor listener");
            gyroSensorListenerRegistered = true;
        } else {
            Log.e(TAG, "Could not register gyroSensor listener");
        }
        return impl;
    }

    @Override
    public void onDestroy() {
        if(accSensorListenerRegistered) {
            Log.i(TAG, "Unregistered accSensor listener");
            sensorManager.unregisterListener(this,accSensor);
        }

        if(gyroSensorListenerRegistered) {
            Log.i(TAG, "Unregistered gyroSensor listener");
            sensorManager.unregisterListener(this,gyroSensor);
        }

        Log.i(TAG, "Destroying service");
        super.onDestroy();
    }
}

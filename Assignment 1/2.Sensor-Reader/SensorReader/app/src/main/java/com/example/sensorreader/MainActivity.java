package com.example.sensorreader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements ServiceConnection {

    final private String TAG = MainActivity.class.getCanonicalName();
    private ISensorReader sensorServerProxy = null;
    TextView tvAccX, tvAccY, tvAccZ, tvGyroX, tvGyroY, tvGyroZ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvAccX = findViewById(R.id.accX);
        tvAccY = findViewById(R.id.accY);
        tvAccZ = findViewById(R.id.accZ);

        Intent i = new Intent(this, SensorService.class);
        bindService(i, this, BIND_AUTO_CREATE);


    }




    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        Log.i(TAG, "Service connected");
        sensorServerProxy = ISensorReader.Stub.asInterface(iBinder);
        float[] data = null;
        try {
            data = sensorServerProxy.readData();
            tvAccX.setText("" + data[0]);
            tvAccY.setText("" + data[1]);
            tvAccZ.setText("" + data[2]);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        Log.i(TAG,"Service disconnected");
        sensorServerProxy = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(sensorServerProxy != null) {
            unbindService(this);
        }
    }
}

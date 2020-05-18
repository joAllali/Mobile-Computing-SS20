package com.example.sensorreader;

import androidx.appcompat.app.AppCompatActivity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.widget.TextView;
import java.util.Timer;
import java.util.TimerTask;
/**
 *This implements a client who retrieves sensor data from a service and displays it
 * @author Jonas Allali (2965826), Julian Blumenröther (2985877), Jena Satkunarajan (2965839)
 */
public class MainActivity extends AppCompatActivity implements ServiceConnection {

    final private String TAG = MainActivity.class.getCanonicalName();
    private ISensorReader sensorServerProxy = null;
    TextView tvAccX, tvAccY, tvAccZ, tvGyroX, tvGyroY, tvGyroZ;
    float[] data = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvAccX = findViewById(R.id.accX);
        tvAccY = findViewById(R.id.accY);
        tvAccZ = findViewById(R.id.accZ);
        tvGyroX = findViewById(R.id.gyroX);
        tvGyroY = findViewById(R.id.gyroY);
        tvGyroZ = findViewById(R.id.gyroZ);

        Intent i = new Intent(this, SensorService.class);
        bindService(i, this, BIND_AUTO_CREATE);
    }

    public void displaySensorData()  {
        try {
            data = sensorServerProxy.readData();
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        if(data!=null) {
            tvAccX.setText("" + data[0]);
            tvAccY.setText("" + data[1]);
            tvAccZ.setText("" + data[2]);
            tvGyroX.setText(""+ data[3]);
            tvGyroY.setText(""+ data[4]);
            tvGyroZ.setText(""+ data[5]);
        } else {
            Log.e(TAG, "Failed to read sensor data");
        }
    }


    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        Log.i(TAG, "Service connected");

        sensorServerProxy = ISensorReader.Stub.asInterface(iBinder);

            //display the data every 100 milliseconds
            new Timer().scheduleAtFixedRate(new TimerTask() {

                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            displaySensorData();
                        }
                    });

                }
            },0,100);

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

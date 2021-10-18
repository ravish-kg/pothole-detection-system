package com.example.ravish.pothole_detection;

import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Ravish on 2/28/2017.
 */
public class service extends Service implements SensorEventListener,LocationListener {

    private SensorManager sensorManager;
    double lat;
    double lon;
    double ax, ay, az;
    private double mAccel;
    private double mAccelCurrent;
    private double mAccelLast;

    private long lastUpdate = 0;
    private float last_x, last_y, last_z;
    private static final int SHAKE_THRESHOLD = 300;

    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.d("max","onStartCommand");
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);

        return START_STICKY;
    }

    public void stopSensor(){
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        Log.d("max", "onSensorChanged");
        /*
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            ax = event.values[0];
            ay = event.values[1];
            az = event.values[2];
            mAccelLast = mAccelCurrent;
            mAccelCurrent = Math.sqrt(ax * ax + ay * ay + az * az);
            double delta = mAccelCurrent - mAccelLast;
            mAccel = mAccel * 0.9f + delta;


            Log.d("max"," "+ax+" "+ay+" "+az);
            int temp = compare((int) ax, (int) ay, (int) az);

            if (temp == 0) {
                //orientation x
                Log.d("max","X orientation");
                Log.d("max",""+(mAccelLast-mAccelCurrent));
                if ((mAccelLast - mAccelCurrent) > 7) {
                    Toast.makeText(this, "pothole x", Toast.LENGTH_SHORT).show();
                    Log.d("max", "pothole x");

                    //sendLocationData(lat, lon);
                }
            } else if (temp == 1) {
                //orientation y
                Log.d("max","y orientation");
                Log.d("max",""+(mAccelLast-mAccelCurrent));
                if ((mAccelLast - mAccelCurrent) > 7) {
                    Toast.makeText(this, "pothole y", Toast.LENGTH_SHORT).show();
                    Log.d("max", "pothole y");

                    //sendLocationData(lat, lon);

                }
            } else if (temp == 2) {
                //orientation z
                Log.d("max","z orientation");
                Log.d("max","cur:"+mAccelCurrent+"      last:"+mAccelLast);
                if ((mAccelLast - mAccelCurrent) > 7) {
                    Toast.makeText(this, "pothole z", Toast.LENGTH_SHORT).show();
                    Log.d("max",""+(mAccelLast-mAccelCurrent));
                    Log.d("max", "pothole z");

                    //sendLocationData(lat, lon);

                }
            }

        }*/

        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            long curTime = System.currentTimeMillis();

            if ((curTime - lastUpdate) > 100) {
                long diffTime = (curTime - lastUpdate);
                lastUpdate = curTime;

                float speed = Math.abs(x + y + z - last_x - last_y - last_z)/ diffTime * 10000;

                if (speed > SHAKE_THRESHOLD) {
                    Toast.makeText(this, "Pothole Detected", Toast.LENGTH_SHORT).show();
                }

                last_x = x;
                last_y = y;
                last_z = z;
            }
        }

    }

    private int compare(int ax, int ay, int az) {
        ax = Math.abs(ax);
        ay = Math.abs(ay);
        az = Math.abs(az);
        if (ax > ay) {
            if (ax > az) return 0;
        } else if (ay > az) return 1;
        else return 2;

        return -1;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onLocationChanged(Location location) {

        Log.d("max", "in Service onLocationChanged");

        lat = location.getLatitude();
        lon = location.getLongitude();
        Toast.makeText(this , "latitude"+lat+"\nlongitude"+lon,Toast.LENGTH_LONG).show();
//        LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
        Log.d("max", "end in Service onLocationChanged");

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
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}


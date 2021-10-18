package com.example.ravish.pothole_detection;

import android.app.Activity;
import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

/**
 * Created by Ravish on 2/23/2017.
 */
public class splash extends Activity {

    private Handler handler = new Handler() {

        public void handleMessage(Notification.MessagingStyle.Message msg) {
            startActivity(new Intent(splash.this, MainActivity.class));
            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.splash_activity);

        handler.sendEmptyMessageDelayed(1000,1000);

    }

}

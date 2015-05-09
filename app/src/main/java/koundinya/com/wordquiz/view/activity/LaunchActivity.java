package koundinya.com.wordquiz.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

import koundinya.com.wordquiz.R;

public class LaunchActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        new Timer().schedule(new TimerTask(){
            public void run() {
                LaunchActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        startActivity(new Intent(LaunchActivity.this, GameActivity.class));
                    }
                });
            }
        }, 2000);

    }

}

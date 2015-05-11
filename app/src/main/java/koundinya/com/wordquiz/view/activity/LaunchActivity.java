package koundinya.com.wordquiz.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

import koundinya.com.wordquiz.R;
import koundinya.com.wordquiz.model.CurrentUser;

public class LaunchActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);


        final Class launchClass =
                CurrentUser.getInstance(this).newUser() ? IndexActivity.class : GameActivity.class;


        new Timer().schedule(new TimerTask(){
            public void run() {
                LaunchActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        startActivity(new Intent(LaunchActivity.this, launchClass));
                    }
                });
            }
        }, 2000);


    }




}

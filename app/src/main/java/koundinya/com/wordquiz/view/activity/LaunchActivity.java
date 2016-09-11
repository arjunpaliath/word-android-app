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

        final Class launchClass;
        final int delay;

        if(CurrentUser.getInstance(this).newUser()){

            launchClass = IndexActivity.class;
            delay = 300;

        }else{

            launchClass = GameActivity.class;
            delay = 300;
        }

        new Timer().schedule(new TimerTask(){
            public void run() {
                LaunchActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        Intent startIntent = new Intent(LaunchActivity.this, launchClass);
                        startIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(startIntent);
                    }
                });
            }
        }, delay);
    }
}

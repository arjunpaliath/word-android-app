package koundinya.com.wordquiz.view.activity;

import android.app.Activity;
import android.os.Bundle;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import koundinya.com.wordquiz.R;

public class GameActivity extends Activity {

    Firebase myFirebaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Firebase.setAndroidContext(this);

        setupFireBase();
    }



    public void setupFireBase() {

        myFirebaseRef = new Firebase("https://wordapp1.firebaseio.com/questions/");

        myFirebaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                System.out.println(snapshot.getValue());

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });


    }

}

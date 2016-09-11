package koundinya.com.wordquiz.model;

import android.content.Context;
import android.content.SharedPreferences;

import com.firebase.client.Firebase;

import koundinya.com.wordquiz.Util.UtilMethods;

/**
 * Created by girishk on 11/05/15.
 */
public class CurrentUser {

    public static User current;
    static SharedPreferences prefs = null;
    private static Context mContext;
    private static CurrentUser instance = new CurrentUser();
    private CurrentUser(){}

    public static CurrentUser getInstance(Context context){
        mContext = context;
        prefs = context.getSharedPreferences("wordpref", mContext.MODE_PRIVATE);

        return instance;
    }

    public String getScore(){
        return Integer.toString(current.score);
    }

    public String getName(){return current.name;}

    public  boolean newUser(){

        String value;
        SharedPreferences.Editor editor = prefs.edit();


        if((value = prefs.getString("username", null)) == null){

            current = new User();
            current.name  = UtilMethods.randomUserName(mContext,UtilMethods.generateRandomInt(2,500));
            current.score = 0;

            editor.putString("username", current.name);
            editor.putInt("score", current.score);
            editor.apply();

            return true;
        }

        current = new User();
        current.name = prefs.getString("username",null);
        current.score = prefs.getInt("score",-1);

        return false;
    }

    public void correctAnswer(boolean correct) {

        SharedPreferences.Editor editor = prefs.edit();

        current.score = correct ?  current.score + 2 : current.score - 1 ;

        editor.putInt("score", current.score);
        editor.apply();

        updateFirebase();

    }


    private void updateFirebase(){

        Firebase.setAndroidContext(mContext);

        Firebase myFirebaseRef = new Firebase("https://wordapp1.firebaseio.com/");

        myFirebaseRef.child("users").child(current.name).child("score").setValue(current.score);
        myFirebaseRef.child("scores").child(current.name).setValue(current.score);

    }
}

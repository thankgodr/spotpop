package com.shixxels.thankgodrichard.spotpopfinal;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.google.gson.Gson;
import com.quickblox.users.model.QBUser;


public class SplashscreenActivity extends AppCompatActivity {
   Helpers app = Helpers.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Force fullscreen on
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        app.fetchSpots(this);
        setContentView(R.layout.activity_splashscreen);






        /****** Create Thread that will sleep for 5 seconds  before the application interface comes up*************/
        Thread background = new Thread() {
            public void run() {

                try {
                    // Thread will sleep for 5 seconds
                    sleep(5*1000);



                        // After 5 seconds redirect to another intent


                    SharedPreferences preference = getSharedPreferences(app.Pref, MODE_PRIVATE);
                    String jsonString = preference.getString("Spotpop","");
                    String loginString = preference.getString("login","");

                    Log.i(LoginActivity.TAG,jsonString);
                    if(jsonString.length() > 1 && loginString.length() > 1){
                        Gson gson = new Gson();
                        QBUser user = gson.fromJson(jsonString,QBUser.class);
                        String[] login = gson.fromJson(loginString,String[].class);

                        Intent i=new Intent(getBaseContext(),MainActivity.class);
                        i.putExtra("info",user);
                        i.putExtra("login",login);
                        startActivity(i);
                    }
                    else{
                        Intent i=new Intent(getBaseContext(),LoginActivity.class);
                        startActivity(i);
                    }







                    //Remove activity
                    finish();

                } catch (Exception e) {
                    Log.e("ter", String.valueOf(e));
                }
            }
        };

        // start thread
        background.start();


    }



}

package com.shixxels.thankgodrichard.spotpopfinal;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;

//Implementing the interface OnTabSelectedListener to our MainActivity
//This interface would help in swiping views
public class LoginActivity extends AppCompatActivity{

    Fragment fragment;
    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);


        findViewById(R.id.text_join).setOnClickListener(new View.OnClickListener(){
            public void onClick(View arg0) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        fragment = new RegisterTab();
                        transaction.replace(R.id.idme,fragment);
                        transaction.commit();

                    }
                }).start();
            }
        });


    }





}
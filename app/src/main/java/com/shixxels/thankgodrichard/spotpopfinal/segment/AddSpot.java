package com.shixxels.thankgodrichard.spotpopfinal.segment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.shixxels.thankgodrichard.spotpopfinal.LoginActivity;
import com.shixxels.thankgodrichard.spotpopfinal.MainActivity;
import com.shixxels.thankgodrichard.spotpopfinal.R;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 */
public class AddSpot extends AppCompatActivity implements View.OnClickListener{
    Toolbar toolbar;
    Button passwordprotect,slow,medium,fast,good4work,poweoutlet;
    Boolean passStr,good4workStr,powerOutletStr;
    int speed = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        forceFullscreenOn();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.segment_add_spot);
        initToolBar();

        //Set click listerner on all buttons
        passwordprotect = (Button) findViewById(R.id.password_protected);
        passwordprotect.setOnClickListener(this);
        passStr = false;
        slow = (Button) findViewById(R.id.slow_selected);
        slow.setOnClickListener(this);
        fast = (Button) findViewById(R.id.fast_select);
        fast.setOnClickListener(this);
        medium = (Button) findViewById(R.id.medium_select);
        medium.setOnClickListener(this);
        good4work = (Button) findViewById(R.id.good4work);
        good4work.setOnClickListener(this);
        poweoutlet = (Button) findViewById(R.id.power_outlet);
        poweoutlet.setOnClickListener(this);
        currentButton(speed);





    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.password_protected:
                Log.i(LoginActivity.TAG,"password proted clicked");
                if(passStr == false){
                    passwordprotect.setBackgroundColor(getResources().getColor(R.color.button_selected));
                    passwordprotect.setTextColor(getResources().getColor(R.color.white));
                    passStr = true;
                }
                else {
                    passwordprotect.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_custom));
                    passwordprotect.setTextColor(getResources().getColor(R.color.btn_text_color));
                    passStr = false;

                }
                break;

            case R.id.slow_selected:
                Log.i(LoginActivity.TAG,"slow  clicked");
                if( speed == 1){
                    speed = 1;
                    currentButton(speed);
                }
                else if(speed == 2){
                    speed = 1;
                    currentButton(speed);
                }
                else if (speed == 3){
                    speed = 1;
                    currentButton(speed);
                }

                break;

            case R.id.medium_select:
                Log.i(LoginActivity.TAG,"medium  clicked");
              if( speed == 1){
                    speed = 2;
                  currentButton(speed);
                }
                else if(speed == 2){
                    speed = 2;
                  currentButton(speed);
                }
                else if (speed == 3){
                    speed = 2;
                  currentButton(speed);
                }
                break;
            case R.id.fast_select:
                Log.i(LoginActivity.TAG,"fast  clicked");
                if( speed == 1){
                    speed = 3;
                    currentButton(speed);
                }
                else if(speed == 2){
                    speed = 3;
                    currentButton(speed);
                }
                else if (speed == 3){
                    speed = 3;
                    currentButton(speed);
                }
                break;
            case R.id.good4work:
                Log.i(LoginActivity.TAG,"goodwork  clicked");
                break;
            case R.id.power_outlet:
                Log.i(LoginActivity.TAG,"power  clicked");
                break;

            default:
                break;
        }

    }


    public void initToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
    }







    private void forceFullscreenOn() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
    }
    private void buttonHanler(Button... args){
      for(Button arg : args){
          Toast.makeText(this,String.valueOf(arg.getId()),Toast.LENGTH_SHORT).show();
      }
    }

    //Handle back button
    public void onBackPressed()
    {
        AddSpot.this.finish();
        return;
    }
    private void currentButton(int i){
        if(i == 1){
            slow.setBackgroundColor(getResources().getColor(R.color.button_selected));
            slow.setTextColor(getResources().getColor(R.color.white));

            fast.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_custom));
            fast.setTextColor(getResources().getColor(R.color.btn_text_color));

            medium.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_custom));
            medium.setTextColor(getResources().getColor(R.color.btn_text_color));

        }
        else if(i == 2){
            medium.setBackgroundColor(getResources().getColor(R.color.button_selected));
            medium.setTextColor(getResources().getColor(R.color.white));

            slow.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_custom));
            slow.setTextColor(getResources().getColor(R.color.btn_text_color));

            fast.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_custom));
            fast.setTextColor(getResources().getColor(R.color.btn_text_color));


        }
        else if(i == 3){
            fast.setBackgroundColor(getResources().getColor(R.color.button_selected));
            fast.setTextColor(getResources().getColor(R.color.white));

            slow.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_custom));
            slow.setTextColor(getResources().getColor(R.color.btn_text_color));

            medium.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_custom));
            medium.setTextColor(getResources().getColor(R.color.btn_text_color));

        }
    }



}



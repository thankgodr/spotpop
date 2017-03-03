package com.shixxels.thankgodrichard.spotpopfinal;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.quickblox.auth.model.QBProvider;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.exception.QBResponseException;
import com.quickblox.users.QBUsers;
import com.quickblox.users.model.QBUser;


//Implementing the interface OnTabSelectedListener to our MainActivity
//This interface would help in swiping views
public class LoginActivity extends AppCompatActivity{

    // init All view child
    EditText username;
    EditText password;
    ImageButton loginBtn;
    TextView error;
    LoginButton loginfb;
    private CallbackManager callbackManager;
    TextView forgotPassword;
    public static  final  String TAG = "burn";


    //get instance o my helper class
    Helpers helpers = Helpers.getInstance();







    @Override
    protected void onCreate(Bundle savedInstanceState) {


        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();


        //Force fullscreen on
        forceFullscreenOn();


        //Initialize our helper class
        helpers.initQuickBlox(this);

        //start interface
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);


        //Declare all buttons and eddit text
        boolean status = false;
        boolean st1,st2,st3,st4;
        username = (EditText) findViewById(R.id.login_username);
        password = (EditText) findViewById(R.id.login_password);
        loginBtn= (ImageButton) findViewById(R.id.login_btn);
        error = (TextView) findViewById(R.id.login_show_info);
        loginfb = (LoginButton) findViewById(R.id.facebook_login);
        forgotPassword = (TextView) findViewById(R.id.forgot_password);


        //hadles the facebook login
        loginfb.registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        error.setText(R.string.please_wait);
                        final String facebookAccessToken = loginResult.getAccessToken().getToken();
                        Log.i(TAG,facebookAccessToken);

                        QBUsers.signInUsingSocialProvider(QBProvider.FACEBOOK, facebookAccessToken, null, new QBEntityCallback<QBUser>() {
                            @Override
                            public void onSuccess(QBUser user, Bundle args) {
                                //TODO start new activity after facebook login
                                helpers.addtoList(user.getId()+"");
                                helpers.followersGet(user.getId()+"",getApplicationContext());
                                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                                intent.putExtra("info", user);
                                intent.putExtra("login",new String[]{facebookAccessToken});
                                startActivity(intent);
                            }

                            @Override
                            public void onError(QBResponseException errors) {
                                //TODO start if error happens
                                Log.i(TAG, "Error on qb signin");
                            }
                        });

                    }

                    @Override
                    public void onCancel() {
                        error.setText(R.string.facebook_canceled);
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        error.setText(R.string.facebook_error);
                    }
                });

        //hanle the Forgot password
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, Forgotpassword.class));
            }
        });



        //Set Onclick Listener for join now text in a new thread
        findViewById(R.id.text_join).setOnClickListener(new View.OnClickListener(){
            public void onClick(View arg0) {

                Fragment fragment;
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                fragment = new RegisterTab();
                        transaction.replace(R.id.idme,fragment);
                        transaction.addToBackStack(null);
                        transaction.commit();

            }

        });



        //Set on click listener for the submit button and perform submit
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(helpers.isInternetAvailable(getApplicationContext())){
                    String usern = username.getText().toString();
                    String pw = password.getText().toString();
                    if(usern.length() > 0){
                        if(pw.length() > 0){
                            error.setVisibility(View.VISIBLE);
                            error.setText(R.string.please_wait);
                            helpers.SignIn(usern,pw,error,getApplicationContext());
                            Log.i(TAG,usern + " " + pw);
                        }
                        else {
                            error.setVisibility(View.VISIBLE);
                            error.setText(R.string.login_password_error);
                        }
                    }
                    else {
                        error.setVisibility(View.VISIBLE);
                        error.setText(R.string.error_invalid_username);
                    }
                }
                else {
                    error.setVisibility(View.VISIBLE);
                    error.setText(R.string.internet_connection_error);
                }



            }
        });



    }

    public void forceFullscreenOn() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
    }


    @Override
     protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }


}
package com.shixxels.thankgodrichard.spotpopfinal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
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


/**
 * Created by thankgodrichard on 9/6/16.
 */
public class RegisterTab extends Fragment {

    ImageButton btnSubmit;
    EditText email;
    EditText username;
    EditText password;
    EditText  password2;
    TextView joinnow;
    TextView error;
    CallbackManager callbackManager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        callbackManager = CallbackManager.Factory.create();

        //inflate the view and store to rootView
        final View rootView = inflater.inflate(R.layout.register_tab, container,false);

        final Helpers helpers = Helpers.getInstance();
        helpers.initQuickBlox(getContext());


        //initializing all edittext, button and setings
                email = (EditText) rootView.findViewById(R.id.regemail);
                password = (EditText) rootView.findViewById(R.id.regpassword);
                password2 = (EditText) rootView.findViewById(R.id.regpassword2);
                btnSubmit = (ImageButton) rootView.findViewById(R.id.register_btn);
                joinnow = (TextView) rootView.findViewById(R.id.login_already);
                username = (EditText) rootView.findViewById(R.id.regusername);
                error = (TextView) rootView.findViewById(R.id.reg_show_info);





        btnSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // get all the input
                        if(helpers.isInternetAvailable(getContext())){
                            String em,pw,pw2,user;
                            em = email.getText().toString();
                            pw = password.getText().toString();
                            pw2 = password2.getText().toString();
                            user = username.getText().toString();
                            if(helpers.isValidEmail(em)){
                                if(user.length() > 3){
                                    if(pw.length() >= 6 && pw2.length() >= 6){
                                        if(helpers.passwordValidations(pw,pw2) == "success"){
                                            error.setText(R.string.please_wait);
                                            helpers.Register(em,pw,user,error,getContext());
                                        }
                                        else {
                                            error.setText(R.string.passwordNotOkay);
                                            Log.i("pass","Not ok");

                                        }
                                    }
                                    else {
                                        error.setText(R.string.passwordEmpty);
                                    }

                                }
                                else {
                                    error.setText(R.string.error_userName);
                                }
                            }
                            else {
                                error.setText(R.string.error_invalid_email);
                            }
                        }
                        else {
                            error.setText(R.string.internet_connection_error);
                        }


                    }
                });

        joinnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),LoginActivity.class);
                startActivity(intent);

            }
        });








        return rootView;
    }




}

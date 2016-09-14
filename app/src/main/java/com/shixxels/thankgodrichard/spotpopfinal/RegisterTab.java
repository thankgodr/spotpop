package com.shixxels.thankgodrichard.spotpopfinal;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.quickblox.auth.QBAuth;
import com.quickblox.auth.model.QBSession;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.QBEntityCallbackImpl;
import com.quickblox.core.QBSettings;
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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
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

        new Thread(new Runnable() {
            @Override
            public void run() {
                btnSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // get all the input
                        String em,pw,pw2,user;
                        em = email.getText().toString();
                        pw = password.getText().toString();
                        pw2 = password2.getText().toString();
                        user = username.getText().toString();
                        if(helpers.passwordValidations(pw,pw2) == "success"){
                            helpers.Register(em,pw,getContext());
                        }
                        else {
                            Log.i("pass","Not ok");
                        }

                    }
                });

                joinnow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       //TODO add when the sign in button i click in register

                    }
                });
            }
        }).start();







        return rootView;
    }




}

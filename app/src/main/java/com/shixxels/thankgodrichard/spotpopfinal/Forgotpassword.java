package com.shixxels.thankgodrichard.spotpopfinal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class Forgotpassword extends AppCompatActivity {

    EditText email;
    ImageButton submit;
    Helpers app = Helpers.getInstance();
    TextView error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword);
        app.initQuickBlox(this);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width * .8), (int)(height *.46));

        email = (EditText) findViewById(R.id.forgot_password_email);
        submit = (ImageButton) findViewById(R.id.forgot_password_btn);
        error = (TextView) findViewById(R.id.forgot_show_info);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String em = email.getText().toString();
                app.forgotPassword(em, error);

            }
        });


    }
}

package com.shixxels.thankgodrichard.spotpop;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class account extends Fragment implements View.OnClickListener {

    EditText fullName = (EditText) getView().findViewById(R.id.fullName);
    public Button btn;

    public account() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        //Set the Title to Our current Fragment
        ((MainActivity)getActivity())
                .setActionBarTitle("My Account");

        btn = (Button) inflater.inflate(R.layout.account, container, false).findViewById(R.id.resetPassword);
        btn.setOnClickListener(this);
        return inflater.inflate(R.layout.account, container, false);

    }

    @Override
    public void onClick(View v) {
        Toast.makeText(getActivity(),"Text!",Toast.LENGTH_SHORT).show();
    }


}

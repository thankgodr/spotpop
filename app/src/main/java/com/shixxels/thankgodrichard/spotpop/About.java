package com.shixxels.thankgodrichard.spotpop;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class About extends Fragment {


    public About() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        //Set the Title to Our current Fragment
        ((MainActivity)getActivity())
                .setActionBarTitle("About");
        return inflater.inflate(R.layout.about, container, false);

    }

}

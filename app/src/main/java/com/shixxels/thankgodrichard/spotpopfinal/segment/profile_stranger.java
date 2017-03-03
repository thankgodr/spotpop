package com.shixxels.thankgodrichard.spotpopfinal.segment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shixxels.thankgodrichard.spotpopfinal.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class profile_stranger extends Fragment {


    public profile_stranger() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.segment_profile_stranger, container, false);
    }

}

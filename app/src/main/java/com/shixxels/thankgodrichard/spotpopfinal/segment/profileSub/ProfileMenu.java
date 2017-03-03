package com.shixxels.thankgodrichard.spotpopfinal.segment.profileSub;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.shixxels.thankgodrichard.spotpopfinal.Helpers;
import com.shixxels.thankgodrichard.spotpopfinal.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileMenu extends Fragment implements View.OnClickListener {
    LinearLayout pushNotify;
    Fragment frag;


    public ProfileMenu() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.segment_profile_menu, container, false);
        pushNotify = (LinearLayout) view.findViewById(R.id.pushnotify);
        pushNotify.setOnClickListener(this);



        return  view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.pushnotify:
                frag = new ProfilePushNotifySettings();
                openFrag(frag,"notifySettings",R.id.profile_menu_container);

        }
    }

    public void openFrag(Fragment frag, String map, int id) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(id,frag);
        transaction.addToBackStack(map);
        transaction.commit();
    }





}

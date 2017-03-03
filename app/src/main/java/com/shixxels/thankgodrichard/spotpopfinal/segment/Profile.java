package com.shixxels.thankgodrichard.spotpopfinal.segment;


import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mlsdev.rximagepicker.RxImagePicker;
import com.mlsdev.rximagepicker.Sources;
import com.quickblox.auth.model.QBProvider;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.exception.QBResponseException;
import com.quickblox.users.QBUsers;
import com.quickblox.users.model.QBUser;
import com.shixxels.thankgodrichard.spotpopfinal.Helpers;
import com.shixxels.thankgodrichard.spotpopfinal.LoginActivity;
import com.shixxels.thankgodrichard.spotpopfinal.MainActivity;
import com.shixxels.thankgodrichard.spotpopfinal.R;
import com.shixxels.thankgodrichard.spotpopfinal.segment.profileSub.ProfileMenu;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import rx.functions.Action1;

/**
 * A simple {@link Fragment} subclass.
 */
public class Profile extends Fragment {

    CircleImageView profileImg;
    TextView namr;
    View stutus;
     Helpers app = Helpers.getInstance();
    Drawable temp;

    public Profile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.segment_profile, container, false);
        stutus = view.findViewById(R.id.stutus_color);
        profileImg = (CircleImageView) view.findViewById(R.id.profile_image);
        namr = (TextView) view.findViewById(R.id.profile_name);
        Fragment frag = new ProfileMenu();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.profile_menu_container,frag);
        transaction.commit();
       QBUser user = app.readpreference(getContext());
       namr.setText(user.getFullName());
        app.onlineStatus(user.getId(),stutus,getContext());
        app.downloadProfilePic(user.getId(),profileImg,getContext(),getActivity());
        profileImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RxImagePicker.with(getContext()).requestImage(Sources.GALLERY).subscribe(new Action1<Uri>() {
                    @Override
                    public void call(Uri uri) {
                        profileImg.setImageURI(uri);
                        try {
                            Bitmap bitmap =  MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), uri);
                            updateProfile("propic",bitmap);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                });
            }
        });



        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((MainActivity) getActivity()).setBackground(5);
    }
    public void updateProfile(final String type, final Bitmap uri){
        SharedPreferences preferences = getContext().getSharedPreferences(app.Pref,getContext().MODE_PRIVATE);
        String jsonStr = preferences.getString("login","");
        Gson gson = new Gson();
        String[] loginDetails = gson.fromJson(jsonStr,String[].class);
        if(loginDetails.length == 1){
            QBUsers.signInUsingSocialProvider(QBProvider.FACEBOOK, loginDetails[0], null, new QBEntityCallback<QBUser>() {
                @Override
                public void onSuccess(QBUser qbUser, Bundle bundle) {
                    if(type =="propic"){
                        File file = app.bitmapToFile(uri,getContext());
                        app.uploadprofilePic(qbUser.getId(),file);
                    }

                    //Todo Add all upadat profile setting above
                }

                @Override
                public void onError(QBResponseException e) {

                }
            });
        }
        else if(loginDetails.length > 1){
            QBUser user3 = new QBUser(loginDetails[0], loginDetails[1]);
            QBUsers.signIn(user3, new QBEntityCallback<QBUser>() {
                @Override
                public void onSuccess(QBUser qbUser, Bundle bundle) {
                    if(type =="propic"){
                        File file = app.bitmapToFile(uri,getContext());
                        app.uploadprofilePic(qbUser.getId(),file);
                    }

                    //Todo Add all upadat profile setting above


                }

                @Override
                public void onError(QBResponseException e) {

                }
            });
        }
    }

}

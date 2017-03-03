package com.shixxels.thankgodrichard.spotpopfinal.segment;


import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.mlsdev.rximagepicker.RxImagePicker;
import com.mlsdev.rximagepicker.Sources;
import com.shixxels.thankgodrichard.spotpopfinal.Helpers;
import com.shixxels.thankgodrichard.spotpopfinal.LoginActivity;
import com.shixxels.thankgodrichard.spotpopfinal.MainActivity;
import com.shixxels.thankgodrichard.spotpopfinal.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import rx.functions.Action1;

/**
 * A simple {@link Fragment} subclass.
 */
public class SpotAdd extends Fragment implements View.OnClickListener {
    Toolbar toolbar;
    double lat,lng;
    Button passwordprotect,slow,medium,fast,good4work,poweoutlet, submit;
    Boolean passStr,good4workStr,powerOutletStr;
    int speed = 1;
    boolean good4WorkSelected = false, powerOutletSelected = false;
    LinearLayout horizontalScrollView;
    ImageButton addImage;
    ImageView closebtm;
    EditText wifipass, businessName, locAdress, ssid, carierName;
    Helpers app = Helpers.getInstance();
    String[] user;
    Geocoder gc;
    Drawable[] images;
    SharedPreferences preference ;
    AsyncHttpClient client;


    public SpotAdd() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.segment_add_spot, container, false);

        initToolBar(view);
        user = ((MainActivity)this.getActivity()).loginDetails;
        // get all Views and setOnclickListener
        businessName = (EditText) view.findViewById(R.id.business_name);
        locAdress = (EditText) view.findViewById(R.id.location_addr);
        ssid = (EditText) view.findViewById(R.id.ssid);
        carierName = (EditText) view.findViewById(R.id.carrier_name);
        wifipass = (EditText) view.findViewById(R.id.wifi_passw);
        wifipass.setEnabled(false);
        submit = (Button) view.findViewById(R.id.add_spot_submit_btm);
        submit.setOnClickListener(this);
        closebtm = (ImageView) view.findViewById(R.id.close_icon);
        closebtm.setOnClickListener(this);
        passwordprotect = (Button) view.findViewById(R.id.password_protected);
        passwordprotect.setOnClickListener(this);
        passStr = false;
        slow = (Button) view.findViewById(R.id.slow_selected);
        slow.setOnClickListener(this);
        fast = (Button) view.findViewById(R.id.fast_select);
        fast.setOnClickListener(this);
        medium = (Button) view.findViewById(R.id.medium_select);
        medium.setOnClickListener(this);
        good4work = (Button) view.findViewById(R.id.good4work);
        good4work.setOnClickListener(this);
        poweoutlet = (Button) view.findViewById(R.id.power_outlet);
        poweoutlet.setOnClickListener(this);
        addImage = (ImageButton) view.findViewById(R.id.answercall);
        addImage.setOnClickListener(this);
        horizontalScrollView = (LinearLayout) view.findViewById(R.id.imageholder);
        currentButton(speed);
        gc = new Geocoder(getContext());
        client = new AsyncHttpClient();

        // get Shared preference
        return view;
    }



    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.password_protected:
                Log.i(LoginActivity.TAG,"password proted clicked");
                if(passStr == false){
                    passwordprotect.setBackgroundColor(getResources().getColor(R.color.button_selected));
                    passwordprotect.setTextColor(getResources().getColor(R.color.white));
                    wifipass.setEnabled(true);
                    passStr = true;
                }
                else {
                    passwordprotect.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_custom));
                    passwordprotect.setTextColor(getResources().getColor(R.color.btn_text_color));
                    wifipass.setEnabled(false);
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
                if(!good4WorkSelected){
                    currentButton(4);
                    good4WorkSelected = true;
                }
                else {
                    currentButton(5);
                    good4WorkSelected = false;
                }
                break;
            case R.id.power_outlet:
                if(!powerOutletSelected){
                    currentButton(6);
                    powerOutletSelected = true;
                }
                else{
                    currentButton(7);
                    powerOutletSelected = false;
                }
                break;
            case R.id.answercall:
                Log.i("Img","imgclicked");
                pickImages();
                break;
            case R.id.close_icon:
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.remove(this);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                ft.commit();
                getFragmentManager().popBackStack();
                break;
            case R.id.add_spot_submit_btm:
                submit();
                break;
            default:
                break;
        }

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
        else if(i == 4){
            good4work.setBackgroundColor(getResources().getColor(R.color.button_selected));
            good4work.setTextColor(getResources().getColor(R.color.white));
        }
        else if(i == 5){
            good4work.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_custom));
            good4work.setTextColor(getResources().getColor(R.color.btn_text_color));
        }
        else if(i == 6){
            poweoutlet.setBackgroundColor(getResources().getColor(R.color.button_selected));
            poweoutlet.setTextColor(getResources().getColor(R.color.white));
        }
        else if(i == 7){
            poweoutlet.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_custom));
            poweoutlet.setTextColor(getResources().getColor(R.color.btn_text_color));
        }
    }

    public void initToolBar(View v) {
        toolbar = (Toolbar) v.findViewById(R.id.toolbar);
    }


    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
    }
    @Override
    public void onStop() {
        super.onStop();
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
    }

    private  void addImageView(Uri uri){
        ImageView image = new ImageView(getContext());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(80,80);
        params.setMargins(5,0,5,0);
        image.setLayoutParams(params);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            image.setImageDrawable(getContext().getDrawable(R.drawable.thumb_1_1));
        }
       Glide.with(getContext()).load(uri).asBitmap().fitCenter().into(image);
        horizontalScrollView.addView(image,0);



    }


        private void pickImages(){
            RxImagePicker.with(getContext()).requestImage(Sources.GALLERY).subscribe(new Action1<Uri>() {
                @Override
                public void call(Uri uri) {
                   addImageView(uri);
                }
            });


        }

    private void submit(){
        //images byte to upload
        List<String> stringByte = new ArrayList<>();
        String busi = businessName.getText().toString(), sid = ssid.getText().toString(),caria = carierName.getText().toString(),
                pwd = wifipass.getText().toString(), loc = locAdress.getText().toString();
        getLatLong(loc);
        //get uploaded image from draweables
        int childcount = horizontalScrollView.getChildCount();
        for(int i= 0; i < childcount; i++){
            ImageView v = (ImageView) horizontalScrollView.getChildAt(i);
            Bitmap drawable = ((BitmapDrawable)v.getDrawable()).getBitmap();
            stringByte.add(app.convertDrawabletoString(drawable));

        }


       // Log.i("log", String.valueOf(log));
        String[][] data = new String[][]{new String[]{"business",busi},new String[]{"ssid",sid},new String[]{"carrier",caria},
                new String[]{"password",pwd},new String[]{"speed",speed + ""},new String[]{"business",busi},new String[]{"address",loc},
                new String[]{"good_for_working",good4WorkSelected + ""},new String[]{"outlets",powerOutletSelected + ""},new String[]{"speed",speed
                + ""},new String[]{"lat",lat + ""},new String[]{"log",lng + ""}};
        app.createRecord(getContext(),"Spots",data,user,stringByte);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        View bottom = getActivity().findViewById(R.id.btmholder);
        bottom.setVisibility(View.GONE);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        View bottom = getActivity().findViewById(R.id.btmholder);
        bottom.setVisibility(View.VISIBLE);

    }

    private void getLatLong(String address){
        client.get("https://maps.googleapis.com/maps/api/geocode/json?address="+address+"&key=AIzaSyBI-nwSw120blZy6ekKKINszBwRx9spa9A\n",
                new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        String s = new String(responseBody);
                        JSONObject json = null;
                        JSONArray jsonArray = null;
                        JSONObject result = null;
                        JSONObject result2 = null;
                        JSONObject result3 = null;


                        try {
                            json = new JSONObject(s);
                            jsonArray = (JSONArray) json.get("results");
                            result = jsonArray.getJSONObject(0);
                            result2 = (JSONObject) result.get("geometry");
                            result3 = (JSONObject) result2.get("location");
                           // Log.i("respondz", String.valueOf(result3.get("lat").getClass().getName()));
                            lat = (double) result3.get("lat");
                            lng = (double) result3.get("lng");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                           lat = 0.0;
                           lng = 0.0;
                        Toast.makeText(getContext(),"Error fetching Address Geocode",Toast.LENGTH_SHORT);
                    }
                });

        Log.i("respondx", String.valueOf(lat));
    }
}



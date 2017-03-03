package com.shixxels.thankgodrichard.spotpopfinal.segment;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.qb.gson.reflect.TypeToken;
import com.quickblox.customobjects.model.QBCustomObject;
import com.shixxels.thankgodrichard.spotpopfinal.Helpers;
import com.shixxels.thankgodrichard.spotpopfinal.MainActivity;
import com.shixxels.thankgodrichard.spotpopfinal.R;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Maplist extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    Button mapviewBtn,listviewBtn;
    int mapList = 1;
    ImageView menuIcon;
    TextView notifyNo;
    LinearLayout listcontent,loaderholder;
    Geocoder gc;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.segment_map_list,container,false);
        gc = new Geocoder(getContext());
        mapviewBtn = (Button) view.findViewById(R.id.mapview);
        listviewBtn = (Button) view.findViewById(R.id.list_view);//change the menu icon
        menuIcon = (ImageView) getActivity().findViewById(R.id.imgmenu);
        menuIcon.setOnClickListener(null);
        menuIcon.setImageResource(R.mipmap.filter_inactive);
        notifyNo = (TextView) getActivity().findViewById(R.id.nofityNumber);
        notifyNo.setVisibility(View.GONE);
        mapOrList();
        listcontent = (LinearLayout) view.findViewById(R.id.list_container);
        return  view;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        ((MainActivity) getActivity()).setBackground(2);
        mapviewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mapList = 1;
                mapOrList();
                Fragment maplist = new Maplist();
                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                transaction.replace(R.id.jumbo,maplist);
                transaction.commit();


            }
        });

        listviewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mapList = 2;
                mapOrList();
                Fragment frag = new ListView();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                listcontent.removeAllViews();
                transaction.replace(R.id.list_container,frag);
                transaction.commit();

            }
        });
        menuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("srr","Fragment clicked");
            }
        });
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        //From quichblox in sharedpref

        Type listOfObjects = new TypeToken<ArrayList<QBCustomObject>>(){}.getType();
        SharedPreferences preference = getContext().getSharedPreferences(Helpers.Pref, getContext().MODE_PRIVATE);
        String content = preference.getString("Spotpop-content","");
        Gson gson = new Gson();
        ArrayList<QBCustomObject> customObjects = gson.fromJson(content,listOfObjects);
        mMap = googleMap;
        for(int i =0; i< customObjects.size(); i++ ){
            String lat = (String) customObjects.get(i).get("lat");
            String log= (String) customObjects.get(i).get("log");
            LatLng area = new LatLng(Integer.parseInt(lat),Integer.parseInt(log));
            mMap.addMarker(new MarkerOptions().position(area).title((String) customObjects.get(i).get("business")));

        }

        LatLng lagos = new LatLng(6.454879,3.424598);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lagos,10));


    }

    public void mapOrList(){
        if(mapList == 1){
            mapviewBtn.setBackgroundColor(getResources().getColor(R.color.white));
            mapviewBtn.setTextColor(getResources().getColor(R.color.bgprofil));

            listviewBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_map));
            listviewBtn.setTextColor(getResources().getColor(R.color.white));



        }
        else if(mapList == 2){

            listviewBtn.setBackgroundColor(getResources().getColor(R.color.white));
            listviewBtn.setTextColor(getResources().getColor(R.color.bgprofil));

            mapviewBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_map));
            mapviewBtn.setTextColor(getResources().getColor(R.color.white));

        }
    }
    public static double getLatLong(String address, String type, Context c){
        double result = 0;
        Geocoder gc = new Geocoder(c);
        if(address != null){
            try {
                List<Address> addresses = gc.getFromLocationName(address,5);
                if(addresses != null && addresses.size() > 0){
                    if(type == "lat") {
                        result = addresses.get(0).getLatitude();
                        Log.i("lat", String.valueOf(result));
                    }
                    else if(type == "long"){
                        result = addresses.get(0).getLongitude();
                        Log.i("long", String.valueOf(result));
                    }
                    else{
                        result = Double.parseDouble(null);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        menuIcon.setImageResource(R.mipmap.ic_inbox);
        ((MainActivity) getActivity()).inboxRecall(menuIcon);
        notifyNo.setVisibility(View.VISIBLE);


    }
}

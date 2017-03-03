package com.shixxels.thankgodrichard.spotpopfinal.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.qb.gson.reflect.TypeToken;
import com.quickblox.customobjects.model.QBCustomObject;
import com.shixxels.thankgodrichard.spotpopfinal.Helpers;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by thankgodrichard on 10/5/16.
 */
public class SearchModel {

    private int imageID;
    private String title;
    private String description;
    private String popsLike;
    private String popsDisLike;

    public int getImageID() {
        return imageID;
    }
    public void setImageID(int imageID) {
        this.imageID = imageID;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getPopsLike() {
        return popsLike;
    }
    public void setPopsLike(String popsLike) {
        this.popsLike = popsLike;
    }




    public static ArrayList<SearchModel> getData(Context context) {
        Type listOfObjects = new TypeToken<ArrayList<QBCustomObject>>(){}.getType();
        SharedPreferences preference = context.getSharedPreferences(Helpers.Pref, context.MODE_PRIVATE);
        String content = preference.getString("Spotpop-content","");
        Gson gson = new Gson();
        ArrayList<QBCustomObject> customObjects = gson.fromJson(content,listOfObjects);
        Log.i("srr", String.valueOf(customObjects));
        int index = customObjects.size();
        ArrayList<SearchModel> dataList = new ArrayList<>();
        Double pops =0.0, stops = 0.0;





        for (int i = 0; i < index; i++) {

            if(customObjects.get(i).get("pops") != null){
                pops = (Double) customObjects.get(i).get("pops");
                Log.i("srr", String.valueOf(pops));
            }
            else{
                pops = 1.0;
            }
            if(customObjects.get(i).get("stops") != null){
                stops = (Double) customObjects.get(i).get("stops");
                Log.i("srr1", String.valueOf(stops));


            }
            else{
                stops = 1.0;
            }

            SearchModel landscape = new SearchModel();
            landscape.setTitle((String) customObjects.get(i).get("business"));
            landscape.setDescription((String) customObjects.get(i).get("address"));
            landscape.setPopsLike(Math.round(calcPercent(pops.intValue(),stops.intValue(),1)) + "");
            landscape.setPopsDisLike(Math.round(calcPercent(pops.intValue(),stops.intValue(),2)) + "");
            dataList.add(landscape);

        }

        return dataList;

    }

    private static double calcPercent(int one, int two, int type){
       double i = one + two;
        double res;
        if(type == 1){
            res = one/i;
        }
        else {
            res = two/i;
        }
        Log.i("srr6", String.valueOf(res * 100));
        return res * 100;

    }


    public String getPopsDisLike() {
        return popsDisLike;
    }

    public void setPopsDisLike(String popsDisLike) {
        this.popsDisLike = popsDisLike;
    }
}

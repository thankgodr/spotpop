package com.shixxels.thankgodrichard.spotpopfinal.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.qb.gson.reflect.TypeToken;
import com.quickblox.auth.QBAuth;
import com.quickblox.auth.model.QBSession;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.QBSettings;
import com.quickblox.core.exception.QBResponseException;
import com.quickblox.core.request.QBRequestGetBuilder;
import com.quickblox.customobjects.QBCustomObjects;
import com.quickblox.customobjects.model.QBCustomObject;
import com.quickblox.users.model.QBUser;
import com.shixxels.thankgodrichard.spotpopfinal.Helpers;
import com.shixxels.thankgodrichard.spotpopfinal.R;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;


public class FeedAdapter {

	private int getProfilepic;
	private String title;
	private String description, handle;
    private String comment;
	private Object prime;
    private String likes;
    private String dislike;
    private  String objectId;
    private  String parentId;
    private QBUser user;
    private String cretedAT;
    private ArrayList imageGalery;




    //getters and setters for Adapter use purpose

    public QBUser getUser() {
        return user;
    }
    public void setUser(QBUser user) {
        this.user = user;
    }
    public String getParentId() {
        return parentId;
    }
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
    public String getObjectId() {
        return objectId;
    }
    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }
    public String getHandle() {
        return handle;
    }
    public void setHandle(String handle) {
        this.handle = handle;
    }
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
    public String getDislike() {
        return dislike;
    }
    public void setDislike(String dislike) {
        this.dislike = dislike;
    }
    public String getLikes() {
        return likes;
    }
    public void setLikes(String likes) {
        this.likes = likes;
    }
	public int getImageID() {
		return getProfilepic;
	}
	public void setImageID(int imageID) {
		this.getProfilepic = imageID;
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
	public int getViewInt() {
		return (int) prime;
	}
	public void setPrime(int prime) {
		this.prime = prime;
	}


	//Call to get data
	public static ArrayList<FeedAdapter> getData(Context context,ArrayList<QBCustomObject> objects) {
        Type listOfObjects = new TypeToken<ArrayList<QBCustomObject>>(){}.getType();
        SharedPreferences preference = context.getSharedPreferences(Helpers.Pref, context.MODE_PRIVATE);
        String useinfo = preference.getString("Spotpop-content","");
        Gson gson = new Gson();
        ArrayList<QBCustomObject> in3 = gson.fromJson(useinfo,listOfObjects);
		ArrayList<FeedAdapter> dataList = new ArrayList<>();
        int index = objects.size();


        for (int i = 0; i < index; i++) {
            Double pops =0.0, stops = 0.0;
            int d = (int) objects.get(i).get("feedType");
            ArrayList tempGalery = getImagesFromFeeds(objects.get(i).getParentId(),in3);
                pops = getPopStopNo(objects.get(i).getParentId(),in3,1);
                stops = getPopStopNo(objects.get(i).getParentId(),in3,2);


			FeedAdapter feedAdapter = new FeedAdapter();
			feedAdapter.setImageID(objects.get(i).getUserId());
			feedAdapter.setTitle(objects.get(i).get("feedTitle")+ "");
            feedAdapter.setDescription((String) objects.get(i).get("description"));
            feedAdapter.setLikes(Math.round(calcPercent(pops.intValue(),stops.intValue(),1)) + "");
            feedAdapter.setDislike(Math.round(calcPercent(pops.intValue(),stops.intValue(),2)) + "");
            feedAdapter.setPrime(d);
            feedAdapter.setObjectId(objects.get(i).getCustomObjectId());
            feedAdapter.setParentId(objects.get(i).getParentId());
            feedAdapter.setImageGalery(tempGalery);
            feedAdapter.setCretedAT(objects.get(i).getCreatedAt().getTime() / 1000 + "");

			dataList.add(feedAdapter);
		}

		return dataList;
	}




    private static ArrayList getImagesFromFeeds(String objectId,ArrayList<QBCustomObject> objects){
        ArrayList result = null;
         for(int i = 0; i < objects.size(); i++){
           String id = objects.get(i).getCustomObjectId();
             if(objectId.equals(id)) {
                 result = (ArrayList) objects.get(i).get("galery");
             }
         }
        return result;

    }

    public ArrayList getImageGalery() {
        return imageGalery;
    }

    public void setImageGalery(ArrayList imageGalery) {
        this.imageGalery = imageGalery;
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
    private static double getPopStopNo(String objectId,ArrayList<QBCustomObject> objects,int type){
        double result = 0.0;
        for(int i = 0; i < objects.size(); i++){
            String id = objects.get(i).getCustomObjectId();
            Log.i("yesp",id+" "+ objectId);
            if(objectId.equals(id)) {
                if(type == 1) {
                    if(objects.get(i).get("pops") != null) {
                        result = (double) objects.get(i).get("pops");
                    }
                    else{
                        result = 1.0;
                    }
                }
                else {
                    if(objects.get(i).get("stops") != null) {
                        result = (double) objects.get(i).get("stops");
                    }
                    else{
                        result = 0.0;
                    }
                }
            }
        }
        return result;

    }


    public String getCretedAT() {
        return cretedAT;
    }

    public void setCretedAT(String cretedAT) {
        this.cretedAT = cretedAT;
    }



}
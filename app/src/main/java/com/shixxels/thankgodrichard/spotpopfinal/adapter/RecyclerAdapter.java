package com.shixxels.thankgodrichard.spotpopfinal.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.quickblox.auth.model.QBProvider;
import com.quickblox.core.Consts;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.exception.QBResponseException;
import com.quickblox.core.request.QBRequestGetBuilder;
import com.quickblox.core.request.QBRequestUpdateBuilder;
import com.quickblox.customobjects.QBCustomObjects;
import com.quickblox.customobjects.model.QBCustomObject;
import com.quickblox.users.QBUsers;
import com.quickblox.users.model.QBUser;
import com.shixxels.thankgodrichard.spotpopfinal.Helpers;
import com.shixxels.thankgodrichard.spotpopfinal.LoginActivity;
import com.shixxels.thankgodrichard.spotpopfinal.R;
import com.shixxels.thankgodrichard.spotpopfinal.model.FeedAdapter;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

	private static final int Image_digit = 1;
	private static final int Map_digit = 2;
    private static final int Text_digit = 3;
    private Context context;
    private Activity ac;
    Helpers app = Helpers.getInstance();
    QBUser user = app.usel;






    private List<FeedAdapter> mDataList;
	private LayoutInflater inflater;
    private String[] latlog;

    public RecyclerAdapter(Context context, List<FeedAdapter> data, Activity ac) {
		inflater = LayoutInflater.from(context);
		this.mDataList = data;
        this.context = context;
        this.ac = ac;
    }




        @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {  // Create the Prime and Non-Prime row Layouts

            case Image_digit:

                ViewGroup imageView = (ViewGroup) inflater.inflate(R.layout.list_item, parent, false);
                MyViewHolder_Images holderPrime = new MyViewHolder_Images(imageView);
                return holderPrime;

            case Map_digit:

                ViewGroup mapView = (ViewGroup) inflater.inflate(R.layout.list_item_map, parent, false);
                MyViewHolder_Map holderNonPrime = new MyViewHolder_Map(mapView);
                return holderNonPrime;

            default:

                ViewGroup defaultRow = (ViewGroup) inflater.inflate(R.layout.list_item_text, parent, false);
                MyViewHolder_Text holderDefault = new MyViewHolder_Text(defaultRow);
                return holderDefault;
        }
    }

	@Override
	public void onBindViewHolder(MyViewHolder holder, int position) {
		FeedAdapter current = mDataList.get(position);

		switch (holder.getItemViewType()) {

			case Image_digit:

				MyViewHolder_Images holder_prime = (MyViewHolder_Images) holder;
				holder_prime.setData(current);

				break;

			case Map_digit:

				MyViewHolder_Map holder_not_prime = (MyViewHolder_Map) holder;
				holder_not_prime.setData(current);

				break;
			default:
				MyViewHolder_Text holder_not_text = (MyViewHolder_Text) holder;
				holder_not_text.setData(current);
				break;
		}
	}

	@Override
	public int getItemCount() {
		return mDataList.size();
	}

	@Override // This will help us to determine ROW TYPE : i.e. the PRIME or NON-PRIME row.
	public int getItemViewType(int position) {

		FeedAdapter feedAdapter = mDataList.get(position);
		if (feedAdapter.getViewInt() == 1)
			return Image_digit;
		else if (feedAdapter.getViewInt() == 2){
            return Map_digit;
        }
        else if (feedAdapter.getViewInt() == 3){
            return Text_digit;
        }
        else {
            return 0;
        }

	}

	class MyViewHolder extends RecyclerView.ViewHolder {

		public MyViewHolder(View itemView) {
			super(itemView);
		}
	}

	// Holder class for Images rows
	public class MyViewHolder_Images extends MyViewHolder implements View.OnClickListener {

        TextView title,like_count,dislike_count, description,timeOfPost;
        LinearLayout imageholder;
        ImageView imgRowType, menu,like,dislike;
        CircleImageView imgThumb;

        public MyViewHolder_Images(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.tvTitle);
            imgThumb = (CircleImageView) itemView.findViewById(R.id.img_row);
            menu   = (ImageView) itemView.findViewById(R.id.img_row_delete);
            imageholder = (LinearLayout) itemView.findViewById(R.id.image_holderfor_imageview);
            like = (ImageView) itemView.findViewById(R.id.comment_like_img);
            like_count = (TextView) itemView.findViewById(R.id.comment_like_no);
            dislike = (ImageView) itemView.findViewById(R.id.comment_dislike_img);
            dislike_count = (TextView) itemView.findViewById(R.id.comment_dislike_no);
            description = (TextView) itemView.findViewById(R.id.tvDescription);
            timeOfPost = (TextView) itemView.findViewById(R.id.time_of_post);
        }

        public void setData(final FeedAdapter current) {
            long timediff = Long.parseLong(current.getCretedAT());
            ArrayList imagesTest = current.getImageGalery();
            for(int i = 0; i < imagesTest.size(); i++){
                Bitmap tempImg = app.converrtStringToBitmap((String) imagesTest.get(i));
                addImageView(this.imageholder,tempImg);
            }
            imgThumb.setImageResource(R.mipmap.loading);
            app.downloadProfilePic(current.getImageID(),imgThumb,context,ac);
            this.title.setText(current.getTitle());
            this.description.setText(current.getDescription());
            this.timeOfPost.setText(caluculateTimeAgo(timediff));
            this.like_count.setText(current.getLikes() + "%");
            this.dislike_count.setText(current.getDislike() + "%");
            this.menu.setOnClickListener(MyViewHolder_Images.this);
            /*this.imgThumb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showImage(current.getImageID());
                }
            });*/
            this.like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    commentAndLikes("like",null,null,current.getParentId());



                }
            });
            this.dislike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    commentAndLikes("dislike",null,null,current.getParentId());
                }
            });
        }
        @Override
        public void onClick(View v) {
            openDialog();

        }
	}

	// Holder class for maps rows
	public class MyViewHolder_Map extends MyViewHolder implements View.OnClickListener {

		TextView title, describtion;
		ImageView  imgRowType, menu,like,dislike;
        CircleImageView imgThumb;


        public MyViewHolder_Map(View itemView) {
			super(itemView);
			title = (TextView) itemView.findViewById(R.id.tvTitle);
			imgThumb = (CircleImageView) itemView.findViewById(R.id.img_row);
			imgRowType = (ImageView) itemView.findViewById(R.id.map_image);
            menu   = (ImageView) itemView.findViewById(R.id.img_row_delete);
            like = (ImageView) itemView.findViewById(R.id.comment_like_img);
            dislike = (ImageView) itemView.findViewById(R.id.comment_dislike_img);
            describtion = (TextView) itemView.findViewById(R.id.tvDescription);

        }

		public void setData(final FeedAdapter current) {

			this.title.setText(current.getTitle());
            this.menu.setOnClickListener(MyViewHolder_Map.this);
            this.describtion.setText(current.getDescription());
            imgThumb.setImageResource(R.mipmap.loading);
            app.downloadProfilePic(current.getImageID(),imgThumb,context,ac);
           /* this.imgThumb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showImage(current.getImageID());
                }
            });*/
            this.like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    commentAndLikes("like",null,null,current.getParentId());

                }
            });
            this.dislike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    commentAndLikes("dislike",null,null,current.getParentId());                }
            });

            QBCustomObject customObject = new QBCustomObject("Spots", current.getParentId());

            QBCustomObjects.getObject(customObject, new QBEntityCallback<QBCustomObject>(){
                @Override
                public void onSuccess(QBCustomObject customObject, Bundle params) {
                  String lat = (String) customObject.get("lat");
                    String log = (String) customObject.get("log");
                    String url = "https://maps.googleapis.com/maps/api/staticmap?center="+lat+","+log+"&zoom=13&size=600x200&maptype=terrain\n" +
                            "&markers=color:blue%7Clabel:S%7C"+lat+","+log+"\n" +
                            "&key=AIzaSyC8nNMDHLu-evfaESi8LcTxwxIH3kaz_Rs\n" +
                            "\n";
                    Picasso.with(context).load(url).fit().into(imgRowType);

                }

                @Override
                public void onError(QBResponseException errors) {

                }
            });





        }
        @Override
        public void onClick(View v) {
			switch (v.getId()) {
                case R.id.img_row_delete:
                    openDialog();
                    break;
            }





        }
	}
	// Holder class for NON-Text rows
	public class MyViewHolder_Text extends MyViewHolder implements View.OnClickListener {

        TextView title;
        ImageView  menu,like,dislike, imgThumb;


        public MyViewHolder_Text(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.tvTitle);
            imgThumb = (ImageView) itemView.findViewById(R.id.img_row);
            menu   = (ImageView) itemView.findViewById(R.id.img_row_delete);
            like = (ImageView) itemView.findViewById(R.id.comment_like_img);
            dislike = (ImageView) itemView.findViewById(R.id.dislike_img);


        }

        public void setData(final FeedAdapter current) {
            this.title.setText(current.getTitle());
            this.imgThumb.setImageResource(current.getImageID());
            this.menu.setOnClickListener(MyViewHolder_Text.this);
            this.imgThumb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showImage(current.getImageID());
                }
            });
            this.like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    commentAndLikes("like",null,null,current.getParentId());
                    Log.i("like","cliked");
                    notifyDataSetChanged();
                }
            });
            this.dislike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    commentAndLikes("dislike",null,null,current.getParentId());
                    notifyDataSetChanged();

                }
            });
        }
        @Override
        public void onClick(View v) {
            Log.i("onClick","i was clicked");
            openDialog();

        }}


    public void openDialog() {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        LinearLayout.LayoutParams dialogParams = new LinearLayout.LayoutParams(
                600, 600);//set height and width here, ie (width,height)

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dislogView = inflater
                .inflate(R.layout.feed_menu, null);
        dialog.setContentView(dislogView, dialogParams);
        // set the custom dialog components - text, image and button
        TextView text = (TextView) dialog.findViewById(R.id.text);


        TextView dialogButton = (TextView) dialog.findViewById(R.id.report);
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void showImage(int imageUri) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();
        final Dialog builder = new Dialog(context,android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        builder.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                //nothing;
            }
        });

        ImageView imageView = new ImageView(context);
        imageView.setImageResource(imageUri);
                builder.addContentView(imageView, new RelativeLayout.LayoutParams(width,
               height ));
        builder.show();
    }

    public void  commentAndLikes(final String action, final String comment, final String customObjId, final String parentID){
        SharedPreferences preferences = context.getSharedPreferences(app.Pref,context.MODE_PRIVATE);
        String jsonStr = preferences.getString("login","");
        Gson gson = new Gson();
        String[] loginDetails = gson.fromJson(jsonStr,String[].class);
        if(loginDetails.length == 1){
            //if user signed in with facebook we het the token from shared preference
            QBUsers.signInUsingSocialProvider(QBProvider.FACEBOOK, loginDetails[0], null, new QBEntityCallback<QBUser>() {
                @Override
                public void onSuccess(final QBUser qbUser, Bundle bundle) {
                    QBCustomObject record = new QBCustomObject();
                    if (action == "like") {
                        record.setClassName("Spots");
                        record.setCustomObjectId(parentID);
                        QBRequestUpdateBuilder updateBuilder = new QBRequestUpdateBuilder();
                        updateBuilder.inc("pops", 1);

                        QBCustomObjects.updateObject(record, updateBuilder, new QBEntityCallback<QBCustomObject>() {
                            @Override
                            public void onSuccess(QBCustomObject qbCustomObject, Bundle bundle) {
                                app.popSpot(parentID, qbUser,context);
                               // app.fetcCustomContent(context);

                            }

                            @Override
                            public void onError(QBResponseException e) {

                            }
                        });

                    } else if (action == "dislike") {
                        record.setClassName("Spots");
                        record.setCustomObjectId(parentID);
                        QBRequestUpdateBuilder updateBuilder = new QBRequestUpdateBuilder();
                        updateBuilder.inc("stops", 1);
                        QBCustomObjects.updateObject(record, updateBuilder, new QBEntityCallback<QBCustomObject>() {
                            @Override
                            public void onSuccess(QBCustomObject qbCustomObject, Bundle bundle) {

                            }

                            @Override
                            public void onError(QBResponseException e) {

                            }
                        });

                    } else if (action == "comment") {
                        record.setClassName("comments");
                        record.setParentId(parentID);
                        record.putString("comment", comment);

                        QBCustomObjects.createObject(record, new QBEntityCallback<QBCustomObject>() {
                            @Override
                            public void onSuccess(QBCustomObject qbCustomObject, Bundle bundle) {

                            }

                            @Override
                            public void onError(QBResponseException e) {

                            }
                        });


                    }


                }

                @Override
                public void onError(QBResponseException e) {

                }
            });
        }
        else if (loginDetails.length > 1) {
            //If user login with user name and password. then loginDetails lenght will be greater than one
            QBUser user3 = new QBUser(loginDetails[0], loginDetails[1]);
            QBUsers.signIn(user3, new QBEntityCallback<QBUser>() {
                @Override
                public void onSuccess(final QBUser user, Bundle params) {
                    QBCustomObject record = new QBCustomObject();
                    if (action == "like") {
                        QBRequestGetBuilder permisionCheck = new QBRequestGetBuilder();
                        permisionCheck.eq("user_id", user.getId());
                        permisionCheck.eq("_parent_id",parentID);

                        QBCustomObjects.getObjects("Feeds", permisionCheck, new QBEntityCallback<ArrayList<QBCustomObject>>() {
                            @Override
                            public void onSuccess(ArrayList<QBCustomObject> objects, Bundle bundle) {
                                if(objects.size()  == 0){
                                    QBCustomObject record1 = new QBCustomObject();
                                    record1.setClassName("Spots");
                                    record1.setCustomObjectId(parentID);
                                    QBRequestUpdateBuilder updateBuilder = new QBRequestUpdateBuilder();
                                    updateBuilder.inc("pops", 1);

                                    QBCustomObjects.updateObject(record1, updateBuilder, new QBEntityCallback<QBCustomObject>() {
                                        @Override
                                        public void onSuccess(QBCustomObject qbCustomObject, Bundle bundle) {
                                            app.popSpot(parentID, user,context);
                                          //  app.fetcCustomContent(context);

                                        }

                                        @Override
                                        public void onError(QBResponseException e) {

                                        }
                                    });
                                }
                                else {
                                    app.toastCenter("You have poped this before",context);
                                }
                            }

                            @Override
                            public void onError(QBResponseException e) {
                                Log.i("testingObjecterr", String.valueOf(e.getHttpStatusCode()));
                            }
                        });



                    } else if (action == "dislike") {
                        QBRequestGetBuilder permisionCheck = new QBRequestGetBuilder();
                        permisionCheck.eq("user_id", user.getId());
                        permisionCheck.eq("_parent_id",parentID);

                        QBCustomObjects.getObjects("DislikeTracker", permisionCheck, new QBEntityCallback<ArrayList<QBCustomObject>>() {
                            @Override
                            public void onSuccess(ArrayList<QBCustomObject> objects, Bundle bundle) {
                                if(objects.size() == 0){
                                    QBCustomObject record2 = new QBCustomObject();
                                    record2.setClassName("Spots");
                                    record2.setCustomObjectId(parentID);
                                    QBRequestUpdateBuilder updateBuilder = new QBRequestUpdateBuilder();
                                    updateBuilder.inc("stops", 1);
                                    QBCustomObjects.updateObject(record2, updateBuilder, new QBEntityCallback<QBCustomObject>() {
                                        @Override
                                        public void onSuccess(QBCustomObject qbCustomObject, Bundle bundle) {
                                            app.disLikeSpotTracker(parentID,user,context);
                                        }

                                        @Override
                                        public void onError(QBResponseException e) {

                                        }
                                    });
                                }
                                else {
                                    app.toastCenter("You already dislike this", context);
                                }
                            }

                            @Override
                            public void onError(QBResponseException e) {

                            }
                        });


                    } else if (action == "comment") {
                        record.setClassName("comments");
                        record.setParentId(parentID);
                        record.putString("comment", comment);

                        QBCustomObjects.createObject(record, new QBEntityCallback<QBCustomObject>() {
                            @Override
                            public void onSuccess(QBCustomObject qbCustomObject, Bundle bundle) {

                            }

                            @Override
                            public void onError(QBResponseException e) {

                            }
                        });


                    }


                }

                @Override
                public void onError(QBResponseException errors) {

                }
            });
        }






    }
    private  void addImageView(LinearLayout linearLayout, Bitmap uri){
        CircleImageView image = new CircleImageView(context);
        image.setBorderColorResource(R.color.bgprofil);
        image.setBorderWidth(1);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(60,60);
        params.setMargins(0,0,5,0);
        image.setLayoutParams(params);
        image.setImageBitmap(uri);
        linearLayout.addView(image,0);

    }

    public static String caluculateTimeAgo(long timeStamp) {

        long timeDiffernce;
        long unixTime = System.currentTimeMillis() / 1000L;  //get current time in seconds.
        int j;
        String[] periods = {"sec.", "min.", "hr(s)", "day(s)", "week(s)", "month", "yr", "d"};
        // you may choose to write full time intervals like seconds, minutes, days and so on
        double[] lengths = {60, 60, 24, 7, 4.35, 12, 10};
        timeDiffernce = unixTime - timeStamp;
        for (j = 0; timeDiffernce >= lengths[j] && j < lengths.length - 1; j++) {
            timeDiffernce /= lengths[j];
        }
        return "about "+timeDiffernce+" " + periods[j] + " ago";
    }




}
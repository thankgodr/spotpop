package com.shixxels.thankgodrichard.spotpopfinal.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.shixxels.thankgodrichard.spotpopfinal.R;

/**
 * Created by thankgodrichard on 9/26/16.
 */
public class FeedMenu {
    public void showDialog(Activity activity, String msg){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.feed_menu);

        TextView text = (TextView) dialog.findViewById(R.id.report);
        text.setText(msg);


        dialog.show();

    }

}

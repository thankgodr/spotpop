package com.shixxels.thankgodrichard.spotpopfinal.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shixxels.thankgodrichard.spotpopfinal.R;
import com.shixxels.thankgodrichard.spotpopfinal.model.NotificationModel;
import com.shixxels.thankgodrichard.spotpopfinal.model.SearchModel;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.MyViewHolder> {
    List<NotificationModel> mData;
    private LayoutInflater inflater;

    public NotificationAdapter(Context context, List<NotificationModel> data) {
        inflater = LayoutInflater.from(context);
        this.mData = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_notification, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        NotificationModel current = mData.get(position);
        holder.setData(current, position);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView descrip;
        int position;
        NotificationModel current;

        public MyViewHolder(View itemView) {
            super(itemView);
            descrip = (TextView) itemView.findViewById(R.id.tvDescription);
            img = (ImageView) itemView.findViewById(R.id.img_row);
        }

        public void setData(NotificationModel current, int position) {
            this.img.setImageResource(current.getImageID());
            this.descrip.setText(current.getDescription());
            this.position = position;
            this.current = current;
        }
    }
}

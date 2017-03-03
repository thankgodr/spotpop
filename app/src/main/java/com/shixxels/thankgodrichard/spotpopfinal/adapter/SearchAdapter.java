package com.shixxels.thankgodrichard.spotpopfinal.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shixxels.thankgodrichard.spotpopfinal.R;
import com.shixxels.thankgodrichard.spotpopfinal.model.SearchModel;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyViewHolder> {
    List<SearchModel> mData;
    private LayoutInflater inflater;

    public SearchAdapter(Context context, List<SearchModel> data) {
        inflater = LayoutInflater.from(context);
        this.mData = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_pop_view, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        SearchModel current = mData.get(position);
        holder.setData(current, position);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title, like, dislike;
        TextView descrip;
        int position;
        SearchModel current;

        public MyViewHolder(View itemView) {
            super(itemView);
            title       = (TextView)  itemView.findViewById(R.id.tvTitle);
            descrip = (TextView) itemView.findViewById(R.id.tvDescription);
            like = (TextView) itemView.findViewById(R.id.like_no);
            dislike = (TextView) itemView.findViewById(R.id.dislike_no);


        }

        public void setData(SearchModel current, int position) {
            this.title.setText(current.getTitle());
            this.descrip.setText(current.getDescription());
            this.like.setText(current.getPopsLike() + "%");
            this.position = position;
            this.current = current;
            this.dislike.setText(current.getPopsDisLike()+"%");
        }
    }
}

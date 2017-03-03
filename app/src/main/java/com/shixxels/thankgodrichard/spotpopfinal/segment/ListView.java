package com.shixxels.thankgodrichard.spotpopfinal.segment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shixxels.thankgodrichard.spotpopfinal.R;
import com.shixxels.thankgodrichard.spotpopfinal.adapter.RecyclerAdapter;
import com.shixxels.thankgodrichard.spotpopfinal.adapter.SearchAdapter;
import com.shixxels.thankgodrichard.spotpopfinal.model.FeedAdapter;
import com.shixxels.thankgodrichard.spotpopfinal.model.SearchModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListView extends Fragment {


    public ListView() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.segment_list_view, container, false);
        setUpRecyclerView(view);
        return view;
    }

      private void setUpRecyclerView(View v) {

        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView5);
        SearchAdapter adapter = new SearchAdapter(getContext(), SearchModel.getData(getContext()));
        recyclerView.setAdapter(adapter);

        LinearLayoutManager mLinearLayoutManagerVertical = new LinearLayoutManager(getContext()); // (Context context, int spanCount)
        mLinearLayoutManagerVertical.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLinearLayoutManagerVertical);

        recyclerView.setItemAnimator(new DefaultItemAnimator()); // Even if we dont use it then also our items shows default animation. #Check Docs
    }

}

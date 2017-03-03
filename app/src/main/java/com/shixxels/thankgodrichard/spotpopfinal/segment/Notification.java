package com.shixxels.thankgodrichard.spotpopfinal.segment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shixxels.thankgodrichard.spotpopfinal.MainActivity;
import com.shixxels.thankgodrichard.spotpopfinal.R;
import com.shixxels.thankgodrichard.spotpopfinal.adapter.NotificationAdapter;
import com.shixxels.thankgodrichard.spotpopfinal.adapter.SearchAdapter;
import com.shixxels.thankgodrichard.spotpopfinal.model.NotificationModel;
import com.shixxels.thankgodrichard.spotpopfinal.model.SearchModel;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 */
public class Notification extends Fragment {


    public Notification() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.segment_notifications, container, false);

        setUpRecyclerView(v);

        return  v;
    }

    private void setUpRecyclerView(View v) {

        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView7);
        NotificationAdapter adapter = new NotificationAdapter(getContext(), NotificationModel.getData());
        recyclerView.setAdapter(adapter);

        LinearLayoutManager mLinearLayoutManagerVertical = new LinearLayoutManager(getContext()); // (Context context, int spanCount)
        mLinearLayoutManagerVertical.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLinearLayoutManagerVertical);

        recyclerView.setItemAnimator(new DefaultItemAnimator()); // Even if we dont use it then also our items shows default animation. #Check Docs
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((MainActivity) getActivity()).setBackground(4);
    }
}

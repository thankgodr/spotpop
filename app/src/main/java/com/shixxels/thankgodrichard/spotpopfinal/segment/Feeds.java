package com.shixxels.thankgodrichard.spotpopfinal.segment;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.qb.gson.reflect.TypeToken;
import com.quickblox.auth.QBAuth;
import com.quickblox.auth.model.QBSession;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.exception.QBResponseException;
import com.quickblox.core.request.QBRequestGetBuilder;
import com.quickblox.customobjects.QBCustomObjects;
import com.quickblox.customobjects.model.QBCustomObject;
import com.shixxels.thankgodrichard.spotpopfinal.Helpers;
import com.shixxels.thankgodrichard.spotpopfinal.MainActivity;
import com.shixxels.thankgodrichard.spotpopfinal.R;
import com.shixxels.thankgodrichard.spotpopfinal.adapter.RecyclerAdapter;
import com.shixxels.thankgodrichard.spotpopfinal.model.FeedAdapter;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Feeds extends Fragment {
     View view;
     ArrayList<QBCustomObject> objects;
    Helpers app = Helpers.getInstance();
    List<Object> followers;
    public Feeds() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         view = inflater.inflate(R.layout.segment_feeds, container, false);
        Type listOfObjects = new TypeToken<ArrayList<QBCustomObject>>() {
        }.getType();
        SharedPreferences preference = getContext().getSharedPreferences(app.Pref, getContext().MODE_PRIVATE);
        String s = preference.getString("Followers","");
        if(s.length() > 1){
            Gson gson = new Gson();
            ArrayList<QBCustomObject> objects = gson.fromJson(s,listOfObjects);
            if(objects != null && !objects.isEmpty()){
                followers = objects.get(0).getArray("followingIds");
                QBAuth.createSession(new QBEntityCallback<QBSession>() {
                    @Override
                    public void onSuccess(QBSession session, Bundle params) {
                        QBRequestGetBuilder requestGetBuilder = new QBRequestGetBuilder();
                        for(int i = 0; i< followers.size(); i++){
                            requestGetBuilder.eq("userId",followers.get(i));
                        }
                        // success
                        requestGetBuilder.setPagesLimit(999);
                        QBCustomObjects.getObjects("Feeds", requestGetBuilder, new QBEntityCallback<ArrayList<QBCustomObject>>() {
                            @Override
                            public void onSuccess(ArrayList<QBCustomObject> customObjects, Bundle params) {
                                setUpRecyclerView(view,customObjects);

                            }

                            @Override
                            public void onError(QBResponseException errors) {
                                Log.i("sewx736", String.valueOf(errors));
                            }
                        });
                    }

                    @Override
                    public void onError(QBResponseException error) {
                        // errors
                        Log.i("session", "No created");
                    }
                });

            }

        }
        return view;
    }


    private void setUpRecyclerView(View v, ArrayList<QBCustomObject> objects) {

        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        RecyclerAdapter adapter = new RecyclerAdapter(getContext(), FeedAdapter.getData(getContext(),objects),getActivity());
        recyclerView.setAdapter(adapter);

        LinearLayoutManager mLinearLayoutManagerVertical = new LinearLayoutManager(getContext()); // (Context context, int spanCount)
        mLinearLayoutManagerVertical.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLinearLayoutManagerVertical);

        recyclerView.setItemAnimator(new DefaultItemAnimator()); // Even if we dont use it then also our items shows default animation. #Check Docs
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((MainActivity) getActivity()).setBackground(1);

    }



}

package com.elsawy.ahmed.sqlaskproject.HomeTabs;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.elsawy.ahmed.sqlaskproject.R;


public class DiscoverTab extends Fragment {


    public DiscoverTab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.home_discover_tab, container, false);

//        mDatabase = FirebaseDatabase.getInstance().getReference();
//
//        mRecycler = rootView.findViewById(R.id.recycler_view_home_discover);
//        mRecycler.addItemDecoration(new VerticalSpaceItemDecoration(10));
//        mRecycler.setItemAnimator(new DefaultItemAnimator());

        return rootView;
    }

//    @Override
//    public Query getQuery(DatabaseReference databaseReference) {
//        Query myTopPostsQuery = databaseReference.child("user-objects").child("global-answers").orderByChild("likesCount");
//
//        return myTopPostsQuery;
//    }


}

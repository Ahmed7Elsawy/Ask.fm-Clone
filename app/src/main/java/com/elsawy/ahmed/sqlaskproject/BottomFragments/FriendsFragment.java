package com.elsawy.ahmed.sqlaskproject.BottomFragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.elsawy.ahmed.sqlaskproject.R;
import com.elsawy.ahmed.sqlaskproject.Utils.VerticalSpaceItemDecoration;
import com.elsawy.ahmed.sqlaskproject.adapter.FriendsAdapter;
import com.elsawy.ahmed.sqlaskproject.adapter.PeopleMayKnowAdapter;


public class FriendsFragment extends Fragment {


    public FriendsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.bottom_friends, container, false);

        RecyclerView friendsRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view_friends);
        friendsRecyclerView.addItemDecoration(new VerticalSpaceItemDecoration(1));
        FriendsAdapter friendsAdapter = new FriendsAdapter(getActivity());
        friendsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        friendsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        friendsRecyclerView.setAdapter(friendsAdapter);

        RecyclerView peopleMayKnowRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view_people_may_be_know);
        peopleMayKnowRecyclerView.addItemDecoration(new VerticalSpaceItemDecoration(1));
        PeopleMayKnowAdapter peopleMayKnowAdapter = new PeopleMayKnowAdapter(getActivity());
        peopleMayKnowRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        peopleMayKnowRecyclerView.setItemAnimator(new DefaultItemAnimator());
        peopleMayKnowRecyclerView.setAdapter(peopleMayKnowAdapter);

        return rootView;
    }

}

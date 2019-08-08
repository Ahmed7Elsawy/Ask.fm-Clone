package com.elsawy.ahmed.sqlaskproject.HomeTabs;


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
import com.elsawy.ahmed.sqlaskproject.adapter.AnswerAdapter;


public class DiscoverTab extends Fragment {

    private int profileID;
    private RecyclerView mRecycler;

    public DiscoverTab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.home_discover_tab, container, false);


        mRecycler = rootView.findViewById(R.id.recycler_view_home_discover);
        mRecycler.addItemDecoration(new VerticalSpaceItemDecoration(10));
        mRecycler.setItemAnimator(new DefaultItemAnimator());
        mRecycler.setLayoutManager(new LinearLayoutManager(DiscoverTab.this.getContext()));

        AnswerAdapter adapter = new AnswerAdapter(DiscoverTab.this.getContext(),"DiscoverTab");
        mRecycler.setAdapter(adapter);

        return rootView;
    }

}

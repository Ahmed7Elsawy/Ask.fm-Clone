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

    private RecyclerView DiscoverTabRecyclerView;
    private final String tab = "DiscoverTab";

    public DiscoverTab() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.home_discover_tab, container, false);

        DiscoverTabRecyclerView = rootView.findViewById(R.id.recycler_view_home_discover);
        DiscoverTabRecyclerView.addItemDecoration(new VerticalSpaceItemDecoration(10));
        DiscoverTabRecyclerView.setItemAnimator(new DefaultItemAnimator());
        DiscoverTabRecyclerView.setLayoutManager(new LinearLayoutManager(DiscoverTab.this.getContext()));

        AnswerAdapter DiscoverTabAnswerAdapter = new AnswerAdapter(DiscoverTab.this.getContext(), tab);
        DiscoverTabRecyclerView.setAdapter(DiscoverTabAnswerAdapter);

        return rootView;
    }

}

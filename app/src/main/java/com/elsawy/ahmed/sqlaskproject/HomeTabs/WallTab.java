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

public class WallTab extends Fragment {

    private RecyclerView wallTabRecyclerView;
    private final String tab = "WallTab";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.home_wall_tab, container, false);

        wallTabRecyclerView = rootView.findViewById(R.id.recycler_view_home_wall);
        wallTabRecyclerView.addItemDecoration(new VerticalSpaceItemDecoration(10));
        wallTabRecyclerView.setItemAnimator(new DefaultItemAnimator());
        wallTabRecyclerView.setLayoutManager(new LinearLayoutManager(WallTab.this.getContext()));

        AnswerAdapter wallTabAnswerAdapter = new AnswerAdapter(WallTab.this.getContext(), tab);
        wallTabRecyclerView.setAdapter(wallTabAnswerAdapter);

        return rootView;
    }

}

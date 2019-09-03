package com.elsawy.ahmed.sqlaskproject.HomeTabs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.elsawy.ahmed.sqlaskproject.ProfileTabs.AnswerProfileTab;
import com.elsawy.ahmed.sqlaskproject.R;
import com.elsawy.ahmed.sqlaskproject.Utils.VerticalSpaceItemDecoration;
import com.elsawy.ahmed.sqlaskproject.adapter.AnswerAdapter;

public class WallTab extends Fragment {

    String TAG = "WallTab";
    private int profileID;
    private RecyclerView mRecycler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.home_wall_tab, container, false);

        mRecycler = rootView.findViewById(R.id.recycler_view_home_wall);
        mRecycler.addItemDecoration(new VerticalSpaceItemDecoration(10));
        mRecycler.setItemAnimator(new DefaultItemAnimator());
        mRecycler.setLayoutManager(new LinearLayoutManager(WallTab.this.getContext()));

        AnswerAdapter answerAdapter = new AnswerAdapter(WallTab.this.getContext(),"WallTab");
        mRecycler.setAdapter(answerAdapter);

        return rootView;
    }

}

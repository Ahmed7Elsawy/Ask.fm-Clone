package com.elsawy.ahmed.sqlaskproject.ProfileTabs;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.elsawy.ahmed.sqlaskproject.R;
import com.elsawy.ahmed.sqlaskproject.Utils.VerticalSpaceItemDecoration;
import com.elsawy.ahmed.sqlaskproject.adapter.AnswerAdapter;


public class LikeProfileTab extends Fragment {

    private String profileID;
    private RecyclerView mRecycler;

    public LikeProfileTab() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle bundle = getArguments();
        if (bundle != null) {
            if (bundle.containsKey("profileID")) {
                profileID = bundle.getString("profileID");
            }
        }

        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.profile_like_tab, container, false);


        mRecycler = rootView.findViewById(R.id.recycler_view_profile_like);
        mRecycler.addItemDecoration(new VerticalSpaceItemDecoration(10));
        mRecycler.setItemAnimator(new DefaultItemAnimator());
        mRecycler.setLayoutManager(new LinearLayoutManager(LikeProfileTab.this.getContext()));

        AnswerAdapter adapter = new AnswerAdapter(LikeProfileTab.this.getContext(), profileID, "LikeTab");
        mRecycler.setAdapter(adapter);

        return rootView;
    }

}
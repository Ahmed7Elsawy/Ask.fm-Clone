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


public class AnswerProfileTab extends Fragment {

    private String profileID;
    private RecyclerView answerProfileRecyclerView;
    private final String tab = "ProfileTab";

    public AnswerProfileTab() {
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
        View rootView = inflater.inflate(R.layout.profile_answer_tab, container, false);

        answerProfileRecyclerView = rootView.findViewById(R.id.recycler_view_profile_answers);
        answerProfileRecyclerView.addItemDecoration(new VerticalSpaceItemDecoration(10));
        answerProfileRecyclerView.setItemAnimator(new DefaultItemAnimator());
        answerProfileRecyclerView.setLayoutManager(new LinearLayoutManager(AnswerProfileTab.this.getContext()));

        AnswerAdapter answerAdapter = new AnswerAdapter(AnswerProfileTab.this.getContext(), profileID, tab);
        answerProfileRecyclerView.setAdapter(answerAdapter);

        return rootView;

    }

}
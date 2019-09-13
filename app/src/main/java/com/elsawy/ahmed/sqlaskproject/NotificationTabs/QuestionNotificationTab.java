package com.elsawy.ahmed.sqlaskproject.NotificationTabs;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.elsawy.ahmed.sqlaskproject.R;
import com.elsawy.ahmed.sqlaskproject.adapter.QuestionAdapter;

public class QuestionNotificationTab extends Fragment {

    RecyclerView questionNotificationRecyclerView;
    QuestionAdapter mAdapter;

    public QuestionNotificationTab() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.notification_question_tab, container, false);
        this.questionNotificationRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view_questions_notification);
        this.questionNotificationRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        this.questionNotificationRecyclerView.setItemAnimator(new DefaultItemAnimator());

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        // we define adapter in onResume to listen to the deleted question after answer it
        this.mAdapter = new QuestionAdapter(getActivity());
        this.questionNotificationRecyclerView.setAdapter(this.mAdapter);
    }

}
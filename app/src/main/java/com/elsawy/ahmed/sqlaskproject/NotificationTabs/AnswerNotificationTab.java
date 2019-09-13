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
import com.elsawy.ahmed.sqlaskproject.Utils.VerticalSpaceItemDecoration;
import com.elsawy.ahmed.sqlaskproject.adapter.AnswerNotificationAdapter;

public class AnswerNotificationTab extends Fragment {

    private RecyclerView answerNotificationRecyclerView;
    private AnswerNotificationAdapter answerNotificationAdapter;
    private final String tab = "answersNotification";

    public AnswerNotificationTab() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.notification_answer_tab, container, false);

        this.answerNotificationRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view_answers_notification);
        this.answerNotificationRecyclerView.addItemDecoration(new VerticalSpaceItemDecoration(5));
        this.answerNotificationRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        this.answerNotificationRecyclerView.setItemAnimator(new DefaultItemAnimator());
        this.answerNotificationAdapter = new AnswerNotificationAdapter(getActivity(), tab);
        this.answerNotificationRecyclerView.setAdapter(this.answerNotificationAdapter);

        return rootView;
    }

}
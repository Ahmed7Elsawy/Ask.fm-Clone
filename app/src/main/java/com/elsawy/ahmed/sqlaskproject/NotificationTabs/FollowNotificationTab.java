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

public class FollowNotificationTab extends Fragment {

    private RecyclerView favoriteNotificationRecyclerView;
    private AnswerNotificationAdapter favoriteNotificationAdapter;


    public FollowNotificationTab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.notification_follow_tab, container, false);

        this.favoriteNotificationRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view_favorite_notification);
        this.favoriteNotificationAdapter = new AnswerNotificationAdapter(getActivity(), "favoriteNotification");

        this.favoriteNotificationRecyclerView.addItemDecoration(new VerticalSpaceItemDecoration(5));
        this.favoriteNotificationRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        this.favoriteNotificationRecyclerView.setItemAnimator(new DefaultItemAnimator());
        this.favoriteNotificationRecyclerView.setAdapter(this.favoriteNotificationAdapter);
        return rootView;

    }

}

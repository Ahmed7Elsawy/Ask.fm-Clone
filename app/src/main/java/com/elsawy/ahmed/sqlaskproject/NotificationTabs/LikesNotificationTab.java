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
import com.elsawy.ahmed.sqlaskproject.Utils.Constants;
import com.elsawy.ahmed.sqlaskproject.Utils.VerticalSpaceItemDecoration;
import com.elsawy.ahmed.sqlaskproject.adapter.AnswerNotificationAdapter;

public class LikesNotificationTab extends Fragment {

    private RecyclerView likesNotificationRecyclerView;
    private AnswerNotificationAdapter likesNotificationAdapter;
    private final String tab = Constants.LIKES_NOTIFICATION;

    public LikesNotificationTab() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.notification_likes_tab, container, false);

        this.likesNotificationRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view_likes_notification);
        this.likesNotificationAdapter = new AnswerNotificationAdapter(getActivity(), tab);

        this.likesNotificationRecyclerView.addItemDecoration(new VerticalSpaceItemDecoration(5));
        this.likesNotificationRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        this.likesNotificationRecyclerView.setItemAnimator(new DefaultItemAnimator());
        this.likesNotificationRecyclerView.setAdapter(this.likesNotificationAdapter);

        return rootView;
    }

}
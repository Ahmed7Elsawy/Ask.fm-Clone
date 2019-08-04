package com.elsawy.ahmed.sqlaskproject.ProfileTabs;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.elsawy.ahmed.sqlaskproject.R;


public class LikeProfileTab extends Fragment {

    String profileID;

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


//        mRecycler = rootView.findViewById(R.id.recycler_view_profile_like);
//        mRecycler.addItemDecoration(new VerticalSpaceItemDecoration(10));
//        mRecycler.setItemAnimator(new DefaultItemAnimator());

        return rootView;
    }

//    @Override
//    public Query getQuery(DatabaseReference databaseReference) {
//        String myUserId = getUid();
//        Query myTopPostsQuery = databaseReference.child("user-objects").child("user-answers").child(profileID)
//                .orderByChild("likesCount");
//
//        return myTopPostsQuery;
//    }

}

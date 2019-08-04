package com.elsawy.ahmed.sqlaskproject.ProfileTabs;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.elsawy.ahmed.sqlaskproject.R;


public class BioProfileTab extends Fragment {

    public BioProfileTab() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.profile_bio_tab, container, false);
    }

}

package com.elsawy.ahmed.sqlaskproject.HomeTabs;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.elsawy.ahmed.sqlaskproject.R;


public class VersusTab extends Fragment {


    public VersusTab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.home_versus_tab, container, false);

        return rootView;
    }

}

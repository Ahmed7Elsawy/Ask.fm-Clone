package com.elsawy.ahmed.sqlaskproject.BottomFragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.elsawy.ahmed.sqlaskproject.HomeTabs.DiscoverTab;
import com.elsawy.ahmed.sqlaskproject.HomeTabs.WallTab;
import com.elsawy.ahmed.sqlaskproject.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    String TAG = "HomeFragment";
    ViewPager viewPager;
    ViewPagerAdapter adapter;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.bottom_home, container, false);

        this.viewPager = (ViewPager) rootView.findViewById(R.id.viewpager_home);
        setupViewPager(this.viewPager);
        ((TabLayout) rootView.findViewById(R.id.home_tabs)).setupWithViewPager(this.viewPager);

        return rootView;
    }

    private void setupViewPager(ViewPager viewPager) {
        this.adapter = new ViewPagerAdapter(getFragmentManager());
        this.adapter.addFragment(new WallTab(), "WALL");
        this.adapter.addFragment(new DiscoverTab(), "DISCOVER");
        viewPager.setAdapter(this.adapter);
    }

    public class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList();
        private final List<String> mFragmentTitleList = new ArrayList();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        public Fragment getItem(int position) {
            return (Fragment) this.mFragmentList.get(position);
        }

        public int getCount() {
            return this.mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            this.mFragmentList.add(fragment);
            this.mFragmentTitleList.add(title);
        }

        public CharSequence getPageTitle(int position) {
            return (CharSequence) this.mFragmentTitleList.get(position);
        }
    }

}

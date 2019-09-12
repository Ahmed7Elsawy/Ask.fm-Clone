package com.elsawy.ahmed.sqlaskproject.BottomFragments;


import android.os.Bundle;
import android.util.Log;
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

public class HomeFragment extends Fragment{

    String TAG = "HomeFragment";
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;
    private TabLayout tabLayout;

    private WallTab wallTab;
    private DiscoverTab discoverTab;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.i("HomeFragment","onCreateView");

        View rootView = inflater.inflate(R.layout.bottom_home, container, false);

        this.viewPager = (ViewPager) rootView.findViewById(R.id.viewpager_home);
        tabLayout = (TabLayout) rootView.findViewById(R.id.home_tabs);

        setupViewPager();
        tabLayout.setupWithViewPager(this.viewPager);

        return rootView;
    }

    private void setupViewPager() {
        this.adapter = new ViewPagerAdapter(getFragmentManager());
        wallTab = new WallTab();
        discoverTab = new DiscoverTab();
        this.adapter.addFragment(wallTab, "WALL");
        this.adapter.addFragment(discoverTab, "DISCOVER");
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

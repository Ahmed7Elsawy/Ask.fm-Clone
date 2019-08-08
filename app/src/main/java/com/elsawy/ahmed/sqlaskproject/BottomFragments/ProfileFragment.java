package com.elsawy.ahmed.sqlaskproject.BottomFragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.elsawy.ahmed.sqlaskproject.ProfileTabs.AnswerProfileTab;
import com.elsawy.ahmed.sqlaskproject.ProfileTabs.BioProfileTab;
import com.elsawy.ahmed.sqlaskproject.ProfileTabs.LikeProfileTab;
import com.elsawy.ahmed.sqlaskproject.R;
import com.elsawy.ahmed.sqlaskproject.SharedPrefManager;
import com.elsawy.ahmed.sqlaskproject.models.Friend;
import com.google.android.material.tabs.TabLayout;
import java.util.ArrayList;

public class ProfileFragment extends Fragment {

    private String TAG = "ProfileFragment";
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;
    private TabLayout tabLayout;
    private Button profileAskBtn, profileFollowBtn;
    private LinearLayout followerCountAndOnlineLayout;
    private LinearLayout profileFriendAndFavoriteLayout;
    private TextView profileUsername;

    private int[] tabIcons = {
            R.drawable.ic_answer_black_24dp,
            R.drawable.ic_like_black_24dp,
            R.drawable.ic_person_black_24dp
    };

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.bottom_profile, container, false);

        this.viewPager = (ViewPager) rootView.findViewById(R.id.viewpager_profile);
        this.tabLayout = (TabLayout) rootView.findViewById(R.id.profile_tabs);
        this.followerCountAndOnlineLayout =(LinearLayout) rootView.findViewById(R.id.follower_count_and_online_layout);
        this.profileFriendAndFavoriteLayout = (LinearLayout) rootView.findViewById(R.id.profile_friend_and_favorite_layout);
        this.profileAskBtn = (Button) rootView.findViewById(R.id.profile_ask_btn);
        this.profileFollowBtn = (Button) rootView.findViewById(R.id.profile_follow_btn);
        this.profileUsername = (TextView)rootView.findViewById(R.id.username_profile);

        setupProfileHeader();

        setupViewPager(this.viewPager);
        tabLayout.setupWithViewPager(this.viewPager);
        setupTabIcons();

        return rootView;

    }

    private void setupProfileHeader() {
        profileFollowBtn.setVisibility(View.GONE);
        profileFriendAndFavoriteLayout.setVisibility(View.GONE);
        followerCountAndOnlineLayout.setVisibility(View.VISIBLE);
        profileAskBtn.setVisibility(View.VISIBLE);
        profileAskBtn.setText(getResources().getString(R.string.ask_yourself));

        profileUsername.setText(SharedPrefManager.getInstance(this.getContext()).getUsername());
    }



    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
    }
    private void setupViewPager(ViewPager viewPager) {
        this.adapter = new ViewPagerAdapter(getFragmentManager());

        Bundle args = new Bundle();
        args.putString("profileID", SharedPrefManager.getInstance(ProfileFragment.this.getContext()).getUserId());

        AnswerProfileTab answerProfileTab = new AnswerProfileTab();
        answerProfileTab.setArguments(args);
        LikeProfileTab likeProfileTab = new LikeProfileTab();
        likeProfileTab.setArguments(args);
        BioProfileTab bioProfileTab = new BioProfileTab();
        bioProfileTab.setArguments(args);

        this.adapter.addFragment(answerProfileTab, "0");
        this.adapter.addFragment(likeProfileTab, "0");
        this.adapter.addFragment(bioProfileTab, "bio");
        viewPager.setAdapter(this.adapter);
    }

    public class ViewPagerAdapter extends FragmentPagerAdapter {
        private final ArrayList mFragmentList = new ArrayList();
        private final ArrayList mFragmentTitleList = new ArrayList();

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
package com.elsawy.ahmed.sqlaskproject.BottomFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.elsawy.ahmed.sqlaskproject.NotificationTabs.AnswerNotificationTab;
import com.elsawy.ahmed.sqlaskproject.NotificationTabs.FollowNotificationTab;
import com.elsawy.ahmed.sqlaskproject.NotificationTabs.LikesNotificationTab;
import com.elsawy.ahmed.sqlaskproject.NotificationTabs.QuestionNotificationTab;
import com.elsawy.ahmed.sqlaskproject.R;

import com.google.android.material.tabs.TabLayout;
import java.util.ArrayList;
import java.util.List;

public class NotificationFragment extends Fragment {
    ViewPager viewPager;
    ViewPagerAdapter adapter;
    TabLayout tabLayout;
    private int[] tabIcons = {
            R.drawable.ic_question_mark_black_24dp,
            R.drawable.ic_answer_black_24dp,
            R.drawable.ic_like_black_24dp,
            R.drawable.ic_three_dots_black_24dp
    };

    public NotificationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.bottom_notification, container, false);

        this.viewPager = (ViewPager) rootView.findViewById(R.id.viewpager_notification);
        setupViewPager(this.viewPager);
        tabLayout = (TabLayout) rootView.findViewById(R.id.notification_tabs);
        tabLayout.setupWithViewPager(this.viewPager);
        setupTabIcons();
        return rootView;

    }
    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
        tabLayout.getTabAt(3).setIcon(tabIcons[3]);
    }
    private void setupViewPager(ViewPager viewPager) {
        this.adapter = new ViewPagerAdapter(getFragmentManager());
        this.adapter.addFragment(new QuestionNotificationTab(), "Question");
        this.adapter.addFragment(new AnswerNotificationTab(), "Answer");
        this.adapter.addFragment(new LikesNotificationTab(), "Likes");
        this.adapter.addFragment(new FollowNotificationTab(), "Follow");
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
            return null;//(CharSequence) this.mFragmentTitleList.get(position);
        }
    }

}

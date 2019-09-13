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
import com.elsawy.ahmed.sqlaskproject.NotificationTabs.FavoriteNotificationTab;
import com.elsawy.ahmed.sqlaskproject.NotificationTabs.LikesNotificationTab;
import com.elsawy.ahmed.sqlaskproject.NotificationTabs.QuestionNotificationTab;
import com.elsawy.ahmed.sqlaskproject.R;

import com.google.android.material.tabs.TabLayout;
import java.util.ArrayList;
import java.util.List;

public class NotificationFragment extends Fragment {

    private ViewPager notificationViewPager;
    private ViewPagerAdapter notificationPagerAdapter;
    private TabLayout notificationTabLayout;

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

        this.notificationViewPager = (ViewPager) rootView.findViewById(R.id.viewpager_notification);
        setupViewPager(this.notificationViewPager);
        notificationTabLayout = (TabLayout) rootView.findViewById(R.id.notification_tabs);
        notificationTabLayout.setupWithViewPager(this.notificationViewPager);
        setupTabIcons();

        return rootView;

    }


    private void setupTabIcons() {
        notificationTabLayout.getTabAt(0).setIcon(tabIcons[0]);
        notificationTabLayout.getTabAt(1).setIcon(tabIcons[1]);
        notificationTabLayout.getTabAt(2).setIcon(tabIcons[2]);
        notificationTabLayout.getTabAt(3).setIcon(tabIcons[3]);
    }

    private void setupViewPager(ViewPager viewPager) {
        this.notificationPagerAdapter = new ViewPagerAdapter(getFragmentManager());
        this.notificationPagerAdapter.addFragment(new QuestionNotificationTab(), "Question");
        this.notificationPagerAdapter.addFragment(new AnswerNotificationTab(), "Answer");
        this.notificationPagerAdapter.addFragment(new LikesNotificationTab(), "Likes");
        this.notificationPagerAdapter.addFragment(new FavoriteNotificationTab(), "Follow");
        viewPager.setAdapter(this.notificationPagerAdapter);

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

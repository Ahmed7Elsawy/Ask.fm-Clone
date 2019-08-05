package com.elsawy.ahmed.sqlaskproject.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;
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
import com.elsawy.ahmed.sqlaskproject.models.Question;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ProfileActivity extends AppCompatActivity {

    private String TAG = "ProfileActivity";
    ViewPager viewPager;
    ViewPagerAdapter adapter;
    TabLayout tabLayout;

    private TextView username;
    private ImageView favoriteImage;
    private ImageView userAcceptedImage;
    private Button follow_btn;
    private Button ask_btn;

    private Friend mfriend;
    private String profileID ;

    private int[] tabIcons = {
            R.drawable.ic_answer_black_24dp,
            R.drawable.ic_like_black_24dp,
            R.drawable.ic_person_black_24dp
    };

    @Override
    protected void onStart() {
        super.onStart();
        Objects.requireNonNull(getSupportActionBar()).hide();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bottom_profile);


        setupProfileHeader();
        this.mfriend = (Friend) getIntent().getParcelableExtra("ProfileInfo");
        if(mfriend != null)
            putProfileInfo(mfriend);

        this.viewPager = (ViewPager) findViewById(R.id.viewpager_profile);
        setupViewPager(this.viewPager);
        tabLayout = (TabLayout) findViewById(R.id.profile_tabs);
        tabLayout.setupWithViewPager(this.viewPager);
        setupTabIcons();

        ask_btn.setOnClickListener(new AskBtnListener());

    }

    private class AskBtnListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Question question = new Question();
            String userID = String.valueOf(SharedPrefManager.getInstance(ProfileActivity.this).getUserId());
            question.setAskerID(userID);
            question.setReceiverID(profileID);
            Intent intent=new Intent(ProfileActivity.this, AskActivity.class);
            intent.putExtra("questionInfo",question);
            ProfileActivity.this.startActivity(intent);

        }
    }

    private void putProfileInfo(Friend mFriend) {
        profileID = mFriend.getFriendID();
//        follow_btn.setVisibility(View.GONE);

        username.setText(mFriend.getFriendName());

        if (mFriend.getFavorite())
            favoriteImage.setImageResource(R.drawable.ic_stars_yellow_24dp);
        else
            favoriteImage.setImageResource(R.drawable.ic_star_black_24dp);
    }

    private void setupProfileHeader(){
        username = (TextView)findViewById(R.id.username_profile);
        favoriteImage = (ImageView) findViewById(R.id.favorite_image_profile);
        userAcceptedImage = (ImageView)findViewById(R.id.user_accepted_profile);
        follow_btn = (Button) findViewById(R.id.profile_follow_btn);
        ask_btn = (Button) findViewById(R.id.profile_ask_btn);
    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
    }

    private void setupViewPager(ViewPager viewPager) {
        this.adapter = new ViewPagerAdapter(getSupportFragmentManager());
        Bundle args = new Bundle();
        args.putString("profileID", mfriend.getFriendID());

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
        private final List<Fragment> mFragmentList = new ArrayList();
        private final List<String> mFragmentTitleList = new ArrayList();

        ViewPagerAdapter(FragmentManager manager) {
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

    @Override
    protected void onStop() {
        super.onStop();
        Objects.requireNonNull(getSupportActionBar()).show();
    }
}
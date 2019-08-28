package com.elsawy.ahmed.sqlaskproject.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.elsawy.ahmed.sqlaskproject.ProfileTabs.AnswerProfileTab;
import com.elsawy.ahmed.sqlaskproject.ProfileTabs.BioProfileTab;
import com.elsawy.ahmed.sqlaskproject.ProfileTabs.LikeProfileTab;
import com.elsawy.ahmed.sqlaskproject.R;
import com.elsawy.ahmed.sqlaskproject.RequestHandler;
import com.elsawy.ahmed.sqlaskproject.SharedPrefManager;
import com.elsawy.ahmed.sqlaskproject.Utils.Constants;
import com.elsawy.ahmed.sqlaskproject.models.Question;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ProfileActivity extends AppCompatActivity {

    private String TAG = "ProfileActivity";
    ViewPager viewPager;
    ViewPagerAdapter adapter;
    TabLayout tabLayout;

    private TextView usernameTV;
    private ImageView favoriteImage;
    private ImageView userAcceptedImage;
    private Button follow_btn;
    private Button ask_btn;
    private LinearLayout friend_and_favorite_layout;

    private String profileID,profileUsername;
    private boolean friendFavorite;

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
        Intent intent = getIntent();
        this.profileID = intent.getStringExtra("ProfileID");
        this.profileUsername = intent.getStringExtra("ProfileUsername");
        boolean isFriend = intent.getBooleanExtra("isFriend",false);

        if(isFriend) {
            String stringFavorite = intent.getStringExtra("friendFavorite");
            Log.i("fav",stringFavorite+"");    friendFavorite = stringFavorite.equals("true");
            putFriendProfileInfo(friendFavorite);
        }else {
            putUnFriendProfileInfo();
            getProfileInfo(this.profileID);
        }

        setupViewPager();
        setupTabIcons();

        ask_btn.setOnClickListener(new AskBtnListener());
        favoriteImage.setOnClickListener(new FavoriteBtnListener());
        follow_btn.setOnClickListener(e -> addFriend());

    }

    private void addFriend() {
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.URL_FRIENDS,
                response -> {
                    try {
                        JSONObject obj = new JSONObject(response);
                        if (obj.getBoolean("error")) {
                            Toast.makeText(ProfileActivity.this, obj.getString("message"), Toast.LENGTH_LONG).show();
                        }else {
                            putFriendProfileInfo(false);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    Toast.makeText(ProfileActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", String.valueOf(SharedPrefManager.getInstance(ProfileActivity.this).getUserId()));
                params.put("friend_id", profileID);
                params.put("add", "add");

                return params;
            }

        };

        RequestHandler.getInstance(ProfileActivity.this).addToRequestQueue(stringRequest);

    }


    private class AskBtnListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Question question = new Question();
            String userID =SharedPrefManager.getInstance(ProfileActivity.this).getUserId();
            question.setAskerID(userID);
            question.setReceiverID(profileID);
            Intent intent=new Intent(ProfileActivity.this, AskActivity.class);
            intent.putExtra("questionInfo",question);
            ProfileActivity.this.startActivity(intent);

        }
    }

    private class FavoriteBtnListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if(friendFavorite){
                friendFavorite = false;
            }else {
                friendFavorite= true;
            }

            updateFavorite(profileID, friendFavorite);
            setFavoriteImage(friendFavorite);
        }
    }

    private void setFavoriteImage(Boolean isFavorite){
        if (isFavorite)
            favoriteImage.setImageResource(R.drawable.ic_stars_yellow_24dp);
        else
            favoriteImage.setImageResource(R.drawable.ic_star_black_24dp);
    }

    private void putFriendProfileInfo(boolean favorite) {
        follow_btn.setVisibility(View.GONE);
        friend_and_favorite_layout.setVisibility(View.VISIBLE);

        usernameTV.setText(this.profileUsername);

        if (favorite)
            favoriteImage.setImageResource(R.drawable.ic_stars_yellow_24dp);
        else
            favoriteImage.setImageResource(R.drawable.ic_star_black_24dp);
    }

    private void putUnFriendProfileInfo() {
        friend_and_favorite_layout.setVisibility(View.GONE);
        follow_btn.setVisibility(View.VISIBLE);

        usernameTV.setText(this.profileUsername);
    }

    private void setupProfileHeader(){
        usernameTV = (TextView)findViewById(R.id.username_profile);
        favoriteImage = (ImageView) findViewById(R.id.favorite_image_profile);
        userAcceptedImage = (ImageView)findViewById(R.id.user_accepted_profile);
        follow_btn = (Button) findViewById(R.id.profile_follow_btn);
        ask_btn = (Button) findViewById(R.id.profile_ask_btn);
        friend_and_favorite_layout = (LinearLayout) findViewById(R.id.profile_friend_and_favorite_layout);
    }

    private void setupTabIcons() {
        tabLayout = (TabLayout) findViewById(R.id.profile_tabs);
        tabLayout.setupWithViewPager(this.viewPager);
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
    }

    private void setupViewPager() {
        this.viewPager = (ViewPager) findViewById(R.id.viewpager_profile);
        this.adapter = new ViewPagerAdapter(getSupportFragmentManager());
        Bundle args = new Bundle();
        args.putString("profileID", this.profileID);

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

    private void updateFavorite(String friend_id, boolean favorite) {
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.URL_FRIENDS,
                response -> {
                    try {
                        JSONObject obj = new JSONObject(response);

                        if (obj.getBoolean("error")) {
                            Toast.makeText(ProfileActivity.this, obj.getString("message"), Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {

                    Toast.makeText(ProfileActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", String.valueOf(SharedPrefManager.getInstance(ProfileActivity.this).getUserId()));
                params.put("friend_id", friend_id);
                params.put("favorite", String.valueOf(favorite));

                return params;
            }

        };

        RequestHandler.getInstance(ProfileActivity.this).addToRequestQueue(stringRequest);

    }

    private void getProfileInfo(String profile_id) {
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.URL_FRIENDS,
                response -> {
                    try {
                        JSONObject obj = new JSONObject(response);
                        Log.i("profileObj",obj.toString());

                        if (obj.getBoolean("error")) {
                            Toast.makeText(ProfileActivity.this, obj.getString("message"), Toast.LENGTH_LONG).show();
                        }else {
                            if(obj.getBoolean("isFriend")){
                                Log.i("favorite",obj.toString());
                                putFriendProfileInfo(obj.getBoolean("favorite"));
                            }else {
                                Log.i("isFriend",obj.toString());
                                putUnFriendProfileInfo();
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {

                    Toast.makeText(ProfileActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", String.valueOf(SharedPrefManager.getInstance(ProfileActivity.this).getUserId()));
                params.put("profile_id", profile_id);

                return params;
            }
        };
        RequestHandler.getInstance(ProfileActivity.this).addToRequestQueue(stringRequest);
    }

}
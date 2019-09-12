package com.elsawy.ahmed.sqlaskproject.BottomFragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.elsawy.ahmed.sqlaskproject.ProfileTabs.AnswerProfileTab;
import com.elsawy.ahmed.sqlaskproject.ProfileTabs.BioProfileTab;
import com.elsawy.ahmed.sqlaskproject.ProfileTabs.LikeProfileTab;
import com.elsawy.ahmed.sqlaskproject.R;
import com.elsawy.ahmed.sqlaskproject.RequestHandler;
import com.elsawy.ahmed.sqlaskproject.SharedPrefManager;
import com.elsawy.ahmed.sqlaskproject.Utils.Constants;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {

    private String TAG = "ProfileFragment";
    final int RESULT_LOAD_IMG = 2;
    final int RESULT_OK = -1;

    private ViewPager viewPager;
    private ViewPagerAdapter adapter;
    private TabLayout tabLayout;
    private Button profileAskBtn, profileFollowBtn;
    private CircleImageView profileImageView;
    private LinearLayout followerCountAndOnlineLayout;
    private LinearLayout profileFriendAndFavoriteLayout;
    private TextView profileUsername;

    private Bitmap bitmap;
    private Uri filePath;

    private View.OnClickListener profileImageViewClickListener = view -> {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);

    };

    private int[] tabIcons = { R.drawable.ic_answer_black_24dp, R.drawable.ic_like_black_24dp, R.drawable.ic_person_black_24dp};

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.bottom_profile, container, false);

        this.viewPager = (ViewPager) rootView.findViewById(R.id.viewpager_profile);
        this.tabLayout = (TabLayout) rootView.findViewById(R.id.profile_tabs);
        this.followerCountAndOnlineLayout =(LinearLayout) rootView.findViewById(R.id.follower_count_and_online_layout);
        this.profileFriendAndFavoriteLayout = (LinearLayout) rootView.findViewById(R.id.profile_friend_and_favorite_layout);
        this.profileAskBtn = (Button) rootView.findViewById(R.id.profile_ask_btn);
        this.profileFollowBtn = (Button) rootView.findViewById(R.id.profile_follow_btn);
        this.profileUsername = (TextView)rootView.findViewById(R.id.username_profile);
        this.profileImageView = (CircleImageView) rootView.findViewById(R.id.profile_image);

        setupProfileHeader();

        setupViewPager(this.viewPager);
        tabLayout.setupWithViewPager(this.viewPager);
        setupTabIcons();
        tabLayout.getTabAt(0).select();

        return rootView;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);
                profileImageView.setImageBitmap(bitmap);
                uploadImage();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void setupProfileHeader() {
        profileFollowBtn.setVisibility(View.GONE);
        profileFriendAndFavoriteLayout.setVisibility(View.GONE);
        followerCountAndOnlineLayout.setVisibility(View.VISIBLE);
        profileAskBtn.setVisibility(View.VISIBLE);
        profileAskBtn.setText(getResources().getString(R.string.ask_yourself));
        profileUsername.setText(SharedPrefManager.getInstance(this.getContext()).getUsername());

        profileImageView.setOnClickListener(profileImageViewClickListener);
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

        ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        public Fragment getItem(int position) {
            return (Fragment) this.mFragmentList.get(position);
        }

        public int getCount() {
            return this.mFragmentList.size();
        }

        void addFragment(Fragment fragment, String title) {
            this.mFragmentList.add(fragment);
            this.mFragmentTitleList.add(title);
        }

        public CharSequence getPageTitle(int position) {
            return (CharSequence) this.mFragmentTitleList.get(position);
        }
    }


    private String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    private void uploadImage(){
        class UploadImage extends AsyncTask<Bitmap,Void,String> {
//            ProgressDialog loading;
//            RequestHandler rh = new RequestHandler(getActivity());

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
//                loading = ProgressDialog.show(MainActivity.this, "Uploading Image", "Please wait...",true,true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
//                loading.dismiss();
//                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Bitmap... params) {
                Bitmap bitmap = params[0];
                String uploadImage = getStringImage(bitmap);

                addImage(getContext(),uploadImage);

                return "result";
            }
        }

        UploadImage ui = new UploadImage();
        ui.execute(bitmap);
    }

    private void addImage(Context context, String uploadImage) {
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.URL_HANDLE_IMAGES,
                response -> {
                    try {
                        Log.i("ImageRequest",response);
                        JSONObject obj = new JSONObject(response);
                        if (obj.getBoolean("error")) {
                            Toast.makeText(context, obj.getString("message"), Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", String.valueOf(SharedPrefManager.getInstance(context).getUserId()));
                params.put("image_data", uploadImage);

                return params;
            }

        };

        RequestHandler.getInstance(context).addToRequestQueue(stringRequest);

    }

}
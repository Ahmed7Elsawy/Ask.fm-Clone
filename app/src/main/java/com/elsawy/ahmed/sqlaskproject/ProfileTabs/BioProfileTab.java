package com.elsawy.ahmed.sqlaskproject.ProfileTabs;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.elsawy.ahmed.sqlaskproject.Activities.ProfileActivity;
import com.elsawy.ahmed.sqlaskproject.R;
import com.elsawy.ahmed.sqlaskproject.RequestHandler;
import com.elsawy.ahmed.sqlaskproject.SharedPrefManager;
import com.elsawy.ahmed.sqlaskproject.Utils.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class BioProfileTab extends Fragment {

    private String profileID;

    private TextView nameTV,emailTV,genderTV,birthdayTV;

    public BioProfileTab() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.profile_bio_tab, container, false);

        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey("profileID"))
                profileID = bundle.getString("profileID");

        nameTV = (TextView) rootView.findViewById(R.id.bio_name_tv);
        emailTV = (TextView) rootView.findViewById(R.id.bio_email_tv);
        genderTV = (TextView) rootView.findViewById(R.id.bio_gender_tv);
        birthdayTV = (TextView) rootView.findViewById(R.id.bio_birthday_tv);

        getUserInfo(profileID);


        return rootView;
    }

    private void getUserInfo(String profile_id) {
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.URL_HANDLE_USER_INFO,
                response -> {
                    try {
                        JSONObject obj = new JSONObject(response);

                        if (obj.getBoolean("error")) {
                            Toast.makeText(BioProfileTab.this.getContext(), obj.getString("message"), Toast.LENGTH_LONG).show();
                        } else {
                            nameTV.setText(obj.getString("username"));
                            emailTV.setText(obj.getString("email"));
                            genderTV.setText(obj.getString("gender"));
                            birthdayTV.setText(obj.getString("birthday"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {

                    Toast.makeText(BioProfileTab.this.getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", profile_id);

                return params;
            }
        };
        RequestHandler.getInstance(BioProfileTab.this.getContext()).addToRequestQueue(stringRequest);
    }

}

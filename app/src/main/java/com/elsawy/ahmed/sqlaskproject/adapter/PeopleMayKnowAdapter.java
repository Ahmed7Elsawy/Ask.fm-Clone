package com.elsawy.ahmed.sqlaskproject.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.elsawy.ahmed.sqlaskproject.Activities.ProfileActivity;
import com.elsawy.ahmed.sqlaskproject.R;
import com.elsawy.ahmed.sqlaskproject.RequestHandler;
import com.elsawy.ahmed.sqlaskproject.SharedPrefManager;
import com.elsawy.ahmed.sqlaskproject.Utils.Constants;
import com.elsawy.ahmed.sqlaskproject.models.Friend;
import com.elsawy.ahmed.sqlaskproject.models.User;
import com.elsawy.ahmed.sqlaskproject.viewholder.FriendViewHolder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class PeopleMayKnowAdapter extends RecyclerView.Adapter<FriendViewHolder> {

    private String TAG = "PeopleMayKnowAdapter";
    private Context mContext;
    private String userId;


    private ArrayList<User> peopleMayKnowList = new ArrayList<>();

    public PeopleMayKnowAdapter(Context mContext) {

        this.mContext = mContext;
        userId = SharedPrefManager.getInstance(mContext).getUserId();
        getPeopleMayKnow();
    }

    @NonNull
    @Override
    public FriendViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        return new FriendViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_friends, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FriendViewHolder holder, final int position) {

        final User currentUser = PeopleMayKnowAdapter.this.peopleMayKnowList.get(position);
        View.OnClickListener addFriendClickListener = view -> {
            addFriend(this.mContext,currentUser.getUserID());
            peopleMayKnowList.remove(position);
            notifyDataSetChanged();
        };

        View.OnClickListener openUserProfile = view ->{
            Intent intent = new Intent(mContext, ProfileActivity.class);
            intent.putExtra("ProfileID", currentUser.getUserID());
            intent.putExtra("ProfileUsername", currentUser.getUsername());
            intent.putExtra("isFriend", currentUser.isFriend());
            mContext.startActivity(intent);
        };

        holder.bindToPeopleMayKnow(currentUser, addFriendClickListener, openUserProfile);

    }

    @Override
    public int getItemCount() {
        return peopleMayKnowList.size();
    }

    private void getPeopleMayKnow() {
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.URL_FRIENDS,
                response -> {
                    try {
                        JSONObject obj = new JSONObject(response);
                        Log.i("OBJResponse", obj.toString());

                        if (!obj.getBoolean("error")) {

                            JSONArray jsonArray = obj.getJSONArray("Friends");
                            for (int friend_num = 0; friend_num < jsonArray.length(); friend_num++) {
                                JSONObject JSON_friend = jsonArray.getJSONObject(friend_num);

                                User currentUser = new Friend();

                                currentUser.setUserID(JSON_friend.getString("user_id"));
                                currentUser.setUsername(JSON_friend.getString("username"));
                                currentUser.setFriend(false);

                                peopleMayKnowList.add(currentUser);
                            }
                            notifyDataSetChanged();

                        } else {
                            Toast.makeText(mContext, obj.getString("message"), Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    Toast.makeText(mContext, error.getMessage(), Toast.LENGTH_LONG).show();
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", userId);
                params.put("key", "mayKnow");

                return params;
            }
        };

        RequestHandler.getInstance(mContext).addToRequestQueue(stringRequest);
    }

    private void addFriend(Context context, String friend_id) {
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.URL_FRIENDS,
                response -> {
                    try {
                        JSONObject obj = new JSONObject(response);
                        Log.i("ADDFRIEND",obj.toString());
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
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", String.valueOf(SharedPrefManager.getInstance(context).getUserId()));
                params.put("friend_id", friend_id);
                params.put("add", "add");

                return params;
            }

        };

        RequestHandler.getInstance(context).addToRequestQueue(stringRequest);

    }

}
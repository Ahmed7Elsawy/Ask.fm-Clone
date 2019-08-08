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
import com.elsawy.ahmed.sqlaskproject.viewholder.FriendViewHolder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class FriendsAdapter extends RecyclerView.Adapter<FriendViewHolder> {

    private String TAG = "FriendsAdapter";
    private Context mContext;
    private String userId;


    private ArrayList<Friend> friendsList = new ArrayList<>();

    public FriendsAdapter(Context mContext) {

        this.mContext = mContext;
        userId = SharedPrefManager.getInstance(mContext).getUserId();
        getFriends();
    }

    @NonNull
    @Override
    public FriendViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        return new FriendViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_friends, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FriendViewHolder holder, final int position) {

        final Friend currentFriend = FriendsAdapter.this.friendsList.get(position);

        holder.bindToFriend(this.mContext, currentFriend);


//        holder.friend_name.setText(currentFriend.getFriendName());
//
//        handleFavoriteImage(holder,currentFriend);
//
//        holder.cardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                openProfileActivity(currentFriend);
//            }
//        });
//
//        holder.friend_name.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                openProfileActivity(currentFriend);
//            }
//        });
//
//        holder.profile_friend_image.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                openProfileActivity(currentFriend);
//            }
//        });
    }

//    private void handleFavoriteImage(@NonNull MyViewHolder holder,final Friend currentFriend){
//        if (currentFriend.getFavorite())
//            holder.favorite_friend.setImageResource(R.drawable.ic_stars_yellow_24dp);
//        else
//            holder.favorite_friend.setImageResource(R.drawable.ic_star_black_24dp);
//
//        holder.favorite_friend.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (currentFriend.getFavorite())
//                    FriendsAdapter.this.ref.child("user-objects").child("friends").child(FriendsAdapter.this.userData.getUid()).child(currentFriend.getFriendID()).child("favorite").setValue(false);
//                else
//                    FriendsAdapter.this.ref.child("user-objects").child("friends").child(FriendsAdapter.this.userData.getUid()).child(currentFriend.getFriendID()).child("favorite").setValue(true);
//            }
//        });
//    }

//    private void openProfileActivity(Friend currentFriend){
//        Intent intent=new Intent(FriendsAdapter.this.mContext, ProfileActivity.class);
//        intent.putExtra("ProfileInfo",currentFriend);
//        FriendsAdapter.this.mContext.startActivity(intent);
//    }

    @Override
    public int getItemCount() {
        return friendsList.size();
    }


    private void getFriends() {
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

                                Friend currentFriend = new Friend();

                                currentFriend.setFriendID(JSON_friend.getString("friend_id"));
                                currentFriend.setFriendName(JSON_friend.getString("name"));
                                currentFriend.setFavorite(JSON_friend.getBoolean("favorite"));

                                friendsList.add(currentFriend);
                            }
                            notifyDataSetChanged();

                        } else {
                            Toast.makeText(
                                    mContext,
                                    obj.getString("message"),
                                    Toast.LENGTH_LONG
                            ).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {

                    Toast.makeText(
                            mContext,
                            error.getMessage(),
                            Toast.LENGTH_LONG
                    ).show();
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", String.valueOf(SharedPrefManager.getInstance(FriendsAdapter.this.mContext).getUserId()));

                return params;
            }

        };

        RequestHandler.getInstance(mContext).addToRequestQueue(stringRequest);
    }


    View.OnClickListener cardViewClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(FriendsAdapter.this.mContext, ProfileActivity.class);
        }
    };


}
package com.elsawy.ahmed.sqlaskproject.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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

        View.OnClickListener deleteFriendClickListener = view -> {
            deleteFriend(this.mContext, currentFriend.getUserID());
            friendsList.remove(position);
            notifyDataSetChanged();
        };

        View.OnClickListener openUserProfile = view -> {
            Intent intent = new Intent(mContext, ProfileActivity.class);
            intent.putExtra("ProfileID", currentFriend.getUserID());
            intent.putExtra("ProfileUsername", currentFriend.getUsername());
            intent.putExtra("isFriend", "true");
            intent.putExtra("friendFavorite", String.valueOf(currentFriend.getFavorite()));
            mContext.startActivity(intent);
        };

        View.OnClickListener favoriteClickListener = view -> {
            if (currentFriend.getFavorite()) {
                currentFriend.setFavorite(false);
            } else {
                currentFriend.setFavorite(true);
            }
            updateFavorite(mContext, currentFriend.getUserID(), currentFriend.getFavorite());
            holder.setFavoriteImage(currentFriend.getFavorite());
        };

        holder.bindToFriend(currentFriend, favoriteClickListener, deleteFriendClickListener, openUserProfile);

    }

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

                        if (!obj.getBoolean("error")) {

                            JSONArray jsonArray = obj.getJSONArray("Friends");
                            for (int friend_num = 0; friend_num < jsonArray.length(); friend_num++) {
                                JSONObject JSON_friend = jsonArray.getJSONObject(friend_num);

                                Friend currentFriend = new Friend();

                                currentFriend.setUserID(JSON_friend.getString("friend_id"));
                                currentFriend.setUsername(JSON_friend.getString("name"));
                                currentFriend.setFavorite(JSON_friend.getBoolean("favorite"));
                                currentFriend.setFriend(true);

                                friendsList.add(currentFriend);
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
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", userId);

                return params;
            }
        };

        RequestHandler.getInstance(mContext).addToRequestQueue(stringRequest);
    }

    private void deleteFriend(Context context, String friend_id) {
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.URL_FRIENDS,
                response -> {
                    try {
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
                params.put("friend_id", friend_id);
                params.put("delete", "delete");

                return params;
            }

        };

        RequestHandler.getInstance(context).addToRequestQueue(stringRequest);

    }

    private void updateFavorite(Context context, String friend_id, boolean favorite) {
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.URL_FRIENDS,
                response -> {
                    try {
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
                params.put("friend_id", friend_id);
                params.put("favorite", String.valueOf(favorite));

                return params;
            }

        };

        RequestHandler.getInstance(context).addToRequestQueue(stringRequest);

    }

}
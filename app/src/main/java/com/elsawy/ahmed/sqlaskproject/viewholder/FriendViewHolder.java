package com.elsawy.ahmed.sqlaskproject.viewholder;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class FriendViewHolder extends RecyclerView.ViewHolder{

    private TextView friend_name;
    private ImageView favorite_friend;
    private CircleImageView profile_friend_image;
    private ImageView accepted_image;
    private ImageView add_friend_image;
    private CardView cardView;

    public FriendViewHolder(@NonNull View itemView) {
        super(itemView);

        friend_name = (TextView) itemView.findViewById(R.id.friend_name);
        favorite_friend = (ImageView) itemView.findViewById(R.id.favorite_friend);
        accepted_image = (ImageView) itemView.findViewById(R.id.accepted_image_friend);
        add_friend_image = (ImageView) itemView.findViewById(R.id.add_image_friend);
        cardView = (CardView) itemView.findViewById(R.id.friend_item_card_view);
        profile_friend_image = (CircleImageView) itemView.findViewById(R.id.profile_image_friend);
    }
    public void bindToFriend(Context context, Friend currentFriend, View.OnClickListener favoriteClickListener, View.OnClickListener deleteFriendClickListener) {

        add_friend_image.setVisibility(View.GONE);
        favorite_friend.setVisibility(View.VISIBLE);
        accepted_image.setVisibility(View.VISIBLE);

        friend_name.setText(currentFriend.getFriendName());
        this.setFavoriteImage(currentFriend.getFavorite());
        favorite_friend.setOnClickListener(favoriteClickListener);

        cardView.setOnClickListener(view -> {
            Intent intent = new Intent(context, ProfileActivity.class);
            intent.putExtra("ProfileID", currentFriend.getFriendID());
            intent.putExtra("ProfileUsername", currentFriend.getFriendName());
            intent.putExtra("isFriend", "true");
            intent.putExtra("friendFavorite", String.valueOf(currentFriend.getFavorite()));
            context.startActivity(intent);
        });

        favorite_friend.setOnClickListener(view -> {
            if (currentFriend.getFavorite()) {
                currentFriend.setFavorite(false);
            } else {
                currentFriend.setFavorite(true);
            }
            updateFavorite(context,currentFriend.getFriendID(),currentFriend.getFavorite());
            setFavoriteImage(currentFriend.getFavorite());
        });

        accepted_image.setOnClickListener(deleteFriendClickListener);
    }

    public void bindToPeopleMayKnow(Context context, Friend currentFriend, View.OnClickListener addFriendClickListener) {

        add_friend_image.setVisibility(View.VISIBLE);
        favorite_friend.setVisibility(View.GONE);
        accepted_image.setVisibility(View.GONE);

        friend_name.setText(currentFriend.getFriendName());
        cardView.setOnClickListener(view -> {
            Intent intent = new Intent(context, ProfileActivity.class);
            intent.putExtra("ProfileID", currentFriend.getFriendID());
            intent.putExtra("ProfileUsername", currentFriend.getFriendName());
            intent.putExtra("isFriend", "false");
            intent.putExtra("friendFavorite", String.valueOf(currentFriend.getFavorite()));
            context.startActivity(intent);
        });
        add_friend_image.setOnClickListener(addFriendClickListener);

    }

    private void setFavoriteImage(Boolean isFavorite){
        if (isFavorite)
            favorite_friend.setImageResource(R.drawable.ic_stars_yellow_24dp);
        else
            favorite_friend.setImageResource(R.drawable.ic_star_black_24dp);
    }

    private void updateFavorite(Context context,String friend_id, boolean favorite) {
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
            protected Map<String, String> getParams() throws AuthFailureError {
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

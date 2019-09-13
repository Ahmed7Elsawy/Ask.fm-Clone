package com.elsawy.ahmed.sqlaskproject.viewholder;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.elsawy.ahmed.sqlaskproject.Activities.ProfileActivity;
import com.elsawy.ahmed.sqlaskproject.R;
import com.elsawy.ahmed.sqlaskproject.SharedPrefManager;
import com.elsawy.ahmed.sqlaskproject.models.Friend;
import com.elsawy.ahmed.sqlaskproject.models.User;

import de.hdodenhof.circleimageview.CircleImageView;

public class FriendViewHolder extends RecyclerView.ViewHolder {

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

    public void bindToFriend(Friend currentFriend, View.OnClickListener favoriteClickListener, View.OnClickListener deleteFriendClickListener, View.OnClickListener openUserProfile) {

        showAsFriendUser();
        friend_name.setText(currentFriend.getUsername());
        this.setFavoriteImage(currentFriend.getFavorite());
        cardView.setOnClickListener(openUserProfile);
        favorite_friend.setOnClickListener(favoriteClickListener);
        accepted_image.setOnClickListener(deleteFriendClickListener);
    }

    public void bindToPeopleMayKnow(User currentFriend, View.OnClickListener addFriendClickListener, View.OnClickListener openUserProfile) {

        showAsUnFriendUser();
        friend_name.setText(currentFriend.getUsername());
        cardView.setOnClickListener(openUserProfile);
        add_friend_image.setOnClickListener(addFriendClickListener);

    }

    public void bindToUser(Context context, Friend currentFriend, View.OnClickListener addFriendClickListener, View.OnClickListener deleteFriendClickListener) {

        if (currentFriend.getUserID().equals(SharedPrefManager.getInstance(context).getUserId()))
            showMyUser();
        else
            showAsUser(currentFriend.isFriend());

        friend_name.setText(currentFriend.getUsername());
        cardView.setOnClickListener(view -> {
            Intent intent = new Intent(context, ProfileActivity.class);
            intent.putExtra("ProfileID", currentFriend.getUserID());
            intent.putExtra("ProfileUsername", currentFriend.getUsername());
            intent.putExtra("isFriend", currentFriend.isFriend());
            if (currentFriend.isFriend())
                intent.putExtra("friendFavorite", String.valueOf(currentFriend.getFavorite()));
            context.startActivity(intent);
        });
        add_friend_image.setOnClickListener(addFriendClickListener);
        accepted_image.setOnClickListener(deleteFriendClickListener);

    }

    private void showMyUser() {
        add_friend_image.setVisibility(View.GONE);
        favorite_friend.setVisibility(View.GONE);
        accepted_image.setVisibility(View.GONE);
    }

    private void showAsFriendUser() {
        add_friend_image.setVisibility(View.GONE);
        favorite_friend.setVisibility(View.VISIBLE);
        accepted_image.setVisibility(View.VISIBLE);
    }

    private void showAsUnFriendUser() {
        add_friend_image.setVisibility(View.VISIBLE);
        favorite_friend.setVisibility(View.GONE);
        accepted_image.setVisibility(View.GONE);
    }

    public void showAsUser(boolean isFriend) {
        favorite_friend.setVisibility(View.GONE);
        if (isFriend) {
            add_friend_image.setVisibility(View.GONE);
            accepted_image.setVisibility(View.VISIBLE);
        } else {
            add_friend_image.setVisibility(View.VISIBLE);
            accepted_image.setVisibility(View.GONE);
        }
    }

    public void setFavoriteImage(Boolean isFavorite) {
        if (isFavorite)
            favorite_friend.setImageResource(R.drawable.ic_stars_yellow_24dp);
        else
            favorite_friend.setImageResource(R.drawable.ic_star_black_24dp);
    }

}
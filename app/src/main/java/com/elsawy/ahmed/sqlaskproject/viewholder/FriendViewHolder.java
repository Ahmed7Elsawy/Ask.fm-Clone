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
import com.elsawy.ahmed.sqlaskproject.Utils.Utilties;
import com.elsawy.ahmed.sqlaskproject.adapter.FriendsAdapter;
import com.elsawy.ahmed.sqlaskproject.models.Friend;
import com.elsawy.ahmed.sqlaskproject.models.Question;

import de.hdodenhof.circleimageview.CircleImageView;

public class FriendViewHolder extends RecyclerView.ViewHolder{

    private TextView friend_name;
    private ImageView favorite_friend;
    private CircleImageView profile_friend_image;
    private ImageView accepted_image;
    private CardView cardView;

    public FriendViewHolder(@NonNull View itemView) {
        super(itemView);

        friend_name = (TextView) itemView.findViewById(R.id.friend_name);
        favorite_friend = (ImageView) itemView.findViewById(R.id.favorite_friend);
        accepted_image = (ImageView) itemView.findViewById(R.id.accepted_image_friend);
        cardView = (CardView) itemView.findViewById(R.id.friend_item_card_view);
        profile_friend_image = (CircleImageView) itemView.findViewById(R.id.profile_image_friend);
    }
    public void bindToFriend(Context context, Friend currentFriend){

        friend_name.setText(currentFriend.getFriendName());
        this.handleFavoriteImage(currentFriend.getFavorite());

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ProfileActivity.class);
                intent.putExtra("ProfileInfo",currentFriend);
                context.startActivity(intent);
            }
        });
    }


    private void handleFavoriteImage(Boolean isFavorite){
        if (isFavorite)
            favorite_friend.setImageResource(R.drawable.ic_stars_yellow_24dp);
        else
            favorite_friend.setImageResource(R.drawable.ic_star_black_24dp);

//        favorite_friend.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (isFavorite)
//                    FriendsAdapter.this.ref.child("user-objects").child("friends").child(FriendsAdapter.this.userData.getUid()).child(currentFriend.getFriendID()).child("favorite").setValue(false);
//                else
//                    FriendsAdapter.this.ref.child("user-objects").child("friends").child(FriendsAdapter.this.userData.getUid()).child(currentFriend.getFriendID()).child("favorite").setValue(true);
//            }
//        });
    }

//    View.OnClickListener cardViewClickListener = new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
//            Intent intent = new Intent(this.mContext, ProfileActivity.class);
//        }
//    };

}

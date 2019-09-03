package com.elsawy.ahmed.sqlaskproject.viewholder;


import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.elsawy.ahmed.sqlaskproject.R;
import com.elsawy.ahmed.sqlaskproject.Utils.Utilties;
import com.elsawy.ahmed.sqlaskproject.models.Answer;

import de.hdodenhof.circleimageview.CircleImageView;

public class AnswerViewHolder extends RecyclerView.ViewHolder {

    private TextView question_text_TV;
    private TextView answer_text_TV;
    private TextView username_TV;
    private TextView answer_time_TV;
    private TextView answer_likes_count_TV;
    private CircleImageView user_profile_image;
    private ImageButton like_btn;
    private ImageView reAsk_btn;
    private ImageView answer_setting_image;
    private CardView itemAnswerCardView;

    public AnswerViewHolder(@NonNull View itemView) {
        super(itemView);
        question_text_TV = (TextView) itemView.findViewById(R.id.question_txt_answer);
        answer_text_TV = (TextView) itemView.findViewById(R.id.question_answer_TV);
        username_TV = (TextView) itemView.findViewById(R.id.answer_username_TV);
        answer_time_TV = (TextView) itemView.findViewById(R.id.answer_time_TV);
        answer_likes_count_TV = (TextView) itemView.findViewById(R.id.answer_like_count_TV);
        user_profile_image = (CircleImageView) itemView.findViewById(R.id.answer_profile_image);
        like_btn = (ImageButton) itemView.findViewById(R.id.answer_like_image);
        reAsk_btn = (ImageView) itemView.findViewById(R.id.answer_re_ask);
        answer_setting_image = (ImageView) itemView.findViewById(R.id.answer_setting);
        itemAnswerCardView = (CardView) itemView.findViewById(R.id.item_answer_card_view);
    }

    public void bindToAnswer(Answer answer, boolean isProfile, View.OnClickListener openProfileClickListener, View.OnClickListener likeClickListener,View.OnClickListener openAnswerDetailClickListener, SpannableString ss) {
//        question_text_TV.setText(answer.getQuestion().getQuestionText());
        question_text_TV.setText(ss);
        question_text_TV.setMovementMethod(LinkMovementMethod.getInstance());
        answer_text_TV.setText(answer.getAnswerText());
        username_TV.setText(answer.getUsername());
        answer_time_TV.setText(Utilties.getTimeAgo(answer.getTimestamp()));

        handleLikesCount(answer.getLikesCount());
        handleLikeImage(answer.isLike());

        if (isProfile) {
            user_profile_image.setVisibility(View.GONE);
            username_TV.setVisibility(View.GONE);
        }

        user_profile_image.setOnClickListener(openProfileClickListener);
        username_TV.setOnClickListener(openProfileClickListener);
        like_btn.setOnClickListener(likeClickListener);
        question_text_TV.setOnClickListener(openAnswerDetailClickListener);
        answer_text_TV.setOnClickListener(openAnswerDetailClickListener);
        answer_time_TV.setOnClickListener(openAnswerDetailClickListener);
    }

    public void handleLikeImage(boolean isLike){
        if (isLike){
            like_btn.setImageResource(R.drawable.ic_favorite_orange_24dp);
        }else {
            like_btn.setImageResource(R.drawable.ic_like_black_24dp);
        }

    }

    public void handleLikesCount(int likesCount){
        answer_likes_count_TV.setText(String.valueOf(likesCount));
    }


}

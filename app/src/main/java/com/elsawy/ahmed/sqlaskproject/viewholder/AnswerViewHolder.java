package com.elsawy.ahmed.sqlaskproject.viewholder;


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

    public TextView question_text_TV;
    public TextView answer_text_TV;
    public TextView username_TV;
    public TextView answer_time_TV;
    public TextView answer_likes_count_TV;
    public CircleImageView user_profile_image;
    public ImageButton like_btn;
    public ImageView reAsk_btn;
    public ImageView answer_setting_image;
    public CardView itemPostCardView;

    public AnswerViewHolder(@NonNull View itemView) {
        super(itemView);
//        question_text_TV = (TextView) itemView.findViewById(R.id.question_txt_answer);
//        answer_text_TV = (TextView) itemView.findViewById(R.id.question_answer_TV);
//        username_TV = (TextView) itemView.findViewById(R.id.answer_username_TV);
//        answer_time_TV = (TextView) itemView.findViewById(R.id.answer_time_TV);
//        answer_likes_count_TV = (TextView) itemView.findViewById(R.id.answer_like_count_TV);
//        user_profile_image = (CircleImageView) itemView.findViewById(R.id.answer_profile_image);
//        like_btn = (ImageButton) itemView.findViewById(R.id.answer_like_image);
//        reAsk_btn = (ImageView) itemView.findViewById(R.id.answer_re_ask);
//        answer_setting_image = (ImageView) itemView.findViewById(R.id.answer_setting);
//        itemPostCardView = (CardView) itemView.findViewById(R.id.item_post_card_view);
    }

    public void bindToAnswer(Answer answer, View.OnClickListener likeClickListener) {
        question_text_TV.setText(answer.question.getQuestionText());
        answer_text_TV.setText(answer.answerText);
        username_TV.setText(answer.question.getReceiver_userID());
        answer_time_TV.setText(Utilties.getTimeAgo(answer.timestamp));
        answer_likes_count_TV.setText(String.valueOf(answer.likesCount));

        like_btn.setOnClickListener(likeClickListener);
    }
}

package com.elsawy.ahmed.sqlaskproject.viewholder;

import android.content.Context;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.elsawy.ahmed.sqlaskproject.R;
import com.elsawy.ahmed.sqlaskproject.Utils.Utilties;
import com.elsawy.ahmed.sqlaskproject.models.Answer;

import de.hdodenhof.circleimageview.CircleImageView;

public class AnswerNotificationViewHolder extends RecyclerView.ViewHolder {

    private TextView answer_notification_TV;
    private TextView answer_time_TV;
    private CircleImageView profileImageView;
    private CardView notificationCardView;
    private ImageView notificationSettingImage;

    public AnswerNotificationViewHolder(@NonNull View itemView) {
        super(itemView);
        answer_notification_TV = (TextView) itemView.findViewById(R.id.answer_notification_TV);
        answer_time_TV = (TextView) itemView.findViewById(R.id.answer_notification_time_TV);
        profileImageView = (CircleImageView) itemView.findViewById(R.id.answer_notification_profile_image);
        notificationCardView = (CardView) itemView.findViewById(R.id.answer_notification_card_view);
        notificationSettingImage = (ImageView) itemView.findViewById(R.id.answer_notification_setting_image);

    }

    public void bindToAnswerNotification(Context mContext, Answer currentAnswer, View.OnClickListener openProfileClickListener, View.OnClickListener openAnswerDetailClickListener, PopupMenu.OnMenuItemClickListener settingMenuItemClickListener) {

        String notificationTextHTML = "<span><span style=\"color:#EE1144;\">" + currentAnswer.getUsername() + "</span> answered your question <b>" + currentAnswer.getQuestion().getQuestionText() + "</b></span>";

        answer_notification_TV.setText(Html.fromHtml(notificationTextHTML));
        answer_time_TV.setText(Utilties.getTimeAgo(currentAnswer.getTimestamp()));

        profileImageView.setOnClickListener(openProfileClickListener);
        notificationCardView.setOnClickListener(openAnswerDetailClickListener);
        notificationSettingImage.setOnClickListener(view -> {
            PopupMenu popup = new PopupMenu(mContext, notificationSettingImage);
            popup.inflate(R.menu.answer_notification_option_menu);
            popup.setOnMenuItemClickListener(settingMenuItemClickListener);
            popup.show();
        });

    }

    public void bindToLikesNotification(Answer currentAnswer, String likeUsername, View.OnClickListener openProfileClickListener, View.OnClickListener openAnswerDetailClickListener) {

        String notificationTextHTML = "<span><span style=\"color:#EE1144;\">" + likeUsername + "</span> likes your answer: <b>" + currentAnswer.getAnswerText() + "</b></span>";

        answer_notification_TV.setText(Html.fromHtml(notificationTextHTML));
        answer_time_TV.setText(Utilties.getTimeAgo(currentAnswer.getTimestamp()));

        profileImageView.setOnClickListener(openProfileClickListener);
        notificationCardView.setOnClickListener(openAnswerDetailClickListener);
        notificationSettingImage.setVisibility(View.GONE);

    }

    public void bindToFavoriteNotification(Answer currentAnswer, View.OnClickListener openProfileClickListener, View.OnClickListener openAnswerDetailClickListener) {

        String notificationTextHTML = "<span><span style=\"color:#EE1144;\">" + currentAnswer.getUsername() + "</span> posted a new answer! Check it out: <b>" + currentAnswer.getAnswerText() + "</b></span>";

        answer_notification_TV.setText(Html.fromHtml(notificationTextHTML));
        answer_time_TV.setText(Utilties.getTimeAgo(currentAnswer.getTimestamp()));

        profileImageView.setOnClickListener(openProfileClickListener);
        notificationCardView.setOnClickListener(openAnswerDetailClickListener);
        notificationSettingImage.setVisibility(View.GONE);

    }

}

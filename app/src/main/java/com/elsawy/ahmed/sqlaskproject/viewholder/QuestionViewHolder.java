package com.elsawy.ahmed.sqlaskproject.viewholder;

import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.elsawy.ahmed.sqlaskproject.Activities.AnswerTheQuestionsActivity;
import com.elsawy.ahmed.sqlaskproject.Activities.AskActivity;
import com.elsawy.ahmed.sqlaskproject.Activities.ProfileActivity;
import com.elsawy.ahmed.sqlaskproject.R;
import com.elsawy.ahmed.sqlaskproject.Utils.Utilties;
import com.elsawy.ahmed.sqlaskproject.models.Question;

import de.hdodenhof.circleimageview.CircleImageView;

public class QuestionViewHolder extends RecyclerView.ViewHolder {

    private TextView question_txt;
    private TextView question_time;
    private ImageView question_setting;
    private CircleImageView profile_asker_image;
    private CardView question_cardView;

    public QuestionViewHolder(@NonNull View itemView) {
        super(itemView);

        question_txt = (TextView) itemView.findViewById(R.id.question_txt);
        question_time = (TextView) itemView.findViewById(R.id.question_time);
        question_setting = (ImageView) itemView.findViewById(R.id.question_setting);
        profile_asker_image = (CircleImageView) itemView.findViewById(R.id.asker_profile_image);
        question_cardView = (CardView) itemView.findViewById(R.id.question_card_view);
    }

    public void bindToQuestion(Context context, Question currentQuestion, View.OnClickListener cardViewClickListener, PopupMenu.OnMenuItemClickListener settingMenuItemClickListener) {

        question_txt.setText(currentQuestion.getQuestionText());
        question_time.setText(Utilties.getTimeAgo(currentQuestion.getQuestionTimestamp()));
        if (!currentQuestion.getAnonymous()) {
            profile_asker_image.setImageDrawable(context.getResources().getDrawable(R.drawable.profile_image));
        }

        question_cardView.setOnClickListener(cardViewClickListener);

        question_setting.setOnClickListener(view -> {
            PopupMenu popup = new PopupMenu(context, question_setting);
            popup.inflate(R.menu.question_options_menu);
            popup.setOnMenuItemClickListener(settingMenuItemClickListener);
            popup.show();
        });

    }

}
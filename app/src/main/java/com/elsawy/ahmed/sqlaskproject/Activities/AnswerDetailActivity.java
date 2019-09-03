 package com.elsawy.ahmed.sqlaskproject.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.elsawy.ahmed.sqlaskproject.R;
import com.elsawy.ahmed.sqlaskproject.RequestHandler;
import com.elsawy.ahmed.sqlaskproject.SharedPrefManager;
import com.elsawy.ahmed.sqlaskproject.Utils.Constants;
import com.elsawy.ahmed.sqlaskproject.Utils.Utilties;
import com.elsawy.ahmed.sqlaskproject.Utils.VerticalSpaceItemDecoration;
import com.elsawy.ahmed.sqlaskproject.adapter.AnswerLikesAdapter;
import com.elsawy.ahmed.sqlaskproject.models.Answer;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class AnswerDetailActivity extends AppCompatActivity {

    private TextView question_text_TV;
    private TextView answer_text_TV;
    private TextView username_TV;
    private TextView answer_time_TV;
    private TextView answer_likes_count_TV;
    private CircleImageView user_profile_image;
    private ImageButton like_btn;
    private RecyclerView answerDetailLikesRecyclerView;
    private ImageView cancelAnswerDetailAction;

    private Answer currentAnswer;

    View.OnClickListener likeClickListener = view -> {
        if (currentAnswer.isLike()) {
            // remove like
            handleLike(currentAnswer.getAnswerID(), "remove");
            currentAnswer.setLike(false);
            handleLikeImage(false);
            currentAnswer.setLikesCount(currentAnswer.getLikesCount() - 1);
            handleLikesCount(currentAnswer.getLikesCount());
        } else {
            // add like
            handleLike(currentAnswer.getAnswerID(), "add");
            handleLikeImage(true);
            currentAnswer.setLike(true);
            currentAnswer.setLikesCount(currentAnswer.getLikesCount() + 1);
            handleLikesCount(currentAnswer.getLikesCount());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_detail);

        currentAnswer = (Answer) getIntent().getParcelableExtra("theAnswer");
        setupPostItems();
        bindToAnswer();
        bindToRecycleView();

        cancelAnswerDetailAction.setOnClickListener(view -> finish());


    }

    private void setupPostItems(){
        question_text_TV = (TextView) findViewById(R.id.question_txt_answer);
        answer_text_TV = (TextView) findViewById(R.id.question_answer_TV);
        username_TV = (TextView) findViewById(R.id.answer_username_TV);
        answer_time_TV = (TextView) findViewById(R.id.answer_time_TV);
        answer_likes_count_TV = (TextView) findViewById(R.id.answer_like_count_TV);
        user_profile_image = (CircleImageView) findViewById(R.id.answer_profile_image);
        like_btn = (ImageButton) findViewById(R.id.answer_like_image);
        cancelAnswerDetailAction = (ImageView) findViewById(R.id.cancel_answer_detail_action);
        answerDetailLikesRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_activity_detail_likes);
    }

    private void bindToAnswer(){
        SpannableString spannableString = handleQuestionAndUsername();
        question_text_TV.setText(spannableString);
        question_text_TV.setMovementMethod(LinkMovementMethod.getInstance());
        answer_text_TV.setText(currentAnswer.getAnswerText());
        username_TV.setText(currentAnswer.getUsername());
        answer_time_TV.setText(Utilties.getTimeAgo(currentAnswer.getTimestamp()));

        handleLikesCount(currentAnswer.getLikesCount());
        handleLikeImage(currentAnswer.isLike());

        like_btn.setOnClickListener(likeClickListener);

    }

    private void bindToRecycleView(){
        answerDetailLikesRecyclerView.addItemDecoration(new VerticalSpaceItemDecoration(1));
        AnswerLikesAdapter answerLikesAdapter = new AnswerLikesAdapter(AnswerDetailActivity.this,currentAnswer.getAnswerID());
        answerDetailLikesRecyclerView.setLayoutManager(new LinearLayoutManager(AnswerDetailActivity.this));
        answerDetailLikesRecyclerView.setItemAnimator(new DefaultItemAnimator());
        answerDetailLikesRecyclerView.setAdapter(answerLikesAdapter);
    }

    private void handleLikeImage(boolean isLike){
        if (isLike){
            like_btn.setImageResource(R.drawable.ic_favorite_orange_24dp);
        }else {
            like_btn.setImageResource(R.drawable.ic_like_black_24dp);
        }

    }

    private void handleLikesCount(int likesCount){
        answer_likes_count_TV.setText(String.valueOf(likesCount));
    }

    private SpannableString handleQuestionAndUsername(){
        String questionAndUsername;
        int questionLength;
        int usernameLength;
        if (currentAnswer.getQuestion().isAnonymous()) {
            questionAndUsername = currentAnswer.getQuestion().getQuestionText() + "  " + currentAnswer.getQuestion().getAskerUsername();
            questionLength = currentAnswer.getQuestion().getQuestionText().length() + 1;
            usernameLength = currentAnswer.getQuestion().getAskerUsername().length() + 1;
        } else {
            questionAndUsername = currentAnswer.getQuestion().getQuestionText();
            questionLength = currentAnswer.getQuestion().getQuestionText().length();
            usernameLength = 0;
        }
        SpannableString spannableString = new SpannableString(questionAndUsername);
        ClickableSpan askerUsernameClickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                openOtherProfileActivity(currentAnswer.getQuestion().getAskerID(), currentAnswer.getQuestion().getAskerUsername());
            }

            @Override
            public void updateDrawState(TextPaint usernameText) {
                super.updateDrawState(usernameText);
                usernameText.setColor(AnswerDetailActivity.this.getResources().getColor(R.color.defaultTextColor));
                usernameText.setTypeface(Typeface.create("Arial", Typeface.NORMAL));
                usernameText.setUnderlineText(false);
            }
        };
        spannableString.setSpan(askerUsernameClickableSpan, questionLength, questionLength + usernameLength, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        return spannableString;
    }

    private void openOtherProfileActivity(String profileId, String profileUsername) {
        Intent intent = new Intent(AnswerDetailActivity.this, ProfileActivity.class);
        intent.putExtra("ProfileID", profileId);
        intent.putExtra("ProfileUsername", profileUsername);
        startActivity(intent);
    }

    private void handleLike(String answerId, String operation) {
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.URL_HANDLE_ANSWER,
                response -> {
                    try {
                        JSONObject obj = new JSONObject(response);
                        if (!obj.getBoolean("error")) {

                        } else {
                            Toast.makeText(AnswerDetailActivity.this, obj.getString("message"), Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    Toast.makeText(AnswerDetailActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("answer_id", answerId);
                params.put("user_id", SharedPrefManager.getInstance(AnswerDetailActivity.this).getUserId());
                params.put("like", operation);

                return params;
            }

        };

        RequestHandler.getInstance(AnswerDetailActivity.this).addToRequestQueue(stringRequest);
    }

}

package com.elsawy.ahmed.sqlaskproject.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
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
import com.elsawy.ahmed.sqlaskproject.Activities.AnswerDetailActivity;
import com.elsawy.ahmed.sqlaskproject.Activities.ProfileActivity;
import com.elsawy.ahmed.sqlaskproject.R;
import com.elsawy.ahmed.sqlaskproject.RequestHandler;
import com.elsawy.ahmed.sqlaskproject.SharedPrefManager;
import com.elsawy.ahmed.sqlaskproject.Utils.Constants;
import com.elsawy.ahmed.sqlaskproject.models.Answer;
import com.elsawy.ahmed.sqlaskproject.viewholder.AnswerViewHolder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AnswerAdapter extends RecyclerView.Adapter<AnswerViewHolder> {

    private Context mContext;
    private String profileId;
    private String tab;
    private boolean isProfile;

    private ArrayList<Answer> answersList = new ArrayList<>();

    public AnswerAdapter(Context mContext, String tab) {  // for my profile
        this.mContext = mContext;
        this.profileId = SharedPrefManager.getInstance(mContext).getUserId();
        this.tab = tab;
        this.isProfile = false;
        getAnswers();
    }

    public AnswerAdapter(Context mContext, String profileId, String tab) { // for other users profile
        this.mContext = mContext;
        this.profileId = profileId;
        this.tab = tab;
        this.isProfile = true;
        getAnswers();
    }

    @NonNull
    @Override
    public AnswerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AnswerViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AnswerViewHolder holder, int position) {
        final Answer currentAnswer = AnswerAdapter.this.answersList.get(position);

        View.OnClickListener openProfileClickListener = view -> {
            openOtherProfileActivity(currentAnswer.getQuestion().getReceiverID(), currentAnswer.getUsername());
        };

        View.OnClickListener openAnswerDetailClickListener = view -> {
            Intent answerDetailIntent = new Intent(mContext, AnswerDetailActivity.class);
            answerDetailIntent.putExtra("theAnswer", currentAnswer);
            mContext.startActivity(answerDetailIntent);
        };

        View.OnClickListener likeClickListener = view -> {
            if (currentAnswer.isLike()) {
                // remove like
                handleLike(currentAnswer.getAnswerID(), "remove"); // remove like from database
                holder.handleLikeImage(false);// remove like from UI
                currentAnswer.setLike(false);
                currentAnswer.setLikesCount(currentAnswer.getLikesCount() - 1);
                holder.handleLikesCount(currentAnswer.getLikesCount()); // update new like count UI
            } else {
                // add like
                handleLike(currentAnswer.getAnswerID(), "add"); // add like to database
                holder.handleLikeImage(true); // add like to UI
                currentAnswer.setLike(true);
                currentAnswer.setLikesCount(currentAnswer.getLikesCount() + 1);
                holder.handleLikesCount(currentAnswer.getLikesCount()); // update new like count UI

            }
        };

        SpannableString spannableQuestion = getSpannableQuestion(currentAnswer); // to show or hide username of the question asker

        holder.bindToAnswer(currentAnswer, isProfile, openProfileClickListener, likeClickListener, openAnswerDetailClickListener, spannableQuestion);

    }

    @Override
    public int getItemCount() {
        return answersList.size();
    }

    private void getAnswers() { // get answers from database
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.URL_HANDLE_ANSWER,
                response -> {
                    try {
                        Log.i("AnswerResponse", response);
                        JSONObject obj = new JSONObject(response);

                        if (!obj.getBoolean("error")) {

                            JSONArray jsonArray = obj.getJSONArray("Answers");
                            for (int answer_num = 0; answer_num < jsonArray.length(); answer_num++) {
                                JSONObject JSON_Answer = jsonArray.getJSONObject(answer_num);

                                Answer currentAnswer = new Answer();

                                currentAnswer.getQuestion().setAskerUsername(JSON_Answer.getString("askerUsername"));
                                currentAnswer.setUsername(JSON_Answer.getString("username"));
                                currentAnswer.setAnswerID(JSON_Answer.getString("answer_id"));
                                currentAnswer.getQuestion().setQuestionText(JSON_Answer.getString("question_text"));
                                currentAnswer.setAnswerText(JSON_Answer.getString("answer_text"));
                                Timestamp timestamp = Timestamp.valueOf(JSON_Answer.getString("answer_time"));
                                currentAnswer.setTimestamp(timestamp.getTime());
                                currentAnswer.setLikesCount(JSON_Answer.getInt("likes_count"));
                                currentAnswer.getQuestion().setAnonymous(JSON_Answer.getBoolean("anonymous"));
                                currentAnswer.getQuestion().setAskerID(JSON_Answer.getString("asker_id"));
                                currentAnswer.getQuestion().setReceiverID(JSON_Answer.getString("receiver_id"));
                                currentAnswer.setLike(JSON_Answer.getBoolean("islike"));

                                answersList.add(currentAnswer);
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
                params.put("user_id", profileId);
                params.put("tab", tab);

                return params;
            }

        };

        RequestHandler.getInstance(mContext).addToRequestQueue(stringRequest);
    }

    private void handleLike(String answerId, String operation) { // change the like at database
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.URL_HANDLE_ANSWER,
                response -> {
                    try {
                        JSONObject obj = new JSONObject(response);
                        if (!obj.getBoolean("error")) {

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
                params.put("answer_id", answerId);
                params.put("user_id", SharedPrefManager.getInstance(mContext).getUserId());
                params.put("like", operation);

                return params;
            }

        };

        RequestHandler.getInstance(mContext).addToRequestQueue(stringRequest);
    }

    private void openOtherProfileActivity(String profileId, String profileUsername) {
        Intent intent = new Intent(mContext, ProfileActivity.class);
        intent.putExtra("ProfileID", profileId);
        intent.putExtra("ProfileUsername", profileUsername);
        mContext.startActivity(intent);
    }

    private SpannableString getSpannableQuestion(Answer currentAnswer) {
        String questionAndUsername;
        int questionLength;
        int usernameLength;

        if (currentAnswer.getQuestion().isAnonymous()) { // hide username (so username length = 0)
            questionAndUsername = currentAnswer.getQuestion().getQuestionText() + "  " + currentAnswer.getQuestion().getAskerUsername();
            questionLength = currentAnswer.getQuestion().getQuestionText().length() + 1;
            usernameLength = currentAnswer.getQuestion().getAskerUsername().length() + 1;
        } else { // show username
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
                usernameText.setColor(mContext.getResources().getColor(R.color.defaultTextColor));
                usernameText.setTypeface(Typeface.create("Arial", Typeface.NORMAL));
                usernameText.setUnderlineText(false);
            }
        };
        spannableString.setSpan(askerUsernameClickableSpan, questionLength, questionLength + usernameLength, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        return spannableString;
    }

}
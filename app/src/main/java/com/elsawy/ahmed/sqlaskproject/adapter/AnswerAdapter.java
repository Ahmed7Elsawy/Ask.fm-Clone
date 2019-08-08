package com.elsawy.ahmed.sqlaskproject.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
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

    private String TAG = "AnswerAdapter";
    private Context mContext;
    private String profileId;
    private String tab;

    private ArrayList<Answer> answersList = new ArrayList<>();

    public AnswerAdapter(Context mContext, String tab) {
        this.mContext = mContext;
        this.profileId = SharedPrefManager.getInstance(mContext).getUserId();
        this.tab = tab;
        getAnswers();
    }

    public AnswerAdapter(Context mContext, String profileId, String tab) {
        this.mContext = mContext;
        this.profileId = profileId;
        this.tab = tab;
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

        holder.bindToAnswer(currentAnswer);

    }

    @Override
    public int getItemCount() {
        return answersList.size();
    }


    private void getAnswers() {
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.URL_SELECT_ANSWER,
                response -> {
                    try {
                        Log.i("AnswerResponse", response);
                        JSONObject obj = new JSONObject(response);

                        if (!obj.getBoolean("error")) {

                            JSONArray jsonArray = obj.getJSONArray("Answers");
                            for (int answer_num = 0; answer_num < jsonArray.length(); answer_num++) {
                                JSONObject JSON_Answer = jsonArray.getJSONObject(answer_num);

                                Answer currentAnswer = new Answer();

                                currentAnswer.setAnswerText(JSON_Answer.getString("answer_text"));
                                currentAnswer.getQuestion().setQuestionText(JSON_Answer.getString("question_text"));
                                currentAnswer.getQuestion().setReceiverID(JSON_Answer.getString("receiver_id"));
                                Timestamp timestamp = Timestamp.valueOf(JSON_Answer.getString("answer_time"));
                                currentAnswer.setTimestamp(timestamp.getTime());
                                currentAnswer.setLikesCount(JSON_Answer.getInt("likes_count"));
                                currentAnswer.setUsername(JSON_Answer.getString("username"));

                                answersList.add(currentAnswer);
                            }
                            notifyDataSetChanged();

                        } else {
                            Toast.makeText(
                                    mContext,
                                    obj.getString("message"),
                                    Toast.LENGTH_LONG
                            ).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {

                    Toast.makeText(
                            mContext,
                            error.getMessage(),
                            Toast.LENGTH_LONG
                    ).show();
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", profileId);
                params.put("tab", tab);

                return params;
            }

        };

        RequestHandler.getInstance(mContext).addToRequestQueue(stringRequest);
    }


}

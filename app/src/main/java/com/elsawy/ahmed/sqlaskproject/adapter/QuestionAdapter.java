package com.elsawy.ahmed.sqlaskproject.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.elsawy.ahmed.sqlaskproject.Activities.AnswerTheQuestionsActivity;
import com.elsawy.ahmed.sqlaskproject.R;
import com.elsawy.ahmed.sqlaskproject.RequestHandler;
import com.elsawy.ahmed.sqlaskproject.SharedPrefManager;
import com.elsawy.ahmed.sqlaskproject.Utils.Constants;
import com.elsawy.ahmed.sqlaskproject.models.Question;
import com.elsawy.ahmed.sqlaskproject.viewholder.QuestionViewHolder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionViewHolder> {


    private Context mContext;
    private String userId;

    private ArrayList<Question> questionsList = new ArrayList<>();

    public QuestionAdapter(Context mContext) {
        this.mContext = mContext;
        userId = SharedPrefManager.getInstance(mContext).getUserId();
        getQuestions();
    }

    @NonNull
    @Override
    public QuestionViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new QuestionViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_received_question, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionViewHolder holder, int position) {

        Question currentQuestion = QuestionAdapter.this.questionsList.get(position);

        PopupMenu.OnMenuItemClickListener settingMenuItemClickListener = item -> {
            switch (item.getItemId()) {
                case R.id.delete_question_menu:
                    deleteQuestion(currentQuestion.getQuestionId(), position);
                    break;
                case R.id.block_menu:
                    // TODO handle block_menu click
                    break;
            }
            return false;
        };

        View.OnClickListener cardViewClickListener = view -> {
            Intent intent = new Intent(mContext, AnswerTheQuestionsActivity.class);
            intent.putExtra("questionInfo", currentQuestion);
            mContext.startActivity(intent);
        };

        holder.bindToQuestion(mContext, currentQuestion, cardViewClickListener, settingMenuItemClickListener);

    }

    @Override
    public int getItemCount() {
        return questionsList.size();
    }

    private void getQuestions() {
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.URL_QUESTION,
                response -> {
                    try {
                        JSONObject obj = new JSONObject(response);
                        if (!obj.getBoolean("error")) {

                            JSONArray jsonArray = obj.getJSONArray("questions");

                            for (int question_num = 0; question_num < jsonArray.length(); question_num++) {
                                JSONObject JSONquestion = jsonArray.getJSONObject(question_num);

                                Question currentQuestion = new Question();

                                currentQuestion.setQuestionId(JSONquestion.getString("question_id"));
                                currentQuestion.setQuestionText(JSONquestion.getString("question_text"));
                                Timestamp timestamp = Timestamp.valueOf(JSONquestion.getString("question_time"));
                                currentQuestion.setQuestionTimestamp(timestamp.getTime());
                                currentQuestion.setAskerID(JSONquestion.getString("asker_id"));
                                currentQuestion.setReceiverID(JSONquestion.getString("receiver_id"));
                                currentQuestion.setAnonymous(JSONquestion.getBoolean("anonymous"));

                                questionsList.add(currentQuestion);
                            }
                            notifyDataSetChanged();

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
                params.put("receiver_id", userId);
                return params;
            }
        };

        RequestHandler.getInstance(mContext).addToRequestQueue(stringRequest);
    }

    private void deleteQuestion(String question_id, int position) {
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.URL_QUESTION,
                response -> {
                    try {
                        JSONObject obj = new JSONObject(response);
                        if (!obj.getBoolean("error")) {
                            //  delete the question from list
                            questionsList.remove(position);
                            notifyDataSetChanged();
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
                params.put("question_id", question_id);
                return params;
            }
        };

        RequestHandler.getInstance(mContext).addToRequestQueue(stringRequest);
    }

}

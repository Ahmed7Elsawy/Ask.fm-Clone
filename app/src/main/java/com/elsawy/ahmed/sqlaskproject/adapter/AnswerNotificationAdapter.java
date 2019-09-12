package com.elsawy.ahmed.sqlaskproject.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.elsawy.ahmed.sqlaskproject.Activities.AnswerDetailActivity;
import com.elsawy.ahmed.sqlaskproject.Activities.ProfileActivity;
import com.elsawy.ahmed.sqlaskproject.R;
import com.elsawy.ahmed.sqlaskproject.RequestHandler;
import com.elsawy.ahmed.sqlaskproject.SharedPrefManager;
import com.elsawy.ahmed.sqlaskproject.Utils.Constants;
import com.elsawy.ahmed.sqlaskproject.models.Answer;
import com.elsawy.ahmed.sqlaskproject.models.User;
import com.elsawy.ahmed.sqlaskproject.viewholder.AnswerNotificationViewHolder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AnswerNotificationAdapter  extends RecyclerView.Adapter<AnswerNotificationViewHolder>{

    private Context mContext;
    private String profileId;
    private String notificationTab;
    private ArrayList<Answer> answersList = new ArrayList<>();
    private ArrayList<User> likeUsersList = new ArrayList<>();

    public AnswerNotificationAdapter(Context mContext, String notificationTab) {
        this.mContext = mContext;
        this.profileId = SharedPrefManager.getInstance(mContext).getUserId();
        this.notificationTab = notificationTab;

        if(notificationTab.equals("answersNotification"))
            getAnswersNotification();
        else if (notificationTab.equals("likesNotification"))
            getLikesNotification();
        else if (notificationTab.equals("favoriteNotification"))
            getFavoriteNotification();

    }

    @NonNull
    @Override
    public AnswerNotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AnswerNotificationViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_answer_notifications, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AnswerNotificationViewHolder holder, int position) {
        final Answer currentAnswer = AnswerNotificationAdapter.this.answersList.get(position);

        View.OnClickListener openAnswerDetailClickListener = view -> {
            Intent answerDetailIntent = new Intent(mContext, AnswerDetailActivity.class);
            answerDetailIntent.putExtra("theAnswer", currentAnswer);
            mContext.startActivity(answerDetailIntent);
        };
        View.OnClickListener openAnswerProfileClickListener = view -> { openProfileUser(currentAnswer.getQuestion().getReceiverID(),currentAnswer.getUsername()); };

        if (notificationTab.equals("answersNotification")) {
            PopupMenu.OnMenuItemClickListener settingMenuItemClickListener = menuItem -> {
                switch (menuItem.getItemId()) {
                    case R.id.delete_answer_menu:
                        deleteAnswersNotification(currentAnswer.getAnswerID(), position);
                        break;
                }
                return false;
            };
            holder.bindToAnswerNotification(mContext, currentAnswer, openAnswerProfileClickListener, openAnswerDetailClickListener, settingMenuItemClickListener);
        }
        else if(notificationTab.equals("likesNotification")){
            final User currentLikeUser = AnswerNotificationAdapter.this.likeUsersList.get(position);
            View.OnClickListener openLikeProfileClickListener = view -> { openProfileUser(currentLikeUser.getUserID(),currentLikeUser.getUsername()); };
            holder.bindToLikesNotification(currentAnswer, currentLikeUser.getUsername(), openLikeProfileClickListener, openAnswerDetailClickListener);

        }else if (notificationTab.equals("favoriteNotification")) {
            holder.bindToFavoriteNotification(currentAnswer, openAnswerProfileClickListener, openAnswerDetailClickListener);
        }

    }

    @Override
    public int getItemCount() {
        return answersList.size();
    }

    private void getAnswersNotification() {
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.URL_HANDLE_ANSWER_NOTIFICATION,
                response -> {
                    try {
                        Log.i("AnswerNotifiResponse", response);
                        JSONObject obj = new JSONObject(response);

                        if (!obj.getBoolean("error")) {

                            JSONArray jsonArray = obj.getJSONArray("AnswerNotification");
                            for (int answer_num = 0; answer_num < jsonArray.length(); answer_num++) {
                                JSONObject JSON_Answer = jsonArray.getJSONObject(answer_num);

                                Answer currentAnswer = new Answer();

                                currentAnswer.setUsername(JSON_Answer.getString("receiverUsername"));
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
                params.put("asker_id", profileId);

                return params;
            }

        };

        RequestHandler.getInstance(mContext).addToRequestQueue(stringRequest);
    }

    private void deleteAnswersNotification(String answerId,int position) {
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.URL_HANDLE_ANSWER_NOTIFICATION,
                response -> {
                    try {
                        Log.i("AnswerNotResponse", response);
                        JSONObject obj = new JSONObject(response);

                        if (!obj.getBoolean("error")) {

                            Log.i("beforeDelete",answersList.size()+"");
                            answersList.remove(position);
                            Log.i("afterDelete",answersList.size()+"");
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
                params.put("answer_id", answerId);
                params.put("asker_id", profileId);

                return params;
            }

        };

        RequestHandler.getInstance(mContext).addToRequestQueue(stringRequest);
    }

    private void getLikesNotification() {
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.URL_HANDLE_LIKES_NOTIFICATION,
                response -> {
                    try {
                        Log.i("likesNotification", response);
                        JSONObject obj = new JSONObject(response);

                        if (!obj.getBoolean("error")) {

                            JSONArray jsonArray = obj.getJSONArray("LikesNotification");
                            for (int answer_num = 0; answer_num < jsonArray.length(); answer_num++) {
                                JSONObject JSON_Answer = jsonArray.getJSONObject(answer_num);

                                Answer currentAnswer = new Answer();
                                User currentUser = new User();

                                currentAnswer.setUsername(SharedPrefManager.getInstance(mContext).getUsername());
                                currentAnswer.setAnswerID(JSON_Answer.getString("answer_id"));
                                currentAnswer.getQuestion().setQuestionText(JSON_Answer.getString("question_text"));
                                currentAnswer.setAnswerText(JSON_Answer.getString("answer_text"));
                                Timestamp timestamp = Timestamp.valueOf(JSON_Answer.getString("answer_time"));
                                currentAnswer.setTimestamp(timestamp.getTime());
                                currentAnswer.setLikesCount(JSON_Answer.getInt("likes_count"));
                                currentAnswer.getQuestion().setAnonymous(JSON_Answer.getBoolean("anonymous"));
                                currentAnswer.getQuestion().setAskerID(JSON_Answer.getString("asker_id"));
                                currentAnswer.getQuestion().setReceiverID(JSON_Answer.getString("receiver_id"));
                                currentAnswer.setLike(false);
                                currentUser.setUsername(JSON_Answer.getString("LikeUsername"));
                                currentUser.setUserID(JSON_Answer.getString("likeUserId"));

                                answersList.add(currentAnswer);
                                likeUsersList.add(currentUser);

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

                return params;
            }

        };

        RequestHandler.getInstance(mContext).addToRequestQueue(stringRequest);
    }

    private void getFavoriteNotification() {
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.URL_HANDLE_FAVORITE_NOTIFICATION,
                response -> {
                    try {
                        Log.i("favoriteNotification", response);
                        JSONObject obj = new JSONObject(response);

                        if (!obj.getBoolean("error")) {

                            JSONArray jsonArray = obj.getJSONArray("FavoritesNotification");
                            for (int answer_num = 0; answer_num < jsonArray.length(); answer_num++) {
                                JSONObject JSON_Answer = jsonArray.getJSONObject(answer_num);

                                Answer currentAnswer = new Answer();
//                                User currentUser = new User();

                                currentAnswer.setUsername(JSON_Answer.getString("receiverUsername"));
                                currentAnswer.getQuestion().setAskerUsername(JSON_Answer.getString("askerUsername"));
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

//                                currentUser.setUsername(JSON_Answer.getString("LikeUsername"));
//                                currentUser.setUserID(JSON_Answer.getString("likeUserId"));

                                answersList.add(currentAnswer);
//                                likeUsersList.add(currentUser);

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

                return params;
            }

        };

        RequestHandler.getInstance(mContext).addToRequestQueue(stringRequest);
    }

    private void openProfileUser(String profileId, String username){
        Intent intent = new Intent(mContext, ProfileActivity.class);
        intent.putExtra("ProfileID", profileId);
        intent.putExtra("ProfileUsername", username);
        mContext.startActivity(intent);
    }

}


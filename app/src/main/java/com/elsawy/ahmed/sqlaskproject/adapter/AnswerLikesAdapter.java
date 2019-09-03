package com.elsawy.ahmed.sqlaskproject.adapter;

import android.content.Context;
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
import com.elsawy.ahmed.sqlaskproject.R;
import com.elsawy.ahmed.sqlaskproject.RequestHandler;
import com.elsawy.ahmed.sqlaskproject.SharedPrefManager;
import com.elsawy.ahmed.sqlaskproject.Utils.Constants;
import com.elsawy.ahmed.sqlaskproject.models.Friend;
import com.elsawy.ahmed.sqlaskproject.viewholder.FriendViewHolder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AnswerLikesAdapter extends RecyclerView.Adapter<FriendViewHolder> {

    private String TAG = "AnswerLikesAdapter";
    private Context mContext;
    private String userId;


    private ArrayList<Friend> answerLikesList = new ArrayList<>();

    public AnswerLikesAdapter(Context mContext,String answerID) {

        this.mContext = mContext;
        userId = SharedPrefManager.getInstance(mContext).getUserId();
        getAnswerLikes(answerID);
    }

    @NonNull
    @Override
    public FriendViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        return new FriendViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_friends, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FriendViewHolder holder, final int position) {

        final Friend currentUser = (AnswerLikesAdapter.this.answerLikesList.get(position)) ;

        View.OnClickListener addFriendClickListener = view -> {
            addFriend(this.mContext, currentUser.getUserID());
            currentUser.setFriend(true);
            holder.showAsUser(currentUser.isFriend());
            notifyDataSetChanged();
        };

        View.OnClickListener deleteFriendClickListener = view -> {
            deleteFriend(this.mContext,currentUser.getUserID());
            currentUser.setFriend(false);
            holder.showAsUser(currentUser.isFriend());
            notifyDataSetChanged();
        };

        holder.bindToUser(this.mContext, currentUser, addFriendClickListener,deleteFriendClickListener);

    }

    @Override
    public int getItemCount() {
        return answerLikesList.size();
    }

    private void getAnswerLikes(String answerID) {
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.URL_HANDLE_ANSWER,
                response -> {
                    try {
                        JSONObject obj = new JSONObject(response);
                        Log.i("AnswerLikes", obj.toString()+"  "+ answerID + userId);

                        if (!obj.getBoolean("error")) {

                            JSONArray jsonArray = obj.getJSONArray("answerLikes");
                            for (int user_number = 0; user_number < jsonArray.length(); user_number++) {
                                JSONObject JSON_user = jsonArray.getJSONObject(user_number);

                                Friend currentUser = new Friend();;
                                if(JSON_user.getString("favorite").equals("null"))
                                {
                                    currentUser.setFriend(false);
                                }else {
                                    currentUser.setFriend(true);
                                    currentUser.setFavorite(JSON_user.getBoolean("favorite"));
                                }
                                currentUser.setUserID(JSON_user.getString("user_id"));
                                currentUser.setUsername(JSON_user.getString("name"));

                                answerLikesList.add(currentUser);
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
                params.put("user_id", userId);
                params.put("answer_id", answerID);

                return params;
            }
        };

        RequestHandler.getInstance(mContext).addToRequestQueue(stringRequest);
    }

    private void addFriend(Context context, String friend_id) {
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.URL_FRIENDS,
                response -> {
                    try {
                        JSONObject obj = new JSONObject(response);
                        Log.i("ADDFRIEND",obj.toString());
                        if (obj.getBoolean("error")) {
                            Toast.makeText(context, obj.getString("message"), Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", String.valueOf(SharedPrefManager.getInstance(context).getUserId()));
                params.put("friend_id", friend_id);
                params.put("add", "add");

                return params;
            }

        };

        RequestHandler.getInstance(context).addToRequestQueue(stringRequest);

    }

    private void deleteFriend(Context context, String friend_id) {
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.URL_FRIENDS,
                response -> {
                    try {
                        JSONObject obj = new JSONObject(response);

                        if (obj.getBoolean("error")) {
                            Toast.makeText(context, obj.getString("message"), Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", String.valueOf(SharedPrefManager.getInstance(context).getUserId()));
                params.put("friend_id", friend_id);
                params.put("delete", "delete");

                return params;
            }

        };

        RequestHandler.getInstance(context).addToRequestQueue(stringRequest);

    }


}

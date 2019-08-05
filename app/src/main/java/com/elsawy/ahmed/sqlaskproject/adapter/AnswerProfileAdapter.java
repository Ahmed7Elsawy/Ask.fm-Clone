//package com.elsawy.ahmed.sqlaskproject.adapter;
//
//import android.content.Context;
//import android.support.annotation.NonNull;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Toast;
//
//import com.firebase.ui.database.FirebaseRecyclerAdapter;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//import com.project1.ahmed.ask_project.R;
//import com.project1.ahmed.ask_project.Utils.Utilties;
//import com.project1.ahmed.ask_project.models.Answer;
//import com.project1.ahmed.ask_project.models.Like;
//import com.project1.ahmed.ask_project.viewholder.AnswerViewHolder;
//
//import java.util.ArrayList;
//
//
//public class AnswerProfileAdapter extends RecyclerView.Adapter<AnswerViewHolder> {
//
//    private Context mContext;
//
//    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
//    private FirebaseUser userData = this.mAuth.getCurrentUser();
//    private DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
//    private ArrayList<Answer> answersList = new ArrayList<>();
//    private String profileID;
//
//    private FirebaseRecyclerAdapter<Answer ,AnswerViewHolder> mAdapter;
//
//
//    public AnswerProfileAdapter(Context mContext, String profileID) {
//        this.mContext = mContext;
//
//        if (profileID.equals(""))
//            this.profileID = AnswerProfileAdapter.this.userData.getUid();
//        else
//            this.profileID = profileID;
//
//        if (this.userData != null) {
//            AnswerProfileAdapter.this.ref.child("user-objects").child("answers").child(this.profileID).addValueEventListener(new AnswerListener());
//            AnswerProfileAdapter.this.ref.child("user-objects").child("answers").child(this.profileID).keepSynced(true);
//        }
//    }
//
//    @NonNull
//    @Override
//    public AnswerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
//        return new AnswerViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_post, viewGroup, false));
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull AnswerViewHolder holder, int position) {
//
//        Answer currentAnswer = AnswerProfileAdapter.this.answersList.get(position);
//
//        //if (this.profileID.equals(AnswerProfileAdapter.this.userData.getUid()))
//        {
//            holder.user_profile_image.setVisibility(View.GONE);
//            holder.username_TV.setVisibility(View.GONE);
//        }
//
////        holder.question_text_TV.setText(currentAnswer.getQuestion().getQuestionText());
////        holder.answer_text_TV.setText(currentAnswer.getAnswerText());
////        holder.answer_time_TV.setText(Utilties.getTimeAgo(currentAnswer.getAnswerTimestamp()));
////        holder.answer_likes_count_TV.setText(Integer.toString(currentAnswer.getAnswerLikesCount()));
////
////        ArrayList<Like> likeList = currentAnswer.getLikeList();
////        if (likeList != null && checkIsLike(likeList)) {
////            holder.like_btn.setImageResource(R.drawable.ic_favorite_orange_24dp);
////        }
////
////        holder.like_btn.setOnClickListener(view -> {
////            if (likeList == null || !checkIsLike(likeList)) {
////                putLike(likeList, currentAnswer.getAnswerKey());
////                updateLikeCount(currentAnswer.getAnswerLikesCount() + 1, currentAnswer.getAnswerKey());
////                holder.like_btn.setImageResource(R.drawable.ic_favorite_orange_24dp);
////            } else {
////                removeLike(likeList, currentAnswer.getAnswerKey());
////                updateLikeCount(currentAnswer.getAnswerLikesCount() - 1, currentAnswer.getAnswerKey());
////                holder.like_btn.setImageResource(R.drawable.ic_like_black_24dp);
////            }
////        });
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return answersList.size();
//    }
//
//    private void updateLikeCount(int count, String answerKey) {
//        this.ref.child("user-objects").child("answers").child(this.profileID).child(answerKey).child("answerLikesCount").setValue(count);
//    }
//
//    private boolean checkIsLike(ArrayList<Like> likeList) {
//        for (Like like : likeList)
//            if (userData.getUid().equals(like.getUid())) {
//                return true;
//            }
//        return false;
//    }
//
//    private void putLike(ArrayList<Like> likeList, String answerKay) {
//        if (likeList == null) {
//            likeList = new ArrayList<>();
//        }
//
//        Like like = new Like(userData.getUid(), userData.getEmail());
//        likeList.add(like);
//
//        this.ref.child("user-objects").child("answers").child(this.profileID).child(answerKay).child("likes").setValue(likeList);
//        Toast.makeText(mContext, "Like", Toast.LENGTH_SHORT).show();
//    }
//
//    private void removeLike(ArrayList<Like> likeList, String answerKay) {
//        Like like = new Like(userData.getUid(), userData.getEmail());
//        likeList.remove(like);
//        this.ref.child("user-objects").child("answers").child(this.profileID).child(answerKay).child("likes").setValue(likeList);
//        Toast.makeText(mContext, "unLike", Toast.LENGTH_SHORT).show();
//    }
//
//    class AnswerListener implements ValueEventListener {
//        AnswerListener() {
//        }
//
//        public void onDataChange(DataSnapshot dataSnapshot) {
//
//            if (dataSnapshot.getValue() != null) {
//                answersList.clear();
//
//                for (DataSnapshot answerSnapshot : dataSnapshot.getChildren()) {
//
//                    Answer answer = answerSnapshot.getValue(Answer.class);
//
//                    answersList.add(answer);
//
//                }
//
//            }
//            AnswerProfileAdapter.this.notifyDataSetChanged();
//        }
//
//        @Override
//        public void onCancelled(DatabaseError databaseError) {
//        }
//    }
//
//}
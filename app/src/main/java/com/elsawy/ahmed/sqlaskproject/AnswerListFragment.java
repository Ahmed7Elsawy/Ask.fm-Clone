//package com.elsawy.ahmed.sqlaskproject;
//
//import android.os.Bundle;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import androidx.fragment.app.Fragment;
//
//
//public abstract class AnswerListFragment extends Fragment {
//
//    private static final String TAG = "AnswerListFragment";
//
////    protected DatabaseReference mDatabase;
//
//
//    private FirebaseRecyclerAdapter<Answer, AnswerViewHolder> mAdapter;
//    protected RecyclerView mRecycler;
//    private LinearLayoutManager mManager;
//
//    public AnswerListFragment() {}
//
//    @Override
//    public void onActivityCreated(Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//
//        // Set up Layout Manager, reverse layout
//        mManager = new LinearLayoutManager(getActivity());
//        mManager.setReverseLayout(true);
//        mManager.setStackFromEnd(true);
//        mRecycler.setLayoutManager(mManager);
//
//        // Set up FirebaseRecyclerAdapter with the Query
//        Query postsQuery = getQuery(mDatabase);
//
//        FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<Answer>()
//                .setQuery(postsQuery, Answer.class)
//                .build();
//
//        mAdapter = new FirebaseRecyclerAdapter<Answer, AnswerViewHolder>(options) {
//
//            @Override
//            public AnswerViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
//                LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
//                return new AnswerViewHolder(inflater.inflate(R.layout.item_post, viewGroup, false));
//            }
//
//            @Override
//            protected void onBindViewHolder(AnswerViewHolder holder, int position, final Answer currentAnswer) {
//                final DatabaseReference postRef = getRef(position);
//
//
//                //if (this.profileID.equals(AnswerProfileAdapter.this.userData.getUid()))
//                {
////                    holder.user_profile_image.setVisibility(View.GONE);
////                    holder.username_TV.setVisibility(View.GONE);
//                }
//
//
//                // Determine if the current user has liked this post and set UI accordingly
//                if (currentAnswer.likes.containsKey(getUid())) {
//                    holder.like_btn.setImageResource(R.drawable.ic_favorite_orange_24dp);
//                } else {
//                    holder.like_btn.setImageResource(R.drawable.ic_like_black_24dp);
//                }
//
//                // Bind Answer to ViewHolder, setting OnClickListener for the star button
//                holder.bindToAnswer(currentAnswer, new View.OnClickListener() {
//                    @Override
//                    public void onClick(View starView) {
//                        // Need to write to both places the post is stored
//                        DatabaseReference globalPostRef = mDatabase.child("user-objects").child("global-answers").child(postRef.getKey());
//                        DatabaseReference userPostRef = mDatabase.child("user-objects").child("user-answers").child(currentAnswer.question.getReceiverID()).child(postRef.getKey());
//
//                        // Run two transactions
//                        onStarClicked(globalPostRef);
//                        onStarClicked(userPostRef);
//                    }
//                });
//
//            }
//        };
//        mRecycler.setAdapter(mAdapter);
//    }
//
//    // [START post_stars_transaction]
//    private void onStarClicked(DatabaseReference postRef) {
//        postRef.runTransaction(new Transaction.Handler() {
//            @Override
//            public Transaction.Result doTransaction(MutableData mutableData) {
//                Answer p = mutableData.getValue(Answer.class);
//                if (p == null) {
//                    return Transaction.success(mutableData);
//                }
//
//                if (p.likes.containsKey(getUid())) {
//                    // Unstar the post and remove self from stars
//                    p.likesCount = p.likesCount - 1;
//                    p.likes.remove(getUid());
//                } else {
//                    // Star the post and add self to stars
//                    p.likesCount = p.likesCount + 1;
//                    p.likes.put(getUid(), true);
//                }
//
//                // Set value and report transaction success
//                mutableData.setValue(p);
//                return Transaction.success(mutableData);
//            }
//
//            @Override
//            public void onComplete(DatabaseError databaseError, boolean b,
//                                   DataSnapshot dataSnapshot) {
//                // Transaction completed
//                Log.d(TAG, "postTransaction:onComplete:" + databaseError);
//            }
//        });
//    }
//    // [END post_stars_transaction]
//
//    @Override
//    public void onStart() {
//        super.onStart();
//        if (mAdapter != null) {
//            mAdapter.startListening();
//        }
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//        if (mAdapter != null) {
//            mAdapter.stopListening();
//        }
//    }
//
//    public String getUid() {
//        return FirebaseAuth.getInstance().getCurrentUser().getUid();
//    }
//
//    public abstract Query getQuery(DatabaseReference databaseReference);
//}

package com.elsawy.ahmed.sqlaskproject.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.Map;

public class Answer implements Parcelable {

//    public String uid;
    private String username;
    private String answerText;
    private String answerID;
    private Long timestamp;
    private int likesCount;
    private boolean isLike = false;
    private Question question;
    public Map<String, Boolean> likes = new HashMap<>();


    public Answer() {
        question = new Question();
    }

    public Answer(Question question) {
        this.question =question;
    }

    protected Answer(Parcel in) {
        username = in.readString();
        answerText = in.readString();
        answerID = in.readString();
        if (in.readByte() == 0) {
            timestamp = null;
        } else {
            timestamp = in.readLong();
        }
        likesCount = in.readInt();
        isLike = in.readByte() != 0;
        question = in.readParcelable(Question.class.getClassLoader());
    }

    public static final Creator<Answer> CREATOR = new Creator<Answer>() {
        @Override
        public Answer createFromParcel(Parcel in) {
            return new Answer(in);
        }

        @Override
        public Answer[] newArray(int size) {
            return new Answer[size];
        }
    };

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    public String getAnswerID() {
        return answerID;
    }

    public void setAnswerID(String answerID) { this.answerID = answerID; }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {this.question = question;}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Map<String, Boolean> getLikes() {
        return likes;
    }

    public boolean isLike() {
        return isLike;
    }

    public void setLike(boolean like) {
        isLike = like;
    }

    public void setLikes(Map<String, Boolean> likes) {
        this.likes = likes;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
//        result.put("uid", uid);
//        result.put("author", author);
        result.put("answerText", answerText);
        result.put("answerID", answerID);
        result.put("timestamp", timestamp);
        result.put("likesCount", likesCount);
        result.put("question", question);
        result.put("likes", likes);

        return result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(username);
        parcel.writeString(answerText);
        parcel.writeString(answerID);
        if (timestamp == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeLong(timestamp);
        }
        parcel.writeInt(likesCount);
        parcel.writeByte((byte) (isLike ? 1 : 0));
        parcel.writeParcelable(question, i);
    }
}

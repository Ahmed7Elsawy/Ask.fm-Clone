package com.elsawy.ahmed.sqlaskproject.models;


import android.os.Parcel;
import android.os.Parcelable;

public class Question implements Parcelable {

    private String askerID;
    private String askerUsername;
    private String ReceiverID;
    private String questionText;
    private String questionId;
    private boolean isAnonymous;
    private Long questionTimestamp;

    public Question(){}

    public Question(String askerID, String ReceiverID, String questionText, boolean isAnonymous, Long questionTimestamp, String questionId) {
        this.askerID = askerID;
        this.ReceiverID = ReceiverID;
        this.questionText = questionText;
        this.isAnonymous = isAnonymous;
        this.questionTimestamp = questionTimestamp;
        this.questionId = questionId;
    }

    protected Question(Parcel in) {
        askerID = in.readString();
        ReceiverID = in.readString();
        questionText = in.readString();
        questionId = in.readString();
        isAnonymous = in.readByte() != 0;
        if (in.readByte() == 0) {
            questionTimestamp = null;
        } else {
            questionTimestamp = in.readLong();
        }
    }

    public static final Creator<Question> CREATOR = new Creator<Question>() {
        @Override
        public Question createFromParcel(Parcel in) {
            return new Question(in);
        }

        @Override
        public Question[] newArray(int size) {
            return new Question[size];
        }
    };

    public String getAskerID() {
        return askerID;
    }

    public String getAskerUsername() {
        return askerUsername;
    }

    public void setAskerUsername(String askerUsername) {
        this.askerUsername = askerUsername;
    }

    public boolean isAnonymous() {
        return isAnonymous;
    }

    public void setAskerID(String askerID) {
        this.askerID = askerID;
    }

    public String getReceiverID() {
        return ReceiverID;
    }

    public void setReceiverID(String receiverID) {
        this.ReceiverID = receiverID;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public boolean getAnonymous() {
        return isAnonymous;
    }

    public void setAnonymous(boolean anonymous) {
        isAnonymous = anonymous;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public Long getQuestionTimestamp() {
        return questionTimestamp;
    }

    public void setQuestionTimestamp(Long questionTimestamp) {
        this.questionTimestamp = questionTimestamp;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(askerID);
        parcel.writeString(ReceiverID);
        parcel.writeString(questionText);
        parcel.writeString(questionId);
        parcel.writeByte((byte) (isAnonymous ? 1 : 0));
        if (questionTimestamp == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeLong(questionTimestamp);
        }
    }
}

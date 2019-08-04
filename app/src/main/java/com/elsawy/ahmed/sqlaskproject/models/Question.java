package com.elsawy.ahmed.sqlaskproject.models;


public class Question {

    private String askerID;
    private String Receiver_userID;
    private String questionText;
    private String questionId;
    private boolean isAnonymous;
    private Long questionTimestamp;

    public Question(){}

    public Question(String askerID, String Receiver_userID, String questionText, boolean isAnonymous, Long questionTimestamp,String questionId) {
        this.askerID = askerID;
        this.Receiver_userID = Receiver_userID;
        this.questionText = questionText;
        this.isAnonymous = isAnonymous;
        this.questionTimestamp = questionTimestamp;
        this.questionId = questionId;
    }

    public String getAskerID() {
        return askerID;
    }

    public void setAskerID(String askerID) {
        this.askerID = askerID;
    }

    public String getReceiver_userID() {
        return Receiver_userID;
    }

    public void setReceiver_userID(String receiver_userID) {
        this.Receiver_userID = receiver_userID;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public boolean isAnonymous() {
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

}

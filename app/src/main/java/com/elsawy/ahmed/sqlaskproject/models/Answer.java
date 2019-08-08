package com.elsawy.ahmed.sqlaskproject.models;

import java.util.HashMap;
import java.util.Map;

public class Answer  {

//    public String uid;
    private String username;
    private String answerText;
    private String answerID;
    private Long timestamp;
    private int likesCount;
    private Question question;
    public Map<String, Boolean> likes = new HashMap<>();


    public Answer() {
        question = new Question();
    }

    public Answer(Question question) {
        this.question =question;
    }

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
}

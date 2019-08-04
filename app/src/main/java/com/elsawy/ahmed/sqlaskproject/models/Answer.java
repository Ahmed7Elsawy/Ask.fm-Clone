package com.elsawy.ahmed.sqlaskproject.models;

import java.util.HashMap;
import java.util.Map;

public class Answer  {

//    public String uid;
//    public String author;
    public String answerText;
    public String answerKey;
    public Long timestamp;
    public int likesCount;
    public Question question;
    public Map<String, Boolean> likes = new HashMap<>();


    public Answer() {}

    public Answer(Question question) {
        this.question =question;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
//        result.put("uid", uid);
//        result.put("author", author);
        result.put("answerText", answerText);
        result.put("answerKey", answerKey);
        result.put("timestamp", timestamp);
        result.put("likesCount", likesCount);
        result.put("question", question);
        result.put("likes", likes);

        return result;
    }
}

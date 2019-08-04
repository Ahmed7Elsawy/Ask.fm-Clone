package com.elsawy.ahmed.sqlaskproject.models;


public class Like {

    private String uid;
    private String author_username;

    public Like() {}

    public Like(String uid, String author_username) {
        this.uid = uid;
        this.author_username = author_username;
    }

    public String getAuthor_username() {
        return author_username;
    }

    public void setAuthor_username(String author_username) {
        this.author_username = author_username;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }


    @Override
    public boolean equals(Object obj) {
        return  (this.getUid().equals(((Like)obj).getUid()));
    }
}

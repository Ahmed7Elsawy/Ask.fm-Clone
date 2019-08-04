package com.elsawy.ahmed.sqlaskproject.models;


public class User {

    public String username;
    public String email;
    public String gender;
    public String birthDay;

    public User() {
    }

    public User(String username, String email, String gender, String birthDay) {
        this.username = username;
        this.email = email;
        this.gender = gender;
        this.birthDay = birthDay;
    }
}

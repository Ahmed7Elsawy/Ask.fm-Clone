package com.elsawy.ahmed.sqlaskproject.models;


public class UserInfo {

    public String username;
    public String email;
    public String gender;
    public String birthDay;

    public UserInfo() {
    }

    public UserInfo(String username, String email, String gender, String birthDay) {
        this.username = username;
        this.email = email;
        this.gender = gender;
        this.birthDay = birthDay;
    }
}

package com.elsawy.ahmed.sqlaskproject.models;


public class Friend  {

    private String friendID;
    private boolean favorite;
    private String friendName;

    public Friend() {
    }

    public Friend(boolean favorite, String friendKey, String friendName) {
        this.favorite = favorite;
        this.friendID = friendKey;
        this.friendName = friendName;
    }


    public String getFriendID() {
        return friendID;
    }

    public void setFriendID(String friendID) {
        this.friendID = friendID;
    }

    public boolean getFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public String getFriendName() {
        return friendName;
    }

    public void setFriendName(String friendName) {
        this.friendName = friendName;
    }


}

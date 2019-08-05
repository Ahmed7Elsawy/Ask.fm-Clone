package com.elsawy.ahmed.sqlaskproject.models;


import android.os.Parcel;
import android.os.Parcelable;

public class Friend implements Parcelable {

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


    protected Friend(Parcel in) {
        friendID = in.readString();
        favorite = in.readByte() != 0;
        friendName = in.readString();
    }

    public static final Creator<Friend> CREATOR = new Creator<Friend>() {
        @Override
        public Friend createFromParcel(Parcel in) {
            return new Friend(in);
        }

        @Override
        public Friend[] newArray(int size) {
            return new Friend[size];
        }
    };

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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(friendID);
        parcel.writeByte((byte) (favorite ? 1 : 0));
        parcel.writeString(friendName);
    }


}

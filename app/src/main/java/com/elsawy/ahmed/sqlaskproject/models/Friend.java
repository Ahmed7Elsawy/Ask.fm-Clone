package com.elsawy.ahmed.sqlaskproject.models;


import android.os.Parcel;
import android.os.Parcelable;

public class Friend extends User implements Parcelable {

    private boolean favorite;

    public Friend() {
    }

    public Friend(boolean favorite, String friendKey, String friendName) {
        this.favorite = favorite;
        super.setUserID(friendKey);
        super.setUsername(friendName);
    }

    protected Friend(Parcel in) {
        favorite = in.readByte() != 0;
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


    public boolean getFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeByte((byte) (favorite ? 1 : 0));
    }

}
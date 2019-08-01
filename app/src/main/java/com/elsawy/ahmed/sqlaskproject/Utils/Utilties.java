package com.elsawy.ahmed.sqlaskproject.Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;



public class Utilties {

    private static final long YEAR_SECOND = 31556926, MONTH_SECOND = 2629743, DAY_SECOND = 86400, HOUR_SECOND = 3600, MINUTE_SECOND = 60;

    public static String getTimeAgo(long timestamp) {
        String s;
        long now = System.currentTimeMillis();
        long difTime = (now - timestamp) / 1000;


        if (timestamp > now || timestamp <= 0) {
            return null;
        }
        if (difTime == YEAR_SECOND)
            s = "a year ago";
        else if (difTime > YEAR_SECOND)
            s = "about " + (difTime / YEAR_SECOND) + " years ago";
        else if (difTime == MONTH_SECOND)
            s = "a month ago";
        else if (difTime > MONTH_SECOND)
            s = "about " + (difTime / MONTH_SECOND) + " months ago";
        else if (difTime == DAY_SECOND)
            s = "yesterday";
        else if (difTime > DAY_SECOND)
            s = "more than " + (difTime / DAY_SECOND) + " days ago";

        else if (difTime == HOUR_SECOND)
            s = "an hour ago";
        else if (difTime > HOUR_SECOND)
            s = "about " + (difTime / HOUR_SECOND) + " hours ago";
        else if (difTime > MINUTE_SECOND)
            s = "more than " + (difTime / MINUTE_SECOND) + " minutes ago";
        else
            s = "just now";
        return s;
    }


//    public static Boolean isConnected(Context mContext) {
//        NetworkInfo activeNetwork = ((ConnectivityManager) mContext.getSystemService("connectivity")).getActiveNetworkInfo();
//        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
//        return Boolean.valueOf(isConnected);
//    }

}
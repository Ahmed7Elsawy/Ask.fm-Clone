package com.elsawy.ahmed.sqlaskproject.Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.Log;


import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.TimeZone;


public class Utilties {

    private static final long YEAR_SECOND = 31556926, MONTH_SECOND = 2629743, DAY_SECOND = 86400, HOUR_SECOND = 3600, MINUTE_SECOND = 60;

    public static String getTimeAgo(long timestamp) {
        String s;
        long Now = System.currentTimeMillis();

        long difTime = (Now - timestamp) / 1000;


        if (timestamp > Now || timestamp <= 0) {
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

    public static void getTime(){
        Date today = new Date();

        //displaying this date on IST timezone
        DateFormat df = new SimpleDateFormat("dd-MM-yy HH:mm:SS z");
        df.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
        String IST = df.format(today);

        Log.i("TGTGTG4",today+"");
        Log.i("TGTGTG5",IST);

        Timestamp timestamp = Timestamp.valueOf(IST);

        long Now = System.currentTimeMillis();
        Log.i("TGTGTG1",today.getTime()+"");
        Log.i("TGTGTG2",Now+"");
        Log.i("TGTGTG3",timestamp.getTime()+"");



    }


//    public static Boolean isConnected(Context mContext) {
//        NetworkInfo activeNetwork = ((ConnectivityManager) mContext.getSystemService("connectivity")).getActiveNetworkInfo();
//        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
//        return Boolean.valueOf(isConnected);
//    }

}
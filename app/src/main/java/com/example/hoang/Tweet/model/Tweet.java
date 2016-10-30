package com.example.hoang.Tweet.model;

import android.text.format.DateUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by hoang on 10/27/16.
 */

public class Tweet {
    private String body;
    private long uid;
    private String createdAt;
    private User user;
    private String createdAtTimeSpan;

    public String getBody() {
        return body;
    }

    public long getUid() {
        return uid;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public User getUser() {
        return user;
    }

    public String getCreatedAtTimeSpan() {
        return getRelativeTimeAgo(createdAt);
    }
    public static Tweet fromJson(JSONObject jsonObject){
        Tweet  tweet = new Tweet();
        try {
            tweet.body  =  jsonObject.getString("text" );
            tweet.uid = jsonObject.getLong("id");
            tweet.createdAt = jsonObject.getString("created_at");
            tweet.user = User.fromJson(jsonObject.getJSONObject("user"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return tweet;
    }

    public static ArrayList<Tweet> fromJsonArray(JSONArray response) {
        ArrayList<Tweet> tweets  = new ArrayList<>();
        for (int i = 0; i < response.length(); i++){
            try {
                JSONObject tweetJsonObject = response.getJSONObject(i);
                Tweet tweet = Tweet.fromJson(tweetJsonObject);
                if (tweet != null){
                    tweets.add(tweet);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                continue;
            }
        }
        return tweets;
    }
    public String getRelativeTimeAgo(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS, DateUtils.FORMAT_ABBREV_ALL).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        relativeDate = relativeDate.replace(" mins", "m");
        relativeDate = relativeDate.replace(" min", "m");
        relativeDate = relativeDate.replace(" hours", "h");
        relativeDate = relativeDate.replace(" hour", "h");
        relativeDate = relativeDate.replace(" days", "d");
        relativeDate = relativeDate.replace(" day", "d");

        return relativeDate;
    }


}

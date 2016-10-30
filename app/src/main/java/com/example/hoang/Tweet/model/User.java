package com.example.hoang.Tweet.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by hoang on 10/27/16.
 */

public class User {
    private String name;
    private long uid;
    private String screeName;
    private String profileImgUrl;

    public String getName() {
        return name;
    }

    public long getUid() {
        return uid;
    }

    public String getScreeName() {
        return screeName;
    }

    public String getProfileImgUrl() {
        return profileImgUrl;
    }

    public static User fromJson(JSONObject jsonObject) {
        User user = new User();
        try {
            user.profileImgUrl = jsonObject.getString("profile_image_url");
            user.name = jsonObject.getString("name");
            user.uid = jsonObject.getLong("id");
            user.screeName = jsonObject.getString("screen_name");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return user;
    }
}

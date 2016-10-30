package com.example.hoang.Tweet.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by hoang on 10/30/16.
 */

public class Entities {
    private String mediaUrl;


    public String getMediaUrl() {
        return mediaUrl;
    }

    public static Entities fromJson(JSONObject jsonObject) {
        Entities entities = new Entities();
        try {
            entities.mediaUrl = jsonObject.getString("media_url");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return entities;
    }
    public static ArrayList<Entities> fromJsonArray(JSONArray response) {
        ArrayList<Entities> entitiess  = new ArrayList<>();
        for (int i = 0; i < response.length(); i++){
            try {
                JSONObject tweetJsonObject = response.getJSONObject(i);
                Entities entities = Entities.fromJson(tweetJsonObject);
                if (entities != null){
                    entitiess.add(entities);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                continue;
            }
        }
        return entitiess;
    }
}

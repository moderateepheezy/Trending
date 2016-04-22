package com.example.simpumind.trends.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by simpumind on 2/18/16.
 */
public class Trends {

    public Integer tweetVolume;
    public String names;
    public String query;
    public String url;

    public Trends(JSONObject json){
        try{
            this.tweetVolume = json.getInt("tweet_volume");
            this.names = json.getString("name");
            this.query = json.getString("query");
            this.url = json.getString("url");
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    public Integer getTweetVolume() {
        return tweetVolume;
    }

    public void setTweetVolume(Integer tweetVolume) {
        this.tweetVolume = tweetVolume;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

package com.example.simpumind.trends.models;

/**
 * Created by simpumind on 2/18/16.
 */
public class TrendListItem {

    public Integer tweetVolume;
    public String names;
    public String query;
    public String url;

    public TrendListItem(Integer tweetVolume, String names, String query, String url) {
        this.tweetVolume = tweetVolume;
        this.names = names;
        this.query = query;
        this.url = url;
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

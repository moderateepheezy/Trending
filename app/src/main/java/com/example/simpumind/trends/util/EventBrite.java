package com.example.simpumind.trends.util;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by simpumind on 2/29/16.
 */
public class EventBrite {

    public String startTime;
    public String endTime;
    public String place;
    public String description;
    public String id;
    public String name;
    public String url;

    public EventBrite(JSONObject json){
        try {
            this.name = json.getString("name");
            this.id = json.getString("id");
            this.endTime = json.getString("end");
            this.startTime = json.getString("start");
            this.place = json.getString("place");
            this.description = json.getString("description");
            this.url = json.getString("url");
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

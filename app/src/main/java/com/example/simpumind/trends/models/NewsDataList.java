package com.example.simpumind.trends.models;

/**
 * Created by SimpuMind on 4/5/16.
 */
public class NewsDataList {

    public String startTime;
    public String newsTitle;
    public String comments;
    public String links;
    public int img;
    public String dscription;

    public NewsDataList(String startTime, String newsTitle, String comments, String links, int img, String dscription) {
        this.startTime = startTime;
        this.newsTitle = newsTitle;
        this.comments = comments;
        this.links = links;
        this.img = img;
        this.dscription = dscription;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getLinks() {
        return links;
    }

    public void setLinks(String links) {
        this.links = links;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getDscription() {
        return dscription;
    }

    public void setDscription(String dscription) {
        this.dscription = dscription;
    }
}

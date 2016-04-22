package com.example.simpumind.trends.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by SimpuMind on 4/4/16.
 */
public class EventsDataList implements Parcelable{

    public String place;
    public String url;
    public String placeName;
    public String startTime;
    public String endTime;
    public String eventName;
    public String description;
    public String ids;
    public String city;
    public String country;
    public String place_id;
    public int img;
    public String imgName;


    public EventsDataList(String place, String url, String placeName, String startTime,
                          String endTime, String eventName, String description, String ids, String city,
                          String country, String place_id, int img, String imgName) {
        this.place = place;
        this.url = url;
        this.placeName = placeName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.eventName = eventName;
        this.description = description;
        this.ids = ids;
        this.city = city;
        this.country = country;
        this.place_id = place_id;
        this.img = img;
        this.imgName = imgName;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
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

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPlace_id() {
        return place_id;
    }

    public void setPlace_id(String place_id) {
        this.place_id = place_id;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }

    public static Creator<EventsDataList> getCREATOR() {
        return CREATOR;
    }

    protected EventsDataList(Parcel in) {
        place = in.readString();
        url = in.readString();
        placeName = in.readString();
        startTime = in.readString();
        endTime = in.readString();
        eventName = in.readString();
        description = in.readString();
        ids = in.readString();
        city = in.readString();
        country = in.readString();
        place_id = in.readString();
        img = in.readInt();
        imgName = in.readString();
    }

    public static final Creator<EventsDataList> CREATOR = new Creator<EventsDataList>() {
        @Override
        public EventsDataList createFromParcel(Parcel in) {
            return new EventsDataList(in);
        }

        @Override
        public EventsDataList[] newArray(int size) {
            return new EventsDataList[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(place);
        dest.writeString(url);
        dest.writeString(placeName);
        dest.writeString(startTime);
        dest.writeString(endTime);
        dest.writeString(eventName);
        dest.writeString(description);
        dest.writeString(ids);
        dest.writeString(city);
        dest.writeString(country);
        dest.writeString(place_id);
        dest.writeInt(img);
        dest.writeString(imgName);
    }

}

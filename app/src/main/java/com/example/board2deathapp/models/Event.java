package com.example.board2deathapp.models;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Event extends Model {

    private String mTitle;
    private String mDesc;
    private Date mStartDate;
    private Date mEndDate;

    public Event(String title, String desc, Date startDate, Date endDate) {
        mTitle = title;
        mDesc = desc;
        mStartDate = startDate;
        mEndDate = endDate;
    }

    public void setName(String name) {
       mTitle = name;
    }

    public void setDesc(String desc) {
       mDesc = desc;
    }

    public void setStartDate(Date startDate) {
        mStartDate = startDate;
    }

    public void setEndDate(Date endDate) {
       mEndDate = endDate;
    }

    public String getName() {
       return mTitle;
    }

    public String getDesc() {
       return mDesc;
    }

    public Date getStartDate() {
       return mStartDate;
    }

    public Date getEndDate() {
       return mEndDate;
    }

    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("title", mTitle);
        map.put("description", mDesc);
        map.put("start_date", mStartDate);
        map.put("end_date", mEndDate);
        return map;
    }

    @Override
    public void fromMap(Map<String, Object> map) {
        mTitle = (String)map.get("title");
        mDesc = (String)map.get("description");
        mStartDate = (Date)map.get("start_date");
        mEndDate = (Date)map.get("end_date");
    }
}

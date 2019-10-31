package com.example.board2deathapp.models;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.board2deathapp.ui.calendar.MyEventRecyclerViewAdapter;
import com.google.firebase.Timestamp;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
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

    public Event() {}

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

    public String getTitle() {
       return mTitle;
    }

    public String getDesc() {
       return mDesc;
    }

    private static boolean isDatesSameDay(Date start, Date end) {
       return start.getYear() == end.getYear() && start.getMonth() == end.getMonth() && start.getDay() == end.getDay();
    }

    public String getDateDuration(Locale locale) {
        DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.SHORT, locale);
        if (isDatesSameDay(mStartDate, mEndDate)) {
            DateFormat timeFormat = DateFormat.getTimeInstance(DateFormat.SHORT, locale);
            return dateFormat.format(mStartDate) + " - " + timeFormat.format(mEndDate);
        }
        return dateFormat.format(mStartDate) + " - " + dateFormat.format(mEndDate);
    }

    public String getDescOneSentence() {
        if (mDesc == null) {
            return "";
        }
        StringBuilder strBuilder = new StringBuilder();
        for (char c : mDesc.toCharArray()) {
            if (c == '.' || c == '?' || c == '!') {
                strBuilder.append(c);
                break;
            }
            strBuilder.append(c);
        }
        return strBuilder.toString();
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
        Timestamp startDateTimestamp = (Timestamp)map.get("start_date");
        Timestamp endDateTimestamp = (Timestamp)map.get("end_date");
        if (startDateTimestamp == null || endDateTimestamp == null) {
           return;
        }
        mStartDate = startDateTimestamp.toDate();
        mEndDate = endDateTimestamp.toDate();
    }
}

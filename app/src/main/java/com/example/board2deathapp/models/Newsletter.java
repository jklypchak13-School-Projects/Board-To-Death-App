package com.example.board2deathapp.models;

import android.app.Activity;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.widget.Toast;

import com.example.board2deathapp.models.DBResponse;
import com.example.board2deathapp.models.Model;

import androidx.fragment.app.FragmentActivity;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Calendar;

public class Newsletter extends Model {

    private static String TAG = "NEWSLETTER";

    private String description;
    private Date date;
    private String title;
    private String username;

    public Newsletter(String title, String description, String username, final Activity a) {
        this.title = title;
        this.description = description;
        this.date = Calendar.getInstance().getTime();
        this.username = username;
    }

    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> attrs = new HashMap<String, Object>();

        attrs.put("title",this.title);
        attrs.put("description", this.description);
        attrs.put("date", this.date);
        attrs.put("username", this.username);
        return attrs;
    }

    @Override
    public void fromMap(Map<String, Object> data) {

        this.title = (String)data.get("title");
        this.description = (String) data.get("description");
        Timestamp t = (Timestamp)data.get("date");
        if(t != null){
            date = t.toDate();
        }
        this.username = (String) data.get("username");


    }


    public Newsletter() {

    }


    public String getDescription() {
        return this.description;
    }
    public String getDate() {
        return DateFormat.getDateTimeInstance().format(this.date);
    }
    public String getUsername() {
        return this.username;
    }
    public String getTitle(){return this.title;}

    public void setTitle(String d){this.title = d;}
    public void setDescription(String d){this.description=d;}
}

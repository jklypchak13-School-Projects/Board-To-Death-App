package com.example.board2deathapp.models;

import android.app.Activity;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.widget.Toast;

import com.example.board2deathapp.models.DBResponse;
import com.example.board2deathapp.models.Model;

import androidx.fragment.app.FragmentActivity;

import com.google.firebase.firestore.DocumentReference;

import java.util.HashMap;
import java.util.Map;


public class Newsletter extends Model {

    private static String TAG = "NEWSLETTER";

    public String description;
    public String date;
    public String username;
    public String owner;

    public Newsletter(String Description, String date, String Username, final Activity a) {

        this.description = Description;
        this.date = date;
        this.username = Username;

        this.create(new DBResponse(a) {
            @Override
            public <T> void onSuccess(T t) {
                Log.d(TAG, t.toString());
                Newsletter.this.setID(((DocumentReference) t).getId());
                if (a != null) {
                    Toast.makeText(a.getApplicationContext(), "Successfully Created a Newsletter", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public <T> void onFailure(T t) {
                if (a != null) {
                    Toast.makeText(a.getApplicationContext(), "There was an issue while creating your Newsletter.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> attrs = new HashMap<String, Object>();


        attrs.put("description", this.description);
        attrs.put("date", this.date);
        attrs.put("username", this.username);
        return attrs;
    }

    @Override
    public void fromMap(Map<String, Object> data) {


        this.description = (String) data.get("description");
        this.date = (String) data.get("date");
        this.username = (String) data.get("username");


    }


    public Newsletter() {

    }


    public String getDescription() {
        return this.description;
    }

    public String getdate() {
        return this.date;
    }

    public String getUsername() {
        return this.username;
    }
}

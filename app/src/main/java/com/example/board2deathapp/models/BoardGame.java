package com.example.board2deathapp.models;

import android.app.Activity;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.widget.Toast;

import com.example.board2deathapp.models.DBResponse;
import com.example.board2deathapp.models.Model;
import com.google.firebase.firestore.DocumentReference;

import java.util.HashMap;
import java.util.Map;


public class BoardGame extends Model {
    private static String TAG = "BOARDGAME";
    private String name;
    private String description;
    private String owner;
    private int player_count;
    private double play_time;
    private static int descriptionLength = 37;

    public BoardGame(String name, String des, String owner, int players, double time, final Activity a) {
        this.name = name;
        this.description = des;
        this.owner = owner;
        this.player_count = players;
        this.play_time = time;

        this.create(new DBResponse(a) {
            @Override
            public <T> void onSuccess(T t) {
                Log.d(TAG, t.toString());
                BoardGame.this.setID(((DocumentReference) t).getId());
                if (a != null) {
                    Toast.makeText(a.getApplicationContext(), "Successfully Created your Game", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public <T> void onFailure(T t) {
                if (a != null) {
                    Toast.makeText(a.getApplicationContext(), "There was an issue created your game.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> attrs = new HashMap<String, Object>();
        attrs.put("name", this.name);
        attrs.put("description", this.description);
        attrs.put("owner", this.owner);
        attrs.put("player_count", this.player_count);
        attrs.put("play_time", this.play_time);
        return attrs;
    }

    @Override
    public void fromMap(Map<String, Object> data) {
        this.name = (String) data.get("name");
        this.description = (String) data.get("description");
        this.owner = (String) data.get("owner");
        this.player_count = Math.toIntExact((long) data.get("player_count"));
        this.play_time = (double) data.get("play_time");


    }

    public BoardGame() {

    }

    public String getTitle() {
        return this.name;
    }

    public String getOwner() {
        return this.owner;
    }

    public double getTime() {
        return this.play_time;
    }

    public String getDescription() {
        return this.description;
    }

    public int getCount() {
        return this.player_count;
    }

    public String getDescriptionPreview() {
        if (this.description.length() < descriptionLength) {
            return this.description;
        } else {
            return this.description.substring(0, descriptionLength) + "...";
        }
    }

    public void setName(String n) {
        name = n;
    }

    public void setDescription(String d) {
        description = d;
    }

    public void setTime(double t) {
        play_time = t;
    }

    public void setCount(int c) {
        player_count = c;
    }


}

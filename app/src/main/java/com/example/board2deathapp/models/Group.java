package com.example.board2deathapp.models;
import android.app.Activity;
import android.os.Parcel;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Group extends Model {
    public String groupName;
    public String description;
    public String date;
    public int maxGroupSize;
    public ArrayList<String> users;
    public String game;
    public String owner;

    public Group(String name, String description, String date, int maxGroupSize, String game, String owner){
        this.groupName = name;
        this.description = description;
        this.date = date;
        this.maxGroupSize = maxGroupSize;
        this.game = game;
        this.owner = owner;
        this.users = new ArrayList<String>();
    }

    @Override
    public Map<String, Object> toMap(){
        Map<String, Object> attrs = new HashMap<String,Object>();
        attrs.put("groupName", this.groupName);
        attrs.put("description", this.description);
        attrs.put("owner", this.owner);
        attrs.put("date", this.date);
        attrs.put("game", this.game);
        attrs.put("maxGroupSize", this.maxGroupSize);
        attrs.put("users", this.users.toArray());
        return attrs;
    }

    @Override
    public void fromMap(Map<String,Object> data){
        this.groupName = (String)data.get("groupName");
        this.description = (String)data.get("description");
        this.owner = (String)data.get("owner");
        this.date = (String)data.get("date");
        this.game = (String)data.get("game");
        this.maxGroupSize = (int)data.get("maxGroupSize");
        this.users = new ArrayList<>();



    }
    public Group(){

    }

    public String getGameString(){
        return game;
    }

    public String getGroupName(){
        return groupName;
    }

    public String getDescription(){
        return description;
    }

    public String getDate(){
        return date;
    }

    public String getOwner(){
        return owner;
    }
}

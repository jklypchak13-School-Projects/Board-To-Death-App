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
    public String name;
    public String description;
    public String owner;
    public int player_count;
    public double play_time;

    public BoardGame(String name, String des, String owner, int players, double time, final Activity a){
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
    public Map<String, Object> toMap(){
        Map<String, Object> attrs = new HashMap<String,Object>();
        attrs.put("name", this.name);
        attrs.put("description", this.description);
        attrs.put("owner", this.owner);
        attrs.put("player_count", this.player_count);
        attrs.put("play_time", this.play_time);
        return attrs;
    }

    @Override
    public void fromMap(Map<String,Object> data){
        this.name = (String)data.get("name");
        this.description = (String)data.get("description");
        this.owner = (String)data.get("owner");
        this.player_count = (int)data.get("player_count");
        this.play_time = (double)data.get("play_time");


    }
    public BoardGame(){

    }
    
    public String getTitle(){
        return this.name;
    }

    public String getOwner(){
        return this.owner;
    }

    public double getTime(){
        return this.play_time;
    }

    public String getDescription(){
        return this.description;
    }

    public int getCount(){
        return this.player_count;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(name);
        out.writeString(description);
        out.writeString(owner);
        out.writeInt(player_count);
        out.writeDouble(play_time);
    }

    public static final Parcelable.Creator<BoardGame> CREATOR
            = new Parcelable.Creator<BoardGame>() {
        public BoardGame createFromParcel(Parcel in) {
            return new BoardGame(in);
        }

        public BoardGame[] newArray(int size) {
            return new BoardGame[size];
        }
    };

    private BoardGame(Parcel in) {
        name= in.readString();
        description = in.readString();
        owner = in.readString();
        player_count = in.readInt();
        play_time = in.readDouble();
    }
}

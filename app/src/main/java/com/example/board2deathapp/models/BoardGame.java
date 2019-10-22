//package com.example.board2deathapp.models;
//
//import android.app.Activity;
//import android.os.Parcel;
//import android.os.Parcelable;
//import android.util.Log;
//import android.widget.Toast;
//
//import com.google.firebase.firestore.DocumentReference;
//
//import java.util.HashMap;
//import java.util.Map;
//
//public class BoardGame extends Model {
//    private static String TAG = "BOARDGAME";
//    public String name;
//    public String description;
//    public String owner;
//    public int player_count;
//    public double play_time;
//
//    public BoardGame(String name, String des, String owner, int players, double time, final Activity a){
//        this.name = name;
//        this.description = des;
//        this.owner = owner;
//        this.player_count = players;
//        this.play_time = time;
//
//        this.create(new DBResponse(a) {
//            @Override
//            public <T> void onSuccess(T t, Model m) {
//                Log.d(TAG, t.toString());
//                m.setID((DocumentReference)t);
//                Toast.makeText(a.getApplicationContext(),"Successfully Created your Game",Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public <T> void onFailure(T t) {
//                Toast.makeText(a.getApplicationContext(), "There was an issue created your game.", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//    public BoardGame(){
//
//    }
//
//    public String getTitle(){
//        return this.name;
//    }
//
//    public String getOwner(){
//        return this.owner;
//    }
//    public Map<String, Object> toMap(){
//        Map<String, Object> attrs = new HashMap<String,Object>();
//        attrs.put("name", this.name);
//        attrs.put("description", this.description);
//        attrs.put("owner", this.owner);
//        attrs.put("player_count", this.player_count);
//        attrs.put("play_time", this.play_time);
//        return attrs;
//    }
//
//    public void fromMap(Map<String, Object> map) {
//        this.description = (String)map.get("description");
//        this.name = (String)map.get("name");
//        this.owner = (String)map.get("owner");
//        this.player_count = (int)map.get("player_count");
//        this.play_time = (double)map.get("play_time");
//    }
//
//}

package com.example.board2deathapp.models;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentReference;

import java.util.HashMap;
import java.util.Map;

public class Chat extends Model {

    private static String TAG = "CHAT";

    private String chat;
    private String owner;


    public Chat(String cha, String owner, final Activity a) {

        this.chat = cha;
        this.owner = owner;
        //this.play_time = time;

        this.create(new DBResponse(a) {
            @Override
            public <T> void onSuccess(T t) {
                Log.d(TAG, t.toString());
                Chat.this.setID(((DocumentReference) t).getId());
                if (a != null) {
                    Toast.makeText(a.getApplicationContext(), "Successfully wrote a message", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public <T> void onFailure(T t) {
                if (a != null) {
                    Toast.makeText(a.getApplicationContext(), "There was an issue writing your message.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> attrs = new HashMap<String, Object>();

        attrs.put("chat", this.chat);
        attrs.put("owner", this.owner);

        return attrs;
    }

    @Override
    public void fromMap(Map<String, Object> data) {
        this.chat = (String) data.get("chat");
        this.owner = (String) data.get("owner");



    }

    public Chat() {

    }


    public String getOwner() {
        return this.owner;
    }



    public String getChat() {
        return this.chat;
    }


    public void setChat(String d) {
        chat = d;
    }


}




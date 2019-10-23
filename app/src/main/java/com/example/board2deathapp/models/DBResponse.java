package com.example.board2deathapp.models;

import android.app.Activity;
import android.util.Log;

public abstract class DBResponse {
    protected Activity mActiv;

    /**
     * Constructs a DBResponse given an Activity
     * @param activ the activity that should be manipulated in response to an operation performed on the Database
     */
    public DBResponse(Activity activ) {
        this.mActiv = activ;
    }

    /**
     * Handles the case where the DB Request/Operation was successful,
     * have access to the current activity via this.mActiv.
     *
     * @param t the response from a CRUD operation
     * @param <T> The type of response from a CRUD operation
     */
//    public <T> void onSuccess(T t, Model m) {
//        Log.d("UNIMPLEMENTED", "DBResponse onSuccess was called with " + t + ", but was unimplemented with Model");
//    }

    /**
     * Handles the case where the DB Request/Operation was successful,
     * have access to the current activity via this.mActiv.
     *
     * @param t the response from a CRUD operation
     * @param <T> The type of response from a CRUD operation
     */
    public <T> void onSuccess(T t) {
        Log.d("UNIMPLEMENTED", "DBResponse onSuccess was called with " + t + ", but was unimplemented");
    }

    /**
     * Handles the case where the DB Request/Operation was unsuccessful,
     * have access to the current activity via this.mActiv.
     *
     * @param t the response from a CRUD operation
     * @param <T> The type of response from a CRUD operation
     */
//    public <T> void onFailure(T t, Model m) {
//        Log.d("UNIMPLEMENTED", "DBResponse onFailure was called with " + t + ", but was unimplemented with Model");
//    }

    /**
     * Handles the case where the DB Request/Operation was unsuccessful,
     * have access to the current activity via this.mActiv.
     *
     * @param t the response from a CRUD operation
     * @param <T> The type of response from a CRUD operation
     */
    public <T> void onFailure(T t) {
        Log.d("UNIMPLEMENTED", "DBResponse onFailure was called with " + t + ", but was unimplemented");
    }
}

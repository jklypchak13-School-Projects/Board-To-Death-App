package com.example.board2deathapp.models;

import android.util.Log;
import android.view.Display;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ModelCollection<T extends Model> {

    private List<T> data;
    private Class clazz;
    public ModelCollection(Class clazz){
        this.clazz = clazz;
        this.data = new ArrayList<T>();

    }

    public List getItems(){
        return this.data;
    }
    public void read(final Query q, final DBResponse dbResponse){
        q.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    Log.d(this.getClass().getName().toUpperCase(), "Successfully acquired" + q);
                    ModelCollection.this.data.clear();
                    ModelCollection.this.data.addAll(task.getResult().toObjects(clazz));
                    if(dbResponse != null){
                        dbResponse.onSuccess(task.getResult());
                    }

                } else {
                    Log.d(this.getClass().getName().toUpperCase(), "Failed to get" + q);
                    if(dbResponse != null) {
                        dbResponse.onFailure(null);
                    }
                }
            }
        });
    }
}

package com.example.board2deathapp.models;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Map;

public abstract class Model {

    /**
     * Performs the CRUD operation Create
     *
     * @param dbResponse an Abstract class that dictates what to do upon a success or a failure.
     *                  Passes in the appropriate values provided by the addOnSuccessListener/addOnFailureListener
     */
    public void create(final DBResponse dbResponse) {
        final Map<String, Object> map = this.toMap();
        FirebaseFirestore fbStore = FirebaseFirestore.getInstance();
        fbStore.collection(this.getClass().getName())
                .add(map)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference docRef) {
                        Log.d(this.getClass().getName().toUpperCase(), "Successfully Created " + docRef);
                        dbResponse.onSuccess(docRef);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(this.getClass().getName().toUpperCase(), "Failed to create" + map + " because " + e);
                        dbResponse.onFailure(e);
                    }
                });
    }

    /**
     * Performs the CRUD operation Delete
     *
     * @param dbResponse an Abstract class that dictates what to do upon a success or a failure.
     *                  Passes in the appropriate values provided by the addOnSuccessListener/addOnFailureListener
     */
    public void delete(final String document, final DBResponse dbResponse) {
        this.collection()
                .document(document)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(this.getClass().getName().toUpperCase(), "Successfully Destroyed " + document);
                        dbResponse.onSuccess(aVoid);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(this.getClass().getName().toUpperCase(), "Failed to Destroy " + document + " because " + e);
                        dbResponse.onFailure(e);
                    }
                });
    }

    /**
     * Performs the CRUD operation Read
     *
     * @param dbResponse an Abstract class that dictates what to do upon a success or a failure.
     *                  Passes in the appropriate values provided by the addOnSuccessListener/addOnFailureListener
     */
    public void read(final Query q, final DBResponse dbResponse) {
        q.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    Log.d(this.getClass().getName().toUpperCase(), "Successfully acquired" + q);
                    dbResponse.onSuccess(task.getResult());
                } else {
                    Log.d(this.getClass().getName().toUpperCase(), "Failed to get" + q);
                    dbResponse.onFailure(null);
                }
            }
        });
    }

    /**
     * Performs the CRUD operation Update
     *
     * @param dbResponse an Abstract class that dictates what to do upon a success or a failure.
     *                  Passes in the appropriate values provided by the addOnSuccessListener/addOnFailureListener
     */
    public void update(final String document, final DBResponse dbResponse) {
        final Map<String, Object> map = this.toMap();
        FirebaseFirestore fbStore = FirebaseFirestore.getInstance();
        this.collection()
                .document(document)
                .set(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(this.getClass().getName().toUpperCase(), "Updated " + document + " with " + map);
                        dbResponse.onSuccess(aVoid);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(this.getClass().getName().toUpperCase(), "Failed to update " + document + " with " + map + " because " + e);
                        dbResponse.onSuccess(e);
                    }
                });
    }

    /**
     * Creates a Collection Reference for this, which corresponds to the name of the class this.
     *
     * @return CollectionReference that aligns with the name of this
     */
    private CollectionReference collection() {
        return FirebaseFirestore.getInstance().collection(this.getClass().getName());
    }

    /**
     * Converts this to a Map in order to work with Firebase
     *
     * @return Map<String, Object> that can be given to Firebase
     */
    abstract Map<String, Object> toMap();
}

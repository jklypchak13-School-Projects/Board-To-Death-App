package com.example.board2deathapp.models;

import android.app.Activity;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.board2deathapp.LandingActivity;
import com.example.board2deathapp.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class User extends Model {
    private static String TAG = "USER";

    private String mUsername;
    private boolean mIsAdmin;

    public User(String username) {
        this.mUsername = username;
        this.mIsAdmin = false;
    }

    public User() {
        this.mIsAdmin = false;
    }

    public String getUsername() {
        return this.mUsername;
    }

    public void setUsername(String newUsername) {
        this.mUsername = newUsername;
    }

    public boolean isAdmin() {
        return this.mIsAdmin;
    }

    /**
     * Signup with a given Username, Email, and Password
     *
     * @param activity UI to modify
     * @param password for User
     */
    public void signUp(final Activity activity, final String email, final String password) {
        final FirebaseAuth fbAuth = FirebaseAuth.getInstance();
        if (fbAuth.getCurrentUser() != null) {
            Log.e(TAG, "User.signUp was called when a User is already signed in " + fbAuth.getCurrentUser().getEmail());
            return;
        }
        if (this.mUsername.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Log.d(TAG, "User.signUp was called with empty email, username, or password");
            return;
        }
        Log.d(TAG, "Attempting to create a User with email: " + email + " username: " + this.mUsername);
        fbAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    final FirebaseUser fbUser = fbAuth.getCurrentUser();
                    User.this.setID(fbUser.getUid());
                    User.this.create(fbUser.getUid(), new DBResponse(activity) {
                        @Override
                        public <T> void onSuccess(T t) {
                            Log.d(TAG, "Successfully created with in Firebase Auth and Firestore with email " + fbUser.getEmail() + " and Document ID of " + fbUser.getUid());
                            Intent landingActivity = new Intent(activity.getApplicationContext(), LandingActivity.class);
                            landingActivity.putExtra("user", User.this);
                            activity.startActivity(landingActivity);
                        }

                        @Override
                        public <T> void onFailure(T t) {
                            Log.e(TAG, "Succeeded in creating Firebase User and Email with authentication, but failed to create a Firebase Firestore user entry with Document ID " + fbUser.getUid());
                            fbUser.delete();
                            Toast.makeText(activity, "Failed to Signup, try again later", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Log.d(TAG, "Failed to Signup because " + task.getException() + " with email: " + email);
                    Toast.makeText(activity, "Failed to Signup, " + task.getException(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    /**
     * Signin with an Email and Password
     *
     * @param activity UI to modify
     * @param password password to sign in with
     */
    public void signIn(final Activity activity, final String email, final String password) {
        final FirebaseAuth fbAuth = FirebaseAuth.getInstance();
        if (fbAuth.getCurrentUser() != null) {
            Log.e(TAG, "User.signIn was called when a User is already signed in " + fbAuth.getCurrentUser().getEmail());
            return;
        }
        if (email.isEmpty() || password.isEmpty()) {
            Log.d(TAG, "User.signIn was called with empty email or password");
            return;
        }
        fbAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful() && task.getResult() != null) {
                   final FirebaseUser fbUser = task.getResult().getUser();
                   if (fbUser != null) {
                       User.this.setID(fbUser.getUid());
                       User.this.get(new DBResponse(activity) {
                           @Override
                           public <T> void onSuccess(T t) {
                               Log.d(TAG, "Successfully logged in with " + fbUser.getEmail() + " and Document ID of " + fbUser.getUid());
                               Intent landingActivity = new Intent(activity.getApplicationContext(), LandingActivity.class);
                               landingActivity.putExtra("user", User.this);
                               activity.startActivity(landingActivity);
                           }

                           @Override
                           public <T> void onFailure(T t) {
                               Log.e(TAG, "Succeeded in signing in with Firebase Auth, but failed to Query Firebase Firestore for User with Document ID " + fbUser.getUid());
                               Toast.makeText(activity, "Failed to login, try again later", Toast.LENGTH_SHORT).show();
                               fbAuth.signOut();
                           }
                       });
                   } else {
                       Log.d(TAG, "Failed to login with email: " + email);
                       Toast.makeText(activity, "Failed to login invalid username and/or password", Toast.LENGTH_LONG).show();
                   }
                } else {
                    Log.d(TAG, "Failed to login with email: " + email);
                    Toast.makeText(activity, "Failed to login invalid username and/or password", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    /**
     * Get a User from Firestore if they are signed in
     *
     * @param dbResponse Respones to make to the Firestore Async Query
     */
    public void get(final DBResponse dbResponse) {
        final FirebaseAuth fbAuth = FirebaseAuth.getInstance();
        if (fbAuth.getCurrentUser() == null) {
            return;
        }
        final FirebaseUser fbUser = fbAuth.getCurrentUser();
        this.setID(fbUser.getUid());
        Query q = this.collection().whereEqualTo(FieldPath.documentId(), fbUser.getUid());
        this.collection().document(fbUser.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful() && task.getResult() != null) {
                    DocumentSnapshot docSnap = task.getResult();
                    if (docSnap.getData() != null) {
                        User.this.fromMap(docSnap.getData());
                        Log.d(TAG, "Successfully queried User with email " + fbUser.getEmail() + " data: " + docSnap.getData());
                        dbResponse.onSuccess(User.this);
                    } else {
                        Log.e(TAG, "Failed to get User with email " + fbUser.getEmail());
                        dbResponse.onFailure(User.this);
                    }
                } else {
                    Log.e(TAG, "Failed to get User with email " + fbUser.getEmail() + " because: " + task.getException());
                    dbResponse.onFailure(User.this);
                }
            }
        });
    }

    /**
     * Delete CRUD Operation for User
     *
     * @param activity UI to modify
     */
    public void delete(final Activity activity) {
        final FirebaseAuth fbAuth = FirebaseAuth.getInstance();
        if (fbAuth.getCurrentUser() == null) {
            return;
        }
        final FirebaseUser fbUser = fbAuth.getCurrentUser();
        this.setID(fbUser.getUid());
        fbUser.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "Successfully deleted User with email " + fbUser.getEmail() + " from Firebase Auth");
                if (fbAuth.getCurrentUser() == null) {
                    User.this.delete(new DBResponse(activity) {
                        @Override
                        public <T> void onSuccess(T t) {
                            Log.d(TAG, "Successfully deleted User with Document ID " + fbUser.getUid() + " from Firebase Firestore");
                            activity.startActivity(new Intent(this.mActiv.getApplicationContext(), LoginActivity.class));
                        }

                        @Override
                        public <T> void onFailure(T t) {
                            Log.e(TAG, "Failed to delete User with Document ID " + fbUser.getUid());
                            Toast.makeText(activity, "Failed to delete user", Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    Toast.makeText(activity, "Failed to delete user, try again later", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    /**
     * Checks to see if provided username is unique allowing responses via DBResponse
     *
     * @param username   username to check for uniqueness
     * @param dbResponse available responses
     */
    public static void uniqueUsername(final String username, final DBResponse dbResponse) {
        FirebaseFirestore.getInstance()
                .collection("user")
                .whereEqualTo("username", username)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult() != null && task.getResult().isEmpty()) {
                                Log.d(TAG, "Username is unique " + username);
                                dbResponse.onSuccess(null);
                            } else {
                                Log.d(TAG, "Username is not unique " + username);
                                dbResponse.onFailure(null);
                            }
                        } else {
                            Log.d(TAG, "Failed to Query FireStore for Username " + username);
                        }
                    }
                });
    }

    public static boolean isValidPassword(final String password) throws Exception {
        if (password.isEmpty()) {
           throw new Exception("Password is empty");
        }
        if (password.length() < 8) {
            throw new Exception("Password must be at least 8 characters");
        }
        if (!password.matches(".*\\d.*")) {
            throw new Exception("Password must contain at least one digit");
        }
        if (!password.matches(".*[a-z].*")) {
            throw new Exception("Password must contain at least one lowercase letter");
        }
        if (!password.matches(".*[A-Z].*")) {
            throw new Exception("Password must contain at least one uppercase letter");
        }
        return true;
    }

    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("isAdmin", this.mIsAdmin);
        map.put("username", this.mUsername);
        return map;
    }

    @Override
    public void fromMap(@NonNull Map<String, Object> map) {
        this.mIsAdmin = (boolean) map.get("isAdmin");
        this.mUsername = (String) map.get("username");
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(mUsername);
        out.writeString(this.mDocID);
        out.writeInt(mIsAdmin ? 1 : 0);
    }

    public static final Parcelable.Creator<User> CREATOR
            = new Parcelable.Creator<User>() {
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };

    private User(Parcel in) {
        mUsername = in.readString();
        this.mDocID = in.readString();
        mIsAdmin = in.readInt() != 0;
    }
}

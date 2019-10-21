package com.example.board2deathapp.models;

import android.app.Activity;
import android.content.Intent;
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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class User extends Model {
    private static String TAG = "USER";

    private String mUserName;
    private String mEmail;
    private boolean mIsAdmin;

    public User(String email, String username) {
        this.mEmail = email;
        this.mUserName = username;
        this.mIsAdmin = false;
    }

    public User(String email) {
        this.mEmail = email;
        this.mIsAdmin = false;
    }

    public void signUp(final Activity activity, final String password) {
        if (!this.mEmail.isEmpty() && !this.mUserName.isEmpty() && !password.isEmpty()) {
            Query q = this.collection().whereEqualTo("email", this.mEmail);
            this.read(q, new DBResponse(activity) {
                @Override
                public <T> void onSuccess(T t) {
                    if (t != null) {
                        QuerySnapshot snap = (QuerySnapshot) t;
                        if (snap.isEmpty()) {
                            User.this.create(new DBResponse(activity) {
                                @Override
                                public <T> void onSuccess(T t) {
                                    User.this.mDocRef = (DocumentReference) t;
                                    FirebaseAuth.getInstance().
                                            createUserWithEmailAndPassword(User.this.mEmail, password).
                                            addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                                @Override
                                                public void onSuccess(AuthResult authResult) {
                                                    // Pass User instance here
                                                    Intent landing_activity = new Intent(activity.getApplicationContext(), LandingActivity.class);
                                                    activity.startActivity(landing_activity);
                                                }
                                            });
                                }
                            });
                        } else {
                            Toast.makeText(this.mActiv, "Email is already taken", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            });
        }
    }

    /**
     * Login as a User via Email and Password
     *
     * @param activity the current Activity
     * @param password the password for User
     */
    public void signIn(final Activity activity, final String password) {
        FirebaseAuth fbAuth = FirebaseAuth.getInstance();
        Log.d(TAG, "Attempting to Login User with email " + this.mEmail);
        if (!this.mEmail.isEmpty() && !password.isEmpty()) {
            fbAuth.signInWithEmailAndPassword(this.mEmail, password).addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "Successfully Logged in User with email " + User.this.mEmail);
                        Query q = User.this.collection().whereEqualTo("email", User.this.mEmail);
                        User.this.read(q, new DBResponse(activity) {
                            @Override
                            public <T> void onSuccess(T t, Model m) {
                                User user = (User) m;
                                QuerySnapshot snapShot = (QuerySnapshot) t;
                                if (snapShot == null || snapShot.size() != 1) {
                                    Log.e(TAG, "Store in invalid state, there isn't exactly one user associated with the email " + user.mEmail);
                                } else {
                                    user.setID(snapShot.getDocuments().get(0).getReference());
                                    user.fromMap(snapShot.getDocuments().get(0).getData());
                                    // Pass into Main activityity
                                    Intent landing_activityity = new Intent(this.mActiv.getApplicationContext(), LandingActivity.class);
                                    this.mActiv.startActivity(landing_activityity);
                                    // Just for testing
                                    user.delete(this.mActiv);
                                }
                            }
                        });
                    } else {
                        Toast.makeText(activity, "Invalid Username and/or Password", Toast.LENGTH_LONG).show();
                        Log.d(TAG, "Failed to login in User with email " + User.this.mEmail);
                    }
                }
            });
        }
    }

    public void delete(Activity activity) {
        this.delete(new DBResponse(activity) {
            @Override
            public <T> void onSuccess(T t, Model m) {
                Log.d(TAG, "Successfully deleted User from Store" + User.this.mEmail);
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    FirebaseAuth.getInstance().getCurrentUser().delete();
                    Log.d(TAG, "Successfully deleted User from FirebaseAuth" + User.this.mEmail);
                }
                Intent signInActivity = new Intent(this.mActiv.getApplicationContext(), LoginActivity.class);
                this.mActiv.startActivity(signInActivity);
            }

            @Override
            public <T> void onFailure(T t, Model m) {
                Toast.makeText(this.mActiv, "Failed to Delete user, try again later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void update(Activity activity, final String password) {
        this.update(new DBResponse(activity) {
            @Override
            public <T> void onSuccess(T t, Model m) {
                User user = (User)m;
                Log.d(TAG, "Successfully updated User" + user.mEmail);
                FirebaseUser fbUser = FirebaseAuth.getInstance().getCurrentUser();
                if (fbUser != null) {
                    fbUser.updateEmail(user.mEmail);
                    fbUser.updatePassword(password);
                    Log.d(TAG, "Updated User in FirebaseAuth " + user.mEmail);
                } else {
                    // Redirect to Login page
                    Intent signInActivity = new Intent(this.mActiv.getApplicationContext(), LoginActivity.class);
                    this.mActiv.startActivity(signInActivity);
                }
            }
        });
    }

    /**
     * Checks to see if provided email is unique allowing responses via DBResponse
     * @param email email to check for uniqueness
     * @param dbResponse available responses
     */
    public static void uniqueEmail(final String email, final DBResponse dbResponse) {
        FirebaseFirestore.getInstance()
                .collection("user")
                .whereEqualTo("email", email)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult() != null && task.getResult().isEmpty()) {
                                Log.d(TAG, "Email is unique " + email);
                                dbResponse.onSuccess(null);
                            } else {
                                Log.d(TAG, "Email is not unique " + email);
                                dbResponse.onFailure(null);
                            }
                        } else {
                            Log.d(TAG, "Failed to Query FireStore for Email " + email);
                        }
                    }
                });
    }

    /**
     * Checks to see if provided username is unique allowing responses via DBResponse
     * @param username username to check for uniqueness
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

    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("username", this.mUserName);
        map.put("email", this.mEmail);
        map.put("isAdmin", this.mIsAdmin);
        return map;
    }

    @Override
    public void fromMap(@NonNull Map<String, Object> map) {
        this.mIsAdmin = (boolean)map.get("isAdmin");
        this.mUserName = (String)map.get("username");
        this.mEmail = (String)map.get("email");
    }
}

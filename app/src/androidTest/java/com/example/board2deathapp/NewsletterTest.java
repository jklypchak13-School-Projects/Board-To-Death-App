package com.example.board2deathapp;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.board2deathapp.models.DBResponse;
import com.example.board2deathapp.models.ModelCollection;
import com.example.board2deathapp.models.Newsletter;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class NewsletterTest {
    private boolean done;
    private ModelCollection<Newsletter> before;
    private ModelCollection<Newsletter> after;

    public NewsletterTest() {
        this.done = false;
    }

    @Test
    public void addNewsletterPost() {
        done = false;
        before = new ModelCollection<>(Newsletter.class);
        before.read(FirebaseFirestore.getInstance().collection("newsletter").orderBy("title"), new DBResponse(null) {
            @Override
            public <T> void onSuccess(T t) {
                Newsletter event = new Newsletter("title", "content", "username");
                event.create(new DBResponse(null) {
                    @Override
                    public <T> void onSuccess(T t) {
                        after = new ModelCollection<>(Newsletter.class);
                        after.read(FirebaseFirestore.getInstance().collection("newsletter").orderBy("title"), new DBResponse(null) {
                            @Override
                            public <T> void onSuccess(T t) {
                                assertEquals(before.getItems().size() + 1, after.getItems().size());
                                done = true;
                            }

                            @Override
                            public <T> void onFailure(T t) {
                                done = true;
                            }
                        });
                    }

                    @Override
                    public <T> void onFailure(T t) {
                        done = true;
                    }
                });
            }

            @Override
            public <T> void onFailure(T t) {
                done = true;
            }
        });
        while (!done) {
        }
        done = false;
    }

    @Test
    public void readSingleNewsletter() {
        done = false;
        Newsletter temp = new Newsletter();
        Query q = FirebaseFirestore.getInstance().collection("newsletter").whereEqualTo("title", "test post");
        temp.read(q, new DBResponse(null) {
            @Override
            public <T> void onSuccess(T t) {
                System.out.println("HERE");
                done = true;
            }

            @Override
            public <T> void onFailure(T t) {
                done = true;
            }
        });
        while (!done) {
        }
        done = false;
        System.out.println(temp.getDescription());
        assertEquals(temp.getDescription(), "I'm a description!");
        assertEquals(temp.getUsername(), "testAccount");
        assertEquals(temp.getTitle(), "test post");
    }

    @Test
    public void deleteNewsletterPost() {
        done = false;
        Newsletter temp = new Newsletter("delete me", "hello!", "testAccount");
        temp.create(new DBResponse(null) {
            @Override
            public <T> void onSuccess(T t) {
                done = true;
            }
            @Override
            public <T> void onFailure(T t) {
                done = true;
            }
        });
        while(!done){}
        done = false;
        temp.delete(new DBResponse(null) {
            @Override
            public <T> void onSuccess(T t) {
                done=true;
                System.out.println("Here");
            }

            @Override
            public <T> void onFailure(T t) {
                done=true;
            }
        });
        while(!done){}
        done=false;
        temp = new Newsletter();
        Query q = FirebaseFirestore.getInstance().collection("newsletter").whereEqualTo("title", "delete me");
        temp.read(q, new DBResponse(null) {
            @Override
            public <T> void onSuccess(T t) {
                done=true;
                assertTrue(false);
            }

            @Override
            public <T> void onFailure(T t) {
                done = true;
                assertTrue(true);
            }
        });
        while (!done) {
        }
        done = false;
    }

}

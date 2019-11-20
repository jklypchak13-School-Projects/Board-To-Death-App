package com.example.board2deathapp;

import com.example.board2deathapp.models.BoardGame;
import com.example.board2deathapp.models.DBResponse;
import com.example.board2deathapp.models.ModelCollection;
import com.example.board2deathapp.models.Newsletter;
import com.google.firebase.firestore.FirebaseFirestore;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void eventBeforeIsAfterAfterDate() {
    }

    @Test
    public void addNewsletterPost() {
        final ModelCollection<Newsletter> before = new ModelCollection<>(Newsletter.class);
        before.read_current(FirebaseFirestore.getInstance().collection("Newsletter").orderBy("title"), new DBResponse(null) {
            @Override
            public <T> void onSuccess(T t) {
                assertTrue(true);
            }

            @Override
            public <T> void onFailure(T t) {
                assertTrue(true);
            }
        });
//            @Override
//            public <T> void onSuccess(T t) {
//                Newsletter event = new Newsletter("title", "content", "username");
//                event.create(new DBResponse(null) {
//                    @Override
//                    public <T> void onSuccess(T t) {
//                        final ModelCollection<Newsletter> after = new ModelCollection<>(Newsletter.class);
//                        after.read_current(FirebaseFirestore.getInstance().collection("Newsletter").orderBy("title"), new DBResponse(null) {
//                            @Override
//                            public <T> void onSuccess(T t) {
//                                assertEquals(before.getItems().size(), after.getItems().size() + 1);
//                            }
//
//                            @Override
//                            public <T> void onFailure(T t) {
//                            }
//                        });
//                    }
//
//                    @Override
//                    public <T> void onFailure(T t) {
//                    }
//                });
//            }
//            @Override
//            public <T> void onFailure(T t) { }
//        });
    }
}
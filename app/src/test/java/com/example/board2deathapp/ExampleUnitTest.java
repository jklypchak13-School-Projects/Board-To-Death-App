package com.example.board2deathapp;

import com.example.board2deathapp.models.Event;
import com.example.board2deathapp.models.User;

import org.junit.Test;

import java.util.Calendar;

import static org.junit.Assert.*;

public class ExampleUnitTest {
    @Test
    public void eventRsvpStringEmpty() {
        Calendar calendar = Calendar.getInstance();
        final Event event = new Event("title", "description", calendar.getTime(), calendar.getTime());
        assertEquals(event.rsvpToString(), "");
        assertFalse(event.isRSVP("username"));
    }

    @Test
    public void eventGetDescriptionSentence() {
        Calendar calendar = Calendar.getInstance();
        final Event event = new Event("title", "description. Not", calendar.getTime(), calendar.getTime());
        assertEquals(event.getDescOneSentence(), "description.");
    }

    @Test
    public void eventGetDescriptionSentenceMany() {
        Calendar calendar = Calendar.getInstance();
        final Event event = new Event("title", "description, this is a long sentence and ends Here. Not here", calendar.getTime(), calendar.getTime());
        assertEquals(event.getDescOneSentence(), "description, this is a long sentence and ends Here.");
    }

    @Test
    public void userPasswordValid() {
        final String password = "ThisIsaValidPassword1";
        try {
            assertTrue(User.isValidPassword(password));
        } catch (Exception e) {}
    }

    @Test
    public void userPasswordInvalidMissingDigit() {
        final String password = "ThisIsaValidPassword";
        try {
            assertTrue(User.isValidPassword(password));
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Password must contain at least one digit");
        }
    }

    @Test
    public void userPasswordInvalidMissingLowercase() {
        final String password = "THISISNOTAVALIDPASSWORD1";
        try {
            assertTrue(User.isValidPassword(password));
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Password must contain at least one lowercase letter");
        }
    }

    @Test
    public void userPasswordEmpty() {
        final String password = "";
        try {
            assertTrue(User.isValidPassword(password));
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Password is empty");
        }
    }

    @Test
    public void userPasswordOneCharacter() {
        final String password = "1";
        try {
            assertTrue(User.isValidPassword(password));
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Password must be at least 8 characters");
        }
    }

    @Test
    public void userPasswordInvalidMissingUppercase() {
        final String password = "thisisnotavalidpassword1";
        try {
            assertTrue(User.isValidPassword(password));
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Password must contain at least one uppercase letter");
        }
    }
}
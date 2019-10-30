package com.example.board2deathapp.ui.calendar;

import android.icu.text.DateFormat;
import android.util.Log;

import androidx.lifecycle.ViewModel;


import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddEventViewModel extends ViewModel {
    private Calendar startCalendar;
    private Calendar endCalendar;

    private static Locale locale = Locale.getDefault();

    private static String getDateText(Calendar calendar) {
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.DEFAULT, locale);
        return dateFormat.format(calendar.getTime());
    }

    private static String getTimeText(Calendar calendar) {
        DateFormat timeFormat = DateFormat.getTimeInstance(DateFormat.SHORT, locale);
        return timeFormat.format(calendar.getTime());
    }

    public AddEventViewModel() {
        startCalendar = Calendar.getInstance();
        endCalendar = Calendar.getInstance();
    }

    public Date getStartDate() {
        return startCalendar.getTime();
    }

    public Date getEndDate() {
        return endCalendar.getTime();
    }

    public String getStartDateText() {
        return getDateText(startCalendar);
    }

    public String getStartTimeText() {
        return getTimeText(startCalendar);
    }

    public String getEndDateText() {
        return getDateText(endCalendar);
    }

    public String getEndTimeText() {
        return getTimeText(endCalendar);
    }

    public void setStartDate(int year, int month, int day) {
        startCalendar.set(year, month, day);
    }

    public void setEndDate(int year, int month, int day) {
        endCalendar.set(year, month, day);
    }

    public void setStartTime(int hours, int minutes) {
        startCalendar.set(Calendar.HOUR_OF_DAY, hours);
        startCalendar.set(Calendar.MINUTE, minutes);
    }

    public void setEndTime(int hours, int minutes) {
        endCalendar.set(Calendar.HOUR_OF_DAY, hours);
        endCalendar.set(Calendar.MINUTE, minutes);
    }
}

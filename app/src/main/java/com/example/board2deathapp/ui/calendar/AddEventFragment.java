package com.example.board2deathapp.ui.calendar;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.board2deathapp.R;
import com.example.board2deathapp.models.DBResponse;
import com.example.board2deathapp.models.Event;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;
import java.util.Date;


public class AddEventFragment extends Fragment implements View.OnClickListener {

    private AddEventViewModel mAddEventViewModel;

    private EditText mTitleEditText;
    private EditText mDescriptionEditText;

    private enum TIME {START, END}

    ;

    public AddEventFragment() {
    }

    /**
     * Construacts a DatePickerDialog for a Button whether ir be teh start or end time button
     *
     * @param button the button to attach the DatePickerDialog to
     * @param time   Start or End TIME
     */
    private void constructDateSelectorButton(final Button button, final TIME time) {
        String defaultText = time == TIME.START ? mAddEventViewModel.getStartDateText() : mAddEventViewModel.getEndDateText();
        button.setText(defaultText);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getActivity() == null) {
                    return;
                }
                DatePickerDialog dialogFragment = new DatePickerDialog(getActivity());
                dialogFragment.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        if (time == TIME.START) {
                            mAddEventViewModel.setStartDate(year, month, day);
                            button.setText(mAddEventViewModel.getStartDateText());
                        } else {
                            mAddEventViewModel.setEndDate(year, month, day);
                            button.setText(mAddEventViewModel.getEndDateText());
                        }
                    }
                });
                dialogFragment.show();
            }
        });
    }

    /**
     * Constructs a TimePickerDialog for a Button whether it be the start or end time button
     *
     * @param button the button to attach the TimePickerDialog to
     * @param time   Start time or End time
     */
    private void constructTimeSelectorButton(final Button button, final TIME time) {
        String defaultText = time == TIME.START ? mAddEventViewModel.getStartTimeText() : mAddEventViewModel.getEndTimeText();
        button.setText(defaultText);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hours, int minutes) {
                        if (time == TIME.START) {
                            mAddEventViewModel.setStartTime(hours, minutes);
                            button.setText(mAddEventViewModel.getStartTimeText());
                        } else {
                            mAddEventViewModel.setEndTime(hours, minutes);
                            button.setText(mAddEventViewModel.getEndTimeText());
                        }
                    }
                };
                int hour = Calendar.getInstance().get(Calendar.HOUR);
                int minute = Calendar.getInstance().get(Calendar.MINUTE);
                TimePickerDialog timeDialog = new TimePickerDialog(getActivity(), onTimeSetListener, hour, minute, false);
                timeDialog.show();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_event, container, false);
        mAddEventViewModel = new AddEventViewModel();
        final Button startDateButton = view.findViewById(R.id.startDateButton);
        constructDateSelectorButton(startDateButton, TIME.START);
        final Button endDateButton = view.findViewById(R.id.endDateButton);
        constructDateSelectorButton(endDateButton, TIME.END);
        final Button startTimeButton = view.findViewById(R.id.startTimeButton);
        constructTimeSelectorButton(startTimeButton, TIME.START);
        final Button endTimeButton = view.findViewById(R.id.endTimeButton);
        constructTimeSelectorButton(endTimeButton, TIME.END);
        mDescriptionEditText = view.findViewById(R.id.descriptionEditText);
        mTitleEditText = view.findViewById(R.id.titleEditText);
        final FloatingActionButton saveButton = view.findViewById(R.id.saveEventButton);
        saveButton.setOnClickListener(this);
        return view;
    }

    public void onClick(View view) {
        final String title = mTitleEditText.getText().toString();
        final String description = mDescriptionEditText.getText().toString();
        if (title.isEmpty()) {
            Toast.makeText(getActivity(), "Must have a title", Toast.LENGTH_SHORT).show();
            return;
        }
        final Date startDate = mAddEventViewModel.getStartDate();
        final Date endDate = mAddEventViewModel.getEndDate();
        if (!startDate.before(endDate)) {
            Toast.makeText(getActivity(), "Start Date must be before End date", Toast.LENGTH_SHORT).show();
            return;
        }
        final Event event = new Event(title, description, startDate, endDate);
        event.create(new DBResponse(getActivity()) {
            @Override
            public <T> void onSuccess(T t) {
                Toast.makeText(getActivity(), "Successfully made event", Toast.LENGTH_SHORT).show();
            }

            @Override
            public <T> void onFailure(T t) {
                Toast.makeText(getActivity(), "Failed to create Event, try again later", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

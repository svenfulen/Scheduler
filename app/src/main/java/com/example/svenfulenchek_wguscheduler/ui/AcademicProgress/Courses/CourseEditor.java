package com.example.svenfulenchek_wguscheduler.ui.AcademicProgress.Courses;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.svenfulenchek_wguscheduler.R;
import com.example.svenfulenchek_wguscheduler.ui.AcademicProgress.DatePickerFragment;
import com.example.svenfulenchek_wguscheduler.ui.utils;

/*
This class can be used to add or edit courses.
To add an existing course, use Intent.putExtra("EDIT", true); and pass existing course data using extras:
COURSE_ID - int
COURSE_TITLE - string
COURSE_START - string
COURSE_END - string
COURSE_STATUS - string
 */
public class CourseEditor extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    // True if setting start date, false if setting end date.
    boolean startDate;

    // True if editing an existing term
    boolean editMode = false;
    int courseID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_editor);

        // UI elements
        EditText courseTitleField = (EditText)findViewById(R.id.courseTitleField);
        Button courseStartField = (Button)findViewById(R.id.courseStartField);
        Button courseEndField = (Button)findViewById(R.id.courseEndField);
        Spinner courseStatusSpinner = (Spinner)findViewById(R.id.courseStatusSpinner);
        Button submitButton = (Button)findViewById(R.id.courseSubmitButton);

        // Populate the spinner
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.course_status, R.layout.support_simple_spinner_dropdown_item);
        spinnerAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        courseStatusSpinner.setAdapter(spinnerAdapter);

        // Determine if the course is being edited or a new course is being created
        Intent existingCourseData = getIntent();
        editMode = existingCourseData.getBooleanExtra("EDIT", false);

        // Populate form with existing data
        if (editMode) {
            courseID = existingCourseData.getIntExtra("COURSE_ID", 0);
            String courseTitle = existingCourseData.getStringExtra("COURSE_TITLE");
            String courseStart = existingCourseData.getStringExtra("COURSE_START");
            String courseEnd = existingCourseData.getStringExtra("COURSE_END");
            String courseStatus = existingCourseData.getStringExtra("COURSE_STATUS");
            courseTitleField.setText(courseTitle);
            courseStartField.setText(courseStart);
            courseEndField.setText(courseEnd);

            // Set the spinner value
            courseStatusSpinner.setSelection(utils.getSpinnerItemIndex(courseStatusSpinner, courseStatus));
        }

        // Open a date picker when the start/end date fields are clicked
        courseStartField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "startDate");
                startDate = true;
            }
        });
        courseEndField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "endDate");
                startDate = false;
            }
        });
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        // Create a string from the chosen date in format (Mm/Dd/YYYY)
        String dateString = (month + 1) + "/" + day + "/" + year;

        Button courseStartField = (Button)findViewById(R.id.courseStartField);
        Button courseEndField = (Button)findViewById(R.id.courseEndField);

        // Set the text on the corresponding button
        if (startDate){
            courseStartField.setText(dateString);
        }
        else{
            courseEndField.setText(dateString);
        }
    }

    public void onUserFinished(View view){
        // Retrieve form data
        EditText courseTitleField = (EditText)findViewById(R.id.courseTitleField);
        String course_title = courseTitleField.getText().toString();

        Button courseStartField = (Button)findViewById(R.id.courseStartField);
        String course_start = courseStartField.getText().toString();

        Button courseEndField = (Button)findViewById(R.id.courseEndField);
        String course_end = courseEndField.getText().toString();

        Spinner courseStatusSpinner = (Spinner)findViewById(R.id.courseStatusSpinner);
        String course_status = courseStatusSpinner.getSelectedItem().toString();

        // Make sure the user has entered in all details
        if (!course_title.isEmpty() && !course_end.equals("End Date") && !course_start.equals("Start Date")) {
            // Return new course data
            try {
                Intent extras = new Intent();
                extras.putExtra("COURSE_TITLE", course_title);
                extras.putExtra("COURSE_START", course_start);
                extras.putExtra("COURSE_END", course_end);
                extras.putExtra("COURSE_STATUS", course_status);
                if(editMode){ extras.putExtra("COURSE_ID", courseID); }
                setResult(RESULT_OK, extras);
                finish();
            } catch (Exception e) {
                e.printStackTrace();
                setResult(RESULT_CANCELED);
                finish();
            }
        }
        else {
            Toast.makeText(getApplicationContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
        }
    }







}
package com.example.svenfulenchek_wguscheduler.ui.UI.Instructors;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.svenfulenchek_wguscheduler.R;
import com.example.svenfulenchek_wguscheduler.ui.Database.Repository;
import com.example.svenfulenchek_wguscheduler.ui.UI.MainActivity;

/*
REQUIRED EXTRAS:
INSTRUCTOR_NAME
INSTRUCTOR_EMAIL
INSTRUCTOR_PHONE
INSTRUCTOR_ID
COURSE_ID
 */
public class InstructorEditor extends AppCompatActivity {

    Repository db;

    boolean editMode = false;
    int INSTRUCTOR_ID;
    int COURSE_ID;

    TextView instructorNameField;
    TextView instructorPhoneField;
    TextView instructorEmailField;
    ImageButton deleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_editor);

        db = MainActivity.db;

        // UI Elements
        instructorNameField = (TextView) findViewById(R.id.instructorNameField);
        instructorPhoneField = (TextView) findViewById(R.id.instructorPhoneField);
        instructorEmailField = (TextView) findViewById(R.id.instructorEmailField);
        deleteButton = (ImageButton) findViewById(R.id.deleteInstructorButton);

        // Determine if an instructor is being edited or a new one is being created
        Intent existingInstructorData = getIntent();
        COURSE_ID = existingInstructorData.getIntExtra("COURSE_ID", -1);
        editMode = existingInstructorData.getBooleanExtra("EDIT", false);

        // Populate UI with existing data if edit mode
        if (editMode){
            instructorNameField.setText(existingInstructorData.getStringExtra("INSTRUCTOR_NAME"));
            instructorPhoneField.setText(existingInstructorData.getStringExtra("INSTRUCTOR_PHONE"));
            instructorEmailField.setText(existingInstructorData.getStringExtra("INSTRUCTOR_EMAIL"));
            INSTRUCTOR_ID = existingInstructorData.getIntExtra("INSTRUCTOR_ID", -1);
        }

    }

    public void deleteInstructor(View view) {
        db.deleteInstructorById(INSTRUCTOR_ID);
        setResult(RESULT_OK);
        finish();
    }

    public void onUserFinished(View view) {
        // Retrieve form data
        String INSTRUCTOR_NAME = instructorNameField.getText().toString();
        String INSTRUCTOR_PHONE = instructorNameField.getText().toString();
        String INSTRUCTOR_EMAIL = instructorNameField.getText().toString();

        // Make sure user has entered in data for each field
        if (!INSTRUCTOR_NAME.isEmpty() && !INSTRUCTOR_EMAIL.isEmpty() && !INSTRUCTOR_PHONE.isEmpty()) {
            // Return course data in Intent
            Intent extras = new Intent();
            extras.putExtra("INSTRUCTOR_NAME", INSTRUCTOR_NAME);
            extras.putExtra("INSTRUCTOR_PHONE", INSTRUCTOR_PHONE);
            extras.putExtra("INSTRUCTOR_EMAIL", INSTRUCTOR_EMAIL);
            if(editMode){ extras.putExtra("INSTRUCTOR_ID", INSTRUCTOR_ID); }
            setResult(RESULT_OK, extras);
            finish();
        }
        else {
            Toast.makeText(getApplicationContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
        }
    }
}
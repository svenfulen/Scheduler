package com.example.svenfulenchek_wguscheduler.ui.UI.Assessments;

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
import com.example.svenfulenchek_wguscheduler.ui.UI.DatePickerFragment;

/*
This class can be used to add or edit assessments.
To add an existing assessment, use Intent.putExtra("EDIT", true); and pass existing assessment data using extras:
ASSESSMENT_ID - int
COURSE_ID - int
ASSESSMENT_TITLE - string
ASSESSMENT_START - string
ASSESSMENT_END - string
ASSESSMENT_TYPE - string
 */
public class AssessmentEditor extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    // True if setting start date, false if setting end date.
    boolean startDate;

    // True if editing an existing assessment
    boolean editMode = false;
    int courseID;
    int assessmentID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_editor);
        // UI elements
        EditText assessmentTitleField = (EditText)findViewById(R.id.assessmentTitleField);
        Button assessmentStartField = (Button)findViewById(R.id.assessmentStartField);
        Button assessmentEndField = (Button)findViewById(R.id.assessmentEndField);
        Spinner assessmentTypeSpinner = (Spinner)findViewById(R.id.assessmentTypeSpinner);
        Button submitButton = (Button)findViewById(R.id.assessmentSubmitButton);

        // Populate the spinner
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.assessment_type, R.layout.support_simple_spinner_dropdown_item);
        spinnerAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        assessmentTypeSpinner.setAdapter(spinnerAdapter);

        // Determine if the assessment is being edited or if a new one is being added
        Intent existingAssessmentData = getIntent();
        editMode = existingAssessmentData.getBooleanExtra("EDIT", false);

        // Populate form with existing data if in edit mode
        if (editMode){
            courseID = existingAssessmentData.getIntExtra("COURSE_ID", -1);
            assessmentID = existingAssessmentData.getIntExtra("ASSESSMENT_ID", -1);
            String assessmentTitle = existingAssessmentData.getStringExtra("ASSESSMENT_TITLE");
            String assessmentStart = existingAssessmentData.getStringExtra("ASSESSMENT_START");
            String assessmentEnd = existingAssessmentData.getStringExtra("ASSESSMENT_END");

            assessmentTitleField.setText(assessmentTitle);
            assessmentStartField.setText(assessmentStart);
            assessmentEndField.setText(assessmentEnd);
        }

        // Open a date picker when the start/end date fields are clicked
        assessmentStartField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "startDate");
                startDate = true;
            }
        });
        assessmentEndField.setOnClickListener(new View.OnClickListener() {
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

        Button assessmentStartField = (Button)findViewById(R.id.assessmentStartField);
        Button assessmentEndField = (Button)findViewById(R.id.assessmentEndField);

        // Set the text on the corresponding button
        if (startDate){
            assessmentStartField.setText(dateString);
        }
        else{
            assessmentEndField.setText(dateString);
        }
    }

    public void onUserFinished(View view){
        // Retrieve form data
        EditText assessmentTitleField = (EditText)findViewById(R.id.assessmentTitleField);
        String assessment_title = assessmentTitleField.getText().toString();

        Button assessmentStartField = (Button)findViewById(R.id.assessmentStartField);
        String assessment_start = assessmentStartField.getText().toString();

        Button assessmentEndField = (Button)findViewById(R.id.assessmentEndField);
        String assessment_end = assessmentEndField.getText().toString();

        Spinner assessmentTypeSpinner = (Spinner)findViewById(R.id.assessmentTypeSpinner);
        String assessment_type = assessmentTypeSpinner.getSelectedItem().toString();


        // Make sure the user has entered in all details
        if (!assessment_title.isEmpty() && !assessment_end.equals("End Date") && !assessment_start.equals("Start Date")) {
            // Return new assessment data

            Intent extras = new Intent();
            extras.putExtra("ASSESSMENT_TITLE", assessment_title);
            extras.putExtra("ASSESSMENT_START", assessment_start);
            extras.putExtra("ASSESSMENT_END", assessment_end);
            extras.putExtra("ASSESSMENT_TYPE", assessment_type);
            if(editMode){ extras.putExtra("COURSE_ID", courseID); extras.putExtra("ASSESSMENT_ID", assessmentID); }
            setResult(RESULT_OK, extras);
            finish();

        }
        else {
            Toast.makeText(getApplicationContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
        }
    }



}
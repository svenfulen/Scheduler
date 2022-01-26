package com.example.svenfulenchek_wguscheduler.ui.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.svenfulenchek_wguscheduler.R;

/*
This class can be used to add or edit terms.
To edit an existing term, use Intent.putExtra("EDIT", true); and pass existing term data:
TERM_ID
TERM_TITLE
TERM_START
TERM_END
When the activity is finished and editMode is set to true, it will return extras including Term ID
 */
public class TermEditor extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    // True if setting start date, false if setting end date.
    boolean startDate;

    // True if editing an existing term
    boolean editMode = false;
    int termID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_editor);

        // UI elements
        EditText termTitleField = (EditText)findViewById(R.id.termTitleField);
        Button startDateField = (Button)findViewById(R.id.startDateField);
        Button endDateField = (Button)findViewById(R.id.endDateField);

        // Determine if the term is being edited or a new term is being created
        Intent existingTermData = getIntent();
        editMode = existingTermData.getBooleanExtra("EDIT", false);

        // Populate form with existing data
        if (editMode) {
            termID = existingTermData.getIntExtra("TERM_ID", 0);
            String termTitle = existingTermData.getStringExtra("TERM_TITLE");
            String termStart = existingTermData.getStringExtra("TERM_START");
            String termEnd = existingTermData.getStringExtra("TERM_END");

            if (!termTitle.isEmpty()) { termTitleField.setText(termTitle); }
            if (!termStart.isEmpty()) { startDateField.setText(termStart); }
            if (!termEnd.isEmpty()) { endDateField.setText(termEnd); }

        }

        // Open a date picker when the start/end date fields are clicked
        startDateField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "startDate");
                startDate = true;
            }
        });
        endDateField.setOnClickListener(new View.OnClickListener() {
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

        Button startDateField = (Button)findViewById(R.id.startDateField);
        Button endDateField = (Button)findViewById(R.id.endDateField);

        // Set the text on the corresponding button
        if (startDate){
            startDateField.setText(dateString);
        }
        else{
            endDateField.setText(dateString);
        }

    }

    public void onUserFinished(View view){
        // Retrieve form data
        EditText termTitleField = (EditText)findViewById(R.id.termTitleField);
        String term_title = termTitleField.getText().toString();

        Button startDateField = (Button)findViewById(R.id.startDateField);
        String term_start = startDateField.getText().toString();

        Button endDateField = (Button)findViewById(R.id.endDateField);
        String term_end = endDateField.getText().toString();

        // Make sure the user has entered in a term title, start date, and end date
        if (!term_end.equals("End Date") && !term_start.equals("Start Date") && !term_title.isEmpty()) {
            // Return new term data
            try {
                Intent extras = new Intent();
                extras.putExtra("TERM_TITLE", term_title);
                extras.putExtra("TERM_START", term_start);
                extras.putExtra("TERM_END", term_end);
                if(editMode) { extras.putExtra("TERM_ID", termID); }
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
package com.example.svenfulenchek_wguscheduler.ui.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.Application;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.svenfulenchek_wguscheduler.R;
import com.example.svenfulenchek_wguscheduler.ui.Database.Repository;
import com.example.svenfulenchek_wguscheduler.ui.Entity.Term;

import java.util.Calendar;
import java.util.Date;

public class TermEditor extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    // True if setting start date, false if setting end date.
    boolean startDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_editor);

        // Get data if editing an existing term
        Intent existingTermData = getIntent();
        String termTitle = existingTermData.getStringExtra("TERM_TITLE");
        String termStart = existingTermData.getStringExtra("TERM_START");
        String termEnd = existingTermData.getStringExtra("TERM_END");

        // Open a date picker for either the start or end date button
        Button startDateField = (Button)findViewById(R.id.startDateField);
        startDateField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "startDate");
                startDate = true;
            }
        });
        Button endDateField = (Button)findViewById(R.id.endDateField);
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
            // Add new term to database
            try {
                Intent extras = new Intent();
                extras.putExtra("TERM_TITLE", term_title);
                extras.putExtra("TERM_START", term_start);
                extras.putExtra("TERM_END", term_end);
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
package com.example.svenfulenchek_wguscheduler.ui.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.svenfulenchek_wguscheduler.R;
import com.example.svenfulenchek_wguscheduler.ui.Database.Repository;
import com.example.svenfulenchek_wguscheduler.ui.Entity.Term;

public class TermEditor extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_editor);

        // Get data if editing an existing term
        Intent existingTermData = getIntent();
        String termTitle = existingTermData.getStringExtra("TERM_TITLE");
        String termStart = existingTermData.getStringExtra("TERM_START");
        String termEnd = existingTermData.getStringExtra("TERM_END");

        //TODO: Populate form based on existingTermData
    }

    // This function actually updates the database, then sends the data to the View that was
    // sent to the database.  The view does not query the database on every term add, it only
    // gets all terms from the database when onCreate is called.
    // TODO: is there a better way to do this?
    public void onUserFinished(View view){
        //TODO: Input validation

        // Retrieve form data
        EditText termTitleField = (EditText)findViewById(R.id.termTitleField);
        String term_title = termTitleField.getText().toString();

        EditText startDateField = (EditText)findViewById(R.id.startDateField);
        String term_start = startDateField.getText().toString();

        EditText endDateField = (EditText)findViewById(R.id.endDateField);
        String term_end = endDateField.getText().toString();

        // Add new term to database
        try {
            Intent extras = new Intent();
            extras.putExtra("TERM_TITLE", term_title);
            extras.putExtra("TERM_START", term_start);
            extras.putExtra("TERM_END", term_end);

            Repository db = new Repository(getApplication());
            Term newTerm = new Term(term_title, term_start, term_end);
            db.insertTerm(newTerm);
            setResult(RESULT_OK, extras);
            finish();
        }
        catch (Exception e) {
            e.printStackTrace();
            setResult(RESULT_CANCELED);
            finish();
        }
    }
}
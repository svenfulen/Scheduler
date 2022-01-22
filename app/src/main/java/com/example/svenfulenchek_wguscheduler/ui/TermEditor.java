package com.example.svenfulenchek_wguscheduler.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.svenfulenchek_wguscheduler.R;

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

    public void onUserFinished(){
        //TODO: Input validation

        //TODO: Get user input
        String term_title = "test";
        String term_start = "date";
        String term_end = "date";

        // Finish the activity and pass Intent with term data
        Intent termData = new Intent();
        termData.putExtra("TERM_TITLE", term_title);
        termData.putExtra("TERM_START", term_start);
        termData.putExtra("TERM_END", term_end);
        setResult(RESULT_OK, termData);
        finish();
    }
}
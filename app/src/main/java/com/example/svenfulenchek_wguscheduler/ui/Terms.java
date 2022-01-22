package com.example.svenfulenchek_wguscheduler.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.svenfulenchek_wguscheduler.R;
import com.example.svenfulenchek_wguscheduler.ui.Models.Term;

import java.util.ArrayList;

public class Terms extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms);

        //TODO: Populate the list with existing terms
        // Lookup the recyclerview in activity layout
        RecyclerView rvTerms = (RecyclerView) findViewById(R.id.rvTerms);

        // TODO: Remove this (once sqlite is setup)
        // List of terms (temporary for building UI)
        ArrayList<Term> ALL_TERMS = new ArrayList<Term>();

        // TODO: Remove this (once sqlite is setup)
        // Add terms
        ALL_TERMS.add(new Term("Term 1", "04/10/2022", "06/10/2022"));
        ALL_TERMS.add(new Term("Term 2", "04/10/2022", "06/10/2022"));
        ALL_TERMS.add(new Term("Term 3", "04/10/2022", "06/10/2022"));
        ALL_TERMS.add(new Term("Term 4", "04/10/2022", "06/10/2022"));

        // Create adapter passing in the sample user data
        TermsAdapter adapter = new TermsAdapter(ALL_TERMS);

        // Attach the adapter to the recyclerview to populate items
        rvTerms.setAdapter(adapter);

        // Set layout manager to position the items
        rvTerms.setLayoutManager(new LinearLayoutManager(this));
        // That's all!

    }

    //TODO: write function to create term fragment in the ui using passed data
    public void createTermFragment(String term_title, String term_start, String term_end){
    }

    // Open the Term Editor activity
    public void addTerm(View view){
        Intent termEditor = new Intent(Terms.this, TermEditor.class);
        startActivityForResult(termEditor, MainActivity.ADD_TERM_REQUEST_CODE);

    }

    // Called when the TermEditor activity is finished with a result
    protected void onTermAdded(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            // Add term to database and UI using the Intent data returned
        }
    }
}
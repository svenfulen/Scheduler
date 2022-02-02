package com.example.svenfulenchek_wguscheduler.ui.UI.Terms;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.svenfulenchek_wguscheduler.R;
import com.example.svenfulenchek_wguscheduler.ui.UI.Adapters.TermsAdapter;
import com.example.svenfulenchek_wguscheduler.ui.UI.MainActivity;
import com.example.svenfulenchek_wguscheduler.ui.utils;
import com.example.svenfulenchek_wguscheduler.ui.Database.Repository;
import com.example.svenfulenchek_wguscheduler.ui.Entity.Term;

import java.util.ArrayList;
import java.util.List;

public class TermsList extends AppCompatActivity{

    Repository db;

    public static ArrayList<Term> TERMS_IN_UI = new ArrayList<Term>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_list);

        // Get data from the repository if there is any
        db = MainActivity.db;

        List<Term> termsInDatabase = db.getAllTerms();
        if(termsInDatabase != null && termsInDatabase.size() > 0) {
            TERMS_IN_UI.clear();
            TERMS_IN_UI.addAll(termsInDatabase);
        }

        RecyclerView rvTerms = (RecyclerView) findViewById(R.id.rvTerms);
        // Create a TermsAdapter using data retrieved from the Repository
        TermsAdapter adapter = new TermsAdapter(TERMS_IN_UI);
        // Attach the adapter to the recyclerview to populate items
        rvTerms.setAdapter(adapter);
        // Set layout manager to position the items
        rvTerms.setLayoutManager(new LinearLayoutManager(this));
    }

    // Open the Term Editor activity
    public void addTerm(View view){
        Intent termEditor = new Intent(TermsList.this, TermEditor.class);
        // Tells the term editor that we are adding a new term and not editing an existing one
        termEditor.putExtra("EDIT", false);
        startActivityForResult(termEditor, utils.ADD_TERM_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == utils.ADD_TERM_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                // Get information about term
                String termTitle = data.getStringExtra("TERM_TITLE");
                String termStart = data.getStringExtra("TERM_START");
                String termEnd = data.getStringExtra("TERM_END");
                // Add term to database
                Term newTerm = new Term(termTitle, termStart, termEnd);
                db.insertTerm(newTerm);

                // Refresh view
                TERMS_IN_UI.clear();
                TERMS_IN_UI.addAll(db.getAllTerms());
                RecyclerView rvTerms = (RecyclerView) findViewById(R.id.rvTerms);
                RecyclerView.Adapter<TermsAdapter.ViewHolder> rvAdapter = rvTerms.getAdapter();
                rvAdapter.notifyDataSetChanged();

            }
        }
        if (requestCode == utils.TERM_LIST_RETURN){
            if (resultCode == Activity.RESULT_OK) {
                TERMS_IN_UI.clear();
                TERMS_IN_UI.addAll(db.getAllTerms());
                RecyclerView rvTerms = (RecyclerView) findViewById(R.id.rvTerms);
                RecyclerView.Adapter adapter = rvTerms.getAdapter();
                adapter.notifyDataSetChanged();
            }
        }
    }



}
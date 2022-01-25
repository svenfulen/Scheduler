package com.example.svenfulenchek_wguscheduler.ui.UI;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.svenfulenchek_wguscheduler.R;
import com.example.svenfulenchek_wguscheduler.ui.DAO.TermDAO;
import com.example.svenfulenchek_wguscheduler.ui.Database.Repository;
import com.example.svenfulenchek_wguscheduler.ui.Entity.Term;

import java.util.ArrayList;
import java.util.List;

public class Terms extends AppCompatActivity {

    ArrayList<Term> TERMS_IN_UI = new ArrayList<Term>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms);

        Repository db = new Repository(getApplication());
        TERMS_IN_UI.addAll(db.getAllTerms());

        // Lookup the recyclerview in activity layout
        RecyclerView rvTerms = (RecyclerView) findViewById(R.id.rvTerms);

        // Create adapter passing in the sample user data
        TermsAdapter adapter = new TermsAdapter(TERMS_IN_UI);

        // Attach the adapter to the recyclerview to populate items
        rvTerms.setAdapter(adapter);

        // Set layout manager to position the items
        rvTerms.setLayoutManager(new LinearLayoutManager(this));
        // That's all!

    }

    // Open the Term Editor activity
    public void addTerm(View view){
        Intent termEditor = new Intent(Terms.this, TermEditor.class);
        startActivityForResult(termEditor, MainActivity.ADD_TERM_REQUEST_CODE);
    }

    // TODO: is there a better way to do this?
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == MainActivity.ADD_TERM_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                // Add term to view
                String termTitle = data.getStringExtra("TERM_TITLE");
                String termStart = data.getStringExtra("TERM_START");
                String termEnd = data.getStringExtra("TERM_END");
                TERMS_IN_UI.add(new Term(termTitle, termStart, termEnd));

                // Refresh view
                RecyclerView rvTerms = (RecyclerView) findViewById(R.id.rvTerms);
                RecyclerView.Adapter<TermsAdapter.ViewHolder> rvAdapter = rvTerms.getAdapter();
                rvAdapter.notifyItemInserted(TERMS_IN_UI.size() -1);
            }

        }
    }
}
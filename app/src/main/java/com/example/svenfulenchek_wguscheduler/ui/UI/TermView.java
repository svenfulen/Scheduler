package com.example.svenfulenchek_wguscheduler.ui.UI;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.widget.Toolbar;

import com.example.svenfulenchek_wguscheduler.R;
import com.example.svenfulenchek_wguscheduler.ui.Database.Repository;
import com.example.svenfulenchek_wguscheduler.ui.Entity.Course;
import com.example.svenfulenchek_wguscheduler.ui.Entity.Term;
import com.example.svenfulenchek_wguscheduler.ui.UI.Adapters.CourseAdapter;
import com.example.svenfulenchek_wguscheduler.ui.utils;

import java.util.ArrayList;

/*
Required extras:
TERM_ID
TERM_TITLE
DATE_RANGE
TERM_START
TERM_END
 */
public class TermView extends AppCompatActivity {

    // Data to load into UI
    String TERM_TITLE;
    String TERM_START;
    String TERM_END;
    String dateRange;
    public static int TERM_ID;
    public static ArrayList<Course> COURSES_IN_UI = new ArrayList<Course>();

    public void updateTermView(){
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar_term_view);
        toolbar.setTitle(TERM_TITLE);
        dateRange = TERM_START + " - " + TERM_END;
        toolbar.setSubtitle(dateRange);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_view);

        Intent existingTermData = getIntent();
        TERM_TITLE = existingTermData.getStringExtra("TERM_TITLE");
        TERM_START = existingTermData.getStringExtra("TERM_START");
        TERM_END = existingTermData.getStringExtra("TERM_END");
        dateRange = TERM_START + " - " + TERM_END;
        TERM_ID = existingTermData.getIntExtra("TERM_ID", 0);

        // Get courses in term if not already done
        if(COURSES_IN_UI.size() < 1) {
            Repository db = new Repository(getApplication());
            COURSES_IN_UI.addAll(db.getCoursesInTerm(TERM_ID));
        }

        // Populate UI
        updateTermView();

        // Populate RecyclerView
        RecyclerView rvCoursesInTerm = (RecyclerView) findViewById(R.id.rvCourses);
        CourseAdapter adapter = new CourseAdapter(COURSES_IN_UI);
        rvCoursesInTerm.setAdapter(adapter);
        rvCoursesInTerm.setLayoutManager(new LinearLayoutManager(this));

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar_term_view);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == utils.EDIT_TERM_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {

                Repository db = new Repository(getApplication());

                String newTermTitle = data.getStringExtra("TERM_TITLE");
                String newTermStart = data.getStringExtra("TERM_START");
                String newTermEnd = data.getStringExtra("TERM_END");

                Term existingTerm = new Term(TERM_TITLE, TERM_START, TERM_END);
                existingTerm.setTermId(TERM_ID);

                Term modifiedTerm = new Term(newTermTitle, newTermStart, newTermEnd);
                modifiedTerm.setTermId(TERM_ID);

                // Quick and dirty way to refresh the TermsList ui
                TermsList.TERMS_IN_UI.clear();
                TermsList.TERMS_IN_UI.addAll(db.getAllTerms());

                TERM_TITLE = newTermTitle;
                TERM_START = newTermStart;
                TERM_END = newTermEnd;

                updateTermView();

                db.updateTermDetailsById(TERM_ID, TERM_TITLE, TERM_START, TERM_END);

                // TODO: Refresh view in TermsList
                // Refresh view
                //RecyclerView rvTerms = (RecyclerView) findViewById(R.id.rvTerms);
                //RecyclerView.Adapter<TermsAdapter.ViewHolder> rvAdapter = rvTerms.getAdapter();
                //rvAdapter.notifyItemInserted(TERMS_IN_UI.size() -1);

            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Create options menu
        MenuInflater inflater = getMenuInflater();
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar_term_view);
        inflater.inflate(R.menu.term_view_menu, toolbar.getMenu());
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.editTerm) {
            Intent termEditor = new Intent(TermView.this, TermEditor.class);
            termEditor.putExtra("EDIT", true);
            termEditor.putExtra("TERM_ID", TERM_ID);
            termEditor.putExtra("TERM_TITLE", TERM_TITLE);
            termEditor.putExtra("TERM_START", TERM_START);
            termEditor.putExtra("TERM_END", TERM_END);
            startActivityForResult(termEditor, utils.EDIT_TERM_REQUEST_CODE);
        }
        return true;
    }


    public void addCourseToTerm(View view){

    }

}
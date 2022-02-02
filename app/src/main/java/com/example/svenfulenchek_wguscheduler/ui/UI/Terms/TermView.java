package com.example.svenfulenchek_wguscheduler.ui.UI.Terms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.widget.Toolbar;

import com.example.svenfulenchek_wguscheduler.R;
import com.example.svenfulenchek_wguscheduler.ui.Database.Repository;
import com.example.svenfulenchek_wguscheduler.ui.Dialog;
import com.example.svenfulenchek_wguscheduler.ui.Entity.Course;
import com.example.svenfulenchek_wguscheduler.ui.Entity.Term;
import com.example.svenfulenchek_wguscheduler.ui.UI.Adapters.CourseAdapter;
import com.example.svenfulenchek_wguscheduler.ui.UI.Courses.CourseEditor;
import com.example.svenfulenchek_wguscheduler.ui.UI.MainActivity;
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
public class TermView extends AppCompatActivity implements Dialog.DialogListener {

    Repository db;

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

        db = MainActivity.db;

        Intent existingTermData = getIntent();
        TERM_TITLE = existingTermData.getStringExtra("TERM_TITLE");
        TERM_START = existingTermData.getStringExtra("TERM_START");
        TERM_END = existingTermData.getStringExtra("TERM_END");
        dateRange = TERM_START + " - " + TERM_END;
        TERM_ID = existingTermData.getIntExtra("TERM_ID", 0);

        // Get courses in term

        COURSES_IN_UI.clear();
        COURSES_IN_UI.addAll(db.getCoursesInTerm(TERM_ID));

        // Populate UI
        updateTermView();

        // Populate RecyclerView
        RecyclerView rvCoursesInTerm = (RecyclerView) findViewById(R.id.rvCourses);
        CourseAdapter adapter = new CourseAdapter(COURSES_IN_UI);
        rvCoursesInTerm.setAdapter(adapter);
        rvCoursesInTerm.setLayoutManager(new LinearLayoutManager(this));

        // Allows the toolbar menu to work.
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar_term_view);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == utils.EDIT_TERM_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                String newTermTitle = data.getStringExtra("TERM_TITLE");
                String newTermStart = data.getStringExtra("TERM_START");
                String newTermEnd = data.getStringExtra("TERM_END");

                TERM_TITLE = newTermTitle;
                TERM_START = newTermStart;
                TERM_END = newTermEnd;
                dateRange = TERM_START + " - " + TERM_END;

                updateTermView();
                db.updateTermDetailsById(TERM_ID, TERM_TITLE, TERM_START, TERM_END);
            }
        }

        if (requestCode == utils.ADD_COURSE_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                // Add course to view
                String courseTitle = data.getStringExtra("COURSE_TITLE");
                String courseStart = data.getStringExtra("COURSE_START");
                String courseEnd = data.getStringExtra("COURSE_END");
                String courseStatus = data.getStringExtra("COURSE_STATUS");
                COURSES_IN_UI.add(new Course(TERM_ID, courseTitle, courseStart, courseEnd, courseStatus));

                // Refresh view
                RecyclerView rvCourses = (RecyclerView) findViewById(R.id.rvCourses);
                RecyclerView.Adapter rvAdapter = rvCourses.getAdapter();
                rvAdapter.notifyItemInserted(COURSES_IN_UI.size() -1);

                // Add course to database

                Course newCourse = new Course(TERM_ID, courseTitle, courseStart, courseEnd, courseStatus);
                db.insertCourse(newCourse);
            }
        }
        if (requestCode == utils.TERM_VIEW_RETURN) {
            if (resultCode == Activity.RESULT_CANCELED) {
                COURSES_IN_UI.clear();
                COURSES_IN_UI.addAll(db.getCoursesInTerm(TERM_ID));
                RecyclerView rvCoursesInTerm = (RecyclerView) findViewById(R.id.rvCourses);
                CourseAdapter adapter = (CourseAdapter) rvCoursesInTerm.getAdapter();
                adapter.notifyDataSetChanged();
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

    // Do this when deleting term
    @Override
    public void onYesClicked() {
        db.deleteTermById(TERM_ID);
        finish();
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
        else if(item.getItemId() == R.id.deleteTerm) {
            // Make sure that there are not courses associated with the term.
            if (db.getCoursesInTerm(TERM_ID).size() > 0) {
                Dialog error = new Dialog("Error", "This term cannot be deleted because there are courses in the term.", "info");
                error.show(getSupportFragmentManager(), "Error Dialog");
            }
            else {
                Dialog warning = new Dialog("Warning", "Are you sure you want to delete this term?", "boolean");
                warning.show(getSupportFragmentManager(), "Warning Dialog");
            }
        }
        return true;
    }

    public void addCourseToTerm(View view){
        Intent courseEditor = new Intent(this, CourseEditor.class);
        courseEditor.putExtra("EDIT", false);
        startActivityForResult(courseEditor, utils.ADD_COURSE_REQUEST_CODE);
    }

}
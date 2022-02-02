package com.example.svenfulenchek_wguscheduler.ui.UI.Courses;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.svenfulenchek_wguscheduler.R;
import com.example.svenfulenchek_wguscheduler.ui.Database.Repository;
import com.example.svenfulenchek_wguscheduler.ui.Dialog;
import com.example.svenfulenchek_wguscheduler.ui.Entity.Assessment;
import com.example.svenfulenchek_wguscheduler.ui.UI.Adapters.AssessmentAdapter;
import com.example.svenfulenchek_wguscheduler.ui.UI.Assessments.AssessmentEditor;
import com.example.svenfulenchek_wguscheduler.ui.UI.MainActivity;
import com.example.svenfulenchek_wguscheduler.ui.utils;

import java.util.ArrayList;

/*
Required extras:
COURSE_ID
TERM_ID
COURSE_TITLE
COURSE_STATUS
COURSE_START
COURSE_END
 */
public class CourseView extends AppCompatActivity {

    Repository db;

    // Data to load into UI
    String COURSE_TITLE;
    String COURSE_STATUS;
    String COURSE_START;
    String COURSE_END;
    String dateRange;
    public static int TERM_ID;
    public static int COURSE_ID;
    public static ArrayList<Assessment> ASSESSMENTS_IN_UI = new ArrayList<Assessment>();

    public void updateCourseView(){
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar_course_view);
        toolbar.setTitle(COURSE_TITLE);
        dateRange = COURSE_START + " - " + COURSE_END;
        toolbar.setSubtitle(COURSE_STATUS + ",  " + dateRange);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_view);

        db = MainActivity.db;

        // Load course data from selected course
        Intent existingCourseData = getIntent();
        COURSE_TITLE = existingCourseData.getStringExtra("COURSE_TITLE");
        COURSE_STATUS = existingCourseData.getStringExtra("COURSE_STATUS");
        COURSE_START = existingCourseData.getStringExtra("COURSE_START");
        COURSE_END = existingCourseData.getStringExtra("COURSE_END");
        dateRange = COURSE_START + " - " + COURSE_END;
        TERM_ID = existingCourseData.getIntExtra("TERM_ID", -1);
        COURSE_ID = existingCourseData.getIntExtra("COURSE_ID", -1);

        // Get Assessments from database if needed
        if(ASSESSMENTS_IN_UI.size() < 1){
            ASSESSMENTS_IN_UI.addAll(db.getAssessmentsInCourse(COURSE_ID));
        }
        updateCourseView();

        // Populate RecyclerView
        RecyclerView rvAssessments = (RecyclerView) findViewById(R.id.rvAssessments);
        AssessmentAdapter adapter = new AssessmentAdapter(ASSESSMENTS_IN_UI);
        rvAssessments.setAdapter(adapter);
        rvAssessments.setLayoutManager(new LinearLayoutManager(this));

        // Allows the toolbar menu to work.
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar_course_view);
        setSupportActionBar(toolbar);
    }

    public void addAssessmentToCourse(View view){
        Intent assessmentEditor = new Intent(this, AssessmentEditor.class);
        assessmentEditor.putExtra("EDIT", false);
        startActivityForResult(assessmentEditor, utils.ADD_ASSESSMENT_REQUEST_CODE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Create options menu
        MenuInflater inflater = getMenuInflater();
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar_course_view);
        inflater.inflate(R.menu.course_view_menu, toolbar.getMenu());
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.editCourse) {
            Intent courseEditor = new Intent(CourseView.this, CourseEditor.class);
            courseEditor.putExtra("EDIT", true);
            courseEditor.putExtra("COURSE_ID", COURSE_ID);
            courseEditor.putExtra("COURSE_TITLE", COURSE_TITLE);
            courseEditor.putExtra("COURSE_START", COURSE_START);
            courseEditor.putExtra("COURSE_END", COURSE_END);
            courseEditor.putExtra("COURSE_STATUS", COURSE_STATUS);
            startActivityForResult(courseEditor, utils.EDIT_COURSE_REQUEST_CODE);
        }
        else if(item.getItemId() == R.id.deleteCourse){
            if(db.getAssessmentsInCourse(COURSE_ID).size() > 0){
                Dialog error = new Dialog("Error", "This course cannot be deleted because there are assessments in the course.", "info");
                error.show(getSupportFragmentManager(), "Error Dialog");
            }
            else {
                db.deleteCourseById(COURSE_ID);
                setResult(Activity.RESULT_CANCELED);
                finish();
            }
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == utils.ADD_ASSESSMENT_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                // Get assessment info
                String assessmentTitle = data.getStringExtra("ASSESSMENT_TITLE");
                String assessmentStart = data.getStringExtra("ASSESSMENT_START");
                String assessmentEnd = data.getStringExtra("ASSESSMENT_END");
                String assessmentType = data.getStringExtra("ASSESSMENT_TYPE");

                // Add assessment to database
                Assessment newAssessment = new Assessment(COURSE_ID, assessmentType,assessmentStart,assessmentEnd, assessmentTitle);
                db.insertAssessment(newAssessment);

                // Refresh view
                ASSESSMENTS_IN_UI.clear();
                ASSESSMENTS_IN_UI.addAll(db.getAssessmentsInCourse(COURSE_ID));
                RecyclerView rvAssessments = (RecyclerView) findViewById(R.id.rvAssessments);
                RecyclerView.Adapter rvAdapter = rvAssessments.getAdapter();
                rvAdapter.notifyDataSetChanged();

            }
        }

        // Called after the user finishes editing the course
        if (requestCode == utils.EDIT_COURSE_REQUEST_CODE){
            if (resultCode == Activity.RESULT_OK) {
                COURSE_TITLE = data.getStringExtra("COURSE_TITLE");
                COURSE_START = data.getStringExtra("COURSE_START");
                COURSE_END = data.getStringExtra("COURSE_END");
                COURSE_STATUS = data.getStringExtra("COURSE_STATUS");

                db.updateCourseDetailsById(COURSE_ID, COURSE_TITLE, COURSE_STATUS, COURSE_START, COURSE_END);
                updateCourseView();
            }
        }

        // Called when the Assessment view finishes
        if (requestCode == utils.COURSE_VIEW_RETURN) {
            // Refresh view
            ASSESSMENTS_IN_UI.clear();
            ASSESSMENTS_IN_UI.addAll(db.getAssessmentsInCourse(COURSE_ID));
            RecyclerView rvAssessments = (RecyclerView) findViewById(R.id.rvAssessments);
            RecyclerView.Adapter rvAdapter = rvAssessments.getAdapter();
            rvAdapter.notifyDataSetChanged();
        }

    }


}
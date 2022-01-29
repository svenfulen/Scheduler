package com.example.svenfulenchek_wguscheduler.ui.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.svenfulenchek_wguscheduler.R;
import com.example.svenfulenchek_wguscheduler.ui.Database.Repository;
import com.example.svenfulenchek_wguscheduler.ui.Entity.Assessment;
import com.example.svenfulenchek_wguscheduler.ui.Entity.Course;
import com.example.svenfulenchek_wguscheduler.ui.Entity.Term;
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

        // Load course data from selected course
        Intent existingCourseData = getIntent();
        COURSE_TITLE = existingCourseData.getStringExtra("COURSE_TITLE");
        COURSE_STATUS = existingCourseData.getStringExtra("COURSE_STATUS");
        COURSE_START = existingCourseData.getStringExtra("COURSE_START");
        COURSE_END = existingCourseData.getStringExtra("COURSE_END");
        dateRange = COURSE_START + " - " + COURSE_END;
        TERM_ID = existingCourseData.getIntExtra("TERM_ID", -1);
        COURSE_ID = existingCourseData.getIntExtra("COURSE_ID", -1);

        // UI Elements

        // TODO: Get assessments in course
        // TODO: Database functions

        updateCourseView();

        // TODO: Populate RecyclerView
        // TODO: AssessmentsAdapter

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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == utils.ADD_ASSESSMENT_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {

            }
        }

    }


}
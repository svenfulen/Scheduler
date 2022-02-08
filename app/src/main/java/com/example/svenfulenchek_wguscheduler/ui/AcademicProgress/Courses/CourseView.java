package com.example.svenfulenchek_wguscheduler.ui.AcademicProgress.Courses;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.svenfulenchek_wguscheduler.R;
import com.example.svenfulenchek_wguscheduler.ui.Database.Repository;
import com.example.svenfulenchek_wguscheduler.ui.Dialog;
import com.example.svenfulenchek_wguscheduler.ui.Entity.Assessment;
import com.example.svenfulenchek_wguscheduler.ui.AcademicProgress.Adapters.AssessmentAdapter;
import com.example.svenfulenchek_wguscheduler.ui.AcademicProgress.Assessments.AssessmentEditor;
import com.example.svenfulenchek_wguscheduler.ui.AcademicProgress.Instructors.InstructorView;
import com.example.svenfulenchek_wguscheduler.ui.MainActivity;
import com.example.svenfulenchek_wguscheduler.ui.AcademicProgress.Notes.NotesView;
import com.example.svenfulenchek_wguscheduler.ui.AcademicProgress.appBroadcastReceiver;
import com.example.svenfulenchek_wguscheduler.ui.utils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

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
        ASSESSMENTS_IN_UI.clear();
        ASSESSMENTS_IN_UI.addAll(db.getAssessmentsInCourse(COURSE_ID));

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

    public void openNotesView(View view){
        Intent notesView = new Intent(this, NotesView.class);
        notesView.putExtra("COURSE_ID", COURSE_ID);
        startActivityForResult(notesView, utils.COURSE_VIEW_RETURN);
    }

    public void openInstructorsView(View view){
        Intent instructorsView = new Intent(this, InstructorView.class);
        instructorsView.putExtra("COURSE_ID", COURSE_ID);
        startActivityForResult(instructorsView, utils.COURSE_VIEW_RETURN);
    }

    // Creates broadcasts for both start and end dates.
    public void setAlerts(View view) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
        AlarmManager alarmManager=(AlarmManager)getSystemService(Context.ALARM_SERVICE);

        //long startDateMillis = sdf.parse(COURSE_START).getTime();
        long startDateMillis = Calendar.getInstance().getTimeInMillis();
        //long endDateMillis = sdf.parse(COURSE_START).getTime();
        long endDateMillis = Calendar.getInstance().getTimeInMillis();

        // Create intents to send data to the broadcast receiver
        Intent startNotificationIntent = new Intent(CourseView.this, appBroadcastReceiver.class);
        startNotificationIntent.putExtra("text", COURSE_TITLE);
        startNotificationIntent.putExtra("title", "Course Start");

        Intent endNotificationIntent = new Intent(CourseView.this, appBroadcastReceiver.class);
        endNotificationIntent.putExtra("text", COURSE_TITLE);
        endNotificationIntent.putExtra("title", "Course End");

        // Create a pending intent that will send the broadcast later
        PendingIntent startBroadcast = PendingIntent.getBroadcast(CourseView.this, (1140000 + COURSE_ID), startNotificationIntent, PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent endBroadcast = PendingIntent.getBroadcast(CourseView.this, (1150000 + COURSE_ID), endNotificationIntent, PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);

        // Tell the alarm manager to send the broadcast on the course start/end dates
        alarmManager.set(AlarmManager.RTC_WAKEUP, startDateMillis, startBroadcast);
        alarmManager.set(AlarmManager.RTC_WAKEUP, endDateMillis, endBroadcast);

        Toast.makeText(view.getContext(),"Alerts have been set for the start and end dates of this course.",Toast.LENGTH_LONG).show();
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
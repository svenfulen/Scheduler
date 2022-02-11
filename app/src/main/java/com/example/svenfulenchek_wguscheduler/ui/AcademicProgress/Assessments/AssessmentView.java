package com.example.svenfulenchek_wguscheduler.ui.AcademicProgress.Assessments;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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
import android.widget.TextView;
import android.widget.Toast;

import com.example.svenfulenchek_wguscheduler.R;
import com.example.svenfulenchek_wguscheduler.ui.Database.Repository;
import com.example.svenfulenchek_wguscheduler.ui.MainActivity;
import com.example.svenfulenchek_wguscheduler.ui.AcademicProgress.appBroadcastReceiver;
import com.example.svenfulenchek_wguscheduler.ui.utils;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Locale;

/*
Required extras:
ASSESSMENT_ID
COURSE_ID
ASSESSMENT_TITLE
ASSESSMENT_TYPE
ASSESSMENT_START
ASSESSMENT_END
 */
public class AssessmentView extends AppCompatActivity {

    Repository db;

    // Data to load into UI
    String ASSESSMENT_TITLE;
    String ASSESSMENT_TYPE;
    String ASSESSMENT_START;
    String ASSESSMENT_END;
    String dateRange;
    public static int ASSESSMENT_ID;
    public static int COURSE_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_view);

        db = MainActivity.db;

        // Load course data from selected course
        Intent existingAssessmentData = getIntent();
        ASSESSMENT_TITLE = existingAssessmentData.getStringExtra("ASSESSMENT_TITLE");
        ASSESSMENT_TYPE = existingAssessmentData.getStringExtra("ASSESSMENT_TYPE");
        ASSESSMENT_START = existingAssessmentData.getStringExtra("ASSESSMENT_START");
        ASSESSMENT_END = existingAssessmentData.getStringExtra("ASSESSMENT_END");

        dateRange = ASSESSMENT_START + " - " + ASSESSMENT_END;

        ASSESSMENT_ID = existingAssessmentData.getIntExtra("ASSESSMENT_ID", -1);
        COURSE_ID = existingAssessmentData.getIntExtra("COURSE_ID", -1);

        // Populate view
        TextView assessmentTitle = (TextView)findViewById(R.id.assessmentTitle);
        TextView assessmentType = (TextView)findViewById(R.id.assessmentType);
        TextView assessmentDateRange = (TextView)findViewById(R.id.assessmentDates);

        assessmentTitle.setText(ASSESSMENT_TITLE);
        assessmentType.setText(ASSESSMENT_TYPE);
        assessmentDateRange.setText(dateRange);

        // Allows the toolbar menu to work.
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar_assessment_view);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Create options menu
        MenuInflater inflater = getMenuInflater();
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar_assessment_view);
        inflater.inflate(R.menu.assessment_view_menu, toolbar.getMenu());
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.editAssessment) {
            Intent assessmentEditor = new Intent(AssessmentView.this, AssessmentEditor.class);
            assessmentEditor.putExtra("EDIT", true);
            assessmentEditor.putExtra("ASSESSMENT_ID", ASSESSMENT_ID);
            assessmentEditor.putExtra("COURSE_ID", COURSE_ID);
            assessmentEditor.putExtra("ASSESSMENT_TITLE", ASSESSMENT_TITLE);
            assessmentEditor.putExtra("ASSESSMENT_START", ASSESSMENT_START);
            assessmentEditor.putExtra("ASSESSMENT_END", ASSESSMENT_END);
            assessmentEditor.putExtra("ASSESSMENT_TYPE", ASSESSMENT_TYPE);

            startActivityForResult(assessmentEditor, utils.EDIT_ASSESSMENT_REQUEST_CODE);
        }
        else if(item.getItemId() == R.id.deleteAssessment){
            db.deleteAssessmentById(ASSESSMENT_ID);
            setResult(Activity.RESULT_OK);
            finish();
        }
        return true;
    }

    // Creates broadcasts for both start and end dates.
    public void setAlerts(View view) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
        AlarmManager alarmManager=(AlarmManager)getSystemService(Context.ALARM_SERVICE);

        long startDateMillis = sdf.parse(ASSESSMENT_START).getTime();
        //long startDateMillis = Calendar.getInstance().getTimeInMillis();
        long endDateMillis = sdf.parse(ASSESSMENT_END).getTime();
        //long endDateMillis = Calendar.getInstance().getTimeInMillis();

        String startAlertText = ASSESSMENT_TITLE + " - " + ASSESSMENT_START;
        String endAlertText = ASSESSMENT_TITLE + " - " + ASSESSMENT_END;

        // Create intents to send data to the broadcast receiver
        Intent startNotificationIntent = new Intent(AssessmentView.this, appBroadcastReceiver.class);
        startNotificationIntent.putExtra("text", startAlertText);
        startNotificationIntent.putExtra("title", "Assessment Start");

        Intent endNotificationIntent = new Intent(AssessmentView.this, appBroadcastReceiver.class);
        endNotificationIntent.putExtra("text", endAlertText);
        endNotificationIntent.putExtra("title", "Assessment End");

        // Create a pending intent that will send the broadcast later
        PendingIntent startBroadcast = PendingIntent.getBroadcast(AssessmentView.this, (1240000 + ASSESSMENT_ID), startNotificationIntent, PendingIntent.FLAG_MUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent endBroadcast = PendingIntent.getBroadcast(AssessmentView.this, (1250000 + ASSESSMENT_ID), endNotificationIntent, PendingIntent.FLAG_MUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);

        // Tell the alarm manager to send the broadcast on the course start/end dates
        alarmManager.set(AlarmManager.RTC_WAKEUP, startDateMillis, startBroadcast);
        alarmManager.set(AlarmManager.RTC_WAKEUP, endDateMillis, endBroadcast);

        Toast.makeText(view.getContext(),"Alerts have been set for the start and end dates of this assessment.",Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == utils.EDIT_ASSESSMENT_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                // Load course data from selected course
                ASSESSMENT_TITLE = data.getStringExtra("ASSESSMENT_TITLE");
                ASSESSMENT_TYPE = data.getStringExtra("ASSESSMENT_TYPE");
                ASSESSMENT_START = data.getStringExtra("ASSESSMENT_START");
                ASSESSMENT_END = data.getStringExtra("ASSESSMENT_END");

                db.updateAssessmentDetailsById(ASSESSMENT_ID, ASSESSMENT_TITLE, ASSESSMENT_START, ASSESSMENT_END);

                dateRange = ASSESSMENT_START + " - " + ASSESSMENT_END;

                // Populate view
                TextView assessmentTitle = (TextView)findViewById(R.id.assessmentTitle);
                TextView assessmentType = (TextView)findViewById(R.id.assessmentType);
                TextView assessmentDateRange = (TextView)findViewById(R.id.assessmentDates);

                assessmentTitle.setText(ASSESSMENT_TITLE);
                assessmentType.setText(ASSESSMENT_TYPE);
                assessmentDateRange.setText(dateRange);

                setResult(Activity.RESULT_OK);

            }
        }


    }


}
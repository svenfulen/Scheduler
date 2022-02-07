package com.example.svenfulenchek_wguscheduler.ui.UI.Assessments;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.svenfulenchek_wguscheduler.R;
import com.example.svenfulenchek_wguscheduler.ui.Database.Repository;
import com.example.svenfulenchek_wguscheduler.ui.UI.MainActivity;
import com.example.svenfulenchek_wguscheduler.ui.utils;

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

    public void setAlerts(boolean active){
        if(active) {
            // Turn on alerts for assessment in view
        }
        else {
            // Turn off alerts for assessment in view
        }
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
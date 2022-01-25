package com.example.svenfulenchek_wguscheduler.ui.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.svenfulenchek_wguscheduler.R;

public class MainActivity extends AppCompatActivity {

    // Request codes for all activities are handled HERE
    public static final int ADD_TERM_REQUEST_CODE = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    // Methods to open different views of the app (Terms, courses, assessments)
    public void openTermsActivity(View view) {
        Intent termsIntent = new Intent(MainActivity.this, Terms.class);
        startActivity(termsIntent);
    }

    public void openCoursesActivity(View view) {
        Intent coursesIntent = new Intent(MainActivity.this, Courses.class);
        startActivity(coursesIntent);
    }

    public void openAssessmentsActivity(View view) {
        Intent assessmentsIntent = new Intent(MainActivity.this, Assessments.class);
        startActivity(assessmentsIntent);
    }

}
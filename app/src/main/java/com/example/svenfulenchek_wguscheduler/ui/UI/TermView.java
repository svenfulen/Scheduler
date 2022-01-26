package com.example.svenfulenchek_wguscheduler.ui.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.widget.Toolbar;

import com.example.svenfulenchek_wguscheduler.R;
import com.example.svenfulenchek_wguscheduler.ui.Database.Repository;
import com.example.svenfulenchek_wguscheduler.ui.Entity.Course;
import com.example.svenfulenchek_wguscheduler.ui.UI.Adapters.CourseAdapter;

import java.util.ArrayList;

/*
Required extras:
TERM_ID
TERM_TITLE
DATE_RANGE
 */
public class TermView extends AppCompatActivity {

    public static int TERM_ID;
    public static ArrayList<Course> COURSES_IN_UI = new ArrayList<Course>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_view);

        Intent termData = getIntent();
        TERM_ID = termData.getIntExtra("TERM_ID", 0);

        // Get courses in term if not already done
        if(COURSES_IN_UI.size() < 1) {
            Repository db = new Repository(getApplication());
            COURSES_IN_UI.addAll(db.getCoursesInTerm(TERM_ID));
        }

        // Data to load into UI
        Intent existingTermData = getIntent();
        String termTitle = existingTermData.getStringExtra("TERM_TITLE");
        String dateRange = existingTermData.getStringExtra("DATE_RANGE");

        // UI elements
        Toolbar selectedTermToolbar = (Toolbar)findViewById(R.id.toolbar_term_view);

        // Populate UI
        selectedTermToolbar.setTitle(termTitle);
        selectedTermToolbar.setSubtitle(dateRange);

        // Populate RecyclerView
        RecyclerView rvCoursesInTerm = (RecyclerView) findViewById(R.id.rvCourses);
        CourseAdapter adapter = new CourseAdapter(COURSES_IN_UI);
        rvCoursesInTerm.setAdapter(adapter);
        rvCoursesInTerm.setLayoutManager(new LinearLayoutManager(this));
    }

}
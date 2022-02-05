package com.example.svenfulenchek_wguscheduler.ui.UI.Instructors;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.svenfulenchek_wguscheduler.R;
import com.example.svenfulenchek_wguscheduler.ui.Database.Repository;
import com.example.svenfulenchek_wguscheduler.ui.Entity.Instructor;
import com.example.svenfulenchek_wguscheduler.ui.UI.Adapters.InstructorAdapter;
import com.example.svenfulenchek_wguscheduler.ui.UI.MainActivity;
import com.example.svenfulenchek_wguscheduler.ui.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Required extras:
 * COURSE_ID
 */
public class InstructorView extends AppCompatActivity {

    Repository db;
    int COURSE_ID;

    public static ArrayList<Instructor> INSTRUCTORS_IN_UI = new ArrayList<Instructor>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_view);

        COURSE_ID = getIntent().getIntExtra("COURSE_ID", -1);
        db = MainActivity.db;

        try {
            if (INSTRUCTORS_IN_UI.size() < 1) {
                INSTRUCTORS_IN_UI.addAll(db.getInstructorsInCourse(COURSE_ID));
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        RecyclerView rvInstructors = (RecyclerView) findViewById(R.id.rvInstructors);
        InstructorAdapter adapter = new InstructorAdapter(INSTRUCTORS_IN_UI);
        rvInstructors.setAdapter(adapter);
        rvInstructors.setLayoutManager(new LinearLayoutManager(this));
    }

    public void addInstructor(View view){
        Intent instructorEditor = new Intent(this, InstructorEditor.class);
        instructorEditor.putExtra("COURSE_ID", COURSE_ID);
        instructorEditor.putExtra("EDIT", false);
        startActivityForResult(instructorEditor, utils.ADD_INSTRUCTOR_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == utils.ADD_INSTRUCTOR_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                // Add the course instructor to the database
                String instructorName = data.getStringExtra("INSTRUCTOR_NAME");
                String instructorPhone = data.getStringExtra("INSTRUCTOR_PHONE");
                String instructorEmail = data.getStringExtra("INSTRUCTOR_EMAIL");
                Instructor instructor = new Instructor(COURSE_ID, instructorName, instructorPhone, instructorEmail);
                db.insertInstructor(instructor);

                // Refresh the view
                INSTRUCTORS_IN_UI.clear();
                INSTRUCTORS_IN_UI.addAll(db.getInstructorsInCourse(COURSE_ID));
                RecyclerView rvInstructors = (RecyclerView) findViewById(R.id.rvInstructors);
                rvInstructors.getAdapter().notifyDataSetChanged();
            }
        }
        if (requestCode == utils.EDIT_INSTRUCTOR_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                // Add the course instructor to the database
                String instructorName = data.getStringExtra("INSTRUCTOR_NAME");
                String instructorPhone = data.getStringExtra("INSTRUCTOR_PHONE");
                String instructorEmail = data.getStringExtra("INSTRUCTOR_EMAIL");
                int instructorId = data.getIntExtra("INSTRUCTOR_EMAIL", -1);
                db.updateInstructorById(instructorId, instructorName, instructorPhone, instructorEmail);

                // Refresh the view
                INSTRUCTORS_IN_UI.clear();
                INSTRUCTORS_IN_UI.addAll(db.getInstructorsInCourse(COURSE_ID));
                RecyclerView rvInstructors = (RecyclerView) findViewById(R.id.rvInstructors);
                rvInstructors.getAdapter().notifyDataSetChanged();
            }
        }
        if (requestCode == utils.INSTRUCTORS_VIEW_RETURN) {
            if (resultCode == Activity.RESULT_OK){
                // Refresh the view
                INSTRUCTORS_IN_UI.clear();
                INSTRUCTORS_IN_UI.addAll(db.getInstructorsInCourse(COURSE_ID));
                RecyclerView rvInstructors = (RecyclerView) findViewById(R.id.rvInstructors);
                rvInstructors.getAdapter().notifyDataSetChanged();
            }
        }

    }


}
package com.example.svenfulenchek_wguscheduler.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.svenfulenchek_wguscheduler.R;
import com.example.svenfulenchek_wguscheduler.ui.Database.Repository;
import com.example.svenfulenchek_wguscheduler.ui.AcademicProgress.Terms.TermsList;

public class MainActivity extends AppCompatActivity {

    // This repository is initialized in the MainActivity's onCreate method
    public static Repository db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create a repository to share across all other Activities
        db = new Repository(getApplication());

        // Programmatically create the Academic Progress Button
        Button academicProgressButton = new Button(getApplicationContext());
        academicProgressButton.setWidth(300);
        academicProgressButton.setHeight(200);
        academicProgressButton.setText("Academic Progress");
        academicProgressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openTermsActivity(view);
            }
        });

        // Add the button to the layout
        LinearLayout layout = findViewById(R.id.home_layout);
        layout.addView(academicProgressButton);
    }

    // Methods to open different parts of the app
    public void openTermsActivity(View view) {
        Intent termsIntent = new Intent(MainActivity.this, TermsList.class);
        startActivity(termsIntent);
    }

}
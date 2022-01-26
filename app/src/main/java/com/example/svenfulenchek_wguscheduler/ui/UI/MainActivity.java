package com.example.svenfulenchek_wguscheduler.ui.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.svenfulenchek_wguscheduler.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    // Methods to open different views of the app (TermsList, courses, assessments)
    public void openTermsActivity(View view) {
        if(TermsList.TERMS_IN_UI.size() < 1) {
            Toast.makeText(getApplicationContext(), "Loading Terms", Toast.LENGTH_SHORT).show();
        }
        Intent termsIntent = new Intent(MainActivity.this, TermsList.class);
        startActivity(termsIntent);
    }


}
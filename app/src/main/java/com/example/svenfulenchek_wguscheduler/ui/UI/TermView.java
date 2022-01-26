package com.example.svenfulenchek_wguscheduler.ui.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toolbar;

import com.example.svenfulenchek_wguscheduler.R;

public class TermView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_view);

        // Data to load into UI
        Intent existingTermData = getIntent();
        String termTitle = existingTermData.getStringExtra("TERM_TITLE");

        // UI elements
        Toolbar selectedTermToolbar = (Toolbar)findViewById(R.id.toolbar_term_view);

        // Populate UI
        selectedTermToolbar.setTitle(termTitle);

    }
}
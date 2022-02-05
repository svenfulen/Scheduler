package com.example.svenfulenchek_wguscheduler.ui.UI.Notes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.svenfulenchek_wguscheduler.R;
import com.example.svenfulenchek_wguscheduler.ui.Database.Repository;
import com.example.svenfulenchek_wguscheduler.ui.UI.MainActivity;

/*
Required extras for edit mode:
NOTE_TITLE
NOTE_CONTENT
NOTE_ID
 */

public class NotesEditor extends AppCompatActivity {

    Repository db;

    boolean editMode = false;
    int NOTE_ID;

    TextView noteTitleEditor;
    TextView noteContentEditor;
    ImageButton deleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_editor);

        db = MainActivity.db;

        // UI elements
        noteTitleEditor = (TextView)findViewById(R.id.noteTitleEditor);
        noteContentEditor = (TextView)findViewById(R.id.noteContentEditor);
        deleteButton = (ImageButton)findViewById(R.id.deleteNoteButton);

        // Determine if the note is being edited or a new note is being created
        Intent existingNoteData = getIntent();
        editMode = existingNoteData.getBooleanExtra("EDIT", false);

        // Populate UI with existing note if edit mode
        if (editMode) {
            noteTitleEditor.setText(existingNoteData.getStringExtra("NOTE_TITLE"));
            noteContentEditor.setText(existingNoteData.getStringExtra("NOTE_CONTENT"));
            deleteButton.setVisibility(View.VISIBLE);
            NOTE_ID = existingNoteData.getIntExtra("NOTE_ID", -1);
        }

    }

    public void deleteNote(View view){
        db.deleteNoteById(NOTE_ID);
        setResult(RESULT_OK);
        finish();
    }

    public void onUserFinished(View view){
        // Retrieve form data
        String NOTE_TITLE = noteTitleEditor.getText().toString();
        String NOTE_CONTENT = noteContentEditor.getText().toString();

        // Make sure user has entered in data for each field
        if (!NOTE_TITLE.isEmpty() && !NOTE_CONTENT.isEmpty()) {
            // Return course data in Intent
            Intent extras = new Intent();
            extras.putExtra("NOTE_TITLE", NOTE_TITLE);
            extras.putExtra("NOTE_CONTENT", NOTE_CONTENT);
            if(editMode){ extras.putExtra("NOTE_ID", NOTE_ID); }
            setResult(RESULT_OK, extras);
            finish();
        }
        else {
            Toast.makeText(getApplicationContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
        }

    }

}
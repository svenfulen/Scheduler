package com.example.svenfulenchek_wguscheduler.ui.UI.Notes;

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
import com.example.svenfulenchek_wguscheduler.ui.Entity.Note;
import com.example.svenfulenchek_wguscheduler.ui.UI.Adapters.NotesAdapter;
import com.example.svenfulenchek_wguscheduler.ui.UI.MainActivity;
import com.example.svenfulenchek_wguscheduler.ui.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Required extras:
 * COURSE_ID
 */
public class NotesView extends AppCompatActivity {

    Repository db;
    int COURSE_ID;

    public static ArrayList<Note> NOTES_IN_UI = new ArrayList<Note>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_view);

        COURSE_ID = getIntent().getIntExtra("COURSE_ID", -1);
        db = MainActivity.db;

        List<Note> notesInDatabase = db.getNotesInCourse(COURSE_ID);
        NOTES_IN_UI.clear();
        NOTES_IN_UI.addAll(notesInDatabase);

        RecyclerView rvNotes = (RecyclerView) findViewById(R.id.rvInstructors);
        NotesAdapter adapter = new NotesAdapter(NOTES_IN_UI);
        rvNotes.setAdapter(adapter);
        rvNotes.setLayoutManager(new LinearLayoutManager(this));
    }

    public void addNote(View view){
        Intent notesEditor = new Intent(this, NotesEditor.class);
        notesEditor.putExtra("COURSE_ID", COURSE_ID);
        notesEditor.putExtra("EDIT", false);
        startActivityForResult(notesEditor, utils.ADD_NOTE_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == utils.ADD_NOTE_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                // Add the new note to the database
                String noteTitle = data.getStringExtra("NOTE_TITLE");
                String noteContent = data.getStringExtra("NOTE_CONTENT");
                Note newNote = new Note(COURSE_ID, noteTitle, noteContent);
                db.insertNote(newNote);

                // Refresh the view
                NOTES_IN_UI.clear();
                NOTES_IN_UI.addAll(db.getNotesInCourse(COURSE_ID));
                RecyclerView rvNotes = (RecyclerView) findViewById(R.id.rvInstructors);
                rvNotes.getAdapter().notifyDataSetChanged();
            }
        }
        if (requestCode == utils.EDIT_NOTE_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                // Add the new note to the database
                String noteTitle = data.getStringExtra("NOTE_TITLE");
                String noteContent = data.getStringExtra("NOTE_CONTENT");
                int NOTE_ID = data.getIntExtra("NOTE_ID", -1);
                db.updateNoteById(NOTE_ID, noteTitle, noteContent);

                // Refresh the view
                NOTES_IN_UI.clear();
                NOTES_IN_UI.addAll(db.getNotesInCourse(COURSE_ID));
                RecyclerView rvNotes = (RecyclerView) findViewById(R.id.rvInstructors);
                rvNotes.getAdapter().notifyDataSetChanged();
            }
        }
        if (requestCode == utils.NOTES_VIEW_RETURN) {
            if (resultCode == Activity.RESULT_OK) {
                // Refresh the view
                NOTES_IN_UI.clear();
                NOTES_IN_UI.addAll(db.getNotesInCourse(COURSE_ID));
                RecyclerView rvNotes = (RecyclerView) findViewById(R.id.rvInstructors);
                rvNotes.getAdapter().notifyDataSetChanged();
            }
        }

    }


}
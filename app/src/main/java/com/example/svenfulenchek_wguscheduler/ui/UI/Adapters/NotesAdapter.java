package com.example.svenfulenchek_wguscheduler.ui.UI.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.svenfulenchek_wguscheduler.R;
import com.example.svenfulenchek_wguscheduler.ui.Entity.Note;
import com.example.svenfulenchek_wguscheduler.ui.UI.Notes.NotesEditor;
import com.example.svenfulenchek_wguscheduler.ui.utils;

import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView noteTitle;
        public ImageButton editNoteButton;

        public ViewHolder(View itemView) {
            super(itemView);
            noteTitle = (TextView) itemView.findViewById(R.id.noteTitle);
            editNoteButton = (ImageButton) itemView.findViewById(R.id.editNoteButton);
        }
    }

    // Store a member variable for notes
    private List<Note> mNotes;

    // Pass the data to the adapter
    public NotesAdapter(List<Note> notes) { this.mNotes = notes; }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the layout
        View noteView = inflater.inflate(R.layout.list_item_note, parent, false);

        // Return a new holder instance
        ViewHolder noteViewHolder = new ViewHolder(noteView);
        return noteViewHolder;
    }

    @Override
    public void onBindViewHolder(NotesAdapter.ViewHolder holder, int position) {
        // Get the data model based on the position
        Note note = mNotes.get(position);

        // Set views
        holder.noteTitle.setText(note.getTitle());

        holder.editNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent notesEditor = new Intent(view.getContext(), NotesEditor.class);
                notesEditor.putExtra("EDIT", true);
                notesEditor.putExtra("NOTE_TITLE", note.getTitle());
                notesEditor.putExtra("NOTE_CONTENT", note.getContent());
                notesEditor.putExtra("NOTE_ID", note.getId());
                notesEditor.putExtra("COURSE_ID", note.getCourseId());
                ((Activity)view.getContext()).startActivityForResult(notesEditor, utils.EDIT_NOTE_REQUEST_CODE);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mNotes.size();
    }
}



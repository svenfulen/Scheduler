package com.example.svenfulenchek_wguscheduler.ui.UI.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.svenfulenchek_wguscheduler.R;
import com.example.svenfulenchek_wguscheduler.ui.Entity.Instructor;
import com.example.svenfulenchek_wguscheduler.ui.UI.Instructors.InstructorEditor;
import com.example.svenfulenchek_wguscheduler.ui.utils;

import java.util.List;

public class InstructorAdapter extends RecyclerView.Adapter<InstructorAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView instructorName;
        public TextView instructorPhone;
        public TextView instructorEmail;
        public ImageButton editButton;

        public ViewHolder(View itemView) {
            super(itemView);
            instructorName = (TextView)itemView.findViewById(R.id.instructorName);
            instructorPhone = (TextView)itemView.findViewById(R.id.instructorPhone);
            instructorEmail = (TextView)itemView.findViewById(R.id.instructorEmail);
            editButton = (ImageButton)itemView.findViewById(R.id.editInstructorButton);
        }
    }

    // Store a member variable for instructors
    private List<Instructor> mInstructors;

    // Pass the data to the adapter
    public InstructorAdapter(List<Instructor> instructors) { this.mInstructors = instructors; }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the layout
        View instructorView = inflater.inflate(R.layout.list_item_instructor, parent, false);

        // Return a new holder instance
        ViewHolder instructorViewHolder = new ViewHolder(instructorView);
        return instructorViewHolder;
    }

    @Override
    public void onBindViewHolder(InstructorAdapter.ViewHolder holder, int position) {
        // Get the data model based on the position
        Instructor instructor = mInstructors.get(position);

        // Set views
        holder.instructorName.setText(instructor.getName());
        holder.instructorPhone.setText(instructor.getPhone());
        holder.instructorEmail.setText(instructor.getEmail());

        holder.editButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent instructorEditor = new Intent(view.getContext(), InstructorEditor.class);
                instructorEditor.putExtra("EDIT", true);
                instructorEditor.putExtra("INSTRUCTOR_ID", instructor.getId());
                instructorEditor.putExtra("COURSE_ID", instructor.getCourseId());
                instructorEditor.putExtra("INSTRUCTOR_NAME", instructor.getName());
                instructorEditor.putExtra("INSTRUCTOR_PHONE", instructor.getPhone());
                instructorEditor.putExtra("INSTRUCTOR_EMAIL", instructor.getEmail());
                ((Activity)view.getContext()).startActivityForResult(instructorEditor, utils.EDIT_INSTRUCTOR_REQUEST_CODE);
            }
        });
    }

    @Override
    public int getItemCount() { return mInstructors.size(); }
}

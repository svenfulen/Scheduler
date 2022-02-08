package com.example.svenfulenchek_wguscheduler.ui.AcademicProgress.Adapters;

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
import com.example.svenfulenchek_wguscheduler.ui.Entity.Course;
import com.example.svenfulenchek_wguscheduler.ui.AcademicProgress.Courses.CourseView;
import com.example.svenfulenchek_wguscheduler.ui.utils;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView courseTitle;
        public ImageButton viewCourseButton;

        public ViewHolder(View itemView) {
            super(itemView);
            courseTitle = (TextView) itemView.findViewById(R.id.li_course_name);
            viewCourseButton = (ImageButton) itemView.findViewById(R.id.view_course_button);
        }
    }

    // Store a member variable for courses
    private List<Course> mCourses;

    // Pass the data to the adapter
    public CourseAdapter(List<Course> courses) { mCourses = courses; }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the layout
        View courseView = inflater.inflate(R.layout.list_item_course, parent, false);

        // Return a new holder instance
        CourseAdapter.ViewHolder courseViewHolder = new ViewHolder(courseView);
        return courseViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CourseAdapter.ViewHolder holder, int position) {
        // Get the data model based on position
        Course course = mCourses.get(position);

        // Set item views
        TextView courseTitle = holder.courseTitle;
        courseTitle.setText(course.getTitle());

        holder.viewCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                // Open the term's detailed view
                Intent courseView = new Intent(view.getContext(), CourseView.class);

                // Pass values to the new activity
                courseView.putExtra("COURSE_TITLE", course.getTitle());
                courseView.putExtra("COURSE_STATUS", course.getStatus());
                courseView.putExtra("COURSE_START", course.getStartDate());
                courseView.putExtra("COURSE_END", course.getEndDate());
                courseView.putExtra("TERM_ID", course.getTermId());
                courseView.putExtra("COURSE_ID", course.getId());

                ((Activity)view.getContext()).startActivityForResult(courseView, utils.TERM_VIEW_RETURN);
            }
        });

    }

    @Override
    public int getItemCount() { return mCourses.size(); }


}

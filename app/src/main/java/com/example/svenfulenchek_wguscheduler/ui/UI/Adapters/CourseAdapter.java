package com.example.svenfulenchek_wguscheduler.ui.UI.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.svenfulenchek_wguscheduler.R;
import com.example.svenfulenchek_wguscheduler.ui.Entity.Course;
import com.example.svenfulenchek_wguscheduler.ui.Entity.Term;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView courseTitle;
        public ViewHolder(View itemView) {
            super(itemView);
            courseTitle = (TextView) itemView.findViewById(R.id.course_name);
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
        View courseView = inflater.inflate(R.layout.list_item_term, parent, false);

        // Return a new holder instance
        CourseAdapter.ViewHolder courseViewHolder = new CourseAdapter.ViewHolder(courseView);
        return courseViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Get the data model based on position
        Course course = mCourses.get(position);

        // Set item views
        TextView termTitle = holder.courseTitle;
        termTitle.setText(course.getTitle());
    }

    @Override
    public int getItemCount() { return mCourses.size(); }


}

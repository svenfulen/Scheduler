package com.example.svenfulenchek_wguscheduler.ui.AcademicProgress.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.svenfulenchek_wguscheduler.R;
import com.example.svenfulenchek_wguscheduler.ui.Entity.Assessment;
import com.example.svenfulenchek_wguscheduler.ui.AcademicProgress.Assessments.AssessmentView;
import com.example.svenfulenchek_wguscheduler.ui.utils;

import java.util.List;

public class AssessmentAdapter extends RecyclerView.Adapter<AssessmentAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView assessmentName;
        public TextView assessmentDateRange;
        public TextView assessmentTypeLetter;
        public ImageButton viewAssessmentButton;

        public ViewHolder(View itemView) {
            super(itemView);
            assessmentName = (TextView) itemView.findViewById(R.id.assessmentName);
            assessmentDateRange = (TextView) itemView.findViewById(R.id.assessmentDateRange);
            assessmentTypeLetter = (TextView) itemView.findViewById(R.id.assessmentTypeLetter);
            viewAssessmentButton = (ImageButton) itemView.findViewById(R.id.viewAssessmentButton);
        }
    }

    // Store a member variable for assessments
    private List<Assessment> mAssessments;

    // Pass the data to the adapter
    public AssessmentAdapter(List<Assessment> assessments) { this.mAssessments = assessments; }

    @Override
    public AssessmentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the layout
        View assessmentView = inflater.inflate(R.layout.list_item_assessment, parent, false);

        // Return a new holder instance
        ViewHolder assessmentViewHolder = new ViewHolder(assessmentView);
        return assessmentViewHolder;
    }

    @Override
    public void onBindViewHolder(AssessmentAdapter.ViewHolder holder, int position) {
        // Get the data model based on the position
        Assessment assessment = mAssessments.get(position);

        // Set views
        holder.assessmentName.setText(assessment.getTitle());
        String dateRange = assessment.getStartDate() + " - " + assessment.getEndDate();
        holder.assessmentDateRange.setText(dateRange);
        if (assessment.getType().equals("Performance")) {
            holder.assessmentTypeLetter.setText("PERFORMANCE ASSESSMENT");
        }
        if (assessment.getType().equals("Objective")) {
            holder.assessmentTypeLetter.setText("OBJECTIVE ASSESSMENT");
        }

        holder.viewAssessmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent assessmentView = new Intent(view.getContext(), AssessmentView.class);
                assessmentView.putExtra("ASSESSMENT_TITLE", assessment.getTitle());
                assessmentView.putExtra("ASSESSMENT_TYPE", assessment.getType());
                assessmentView.putExtra("ASSESSMENT_START", assessment.getStartDate());
                assessmentView.putExtra("ASSESSMENT_END", assessment.getEndDate());
                assessmentView.putExtra("ASSESSMENT_ID", assessment.getId());
                assessmentView.putExtra("COURSE_ID", assessment.getCourseId());
                ((Activity)view.getContext()).startActivityForResult(assessmentView, utils.COURSE_VIEW_RETURN);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mAssessments.size();
    }

}

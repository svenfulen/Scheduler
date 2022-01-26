package com.example.svenfulenchek_wguscheduler.ui.UI;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.svenfulenchek_wguscheduler.R;
import com.example.svenfulenchek_wguscheduler.ui.Entity.Term;

import java.util.List;

public class TermsAdapter extends RecyclerView.Adapter<TermsAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView termTitle;
        public TextView termDateRange;

        public ViewHolder(View itemView) {
            super(itemView);
            termTitle = (TextView) itemView.findViewById(R.id.termTitle);
            termDateRange = (TextView) itemView.findViewById(R.id.termDateRange);
        }
    }

    // Store a member variable for the terms
    private List<Term> mTerms;

    // Pass the data to the adapter
    public TermsAdapter(List<Term> terms) {
        mTerms = terms;
    }

    @Override
    public TermsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the layout
        View contactView = inflater.inflate(R.layout.list_item_term, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(TermsAdapter.ViewHolder holder, int position) {
        // Get the data model based on position
        Term term = mTerms.get(position);

        // Set item views
        TextView termTitle = holder.termTitle;
        termTitle.setText(term.getTermTitle());

        String dateRangeText = ( term.getStartDate() + " - " + term.getEndDate());
        TextView termDateRange = holder.termDateRange;
        termDateRange.setText(dateRangeText);
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return mTerms.size();
    }

    public Term getItem(int position){
        return mTerms.get(position);
    }

}
// TODO: Code analysis, refactoring, commenting

package com.example.svenfulenchek_wguscheduler.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.svenfulenchek_wguscheduler.R;
import com.example.svenfulenchek_wguscheduler.ui.Models.Term;

import java.util.List;

// Create the basic adapter extending from RecyclerView.Adapter
// Note that we specify the custom ViewHolder which gives us access to our views
public class TermsAdapter extends
        RecyclerView.Adapter<TermsAdapter.ViewHolder> {

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView termTitle;
        public TextView termDateRange;
        //public TextView endDate;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            termTitle = (TextView) itemView.findViewById(R.id.termTitle);
            termDateRange = (TextView) itemView.findViewById(R.id.termDateRange);
            //endDate = (TextView) itemView.findViewById(R.id.termEndDate);

        }
    }

    // Store a member variable for the terms
    private List<Term> mTerms;

    // Pass in the contact array into the constructor
    public TermsAdapter(List<Term> terms) {
        mTerms = terms;
    }

    // Usually involves inflating a layout from XML and returning the holder
    @Override
    public TermsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
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

        // Set item views based on your views and data model
        TextView termTitle = holder.termTitle;
        termTitle.setText(term.getTermTitle());

        String dateRangeText = ( term.getStartDate() + " - " + term.getEndDate());
        TextView termDateRange = holder.termDateRange;
        termDateRange.setText(dateRangeText);


        //TextView termEnd = holder.endDate;
        //termEnd.setText(term.getEndDate());
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return mTerms.size();
    }


}
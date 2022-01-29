package com.example.svenfulenchek_wguscheduler.ui.UI.Adapters;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.svenfulenchek_wguscheduler.R;
import com.example.svenfulenchek_wguscheduler.ui.Entity.Term;

import java.util.List;

public class TermsAdapter extends RecyclerView.Adapter<TermsAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView termTitle;
        public TextView termDateRange;
        public ImageButton viewTermButton;

        public ViewHolder(View itemView) {
            super(itemView);
            termTitle = (TextView) itemView.findViewById(R.id.termTitle);
            termDateRange = (TextView) itemView.findViewById(R.id.termDateRange);
            viewTermButton = (ImageButton) itemView.findViewById(R.id.viewTermButton);


        }

    }

    // Store a member variable for the terms
    private List<Term> mTerms;
    private termClickListener onTermClickListener;

    // Pass the data to the adapter
    public TermsAdapter(List<Term> terms, termClickListener clickListener) {
        this.mTerms = terms;
        this.onTermClickListener = clickListener;
    }

    @Override
    public TermsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the layout
        View termsView = inflater.inflate(R.layout.list_item_term, parent, false);

        // Return a new holder instance
        ViewHolder termsViewHolder = new ViewHolder(termsView);
        return termsViewHolder;
    }

    // Populating data into the item through holder
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

        holder.viewTermButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                onTermClickListener.onTermClick(term.getTermId(), term.getTermTitle(), term.getStartDate(), term.getEndDate());
            }
        });

    }

    public interface termClickListener {
        void onTermClick(int termId, String termTitle, String termStart, String termEnd);
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return mTerms.size();
    }

    // TODO: Remove this if it isn't ever needed
    //public Term getItem(int position){return mTerms.get(position);}

}
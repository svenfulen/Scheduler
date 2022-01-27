package com.example.svenfulenchek_wguscheduler.ui.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

// This object contains all other objects it is at the top of a hierarchy

@Entity(tableName = "TERMS")
public class Term {
    // Primary key for identifying term in database
    @PrimaryKey(autoGenerate = true)
    private int termId;

    // Attributes
    private String termTitle;
    private String startDate;
    private String endDate;

    // Constructor
    public Term (String termTitle, String startDate, String endDate){
        this.termTitle = termTitle;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    // Getters and setters
    public String getTermTitle() {
        return termTitle;
    }
    public void setTermTitle(String termTitle) {
        this.termTitle = termTitle;
    }
    public String getStartDate() {
        return startDate;
    }
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
    public String getEndDate() {
        return endDate;
    }
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
    public int getTermId() { return termId; }
    public void setTermId(int termId) { this.termId = termId; }

}

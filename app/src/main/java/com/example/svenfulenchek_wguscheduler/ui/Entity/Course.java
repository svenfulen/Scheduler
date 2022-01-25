package com.example.svenfulenchek_wguscheduler.ui.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

@Entity(tableName = "COURSES")
public class Course {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private int termId;

    private String title;
    private String startDate;
    private String endDate;
    private String status;

    public Course(int termId, String title, String startDate, String endDate, String status) {
        this.termId = termId;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getId(){ return id; }

    public void setId(int id) { this.id = id; }

    public int getTermId() { return termId; }

    public void setTermId(int termId) { this.termId = termId; }
}

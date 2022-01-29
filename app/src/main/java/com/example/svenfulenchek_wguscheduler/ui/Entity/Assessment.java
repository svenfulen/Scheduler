package com.example.svenfulenchek_wguscheduler.ui.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "ASSESSMENTS")
public class Assessment {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private int courseId;

    private String type;
    private String startDate;
    private String endDate;
    private String title;

    public Assessment(int courseId, String type, String startDate, String endDate, String title) {
        this.courseId = courseId;
        this.type = type;
        this.startDate = startDate;
        this.endDate = endDate;
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public int getCourseId() { return courseId; }

    public void setCourseId(int courseId) { this.courseId = courseId; }
}

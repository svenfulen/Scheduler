package com.example.svenfulenchek_wguscheduler.ui.Models;

public class Assessment {
    public enum assessmentType{ PERFORMANCE, OBJECTIVE }

    private assessmentType type;
    private String startDate;
    private String endDate;
    private String title;
    private String information;

    public assessmentType getType() {
        return type;
    }

    public void setType(assessmentType type) {
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

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

}

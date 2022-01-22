package com.example.svenfulenchek_wguscheduler.ui.Models;

import java.util.ArrayList;
// This object contains all other objects it is at the top of a hierarchy
public class Term {

    private String termTitle;
    // Strings to be displayed in MM/DD/YYYY format
    private String startDate;
    private String endDate;
    // ArrayList of courses in the term
    private ArrayList<Course> coursesInTerm = new ArrayList<Course>();

    public Term (String termTitle, String startDate, String endDate){
        this.termTitle = termTitle;
        this.startDate = startDate;
        this.endDate = endDate;
    }

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

    public ArrayList<Course> getCoursesInTerm() {
        return coursesInTerm;
    }

    public void setCoursesInTerm(ArrayList<Course> coursesInTerm) {
        this.coursesInTerm = coursesInTerm;
    }

    // TODO: public void addCourseToTerm()
    // TODO: public void removeCourseFromTerm()

}

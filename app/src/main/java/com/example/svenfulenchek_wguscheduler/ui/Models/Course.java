package com.example.svenfulenchek_wguscheduler.ui.Models;

import java.util.ArrayList;

public class Course {
    public enum courseStatus {
        IN_PROGRESS, COMPLETED, DROPPED, PLAN_TO_TAKE
    }

    private static class courseInstructor {
        public String name;
        public String phoneNumber;
        public String email;
        public courseInstructor(String name, String phoneNumber, String email){
            this.name = name;
            this.phoneNumber = phoneNumber;
            this.email = email;
        }
    }

    private String title;
    private String startDate;
    private String endDate;
    private ArrayList<Note> notes;
    private ArrayList<Assessment> assessments;
    private ArrayList<courseInstructor> instructors;
    private courseStatus status;

    public Course(String title, String startDate, String endDate, courseStatus status) {
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

    public ArrayList<Note> getNotes() {
        return notes;
    }

    public void setNotes(ArrayList<Note> notes) {
        this.notes = notes;
    }

    public void addNote(Note note) {
        this.notes.add(note);
    }

    public ArrayList<Assessment> getAssessments() {
        return assessments;
    }

    public void setAssessments(ArrayList<Assessment> assessments) {
        this.assessments = assessments;
    }

    public void addAssessment(Assessment assessment) {
        this.assessments.add(assessment);
    }

    public ArrayList<courseInstructor> getInstructors() {
        return instructors;
    }

    public void setInstructors(ArrayList<courseInstructor> instructors) {
        this.instructors = instructors;
    }

    public void addInstructor(String name, String phoneNumber, String email) {
        this.instructors.add(new courseInstructor(name, phoneNumber, email));
    }

    public courseStatus getStatus() {
        return status;
    }

    public void setStatus(courseStatus status) {
        this.status = status;
    }
}

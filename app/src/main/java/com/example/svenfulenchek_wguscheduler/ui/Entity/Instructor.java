package com.example.svenfulenchek_wguscheduler.ui.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "INSTRUCTORS")
public class Instructor {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private int courseId;

    private String name;
    private String phone;
    private String email;

    public Instructor(int courseId, String name, String phone, String email){
        this.courseId = courseId;
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

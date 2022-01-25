package com.example.svenfulenchek_wguscheduler.ui.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "NOTES")
public class Note {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private int courseId;

    private String title;
    private String content;

    public Note(int courseId, String title, String content) {
        this.courseId = courseId;
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public int getCourseId() { return courseId; }

    public void setCourseId(int courseId) { this.courseId = courseId; }
}

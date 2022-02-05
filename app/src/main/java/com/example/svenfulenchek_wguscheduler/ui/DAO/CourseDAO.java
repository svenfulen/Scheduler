package com.example.svenfulenchek_wguscheduler.ui.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.svenfulenchek_wguscheduler.ui.Entity.Course;

import java.util.List;

@Dao
public interface CourseDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Course course);

    @Update
    void update(Course course);

    @Delete
    void delete(Course course);

    @Query("SELECT * FROM COURSES")
    public List<Course> getAllCourses();

    @Query("SELECT * FROM COURSES WHERE termId=:termId")
    public List<Course> getCoursesInTerm(int termId);

    @Query("DELETE FROM COURSES WHERE id=:courseId")
    void deleteById(int courseId);

    @Query("UPDATE COURSES " + "SET title=:COURSE_TITLE, startDate=:COURSE_START, endDate=:COURSE_END, status=:COURSE_STATUS " + "WHERE id=:COURSE_ID")
    void updateById(int COURSE_ID, String COURSE_TITLE, String COURSE_STATUS, String COURSE_START, String COURSE_END);

    }

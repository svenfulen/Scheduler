package com.example.svenfulenchek_wguscheduler.ui.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.svenfulenchek_wguscheduler.ui.Entity.Instructor;

import java.util.List;

@Dao
public interface InstructorDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Instructor instructor);

    @Update
    void update(Instructor instructor);

    @Delete
    void delete(Instructor instructor);

    @Query("SELECT * FROM INSTRUCTORS WHERE courseId=:courseId")
    public List<Instructor> getInstructorsInCourse(int courseId);

    @Query("UPDATE INSTRUCTORS " + "SET name = :instructorName, phone=:instructorPhone, email=:instructorEmail " + "WHERE id = :instructorId" )
    void updateById(int instructorId, String instructorName, String instructorPhone, String instructorEmail);

    @Query("DELETE FROM INSTRUCTORS WHERE id=:id")
    void deleteById(int id);

}

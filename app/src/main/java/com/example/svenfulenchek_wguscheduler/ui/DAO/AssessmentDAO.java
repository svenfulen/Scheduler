package com.example.svenfulenchek_wguscheduler.ui.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.svenfulenchek_wguscheduler.ui.Entity.Assessment;

import java.util.List;

@Dao
public interface AssessmentDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Assessment assessment);

    @Update
    void update(Assessment assessment);

    @Delete
    void delete(Assessment assessment);

    @Query("SELECT * FROM ASSESSMENTS")
    public List<Assessment> getAllAssessments();

    @Query("SELECT * FROM ASSESSMENTS WHERE courseId=:courseId")
    public List<Assessment> getAssessmentsByCourseId(int courseId);

    @Query("DELETE FROM ASSESSMENTS WHERE id=:id")
    void deleteById(int id);

    @Query("UPDATE ASSESSMENTS " + "SET title = :assessmentTitle, startDate = :assessmentStart, endDate = :assessmentEnd " + "WHERE id = :assessmentID" )
    void updateAssessmentDetailsById(int assessmentID, String assessmentTitle, String assessmentStart, String assessmentEnd);

}

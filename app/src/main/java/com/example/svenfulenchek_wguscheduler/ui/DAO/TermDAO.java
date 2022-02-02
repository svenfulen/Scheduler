package com.example.svenfulenchek_wguscheduler.ui.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.svenfulenchek_wguscheduler.ui.Entity.Term;

import java.util.List;

@Dao
public interface TermDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Term term);

    @Update
    void update(Term term);

    @Delete
    void delete(Term term);

    @Query("SELECT * FROM TERMS")
    List<Term> getAllTerms();

    @Query("UPDATE TERMS " + "SET termTitle = :termTitle, startDate = :termStart, endDate = :termEnd " + "WHERE termId = :termID" )
    void updateTermDetailsById(int termID, String termTitle, String termStart, String termEnd);

    // TODO: remove this if never used
    @Query("SELECT * FROM TERMS WHERE termId = :termID")
    Term getTermById(int termID);
}

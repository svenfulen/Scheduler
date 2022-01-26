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
    public List<Term> getAllTerms();

    // TODO: Test this function.  I don't know if it works but this is basically what it should do.
    @Query("UPDATE TERMS " + "SET termTitle = :termTitle, startDate = :termStart, endDate = :termEnd " + "WHERE termId = :termID" )
    void updateTermDetailsById(int termID, String termTitle, String termStart, String termEnd);
}

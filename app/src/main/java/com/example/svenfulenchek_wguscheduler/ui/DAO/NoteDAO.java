package com.example.svenfulenchek_wguscheduler.ui.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.svenfulenchek_wguscheduler.ui.Entity.Note;

import java.util.List;

@Dao
public interface NoteDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Note note);

    @Update
    void update(Note note);

    @Delete
    void delete(Note note);

    @Query("SELECT * FROM NOTES")
    public List<Note> getAllNotes();

    @Query("SELECT * FROM NOTES WHERE courseId=:courseId")
    public List<Note> getNotesInCourse(int courseId);

    @Query("UPDATE NOTES " + "SET title = :noteTitle, content = :noteContent " + "WHERE id = :noteId" )
    void updateById(int noteId, String noteTitle, String noteContent);

    @Query("DELETE FROM NOTES WHERE id=:id")
    void deleteById(int id);
}

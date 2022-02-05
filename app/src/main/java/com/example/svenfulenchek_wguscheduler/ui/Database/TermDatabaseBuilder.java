package com.example.svenfulenchek_wguscheduler.ui.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.svenfulenchek_wguscheduler.ui.DAO.AssessmentDAO;
import com.example.svenfulenchek_wguscheduler.ui.DAO.CourseDAO;
import com.example.svenfulenchek_wguscheduler.ui.DAO.InstructorDAO;
import com.example.svenfulenchek_wguscheduler.ui.DAO.NoteDAO;
import com.example.svenfulenchek_wguscheduler.ui.DAO.TermDAO;
import com.example.svenfulenchek_wguscheduler.ui.Entity.Instructor;
import com.example.svenfulenchek_wguscheduler.ui.Entity.Term;
import com.example.svenfulenchek_wguscheduler.ui.Entity.Course;
import com.example.svenfulenchek_wguscheduler.ui.Entity.Assessment;
import com.example.svenfulenchek_wguscheduler.ui.Entity.Note;
import com.example.svenfulenchek_wguscheduler.ui.utils;

@Database(entities={Term.class, Course.class, Assessment.class, Note.class, Instructor.class}, version= utils.DATABASE_VERSION, exportSchema = false)
public abstract class TermDatabaseBuilder extends RoomDatabase {
    // Data access objects
    public abstract TermDAO termDAO();
    public abstract CourseDAO courseDAO();
    public abstract AssessmentDAO assessmentDAO();
    public abstract NoteDAO noteDAO();
    public abstract InstructorDAO InstructorDAO();

    // Database instance
    private static volatile TermDatabaseBuilder INSTANCE;

    // If there is no database built, build a new one.  Else, return the current database
    static TermDatabaseBuilder getDatabase(final Context context){
        if (INSTANCE == null) {
            synchronized (TermDatabaseBuilder.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), TermDatabaseBuilder.class, "TermDatabase.db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}

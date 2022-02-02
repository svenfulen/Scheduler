package com.example.svenfulenchek_wguscheduler.ui.Database;

import android.app.Application;

import com.example.svenfulenchek_wguscheduler.ui.DAO.AssessmentDAO;
import com.example.svenfulenchek_wguscheduler.ui.DAO.CourseDAO;
import com.example.svenfulenchek_wguscheduler.ui.DAO.NoteDAO;
import com.example.svenfulenchek_wguscheduler.ui.DAO.TermDAO;
import com.example.svenfulenchek_wguscheduler.ui.Entity.Assessment;
import com.example.svenfulenchek_wguscheduler.ui.Entity.Course;
import com.example.svenfulenchek_wguscheduler.ui.Entity.Note;
import com.example.svenfulenchek_wguscheduler.ui.Entity.Term;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repository {
    private TermDAO mTermDAO;
    private CourseDAO mCourseDAO;
    private AssessmentDAO mAssessmentDAO;
    private NoteDAO mNoteDAO;

    private List<Term> mAllTerms;
    private List<Course> mAllCourses;
    private List<Assessment> mAllAssessments;
    private List<Note> mAllNotes;

    private List<Course> coursesInTerm;

    private List<Assessment> assessmentsInCourse;

    // Create threads for database operations to run on
    private static int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public Repository(Application application){
        TermDatabaseBuilder db = TermDatabaseBuilder.getDatabase(application);
        mTermDAO = db.termDAO();
        mCourseDAO = db.courseDAO();
        mAssessmentDAO = db.assessmentDAO();
        mNoteDAO = db.noteDAO();
    }

    public void insertTerm(Term term){
        databaseExecutor.execute(()->{
            mTermDAO.insert(term);
        });
        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void deleteTermById(int id){
        databaseExecutor.execute(()->{
            mTermDAO.deleteById(id);
        });
        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public List<Term> getAllTerms(){
        databaseExecutor.execute(()->{
            mAllTerms = mTermDAO.getAllTerms();
        });
        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllTerms;
    }

    public void updateTermDetailsById(int termID, String termTitle, String termStart, String termEnd){
        databaseExecutor.execute(()->{
            mTermDAO.updateTermDetailsById(termID,termTitle,termStart,termEnd);
        });
        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void insertCourse(Course course){
        databaseExecutor.execute(()->{
            mCourseDAO.insert(course);
        });
        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void deleteCourseById(int courseId){
        databaseExecutor.execute(()->{
            mCourseDAO.deleteById(courseId);
        });
        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public List<Course> getCoursesInTerm(int termId){
        databaseExecutor.execute(()->{
            coursesInTerm = mCourseDAO.getCoursesInTerm(termId);
        });
        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        return coursesInTerm;
    }

    public void insertAssessment(Assessment assessment){
        databaseExecutor.execute(() -> {
            mAssessmentDAO.insert(assessment);
        });
        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void deleteAssessmentById(int id) {
        databaseExecutor.execute(() -> {
            mAssessmentDAO.deleteById(id);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void updateAssessmentDetailsById(int assessmentID, String assessmentTitle, String assessmentStart, String assessmentEnd) {
        databaseExecutor.execute(() -> {
            mAssessmentDAO.updateAssessmentDetailsById(assessmentID, assessmentTitle, assessmentStart, assessmentEnd);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public List<Assessment> getAssessmentsInCourse(int courseId){
        databaseExecutor.execute(() -> {
            assessmentsInCourse = mAssessmentDAO.getAssessmentsByCourseId(courseId);
        });
        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        return assessmentsInCourse;
    }

    public void updateCourseDetailsById(int COURSE_ID, String COURSE_TITLE, String COURSE_STATUS, String COURSE_START, String COURSE_END){
        databaseExecutor.execute(() -> {
            mCourseDAO.updateById(COURSE_ID, COURSE_TITLE, COURSE_STATUS, COURSE_START, COURSE_END);
        });
        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

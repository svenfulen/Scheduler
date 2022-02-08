package com.example.svenfulenchek_wguscheduler.ui;

import android.widget.Spinner;

// This class stores constant values and utility functions
public class utils {
    public static final int DATABASE_VERSION = 65;

    // Request codes for all activities are handled HERE
    public static final int INSTRUCTORS_VIEW_RETURN = 1005;
    public static final int NOTES_VIEW_RETURN = 1006;
    public static final int COURSE_VIEW_RETURN = 1007;
    public static final int TERM_LIST_RETURN = 1009;
    public static final int TERM_VIEW_RETURN = 1008;
    public static final int APPOINTMENT_VIEW_RETURN = 1009;

    public static final int ADD_TERM_REQUEST_CODE = 1010;
    public static final int EDIT_TERM_REQUEST_CODE = 1011;

    public static final int ADD_COURSE_REQUEST_CODE = 1020;
    public static final int EDIT_COURSE_REQUEST_CODE = 1021;

    public static final int ADD_ASSESSMENT_REQUEST_CODE = 1030;
    public static final int EDIT_ASSESSMENT_REQUEST_CODE = 1031;

    public static final int ADD_NOTE_REQUEST_CODE = 1040;
    public static final int EDIT_NOTE_REQUEST_CODE = 1041;

    public static final int ADD_INSTRUCTOR_REQUEST_CODE = 1050;
    public static final int EDIT_INSTRUCTOR_REQUEST_CODE = 1051;

    public static final int ADD_APPOINTMENT_REQUEST_CODE = 1060;
    public static final int EDIT_APPOINTMENT_REQUEST_CODE = 1061;

    public static int getSpinnerItemIndex(Spinner spinner, String myString){
        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                return i;
            }
        }

        return 0;
    }

}

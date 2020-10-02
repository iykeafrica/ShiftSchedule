package com.example.calendertestapp;

import android.os.Bundle;
import android.widget.CalendarView;

import androidx.lifecycle.ViewModel;

public class ShiftScheduleViewModel extends ViewModel {
    public static final String FINAL_DATE = "com.example.calendertestapp.FINAL_DATE";
    public static final String CURRENT_DATE = "com.example.calendertestapp.CURRENT_DATE";
    public static final String I_DAY = "com.example.calendertestapp.I_DAY";
    public static final String I_YEAR = "com.example.calendertestapp.I_YEAR";
    public static final String I_MONTH = "com.example.calendertestapp.I_MONTH";
    public static final String DAYS_IN_MONTH = "com.example.calendertestapp.DAYS_IN_MONTH";
    public static final String COUNT = "com.example.calendertestapp.COUNT";

    public static String mFinalDate = "02/01/1970";
    public String mCurrentDate;
    public int iDay;
    public int iMonth;
    public int iYear;
    public int mDaysInMonth;
    public boolean mIsNewlyCreated = true;
    public int mCount;

    public void saveState(Bundle outState) {
        outState.putString(FINAL_DATE, mFinalDate);
        outState.putString(CURRENT_DATE, mCurrentDate);
        outState.putInt(I_DAY, iDay);
        outState.putInt(I_MONTH, iMonth);
        outState.putInt(I_YEAR, iYear);
        outState.putInt(DAYS_IN_MONTH, mDaysInMonth);
        outState.putInt(COUNT, mCount);
    }

    public void restoreState(Bundle inState) {
        mFinalDate = inState.getString(FINAL_DATE, "02/01/1970");
        mCurrentDate = inState.getString(CURRENT_DATE, "");
        iMonth = inState.getInt(I_DAY, 0);
        iMonth = inState.getInt(I_MONTH, 0);
        iYear = inState.getInt(I_YEAR, 0);
        mDaysInMonth = inState.getInt(DAYS_IN_MONTH, 0);
        mCount = inState.getInt(COUNT, 0);
    }
}

package com.example.calendertestapp.plant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.calendertestapp.R;
import com.example.calendertestapp.ShiftScheduleViewModel;
import com.example.calendertestapp.WelcomeActivity;

import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class PlantShiftAActivity extends AppCompatActivity {

    private static final String TAG = "PlantShiftAActivity";
    public static final int SHIFT_CYCLE_DAYS = 9;
    private TextView mShiftView, mWeekday, mDayOfMonth, mMonth, mShiftDays;
    private Date mNow, mDateOnCreate, mDateOnResume;
    private SimpleDateFormat dates = new SimpleDateFormat("dd/MM/yyyy");
    private CalendarView mCalendarView;
    private Calendar mCalendar;

    private ShiftScheduleViewModel mViewModel;
    private long mBackPressed = 0;
    private String mOnResumeDate;
    private int mDay;
    private String mMonth_;
    private String mYear_;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_shift_schedule);

        assert getSupportActionBar() != null;
        getSupportActionBar().hide();

        ViewModelProvider provider = new ViewModelProvider(getViewModelStore(), ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()));
        mViewModel = provider.get(ShiftScheduleViewModel.class);

        if (savedInstanceState != null && mViewModel.mIsNewlyCreated){
            mViewModel.restoreState(savedInstanceState);
        }
        mViewModel.mIsNewlyCreated = false;

        mNow = new Date();
        mDateOnCreate = new Date();
        mDateOnResume = new Date();

        mMonth = findViewById(R.id.month_view);
        mShiftDays = findViewById(R.id.shift_days);
        mShiftDays.setVisibility(View.GONE);
        mWeekday = findViewById(R.id.weekday_view);
        mDayOfMonth = findViewById(R.id.day_of_month_view);
        mShiftView = findViewById(R.id.shift_view);
        mCalendarView = findViewById(R.id.calendar_view);
        ImageView help = findViewById(R.id.help);

        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = pref.edit();
                editor.clear();
                editor.apply();
                startActivity(new Intent(getApplicationContext(), WelcomeActivity.class));
            }
        });
        calenderDateClick();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        if (outState != null){
            mViewModel.saveState(outState);
        }
    }

    private void calenderDateClick() {
        mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                mViewModel.iDay = dayOfMonth;
                mViewModel.iMonth = (month + 1);
                mViewModel.iYear = year;


                mViewModel.mCurrentDate = dayOfMonth + "/" + (month + 1) + "/" + year;

//                mDateOnCreate = new Date(year, month, dayOfMonth);
//                mNow = new Date(year, month, (dayOfMonth - 1));

                mDateOnCreate = new Date( mViewModel.iYear, mViewModel.iMonth, mViewModel.iDay);
                mNow = new Date(mViewModel.iYear, mViewModel.iMonth, (mViewModel.iDay - 1));

                mDayOfMonth.setText("" + mViewModel.iDay);
                dateFormatter();
                shiftDutyCheck();

                mCalendar = new GregorianCalendar(year, month, dayOfMonth);
                mViewModel.mDaysInMonth = mCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
                shiftWorkingDays();
            }
        });

    }

    private void dateFormatter() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE");
        Format formatter = new SimpleDateFormat("MMMM");

        mMonth.setText("" + formatter.format(mDateOnCreate));
        mWeekday.setText("" + simpleDateFormat.format(mNow));
    }

    protected void onResume() {
        super.onResume();
        //LocalDate today = LocalDate.now();

        Calendar cal = Calendar.getInstance();

        if (mViewModel.mCurrentDate != null){
            //mViewModel.mCurrentDate = dayOfMonth + "/" + (month + 1) + "/" + year;
            try {
                mDateOnResume = new SimpleDateFormat("dd/MM/yyyy").parse(mViewModel.mCurrentDate);
                Log.i(TAG, "onResume: " + "If mViewModel.mCurrentDate != null. Then, mDateOnResume is: " + mDateOnResume);

            } catch (ParseException e) {
                e.printStackTrace();
            }
            assert mDateOnResume != null;
            cal.setTime(mDateOnResume);
            Log.i(TAG, "onResume: " + "cal.setTime(mDateOnResume) is: " + mDateOnResume);

            mOnResumeDate = mViewModel.mCurrentDate;
            Log.i(TAG, "onResume: " + "mOnResumeDate = mViewModel.mCurrentDate: " + mOnResumeDate);

            mDay = Integer.parseInt(mViewModel.mCurrentDate.substring(0,1));
            Log.i(TAG, "onResume: " + "mDay is: " + mDay);

            mMonth_ = mViewModel.mCurrentDate.substring(2,4);
            Log.i(TAG, "onResume: " + "mMonth_ is: " + mMonth_);

            mYear_ = mViewModel.mCurrentDate.substring(5,9);
            Log.i(TAG, "onResume: " + "mYear_ is: " + mYear_);

        } else {
            cal.setTime(mDateOnResume);
            Log.i(TAG, "onResume: " + "If mViewModel.mCurrentDate == null. Then, mDateOnResume is: " + mDateOnResume);

            mDay = cal.get(Calendar.DAY_OF_MONTH);
            Log.i(TAG, "onResume: " + "mDay is: " + mDay);

            mViewModel.iMonth = cal.get(Calendar.MONTH) + 1;
            mViewModel.iYear = cal.get(Calendar.YEAR);

            mOnResumeDate = mDay + "/" + mViewModel.iMonth + "/" + mViewModel.iYear;

            mMonth_ = String.valueOf(mViewModel.iMonth);
            Log.i(TAG, "onResume: " + "mMonth_ is: " + mMonth_);

            mYear_ = String.valueOf(mViewModel.iYear);
            Log.i(TAG, "onResume: " + "mYear_ is: " + mYear_);
        }



        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(mDateOnCreate);
        String onCreateDate = cal2.get(Calendar.DAY_OF_MONTH) + "/" + (cal2.get(Calendar.MONTH) + 1) + "/" + cal2.get(Calendar.YEAR);

        if (mOnResumeDate.equals(onCreateDate)) {
            Log.i(TAG, "onResume: " + mOnResumeDate + " == " + onCreateDate);

            String sDayOfMonth = String.valueOf(mDay);
            mDayOfMonth.setText(sDayOfMonth);

            String month = mMonth_;
            String year = mYear_;

            if (mViewModel.mCurrentDate == null){
                mViewModel.mCurrentDate = sDayOfMonth + "/" + month + "/" + year;
                Log.i(TAG, "onResume: " + "mViewModel.mCurrentDate is null: " + mViewModel.mCurrentDate);
            }
            Log.i(TAG, "onResume: " + "mViewModel.mCurrentDate is not null: " + mViewModel.mCurrentDate);

            shiftDutyCheck();
//            dateFormatter();
            if (mViewModel.iDay != 0) {
                mDayOfMonth.setText("" + mViewModel.iDay);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE");
                Format formatter = new SimpleDateFormat("MMMM");

                mMonth.setText("" + formatter.format(mDateOnResume));
                mWeekday.setText("" + simpleDateFormat.format(mDateOnResume));
            } else {
                dateFormatter();
            }


            mViewModel.mDaysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
            shiftWorkingDays();

        } else {
            Log.i(TAG, "onResume: " + mOnResumeDate + " != " + onCreateDate);

            shiftDutyCheck();
//            dateFormatter();

            if (mViewModel.iDay != 0) {
                mDayOfMonth.setText("" + mViewModel.iDay);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE");
                Format formatter = new SimpleDateFormat("MMMM");

                mMonth.setText("" + formatter.format(mDateOnResume));
                mWeekday.setText("" + simpleDateFormat.format(mDateOnResume));
            } else {
                dateFormatter();
            }

            mViewModel.mDaysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
            shiftWorkingDays();
        }
    }

    private void shiftDutyCheck() {
        try {
            Date date1 = dates.parse(mViewModel.mCurrentDate);
            Date date2 = dates.parse(mViewModel.mFinalDate);
            long difference = date1.getTime() - date2.getTime();
            long differenceDates = difference / (24 * 60 * 60 * 1000);

            int b = (int) differenceDates % SHIFT_CYCLE_DAYS;
            if (b == 8) {
                mShiftView.setText("First Morning Duty");
            } else if (b == 0) {
                mShiftView.setText("Second Morning Duty");
            } else if (b == 1) {
                mShiftView.setText("Third Morning Duty");
            } else if (b == 2) {
                mShiftView.setText("First Night Duty");
            } else if (b == 3) {
                mShiftView.setText("Second Night Duty");
            } else if (b == 4) {
                mShiftView.setText("Third Night Duty");
            } else if (b == 5) {
                mShiftView.setText("First Off Duty");
            } else if (b == 6) {
                mShiftView.setText("Second Off Duty");
            } else if (b == 7) {
                mShiftView.setText("Third Off Duty");
            }
        } catch (Exception exception) {
            Toast.makeText(this, "Unable to find difference", Toast.LENGTH_SHORT).show();
        }
    }

    public void shiftWorkingDays() {
        mViewModel.mCount = 0;

        for (int i = 1; i <= mViewModel.mDaysInMonth; i++) {
            //Log.i(TAG, "Count Down Is " + i);

            String sDayOfMonth = String.valueOf(i);
            String sMonth = String.valueOf(mViewModel.iMonth);
            String sYear = String.valueOf(mViewModel.iYear);
            //Log.i(TAG, "DayOfMonth Count is " + sDayOfMonth);

            String currentDate = sDayOfMonth + "/" + (sMonth) + "/" + sYear;
            Log.i(TAG, "Current Date Count is " + currentDate);

            try {
                Date date1 = dates.parse(currentDate);
                Log.i(TAG, "Formatted Date Count is " + date1);

                Date date2 = dates.parse(mViewModel.mFinalDate);
                long difference = date1.getTime() - date2.getTime();
                //Log.i(TAG, "Difference of Date Count is " + difference);

                long differenceDates = difference / (24 * 60 * 60 * 1000);
                Log.i(TAG, "Difference of Date Count is " + differenceDates);

                int b = (int) differenceDates % SHIFT_CYCLE_DAYS;
                if (b == 8 || b == 0 || b == 1 || b == 2 || b == 3 || b == 4) {
                    mViewModel.mCount++;
                    Log.i(TAG, "Testing Count is " + mViewModel.mCount);
                }

            } catch (Exception exception) {
                Toast.makeText(this, "Unable to find difference", Toast.LENGTH_SHORT).show();
            }

        }
        mShiftDays.setText("" + mViewModel.mCount);
        mShiftDays.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBackPressed() {

        long time = System.currentTimeMillis();
        Log.i(TAG, "onBackPressed: " + time);


        if (time - mBackPressed > 2500){
            mBackPressed = time;
            Toast.makeText(this, "Press Back again to exit", Toast.LENGTH_SHORT).show();
        } else  {
            moveTaskToBack(true);
        }
    }

}
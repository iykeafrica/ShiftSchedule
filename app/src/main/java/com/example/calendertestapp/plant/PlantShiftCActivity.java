package com.example.calendertestapp.plant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

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

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class PlantShiftCActivity extends AppCompatActivity {

    private static final String TAG = "PlantShiftCActivity";
    public static final int SHIFT_CYCLE_DAYS = 9;
    private TextView mShiftView, mWeekday, mDayOfMonth, mMonth, mShiftDays;
    private Date mNow, mDateOnCreate, mDateOnResume;

    private SimpleDateFormat dates = new SimpleDateFormat("dd/MM/yyyy");
    private CalendarView mCalendarView;
    private Calendar mCalendar;

    private ShiftScheduleViewModel mViewModel;
    private long  mBackPressed = 0;

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
                mViewModel.iYear = year;
                mViewModel.iMonth = (month + 1);


                mViewModel.mCurrentDate = dayOfMonth + "/" + (month + 1) + "/" + year;

                mDateOnCreate = new Date(year, month, dayOfMonth);
                mNow = new Date(year, month, (dayOfMonth - 1));

                mDayOfMonth.setText("" + dayOfMonth);
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
        cal.setTime(mDateOnResume);

        int day = cal.get(Calendar.DAY_OF_MONTH);
        mViewModel.iMonth = cal.get(Calendar.MONTH) + 1;
        mViewModel.iYear = cal.get(Calendar.YEAR);

        String onResumeDate = day + "/" + mViewModel.iMonth + "/" + mViewModel.iYear;

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(mDateOnCreate);
        String onCreateDate = cal2.get(Calendar.DAY_OF_MONTH) + "/" + (cal2.get(Calendar.MONTH) + 1) + "/" + cal2.get(Calendar.YEAR);

        if (onResumeDate.equals(onCreateDate)) {
            Log.i(TAG, "onResume: " + onResumeDate + " == " + onCreateDate);

            String sDayOfMonth = String.valueOf(day);
            mDayOfMonth.setText(sDayOfMonth);

            String month = String.valueOf(mViewModel.iMonth);
            String year = String.valueOf(mViewModel.iYear);
            mViewModel.mCurrentDate = sDayOfMonth + "/" + month + "/" + year;

            shiftDutyCheck();
            dateFormatter();

            mViewModel.mDaysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
            shiftWorkingDays();

        } else {
            Log.i(TAG, "onResume: " + onResumeDate + " != " + onCreateDate);
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
                mShiftView.setText("First Off Duty");
            } else if (b == 0) {
                mShiftView.setText("Second Off Duty");
            } else if (b == 1) {
                mShiftView.setText("Third Off Duty");
            } else if (b == 2) {
                mShiftView.setText("First Morning Duty");
            } else if (b == 3) {
                mShiftView.setText("Second Morning Duty");
            } else if (b == 4) {
                mShiftView.setText("Third Morning Duty");
            } else if (b == 5) {
                mShiftView.setText("First Night Duty");
            } else if (b == 6) {
                mShiftView.setText("Second Night Duty");
            } else if (b == 7) {
                mShiftView.setText("Third Night Duty");
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
                if (b == 5 || b == 6 || b == 7 || b == 2 || b == 3 || b == 4) {
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
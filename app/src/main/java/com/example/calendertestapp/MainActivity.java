package com.example.calendertestapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity {
    TextView mShiftView, mWeekday, mDayOfMonth, mMonth, mShiftDays;
    CalendarView mCalendarView;
    private Date mNow;
    private String mCurrentDate;
    private static final String mFinalDate = "02/01/1970";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNow = new Date();

        mMonth = findViewById(R.id.month_view);
        mShiftDays = findViewById(R.id.shift_days);
        mWeekday = findViewById(R.id.weekday_view);
        mDayOfMonth = findViewById(R.id.day_of_month_view);
        mShiftView = findViewById(R.id.shift_view);
        mCalendarView = findViewById(R.id.calendar_view);


        mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                mCurrentDate = dayOfMonth + "/" + (month + 1) + "/" + year;

                mNow = new Date(year, month, (dayOfMonth - 1));
                dateFormatter();

                mDayOfMonth.setText("" + dayOfMonth + "");
                shiftDutyCheck();

                //calculateDaysInMonth(year, month + 1);
                calDaysInMonth(year, month, dayOfMonth);
            }
        });

    }

    private void dateFormatter() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE");
        Format formatter = new SimpleDateFormat("MMMM");

        mMonth.setText("" + formatter.format(mNow));
        mWeekday.setText("" + simpleDateFormat.format(mNow));
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onResume() {
        super.onResume();
//        LocalDate today = LocalDate.now();
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
//        mDateView.setText("" + today.format(formatter));
        dateFormatter();

        Calendar c = Calendar.getInstance();
        c.setTime(mNow);

        int dayOfMonth = c.get(Calendar.DAY_OF_MONTH);
        String sDayOfMonth = String.valueOf(dayOfMonth);
        int month = c.get(Calendar.MONTH) + 1;
        String sMonth = String.valueOf(month);
        int year = c.get(Calendar.YEAR);
        String sYear = String.valueOf(year);
        mDayOfMonth.setText(sDayOfMonth);

        mCurrentDate = sDayOfMonth + "/" + sMonth + "/" + sYear;
        shiftDutyCheck();
    }

    private void shiftDutyCheck() {
        try {
            SimpleDateFormat dates = new SimpleDateFormat("dd/MM/yyyy");
            Date date1 = dates.parse(mCurrentDate);
            Date date2 = dates.parse(mFinalDate);
            long difference = date1.getTime() - date2.getTime();
            long differenceDates = difference / (24 * 60 * 60 * 1000);

            int a = (int) differenceDates;
            int b = a % 9;
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
            Toast.makeText(MainActivity.this, "Unable to find difference", Toast.LENGTH_SHORT).show();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void calculateDaysInMonth(int yearToCal, int monthToCal) {
        //Java 8 and above
        YearMonth yearMonthObject = YearMonth.of(yearToCal, monthToCal);
        int daysInMonth = yearMonthObject.lengthOfMonth();
        mShiftDays.setText("" + daysInMonth);
    }

    public void calDaysInMonth(int yearToCal, int monthToCal, int daysToCal) {
        //Java 7 and below
        // Create a calendar object and set year and month
        Calendar calendar = new GregorianCalendar(yearToCal, monthToCal, daysToCal);

        // Get the number of days in that month
        int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        mShiftDays.setText("" + daysInMonth);
    }

    public void calDaysInMonthShift(int yearToCal, int monthToCal, int daysOfMonthToCal) {
        //Java 7 and below
        // Create a calendar object and set year and month
        Calendar calendar = new GregorianCalendar(yearToCal, monthToCal, daysOfMonthToCal);

        // Get the number of days in that month
        int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
//        mShiftDays.setText("" + daysInMonth);
        int dIM = (((31 - daysInMonth) + 1) % 9);
        for (int i = daysInMonth; i <= 1; i--){

        }

    }



}

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
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    TextView mShiftView, mWeekday, mDayOfMonth, mMonth, mShiftDays;
    CalendarView mCalendarView;
    private Date mNow;
    private String mCurrentDate;
    private static final String  mFinalDate = "02/01/1970";


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
        mShiftView.setVisibility(View.GONE);
        mCalendarView = findViewById(R.id.calendar_view);


        mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                mCurrentDate = dayOfMonth + "/" + (month + 1) + "/" + year;

                mNow = new Date(year, month, (dayOfMonth - 1));
                dateFormatter();

                mDayOfMonth.setText("" + dayOfMonth);
                shiftDutyCheck();
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
                mShiftView.setVisibility(View.VISIBLE);
            } else if (b == 0) {
                mShiftView.setText("Second Morning Duty");
                mShiftView.setVisibility(View.VISIBLE);
            } else if (b == 1) {
                mShiftView.setText("Third Morning Duty");
                mShiftView.setVisibility(View.VISIBLE);
            } else if (b == 2) {
                mShiftView.setText("First Night Duty");
                mShiftView.setVisibility(View.VISIBLE);
            } else if (b == 3) {
                mShiftView.setText("Second Night Duty");
                mShiftView.setVisibility(View.VISIBLE);
            } else if (b == 4) {
                mShiftView.setText("Third Night Duty");
                mShiftView.setVisibility(View.VISIBLE);
            } else if (b == 5) {
                mShiftView.setText("First Off Duty");
                mShiftView.setVisibility(View.VISIBLE);
            } else if (b == 6) {
                mShiftView.setText("Second Off Duty");
                mShiftView.setVisibility(View.VISIBLE);
            } else if (b == 7) {
                mShiftView.setText("Third Off Duty");
                mShiftView.setVisibility(View.VISIBLE);
            }
        } catch (Exception exception) {
            Toast.makeText(MainActivity.this, "Unable to find difference", Toast.LENGTH_SHORT).show();
        }

    }
}

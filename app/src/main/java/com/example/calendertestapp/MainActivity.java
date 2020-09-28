package com.example.calendertestapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private TextView mShiftView, mWeekday, mDayOfMonth, mMonth, mShiftDays;
    private Date mNow, mOther;
    private LocalDate mLocalDate;
    private String mCurrentDate;
    private static final String mFinalDate = "02/01/1970";
    private int iYear;
    private int iMonth;
    private int mDaysInMonth;
    private SimpleDateFormat dates = new SimpleDateFormat("dd/MM/yyyy");
    private CalendarView mCalendarView;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNow = new Date();
        mOther = new Date();
        mLocalDate = LocalDate.now();

        mMonth = findViewById(R.id.month_view);
        mShiftDays = findViewById(R.id.shift_days);
        mShiftDays.setVisibility(View.GONE);
        mWeekday = findViewById(R.id.weekday_view);
        mDayOfMonth = findViewById(R.id.day_of_month_view);
        mShiftView = findViewById(R.id.shift_view);
        mCalendarView = findViewById(R.id.calendar_view);

        calenderDateClick();

    }

    private void calenderDateClick() {
        mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                iYear = year;
                iMonth = (month + 1);

                mCurrentDate = dayOfMonth + "/" + (month + 1) + "/" + year;

                mNow = new Date(year, month, (dayOfMonth - 1));
                mLocalDate = LocalDate.of(year, month + 1, dayOfMonth);
                mOther = new Date(year, month, dayOfMonth);

                mDayOfMonth.setText("" + dayOfMonth);
                dateFormatter();
                shiftDutyCheck();

                Calendar calendar = new GregorianCalendar(year, month, dayOfMonth);
                mDaysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
                shiftWorkingDays();
            }
        });

    }

    private void dateFormatter() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE");
        Format formatter = new SimpleDateFormat("MMMM");

        mMonth.setText("" + formatter.format(mOther));
        mWeekday.setText("" + simpleDateFormat.format(mNow));

//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
//        mDateView.setText("" + today.format(formatter));
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onResume() {
        super.onResume();
        LocalDate today = LocalDate.now();
        iMonth = today.getMonthValue();
        iYear = today.getYear();

        ZonedDateTime zdtToday = today.atStartOfDay(ZoneId.systemDefault());
        ZonedDateTime zdtLocalDateTime = mLocalDate.atStartOfDay(ZoneId.systemDefault());

        if (zdtToday.equals(zdtLocalDateTime)){
            Log.i(TAG, "onResume: " + zdtToday + " == " + zdtLocalDateTime);

            String sDayOfMonth = String.valueOf(today.getDayOfMonth());
            mDayOfMonth.setText(sDayOfMonth);

            String month = String.valueOf(iMonth);
            String year = String.valueOf(iYear);
            mCurrentDate = sDayOfMonth + "/" + month + "/" + year;

            shiftDutyCheck();
            dateFormatter();

            Calendar cal = Calendar.getInstance();
            mDaysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
            shiftWorkingDays();

        } else {
            Log.i(TAG, "onResume: " + zdtToday + " != " + zdtLocalDateTime);
        }
    }

    private void shiftDutyCheck() {
        try {
            Date date1 = dates.parse(mCurrentDate);
            Date date2 = dates.parse(mFinalDate);
            long difference = date1.getTime() - date2.getTime();
            long differenceDates = difference / (24 * 60 * 60 * 1000);

            int b = (int) differenceDates % 9;
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

    public void shiftWorkingDays() {
        int count = 0;

        for (int i = 1; i <= mDaysInMonth; i++) {
            //Log.i(TAG, "Count Down Is " + i);

            String sDayOfMonth = String.valueOf(i);
            String sMonth = String.valueOf(iMonth);
            String sYear = String.valueOf(iYear);
            //Log.i(TAG, "DayOfMonth Count is " + sDayOfMonth);

            String currentDate = sDayOfMonth + "/" + (sMonth) + "/" + sYear;
            Log.i(TAG, "Current Date Count is " + currentDate);

            try {
                Date date1 = dates.parse(currentDate);
                Log.i(TAG, "Formatted Date Count is " + date1);

                Date date2 = dates.parse(mFinalDate);
                long difference = date1.getTime() - date2.getTime();
                //Log.i(TAG, "Difference of Date Count is " + difference);

                long differenceDates = difference / (24 * 60 * 60 * 1000);
                Log.i(TAG, "Difference of Date Count is " + differenceDates);

                int b = (int) differenceDates % 9;
                if (b == 8 || b == 0 || b == 1 || b == 2 || b == 3 || b == 4) {
                    count++;
                    Log.i(TAG, "Testing Count is " + count);
                }

            } catch (Exception exception) {
                Toast.makeText(MainActivity.this, "Unable to find difference", Toast.LENGTH_SHORT).show();
            }

        }
        mShiftDays.setText("" + count);
        mShiftDays.setVisibility(View.VISIBLE);
    }

}



//
//    @RequiresApi(api = Build.VERSION_CODES.O)
//    public void calculateDaysInMonth(int yearToCal, int monthToCal) {
//        //Java 8 and above
//        YearMonth yearMonthObject = YearMonth.of(yearToCal, monthToCal);
//        int daysInMonth = yearMonthObject.lengthOfMonth();
//        mShiftDays.setText("" + daysInMonth);
//    }
//
//    public void calDaysInMonth(int yearToCal, int monthToCal, int daysToCal) {
//        //Java 7 and below
//        // Create a calendar object and set year and month
//        Calendar calendar = new GregorianCalendar(yearToCal, monthToCal, daysToCal);
//
//        // Get the number of days in that month
//        int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
//        mShiftDays.setText("" + daysInMonth);
//    }


//    ZoneId defaultZoneId = ZoneId.systemDefault();
//    Date date = Date.from(today.atStartOfDay(defaultZoneId).toInstant());
//
//    Instant instant = mNow.toInstant();
//    ZonedDateTime zdt = instant.atZone(ZoneId.systemDefault());
//    LocalDate date1 = zdt.toLocalDate();

//    LocalDateTime localDateToday = today.atStartOfDay();
//    LocalDateTime localDateNow = LocalDateTime.ofInstant(Instant.from(mNow.toInstant().atZone(defaultZoneId)), ZoneId.systemDefault());

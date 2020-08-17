package com.example.calendertestapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    TextView mShiftView, mWeekday, mDayOfMonth, mMonth, mShiftDays;
    CalendarView mCalendarView;
    private Date mNow, mOther;
    private String mCurrentDate;
    private static final String mFinalDate = "02/01/1970";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNow = new Date();
        mOther = new Date();

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
                mOther = new Date(year, month, dayOfMonth);
                
                mDayOfMonth.setText("" + dayOfMonth);
                dateFormatter();
                shiftDutyCheck();

                shiftWorkingDays(year, month, dayOfMonth);
            }
        });

    }

    private void dateFormatter() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE");
        Format formatter = new SimpleDateFormat("MMMM");

        mMonth.setText("" + formatter.format(mOther));
        mWeekday.setText("" + simpleDateFormat.format(mNow));
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onResume() {
        super.onResume();
//        LocalDate today = LocalDate.now();
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
//        mDateView.setText("" + today.format(formatter));
        Calendar cal = Calendar.getInstance();
        cal.setTime(mNow);

        String sDayOfMonth = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
        String sMonth = String.valueOf(cal.get(Calendar.MONTH) + 1);
        String sYear = String.valueOf(cal.get(Calendar.YEAR));
        mDayOfMonth.setText(sDayOfMonth);

        mCurrentDate = sDayOfMonth + "/" + sMonth + "/" + sYear;
        shiftDutyCheck();

        dateFormatter();


        int daysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
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

    public void shiftWorkingDays(int yearToCal, int monthToCal, int daysOfMonthToCal) {
        Calendar calendar = new GregorianCalendar(yearToCal, monthToCal, daysOfMonthToCal);
        int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        int count = 0;

        for (int i = 1; i <= daysInMonth; i++) {
            //Log.i(TAG, "Count Down Is " + i);

            String sDayOfMonth = String.valueOf(i);
            String sMonth = String.valueOf(monthToCal + 1);
            String sYear = String.valueOf(yearToCal);
            //Log.i(TAG, "DayOfMonth Count is " + sDayOfMonth);

            String currentDate = sDayOfMonth + "/" + (sMonth) + "/" + sYear;
            Log.i(TAG, "Current Date Count is " + currentDate);

            try {
                SimpleDateFormat dates = new SimpleDateFormat("dd/MM/yyyy");
                Date date1 = dates.parse(currentDate);
                Log.i(TAG, "Formatted Date Count is " + date1);

                Date date2 = dates.parse(mFinalDate);
                long difference = date1.getTime() - date2.getTime();
                //Log.i(TAG, "Difference of Date Count is " + difference);

                long differenceDates = difference / (24 * 60 * 60 * 1000);
                Log.i(TAG, "Difference of Date Count is " + differenceDates);

                int a = (int) differenceDates;
                int b = a % 9;
                if (b == 8 || b == 0 || b == 1 || b == 2 || b == 3 || b == 4) {
                    count++;
                    Log.i(TAG, "Testing Count is " + count);
                }

            } catch (Exception exception) {
                Toast.makeText(MainActivity.this, "Unable to find difference", Toast.LENGTH_SHORT).show();
            }

        }
        mShiftDays.setText("" + count);
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


}

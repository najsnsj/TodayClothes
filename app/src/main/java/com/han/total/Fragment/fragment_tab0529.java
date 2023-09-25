package com.han.total.Fragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.TextView;

import com.han.total.R;



public class fragment_tab0529 extends AppCompatActivity {


    CalendarView calendar;
    TextView tv_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_tab0529);



        calendar = findViewById(R.id.calendar_dh);
        tv_date = findViewById(R.id.tv_date_dh);

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
                tv_date.setText(year + "년 " + (month + 1) + "월 " + day + "일 선택");
            }
        });//추가-최동혁
    }
}
package com.example.majorproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.majorproject.adapter.ReportDateAdapter;
import com.example.majorproject.classes.Clas;
import com.example.majorproject.classes.DatePicker;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReportActivity extends AppCompatActivity {
    private TextView subjectView;
    private RecyclerView datesRecyclerView;
    List<DatePicker> dates;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);


        subjectView=findViewById(R.id.report_activity_subject_name_textView);
        datesRecyclerView=findViewById(R.id.dates);

        ReportDateAdapter reportDateAdapter2=new ReportDateAdapter(dates,this);
//        reportDateAdapter2.reportActivity_context=getApplicationContext();

        Intent screen=getIntent();
        String subject= screen.getStringExtra("subject");
        String class_id= screen.getStringExtra("class_id");

        subjectView.setText(subject);

        Database g=Database.Database(this);
        this.dates= new ArrayList<DatePicker>();
        Cursor t= g.select_attendance_date(class_id);

        try {
            if(t.moveToFirst()){
                do{
                    String date = t.getString(0);
                    String id = t.getString(1);
                    String classId = t.getString(2);
                    DatePicker obj = new DatePicker(date,id,classId);
                    dates.add(obj);
                }
                while(t.moveToNext());
            }
        }
        catch (Exception e){
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }

        ReportDateAdapter reportDateAdapter = new ReportDateAdapter(dates,this);
//        reportDateAdapter.takeAttendance_context=this;
        datesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        datesRecyclerView.addItemDecoration(new DividerItemDecoration(datesRecyclerView.getContext(), DividerItemDecoration.VERTICAL));
        datesRecyclerView.setAdapter(reportDateAdapter);

    }
}
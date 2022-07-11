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

import java.util.ArrayList;
import java.util.List;

public class ReportActivity extends AppCompatActivity {
    private TextView subjectView;
    private RecyclerView datesRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        subjectView=findViewById(R.id.report_activity_subject_name_textView);
        datesRecyclerView=findViewById(R.id.dates);

        Intent screen=getIntent();
        String subject= screen.getStringExtra("subject");
        String class_id= screen.getStringExtra("class_id");

        subjectView.setText(subject);

        Database g=Database.Database(this);
        List<String> dates = new ArrayList<String>();
        Cursor t= g.select_attendance_date(class_id);

        try {
            if(t.moveToFirst()){
                do{
                    String date = t.getString(0);
                    dates.add(date);
                }
                while(t.moveToNext());
            }
        }
        catch (Exception e){
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }

        ReportDateAdapter reportDateAdapter = new ReportDateAdapter(dates);
//        reportDateAdapter.takeAttendance_context=this;
        datesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        datesRecyclerView.addItemDecoration(new DividerItemDecoration(datesRecyclerView.getContext(), DividerItemDecoration.VERTICAL));
        datesRecyclerView.setAdapter(reportDateAdapter);

    }
}
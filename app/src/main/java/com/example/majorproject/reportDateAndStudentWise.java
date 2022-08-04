package com.example.majorproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;


import com.example.majorproject.adapter.Date_Wise_Report;
import com.example.majorproject.classes.Student;

import java.util.ArrayList;
import java.util.List;

public class reportDateAndStudentWise extends AppCompatActivity {
    private Button studentBtn,dateBtn;
    private RecyclerView recyclerView;
    private List<Student>data;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_date_and_student_wise);
        Intent screen=getIntent();
        String attendance_master_id = screen.getStringExtra("attendance_master_id");
        String class_id = screen.getStringExtra("class_id");
//        studentBtn=findViewById(R.id.studentWise);
//        dateBtn=findViewById(R.id.dateWise);
        recyclerView=findViewById(R.id.recyclerViewStudentAndDateWise);
        data=new ArrayList<>();

        Database g=Database.Database(this);
//        this.stds = new ArrayList<Student>();
        Cursor t= g.existingAttendanceRecord(attendance_master_id,class_id);

        try {
            if(t.moveToFirst()){
                do{
                    String student_id = t.getString(0);
                    String student_name = t.getString(1);
                    String student_roll_no = t.getString(2);
                    String count = t.getString(3);
                    Student obj = new Student(student_id,student_name ,student_roll_no,count);
                    this.data.add(obj);
                }
                while(t.moveToNext());
            }
        }
        catch (Exception e){
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }

//        data.add(new Student("1","joy","20","29"));
//        data.add(new Student("1","joy","20","29"));

        Date_Wise_Report date_wise_report = new Date_Wise_Report(data);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(date_wise_report);
//        Toast.makeText(this, ""+class_id+attendance_master_id, Toast.LENGTH_SHORT).show();

    }
}
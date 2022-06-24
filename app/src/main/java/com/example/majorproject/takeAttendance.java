package com.example.majorproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.majorproject.adapter.Atten_Class_Adapter;
import com.example.majorproject.adapter.Atten_Student_Adapter;
import com.example.majorproject.adapter.ClassAdapter;
import com.example.majorproject.classes.Clas;
import com.example.majorproject.classes.Student;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class takeAttendance extends AppCompatActivity {
    private List<Student> stds;
    private RecyclerView recyclerView;
    private TextView view_sub_name,view_date;
    private CalendarView _date;
    private String strDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_attendance2);
        recyclerView=findViewById(R.id.studentRecyclerView);
        view_sub_name=findViewById(R.id.view_sub_name);
        view_date=findViewById(R.id.view_date);


        Bundle bundle = getIntent().getExtras();
        String id = bundle.getString("id",null);
        String sub_name=bundle.getString("sub_name",null);
        view_sub_name.setText(sub_name);

//      Dialog box code
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dilogBox=LayoutInflater.from(this).inflate(R.layout.dialogbox_attendance,viewGroup,false);
//      View dilogBox = LayoutInflater.from(getBaseContext()).inflate( R.layout.dialogbox_add_class,container,false);
        builder.setView(dilogBox);
        AlertDialog action = builder.create();
        action.show();
        dilogBox.findViewById(R.id.click).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _date=dilogBox.findViewById(R.id.calendarView);
                strDate="dsfdsfsfd"+"16/7/2022";
                _date.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                 @Override
                    public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
                        //date=day+"/"+(month+1)+"/"+year;

                    }
                });
               // view_date.setText(date);
                Toast.makeText(takeAttendance.this,"hhh"+ strDate, Toast.LENGTH_SHORT).show();
                action.cancel();

            }
        });


//        stds.add(new Student("0","Joydeep","14"));
        Database g=new Database(this);
        this.stds = new ArrayList<Student>();
        Cursor t= g.select_student(id);
        try {
            if(t.moveToFirst()){
                do{
                    String student_id = t.getString(0);
                    String student_name = t.getString(2);
                    String student_roll_no = t.getString(3);

                    Student obj = new Student(student_id,student_name ,student_roll_no);
                    this.stds.add(obj);
                }
                while(t.moveToNext());
            }
        }
        catch (Exception e){
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }

        Atten_Student_Adapter atten_student_adapter = new Atten_Student_Adapter(stds);
        atten_student_adapter.takeAttendance_context=this;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(atten_student_adapter);

    }
}
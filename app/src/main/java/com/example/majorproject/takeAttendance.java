package com.example.majorproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.majorproject.Fragment.Attend;
import com.example.majorproject.Fragment.Class;
import com.example.majorproject.adapter.Atten_Class_Adapter;
import com.example.majorproject.adapter.Atten_Student_Adapter;
import com.example.majorproject.adapter.ClassAdapter;
import com.example.majorproject.classes.Clas;
import com.example.majorproject.classes.Student;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class takeAttendance extends AppCompatActivity {
    private List<Student> stds;
    private RecyclerView recyclerView;
    private TextView view_sub_name,view_date,present_student;
    private ImageView save;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_attendance2);
        recyclerView=findViewById(R.id.studentRecyclerView);
        view_sub_name=findViewById(R.id.view_sub_name);
        view_date=findViewById(R.id.view_date);
        present_student=findViewById(R.id.presentTotalStudent);
        save=findViewById(R.id.save);
        Intent screen=getIntent();
        String date = screen.getStringExtra("date");
        String subject= screen.getStringExtra("subject");
        String class_id= screen.getStringExtra("class_id");
        String attendance_master_id= screen.getStringExtra("attendanceMasterId");
//        Toast.makeText(this, "id: "+attendance_master_id, Toast.LENGTH_SHORT).show();

        view_sub_name.setText(subject);
        view_date.setText(date);


        Database g=Database.Database(this);
        this.stds = new ArrayList<Student>();
        Cursor t= g.existingAttendanceRecord(attendance_master_id,class_id);

        try {
            if(t.moveToFirst()){
                do{
                    String student_id = t.getString(0);
                    String student_name = t.getString(1);
                    String student_roll_no = t.getString(2);
                    String count = t.getString(3);
                    Student obj = new Student(student_id,student_name ,student_roll_no,count);
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

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                List<Student> filterdStudent=stds.stream().filter(new Predicate<Student>() {
                    @Override
                    public boolean test(Student student) {
                        return student.getCount().equals("1");
                    }
                }).collect(Collectors.toList());

//                Toast.makeText(takeAttendance.this, "okkkk", Toast.LENGTH_SHORT).show();
//                androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(getApplicationContext());
//                builder.setTitle("Save Change !");
//                builder.setMessage("Are you sure ?");
//                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        finish();
//                    }
//                });
//                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        dialogInterface.cancel();
//                    }
//                });
//                AlertDialog dialog = builder.create();
//                dialog.show();
                AlertDialog.Builder builder = new AlertDialog.Builder(takeAttendance.this);
                builder.setTitle("Save Change !");
                builder.setMessage("Total Present Student :"+filterdStudent.size());
                builder.setCancelable(false);
                builder.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                        {
                            finish();
                            Database db=Database.Database(takeAttendance.this);
                            db.delete_daily_attendance(attendance_master_id);
                            for (Student  obj:filterdStudent) {
                                db.insert_daily_attendance(attendance_master_id,obj.getStudentId());
                            }
//                            Attend attend = new Attend();
//                            getParentFragmentManager().beginTransaction().replace(R.id.fragmentContainer,attend).commit();

                        }
                });
                builder .setNegativeButton("No", new DialogInterface.OnClickListener() {
                     @Override
                     public void onClick(DialogInterface dialog,int which)
                     {
                         dialog.cancel();
                     }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

            }
        });
    }
    public void totalPresentStudent (){
        List<Student> filterdStudent=stds.stream().filter(new Predicate<Student>() {
            @Override
            public boolean test(Student student) {
                return student.getCount().equals("1");
            }
        }).collect(Collectors.toList());
        present_student.setText(""+filterdStudent.size());

    }
}
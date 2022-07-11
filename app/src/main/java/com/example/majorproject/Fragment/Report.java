package com.example.majorproject.Fragment;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.majorproject.Database;
import com.example.majorproject.MainActivity;
import com.example.majorproject.R;
import com.example.majorproject.CustomFragment;
import com.example.majorproject.ReportActivity;
import com.example.majorproject.classes.Clas;
import com.example.majorproject.takeAttendance;

import java.util.ArrayList;
import java.util.List;


public class Report extends CustomFragment {
    private MainActivity context;
    private Spinner class_name, course, subject;
    private Button click, export;
    String data_class_name,data_course_name,data_subject,data_class_id;


    public Report() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_report, container, false);
        class_name=view.findViewById(R.id.report_spinner_class_name);
        course=view.findViewById(R.id.report_spinner_course_name);
        subject=view.findViewById(R.id.report_spinner_subject_name);
        click=view.findViewById(R.id.report_click_btn);
        export=view.findViewById(R.id.exportExcel);

        Database g=Database.Database(context);
        Cursor t=g.spinner_class();
        List<String> spinerClassName;
        spinerClassName = new ArrayList<String>();
        spinerClassName.add("Select Class");


        try {
            if(t.moveToFirst()){
                do{
                    String  class_name= t.getString(0);
                    spinerClassName.add(class_name);
                }
                while(t.moveToNext());
            }
        }
        catch (Exception e){
            Toast.makeText(context, "Error class block", Toast.LENGTH_SHORT).show();
        }
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter(getContext(),
                android.R.layout.simple_spinner_item,spinerClassName);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        class_name.setAdapter(adapter);
        class_name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                data_class_name = class_name.getSelectedItem().toString();

//                Toast.makeText(getContext(), ""+class_name, Toast.LENGTH_SHORT).show();
                if(!data_class_name.equals("Select Class"))
                {
                    course.performClick();
                    List<String> spinerCourseName;
                    spinerCourseName = new ArrayList<String>();
                    spinerCourseName.add("Select Course");
                    //spinerCourseName=spinerCourseName.subList(0,0);
                    Cursor s=g.spinner_course(data_class_name);
                    try {
                        if(s.moveToFirst()){
                            do{
                                String  course_name= s.getString(0);
                                spinerCourseName.add(course_name);
                            }
                            while(s.moveToNext());
                        }
                    }
                    catch (Exception e){
                        Toast.makeText(context, "Error course block", Toast.LENGTH_SHORT).show();
                        Log.d("crs","err: "+e);
                    }
//                Toast.makeText(getContext(), ""+class_name, Toast.LENGTH_SHORT).show();
                    ArrayAdapter<CharSequence> adapter2 = new ArrayAdapter(getContext(),
                            android.R.layout.simple_spinner_item,spinerCourseName);
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    course.setAdapter(adapter2);
                    course.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            data_course_name = course.getSelectedItem().toString();

//                          Toast.makeText(getContext(), ""+course_name, Toast.LENGTH_SHORT).show();
                            if (!data_course_name.equals("Select Course")) {
                                subject.performClick();
                                List<String> spinerSubjectName;
                                spinerSubjectName = new ArrayList<String>();
                                spinerSubjectName.add("Select Subject");
                                Cursor r = g.spinner_sub(data_class_name, data_course_name);
                                try {
                                    if (r.moveToFirst()) {
                                        do {
                                            String subject_name = r.getString(0);
                                            spinerSubjectName.add(subject_name);
                                        }
                                        while (r.moveToNext());
                                    }
                                } catch (Exception e) {
                                    Toast.makeText(context, "Error Subject block", Toast.LENGTH_SHORT).show();
                                    Log.d("sub", "err: " + e);
                                }
//                            Log.d("data",""+spinerSubjectName);
                                ArrayAdapter<CharSequence> adapter3 = new ArrayAdapter(getContext(),
                                        android.R.layout.simple_spinner_item, spinerSubjectName);
                                adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                subject.setAdapter(adapter3);
                                subject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                        data_subject = subject.getSelectedItem().toString();
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {

                                    }
                                });
                            }

                            }

                            @Override
                            public void onNothingSelected (AdapterView < ? > adapterView){

                            }

                    });
                }
                else {
                    List<String> spinerCourseName;
                    spinerCourseName = new ArrayList<String>();
                    spinerCourseName.add("Select Course");
                    ArrayAdapter<CharSequence> adapter2 = new ArrayAdapter(getContext(),
                            android.R.layout.simple_spinner_item,spinerCourseName);
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    course.setAdapter(adapter2);
                    List<String> spinerSubjectName;
                    spinerSubjectName = new ArrayList<String>();
                    spinerSubjectName.add("Select Subject");
                    ArrayAdapter<CharSequence> adapter3 = new ArrayAdapter(getContext(),
                            android.R.layout.simple_spinner_item,spinerSubjectName);
                    adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    subject.setAdapter(adapter3);


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Log.d("msg", "onClick: "+data_class_name);
//                Log.d("msg", "onClick: "+data_course_name);
//                Log.d("msg", "onClick: "+data_subject);
                if(data_subject.equals("Select Subject"))
                {
                    Toast.makeText(getContext(), "Select all Fields", Toast.LENGTH_SHORT).show();
//                        Toast.makeText(getContext(), data_period, Toast.LENGTH_SHORT).show();
                }
                else {
                    Database k = Database.Database(context);
                    data_class_id = k.select_class_id(data_subject, data_course_name, data_class_name);

                    Intent screen = new Intent(getContext(), ReportActivity.class);
                    screen.putExtra("subject",data_subject);
                    screen.putExtra("class_id", data_class_id);

                    startActivity(screen);

                }
            }
        });
        export.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(data_subject.equals("Select Subject"))
                {
                    Toast.makeText(getContext(), "Select all Fields", Toast.LENGTH_SHORT).show();
//                        Toast.makeText(getContext(), data_period, Toast.LENGTH_SHORT).show();
                }
                else {

                }
            }
        });


        return view;
    }

    @Override
    public void setMainclass(MainActivity _context) {
        context = _context;
    }


}
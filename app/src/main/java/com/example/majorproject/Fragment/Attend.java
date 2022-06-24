package com.example.majorproject.Fragment;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.majorproject.CustomFragment;
import com.example.majorproject.Database;
import com.example.majorproject.MainActivity;
import com.example.majorproject.R;
import com.example.majorproject.classes.Clas;

import java.util.ArrayList;
import java.util.List;


public class Attend extends CustomFragment {
    private MainActivity context;


    public Attend() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_attend, container, false);
        Spinner spinner = view.findViewById(R.id.spinner_class);
        Spinner spinner2 = view.findViewById(R.id.spinner_course);
        Spinner spinner3 = view.findViewById(R.id.spinner_subject);

        Database g=Database.Database(context);
        Cursor t=g.spinner_class();
        List<String> spinerClassName;
        List<String> spinerSubjectName;
        spinerClassName = new ArrayList<String>();
        spinerSubjectName = new ArrayList<String>();
        spinerClassName.add("Select Class");
        spinerSubjectName.add("Select Subject");

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
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String class_name = spinner.getSelectedItem().toString();
//                Toast.makeText(getContext(), ""+class_name, Toast.LENGTH_SHORT).show();
                if(!class_name.equals("Select Class"))
                {
                    List<String> spinerCourseName;
                    spinerCourseName = new ArrayList<String>();
                    spinerCourseName.add("Select Course");
                    //spinerCourseName=spinerCourseName.subList(0,0);
                    Cursor s=g.spinner_course(class_name);
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
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter2);
                spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        String course_name = spinner2.getSelectedItem().toString();
//                        Toast.makeText(getContext(), ""+course_name, Toast.LENGTH_SHORT).show();
                        if(!class_name.equals("Select Course")){

                        }
                        else{

                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
                }
                else {
                    List<String> spinerCourseName;
                    spinerCourseName = new ArrayList<String>();
                    spinerCourseName.add("Select Course");
                    ArrayAdapter<CharSequence> adapter2 = new ArrayAdapter(getContext(),
                            android.R.layout.simple_spinner_item,spinerCourseName);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner2.setAdapter(adapter2);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        return view;
    }
    @Override
    public void setMainclass(MainActivity _context) {
        context = _context;
    }
}
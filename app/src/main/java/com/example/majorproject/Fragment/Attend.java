package com.example.majorproject.Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.majorproject.CustomFragment;
import com.example.majorproject.Database;
import com.example.majorproject.MainActivity;
import com.example.majorproject.R;
import com.example.majorproject.takeAttendance;

import android.app.DatePickerDialog;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.List;


public class Attend extends CustomFragment {
    private MainActivity context;
    private TextView attenDate;
    DatePickerDialog datePickerDialog;
    String date;

    TextView period;
    boolean[] selectedPeriod;
    ArrayList<Integer> periodList = new ArrayList<>();
    String[] periodArray = {"1st Period", "2nd Period", "3rd Period", "4th Period", "5th Period", "6th Period"};

//--------------------------Data for Send DataBase -------------------------------------------//
    String data_class="",data_course="",data_subject="",data_date="",data_period="",data_period_type="",data_topic="",data_class_id="",attendance_master_id="";



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
        Spinner period_type = view.findViewById(R.id.spinner_period_type);
        Button click = view.findViewById(R.id.atten_click);
        TextView topic=view.findViewById(R.id.atten_topic);


        attenDate=view.findViewById(R.id.atten_date);
        data_date= new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());

        attenDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text

                               date =dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                                if(monthOfYear<10) date =dayOfMonth + "/0" + (monthOfYear + 1) + "/" + year;
                                if(dayOfMonth<10) date ="0"+date;
                               attenDate.setText("Date : "+date);
                               data_date=date;
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        attenDate.setText("Date : "+data_date);

        //-----------------------------Select class------------------------------
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
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String class_name = spinner.getSelectedItem().toString();
                data_class=class_name;
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
                adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter2);
                spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            String course_name = spinner2.getSelectedItem().toString();
                            data_course=course_name;
//                          Toast.makeText(getContext(), ""+course_name, Toast.LENGTH_SHORT).show();

                            List<String> spinerSubjectName;
                            spinerSubjectName = new ArrayList<String>();
                            spinerSubjectName.add("Select Subject");
                            Cursor r=g.spinner_sub(class_name,course_name);
                            try {
                                if(r.moveToFirst()){
                                    do{
                                        String  subject_name= r.getString(0);
                                        spinerSubjectName.add(subject_name);
                                    }
                                    while(r.moveToNext());
                                }
                            }
                            catch (Exception e){
                                Toast.makeText(context, "Error Subject block", Toast.LENGTH_SHORT).show();
                                Log.d("sub","err: "+e);
                            }
//                            Log.d("data",""+spinerSubjectName);
                            ArrayAdapter<CharSequence> adapter3 = new ArrayAdapter(getContext(),
                                    android.R.layout.simple_spinner_item,spinerSubjectName);
                            adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinner3.setAdapter(adapter3);
                            spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                    data_subject = spinner3.getSelectedItem().toString();
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {

                                }
                            });


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
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner2.setAdapter(adapter2);
                    List<String> spinerSubjectName;
                    spinerSubjectName = new ArrayList<String>();
                    spinerSubjectName.add("Select Subject");
                    ArrayAdapter<CharSequence> adapter3 = new ArrayAdapter(getContext(),
                            android.R.layout.simple_spinner_item,spinerSubjectName);
                    adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner3.setAdapter(adapter3);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        //    ---------------------------------------------- Multi Select Spinner Code (select period)-------------------------------------------  //
        period = view.findViewById(R.id.atten_Period);

        // initialize selected language array
        selectedPeriod = new boolean[periodArray.length];

        period.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Initialize alert dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                // set title
                builder.setTitle("Select Period");

                // set dialog non cancelable
                builder.setCancelable(false);

                builder.setMultiChoiceItems(periodArray, selectedPeriod, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                        // check condition
                        if (b) {
                            // when checkbox selected
                            // Add position  in lang list
                            periodList.add(i);
                            // Sort array list
                            Collections.sort(periodList);
                        } else {
                            // when checkbox unselected
                            // Remove position from langList
                            periodList.remove(Integer.valueOf(i));
                        }
                    }
                });

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Initialize string builder
                        StringBuilder stringBuilder = new StringBuilder();
                        // use for loop
                        for (int j = 0; j < periodList.size(); j++) {
                            // concat array value
                            stringBuilder.append(periodArray[periodList.get(j)]);
                            // check condition
                            if (j != periodList.size() - 1) {
                                // When j value  not equal
                                // to lang list size - 1
                                // add comma
                                stringBuilder.append(", ");
                            }
                        }
                        // set text on textView

                        data_period=stringBuilder.toString();
                        if (data_period.equals("")){period.setText("Select Period");}
                        else {period.setText(data_period);}
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // dismiss dialog
                        dialogInterface.dismiss();
                    }
                });
                builder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // use for loop
                        for (int j = 0; j < selectedPeriod.length; j++) {
                            // remove all selection
                            selectedPeriod[j] = false;
                            // clear language list
                            periodList.clear();
                            data_period="";
                            // clear text view value
                            period.setText("Select Period");
                        }
                    }
                });
                // show dialog
                builder.show();
            }
        });
        //    ---------------------------------------------- Period Type Select Spinner Code -------------------------------------------  //
        List<String> spinerPeriodType;
        spinerPeriodType = new ArrayList<String>();
        spinerPeriodType.add("Select Period Type");
        spinerPeriodType.add("Theory");
        spinerPeriodType.add("Practical");
        spinerPeriodType.add("Both");
        ArrayAdapter<CharSequence> adapterPeriodType = new ArrayAdapter(getContext(),
                android.R.layout.simple_spinner_item,spinerPeriodType);
        adapterPeriodType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        period_type.setAdapter(adapterPeriodType);
        period_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                data_period_type = period_type.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //---------------------------------------------------- Press click button -------------------------------------------------//
            click.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    data_topic=topic.getText().toString();

                    //--------------------class id fatch -------------------//

                    if(data_subject.equals("Select Subject") || data_period==null || data_period.equals("") || data_period_type.equals("Select Period Type"))
                    {
                        Toast.makeText(getContext(), "Select all Fields", Toast.LENGTH_SHORT).show();
//                        Toast.makeText(getContext(), data_period, Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Database k=Database.Database(context);
                        data_class_id=k.select_class_id(data_subject,data_course,data_class);

//                    Toast.makeText(getContext(), "" + data_class_id, Toast.LENGTH_SHORT).show();
//                    Toast.makeText(getContext(), data_class, Toast.LENGTH_SHORT).show();
//                    Toast.makeText(getContext(), data_course, Toast.LENGTH_SHORT).show();
//                    Toast.makeText(getContext(), data_subject, Toast.LENGTH_SHORT).show();
//                    Toast.makeText(getContext(), data_period_type, Toast.LENGTH_SHORT).show();
//                    Toast.makeText(getContext(), data_date, Toast.LENGTH_SHORT).show();
//                    Toast.makeText(getContext(), data_period, Toast.LENGTH_SHORT).show();
//                    Toast.makeText(getContext(), data_topic, Toast.LENGTH_SHORT).show();

                        Database db=Database.Database(context);
                        String select_count_attendance_master_id = db.select_count_attendance_master_id(data_date,data_period,data_class_id);
                        if(select_count_attendance_master_id.equals("0")) {
                            db.insert_attendance_master(data_date, data_period, data_period_type, data_class_id, data_topic);
                            attendance_master_id=db.select_attendance_master_id(data_date,data_period,data_class_id);
                        }
                        else{
                            attendance_master_id=db.select_attendance_master_id(data_date,data_period,data_class_id);
                            db.update_attendance_master(attendance_master_id,data_period_type,data_topic);
                            Toast.makeText(getContext(), "Data Already Exists", Toast.LENGTH_SHORT).show();
//                            Log.d("booo",""+a);

                        }


                        Intent screen = new Intent(getContext(), takeAttendance.class);
                        screen.putExtra("date",data_date);
                        screen.putExtra("subject",data_subject);
                        screen.putExtra("class_id", data_class_id);
                        screen.putExtra("attendanceMasterId",attendance_master_id);
                        startActivity(screen);

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
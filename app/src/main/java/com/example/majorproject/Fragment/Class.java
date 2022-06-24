package com.example.majorproject.Fragment;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.majorproject.Database;
import com.example.majorproject.MainActivity;
import com.example.majorproject.R;
import com.example.majorproject.CustomFragment;
import com.example.majorproject.adapter.ClassAdapter;
import com.example.majorproject.classes.Clas;

import java.util.ArrayList;
import java.util.List;


public class Class extends CustomFragment {
    private EditText subject,course,clas;
    private Button addBtn;
    private MainActivity context;
    private ViewGroup viewGroup;
    private RecyclerView recyclerView;
    private List<Clas>_clas;
    public Class() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }
    public void onClick(View view){

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        ViewGroup viewGroup= this.viewGroup;
        View dilogBox = LayoutInflater.from(viewGroup.getContext()).inflate( R.layout.dialogbox_add_class,viewGroup,false);


        builder.setView(dilogBox);
        AlertDialog show = builder.create();
        show.show();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_class, container, false);
        recyclerView=view.findViewById(R.id.classRecyclerView);
        classArray();



        ClassAdapter classAdapter=new ClassAdapter(_clas);
        classAdapter.cls = this;
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(classAdapter);


        view.findViewById(R.id.addclassbtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                View dilogBox = LayoutInflater.from(view.getContext()).inflate( R.layout.dialogbox_add_class,container,false);
                subject=dilogBox.findViewById(R.id.classSubject);
                course=dilogBox.findViewById(R.id.classCourse);
                clas=dilogBox.findViewById(R.id.classClas);
                addBtn=dilogBox.findViewById(R.id.addBtn);
                builder.setView(dilogBox);
                AlertDialog action = builder.create();
                action.show();

                addBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String subject1 = subject.getText().toString();
                        String course1 = course.getText().toString();
                        String clas1 = clas.getText().toString();
                        if (subject1.equals("") || course1.equals("") || clas1.equals("")){
                            Toast.makeText(context, "Enter all the Fields ", Toast.LENGTH_SHORT).show();
                        }
                        else {

//                            Toast.makeText(context, ""+subject1, Toast.LENGTH_SHORT).show();
                            Database g=Database.Database(context);
                            SQLiteDatabase db=g.getWritableDatabase();
                            Boolean i = g.insert_class(subject1, course1, clas1);
                            if (i == true) {
                                Toast.makeText(context, "Successfull", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, "Not Successfull", Toast.LENGTH_SHORT).show();
                            }
                            classArray();
                            classAdapter.localDataSet =_clas;
                            classAdapter.notifyDataSetChanged();
                            action.cancel();

                        }
                    }
                });

            }
        });
        return view;

    }

    public void setNewFragment(String classId,String classNmae){
        StudentDetail studentDetail = new StudentDetail();
        studentDetail.classId = classId;
        studentDetail.className=classNmae;
        getParentFragmentManager().beginTransaction().replace(R.id.fragmentContainer,studentDetail).commit();
    }
    public void classArray(){
        Database g=Database.Database(context);
        _clas = new ArrayList<Clas>();
        Cursor t= g.select_class();
        try {
            if(t.moveToFirst()){
                do{
                    String class_id = t.getString(0);
                    String sub_name = t.getString(1);
                    String course_name = t.getString(2);
                    String  class_name= t.getString(3);
                    Clas obj = new Clas(class_id,sub_name ,course_name, class_name);
                    _clas.add(obj);
                }
                while(t.moveToNext());
            }
        }
        catch (Exception e){
            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
        }

    }
    @Override
    public void setMainclass(MainActivity _context) {
        context = _context;
    }
}
package com.example.majorproject.Fragment;

import android.database.Cursor;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.majorproject.Database;
import com.example.majorproject.MainActivity;
import com.example.majorproject.R;
import com.example.majorproject.CustomFragment;
import com.example.majorproject.classes.Clas;

import java.util.ArrayList;
import java.util.List;


public class Report extends CustomFragment {
    private MainActivity context;


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
        // Inflate the layout for this fragment
        Database g=Database.Database(context);
        Cursor t=g.spinner_class();
        List<Clas> spinerClassName;
        spinerClassName = new ArrayList<Clas>();

     try {
            if(t.moveToFirst()){
                do{
                   String  class_name= t.getString(0);
                    Clas obj = new Clas(class_name);
                    spinerClassName.add(obj);
                }
                while(t.moveToNext());
            }
            Log.d("dbMsg",""+spinerClassName.get(1).getClass_name());
         Log.d("dbMsg",""+spinerClassName.size());
        }
        catch (Exception e){
            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
        }
        return inflater.inflate(R.layout.fragment_report, container, false);
    }

    @Override
    public void setMainclass(MainActivity _context) {
        context = _context;
    }


}
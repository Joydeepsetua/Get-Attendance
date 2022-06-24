package com.example.majorproject.Fragment;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.majorproject.CustomFragment;
import com.example.majorproject.Database;
import com.example.majorproject.MainActivity;
import com.example.majorproject.R;
import com.example.majorproject.classes.Clas;

import java.util.ArrayList;
import java.util.List;


public class Attendance extends CustomFragment {
    private MainActivity context;


    public Attendance() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_attendance, container, false);
    }

    @Override
    public void setMainclass(MainActivity _context) {
        context = _context;
    }


}
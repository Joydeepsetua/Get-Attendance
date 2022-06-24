package com.example.majorproject.Fragment;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.majorproject.MainActivity;
import com.example.majorproject.R;
import com.example.majorproject.CustomFragment;


public class Export extends CustomFragment {


    private MainActivity context;

    public Export() {
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
        return inflater.inflate(R.layout.fragment_export, container, false);
    }

    @Override
    public void setMainclass(MainActivity _context) {
        context = _context;
    }


}
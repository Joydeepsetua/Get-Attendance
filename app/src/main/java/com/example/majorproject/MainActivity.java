package com.example.majorproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.majorproject.Fragment.Attend;
import com.example.majorproject.Fragment.Attendance;
import com.example.majorproject.Fragment.Class;
import com.example.majorproject.Fragment.Export;
import com.example.majorproject.Fragment.Report;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadFragments(new Attend());
        bottomNavigationView=findViewById(R.id.navBar);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                CustomFragment fragment =null;
                switch (item.getItemId()){
                    case R.id.attendance_menu_item:
                        fragment= new Attend();
                        break;
                    case R.id.class_menu_item:
                        fragment= new Class();
                        break;
                    case R.id.report_menu_item:
                        fragment= new  Report();
                        break;
                    case R.id.export_menu_item:
                        fragment= new Export();
                        break;
                }
                return loadFragments(fragment);
            }
        });

    }
    public  boolean loadFragments(CustomFragment fragment)
    {
        if(fragment!=null)
        {
            fragment.setMainclass(this);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainer,fragment)
                    .commit();
        }
        return true;
    }
    }

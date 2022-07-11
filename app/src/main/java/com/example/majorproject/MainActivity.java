package com.example.majorproject;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.majorproject.Fragment.Attend;
import com.example.majorproject.Fragment.Attendance;
import com.example.majorproject.Fragment.Class;
import com.example.majorproject.Fragment.Export;
import com.example.majorproject.Fragment.Report;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private int PermitionRequestCode=20;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadFragments(new Attend());
        bottomNavigationView=findViewById(R.id.navBar);
        requestPermition();

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
    private void requestPermition (){
        ActivityCompat.requestPermissions(this,new String[]{WRITE_EXTERNAL_STORAGE,READ_EXTERNAL_STORAGE},PermitionRequestCode);
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if(requestCode == 20){
//            if(grantResults.length > 0){
//                boolean write = grantResults[0] == PackageManager.PERMISSION_GRANTED;
//                boolean read = grantResults[1]  == PackageManager.PERMISSION_GRANTED;
//                if(write && read){
//                    Toast.makeText(this, "Permission done", Toast.LENGTH_SHORT).show();
//                }else{
//                    Toast.makeText(this, "Permion denied", Toast.LENGTH_SHORT).show();
//                }
//            }
//        }
//    }
    }

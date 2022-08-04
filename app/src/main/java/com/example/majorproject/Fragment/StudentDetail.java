package com.example.majorproject.Fragment;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.os.storage.StorageManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.majorproject.Database;
import com.example.majorproject.MainActivity;
import com.example.majorproject.R;
import com.example.majorproject.StudentTouchHelper;
import com.example.majorproject.adapter.StudentAdapter;
import com.example.majorproject.classes.Student;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class StudentDetail extends Fragment {

    public String classId,className;
    private ImageButton backButton;
    private ImageButton addImageBtn;
    private EditText _studentName,_studentRollNo;
    private TextView viewClassName;
    private Button importCSV;
    private Button _studentAddBtn;
    public Context context;
    private  MainActivity context1;
    private RecyclerView studentList;
    public List<Student>students;
    public static final int requstcode = 1;
    private  static  final int PermitionRequestCode=20;
    private List<List<String>> data = new ArrayList<>();
    StudentAdapter studentAdapter;


    public StudentDetail( ) {
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
        View view = inflater.inflate(R.layout.fragment_student_detail, container, false);
        addImageBtn=view.findViewById(R.id.addImageBtn);
        studentList=view.findViewById(R.id.studentList);
        backButton=view.findViewById(R.id.backIcon);
        viewClassName=view.findViewById(R.id.viewClassName);

        viewClassName.setText(className);

        context=container.getContext();
        StudentTouchHelper.containerView=container;
        studentArray();
        studentAdapter=new StudentAdapter(students);
        studentAdapter.db = new Database(context);
        studentList.setLayoutManager(new LinearLayoutManager(view.getContext()));
        studentList.setAdapter(studentAdapter);
        StudentTouchHelper studentTouchHelper = new StudentTouchHelper(studentAdapter);
        studentTouchHelper.studentDetail = this;
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(studentTouchHelper);
        itemTouchHelper.attachToRecyclerView(studentList);


        addImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                View dilogBox = LayoutInflater.from(context).inflate( R.layout.dialogbox_add_student,container,false);
                _studentName=dilogBox.findViewById(R.id.studentName);
                _studentRollNo=dilogBox.findViewById(R.id.studentRollNo);
                importCSV=dilogBox.findViewById(R.id.importCSV);

                builder.setView(dilogBox);
                AlertDialog action = builder.create();
                action.show();
                importCSV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        requestPermition();
                        Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
                        intent.setType("*/*");
                        someActivityResultLauncher.launch(intent);
                        action.cancel();
                    }
                });


                dilogBox.findViewById(R.id.studentAddBtn).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String studentName = _studentName.getText().toString();
                        String studentRollNo=_studentRollNo.getText().toString();
//                        Toast.makeText(context, classId, Toast.LENGTH_SHORT).show();
                        if (studentName.equals("") || studentRollNo.equals("")){
                            Toast.makeText(context, "Enter all the Fields ", Toast.LENGTH_SHORT).show();
                        }
                        else {
                           // Toast.makeText(context, studentName+studentRollNo+" "+classId, Toast.LENGTH_SHORT).show();
                            Database g =Database.Database(context);
                            SQLiteDatabase db=g.getWritableDatabase();
                            Boolean i = g.insert_student(classId,studentName,studentRollNo);
                            if (i == true) {
                                Toast.makeText(context, "Successfull", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, "Roll no already Exist", Toast.LENGTH_SHORT).show();
                            }
                            studentArray();
                            studentAdapter.localDataSet = students;
                            studentAdapter.notifyDataSetChanged();
                            action.cancel();

                        }

                    }
                });

            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(context, "okk", Toast.LENGTH_SHORT).show();
                Class clas = new Class();
                getParentFragmentManager().beginTransaction().replace(R.id.fragmentContainer,clas).commit();
            }
        });

        return view;
    }
    public void studentArray(){
        Database g=Database.Database(context);
        this.students = new ArrayList<Student>();
        Cursor t= g.select_student(classId);
        try {
            if(t.moveToFirst()){
                do{
                    String student_id = t.getString(0);
                    String student_name = t.getString(2);
                    String student_roll_no = t.getString(3);

                    Student obj = new Student(student_id,student_name ,student_roll_no);
                    this.students.add(obj);
                }
                while(t.moveToNext());
            }
        }
        catch (Exception e){
            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
        }
    }
    private void requestPermition (){
//        Log.e("TAG", "requestPermition: yes");
        ActivityCompat.requestPermissions(getActivity(),new String[]{WRITE_EXTERNAL_STORAGE,READ_EXTERNAL_STORAGE},PermitionRequestCode);
    }



    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
        new ActivityResultContracts.StartActivityForResult(),
        new ActivityResultCallback<ActivityResult>() {
            private ArrayList<Object> data;

            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    // There are no request codes
                    Intent d = result.getData();
//                    Log.d("GPD", "onActivityResult: "+d.getData().getPath());

                    String path = getInternalStorageDirectoryPath(getContext()) +"/"+d.getData().getPath().split(":")[1];
//                    Log.d("GPD", "onActivityResult: "+path);
                    try {
                        this.data= new ArrayList<>();
                        FileReader file = new FileReader(path);
                        try {
                            Scanner myReader = new Scanner(file);
                            while(myReader.hasNextLine()){
                                String []temp = myReader.nextLine().split(",");
                                String data_roll_no=temp[0];
                                String data_name=temp[1];
//                            Log.d("TAG", "Name: "+data_name+" , Roll: "+data_roll_no);
                                Database g =Database.Database(context);
                                SQLiteDatabase db=g.getWritableDatabase();
                                g.insert_student(classId,data_name,data_roll_no);

                            }
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    finally {
                        studentArray();
                        studentAdapter.localDataSet = students;
                        studentAdapter.notifyDataSetChanged();
                    }
                }
            }
        });

                
                

    public  String getInternalStorageDirectoryPath(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            StorageManager storageManager = (StorageManager) context.getSystemService(Context.STORAGE_SERVICE);
            return storageManager.getPrimaryStorageVolume().getDirectory().getAbsolutePath();
        } else {
            return Environment.getExternalStorageDirectory().getAbsolutePath();
        }
    }
}
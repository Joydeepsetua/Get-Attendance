package com.example.majorproject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.majorproject.Fragment.StudentDetail;
import com.example.majorproject.adapter.StudentAdapter;

public class StudentTouchHelper extends ItemTouchHelper.SimpleCallback {
    public static ViewGroup containerView;
    private EditText _studentName,_studentRollNo;
    StudentAdapter studentAdapter;
    public StudentDetail studentDetail;
    public StudentTouchHelper( StudentAdapter studentAdapter) {
        super(0, ItemTouchHelper.LEFT |ItemTouchHelper.RIGHT);
        this.studentAdapter = studentAdapter;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        final int position = viewHolder.getAdapterPosition();
        if (direction== ItemTouchHelper.RIGHT)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(studentDetail.getContext());
            builder.setTitle("Delete Student");
            builder.setMessage("Are you sure ?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    studentAdapter.deleteStudent(position);
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    studentAdapter.notifyItemChanged(position);
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();
        }else {
            androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(studentDetail.getContext());
            View dilogBox = LayoutInflater.from(studentDetail.getContext()).inflate( R.layout.dialogbox_add_student,containerView,false);
            builder.setView(dilogBox);
            androidx.appcompat.app.AlertDialog action = builder.create();
            _studentName=dilogBox.findViewById(R.id.studentName);
            _studentRollNo=dilogBox.findViewById(R.id.studentRollNo);
            String[] studentDetails =studentAdapter.fetchStudent(position);
            String id=studentDetails[0];
            String name=studentDetails[1];
            String rollNo=studentDetails[2];


            _studentRollNo.setText(rollNo);
            _studentName.setText(name);
            action.show();
            dilogBox.findViewById(R.id.studentAddBtn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String newName=_studentName.getText().toString();
                    String newRollNo=_studentRollNo.getText().toString();
//                    Toast.makeText(studentDetail.context, newName, Toast.LENGTH_SHORT).show();
                    studentAdapter.db.update_student(id,newName,newRollNo);
                    studentDetail.studentArray();
                    studentAdapter.localDataSet=studentDetail.students;
                    studentAdapter.notifyDataSetChanged();
                    action.cancel();

                }
            });
        }

    }
}

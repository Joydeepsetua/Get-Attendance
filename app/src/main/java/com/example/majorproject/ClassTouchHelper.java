package com.example.majorproject;

import static com.example.majorproject.StudentTouchHelper.containerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.majorproject.Fragment.Class;
import com.example.majorproject.adapter.ClassAdapter;
import com.example.majorproject.classes.Clas;

public class ClassTouchHelper extends ItemTouchHelper.SimpleCallback {
    public Context context;
    public Class aClass;
    EditText subject,course,className;

    ClassAdapter classAdapter;
        public ClassTouchHelper(ClassAdapter classAdapter) {
            super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
            this.classAdapter = classAdapter;

        }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        final int position = viewHolder.getAdapterPosition();
        if (direction== ItemTouchHelper.RIGHT) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Delete Class !");
            builder.setMessage("Are you sure ?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    classAdapter.deleteClass(position);
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    classAdapter.notifyItemChanged(position);
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();
        }
        else {

            androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(context);
            View dilogBox = LayoutInflater.from(context).inflate( R.layout.dialogbox_add_class,containerView,false);
            builder.setView(dilogBox);
            androidx.appcompat.app.AlertDialog action = builder.create();
            subject=dilogBox.findViewById(R.id.classSubject);
            course=dilogBox.findViewById(R.id.classCourse);
            className=dilogBox.findViewById(R.id.classClas);
            String[] classDetails = classAdapter.fetchClass(position);
            String class_id=classDetails[0];
            String data_subject=classDetails[1];
            String data_course=classDetails[2];
            String data_class_name=classDetails[3];


            subject.setText(data_subject);
            course.setText(data_course);
            className.setText(data_class_name);
            action.show();
            dilogBox.findViewById(R.id.addBtn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String newSubject=subject.getText().toString();
                    String newCourse=course.getText().toString();
                    String newClassName=className.getText().toString();
//                    Toast.makeText(context, newClassName+newCourse+newSubject, Toast.LENGTH_SHORT).show();

                    classAdapter.db.update_class(class_id, newSubject, newCourse, newClassName);
                    aClass.classArray();
                    classAdapter.localDataSet=aClass._clas;
                    classAdapter.notifyDataSetChanged();
                    action.cancel();

                }
            });
        }
    }
}

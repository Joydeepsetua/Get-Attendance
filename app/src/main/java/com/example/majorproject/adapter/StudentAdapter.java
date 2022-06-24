package com.example.majorproject.adapter;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.majorproject.Database;
import com.example.majorproject.Fragment.Class;
import com.example.majorproject.Fragment.StudentDetail;
import com.example.majorproject.R;
import com.example.majorproject.classes.Clas;
import com.example.majorproject.classes.Student;

import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.ViewHolder> {

    public List<Student> localDataSet;
    public StudentDetail stdn;
    public Database db;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView1, textView2;
        private final View view;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            textView1 = (TextView) view.findViewById(R.id.card_student_name);
            textView2 = (TextView) view.findViewById(R.id.card_student_roll_no);

            this.view=view;

        }

        public TextView getTextView1() {
            return textView1;
        }
        public TextView getTextView2() {
            return textView2;
        }

        public View getView() {
            return view;
        }
    }


    public StudentAdapter(List<Student> dataSet) {
        localDataSet = dataSet;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.layout_student, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, @SuppressLint("RecyclerView") final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element

        viewHolder.getTextView1().setText(localDataSet.get(position).getStudentName());
        viewHolder.getTextView2().setText(localDataSet.get(position).getStudentRollNo());


    }
    public void deleteStudent(int position){
        Student item = localDataSet.get(position);
        String id = item.getStudentId();
        db.delete_student(id);
        localDataSet.remove(position);
        notifyItemRemoved(position);
    }
    public String[] fetchStudent(int position){
        Student item = localDataSet.get(position);
        String id= item.getStudentId();
        String name= item.getStudentName();
        String rollNO= item.getStudentRollNo();
        return new String[]{id,name,rollNO};


    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.size();
    }

}

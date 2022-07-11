package com.example.majorproject.adapter;


import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.majorproject.R;
import com.example.majorproject.classes.Student;
import com.example.majorproject.takeAttendance;

import java.util.List;

public class Atten_Student_Adapter extends RecyclerView.Adapter<Atten_Student_Adapter.ViewHolder> {
    public List<Student> localDataSet;
    public takeAttendance takeAttendance_context;



    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView1,textView2;
        public CheckBox checkBox;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            textView1 = (TextView) view.findViewById(R.id.nameView);
            textView2 = (TextView) view.findViewById(R.id.rollNoView);
            checkBox = (view.findViewById(R.id.checkbox));
        }

        public TextView getTextView1() {
            return textView1;
        }
        public TextView getTextView2(){
            return textView2;
        }

    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used
     * by RecyclerView.
     */
    public Atten_Student_Adapter(List<Student> dataSet)
    {
        localDataSet = dataSet;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_student_attendance, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, @SuppressLint("RecyclerView") final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.getTextView1().setText(localDataSet.get(position).getStudentName());
        viewHolder.getTextView2().setText(localDataSet.get(position).getStudentRollNo());
        takeAttendance_context.totalPresentStudent();
        Boolean checked=true ;
        if(localDataSet.get(position).getCount().equals("0"))
        { checked =false;
            viewHolder.checkBox.setChecked(checked);}
        else
            viewHolder.checkBox.setChecked(checked);

        viewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean checked=true ;
                if(localDataSet.get(position).getCount().equals("0"))
                { checked =false;}
                if(checked) {
                    localDataSet.get(position).setCount("0");
                }
                else
                {localDataSet.get(position).setCount("1");}
//                 Log.d("sss",""+ localDataSet.get(position).toString());
                takeAttendance_context.totalPresentStudent();
            }
        });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}


package com.example.majorproject.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            textView1 = (TextView) view.findViewById(R.id.nameView);
            textView2 = (TextView) view.findViewById(R.id.rollNoView);
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
    public Atten_Student_Adapter(List<Student> dataSet) {
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
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.getTextView1().setText(localDataSet.get(position).getStudentName());
        viewHolder.getTextView2().setText(localDataSet.get(position).getStudentRollNo());

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}


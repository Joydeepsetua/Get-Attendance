package com.example.majorproject.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.majorproject.R;
import com.example.majorproject.classes.Student;

import java.util.List;

public class Date_Wise_Report extends RecyclerView.Adapter<Date_Wise_Report.ViewHolder>{


    private List<Student> localDataSet;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private  TextView name, rollno, total;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            name = (TextView) view.findViewById(R.id.report_name);
            rollno= (TextView) view.findViewById(R.id.report_rollno);
            total = (TextView) view.findViewById(R.id.report_total);
        }

        public TextView getName() {
            return name;
        }
        public TextView getRollno() {
            return rollno;
        }
        public TextView getTotal() {
            return total;
        }
    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used
     * by RecyclerView.
     */
    public Date_Wise_Report(List<Student> dataSet) {
        localDataSet = dataSet;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.layout_student_date_wise, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.getName().setText(localDataSet.get(position).getStudentName());
        viewHolder.getRollno().setText(localDataSet.get(position).getStudentRollNo());
        if(localDataSet.get(position).getCount().equals("1"))
        viewHolder.getTotal().setText("Present");
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}


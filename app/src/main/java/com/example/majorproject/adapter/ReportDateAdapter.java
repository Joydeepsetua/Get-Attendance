package com.example.majorproject.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.majorproject.Fragment.Attendance;
import com.example.majorproject.R;
import com.example.majorproject.ReportActivity;
import com.example.majorproject.classes.DatePicker;
import com.example.majorproject.reportDateAndStudentWise;
//import com.example.majorproject.reportDateAndStudentWise;

import java.util.List;


public class ReportDateAdapter extends RecyclerView.Adapter<ReportDateAdapter.ViewHolder> {

    public List<DatePicker> localDataSet;
    public Context reportActivity_context;


    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private final View view;


        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            textView = (TextView) view.findViewById(R.id.dateTextView);
            this.view=view;

        }

        public TextView getTextView() {
            return textView;
        }
        public View getView() {
            return view;
        }

    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used
     * by RecyclerView.
     */
    public ReportDateAdapter(List<DatePicker> dataSet,Context context) {
        localDataSet = dataSet;reportActivity_context=context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.layout_dates, viewGroup, false);
        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, @SuppressLint("RecyclerView") final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.getTextView().setText(localDataSet.get(position).getDate());
        String attendance_master_id=localDataSet.get(position).getId();
        String class_id = localDataSet.get(position).getClassId();
        viewHolder.getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent screen = new Intent(reportActivity_context, reportDateAndStudentWise.class);
                screen.putExtra("attendance_master_id",attendance_master_id);
                screen.putExtra("class_id",class_id);
                reportActivity_context.startActivity(screen);
            }
        });



    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}



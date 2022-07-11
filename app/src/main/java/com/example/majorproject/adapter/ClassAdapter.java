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
import com.example.majorproject.R;
import com.example.majorproject.classes.Clas;
import com.example.majorproject.classes.Student;

import java.util.List;

public class ClassAdapter extends RecyclerView.Adapter<ClassAdapter.ViewHolder> {

    public List<Clas> localDataSet;
    public Class cls;
    public Database db;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView1, textView2, textView3,textView4;
        private final View view;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            textView1 = (TextView) view.findViewById(R.id.card_subject_name);
            textView2 = (TextView) view.findViewById(R.id.card_course_name);
            textView3 = (TextView) view.findViewById(R.id.card_class_name);
            textView4 = (TextView) view.findViewById(R.id.card_total_student);
            this.view=view;
            
        }

        public TextView getTextView1() {
            return textView1;
        }
        public TextView getTextView2() {
            return textView2;
        }
        public TextView getTextView3() {
            return textView3;
        }
        public TextView getTextView4() {       return textView4;   }
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
    public ClassAdapter(List<Clas> dataSet) {
        localDataSet = dataSet;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.layout_class, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, @SuppressLint("RecyclerView") final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.getTextView3().setText(localDataSet.get(position).getClass_id());
        viewHolder.getTextView1().setText(localDataSet.get(position).getSub_name());
        viewHolder.getTextView2().setText(localDataSet.get(position).getCourse_name());
        viewHolder.getTextView3().setText(localDataSet.get(position).getClass_name());
        viewHolder.getTextView4().setText(localDataSet.get(position).getStudentCount());

        viewHolder.getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(view.getContext(), localDataSet.get(position).getSub_name(), Toast.LENGTH_SHORT).show();
                cls.setNewFragment(localDataSet.get(position).getClass_id(),localDataSet.get(position).getSub_name());

            }
        });

    }
    public void deleteClass(int position) {
        Clas item = localDataSet.get(position);
        String id = item.getClass_id();
//        Log.d("gg", "deleteClass: "+id);
        db.delete_class(id);
        localDataSet.remove(position);
        notifyItemRemoved(position);
    }
    public String[] fetchClass(int position){
        Clas item = localDataSet.get(position);
        String id= item.getClass_id();
        String subject= item.getSub_name();
        String course= item.getCourse_name();
        String class_name= item.getClass_name();
        return new String[]{id,subject,course,class_name};


    }
    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}


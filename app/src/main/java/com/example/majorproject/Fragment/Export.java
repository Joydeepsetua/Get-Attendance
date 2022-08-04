package com.example.majorproject.Fragment;

import android.database.Cursor;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.majorproject.Database;
import com.example.majorproject.MainActivity;
import com.example.majorproject.R;
import com.example.majorproject.CustomFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import in.ac.polydurg.generate_pdf.GeneratePdf;


public class Export extends CustomFragment {


    private MainActivity context;
    private Button teacher_report;

    public Export() {
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
        View view=inflater.inflate(R.layout.fragment_export, container, false);
        teacher_report=view.findViewById(R.id.teacher_report);
        teacher_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String htmlTemplate = "\n" +
                        "<html><head><meta content=\"text/html; charset=UTF-8\" http-equiv=\"content-type\"><style type=\"text/css\">ol{margin:0;padding:0}table td,table th{padding:10}.c0{margin-left:-72pt;padding-top:0pt;padding-bottom:0pt;line-height:1.15;orphans:2;widows:2;text-align:center;margin-right:-72pt;height:11pt}.c1{-webkit-text-decoration-skip:none;color:#000000;font-weight:700;text-decoration:underline;vertical-align:baseline;text-decoration-skip-ink:none;font-size:15pt;font-family:\"Times New Roman\";font-style:normal}.c2{margin-left:-72pt;padding-top:0pt;padding-bottom:0pt;line-height:1.15;orphans:2;widows:2;text-align:center;margin-right:-72pt}.c4{color:#000000;font-weight:400;text-decoration:none;vertical-align:baseline;font-size:13pt;font-family:\"Times New Roman\";font-style:normal}.c5{color:#000000;font-weight:400;text-decoration:none;vertical-align:baseline;font-size:11pt;font-family:\"Arial\";font-style:normal}.c3{background-color:#ffffff;max-width:451.4pt;padding:0pt 72pt 0pt 72pt}.title{padding-top:0pt;color:#000000;font-size:26pt;padding-bottom:3pt;font-family:\"Arial\";line-height:1.15;page-break-after:avoid;orphans:2;widows:2;text-align:left}.subtitle{padding-top:0pt;color:#666666;font-size:15pt;padding-bottom:16pt;font-family:\"Arial\";line-height:1.15;page-break-after:avoid;orphans:2;widows:2;text-align:left}li{color:#000000;font-size:11pt;font-family:\"Arial\"}p{margin:0;color:#000000;font-size:11pt;font-family:\"Arial\"}h1{padding-top:20pt;color:#000000;font-size:20pt;padding-bottom:6pt;font-family:\"Arial\";line-height:1.15;page-break-after:avoid;orphans:2;widows:2;text-align:left}h2{padding-top:18pt;color:#000000;font-size:16pt;padding-bottom:6pt;font-family:\"Arial\";line-height:1.15;page-break-after:avoid;orphans:2;widows:2;text-align:left}h3{padding-top:16pt;color:#434343;font-size:14pt;padding-bottom:4pt;font-family:\"Arial\";line-height:1.15;page-break-after:avoid;orphans:2;widows:2;text-align:left}h4{padding-top:14pt;color:#666666;font-size:12pt;padding-bottom:4pt;font-family:\"Arial\";line-height:1.15;page-break-after:avoid;orphans:2;widows:2;text-align:left}h5{padding-top:12pt;color:#666666;font-size:11pt;padding-bottom:4pt;font-family:\"Arial\";line-height:1.15;page-break-after:avoid;orphans:2;widows:2;text-align:left}h6{padding-top:12pt;color:#666666;font-size:11pt;padding-bottom:4pt;font-family:\"Arial\";line-height:1.15;page-break-after:avoid;font-style:italic;orphans:2;widows:2;text-align:left}</style></head><body class=\"c3 doc-content\"><p class=\"c0\"><span class=\"c5\"></span></p><p class=\"c2\"><span class=\"c1\">Teacher Report</span></p><p class=\"c0\"><span class=\"c1\"></span></p><p class=\"c2\"><span class=\"c4\">{data}</span></p></body></html>" ;
                String htmlTable = "<table border='1' cellspacing='0'>";
//                List<List<String>> teacher_report = new ArrayList<>();
                List<List<String>> report = new ArrayList<>();
                List<String> head = new ArrayList<>();
                head.add("SN");
                head.add("Date");
                head.add("Duration");
                head.add("Type");
                head.add("Topic");
                report.add(head);
                Database g=Database.Database(context);
                Cursor t= g.select_attendance_master();
                try {
                    if(t.moveToFirst()){
                        do{
//                                id integer primary key autoincrement , date varchar, duration varchar , period_type varchar, class_id varchar,topic varchar
//                                String id = t.getString(0);
//                                String date = t.getString(1);
//                                String duration = t.getString(2);
//                                String  period_type= t.getString(3);
//                                String  topic= t.getString(5);
                            List<String> temp =  new ArrayList<>();
                            temp.add(t.getString(0));
                            temp.add(t.getString(1));
                            temp.add(t.getString(2));
                            temp.add(t.getString(3));
                            temp.add(t.getString(5));
                            report.add(temp);
                        }
                        while(t.moveToNext());
                    }
                }
                catch (Exception e){
                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                }
//                report = generateAttendanceData();

                for (List<String> rep : report) {
                    htmlTable += "<tr>";
                    for (String ele : rep) {
                        htmlTable += "<td>" + ele + "</td>";
                    }
                    htmlTable += "</tr>";
                }
                htmlTable += "</table>";
                HashMap<String, String> keyValuePairHashMap = new HashMap<>();
                keyValuePairHashMap.put("data", htmlTable);
//                keyValuePairHashMap.put("data_class_name", data_class_name);
//                keyValuePairHashMap.put("data_course_name", data_course_name);
//                keyValuePairHashMap.put("data_subject", data_subject);
//                keyValuePairHashMap.put("fromDate", fromDate);
//                keyValuePairHashMap.put("toDate", toDate);
                String fileName = "Teacher Report";
                GeneratePdf generatePdf = new GeneratePdf(getContext(), fileName, htmlTemplate, keyValuePairHashMap);
            }
        });
        return view;
    }

    @Override
    public void setMainclass(MainActivity _context) {
        context = _context;
    }


}
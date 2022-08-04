package com.example.majorproject.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.majorproject.Database;
import com.example.majorproject.MainActivity;
import com.example.majorproject.R;
import com.example.majorproject.CustomFragment;
import com.example.majorproject.ReportActivity;
import com.example.majorproject.classes.Clas;
import com.example.majorproject.takeAttendance;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import in.ac.polydurg.generate_pdf.GeneratePdf;


public class Report extends CustomFragment {
    private MainActivity context;
    private Spinner class_name, course, subject;
    private Button click, export,exportPdf;
    String data_class_name="",data_course_name="",data_subject="",data_class_id,fromDate="", toDate="";




    public Report() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_report, container, false);
        class_name=view.findViewById(R.id.report_spinner_class_name);
        course=view.findViewById(R.id.report_spinner_course_name);
        subject=view.findViewById(R.id.report_spinner_subject_name);
        click=view.findViewById(R.id.report_click_btn);
        export=view.findViewById(R.id.exportExcel);
        exportPdf=view.findViewById(R.id.exportPDF);
        Database g=Database.Database(context);
        Cursor t=g.spinner_class();
        List<String> spinerClassName;
        spinerClassName = new ArrayList<String>();
        spinerClassName.add("Select Class");


        try {
            if(t.moveToFirst()){
                do{
                    String  class_name= t.getString(0);
                    spinerClassName.add(class_name);
                }
                while(t.moveToNext());
            }
        }
        catch (Exception e){
            Toast.makeText(context, "Error class block", Toast.LENGTH_SHORT).show();
        }
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter(getContext(),
                android.R.layout.simple_spinner_item,spinerClassName);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        class_name.setAdapter(adapter);
        class_name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                data_class_name = class_name.getSelectedItem().toString();

//                Toast.makeText(getContext(), ""+class_name, Toast.LENGTH_SHORT).show();
                if(!data_class_name.equals("Select Class"))
                {
                    course.performClick();
                    List<String> spinerCourseName;
                    spinerCourseName = new ArrayList<String>();
                    spinerCourseName.add("Select Course");
                    //spinerCourseName=spinerCourseName.subList(0,0);
                    Cursor s=g.spinner_course(data_class_name);
                    try {
                        if(s.moveToFirst()){
                            do{
                                String  course_name= s.getString(0);
                                spinerCourseName.add(course_name);
                            }
                            while(s.moveToNext());
                        }
                    }
                    catch (Exception e){
                        Toast.makeText(context, "Error course block", Toast.LENGTH_SHORT).show();
                        Log.d("crs","err: "+e);
                    }
//                Toast.makeText(getContext(), ""+class_name, Toast.LENGTH_SHORT).show();
                    ArrayAdapter<CharSequence> adapter2 = new ArrayAdapter(getContext(),
                            android.R.layout.simple_spinner_item,spinerCourseName);
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    course.setAdapter(adapter2);
                    course.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            data_course_name = course.getSelectedItem().toString();

//                          Toast.makeText(getContext(), ""+course_name, Toast.LENGTH_SHORT).show();
                            if (!data_course_name.equals("Select Course")) {
                                subject.performClick();
                                List<String> spinerSubjectName;
                                spinerSubjectName = new ArrayList<String>();
                                spinerSubjectName.add("Select Subject");
                                Cursor r = g.spinner_sub(data_class_name, data_course_name);
                                try {
                                    if (r.moveToFirst()) {
                                        do {
                                            String subject_name = r.getString(0);
                                            spinerSubjectName.add(subject_name);
                                        }
                                        while (r.moveToNext());
                                    }
                                } catch (Exception e) {
                                    Toast.makeText(context, "Error Subject block", Toast.LENGTH_SHORT).show();
//                                    Log.d("sub", "err: " + e);
                                }
//                            Log.d("data",""+spinerSubjectName);
                                ArrayAdapter<CharSequence> adapter3 = new ArrayAdapter(getContext(),
                                        android.R.layout.simple_spinner_item, spinerSubjectName);
                                adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                subject.setAdapter(adapter3);
                                subject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                        data_subject = subject.getSelectedItem().toString();
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {

                                    }
                                });
                            }

                            }

                            @Override
                            public void onNothingSelected (AdapterView < ? > adapterView){

                            }

                    });
                }
                else {
                    List<String> spinerCourseName;
                    spinerCourseName = new ArrayList<String>();
                    spinerCourseName.add("Select Course");
                    ArrayAdapter<CharSequence> adapter2 = new ArrayAdapter(getContext(),
                            android.R.layout.simple_spinner_item,spinerCourseName);
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    course.setAdapter(adapter2);
                    List<String> spinerSubjectName;
                    spinerSubjectName = new ArrayList<String>();
                    spinerSubjectName.add("Select Subject");
                    ArrayAdapter<CharSequence> adapter3 = new ArrayAdapter(getContext(),
                            android.R.layout.simple_spinner_item,spinerSubjectName);
                    adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    subject.setAdapter(adapter3);


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        exportPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(data_subject.equals("Select Subject")||data_subject.equals(""))
                {
                    Toast.makeText(getContext(), "Select all Fields", Toast.LENGTH_SHORT).show();
//                        Toast.makeText(getContext(), data_period, Toast.LENGTH_SHORT).show();
                }
                else {
                    String htmlTemplate = "\n" +
                            "<html><head><meta content=\"text/html; charset=UTF-8\" http-equiv=\"content-type\"><style type=\"text/css\">ol{margin:0;padding:0}table td,table th{padding:10}.c12{-webkit-text-decoration-skip:none;color:#000000;font-weight:700;text-decoration:underline;vertical-align:baseline;text-decoration-skip-ink:none;font-size:14pt;font-family:\"Georgia\";font-style:normal}.c7{background-color:#0f111a;color:#c3e88d;font-weight:400;text-decoration:none;vertical-align:baseline;font-size:14pt;font-family:\"Georgia\";font-style:normal}.c6{color:#000000;font-weight:400;text-decoration:none;vertical-align:baseline;font-size:16pt;font-family:\"Times New Roman\";font-style:normal}.c11{color:#000000;font-weight:400;text-decoration:none;vertical-align:baseline;font-size:12pt;font-family:\"Georgia\";font-style:normal}.c5{color:#000000;font-weight:400;text-decoration:none;vertical-align:baseline;font-size:14pt;font-family:\"Georgia\";font-style:normal}.c2{padding-top:0pt;padding-bottom:0pt;line-height:1.15;orphans:2;widows:2;text-align:left;height:11pt}.c0{padding-top:0pt;padding-bottom:0pt;line-height:1.15;orphans:2;widows:2;text-align:center;height:11pt}.c13{padding-top:0pt;padding-bottom:0pt;line-height:1.15;orphans:2;widows:2;text-align:left}.c4{padding-top:0pt;padding-bottom:0pt;line-height:1.15;orphans:2;widows:2;text-align:center}.c18{padding-top:0pt;padding-bottom:0pt;line-height:1.15;orphans:2;widows:2;text-align:justify}.c16{color:#000000;text-decoration:none;vertical-align:baseline;font-family:\"Arial\";font-style:normal}.c17{-webkit-text-decoration-skip:none;color:#1155cc;text-decoration:underline;text-decoration-skip-ink:none}.c3{background-color:#ffffff;max-width:504.2pt;padding:14.2pt 41.6pt 72pt 49.6pt}.c1{font-size:15pt;font-family:\"Times New Roman\";font-weight:700}.c15{font-size:12pt;font-family:\"Georgia\";font-weight:400}.c9{font-size:16pt;font-family:\"Times New Roman\";font-weight:400}.c8{font-size:15pt;font-weight:700}.c10{color:inherit;text-decoration:inherit}.c14{font-size:15pt}.title{padding-top:0pt;color:#000000;font-size:26pt;padding-bottom:3pt;font-family:\"Arial\";line-height:1.15;page-break-after:avoid;orphans:2;widows:2;text-align:left}.subtitle{padding-top:0pt;color:#666666;font-size:15pt;padding-bottom:16pt;font-family:\"Arial\";line-height:1.15;page-break-after:avoid;orphans:2;widows:2;text-align:left}li{color:#000000;font-size:11pt;font-family:\"Arial\"}p{margin:0;color:#000000;font-size:11pt;font-family:\"Arial\"}h1{padding-top:20pt;color:#000000;font-size:20pt;padding-bottom:6pt;font-family:\"Arial\";line-height:1.15;page-break-after:avoid;orphans:2;widows:2;text-align:left}h2{padding-top:18pt;color:#000000;font-size:16pt;padding-bottom:6pt;font-family:\"Arial\";line-height:1.15;page-break-after:avoid;orphans:2;widows:2;text-align:left}h3{padding-top:16pt;color:#434343;font-size:14pt;padding-bottom:4pt;font-family:\"Arial\";line-height:1.15;page-break-after:avoid;orphans:2;widows:2;text-align:left}h4{padding-top:14pt;color:#666666;font-size:12pt;padding-bottom:4pt;font-family:\"Arial\";line-height:1.15;page-break-after:avoid;orphans:2;widows:2;text-align:left}h5{padding-top:12pt;color:#666666;font-size:11pt;padding-bottom:4pt;font-family:\"Arial\";line-height:1.15;page-break-after:avoid;orphans:2;widows:2;text-align:left}h6{padding-top:12pt;color:#666666;font-size:11pt;padding-bottom:4pt;font-family:\"Arial\";line-height:1.15;page-break-after:avoid;font-style:italic;orphans:2;widows:2;text-align:left}</style></head><body class=\"c3 doc-content\"><p class=\"c0\"><span class=\"c12\"></span></p><p class=\"c4\"><span class=\"c12\">Daily Attendance Report</span></p><p class=\"c0\"><span class=\"c12\"></span></p><p class=\"c18\"><span class=\"c9\">Attendance Report from </span><span class=\"c1\">{fromDate}</span><span class=\"c9\">&nbsp;to </span><span class=\"c1\">{toDate}</span></p><p class=\"c13\"><span class=\"c14\">Course &nbsp;:</span><span class=\"c8\">{data_course_name} </span><span class=\"c14\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Semester : </span><span class=\"c16 c8\">{data_class_name}</span></p><p class=\"c13\"><span class=\"c14\">Subject : </span><span class=\"c8 c16\">{data_subject}</span></p><p class=\"c2\"><span class=\"c7\"></span></p><p class=\"c4\"><span class=\"c5\">{attendanceTable}</span></p><p class=\"c2\"><span class=\"c5\"></span></p><br><br><br><p class=\"c13\"><span class=\"c15\">**This report is generated by GetAttendace Android App. Developed by </span><span class=\"c15 c17\"><a class=\"c10\" href=\"https://www.google.com/url?q=https://www.instagram.com/joydeepsetua/&amp;sa=D&amp;source=editors&amp;ust=1657700788989446&amp;usg=AOvVaw3Ay6dl7MH8C25YMBAlDVzW\">Joydeep Setua</a></span><span class=\"c11\">**</span></p><p class=\"c2\"><span class=\"c5\"></span></p></body></html>";
                    String htmlTable = "<table border='1' cellspacing='0'>";
                    List<List<String>> report = new ArrayList<>();
                    report = generateAttendanceData();

                    for (List<String> rep : report) {
                        htmlTable += "<tr>";
                        for (String ele : rep) {
                            htmlTable += "<td>" + ele + "</td>";
                        }
                        htmlTable += "</tr>";
                    }
                    htmlTable += "</table>";
                    HashMap<String, String> keyValuePairHashMap = new HashMap<>();
                    keyValuePairHashMap.put("attendanceTable", htmlTable);
                    keyValuePairHashMap.put("data_class_name", data_class_name);
                    keyValuePairHashMap.put("data_course_name", data_course_name);
                    keyValuePairHashMap.put("data_subject", data_subject);
                    keyValuePairHashMap.put("fromDate", fromDate);
                    keyValuePairHashMap.put("toDate", toDate);
                    String fileName = data_subject + "_" + toDate;
                    GeneratePdf generatePdf = new GeneratePdf(getContext(), fileName, htmlTemplate, keyValuePairHashMap);
                }
            }
        });

        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Log.d("msg", "onClick: "+data_class_name);
//                Log.d("msg", "onClick: "+data_course_name);
//                Log.d("msg", "onClick: "+data_subject);
                if(data_subject.equals("Select Subject")||data_subject.equals(""))
                {
                    Toast.makeText(getContext(), "Select all Fields", Toast.LENGTH_SHORT).show();
//                        Toast.makeText(getContext(), data_period, Toast.LENGTH_SHORT).show();
                }
                else {
                    Database k = Database.Database(context);
                    data_class_id = k.select_class_id(data_subject, data_course_name, data_class_name);

                    Intent screen = new Intent(getContext(), ReportActivity.class);
                    screen.putExtra("subject",data_subject);
                    screen.putExtra("class_id", data_class_id);

                    startActivity(screen);

                }
            }
        });
        export.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(data_subject.equals("Select Subject")||data_subject.equals(""))
                {
                    Toast.makeText(getContext(), "Select all Fields", Toast.LENGTH_SHORT).show();
//                        Toast.makeText(getContext(), data_period, Toast.LENGTH_SHORT).show();
                }
                else {
                    int fileVerson=1;
                    String filename=data_subject+fileVerson+".csv";
                    fileVerson=fileVerson+1;
//                             String filename = "csvfile.csv";
                    List<List<String>> report = new ArrayList<>();
                    report=  generateAttendanceData();
//                    Log.d("123", "onClick: "+report);

                    File directoryDownload = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                    File logDir = new File (directoryDownload, "Get Attendance"); //Creates a new folder in DOWNLOAD directory
                    logDir.mkdirs();
                    File file = new File(logDir, filename);
                    FileOutputStream outputStream = null;
                    try {

                        outputStream = new FileOutputStream(file, true);
                        //outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
//                        for (int i = 0; i < report.size(); i++) {
////                            outputStream.write((getString(i) + ",").getBytes());
//                            for (int j;j<report)
//                            outputStream.write((report.get(i) + ",").getBytes());
//                            outputStream.write((report.get(i + 1) + ",").getBytes());
//                            outputStream.write((report.get(i + 2) + "\n").getBytes());
//
//                        }
                        for(List<String> rep: report){
                            for(String ele: rep){
                                outputStream.write((ele+",").getBytes());
                            }
                            outputStream.write(("\n").getBytes());
                        }
                        outputStream.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

//                    Log.d("TAG", "onClick: " + report.size());
                    AlertDialog.Builder builder= new AlertDialog.Builder(context);
                    builder.setTitle("Export Success !");
                    builder.setMessage("Open file Manager -> Internal Storage -> \nDownloads folder -> Get Attendance -> \nYour Export File");
                    builder.setPositiveButton(
                            "Ok",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    builder.show();
                }
            }
        });


        return view;
    }

    @Override
    public void setMainclass(MainActivity _context) {
        context = _context;
    }

    private List<List<String>> findElements(List<List<String>> sam,Integer[] indexs,String[] vals){
        List<List<String>> result = new ArrayList<>();
        for(List<String> s: sam){
            int count = 0;
            boolean flag = true;
            for(Integer index: indexs){
                if(!s.get(index).equals(vals[count])) {
                    flag = false;
                }
                if(s.get(index).equals("n")){
                    flag = true;
                }
                if(!flag) continue;
            }
            if(flag) result.add(s);
            count+=1;
        }
        return result;
    }

    private List<List<String>>  generateAttendanceData()
    {
        Database k = Database.Database(context);
        data_class_id = k.select_class_id(data_subject, data_course_name, data_class_name);

        Database db = Database.Database(getContext());

        Cursor creport = db.getReport(data_class_id);
        List<List<String>> report = new ArrayList<>();
        List<List<String>> data = new ArrayList<>();
        HashSet<String> Date = new HashSet<>();
        HashSet<String> stuid = new HashSet<>();


        if (creport.moveToNext()) {
            do {
                String name = creport.getString(0);
                String rollNo = creport.getString(1);
                String date = creport.getString(2);
                List<String> temp = new ArrayList<>();
                temp.add(name);
                temp.add(" "+rollNo);
                if(date == null) {
                    temp.add("n");
                }
                else {
                    temp.add(date);
                }
                Date.add(date);
                temp.add(creport.getString(3));
//                Log.d("123", "generateAttendanceData: "+creport.getString(3));
                stuid.add(creport.getString(3));
                data.add(temp);
            } while (creport.moveToNext());
        }
//        Log.d("123", "generateAttendanceData: "+data);
//        Log.d("123", "generateAttendanceData: "+Date);
        Date = new HashSet<> ((new ArrayList<>(Date).subList(1,Date.size())));
//        Log.d("123", "generateAttendanceData: "+Date);
        List<String> listOfDate = Date.stream().sorted().collect(Collectors.toList());
         fromDate=listOfDate.get(0);
         toDate=listOfDate.get(listOfDate.size()-1);
      //                   Date = new HashSet<String>(list);
//                    Log.e("TAG", "onClick: "+list );
        // colums with date
        List<String> temp = new ArrayList<>();
        temp.add("Name");
        temp.add("Roll Number");
        for (String d : listOfDate) temp.add(d);
        temp.add("Total");
        report.add(temp);

        // row with studentid
        Log.d("ansh studi", "generateAttendanceData: "+stuid);
        for (String id : stuid) {
            Log.d("ansh", "generateAttendanceData: "+findElements(data, new Integer[]{3}, new String[]{id}));
            List<String> lt = findElements(data, new Integer[]{3}, new String[]{id}).get(0);
            Log.d("ansh", "generateAttendanceData: "+lt);
            temp = new ArrayList<>();
            temp.add(lt.get(0));
            temp.add(lt.get(1));
            report.add(temp);
        }

        for (String da : listOfDate) {
            List<List<String>> elefind = findElements(data, new Integer[]{2}, new String[]{da});
            int count = 1;
//            int countTotalAttendance=0;
            for (String id : stuid) {
                if (findElements(elefind, new Integer[]{3}, new String[]{id}).size() == 1 && !findElements(elefind, new Integer[]{3}, new String[]{id}).get(0).get(2).equals("n")) {
                    report.get(count).add("P");
//                    countTotalAttendance=countTotalAttendance+1;
                } else {
                    report.get(count).add("A");
                }
                count += 1;
            }
//            report.get(count).add(""+countTotalAttendance);
        }
        for(List<String> rep : report.subList(1,report.size())){
            int count = 0;
            for(String ap: rep.subList(2,rep.size())){ if(ap.equals("P")) count+=1; }
            rep.add(""+count);
        }
        return report;
    }

}
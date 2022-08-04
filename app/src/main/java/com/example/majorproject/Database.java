package com.example.majorproject;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class Database extends SQLiteOpenHelper {
    private static final String dbname = "upasthiti.db";
    private static Database instance = null;

    public Database(@Nullable Context context) {
        super(context, dbname, null, 6);
//        Log.d("dbMsg", "database constructor");
    }


    public static Database Database(Context context) {
        if (instance == null) {
            instance = new Database(context);
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
//        Log.d("dbMsg", "database created");
        String query1 = "create table class (id integer primary key autoincrement , sub_name text , course_name text ,  class_name text)";
        sqLiteDatabase.execSQL(query1);
        String query2 = "create table student (id integer primary key autoincrement , class_id varchar, student_name text , student_roll_no text , unique (class_id,student_roll_no))";
        sqLiteDatabase.execSQL(query2);
        String query3 = "create table attendance_master (id integer primary key autoincrement , date varchar, duration varchar , period_type varchar, class_id varchar,topic varchar )";
        sqLiteDatabase.execSQL(query3);
        String query4= "create table daily_attendance (id integer primary key autoincrement , attendance_master_id varchar, student_id varchar)";
//        "select  from student,attendance_master,daily_attendance where student.id=daily_attendance.student_id and attendance_master.id=daily_attendance.attendance_master_id"
        sqLiteDatabase.execSQL(query4);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists class");
        sqLiteDatabase.execSQL("drop table if exists student");
        sqLiteDatabase.execSQL("drop table if exists attendance_master");
        sqLiteDatabase.execSQL("drop table if exists daily_attendance");

//        Log.d("dbMsg", "database upgraded");
        onCreate(sqLiteDatabase);

    }

    public boolean insert_class(String _sub_name, String _course_name, String _class_name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues c = new ContentValues();
        c.put("sub_name", _sub_name);
        c.put("course_name", _course_name);
        c.put("class_name", _class_name);

        long r = db.insert("class", null, c);
        if (r == -1)
            return false;
        else
            return true;
    }

    public Cursor select_class() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("select * from class", null);
    }

    public String select_class_id(String sub_name, String course_name, String class_name) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("select id from class where sub_name=\'"+sub_name+"\' and  course_name=\'"+course_name+"\' and class_name = \'"+class_name+"\'", null);
          if(c!=null)
        {
            c.moveToFirst();
            return c.getString(0);
                  }
        return  null;
    }
    //date varchar, duration varchar , period_type varchar, class_id varchar,topic varchar
    public String select_attendance_master_id(String date, String duration, String class_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        c=db.rawQuery("select id from attendance_master where date=\'"+date+"\' and  duration=\'"+duration+"\' and class_id = \'"+class_id+"\'", null);
        if(c!=null)
        {
            Log.d("jjjj","m:"+c);
            c.moveToFirst();
            String xyz=c.getString(0);
            Log.d("jjjj","msg:"+xyz);
            return xyz;
        }
        return  null;
    }
    public String select_count_attendance_master_id(String date, String duration, String class_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c=db.rawQuery("select count(id) from attendance_master where date=\'"+date+"\' and  duration=\'"+duration+"\' and class_id = \'"+class_id+"\'", null);
        if(c!=null)
        {

            c.moveToFirst();
            return c.getString(0);

        }
        return  null;
    }

    public Cursor spinner_class() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("select distinct class_name from class  order by class_name", null);
    }

    public Cursor spinner_course(String class_name) {
        SQLiteDatabase db = this.getReadableDatabase();
//        return db.rawQuery("select course_name from class", null);
        return db.rawQuery("select distinct course_name from class where class_name=\'" + class_name + "\'  order by course_name", null);
    }

    public Cursor spinner_sub(String class_name, String course_name) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("select distinct sub_name from class where class_name=\'" + class_name + "\' and course_name=\'" + course_name + "\' order by sub_name", null);
    }

    public Cursor countClassStudent() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("select  class.id ,sub_name ,course_name,class_name , count(student.id)  from class left join student on  class.id=student.class_id group by  sub_name ,course_name,class_name  order by class.id", null);
     //   return db.rawQuery("select  student.id ,student.name, student.roll, count(daily_attendance.student_id)  from student left join daily_attendance on student.id=daily_attendace.studentid
        //   where daily_attendance.attendance_master_id ='' and  student.class_id=''  group by  student.id ,student.name, student.roll ", null);
    }
    public Cursor existingAttendanceRecord(String attendance_master_id, String class_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql ="select  student.id ,student.student_name, student.student_roll_no, count(daily_attendance.student_id)  from student left join daily_attendance on student.id=daily_attendance.student_id " +
                "and daily_attendance.attendance_master_id =\'"+ attendance_master_id+"\' where  student.class_id=\'"+ class_id +"\'  group by  student.id ,student.student_name, student.student_roll_no ";

        return db.rawQuery(sql, null);
    }



    public boolean insert_student(String _class_id, String _student_name, String _student_roll_no) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues c = new ContentValues();
        c.put("class_id", _class_id);
        c.put("student_name", _student_name);
        c.put("student_roll_no", _student_roll_no);

        long r = db.insert("student", null, c);
        if (r == -1)
            return false;
        else
            return true;
    }

    public Cursor select_student(String classId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from student where class_id =" + classId + " order by student_roll_no asc;", null);
        return cursor;
    }

    public Boolean delete_student(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        long r = db.delete("student", "ID=?", new String[]{id});
        if (r == -1)
            return false;
        else
            return true;

    }
    public Boolean delete_class(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        long r = db.delete("class", "ID=?", new String[]{id});
        if (r == -1)
            return false;
        else
            return true;

    }


    public Boolean update_student(String id, String name, String rollNo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("student_name", name);
        values.put("student_roll_no", rollNo);
        long r = db.update("student", values, "ID=?", new String[]{id});
        if (r == -1)
            return false;
        else
            return true;
    }

    public Boolean update_class(String id, String subject, String course, String className) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("sub_name", subject);
        values.put("course_name", course);
        values.put("class_name", className);
        long r = db.update("class", values, "ID=?", new String[]{id});
        if (r == -1)
            return false;
        else
            return true;
    }

    public boolean insert_attendance_master(String date, String duration, String period_type, String class_id,String topic ) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues c = new ContentValues();
        c.put("date", date);
        c.put("duration", duration);
        c.put("period_type", period_type);
        c.put("class_id", class_id);
        c.put("topic", topic);

        long r = db.insert("attendance_master", null, c);
        if (r == -1)
            return false;
        else
            return true;
    }
    public Boolean update_attendance_master(String id, String period_type, String topic) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("period_type", period_type);
        values.put("topic", topic);
        long r = db.update("attendance_master", values, "ID=?", new String[]{id});
        if (r == -1)
            return false;
        else
            return true;
    }
    public void delete_daily_attendance(String attendance_master_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("daily_attendance","attendance_master_id=?",new String[]{attendance_master_id});

    }
    public void insert_daily_attendance(String attendance_master_id,String student_id ) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues c = new ContentValues();
        c.put("attendance_master_id", attendance_master_id);
        c.put("student_id", student_id);
               long r = db.insert("daily_attendance", null, c);
    }
//    table attendance_master (id integer primary key autoincrement , date varchar, duration varchar , period_type varchar, class_id varchar,topic varchar )";
public Cursor select_attendance_date(String classId) {
    SQLiteDatabase db = this.getReadableDatabase();
    Cursor cursor = db.rawQuery("select date, id , class_id from attendance_master where class_id =" + classId + " order by date asc;", null);
    return cursor;
}
    public Cursor getReport(String id){
        String sql="SELECT *\n" +
                "  FROM (\n" +
                "           SELECT student.student_name,\n" +
                "                  student.student_roll_no,\n" +
                "                  main.date,\n" +
                "                  student.id,\n"+
                "                  student.class_id\n" +
                "             FROM student\n" +
                "                  LEFT JOIN\n" +
                "                  (\n" +
                "                      SELECT student.id,\n" +
                "                             attendance_master.date\n" +
                "                        FROM student,\n" +
                "                             attendance_master,\n" +
                "                             daily_attendance\n" +
                "                       WHERE student.id = daily_attendance.student_id AND \n" +
                "                             attendance_master.id = daily_attendance.attendance_master_id\n" +
                "                       ORDER BY date\n" +
                "                  )\n" +
                "                  main ON student.id = main.id\n" +
                "       ) where class_id=" +
                id+
                "       order by date\n" +
                "       ;\n";

        Cursor report = getReadableDatabase().rawQuery(sql,null);
//        Cursor date = getReadableDatabase().rawQuery("SELECT DISTINCT date FROM (SELECT student.student_name,student.student_roll_no,attendance_master.date FROM student,attendance_master,daily_attendance WHERE student.id = daily_attendance.student_id AND attendance_master.id = daily_attendance.attendance_master_id ORDER BY date);",null);
//        Cursor student = getReadableDatabase().rawQuery("Select * from student;",null);
        return report;
    }
    public Cursor select_attendance_master() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from attendance_master;", null);
        return cursor;
    }

}
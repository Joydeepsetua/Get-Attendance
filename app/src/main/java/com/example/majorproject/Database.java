package com.example.majorproject;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class Database extends SQLiteOpenHelper {
    private static final String dbname="upasthiti.db";
    private static Database instance = null;
    public Database(@Nullable Context context) {
        super(context, dbname, null, 4);
        Log.d("dbMsg", "database constructor");
    }


    public static Database Database(Context context)
    {
        if (instance == null) {
            instance = new Database(context);
        }
        return instance;
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.d("dbMsg", "database created");
        String query1 = "create table class (id integer primary key autoincrement , sub_name text , course_name text ,  class_name text)";
        sqLiteDatabase.execSQL(query1);
        String query2 = "create table student (id integer primary key autoincrement , class_id varchar, student_name text , student_roll_no text )";
        sqLiteDatabase.execSQL(query2);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists class");
        sqLiteDatabase.execSQL("drop table if exists student");
         Log.d("dbMsg", "database upgraded");
        onCreate(sqLiteDatabase);

    }
    public boolean  insert_class(String _sub_name, String _course_name, String _class_name)
    {
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues c = new ContentValues();
        c.put("sub_name",_sub_name );
        c.put("course_name", _course_name);
        c.put("class_name", _class_name);

        long r=db.insert("class",null,c);
        if (r==-1)
            return false;
        else
            return true;
    }

    public Cursor select_class (){
        SQLiteDatabase db=this.getReadableDatabase();
        return db.rawQuery("select * from class", null);
    }
    public Cursor student_count(String class_id){
        SQLiteDatabase db=this.getReadableDatabase();
        return db.rawQuery("select count (student_name) from student where class_id="+class_id, null);
    }
    public Cursor spinner_class (){
        SQLiteDatabase db=this.getReadableDatabase();
        return db.rawQuery("select class_name from class", null);
    }
    public Cursor spinner_course (String class_name){
        SQLiteDatabase db=this.getReadableDatabase();
//        return db.rawQuery("select course_name from class", null);
        return db.rawQuery("select course_name from class where class_name=\'"+class_name+"\'", null);
    }
    public Cursor spinner_sub (String course_name){
        SQLiteDatabase db=this.getReadableDatabase();
        return db.rawQuery("select sub_name from class where class_name="+course_name, null);
    }


    public boolean  insert_student(String _class_id ,String _student_name,String _student_roll_no)
    {
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues c = new ContentValues();
        c.put("class_id", _class_id);
        c.put("student_name", _student_name);
        c.put("student_roll_no", _student_roll_no);

        long r=db.insert("student",null,c);
        if (r==-1)
            return false;
        else
            return true;
    }
    public Cursor select_student (String classId){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from student where class_id =" +classId+" order by student_roll_no asc;", null);
        return cursor;
    }
    public Boolean delete_student(String id){
        SQLiteDatabase db=this.getWritableDatabase();
        long r=db.delete("student","ID=?",new String[]{id});
        if (r==-1)
            return false;
        else
            return true;

    }
    public Boolean update_student(String id, String name , String rollNo){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values= new ContentValues();
        values.put("student_name",name);
        values.put("student_roll_no",rollNo);
        long r=db.update("student",values,"ID=?", new String[]{id});
        if (r==-1)
            return false;
        else
            return true;
    }
/*
    public Cursor getsub (){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from subject", null);
        return cursor;
    }

    public Cursor getAtten (String branch,String sem){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from student where branch= \""+branch+"\" and semester=\""+sem+"\" group by rollno", null);
        return cursor;
    }
    public boolean  inser_atten(String date2, String sub_name2, String rollno2, String name2)
    {
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues c = new ContentValues();

        c.put("date", date2);
        c.put("sub_name", sub_name2);
        c.put("roll_no", rollno2);
        c.put("name", name2);

        long r=db.insert("attendance",null,c);
        if (r==-1)
            return false;
        else
            return true;
    }

    public Cursor getResult (String sub_name){
        SQLiteDatabase db=this.getReadableDatabase();
        //select s.name , count(*) from attendance a , student s where a.roll_no = s.rollno and a.sub_name="Multimedia Technology" group by s.rollno;
        Cursor cursor = db.rawQuery("select s.name, count(*) from attendance a , student s where a.roll_no = s.rollno and a.sub_name= \""+sub_name+"\"group by s.rollno", null);

        return cursor;
    }
    public Cursor totalClass (String sub_name){
        SQLiteDatabase db=this.getReadableDatabase();
        //select count(*)from (select count(*) from attendance where sub_name="Multimedia Technology" group by date);
        Cursor cursor = db.rawQuery("select count(*)from (select count(*) from attendance where sub_name= \""+sub_name+"\" group by date)", null);

        return cursor;
    }
     */
}




package com.example.majorproject.classes;

public class DatePicker {
    String date, id , classId;

    public DatePicker(String date, String id, String classId) {
        this.date = date;
        this.id = id;
        this.classId = classId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }
}

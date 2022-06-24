package com.example.majorproject.classes;

public class Student {
    String studentId;
    String studentName;
    String StudentRollNo;

    public Student(String studentName, String studentRollNo) {
        this.studentName = studentName;
        StudentRollNo = studentRollNo;
    }

    public Student(String studentId, String studentName, String studentRollNo) {
        this.studentId = studentId;
        this.studentName = studentName;
        StudentRollNo = studentRollNo;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentRollNo() {
        return StudentRollNo;
    }

    public void setStudentRollNo(String studentRollNo) {
        StudentRollNo = studentRollNo;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }
}

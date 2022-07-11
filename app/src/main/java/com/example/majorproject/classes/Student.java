package com.example.majorproject.classes;

public class Student {
    String studentId;
    String studentName;
    String StudentRollNo;
    String count;

    public Student(String studentName, String studentRollNo) {
        this.studentName = studentName;
        StudentRollNo = studentRollNo;
    }

    public Student(String studentId, String studentName, String studentRollNo) {
        this.studentId = studentId;
        this.studentName = studentName;
        StudentRollNo = studentRollNo;
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentId='" + studentId + '\'' +
                ", studentName='" + studentName + '\'' +
                ", StudentRollNo='" + StudentRollNo + '\'' +
                ", count='" + count + '\'' +
                '}';
    }

    public Student(String studentId, String studentName, String studentRollNo, String count) {
        this.studentId = studentId;
        this.studentName = studentName;
        StudentRollNo = studentRollNo;
        this.count = count;
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

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}

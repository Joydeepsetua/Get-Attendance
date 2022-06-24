package com.example.majorproject.classes;

public class Clas {
    String class_id;
    String sub_name;
    String course_name;
    String class_name;

    public Clas(String sub_name, String course_name, String class_name) {
        this.sub_name = sub_name;
        this.course_name = course_name;
        this.class_name = class_name;
    }

    public Clas(String class_id, String sub_name, String course_name, String class_name) {
        this.class_id = class_id;
        this.sub_name = sub_name;
        this.course_name = course_name;
        this.class_name = class_name;
    }



    public Clas(String class_name) {
        this.class_name = class_name;
    }

    public String getSub_name() {
        return sub_name;
    }

    public void setSub_name(String sub_name) {
        this.sub_name = sub_name;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

    public String getClass_id() {
        return class_id;
    }

    public void setClass_id(String class_id) {
        this.class_id = class_id;
    }
}

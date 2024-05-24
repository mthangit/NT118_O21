package com.example.lab3_3;

public class Student {
    private int id;
    private String name;
    private String mssv;
    private String className;
    public Student(){

    }

    public Student(String name, String mssv, String className) {
        this.name = name;
        this.mssv = mssv;
        this.className = className;
    }

    // Getters and setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getMssv() {
        return mssv;
    }
    public void setMssv(String mssv) {
        this.mssv = mssv;
    }
    public String getClassName() {
        return className;
    }
    public void setClassName(String className) {
        this.className = className;
    }
}

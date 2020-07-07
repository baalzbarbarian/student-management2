package com.example.ass_quanlysinhvien.Model;

public class mStudents {

    private int id;
    private String classCode;
    private String studentName;
    private String studentBirthday;

    public mStudents(String classCode, String studentName, String studentBirthday) {
        this.classCode = classCode;
        this.studentName = studentName;
        this.studentBirthday = studentBirthday;
    }

    public mStudents(int id, String classCode, String studentName, String studentBirthday) {
        this.id = id;
        this.classCode = classCode;
        this.studentName = studentName;
        this.studentBirthday = studentBirthday;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public mStudents() {
    }

    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentBirthday() {
        return studentBirthday;
    }

    public void setStudentBirthday(String studentBirthday) {
        this.studentBirthday = studentBirthday;
    }
}

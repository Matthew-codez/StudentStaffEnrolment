package za.ac.cput.domain;

import java.io.Serializable;

public class Student implements Serializable {

    private String studentNum;
    private String studentName;
    private String password;

        public Student(){
        
    }
    public Student(String studentNum, String studentName, String password) {
        this.studentNum = studentNum;
        this.studentName = studentName;
        this.password = password;
    }

    public String getStudentNum() {
        return studentNum;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getPassword() {
        return password;
    }

//    public void setStudentNum(int studentNum) {
//        this.studentNum = studentNum;
//    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Student{" + "studentNum=" + studentNum + ", studentName=" + studentName + ", password=" + password + '}';
    }

}


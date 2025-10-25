package za.ac.cput.domain;


public class Student {
private int studentNum;
private String studentName;

    public Student(int studentNum, String studentName) {
        this.studentNum = studentNum;
        this.studentName = studentName;
    }
    
    public int getStudentNum() {
        return studentNum;
    }

    public String getStudentName() {
        return studentName;
    }

    @Override
    public String toString() {
        return "Student{" + "studentNum=" + studentNum + ", studentName=" + studentName + '}';
    }
    
}

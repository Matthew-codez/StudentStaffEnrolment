package za.ac.cput.domain;

public class Enrolment {
    private int enrolId;
    private int studentNum;
    private String courseId;
    
    public Enrolment(int enrol_id,int student_num, String course_id){
        this.enrolId = enrolId;
        this.studentNum = studentNum;
        this.courseId = courseId;

    }

    public int getEnrolmentId() {
        return enrolId;
    }

    public int getStudentNum() {
        return studentNum;
    }

    public String getCourseId() {
        return courseId;
    }

    @Override
    public String toString() {
        return "Enrolment{" + "enrolId=" + enrolId + ", studentNum=" + studentNum + ", courseId=" + courseId + '}';
    }
}


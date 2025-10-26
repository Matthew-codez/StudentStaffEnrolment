package za.ac.cput.DAO;

import java.sql.*;
import java.util.ArrayList;
import za.ac.cput.Database.DBConnection;
import za.ac.cput.domain.Course;

public class CourseDAO {
    private Connection conn;
    private Statement stmt;
    private PreparedStatement pstmt;

    public ArrayList<Course> getAllCourses(){
        ArrayList<Course> courseList = new ArrayList<>();
        try{
            conn = DBConnection.derbyConnection();
            String courseSql = "SELECT * FROM COURSE";
            pstmt = this.conn.prepareStatement(courseSql);
            ResultSet rs = pstmt.executeQuery();
            if(rs != null){
                while(rs.next()){
                    courseList.add(new Course(rs.getString("courseId"),
                            rs.getString("courseName")));
                }
                rs.close();
            }
        }catch(SQLException ex){
            System.out.println("Exception: "+ ex.getMessage());
        }
        finally{
            try{
                if(pstmt != null)
                    pstmt.close();
                if(conn != null)
                    conn.close();
            }catch(Exception ex){
                System.out.println("Exception: "+ ex.getMessage());
            }
        }
        return courseList;
    }
    public void addCourse(Course course){
        System.out.println("Trying to add course");
        int ok;
        
        try{
            conn = DBConnection.derbyConnection();
        String sql = "INSERT INTO COURSE (course_id, course_name) VALUES(?,?)";
        pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, course.getCourseId());
        pstmt.setString(2, course.getCourseName());
        ok = pstmt.executeUpdate();
        
        if (ok >0){
            System.out.println("Course added: " + course.getCourseId());
        }
        }catch(SQLException sqlEx){
            System.out.println("SQL Error adding course" + sqlEx.getMessage());
        }finally{
            try{
                if(pstmt != null)
                    pstmt.close();
                if(conn != null)
                    conn.close();
            }catch(SQLException sqlEx){
            System.out.println("SQL Error " + sqlEx.getMessage());
            }
        }
    }
}
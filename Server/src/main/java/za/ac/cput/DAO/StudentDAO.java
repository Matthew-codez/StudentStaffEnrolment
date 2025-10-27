package za.ac.cput.DAO;

/**
 *
 * @author user
 */
import java.sql.*;
import java.util.ArrayList;
import za.ac.cput.Database.DBConnection;
import za.ac.cput.domain.Student;

public class StudentDAO {

    private Connection conn;
    private PreparedStatement pstmt;

    public ArrayList<Student> getAllStudents() {
        ArrayList<Student> studentList = new ArrayList<>();
        try {
            conn = DBConnection.derbyConnection();
            String studentSql = "SELECT * FROM STUDENT";
            pstmt = this.conn.prepareStatement(studentSql);
            ResultSet rs = pstmt.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    studentList.add(new Student(rs.getString("studentNum"),
                            rs.getString("studentName"), rs.getString("password")));
                }
                rs.close();
            }
        } catch (SQLException ex) {
            System.out.println("Exception: " + ex.getMessage());
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception ex) {
                System.out.println("Exception: " + ex.getMessage());
            }
        }
        return studentList;
    }

    public void addStudent(Student student) {
        System.out.println("Trying to add student");
        int ok;

        try {
            conn = DBConnection.derbyConnection();
            String sql = "INSERT INTO STUDENT (student_num, student_name, password) VALUES(?,?,?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, student.getStudentNum());
            pstmt.setString(2, student.getStudentName());
            pstmt.setString(3, student.getPassword());
            ok = pstmt.executeUpdate();

            if (ok > 0) {
                System.out.println("Student added: " + student.getStudentNum());
            }
        } catch (SQLException sqlEx) {
            System.out.println("SQL Error adding student" + sqlEx.getMessage());
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException sqlEx) {
                System.out.println("SQL Error " + sqlEx.getMessage());
            }
        }
    }

    public Student getStudentByNum(String studentNum) {
        Student student = null;
        try {
            conn = DBConnection.derbyConnection();
            String sql = "SELECT * FROM STUDENT WHERE student_num = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, studentNum);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                student = new Student(
                        rs.getString("student_num"),
                        rs.getString("student_name"),
                        rs.getString("password")
                );
            }
            rs.close();

        } catch (SQLException ex) {
            System.out.println("SQL Error: " + ex.getMessage());
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println("SQL Error: " + ex.getMessage());
            }
        }
        return student;
    }

//    public void updatePassword(Student student) {
//        System.out.println("Trying to update object");
//        int ok;
//
//        try {
//            conn = DBConnection.derbyConnection();
//            String sql = "UPDATE STUDENT SET password = ? WHERE studentNum = ?";
//            pstmt = conn.prepareStatement(sql);
//            pstmt.setString(1, student.getPassword());
//            pstmt.setInt(2, student.getStudentNum());
//            ok = pstmt.executeUpdate();
//
//            if (ok > 0) {
//                System.out.println("Password updated: " + student.getStudentNum());
//            }
//        } catch (SQLException sqlEx) {
//            System.out.println("SQL Error updating student" + sqlEx.getMessage());
//        } finally {
//            try {
//                if (pstmt != null) {
//                    pstmt.close();
//                }
//                if (conn != null) {
//                    conn.close();
//                }
//            } catch (SQLException sqlEx) {
//                System.out.println("SQL Error " + sqlEx.getMessage());
//            }
//        }
//    }
}

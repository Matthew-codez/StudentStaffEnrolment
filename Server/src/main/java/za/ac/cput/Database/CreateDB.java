package za.ac.cput.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateDB {

    private static Connection conn;
    private static PreparedStatement pstmt;

    public static void createTables() {
        try {
            Connection conn = DBConnection.derbyConnection();
            Statement s = conn.createStatement();

            String create_Table_stmt = "CREATE TABLE STUDENT("
                    + "student_num INTEGER PRIMARY KEY,"
                    + "student_name VARCHAR(50),"
                    + "password VARCHAR(10))";

            String create_Table_stmt2 = "CREATE TABLE COURSE("
                    + "course_id VARCHAR(10) PRIMARY KEY,"
                    + "course_name VARCHAR(50))";

            String create_Table_stmt3 = "CREATE TABLE ENROLLMENT("
                    + "enrollment_id INTEGER PRIMARY KEY,"
                    + "student_num INTEGER,"
                    + "course_id VARCHAR(10),"
                    + "FOREIGN KEY(student_num) REFERENCES STUDENT(student_num),"
                    + "FOREIGN KEY(course_id) REFERENCES COURSE(course_id))";

            String create_Table_stmt4 = "CREATE TABLE ADMIN("
                    + "admin_name VARCHAR (50),"
                    + "password VARCHAR (10))";

            s.execute(create_Table_stmt);
            s.execute(create_Table_stmt2);
            s.execute(create_Table_stmt3);
            s.execute(create_Table_stmt4);

            System.out.println("Tables have been created");
        } catch (SQLException sqlEx) {
            System.out.println("SQL Exception Caught: " + sqlEx);
        }
    }

    public static void insertStudents() {
        int ok;
        try {
            conn = DBConnection.derbyConnection();
            String sql = "INSERT INTO STUDENT (student_num, student_name, password) VALUES(?,?,?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, 230048870);
            pstmt.setString(2, "Matthew Ferreira");
            pstmt.setString(3, "password");
            ok = pstmt.executeUpdate();

            if (ok > 0) {
                System.out.println("Student added successfully");
            }
        } catch (SQLException sqlEx) {
            System.out.println("SQL Exception Caught: " + sqlEx);
        } finally {
            try {

                if (pstmt != null) {
                    pstmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException sqlEx) {
                System.out.println("SQL Exception Caught: " + sqlEx);
            }
        }
    }

    public static void insertCourses() {
        int ok;
        try {
            conn = DBConnection.derbyConnection();
            String sql = "INSERT INTO Course (course_id, course_name) VALUES(?,?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, "DCIPTA");
            pstmt.setString(2, "DIP: ICT IN APPLICATIONS DEVELOPMENT");
            ok = pstmt.executeUpdate();

            if (ok > 0) {
                System.out.println("Course added successfully");
            }
        } catch (SQLException sqlEx) {
            System.out.println("SQL Exception Caught: " + sqlEx);
        } finally {
            try {

                if (pstmt != null) {
                    pstmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException sqlEx) {
                System.out.println("SQL Exception Caught: " + sqlEx);
            }
        }
    }

    public static void insertAdmin() {
        int ok;
        try {
            conn = DBConnection.derbyConnection();
            String sql = "INSERT INTO Admin (admin_name, password) VALUES(?,?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, "David");
            pstmt.setString(2, "12345");
            ok = pstmt.executeUpdate();

            if (ok > 0) {
                System.out.println("Admin added successfully");
            }
        } catch (SQLException sqlEx) {
            System.out.println("SQL Exception Caught: " + sqlEx);
        } finally {
            try {

                if (pstmt != null) {
                    pstmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException sqlEx) {
                System.out.println("SQL Exception Caught: " + sqlEx);
            }
        }
    }

    public static void main(String[] args) {
        createTables();
        insertStudents();
        insertCourses();
        insertAdmin();
    }
}

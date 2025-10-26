package za.ac.cput.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import za.ac.cput.Database.DBConnection;
import za.ac.cput.domain.Enrolment;

public class EnrolmentDAO {

    private Connection conn;
    private PreparedStatement pstmt;

    public ArrayList<Enrolment> getAllEnrolments() {
        ArrayList<Enrolment> enrolmentList = new ArrayList<>();
        try {
            conn = DBConnection.derbyConnection();
            String enrolmentSql = "SELECT * FROM ENROLMENT";
            pstmt = this.conn.prepareStatement(enrolmentSql);
            ResultSet rs = pstmt.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    enrolmentList.add(new Enrolment(rs.getInt("enrolId"),
                            rs.getInt("studentNum"), rs.getString("courseId")));
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
        return enrolmentList;
    }

    public void addEnrolment(Enrolment enrolment) {
        int ok;

        try {
            conn = DBConnection.derbyConnection();
            String sql = "INSERT INTO ENROLMENT (student_num, course_id) VALUES(?,?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, enrolment.getStudentNum());
            pstmt.setString(2, enrolment.getCourseId());

            ok = pstmt.executeUpdate();

            if (ok > 0) {
                System.out.println("Enrolment Successful: " + enrolment.getEnrolmentId());
            }
        } catch (SQLException sqlEx) {
            System.out.println("SQL Error adding enrolment" + sqlEx.getMessage());
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
}

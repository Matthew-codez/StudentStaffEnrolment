package za.ac.cput.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import za.ac.cput.Database.DBConnection;
import za.ac.cput.domain.Admin;

public class AdminDAO {

    private Connection conn;
    private PreparedStatement pstmt;

    public ArrayList<Admin> getAllAdmin() {
        ArrayList<Admin> adminList = new ArrayList<>();
        try {
            conn = DBConnection.derbyConnection();
            String sql = "SELECT * FROM Admin";
            pstmt = this.conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    adminList.add(new Admin(rs.getString("adminName"),
                            rs.getString("password")));
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
        return adminList;
    }
}

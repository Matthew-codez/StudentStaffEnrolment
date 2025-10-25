package za.ac.cput.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
        public static Connection derbyConnection() throws SQLException {
            String dbUrl = "jdbc:derby://localhost:1527/StudentEnrollmentDB";
            String username = "administrator";
            String password = "admin";
            System.out.println("Inside Connection Package");
            Connection conn = DriverManager.getConnection(dbUrl, username, password);
            System.out.println("Connection Success");

            return conn;
        }
    }

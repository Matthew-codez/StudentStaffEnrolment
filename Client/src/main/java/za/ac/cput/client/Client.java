package za.ac.cput.client;

import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import za.ac.cput.domain.Course;
import za.ac.cput.domain.Student;

/**
 *
 * @author 
 */
public class Client extends JFrame {

    private CardLayout cardLayout;
    private JPanel mainPnl;

    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton loginBtn;
    private JLabel loginStatus;

    private JTextField txtStudentNum, txtStudentName;
    private JTextField TxtCourseId, txtCourseName;

    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    private String currentUser;

    private JTable courseTable;
    private JTable enrolledTable;
    private DefaultTableModel courseModel;
    private DefaultTableModel enrolledModel;

    private JButton enrollBtn;
    private JButton refreshBtn;
    private JButton viewBtn;
    
    private JLabel newStudentName;
    private JLabel newStudentNumber;
    private JTextField txtCourseId;
    private JTextField txtCourseNames;

    private final String Url = "jdbc:derby://localhost:1527/StudentEnrollmentDB";
    private final String Username = "administrator";
    private final String Password = "admin";

    public Client() {
        super("Student Staff App - Client");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(700, 400);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPnl = new JPanel(cardLayout);

        mainPnl.add(LoginPnl(), "Login");
        mainPnl.add(StudentPnl(), "Student");
        mainPnl.add(AdminPnl(), "Admin");
        
        add(mainPnl);
        cardLayout.show(mainPnl, "Admin");

        communicate();
    }

    private void communicate() {
        try {
            socket = new Socket("127.0.0.1", 6666);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            System.out.println("Connected to server.");
        } catch (IOException ioe) {
            JOptionPane.showMessageDialog(this,
                    "Could not connect to server: " + ioe.getMessage(),
                    "Connection Error",
                    JOptionPane.ERROR_MESSAGE);
            loginBtn.setEnabled(false);
            loginStatus.setText("Start the server first");
        }
    }

    private JPanel LoginPnl() {
        JPanel pnl = new JPanel(new BorderLayout(10, 10));
        pnl.setBorder(BorderFactory.createEmptyBorder(50, 120, 50, 120));

        JLabel title = new JLabel("Student Enrolment System", JLabel.CENTER);
        pnl.add(title, BorderLayout.NORTH);

        JPanel formPnl = new JPanel(new GridLayout(3, 2, 10, 10));;

        JLabel lblUsername = new JLabel("Student or Admin Number:", JLabel.RIGHT);
        txtUsername = new JTextField();
        formPnl.add(lblUsername);
        formPnl.add(txtUsername);

        JLabel lblPassword = new JLabel("Password:", JLabel.RIGHT);
        txtPassword = new JPasswordField();
        formPnl.add(lblPassword);
        formPnl.add(txtPassword);

        loginBtn = new JButton("Login");
        loginBtn.setBackground(new Color(30, 60, 114));
        loginBtn.setForeground(Color.WHITE);

        formPnl.add(new JLabel());
        formPnl.add(loginBtn);

        JPanel wrapperPnl = new JPanel(new GridBagLayout());

        wrapperPnl.add(formPnl);

        pnl.add(wrapperPnl, BorderLayout.CENTER);

        loginStatus = new JLabel(" ", JLabel.CENTER);
        pnl.add(loginStatus, BorderLayout.SOUTH);

        loginBtn.addActionListener(e -> Login());

        return pnl;
    }

    private JPanel StudentPnl() {
        JPanel pnl = new JPanel(new BorderLayout(12, 12));

        JLabel title = new JLabel("Student Dashboard", JLabel.CENTER);
        pnl.add(title, BorderLayout.NORTH);

        JPanel centerPnl = new JPanel(new GridLayout(0, 1, 5, 5)); // 0 rows, 1 col, 5px gaps

        courseModel = new DefaultTableModel(new String[]{"Course ID", "Course Name"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        courseTable = new JTable(courseModel);
        courseTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        courseTable.setRowHeight(24);

        JScrollPane courseScrollPane = new JScrollPane(courseTable);
        centerPnl.add(courseScrollPane);

        JPanel courseBtnPnl = new JPanel(new FlowLayout());
        enrollBtn = new JButton("Enroll");
        refreshBtn = new JButton("Refresh");
        courseBtnPnl.add(enrollBtn);
        courseBtnPnl.add(refreshBtn);

        centerPnl.add(courseBtnPnl);

        enrolledModel = new DefaultTableModel(new String[]{"Course ID", "Course Name"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        enrolledTable = new JTable(enrolledModel);
        enrolledTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        enrolledTable.setRowHeight(24);

        JScrollPane enrolledScrollPane = new JScrollPane(enrolledTable);
        centerPnl.add(enrolledScrollPane);

        JPanel enrolledBtnPnl = new JPanel(new FlowLayout());
        viewBtn = new JButton("View Courses");
        enrolledBtnPnl.add(viewBtn);

        centerPnl.add(enrolledBtnPnl);

        pnl.add(centerPnl, BorderLayout.CENTER);

        return pnl;
    }

    private JPanel AdminPnl() {
        JPanel pnl = new JPanel(new BorderLayout(15, 15));
        pnl.add(new JLabel("Administrator Dashboard", JLabel.CENTER), BorderLayout.NORTH);

        JPanel forms = new JPanel(new GridLayout(2, 1, 20, 20));

        JPanel studentFieldsPnl = new JPanel(new GridLayout(2, 2, 10, 10));
        
        JLabel newStudentName = new JLabel("Add Student Number: ");
        JLabel newStudentNumber = new JLabel("Add Student Name: ");
        
        txtStudentNum = new JTextField();
        txtStudentName = new JTextField();
        
        studentFieldsPnl.add(newStudentName);
        studentFieldsPnl.add(txtStudentNum);
        studentFieldsPnl.add(newStudentNumber);
        studentFieldsPnl.add(txtStudentName);
        
        JButton addStudentBtn = new JButton("Add Student");

        JPanel studentPnl = new JPanel(new BorderLayout(5, 5));
        studentPnl.add(studentFieldsPnl, BorderLayout.CENTER);
        
        JPanel studentBtnPnl = new JPanel(new FlowLayout(FlowLayout.CENTER));
        studentBtnPnl.add(addStudentBtn);
        
        studentPnl.add(studentBtnPnl, BorderLayout.SOUTH);

        
        JPanel txtCoursePnl = new JPanel(new GridLayout(2, 2, 10, 10));
        
        txtCourseId = new JTextField();
        txtCourseName = new JTextField();

        txtCoursePnl.add(new JLabel("Course ID:"));
        txtCoursePnl.add(txtCourseId);
        txtCoursePnl.add(new JLabel("Course Name:"));
        txtCoursePnl.add(txtCourseName);
        
        JButton addCourseBtn = new JButton("Add Course");

        JPanel coursePnl = new JPanel(new BorderLayout(5, 5));
        coursePnl.add(txtCoursePnl, BorderLayout.CENTER);
        
        JPanel courseBtnPnl = new JPanel(new FlowLayout(FlowLayout.CENTER));
        courseBtnPnl.add(addCourseBtn);
        
        coursePnl.add(courseBtnPnl, BorderLayout.SOUTH);
        
        forms.add(studentPnl);
        forms.add(coursePnl);

        pnl.add(forms, BorderLayout.CENTER);
        
        addStudentBtn.addActionListener(e -> {
            try {
                String studentNum = txtStudentNum.getText().trim();
                String studentName = txtStudentName.getText().trim();

                Student student = new Student(studentNum, studentName);

                out.writeObject(student);
                out.flush();

                JOptionPane.showMessageDialog(this, "Student object sent to server.");
                
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error sending student: " + ex.getMessage());
            } catch (Exception ex) { 
                JOptionPane.showMessageDialog(this, "An error occurred: " + ex.getMessage());
            }
        });

        addCourseBtn.addActionListener(e -> {
            try {
                String courseId = txtCourseId.getText().trim();
                String courseName = txtCourseName.getText().trim();

                Course course = new Course(courseId, courseName);

                out.writeObject(course);
                out.flush();

                JOptionPane.showMessageDialog(this, "Course object sent to server.");

            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error sending course: " + ex.getMessage());
            } catch (Exception ex) { 
                JOptionPane.showMessageDialog(this, "An error occurred: " + ex.getMessage());
            }
        });
                
        return pnl;
    }

    private void Login() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public static void main(String[] args) {
        Client client = new Client();
        client.setVisible(true);
    }
}

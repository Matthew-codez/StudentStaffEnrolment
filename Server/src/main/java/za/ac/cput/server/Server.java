package za.ac.cput.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import za.ac.cput.DAO.CourseDAO;
import za.ac.cput.DAO.EnrolmentDAO;
import za.ac.cput.DAO.StudentDAO;
import za.ac.cput.domain.Course;
import za.ac.cput.domain.Enrolment;
import za.ac.cput.domain.Admin;
import za.ac.cput.domain.Student;

public class Server {

    private ServerSocket listener;
    private Socket client;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    public Server() {
        try {
            listener = new ServerSocket(6666, 1);
        } catch (IOException ioe) {
            System.out.println("IO Exception: " + ioe.getMessage());
        }

    }

    public void listen() {
        try {
            System.out.println("Server is listening");
            client = listener.accept();
            System.out.println("Now we processClient");
            processClient();
        } catch (IOException ioe) {
            System.out.println("IO Exception found:" + ioe.getMessage());
        }
    }

    private void getStreams() throws IOException {

        out = new ObjectOutputStream(client.getOutputStream());
        in = new ObjectInputStream(client.getInputStream());

    }

    public void closeAll() {
        try {
            out.close();
            in.close();
            client.close();
        } catch (IOException ioe) {
            System.out.println("IO Exception: " + ioe.getMessage());
        }
    }

    public void processClient() {
        try {
            getStreams();

            Object request = in.readObject();

            do {
                //can just call the object: 
                //Student student = (Student) in.readObject;
                //...
                //Courses courses = (Courses) in.readObject;
                //...
                //Enrolment enrolment = (Enrolment) in.readObject;
                //...
                //but then client needs to do everything in this order exactly otherwise ClassCastException gets thrown and everything breaks

                if (request instanceof Student) {
                    Student student = (Student) request;
                    StudentDAO dao = new StudentDAO();
                    dao.addStudent(student);

                } else if (request instanceof Course) {
                    Course course = (Course) request;
                    CourseDAO dao = new CourseDAO();
                    dao.addCourse(course);

                    ArrayList<Course> list = dao.getAllCourses();
                    out.writeObject(list);
                    out.flush();

                } else if (request instanceof Enrolment) {
                    Enrolment enrolment = (Enrolment) request;
                    EnrolmentDAO dao = new EnrolmentDAO();
                    dao.addEnrolment(enrolment);

                    ArrayList<Enrolment> list = dao.getAllEnrolments();
                    out.writeObject(list);

//                } else if (request instanceof Admin) {
//                    Admin l = (Admin) request;
//
                }

            } while (true);

        } catch (IOException | ClassNotFoundException ioe) {
            System.out.println("IO Exception:" + ioe.getMessage());
        } finally {
            closeAll();
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.listen();

    }
}

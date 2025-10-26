package za.ac.cput.domain;

import java.io.Serializable;

public class Admin implements Serializable {

    private String adminName;
    private String password;

    public Admin(String adminName, String password) {
        this.adminName = adminName;
        this.password = password;
    }

    public String getUsername() {
        return adminName;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "Login{" + "adminName=" + adminName + ", password=" + password + '}';
    }
}


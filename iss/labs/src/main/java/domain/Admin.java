package domain;

import java.util.Objects;

public class Admin extends User{


    public Admin(String username, String password) {
        super(username, password);
    }

    public Admin() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Admin admin = (Admin) o;
        return Objects.equals(username, admin.username) && Objects.equals(password, admin.password) && Objects.equals(ID, admin.ID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password, ID);
    }

    @Override
    public String toString() {
        return "Admin{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", ID=" + ID +
                '}';
    }
}

package repository;

import domain.Admin;
import domain.validator.Validator;
import repository.interfaces.RepositoryAdmin;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class RepoAdmin implements RepositoryAdmin {
    private String url;
    private String username;
    private String password;
    private Validator<Admin> validator;

    public RepoAdmin(String url, String username, String password, Validator<Admin> validator) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.validator = validator;
    }

    @Override
    public void add(Admin entity) {
        if (entity == null)
            throw new IllegalArgumentException("Entity must be not null");
        validator.validate(entity);
        String sql = "insert into admins (usernameu,passwordu) values (?,?)";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)
        ) {

            ps.setString(1, entity.getUsername());
            ps.setString(2, entity.getPassword());

            ps.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public Admin findOneByUsername(String string) {
        if (string == null)
            throw new IllegalArgumentException("username must be not null");


        String sql = "SELECT * from admins where admins.usernameu = ? ";
        Admin ev;

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1,string);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String st1 = resultSet.getString("usernameu");
                String st2 = resultSet.getString("passwordu");

                ev = new Admin(st1,st2 );
                ev.setID(id);

                return ev;

            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return null;
    }

    @Override
    public Iterable<Admin> findAll() {
        String sql = "SELECT * from admins ";
        Admin ev;
        Set<Admin> events = new HashSet<>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String st1 = resultSet.getString("usernameu");
                String st2 = resultSet.getString("passwordu");

                ev = new Admin(st1,st2);
                ev.setID(id);


                events.add(ev);

            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return events;
    }
}

package repository;


import domain.Admin;
import domain.Agent;
import domain.validator.Validator;
import repository.interfaces.RepositoryAgent;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class RepoAgent implements RepositoryAgent {
    private String url;
    private String username;
    private String password;
    private Validator<Agent> validator;

    public RepoAgent(String url, String username, String password, Validator<Agent> validator) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.validator = validator;
    }

    @Override
    public void add(Agent entity) {
        if (entity == null)
            throw new IllegalArgumentException("Entity must be not null");
        validator.validate(entity);
        String sql = "insert into agents (usernameu,passwordu) values (?,?)";

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
    public Agent findOneByUsername(String string) {
        if (string == null)
            throw new IllegalArgumentException("username must be not null");


        String sql = "SELECT * from agents where agents.usernameu = ? ";
        Agent ev;

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1,string);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String st1 = resultSet.getString("usernameu");
                String st2 = resultSet.getString("passwordu");

                ev = new Agent(st1,st2 );
                ev.setID(id);

                return ev;

            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return null;
    }

    @Override
    public Iterable<Agent> findAll() {
        String sql = "SELECT * from agents ";
        Agent ev;
        Set<Agent> events = new HashSet<>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String st1 = resultSet.getString("usernameu");
                String st2 = resultSet.getString("passwordu");

                ev = new Agent(st1,st2);
                ev.setID(id);


                events.add(ev);

            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return events;
    }

}

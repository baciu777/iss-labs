package repository;

import domain.Admin;
import domain.Agent;
import domain.Product;
import domain.validator.ValidationException;
import domain.validator.Validator;
import repository.interfaces.RepositoryProduct;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class RepoProduct implements RepositoryProduct {
    private String url;
    private String username;
    private String password;
    private Validator<Product> validator;

    public RepoProduct(String url, String username, String password, Validator<Product> validator) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.validator = validator;
    }

    @Override
    public void add(Product entity) {
        if (entity == null)
            throw new IllegalArgumentException("Entity must be not null");
        validator.validate(entity);
        String sql = "insert into products (name,provider,quantity,price) values (?,?,?,?)";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)
        ) {

            ps.setString(1, entity.getName());
            ps.setString(2, entity.getProvider());
            ps.setInt(3, entity.getQuantity());
            ps.setInt(4, entity.getPrice());
            ps.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Iterable<Product> findAll() {
        String sql = "SELECT * from products";
        Product ev;
        Set<Product> events = new HashSet<>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String st1 = resultSet.getString("name");
                String st2 = resultSet.getString("provider");
                int st3 = resultSet.getInt("quantity");
                int st4 = resultSet.getInt("price");
                ev = new Product(st1,st2,st3,st4);
                ev.setID(id);


                events.add(ev);

            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return events;
    }


    @Override
    public void delete(Product agent) {
        int row_count = 0;
        Product event = null;
        String sql = "delete from products where products.id=?  ";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, event.getID());


            ps.executeUpdate();


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }

    @Override
    public void update(Product entity, Integer id) {
        if (entity == null)
            throw new IllegalArgumentException("Entity must be not null");
        validator.validate(entity);
        String sql = "update products SET quantity=?,price=? where ID=?";
        int row_count = 0;
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, entity.getQuantity());
            ps.setInt(2, entity.getPrice());
            ps.setInt(3, entity.getID());
            row_count = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void order(Product entity,int quantity, Integer id) {
        if (entity == null)
            throw new IllegalArgumentException("Entity must be not null");
        validator.validate(entity);


        String sql = "update products SET quantity=? where ID=?";
        int row_count = 0;
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {
            int quantityFinal=quantity-entity.getQuantity();
            if(quantityFinal<0)
                throw new ValidationException("you chosen a bigger quantity than the stock has");
            ps.setInt(1, quantityFinal);
            ps.setInt(3, entity.getID());
            row_count = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}

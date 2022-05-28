package repository;

import domain.Admin;
import domain.Agent;
import domain.validator.Validator;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import repository.interfaces.RepositoryAdmin;

import javax.persistence.Query;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class RepoAdmin extends HibernateRepository implements RepositoryAdmin {
    private String url;
    private String username;
    private String password;
    private Validator<Admin> validator;


    public RepoAdmin(String url, String username, String password, Validator<Admin> validator) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.validator = validator;
        initialize();


    }

    @Override
    public void add(Admin entity) {


    }

    @Override
    public Admin findOneByUsername(String string) {
        if (string == null)
            throw new IllegalArgumentException("username must be not null");

        List<Admin> parts = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();///////////////////////
                Query query= session.createQuery("select g from Admin g where g.username = :team", Admin.class);
                query.setParameter("team",string);
                parts =query.getResultList();
                transaction.commit();

            } catch (RuntimeException exception) {
                if (transaction != null)
                    transaction.rollback();

                System.err.println("Error BD " +exception);
            }
        }


        return parts.get(0);
    }

    @Override
    public Iterable<Admin> findAll() {
        List<Admin> parts=new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                parts = session.createQuery("from Admin as p order by p.username", Admin.class).list();
                transaction.commit();

            } catch (RuntimeException exception) {
                if (transaction != null)
                    transaction.rollback();

                System.err.println("Error BD "+exception );
            }
        }


        return parts;
    }
}

package repository;


import domain.Admin;
import domain.Agent;
import domain.validator.Validator;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import repository.interfaces.RepositoryAgent;

import javax.persistence.Query;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RepoAgent extends HibernateRepository implements RepositoryAgent {
    private String url;
    private String username;
    private String password;
    private Validator<Agent> validator;


    public RepoAgent(String url, String username, String password, Validator<Agent> validator) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.validator = validator;
        initialize();
    }

    @Override
    public void add(Agent entity) {


    }

    @Override
    public Agent findOneByUsername(String string) {
        if (string == null)
            throw new IllegalArgumentException("username must be not null");




        List<Agent> parts = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();///////////////////////
                Query query= session.createQuery("select g from Agent g where g.username = :team", Agent.class);
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
    public Iterable<Agent> findAll() {
        List<Agent> parts=new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                parts = session.createQuery("from Agent as p order by p.username", Agent.class).list();
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

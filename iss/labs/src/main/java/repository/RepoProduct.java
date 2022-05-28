package repository;

import domain.Admin;
import domain.Agent;
import domain.Product;
import domain.validator.ValidationException;
import domain.validator.Validator;
import observer.Observable;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import repository.interfaces.RepositoryProduct;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RepoProduct extends Observable implements RepositoryProduct  {
    private String url;
    private String username;
    private String password;
    private Validator<Product> validator;


    public RepoProduct(String url, String username, String password, Validator<Product> validator) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.validator = validator;
        HibernateRepository.initialize();



    }

    @Override
    public void add(Product entity) {
        if (entity == null)
            throw new IllegalArgumentException("Entity must be not null");
        validator.validate(entity);




        try(Session session = HibernateRepository.sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();

                session.save(entity);
                tx.commit();
                notifyObservers();
            } catch (RuntimeException ex) {
                System.err.println("Eroare la inserare "+ex);
                if (tx != null)
                    tx.rollback();
            }
        }



    }

    @Override
    public Iterable<Product> findAll() {
        List<Product> parts=new ArrayList<>();
        try (Session session = HibernateRepository.sessionFactory.openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                parts = session.createQuery("from Product as p order by p.name", Product.class).list();
                transaction.commit();

            } catch (RuntimeException exception) {
                if (transaction != null)
                    transaction.rollback();

                System.err.println("Error BD "+exception );
            }
        }
        return parts;
    }


    @Override
    public void delete(Product agent) {


        try(Session session = HibernateRepository.sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();

                Product crit = session.createQuery("from Product where id = "+ agent.getID(), Product.class)
                        .setMaxResults(1)
                        .uniqueResult();
                System.err.println("Stergem mesajul " + crit.getName());
                session.delete(crit);
                tx.commit();
                notifyObservers();
            } catch (RuntimeException ex) {
                System.err.println("Eroare la stergere "+ex);
                if (tx != null)
                    tx.rollback();
            }
        }

    }

    @Override
    public void update(Product entity, Integer id) {
        if (entity == null)
            throw new IllegalArgumentException("Entity must be not null");
        validator.validate(entity);

        try(Session session = HibernateRepository.sessionFactory.openSession()){
            Transaction tx=null;
            try{
                tx = session.beginTransaction();

              Product crit = session.createQuery("from Product where id = "+ id, Product.class)
                      .getSingleResult();

                crit.setQuantity(entity.getQuantity());
                crit.setPrice(entity.getPrice());



                session.update(crit);
                tx.commit();
                notifyObservers();

            } catch(RuntimeException ex){
                System.err.println("Eroare la update "+ex);
                if (tx!=null)
                    tx.rollback();
            }
        }

    }

    @Override
    public void order(Product entity,int quantity, Integer id) {
        if (entity == null)
            throw new IllegalArgumentException("Entity must be not null");
        validator.validate(entity);


        try(Session session = HibernateRepository.sessionFactory.openSession()){
            Transaction tx=null;
            try{
                tx = session.beginTransaction();

                Product crit = session.createQuery("from Product where id = "+ id, Product.class)
                        .setMaxResults(1)
                        .uniqueResult();

                crit.setQuantity(crit.getQuantity()-quantity);




                session.update(crit);
                tx.commit();
                notifyObservers();

            } catch(RuntimeException ex){
                System.err.println("Eroare la update "+ex);
                if (tx!=null)
                    tx.rollback();
            }
        }


    }


}

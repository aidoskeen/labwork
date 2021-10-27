package com.example.labwork.Controllers;

import com.example.labwork.model.Administrator;
import com.example.labwork.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public class UserHiberCtrl {
    private Session session;

    private static SessionFactory factory;
    public UserHiberCtrl(SessionFactory factory) {
        this.factory=factory;
    }
    public void start(){
        try {
            factory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
    public Session getSession(){
        return session;
    }


    public void createUser(Administrator user){
        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            session.save(user);
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

}

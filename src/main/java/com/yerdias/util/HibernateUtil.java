package com.yerdias.util;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateUtil {

    final static StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure("META-INF/hibernate.cfg.xml")
            .build();

    private static final SessionFactory sessionFactory;

    static {
        try {
            sessionFactory = new MetadataSources(registry).buildMetadata()
                    .buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static Session getSession()
            throws HibernateException {
        Session session = null;
        try {
            session = sessionFactory.getCurrentSession();
        } catch (HibernateException he) {
            session = sessionFactory.openSession();
        }
        return session;
    }

}

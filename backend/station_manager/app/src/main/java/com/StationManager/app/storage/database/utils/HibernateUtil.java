package com.StationManager.app.storage.database.utils;

import com.StationManager.app.Settings;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    private static SessionFactory sessionFactory;

    private static SessionFactory buildSessionFactory() {
        try {
            // Create the SessionFactory from hibernate.cfg.xml
            Configuration configuration = new Configuration();
            System.out.println(Settings.PG_URL);
            configuration.setProperty("hibernate.connection.url", Settings.PG_URL);
            configuration.setProperty("hibernate.connection.username", Settings.PG_USER);
            configuration.setProperty("hibernate.connection.password", Settings.PG_PASSWORD);

            configuration.configure();
            System.out.println("Hibernate Configuration loaded");

            System.out.println("Hibernate serviceRegistry created");

            return configuration.buildSessionFactory();
        } catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) sessionFactory = buildSessionFactory();
        return sessionFactory;
    }

}

package com.StationManager.shared.storage.database.utils;

import com.StationManager.shared.Settings;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HibernateUtil {

    protected static final Logger logger = LoggerFactory.getLogger(HibernateUtil.class);

    private static SessionFactory sessionFactory;

    private static SessionFactory buildSessionFactory() {
        try {
            // Create the SessionFactory from hibernate.cfg.xml
            Configuration configuration = new Configuration();
            configuration.setProperty("hibernate.connection.url", Settings.PG_URL);
            configuration.setProperty("hibernate.connection.username", Settings.PG_USER);
            configuration.setProperty("hibernate.connection.password", Settings.PG_PASSWORD);

            configuration.configure();
            logger.info("Hibernate Configuration loaded");
            return configuration.buildSessionFactory();
        } catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            logger.error("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory    getSessionFactory() {
        if (sessionFactory == null) sessionFactory = buildSessionFactory();
        return sessionFactory;
    }

}

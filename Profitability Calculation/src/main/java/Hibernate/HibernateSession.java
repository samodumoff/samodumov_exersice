package Hibernate;

import DataBaseEntity.Objects;
import DataBaseEntity.Purchase;
import DataBaseEntity.Sale;
import DataBaseEntity.Store;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import java.util.Properties;

public class HibernateSession {

    private static SessionFactory sessionFactory;

    private HibernateSession() {

    }

    public static SessionFactory getSessionFactory(String[] properties) {
        if (sessionFactory == null && properties.length == 6) {
            try {
                Configuration configuration = new Configuration();
                Properties settings = new Properties();

                settings.put(Environment.DRIVER, properties[0]);
                settings.put(Environment.URL, properties[1]);
                settings.put(Environment.USER, properties[2]);
                settings.put(Environment.PASS, properties[3]);
                settings.put(Environment.DIALECT, properties[4]);
                settings.put(Environment.SHOW_SQL, properties[5]);

                configuration.setProperties(settings);
                //configuration.configure("hibernate.cfg.xml");
                configuration.addAnnotatedClass(Purchase.class);
                configuration.addAnnotatedClass(Sale.class);
                configuration.addAnnotatedClass(Store.class);
                configuration.addAnnotatedClass(Objects.class);
                StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
                sessionFactory = configuration.buildSessionFactory(builder.build());

            } catch (Exception e) {
                System.out.println("Error in try make session - " + e.getMessage());
            }
        }
        return sessionFactory;
    }

}

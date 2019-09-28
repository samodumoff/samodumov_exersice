import DataBaseEntity.Objects;
import DataBaseEntity.Purchase;
import DataBaseEntity.Sale;
import DataBaseEntity.Store;
import Hibernate.*;
import Program.Rules;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Properties;

public class RulesTest {

    private static String purchase_right = "PURCHASE iphone 1 1000 01.01.1999";
    private static String purchase_error = "PURCHASE iphone dsfsdf 1000 01.01.1999";
    private static String new_object_right = "NEWPRODUCT iphone";
    private static String new_object_error = "NEWPRODUCT iphone";
    private static String demand_right = "DEMAND iphone 1 10000 01.01.1999";
    private static String demand_error = "DEMAND iphone 15 1000 02.01.1999";


    private static SessionFactory sessionFactory;
    private static Session session;
    private static Transaction transaction;

    private static Service service;
    private static IDao dao;

    @BeforeClass
    public static void setUp(){

        Configuration configuration = new Configuration();
        Properties settings = new Properties();

        settings.put(Environment.DRIVER, "org.hsqldb.jdbcDriver");
        settings.put(Environment.URL, "jdbc:hsqldb:mem://localhost:5432/samodumov_db?createDatabaseIfNotExist=true&useUnicode=yes&characterEncoding=UTF-8");
        settings.put(Environment.USER, "sa");
        settings.put(Environment.PASS, "");
        settings.put(Environment.DIALECT, "org.hibernate.dialect.HSQLDialect");
        settings.put(Environment.SHOW_SQL, "true");
        settings.put(Environment.HBM2DDL_AUTO, "create");

        configuration.setProperties(settings);
        configuration.addAnnotatedClass(Purchase.class);
        configuration.addAnnotatedClass(Sale.class);
        configuration.addAnnotatedClass(Store.class);
        configuration.addAnnotatedClass(Objects.class);

        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
        sessionFactory  = configuration.buildSessionFactory(builder.build());
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
        service = new Service();

    }

    @Before
    public void letsSession(){
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
        session.createQuery("delete from Objects").executeUpdate();
        session.createQuery("delete from Purchase").executeUpdate();
        session.createQuery("delete from Sale").executeUpdate();
        session.createQuery("delete from Store").executeUpdate();

        PurchaseDaoImpl.sessionFactory = sessionFactory;
        ObjectsDaoImpl.sessionFactory = sessionFactory;
        StoreDaoImpl.sessionFactory = sessionFactory;
        SaleDaoImpl.sessionFactory = sessionFactory;

    }

    @Test
    public void testRightPurchaseValidation(){

        String[] argumentsArray = purchase_right.split(" ");
        Objects obj = new Objects("iphone");

        session.save(obj);
        transaction.commit();
        session.close();

        Purchase purchase = Rules.validationPurchase(argumentsArray, service);

        Assert.assertNotEquals(purchase, null);
        Assert.assertEquals(purchase.getPrice(), 1000.0, 0.1);
        Assert.assertEquals(purchase.getCount(), 1);

    }

    @Test
    public void testErrorPurchaseValidation(){

        String[] argumentsArray = purchase_error.split(" ");
        Objects obj = new Objects("iphone");

        session.save(obj);
        transaction.commit();
        session.close();

        Purchase purchase = Rules.validationPurchase(argumentsArray, service);

        Assert.assertEquals(purchase, null);

    }
    @Test
    public void testRightNewObjectValidation(){

        String[] argumentsArray = new_object_right.split(" ");
       /* Objects obj = new Objects("iphone");

        session.save(obj);*/
        transaction.commit();
        session.close();

        Objects objects = Rules.validationNewProduct(argumentsArray, service);

        Assert.assertNotEquals(objects, null);
        Assert.assertEquals(objects.getName(), "iphone");

    }

    @Test
    public void testErrorNewObjectValidation(){

        String[] argumentsArray = new_object_error.split(" ");
        Objects obj = new Objects("iphone");

        session.save(obj);
        transaction.commit();
        session.close();

        Objects objects = Rules.validationNewProduct(argumentsArray, service);

        Assert.assertEquals(objects, null);

    }

    @Test
    public void testRightDemandValidation(){

        String[] argumentsArray = demand_right.split(" ");

        Store obj = new Store("iphone", 1);

        session.save(obj);
        transaction.commit();
        session.close();

        Sale sale = Rules.validationDemand(argumentsArray, service);

        Assert.assertNotEquals(sale, null);
        Assert.assertEquals(sale.getPrice(), 10000.0, 0.1);
        Assert.assertEquals(sale.getCount(), 1);

    }

    @Test
    public void testErrorDemandValidation(){

        String[] argumentsArray = demand_error.split(" ");

        Store obj = new Store("iphone", 1);

        session.save(obj);
        transaction.commit();
        session.close();

        Sale sale = Rules.validationDemand(argumentsArray, service);

        Assert.assertEquals(sale, null);

    }

}

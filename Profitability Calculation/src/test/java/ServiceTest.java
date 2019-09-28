import DataBaseEntity.Objects;
import DataBaseEntity.Purchase;
import DataBaseEntity.Sale;
import DataBaseEntity.Store;
import Hibernate.*;
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

import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;


public class ServiceTest {

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

    }

    @Test
    public void testFindByNameInObjects(){

        ObjectsDaoImpl.sessionFactory = sessionFactory;

        dao = new ObjectsDaoImpl();

        Objects obj = new Objects("htc");
        session.save(obj);
        transaction.commit();
        session.close();

        List<Objects> objects = service.findByName(dao, "htc");

        Assert.assertEquals(objects.size(), 1);
        Assert.assertEquals(objects.get(0).getName(), "htc");

    }

    @Test
    public void testGetAllObjects(){

        ObjectsDaoImpl.sessionFactory = sessionFactory;

        dao = new ObjectsDaoImpl();

        Objects obj = new Objects("htc");
        Objects obj1 = new Objects("samsung");
        Objects obj2 = new Objects("iphone");

        session.save(obj);
        session.save(obj1);
        session.save(obj2);

        transaction.commit();
        session.close();

        List<Objects> objects = service.getAll(dao);

        Assert.assertEquals(objects.size(), 3);
        Assert.assertEquals(objects.get(0).getName(), "htc");
        Assert.assertEquals(objects.get(1).getName(), "samsung");
        Assert.assertEquals(objects.get(2).getName(), "iphone");

    }

    @Test
    public void testSaveObject(){

        session.close();
        ObjectsDaoImpl.sessionFactory = sessionFactory;

        dao = new ObjectsDaoImpl();

        Objects obj = new Objects("htc");
        service.saveObj(dao, obj);

        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
        List<Objects> objects = session.createQuery("from Objects").list();

        transaction.commit();
        session.close();


        Assert.assertEquals(objects.size(), 1);
        Assert.assertEquals(objects.get(0).getName(), "htc");
    }

    @Test
    public void testUpdateObject(){

        ObjectsDaoImpl.sessionFactory = sessionFactory;

        dao = new ObjectsDaoImpl();

        Objects obj = new Objects("htc");
        Objects obj1 = new Objects("iphone");

        session.save(obj);
        session.save(obj1);
        transaction.commit();
        session.close();

        obj.setName("onePlus");

        service.updateObj(dao, obj);

        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
        List<Objects> objects = session.createQuery("from Objects").list();

        Assert.assertEquals(objects.size(), 2);
        Assert.assertEquals(objects.get(0).getName(), "onePlus");
        Assert.assertEquals(objects.get(1).getName(), "iphone");
    }

    @Test
    public void testDeleteObject(){

        ObjectsDaoImpl.sessionFactory = sessionFactory;

        dao = new ObjectsDaoImpl();

        Objects obj = new Objects("htc");
        Objects obj1 = new Objects("samsung");

        session.save(obj);
        session.save(obj1);
        transaction.commit();
        session.close();

        service.deleteObj(dao, obj);

        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
        List<Objects> objects = session.createQuery("from Objects").list();

        Assert.assertEquals(objects.size(), 1);
        Assert.assertEquals(objects.get(0).getName(), "samsung");
    }

    @Test
    public void testFindByNameInPurchase(){

        long tmpTime = System.currentTimeMillis();

        PurchaseDaoImpl.sessionFactory = sessionFactory;

        dao = new PurchaseDaoImpl();

        Purchase purchase = new Purchase("htc", 2, 1000, new Timestamp(tmpTime));
        Purchase purchase2 = new Purchase("htc", 5, 2000, new Timestamp(tmpTime));

        session.save(purchase);
        session.save(purchase2);
        transaction.commit();
        session.close();

        List<Purchase> objects = service.findByName(dao, "htc");

        Assert.assertEquals(objects.size(), 2);
        Assert.assertEquals(objects.get(0).getName(), "htc");
        Assert.assertEquals(objects.get(1).getName(), "htc");
        Assert.assertEquals(objects.get(0).getCount(), 2);
        Assert.assertEquals(objects.get(1).getCount(), 5);
        Assert.assertEquals(objects.get(0).getPrice(), 1000, 0.1);
        Assert.assertEquals(objects.get(1).getPrice(), 2000, 0.1);
        Assert.assertEquals(objects.get(0).getDate().getTime(), tmpTime);
        Assert.assertEquals(objects.get(1).getDate().getTime(), tmpTime);

    }

    @Test
    public void testGetAllPurchase(){

        long tmpTime = System.currentTimeMillis();

        PurchaseDaoImpl.sessionFactory = sessionFactory;

        dao = new PurchaseDaoImpl();

        Purchase purchase = new Purchase("htc", 2, 1000, new Timestamp(tmpTime));
        Purchase purchase2 = new Purchase("iphone", 5, 1000, new Timestamp(tmpTime));

        session.save(purchase);
        session.save(purchase2);

        transaction.commit();
        session.close();

        List<Purchase> objects = service.getAll(dao);

        Assert.assertEquals(objects.size(), 2);
        Assert.assertEquals(objects.get(0).getName(), "htc");
        Assert.assertEquals(objects.get(1).getName(), "iphone");
        Assert.assertEquals(objects.get(0).getCount(), 2);
        Assert.assertEquals(objects.get(1).getCount(), 5);
        Assert.assertEquals(objects.get(0).getPrice(), 1000, 0.1);
        Assert.assertEquals(objects.get(1).getPrice(), 1000, 0.1);
        Assert.assertEquals(objects.get(0).getDate().getTime(), tmpTime);
        Assert.assertEquals(objects.get(1).getDate().getTime(), tmpTime);

    }

    @Test
    public void testSavePurchase(){

        long tmpTime = System.currentTimeMillis();

        session.close();
        PurchaseDaoImpl.sessionFactory = sessionFactory;

        dao = new PurchaseDaoImpl();

        Purchase purchase = new Purchase("htc", 2, 1000, new Timestamp(tmpTime));

        service.saveObj(dao, purchase);

        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
        List<Purchase> objects = session.createQuery("from Purchase").list();

        transaction.commit();
        session.close();

        Assert.assertEquals(objects.size(), 1);
        Assert.assertEquals(objects.get(0).getName(), "htc");
        Assert.assertEquals(objects.get(0).getCount(), 2);
        Assert.assertEquals(objects.get(0).getPrice(), 1000, 0.1);
        Assert.assertEquals(objects.get(0).getDate().getTime(), tmpTime);
    }

    @Test
    public void testUpdatePurchase(){

        long tmpLong = System.currentTimeMillis();

        PurchaseDaoImpl.sessionFactory = sessionFactory;

        dao = new PurchaseDaoImpl();

        Purchase purchase = new Purchase("samsung", 2, 1000, new Timestamp(tmpLong));
        Purchase purchase2 = new Purchase("iphone", 2, 2000, new Timestamp(tmpLong));

        session.save(purchase);
        session.save(purchase2);
        transaction.commit();
        session.close();

        purchase2.setName("onePlus");

        service.updateObj(dao, purchase2);

        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
        List<Purchase> objects = session.createQuery("from Purchase").list();

        Assert.assertEquals(objects.size(), 2);
        Assert.assertEquals(objects.get(0).getName(), "samsung");
        Assert.assertEquals(objects.get(0).getCount(), 2);
        Assert.assertEquals(objects.get(0).getPrice(), 1000, 0.1);
        Assert.assertEquals(objects.get(0).getDate().getTime(), tmpLong);
        Assert.assertEquals(objects.get(1).getName(), "onePlus");
        Assert.assertEquals(objects.get(1).getCount(), 2);
        Assert.assertEquals(objects.get(1).getPrice(), 2000, 0.1);
        Assert.assertEquals(objects.get(1).getDate().getTime(), tmpLong);
    }

    @Test
    public void testDeletePurchase(){

        long tmpLong = System.currentTimeMillis();

        PurchaseDaoImpl.sessionFactory = sessionFactory;

        dao = new PurchaseDaoImpl();

        Purchase purchase = new Purchase("iphone", 2, 2000, new Timestamp(tmpLong));
        Purchase purchase2 = new Purchase("samsung", 20, 5000, new Timestamp(tmpLong));

        session.save(purchase);
        session.save(purchase2);
        transaction.commit();
        session.close();

        service.deleteObj(dao, purchase);

        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
        List<Purchase> objects = session.createQuery("from Purchase").list();

        Assert.assertEquals(objects.size(), 1);
        Assert.assertEquals(objects.get(0).getName(), "samsung");
        Assert.assertEquals(objects.get(0).getCount(), 20);
        Assert.assertEquals(objects.get(0).getPrice(), 5000, 0.1);
        Assert.assertEquals(objects.get(0).getDate().getTime(), tmpLong);

    }
    @Test
    public void testFindByNameInStore(){

        StoreDaoImpl.sessionFactory = sessionFactory;

        dao = new StoreDaoImpl();

        Store obj = new Store("htc", 1000.0);

        session.save(obj);
        transaction.commit();
        session.close();

        List<Store> store = service.findByName(dao, "htc");

        Assert.assertEquals(store.size(), 1);
        Assert.assertEquals(store.get(0).getName(), "htc");
        Assert.assertEquals(store.get(0).getPrice(), 1000.0, 0.1);

    }

    @Test
    public void testGetAllStore(){

        StoreDaoImpl.sessionFactory = sessionFactory;

        dao = new StoreDaoImpl();

        Store obj = new Store("htc", 1000.0);
        Store obj1 = new Store("samsung", 2000.0);

        session.save(obj);
        session.save(obj1);

        transaction.commit();
        session.close();

        List<Store> store = service.getAll(dao);

        Assert.assertEquals(store.size(), 2);
        Assert.assertEquals(store.get(0).getName(), "htc");
        Assert.assertEquals(store.get(1).getName(), "samsung");
        Assert.assertEquals(store.get(0).getPrice(), 1000.0, 0.1);
        Assert.assertEquals(store.get(1).getPrice(), 2000.0, 0.1);

    }

    @Test
    public void testSaveStore(){

        session.close();
        StoreDaoImpl.sessionFactory = sessionFactory;

        dao = new StoreDaoImpl();

        Store obj = new Store("htc", 1000.0);
        service.saveObj(dao, obj);

        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
        List<Store> objects = session.createQuery("from Store").list();

        transaction.commit();
        session.close();

        Assert.assertEquals(objects.size(), 1);
        Assert.assertEquals(objects.get(0).getName(), "htc");
        Assert.assertEquals(objects.get(0).getPrice(), 1000.0, 0.1);
    }

    @Test
    public void testUpdateStore(){

        StoreDaoImpl.sessionFactory = sessionFactory;

        dao = new StoreDaoImpl();

        Store obj = new Store("htc", 1000.0);
        Store obj1 = new Store("onePLus", 15000.0);

        session.save(obj);
        session.save(obj1);
        transaction.commit();
        session.close();

        obj.setName("samsung");

        service.updateObj(dao, obj);

        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
        List<Store> objects = session.createQuery("from Store").list();

        Assert.assertEquals(objects.size(), 2);
        Assert.assertEquals(objects.get(0).getName(), "samsung");
        Assert.assertEquals(objects.get(0).getPrice(), 1000.0, 0.1);

        Assert.assertEquals(objects.get(1).getName(), "onePLus");
        Assert.assertEquals(objects.get(1).getPrice(), 15000.0, 0.1);
    }

    @Test
    public void testDeleteStore(){

        StoreDaoImpl.sessionFactory = sessionFactory;

        dao = new StoreDaoImpl();

        Store obj = new Store("htc", 1000.0);
        Store obj1 = new Store("samsung", 2000.0);

        session.save(obj);
        session.save(obj1);
        transaction.commit();
        session.close();

        service.deleteObj(dao, obj);

        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
        List<Store> objects = session.createQuery("from Store").list();

        Assert.assertEquals(objects.size(), 1);
        Assert.assertEquals(objects.get(0).getName(), "samsung");
        Assert.assertEquals(objects.get(0).getPrice(), 2000.0, 0.1);
    }
    @Test
    public void testFindByNameInSale(){

        long tmpLong = System.currentTimeMillis();

        SaleDaoImpl.sessionFactory = sessionFactory;

        dao = new SaleDaoImpl();

        Sale obj = new Sale("htc", 2, 2000.0, new Timestamp(tmpLong) );

        session.save(obj);
        transaction.commit();
        session.close();

        List<Sale> objects = service.findByName(dao, "htc");

        Assert.assertEquals(objects.size(), 1);
        Assert.assertEquals(objects.get(0).getName(), "htc");
        Assert.assertEquals(objects.get(0).getCount(), 2);
        Assert.assertEquals(objects.get(0).getPrice(), 2000.0, 0.1);
        Assert.assertEquals(objects.get(0).getDate().getTime(), tmpLong);
    }

    @Test
    public void testGetAllSales(){

        long tmpLong = System.currentTimeMillis();

        SaleDaoImpl.sessionFactory = sessionFactory;

        dao = new SaleDaoImpl();

        Sale obj = new Sale("htc", 2, 2000.0, new Timestamp(tmpLong) );
        Sale obj1 = new Sale("samsung", 5, 2000.0, new Timestamp(tmpLong) );
        Sale obj2 = new Sale("iphone", 4, 6000.0, new Timestamp(tmpLong) );

        session.save(obj);
        session.save(obj1);
        session.save(obj2);

        transaction.commit();
        session.close();

        List<Sale> objects = service.getAll(dao);

        Assert.assertEquals(objects.size(), 3);
        Assert.assertEquals(objects.get(0).getName(), "htc");
        Assert.assertEquals(objects.get(0).getCount(), 2);
        Assert.assertEquals(objects.get(0).getPrice(), 2000.0, 0.1);
        Assert.assertEquals(objects.get(0).getDate().getTime(), tmpLong);
        Assert.assertEquals(objects.get(1).getName(), "samsung");
        Assert.assertEquals(objects.get(1).getCount(), 5);
        Assert.assertEquals(objects.get(1).getPrice(), 2000.0, 0.1);
        Assert.assertEquals(objects.get(1).getDate().getTime(), tmpLong);
        Assert.assertEquals(objects.get(2).getName(), "iphone");
        Assert.assertEquals(objects.get(2).getCount(), 4);
        Assert.assertEquals(objects.get(2).getPrice(), 6000.0, 0.1);
        Assert.assertEquals(objects.get(2).getDate().getTime(), tmpLong);

    }

    @Test
    public void testSaveSale(){

        long tmpLong = System.currentTimeMillis();

        session.close();
        SaleDaoImpl.sessionFactory = sessionFactory;

        dao = new SaleDaoImpl();

        Sale obj = new Sale("iphone", 4, 6000.0, new Timestamp(tmpLong) );
        service.saveObj(dao, obj);

        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
        List<Sale> objects = session.createQuery("from Sale").list();

        transaction.commit();
        session.close();

        Assert.assertEquals(objects.size(), 1);
        Assert.assertEquals(objects.get(0).getName(), "iphone");
        Assert.assertEquals(objects.get(0).getCount(), 4);
        Assert.assertEquals(objects.get(0).getPrice(), 6000.0, 0.1);
        Assert.assertEquals(objects.get(0).getDate().getTime(), tmpLong);
    }

    @Test
    public void testUpdateSale(){

        long tmpLong = System.currentTimeMillis();

        SaleDaoImpl.sessionFactory = sessionFactory;

        dao = new SaleDaoImpl();

        Sale obj = new Sale("iphone", 4, 6000.0, new Timestamp(tmpLong) );
        Sale obj1 = new Sale("xiaomi", 50, 100.0, new Timestamp(tmpLong) );

        session.save(obj);
        session.save(obj1);
        transaction.commit();
        session.close();

        obj.setName("samsung");

        service.updateObj(dao, obj);

        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
        List<Sale> objects = session.createQuery("from Sale").list();

        Assert.assertEquals(objects.size(), 2);
        Assert.assertEquals(objects.get(0).getName(), "samsung");
        Assert.assertEquals(objects.get(0).getCount(), 4);
        Assert.assertEquals(objects.get(0).getPrice(), 6000.0, 0.1);
        Assert.assertEquals(objects.get(0).getDate().getTime(), tmpLong);
        Assert.assertEquals(objects.get(1).getName(), "xiaomi");
        Assert.assertEquals(objects.get(1).getCount(), 50);
        Assert.assertEquals(objects.get(1).getPrice(), 100.0, 0.1);
        Assert.assertEquals(objects.get(1).getDate().getTime(), tmpLong);
    }

    @Test
    public void testDeleteSale(){

        long tmpLong = System.currentTimeMillis();

        SaleDaoImpl.sessionFactory = sessionFactory;

        dao = new SaleDaoImpl();

        Sale obj = new Sale("xiaomi", 50, 100.0, new Timestamp(tmpLong) );
        Sale obj1 = new Sale("oppo", 10, 1000.0, new Timestamp(tmpLong) );

        session.save(obj);
        session.save(obj1);
        transaction.commit();
        session.close();

        service.deleteObj(dao, obj);

        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
        List<Sale> objects = session.createQuery("from Sale").list();

        Assert.assertEquals(objects.size(), 1);
        Assert.assertEquals(objects.get(0).getName(), "oppo");
        Assert.assertEquals(objects.get(0).getCount(), 10);
        Assert.assertEquals(objects.get(0).getPrice(), 1000.0, 0.1);
        Assert.assertEquals(objects.get(0).getDate().getTime(), tmpLong);
    }

}

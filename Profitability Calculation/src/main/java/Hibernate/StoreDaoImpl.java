package Hibernate;

import DataBaseEntity.Store;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class StoreDaoImpl implements IDao<Store> {

    public static SessionFactory sessionFactory;

    @Override
    public List<Store> findAllByName(String name) {

        Query query = sessionFactory.openSession().createQuery("From Store WHERE name = :paramName");
        query.setParameter("paramName", name);
        List<Store> storeList = query.list();

        return storeList;
    }

    @Override
    public List<Store> getAll() {
        Query query = sessionFactory.openSession().createQuery("From Store");
        List<Store> storeList = query.list();

        return storeList;
    }

    @Override
    public void save(Store store) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(store);
        transaction.commit();
        session.close();
    }

    @Override
    public void update(Store store) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.update(store);
        transaction.commit();
        session.close();
    }

    @Override
    public void delete(Store store) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(store);
        transaction.commit();
        session.close();
    }
}

package Hibernate;

import DataBaseEntity.Purchase;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class PurchaseDaoImpl implements IDao<Purchase> {

    public static SessionFactory sessionFactory;
    @Override
    public List<Purchase> findAllByName(String name) {

        Query query = sessionFactory.openSession().createQuery("From Purchase WHERE name = :paramName");
        query.setParameter("paramName", name);
        List<Purchase> purchaseList = query.list();

        return purchaseList;

    }

    @Override
    public List<Purchase> getAll() {
        Query query = sessionFactory.openSession().createQuery("From Purchase");
        List<Purchase> purchaseList = query.list();

        return purchaseList;
    }

    @Override
    public void save(Purchase purchase) {

        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(purchase);
        transaction.commit();
        session.close();
    }

    @Override
    public void update(Purchase purchase) {

        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.update(purchase);
        transaction.commit();
        session.close();
    }

    @Override
    public void delete(Purchase purchase) {

        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(purchase);
        transaction.commit();
        session.close();
    }
}

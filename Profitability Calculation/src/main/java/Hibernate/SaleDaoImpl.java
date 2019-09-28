package Hibernate;

import DataBaseEntity.Sale;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class SaleDaoImpl implements IDao<Sale> {

    public static SessionFactory sessionFactory;

    @Override
    public List<Sale> findAllByName(String name) {

        Query query = sessionFactory.openSession().createQuery("From Sale WHERE name = :paramName");
        query.setParameter("paramName", name);
        List<Sale> saleList = query.list();

        return saleList;
    }

    @Override
    public List<Sale> getAll() {
        Query query = sessionFactory.openSession().createQuery("From Sale");
        List<Sale> saleList = query.list();

        return saleList;
    }

    @Override
    public void save(Sale sale) {

        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(sale);
        transaction.commit();
        session.close();

    }

    @Override
    public void update(Sale sale) {

        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.update(sale);
        transaction.commit();
        session.close();

    }

    @Override
    public void delete(Sale sale) {

        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(sale);
        transaction.commit();
        session.close();

    }
}

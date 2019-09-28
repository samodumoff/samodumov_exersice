package Hibernate;

import DataBaseEntity.Objects;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class ObjectsDaoImpl implements IDao<Objects> {

    public static SessionFactory sessionFactory;

    @Override
    public List<Objects> findAllByName(String name) {

        Query query = sessionFactory.openSession().createQuery("From Objects WHERE name = :paramName");
        query.setParameter("paramName", name);
        List<Objects> objectsList = query.list();

        return objectsList;
    }

    @Override
    public List<Objects> getAll() {

        Query query = sessionFactory.openSession().createQuery("From Objects");
        List<Objects> objectsList = query.list();

        return objectsList;
    }

    @Override
    public void save(Objects objects) {

        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(objects);
        transaction.commit();
        session.close();

    }

    @Override
    public void update(Objects objects) {

        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.update(objects);
        transaction.commit();
        session.close();

    }

    @Override
    public void delete(Objects objects) {

        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(objects);
        transaction.commit();
        session.close();

    }
}

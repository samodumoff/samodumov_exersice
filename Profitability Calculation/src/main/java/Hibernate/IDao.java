package Hibernate;

import java.util.List;

public interface IDao<T> {

    List<T> findAllByName( String name );

    List<T> getAll();

    void save(T t);

    void update(T t);

    void delete(T t);

}

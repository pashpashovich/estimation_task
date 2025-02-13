package by.innowise.registrationapp.dao;

import java.util.List;
import java.util.Optional;

public interface AbstractHibernateDao<T, ID> {
    Optional<T> findById(ID id);

    List<T> findAll();

    void save(T object);

    void update(T object);

    void delete(T object);
}
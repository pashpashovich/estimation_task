package by.innowise.registrationapp.dao;

import java.util.Optional;

public interface AbstractHibernateDao<T, ID> {
    Optional<T> findById(ID id);

    Optional<T> findByEmail(String email);

    void save(T object);

    void update(T object);
}
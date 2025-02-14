package by.innowise.registrationapp.dao;

import by.innowise.registrationapp.entity.User;
import by.innowise.registrationapp.util.HibernateUtil;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserDao implements AbstractHibernateDao<User, Long> {

    @Override
    public Optional<User> findById(Long id) {
        try (Session session = HibernateUtil.getSession()) {
            User user = session.get(User.class, id);
            return Optional.ofNullable(user);
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        try (Session session = HibernateUtil.getSession()) {
            User user = session.createQuery("FROM User WHERE email = :email", User.class)
                    .setParameter("email", email)
                    .uniqueResult();
            return Optional.ofNullable(user);
        }
    }

    @Override
    public void save(User user) {
        try (Session session = HibernateUtil.getSession()) {
            session.beginTransaction();
            session.persist(user);
            session.getTransaction().commit();
        }
    }

    @Override
    public void update(User user) {
        try (Session session = HibernateUtil.getSession()) {
            session.beginTransaction();
            session.merge(user);
            session.getTransaction().commit();
        }
    }

}

package by.innowise.registrationapp.dao;

import by.innowise.registrationapp.entity.User;
import by.innowise.registrationapp.util.HibernateUtil;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserDao {

    public User save(User user) {
        try (Session session = HibernateUtil.getSession()) {
            session.persist(user);
        }
        return user;
    }
    public Optional<User> findByEmail(String email) {
        try (Session session = HibernateUtil.getSession()) {
            User user = session.createQuery("FROM User WHERE email = :email", User.class)
                    .setParameter("email", email)
                    .uniqueResult();
            return Optional.ofNullable(user);
        }
    }

//    public Optional<User> findById(Long id) {
//        try (Session session = HibernateUtil.getSession()) {
//            return Optional.ofNullable(session.get(User.class, id));
//        }
//    }
}

package acm.dao.impl;

import acm.dao.UserDAO;
import acm.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository("userDAO")
@Transactional
public class UserDAOImpl implements UserDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional
    @Override
    public User getUserByName(String username) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Object userObj = session.createQuery("from User where username = :name").setString("name", username).uniqueResult();
        User u = (User) userObj;

        session.getTransaction().commit();
        session.close();
        return u;
    }

    @Transactional
    @Override
    public User getUserById(Long id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        User result = (User) session.get(User.class, id);

        session.getTransaction().commit();
        session.close();

        return result;
    }

}

package acm.dao.impl;

import java.sql.Timestamp;
import java.util.List;

import acm.model.Account;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import acm.dao.AccountDAO;
import acm.model.User;

@Repository("acccountDAO")
@Transactional
public class AccountDAOImpl implements AccountDAO {

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
	public Account update(Account existingAccount) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		session.update(existingAccount);

		session.getTransaction().commit();
		session.close();
		return existingAccount;
	}

	@Transactional
	@Override
	public List<Account> getAllAccountsByUser(User u) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		List result = session.createQuery("from Account where USER_ID = :id").setLong("id", u.getId()).list();
		
		session.getTransaction().commit();
		session.close();

		return result;
	}

    @Transactional
    @Override
    public Account getAccountByNumber(String number) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Account result = (Account)session.createQuery("from Account where number = :nr").setString("nr", number).uniqueResult();

        session.getTransaction().commit();
        session.close();

        return result;
    }
}

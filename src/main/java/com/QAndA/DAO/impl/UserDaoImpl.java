package com.QAndA.DAO.impl;

import com.QAndA.DAO.UserDao;
import com.QAndA.Domain.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

/**
 * Created by George on 11/02/2015.
 */

@Repository
public class UserDaoImpl implements UserDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	@Transactional
	public User save(User user) {
		Session session = sessionFactory.getCurrentSession();
		long id = (Long) session.save(user); //TODO check this actually returns the id
		user.setId(id);

		return user;
	}

	@Override
	@Transactional
	public User get(long id) {
		return (User) sessionFactory.getCurrentSession().get(User.class, id);
	}

	@Override
	@Transactional
	public User update(User user) {
//		User updatedUser = this.get(user.getId());
//		updatedUser.setfName(user.getfName());
//		updatedUser.setlName(user.getlName());
//		updatedUser.setAnswer;
		return null;
	}

	@Override
	@Transactional
	public boolean delete(User user) {
		Session session = sessionFactory.getCurrentSession();
		session.delete(user);//TODO Think about knock on effects for answers - possibly link to specific ' null account'?
		return true;
	}
}

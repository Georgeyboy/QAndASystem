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
		user.setAccountActive(true);
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
		User updatedUser = this.get(user.getId());
		updatedUser.setfName(user.getfName());
		updatedUser.setlName(user.getlName());
		updatedUser.setEmail(user.getEmail());
		updatedUser.setAvatarLocation(user.getAvatarLocation());
		this.save(updatedUser);
		return updatedUser;
	}

	@Override
	@Transactional
	public boolean delete(User user) {
//		USERS DO NOT GET DELETED
//		This is to ensure questions and answers still link to a user for reference
//		Instead, users can deactivated. This will be displayed upon viewing their account page
		return false;
	}

	@Override
	@Transactional
	public void activateAccount(User user) {
		User u = this.get(user.getId());
		u.setAccountActive(true);
		this.save(u);
	}

	@Override
	@Transactional
	public void deactivateAccount(User user) {
		User u = this.get(user.getId());
		u.setAccountActive(false);
		this.save(u);
	}
}

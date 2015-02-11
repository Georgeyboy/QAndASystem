package com.QAndA.DAO.impl;

import com.QAndA.DAO.UserDao;
import com.QAndA.Domain.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by George on 11/02/2015.
 */

@Repository
public class UserDaoImpl implements UserDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public User save(User user) {
		return null;
	}

	@Override
	public User get(long id) {
		return null;
	}

	@Override
	public User update(User user) {
		return null;
	}

	@Override
	public boolean delete(User user) {
		return false;
	}
}

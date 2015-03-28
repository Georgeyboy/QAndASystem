package com.QAndA.DAO.impl;

import com.QAndA.DAO.UserRoleDao;
import com.QAndA.Domain.UserRole;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by George on 25/02/2015.
 */
@Repository
public class UserRoleDaoImpl implements UserRoleDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	@Transactional
	public UserRole save(UserRole userRole) {
		Session session = sessionFactory.getCurrentSession();
		long id = (Long) session.save(userRole);
		userRole.setId(id);

		return userRole;
	}

	@Override
	@Transactional
	public UserRole get(long id) {
		return (UserRole) sessionFactory.getCurrentSession().get(UserRole.class, id);
	}

	@Override
	@Transactional
	public UserRole update(UserRole userRole) {
		sessionFactory.getCurrentSession().saveOrUpdate(userRole);
		return this.get(userRole.getId());
	}

	@Override
	@Transactional
	public boolean delete(UserRole userRole) {
		Session session = sessionFactory.getCurrentSession();
		session.delete(userRole);
		return false;
	}

	@Override
	@Transactional
	@SuppressWarnings("unchecked")
	public UserRole findByRole(String role) {
		List<UserRole> roles = sessionFactory.getCurrentSession().createQuery("from UserRole where role=?")
				.setParameter(0, role)
				.list();

		if(roles.size() != 0){
			return roles.get(0);
		}else{
			return null;
		}
	}
}

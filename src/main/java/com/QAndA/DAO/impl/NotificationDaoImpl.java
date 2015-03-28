package com.QAndA.DAO.impl;

import com.QAndA.DAO.NotificationDao;
import com.QAndA.Domain.Notification;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

/**
 * Created by George on 19/02/2015.
 */
@Repository
public class NotificationDaoImpl implements NotificationDao{

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	@Transactional
	public Notification save(Notification notification) {
		Session session = sessionFactory.getCurrentSession();
		long id = (Long) session.save(notification);
		notification.setId(id);

		return notification;
	}

	@Override
	@Transactional
	public Notification get(long id) {
		return (Notification) sessionFactory.getCurrentSession().get(Notification.class, id);
	}

	@Override
	@Transactional
	public Notification update(Notification notification) {
		sessionFactory.getCurrentSession().saveOrUpdate(notification);
		return this.get(notification.getId());
	}

	@Override
	@Transactional
	public boolean delete(Notification notification) {
		Session session = sessionFactory.getCurrentSession();
		session.delete(notification);
		return false;
	}

	@Override
	@Transactional
	public void setRead(Notification notification) {

	}
}

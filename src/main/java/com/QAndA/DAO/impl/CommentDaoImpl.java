package com.QAndA.DAO.impl;

import com.QAndA.DAO.CommentDao;
import com.QAndA.Domain.Comment;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

/**
 * Created by George on 18/03/2015.
 */
@Repository
public class CommentDaoImpl implements CommentDao{

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	@Transactional
	public Comment save(Comment comment) {
		Session session = sessionFactory.getCurrentSession();
		long id = (Long) session.save(comment);
		comment.setId(id);

		return comment;
	}

	@Override
	@Transactional
	public Comment get(long id) {
		return (Comment) sessionFactory.getCurrentSession().get(Comment.class, id);
	}

	@Override
	@Transactional
	public Comment update(Comment comment) {
		sessionFactory.getCurrentSession().saveOrUpdate(comment);
		return this.get(comment.getId());
	}

	@Override
	@Transactional
	public boolean delete(Comment comment) {
		Session session = sessionFactory.getCurrentSession();
		session.delete(comment);
		return true;
	}

}

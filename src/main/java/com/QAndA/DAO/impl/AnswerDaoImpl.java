package com.QAndA.DAO.impl;

import com.QAndA.DAO.AnswerDao;
import com.QAndA.Domain.Answer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

/**
 * Created by George on 19/02/2015.
 */
@Repository
public class AnswerDaoImpl implements AnswerDao{

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	@Transactional
	public Answer save(Answer answer) {
		Session session = sessionFactory.getCurrentSession();
		long id = (Long) session.save(answer);
		answer.setId(id);

		return answer;
	}

	@Override
	@Transactional
	public Answer get(long id) {
		return (Answer) sessionFactory.getCurrentSession().get(Answer.class, id);
	}

	@Override
	@Transactional
	public Answer update(Answer answer) {
		sessionFactory.getCurrentSession().saveOrUpdate(answer);
		return this.get(answer.getId());
	}

	@Override
	@Transactional
	public boolean delete(Answer answer) {
		Session session = sessionFactory.getCurrentSession();
		session.delete(answer);
		return false;
	}
}

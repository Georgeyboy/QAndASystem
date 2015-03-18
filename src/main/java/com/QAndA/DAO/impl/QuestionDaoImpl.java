package com.QAndA.DAO.impl;

import com.QAndA.DAO.QuestionDao;
import com.QAndA.Domain.Question;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by George on 19/02/2015.
 */
@Repository
public class QuestionDaoImpl implements QuestionDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	@Transactional
	public Question save(Question question) {
		Session session = sessionFactory.getCurrentSession();
		long id = (Long) session.save(question); //TODO check this actually returns the id
		question.setId(id);

		return question;
	}

	@Override
	@Transactional
	public Question get(long id) {
		return (Question) sessionFactory.getCurrentSession().get(Question.class, id);
	}

	@Override
	@Transactional
	public Question update(Question question) {
		Question updatedQuestion = this.get(question.getId());
		updatedQuestion.setTitle(question.getTitle());
		updatedQuestion.setUser(question.getUser());
		return null;
	}

	@Override
	@Transactional
	public boolean delete(Question question) {
		Session session = sessionFactory.getCurrentSession();
		session.delete(question);
		return true;//TODO try catch in here for deletion
	}

	@Override
	@Transactional
	public List<Question> getRecentQuestions(int limit) {
		Session session = sessionFactory.getCurrentSession();
		List<Question> questions = session.createQuery("FROM Question r ORDER BY r.id desc").setMaxResults(limit).list();

		return questions;
	}
}

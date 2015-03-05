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
		long id = (Long) session.save(answer); //TODO check this actually returns the id
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
		Answer updatedAnswer = this.get(answer.getId());
		updatedAnswer.setAnswer(answer.getAnswer());
		updatedAnswer.setQuestion(answer.getQuestion());
		updatedAnswer.setUser(answer.getUser());
		this.save(updatedAnswer);
//		TODO check if this also updates the appropriate question
		return updatedAnswer;
	}

	@Override
	@Transactional
	public boolean delete(Answer answer) {
		Session session = sessionFactory.getCurrentSession();
		session.delete(answer);
		return false;
	}
}

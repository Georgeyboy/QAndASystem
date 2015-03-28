package com.QAndA.DAO.impl;

import com.QAndA.DAO.QuestionDao;
import com.QAndA.Domain.Question;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
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
		long id = (Long) session.save(question);
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
		sessionFactory.getCurrentSession().saveOrUpdate(question);
		return this.get(question.getId());
	}

	@Override
	@Transactional
	public boolean delete(Question question) {
		Session session = sessionFactory.getCurrentSession();
		session.delete(question);
		return true;
	}

	@Override
	@Transactional
	public List<Question> getRecentQuestions(int limit) {
		Session session = sessionFactory.getCurrentSession();
		List<Question> questions = session.createQuery("FROM Question r ORDER BY r.id desc").setMaxResults(limit).list();

		return questions;
	}

	@Override
	@Transactional
	public List<Question> searchQuestions(String query, Integer startLimit, Integer endLimit) {
		if(startLimit == null){
			startLimit = 0;
		}
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Question.class);
		criteria.setFirstResult(0);

//		Adjust search query
		String[] queryList = query.split(" ");
		for(String q : queryList){
			q = "%" + q + "%";
			criteria.add(Restrictions.like("title", q).ignoreCase());
		}
//		query = "%" + query + "%";
//
//		criteria.add(Restrictions.like("title", query).ignoreCase());

//		Add end limit if exists
		if(endLimit != null){
			criteria.setMaxResults(endLimit);
		}

		List<Question> results = criteria.list();
		return results;
	}

	@Override
	@Transactional
	public long getSearchCount(String query){
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Question.class);
//		Adjust search query
		query = "%" + query + "%";
//		TODO Split query on spaces for multiple word search (use .and?)
		criteria.add(Restrictions.like("title", query));
		criteria.setProjection(Projections.rowCount());
		long count = (Long) criteria.uniqueResult();
		System.out.println("Search count for query : '" + query + "' = " + count);
		return count;
	}
}

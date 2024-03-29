package com.QAndA.DAO;

import com.QAndA.Domain.Question;

import java.util.List;

/**
 * Created by George on 19/02/2015.
 */
public interface QuestionDao extends SuperDao<Question> {

	public List<Question> getRecentQuestions(int limit);

	public List<Question> searchQuestions(String query, Integer startLimit, Integer endLimit);

	public long getSearchCount(String query);

}

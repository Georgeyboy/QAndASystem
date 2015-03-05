package com.QAndA.Services;

import com.QAndA.DAO.QuestionDao;
import com.QAndA.DTO.QuestionDTO;
import com.QAndA.Domain.Question;
import com.QAndA.Domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by George on 05/03/2015.
 */
@Service
public class QuestionService {

	@Autowired
	private QuestionDao questionDao;


	public Question saveQuestion(QuestionDTO dto, User user){
		Question question = new Question();

		question.setTitle(dto.getTitle());
		question.setQuestion(dto.getQuestion());
		question.setUser(user);

		Date date = new Date();
		question.setDate(date);

		return questionDao.save(question);

	}

}

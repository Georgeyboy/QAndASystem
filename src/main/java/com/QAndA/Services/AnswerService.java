package com.QAndA.Services;

import com.QAndA.DAO.AnswerDao;
import com.QAndA.DTO.AnswerDTO;
import com.QAndA.Domain.Answer;
import com.QAndA.Domain.Question;
import com.QAndA.Domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by George on 05/03/2015.
 */

@Service
public class AnswerService {

	@Autowired
	private AnswerDao answerDao;


	public Answer saveAnswer(AnswerDTO dto, User user, Question question){

		Answer answer = new Answer();

		answer.setUser(user);
		answer.setQuestion(question);
		answer.setAnswer(dto.getDescription());
		answer.setDate(new Date());

		return answerDao.save(answer);
	}


	public Set<AnswerDTO> answersToDtos(List<Answer> answers, long questionID){
		Set<AnswerDTO> results = new HashSet<AnswerDTO>();

		for(Answer answer : answers){
			AnswerDTO result = new AnswerDTO();
			result.setDescription(answer.getAnswer());
			result.setQuestionID(String.valueOf(questionID));
			result.setUserID(answer.getUser().getUsername());
			results.add(result);
		}

		return results;
	}
}

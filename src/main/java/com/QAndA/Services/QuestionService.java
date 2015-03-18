package com.QAndA.Services;

import com.QAndA.DAO.QuestionDao;
import com.QAndA.DTO.QuestionDTO;
import com.QAndA.Domain.Question;
import com.QAndA.Domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

	public List<QuestionDTO> questionsToDtos(List<Question> questions){

		List<QuestionDTO> results = new ArrayList<QuestionDTO>();
		for(Question question : questions){
			results.add(this.questionToDto(question));
		}

		return results;

	}

	public QuestionDTO questionToDto(Question question){
		QuestionDTO dto = new QuestionDTO();
		dto.setTitle(question.getTitle());
		dto.setQuestion(question.getQuestion());
		dto.setLink("/question/" + question.getId());
		return dto;
	}



	public Question getQuestion(String id){
		Long qid = Long.parseLong(id);
		return questionDao.get(qid);
	}
	
	
	public List<QuestionDTO> getRecentQuestionsDtos(int limit){
		return this.questionsToDtos(questionDao.getRecentQuestions(limit));
	}

}

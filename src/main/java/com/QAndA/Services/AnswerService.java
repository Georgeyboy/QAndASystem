package com.QAndA.Services;

import com.QAndA.DAO.AnswerDao;
import com.QAndA.DTO.AnswerDTO;
import com.QAndA.Domain.Answer;
import com.QAndA.Domain.Question;
import com.QAndA.Domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by George on 05/03/2015.
 */

@Service
public class AnswerService {

	@Autowired
	private AnswerDao answerDao;

	@Autowired
	private CommentService commentService;


	public Answer saveAnswer(AnswerDTO dto, User user, Question question){

		Answer answer = new Answer();

		answer.setUser(user);
		answer.setQuestion(question);
		answer.setAnswer(dto.getDescription());
		answer.setDate(new Date());

		return answerDao.save(answer);
	}


	public List<AnswerDTO> answersToDtos(List<Answer> answers, long questionID){
		List<AnswerDTO> results = new ArrayList<AnswerDTO>();

		for(Answer answer : answers){
			results.add(this.answerToDto(answer, questionID));
		}

		return results;
	}

	public AnswerDTO answerToDto(Answer answer, long questionID){
		AnswerDTO result = new AnswerDTO();
		result.setId(String.valueOf(answer.getId()));
		result.setDescription(answer.getAnswer());
		result.setQuestionID(String.valueOf(questionID));
		result.setUsername(answer.getUser().getUsername());
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyy HH:mm");
		result.setDate(dateFormat.format(answer.getDate()));
		result.setComments(commentService.commentsToDto(answer.getComments()));
		return result;
	}

	public Answer getAnswer(String id){
		Long aid = Long.parseLong(id);
		return answerDao.get(aid);
	}

}

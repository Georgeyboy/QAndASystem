package com.QAndA.Services;

import com.QAndA.DAO.QuestionDao;
import com.QAndA.DTO.QuestionDTO;
import com.QAndA.DTO.SearchPacketDto;
import com.QAndA.Domain.Comment;
import com.QAndA.Domain.Question;
import com.QAndA.Domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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

	@Autowired
	private CommentService commentService;


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
		dto.setId(String.valueOf(question.getId()));
		dto.setTitle(question.getTitle());
		dto.setQuestion(question.getQuestion());
		dto.setUsername(question.getUser().getUsername());
		dto.setLink("/question/" + question.getId());
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyy HH:mm");
		dto.setDate(dateFormat.format(question.getDate()));
		List<Comment> comments = new ArrayList<Comment>();
		comments.addAll(question.getComments());
		dto.setComments(commentService.commentsToDto(comments));
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

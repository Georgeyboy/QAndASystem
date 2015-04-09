package com.QAndA.Services;

import com.QAndA.DAO.AnswerDao;
import com.QAndA.DAO.CommentDao;
import com.QAndA.DAO.QuestionDao;
import com.QAndA.DTO.CommentDTO;
import com.QAndA.Domain.Answer;
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
 * Created by George on 18/03/2015.
 */
@Service
public class CommentService {


	@Autowired
	private CommentDao commentDao;

	@Autowired
	private QuestionService questionService;

	@Autowired
	private AnswerService answerService;

	@Autowired
	private UserService userService;


	public List<CommentDTO> commentsToDto(List<Comment> comments){

		List<CommentDTO> results = new ArrayList<CommentDTO>();
		for(Comment comment : comments){
			results.add(this.commentToDto(comment));
		}

		return results;

	}

	public CommentDTO commentToDto(Comment comment){
		CommentDTO dto = new CommentDTO();
		dto.setId(String.valueOf(comment.getId()));
		dto.setComment(comment.getComment());
		dto.setTargetId(String.valueOf(comment.getTargetId()));
		dto.setUser(String.valueOf(comment.getUsername()));
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyy HH:mm");
		dto.setDate(dateFormat.format(comment.getDate()));
		User user = userService.findByUsername(comment.getUsername());
		dto.setUserRp(user.getRp());
		dto.setUserLevel(user.calculateLevel());
		return dto;
	}



	public Comment saveComment(CommentDTO dto){

		System.out.println("Saving Comment...");
		System.out.println("user: " + dto.getUser());
		System.out.println("targetId: " + dto.getTargetId());
		System.out.println("comment: " + dto.getComment());

		Comment comment =  new Comment();
		Object target = questionService.getQuestion(dto.getTargetId());
		if(target == null){
//			Target must be an answer
			target = answerService.getAnswer(dto.getTargetId());
			comment.setAnswer((Answer) target);
		}else{
//			Target must be a question
			comment.setQuestion((Question) target);
		}

		comment.setComment(dto.getComment());
		comment.setUsername(dto.getUser());
		comment.setDate(new Date());

		comment = commentDao.save(comment);
		return comment;
	}
}

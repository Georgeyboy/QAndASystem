package com.QAndA.Services;

import com.QAndA.DAO.UserDao;
import com.QAndA.DTO.AccountDto;
import com.QAndA.Domain.Answer;
import com.QAndA.Domain.Question;
import com.QAndA.Domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by George on 12/03/2015.
 */
@Service
public class AccountService {


	@Autowired
	private UserDao userDao;


	public AccountDto getAccountDto(User user){
		if(user == null){
			return null;
		}

		AccountDto dto = new AccountDto();
		dto.setUsername(user.getUsername());
		dto.setFirstName(user.getfName());
		dto.setLastName(user.getlName());


		for(Question question : user.getQuestions()){
			dto.addQuestion(question.getTitle(), "/question/" + question.getId());
		}

		for(Answer answer : user.getAnswers()){
			dto.addAnswer(answer.getQuestion().getTitle(), "/question/" + answer.getQuestion().getId());
		}

		return dto;
	}
}

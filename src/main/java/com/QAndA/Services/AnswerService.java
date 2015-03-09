package com.QAndA.Services;

import com.QAndA.DTO.AnswerDTO;
import com.QAndA.Domain.Answer;
import com.QAndA.Domain.Question;
import com.QAndA.Domain.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by George on 05/03/2015.
 */

@Service
public class AnswerService {


	public Answer saveAnswer(AnswerDTO dto, User user, Question question){


		return null;
	}


	public List<AnswerDTO> answersToDtos(List<Answer> answers, long questionID){
		List<AnswerDTO> results = new ArrayList<AnswerDTO>();

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

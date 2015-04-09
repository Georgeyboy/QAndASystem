package com.QAndA.DTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by George on 12/03/2015.
 */
public class AccountDto {

	private String username;
	private List<QuestionDTO> questions = new ArrayList<QuestionDTO>();
	private List<QuestionDTO> answers = new ArrayList<QuestionDTO>();
	private String firstName;
	private String lastName;
	private Integer rp;
	private Integer level = 0;

	public Integer getRp() {
		return rp;
	}

	public void setRp(Integer rp) {
		this.rp = rp;
		calculateLevel();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<QuestionDTO> getQuestions() {
		return questions;
	}

	public void setQuestions(List<QuestionDTO> questions) {
		this.questions = questions;
	}

	public List<QuestionDTO> getAnswers() {
		return answers;
	}

	public void setAnswers(List<QuestionDTO> answers) {
		this.answers = answers;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public void addQuestion(String title, String link){
		QuestionDTO q = new QuestionDTO();
		q.setTitle(title);
		q.setLink(link);
		this.getQuestions().add(q);
	}

	public void addAnswer(String title, String link){
		QuestionDTO a = new QuestionDTO();
		a.setTitle(title);
		a.setLink(link);
		this.getAnswers().add(a);
	}

	private void calculateLevel(){
		if(rp <= 0){
			level = 0;
		}else{

			int bracket = 10;
			int rpTemp = rp.intValue();
			do{
				rpTemp = rpTemp - bracket;
				level++;
				bracket += 10;
			}while(rpTemp > 0);
		}
	}


}


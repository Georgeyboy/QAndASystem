package com.QAndA.Domain;

import javax.persistence.*;

/**
 * Created by George on 11/02/2015.
 */

@Entity
@Table(name = "qa_answer")
public class Answer {

	@Id
	@GeneratedValue
	private long id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user")
	private User user;

	private String answer;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "question")
	private Question question;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}
}

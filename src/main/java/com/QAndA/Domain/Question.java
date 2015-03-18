package com.QAndA.Domain;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by George on 11/02/2015.
 */
@Entity
@Table(name = "qa_question")
public class Question {

	@Id
	@GeneratedValue
	private long id;

	@ManyToOne(fetch = FetchType.EAGER)
	private User user;

	private String title;
	private String question;
	//private String keywords;//TODO should search be based off of this or title?

	@OneToMany(mappedBy = "question", fetch = FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	private List<Answer> answers;

	private Date date;

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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public List<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}

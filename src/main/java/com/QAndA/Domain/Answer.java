package com.QAndA.Domain;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by George on 11/02/2015.
 */

@Entity
@Table(name = "qa_answer")
public class Answer{

	@Id
	@GeneratedValue
	private long id;

	@ManyToOne(fetch = FetchType.EAGER)
	private User user;

	private String answer;


	@ManyToOne(fetch = FetchType.EAGER)
	private Question question;

	@OneToMany(mappedBy = "answer", fetch = FetchType.EAGER)
	private List<Comment> comments;

	private Date date;

	private Integer rp;

	public Integer getRp() {
		return rp;
	}

	public void setRp(Integer rp) {
		this.rp = rp;
	}

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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}


}

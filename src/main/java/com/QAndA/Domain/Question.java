package com.QAndA.Domain;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.IndexColumn;

import javax.persistence.*;
import java.util.*;

/**
 * Created by George on 11/02/2015.
 */

@Entity
@Table(name = "qa_question")
public class Question{

	@Id
	@GeneratedValue
	private long id;

	@ManyToOne(fetch = FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	private User user;

	private String title;
	private String question;

	@OneToMany(mappedBy = "question", fetch = FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	private Set<Comment> comments = new LinkedHashSet<Comment>();

	@OneToMany(mappedBy = "question", fetch = FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	private Set<Answer> answers = new LinkedHashSet<Answer>();

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

	public Set<Comment> getComments() {
		return comments;
	}

	public void setComments(Set<Comment> comments) {
		this.comments = comments;
	}

	public Set<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(Set<Answer> answers) {
		this.answers = answers;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}

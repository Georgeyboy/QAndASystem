package com.QAndA.Domain;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by George on 18/03/2015.
 */
@Entity
@Table(name = "qa_comment")
public class Comment {

	@Id
	@GeneratedValue
	private long id;
	private String comment;
	private String username;
	private long targetId;

//	!!! WARNING !!! One of either answer or question will be null at any time
//	This should not be used to look up the question / answer when given the comment
//	Use the 'targetId' field for lookup

	@ManyToOne(fetch = FetchType.EAGER)
	private Question question;

	@ManyToOne(fetch = FetchType.EAGER)
	private Answer answer;

	private Date date;


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public long getTargetId() {
		return targetId;
	}

	public void setTargetId(long targetId) {
		this.targetId = targetId;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public Answer getAnswer() {
		return answer;
	}

	public void setAnswer(Answer answer) {
		this.answer = answer;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}

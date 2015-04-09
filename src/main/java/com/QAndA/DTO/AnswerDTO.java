package com.QAndA.DTO;

import java.util.List;

/**
 * Created by George on 05/03/2015.
 */
public class AnswerDTO {

	private String id;
	private String description;
	private String questionID;
	private String username;
	private List<CommentDTO> comments;
	private String date;
	private Integer rp;
	private Integer userRp;
	private Integer userLevel;

	public Integer getUserRp() {
		return userRp;
	}

	public void setUserRp(Integer userRp) {
		this.userRp = userRp;
	}

	public Integer getUserLevel() {
		return userLevel;
	}

	public void setUserLevel(Integer userLevel) {
		this.userLevel = userLevel;
	}

	public Integer getRp() {
		return rp;
	}

	public void setRp(Integer rp) {
		this.rp = rp;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getQuestionID() {
		return questionID;
	}

	public void setQuestionID(String questionID) {
		this.questionID = questionID;
	}


	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAnswerID() {
		return id;
	}

	public void setAnswerID(String answerID) {
		this.id = answerID;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<CommentDTO> getComments() {
		return comments;
	}

	public void setComments(List<CommentDTO> comments) {
		this.comments = comments;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
}

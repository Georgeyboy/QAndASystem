package com.QAndA.DTO;

import java.util.Date;
import java.util.List;

/**
 * Created by George on 05/03/2015.
 */


public class QuestionDTO {

	private String id;
	private String title;
	private String question;
	private String link;
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

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
}

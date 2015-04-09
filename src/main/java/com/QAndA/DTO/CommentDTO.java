package com.QAndA.DTO;

/**
 * Created by George on 18/03/2015.
 */
public class CommentDTO {

	private String targetId;
	private String id;
	private String user;
	private String comment;
	private String date;
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

	public String getTargetId() {
		return targetId;
	}

	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
}

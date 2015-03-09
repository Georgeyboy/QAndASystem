package com.QAndA.DTO;

/**
 * Created by George on 05/03/2015.
 */
public class AnswerDTO {

	private String description;

	private String questionID;

	private String userID;

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


	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}
}

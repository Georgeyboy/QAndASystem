package com.QAndA.Domain;

import javax.persistence.*;
import java.util.List;

/**
 * Created by George on 10/02/2015.
 */

@Entity
public class User {

	@Id
	@GeneratedValue
	private long id;

	private String fName;
	private String lName;
	private String email;
	private String avatarLocation;

	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
	private List<Notification> notifications;

	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
	private List<Question> questions;

	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
	private List<Answer> answers;


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getfName() {
		return fName;
	}

	public void setfName(String fName) {
		this.fName = fName;
	}

	public String getlName() {
		return lName;
	}

	public void setlName(String lName) {
		this.lName = lName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAvatarLocation() {
		return avatarLocation;
	}

	public void setAvatarLocation(String avatarLocation) {
		this.avatarLocation = avatarLocation;
	}

	public List<Notification> getNotifications() {
		return notifications;
	}

	public void setNotifications(List<Notification> notifications) {
		this.notifications = notifications;
	}

	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

	public List<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
	}
}

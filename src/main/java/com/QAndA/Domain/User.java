package com.QAndA.Domain;


import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by George on 10/02/2015.
 */

@Entity
@Table(name = "qa_user")
public class User {

	@Id
	@GeneratedValue
	private long id;

	private String fName;
	private String lName;
	private String avatarLocation;

	@Column(nullable = false)
	private String username;
	private String password;
	private boolean enabled;

	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
	private List<Notification> notifications;

	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
	private List<Question> questions;

	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
	private List<Answer> answers;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
	private Set<UserRole> userRole = new HashSet<UserRole>(0);


//	Available User Roles
	public static final int ROLE_USER = 1;
	public static final int ROLE_ADMIN = 2;


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

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Set<UserRole> getUserRole() {
		return userRole;
	}

	public void setUserRole(Set<UserRole> userRole) {
		this.userRole = userRole;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}

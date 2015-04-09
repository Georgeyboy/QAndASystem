package com.QAndA.Domain;


import javax.persistence.*;
import java.util.*;

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

//	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
//	private List<Comment> comments;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
	private Set<UserRole> userRole = new HashSet<UserRole>(0);

	private Integer rp;

	@ElementCollection
	private Map<String, Integer> voteHistory;




	//	Available User Roles
	public static final int ROLE_USER = 1;
	public static final int ROLE_ADMIN = 2;


	public Map<String, Integer> getVoteHistory() {
		return voteHistory;
	}

	public void setVoteHistory(Map<String, Integer> voteHistory) {
		this.voteHistory = voteHistory;
	}

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

	public Integer calculateLevel(){
		if(rp <= 0){
			return 0;
		}else{
			int level = 0;
			int bracket = 10;
			int rpTemp = rp.intValue();
			do{
				rpTemp = rpTemp - bracket;
				level++;
				bracket += 10;
			}while(rpTemp > 0);
			return level;
		}


	}

}

package com.QAndA.Domain;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Hibernate mapping class for user roles
 * Loosely based on the tutorial http://www.mkyong.com/spring-security/spring-security-hibernate-annotation-example/
 *
 * This class is a necessary requirement when using spring security, which must include specific tables / data to function.
 *
 */

@Entity
@Table(name = "qa_userrole")
public class UserRole {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "user_role_id",
			unique = true, nullable = false)
	private long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "username", nullable = false)
	private User user;

	@Column(name = "role", nullable = false, length = 45)
	private String role;


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

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
}

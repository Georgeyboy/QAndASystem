package com.QAndA.DAO.impl;

import com.QAndA.DAO.UserDao;
import com.QAndA.Domain.User;
import com.QAndA.Exceptions.UsernameInUseException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by George on 11/02/2015.
 */

@Repository
public class UserDaoImpl implements UserDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	@Transactional
	public User save(User user) throws UsernameInUseException {
		Session session = sessionFactory.getCurrentSession();
//		Check if user already exists
		if(this.findByUsername(user.getUsername()) != null){
//			User already exists, cannot do this!
			throw new UsernameInUseException();
		}

		user.setEnabled(true);

		String tempPass = user.getPassword();
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		user.setPassword(encoder.encode(tempPass));
		tempPass = "";

		long id = (Long) session.save(user); //TODO check this actually returns the id
		user.setId(id);

		return user;
	}

	@Override
	@Transactional
	public User get(long id) {
		return (User) sessionFactory.getCurrentSession().get(User.class, id);
	}

	@Override
	@Transactional
	public User update(User user) {
		User updatedUser = this.get(user.getId());
		System.out.println("updatedUser = " + updatedUser	 + " and id = " + user.getId());
//		updatedUser.setfName(user.getfName());
//		updatedUser.setlName(user.getlName());
//		updatedUser.setAvatarLocation(user.getAvatarLocation());
//		updatedUser.setUsername(user.getUsername());
//		updatedUser.setPassword(user.getPassword());
//		updatedUser.setEnabled(user.isEnabled());
		sessionFactory.getCurrentSession().saveOrUpdate(user);
		return user;
	}

	@Override
	@Transactional
	public boolean delete(User user) {
//		USERS DO NOT GET DELETED
//		This is to ensure questions and answers still link to a user for reference
//		Instead, users can be deactivated. This will be displayed upon viewing their account page
		return false;
	}

	@Override
	@Transactional
	public void activateAccount(User user) {
		User u = this.get(user.getId());
		u.setEnabled(true);
		sessionFactory.getCurrentSession().saveOrUpdate(u);
	}

	@Override
	@Transactional
	public void deactivateAccount(User user) {
		User u = this.get(user.getId());
		u.setEnabled(false);
		sessionFactory.getCurrentSession().saveOrUpdate(u);
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional
	public User findByUsername(String username) {
		System.out.println("Searching for user with username " + username);
		List<User> users = sessionFactory.getCurrentSession().createQuery("from User where username= :u")
				.setParameter("u", username)
				.list();
		System.out.println("Found " + users.size());

		if(users.size() != 0){
			return users.get(0);
		}else{
			return null;
		}
	}
}

package com.QAndA.DAO;

import com.QAndA.Domain.User;

/**
 * Created by George on 11/02/2015.
 */
public interface UserDao extends SuperDao<User> {

	public void activateAccount(User user);

	public void deactivateAccount(User user);


}

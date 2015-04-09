package com.QAndA.DAO;

import com.QAndA.Domain.User;
import com.QAndA.Exceptions.UsernameInUseException;

import java.util.Map;

/**
 * Created by George on 11/02/2015.
 */
public interface UserDao{

	public User save(User user) throws UsernameInUseException;

	public User get(long id);

	public User update(User user);

	public boolean delete(User user);

	public void activateAccount(User user);

	public void deactivateAccount(User user);

	public User findByUsername(String username);

	public Map<String, Integer> getVoteHistory(String username);


}

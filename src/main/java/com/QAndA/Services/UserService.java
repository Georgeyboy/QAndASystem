package com.QAndA.Services;

import com.QAndA.DAO.UserDao;
import com.QAndA.DAO.UserRoleDao;
import com.QAndA.DTO.SignUpFormDto;
import com.QAndA.Domain.User;
import com.QAndA.Domain.UserRole;
import com.QAndA.Exceptions.UsernameInUseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by George on 04/03/2015.
 */

@Service
public class UserService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private UserRoleDao userRoleDao;


	public boolean saveUser(SignUpFormDto dto){
		User user = new User();
		user.setEnabled(true);
		user.setfName(dto.getFname());
		user.setlName(dto.getLname());
		user.setUsername(dto.getUsername());
		user.setPassword(dto.getPass1());
		user.setRp(0);
//		user.setAvatarLocation(dto.getAvatarLocation());

		try {
			user = userDao.save(user);

		}catch(UsernameInUseException e){
			e.printStackTrace();
			return false;
		}

		UserRole role = new UserRole();
		role.setUser(user);
		role.setRole("USER");
		userRoleDao.save(role);
		return true;
	}


	public User findByUsername(String username){
		return userDao.findByUsername(username);
	}

	public User update(User user){
		return userDao.update(user);
	}

	public Map<String, Integer> getVoteHistory(String username){
		return userDao.getVoteHistory(username);
	}

}

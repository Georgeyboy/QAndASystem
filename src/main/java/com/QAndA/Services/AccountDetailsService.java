package com.QAndA.Services;

import com.QAndA.DAO.UserDao;
import com.QAndA.DAO.UserRoleDao;
import com.QAndA.DAO.impl.UserDaoImpl;
import com.QAndA.Domain.User;
import com.QAndA.Domain.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by George on 26/02/2015.
 */
@Service("userDetailsService")
public class AccountDetailsService implements UserDetailsService{

	@Autowired
	private UserDao userDao;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("Given username is '" + username + "'");
		User user = userDao.findByUsername(username);

		if(user == null){
			throw new UsernameNotFoundException("No user with username '" + username + "' found");
		}

		List<GrantedAuthority> authorities = getAuthorities(user.getUserRole());
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.isEnabled(), true, true, true, authorities);
	}



	private List<GrantedAuthority> getAuthorities(Set<UserRole> roles){

		Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();

		for(UserRole role : roles){
			authorities.add(new SimpleGrantedAuthority(role.getRole()));
		}

		return new ArrayList<GrantedAuthority>(authorities);
	}
}

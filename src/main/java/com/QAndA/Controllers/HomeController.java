package com.QAndA.Controllers;

import com.QAndA.DAO.UserDao;
import com.QAndA.Domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
* Created by George on 10/02/2015.
*/
@Controller
public class HomeController {

	@Autowired
	private UserDao userDao;

	public HomeController(){
		System.out.println("HomeController found!");
	}

	@RequestMapping("/")
	public String index() {
		System.out.println("HomeController '/' hit");
		return "index";
	}

	@RequestMapping("/test")
	public String test(){
		System.out.println("HomeController '/test' hit");
		return "test";
	}

	@RequestMapping("/addTestUser")
	public String addTestUser(){
		System.out.println("Adding test user");
		User user = new User();
		user.setfName("George");
		user.setlName("Harris");
		userDao.save(user);
		return "test";
	}


}

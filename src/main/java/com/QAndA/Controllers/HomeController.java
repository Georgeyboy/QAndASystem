package com.QAndA.Controllers;

import com.QAndA.DAO.UserDao;
import com.QAndA.DAO.UserRoleDao;
import com.QAndA.DTO.SignUpFormDto;
import com.QAndA.Domain.User;
import com.QAndA.Domain.UserRole;
import com.QAndA.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

/**
* Created by George on 10/02/2015.
*/
@Controller
public class HomeController {

	@Autowired
	private UserDao userDao;

	@Autowired
	private UserRoleDao userRoleDao;

	@Autowired
	private UserService userService;



	@ModelAttribute("loggedIn")
	public boolean loggedIn(){
		Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(user instanceof String){
//			No logged in user
			return false;
		}else{
//			Assume there is a logged in user?
			return true;
		}
	}

	@ModelAttribute("currentUser")
	public String username(){
		Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(user instanceof String){
//			No logged in user
			return "NO USER";
		}else{
			org.springframework.security.core.userdetails.User u = (org.springframework.security.core.userdetails.User) user;
			System.out.println("username = " + u.getUsername());
			return u.getUsername();
		}
	}

	@RequestMapping("/")
	public String index() {
		System.out.println("HomeController '/' hit");
		return "index";
	}

	@RequestMapping("/test")
	public String test(final Model model){
		System.out.println("HomeController '/test' hit");
		return "test";
	}

	@RequestMapping("/addTestUser")
	public String addTestUser(){
		System.out.println("Adding test user");
		User user = new User();
		user.setfName("George");
		user.setlName("Harris");
		user.setPassword("g");
		user.setUsername("g");
		try {
			user = userDao.save(user);
		}catch(Exception e){
			e.printStackTrace();
		}

		UserRole adminRole = new UserRole();
		adminRole.setUser(user);
		adminRole.setRole("ADMIN");
		userRoleDao.save(adminRole);
//		UserRole admin = userRoleDao.findByRole("ADMIN");
//		UserRole userRole = userRoleDao.findByRole("USER");
//		if(admin == null){
//			admin = new UserRole();
//			admin.setRole("ADMIN");
//			admin.setUser(user);
//			userRoleDao.save(admin);
//			user.getUserRole().add(userRoleDao.findByRole("ADMIN"));
//		}else{
//			user.getUserRole().add(userRoleDao.findByRole("ADMIN"));
//		}
//		if(userRole == null){
//			userRole = new UserRole();
//			userRole.setRole("USER");
//			userRole.setUser(user);
//			userRoleDao.save(userRole);
//			user.getUserRole().add(userRoleDao.findByRole("USER"));
//		}else{
//			user.getUserRole().add(userRoleDao.findByRole("USER"));
//		}

		return "test";
	}


	@RequestMapping(value = "/signUp")
	public String signUp(final SignUpFormDto dto, final ModelMap model){
		model.addAttribute("dto", dto);


		return "signUp";
	}

	/**
	 * For when a user submits a signup form
	 * @param model
	 * @return redirect to home page
	 */
	@RequestMapping(value = "/signUp", method = RequestMethod.POST)
	public String signUpSubmit(final SignUpFormDto dto, final ModelMap model, final BindingResult bindingResult){

		System.out.println("User Form submitted!");
		List<String> signUpSuccess = new ArrayList<String>();
		List<String> signUpFail = new ArrayList<String>();
		boolean formPassed = true;


//		Form validation
//		Username
		if(userDao.findByUsername(dto.getUsername()) != null){
			signUpFail.add("Username already in use! Try another username");
			formPassed = false;
		}else if(dto.getUsername().length() < 1){
			signUpFail.add("Username cannot be empty");
			formPassed = false;
		}

//		First Name
		if(dto.getFname().length() < 1){
			signUpFail.add("Your name cannot be blank");
			formPassed = false;
		}

//		Last Name
		if(dto.getLname().length() < 1){
			signUpFail.add("Your name cannot be blank");
			formPassed = false;
		}

//		Password
		if(dto.getPass1().length() < 1 | dto.getPass2().length() < 1){
			signUpFail.add("Please enter a matching password");
			formPassed = false;
		}else if(!dto.getPass1().equals(dto.getPass2())){
			signUpFail.add("Passwords do not match!");
			formPassed = false;
		}

		if(formPassed) {
//			Form has passed!
			userService.saveUser(dto);
			signUpSuccess.add("New account successfully created!");
		}

		model.addAttribute("signUpSuccess", signUpSuccess);
		model.addAttribute("signUpFail", signUpFail);
		model.addAttribute("dto", dto);

		return "signUp";
	}


	@RequestMapping("/secure")
	public String securePage(final Model model){
		model.addAttribute("message", "This page is secure");
		return "index";
	}



}

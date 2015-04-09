package com.QAndA.Controllers;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by George on 03/04/2015.
 */

@Controller
@RequestMapping("/")
public class MainController {

	public MainController(){
		System.out.println("MainController initialized");
	}

	@ModelAttribute("loggedIn")
	public boolean loggedIn(){
		System.out.println("MCloggedIn()");
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
		System.out.println("MCuserName()");
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


	@RequestMapping("")
	public String mainRedirect(final Model model){
		model.asMap().clear();
		return "redirect:/basic/";
	}

	@RequestMapping("version")
	public String login(){
		return "version";
	}
}

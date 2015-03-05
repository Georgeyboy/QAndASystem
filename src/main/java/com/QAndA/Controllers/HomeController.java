package com.QAndA.Controllers;

import com.QAndA.DAO.UserDao;
import com.QAndA.DAO.UserRoleDao;
import com.QAndA.DTO.QuestionDTO;
import com.QAndA.DTO.SignUpFormDto;
import com.QAndA.Domain.Question;
import com.QAndA.Domain.User;
import com.QAndA.Domain.UserRole;
import com.QAndA.Services.QuestionService;
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
	private UserService userService;

	@Autowired
	private QuestionService questionService;



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
	public String index(final Model model,
					   @RequestParam(value = "loginerror", required = false) String error){

		List<String> loginerrors = new ArrayList<String>();

		if(error != null){
			loginerrors.add(error);
		}

		model.addAttribute("loginerror", loginerrors);
		return "index";
	}


	@RequestMapping(value = "/signUp")
	public String signUp(final SignUpFormDto dto, final ModelMap model){
		model.addAttribute("dto", dto);


		return "signup";
	}

	/**
	 * For when a user submits a signup form
	 * @param model
	 * @return redirect to home page
	 */
	@RequestMapping(value = "/signUp", method = RequestMethod.POST)
	public String signUpSubmit(final SignUpFormDto dto, final ModelMap model){

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

		return "signup";
	}


	@RequestMapping("/askQuestion")
	public String askQuestion(final Model model, final QuestionDTO dto){
		System.out.println("Ask question hit");
		model.addAttribute("dto", dto);

		return "askquestion";
	}

	@RequestMapping(value = "/askQuestion", method = RequestMethod.POST)
	public String askQuestionSubmit(final Model model, final QuestionDTO dto){

//		Validation
		List<String> submitFail = new ArrayList<String>();
		boolean submitPasssed = true;

		if(dto.getTitle().length() < 1){
			submitFail.add("Title cannot be empty. Please provide a descriptive title so that other users can help you best.");
			submitPasssed = false;
		}

		if(dto.getQuestion().length() < 1){
			submitFail.add("Question cannot be empty - please provide a question!");
			submitPasssed = false;
		}

		if(submitPasssed) {
//			Form has passed!
			String username = this.username();
			User user = userService.findByUsername(username);
			Question question = questionService.saveQuestion(dto, user);
//			TODO:Redirect to question page using /question/{questionId}
			return "redirect:/";
		}

		model.addAttribute("submitFail", submitFail);
		model.addAttribute("dto", dto);

		return "askquestion";
	}


}

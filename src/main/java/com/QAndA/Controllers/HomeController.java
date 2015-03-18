package com.QAndA.Controllers;

import com.QAndA.DAO.UserDao;
import com.QAndA.DAO.UserRoleDao;
import com.QAndA.DTO.AccountDto;
import com.QAndA.DTO.AnswerDTO;
import com.QAndA.DTO.QuestionDTO;
import com.QAndA.DTO.SignUpFormDto;
import com.QAndA.Domain.Answer;
import com.QAndA.Domain.Question;
import com.QAndA.Domain.User;
import com.QAndA.Domain.UserRole;
import com.QAndA.Services.AccountService;
import com.QAndA.Services.AnswerService;
import com.QAndA.Services.QuestionService;
import com.QAndA.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

	@Autowired
	private AnswerService answerService;

	@Autowired
	private AccountService accountService;



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
			return "redirect:/question/" + question.getId();
		}

		model.addAttribute("submitFail", submitFail);
		model.addAttribute("dto", dto);

		return "askquestion";
	}



	@RequestMapping(value = "/question/{questionID}", method = RequestMethod.GET)
	public String viewQuestion(final Model model,
							   @PathVariable String questionID){
		System.out.println("View Question hit");

		Question question = questionService.getQuestion(questionID);
		if(question == null){
//			TODO: Return question error page / invalid question etc
			return "redirect:/";
		}

		model.addAttribute("questionTitle", question.getTitle());
		model.addAttribute("questionDescription", question.getQuestion());
		model.addAttribute("questionUser", question.getUser().getUsername());
//		model.addAttribute("questionDate", question.getDate()); //TODO format date and use in page

		List<Answer> answers = question.getAnswers();
		Set<AnswerDTO> answerDTOs = answerService.answersToDtos(answers, question.getId());


		AnswerDTO submitAnswer = new AnswerDTO();
		submitAnswer.setQuestionID(questionID);

		model.addAttribute("dto", submitAnswer);
		model.addAttribute("answers", answerDTOs);

		return "question";
	}


	@RequestMapping(value = "/postAnswer", method = RequestMethod.POST)
	public String postAnswer(final Model model, final AnswerDTO dto){
		System.out.println("user: " + dto.getUserID());

		Answer answer = answerService.saveAnswer(dto, userService.findByUsername(username()), questionService.getQuestion(dto.getQuestionID()));

		return "redirect:/question/" + dto.getQuestionID();
	}


	//TODO validate user signup form to ensure username etc doesnt have special characters
	@RequestMapping(value = "/user/{username}", method= RequestMethod.GET)
	public String viewUser(final Model model, @PathVariable String username){

		AccountDto dto;

		User user = userDao.findByUsername(username);
		if(user == null){
//			TODO cannot find user page
			return "redirect:/";
		}

		dto = accountService.getAccountDto(user);

		model.addAttribute("dto", dto);

		return "account";
	}



	@RequestMapping(value = "/recentQuestions", method = RequestMethod.GET)
	public String viewTopQuestions(final Model model){

		model.addAttribute("questions", questionService.getRecentQuestionsDtos(10));

		return "recentQuestions";

	}


}

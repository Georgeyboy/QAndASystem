package com.QAndA.Controllers;

import com.QAndA.DAO.UserDao;
import com.QAndA.DTO.*;
import com.QAndA.Domain.Answer;
import com.QAndA.Domain.Question;
import com.QAndA.Domain.User;
import com.QAndA.Services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

/**
* Created by George on 10/02/2015.
*/
@Controller
@RequestMapping("/basic")
public class BasicController {

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

	@Autowired
	private CommentService commentService;

	@Autowired
	private SearchService searchService;

	private SearchPacketDto layoverPacket;



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

	@ModelAttribute("blankSearchPacket")
	public SearchPacketDto getBlankSearchPacket(){
		return new SearchPacketDto();
	}



	@RequestMapping("")
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
		System.out.println("Question: " + dto.getQuestion());
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
			String username = username();
			User user = userService.findByUsername(username);
			Question question = questionService.saveQuestion(dto, user);
			model.asMap().clear();
			return "redirect:/basic/question/" + question.getId();
		}

		model.addAttribute("submitFail", submitFail);
		model.addAttribute("dto", dto);

		return "askquestion";
	}



	@RequestMapping(value = "/question/{questionID}", method = RequestMethod.GET)
	public String viewQuestion(final Model model,
							   @PathVariable String questionID,
							   final RedirectAttributes redirectAttributes){
		System.out.println("View Question hit");

		Question question = questionService.getQuestion(questionID);
		if(question == null){
			String[] errors = {"Question not found. It may have been removed"};
			model.addAttribute("errors", errors);
			return "error";
		}

		QuestionDTO questionDto = questionService.questionToDto(question);
		model.addAttribute("question", questionDto);



		List<Answer> answers = new ArrayList<Answer>();
		answers.addAll(question.getAnswers());
		List<AnswerDTO> answerDTOs = answerService.answersToDtos(answers, question.getId());

		AnswerDTO submitAnswer = new AnswerDTO();
		submitAnswer.setQuestionID(questionID);

		model.addAttribute("dto", submitAnswer);
		model.addAttribute("answers", answerDTOs);
		model.addAttribute("commentDto", new CommentDTO());

		return "question";
	}

	@RequestMapping(value = "/postComment", method= RequestMethod.POST)
	public String postComment(final Model model, @RequestParam("targetId") String targetId,
							  @RequestParam("commentText") String comment,
							  @RequestParam("commentUsername") String username,
							  @RequestParam("questionId") String questionId){
		CommentDTO dto = new CommentDTO();
		dto.setComment(comment);
		dto.setUser(username);
		dto.setTargetId(targetId);

		commentService.saveComment(dto);
		model.asMap().clear();
		return "redirect:/basic/question/" + questionId;
	}


	@RequestMapping(value = "/postAnswer", method = RequestMethod.POST)
	public String postAnswer(final Model model, final AnswerDTO dto){
		System.out.println("user: " + dto.getUsername());

		Answer answer = answerService.saveAnswer(dto, userService.findByUsername(username()), questionService.getQuestion(dto.getQuestionID()));
		model.asMap().clear();
		return "redirect:/basic/question/" + dto.getQuestionID();
	}


	//TODO validate user signup form to ensure username etc doesnt have special characters
	@RequestMapping(value = "/user/{username}", method= RequestMethod.GET)
	public String viewUser(final Model model, @PathVariable String username){

		AccountDto dto;

		User user = userDao.findByUsername(username);
		if(user == null){
			String[] errors = {"User not found. If you followed a link, the user may have been removed / deleted. If not, try checking the spelling of the username and try again."};
			model.addAttribute("errors", errors);
			return "error";
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

//	/**
//	 * Custom Error page
//	 * To use this efficiently, make sure to add model attributes to a 'redirectAttributes' model and then redirect to '/error'
//	 * @return
//	 */
//	@RequestMapping(value = "/error", method = RequestMethod.GET)
//	public String error(){
//		return "error";
//	}

	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public String initialSearch(final Model model, @RequestParam("query") String query){
		System.out.println("/search hit!");

		SearchPacketDto resultPacket = searchService.searchPacketToDto(searchService.initialSearch(query));

		layoverPacket = resultPacket;
		model.asMap().clear();
		return "redirect:/basic/searchResults/1";
	}

	@RequestMapping(value = "/searchResults/{pageNumber}", method = RequestMethod.GET)
	public String getSearchPage(final Model model, @PathVariable String pageNumber){
		System.out.println("/searchResults/" + pageNumber + " hit!");
		SearchPacketDto searchPacket = new SearchPacketDto();
		searchPacket.setQuery(layoverPacket.getQuery());
		searchPacket.setPageNumber(Integer.parseInt(pageNumber));
		searchPacket.setMaxPages(layoverPacket.getMaxPages());
		searchPacket.setResultsPerPage(layoverPacket.getResultsPerPage());
		searchPacket.setResults(questionService.questionsToDtos(searchService.getSearchResultsPage(searchService.dtoToSearchPacket(searchPacket))));
		System.out.println("Search found " + searchPacket.getResults().size() + " results");
		model.addAttribute("searchPacket", searchPacket);


		return "searchResults";
	}

}

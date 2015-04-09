package com.QAndA.Controllers;

import com.QAndA.DAO.UserDao;
import com.QAndA.DTO.*;
import com.QAndA.Domain.Answer;
import com.QAndA.Domain.Question;
import com.QAndA.Domain.User;
import com.QAndA.Services.*;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by George on 10/02/2015.
 */
@Controller
@RequestMapping("/rep")
public class AdvancedController {

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
		return "rep/indexR";
	}


	@RequestMapping(value = "/signUp")
	public String signUp(final SignUpFormDto dto, final ModelMap model){
		model.addAttribute("dto", dto);


		return "rep/signupR";
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

		return "rep/signupR";
	}


	@RequestMapping("/askQuestion")
	public String askQuestion(final Model model, final QuestionDTO dto){
		System.out.println("Ask question hit");
		model.addAttribute("dto", dto);

		return "rep/askquestionR";
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
			return "redirect:/rep/question/" + question.getId();
		}

		model.addAttribute("submitFail", submitFail);
		model.addAttribute("dto", dto);

		return "rep/askquestionR";
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
			return "rep/errorR";
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

		return "rep/questionR";
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
		return "redirect:/rep/question/" + questionId;
	}


	@RequestMapping(value = "/postAnswer", method = RequestMethod.POST)
	public String postAnswer(final Model model, final AnswerDTO dto){
		System.out.println("user: " + dto.getUsername());

		Answer answer = answerService.saveAnswer(dto, userService.findByUsername(username()), questionService.getQuestion(dto.getQuestionID()));
		model.asMap().clear();
		return "redirect:/rep/question/" + dto.getQuestionID();
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
		model.addAttribute("alerts", new String[0]);
		model.addAttribute("dto", dto);

		return "rep/accountR";
	}

	@RequestMapping(value = "/recentQuestions", method = RequestMethod.GET)
	public String viewTopQuestions(final Model model){

		model.addAttribute("questions", questionService.getRecentQuestionsDtos(10));

		return "rep/recentQuestionsR";
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
		return "redirect:/rep/searchResults/1";
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


		return "rep/searchResultsR";
	}

	@RequestMapping(value = "/deleteQuestion/{id}")
	public String deleteQuestion(final Model model, @PathVariable String id){

		Question question = questionService.getQuestion(id);

		if(!question.getUser().getUsername().equals(username())){
//			User who initiated this method is not the question owner - therefore does not have permission to delete
			String[] errors = {"You do not have permission to delete this question. Only the user who asked the question originally can delete it."};
			model.addAttribute("errors", errors);
			return "rep/errorR";
		}


		questionService.deleteQuestion(id);

		AccountDto dto;
		User user = userDao.findByUsername(username());
		if(user == null){
			String[] errors = {"User not found. If you followed a link, the user may have been removed / deleted. If not, try checking the spelling of the username and try again."};
			model.addAttribute("errors", errors);
			return "rep/errorR";
		}

		dto = accountService.getAccountDto(user);
		String[] alerts = {"You successfully deleted a question"};
		model.addAttribute("alerts", alerts);
		model.addAttribute("dto", dto);
		return "rep/accountR";
	}

	@RequestMapping("/loginfail")
	public String loginFail(final Model model){
		String[] loginerrors = {"Unable to log in. Please check your username and password and try again."};
		model.addAttribute("loginerror", loginerrors);
		return "rep/indexR";
	}


	@RequestMapping("/voteQuestion")
	@ResponseBody
	public String voteQuestion(@RequestParam(value = "user", required = true) String username,
							   @RequestParam(value = "question", required = true) String questionId,
							   @RequestParam(value = "value", required = true) String value){

		User user = userService.findByUsername(username);

		Question question = questionService.getQuestion(questionId);

		if(user == null){
			JSONObject result = new JSONObject();
			result.put("success", false);
			result.put("message", "You must be logged in to vote");
			return result.toJSONString();
		}

		if(user.getUsername().equals(question.getUser().getUsername())){
//			User cannot vote on their own question
			JSONObject result = new JSONObject();
			result.put("success", false);
			result.put("message", "You cannot upvote or downvote your own question.");
			return result.toJSONString();
		}

		Map<String, Integer> voteHistory = userService.getVoteHistory(username);

		Integer valueInt = Integer.parseInt(value);
		if(voteHistory.containsKey(questionId)){
//			User has voted on this before

			if(valueInt == voteHistory.get(questionId)){
//				User has given the same vote before - do not allow another vote
				JSONObject result = new JSONObject();
				result.put("success", false);
				result.put("message", "You cannot upvote or downvote more than once per question. ");
				return result.toJSONString();
			}else{
//				User has voted in the opposite direction - flip vote
				voteHistory.remove(questionId);
				voteHistory.put(questionId, valueInt);
				question.setRp(question.getRp() + (2*valueInt)); //Vote has been changed from +1 to -1, therefore question loses 2 points
			}
		}else{
//			User has not voted before
			voteHistory.put(questionId, valueInt);
			question.setRp(question.getRp() + valueInt);
		}

		user.setVoteHistory(voteHistory);
		userService.update(user);
		questionService.update(question);

		JSONObject result = new JSONObject();
		result.put("success", true);
		result.put("score", question.getRp());

//		Reward question asker
		User asker = question.getUser();
		asker.setRp(asker.getRp() + (2 * valueInt));
		userService.update(asker);

		return result.toJSONString();
	}

	@RequestMapping("/voteAnswer")
	@ResponseBody
	public String voteAnswer(@RequestParam(value = "user", required = true) String username,
							   @RequestParam(value = "answer", required = true) String answerId,
							   @RequestParam(value = "value", required = true) String value){

		User user = userService.findByUsername(username);

		Answer answer = answerService.getAnswer(answerId);

		if(user == null){
			JSONObject result = new JSONObject();
			result.put("success", false);
			result.put("message", "You must be logged in to vote.");
			return result.toJSONString();
		}

		if(user.getUsername().equals(answer.getUser().getUsername())){
//			User cannot vote on their own question
			JSONObject result = new JSONObject();
			result.put("success", false);
			result.put("message", "You cannot upvote or downvote your own answer.");
			return result.toJSONString();
		}

		Map<String, Integer> voteHistory = userService.getVoteHistory(username);

		Integer valueInt = Integer.parseInt(value);
		if(voteHistory.containsKey(answerId)){
//			User has voted on this before

			if(valueInt == voteHistory.get(answerId)){
//				User has given the same vote before - do not allow another vote
				JSONObject result = new JSONObject();
				result.put("success", false);
				result.put("message", "You cannot upvote or downvote more than once per answer. ");
				return result.toJSONString();
			}else{
//				User has voted in the opposite direction - flip vote
				voteHistory.remove(answerId);
				voteHistory.put(answerId, valueInt);
				answer.setRp(answer.getRp() + (2*valueInt)); //Vote has been changed from +1 to -1, therefore answer loses 2 points
			}
		}else{
//			User has not voted before
			voteHistory.put(answerId, valueInt);
			answer.setRp(answer.getRp() + valueInt);
		}

		user.setVoteHistory(voteHistory);
		userService.update(user);
		answerService.update(answer);

		JSONObject result = new JSONObject();
		result.put("success", true);
		result.put("score", answer.getRp());

//		Reward answerer
		User answerer = answer.getUser();
		answerer.setRp(answerer.getRp() + (2 * valueInt));
		userService.update(answerer);

		return result.toJSONString();
	}

}

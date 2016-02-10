package com.sameer.spring;
/**
 * @author misgersam
 */
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sameer.spring.model.Questions;
import com.sameer.spring.model.TestDetails;
import com.sameer.spring.model.TestResult;
import com.sameer.spring.model.User;
import com.sameer.spring.model.UserAnswers;
import com.sameer.spring.service.QuestionsService;
import com.sameer.spring.service.TestResultService;
import com.sameer.spring.service.TestService;
import com.sameer.spring.service.UserAnswersService;
import com.sameer.spring.service.UserService;

@Controller
public class ExamController {
	
	@Autowired
	private QuestionsService questionsService;
	@Autowired
	private TestService testService;
	@Autowired
	private UserAnswersService userAnswersService;
	@Autowired
	private TestResultService testResultService;
	@Autowired
	private UserService userService;
	private static final Logger logger = LoggerFactory.getLogger(ExamController.class);
	
	/**
	 * invoked when user clicks start exam button this will redirect him to questions page
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/beginExam")
    public String success(Model model, HttpServletRequest request){
		String userName = (String)request.getSession().getAttribute("userName");
		TestDetails testDetails = testService.getTestById(1);
		if(userName==null){					// if user is not found in session we will redirect him to login
			return "redirect:/login";
		}
		if(!testResultService.checkTestValidationForUser(userName, testDetails.getTestDuration())){	//if user has already taken exam
			return "redirect:/finishExam";
		}
		if(testResultService.getTestResultByUser(userName) == null){	//making an entry that user has started exam
			TestResult testResult = new TestResult();
			testResult.setUserId(userName);
			testResult.setStartTime(String.valueOf(System.currentTimeMillis()));
			testResult.setTestCompleated("false");
			testResultService.addTestResult(testResult);
		}
		int duration = testResultService.getSecondsLeft(userName, testDetails.getTestDuration()); //getting no of seconds left
		List<Questions> questionList = questionsService.listQuestion();
		model.addAttribute("questionList", questionList);
		model.addAttribute("testDetails", testDetails);
		model.addAttribute("duration", duration);
        return "questions";
    }	
	/**
	 * returns a question for id that user has choosen 
	 * @param questionId
	 * @param request
	 * @param response
	 * @throws JSONException
	 */
	@RequestMapping("/getQuestion")
    public void getQuestion(@RequestParam(value = "questionId") String questionId, HttpServletRequest request, HttpServletResponse response) throws JSONException{
		JSONObject object = new JSONObject();
		String userName = (String)request.getSession().getAttribute("userName");
		Questions question = questionsService.getQuestionById(Integer.parseInt(questionId));
		object.put("questionId", question.getId());
		object.put("question", question.getQuestion());
		object.put("option1", question.getOption1());
		object.put("option2", question.getOption2());
		object.put("option3", question.getOption3());
		object.put("option4", question.getOption4());
		UserAnswers userAnswer = userAnswersService.getUserAnswerByQuestionByIdAnduserName(question.getId(), userName);
		if(userAnswer != null)	//if user has already answered a question we will pass the answer with the question as well
			object.put("useranswer", userAnswer.getAnswer());
		try {
			response.getWriter().write(object.toString());
		} catch (IOException e) {
			logger.error("ExamController: error writing output "+e);
		}
    }
	/**
	 * when user submits an answer for the question, answer will be updated if he has previously answered the question
	 * @param questionId
	 * @param answer
	 * @param request
	 * @param response
	 * @throws JSONException
	 */
	@RequestMapping("/submitAnswer")
    public void submitAnswer(@RequestParam(value = "questionId") String questionId,
    		@RequestParam(value = "answer") String answer,
    		HttpServletRequest request, HttpServletResponse response) throws JSONException{
		String userName = (String)request.getSession().getAttribute("userName");
		JSONObject object = new JSONObject();
		UserAnswers userAnswer = userAnswersService.getUserAnswerByQuestionByIdAnduserName(Integer.parseInt(questionId), userName);
		if(userAnswer == null){			// if user has not answered a question
			userAnswer = new UserAnswers();
			userAnswer.setAnswer(answer);
			userAnswer.setQuestionId(Integer.parseInt(questionId));
			userAnswer.setUserName(userName);
			object.put("msg", "Answer Submitted for question successfully");
			userAnswersService.addUserAnswer(userAnswer);
		}else{			//if user has answered a question
			userAnswer.setAnswer(answer);
			userAnswersService.updateUserAnswer(userAnswer);
			object.put("msg", "Answer Updated for question");
		}
		
		object.put("questionId", questionId);
		try {
			response.getWriter().write(object.toString());
		} catch (IOException e) {
			logger.error("ExamController: error writing output "+e);
		}
    }
	/**
	 * this method checks whether user has answered all the questions or not and returns status as success or failure 
	 * @param request
	 * @param response
	 * @throws JSONException
	 */
	@RequestMapping("/checkAllAnswered")
    public void checkAllAnswered(HttpServletRequest request, HttpServletResponse response) throws JSONException{
		String userName = (String)request.getSession().getAttribute("userName");
		JSONObject object = new JSONObject();
		List<UserAnswers> userAnswer = userAnswersService.getUserAnswers(userName);
		List<Questions> questionList = questionsService.listQuestion();
		String unansweredQuestions = "";
		for (Questions questions : questionList) {
			UserAnswers answer = userAnswersService.getUserAnswerByQuestionByIdAnduserName(questions.getId(), userName);
			if(answer == null){
				unansweredQuestions += "".equalsIgnoreCase(unansweredQuestions)?"":", ";
				unansweredQuestions += questions.getId();
			}
		}
		if(userAnswer.size() == questionList.size()){
			object.put("status", "success");
		}else{
			object.put("status", "failure");
			object.put("msg", "You have not attempted all the questions, would you like to end anyway?");
			object.put("unansweredquestions", unansweredQuestions);
		}
		try {
			response.getWriter().write(object.toString());
		} catch (IOException e) {
			logger.error("ExamController: error writing output "+e);
		}
    }
	/**
	 * this method will finish the exam and automatic grading will be done 
	 * @param request
	 * @param response
	 * @return
	 * @throws JSONException
	 */
	@RequestMapping("/finishExam")
    public String finishExam(HttpServletRequest request, HttpServletResponse response) throws JSONException{
		String userName = (String)request.getSession().getAttribute("userName");
		if(userName==null){
			return "redirect:/login";
		}
		JSONObject object = new JSONObject();
		List<Questions> questionList = questionsService.listQuestion();
		int rightAnswers = 0;
		for (Questions questions : questionList) {
			UserAnswers answer = userAnswersService.getUserAnswerByQuestionByIdAnduserName(questions.getId(), userName);
			if(answer != null){
				if(answer.getAnswer().equalsIgnoreCase(questions.getAnswer())){
					rightAnswers++;
				}
			}
		}
		TestResult testResult = testResultService.getTestResultByUser(userName);
		testResult.setFinishTime(String.valueOf(System.currentTimeMillis()));
		testResult.setScore(rightAnswers);
		testResult.setUserId(userName);
		testResult.setTestCompleated("true");
		testResultService.updateTestResult(testResult);
		return "redirect:/result";
    }
	/**
	 * this method will display the result page to the user
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws JSONException
	 */
	@RequestMapping("/result")
    public String result(Model model, HttpServletRequest request, HttpServletResponse response) throws JSONException{
		String userName = (String)request.getSession().getAttribute("userName");
		if(userName==null){
			return "redirect:/login";
		}
		TestResult result = testResultService.getTestResultByUser(userName);
		if(result == null){
			return "redirect:/success";
		}
		List<Questions> questions = questionsService.listQuestion();
		float percentage = (result.getScore()/(float)questions.size())*100;
		User user = userService.getUserById(userName);
		model.addAttribute("user", user);
		model.addAttribute("percentage", String.valueOf(percentage));
		model.addAttribute("totalQuestions", questions.size());
		model.addAttribute("result",result);
		return "result";
    }
	@RequestMapping("/checkTestValidity")
    public void checkTestValidity(HttpServletRequest request, HttpServletResponse response) throws JSONException{
		JSONObject object = new JSONObject();
		String userName = (String)request.getSession().getAttribute("userName");
		TestDetails testDetails = testService.getTestById(1);
		int duration = testResultService.getSecondsLeft(userName, testDetails.getTestDuration());
		if(duration == 0){
			object.put("status", "expired");
			object.put("msg", "You time elapsed, we will end the exam!");
		}else{
			object.put("status", "valid");
		}
		try {
			response.getWriter().write(object.toString());
		} catch (IOException e) {
			logger.error("ExamController: error writing output "+e);
		}
    }
}

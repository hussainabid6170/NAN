package com.sameer.spring;
/**
 * @author misgersam
 */
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.sameer.spring.model.TestDetails;
import com.sameer.spring.model.TestResult;
import com.sameer.spring.model.User;
import com.sameer.spring.service.TestResultService;
import com.sameer.spring.service.TestService;
import com.sameer.spring.service.UserService;

@Controller
public class LoginController {
	
	@Autowired
	private UserService	userService;
	@Autowired
	private TestResultService testResultService;
	@Autowired
	private TestService testService;
	
	/**
	 * this will be invoked first and will return login page or success page 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping({"/", "/login"})
	public String login(Model model, HttpServletRequest request) {
		String userName = (String)request.getSession().getAttribute("userName");
		
		if(userName != null){			//if user is already logged in
			return "redirect:/success";
		}
		model.addAttribute("status", "invalid");
		return "login";
	}
	/**
	 * this method will validate user credentials with database
	 * @param username
	 * @param password
	 * @param request
	 * @return
	 */
	@RequestMapping(value= "/login/validate", method = RequestMethod.POST)
	public String validate(@RequestParam(value = "username") String username,
            @RequestParam(value = "password") String password, HttpServletRequest request){
		boolean isValid = userService.validateUser(username, password);
		request.getSession().setAttribute("userName", username);
		if(isValid){		//if user is valid redirects to success
			return "redirect:/success";
		}
		return "redirect:/loginFailed";
	}
	
	/**
	 * invoked when user credentails are valid
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/success")
    public String success(Model model, HttpServletRequest request){
		String userName = (String)request.getSession().getAttribute("userName");
		User user = userService.getUserById(userName);
		TestDetails testDetails = testService.getTestById(1);
		if(user==null){		// if user not found in database
			return "redirect:/login";
		}
		
		if(!testResultService.checkTestValidationForUser(userName, testDetails.getTestDuration())){			// if user has take an exam already
			return "redirect:/finishExam";
		}
		TestResult result = testResultService.getTestResultByUser(userName);		// if users exam has started but not finished
		if(result != null){
			return "redirect:/beginExam";
		}
		model.addAttribute("user", user);
		model.addAttribute("testDetails", testDetails);
		model.addAttribute("status", "valid");
        return "login";
    }
	/**
	 * if login fails
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/loginFailed")
    public String failure(Model model, HttpServletRequest request){
		request.getSession().removeAttribute("userName");
		model.addAttribute("status", "invalid");
		model.addAttribute("msg", "Login Failed, Please make sure you have entered a valid credentials.");
        return "login";
    }
	
}

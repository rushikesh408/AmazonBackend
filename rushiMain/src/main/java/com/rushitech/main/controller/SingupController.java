package com.rushitech.main.controller;

import java.util.HashMap;
import java.util.Map;

import javax.print.attribute.standard.JobKOctets;
import javax.security.auth.Subject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.context.Context;

import com.rushitech.main.entity.SignupUsers;
import com.rushitech.main.pojo.UserLogin;
import com.rushitech.main.pojo.UserSingup;
import com.rushitech.main.service.EmailService;
import com.rushitech.main.service.UserService;

import jakarta.mail.MessagingException;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/user")
public class SingupController {

		
	@Autowired
	UserService userService;
	@Autowired
	EmailService emailService;
	
	@PostMapping("/signup")
	public ResponseEntity<Map<String, Object>> usersignup(@Valid @RequestBody UserSingup userSingup) throws Exception {

		SignupUsers resObject= userService.userSignupService(userSingup);
		
		Map<String, Object> signupMap = new HashMap<String,Object>();
		
		signupMap.put("result", "success");
		signupMap.put("data", resObject);
		
		return ResponseEntity.status(HttpStatus.OK).body(signupMap);
		

	}
	
	@GetMapping("/login")
	public ResponseEntity<?> userLogin( @Valid @RequestBody UserLogin userLogin) throws Exception{
		SignupUsers resObject= userService.userLoginService(userLogin);

		Map<String, Object> loginMap = new HashMap<String,Object>();
		
		loginMap.put("result", "success");
		loginMap.put("data", resObject);
		
		return ResponseEntity.status(HttpStatus.OK).body(loginMap);
		
	}
	
	@GetMapping("/send-email")
	public ResponseEntity<?> sendEmail(){
		String fromEmail = "vrushikesh2506@gmail.com";
		String toEmail = "birlachandhana@gmail.com";
		String subject = "This is from Spring boot";
		String mailbody="Hello my World";
		
		emailService.sendEmail(fromEmail,toEmail,subject,mailbody);
		
		Map<String, String> respoMap = new HashMap<String,String>();
		respoMap.put("result", "email sent");
		respoMap.put("message", "success");
		
		return ResponseEntity.status(HttpStatus.OK).body(respoMap);
	}
	@GetMapping("/send-emailV2")
	public ResponseEntity<?> sendEmailV2() throws MessagingException{
		 Context context = new Context();
		String fromEmail = "vrushikesh2506@gmail.com";
		String toEmail = "birlachandhana@gmail.com";
		String subject = "This is from Spring boot";
		//String mailbody="Hello my World";
		String fileName = "EmailTemplate";
		
		emailService.sendTemplateEmail(fromEmail,toEmail,subject,fileName);
		
		Map<String, String> respoMap = new HashMap<String,String>();
		respoMap.put("result", "email sent");
		respoMap.put("message", "success");
		
		return ResponseEntity.status(HttpStatus.OK).body(respoMap);
	}
	
	
	
	/* @PostMapping("/sendEmailV2")
	    public String sendEmail(@RequestBody EmailRequest emailRequest) {
	        Context context = new Context();
	        // Set variables for the template from the POST request data
	        context.setVariable("name", emailRequest.getName());
	        context.setVariable("message", emailRequest.getMessage());
	        context.setVariable("subject", emailRequest.getSubject());
	        try {
	            emailService.sendEmail(emailRequest.getTo(), emailRequest.getSubject(), "emailTemplate", context);
	            return "Email sent successfully!";
	        } catch (Exception e) {
	            return "Error sending email: " + e.getMessage();
	        }
	    }
	*/

}

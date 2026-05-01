package com.rushitech.main.controller;

import java.util.HashMap;
import java.util.Map;

import javax.print.attribute.standard.JobKOctets;
import javax.security.auth.Subject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.context.Context;

import com.rushitech.main.entity.SignupUsers;
import com.rushitech.main.pojo.ForgotPasswordApiData;
import com.rushitech.main.pojo.ResetPasswordApiData;
import com.rushitech.main.pojo.UserLogin;
import com.rushitech.main.pojo.UserSingup;
import com.rushitech.main.service.EmailService;
import com.rushitech.main.service.JWTTokenGenerator;
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
	
	@Autowired
	JWTTokenGenerator jwtTokenGenerator;

	@PostMapping("/signup")
	public ResponseEntity<Map<String, Object>> usersignup(@Valid @RequestBody UserSingup userSingup) throws Exception {

		SignupUsers resObject = userService.userSignupService(userSingup);

		Map<String, Object> signupMap = new HashMap<String, Object>();

		signupMap.put("result", "success");
		signupMap.put("data", resObject);

		return ResponseEntity.status(HttpStatus.OK).body(signupMap);

	}

	@GetMapping("/login")
	public ResponseEntity<?> userLogin(@RequestHeader("Authorization") String jwtToekn,  @Valid @RequestBody UserLogin userLogin) throws Exception {
		System.out.println();
		System.out.println();
		System.out.print(jwtToekn);
		System.out.println();
	Boolean valid = 	jwtTokenGenerator.verifyJwtToken(jwtToekn);
		System.out.println(valid);
		System.out.println();
		System.out.println();
		
		if (valid=true) {
			Map<String, Object> resObject = userService.userLoginService(userLogin);

			Map<String, Object> loginMap = new HashMap<String, Object>();

			
			
			loginMap.put("result", "success");
			loginMap.put("data", resObject);

			return ResponseEntity.
					status(HttpStatus.OK).
					header("Authorization", resObject.get("token").toString()).
					body(loginMap);

		}
		else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}
		

	}

	@PostMapping("/forgot-password")
	public ResponseEntity<Map<String, Object>> forgotPassword(
			@Valid @RequestBody ForgotPasswordApiData forgotPasswordApiData) throws Exception {

		SignupUsers forgotPasswordObjSignupUsers = userService.forgotPasswordService(forgotPasswordApiData);

		Map<String, Object> forgotPasswordMap = new HashMap<String, Object>();

		forgotPasswordMap.put("message", "success");
		forgotPasswordMap.put("result", forgotPasswordObjSignupUsers);

		return ResponseEntity.status(HttpStatus.OK).body(forgotPasswordMap);

	}

	@PostMapping("/reset-password")
	public ResponseEntity<?> resetPassword(@Valid @RequestBody ResetPasswordApiData resetPasswordApiData)
			throws Exception {

		SignupUsers resetPasswordData = userService.resetPasswordService(resetPasswordApiData);

		Map<String, Object> resetPasswordMap = new HashMap<String, Object>();

		resetPasswordMap.put("message", "success");
		resetPasswordMap.put("result", resetPasswordData);

		return ResponseEntity.status(HttpStatus.OK).body(resetPasswordMap);
	}

	@GetMapping("/send-email")
	public ResponseEntity<?> sendEmail() {
		String fromEmail = "vrushikesh2506@gmail.com";
		String toEmail = "birlachandhana@gmail.com";
		String subject = "This is from Spring boot";
		String mailbody = "Hello my World";

		emailService.sendEmail(fromEmail, toEmail, subject, mailbody);

		Map<String, String> respoMap = new HashMap<String, String>();
		respoMap.put("result", "email sent");
		respoMap.put("message", "success");

		return ResponseEntity.status(HttpStatus.OK).body(respoMap);
	}

	@GetMapping("/send-emailV2")
	public ResponseEntity<?> sendEmailV2() throws MessagingException {
		String fromEmail = "vrushikesh2506@gmail.com";
		String toEmail = "birlachandhana@gmail.com";
		String subject = "This is from Spring Boot";
		String fileName = "common-email-template";

		String userName = "Chandhana";
		String emailType = "signup";
		String actionUrl = "http://localhost:8080/login";

		emailService.sendTemplateEmail(fromEmail, toEmail, subject, fileName, userName, emailType, actionUrl);

		Map<String, String> responseMap = new HashMap<>();
		responseMap.put("result", "email sent");
		responseMap.put("message", "success");

		return ResponseEntity.status(HttpStatus.OK).body(responseMap);
	}
	/*
	 * @PostMapping("/sendEmailV2") public String sendEmail(@RequestBody
	 * EmailRequest emailRequest) { Context context = new Context(); // Set
	 * variables for the template from the POST request data
	 * context.setVariable("name", emailRequest.getName());
	 * context.setVariable("message", emailRequest.getMessage());
	 * context.setVariable("subject", emailRequest.getSubject()); try {
	 * emailService.sendEmail(emailRequest.getTo(), emailRequest.getSubject(),
	 * "emailTemplate", context); return "Email sent successfully!"; } catch
	 * (Exception e) { return "Error sending email: " + e.getMessage(); } }
	 */

}

package com.rushitech.main.controller;

import java.util.HashMap;
import java.util.Map;

import javax.print.attribute.standard.JobKOctets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.rushitech.main.entity.SignupUsers;
import com.rushitech.main.pojo.UserLogin;
import com.rushitech.main.pojo.UserSingup;
import com.rushitech.main.service.UserService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/user")
public class SingupController {

		
	@Autowired
	UserService userService;
	
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
	

}

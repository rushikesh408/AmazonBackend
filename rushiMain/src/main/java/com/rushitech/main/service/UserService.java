package com.rushitech.main.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.rushitech.main.entity.SignupUsers;
import com.rushitech.main.pojo.ForgotPasswordApiData;
import com.rushitech.main.pojo.ResetPasswordApiData;
import com.rushitech.main.pojo.UserLogin;
import com.rushitech.main.pojo.UserSingup;
import com.rushitech.main.repository.UsersRepo;

import jakarta.validation.constraints.NotNull;

@Service
public class UserService {

	@Autowired
	UsersRepo usersRepo;

	@Autowired
	EmailService emailService;

	public PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	public SignupUsers userSignupService(UserSingup userSingup) throws Exception {
		SignupUsers signupUsers = new SignupUsers();

		Optional<SignupUsers> dbemailData = usersRepo.findByEmail(userSingup.getEmail());

		if (dbemailData.isEmpty()) {
			signupUsers.setMobilenumber(userSingup.getMobilenumber());
			signupUsers.setPassword(passwordEncoder.encode(userSingup.getPassword()));
			signupUsers.setEmail(userSingup.getEmail());
			signupUsers.setName(userSingup.getName());

			SignupUsers dbUserData = usersRepo.save(signupUsers);

			if (dbUserData != null) {

				emailService.sendTemplateEmail(
					    "vrushikesh2506@gmail.com",
					    dbUserData.getEmail(),
					    "Signup successful",
					    "common-email-template",
					    dbUserData.getName(),
					    "signup",
					    "http://localhost:8080/login"
					);
			}

			return dbUserData;
		} else {
			throw new Exception("user already exists. please login");
		}

//		usersRepo.existsById(null)

	}

	public SignupUsers forgotPasswordService(ForgotPasswordApiData forgotPasswordApiData) throws Exception {
		SignupUsers signupUsers = new SignupUsers();
		Optional<SignupUsers> dbemailData = usersRepo.findByEmail(forgotPasswordApiData.getEmail());
		if (dbemailData.isEmpty()) {
			throw new Exception("user does not exists. please login");
		} else {
			SignupUsers dbUserData = dbemailData.get();

			dbUserData.setPasswordResetuuid(UUID.randomUUID().toString());
			SignupUsers passwordResetData = usersRepo.save(dbUserData);

			System.out.print(passwordResetData);
			//return passwordResetData;
			
			String resetLink = "http://localhost:3000/reset-password?passwordResetuuid=" + passwordResetData.getPasswordResetuuid();

			emailService.sendTemplateEmail(
			        "vrushikesh2506@gmail.com",
			        passwordResetData.getEmail(),
			        "Reset Your Password",
			        "EmailTemplate",
			        passwordResetData.getName(),
			        "reset-password",
			        resetLink
			);
			return passwordResetData;
		}

	}

	public SignupUsers resetPasswordService(ResetPasswordApiData resetPasswordApiData) throws Exception {
		SignupUsers user = new SignupUsers();
		Optional<SignupUsers> dbemailData = usersRepo
				.findByPasswordResetuuid(resetPasswordApiData.getPasswordResetuuid());
		
		if (dbemailData.isEmpty()) {
			throw new Exception("password reset link is not active please try again");
		}
		
		if ((resetPasswordApiData.getPassword().equals(resetPasswordApiData.getConfirmPassword()))==false) {
				throw new Exception("both passwords does not match");
		}
		
		if ( resetPasswordApiData.getPasswordResetuuid().isEmpty() ) {
			throw new Exception("password reset link is not active please try again");
		}
		
		SignupUsers dbUserData = dbemailData.get();
		dbUserData.setPassword(passwordEncoder.encode(resetPasswordApiData.getPassword()));
		dbUserData.setPasswordResetuuid(null);
		 SignupUsers passwordResetData= usersRepo.save(dbUserData);
		 
		 return passwordResetData;
		

	}

	public SignupUsers userLoginService(UserLogin userLogin) throws Exception {
		SignupUsers signupUsers = new SignupUsers();

		Optional<SignupUsers> dbemailData = usersRepo.findByEmail(userLogin.getEmail());

		if (dbemailData.isEmpty()) {
			throw new Exception("user already exists. please login");
		} else {
			SignupUsers dbUserData = dbemailData.get();

			if (passwordEncoder.matches(userLogin.getPassword(), dbUserData.getPassword())) {
				Map<String, Object> loginDetails = new HashMap<String, Object>();
				loginDetails.put("message", "success");
				loginDetails.put("user", userLogin.getEmail());
				return dbUserData;
			} else {
				throw new Exception("Invalid password please try again");
			}
		}
	}

}

package com.rushitech.main.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.rushitech.main.entity.SignupUsers;
import com.rushitech.main.pojo.UserLogin;
import com.rushitech.main.pojo.UserSingup;
import com.rushitech.main.repository.UsersRepo;

@Service
public class UserService {

	@Autowired
	UsersRepo usersRepo;

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

			return dbUserData;
		} else {
			throw new Exception("user already exists. please login");
		}

//		usersRepo.existsById(null)

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

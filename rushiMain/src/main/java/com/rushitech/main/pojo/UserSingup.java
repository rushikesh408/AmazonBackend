package com.rushitech.main.pojo;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UserSingup {
	//name
	//email
	//password
	//phonenumber

		@NotNull(message = "name cannot be empty")
		private String name; 
		@NotNull(message = "email cannot be empty")
		@Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
		private String email;
		@NotNull(message = "mobile number cannot be empty")
		private int mobilenumber;
		@NotNull(message = "password is must")
		private String password;
		
}

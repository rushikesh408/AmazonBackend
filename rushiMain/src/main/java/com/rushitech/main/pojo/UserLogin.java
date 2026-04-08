package com.rushitech.main.pojo;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserLogin {
	@NotNull(message = "email cannot be null")
	@Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
	@Size(min = 3)
	private String email;
	@NotNull(message = "password cannot be null")
	@Size(min = 3)
	private String password;

}

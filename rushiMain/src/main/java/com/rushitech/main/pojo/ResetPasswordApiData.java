package com.rushitech.main.pojo;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ResetPasswordApiData {

	@NotNull(message = "unable to reset please try again")
	private String passwordResetuuid;

	@NotNull(message = "cannot be empty")

	private String password;

	@NotNull(message = "cannot be empty")

	private String confirmPassword;

}

package com.rushitech.main.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data; 

@Entity
@Table(name="signupuser")
@Data
public class SignupUsers {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private int id;
	private String email;
	private String name;
	private String password;
	private int mobilenumber;
	private LocalDateTime createdOnDateTime=LocalDateTime.now();
	private boolean isActive=true;
	private boolean isEmailVerified=false;
//	@Id
//	 @GeneratedValue(strategy = GenerationType.UUID)
//	private UUID userUnqId;
	
}

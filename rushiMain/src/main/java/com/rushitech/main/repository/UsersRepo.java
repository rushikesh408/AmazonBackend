package com.rushitech.main.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rushitech.main.entity.SignupUsers;
import java.util.List;


public interface UsersRepo extends JpaRepository<SignupUsers, Integer> {

		Optional< SignupUsers> findByEmail(String email);
		Optional< SignupUsers>  findByPassword(String password);
}

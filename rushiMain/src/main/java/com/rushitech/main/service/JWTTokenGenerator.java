package com.rushitech.main.service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.rushitech.main.entity.SignupUsers;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JWTTokenGenerator {

	@Value("${jwt.secret}")
	private String jwtSecret;
	private SecretKey key;

	private int jwtExpiration = 24 * 60 * 60 * 100;

	private Key generateSecretKey() {
		return Keys.hmacShaKeyFor(jwtSecret.getBytes());
	}

	public String generateToken(SignupUsers signupUsers) {

		Date tokenGeneratedTimeDate = new Date();

		Date expiryDate = new Date(tokenGeneratedTimeDate.getTime() + jwtExpiration);

		Map<String, Object> tokenMap = new HashMap<String, Object>();
		tokenMap.put("id", signupUsers.getId());
		tokenMap.put("email", signupUsers.getEmail());
		tokenMap.put("name", signupUsers.getName());

		String jwtToken = Jwts.builder().claims().add(tokenMap).and().subject(signupUsers.getEmail())
				.issuedAt(tokenGeneratedTimeDate).expiration(expiryDate).signWith(generateSecretKey()).compact();

		return jwtToken;
	}
	
	
	public Claims getJwtClaims(String token) {
		
		SecretKey secretKey = new SecretKeySpec(jwtSecret.getBytes(), "HmacSHA256");
		Claims claims = Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload();
		
		return claims;
	}
	
	
	public Boolean verifyJwtToken(String token) {
		Claims claims = getJwtClaims(token);
		Boolean isValidBoolean = claims.getExpiration().after(new Date());
		
		return isValidBoolean;
	}

}

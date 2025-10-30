package com.example.spring_security_jwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.spring_security_jwt.entity.User;
import com.example.spring_security_jwt.repository.UserRepository;
import com.example.spring_security_jwt.security.JwtUtil;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder encoder;

	@Autowired
	private JwtUtil jwtUtil;

	@PostMapping("/signin")
	public String authenticateUser(@RequestBody User user) {
		Authentication authentication = authenticationManager.authenticate(
				new org.springframework.security.authentication.UsernamePasswordAuthenticationToken(user.getUsername(),
						user.getPassword()));

		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		return jwtUtil.generateToken(userDetails.getUsername());
	}

	@PostMapping("/signup")
	public String registerUser(@RequestBody User user) {
		if (userRepository.existsByUsername(user.getUsername())) {
			return "User already exists";
		}

		User newUser = new User(null, user.getUsername(), encoder.encode(user.getPassword()));
		userRepository.save(newUser);

		return "User registerd successfully!";

	}

}

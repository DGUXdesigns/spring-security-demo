package com.example.spring_security_jwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.spring_security_jwt.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	User findByUsername(String username);

	boolean existsByUsername(String username);
}

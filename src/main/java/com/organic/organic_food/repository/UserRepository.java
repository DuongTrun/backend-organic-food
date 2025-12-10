package com.organic.organic_food.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.organic.organic_food.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByEmail(String email);
}

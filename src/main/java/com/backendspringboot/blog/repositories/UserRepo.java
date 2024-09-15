package com.backendspringboot.blog.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backendspringboot.blog.entities.User;

public interface UserRepo extends JpaRepository<User, Integer> {

Optional<User> findByEmail(String email);

	
}

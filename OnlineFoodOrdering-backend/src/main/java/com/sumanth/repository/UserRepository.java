package com.sumanth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sumanth.model.User;

public interface UserRepository extends JpaRepository<User,Long>{
	
	public User findByEmail(String username);
}

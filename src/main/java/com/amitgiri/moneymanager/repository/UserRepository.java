package com.amitgiri.moneymanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.amitgiri.moneymanager.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	public boolean existsByEmail(String email);
	
}

package com.suresh.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.suresh.models.UserEntity;

public interface UserDetailsRepository extends JpaRepository<UserEntity, Integer> {

	public UserEntity findByUserEmail(String email);
}

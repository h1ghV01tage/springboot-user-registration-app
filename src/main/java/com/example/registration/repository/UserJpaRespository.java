package com.example.registration.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.registration.dto.UserDTO;

public interface UserJpaRespository extends JpaRepository<UserDTO, Long> {

	UserDTO findByName(String name);
	
}

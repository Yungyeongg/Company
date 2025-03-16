package com.list.company.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.list.company.model.Users;

public interface UsersRepository extends JpaRepository<Users, Long> {
	
	Optional<Users> findByUserId(String userId); // Optional: 明示的なnull処理
}

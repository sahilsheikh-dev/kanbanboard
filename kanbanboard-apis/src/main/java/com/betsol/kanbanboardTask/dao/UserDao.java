package com.betsol.kanbanboardTask.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.betsol.kanbanboardTask.entity.Tbluser;

@Repository
public interface UserDao extends JpaRepository<Tbluser, Long> {
	
	Tbluser findByUsername(String username);
	Tbluser findByPassword(String password);
	Optional<Tbluser> findByUserId(String userId);
	boolean existsByUsername(String username);
	boolean existsByPassword(String password);
	boolean existsByUserId(String userId);
	
}

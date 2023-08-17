package com.betsol.kanbanboardTask.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.betsol.kanbanboardTask.entity.Tbltask;

@Repository
public interface TaskDao extends JpaRepository<Tbltask, Long> {
	
	 List<Tbltask> findByProjectId(String projectId);
	 List<Tbltask> findByAssigneeById(String userId);
	 Optional<Tbltask> findByTaskId(String taskId);
	 boolean existsByTaskId(String taskId);

}

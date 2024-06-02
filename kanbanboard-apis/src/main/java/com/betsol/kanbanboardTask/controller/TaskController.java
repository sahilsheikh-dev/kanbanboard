package com.betsol.kanbanboardTask.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.betsol.kanbanboardTask.entity.Tbltask;
import com.betsol.kanbanboardTask.service.TaskService;

@RestController
public class TaskController {
	
	@Autowired
	private TaskService taskService;
	
	@GetMapping("/")
	public String welcome() {
		return "Welcome to Kanban Board";
	}
	
	@GetMapping("/tasks")
	public List<Tbltask> getTasks() {
		return taskService.filterDeletedTask(taskService.findAll());
	}
	
	@GetMapping("/tasksByProjectId/{projectId}")
	public List<Tbltask> getTasksByProjectId(@PathVariable("projectId") String projectId) {
		return taskService.filterDeletedTask(taskService.findByProject(projectId));
	}
	
	@GetMapping("/tasksByUserId/{userId}")
	public List<Tbltask> getTasksByUserId(@PathVariable("userId") String userId) {
		return taskService.filterDeletedTask(taskService.findByAssigneeById(userId));
	}
	
	@GetMapping("/tasks/{id}")
	public Tbltask getTasks(@PathVariable("id") String taskId) {
		return taskService.findByTaskId(taskId);
	}
	
	@PostMapping("/tasks")
	public Tbltask saveTask(@RequestBody Tbltask task) {
		return taskService.saveTask(task);
	}
	
	@PutMapping("/tasks")
	public Tbltask updateTask(@RequestBody Tbltask task) {
		return taskService.updateTask(task);
	}
	
	@PutMapping("/changeStatus")
	public Tbltask changeStatus(@RequestParam("taskId") String taskId, @RequestParam("status") String status) {
		return taskService.changeStatus(taskId, status);
	}
	
	@PutMapping("/taskInitialized/{taskId}")
	public Tbltask taskInitialized(@PathVariable String taskId) {
		return taskService.taskInitialized(taskId);
	}
	
	@DeleteMapping("/softDelete/{taskId}")
	public Tbltask softDeleteById(@PathVariable("taskId") String taskId) {
		return taskService.softDeleteTask(taskId);
	}
	
	@PutMapping("/recoverTask/{taskId}")
	public Tbltask recoverTaskById(@PathVariable("taskId") String taskId) {
		return taskService.recoverTaskById(taskId);
	}
	
	@DeleteMapping("deleteTask/{taskId}")
	public Tbltask deleteTask(@PathVariable("taskId") String taskId) {
		return taskService.deleteTask(taskId);
	}
	
	@GetMapping("/deletedTasks")
	public List<Tbltask> getDeletedTask() {
		return taskService.getDeletedTask();
	}
	
	
}

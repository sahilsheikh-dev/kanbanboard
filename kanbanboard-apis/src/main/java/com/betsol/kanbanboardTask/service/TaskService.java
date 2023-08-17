package com.betsol.kanbanboardTask.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.betsol.kanbanboardTask.Enum.TaskResponseMessages;
import com.betsol.kanbanboardTask.dao.TaskDao;
import com.betsol.kanbanboardTask.entity.Tbltask;
import com.betsol.kanbanboardTask.utilities.Utilities;

@Service
public class TaskService {
	
	@Autowired
	private TaskDao taskDao;
	
	public boolean existsById(Long id) {
		return taskDao.existsById(id);
	}
	
	public boolean existsByTaskId(String taskId) {
		return taskDao.existsByTaskId(taskId);
	}
	
	public List<Tbltask> findAll() {
		return sortTaskByPriority(taskDao.findAll());
	}
	
	public List<Tbltask> findByProject(String projectId) {
		return sortTaskByPriority(taskDao.findByProjectId(projectId));
	}
	
	public List<Tbltask> sortTaskByPriority(List<Tbltask> tasks) {
		Collections.sort(tasks, new Comparator<Tbltask>() {
			@Override
			public int compare(Tbltask t1, Tbltask t2) {
				if (t1.getPriority() == null || t2.getPriority() == null) return 0;
				else return Integer.compare(Integer.parseInt(String.valueOf(t1.getPriority().replace("P", ""))), 
						Integer.parseInt(String.valueOf(t2.getPriority().replace("P", ""))));
			}
		});
		return tasks;
	}
	
	public List<Tbltask> filterDeletedTask(List<Tbltask> tasks) {
		ArrayList<Tbltask> taskList = new ArrayList<>();
		for(Tbltask task: tasks) {
			if (task.getIsDeleted() == 0) taskList.add(task);
		}
		return taskList;
	}
	
	public List<Tbltask> getDeletedTask() {
		List<Tbltask> tasks = findAll();
		ArrayList<Tbltask> taskList = new ArrayList<>();
		for(Tbltask task: tasks)
			if (task.getIsDeleted() == 1) taskList.add(task);
		return taskList;
	}
	
	public Tbltask findById(Long id) {
		Tbltask task = new Tbltask();
		if (existsById(id)) {
			Optional<Tbltask> optTask = taskDao.findById(id);
			task = optTask.get();
		} else task.setOutputCode(TaskResponseMessages.TASK_NOT_FOUND.getTaskResponseMessages());
		return task;
	}
	
	public Tbltask findByTaskId(String taskId) {
		Tbltask task = new Tbltask();
		if (existsByTaskId(taskId)) {
			Optional<Tbltask> optTask = taskDao.findByTaskId(taskId);
			task = optTask.get();
		} else task.setOutputCode(TaskResponseMessages.TASK_NOT_FOUND.getTaskResponseMessages());
		return task;
	}
	
	public Tbltask saveTask(Tbltask task) {
		Tbltask tblTask = new Tbltask();
		if (Utilities.isEmpty(task.getTaskId())) {
			if (!existsByTaskId(task.getTaskId())) task.setTaskId(Utilities.generateRandomId(30));
		}
		LocalDate createdDate = LocalDate.now();
		task.setCreatedAt(createdDate.toString());
		tblTask = taskDao.save(task);
		if (existsById(tblTask.getId())) tblTask.setOutputCode(TaskResponseMessages.TASK_ADDED.getTaskResponseMessages());
		else tblTask.setOutputCode(TaskResponseMessages.TASK_FAILED.getTaskResponseMessages());
		return tblTask;
	}
	
	public Tbltask updateTask(Tbltask task) {
		Tbltask tblTask = new Tbltask();
		if (existsByTaskId(task.getTaskId())) {
			tblTask = saveTask(task);
			if (existsById(tblTask.getId())) tblTask.setOutputCode(TaskResponseMessages.TASK_UPDATED.getTaskResponseMessages());
			else tblTask.setOutputCode(TaskResponseMessages.TASK_FAILED.getTaskResponseMessages());
		} else {
			tblTask.setOutputCode(TaskResponseMessages.TASK_NOT_FOUND.getTaskResponseMessages());
		}
		return tblTask;
	}
	
	public Tbltask softDeleteTask(String taskId) {
		Tbltask task = new Tbltask();
		if (existsByTaskId(taskId)) {
			task = findByTaskId(taskId);
			task.setIsDeleted(1);
			taskDao.save(task);
			if (task.getIsDeleted() == 1) task.setOutputCode(TaskResponseMessages.TASK_MOVED_TO_RECYCLEBIN.getTaskResponseMessages());
			else task.setOutputCode(TaskResponseMessages.TASK_FAILED.getTaskResponseMessages());
		} else task.setOutputCode(TaskResponseMessages.TASK_NOT_FOUND.getTaskResponseMessages());
		return task;
	}
	
	public Tbltask recoverTaskById(String taskId) {
		Tbltask task = new Tbltask();
		if (existsByTaskId(taskId)) {
			task = findByTaskId(taskId);
			task.setIsDeleted(0);
			taskDao.save(task);
			if (task.getIsDeleted() == 0) task.setOutputCode(TaskResponseMessages.TASK_RESTORED.getTaskResponseMessages());
			else task.setOutputCode(TaskResponseMessages.TASK_FAILED.getTaskResponseMessages());
		} else task.setOutputCode(TaskResponseMessages.TASK_NOT_FOUND.getTaskResponseMessages());
		return task;
	}
	
	public Tbltask deleteTask(String taskId) {
		Tbltask task = new Tbltask();
		if (existsByTaskId(taskId)) {
			task = findByTaskId(taskId);
			taskDao.delete(task);
			if (existsById(task.getId())) task.setOutputCode(TaskResponseMessages.TASK_FAILED.getTaskResponseMessages());
			else {
				task = new Tbltask();
				task.setOutputCode(TaskResponseMessages.TASK_DELETED.getTaskResponseMessages());
			}
		} else task.setOutputCode(TaskResponseMessages.TASK_NOT_FOUND.getTaskResponseMessages());
		return task;
	}
	
	public Tbltask changeStatus(String taskId, String status) {
		Tbltask task = new Tbltask();
		if (existsByTaskId(taskId)) {
			task = findByTaskId(taskId);
			task.setStatus(status);
			task = saveTask(task);
			if (existsById(task.getId())) task.setOutputCode(TaskResponseMessages.TASK_UPDATED.getTaskResponseMessages());
			else task.setOutputCode(TaskResponseMessages.TASK_FAILED.getTaskResponseMessages());
		} else task.setOutputCode(TaskResponseMessages.TASK_NOT_FOUND.getTaskResponseMessages());
		return task;
	}
	
	public Tbltask taskInitialized(String taskId) {
		Tbltask task = new Tbltask();
		if (existsByTaskId(taskId)) {
			task = findByTaskId(taskId);
			LocalDate createdDate = LocalDate.now();
			task.setTaskInitializedAt(createdDate.toString());
			task = saveTask(task);
			if (existsById(task.getId())) task.setOutputCode(TaskResponseMessages.TASK_UPDATED.getTaskResponseMessages());
			else task.setOutputCode(TaskResponseMessages.TASK_FAILED.getTaskResponseMessages());
		} else task.setOutputCode(TaskResponseMessages.TASK_NOT_FOUND.getTaskResponseMessages());
		return task;
	}

	public List<Tbltask> findByAssigneeById(String userId) {
		return sortTaskByPriority(taskDao.findByAssigneeById(userId));
	}

}

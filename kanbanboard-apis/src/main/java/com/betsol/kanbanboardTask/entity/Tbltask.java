package com.betsol.kanbanboardTask.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table
public class Tbltask implements Comparable<Tbltask> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, length = 30)
	private String taskId;
	
	@Column(nullable = false, length = 4000)
	private String title;
	
	@Column(nullable = false, length = 4000)
	private String summary;
	
	@Column(nullable = false, length = 4000)
	private String description;
	
	@Column(nullable = false, length = 2)
	private String priority;
	
	@Column(nullable = false, length = 50)
	private String status;
	
	@Column(nullable = false)
	private String assigneeId;
	
	@Column(nullable = false)
	private String assigneeById;
	
	@Column(nullable = true, length = 30)
	private String projectId;
	
	@Column(nullable = false, length = 10)
	private String deadline;
	
	@Column(nullable = true, length = 10)
	private String taskInitializedAt;
	
	@Column(nullable = false, length = 10)
	private String createdAt;
	
	@Column(nullable = false, columnDefinition = "int default 0")
	private int isDeleted;
	
	@Transient
	private String outputCode;
	
	public Tbltask() {}
	
	public Tbltask(Long id, String taskId, String title, String summary, String description, String priority, String status, String assigneeId,
			String assigneeById, String projectId, String deadline, String taskInitializedAt, String createdAt, int isDeleted) {
		this.id = id;
		this.taskId = taskId;
		this.title = title;
		this.summary = summary;
		this.description = description;
		this.priority = priority;
		this.status = status;
		this.assigneeId = assigneeId;
		this.assigneeById = assigneeById;
		this.projectId = projectId;
		this.deadline = deadline;
		this.taskInitializedAt = taskInitializedAt;
		this.createdAt = createdAt;
		this.isDeleted = isDeleted;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAssigneeId() {
		return assigneeId;
	}

	public void setAssigneeId(String assigneeId) {
		this.assigneeId = assigneeId;
	}
	
	public String getAssigneeById() {
		return assigneeById;
	}

	public void setAssigneeById(String assigneeById) {
		this.assigneeById = assigneeById;
	}
	
	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getDeadline() {
		return deadline;
	}

	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}

	public String getTaskInitializedAt() {
		return taskInitializedAt;
	}

	public void setTaskInitializedAt(String taskInitializedAt) {
		this.taskInitializedAt = taskInitializedAt;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public int getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(int isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getOutputCode() {
		return outputCode;
	}

	public void setOutputCode(String outputCode) {
		this.outputCode = outputCode;
	}

	@Override
	public int compareTo(Tbltask o) {
		if (getPriority() == null || o.getPriority() == null) return 0;
		else return getPriority().compareTo(o.getPriority());
	}

}
package com.betsol.kanbanboardTask.Enum;

public enum TaskResponseMessages {
	TASK_ADDED("Task added successfully!"),
	TASK_UPDATED("Task updated successfully!"),
	TASK_MOVED_TO_RECYCLEBIN("Task Moved to Recycle Bin"),
	TASK_RESTORED("Task Restored"),
	TASK_DELETED("Task Deleted Successfully!"),
	TASK_FAILED("Operation Failed"),
	TASK_NOT_FOUND("Task Not Found");

    private String taskResponseMessages;

    private TaskResponseMessages(String taskResponseMessages) {
        this.taskResponseMessages = taskResponseMessages;
    }

    public String getTaskResponseMessages() {
        return taskResponseMessages;
    }

    public void setTaskResponseMessages(String passwordResponseMessages) {
        this.taskResponseMessages = passwordResponseMessages;
    }
}

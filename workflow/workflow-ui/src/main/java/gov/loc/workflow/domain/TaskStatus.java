package gov.loc.workflow.domain;

import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class TaskStatus {

	private String task;
	private String priority;
	private String status;
	private Date createdOn;
	
	public TaskStatus() {}
	
	public TaskStatus(String task, String priority, String status, Date createdOn, Date dueTo) {
		this.task = task;
		this.priority = priority;
		this.status = status;
		this.createdOn = createdOn;
	}

	public String getTask() {
		return task;
	}

	public void setTask(String task) {
		this.task = task;
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

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}


}
